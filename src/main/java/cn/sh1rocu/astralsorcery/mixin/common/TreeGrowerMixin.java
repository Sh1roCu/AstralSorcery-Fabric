package cn.sh1rocu.astralsorcery.mixin.common;

import cn.sh1rocu.astralsorcery.util.EventHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TreeGrower.class)
public class TreeGrowerMixin {
    @Inject(method = "growTree", at = @At(
            value = "INVOKE",
            target = "Ljava/util/Optional;orElse(Ljava/lang/Object;)Ljava/lang/Object;",
            shift = At.Shift.AFTER), cancellable = true)
    private void as$growTree(ServerLevel level, ChunkGenerator chunkGenerator, BlockPos pos,
                             BlockState state, RandomSource random, CallbackInfoReturnable<Boolean> cir) {
        var event = EventHooks.fireBlockGrowFeature(level, random, pos);
        if (event.isCanceled()) {
            cir.setReturnValue(false);
        }
    }
}
