package cn.sh1rocu.astralsorcery.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

/**
 * A simple impl of NeoForge's LivingShieldBlockEvent
 */
public interface SimpleShieldBlockCallback {

    Event<SimpleShieldBlockCallback> EVENT = EventFactory.createArrayBacked(SimpleShieldBlockCallback.class, callbacks ->
            (blocked, damageSource, entity) -> {
                boolean result = blocked;
                for (SimpleShieldBlockCallback callback : callbacks) {
                    result = callback.allowBlock(result, damageSource, entity);
                }
                return result;
            });

    boolean allowBlock(boolean blocked, DamageSource damageSource, LivingEntity entity);
}
