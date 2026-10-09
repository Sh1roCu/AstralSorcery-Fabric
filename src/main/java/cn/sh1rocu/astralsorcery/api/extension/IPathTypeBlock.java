package cn.sh1rocu.astralsorcery.api.extension;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;

import javax.annotation.Nullable;

public interface IPathTypeBlock {

    PathType getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob);
}
