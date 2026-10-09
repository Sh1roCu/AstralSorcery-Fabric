package cn.sh1rocu.astralsorcery.util.transfer;

import com.google.common.collect.Iterators;
import hellfirepvp.astralsorcery.common.util.inventory.InventoryStackList;
import it.unimi.dsi.fastutil.objects.ObjectAVLTreeSet;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleStackStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.*;

/**
 * A shim for Forge's ItemStackHandler, over-engineered to be as fast as possible with fabric transfer.
 * <p>
 * From Create-Fabric(<a href="https://github.com/crabdancing/Create-fabric/blob/mc1.21.1/fabric/dev/src/main/java/com/simibubi/create/infrastructure/fabric/transfer/item/ItemStackHandler.java">...</a>)
 */
public class FabricItemStackHandler implements SlottedStackStorage {
    private final List<Slot> slots;
    private final SortedSet<Slot> nonEmptySlots;
    private final Map<Item, SortedSet<Slot>> lookup;

    public FabricItemStackHandler() {
        this(1);
    }

    public FabricItemStackHandler(int stacks) {
        this(Util.make(new ItemStack[stacks], array -> Arrays.fill(array, ItemStack.EMPTY)));
    }

    public FabricItemStackHandler(int size, InventoryStackList stacks) {
        this.slots = new ArrayList<>(size);
        this.nonEmptySlots = createSlotSet();
        this.lookup = new HashMap<>();
        for (int i = 0; i < size; i++) {
            // slot handles filling lookup
            this.slots.add(new Slot(i, stacks.getStackInSlot(i)));
        }
    }

    public FabricItemStackHandler(ItemStack[] stacks) {
        this.slots = new ArrayList<>(stacks.length);
        this.nonEmptySlots = createSlotSet();
        this.lookup = new HashMap<>();
        for (int i = 0; i < stacks.length; i++) {
            ItemStack stack = stacks[i];
            // slot handles filling lookup
            this.slots.add(new Slot(i, stack));
        }
    }

    // core functionality

    @Override
    public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        StoragePreconditions.notBlankNotNegative(resource, maxAmount);
        long inserted = 0;
        Iterator<Slot> itr = getInsertableSlotsFor(resource);
        while (itr.hasNext()) {
            Slot slot = itr.next();
            inserted += slot.insert(resource, maxAmount - inserted, transaction);
            if (inserted >= maxAmount)
                break;
        }
        return inserted;
    }

    @Override
    public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        StoragePreconditions.notBlankNotNegative(resource, maxAmount);
        Item item = resource.getItem();
        SortedSet<Slot> slots = getSlotsContaining(item);
        if (slots.isEmpty())
            return 0; // no slots hold this item
        long extracted = 0;
        for (Slot slot : slots) {
            extracted += slot.extract(resource, maxAmount - extracted, transaction);
            if (extracted >= maxAmount)
                break;
        }
        return extracted;
    }

    // iteration

    @Override
    public Iterable<StorageView<ItemVariant>> nonEmptyViews() {
        //noinspection unchecked,rawtypes
        return (Iterable) nonEmptySlots;
    }

    @Override
    public Iterator<StorageView<ItemVariant>> nonEmptyIterator() {
        //noinspection unchecked,rawtypes
        return (Iterator) nonEmptySlots.iterator();
    }

    @Override
    public Iterator<StorageView<ItemVariant>> iterator() {
        //noinspection unchecked,rawtypes
        return (Iterator) this.slots.iterator();
    }

    // slot support

    @Override
    public int getSlotCount() {
        return slots.size();
    }

    @Override
    public SingleSlotStorage<ItemVariant> getSlot(int slot) {
        return this.getInternalSlot(slot);
    }

    @Override
    public List<SingleSlotStorage<ItemVariant>> getSlots() {
        //noinspection unchecked,rawtypes
        return (List) slots;
    }

    // API, mostly from forge, with extras

    public ItemStack getStackInSlot(int slot) {
        return this.getInternalSlot(slot).getStack();
    }

    public void setStackInSlot(int slot, ItemStack stack) {
        this.getInternalSlot(slot).setNewStack(stack);
    }

    public ItemVariant getVariantInSlot(int slot) {
        return this.getSlot(slot).getResource();
    }

    @Override
    public int getSlotLimit(int slot) {
        return Item.ABSOLUTE_MAX_STACK_SIZE;
    }

    protected int getStackLimit(int slot, ItemVariant variant) {
        return Math.min(this.getSlotLimit(slot), variant.getComponentMap().getOrDefault(DataComponents.MAX_STACK_SIZE, 1));
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return true;
    }

    /**
     * Once a transaction is committed, this is invoked once for each modified slot.
     */
    protected void onContentsChanged(int slot) {
    }

    /**
     * Called after NBT is loaded and this handler has been updated.
     */
    protected void onLoad() {
    }

    /**
     * True if this handler only contains empty stacks.
     */
    public boolean empty() {
        return this.nonEmptySlots.isEmpty();
    }

    /**
     * Resize this handler, clearing all existing content.
     */
    public void setSize(int size) {
        this.slots.clear();
        this.nonEmptySlots.clear();
        this.lookup.clear();
        for (int i = 0; i < size; i++) {
            this.slots.add(new Slot(i, ItemStack.EMPTY));
        }
    }

    public boolean isEmpty() {
        return this.nonEmptySlots.isEmpty();
    }

    // serialization

    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("Size", this.slots.size());

        ListTag slots = new ListTag();
        for (Slot slot : this.slots) {
            if (!slot.getStack().isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", slot.index);
                slots.add(slot.save(provider, itemTag));
            }
        }

        nbt.put("Items", slots);
        return nbt;
    }

    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        setSize(nbt.contains("Size", Tag.TAG_INT) ? nbt.getInt("Size") : slots.size()); // also clears
        ListTag slots = nbt.getList("Items", Tag.TAG_COMPOUND);
        for (int i = 0; i < slots.size(); i++) {
            CompoundTag slotTag = slots.getCompound(i);
            int index = slotTag.getInt("Slot");

            if (index >= 0 && index < this.slots.size()) {
                this.slots.get(index).load(provider, slotTag);
            }
        }
        onLoad();
    }

    // internals

    private Slot getInternalSlot(int index) {
        return this.slots.get(index);
    }

    private SortedSet<Slot> getSlotsContaining(Item item) {
        return this.lookup.getOrDefault(item, Collections.emptySortedSet());
    }

    private void onStackChange(Slot slot, ItemStack oldStack, ItemStack newStack) {
        if (ItemStack.isSameItem(oldStack, newStack))
            return;
        SortedSet<Slot> oldItemSlots = this.getSlotsContaining(oldStack.getItem());
        if (!oldItemSlots.isEmpty()) {
            oldItemSlots.remove(slot);
        }
        this.getSetForItem(newStack.getItem()).add(slot);
        if (oldStack.isEmpty()) { // no longer empty
            this.nonEmptySlots.add(slot);
        } else if (newStack.isEmpty()) { // became empty
            this.nonEmptySlots.remove(slot);
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '[' + slots + ']';
    }

    private Iterator<Slot> getInsertableSlotsFor(ItemVariant variant) {
        SortedSet<Slot> slots = getSlotsContaining(variant.getItem());
        SortedSet<Slot> emptySlots = getSlotsContaining(Items.AIR);
        if (slots.isEmpty()) {
            return emptySlots.isEmpty() ? Collections.emptyIterator() : emptySlots.iterator();
        } else {
            return emptySlots.isEmpty() ? slots.iterator() : Iterators.concat(
                    slots.iterator(), emptySlots.iterator()
            );
        }
    }

    private SortedSet<Slot> getSetForItem(Item item) {
        return this.lookup.computeIfAbsent(item, $ -> createSlotSet());
    }

    private static SortedSet<Slot> createSlotSet() {
        return new ObjectAVLTreeSet<>(Comparator.comparingInt(slot -> slot.index));
    }

    private class Slot extends SingleStackStorage {
        private final int index;

        private ItemStack stack;
        private ItemStack lastStack; // last stack pre-transaction
        private ItemVariant variant;

        private Slot(int index, ItemStack initial) {
            this.index = index;
            this.lastStack = initial.copy();
            this.setStack(initial);

            FabricItemStackHandler.this.getSetForItem(this.stack.getItem()).add(this);
            if (!this.stack.isEmpty()) {
                FabricItemStackHandler.this.nonEmptySlots.add(this);
            }
        }

        @Override
        protected int getCapacity(ItemVariant itemVariant) {
            return FabricItemStackHandler.this.getStackLimit(index, itemVariant);
        }

        @Override
        protected ItemStack getStack() {
            return this.stack;
        }

        /**
         * Should only be used in transactions.
         */
        @Override
        protected void setStack(ItemStack stack) {
            this.stack = stack;
            this.variant = ItemVariant.of(stack);
        }

        private void setNewStack(ItemStack stack) {
            this.setStack(stack);
            this.onFinalCommit();
        }

        @Override
        public ItemVariant getResource() {
            return this.variant;
        }

        @Override
        protected void onFinalCommit() {
            this.onStackChange();
            this.notifyHandlerOfChange();
        }

        private void onStackChange() {
            FabricItemStackHandler.this.onStackChange(this, lastStack, stack);
            this.lastStack = stack.copy();
        }

        private void notifyHandlerOfChange() {
            FabricItemStackHandler.this.onContentsChanged(index);
        }

        /**
         * "Slot" is a reserved key.
         */
        private Tag save(HolderLookup.Provider provider, Tag tag) {
            return stack.save(provider, tag);
        }

        private void load(HolderLookup.Provider provider, CompoundTag tag) {
            ItemStack.parse(provider, tag).ifPresent(this::setStack);
            onStackChange();
            // intentionally do not notify handler, matches forge
        }
    }
}