/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.data.sync;

import cn.sh1rocu.astralsorcery.api.event.LevelEvent;
import cn.sh1rocu.astralsorcery.util.neoforge.network.PacketDistributor;
import hellfirepvp.astralsorcery.common.network.play.PktSyncData;
import hellfirepvp.astralsorcery.common.util.MiscUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: SyncDataManager
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class SyncDataManager {

    private static final SyncDataManager INSTANCE = new SyncDataManager();

    private final Map<SyncData.Type<?, ?, ?, ?>, SyncData<?, ?, ?>> dataMap = new HashMap<>();
    private final Map<SyncData.Type<?, ?, ?, ?>, ClientData> clientDataMap = new HashMap<>();

    private final Set<SyncData.Type<?, ?, ?, ?>> dirtyData = new HashSet<>();

    private SyncDataManager() {
    }

    public static SyncDataManager getInstance() {
        return INSTANCE;
    }


    public <T extends SyncData<?, ?, ?>> T getData(SyncData.Type<T, ?, ?, ?> type) {
        return MiscUtil.cast(this.dataMap.computeIfAbsent(type, t -> t.dataProvider().get()));
    }

    public <D extends ClientData> D getClientData(SyncData.Type<?, ?, ?, D> type) {
        return MiscUtil.cast(this.clientDataMap.computeIfAbsent(type, t -> t.clientDataProvider().get()));
    }

    public void markForUpdate(SyncData.Type<?, ?, ?, ?> type) {
        this.dirtyData.add(type);
    }

    public void clearLevel(ServerLevel sLevel) {
        this.dataMap.values().forEach(data -> data.clearLevel(sLevel));
    }

    public void clear(EnvType side) {
        if (side == EnvType.CLIENT) {
            this.clientDataMap.values().forEach(ClientData::clear);
        } else {
            this.dataMap.values().forEach(SyncData::clearAll);
        }
    }

    public void attachEventListeners() {
        ServerTickEvents.END_SERVER_TICK.register(this::onServerTick);
        LevelEvent.UNLOAD.register(this::onWorldUnload);
    }

    private void onServerTick(MinecraftServer server) {
        if (this.dirtyData.isEmpty()) return;
        PacketDistributor.sendToAllPlayers(PktSyncData.syncDiff(this, this.dirtyData), server);
        this.dirtyData.clear();
    }

    private void onWorldUnload(LevelEvent.Unload event) {
        if (event.getLevel() instanceof ServerLevel sLevel) {
            this.clearLevel(sLevel);
        }
    }
}
