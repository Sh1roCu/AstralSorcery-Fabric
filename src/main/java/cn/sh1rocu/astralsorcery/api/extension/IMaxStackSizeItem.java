package cn.sh1rocu.astralsorcery.api.extension;

import net.minecraft.world.item.ItemStack;

public interface IMaxStackSizeItem {

    int getMaxStackSize(ItemStack stack);
}
