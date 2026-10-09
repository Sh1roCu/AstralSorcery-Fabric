/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.client.lib;

import cn.sh1rocu.astralsorcery.api.extension.client.IClientBlockExtensions;
import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: ClientExtensionsAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class ClientExtensionsAS {

    private static final Map<Block, IClientBlockExtensions> BLOCK_EXTENSIONS = new Reference2ObjectOpenHashMap<>();

    public static void registerExtensions() {
        registerBlock(BlockExtensionNone.INSTANCE,
                BlocksAS.TRANSLUCENT_BLOCK, BlocksAS.TRANSLUCENT_TREE, BlocksAS.FLARE_LIGHT);
    }

    @Nullable
    public static IClientBlockExtensions of(Block block){
        return BLOCK_EXTENSIONS.get(block);
    }

    private static void registerBlock(IClientBlockExtensions extensions, Block... blocks) {
        register(extensions, BLOCK_EXTENSIONS, blocks);
    }

    @SafeVarargs
    private static <T, E> void register(E extensions, Map<T, E> target, T... objects) {
        if (objects.length == 0) {
            throw new IllegalArgumentException("At least one target must be provided");
        }
        Objects.requireNonNull(extensions, "Extensions must not be null");

        for (T object : objects) {
            Objects.requireNonNull(objects, "Target must not be null");
            E oldExtensions = target.put(object, extensions);
            if (oldExtensions != null) {
                throw new IllegalStateException(String.format(
                        Locale.ROOT,
                        "Duplicate client extensions registration for %s (old: %s, new: %s)",
                        object,
                        oldExtensions,
                        extensions));
            }
        }
    }

    private static class BlockExtensionNone implements IClientBlockExtensions {

        public static final BlockExtensionNone INSTANCE = new BlockExtensionNone();

        private BlockExtensionNone() {
        }

        @Override
        public boolean addHitEffects(BlockState state, Level level, HitResult target, ParticleEngine manager) {
            return true;
        }

        @Override
        public boolean addDestroyEffects(BlockState state, Level Level, BlockPos pos, ParticleEngine manager) {
            return true;
        }
    }
}
