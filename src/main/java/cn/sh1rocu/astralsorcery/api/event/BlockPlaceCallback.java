package cn.sh1rocu.astralsorcery.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;

public interface BlockPlaceCallback {

    Event<BlockPlaceCallback> EVENT = EventFactory.createArrayBacked(BlockPlaceCallback.class, callbacks ->
            (blockItem, context, placedState) -> {
                for (BlockPlaceCallback callback : callbacks) {
                    callback.onBlockPlace(blockItem, context, placedState);
                }
            });

    void onBlockPlace(BlockItem blockItem, BlockPlaceContext context, BlockState placedState);
}
