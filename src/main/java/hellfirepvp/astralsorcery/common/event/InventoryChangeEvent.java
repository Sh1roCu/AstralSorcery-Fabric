/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.event;

import cn.sh1rocu.astralsorcery.api.event.PlayerEvent;
import hellfirepvp.astralsorcery.common.util.MiscUtil;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: InventoryChangeEvent
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class InventoryChangeEvent extends PlayerEvent {

    private final ItemStack newStack;

    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    });

    public InventoryChangeEvent(ServerPlayer player, ItemStack newStack) {
        super(player);
        this.newStack = newStack;
    }

    public ServerPlayer getPlayer() {
        return MiscUtil.cast(super.getEntity());
    }

    public ItemStack getNewStack() {
        return this.newStack.copy();
    }

    public interface Callback {
        void post(InventoryChangeEvent event);
    }
}
