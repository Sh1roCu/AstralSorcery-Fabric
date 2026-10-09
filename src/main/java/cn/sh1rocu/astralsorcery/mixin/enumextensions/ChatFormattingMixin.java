package cn.sh1rocu.astralsorcery.mixin.enumextensions;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.lib.constants.ColorsAS;
import net.minecraft.ChatFormatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.MixinIntrinsics;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Locale;

@Mixin(ChatFormatting.class)
enum ChatFormattingMixin {
    ASTRALSORCERY_RELIC(AstralSorcery.MODID.toUpperCase(Locale.ENGLISH) + ":RELIC", '!', MixinIntrinsics.currentEnumOrdinal(), ColorsAS.RARITY_RELIC.getColor()),
    ASTRALSORCERY_ARTIFACT(AstralSorcery.MODID.toUpperCase(Locale.ENGLISH) + ":ARTIFACT", '@', MixinIntrinsics.currentEnumOrdinal(), ColorsAS.RARITY_ARTIFACT.getColor());

    @Shadow
    ChatFormattingMixin(final String name, final char code, final int id, @Nullable final Integer color) {
    }
}
