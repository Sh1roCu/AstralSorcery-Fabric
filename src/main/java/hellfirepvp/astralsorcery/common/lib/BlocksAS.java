/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib;

import com.google.common.collect.Sets;
import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.block.*;
import hellfirepvp.astralsorcery.common.block.ore.AquamarineShaleOreBlock;
import hellfirepvp.astralsorcery.common.block.ore.RawStarmetalBlock;
import hellfirepvp.astralsorcery.common.block.ore.RockCrystalOreBlock;
import hellfirepvp.astralsorcery.common.block.ore.StarmetalOreBlock;
import hellfirepvp.astralsorcery.common.block.tile.*;
import hellfirepvp.astralsorcery.common.tile.TileAltar;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.Set;
import java.util.function.Supplier;

import static hellfirepvp.astralsorcery.common.lib.constants.PropertiesAS.Block.*;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: BlocksAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class BlocksAS {

    public static final Set<Block> REGISTERED_BLOCKS = Sets.newHashSet();

    public static void init() {

    }

    //----------------------------- DECORATIVE BLOCKS ------------------------------//

    public static final Block MARBLE_ARCH = registerSimple("marble_arch", MARBLE);
    public static final Block MARBLE_BRICKS = registerSimple("marble_bricks", MARBLE);
    public static final Block MARBLE_CHISELED = registerSimple("marble_chiseled", MARBLE);
    public static final Block MARBLE_ENGRAVED = registerSimple("marble_engraved", MARBLE);
    public static final PillarBlock MARBLE_PILLAR = register("marble_pillar", PillarBlock.make(MARBLE));
    public static final Block MARBLE_RAW = registerSimple("marble_raw", MARBLE);
    public static final Block MARBLE_RUNED = registerSimple("marble_runed", MARBLE);
    public static final SlabBlock MARBLE_SLAB = register("marble_slab", makeSlab(MARBLE));
    public static final StairBlock MARBLE_STAIRS = register("marble_stairs", makeStairs(MARBLE_BRICKS, MARBLE));

    public static final Block SOOTY_MARBLE_ARCH = registerSimple("sooty_marble_arch", SOOTY_MARBLE);
    public static final Block SOOTY_MARBLE_BRICKS = registerSimple("sooty_marble_bricks", SOOTY_MARBLE);
    public static final Block SOOTY_MARBLE_CHISELED = registerSimple("sooty_marble_chiseled", SOOTY_MARBLE);
    public static final Block SOOTY_MARBLE_ENGRAVED = registerSimple("sooty_marble_engraved", SOOTY_MARBLE);
    public static final PillarBlock SOOTY_MARBLE_PILLAR = register("sooty_marble_pillar", PillarBlock.make(SOOTY_MARBLE));
    public static final Block SOOTY_MARBLE_RAW = registerSimple("sooty_marble_raw", SOOTY_MARBLE);
    public static final Block SOOTY_MARBLE_RUNED = registerSimple("sooty_marble_runed", SOOTY_MARBLE);
    public static final SlabBlock SOOTY_MARBLE_SLAB = register("sooty_marble_slab", makeSlab(SOOTY_MARBLE));
    public static final StairBlock SOOTY_MARBLE_STAIRS = register("sooty_marble_stairs", makeStairs(SOOTY_MARBLE_BRICKS, SOOTY_MARBLE));

    public static final Block INFUSED_WOOD_RAW = registerSimple("infused_wood", INFUSED_WOOD);
    public static final Block INFUSED_WOOD_ARCH = registerSimple("infused_wood_arch", INFUSED_WOOD);
    public static final ColumnBlock INFUSED_WOOD_COLUMN = register("infused_wood_column", ColumnBlock.make(INFUSED_WOOD));
    public static final Block INFUSED_WOOD_ENGRAVED = registerSimple("infused_wood_engraved", INFUSED_WOOD);
    public static final Block INFUSED_WOOD_ENRICHED = registerSimple("infused_wood_enriched", INFUSED_WOOD);
    public static final Block INFUSED_WOOD_INFUSED = registerSimple("infused_wood_infused", INFUSED_WOOD);
    public static final Block INFUSED_WOOD_PLANKS = registerSimple("infused_wood_planks", INFUSED_WOOD);
    public static final SlabBlock INFUSED_WOOD_SLAB = register("infused_wood_slab", makeSlab(INFUSED_WOOD));
    public static final StairBlock INFUSED_WOOD_STAIRS = register("infused_wood_stairs", makeStairs(INFUSED_WOOD_PLANKS, INFUSED_WOOD));

    //----------------------------- RESOURCE BLOCKS ------------------------------//

    public static final AquamarineShaleOreBlock AQUAMARINE_SHALE = register("aquamarine_shale", () ->
            new AquamarineShaleOreBlock(UniformInt.of(2, 5), new ColorRGBA(0xDBD3A0), BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).requiresCorrectToolForDrops()));
    public static final RockCrystalOreBlock ROCK_CRYSTAL_ORE = register("rock_crystal_ore", () ->
            new RockCrystalOreBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE)));
    public static final StarmetalOreBlock STARMETAL_ORE = register("starmetal_ore", () ->
            new StarmetalOreBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE).mapColor(MapColor.COLOR_BLUE)));
    public static final RawStarmetalBlock RAW_STARMETAL_BLOCK = register("raw_starmetal_block", () ->
            new RawStarmetalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_BLUE)));

    public static final FlowerBlock GLIMMER_AMARANTH = register("glimmer_amaranth", () ->
            new FlowerBlock(MobEffects.LUCK, 20, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).lightLevel(state -> 6)));
    public static final FlowerBlock HYACINTH = register("hyacinth", () ->
            new FlowerBlock(MobEffects.HEAL, 1, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY)));
    public static final FlowerBlock IRIS = register("iris", () ->
            new FlowerBlock(MobEffects.LEVITATION, 14, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY)));
    public static final FlowerBlock ORCHID = register("orchid", () ->
            new FlowerBlock(MobEffects.DAMAGE_RESISTANCE, 2, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY)));
    public static final FlowerBlock PROTEA = register("protea", () ->
            new FlowerBlock(MobEffects.DIG_SPEED, 5, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY)));
    public static final FlowerBlock THISTLE = register("thistle", () ->
            new FlowerBlock(MobEffects.DAMAGE_BOOST, 12, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY)));

    public static final FlowerPotBlock POTTED_GLIMMER_AMARANTH = register("potted_glimmer_amaranth", () ->
            new PlantableFlowerPotBlock(GLIMMER_AMARANTH, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).lightLevel(state -> 6)));
    public static final FlowerPotBlock POTTED_HYACINTH = register("potted_hyacinth", () ->
            new PlantableFlowerPotBlock(HYACINTH, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final FlowerPotBlock POTTED_IRIS = register("potted_iris", () ->
            new PlantableFlowerPotBlock(IRIS, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final FlowerPotBlock POTTED_ORCHID = register("potted_orchid", () ->
            new PlantableFlowerPotBlock(ORCHID, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final FlowerPotBlock POTTED_PROTEA = register("potted_protea", () ->
            new PlantableFlowerPotBlock(PROTEA, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final FlowerPotBlock POTTED_THISTLE = register("potted_thistle", () ->
            new PlantableFlowerPotBlock(THISTLE, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));

    //----------------------------- TILE BLOCKS ------------------------------//

    public static final AltarBlock ALTAR_ILLUMINATION =
            register("altar_illumination", () -> new AltarBlock(MARBLE_NO_OCCLUSION, () -> TileAltar.AltarType.ILLUMINATION));
    public static final AltarBlock ALTAR_RESONANCE =
            register("altar_resonance", () -> new AltarBlock(MARBLE_NO_OCCLUSION, () -> TileAltar.AltarType.RESONANCE));
    public static final AltarBlock ALTAR_LUMINANCE =
            register("altar_luminance", () -> new AltarBlock(MARBLE_NO_OCCLUSION, () -> TileAltar.AltarType.LUMINANCE));
    public static final AltarBlock ALTAR_RADIANCE =
            register("altar_radiance", () -> new AltarBlock(MARBLE_NO_OCCLUSION, () -> TileAltar.AltarType.RADIANCE));

    public static final FocusRelayBlock FOCUS_RELAY =
            register("focus_relay", () -> new FocusRelayBlock(INFUSED_GLASS_LIT));
    public static final CelestialCrystalClusterBlock CELESTIAL_CRYSTAL_CLUSTER =
            register("celestial_crystal_cluster", () -> new CelestialCrystalClusterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)
                    .strength(0.5F, 10F)
                    .lightLevel(state -> 6)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .sound(SoundsAS.CRYSTAL_SOUND_TYPE)
                    .forceSolidOn()
                    .dynamicShape()));
    public static final GemCrystalClusterBlock GEM_CRYSTAL_CLUSTER =
            register("gem_crystal_cluster", () -> new GemCrystalClusterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)
                    .strength(0.5F, 10F)
                    .lightLevel(state -> 5)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .sound(SoundsAS.CRYSTAL_SOUND_TYPE)
                    .forceSolidOn()
                    .dynamicShape()));
    public static final LumenCrystalClusterBlock LUMEN_CRYSTAL_CLUSTER =
            register("lumen_crystal_cluster", () -> new LumenCrystalClusterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)
                    .strength(0.5F, 10F)
                    .lightLevel(state -> 4)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .sound(SoundsAS.CRYSTAL_SOUND_TYPE)
                    .forceSolidOn()
                    .dynamicShape()));

    public static final LumenArrayBlock LUMEN_ARRAY =
            register("lumen_array", () -> new LumenArrayBlock(MARBLE_NO_OCCLUSION));
    public static final LumenAlchemyArrayBlock LUMEN_ALCHEMY_ARRAY =
            register("lumen_alchemy_array", () -> new LumenAlchemyArrayBlock(MARBLE_NO_OCCLUSION));
    public static final LumenFilamentBlock LUMEN_FILAMENT =
            register("lumen_filament", () -> new LumenFilamentBlock(INFUSED_GLASS_LIT));
    public static final LumenCrystallizerBlock LUMEN_CRYSTALLIZER =
            register("lumen_crystallizer", () -> new LumenCrystallizerBlock(MARBLE_NO_OCCLUSION));
    public static final LightwellBlock LIGHTWELL =
            register("lightwell", () -> new LightwellBlock(MARBLE_NO_OCCLUSION));
    public static final InfuserBlock INFUSER =
            register("infuser", () -> new InfuserBlock(MARBLE_NO_OCCLUSION));
    public static final ChaliceBlock CHALICE =
            register("chalice", () -> new ChaliceBlock(CHALICE_GOLD_MACHINERY));
    public static final AttunementAltarBlock ATTUNEMENT_ALTAR =
            register("attunement_altar", () -> new AttunementAltarBlock(MARBLE_NO_OCCLUSION));
    public static final TranslucentBlock TRANSLUCENT_BLOCK =
            register("translucent_block", () -> new TranslucentBlock(UNBREAKABLE_TRANSLUCENT_BLOCK_LIT));
    public static final TranslucentTreeBlock TRANSLUCENT_TREE =
            register("translucent_tree", () -> new TranslucentTreeBlock(UNBREAKABLE_TRANSLUCENT_BLOCK_LIT));
    public static final FlareLightBlock FLARE_LIGHT =
            register("flare_light", () -> new FlareLightBlock(AIRY_LIT));
    public static final TreeBeaconBlock TREE_BEACON =
            register("tree_beacon", () -> new TreeBeaconBlock(WOODEN_FOLIAGE));
    public static final CelestialGatewayBlock CELESTIAL_GATEWAY =
            register("celestial_gateway", () -> new CelestialGatewayBlock(UNBREAKABLE_GLASS_LIT));

    public static final LensBlock LENS =
            register("lens", () -> new LensBlock(INFUSED_GLASS));
    public static final PrismBlock PRISM =
            register("prism", () -> new PrismBlock(INFUSED_GLASS));
    public static final StarlightFocusCrystalBlock STARLIGHT_FOCUS_ROCK_CRYSTAL =
            register("starlight_focus_rock_crystal", () -> new StarlightFocusCrystalBlock(CRYSTAL_LIT, StarlightFocusCrystalBlock.Type.ROCK_CRYSTAL));
    public static final StarlightFocusCrystalBlock STARLIGHT_FOCUS_CELESTIAL_CRYSTAL =
            register("starlight_focus_celestial_crystal", () -> new StarlightFocusCrystalBlock(CRYSTAL_LIT, StarlightFocusCrystalBlock.Type.CELESTIAL_CRYSTAL));
    public static final StellarFilamentBlock STELLAR_FILAMENT =
            register("stellar_filament", () -> new StellarFilamentBlock(CRYSTAL_LIT));
    public static final CaveIlluminatorBlock CAVE_ILLUMINATOR =
            register("cave_illuminator", () -> new CaveIlluminatorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)
                    .strength(1F, 5F)
                    .sound(SoundType.GLASS)
                    .lightLevel(state -> 10)));

    //----------------------------- WORLDGEN BLOCKS ------------------------------//

    public static final StructureMarkerBlock STRUCTURE_MARKER =
            register("structure_marker", () -> new StructureMarkerBlock(BlockBehaviour.Properties.of()
                    .strength(-1.0F, 3600000.8F)
                    .noLootTable()
                    .noOcclusion()
                    .isValidSpawn(Blocks::never)
                    .noTerrainParticles()
                    .pushReaction(PushReaction.BLOCK)));

    //----------------------------- REGISTER ------------------------------//

    private static Supplier<SlabBlock> makeSlab(BlockBehaviour.Properties properties) {
        return () -> new SlabBlock(properties);
    }

    private static Supplier<StairBlock> makeStairs(Block baseBlock, BlockBehaviour.Properties properties) {
        return () -> new StairBlock(baseBlock.defaultBlockState(), properties);
    }

    private static Block registerSimple(String name, BlockBehaviour.Properties props) {
        return register(name, () -> new Block(props));
    }

    public static <T extends Block> T register(String name, Supplier<T> supplier) {
        T block = supplier.get();
        REGISTERED_BLOCKS.add(block);
        return Registry.register(BuiltInRegistries.BLOCK, AstralSorcery.key(name), block);
    }
}
