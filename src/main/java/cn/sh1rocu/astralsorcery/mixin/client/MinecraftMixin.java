package cn.sh1rocu.astralsorcery.mixin.client;

import cn.sh1rocu.astralsorcery.api.event.LevelEvent;
import cn.sh1rocu.astralsorcery.api.event.RenderFrameEvent;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import hellfirepvp.astralsorcery.client.helper.LinkSessionEffectHelper;
import hellfirepvp.astralsorcery.client.helper.RenderAstrolabeOverlay;
import hellfirepvp.astralsorcery.client.lib.ClientExtensionsAS;
import hellfirepvp.astralsorcery.common.event.handler.InteractEventHandler;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow
    @Nullable
    public LocalPlayer player;

    @Shadow
    @Nullable
    public ClientLevel level;

    @Shadow
    @Nullable
    public HitResult hitResult;

    @Shadow
    @Final
    private DeltaTracker.Timer timer;

    @Inject(method = "startAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;resetAttackStrengthTicker()V", shift = At.Shift.AFTER))
    private void as$leftClickEmpty(CallbackInfoReturnable<Boolean> cir) {
        InteractEventHandler.onLeftClickEmpty(this.player);
    }

    @Inject(method = "setLevel", at = @At("HEAD"))
    private void as$onUnload(ClientLevel clientLevel, ReceivingLevelScreen.Reason reason, CallbackInfo ci) {
        if (this.level != null) {
            var event = new LevelEvent.Unload(this.level);
            LevelEvent.UNLOAD.invoker().post(event);
        }
    }

    @Inject(method = "disconnect(Lnet/minecraft/client/gui/screens/Screen;Z)V", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/Minecraft;level:Lnet/minecraft/client/multiplayer/ClientLevel;",
            ordinal = 0,
            shift = At.Shift.AFTER
    ))
    private void as$onDisconnect(Screen screen, boolean keepResourcePacks, CallbackInfo ci) {
        if (this.level != null) {
            var event = new LevelEvent.Unload(this.level);
            LevelEvent.UNLOAD.invoker().post(event);
        }
    }


    @WrapWithCondition(
            method = "continueAttack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/particle/ParticleEngine;crack(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)V"
            )
    )
    private boolean as$addHitEffects(ParticleEngine engine, BlockPos pos, Direction side) {
        if (this.level == null) return true;
        BlockState state = this.level.getBlockState(pos);
        var ex = ClientExtensionsAS.of(state.getBlock());
        if (ex == null) {
            return true;
        }
        return !ex.addHitEffects(state, this.level, this.hitResult, engine);
    }

    @Inject(
            method = "setScreen",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/Minecraft;screen:Lnet/minecraft/client/gui/screens/Screen;",
                    opcode = Opcodes.PUTFIELD,
                    shift = At.Shift.BEFORE
            ),
            cancellable = true)
    private void as$preventOpenScreen(Screen screen, CallbackInfo ci) {
        var cancelled = new AtomicBoolean(false);
        RenderAstrolabeOverlay.preventScreenOpen(cancelled);
        if (cancelled.get()) {
            ci.cancel();
        }
    }

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;render(Lnet/minecraft/client/DeltaTracker;Z)V"))
    private void as$onRenderStart(CallbackInfo ci) {
        RenderFrameEvent.PRE.invoker().post(new RenderFrameEvent.Pre(this.timer));
    }

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;render(Lnet/minecraft/client/DeltaTracker;Z)V", shift = At.Shift.AFTER))
    private void as$onRenderEnd(CallbackInfo ci) {
        RenderFrameEvent.POST.invoker().post(new RenderFrameEvent.Post(this.timer));
    }

    @Inject(method = "continueAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/BlockHitResult;getDirection()Lnet/minecraft/core/Direction;"))
    private void as$onClickInputEvent(boolean leftClick, CallbackInfo ci, @Local BlockHitResult blockHitResult, @Local BlockPos blockPos) {
        LinkSessionEffectHelper.onMouseClick(0);
    }

    @Inject(method = "startAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/HitResult;getType()Lnet/minecraft/world/phys/HitResult$Type;"))
    private void as$onAttackClickInputEvent(CallbackInfoReturnable<Boolean> cir) {
        LinkSessionEffectHelper.onMouseClick(0);
    }

    @Inject(method = "startUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;", ordinal = 0))
    private void as$callForgeUseInputEvent(CallbackInfo ci) {
        LinkSessionEffectHelper.onMouseClick(1);
    }

    @Inject(method = "pickBlock", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Abilities;instabuild:Z", ordinal = 0))
    private void as$callInteractionPickInput(CallbackInfo ci) {
        LinkSessionEffectHelper.onMouseClick(2);
    }
}