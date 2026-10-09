/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.perk.tree.perk.key;

import cn.sh1rocu.astralsorcery.api.event.SimpleIncomingDamageCallback;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import hellfirepvp.astralsorcery.common.config.ConfigEntry;
import hellfirepvp.astralsorcery.common.lib.PerksAS;
import hellfirepvp.astralsorcery.common.lib.types.PerkDataTypesAS;
import hellfirepvp.astralsorcery.common.perk.PerkManager;
import hellfirepvp.astralsorcery.common.perk.convert.PerkAttributeConverter;
import hellfirepvp.astralsorcery.common.perk.modifier.PerkAttributeModifier;
import hellfirepvp.astralsorcery.common.perk.tree.PerkCategory;
import hellfirepvp.astralsorcery.common.perk.tree.PerkType;
import hellfirepvp.astralsorcery.common.perk.tree.perk.KeyPerk;
import hellfirepvp.astralsorcery.common.perk.tree.requirement.PerkRequirement;
import hellfirepvp.astralsorcery.common.research.PlayerProgress;
import hellfirepvp.astralsorcery.common.research.ResearchManager;
import net.fabricmc.api.EnvType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Collection;
import java.util.Collections;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: KeyPerkDamageArmor
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class KeyPerkDamageArmor extends KeyPerk {

    public static final MapCodec<KeyPerkDamageArmor> CODEC = RecordCodecBuilder.mapCodec(inst -> perkModifierFields(inst).apply(inst, KeyPerkDamageArmor::new));
    public static final PerkType<KeyPerkDamageArmor> TYPE =
            PerkType.of(KeyPerkDamageArmor.CODEC, PerkDataTypesAS.DEFAULT_DATA, KeyPerkDamageArmor::new);
    public static final Config CONFIG = new Config();

    private KeyPerkDamageArmor(ResourceLocation key, float x, float y) {
        this(key, defaultNameKey(key), x, y, PerkCategory.MAJOR, Collections.emptySet(), Collections.emptySet(), Collections.emptySet());
    }

    protected KeyPerkDamageArmor(ResourceLocation key, String nameKey, float x, float y, PerkCategory category, Collection<PerkRequirement> requirements, Collection<PerkAttributeConverter> converters, Collection<PerkAttributeModifier> modifiers) {
        super(key, nameKey, x, y, category, requirements, converters, modifiers);
    }

    @Override
    protected void attachEventListeners() {
        super.attachEventListeners();
        SimpleIncomingDamageCallback.MODIFY_DAMAGE.register(this::onDamage);
    }

    private float onDamage(LivingEntity target, DamageSource damageSource, float amount) {
        Entity source = damageSource.getEntity();
        if (!(source instanceof ServerPlayer sPlayer)) return amount;
        EnvType side = this.getSide(sPlayer);
        if (side != EnvType.SERVER) return amount;
        PlayerProgress progress = ResearchManager.getProgress(sPlayer, side);
        if (!progress.getPerkData().hasPerkEffect(this)) return amount;

        int foundArmorPieces = 0;
        for (ItemStack stack : target.getArmorSlots()) {
            if (!stack.isEmpty()) foundArmorPieces++;
        }
        if (foundArmorPieces <= 0) return amount;

        float result = amount;

        float perc = PerkManager.getOrCreateAttributes(sPlayer)
                .modifyValue(sPlayer, progress, PerksAS.AttributeTypes.PERK_EFFECT, CONFIG.damageConversionPerArmor.get().floatValue());
        result = Math.max(amount - perc * foundArmorPieces, 0);

        int armorDmg = (int) Math.ceil(amount * perc * 1.5F);
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmor()) continue;
            ItemStack stack = target.getItemBySlot(slot);
            if (stack.isEmpty()) continue;
            stack.hurtAndBreak(armorDmg, sPlayer.serverLevel(), sPlayer, item -> {
                target.onEquippedItemBroken(item, slot);
            });
        }

        return result;
    }

    @Override
    public PerkType<?> getType() {
        return TYPE;
    }

    public static class Config extends ConfigEntry {

        public ModConfigSpec.DoubleValue damageConversionPerArmor;

        private Config() {
            super("key_damage_armor");
        }

        @Override
        public void createEntries(ModConfigSpec.Builder cfgBuilder) {
            this.damageConversionPerArmor = cfgBuilder
                    .comment("Percentage of damage dealt that gets instead converted to durability damage towards armors.")
                    .translation(translationKey("damageConversionPerArmor"))
                    .defineInRange("damageConversionPerArmor", 0.05F, 0F, 1F);

        }
    }
}
