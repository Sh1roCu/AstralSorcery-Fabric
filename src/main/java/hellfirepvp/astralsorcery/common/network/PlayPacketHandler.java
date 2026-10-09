/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.network;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.util.NameUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: PlayPacketHandler
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public abstract class PlayPacketHandler<T extends CustomPacketPayload> {

    private final CustomPacketPayload.Type<T> type;

    PlayPacketHandler(CustomPacketPayload.Type<T> type) {
        this.type = type;
    }

    public final CustomPacketPayload.Type<T> type() {
        return this.type;
    }

    public static <T extends CustomPacketPayload> CustomPacketPayload.Type<T> makeType(String name) {
        return new CustomPacketPayload.Type<>(NameUtil.prefixPath(AstralSorcery.key(name), "play/"));
    }

    public abstract StreamCodec<RegistryFriendlyByteBuf, T> codec();

    public abstract void register();

    public abstract static class ToClient<T extends CustomPacketPayload> extends PlayPacketHandler<T> {

        protected ToClient(CustomPacketPayload.Type<T> type) {
            super(type);
        }

        @Environment(EnvType.CLIENT)
        public abstract void receive(T payload, ClientPlayNetworking.Context context);

        @Override
        public final void register() {
            PayloadTypeRegistry.playS2C().register(this.type(), this.codec());
        }

        @Environment(EnvType.CLIENT)
        public final void registerReceiver() {
            ClientPlayNetworking.registerGlobalReceiver(this.type(), this::receive);
        }
    }

    public abstract static class ToServer<T extends CustomPacketPayload> extends PlayPacketHandler<T> implements ServerPlayNetworking.PlayPayloadHandler<T> {

        protected ToServer(CustomPacketPayload.Type<T> type) {
            super(type);
        }

        @Override
        public final void register() {
            PayloadTypeRegistry.playC2S().register(this.type(), this.codec());
            ServerPlayNetworking.registerGlobalReceiver(this.type(), this);
        }
    }

    public abstract static class BiDirectional<T extends CustomPacketPayload> extends PlayPacketHandler<T> {

        protected BiDirectional(CustomPacketPayload.Type<T> type) {
            super(type);
        }

        public final void register() {
            PayloadTypeRegistry.playC2S().register(this.type(), this.codec());
            PayloadTypeRegistry.playS2C().register(this.type(), this.codec());

            ServerPlayNetworking.registerGlobalReceiver(this.type(), this::handleServer);
        }

        @Environment(EnvType.CLIENT)
        public final void registerReceiver() {
            ClientPlayNetworking.registerGlobalReceiver(this.type(), this::handleClient);
        }

        @Environment(EnvType.CLIENT)
        public abstract void handleClient(T payload, ClientPlayNetworking.Context context);

        public abstract void handleServer(T payload, ServerPlayNetworking.Context context);
    }

}
