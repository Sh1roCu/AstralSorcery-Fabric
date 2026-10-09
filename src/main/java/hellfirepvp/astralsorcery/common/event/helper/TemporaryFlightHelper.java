/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.event.helper;

import cn.sh1rocu.astralsorcery.AstralSorceryFabric;
import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.util.tick.TimeoutList;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: TemporaryFlightHelper
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class TemporaryFlightHelper {

    private static final ResourceLocation MODIFIER_ID = AstralSorcery.key("temporary_flight");
    private static final AttributeModifier MODIFIER = new AttributeModifier(MODIFIER_ID, 1, AttributeModifier.Operation.ADD_VALUE);
    private static final TimeoutList<Player> temporaryFlight = new TimeoutList<>(player -> {
        AttributeInstance attr = player.getAttribute(AstralSorceryFabric.CREATIVE_FLIGHT);
        if (attr != null) attr.removeModifier(MODIFIER_ID);
    });

    private TemporaryFlightHelper() {
    }

    public static void attachListeners() {
        ServerTickEvents.END_SERVER_TICK.register(temporaryFlight::onServerTick);
        ServerPlayerEvents.LEAVE.register(TemporaryFlightHelper::onDisconnect);
    }

    private static void onDisconnect(Player player) {
        temporaryFlight.remove(player);
    }

    public static boolean allowFlight(Player player) {
        return allowFlight(player, 20);
    }

    public static boolean allowFlight(Player player, int timeout) {
        if (temporaryFlight.setOrAddTimeout(timeout, player)) {
            AttributeInstance attr = player.getAttribute(AstralSorceryFabric.CREATIVE_FLIGHT);
            if (attr != null && !attr.hasModifier(MODIFIER_ID)) {
                attr.addTransientModifier(MODIFIER);
            }
            return true;
        }
        return false;
    }
}
