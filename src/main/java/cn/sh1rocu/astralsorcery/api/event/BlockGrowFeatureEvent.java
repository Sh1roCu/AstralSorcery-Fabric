package cn.sh1rocu.astralsorcery.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;

public class BlockGrowFeatureEvent extends LevelEvent implements ICancellableEvent {
    private final RandomSource rand;
    private final BlockPos pos;
//    @Nullable
//    private Holder<ConfiguredFeature<?, ?>> feature;

    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    });

    public BlockGrowFeatureEvent(LevelAccessor level, RandomSource rand, BlockPos pos/*, @Nullable Holder<ConfiguredFeature<?, ?>> feature*/) {
        super(level);
        this.rand = rand;
        this.pos = pos;
        // this.feature = feature;
    }

    public RandomSource getRandom() {
        return this.rand;
    }

    public BlockPos getPos() {
        return pos;
    }

//    @Nullable
//    public Holder<ConfiguredFeature<?, ?>> getFeature() {
//        return feature;
//    }
//
//    public void setFeature(@Nullable Holder<ConfiguredFeature<?, ?>> feature) {
//        this.feature = feature;
//    }
//
//    public void setFeature(ResourceKey<ConfiguredFeature<?, ?>> featureKey) {
//        this.feature = this.getLevel().registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(featureKey).orElse(null);
//    }

    @Override
    public void setCanceled(boolean canceled) {
        ICancellableEvent.super.setCanceled(canceled);
    }

    public interface Callback {
        void post(BlockGrowFeatureEvent event);
    }
}
