package cn.sh1rocu.astralsorcery.mixin.common;

import cn.sh1rocu.astralsorcery.AstralSorceryFabric;
import cn.sh1rocu.astralsorcery.api.event.*;
import cn.sh1rocu.astralsorcery.api.extension.ILandingEffectsBlock;
import cn.sh1rocu.astralsorcery.util.EventHooks;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    @Nullable
    public abstract AttributeInstance getAttribute(Holder<Attribute> attribute);

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyReturnValue(method = "createLivingAttributes", at = @At("RETURN"))
    private static AttributeSupplier.Builder as$addAttributes(AttributeSupplier.Builder builder) {
        return builder.add(AstralSorceryFabric.SWIM_SPEED);
    }

    @ModifyArg(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;moveRelative(FLnet/minecraft/world/phys/Vec3;)V", ordinal = 0))
    private float as$swimSpeed(float original) {
        return original * (float) this.getAttribute(AstralSorceryFabric.SWIM_SPEED).getValue();
    }

    @ModifyArg(method = "jumpInLiquid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;add(DDD)Lnet/minecraft/world/phys/Vec3;"), index = 1)
    private double as$modifySwimSpeed(double y) {
        return y * this.getAttribute(AstralSorceryFabric.SWIM_SPEED).getValue();
    }

    @ModifyVariable(method = "heal(F)V", at = @At("HEAD"), argsOnly = true)
    private float as$livingHealEvent(float original) {
        return EventHooks.onLivingHeal((LivingEntity) (Object) this, original);
    }

    @SuppressWarnings("ConstantConditions")
    @WrapOperation(
            method = "checkFallDamage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I"
            )
    )
    private int as$addLandingEffects(ServerLevel instance, ParticleOptions type, double posX, double posY, double posZ,
                                     int particleCount, double xOffset, double yOffset, double zOffset, double speed,
                                     Operation<Integer> original,
                                     @Local(argsOnly = true) BlockState state,
                                     @Local(argsOnly = true) BlockPos pos) {
        if (state.getBlock() instanceof ILandingEffectsBlock custom) {
            if (!custom.addLandingEffects(state, (ServerLevel) level(), pos, state, (LivingEntity) (Object) this, particleCount)) {
                return original.call(instance, type, posX, posY, posZ, particleCount, xOffset, yOffset, zOffset, speed);
            } else {
                return 0;
            }
        }

        return original.call(instance, type, posX, posY, posZ, particleCount, xOffset, yOffset, zOffset, speed);
    }

    @WrapMethod(method = "hurt")
    private boolean as$modifyDamageAmount(DamageSource source, float amount, Operation<Boolean> original) {
        return original.call(source, SimpleIncomingDamageCallback.MODIFY_DAMAGE.invoker().modifyDamage((LivingEntity) (Object) this, source, amount));
    }

    @ModifyVariable(method = "actuallyHurt", at = @At(value = "STORE", ordinal = 1), ordinal = 0, argsOnly = true)
    private float as$modifyActualDamageAmount(float original, @Local(argsOnly = true) DamageSource source) {
        return SimpleDamageCallback.PRE.invoker().onLivingDamagePre((LivingEntity) (Object) this, source, original, original);
    }

    @ModifyExpressionValue(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getDamageAfterArmorAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F"))
    private float as$pierceArmor(float absorbed, @Local(argsOnly = true) DamageSource source, @Local(argsOnly = true) float initialAmount) {
        if (SimpleIncomingDamageCallback.PIERCE_ARMOR.invoker().pierceArmor((LivingEntity) (Object) this, source)) {
            return initialAmount;
        }
        return absorbed;
    }

    @ModifyVariable(method = "knockback", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private double as$modifyKnockbackStrength(double strength, double ogstrength, double xRatio, double zRatio, @Share("knockbackEvent") LocalRef<LivingKnockBackEvent> eventRef) {
        var event = new LivingKnockBackEvent((LivingEntity) (Object) this, (float) strength, xRatio, zRatio);
        LivingKnockBackEvent.EVENT.invoker().onLivingKnockBack(event);
        eventRef.set(event);
        if (!event.isCanceled() && event.getOriginalStrength() != event.getStrength()) {
            return event.getStrength();
        }
        return strength;
    }

    @ModifyVariable(method = "knockback", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    private double as$modifyRatioX(double ratioX, @Share("knockbackEvent") LocalRef<LivingKnockBackEvent> eventRef) {
        var event = eventRef.get();
        if (event.getOriginalRatioX() != event.getRatioX())
            return event.getRatioX();
        return ratioX;
    }

    @ModifyVariable(method = "knockback", at = @At("HEAD"), ordinal = 2, argsOnly = true)
    private double as$modifyRatioZ(double ratioZ, @Share("knockbackEvent") LocalRef<LivingKnockBackEvent> eventRef) {
        var event = eventRef.get();
        if (event.getOriginalRatioZ() != event.getRatioZ())
            return event.getRatioZ();
        return ratioZ;
    }

    @Inject(method = "knockback", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getAttributeValue(Lnet/minecraft/core/Holder;)D"), cancellable = true)
    private void as$shouldCancelKnockback(double strength, double xRatio, double zRatio, CallbackInfo ci, @Share("knockbackEvent") LocalRef<LivingKnockBackEvent> eventRef) {
        if (eventRef.get().isCanceled())
            ci.cancel();
    }

    @ModifyExpressionValue(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isDamageSourceBlocked(Lnet/minecraft/world/damagesource/DamageSource;)Z"))
    private boolean as$damageBlocked(boolean original, @Local(argsOnly = true) DamageSource damageSource) {
        return SimpleShieldBlockCallback.EVENT.invoker().allowBlock(original, damageSource, (LivingEntity) (Object) this);
    }

    @Inject(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z", at = @At(value = "JUMP", opcode = Opcodes.IFNONNULL))
    private void as$onEffectAdded(MobEffectInstance newEffect, Entity entity, CallbackInfoReturnable<Boolean> cir, @Local(index = 3) MobEffectInstance oldEffect) {
        var event = new MobEffectEvent.Added((LivingEntity) (Object) this, oldEffect, newEffect, entity);
        MobEffectEvent.ADDED.invoker().post(event);
    }
}