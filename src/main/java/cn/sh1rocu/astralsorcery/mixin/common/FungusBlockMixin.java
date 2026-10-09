package cn.sh1rocu.astralsorcery.mixin.common;

import cn.sh1rocu.astralsorcery.util.EventHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.FungusBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FungusBlock.class)
public class FungusBlockMixin {
    @Inject(method = "performBonemeal", at = @At("HEAD"), cancellable = true)
    private void as$fireBlockGrowFeature(ServerLevel level, RandomSource random, BlockPos pos, BlockState state, CallbackInfo ci) {
        var event = EventHooks.fireBlockGrowFeature(level, random, pos);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
}
