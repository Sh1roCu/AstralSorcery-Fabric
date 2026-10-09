package cn.sh1rocu.astralsorcery.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import hellfirepvp.astralsorcery.common.lumen.binding.effect.LumenBindingCollectDropsEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(Block.class)
public class BlockMixin {
    @ModifyReturnValue(
            method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;",
            at = @At("RETURN"))
    private static List<ItemStack> as$getDrops(List<ItemStack> original, @Local(argsOnly = true) Entity breaker) {
        LumenBindingCollectDropsEffect.onBlockDrops(breaker, original);
        return original;
    }
}
