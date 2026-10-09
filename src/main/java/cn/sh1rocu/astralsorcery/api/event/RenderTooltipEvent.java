/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package cn.sh1rocu.astralsorcery.api.event;

import com.mojang.datafixers.util.Either;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class RenderTooltipEvent extends BaseEvent {
    protected final ItemStack itemStack;
    protected final GuiGraphics graphics;
    protected int x;
    protected int y;
    protected Font font;
    protected final List<ClientTooltipComponent> components;

    public static final Event<GatherComponents.Callback> GATHER_COMPONENTS = EventFactory.createArrayBacked(GatherComponents.Callback.class, callbacks -> event -> {
        for (GatherComponents.Callback callback : callbacks) {
            callback.post(event);
        }
    });

    @ApiStatus.Internal
    protected RenderTooltipEvent(ItemStack itemStack, GuiGraphics graphics, int x, int y, Font font, List<ClientTooltipComponent> components) {
        this.itemStack = itemStack;
        this.graphics = graphics;
        this.components = Collections.unmodifiableList(components);
        this.x = x;
        this.y = y;
        this.font = font;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public GuiGraphics getGraphics() {
        return this.graphics;
    }

    public List<ClientTooltipComponent> getComponents() {
        return components;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Font getFont() {
        return font;
    }

    public static class GatherComponents extends BaseEvent implements ICancellableEvent {
        private final ItemStack itemStack;
        private final int screenWidth;
        private final int screenHeight;
        private final List<Either<FormattedText, TooltipComponent>> tooltipElements;
        private int maxWidth;

        @ApiStatus.Internal
        public GatherComponents(ItemStack itemStack, int screenWidth, int screenHeight, List<Either<FormattedText, TooltipComponent>> tooltipElements, int maxWidth) {
            this.itemStack = itemStack;
            this.screenWidth = screenWidth;
            this.screenHeight = screenHeight;
            this.tooltipElements = tooltipElements instanceof ArrayList<Either<FormattedText, TooltipComponent>> ? tooltipElements : new ArrayList<>(tooltipElements);
            this.maxWidth = maxWidth;
        }

        public ItemStack getItemStack() {
            return itemStack;
        }

        public int getScreenWidth() {
            return screenWidth;
        }

        public int getScreenHeight() {
            return screenHeight;
        }

        public List<Either<FormattedText, TooltipComponent>> getTooltipElements() {
            return tooltipElements;
        }

        public int getMaxWidth() {
            return maxWidth;
        }

        public void setMaxWidth(int maxWidth) {
            this.maxWidth = maxWidth;
        }

        public interface Callback {
            void post(GatherComponents event);
        }
    }
}
