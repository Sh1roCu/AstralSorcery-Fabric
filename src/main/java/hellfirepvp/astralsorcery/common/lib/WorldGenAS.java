/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.worldgen.feature.RockCrystalOreFeature;
import hellfirepvp.astralsorcery.common.worldgen.feature.RockCrystalOreFeatureConfiguration;
import hellfirepvp.astralsorcery.common.worldgen.placement.RiverbedPlacement;
import hellfirepvp.astralsorcery.common.worldgen.structure.TemplateStructure;
import hellfirepvp.astralsorcery.common.worldgen.structure.processor.FlareLightColorizationProcessor;
import hellfirepvp.astralsorcery.common.worldgen.structure.processor.FlareLightExtinguishProcessor;
import hellfirepvp.astralsorcery.common.worldgen.structure.processor.FocalPointRegisterProcessor;
import hellfirepvp.astralsorcery.common.worldgen.structure.processor.StructureMarkerProcessor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: WorldGenAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class WorldGenAS {

    public static void init() {

    }

    public static final RockCrystalOreFeature ROCK_CRYSTAL_ORE =
            register("rock_crystal_ore", new RockCrystalOreFeature(RockCrystalOreFeatureConfiguration.CODEC));

    public static final PlacementModifierType<RiverbedPlacement> RIVERBED_PLACEMENT =
            register("riverbed_placement", (PlacementModifierType<RiverbedPlacement>) () -> RiverbedPlacement.CODEC);

    public static final StructureType<TemplateStructure> TEMPLATE_STRUCTURE =
            register("template_structure", (StructureType<TemplateStructure>) () -> TemplateStructure.CODEC);

    public static final StructureProcessorType<FocalPointRegisterProcessor> FOCAL_POINT_REGISTER_PROCESSOR =
            register("focal_point_register_processor", (StructureProcessorType<FocalPointRegisterProcessor>) () -> FocalPointRegisterProcessor.CODEC);
    public static final StructureProcessorType<StructureMarkerProcessor> STRUCTURE_MARKER_PROCESSOR =
            register("structure_marker_processor", (StructureProcessorType<StructureMarkerProcessor>) () -> StructureMarkerProcessor.CODEC);
    public static final StructureProcessorType<FlareLightExtinguishProcessor> FLARE_LIGHT_EXTINGUISH_PROCESSOR =
            register("flare_light_extinguish_processor", (StructureProcessorType<FlareLightExtinguishProcessor>) () -> FlareLightExtinguishProcessor.CODEC);
    public static final StructureProcessorType<FlareLightColorizationProcessor> FLARE_LIGHT_COLORIZATION_PROCESSOR =
            register("flare_light_colorization_processor", (StructureProcessorType<FlareLightColorizationProcessor>) () -> FlareLightColorizationProcessor.CODEC);

    private static <T extends Feature<FC>, FC extends FeatureConfiguration> T register(String name, T feature) {
        return Registry.register(BuiltInRegistries.FEATURE, AstralSorcery.key(name), feature);
    }

    private static <P extends PlacementModifier> PlacementModifierType<P> register(String name, PlacementModifierType<P> placement) {
        return Registry.register(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE, AstralSorcery.key(name), placement);
    }

    private static <S extends Structure> StructureType<S> register(String name, StructureType<S> structureType) {
        return Registry.register(BuiltInRegistries.STRUCTURE_TYPE, AstralSorcery.key(name), structureType);
    }

    private static <P extends StructureProcessor> StructureProcessorType<P> register(String name, StructureProcessorType<P> processorType) {
        return Registry.register(BuiltInRegistries.STRUCTURE_PROCESSOR, AstralSorcery.key(name), processorType);
    }
}
