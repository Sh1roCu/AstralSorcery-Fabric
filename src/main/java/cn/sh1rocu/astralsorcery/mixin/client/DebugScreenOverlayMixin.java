package cn.sh1rocu.astralsorcery.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import hellfirepvp.astralsorcery.client.effect.EffectHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(DebugScreenOverlay.class)
public class DebugScreenOverlayMixin {
    @Inject(method = "drawGameInformation", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/DebugScreenOverlay;renderLines(Lnet/minecraft/client/gui/GuiGraphics;Ljava/util/List;Z)V"))
    private void as$drawLeft(GuiGraphics guiGraphics, CallbackInfo ci, @Local List<String> lines) {
        EffectHandler.getInstance().displayDebug(lines);
    }
}
