/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib.types;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.data.sync.ClientData;
import hellfirepvp.astralsorcery.common.data.sync.ClientSyncData;
import hellfirepvp.astralsorcery.common.data.sync.ClientSyncDiffData;
import hellfirepvp.astralsorcery.common.data.sync.SyncData;
import hellfirepvp.astralsorcery.common.data.sync.client.CelestialGatewayClientData;
import hellfirepvp.astralsorcery.common.data.sync.client.FocalPointClientData;
import hellfirepvp.astralsorcery.common.data.sync.client.LightConnectionClientData;
import hellfirepvp.astralsorcery.common.data.sync.client.PatreonEntityClientData;
import hellfirepvp.astralsorcery.common.data.sync.server.CelestialGatewaySyncData;
import hellfirepvp.astralsorcery.common.data.sync.server.FocalPointSyncData;
import hellfirepvp.astralsorcery.common.data.sync.server.LightConnectionSyncData;
import hellfirepvp.astralsorcery.common.data.sync.server.PatreonEntitySyncData;
import hellfirepvp.astralsorcery.common.lib.RegistriesAS;
import net.minecraft.core.Registry;

import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: SyncDataTypesAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class SyncDataTypesAS {

    public static void init() {

    }

    public static final SyncData.Type<FocalPointSyncData, FocalPointSyncData.ClientSync, FocalPointSyncData.ClientDiffSync, FocalPointClientData> FOCAL_POINT =
            register("focal_point", () ->
                    new SyncData.Type<>(FocalPointSyncData::new, FocalPointClientData::new,
                            FocalPointSyncData.ClientSync.STREAM_CODEC, FocalPointSyncData.ClientDiffSync.STREAM_CODEC));
    public static final SyncData.Type<LightConnectionSyncData, LightConnectionSyncData.ClientSync, LightConnectionSyncData.ClientDiffSync, LightConnectionClientData> LIGHT_CONNECTION =
            register("light_connection", () ->
                    new SyncData.Type<>(LightConnectionSyncData::new, LightConnectionClientData::new,
                            LightConnectionSyncData.ClientSync.STREAM_CODEC, LightConnectionSyncData.ClientDiffSync.STREAM_CODEC));
    public static final SyncData.Type<CelestialGatewaySyncData, CelestialGatewaySyncData.ClientSync, CelestialGatewaySyncData.ClientDiffSync, CelestialGatewayClientData> CELESTIAL_GATEWAY =
            register("celestial_gateway", () ->
                    new SyncData.Type<>(CelestialGatewaySyncData::new, CelestialGatewayClientData::new,
                            CelestialGatewaySyncData.ClientSync.STREAM_CODEC, CelestialGatewaySyncData.ClientDiffSync.STREAM_CODEC));
    public static final SyncData.Type<PatreonEntitySyncData, PatreonEntitySyncData.ClientSync, PatreonEntitySyncData.ClientDiffSync, PatreonEntityClientData> PATREON_ENTITY =
            register("patreon_entity", () ->
                    new SyncData.Type<>(PatreonEntitySyncData::new, PatreonEntityClientData::new,
                            PatreonEntitySyncData.ClientSync.STREAM_CODEC, PatreonEntitySyncData.ClientDiffSync.STREAM_CODEC));

    private static <D extends SyncData<SA, SD, C>, SA extends ClientSyncData<C>, SD extends ClientSyncDiffData<C>, C extends ClientData>
    SyncData.Type<D, SA, SD, C> register(String name, Supplier<SyncData.Type<D, SA, SD, C>> supplier) {
        return Registry.register(RegistriesAS.REGISTRY_SYNC_DATA_TYPES, AstralSorcery.key(name), supplier.get());
    }
}
