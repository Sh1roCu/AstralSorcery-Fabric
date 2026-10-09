/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.util;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: BlockBreakUtil
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class BlockBreakUtil {

    public static Result breakBlockWithoutPlayer(ServerLevel sLevel, BlockPos pos, ItemStack heldItem, boolean bypassHarvestCheck) {
        ServerPlayer fakePlayer = MiscUtil.getAstralFakePlayer(sLevel);
        fakePlayer.setItemInHand(InteractionHand.MAIN_HAND, heldItem);
        BlockState state = sLevel.getBlockState(pos);

        try {
            boolean canceled = !PlayerBlockBreakEvents.BEFORE.invoker().beforeBlockBreak(sLevel, fakePlayer, pos, state, sLevel.getBlockEntity(pos));
            if (canceled) {
                return Result.failure();
            }
        } catch (Exception exc) {
            return Result.failure();
        }

        boolean harvestable;
        if (bypassHarvestCheck) {
            harvestable = true;
        } else {
            try {
                // harvestable = state.canHarvestBlock(sLevel, pos, fakePlayer);
                harvestable = fakePlayer.hasCorrectToolForDrops(state);
            } catch (Exception exc) {
                return Result.failure();
            }
        }

        return BlockUtil.captureLevelChanges(sLevel, () -> {
            try {
                heldItem.copy().mineBlock(sLevel, state, pos, fakePlayer);

                // BlockDropsEvent event = captureBreak(() -> {
                boolean removed = onDestroyedByPlayer(state, sLevel, pos, fakePlayer, harvestable, Fluids.EMPTY.defaultFluidState());
                if (removed) {
                    state.getBlock().destroy(sLevel, pos, state);
                    if (harvestable) {
                        state.getBlock().playerDestroy(sLevel, fakePlayer, pos, state, sLevel.getBlockEntity(pos), heldItem);
                    }
                }
                // });

                PlayerBlockBreakEvents.AFTER.invoker().afterBlockBreak(sLevel, fakePlayer, pos, state, sLevel.getBlockEntity(pos));
                //Break drops event didn't fire, so nothing got broken/harvested
//                if (event == null) {
//                    return BlockUtil.ChangeResult.revert(Result.failure());
//                }

                // return BlockUtil.ChangeResult.apply(Result.success(event.getDrops(), event.getDroppedExperience()));
                return BlockUtil.ChangeResult.apply(Result.success(List.of(), 0));
            } catch (Exception exc) {
                return BlockUtil.ChangeResult.revert(Result.failure());
            }
        }).value();
    }

    private static boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if (level.isClientSide()) {
            // On the client, vanilla calls Level#setBlock, per MultiPlayerGameMode#destroyBlock
            return level.setBlock(pos, fluid.createLegacyBlock(), 11);
        } else {
            // On the server, vanilla calls Level#removeBlock, per ServerPlayerGameMode#destroyBlock
            return level.removeBlock(pos, false);
        }
    }

//    @Nullable
//    private static BlockDropsEvent captureBreak(Runnable run) {
//        ClientObject<BlockDropsEvent> eventObj = new ClientObject<>(null);
//        Consumer<BlockDropsEvent> eventHandler = event -> {
//            if (!event.isCanceled()) {
//                eventObj.set(event);
//            }
//            event.setCanceled(true);
//        };
//
//        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, BlockDropsEvent.class, eventHandler);
//        try {
//            run.run();
//        } finally {
//            NeoForge.EVENT_BUS.unregister(eventHandler);
//        }
//        return eventObj.get();
//    }

    public static class Result {

        private final boolean success;
        private final List<ItemEntity> capturedDrops = new ArrayList<>();
        private int droppedExperience = 0;

        private Result(boolean success) {
            this.success = success;
        }

        private static Result failure() {
            return new Result(false);
        }

        private static Result success(List<ItemEntity> drops, int exp) {
            Result result = new Result(true);
            result.capturedDrops.addAll(drops);
            result.droppedExperience = exp;
            return result;
        }

        public boolean isSuccess() {
            return this.success;
        }

        public List<ItemEntity> getCapturedDrops() {
            return this.capturedDrops;
        }

        public int getDroppedExperience() {
            return this.droppedExperience;
        }

        public void dropResultsInWorld(ServerLevel sLevel, BlockState brokenState, BlockPos pos, ItemStack tool) {
            for (ItemEntity entity : this.getCapturedDrops()) {
                //Emulating Block#popResource position calc
                double height = (double) EntityType.ITEM.getHeight() / 2.0;
                double x = pos.getX() + 0.5 + Mth.nextDouble(sLevel.random, -0.25, 0.25);
                double y = pos.getY() + 0.5 + Mth.nextDouble(sLevel.random, -0.25, 0.25) - height;
                double z = pos.getZ() + 0.5 + Mth.nextDouble(sLevel.random, -0.25, 0.25);
                entity.setPos(x, y, z);
                sLevel.addFreshEntity(entity);
            }
            brokenState.spawnAfterBreak(sLevel, pos, tool, false);
            if (this.getDroppedExperience() > 0) {
                brokenState.getBlock().popExperience(sLevel, pos, this.getDroppedExperience());
            }
        }
    }
}
