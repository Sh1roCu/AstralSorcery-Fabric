package cn.sh1rocu.astralsorcery.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.level.LevelAccessor;

public abstract class LevelEvent extends BaseEvent {
    private final LevelAccessor level;

    public static final Event<Load.Callback> LOAD = EventFactory.createArrayBacked(Load.Callback.class, callbacks -> event -> {
        for (Load.Callback callback : callbacks)
            callback.post(event);
    });
    public static final Event<Unload.Callback> UNLOAD = EventFactory.createArrayBacked(Unload.Callback.class, callbacks -> event -> {
        for (Unload.Callback callback : callbacks)
            callback.post(event);
    });

    public LevelEvent(LevelAccessor level) {
        this.level = level;
    }

    public LevelAccessor getLevel() {
        return level;
    }

    public static class Load extends LevelEvent {
        public Load(LevelAccessor level) {
            super(level);
        }

        public interface Callback {
            void post(Load event);
        }
    }

    public static class Unload extends LevelEvent {
        public Unload(LevelAccessor level) {
            super(level);
        }

        public interface Callback {
            void post(Unload event);
        }
    }
}