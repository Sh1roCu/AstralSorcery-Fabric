package cn.sh1rocu.astralsorcery.mixin.common;

import cn.sh1rocu.astralsorcery.api.event.LivingChangeTargetEvent;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;
import java.util.function.Predicate;

@Mixin(StartAttacking.class)
public class StartAttackingMixin {
    @SuppressWarnings("rawtypes")
    @Inject(method = "method_47123", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/behavior/declarative/MemoryAccessor;set(Ljava/lang/Object;)V"), cancellable = true)
    private static void as$onLivingChangeTarget(
            Predicate predicate, Function function, MemoryAccessor memoryAccessor, MemoryAccessor memoryAccessor2, ServerLevel level, Mob mob, long l, CallbackInfoReturnable<Boolean> cir,
            @Local LivingEntity target, @Share("livingChangeTargetEvent") LocalRef<LivingChangeTargetEvent> eventRef
    ) {
        var changeTargetEvent = new LivingChangeTargetEvent(mob, target, LivingChangeTargetEvent.LivingTargetType.BEHAVIOR_TARGET);
        LivingChangeTargetEvent.EVENT.invoker().post(changeTargetEvent);
        eventRef.set(changeTargetEvent);
        if (changeTargetEvent.isCanceled() || changeTargetEvent.getNewAboutToBeSetTarget() == null)
            cir.setReturnValue(false);
    }

    @ModifyArg(method = "method_47123", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/behavior/declarative/MemoryAccessor;set(Ljava/lang/Object;)V"))
    private static Object as$changeTarget(Object object, @Share("livingChangeTargetEvent") LocalRef<LivingChangeTargetEvent> eventRef) {
        return eventRef.get().getNewAboutToBeSetTarget();
    }
}