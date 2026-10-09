/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.effect;

import hellfirepvp.astralsorcery.common.lib.MobEffectsAS;
import hellfirepvp.astralsorcery.common.util.MobEffectUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: RevivalMobEffect
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class RevivalMobEffect extends BasicMobEffect {

    public RevivalMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public RevivalMobEffect(MobEffectCategory category, int color, ParticleOptions particle) {
        super(category, color, particle);
    }

    @Override
    public void attachEventListeners() {
        super.attachEventListeners();
        ServerLivingEntityEvents.ALLOW_DEATH.register(this::onDeath);
    }

    private boolean onDeath(LivingEntity entity, DamageSource damageSource, float damageAmount) {
        if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) return true;

        if (!entity.hasEffect(MobEffectsAS.PHOENIX_BLESSING)) return true;

        Level level = entity.level();
        if (level.isClientSide()) return true;

        MobEffectInstance inst = entity.getEffect(MobEffectsAS.PHOENIX_BLESSING);
        if (inst == null || inst.getDuration() <= 0) return true;
        int amplifier = inst.getAmplifier();
        int nextAmplifier = amplifier - 1;

        boolean cancelled = true;
        // event.setCanceled(true);

        entity.setHealth(entity.getMaxHealth() / 3F);
        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 1));
        entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 0));

        entity.removeEffect(MobEffectsAS.PHOENIX_BLESSING);
        if (nextAmplifier > 0) {
            entity.addEffect(MobEffectUtil.newAmplifier(inst, nextAmplifier));
        }

        return !cancelled;
    }
}
