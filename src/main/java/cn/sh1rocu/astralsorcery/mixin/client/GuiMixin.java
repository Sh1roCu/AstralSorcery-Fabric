package cn.sh1rocu.astralsorcery.mixin.client;

import hellfirepvp.astralsorcery.client.helper.WandPreviewRenderHelper;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
    @Inject(method = "renderHotbarAndDecorations", at = @At("HEAD"))
    private void as$onRenderHotbar(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        WandPreviewRenderHelper.onRenderGuiLayer(guiGraphics);
    }
}