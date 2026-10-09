package cn.sh1rocu.astralsorcery.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class LivingChangeTargetEvent extends LivingEvent implements ICancellableEvent {
    private final ILivingTargetType targetType;
    @Nullable
    private final LivingEntity originalAboutToBeSetTarget;
    @Nullable
    private LivingEntity newAboutToBeSetTarget;

    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (final Callback callback : callbacks)
            callback.post(event);
    });

    public LivingChangeTargetEvent(LivingEntity entity, @Nullable LivingEntity aboutToBeSetTarget, ILivingTargetType targetType) {
        super(entity);
        this.originalAboutToBeSetTarget = aboutToBeSetTarget;
        this.newAboutToBeSetTarget = aboutToBeSetTarget;
        this.targetType = targetType;
    }

    @Nullable
    public LivingEntity getNewAboutToBeSetTarget() {
        return newAboutToBeSetTarget;
    }

    public void setNewAboutToBeSetTarget(@Nullable LivingEntity newAboutToBeSetTarget) {
        this.newAboutToBeSetTarget = newAboutToBeSetTarget;
    }

    public ILivingTargetType getTargetType() {
        return targetType;
    }

    @Nullable
    public LivingEntity getOriginalAboutToBeSetTarget() {
        return originalAboutToBeSetTarget;
    }

    public interface ILivingTargetType {

    }

    public enum LivingTargetType implements ILivingTargetType {
        MOB_TARGET,
        BEHAVIOR_TARGET;
    }

    public interface Callback {
        void post(LivingChangeTargetEvent event);
    }
}