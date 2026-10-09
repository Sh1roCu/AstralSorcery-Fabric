/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.util.inventory;

import cn.sh1rocu.astralsorcery.util.transfer.FabricItemStackHandler;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: InventoryView
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class InventoryView extends FabricItemStackHandler {

    private final int size;
    private final InventoryStackList contents;
    private final Set<Direction> applicableSides;
    private final Consumer<Integer> changeListener;
    private final BiFunction<Integer, ItemStack, Integer> stackSizeLimiter;

    protected InventoryView(int size,
                            InventoryStackList contents,
                            Set<Direction> applicableSides,
                            Consumer<Integer> changeListener,
                            BiFunction<Integer, ItemStack, Integer> stackSizeLimiter) {
        super(size, contents);
        this.size = size;
        this.contents = contents;
        this.applicableSides = applicableSides;
        this.changeListener = changeListener;
        this.stackSizeLimiter = stackSizeLimiter;
    }

    public int getSize() {
        return this.size;
    }

    @Nonnull
    public Iterator<ItemStack> iteratorStacks() {
        return this.contents.iterator();
    }

    protected void validateViewSize(int slot) {
        if (slot >= this.getSize()) {
            throw new IndexOutOfBoundsException("Slot " + slot + " not in valid range - [0, " + this.getSize() + ")");
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return this.stackSizeLimiter.apply(slot, this.getStackInSlot(slot));
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return true;
    }

    public void clearInventory() {
        super.setSize(getSize());
        for (int i = 0; i < getSlotCount(); i++) {
            this.setStackInSlot(i, ItemStack.EMPTY);
            this.onContentsChanged(i);
        }
    }

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        this.contents.setStackInSlot(slot, super.getStackInSlot(slot));
        this.changeListener.accept(slot);
    }

    protected boolean hasHandlerForSide(@Nullable Direction facing) {
        return facing == null || this.applicableSides.contains(facing);
    }

    @Nullable
    public InventoryView getInventoryAccess(@Nullable Direction direction) {
        if (!this.hasHandlerForSide(direction)) {
            return null;
        }
        return this;
    }
}
