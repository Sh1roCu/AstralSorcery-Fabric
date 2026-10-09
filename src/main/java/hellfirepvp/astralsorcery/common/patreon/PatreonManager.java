/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.patreon;

import hellfirepvp.astralsorcery.common.data.sync.SyncDataManager;
import hellfirepvp.astralsorcery.common.data.sync.server.PatreonEntitySyncData;
import hellfirepvp.astralsorcery.common.lib.types.SyncDataTypesAS;
import hellfirepvp.astralsorcery.common.patreon.entity.PatreonPartialEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.Level;

import java.util.*;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: PatreonManager
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class PatreonManager {

    public static void attachListeners() {
        ServerTickEvents.END_SERVER_TICK.register(PatreonManager::onServerTick);
    }

    private static void onServerTick(MinecraftServer server) {
        PlayerList players = server.getPlayerList();
        PatreonEntitySyncData data = SyncDataManager.getInstance().getData(SyncDataTypesAS.PATREON_ENTITY);

        Set<UUID> existingOwners = new HashSet<>(data.getOwners());
        Set<UUID> foundOwners = new HashSet<>();
        Map<UUID, List<PatreonEffect>> playerEffects = PatreonEffectHelper.getPatreonEffects(players.getPlayers());

        playerEffects.forEach((playerUUID, effects) -> {
            ServerPlayer sPlayer = players.getPlayer(playerUUID);
            if (sPlayer == null) return;

            Set<PatreonPartialEntity> knownEntities = data.getEntities(playerUUID);
            for (PatreonEffect effect : PatreonEffectHelper.getPatreonEffects(EnvType.SERVER, playerUUID)) {
                if (effect == null || effect.getPartialEntityProvider() == null) continue;

                PatreonPartialEntity existing = knownEntities.stream()
                        .filter(e -> e.getEffectUUID().equals(effect.getEffectUUID()))
                        .findFirst()
                        .orElse(null);
                if (existing == null) {
                    existing = data.create(sPlayer, effect);
                }
                if (existing == null) return;

                foundOwners.add(playerUUID);
                Level playerLevel = sPlayer.level();
                if (existing.getLastTickedDimension() != null &&
                        !existing.getLastTickedDimension().equals(playerLevel.dimension())) {
                    existing.placeNear(sPlayer);
                }

                if (existing.tick(playerLevel)) {
                    data.addUpdate(sPlayer, effect.getEffectUUID(), existing);
                }
            }
        });

        for (UUID owner : existingOwners) {
            if (foundOwners.contains(owner)) continue;
            data.remove(owner);
        }
    }
}
