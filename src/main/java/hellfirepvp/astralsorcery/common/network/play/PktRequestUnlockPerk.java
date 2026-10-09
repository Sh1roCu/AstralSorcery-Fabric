/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.network.play;

import hellfirepvp.astralsorcery.client.screen.tome.TomePerkTreeScreen;
import hellfirepvp.astralsorcery.common.network.PlayPacketHandler;
import hellfirepvp.astralsorcery.common.perk.data.PerkTree;
import hellfirepvp.astralsorcery.common.research.PlayerProgress;
import hellfirepvp.astralsorcery.common.research.ResearchHelper;
import hellfirepvp.astralsorcery.common.research.ResearchManager;
import hellfirepvp.astralsorcery.common.research.perk.PerkAllocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: PktRequestUnlockPerk
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class PktRequestUnlockPerk extends PlayPacketHandler.BiDirectional<PktRequestUnlockPerk.Request> {

    static final CustomPacketPayload.Type<Request> TYPE = makeType("request_unlock_perk");
    static final StreamCodec<RegistryFriendlyByteBuf, Request> CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC,
            Request::perkKey,
            Request::new
    );

    public static final PktRequestUnlockPerk HANDLER = new PktRequestUnlockPerk();

    private PktRequestUnlockPerk() {
        super(TYPE);
    }

    public static Request unlock(ResourceLocation perkKey) {
        return new Request(perkKey);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void handleClient(Request payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            PerkTree.getInstance().getPerk(EnvType.CLIENT, payload.perkKey()).ifPresent(perk -> {
                if (Minecraft.getInstance().screen instanceof TomePerkTreeScreen perkTreeScreen) {
                    perkTreeScreen.playUnlockAnimation(perk);
                }
            });
        });
    }

    @Override
    public void handleServer(Request payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            if (context.player() instanceof ServerPlayer sPlayer) {
                PerkTree.getInstance().getPerk(EnvType.SERVER, payload.perkKey()).ifPresent(perk -> {
                    PlayerProgress progress = ResearchManager.getProgress(sPlayer, EnvType.SERVER);
                    if (!progress.getPerkData().hasPerkAllocation(perk) &&
                            perk.mayUnlockPerk(progress, sPlayer) &&
                            perk.hasPlayerPerkAllowingUnlock(progress, sPlayer)) {
                        if (ResearchHelper.applyPerk(sPlayer, perk, PerkAllocation.unlock())) {
                            context.responseSender().sendPacket(unlock(payload.perkKey()));
                        }
                    }
                });
            }
        });
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, Request> codec() {
        return CODEC;
    }

    public static record Request(ResourceLocation perkKey) implements CustomPacketPayload {

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
}
