/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.datagen.data.tags;

import hellfirepvp.astralsorcery.common.lib.constants.TagsAS;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;

import java.util.concurrent.CompletableFuture;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AstralBiomeTagsProvider
 * Created by HellFirePvP
 * Date: 07.10.2026 / 14:03
 */
public class AstralBiomeTagsProvider extends FabricTagProvider<Biome> {

    public AstralBiomeTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, Registries.BIOME, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.getOrCreateTagBuilder(TagsAS.Biomes.FOCAL_POINT_BIOMES)
                .forceAddTag(ConventionalBiomeTags.IS_OVERWORLD);
        this.getOrCreateTagBuilder(TagsAS.Biomes.OBLITERATION_BIOMES)
                .forceAddTag(ConventionalBiomeTags.IS_OVERWORLD)
                .forceAddTag(ConventionalBiomeTags.IS_END);
        this.getOrCreateTagBuilder(TagsAS.Biomes.DIG_SITE_BIOMES)
                .forceAddTag(ConventionalBiomeTags.IS_FOREST)
                .forceAddTag(ConventionalBiomeTags.IS_MOUNTAIN)
                .forceAddTag(ConventionalBiomeTags.IS_SAVANNA)
                .forceAddTag(ConventionalBiomeTags.IS_DESERT);
        this.getOrCreateTagBuilder(TagsAS.Biomes.MOON_DIAL_BIOMES)
                .forceAddTag(ConventionalBiomeTags.IS_DESERT);
        this.getOrCreateTagBuilder(TagsAS.Biomes.COLUMN_BIOMES)
                .forceAddTag(ConventionalBiomeTags.IS_OVERWORLD);
        this.getOrCreateTagBuilder(TagsAS.Biomes.ROTUNDA_BIOMES)
                .forceAddTag(ConventionalBiomeTags.IS_MOUNTAIN_PEAK);
    }
}
