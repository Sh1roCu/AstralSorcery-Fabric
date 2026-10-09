package cn.sh1rocu.astralsorcery.mixin.common;

import cn.sh1rocu.astralsorcery.api.extension.INoRepairItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RepairItemRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RepairItemRecipe.class)
public class RepairItemRecipeMixin {
    @WrapOperation(
            method = "getItemsToCombine",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/crafting/RepairItemRecipe;canCombine(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z"
            )
    )
    private boolean as$noRepair(ItemStack stack1, ItemStack stack2, Operation<Boolean> original) {
        return !(stack1.getItem() instanceof INoRepairItem) && !(stack2.getItem() instanceof INoRepairItem)
                && original.call(stack1, stack2);
    }
}