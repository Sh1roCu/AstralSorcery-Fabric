package cn.sh1rocu.astralsorcery.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.DeltaTracker;

public class RenderFrameEvent extends BaseEvent {
    private final DeltaTracker.Timer partialTick;

    public RenderFrameEvent(DeltaTracker.Timer timer) {
        this.partialTick = timer;
    }

    public DeltaTracker getPartialTick() {
        return this.partialTick;
    }

    public static final Event<Start> PRE = EventFactory.createArrayBacked(Start.class, callbacks -> event -> {
        for (Start callback : callbacks) {
            callback.post(event);
        }
    });
    public static final Event<End> POST = EventFactory.createArrayBacked(End.class, callbacks -> event -> {
        for (End callback : callbacks) {
            callback.post(event);
        }
    });

    public interface Start {
        void post(Pre event);
    }

    public interface End {
        void post(Post event);
    }

    public static final class Pre extends RenderFrameEvent {
        public Pre(DeltaTracker.Timer timer) {
            super(timer);
        }
    }

    public static final class Post extends RenderFrameEvent {
        public Post(DeltaTracker.Timer timer) {
            super(timer);
        }
    }
}