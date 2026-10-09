/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.perk.tree.perk.root;

import cn.sh1rocu.astralsorcery.api.event.BlockPlaceCallback;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import hellfirepvp.astralsorcery.common.constellation.BaseConstellation;
import hellfirepvp.astralsorcery.common.event.AttributeEvent;
import hellfirepvp.astralsorcery.common.lib.ConstellationsAS;
import hellfirepvp.astralsorcery.common.lib.PerksAS;
import hellfirepvp.astralsorcery.common.lib.types.PerkDataTypesAS;
import hellfirepvp.astralsorcery.common.perk.PerkAttributeMap;
import hellfirepvp.astralsorcery.common.perk.PerkManager;
import hellfirepvp.astralsorcery.common.perk.convert.PerkAttributeConverter;
import hellfirepvp.astralsorcery.common.perk.modifier.PerkAttributeModifier;
import hellfirepvp.astralsorcery.common.perk.tree.AbstractPerk;
import hellfirepvp.astralsorcery.common.perk.tree.PerkCategory;
import hellfirepvp.astralsorcery.common.perk.tree.PerkType;
import hellfirepvp.astralsorcery.common.perk.tree.perk.RootPerk;
import hellfirepvp.astralsorcery.common.perk.tree.requirement.PerkRequirement;
import hellfirepvp.astralsorcery.common.research.PlayerProgress;
import hellfirepvp.astralsorcery.common.research.ResearchHelper;
import hellfirepvp.astralsorcery.common.research.ResearchManager;
import hellfirepvp.astralsorcery.common.util.DiminishingMultiplier;
import net.fabricmc.api.EnvType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collection;
import java.util.Collections;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: RootPerkAevitas
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class RootPerkAevitas extends RootPerk<AbstractPerk.Data> {

    public static final MapCodec<RootPerkAevitas> CODEC = RecordCodecBuilder.mapCodec(inst -> perkRootFields(inst).apply(inst, RootPerkAevitas::new));
    public static final PerkType<RootPerkAevitas> TYPE =
            PerkType.of(RootPerkAevitas.CODEC, PerkDataTypesAS.DEFAULT_DATA, RootPerkAevitas::new);
    public static final Config CONFIG = new Config("root.aevitas");

    private RootPerkAevitas(ResourceLocation key, float x, float y) {
        this(key, defaultNameKey(key), x, y, PerkCategory.ROOT, Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), ConstellationsAS.AEVITAS);
    }

    protected RootPerkAevitas(ResourceLocation key, String nameKey, float x, float y, PerkCategory category, Collection<PerkRequirement> requirements, Collection<PerkAttributeConverter> converters, Collection<PerkAttributeModifier> modifiers, BaseConstellation constellation) {
        super(key, nameKey, x, y, category, requirements, converters, modifiers, constellation);
    }

    @Override
    protected Config getConfig() {
        return CONFIG;
    }

    @Override
    protected DiminishingMultiplier createMultiplier() {
        return DiminishingMultiplier.of()
                .trackChunks(32, 32)
                .multiplierLossRate(0F)
                .build();
    }

    @Override
    protected void attachEventListeners() {
        super.attachEventListeners();
        BlockPlaceCallback.EVENT.register(this::onPlace);
    }

    private void onPlace(BlockItem blockItem, BlockPlaceContext context, BlockState placed) {
        if (!(context.getPlayer() instanceof ServerPlayer sPlayer)) return;
        EnvType side = this.getSide(sPlayer);
        if (side != EnvType.SERVER) return;
        PlayerProgress progress = ResearchManager.getProgress(sPlayer, side);
        if (!progress.getPerkData().hasPerkEffect(this)) return;
        PerkAttributeMap perkMap = PerkManager.getOrCreateAttributes(sPlayer);

        float hardness;
        try {
            hardness = placed.getDestroySpeed(sPlayer.serverLevel(), context.getClickedPos());
        } catch (Exception e) {
            hardness = 1F;
        }

        float xp = Math.min(hardness * 4F, 100F);
        xp *= this.getExpMultiplier();
        xp *= this.getDiminishingMultiplier(sPlayer);
        xp *= perkMap.getModifier(sPlayer, progress, PerksAS.AttributeTypes.PERK_EFFECT);
        xp *= perkMap.getModifier(sPlayer, progress, PerksAS.AttributeTypes.PERK_EXPERIENCE);

        xp = AttributeEvent.postProcessModded(sPlayer, PerksAS.AttributeTypes.PERK_EXPERIENCE, xp);

        ResearchHelper.addPerkExp(sPlayer, xp);
    }

    @Override
    public PerkType<?> getType() {
        return TYPE;
    }
}
