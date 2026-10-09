package cn.sh1rocu.astralsorcery.util;

import cn.sh1rocu.astralsorcery.api.extension.client.IGuiGraphics;
import cn.sh1rocu.astralsorcery.mixin.accessor.client.GuiGraphicsAccessor;
import cn.sh1rocu.astralsorcery.util.neoforge.client.ClientHooks;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class GuiGraphicsUtil {
    private GuiGraphicsUtil() {
    }

    public static void renderComponentTooltip(GuiGraphics guiGraphics, Font p_font, List<? extends FormattedText> tooltipLines, int p_mouseX, int p_mouseY, ItemStack stack) {
        ((IGuiGraphics) guiGraphics).as$setTooltipItemStack(stack);
        List<ClientTooltipComponent> components = ClientHooks.gatherTooltipComponents(stack, tooltipLines, p_mouseX, guiGraphics.guiWidth(), guiGraphics.guiHeight(), p_font);
        ((GuiGraphicsAccessor) guiGraphics).as$renderTooltipInternal(p_font, components, p_mouseX, p_mouseY, DefaultTooltipPositioner.INSTANCE);
        ((IGuiGraphics) guiGraphics).as$setTooltipItemStack(ItemStack.EMPTY);
    }

    public static void renderComponentTooltipFromElements(GuiGraphics guiGraphics, Font font, List<com.mojang.datafixers.util.Either<FormattedText, TooltipComponent>> elements, int mouseX, int mouseY, ItemStack stack) {
        ((IGuiGraphics) guiGraphics).as$setTooltipItemStack(stack);
        List<ClientTooltipComponent> components = ClientHooks.gatherTooltipComponentsFromElements(stack, elements, mouseX, guiGraphics.guiWidth(), guiGraphics.guiHeight(), font);
        ((GuiGraphicsAccessor) guiGraphics).as$renderTooltipInternal(font, components, mouseX, mouseY, DefaultTooltipPositioner.INSTANCE);
        ((IGuiGraphics) guiGraphics).as$setTooltipItemStack(ItemStack.EMPTY);
    }
}
