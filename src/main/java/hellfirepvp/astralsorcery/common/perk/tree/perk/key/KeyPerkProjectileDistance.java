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
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Collection;
import java.util.Collections;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: KeyPerkProjectileDistance
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class KeyPerkProjectileDistance extends KeyPerk {

    public static final MapCodec<KeyPerkProjectileDistance> CODEC = RecordCodecBuilder.mapCodec(inst -> perkModifierFields(inst).apply(inst, KeyPerkProjectileDistance::new));
    public static final PerkType<KeyPerkProjectileDistance> TYPE =
            PerkType.of(KeyPerkProjectileDistance.CODEC, PerkDataTypesAS.DEFAULT_DATA, KeyPerkProjectileDistance::new);
    public static final Config CONFIG = new Config();

    private KeyPerkProjectileDistance(ResourceLocation key, float x, float y) {
        this(key, defaultNameKey(key), x, y, PerkCategory.MAJOR, Collections.emptySet(), Collections.emptySet(), Collections.emptySet());
    }

    protected KeyPerkProjectileDistance(ResourceLocation key, String nameKey, float x, float y, PerkCategory category, Collection<PerkRequirement> requirements, Collection<PerkAttributeConverter> converters, Collection<PerkAttributeModifier> modifiers) {
        super(key, nameKey, x, y, category, requirements, converters, modifiers);
    }

    @Override
    protected void attachEventListeners() {
        super.attachEventListeners();
        SimpleIncomingDamageCallback.MODIFY_DAMAGE.register(this::onDamage);
    }

    private float onDamage(LivingEntity target, DamageSource damageSource, float amount) {
        if (!damageSource.is(DamageTypeTags.IS_PROJECTILE)) return amount;
        Entity source = damageSource.getEntity();
        if (!(source instanceof ServerPlayer sPlayer)) return amount;
        if (damageSource.isDirect()) return amount;
        EnvType side = this.getSide(sPlayer);
        if (side != EnvType.SERVER) return amount;
        PlayerProgress progress = ResearchManager.getProgress(sPlayer, side);
        if (!progress.getPerkData().hasPerkEffect(this)) return amount;

        double dist = sPlayer.distanceTo(target) / CONFIG.distanceCap.getAsDouble();
        dist = Math.min(Math.pow(dist, 1.5F), 1);

        float mult = PerkManager.getOrCreateAttributes(sPlayer)
                .modifyValue(sPlayer, progress, PerksAS.AttributeTypes.PERK_EFFECT, CONFIG.additionalDamageMultiplier.get().floatValue());

        float result = amount;
        result *= (float) (1 + mult * dist);

        return result;
    }

    @Override
    public PerkType<?> getType() {
        return TYPE;
    }

    public static class Config extends ConfigEntry {

        public ModConfigSpec.DoubleValue distanceCap;
        public ModConfigSpec.DoubleValue additionalDamageMultiplier;

        public Config() {
            super("key_projectile_distance");
        }

        @Override
        public void createEntries(ModConfigSpec.Builder cfgBuilder) {
            this.distanceCap = cfgBuilder
                    .comment("Distance at and after which the full 'additionalDamageMultiplier' is applied.")
                    .translation(translationKey("distanceCap"))
                    .defineInRange("distanceCap", 192F, 4, Short.MAX_VALUE);
            this.additionalDamageMultiplier = cfgBuilder
                    .comment("The maximum damage multiplier obtainable at max distance.")
                    .translation(translationKey("additionalDamageMultiplier"))
                    .defineInRange("additionalDamageMultiplier", 1.5F, 0.05F, 50F);
        }
    }
}
