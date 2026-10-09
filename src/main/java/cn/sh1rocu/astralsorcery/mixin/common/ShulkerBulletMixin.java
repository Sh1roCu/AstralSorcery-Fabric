package cn.sh1rocu.astralsorcery.mixin.common;

import cn.sh1rocu.astralsorcery.util.EventHooks;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShulkerBullet.class)
public class ShulkerBulletMixin {
    @WrapWithCondition(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/ShulkerBullet;hitTargetOrDeflectSelf(Lnet/minecraft/world/phys/HitResult;)Lnet/minecraft/world/entity/projectile/ProjectileDeflection;"))
    private boolean as$onProjectileImpact(ShulkerBullet projectile, HitResult result) {
        return !EventHooks.onProjectileImpact(projectile, result);
    }
}
