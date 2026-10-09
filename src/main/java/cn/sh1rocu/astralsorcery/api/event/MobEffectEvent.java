package cn.sh1rocu.astralsorcery.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public abstract class MobEffectEvent extends LivingEvent {
    @Nullable
    protected final MobEffectInstance effectInstance;

    public static final Event<Added.Callback> ADDED = EventFactory.createArrayBacked(Added.Callback.class, callbacks -> event -> {
        for (final Added.Callback callback : callbacks)
            callback.post(event);
    });


    protected MobEffectEvent(LivingEntity living, @Nullable MobEffectInstance effectInstance) {
        super(living);
        this.effectInstance = effectInstance;
    }

    @Nullable
    public MobEffectInstance getEffectInstance() {
        return effectInstance;
    }

    public static class Added extends MobEffectEvent {
        private final MobEffectInstance oldEffectInstance;
        private final Entity source;

        public Added(LivingEntity living, MobEffectInstance oldEffectInstance, MobEffectInstance newEffectInstance, Entity source) {
            super(living, newEffectInstance);
            this.oldEffectInstance = oldEffectInstance;
            this.source = source;
        }

        @Override
        public MobEffectInstance getEffectInstance() {
            return super.getEffectInstance();
        }

        @Nullable
        public MobEffectInstance getOldEffectInstance() {
            return oldEffectInstance;
        }

        @Nullable
        public Entity getEffectSource() {
            return source;
        }

        public interface Callback {
            void post(Added event);
        }
    }
}