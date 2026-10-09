package cn.sh1rocu.astralsorcery.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import hellfirepvp.astralsorcery.client.util.tooltip.TooltipUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TooltipRenderUtil.class)
public class TooltipRenderUtilMixin {

    @Inject(method = "renderTooltipBackground", at = @At("HEAD"))
    private static void as$storeBgColor(GuiGraphics guiGraphics, int x, int y, int width, int height, int z, CallbackInfo ci,
                                        @Share("colorOverride") LocalRef<TooltipUtil.ColorOverride> overrideLocalRef) {
        overrideLocalRef.set(TooltipUtil.getInstance().colorTooltip());
    }

    @WrapOperation(method = "renderTooltipBackground", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderHorizontalLine(Lnet/minecraft/client/gui/GuiGraphics;IIIII)V",
            ordinal = 0))
    private static void as$renderHorizontalLine1(GuiGraphics guiGraphics, int x, int y, int length, int z, int color, Operation<Void> original,
                                                 @Share("colorOverride") LocalRef<TooltipUtil.ColorOverride> overrideLocalRef) {
        var override = overrideLocalRef.get();
        original.call(guiGraphics, x, y, length, z, override == null ? color : override.backgroundStart());
    }

    @WrapOperation(method = "renderTooltipBackground", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderHorizontalLine(Lnet/minecraft/client/gui/GuiGraphics;IIIII)V",
            ordinal = 1))
    private static void as$renderHorizontalLine2(GuiGraphics guiGraphics, int x, int y, int length, int z, int color, Operation<Void> original,
                                                 @Share("colorOverride") LocalRef<TooltipUtil.ColorOverride> overrideLocalRef) {
        var override = overrideLocalRef.get();
        original.call(guiGraphics, x, y, length, z, override == null ? color : override.backgroundEnd());
    }

    @WrapOperation(method = "renderTooltipBackground", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderRectangle(Lnet/minecraft/client/gui/GuiGraphics;IIIIII)V"))
    private static void as$wrapRenderRectangle(GuiGraphics guiGraphics, int x, int y, int width, int height, int z, int color, Operation<Void> original,
                                               @Share("colorOverride") LocalRef<TooltipUtil.ColorOverride> overrideLocalRef) {
        var override = overrideLocalRef.get();
        if (override != null) {
            as$renderRectangle(guiGraphics, x, y, width, height, z, override.backgroundStart(), override.backgroundEnd());
        } else {
            original.call(guiGraphics, x, y, width, height, z, color);
        }
    }

    @Unique
    private static void as$renderRectangle(GuiGraphics guiGraphics, int x, int y, int width, int height, int z, int color, int colorTo) {
        guiGraphics.fillGradient(x, y, x + width, y + height, z, color, colorTo);
    }

    @WrapOperation(method = "renderTooltipBackground", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderVerticalLine(Lnet/minecraft/client/gui/GuiGraphics;IIIII)V",
            ordinal = 0))
    private static void as$renderVerticalLineGradient1(GuiGraphics guiGraphics, int x, int y, int length, int z, int color, Operation<Void> original,
                                                       @Share("colorOverride") LocalRef<TooltipUtil.ColorOverride> overrideLocalRef) {
        var override = overrideLocalRef.get();
        original.call(guiGraphics, x, y, length, z, override == null ? color : override.backgroundStart());
    }

    @WrapOperation(method = "renderTooltipBackground", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderVerticalLine(Lnet/minecraft/client/gui/GuiGraphics;IIIII)V",
            ordinal = 1))
    private static void as$renderVerticalLineGradient2(GuiGraphics guiGraphics, int x, int y, int length, int z, int color, Operation<Void> original,
                                                       @Share("colorOverride") LocalRef<TooltipUtil.ColorOverride> overrideLocalRef) {
        var override = overrideLocalRef.get();
        original.call(guiGraphics, x, y, length, z, override == null ? color : override.backgroundEnd());
    }

    @WrapOperation(method = "renderTooltipBackground", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderFrameGradient(Lnet/minecraft/client/gui/GuiGraphics;IIIIIII)V"))
    private static void as$renderFrameGradient(GuiGraphics guiGraphics, int x, int y, int width, int height, int z, int topColor, int bottomColor,
                                               Operation<Void> original,
                                               @Share("colorOverride") LocalRef<TooltipUtil.ColorOverride> overrideLocalRef) {
        var override = overrideLocalRef.get();
        if (override != null) {
            original.call(guiGraphics, x, y, width, height, z, override.borderStart(), override.borderEnd());
        } else {
            original.call(guiGraphics, x, y, width, height, z, topColor, bottomColor);
        }
    }
}
