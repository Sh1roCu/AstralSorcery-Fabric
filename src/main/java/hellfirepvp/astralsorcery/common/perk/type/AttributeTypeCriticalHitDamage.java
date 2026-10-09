/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.perk.type;

import cn.sh1rocu.astralsorcery.api.event.BaseEvent;
import cn.sh1rocu.astralsorcery.api.event.CriticalHitEvent;
import cn.sh1rocu.astralsorcery.api.event.EntityJoinLevelEvent;
import hellfirepvp.astralsorcery.common.event.AttributeEvent;
import hellfirepvp.astralsorcery.common.perk.PerkManager;
import hellfirepvp.astralsorcery.common.perk.type.base.PerkAttributeType;
import hellfirepvp.astralsorcery.common.research.PlayerProgress;
import hellfirepvp.astralsorcery.common.research.ResearchManager;
import hellfirepvp.astralsorcery.common.util.SidedHelper;
import net.fabricmc.api.EnvType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AttributeTypeCriticalHitChance
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AttributeTypeCriticalHitDamage extends PerkAttributeType {

    public AttributeTypeCriticalHitDamage() {
        super(true);
    }

    @Override
    protected void attachListeners() {
        super.attachListeners();
        CriticalHitEvent.EVENT.register(BaseEvent.LOW, this::onCritHit);
        EntityJoinLevelEvent.EVENT.register(BaseEvent.LOW, this::onArrowSpawn);
    }

    private void onCritHit(CriticalHitEvent event) {
        if (!event.isCriticalHit()) return;

        Player attacker = event.getEntity();
        EnvType side = SidedHelper.getSide(attacker);
        if (!this.hasTypeApplied(attacker, side)) return;

        PlayerProgress progress = ResearchManager.getProgress(attacker, side);
        if (!progress.isValid()) return;

        float critDmg = PerkManager.getOrCreateAttributes(attacker)
                .getModifier(attacker, progress, this);
        critDmg = AttributeEvent.postProcessModded(attacker, this, critDmg);
        if (critDmg >= 0) {
            event.setDamageMultiplier(event.getDamageMultiplier() * critDmg);
        }
    }

    private void onArrowSpawn(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof Arrow arrow)) return;
        if (!arrow.isCritArrow()) return;
        if (!(arrow.getOwner() instanceof Player player)) return;
        EnvType side = SidedHelper.getSide(player);
        if (!this.hasTypeApplied(player, side)) return;

        PlayerProgress progress = ResearchManager.getProgress(player, side);
        if (!progress.isValid()) return;

        float critDmg = PerkManager.getOrCreateAttributes(player)
                .getModifier(player, progress, this);
        critDmg = AttributeEvent.postProcessModded(player, this, critDmg);
        if (critDmg >= 0) {
            arrow.setBaseDamage(arrow.getBaseDamage() * critDmg);
        }
    }
}
