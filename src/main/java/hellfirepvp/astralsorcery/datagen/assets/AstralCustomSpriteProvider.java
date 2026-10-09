/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.datagen.assets;

import cn.sh1rocu.astralsorcery.util.neoforge.common.data.SpriteSourceProvider;
import hellfirepvp.astralsorcery.AstralSorcery;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AstralCustomSpriteProvider
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AstralCustomSpriteProvider extends SpriteSourceProvider {

    public AstralCustomSpriteProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void configure(BiConsumer<ResourceLocation, List<SpriteSource>> provider, HolderLookup.Provider lookup) {
        provider.accept(BLOCKS_ATLAS, this.atlas(BLOCKS_ATLAS)
                .addSource(new SingleFile(AstralSorcery.key("model/astrolabe_in_hand"), Optional.empty())).sources());
    }

    @Override
    public @NotNull String getName() {
        return "AstralSorcery Custom Sprites";
    }
}
