package cn.sh1rocu.astralsorcery.mixin.common;

import cn.sh1rocu.astralsorcery.AstralSorceryFabric;
import cn.sh1rocu.astralsorcery.api.event.*;
import cn.sh1rocu.astralsorcery.util.EventHooks;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void as$tickPreEvent(CallbackInfo ci) {
        PlayerTickEvent.PRE.invoker().post(new PlayerTickEvent.Pre((Player) (Object) this));
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void as$tickPostEvent(CallbackInfo ci) {
        PlayerTickEvent.POST.invoker().post(new PlayerTickEvent.Post((Player) (Object) this));
    }

    @ModifyReturnValue(method = "createAttributes", at = @At("RETURN"))
    private static AttributeSupplier.Builder as$addPlayerAttributes(AttributeSupplier.Builder original) {
        return original.add(AstralSorceryFabric.CREATIVE_FLIGHT);
    }

    @WrapOperation(method = "causeFallDamage", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Abilities;mayfly:Z", opcode = Opcodes.GETFIELD))
    private boolean as$mayFly(Abilities instance, Operation<Boolean> original) {
        return original.call(instance) || this.getAttributeValue(AstralSorceryFabric.CREATIVE_FLIGHT) > 0;
    }

    @ModifyReturnValue(method = "hasCorrectToolForDrops", at = @At("RETURN"))
    private boolean as$hasCorrectToolForDrops(boolean original, @Local(argsOnly = true) BlockState state) {
        return SimpleHarvestCheckCallback.EVENT.invoker().canHarvest(original, (Player) (Object) this, state);
    }

    @ModifyReturnValue(method = "getDestroySpeed", at = @At("RETURN"))
    private float as$getDestroySpeed(float original, @Local(argsOnly = true) BlockState state) {
        return EventHooks.getBreakSpeed((Player) (Object) this, state, original, null);
    }

    @ModifyVariable(method = "actuallyHurt", at = @At(value = "STORE", ordinal = 1), ordinal = 0, argsOnly = true)
    private float as$modifyActualDamageAmount(float original, @Local(argsOnly = true) DamageSource source) {
        return SimpleDamageCallback.PRE.invoker().onLivingDamagePre((Player) (Object) this, source, original, original);
    }

    @ModifyExpressionValue(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getDamageAfterArmorAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F"))
    private float as$pierceArmor(float absorbed, @Local(argsOnly = true) DamageSource source, @Local(argsOnly = true) float initialAmount) {
        if (SimpleIncomingDamageCallback.PIERCE_ARMOR.invoker().pierceArmor((Player) (Object) this, source)) {
            return initialAmount;
        }
        return absorbed;
    }

    // from PortingLib
    @ModifyVariable(
            method = "attack",
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isSprinting()Z", ordinal = 1),
                    to = @At(value = "CONSTANT", args = "floatValue=1.5F")
            ),
            at = @At(value = "STORE", opcode = Opcodes.ISTORE), index = 9
    )
    private boolean as$modifyResult(boolean value, @Share("isCriticalHit") LocalBooleanRef vanilla) {
        vanilla.set(value);
        return true;
    }

    // from PortingLib
    @ModifyVariable(method = "attack", at = @At(value = "CONSTANT", args = "floatValue=1.5F"), index = 9)
    private boolean as$modifyVanillaResult(boolean value, @Share("isCriticalHit") LocalBooleanRef vanilla) {
        return vanilla.get();
    }

    // from PortingLib
    @ModifyExpressionValue(method = "attack", at = @At(value = "CONSTANT", args = "floatValue=1.5F"))
    private float as$getCriticalDamageMultiplier(float original, Entity target, @Share("isCriticalHit") LocalBooleanRef vanilla, @Share("event") LocalRef<CriticalHitEvent> eventRef) {
        boolean vanillaCritical = vanilla.get();
        var critEvent = EventHooks.fireCriticalHit((Player) (Object) this, target, vanillaCritical, vanillaCritical ? original : 1.0F);
        eventRef.set(critEvent);
        if (critEvent.isCriticalHit()) {
            vanilla.set(true);
            return critEvent.getDamageMultiplier();
        }
        return 1.0F;
    }

    // from PortingLib
    @ModifyVariable(method = "attack", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Player;walkDist:F"), index = 9)
    private boolean as$isCriticalHit(boolean value, @Share("event") LocalRef<CriticalHitEvent> eventRef) {
        return eventRef.get().isCriticalHit();
    }
}