/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib;

import cn.sh1rocu.astralsorcery.util.neoforge.fluids.crafing.*;
import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.artifact.ArtifactCondition;
import hellfirepvp.astralsorcery.common.artifact.ArtifactEffect;
import hellfirepvp.astralsorcery.common.artifact.ArtifactType;
import hellfirepvp.astralsorcery.common.constellation.BaseConstellation;
import hellfirepvp.astralsorcery.common.crystal.CrystalProperty;
import hellfirepvp.astralsorcery.common.data.sync.SyncData;
import hellfirepvp.astralsorcery.common.focal.node.FocalPointNode;
import hellfirepvp.astralsorcery.common.lumen.Lumen;
import hellfirepvp.astralsorcery.common.lumen.binding.effect.LumenBindingEffect;
import hellfirepvp.astralsorcery.common.lumen.binding.usage.LumenBindingUsage;
import hellfirepvp.astralsorcery.common.perk.PerkAttributeLimiter;
import hellfirepvp.astralsorcery.common.perk.convert.PerkAttributeConverter;
import hellfirepvp.astralsorcery.common.perk.modifier.PerkAttributeModifier;
import hellfirepvp.astralsorcery.common.perk.reader.PerkAttributeTypeReader;
import hellfirepvp.astralsorcery.common.perk.source.ModifierSourceProvider;
import hellfirepvp.astralsorcery.common.perk.tree.PerkDataType;
import hellfirepvp.astralsorcery.common.perk.tree.PerkType;
import hellfirepvp.astralsorcery.common.perk.tree.requirement.PerkRequirement;
import hellfirepvp.astralsorcery.common.perk.type.base.PerkAttributeType;
import hellfirepvp.astralsorcery.common.recipe.altar.effect.AltarEffect;
import hellfirepvp.astralsorcery.common.recipe.altar.output.AltarRecipeOutputModifier;
import hellfirepvp.astralsorcery.common.recipe.liquid.interaction.result.LiquidInteractionResult;
import hellfirepvp.astralsorcery.common.recipe.liquid.output.LiquidStarlightRecipeOutputModifier;
import hellfirepvp.astralsorcery.common.research.condition.ResearchNodeCondition;
import hellfirepvp.astralsorcery.common.research.tome.TomePage;
import hellfirepvp.astralsorcery.common.starlight.api.provider.TransmissionNodeProvider;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import hellfirepvp.astralsorcery.common.focal.node.FocalPointNode;
import hellfirepvp.astralsorcery.common.worldgen.structure.marker.StructureMarkerReplacement;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: RegistriesAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class RegistriesAS {

    public static void init() {

    }

    /// FluidIngredientType
    public static final ResourceKey<Registry<FluidIngredientType<?>>> FLUID_INGREDIENT_TYPE_KEY = registryKey("fluid_ingredient_type");
    public static final Registry<FluidIngredientType<?>> FLUID_INGREDIENT_TYPES = FabricRegistryBuilder.createSimple(FLUID_INGREDIENT_TYPE_KEY)
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public static final FluidIngredientType<EmptyFluidIngredient> EMPTY_FLUID_INGREDIENT_TYPE = register("empty", new FluidIngredientType<>(EmptyFluidIngredient.CODEC));
    public static final FluidIngredientType<SingleFluidIngredient> SINGLE_FLUID_INGREDIENT_TYPE = register("single", new FluidIngredientType<>(SingleFluidIngredient.CODEC));
    public static final FluidIngredientType<TagFluidIngredient> TAG_FLUID_INGREDIENT_TYPE = register("tag", new FluidIngredientType<>(TagFluidIngredient.CODEC));
    public static final FluidIngredientType<CompoundFluidIngredient> COMPOUND_FLUID_INGREDIENT_TYPE = register("compound", new FluidIngredientType<>(CompoundFluidIngredient.CODEC));

    private static <T extends FluidIngredient> FluidIngredientType<T> register(String name, FluidIngredientType<T> type) {
        return Registry.register(FLUID_INGREDIENT_TYPES, AstralSorcery.key(name), type);
    }

    ///

    public static final ResourceKey<Registry<BaseConstellation>> KEY_CONSTELLATIONS = registryKey("constellations");
    public static final ResourceKey<Registry<Lumen>> KEY_LUMEN = registryKey("lumen");
    public static final ResourceKey<Registry<CrystalProperty>> KEY_CRYSTAL_PROPERTIES = registryKey("crystal_properties");
    public static final ResourceKey<Registry<TransmissionNodeProvider<?>>> KEY_TRANSMISSION_NODES = registryKey("transmission_nodes");
    public static final ResourceKey<Registry<AltarEffect>> KEY_ALTAR_EFFECTS = registryKey("altar_effects");
    public static final ResourceKey<Registry<PerkType<?>>> KEY_PERK_TYPES = registryKey("perk_types");
    public static final ResourceKey<Registry<PerkDataType<?>>> KEY_PERK_DATA_TYPES = registryKey("perk_data_types");
    public static final ResourceKey<Registry<PerkAttributeTypeReader.Type>> KEY_PERK_ATTRIBUTE_TYPE_READERS = registryKey("perk_attribute_type_readers");
    public static final ResourceKey<Registry<PerkAttributeType>> KEY_PERK_ATTRIBUTE_TYPES = registryKey("perk_attribute_types");
    public static final ResourceKey<Registry<PerkAttributeConverter>> KEY_PERK_CONVERTERS = registryKey("perk_converters");
    public static final ResourceKey<Registry<PerkAttributeModifier>> KEY_PERK_CUSTOM_MODIFIERS = registryKey("perk_custom_modifiers");
    public static final ResourceKey<Registry<ModifierSourceProvider<?>>> KEY_PERK_MODIFIER_SOURCES = registryKey("perk_modifier_sources");
    public static final ResourceKey<Registry<PerkAttributeLimiter.Limit>> KEY_PERK_ATTRIBUTE_LIMITS = registryKey("perk_attribute_limits");

    public static final ResourceKey<Registry<TomePage.TomePageType<?>>> KEY_TOME_PAGE_TYPES = registryKey("tome_page_types");
    public static final ResourceKey<Registry<SyncData.Type<?, ?, ?, ?>>> KEY_SYNC_DATA_TYPES = registryKey("sync_data_types");
    public static final ResourceKey<Registry<FocalPointNode.Type<?>>> KEY_FOCAL_NODE_TYPES = registryKey("focal_node_types");
    public static final ResourceKey<Registry<ResearchNodeCondition.Type<?>>> KEY_RESEARCH_NODE_CONDITION_TYPES = registryKey("research_node_condition_types");
    public static final ResourceKey<Registry<AltarRecipeOutputModifier.Type<?>>> KEY_ALTAR_OUTPUT_MODIFIER_TYPES = registryKey("altar_output_modifier_types");
    public static final ResourceKey<Registry<LiquidStarlightRecipeOutputModifier.Type<?>>> KEY_LIQUID_STARLIGHT_OUTPUT_MODIFIER_TYPES = registryKey("liquid_starlight_output_modifier_types");
    public static final ResourceKey<Registry<LiquidInteractionResult.Type<?>>> KEY_LIQUID_INTERACTION_RESULT_TYPES = registryKey("liquid_interaction_result_types");
    public static final ResourceKey<Registry<PerkRequirement.Type<?>>> KEY_PERK_REQUIREMENT_TYPES = registryKey("perk_requirement_types");
    public static final ResourceKey<Registry<ArtifactType>> KEY_ARTIFACT_TYPES = registryKey("artifact_types");
    public static final ResourceKey<Registry<ArtifactCondition.Type<?>>> KEY_ARTIFACT_CONDITION_TYPES = registryKey("artifact_condition_types");
    public static final ResourceKey<Registry<ArtifactEffect.Type<?>>> KEY_ARTIFACT_EFFECT_TYPES = registryKey("artifact_effect_types");
    public static final ResourceKey<Registry<LumenBindingUsage.Type<?>>> KEY_LUMEN_BINDING_USAGE_TYPES = registryKey("lumen_binding_usage_types");
    public static final ResourceKey<Registry<LumenBindingEffect.Type<?>>> KEY_LUMEN_BINDING_EFFECT_TYPES = registryKey("lumen_binding_effect_types");
    public static final ResourceKey<Registry<StructureMarkerReplacement.Type<?>>> KEY_STRUCTURE_MARKER_REPLACEMENT_TYPES = registryKey("structure_marker_replacement_types");

    public static final Registry<BaseConstellation> REGISTRY_CONSTELLATIONS = FabricRegistryBuilder.createSimple(KEY_CONSTELLATIONS).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<Lumen> REGISTRY_LUMEN = FabricRegistryBuilder.createSimple(KEY_LUMEN).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<CrystalProperty> REGISTRY_CRYSTAL_PROPERTIES = FabricRegistryBuilder.createSimple(KEY_CRYSTAL_PROPERTIES).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<TransmissionNodeProvider<?>> REGISTRY_TRANSMISSION_NODES = FabricRegistryBuilder.createSimple(KEY_TRANSMISSION_NODES).buildAndRegister();
    public static final Registry<AltarEffect> REGISTRY_ALTAR_EFFECTS = FabricRegistryBuilder.createSimple(KEY_ALTAR_EFFECTS).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<PerkType<?>> REGISTRY_PERK_TYPES = FabricRegistryBuilder.createSimple(KEY_PERK_TYPES).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<PerkDataType<?>> REGISTRY_PERK_DATA_TYPES = FabricRegistryBuilder.createSimple(KEY_PERK_DATA_TYPES).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<PerkAttributeTypeReader.Type> REGISTRY_PERK_ATTRIBUTE_TYPE_READERS = FabricRegistryBuilder.createSimple(KEY_PERK_ATTRIBUTE_TYPE_READERS).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<PerkAttributeType> REGISTRY_PERK_ATTRIBUTE_TYPES = FabricRegistryBuilder.createSimple(KEY_PERK_ATTRIBUTE_TYPES).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<PerkAttributeConverter> REGISTRY_PERK_CONVERTERS = FabricRegistryBuilder.createSimple(KEY_PERK_CONVERTERS).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<PerkAttributeModifier> REGISTRY_PERK_CUSTOM_MODIFIERS = FabricRegistryBuilder.createSimple(KEY_PERK_CUSTOM_MODIFIERS).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<ModifierSourceProvider<?>> REGISTRY_PERK_MODIFIER_SOURCES = FabricRegistryBuilder.createSimple(KEY_PERK_MODIFIER_SOURCES).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<PerkAttributeLimiter.Limit> REGISTRY_PERK_ATTRIBUTE_LIMITS = FabricRegistryBuilder.createSimple(KEY_PERK_ATTRIBUTE_LIMITS).attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public static final Registry<TomePage.TomePageType<?>> REGISTRY_TOME_PAGE_TYPES = FabricRegistryBuilder.createSimple(KEY_TOME_PAGE_TYPES).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<SyncData.Type<?, ?, ?, ?>> REGISTRY_SYNC_DATA_TYPES = FabricRegistryBuilder.createSimple(KEY_SYNC_DATA_TYPES).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<FocalPointNode.Type<?>> REGISTRY_FOCAL_NODE_TYPES = FabricRegistryBuilder.createSimple(KEY_FOCAL_NODE_TYPES).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<ResearchNodeCondition.Type<?>> REGISTRY_RESEARCH_NODE_CONDITION_TYPES = FabricRegistryBuilder.createSimple(KEY_RESEARCH_NODE_CONDITION_TYPES).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<AltarRecipeOutputModifier.Type<?>> REGISTRY_ALTAR_OUTPUT_MODIFIER_TYPES = FabricRegistryBuilder.createSimple(KEY_ALTAR_OUTPUT_MODIFIER_TYPES).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<LiquidStarlightRecipeOutputModifier.Type<?>> REGISTRY_LIQUID_STARLIGHT_OUTPUT_MODIFIER_TYPES = FabricRegistryBuilder.createSimple(KEY_LIQUID_STARLIGHT_OUTPUT_MODIFIER_TYPES).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<LiquidInteractionResult.Type<?>> REGISTRY_LIQUID_INTERACTION_RESULT_TYPES = FabricRegistryBuilder.createSimple(KEY_LIQUID_INTERACTION_RESULT_TYPES).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<PerkRequirement.Type<?>> REGISTRY_PERK_REQUIREMENT_TYPES = FabricRegistryBuilder.createSimple(KEY_PERK_REQUIREMENT_TYPES).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<ArtifactType> REGISTRY_ARTIFACT_TYPES = FabricRegistryBuilder.createSimple(KEY_ARTIFACT_TYPES).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<ArtifactCondition.Type<?>> REGISTRY_ARTIFACT_CONDITION_TYPES = FabricRegistryBuilder.createSimple(KEY_ARTIFACT_CONDITION_TYPES).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<ArtifactEffect.Type<?>> REGISTRY_ARTIFACT_EFFECT_TYPES = FabricRegistryBuilder.createSimple(KEY_ARTIFACT_EFFECT_TYPES).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<LumenBindingUsage.Type<?>> REGISTRY_LUMEN_BINDING_USAGE_TYPES = FabricRegistryBuilder.createSimple(KEY_LUMEN_BINDING_USAGE_TYPES).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<LumenBindingEffect.Type<?>> REGISTRY_LUMEN_BINDING_EFFECT_TYPES = FabricRegistryBuilder.createSimple(KEY_LUMEN_BINDING_EFFECT_TYPES).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Registry<StructureMarkerReplacement.Type<?>> REGISTRY_STRUCTURE_MARKER_REPLACEMENT_TYPES = FabricRegistryBuilder.createSimple(KEY_STRUCTURE_MARKER_REPLACEMENT_TYPES).buildAndRegister();

    private static <T> ResourceKey<Registry<T>> registryKey(String name) {
        return ResourceKey.createRegistryKey(AstralSorcery.key(name));
    }
}
