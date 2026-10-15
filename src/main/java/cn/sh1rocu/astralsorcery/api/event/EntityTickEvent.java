package cn.sh1rocu.astralsorcery.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Entity;

public abstract class EntityTickEvent extends EntityEvent {
    protected EntityTickEvent(Entity entity) {
        super(entity);
    }

    public static final Event<Pre.Callback> PRE = EventFactory.createArrayBacked(Pre.Callback.class, callbacks -> event -> {
        for (Pre.Callback callback : callbacks)
            callback.post(event);
    });
    public static final Event<Post.Callback> POST = EventFactory.createArrayBacked(Post.Callback.class, callbacks -> event -> {
        for (Post.Callback callback : callbacks)
            callback.post(event);
    });

    public static class Pre extends EntityTickEvent implements ICancellableEvent {

        public Pre(Entity entity) {
            super(entity);
        }

        public interface Callback {
            void post(Pre event);
        }
    }

    public static class Post extends EntityTickEvent {

        public Post(Entity entity) {
            super(entity);
        }

        public interface Callback {
            void post(Post event);
        }
    }
}