package cn.sh1rocu.astralsorcery.mixin.common;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import hellfirepvp.astralsorcery.common.lib.FluidsAS;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    public ItemEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/item/ItemEntity;getDeltaMovement()Lnet/minecraft/world/phys/Vec3;",
            ordinal = 0, shift = At.Shift.AFTER))
    private void as$setItemMovement(CallbackInfo ci, @Share("inStarlight") LocalBooleanRef inStarlight) {
        inStarlight.set(false);
        if (this.getFluidHeight(FluidsAS.LIQUID_STARLIGHT_KEY) > 0.1F) {
            inStarlight.set(true);
            double gravity = this.getGravity();
            if (gravity != 0) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, -gravity, 0.0));
            }
        }
    }

    @WrapWithCondition(method = "tick", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/item/ItemEntity;setUnderwaterMovement()V",
            ordinal = 0))
    private boolean as$setUnderwaterMovement(ItemEntity instance, @Share("inStarlight") LocalBooleanRef inStarlight) {
        return !inStarlight.get();
    }

    @WrapWithCondition(method = "tick", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/item/ItemEntity;setUnderLavaMovement()V",
            ordinal = 0))
    private boolean as$setUnderLavaMovement(ItemEntity instance, @Share("inStarlight") LocalBooleanRef inStarlight) {
        return !inStarlight.get();
    }

    @WrapWithCondition(method = "tick", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/item/ItemEntity;applyGravity()V",
            ordinal = 0))
    private boolean as$applyGravity(ItemEntity instance, @Share("inStarlight") LocalBooleanRef inStarlight) {
        return !inStarlight.get();
    }
}
