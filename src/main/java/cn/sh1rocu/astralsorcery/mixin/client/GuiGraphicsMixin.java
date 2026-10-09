package cn.sh1rocu.astralsorcery.mixin.client;

import cn.sh1rocu.astralsorcery.api.extension.client.IGuiGraphics;
import cn.sh1rocu.astralsorcery.util.neoforge.client.ClientHooks;
import com.llamalad7.mixinextras.sugar.Local;
import hellfirepvp.astralsorcery.client.util.tooltip.TooltipUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin implements IGuiGraphics {

    @Shadow
    public abstract int guiWidth();

    @Shadow
    public abstract int guiHeight();

    @Unique
    private ItemStack as$tooltipStack = ItemStack.EMPTY;

    @Override
    public ItemStack as$getTooltipItemStack() {
        return as$tooltipStack;
    }

    @Override
    public void as$setTooltipItemStack(ItemStack itemStack) {
        as$tooltipStack = itemStack;
    }

    @Inject(method = "renderTooltipInternal", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z", shift = At.Shift.AFTER))
    private void as$renderTooltipInternal(Font font, List<ClientTooltipComponent> components, int mouseX, int mouseY, ClientTooltipPositioner tooltipPositioner, CallbackInfo ci) {
        TooltipUtil.getInstance().tooltipContext(tooltipPositioner);
    }

    @ModifyArg(
            method = "renderTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;Ljava/util/Optional;II)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;renderTooltipInternal(Lnet/minecraft/client/gui/Font;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;)V"),
            index = 1)
    private List<ClientTooltipComponent> as$modifyComponents(List<ClientTooltipComponent> components,
                                                             @Local(argsOnly = true) Font font,
                                                             @Local(argsOnly = true) List<Component> tooltipLines,
                                                             @Local(argsOnly = true) Optional<TooltipComponent> visualTooltipComponent,
                                                             @Local(argsOnly = true, ordinal = 0) int mouseX) {
        return ClientHooks.gatherTooltipComponents(as$tooltipStack, tooltipLines, visualTooltipComponent, mouseX, guiWidth(), guiHeight(), font);
    }

    @ModifyArg(
            method = "renderComponentTooltip",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;II)V"),
            index = 1
    )
    private List<ClientTooltipComponent> as$modifyComponents(List<ClientTooltipComponent> components,
                                                             @Local(argsOnly = true) Font font,
                                                             @Local(argsOnly = true) List<Component> tooltipLines,
                                                             @Local(argsOnly = true, ordinal = 0) int mouseX) {
        return ClientHooks.gatherTooltipComponents(as$tooltipStack, tooltipLines, mouseX, guiWidth(), guiHeight(), font);
    }

    @Inject(method = "renderTooltip(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;II)V", at = @At("HEAD"))
    private void as$renderTooltipPre(Font font, ItemStack itemStack, int i, int j, CallbackInfo ci) {
        as$tooltipStack = itemStack;
    }

    @Inject(method = "renderTooltip(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;II)V", at = @At("RETURN"))
    private void as$renderTooltipPost(Font font, ItemStack itemStack, int i, int j, CallbackInfo ci) {
        as$tooltipStack = ItemStack.EMPTY;
    }
}