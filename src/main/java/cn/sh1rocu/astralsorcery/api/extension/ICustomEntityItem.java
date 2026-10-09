package cn.sh1rocu.astralsorcery.api.extension;

import cn.sh1rocu.astralsorcery.api.event.EntityJoinLevelEvent;
import cn.sh1rocu.astralsorcery.util.neoforge.common.util.LogicalSidedProvider;
import net.fabricmc.api.EnvType;
import net.minecraft.server.TickTask;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public interface ICustomEntityItem {

    boolean hasCustomEntity(ItemStack stack);

    @Nullable
    Entity createEntity(Level level, Entity location, ItemStack stack);

    static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity.getClass().equals(ItemEntity.class)) {
            ItemStack stack = ((ItemEntity) entity).getItem();
            Item item = stack.getItem();
            if (item instanceof ICustomEntityItem custom && custom.hasCustomEntity(stack)) {
                Entity newEntity = custom.createEntity(event.getLevel(), entity, stack);
                if (newEntity != null) {
                    entity.discard();
                    event.setCanceled(true);
                    var executor = LogicalSidedProvider.WORKQUEUE.get(event.getLevel().isClientSide() ? EnvType.CLIENT : EnvType.SERVER);
                    executor.tell(new TickTask(0, () -> event.getLevel().addFreshEntity(newEntity)));
                }
            }
        }
    }
}
