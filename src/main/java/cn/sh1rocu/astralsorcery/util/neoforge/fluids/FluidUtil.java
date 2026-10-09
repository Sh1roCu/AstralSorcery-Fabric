package cn.sh1rocu.astralsorcery.util.neoforge.fluids;

import cn.sh1rocu.astralsorcery.util.fluid.FluidStack;
import cn.sh1rocu.astralsorcery.util.transfer.ItemStackStorage;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FluidUtil {
    private FluidUtil() {
    }

    public static Optional<Storage<FluidVariant>> getImmutableFluidHandler(ItemStack itemStack) {
        return getFluidHandler(itemStack, ContainerItemContext.withConstant(itemStack));
    }

    public static Optional<Storage<FluidVariant>> getFluidHandler(ItemStack itemStack, ContainerItemContext context) {
        return Optional.ofNullable(FluidStorage.ITEM.find(itemStack, context));
    }

    public static Optional<FluidStack> getFluidContained(ItemStack container) {
        if (!container.isEmpty()) {
            container = container.copyWithCount(1);
            Optional<FluidStack> fluidContained = getImmutableFluidHandler(container)
                    .map(handler -> {
                        var extracted = StorageUtil.findExtractableContent(handler, null);
                        if (extracted != null && !extracted.resource().isBlank() && extracted.amount() > 0) {
                            return new FluidStack(extracted.resource(), extracted.amount());
                        }
                        return null;
                    });
            if (fluidContained.isPresent() && !fluidContained.get().isEmpty()) {
                return fluidContained;
            }
        }
        return Optional.empty();
    }

    public static FluidActionResult tryFillContainer(ItemStack container, Storage<FluidVariant> fluidSource, long maxAmount, @Nullable Player player, boolean doFill) {
        ItemStack containerCopy = container.copyWithCount(1); // do not modify the input
        var context = ContainerItemContext.ofSingleSlot(new ItemStackStorage(containerCopy));
        return getFluidHandler(containerCopy, context)
                .map(containerFluidHandler -> {
                    FluidStack simulatedTransfer = tryFluidTransfer(containerFluidHandler, fluidSource, maxAmount, false);
                    if (!simulatedTransfer.isEmpty()) {
                        if (doFill) {
                            tryFluidTransfer(containerFluidHandler, fluidSource, maxAmount, true);
                            if (player != null) {
                                SoundEvent soundevent = FluidVariantAttributes.getFillSound(simulatedTransfer.getFluidVariant());

                                if (soundevent != null) {
                                    player.level().playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
                                }
                            }
                        } else {
                            // We are acting on a COPY of the stack, so performing changes on the source is acceptable even if we are simulating.
                            // We need to perform the change otherwise the call to getContainer() will be incorrect.
                            try (Transaction tx = Transaction.openOuter()) {
                                containerFluidHandler.insert(simulatedTransfer.getFluidVariant(), simulatedTransfer.getAmount(), tx);
                                tx.commit();
                            }
                        }

                        ItemStack resultContainer = context.getItemVariant().toStack();
                        return new FluidActionResult(resultContainer);
                    }
                    return FluidActionResult.FAILURE;
                })
                .orElse(FluidActionResult.FAILURE);
    }

    public static FluidActionResult tryEmptyContainer(ItemStack container, Storage<FluidVariant> fluidDestination, long maxAmount, @Nullable Player player, boolean doDrain) {
        ItemStack containerCopy = container.copyWithCount(1); // do not modify the input
        var context = ContainerItemContext.ofSingleSlot(new ItemStackStorage(containerCopy));
        return getFluidHandler(containerCopy, context)
                .map(containerFluidHandler -> {
                    FluidStack transfer = tryFluidTransfer(fluidDestination, containerFluidHandler, maxAmount, doDrain);
                    if (transfer.isEmpty())
                        return FluidActionResult.FAILURE;
                    if (!doDrain) {
                        // We are acting on a COPY of the stack, so performing changes on the source is acceptable even if we are simulating.
                        // We need to perform the change otherwise the call to getContainer() will be incorrect.
                        try (Transaction tx = Transaction.openOuter()) {
                            containerFluidHandler.extract(transfer.getFluidVariant(), transfer.getAmount(), tx);
                            tx.commit();
                        }
                    }

                    if (doDrain && player != null) {
                        SoundEvent soundevent = FluidVariantAttributes.getEmptySound(transfer.getFluidVariant());

                        if (soundevent != null) {
                            player.level().playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
                        }
                    }

                    ItemStack resultContainer = context.getItemVariant().toStack();
                    return new FluidActionResult(resultContainer);
                })
                .orElse(FluidActionResult.FAILURE);
    }

    public static FluidStack tryFluidTransfer(Storage<FluidVariant> fluidDestination, Storage<FluidVariant> fluidSource, long maxAmount, boolean doTransfer) {
        FluidStack drainable = FluidStack.EMPTY;
        try (Transaction tx = Transaction.openOuter()) {
            var resourceAmount = StorageUtil.extractAny(fluidSource, maxAmount, tx);
            if (resourceAmount != null) {
                drainable = new FluidStack(resourceAmount.resource(), resourceAmount.amount());
            }
            tx.abort();
        }
        if (!drainable.isEmpty()) {
            return tryFluidTransfer_Internal(fluidDestination, fluidSource, drainable, doTransfer);
        }
        return FluidStack.EMPTY;
    }

    public static FluidStack tryFluidTransfer(Storage<FluidVariant> fluidDestination, Storage<FluidVariant> fluidSource, FluidStack resource, boolean doTransfer) {
        long drainable = StorageUtil.simulateExtract(fluidSource, resource.getFluidVariant(), resource.getAmount(), null);
        if (drainable > 0) {
            return tryFluidTransfer_Internal(fluidDestination, fluidSource, resource.copyWithAmount(drainable), doTransfer);
        }
        return FluidStack.EMPTY;
    }

    private static FluidStack tryFluidTransfer_Internal(Storage<FluidVariant> fluidDestination, Storage<FluidVariant> fluidSource, FluidStack drainable, boolean doTransfer) {
        long fillableAmount = StorageUtil.simulateInsert(fluidDestination, drainable.getFluidVariant(), drainable.getAmount(), null);
        if (fillableAmount > 0) {
            drainable.setAmount(fillableAmount);
            if (doTransfer) {
                FluidStack drained;
                try (Transaction tx = Transaction.openOuter()) {
                    long extracted = fluidSource.extract(drainable.getFluidVariant(), drainable.getAmount(), tx);
                    drained = drainable.copyWithAmount(extracted);
                    tx.commit();
                }
                if (!drained.isEmpty()) {
                    try (Transaction tx = Transaction.openOuter()) {
                        drained.setAmount(fluidDestination.insert(drained.getFluidVariant(), drained.getAmount(), tx));
                        tx.commit();
                        return drained;
                    }
                }
            } else {
                return drainable;
            }
        }
        return FluidStack.EMPTY;
    }

    public static ItemStack getFilledBucket(FluidStack fluidStack) {
        if (fluidStack.getFluidVariant().getComponents().isEmpty()) {
            if (fluidStack.is(Fluids.WATER)) {
                return new ItemStack(Items.WATER_BUCKET);
            } else if (fluidStack.is(Fluids.LAVA)) {
                return new ItemStack(Items.LAVA_BUCKET);
            }
        }
        return fluidStack.getFluid().getBucket().getDefaultInstance();
    }
}
