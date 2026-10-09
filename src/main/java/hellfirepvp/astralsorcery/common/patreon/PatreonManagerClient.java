/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.patreon;

import hellfirepvp.astralsorcery.common.data.sync.SyncDataManager;
import hellfirepvp.astralsorcery.common.data.sync.client.PatreonEntityClientData;
import hellfirepvp.astralsorcery.common.lib.types.SyncDataTypesAS;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: PatreonManagerClient
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class PatreonManagerClient {

    public static void attachListeners() {
        ClientTickEvents.END_CLIENT_TICK.register(PatreonManagerClient::onClientTick);
    }

    private static void onClientTick(Minecraft client) {
        Level clientLevel = client.level;
        Player player = client.player;
        if (clientLevel == null || player == null) return;
        if (client.isPaused()) return;

        ResourceKey<Level> levelKey = clientLevel.dimension();
        PatreonEntityClientData data = SyncDataManager.getInstance().getClientData(SyncDataTypesAS.PATREON_ENTITY);

        data.getAllEntities().forEach(playerEntities -> {
            playerEntities.forEach(entity -> {
                if (entity.getLastTickedDimension() == null || !entity.getLastTickedDimension().equals(levelKey)) {
                    return;
                }
                entity.tick(clientLevel);
            });
        });
    }
}
