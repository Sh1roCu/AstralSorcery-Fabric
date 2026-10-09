package cn.sh1rocu.astralsorcery.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class CriticalHitEvent extends PlayerEvent {
    private final Entity target;
    private final float vanillaDmgMultiplier;
    private final boolean isVanillaCritical;

    private float dmgMultiplier;
    private boolean isCriticalHit;

    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (final Callback callback : callbacks)
            callback.post(event);
    });

    public CriticalHitEvent(Player player, Entity target, float dmgMultiplier, boolean isCriticalHit) {
        super(player);
        this.target = target;
        this.dmgMultiplier = this.vanillaDmgMultiplier = dmgMultiplier;
        this.isCriticalHit = this.isVanillaCritical = isCriticalHit;
    }

    public Entity getTarget() {
        return this.target;
    }

    public float getDamageMultiplier() {
        return this.dmgMultiplier;
    }

    public void setDamageMultiplier(float dmgMultiplier) {
        if (dmgMultiplier < 0) {
            throw new UnsupportedOperationException("Attempted to set a negative damage multiplier: " + dmgMultiplier);
        }
        this.dmgMultiplier = dmgMultiplier;
    }

    public boolean isCriticalHit() {
        return this.isCriticalHit;
    }

    public void setCriticalHit(boolean isCriticalHit) {
        this.isCriticalHit = isCriticalHit;
    }

    public float getVanillaMultiplier() {
        return this.vanillaDmgMultiplier;
    }

    public boolean isVanillaCritical() {
        return this.isVanillaCritical;
    }

    public interface Callback {
        void post(CriticalHitEvent event);
    }
}