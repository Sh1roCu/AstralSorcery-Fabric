package cn.sh1rocu.astralsorcery.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import hellfirepvp.astralsorcery.client.helper.RenderAstrolabeOverlay;
import hellfirepvp.astralsorcery.client.util.camera.CameraManager;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {

    @Inject(method = "onPress", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getOverlay()Lnet/minecraft/client/gui/screens/Overlay;"), cancellable = true)
    private void as$onMouseButtonPre(long windowPointer, int button, int action, int modifiers, CallbackInfo ci) {
        var cancelled = new AtomicBoolean(false);
        RenderAstrolabeOverlay.overrideMouseClickDuringDrawing(button, action, cancelled);
        CameraManager.getInstance().onMouseInput(cancelled);
        if (cancelled.get()) {
            ci.cancel();
        }
    }

    @Inject(method = "onScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isSpectator()Z"), cancellable = true)
    private void as$mouseScrolling(long windowPointer, double xOffset, double yOffset, CallbackInfo ci,
                                   @Local(ordinal = 3) double scrollX, @Local(ordinal = 4) double scrollY) {
        var cancelled = new AtomicBoolean(false);
        RenderAstrolabeOverlay.astrolabeMouseScroll(scrollX, scrollY, cancelled);
        if (cancelled.get()) {
            ci.cancel();
        }
    }
}