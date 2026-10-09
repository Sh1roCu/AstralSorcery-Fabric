/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.starlight.transmission;

import cn.sh1rocu.astralsorcery.api.event.LevelEvent;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: StarlightTransmissionLevelHelper
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class StarlightTransmissionLevelHelper {

    private static final StarlightTransmissionLevelHelper INSTANCE = new StarlightTransmissionLevelHelper();
    private final Map<ResourceKey<Level>, StarlightTransmissionLevelHandler> levelHandlers = new HashMap<>();

    private StarlightTransmissionLevelHelper() {
    }

    public static StarlightTransmissionLevelHelper getInstance() {
        return INSTANCE;
    }

    public void attachEventListeners() {
        ServerTickEvents.END_WORLD_TICK.register(this::onLevelTick);
        LevelEvent.UNLOAD.register(this::onLevelUnload);
        ServerChunkEvents.CHUNK_LOAD.register(this::onChunkLoad);
        ServerChunkEvents.CHUNK_UNLOAD.register(this::onChunkUnload);
    }

    private void onLevelTick(Level level) {
        if (level instanceof ServerLevel sLevel) {
            this.levelHandlers.computeIfAbsent(sLevel.dimension(), StarlightTransmissionLevelHandler::new).tick(sLevel);
        }
    }

    private void onLevelUnload(LevelEvent.Unload event) {
        if (!(event.getLevel() instanceof ServerLevel sLevel)) return;
        ResourceKey<Level> dimKey = sLevel.dimension();
        StarlightTransmissionLevelHandler handle = this.levelHandlers.remove(dimKey);
        if (handle != null) {
            handle.clear();
        }
    }

    private void onChunkLoad(Level level, LevelChunk chunk) {
        if (level instanceof ServerLevel sLevel) {
            this.getHandler(sLevel).ifPresent(handler ->
                    handler.onChunkLoad(chunk.getPos()));
        }
    }

    private void onChunkUnload(Level level, LevelChunk chunk) {
        if (level instanceof ServerLevel sLevel) {
            this.getHandler(sLevel).ifPresent(handler ->
                    handler.onChunkUnload(chunk.getPos()));
        }
    }

    public Optional<StarlightTransmissionLevelHandler> getHandler(ServerLevel level) {
        return Optional.ofNullable(this.levelHandlers.get(level.dimension()));
    }

    public void clearServer() {
        this.levelHandlers.values().forEach(StarlightTransmissionLevelHandler::clear);
        this.levelHandlers.clear();
    }
}
