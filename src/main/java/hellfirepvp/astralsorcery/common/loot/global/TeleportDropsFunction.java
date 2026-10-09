/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.loot.global;

import hellfirepvp.astralsorcery.common.perk.tree.perk.key.KeyPerkTeleportDrops;
import hellfirepvp.astralsorcery.common.research.ResearchManager;
import hellfirepvp.astralsorcery.common.util.ItemUtil;
import hellfirepvp.astralsorcery.common.util.MiscUtil;
import hellfirepvp.astralsorcery.common.util.SidedHelper;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.api.EnvType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: TeleportDropsFunction
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class TeleportDropsFunction {

    public static ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        Player player = MiscUtil.firstNonNull(
                () -> getLootingPlayer(context, LootContextParams.LAST_DAMAGE_PLAYER),
                () -> getLootingPlayer(context, LootContextParams.ATTACKING_ENTITY),
                () -> getLootingPlayer(context, LootContextParams.DIRECT_ATTACKING_ENTITY),
                () -> getLootingPlayer(context, LootContextParams.THIS_ENTITY)
        ).orElse(null);

        if (player != null) {
            EnvType side = SidedHelper.getSide(player);
            if (ResearchManager.getProgress(player, side).getPerkData().hasPerkEffect(perk -> perk instanceof KeyPerkTeleportDrops)) {
                Level level = player.level();
                Vec3 pos = player.position();
                generatedLoot.forEach(stack -> {
                    if (!player.addItem(stack)) {
                        ItemUtil.dropItem(level, pos, stack);
                    }
                });
                return ObjectArrayList.of();
            }
        }

        return generatedLoot;
    }

    private static Player getLootingPlayer(LootContext ctx, LootContextParam<? extends Entity> param) {
        if (ctx.hasParam(param)) {
            Entity e = ctx.getParam(param);
            if (e instanceof Player player) return player;
        }
        return null;
    }
}
