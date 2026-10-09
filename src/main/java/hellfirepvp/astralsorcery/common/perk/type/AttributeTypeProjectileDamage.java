/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.perk.type;

import cn.sh1rocu.astralsorcery.api.event.SimpleIncomingDamageCallback;
import hellfirepvp.astralsorcery.common.event.AttributeEvent;
import hellfirepvp.astralsorcery.common.perk.PerkManager;
import hellfirepvp.astralsorcery.common.perk.type.base.PerkAttributeType;
import hellfirepvp.astralsorcery.common.research.PlayerProgress;
import hellfirepvp.astralsorcery.common.research.ResearchManager;
import hellfirepvp.astralsorcery.common.util.SidedHelper;
import net.fabricmc.api.EnvType;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AttributeTypeProjectileDamage
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AttributeTypeProjectileDamage extends PerkAttributeType {

    public AttributeTypeProjectileDamage() {
        super(false);
    }

    @Override
    protected void attachListeners() {
        super.attachListeners();
        SimpleIncomingDamageCallback.MODIFY_DAMAGE.register(this::onProjectileHurt);
    }

    private float onProjectileHurt(LivingEntity entity, DamageSource damageSource, float amount) {
        if (damageSource.is(DamageTypeTags.IS_PROJECTILE) && damageSource.getEntity() instanceof Player player) {
            EnvType side = SidedHelper.getSide(player);
            if (!this.hasTypeApplied(player, side)) return amount;

            PlayerProgress progress = ResearchManager.getProgress(player, side);
            if (!progress.isValid()) return amount;

            float dmg = PerkManager.getOrCreateAttributes(player)
                    .modifyValue(player, progress, this, amount);
            dmg = AttributeEvent.postProcessModded(player, this, dmg);
            // event.setAmount(dmg);
            return dmg;
        }
        return amount;
    }
}
