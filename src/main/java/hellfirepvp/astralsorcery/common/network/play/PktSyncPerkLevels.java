/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.network.play;

import hellfirepvp.astralsorcery.common.network.PlayPacketHandler;
import hellfirepvp.astralsorcery.common.perk.PerkLevelManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: PktSyncPerkLevels
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class PktSyncPerkLevels extends PlayPacketHandler.ToClient<PktSyncPerkLevels.Request> {

    static final CustomPacketPayload.Type<PktSyncPerkLevels.Request> TYPE = makeType("sync_perk_levels");
    static final StreamCodec<RegistryFriendlyByteBuf, PktSyncPerkLevels.Request> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            Request::maxPerkLevel,
            Request::new
    );

    public static final PktSyncPerkLevels HANDLER = new PktSyncPerkLevels();

    private PktSyncPerkLevels() {
        super(TYPE);
    }

    public static PktSyncPerkLevels.Request sync(ServerPlayer sPlayer) {
        return new Request(PerkLevelManager.getInstance().getMaxLevel(EnvType.SERVER, sPlayer));
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, Request> codec() {
        return CODEC;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void receive(Request payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            PerkLevelManager.getInstance().initializeClientLevels(payload.maxPerkLevel());
        });
    }

    public static record Request(int maxPerkLevel) implements CustomPacketPayload {

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

}
