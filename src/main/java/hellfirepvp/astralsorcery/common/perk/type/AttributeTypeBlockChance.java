/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.perk.type;

import cn.sh1rocu.astralsorcery.api.event.SimpleShieldBlockCallback;
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
import net.minecraft.world.entity.projectile.AbstractArrow;

import java.util.Random;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AttributeTypeBlockChance
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AttributeTypeBlockChance extends PerkAttributeType {

    protected final Random rand = new Random();

    public AttributeTypeBlockChance() {
        super(false);
    }

    @Override
    protected void attachListeners() {
        super.attachListeners();
        SimpleShieldBlockCallback.EVENT.register(this::onBlockTest);
    }

    private boolean onBlockTest(boolean blocked, DamageSource damageSource, LivingEntity entity) {
        if (blocked) return blocked;
        if (damageSource.is(DamageTypeTags.BYPASSES_SHIELD)) return blocked;
        if (damageSource.getDirectEntity() instanceof AbstractArrow arrow && arrow.getPierceLevel() > 0) return blocked;
        if (damageSource.getSourcePosition() == null) return blocked;

        if (!(entity instanceof Player player)) return blocked;
        EnvType side = SidedHelper.getSide(player);
        if (!this.hasTypeApplied(player, side)) return blocked;

        PlayerProgress progress = ResearchManager.getProgress(player, side);
        if (!progress.isValid()) return blocked;

        boolean result = blocked;

        float blockChance = PerkManager.getOrCreateAttributes(player)
                .modifyValue(player, progress, this, 0F);
        blockChance = AttributeEvent.postProcessModded(player, this, blockChance);
        if (blockChance >= this.rand.nextFloat()) {
            // event.setBlocked(true);
            result = true;
        }

        return result;
    }
}
