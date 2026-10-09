/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.perk.source;

import cn.sh1rocu.astralsorcery.api.event.PlayerTickEvent;
import hellfirepvp.astralsorcery.common.lib.RegistriesAS;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: ModifierManager
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class ModifierManager {

    private static final ModifierManager INSTANCE = new ModifierManager();

    private static final Map<UUID, Set<ModifierSource>> modifierCache = new HashMap<>();
    private static final Map<UUID, Set<ModifierSource>> modifierCacheClient = new HashMap<>();

    private ModifierManager() {
    }

    public static ModifierManager getInstance() {
        return INSTANCE;
    }

    public void attachEventListeners() {
        PlayerTickEvent.POST.register(this::onPlayerTick);
        ServerPlayerEvents.LEAVE.register(this::onDisconnect);
    }

    private void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer sPlayer) {
            for (ModifierSourceProvider<?> sourceProvider : RegistriesAS.REGISTRY_PERK_MODIFIER_SOURCES) {
                sourceProvider.update(sPlayer);
            }
        }
    }

    @Nonnull
    private static Set<ModifierSource> getModifiers(Player player, EnvType side) {
        if (side == EnvType.CLIENT) {
            return modifierCacheClient.computeIfAbsent(player.getUUID(), uuid -> new HashSet<>());
        } else {
            return modifierCache.computeIfAbsent(player.getUUID(), uuid -> new HashSet<>());
        }
    }

    @Nonnull
    public static Set<ModifierSource> getAppliedModifiers(Player player, EnvType side) {
        return new HashSet<>(getModifiers(player, side));
    }

    public static void addModifier(Player player, EnvType side, ModifierSource source) {
        Set<ModifierSource> modifiers = getModifiers(player, side);
        if (!modifiers.contains(source) && modifiers.add(source)) {
            source.onApply(player, side);
        }
    }

    public static void removeModifier(Player player, EnvType side, ModifierSource source) {
        Set<ModifierSource> modifiers = getModifiers(player, side);
        if (modifiers.remove(source)) {
            source.onRemove(player, side);
        }
    }

    public static boolean isModifierApplied(Player player, EnvType side, ModifierSource source) {
        return getModifiers(player, side).contains(source);
    }

    @Environment(EnvType.CLIENT)
    public static void clearClientCache() {
        modifierCacheClient.clear();
    }

    private void onDisconnect(Player player) {
        if (player instanceof ServerPlayer sPlayer) {
            for (ModifierSourceProvider<?> sourceProvider : RegistriesAS.REGISTRY_PERK_MODIFIER_SOURCES) {
                sourceProvider.removeModifiers(sPlayer);
            }
        }
    }
}
