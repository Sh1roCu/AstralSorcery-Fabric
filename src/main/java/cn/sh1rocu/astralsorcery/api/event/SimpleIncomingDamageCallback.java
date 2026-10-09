package cn.sh1rocu.astralsorcery.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

/**
 * A simple impl of NeoForge's LivingIncomingDamageEvent
 *
 */
public interface SimpleIncomingDamageCallback {

    Event<ModifyDamageCallback> MODIFY_DAMAGE
            = EventFactory.createArrayBacked(ModifyDamageCallback.class, callbacks ->
            (entity, damageSource, amount) -> {
                float result = amount;
                for (ModifyDamageCallback callback : callbacks) {
                    result = callback.modifyDamage(entity, damageSource, result);
                }
                return result;
            });
    Event<PierceArmorCallback> PIERCE_ARMOR = EventFactory.createArrayBacked(PierceArmorCallback.class, callbacks ->
            (entity, damageSource) -> {
                boolean result = false;
                for (PierceArmorCallback callback : callbacks) {
                    result = callback.pierceArmor(entity, damageSource);
                }
                return result;
            });

    interface ModifyDamageCallback {
        float modifyDamage(LivingEntity entity, DamageSource damageSource, float amount);
    }

    interface PierceArmorCallback {
        boolean pierceArmor(LivingEntity entity, DamageSource damageSource);
    }
}
