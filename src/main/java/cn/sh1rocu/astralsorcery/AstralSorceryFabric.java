package cn.sh1rocu.astralsorcery;

import cn.sh1rocu.astralsorcery.api.event.EntityJoinLevelEvent;
import cn.sh1rocu.astralsorcery.api.extension.ICustomEntityItem;
import cn.sh1rocu.astralsorcery.api.extension.IPathTypeBlock;
import cn.sh1rocu.astralsorcery.util.neoforge.common.BooleanAttribute;
import cn.sh1rocu.astralsorcery.util.neoforge.common.util.LogicalSidedProvider;
import cn.sh1rocu.astralsorcery.util.neoforge.fluids.FluidInteractionRegistry;
import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import hellfirepvp.astralsorcery.common.lib.constants.TagsAS;
import hellfirepvp.astralsorcery.datagen.data.world.AstralWorldGenProvider;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.LandPathNodeTypesRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import static cn.sh1rocu.astralsorcery.api.event.BaseEvent.*;

public class AstralSorceryFabric implements ModInitializer {

    public static final Holder<Attribute> SWIM_SPEED = Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, AstralSorcery.key("swim_speed"),
            new RangedAttribute("attribute.name.astralsorcery.swim_speed", 1.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final Holder<Attribute> CREATIVE_FLIGHT = Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, AstralSorcery.key("creative_flight"),
            new BooleanAttribute("attribute.name.astralsorcery.creative_flight", false).setSyncable(true));

    public static HolderLookup.Provider LOOKUP = VanillaRegistries.createLookup();

    @Override
    public void onInitialize() {
        FluidInteractionRegistry.init();
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            LogicalSidedProvider.setServer(() -> server);
            LOOKUP = server.registryAccess();
        });
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            LogicalSidedProvider.setServer(() -> null);
        });
        EntityJoinLevelEvent.EVENT.register(HIGH, ICustomEntityItem::onEntityJoinWorld);

        AstralSorcery.init();
        BlocksAS.REGISTERED_BLOCKS.forEach(block -> {
            if (block instanceof IPathTypeBlock custom) {
                LandPathNodeTypesRegistry.registerDynamic(block, (state, world, pos, neighbor) ->
                        custom.getBlockPathType(state, world, pos, null));
            }
        });
        AstralWorldGenProvider.generateBiomeModifiers();

        FuelRegistry.INSTANCE.add(TagsAS.Items.INFUSED_WOOD, 300);
        FuelRegistry.INSTANCE.add(BlocksAS.INFUSED_WOOD_SLAB, 150);
        FuelRegistry.INSTANCE.add(BlocksAS.INFUSED_WOOD_STAIRS, 300);

        UseBlockCallback.EVENT.addPhaseOrdering(HIGHEST, HIGH);
        UseBlockCallback.EVENT.addPhaseOrdering(HIGH, Event.DEFAULT_PHASE);
        UseBlockCallback.EVENT.addPhaseOrdering(Event.DEFAULT_PHASE, LOW);
        UseBlockCallback.EVENT.addPhaseOrdering(LOW, LOWEST);

        PlayerBlockBreakEvents.AFTER.addPhaseOrdering(HIGHEST, HIGH);
        PlayerBlockBreakEvents.AFTER.addPhaseOrdering(HIGH, Event.DEFAULT_PHASE);
        PlayerBlockBreakEvents.AFTER.addPhaseOrdering(Event.DEFAULT_PHASE, LOW);
        PlayerBlockBreakEvents.AFTER.addPhaseOrdering(LOW, LOWEST);

        ServerLivingEntityEvents.ALLOW_DAMAGE.addPhaseOrdering(HIGHEST, HIGH);
        ServerLivingEntityEvents.ALLOW_DAMAGE.addPhaseOrdering(HIGH, Event.DEFAULT_PHASE);
        ServerLivingEntityEvents.ALLOW_DAMAGE.addPhaseOrdering(Event.DEFAULT_PHASE, LOW);
        ServerLivingEntityEvents.ALLOW_DAMAGE.addPhaseOrdering(LOW, LOWEST);
    }
}
