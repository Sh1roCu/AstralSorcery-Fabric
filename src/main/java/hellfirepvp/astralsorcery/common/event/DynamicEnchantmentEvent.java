/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.event;

import cn.sh1rocu.astralsorcery.api.event.PlayerEvent;
import hellfirepvp.astralsorcery.common.enchantment.CombinedEnchantmentModifiers;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: DynamicEnchantmentEvent
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class DynamicEnchantmentEvent {

    public static class Add extends PlayerEvent {

        private final CombinedEnchantmentModifiers.Mutable newDynamicEnchantments;

        public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
            for (Callback callback : callbacks) {
                callback.post(event);
            }
        });

        public Add(Player player) {
            super(player);
            this.newDynamicEnchantments = CombinedEnchantmentModifiers.of().mutable();
        }

        public CombinedEnchantmentModifiers.Mutable getDynamicEnchantments() {
            return this.newDynamicEnchantments;
        }

        public interface Callback {
            void post(Add event);
        }
    }

    public static class Modify extends PlayerEvent {

        private final CombinedEnchantmentModifiers.Mutable dynamicEnchantments;

        public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
            for (Callback callback : callbacks) {
                callback.post(event);
            }
        });

        public Modify(Player player, CombinedEnchantmentModifiers dynamicEnchantments) {
            super(player);
            this.dynamicEnchantments = dynamicEnchantments.mutable();
        }

        public CombinedEnchantmentModifiers.Mutable getDynamicEnchantments() {
            return this.dynamicEnchantments;
        }

        public interface Callback {
            void post(Modify event);
        }
    }
}
