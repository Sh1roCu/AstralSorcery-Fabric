package cn.sh1rocu.astralsorcery.mixin.client;

import cn.sh1rocu.astralsorcery.api.extension.client.IGuiGraphics;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecipeBookComponent.class)
public abstract class RecipeComponentMixin {

    @Inject(method = "renderGhostRecipeTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderComponentTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;II)V"))
    private void as$renderGhostRecipeTooltip(GuiGraphics guiGraphics, int i, int j, int k, int l, CallbackInfo ci, @Local ItemStack itemStack) {
        ((IGuiGraphics) guiGraphics).as$setTooltipItemStack(itemStack);
    }

    @Inject(method = "renderGhostRecipeTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderComponentTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;II)V", shift = At.Shift.AFTER))
    private void as$renderGhostRecipeTooltip(GuiGraphics guiGraphics, int i, int j, int k, int l, CallbackInfo ci) {
        ((IGuiGraphics) guiGraphics).as$setTooltipItemStack(ItemStack.EMPTY);
    }
}