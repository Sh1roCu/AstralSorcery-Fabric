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
import hellfirepvp.astralsorcery.common.lib.constants.TagsAS;
import hellfirepvp.astralsorcery.common.perk.PerkManager;
import hellfirepvp.astralsorcery.common.perk.type.base.PerkAttributeType;
import hellfirepvp.astralsorcery.common.research.PlayerProgress;
import hellfirepvp.astralsorcery.common.research.ResearchManager;
import hellfirepvp.astralsorcery.common.util.SidedHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AttributeTypeElementalResistance
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AttributeTypeElementalResistance extends PerkAttributeType {

    public AttributeTypeElementalResistance() {
        super(true);
    }

    @Override
    protected void attachListeners() {
        super.attachListeners();
        SimpleIncomingDamageCallback.MODIFY_DAMAGE.register(this::onDamage);
        // For Fabric
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(this::allowDamage);
    }

    private float onDamage(LivingEntity entity, DamageSource damageSource, float amount) {
        if (!(entity instanceof Player player)) return amount;
        EnvType side = SidedHelper.getSide(player);
        if (!this.hasTypeApplied(player, side)) return amount;

        PlayerProgress progress = ResearchManager.getProgress(player, side);
        if (!progress.isValid()) return amount;
        if (!damageSource.is(TagsAS.DamageTypes.IS_ELEMENTAL)) return amount;

        float resistanceMultiplier = PerkManager.getOrCreateAttributes(player)
                .getModifier(player, progress, this);
        resistanceMultiplier = AttributeEvent.postProcessModded(player, this, resistanceMultiplier);
        resistanceMultiplier = Mth.clamp(1F - resistanceMultiplier, 0F, 1F);

//        if (resistanceMultiplier <= 0) {
//            // event.setCanceled(true);
//            return;
//        }
        if (resistanceMultiplier > 0) {
            return amount * resistanceMultiplier;
        }
        return amount;
    }

    private boolean allowDamage(LivingEntity entity, DamageSource damageSource, float amount) {
        if (!(entity instanceof Player player)) return true;
        EnvType side = SidedHelper.getSide(player);
        if (!this.hasTypeApplied(player, side)) return true;

        PlayerProgress progress = ResearchManager.getProgress(player, side);
        if (!progress.isValid()) return true;
        if (!damageSource.is(TagsAS.DamageTypes.IS_ELEMENTAL)) return true;
        float resistanceMultiplier = PerkManager.getOrCreateAttributes(player)
                .getModifier(player, progress, this);

        resistanceMultiplier = AttributeEvent.postProcessModded(player, this, resistanceMultiplier);
        resistanceMultiplier = Mth.clamp(1F - resistanceMultiplier, 0F, 1F);

        if (resistanceMultiplier <= 0) {
            return false;
        }

        return true;
    }
}
