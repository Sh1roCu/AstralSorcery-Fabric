package cn.sh1rocu.astralsorcery.mixin.common;

import cn.sh1rocu.astralsorcery.util.EventHooks;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @ModifyReturnValue(method = "getItemEnchantmentLevel", at = @At("RETURN"))
    private static int as$getItemEnchantmentLevel(int original, Holder<Enchantment> enchantment, ItemStack stack) {
        return EventHooks.getEnchantmentLevelSpecific(original, stack, enchantment);
    }
}
