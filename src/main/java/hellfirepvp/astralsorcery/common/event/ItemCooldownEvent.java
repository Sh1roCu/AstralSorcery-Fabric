/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.event;

import cn.sh1rocu.astralsorcery.api.event.PlayerEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: ItemCooldownEvent
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class ItemCooldownEvent extends PlayerEvent {

    private final int originalCooldown;
    private int cooldown;

    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    });

    public ItemCooldownEvent(Player player, int cooldown) {
        super(player);
        this.originalCooldown = cooldown;
        this.setCooldown(this.getOriginalCooldown());
    }

    public int getOriginalCooldown() {
        return this.originalCooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getCooldown() {
        return this.cooldown;
    }

    public interface Callback {
        void post(ItemCooldownEvent event);
    }
}
