package cn.sh1rocu.astralsorcery.mixin.enumextensions;

import hellfirepvp.astralsorcery.AstralSorcery;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.MixinIntrinsics;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Rarity.class)
enum RarityMixin {
    ASTRALSORCERY_RELIC(MixinIntrinsics.currentEnumOrdinal(), AstralSorcery.MODID + ":relic", ChatFormatting.ASTRALSORCERY_RELIC),
    ASTRALSORCERY_ARTIFACT(MixinIntrinsics.currentEnumOrdinal(), AstralSorcery.MODID + ":relic", ChatFormatting.ASTRALSORCERY_ARTIFACT);

    @Shadow
    RarityMixin(final int id, final String name, final ChatFormatting color) {
    }
}
