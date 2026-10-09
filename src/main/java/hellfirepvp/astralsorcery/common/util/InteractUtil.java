/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.util;

import cn.sh1rocu.astralsorcery.util.neoforge.fluids.FluidActionResult;
import cn.sh1rocu.astralsorcery.util.neoforge.fluids.FluidUtil;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: InteractUtil
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class InteractUtil {

    @Nullable
    public static ItemInteractionResult tryTransferFluidIntoBlock(ItemStack heldStack, Player player, Level level, BlockPos pos, Consumer<ItemStack> stackUpdateFn) {
        var target = MiscUtil.getTileAt(level, pos, BlockEntity.class, true)
                .map(blockEntity -> FluidStorage.SIDED.find(level, pos, null))
                .orElse(null);
        if (heldStack.isEmpty() || target == null) {
            return null;
        }

        FluidActionResult far = FluidUtil.tryEmptyContainer(heldStack, target, FluidConstants.BUCKET, player, true);
        if (!far.isSuccess()) return null;
        if (!player.isCreative()) stackUpdateFn.accept(far.getResult());
        return ItemInteractionResult.SUCCESS;
    }

    @Nullable
    public static ItemInteractionResult tryTransferFluidFromBlock(ItemStack heldStack, Player player, Level level, BlockPos pos, Consumer<ItemStack> stackUpdateFn) {
        var target = MiscUtil.getTileAt(level, pos, BlockEntity.class, true)
                .map(blockEntity -> FluidStorage.SIDED.find(level, pos, null))
                .orElse(null);
        if (heldStack.isEmpty() || target == null) {
            return null;
        }

        FluidActionResult far = FluidUtil.tryFillContainer(heldStack, target, FluidConstants.BUCKET, player, true);
        if (!far.isSuccess()) return null;
        if (!player.isCreative()) stackUpdateFn.accept(far.getResult());
        return ItemInteractionResult.SUCCESS;
    }

    @Nullable
    public static ItemInteractionResult tryPlaceItemIntoBlock(ItemStack heldStack, Player player, Level level, BlockPos pos) {
        var target = MiscUtil.getTileAt(level, pos, BlockEntity.class, true)
                .map(blockEntity -> ItemStorage.SIDED.find(level, pos, null))
                .orElse(null);
        if (target == null) {
            return null;
        }

        boolean playPickupSound = false;
        ItemInteractionResult result = null;
        //Extract 1 item that can be found
        for (var view : target.nonEmptyViews()) {
            ItemStack invSlot = ItemStack.EMPTY;
            try (Transaction tx = Transaction.openOuter()) {
                long extracted = target.extract(view.getResource(), 64, tx);
                if (extracted > 0) invSlot = view.getResource().toStack((int) extracted);
                tx.commit();
            }
            if (!invSlot.isEmpty()) {
                if (!player.getInventory().add(invSlot)) {
                    ItemUtil.dropItem(level, player.position(), invSlot);
                }
                playPickupSound = true;
                result = ItemInteractionResult.SUCCESS;
                break;
            }
        }

        if (!heldStack.isEmpty()) {
            long inserted;
            try (Transaction tx = Transaction.openOuter()) {
                inserted = target.insert(ItemVariant.of(heldStack), heldStack.getCount(), tx);
                tx.commit();
            }
            if (inserted > 0) {
                playPickupSound = true;
                if (!player.isCreative()) {
                    heldStack.shrink((int) inserted);
                }
                result = ItemInteractionResult.SUCCESS;
            }
        }


        if (playPickupSound) {
            level.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS,
                    0.2F, ((level.random.nextFloat() - level.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
        }
        return result;
    }

    public static void handleContainerReplacement(Player player, ItemStack newStack, InteractionHand hand) {
        if (!player.isCreative()) {
            ItemStack contained = player.getItemInHand(hand);
            contained.shrink(1);
            giveItemToPlayer(player, newStack, hand);
        }
    }

    public static void giveItemToPlayer(Player player, ItemStack stack) {
        giveItemToPlayer(player, stack, -1);
    }

    public static void giveItemToPlayer(Player player, ItemStack stack, InteractionHand hand) {
        int slot = hand == InteractionHand.MAIN_HAND ? player.getInventory().selected : Inventory.SLOT_OFFHAND;
        giveItemToPlayer(player, stack, slot);
    }

    public static void giveItemToPlayer(Player player, ItemStack stack, int slot) {
        try (Transaction tx = Transaction.openOuter()) {
            PlayerInventoryStorage.of(player).offerOrDrop(ItemVariant.of(stack), stack.getCount(), tx);
            tx.commit();
        }
    }
}
