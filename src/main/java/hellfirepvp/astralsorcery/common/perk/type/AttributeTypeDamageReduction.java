/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.perk.type;

import cn.sh1rocu.astralsorcery.api.event.SimpleDamageCallback;
import hellfirepvp.astralsorcery.common.event.AttributeEvent;
import hellfirepvp.astralsorcery.common.perk.PerkManager;
import hellfirepvp.astralsorcery.common.perk.type.base.PerkAttributeType;
import hellfirepvp.astralsorcery.common.research.PlayerProgress;
import hellfirepvp.astralsorcery.common.research.ResearchManager;
import hellfirepvp.astralsorcery.common.util.SidedHelper;
import net.fabricmc.api.EnvType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AttributeTypeDamageReduction
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AttributeTypeDamageReduction extends PerkAttributeType {

    public AttributeTypeDamageReduction() {
        super(true);
    }

    @Override
    protected void attachListeners() {
        super.attachListeners();
        SimpleDamageCallback.PRE.register(this::onDamage);
    }

    private float onDamage(LivingEntity entity, DamageSource damageSource, float vanillaAmount, float newAmount) {
        if (entity instanceof ServerPlayer sPlayer) {
            EnvType side = SidedHelper.getSide(sPlayer);
            if (!this.hasTypeApplied(sPlayer, side)) return newAmount;

            PlayerProgress progress = ResearchManager.getProgress(sPlayer, side);
            if (!progress.isValid()) return newAmount;

            float reduction = PerkManager.getOrCreateAttributes(sPlayer)
                    .getModifier(sPlayer, progress, this);
            reduction = AttributeEvent.postProcessModded(sPlayer, this, reduction);

            float result = newAmount;

            reduction = Mth.clamp(1F - reduction, 0F, 1F);
            result = newAmount * reduction;

            return result;
        }
        return newAmount;
    }
}
