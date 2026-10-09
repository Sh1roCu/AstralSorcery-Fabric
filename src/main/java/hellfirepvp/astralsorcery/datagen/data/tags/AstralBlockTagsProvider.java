/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.datagen.data.tags;

import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import hellfirepvp.astralsorcery.common.lib.constants.TagsAS;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AstralBlockTagsProvider
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AstralBlockTagsProvider extends FabricTagProvider.BlockTagProvider {

    private static AstralBlockTagsProvider INSTANCE;

    public AstralBlockTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
        INSTANCE = this;
    }

    public static AstralBlockTagsProvider getInstance() {
        return INSTANCE;
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.addMiningTags();

        this.getOrCreateTagBuilder(TagsAS.Blocks.MARBLE)
                .add(
                        BlocksAS.MARBLE_ARCH,
                        BlocksAS.MARBLE_BRICKS,
                        BlocksAS.MARBLE_CHISELED,
                        BlocksAS.MARBLE_ENGRAVED,
                        BlocksAS.MARBLE_PILLAR,
                        BlocksAS.MARBLE_RAW,
                        BlocksAS.MARBLE_RUNED
                );
        this.getOrCreateTagBuilder(ConventionalBlockTags.ORES)
                .add(
                        BlocksAS.ROCK_CRYSTAL_ORE,
                        BlocksAS.AQUAMARINE_SHALE,
                        BlocksAS.STARMETAL_ORE
                );
        this.getOrCreateTagBuilder(TagsAS.Blocks.SOOTY_MARBLE)
                .add(
                        BlocksAS.SOOTY_MARBLE_ARCH,
                        BlocksAS.SOOTY_MARBLE_BRICKS,
                        BlocksAS.SOOTY_MARBLE_CHISELED,
                        BlocksAS.SOOTY_MARBLE_ENGRAVED,
                        BlocksAS.SOOTY_MARBLE_PILLAR,
                        BlocksAS.SOOTY_MARBLE_RAW,
                        BlocksAS.SOOTY_MARBLE_RUNED
                );
        this.getOrCreateTagBuilder(TagsAS.Blocks.INFUSED_WOOD)
                .add(
                        BlocksAS.INFUSED_WOOD_RAW,
                        BlocksAS.INFUSED_WOOD_ARCH,
                        BlocksAS.INFUSED_WOOD_COLUMN,
                        BlocksAS.INFUSED_WOOD_ENGRAVED,
                        BlocksAS.INFUSED_WOOD_ENRICHED,
                        BlocksAS.INFUSED_WOOD_INFUSED,
                        BlocksAS.INFUSED_WOOD_PLANKS
                );

        this.getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS)
                .add(
                        BlocksAS.GLIMMER_AMARANTH,
                        BlocksAS.HYACINTH,
                        BlocksAS.IRIS,
                        BlocksAS.ORCHID,
                        BlocksAS.PROTEA,
                        BlocksAS.THISTLE
                );
        this.getOrCreateTagBuilder(BlockTags.FLOWER_POTS)
                .add(
                        BlocksAS.POTTED_GLIMMER_AMARANTH,
                        BlocksAS.POTTED_HYACINTH,
                        BlocksAS.POTTED_IRIS,
                        BlocksAS.POTTED_ORCHID,
                        BlocksAS.POTTED_PROTEA,
                        BlocksAS.POTTED_THISTLE
                );

        this.getOrCreateTagBuilder(TagsAS.Blocks.VALID_TREE_BEACON_BLOCK)
                .forceAddTag(BlockTags.LEAVES)
                .forceAddTag(BlockTags.LOGS)
                .add(
                        Blocks.VINE,
                        Blocks.MANGROVE_ROOTS,
                        Blocks.MUDDY_MANGROVE_ROOTS
                );

        this.getOrCreateTagBuilder(ConventionalBlockTags.RELOCATION_NOT_SUPPORTED)
                .add(
                        BlocksAS.LUMEN_ARRAY,
                        BlocksAS.LUMEN_ALCHEMY_ARRAY,
                        BlocksAS.LUMEN_FILAMENT,
                        BlocksAS.LUMEN_CRYSTALLIZER,
                        BlocksAS.LENS,
                        BlocksAS.PRISM,
                        BlocksAS.STARLIGHT_FOCUS_ROCK_CRYSTAL,
                        BlocksAS.STARLIGHT_FOCUS_CELESTIAL_CRYSTAL,
                        BlocksAS.STELLAR_FILAMENT,
                        BlocksAS.INFUSER,
                        BlocksAS.ATTUNEMENT_ALTAR,
                        BlocksAS.TREE_BEACON,
                        BlocksAS.CELESTIAL_GATEWAY,
                        BlocksAS.ALTAR_ILLUMINATION,
                        BlocksAS.ALTAR_RESONANCE,
                        BlocksAS.ALTAR_LUMINANCE,
                        BlocksAS.ALTAR_RADIANCE
                );
        this.getOrCreateTagBuilder(TagsAS.Blocks.SIMULATED_NON_MOVEABLE)
                .add(
                        BlocksAS.LUMEN_ARRAY,
                        BlocksAS.LUMEN_ALCHEMY_ARRAY,
                        BlocksAS.LUMEN_FILAMENT,
                        BlocksAS.LUMEN_CRYSTALLIZER,
                        BlocksAS.LENS,
                        BlocksAS.PRISM,
                        BlocksAS.STARLIGHT_FOCUS_ROCK_CRYSTAL,
                        BlocksAS.STARLIGHT_FOCUS_CELESTIAL_CRYSTAL,
                        BlocksAS.STELLAR_FILAMENT,
                        BlocksAS.INFUSER,
                        BlocksAS.ATTUNEMENT_ALTAR,
                        BlocksAS.TREE_BEACON,
                        BlocksAS.CELESTIAL_GATEWAY,
                        BlocksAS.ALTAR_ILLUMINATION,
                        BlocksAS.ALTAR_RESONANCE,
                        BlocksAS.ALTAR_LUMINANCE,
                        BlocksAS.ALTAR_RADIANCE
                );
    }

    private void addMiningTags() {
        this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(
                        BlocksAS.MARBLE_ARCH,
                        BlocksAS.MARBLE_BRICKS,
                        BlocksAS.MARBLE_CHISELED,
                        BlocksAS.MARBLE_ENGRAVED,
                        BlocksAS.MARBLE_PILLAR,
                        BlocksAS.MARBLE_RAW,
                        BlocksAS.MARBLE_RUNED,
                        BlocksAS.MARBLE_SLAB,
                        BlocksAS.MARBLE_STAIRS,
                        BlocksAS.SOOTY_MARBLE_ARCH,
                        BlocksAS.SOOTY_MARBLE_BRICKS,
                        BlocksAS.SOOTY_MARBLE_CHISELED,
                        BlocksAS.SOOTY_MARBLE_ENGRAVED,
                        BlocksAS.SOOTY_MARBLE_PILLAR,
                        BlocksAS.SOOTY_MARBLE_RAW,
                        BlocksAS.SOOTY_MARBLE_RUNED,
                        BlocksAS.SOOTY_MARBLE_SLAB,
                        BlocksAS.SOOTY_MARBLE_STAIRS,

                        BlocksAS.ROCK_CRYSTAL_ORE,
                        BlocksAS.STARMETAL_ORE,
                        BlocksAS.RAW_STARMETAL_BLOCK,

                        BlocksAS.ALTAR_ILLUMINATION,
                        BlocksAS.ALTAR_RESONANCE,
                        BlocksAS.ALTAR_LUMINANCE,
                        BlocksAS.ALTAR_RADIANCE,
                        BlocksAS.FOCUS_RELAY,
                        BlocksAS.CELESTIAL_CRYSTAL_CLUSTER,
                        BlocksAS.GEM_CRYSTAL_CLUSTER,
                        BlocksAS.LUMEN_CRYSTAL_CLUSTER,
                        BlocksAS.LUMEN_ARRAY,
                        BlocksAS.LUMEN_ALCHEMY_ARRAY,
                        BlocksAS.LUMEN_CRYSTALLIZER,
                        BlocksAS.LIGHTWELL,
                        BlocksAS.INFUSER,
                        BlocksAS.CHALICE,
                        BlocksAS.ATTUNEMENT_ALTAR,
                        BlocksAS.STELLAR_FILAMENT,
                        BlocksAS.CELESTIAL_GATEWAY,
                        BlocksAS.CAVE_ILLUMINATOR
                );
        this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE)
                .add(
                        BlocksAS.INFUSED_WOOD_RAW,
                        BlocksAS.INFUSED_WOOD_ARCH,
                        BlocksAS.INFUSED_WOOD_COLUMN,
                        BlocksAS.INFUSED_WOOD_ENGRAVED,
                        BlocksAS.INFUSED_WOOD_ENRICHED,
                        BlocksAS.INFUSED_WOOD_INFUSED,
                        BlocksAS.INFUSED_WOOD_PLANKS,
                        BlocksAS.INFUSED_WOOD_SLAB,
                        BlocksAS.INFUSED_WOOD_STAIRS,

                        BlocksAS.TREE_BEACON
                );
        this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(
                        BlocksAS.AQUAMARINE_SHALE
                );

        this.getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(
                        BlocksAS.MARBLE_ARCH,
                        BlocksAS.MARBLE_BRICKS,
                        BlocksAS.MARBLE_CHISELED,
                        BlocksAS.MARBLE_ENGRAVED,
                        BlocksAS.MARBLE_PILLAR,
                        BlocksAS.MARBLE_RAW,
                        BlocksAS.MARBLE_RUNED,
                        BlocksAS.MARBLE_SLAB,
                        BlocksAS.MARBLE_STAIRS,
                        BlocksAS.SOOTY_MARBLE_ARCH,
                        BlocksAS.SOOTY_MARBLE_BRICKS,
                        BlocksAS.SOOTY_MARBLE_CHISELED,
                        BlocksAS.SOOTY_MARBLE_ENGRAVED,
                        BlocksAS.SOOTY_MARBLE_PILLAR,
                        BlocksAS.SOOTY_MARBLE_RAW,
                        BlocksAS.SOOTY_MARBLE_RUNED,
                        BlocksAS.SOOTY_MARBLE_SLAB,
                        BlocksAS.SOOTY_MARBLE_STAIRS,

                        BlocksAS.AQUAMARINE_SHALE,
                        BlocksAS.FOCUS_RELAY,
                        BlocksAS.STELLAR_FILAMENT,
                        BlocksAS.CAVE_ILLUMINATOR
                );
        this.getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(
                        BlocksAS.ROCK_CRYSTAL_ORE,
                        BlocksAS.STARMETAL_ORE,
                        BlocksAS.RAW_STARMETAL_BLOCK,

                        BlocksAS.CELESTIAL_CRYSTAL_CLUSTER,
                        BlocksAS.GEM_CRYSTAL_CLUSTER,
                        BlocksAS.LUMEN_CRYSTAL_CLUSTER,
                        BlocksAS.ALTAR_ILLUMINATION,
                        BlocksAS.ALTAR_RESONANCE,
                        BlocksAS.ALTAR_LUMINANCE,
                        BlocksAS.ALTAR_RADIANCE,
                        BlocksAS.LUMEN_ARRAY,
                        BlocksAS.LUMEN_ALCHEMY_ARRAY,
                        BlocksAS.LUMEN_CRYSTALLIZER,
                        BlocksAS.LIGHTWELL,
                        BlocksAS.INFUSER,
                        BlocksAS.CHALICE,
                        BlocksAS.ATTUNEMENT_ALTAR,
                        BlocksAS.TREE_BEACON,
                        BlocksAS.CELESTIAL_GATEWAY
                );
    }
}
