/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.perk.type;

import cn.sh1rocu.astralsorcery.api.event.MobEffectEvent;
import hellfirepvp.astralsorcery.common.event.AttributeEvent;
import hellfirepvp.astralsorcery.common.perk.PerkManager;
import hellfirepvp.astralsorcery.common.perk.type.base.PerkAttributeType;
import hellfirepvp.astralsorcery.common.research.ResearchManager;
import hellfirepvp.astralsorcery.common.util.SidedHelper;
import net.fabricmc.api.EnvType;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AttributeTypePotionDuration
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AttributeTypePotionDuration extends PerkAttributeType {

    public AttributeTypePotionDuration() {
        super(true);
    }

    @Override
    protected void attachListeners() {
        super.attachListeners();
        MobEffectEvent.ADDED.register(this::onPotionAdded);
    }

    private void onPotionAdded(MobEffectEvent.Added event) {
        if (!(event.getEntity() instanceof Player player)) return;
        EnvType side = SidedHelper.getSide(player);
        if (!this.hasTypeApplied(player, side)) return;
        if (event.getEffectInstance() == null) return;

        if (event.getOldEffectInstance() == null) {
            modifyPotionDuration(player, side, event.getEffectInstance(), event.getEffectInstance());
        } else {
            modifyPotionDuration(player, side, event.getEffectInstance(), event.getOldEffectInstance());
        }
    }

    private void modifyPotionDuration(Player player, EnvType side, MobEffectInstance newInstance, MobEffectInstance existingInstance) {
        if (newInstance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL ||
                existingInstance.getAmplifier() > newInstance.getAmplifier()) {
            return;
        }

        float newDuration = newInstance.getDuration();
        newDuration = PerkManager.getOrCreateAttributes(player)
                .modifyValue(player, ResearchManager.getProgress(player, side), this, newDuration);
        newDuration = AttributeEvent.postProcessModded(player, this, newDuration);

        if (newInstance.getDuration() < newDuration) {
            newInstance.duration = Mth.floor(newDuration);
        }
    }
}
