package cn.sh1rocu.astralsorcery.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

import static cn.sh1rocu.astralsorcery.api.event.BaseEvent.*;

/**
 * A simple impl of NeoForge's LivingDamageEvent
 *
 */
public interface SimpleDamageCallback {

    Event<PreCallback> PRE = EventFactory.createWithPhases(PreCallback.class, callbacks ->
            (entity, damageSource, vanillaAmount, newAmount) -> {
                for (PreCallback preCallback : callbacks) {
                    newAmount = preCallback.onLivingDamagePre(entity, damageSource, vanillaAmount, newAmount);
                }
                return newAmount;
            }, HIGHEST, HIGH, Event.DEFAULT_PHASE, LOW, LOWEST);

    interface PreCallback {
        float onLivingDamagePre(LivingEntity entity, DamageSource damageSource, float vanillaAmount, float newAmount);
    }
}
