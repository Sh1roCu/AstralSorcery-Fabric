package cn.sh1rocu.astralsorcery.mixin.common;

import cn.sh1rocu.astralsorcery.util.EventHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MushroomBlock.class)
public class MushroomBlockMixin {
    @Inject(method = "growMushroom", at = @At(value = "INVOKE", target = "Ljava/util/Optional;isEmpty()Z"), cancellable = true)
    public void growMushroom(ServerLevel level, BlockPos pos, BlockState state, RandomSource random, CallbackInfoReturnable<Boolean> cir) {
        var event = EventHooks.fireBlockGrowFeature(level, random, pos);
        if (event.isCanceled()) {
            cir.setReturnValue(false);
        }
    }
}
