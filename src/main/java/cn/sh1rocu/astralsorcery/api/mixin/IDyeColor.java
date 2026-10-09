package cn.sh1rocu.astralsorcery.api.mixin;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public interface IDyeColor {

    TagKey<Item> as$getTag();

    @Nullable
    static DyeColor getColor(ItemStack stack) {
        if (stack.getItem() instanceof DyeItem)
            return ((DyeItem) stack.getItem()).getDyeColor();

        for (int x = 0; x < DyeColor.BLACK.getId(); x++) {
            DyeColor color = DyeColor.byId(x);
            if (stack.is(((IDyeColor) (Object) color).as$getTag()))
                return color;
        }

        return null;
    }
}
