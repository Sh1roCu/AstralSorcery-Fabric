/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.perk.type;

import hellfirepvp.astralsorcery.common.event.AttributeEvent;
import hellfirepvp.astralsorcery.common.perk.PerkManager;
import hellfirepvp.astralsorcery.common.perk.type.base.PerkAttributeType;
import hellfirepvp.astralsorcery.common.research.PlayerProgress;
import hellfirepvp.astralsorcery.common.research.ResearchManager;
import hellfirepvp.astralsorcery.common.util.DamageUtil;
import hellfirepvp.astralsorcery.common.util.SidedHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AttributeTypeDamageReflect
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AttributeTypeDamageReflect extends PerkAttributeType {

    public AttributeTypeDamageReflect() {
        super(false);
    }

    @Override
    protected void attachListeners() {
        super.attachListeners();
        ServerLivingEntityEvents.AFTER_DAMAGE.register(this::onDamageTaken);
    }

    private void onDamageTaken(LivingEntity entity, DamageSource source, float baseDamageTaken, float damageTaken, boolean blocked) {
        if (!(source.getEntity() instanceof LivingEntity attacker)) return;
        if (!(entity instanceof Player player)) return;
        EnvType side = SidedHelper.getSide(player);
        if (!this.hasTypeApplied(player, side)) return;

        PlayerProgress progress = ResearchManager.getProgress(player, side);
        if (!progress.isValid()) return;

        float reflectPercent = PerkManager.getOrCreateAttributes(player)
                .modifyValue(player, progress, this, 0F);
        reflectPercent = AttributeEvent.postProcessModded(player, this, reflectPercent);

        float damageToReflect = damageTaken * reflectPercent;
        if (damageToReflect <= 0) return;

        DamageUtil.shotgunAttack(attacker, e -> {
            e.hurt(e.level().damageSources().thorns(player), damageToReflect);
        });
    }
}
