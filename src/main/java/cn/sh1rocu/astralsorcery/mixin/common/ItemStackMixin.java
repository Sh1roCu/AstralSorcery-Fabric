package cn.sh1rocu.astralsorcery.mixin.common;

import cn.sh1rocu.astralsorcery.AstralSorceryFabric;
import cn.sh1rocu.astralsorcery.api.extension.IMaxDamageItem;
import cn.sh1rocu.astralsorcery.api.extension.IMaxStackSizeItem;
import cn.sh1rocu.astralsorcery.util.EventHooks;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import hellfirepvp.astralsorcery.common.event.handler.TooltipEventHandler;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract Item getItem();

    @Inject(method = "getTooltipLines", at = @At(value = "RETURN", ordinal = 1))
    private void getTooltip(Item.TooltipContext tooltipContext, @Nullable Player entity, TooltipFlag tooltipType, CallbackInfoReturnable<List<Component>> info) {
        TooltipEventHandler.onTooltip((ItemStack) (Object) this, tooltipContext, entity, tooltipType, info.getReturnValue());
    }

    @ModifyReturnValue(method = "getEnchantments", at = @At("RETURN"))
    private ItemEnchantments as$modifyEnchantments(ItemEnchantments original) {
        return EventHooks.getAllEnchantmentLevels(original, (ItemStack) (Object) this, AstralSorceryFabric.LOOKUP.lookupOrThrow(Registries.ENCHANTMENT));
    }

    @Inject(method = "getMaxDamage", at = @At("HEAD"), cancellable = true)
    private void as$itemMaxDamage(CallbackInfoReturnable<Integer> cir) {
        if (this.getItem() instanceof IMaxDamageItem item) {
            cir.setReturnValue(item.getMaxDamage((ItemStack) (Object) this));
        }
    }

    @ModifyReturnValue(method = "getMaxStackSize", at = @At("RETURN"))
    private int as$getMaxStackSize(int original) {
        if (this.getItem() instanceof IMaxStackSizeItem item) {
            return item.getMaxStackSize((ItemStack) (Object) this);
        }

        return original;
    }
}
