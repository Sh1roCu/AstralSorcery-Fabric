package cn.sh1rocu.astralsorcery.api.extension;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface IRunningEffectsBlock {

    boolean addRunningEffects(BlockState state, Level level, BlockPos pos, Entity entity);
}