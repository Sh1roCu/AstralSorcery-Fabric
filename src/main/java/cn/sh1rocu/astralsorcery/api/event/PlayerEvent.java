package cn.sh1rocu.astralsorcery.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PlayerEvent extends LivingEvent {
    private final Player player;

    public PlayerEvent(Player player) {
        super(player);
        this.player = player;
    }

    @Override
    public Player getEntity() {
        return player;
    }

    public static class BreakSpeed extends PlayerEvent implements ICancellableEvent {
        private static final BlockPos LEGACY_UNKNOWN = new BlockPos(0, -1, 0);
        private final BlockState state;
        private final float originalSpeed;
        private float newSpeed = 0.0f;
        private final Optional<BlockPos> pos;

        public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
            for (Callback callback : callbacks) {
                callback.post(event);
            }
        });

        public BreakSpeed(Player player, BlockState state, float original, @Nullable BlockPos pos) {
            super(player);
            this.state = state;
            this.originalSpeed = original;
            this.setNewSpeed(original);
            this.pos = Optional.ofNullable(pos);
        }

        public BlockState getState() {
            return state;
        }

        public float getOriginalSpeed() {
            return originalSpeed;
        }

        public float getNewSpeed() {
            return newSpeed;
        }

        public void setNewSpeed(float newSpeed) {
            this.newSpeed = newSpeed;
        }

        public Optional<BlockPos> getPosition() {
            return this.pos;
        }

        public interface Callback {
            void post(BreakSpeed breakSpeed);
        }
    }
}