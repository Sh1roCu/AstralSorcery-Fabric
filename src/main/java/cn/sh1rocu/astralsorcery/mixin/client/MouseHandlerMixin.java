package cn.sh1rocu.astralsorcery.mixin.client;

import hellfirepvp.astralsorcery.client.helper.RenderAstrolabeOverlay;
import hellfirepvp.astralsorcery.client.util.camera.CameraManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "onPress", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getOverlay()Lnet/minecraft/client/gui/screens/Overlay;"), cancellable = true)
    private void as$onMouseButtonPre(long windowPointer, int button, int action, int modifiers, CallbackInfo ci) {
        var cancelled = new AtomicBoolean(false);
        RenderAstrolabeOverlay.overrideMouseClickDuringDrawing(button, action, cancelled);
        CameraManager.getInstance().onMouseInput(cancelled);
        if (cancelled.get()) {
            ci.cancel();
        }
    }
}