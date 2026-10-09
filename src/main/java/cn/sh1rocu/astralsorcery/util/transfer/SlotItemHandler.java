package cn.sh1rocu.astralsorcery.util.transfer;

import net.minecraft.world.inventory.Slot;

/**
 * From Create-Fabric(<a href="https://github.com/crabdancing/Create-fabric/blob/mc1.21.1/fabric/dev/src/main/java/com/simibubi/create/infrastructure/fabric/transfer/item/SlotItemHandler.java">...</a>)
 */
public class SlotItemHandler extends Slot {
    public SlotItemHandler(SlottedStackStorage storage, int slot, int x, int y) {
        super(new StorageWrapperContainer(storage), slot, x, y);
    }
}