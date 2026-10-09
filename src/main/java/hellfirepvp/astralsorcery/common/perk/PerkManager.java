/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.perk;

import cn.sh1rocu.astralsorcery.util.neoforge.network.PacketDistributor;
import hellfirepvp.astralsorcery.common.network.play.PktSyncPerkActivity;
import hellfirepvp.astralsorcery.common.perk.source.ModifierManager;
import hellfirepvp.astralsorcery.common.perk.source.ModifierSource;
import hellfirepvp.astralsorcery.common.perk.tick.PerkCooldownHelper;
import hellfirepvp.astralsorcery.common.perk.tree.AbstractPerk;
import hellfirepvp.astralsorcery.common.research.PlayerPerkData;
import hellfirepvp.astralsorcery.common.research.PlayerProgress;
import hellfirepvp.astralsorcery.common.research.ResearchManager;
import hellfirepvp.astralsorcery.common.util.SidedHelper;
import hellfirepvp.astralsorcery.common.util.data.SidedReference;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: PerkManager
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class PerkManager {

    private static final PerkManager INSTANCE = new PerkManager();

    private final SidedReference<Map<UUID, PerkAttributeMap>> perkMap = SidedReference.create(HashMap::new);

    private PerkManager() {
    }

    public static PerkManager getInstance() {
        return INSTANCE;
    }

    public static PerkAttributeMap getOrCreateAttributes(Player player) {
        EnvType side = SidedHelper.getSide(player);
        return getInstance().perkMap.getData(side)
                .map(dataMap -> dataMap.computeIfAbsent(player.getUUID(), id -> new PerkAttributeMap(side)))
                .orElseThrow();
    }

    public void attachEventListeners() {
        ServerPlayerEvents.JOIN.register(this::onPlayerConnect);
        ServerPlayerEvents.LEAVE.register(this::onPlayerDisconnect);
        ServerPlayerEvents.COPY_FROM.register(this::onPlayerRecreate);
    }

    private void onPlayerConnect(Player player) {
        modifyAllPerks(player, EnvType.SERVER, Action.ADD);

        if (player instanceof ServerPlayer sPlayer) {
            PacketDistributor.sendToPlayer(sPlayer, PktSyncPerkActivity.applyAll());
        }
    }

    private void onPlayerDisconnect(Player player) {
        modifyAllPerks(player, EnvType.SERVER, Action.REMOVE);
    }

    private void onPlayerRecreate(Player oldPlayer, Player newPlayer, boolean alive) {
        modifyAllPerks(oldPlayer, EnvType.SERVER, Action.REMOVE);
        modifyAllPerks(newPlayer, EnvType.SERVER, Action.ADD);

        PerkCooldownHelper.removeAllCooldowns(oldPlayer, EnvType.SERVER);
        if (newPlayer instanceof ServerPlayer sPlayer) {
            PacketDistributor.sendToPlayer(sPlayer, PktSyncPerkActivity.applyAll());
        }
    }

    public void clearServer() {
        this.perkMap.getData(EnvType.SERVER).ifPresent(Map::clear);
    }

    public void clearClient() {
        this.perkMap.getData(EnvType.CLIENT).ifPresent(Map::clear);
    }

    @Environment(EnvType.CLIENT)
    public static <D extends AbstractPerk.Data> void clientChangePerkData(AbstractPerk<D> perk, D oldData, D newData) {
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        PlayerProgress progress = ResearchManager.getProgress(player, EnvType.CLIENT);
        PlayerPerkData perkData = progress.getPerkData();

        if (!perkData.hasPerkAllocation(perk)) {
            return;
        }
        perkData.updateAllocatedPerkData(perk, oldData);
        PerkApplicationManager.modifySource(player, EnvType.CLIENT, perk, Action.REMOVE);
        perkData.updateAllocatedPerkData(perk, newData);
        PerkApplicationManager.modifySource(player, EnvType.CLIENT, perk, Action.ADD);
    }

    @Environment(EnvType.CLIENT)
    public static void clientClearAllPerks() {
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        PlayerProgress progress = ResearchManager.getProgress(player, EnvType.CLIENT);
        if (!progress.isValid()) {
            return;
        }

        PerkAttributeMap attr = getOrCreateAttributes(player);
        for (ModifierSource source : ModifierManager.getAppliedModifiers(player, EnvType.CLIENT)) {
            if (source instanceof AbstractPerk) {
                PerkApplicationManager.removeSource(attr, player, EnvType.CLIENT, source);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static void clientRefreshAllPerks() {
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        modifyAllPerks(player, EnvType.CLIENT, Action.ADD);
        PerkCooldownHelper.removeAllCooldowns(player, EnvType.CLIENT);
    }

    private static void modifyAllPerks(Player player, EnvType side, Action action) {
        ResearchManager.getProgress(player, side).getPerkData().getEffectGrantingPerks()
                .forEach(perk -> PerkApplicationManager.modifySource(player, side, perk, action));
    }

    public enum Action {

        ADD,
        REMOVE;

        public boolean isRemove() {
            return this == REMOVE;
        }

    }
}
