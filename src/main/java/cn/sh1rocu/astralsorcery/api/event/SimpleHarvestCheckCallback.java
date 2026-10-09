package cn.sh1rocu.astralsorcery.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A simple impl of NeoForge's PlayerEvent.HarvestCheck
 *
 */
public interface SimpleHarvestCheckCallback {

    Event<SimpleHarvestCheckCallback> EVENT = EventFactory.createArrayBacked(SimpleHarvestCheckCallback.class, callbacks -> (vanillaValue, player, state) -> {
        boolean result = vanillaValue;
        for (SimpleHarvestCheckCallback callback : callbacks) {
            result = callback.canHarvest(result, player, state);
        }
        return result;
    });

    boolean canHarvest(boolean vanillaValue, Player player, BlockState state);
}
