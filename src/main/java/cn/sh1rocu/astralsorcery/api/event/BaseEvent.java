package cn.sh1rocu.astralsorcery.api.event;

import hellfirepvp.astralsorcery.AstralSorcery;
import net.minecraft.resources.ResourceLocation;

public class BaseEvent {
    protected boolean isCanceled = false;

    public static final ResourceLocation HIGHEST = AstralSorcery.key("event_highest_priority");
    public static final ResourceLocation HIGH = AstralSorcery.key("event_high_priority");
    public static final ResourceLocation LOW = AstralSorcery.key("event_low_priority");
    public static final ResourceLocation LOWEST = AstralSorcery.key("event_lowest_priority");
}