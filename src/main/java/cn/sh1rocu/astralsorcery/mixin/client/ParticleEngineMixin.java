package cn.sh1rocu.astralsorcery.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import hellfirepvp.astralsorcery.client.lib.ClientExtensionsAS;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ParticleEngine.class)
public class ParticleEngineMixin {
    @Shadow
    protected ClientLevel level;

    @ModifyExpressionValue(method = "destroy", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;shouldSpawnTerrainParticles()Z"))
    private boolean as$addDestroyEffects(boolean original, BlockPos blockPos, BlockState blockState) {
        var ex = ClientExtensionsAS.of(blockState.getBlock());
        if (ex != null) {
            if (!ex.addDestroyEffects(blockState, level, blockPos, (ParticleEngine) (Object) this)) {
                return false;
            }
        }
        return original;
    }
}