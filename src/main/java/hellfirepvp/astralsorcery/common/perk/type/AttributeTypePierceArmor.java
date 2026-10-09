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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

import java.util.Random;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AttributeTypePierceArmor
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AttributeTypePierceArmor extends PerkAttributeType {

    protected final Random rand = new Random();

    public AttributeTypePierceArmor() {
        super(false);
    }

    @Override
    protected void attachListeners() {
        super.attachListeners();
        SimpleIncomingDamageCallback.PIERCE_ARMOR.register(this::onLivingDamage);
    }

    private boolean onLivingDamage(LivingEntity entity, DamageSource damageSource) {
        if (damageSource.getEntity() instanceof ServerPlayer sPlayer) {
            EnvType side = SidedHelper.getSide(sPlayer);
            if (!this.hasTypeApplied(sPlayer, side)) return false;

            PlayerProgress progress = ResearchManager.getProgress(sPlayer, side);
            if (!progress.isValid()) return false;

            float pierceChance = PerkManager.getOrCreateAttributes(sPlayer)
                    .modifyValue(sPlayer, progress, this, 0);
            pierceChance = AttributeEvent.postProcessModded(sPlayer, this, pierceChance);
            if (pierceChance >= this.rand.nextFloat()) {
                //event.addReductionModifier(DamageContainer.Reduction.ARMOR, (container, dmg) -> 0F);
                return true;
            }
        }
        return false;
    }
}
