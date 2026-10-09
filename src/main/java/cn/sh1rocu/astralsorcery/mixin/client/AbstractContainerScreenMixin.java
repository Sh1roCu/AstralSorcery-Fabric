package cn.sh1rocu.astralsorcery.mixin.client;

import cn.sh1rocu.astralsorcery.api.extension.client.IGuiGraphics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin {

    @Inject(method = "renderTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;Ljava/util/Optional;II)V", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)
    private void as$renderTooltipPre(GuiGraphics guiGraphics, int i, int j, CallbackInfo ci, ItemStack itemStack) {
        ((IGuiGraphics) guiGraphics).as$setTooltipItemStack(itemStack);
    }

    @Inject(method = "renderTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;Ljava/util/Optional;II)V", shift = At.Shift.AFTER))
    private void as$renderTooltipPost(GuiGraphics guiGraphics, int i, int j, CallbackInfo ci) {
        ((IGuiGraphics) guiGraphics).as$setTooltipItemStack(ItemStack.EMPTY);
    }
}