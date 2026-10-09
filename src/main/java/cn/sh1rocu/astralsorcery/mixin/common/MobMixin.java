package cn.sh1rocu.astralsorcery.mixin.common;

import cn.sh1rocu.astralsorcery.api.event.LivingChangeTargetEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Mob.class)
public abstract class MobMixin {
    @Shadow
    @Nullable
    private LivingEntity target;

    @ModifyVariable(method = "setTarget", at = @At("HEAD"), argsOnly = true)
    private LivingEntity as$onChangeTarget(LivingEntity value) {
        var changeTargetEvent = new LivingChangeTargetEvent((Mob) (Object) this, value, LivingChangeTargetEvent.LivingTargetType.MOB_TARGET);
        LivingChangeTargetEvent.EVENT.invoker().post(changeTargetEvent);
        if (!changeTargetEvent.isCanceled()) {
            return changeTargetEvent.getNewAboutToBeSetTarget();
        }
        return this.target;
    }
}