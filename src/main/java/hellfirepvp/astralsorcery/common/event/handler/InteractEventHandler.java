/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.event.handler;

import cn.sh1rocu.astralsorcery.api.event.BaseEvent;
import cn.sh1rocu.astralsorcery.util.neoforge.network.PacketDistributor;
import hellfirepvp.astralsorcery.client.screen.tome.TomeResearchScreen;
import hellfirepvp.astralsorcery.common.item.base.InterceptInteractItem;
import hellfirepvp.astralsorcery.common.item.wand.ArchitectWandItem;
import hellfirepvp.astralsorcery.common.item.wand.ExchangeWandItem;
import hellfirepvp.astralsorcery.common.item.wand.WandBlockStorageHelper;
import hellfirepvp.astralsorcery.common.lib.ItemsAS;
import hellfirepvp.astralsorcery.common.network.play.PktOpenClientScreen;
import hellfirepvp.astralsorcery.common.util.MiscUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: InteractEventHandler
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class InteractEventHandler {

    public static void attachListeners() {
        UseBlockCallback.EVENT.register(InteractEventHandler::onBlockInteract);
        UseEntityCallback.EVENT.register(InteractEventHandler::onEntityInteract);
        UseBlockCallback.EVENT.register(BaseEvent.LOW, InteractEventHandler::onOpenLectern);
        AttackBlockCallback.EVENT.register(InteractEventHandler::onLeftClickBlock);
        // impl via mixin
        // InteractEventHandler::onLeftClickEmpty;
    }

    private static InteractionResult onBlockInteract(Player player, Level world, InteractionHand hand, BlockHitResult hitResult) {
        InteractionResult[] result = new InteractionResult[]{InteractionResult.PASS};
        ItemStack held = player.getItemInHand(hand);
        if (held.getItem() instanceof InterceptInteractItem.Block blockInteractItem) {
            if (blockInteractItem.shouldInterceptBlockInteract(FabricLoader.getInstance().getEnvironmentType(), player, hand, hitResult.getBlockPos(),
                    hitResult, hitResult.getDirection()) &&
                    blockInteractItem.doBlockInteract(FabricLoader.getInstance().getEnvironmentType(), player, hand, hitResult.getBlockPos(), hitResult,
                            hitResult.getDirection())) {
                result[0] = InteractionResult.SUCCESS;
//                event.setCanceled(true);
//                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }
        MiscUtil.getTileAt(world, hitResult.getBlockPos(), LecternBlockEntity.class, false).ifPresent(tile -> {
            if (tile.getBook().is(ItemsAS.TOME)) {
                result[0] = InteractionResult.SUCCESS;
//                event.setCanceled(true);
//                event.setCancellationResult(InteractionResult.SUCCESS);

                if (player instanceof ServerPlayer sPlayer) {
                    PacketDistributor.sendToPlayer(sPlayer, PktOpenClientScreen.openScreen(PktOpenClientScreen.ScreenType.TOME));
                }
            }
        });

        return result[0];
    }

    private static InteractionResult onEntityInteract(Player player, Level world, InteractionHand hand, Entity entity, @Nullable EntityHitResult hitResult) {
        InteractionResult result = InteractionResult.PASS;
        ItemStack held = player.getItemInHand(hand);
        if (held.getItem() instanceof InterceptInteractItem.Entity<?> entityInteractItem && entityInteractItem.getEntityFilterClass().isInstance(entity)) {
            if (entityInteract(entityInteractItem, MiscUtil.cast(entity), FabricLoader.getInstance().getEnvironmentType(), player, hand)) {
                result = InteractionResult.SUCCESS;
//                event.setCanceled(true);
//                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }

        return result;
    }

    private static <T extends Entity> boolean entityInteract(InterceptInteractItem.Entity<T> interactItem, T entity, EnvType side, Player interacter, InteractionHand hand) {
        return interactItem.shouldInterceptEntityInteract(side, interacter, hand, entity) &&
                interactItem.doEntityInteract(side, interacter, hand, entity);
    }

    private static InteractionResult onLeftClickBlock(Player player, Level world, InteractionHand hand, BlockPos pos, Direction direction) {
        InteractionResult result = InteractionResult.PASS;
        if (!player.isShiftKeyDown()) return result;
        tryClearWandStorage(player, player.getItemInHand(hand));

        return result;
    }

    public static void onLeftClickEmpty(@Nullable Player player) {
        if (player == null || !player.isShiftKeyDown()) return;
        tryClearWandStorage(player, player.getItemInHand(InteractionHand.MAIN_HAND));
    }

    private static void tryClearWandStorage(Player player, ItemStack held) {
        if (held.isEmpty()) return;
        if (!(held.getItem() instanceof ArchitectWandItem) && !(held.getItem() instanceof ExchangeWandItem)) return;
        if (!WandBlockStorageHelper.getStorage(held).hasStoredStates()) return;

        WandBlockStorageHelper.clearStorage(held);
        player.displayClientMessage(Component.translatable("message.astralsorcery.wand.cleared"), true);
    }

    private static InteractionResult onOpenLectern(Player player, Level world, InteractionHand hand, BlockHitResult hitResult) {
        InteractionResult[] result = new InteractionResult[]{InteractionResult.PASS};
        MiscUtil.getTileAt(world, hitResult.getBlockPos(), LecternBlockEntity.class, false).ifPresent(lectern -> {
            ItemStack contained = lectern.getBook();
            if (contained.is(ItemsAS.TOME)) {
                result[0] = InteractionResult.SUCCESS;
//                event.setCanceled(true);
//                event.setCancellationResult(InteractionResult.SUCCESS);
                if (world.isClientSide()) {
                    openTomeScreen();
                }
            }
        });

        return result[0];
    }

    @Environment(EnvType.CLIENT)
    private static void openTomeScreen() {
        Minecraft.getInstance().setScreen(TomeResearchScreen.getOpenTome());
    }
}
