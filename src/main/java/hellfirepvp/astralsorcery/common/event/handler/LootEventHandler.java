/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.event.handler;

import hellfirepvp.astralsorcery.common.config.server.LootTableConfig;
import hellfirepvp.astralsorcery.common.lib.ItemsAS;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: LootEventHandler
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class LootEventHandler {

    public static void attachListeners() {
        LootTableEvents.MODIFY.register(LootEventHandler::onLootLoad);
    }

    private static void onLootLoad(ResourceKey<LootTable> key, LootTable.Builder tableBuilder, LootTableSource source, HolderLookup.Provider registries) {
        if (LootTableConfig.CONFIG.canAddConstellationPaper(key.location())) {
            tableBuilder.withPool(LootPool.lootPool()
                    .setRolls(BinomialDistributionGenerator.binomial(1, 0.33F))
                    .add(LootItem.lootTableItem(ItemsAS.CONSTELLATION_PAPER)));
            // TODO?
            // .name(AstralSorcery.key("configured_constellation_paper_pool").toString());
        }
    }
}
