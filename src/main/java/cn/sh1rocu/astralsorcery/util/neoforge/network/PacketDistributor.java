package cn.sh1rocu.astralsorcery.util.neoforge.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class PacketDistributor {
    private PacketDistributor() {
    }

    public static void sendToServer(CustomPacketPayload payload) {
        ClientPlayNetworking.send(payload);
    }

    public static void sendToPlayer(ServerPlayer serverPlayer, CustomPacketPayload payload) {
        ServerPlayNetworking.send(serverPlayer, payload);
    }

    public static void sendToPlayersTrackingEntity(Entity entity, CustomPacketPayload toSend) {
        for (ServerPlayer target : PlayerLookup.tracking(entity)) {
            sendToPlayer(target, toSend);
        }
    }

    public static void sendToPlayersTrackingEntityAndSelf(Entity entity, CustomPacketPayload payload) {
        if (entity instanceof ServerPlayer player) {
            sendToPlayer(player, payload);
        }

        sendToPlayersTrackingEntity(entity, payload);
    }

    public static void sendToPlayersNear(ServerLevel level, @Nullable ServerPlayer except,
                                         double x, double y, double z, double radius,
                                         CustomPacketPayload payload) {
        var rad = radius * radius;
        for (var player : level.players()) {
            if (player == except || player.distanceToSqr(x, y, z) > rad) {
                continue;
            }
            if (ServerPlayNetworking.canSend(player, payload.type())) {
                ServerPlayNetworking.send(player, payload);
            }
        }
    }

    public static void sendToAllPlayers(CustomPacketPayload payload, MinecraftServer server) {
        for (ServerPlayer player : PlayerLookup.all(server)) {
            sendToPlayer(player, payload);
        }
    }
}