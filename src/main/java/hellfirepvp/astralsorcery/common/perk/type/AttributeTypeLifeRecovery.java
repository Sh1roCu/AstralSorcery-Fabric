/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.perk.type;

import cn.sh1rocu.astralsorcery.api.event.BaseEvent;
import cn.sh1rocu.astralsorcery.api.event.LivingHealEvent;
import hellfirepvp.astralsorcery.common.event.AttributeEvent;
import hellfirepvp.astralsorcery.common.perk.PerkManager;
import hellfirepvp.astralsorcery.common.perk.type.base.PerkAttributeType;
import hellfirepvp.astralsorcery.common.research.ResearchManager;
import hellfirepvp.astralsorcery.common.util.SidedHelper;
import net.fabricmc.api.EnvType;
import net.minecraft.world.entity.player.Player;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AttributeTypeLifeRecovery
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AttributeTypeLifeRecovery extends PerkAttributeType {

    public AttributeTypeLifeRecovery() {
        super(true);
    }

    @Override
    protected void attachListeners() {
        super.attachListeners();
        LivingHealEvent.EVENT.register(BaseEvent.LOW, this::onHeal);
    }

    private void onHeal(LivingHealEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        EnvType side = SidedHelper.getSide(player);
        if (!this.hasTypeApplied(player, side)) return;

        float heal = event.getAmount();
        heal = PerkManager.getOrCreateAttributes(player)
                .modifyValue(player, ResearchManager.getProgress(player, side), this, heal);
        heal = AttributeEvent.postProcessModded(player, this, heal);
        if (heal <= 0) {
            event.setCanceled(true);
        } else {
            event.setAmount(heal);
        }
    }
}
