/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.datagen.data.world;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import hellfirepvp.astralsorcery.common.lib.WorldGenAS;
import hellfirepvp.astralsorcery.common.worldgen.feature.RockCrystalOreFeatureConfiguration;
import hellfirepvp.astralsorcery.common.worldgen.placement.RiverbedPlacement;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AstralWorldGenProvider
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AstralWorldGenProvider {

    public static final ResourceKey<ConfiguredFeature<?, ?>> MARBLE_ORE = configured("marble_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ROCK_CRYSTAL_ORE = configured("rock_crystal_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AQUAMARINE_SHALE_ORE = configured("aquamarine_shale_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLIMMER_AMARANTH_FLOWERS = configured("glimmer_amaranth_flowers");

    public static final ResourceKey<PlacedFeature> PLACED_MARBLE_ORE = placed("placed_marble_ore");
    public static final ResourceKey<PlacedFeature> PLACED_ROCK_CRYSTAL_ORE = placed("placed_rock_crystal_ore");
    public static final ResourceKey<PlacedFeature> PLACED_AQUAMARINE_SHALE_ORE = placed("placed_aquamarine_shale_ore");
    public static final ResourceKey<PlacedFeature> PLACED_GLIMMER_AMARANTH_FLOWERS = placed("placed_glimmer_amaranth_flowers");

    //Set up the feature to begin with & its configuration
    public static void generateConfiguredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        context.register(MARBLE_ORE, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD), BlocksAS.MARBLE_RAW.defaultBlockState(), 26)));
        context.register(ROCK_CRYSTAL_ORE, new ConfiguredFeature<>(WorldGenAS.ROCK_CRYSTAL_ORE,
                new RockCrystalOreFeatureConfiguration(BlockPredicate.matchesTag(BlockTags.DEEPSLATE_ORE_REPLACEABLES))));
        context.register(AQUAMARINE_SHALE_ORE, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(BlocksAS.AQUAMARINE_SHALE))));
        context.register(GLIMMER_AMARANTH_FLOWERS, new ConfiguredFeature<>(Feature.FLOWER,
                FeatureUtils.simpleRandomPatchConfiguration(56,
                        PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(BlockStateProvider.simple(BlocksAS.GLIMMER_AMARANTH))))));
    }
    //Pair the configured feature with placement rules

    public static void generatePlacedFeatures(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(PLACED_MARBLE_ORE, new PlacedFeature(features.getOrThrow(MARBLE_ORE),
                List.of(
                        CountPlacement.of(UniformInt.of(0, 7)),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(100))
                )));
        context.register(PLACED_ROCK_CRYSTAL_ORE, new PlacedFeature(features.getOrThrow(ROCK_CRYSTAL_ORE),
                List.of(
                        RarityFilter.onAverageOnceEvery(28),
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(4), VerticalAnchor.aboveBottom(7))
                )));
        context.register(PLACED_AQUAMARINE_SHALE_ORE, new PlacedFeature(features.getOrThrow(AQUAMARINE_SHALE_ORE),
                List.of(
                        CountPlacement.of(BiasedToBottomInt.of(4, 8)),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_TOP_SOLID,
                        new RiverbedPlacement()
                )));
        context.register(PLACED_GLIMMER_AMARANTH_FLOWERS, new PlacedFeature(features.getOrThrow(GLIMMER_AMARANTH_FLOWERS),
                List.of(
                        CountPlacement.of(UniformInt.of(0, 4)),
                        RarityFilter.onAverageOnceEvery(40),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP
                )));
    }

    //Actually add the placed features to biomes to generate
    public static void generateBiomeModifiers() {
        BiomeModifications.addFeature(
                BiomeSelectors.tag(BiomeTags.IS_OVERWORLD),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                PLACED_MARBLE_ORE
        );
        BiomeModifications.addFeature(
                BiomeSelectors.tag(BiomeTags.IS_OVERWORLD),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                PLACED_ROCK_CRYSTAL_ORE
        );
        BiomeModifications.addFeature(
                BiomeSelectors.tag(BiomeTags.IS_OVERWORLD),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                PLACED_AQUAMARINE_SHALE_ORE
        );
        BiomeModifications.addFeature(
                BiomeSelectors.tag(ConventionalBiomeTags.IS_SNOWY),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                PLACED_GLIMMER_AMARANTH_FLOWERS
        );
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> configured(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, AstralSorcery.key(name));
    }

    private static ResourceKey<PlacedFeature> placed(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, AstralSorcery.key(name));
    }
}
