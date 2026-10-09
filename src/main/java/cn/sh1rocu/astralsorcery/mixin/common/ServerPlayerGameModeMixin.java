package cn.sh1rocu.astralsorcery.mixin.common;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import hellfirepvp.astralsorcery.common.block.tile.LumenCrystalClusterBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {

    @Shadow
    @Final
    protected ServerPlayer player;

    @WrapOperation(method = "destroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z"))
    private boolean as$destroyBlock(ServerLevel instance, BlockPos pos, boolean b, Operation<Boolean> original, @Local(ordinal = 1) BlockState state) {
        if (state.getBlock() instanceof LumenCrystalClusterBlock block) {
            return block.onDestroyedByPlayer(state, instance, pos, this.player, instance.getFluidState(pos));
        }
        return original.call(instance, pos, b);
    }
}