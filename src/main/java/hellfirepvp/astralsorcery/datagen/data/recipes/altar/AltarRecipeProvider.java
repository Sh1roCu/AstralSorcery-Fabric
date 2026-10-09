/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.datagen.data.recipes.altar;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.EnumExtensions;
import hellfirepvp.astralsorcery.common.component.ArtifactTypeComponent;
import hellfirepvp.astralsorcery.common.component.FlagsComponent;
import hellfirepvp.astralsorcery.common.ingredient.IsEnchantedIngredient;
import hellfirepvp.astralsorcery.common.ingredient.IsFlagSetIngredient;
import hellfirepvp.astralsorcery.common.ingredient.IsStableArtifactIngredient;
import hellfirepvp.astralsorcery.common.lib.*;
import hellfirepvp.astralsorcery.common.lib.constants.TagsAS;
import hellfirepvp.astralsorcery.common.recipe.altar.output.*;
import hellfirepvp.astralsorcery.common.recipe.builder.AltarRecipeBuilder;
import hellfirepvp.astralsorcery.common.research.ResearchTier;
import hellfirepvp.astralsorcery.common.tile.TileAltar;
import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AltarRecipeProvider
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AltarRecipeProvider {

    public static void registerRecipes(RecipeOutput recipeOutput) {
        registerVanillaConversionRecipes(recipeOutput);
        registerIlluminationRecipes(recipeOutput);
        registerResonanceRecipes(recipeOutput);
        registerLuminanceRecipes(recipeOutput);
        registerRadianceRecipes(recipeOutput);
    }

    private static void registerIlluminationRecipes(RecipeOutput recipeOutput) {
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.CRYSTAL_PICKAXE)
                .addOutput(ItemsAS.CRYSTAL_PICKAXE)
                .setGridLines(
                        "CCC",
                        " S ",
                        " S "
                )
                .addInput('C', TagsAS.Items.CRYSTAL)
                .addInput('S', ConventionalItemTags.WOODEN_RODS)
                .addOutputModifier(AltarOutputMergeCrystalProperties.INSTANCE)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.CRYSTAL_SHOVEL)
                .addOutput(ItemsAS.CRYSTAL_SHOVEL)
                .setGridLines(
                        " C ",
                        " S ",
                        " S "
                )
                .addInput('C', TagsAS.Items.CRYSTAL)
                .addInput('S', ConventionalItemTags.WOODEN_RODS)
                .addOutputModifier(AltarOutputMergeCrystalProperties.INSTANCE)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.CRYSTAL_SWORD)
                .addOutput(ItemsAS.CRYSTAL_SWORD)
                .setGridLines(
                        " C ",
                        " C ",
                        " S "
                )
                .addInput('C', TagsAS.Items.CRYSTAL)
                .addInput('S', ConventionalItemTags.WOODEN_RODS)
                .addOutputModifier(AltarOutputMergeCrystalProperties.INSTANCE)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.CRYSTAL_AXE)
                .addOutput(ItemsAS.CRYSTAL_AXE)
                .setGridLines(
                        "CC ",
                        "CS ",
                        " S "
                )
                .addInput('C', TagsAS.Items.CRYSTAL)
                .addInput('S', ConventionalItemTags.WOODEN_RODS)
                .addOutputModifier(AltarOutputMergeCrystalProperties.INSTANCE)
                .save(recipeOutput);

        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_LIGHTWELL)
                .addOutput(ItemsAS.BLOCK_LIGHTWELL)
                .setGridLines(
                        "A A",
                        "RCR",
                        "RRR"
                )
                .addInput('C', TagsAS.Items.CRYSTAL)
                .addInput('A', TagsAS.Items.GEMS_AQUAMARINE)
                .addInput('R', ItemsAS.BLOCK_MARBLE_RUNED)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.CHISEL)
                .addOutput(ItemsAS.CHISEL)
                .setGridLines(
                        " GS",
                        " WG",
                        "W  "
                )
                .addInput('S', ItemsAS.STARMETAL_INGOT)
                .addInput('G', ConventionalItemTags.GOLD_NUGGETS)
                .addInput('W', ItemsAS.BLOCK_INFUSED_WOOD_PLANKS)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_FOCUS_RELAY)
                .addOutput(new ItemStack(ItemsAS.BLOCK_FOCUS_RELAY, 2))
                .setGridLines(
                        "   ",
                        "GLG",
                        "PRP"
                )
                .addInput('L', ItemsAS.GLASS_LENS)
                .addInput('G', ConventionalItemTags.GOLD_NUGGETS)
                .addInput('R', ItemsAS.BLOCK_MARBLE_RUNED)
                .addInput('P', ItemsAS.BLOCK_INFUSED_WOOD_PLANKS)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_ALTAR_RESONANCE)
                .addOutput(ItemsAS.BLOCK_ALTAR_RESONANCE)
                .setGridLines(
                        "P P",
                        "PCP",
                        "RLR"
                )
                .addInput('C', TagsAS.Items.CRYSTAL)
                .addInput('L', FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addInput('P', ItemsAS.BLOCK_MARBLE_PILLAR)
                .addInput('R', ItemsAS.BLOCK_MARBLE_RUNED)
                .addOutputModifier(AltarOutputUpdateResearchTier.create(ResearchTier.RESONANCE))
                .addOutputModifier(AltarOutputSetBlock.builder().add(BlocksAS.ALTAR_RESONANCE).build())
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.ILLUMINATION_POWDER)
                .addOutput(new ItemStack(ItemsAS.ILLUMINATION_POWDER, 16))
                .setGridLines(
                        " G ",
                        "GLG",
                        " A "
                )
                .addInput('A', TagsAS.Items.GEMS_AQUAMARINE)
                .addInput('L', FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addInput('G', ConventionalItemTags.GLOWSTONE_DUSTS)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.NOCTURNAL_POWDER)
                .addOutput(new ItemStack(ItemsAS.NOCTURNAL_POWDER, 4))
                .setGridLines(
                        " I ",
                        "CBC",
                        " I "
                )
                .addInput('I', ItemsAS.ILLUMINATION_POWDER)
                .addInput('C', ItemTags.COALS)
                .addInput('B', ConventionalItemTags.BLACK_DYES)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.VIVID_POWDER)
                .addOutput(new ItemStack(ItemsAS.VIVID_POWDER, 4))
                .setGridLines(
                        " I ",
                        "SAS",
                        "ISI"
                )
                .addInput('I', ItemsAS.ILLUMINATION_POWDER)
                .addInput('S', ConventionalItemTags.SEEDS)
                .addInput('A', TagsAS.Items.GEMS_AQUAMARINE)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_CAVE_ILLUMINATOR)
                .addOutput(ItemsAS.BLOCK_CAVE_ILLUMINATOR)
                .setGridLines(
                        "RIR",
                        "ILI",
                        "RIR"
                )
                .addInput('I', ItemsAS.ILLUMINATION_POWDER)
                .addInput('R', ItemsAS.BLOCK_MARBLE_RUNED)
                .addInput('L', FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.KNOWLEDGE_SHARE)
                .addOutput(ItemsAS.KNOWLEDGE_SHARE)
                .setGridLines(
                        " F ",
                        "SPS",
                        " B "
                )
                .addInput('F', ConventionalItemTags.FEATHERS)
                .addInput('P', ItemsAS.PARCHMENT)
                .addInput('B', ConventionalItemTags.BLACK_DYES)
                .addInput('S', ItemsAS.STARDUST)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.ARCHITECT_WAND)
                .addOutput(ItemsAS.ARCHITECT_WAND)
                .setGridLines(
                        " SP",
                        "PR ",
                        "R  "
                )
                .addInput('P', ConventionalItemTags.PURPLE_DYES)
                .addInput('R', ItemsAS.BLOCK_MARBLE_RUNED)
                .addInput('S', ItemsAS.STARDUST)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.EXCHANGE_WAND)
                .addOutput(ItemsAS.EXCHANGE_WAND)
                .setGridLines(
                        " SD",
                        "DRS",
                        "R  "
                )
                .addInput('D', ConventionalItemTags.DIAMOND_GEMS)
                .addInput('R', ItemsAS.BLOCK_MARBLE_RUNED)
                .addInput('S', ItemsAS.STARDUST)
                .save(recipeOutput);

        Ingredient matchAnyArtifactShard = DefaultCustomIngredients.any(RegistriesAS.REGISTRY_ARTIFACT_TYPES.stream()
                .map(ArtifactTypeComponent::new)
                .map(typeCmp -> {
                    ItemStack stack = new ItemStack(ItemsAS.ARTIFACT_SHARD);
                    stack.set(DataComponentsAS.ARTIFACT_TYPE, typeCmp);
                    return DefaultCustomIngredients.components(stack);
                }).toArray(Ingredient[]::new));
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.AKASHIC_SINGULARITY, "_with_generated_artifact_loot")
                .addOutput(ItemsAS.AKASHIC_SINGULARITY)
                .setGridLines(
                        "S S",
                        " D ",
                        "SAS"
                )
                .addInput('D', ItemsAS.STARDUST)
                .addInput('S', matchAnyArtifactShard)
                .addInput('A', TagsAS.Items.GEMS_AQUAMARINE)
                .addOutputModifier(AltarOutputGenerateArtifactShardLoot.of(4, 12))
                .mayChain()
                .save(recipeOutput);
    }

    private static void registerResonanceRecipes(RecipeOutput recipeOutput) {
        AltarRecipeBuilder.builder(TileAltar.AltarType.RESONANCE)
                .setRecipeId(ItemsAS.BLOCK_INFUSER)
                .addOutput(ItemsAS.BLOCK_INFUSER)
                .setGridLines(
                        " S ",
                        "GLG",
                        "RRR"
                )
                .setRelayLines(
                        "     ",
                        " P P ",
                        " P P ",
                        " PRP ",
                        "     "
                )
                .addInput('S', ItemsAS.STARMETAL_INGOT)
                .addInput('G', ConventionalItemTags.GOLD_INGOTS)
                .addInput('P', ItemsAS.BLOCK_MARBLE_PILLAR)
                .addInput('R', ItemsAS.BLOCK_MARBLE_RUNED)
                .addInput('L', FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.RESONANCE)
                .setRecipeId(ItemsAS.BLOCK_CHALICE)
                .addOutput(ItemsAS.BLOCK_CHALICE)
                .setGridLines(
                        "   ",
                        " R ",
                        "GSG"
                )
                .setRelayLines(
                        "     ",
                        "     ",
                        "     ",
                        " MSM ",
                        "GGGGG"
                )
                .addInput('A', TagsAS.Items.GEMS_AQUAMARINE)
                .addInput('R', ItemsAS.RESONATING_GEM)
                .addInput('S', ItemsAS.BLOCK_SOOTY_MARBLE_RAW)
                .addInput('M', ItemsAS.BLOCK_MARBLE_RUNED)
                .addInput('G', ConventionalItemTags.GOLD_NUGGETS)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.RESONANCE)
                .setRecipeId(ItemsAS.BLOCK_ALTAR_LUMINANCE)
                .addOutput(ItemsAS.BLOCK_ALTAR_LUMINANCE)
                .setGridLines(
                        "   ",
                        "GAG",
                        "RRR"
                )
                .setRelayLines(
                        "G   G",
                        "G   G",
                        "P   P",
                        "P   P",
                        "RRRRR"
                )
                .addInput('A', ItemsAS.RESONATING_GEM)
                .addInput('R', ItemsAS.BLOCK_MARBLE_RUNED)
                .addInput('P', ItemsAS.BLOCK_MARBLE_PILLAR)
                .addInput('G', ConventionalItemTags.GOLD_INGOTS)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addOutputModifier(AltarOutputUpdateResearchTier.create(ResearchTier.LUMINANCE))
                .addOutputModifier(AltarOutputSetBlock.builder().add(BlocksAS.ALTAR_LUMINANCE).build())
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.RESONANCE)
                .setRecipeId(ItemsAS.BLOCK_TREE_BEACON)
                .addOutput(ItemsAS.BLOCK_TREE_BEACON)
                .setGridLines(
                        "G G",
                        " S ",
                        "RRR"
                )
                .setRelayLines(
                        "D   D",
                        "LV VL",
                        "L   L",
                        "LV VL",
                        "D   D"
                )
                .addInput('G', ItemsAS.RESONATING_GEM)
                .addInput('S', ItemTags.SAPLINGS)
                .addInput('R', ItemsAS.BLOCK_MARBLE_RUNED)
                .addInput('V', ItemsAS.VIVID_POWDER)
                .addInput('D', ItemsAS.STARDUST)
                .addInput('L', ItemTags.LEAVES)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.RESONANCE)
                .setRecipeId(ItemsAS.BLOCK_CELESTIAL_GATEWAY)
                .addOutput(ItemsAS.BLOCK_CELESTIAL_GATEWAY)
                .setGridLines(
                        " E ",
                        "LCL",
                        "RRR"
                )
                .setRelayLines(
                        "D   D",
                        " NNN ",
                        " N N ",
                        "G   G",
                        "DG GD"
                )
                .addInput('L', ItemsAS.GLASS_LENS)
                .addInput('C', TagsAS.Items.CRYSTAL)
                .addInput('N', ItemsAS.NOCTURNAL_POWDER)
                .addInput('G', ConventionalItemTags.GOLD_INGOTS)
                .addInput('D', ItemsAS.STARDUST)
                .addInput('R', ItemsAS.BLOCK_MARBLE_RUNED)
                .addInput('E', ConventionalItemTags.ENDER_PEARLS)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.RESONANCE)
                .setRecipeId(ItemsAS.GRAPPLING_WAND)
                .addOutput(ItemsAS.GRAPPLING_WAND)
                .setGridLines(
                        " CB",
                        "BR ",
                        "R  "
                )
                .setRelayLines(
                        "S   S",
                        " S S ",
                        "     ",
                        " S S ",
                        "S   S"
                )
                .addInput('R', ItemsAS.BLOCK_MARBLE_RUNED)
                .addInput('B', Items.BLUE_DYE)
                .addInput('C', TagsAS.Items.CRYSTAL)
                .addInput('S', ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(1000 * 81))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(1000 * 81))
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.RESONANCE)
                .setRecipeId(ItemsAS.BLINK_WAND)
                .addOutput(ItemsAS.BLINK_WAND)
                .setGridLines(
                        " CD",
                        "DR ",
                        "R  "
                )
                .setRelayLines(
                        "G   G",
                        "     ",
                        "     ",
                        "S   S",
                        "S   S"
                )
                .addInput('R', ItemsAS.BLOCK_MARBLE_RUNED)
                .addInput('C', TagsAS.Items.CRYSTAL)
                .addInput('D', ConventionalItemTags.DIAMOND_GEMS)
                .addInput('G', ItemsAS.RESONATING_GEM)
                .addInput('S', ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.RESONANCE)
                .setRecipeId(ItemsAS.BLOCK_ATTUNEMENT_ALTAR)
                .addOutput(ItemsAS.BLOCK_ATTUNEMENT_ALTAR)
                .setGridLines(
                        " C ",
                        "M M",
                        "RFR"
                )
                .setRelayLines(
                        "     ",
                        "S   S",
                        "S   S",
                        "GG GG",
                        "RRRRR"
                )
                .addInput('F', ItemsAS.BLOCK_FOCUS_RELAY)
                .addInput('C', TagsAS.Items.CRYSTAL)
                .addInput('M', ItemsAS.STARMETAL_INGOT)
                .addInput('S', ItemsAS.STARDUST)
                .addInput('G', ConventionalItemTags.GOLD_INGOTS)
                .addInput('R', ItemsAS.BLOCK_MARBLE_RUNED)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.RESONANCE)
                .setRecipeId(ItemsAS.SHIFTING_STAR)
                .addOutput(ItemsAS.SHIFTING_STAR)
                .setGridLines(
                        " S ",
                        "SRS",
                        " S "
                )
                .setRelayLines(
                        "  A  ",
                        "  A  ",
                        "AA AA",
                        "  A  ",
                        "  A  "
                )
                .addInput('R', ItemsAS.RESONATING_GEM)
                .addInput('A', TagsAS.Items.GEMS_AQUAMARINE)
                .addInput('S', ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.ILLUMINATION_POWDER)
                .addRequiredAdditionalInput(1, ItemsAS.ILLUMINATION_POWDER)
                .addRequiredAdditionalInput(1, ItemsAS.ILLUMINATION_POWDER)
                .addRequiredAdditionalInput(1, ItemsAS.ILLUMINATION_POWDER)
                .addRequiredAdditionalInput(1, ItemsAS.ILLUMINATION_POWDER)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.RESONANCE)
                .setRecipeId(ItemsAS.PERK_SEAL)
                .addOutput(new ItemStack(ItemsAS.PERK_SEAL, 8))
                .setGridLines(
                        " N ",
                        "NLN",
                        " N "
                )
                .setRelayLines(
                        "     ",
                        " S S ",
                        "     ",
                        " S S ",
                        "     "
                )
                .addInput('L', ItemsAS.GLASS_LENS)
                .addInput('N', ItemsAS.NOCTURNAL_POWDER)
                .addInput('S', ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.NOCTURNAL_POWDER)
                .addRequiredAdditionalInput(1, ItemsAS.NOCTURNAL_POWDER)
                .addRequiredAdditionalInput(1, ItemsAS.NOCTURNAL_POWDER)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.RESONANCE)
                .setRecipeId(ItemsAS.PERK_NULLIFIER)
                .addOutput(new ItemStack(ItemsAS.PERK_NULLIFIER, 8))
                .setGridLines(
                        " P ",
                        "P P",
                        " P "
                )
                .setRelayLines(
                        "     ",
                        "S   S",
                        " M M ",
                        "S   S",
                        "     "
                )
                .addInput('P', ItemsAS.PERK_SEAL)
                .addInput('M', ItemsAS.STARMETAL_INGOT)
                .addInput('S', ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.NOCTURNAL_POWDER)
                .addRequiredAdditionalInput(1, ItemsAS.NOCTURNAL_POWDER)
                .addRequiredAdditionalInput(1, ItemsAS.NOCTURNAL_POWDER)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.RESONANCE)
                .setRecipeId(ItemsAS.STARDEW)
                .addOutput(ItemsAS.STARDEW)
                .setGridLines(
                        " A ",
                        " G ",
                        "   "
                )
                .addInput('A', TagsAS.Items.GEMS_AQUAMARINE)
                .addInput('G', Items.GLASS_BOTTLE)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.RESONANCE)
                .setRecipeId(ItemsAS.BLOCK_LUMEN_ARRAY)
                .addOutput(ItemsAS.BLOCK_LUMEN_ARRAY)
                .setGridLines(
                        "G G",
                        "L L",
                        "GGG"
                )
                .setRelayLines(
                        "A   A",
                        "P   P",
                        "P   P",
                        "PG GP",
                        "CSSSC"
                )
                .addInput('L', ItemsAS.GLASS_LENS)
                .addInput('G', ConventionalItemTags.GOLD_NUGGETS)
                .addInput('A', TagsAS.Items.GEMS_AQUAMARINE)
                .addInput('P', ItemsAS.BLOCK_MARBLE_PILLAR)
                .addInput('C', ItemsAS.BLOCK_MARBLE_CHISELED)
                .addInput('S', ItemsAS.BLOCK_SOOTY_MARBLE_RAW)
                .addRequiredAdditionalInput(1, TagsAS.Items.GEMS_AQUAMARINE)
                .addRequiredAdditionalInput(1, TagsAS.Items.GEMS_AQUAMARINE)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.RESONANCE)
                .setRecipeId(ItemsAS.BLOCK_LUMEN_FILAMENT)
                .addOutput(new ItemStack(ItemsAS.BLOCK_LUMEN_FILAMENT, 8))
                .setGridLines(
                        "   ",
                        " L ",
                        "GGG"
                )
                .setRelayLines(
                        "     ",
                        "     ",
                        "     ",
                        " GEG ",
                        "GEIEG"
                )
                .addInput('L', ItemsAS.GLASS_LENS)
                .addInput('G', ConventionalItemTags.GOLD_NUGGETS)
                .addInput('E', ItemsAS.BLOCK_INFUSED_WOOD_ENRICHED)
                .addInput('I', ItemsAS.BLOCK_INFUSED_WOOD_INFUSED)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.RESONANCE)
                .setRecipeId(ItemsAS.BLOCK_LUMEN_CRYSTALLIZER)
                .addOutput(ItemsAS.BLOCK_LUMEN_CRYSTALLIZER)
                .setGridLines(
                        "R R",
                        "RCR",
                        " M "
                )
                .setRelayLines(
                        "     ",
                        " SSS ",
                        "     ",
                        "  F  ",
                        "GSSSG"
                )
                .addInput('F', ItemsAS.BLOCK_LUMEN_FILAMENT)
                .addInput('S', ItemsAS.BLOCK_SOOTY_MARBLE_RAW)
                .addInput('G', ConventionalItemTags.GOLD_NUGGETS)
                .addInput('R', ItemsAS.BLOCK_MARBLE_RUNED)
                .addInput('C', TagsAS.Items.CRYSTAL)
                .addInput('M', ItemsAS.STARMETAL_INGOT)
                .save(recipeOutput);

        /*AltarRecipeBuilder.builder(TileAltar.AltarType.RESONANCE)
                .setRecipeId(ItemsAS.BLOCK_INFUSER)
                .addOutput(ItemsAS.BLOCK_INFUSER)
                .setGridLines(
                        "   ",
                        "   ",
                        "   "
                )
                .setRelayLines(
                        "     ",
                        "     ",
                        "     ",
                        "     ",
                        "     "
                )
                .save(recipeOutput);*/
    }

    private static void registerLuminanceRecipes(RecipeOutput recipeOutput) {
        AltarRecipeBuilder.builder(TileAltar.AltarType.LUMINANCE)
                .setRecipeId(ItemsAS.LINKING_TOOL)
                .addOutput(ItemsAS.LINKING_TOOL)
                .setGridLines(
                        " SC",
                        " PS",
                        "P  "
                )
                .setRelayLines(
                        "I   I",
                        "     ",
                        "     ",
                        "A   A",
                        "I   I"
                )
                .addInput('C', TagsAS.Items.CRYSTAL)
                .addInput('S', ItemsAS.STARDUST)
                .addInput('P', ItemsAS.BLOCK_INFUSED_WOOD_RAW)
                .addInput('I', ItemsAS.ILLUMINATION_POWDER)
                .addInput('A', TagsAS.Items.GEMS_AQUAMARINE)
                .addRequiredAdditionalInput(1, ItemsAS.RESONATING_GEM)
                .addRequiredAdditionalInput(1, ItemsAS.RESONATING_GEM)
                .addRequiredAdditionalInput(1, ItemsAS.RESONATING_GEM)
                .addFocusConstellation(ConstellationsAS.AEVITAS)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.LUMINANCE)
                .setRecipeId(ItemsAS.BLOCK_STARLIGHT_FOCUS_ROCK_CRYSTAL)
                .addOutput(ItemsAS.BLOCK_STARLIGHT_FOCUS_ROCK_CRYSTAL)
                .setGridLines(
                        "   ",
                        " C ",
                        "   "
                )
                .setRelayLines(
                        "R   R",
                        " I I ",
                        "     ",
                        " I I ",
                        "R   R"
                )
                .addInput('C', ItemsAS.ATTUNED_ROCK_CRYSTAL)
                .addInput('I', ItemsAS.ILLUMINATION_POWDER)
                .addInput('R', ItemsAS.RESONATING_GEM)
                .addOutputModifier(AltarOutputCopyDataComponents.of(AltarOutputReplaceWithInput.SlotType.ALTAR_GRID, 4,
                        DataComponentsAS.CRYSTAL_ATTRIBUTES, DataComponentsAS.ATTUNED_CONSTELLATION))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(1000 * 81))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(1000 * 81))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(1000 * 81))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(1000 * 81))
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.LUMINANCE)
                .setRecipeId(ItemsAS.BLOCK_STARLIGHT_FOCUS_CELESTIAL_CRYSTAL)
                .addOutput(ItemsAS.BLOCK_STARLIGHT_FOCUS_CELESTIAL_CRYSTAL)
                .setGridLines(
                        "   ",
                        " C ",
                        "   "
                )
                .setRelayLines(
                        "R   R",
                        " I I ",
                        "     ",
                        " I I ",
                        "R   R"
                )
                .addInput('C', ItemsAS.ATTUNED_CELESTIAL_CRYSTAL)
                .addInput('I', ItemsAS.ILLUMINATION_POWDER)
                .addInput('R', ItemsAS.RESONATING_GEM)
                .addOutputModifier(AltarOutputCopyDataComponents.of(AltarOutputReplaceWithInput.SlotType.ALTAR_GRID, 4,
                        DataComponentsAS.CRYSTAL_ATTRIBUTES, DataComponentsAS.ATTUNED_CONSTELLATION))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(1000 * 81))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(1000 * 81))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(1000 * 81))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(1000 * 81))
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.LUMINANCE)
                .setRecipeId(ItemsAS.BLOCK_LENS)
                .addOutput(ItemsAS.BLOCK_LENS)
                .setGridLines(
                        " L ",
                        "LCL",
                        "WWW"
                )
                .setRelayLines(
                        "     ",
                        "A   A",
                        "A   A",
                        " RRR ",
                        "     "
                )
                .addInput('C', TagsAS.Items.CRYSTAL)
                .addInput('L', ItemsAS.GLASS_LENS)
                .addInput('W', ItemsAS.BLOCK_INFUSED_WOOD_ENGRAVED)
                .addInput('R', ItemsAS.BLOCK_MARBLE_RUNED)
                .addInput('A', TagsAS.Items.GEMS_AQUAMARINE)
                .addOutputModifier(AltarOutputCopyDataComponents.of(AltarOutputReplaceWithInput.SlotType.ALTAR_GRID, 4,
                        DataComponentsAS.CRYSTAL_ATTRIBUTES))
                .addOutputModifier(AltarOutputSetCrystalCount.of(2))
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.LUMINANCE)
                .setRecipeId(ItemsAS.BLOCK_PRISM)
                .addOutput(ItemsAS.BLOCK_PRISM)
                .setGridLines(
                        "L L",
                        "LCL",
                        "L L"
                )
                .setRelayLines(
                        "A A A",
                        "     ",
                        "A   A",
                        " RRR ",
                        "RWWWR"
                )
                .addInput('C', TagsAS.Items.CRYSTAL)
                .addInput('L', ItemsAS.GLASS_LENS)
                .addInput('A', ItemsAS.RESONATING_GEM)
                .addInput('W', ItemsAS.BLOCK_INFUSED_WOOD_ENGRAVED)
                .addInput('R', ItemsAS.BLOCK_MARBLE_RUNED)
                .addOutputModifier(AltarOutputCopyDataComponents.of(AltarOutputReplaceWithInput.SlotType.ALTAR_GRID, 4,
                        DataComponentsAS.CRYSTAL_ATTRIBUTES))
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.LUMINANCE)
                .setRecipeId(ItemsAS.BLOCK_LUMEN_ALCHEMY_ARRAY)
                .addOutput(ItemsAS.BLOCK_LUMEN_ALCHEMY_ARRAY)
                .setGridLines(
                        " C ",
                        "P P",
                        "PHP"
                )
                .setRelayLines(
                        "D   D",
                        "G   G",
                        "G   G",
                        " GGG ",
                        "GSSSG"
                )
                .addInput('C', TagsAS.Items.CRYSTAL)
                .addInput('H', ItemsAS.BLOCK_LUMEN_ARRAY)
                .addInput('P', ItemsAS.BLOCK_MARBLE_PILLAR)
                .addInput('S', ItemsAS.BLOCK_SOOTY_MARBLE_RAW)
                .addInput('G', ConventionalItemTags.GOLD_NUGGETS)
                .addInput('D', ItemsAS.STARDUST)
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.LUMINANCE)
                .setRecipeId(ItemsAS.BLOCK_ALTAR_RADIANCE)
                .addOutput(ItemsAS.BLOCK_ALTAR_RADIANCE)
                .setGridLines(
                        "MLM",
                        "MCM",
                        "M M"
                )
                .setRelayLines(
                        "G   G",
                        "P   P",
                        "P   P",
                        "PSSSP",
                        "PRRRP"
                )
                .addInput('C', TagsAS.Items.CELESTIAL_CRYSTAL)
                .addInput('L', ItemsAS.GLASS_LENS)
                .addInput('M', ItemsAS.STARMETAL_INGOT)
                .addInput('R', ItemsAS.BLOCK_MARBLE_RUNED)
                .addInput('P', ItemsAS.BLOCK_MARBLE_PILLAR)
                .addInput('S', ItemsAS.BLOCK_SOOTY_MARBLE_RAW)
                .addInput('G', ItemsAS.RESONATING_GEM)
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.NOCTURNAL_POWDER)
                .addRequiredAdditionalInput(1, ItemsAS.ILLUMINATION_POWDER)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.NOCTURNAL_POWDER)
                .addRequiredAdditionalInput(1, ItemsAS.ILLUMINATION_POWDER)
                .addRequiredLumen(LumenAS.AEVITAS.stack(200))
                .addRequiredLumen(LumenAS.ARMARA.stack(200))
                .addRequiredLumen(LumenAS.VICIO.stack(200))
                .addRequiredLumen(LumenAS.DISCIDIA.stack(200))
                .addRequiredLumen(LumenAS.EVORSIO.stack(200))
                .setBaseFocusShatterChance(1F)
                .addFocusConstellation(ConstellationsAS.ARMARA)
                .addRequiredStarlight(ConstellationsAS.ARMARA)
                .addOutputModifier(AltarOutputUpdateResearchTier.create(ResearchTier.RADIANCE))
                .addOutputModifier(AltarOutputSetBlock.builder().add(BlocksAS.ALTAR_RADIANCE).build())
                .save(recipeOutput);

        AltarRecipeBuilder.builder(TileAltar.AltarType.LUMINANCE)
                .setRecipeId(ItemsAS.IRIDESCENT_CRYSTAL_PICKAXE)
                .addOutput(ItemsAS.IRIDESCENT_CRYSTAL_PICKAXE)
                .setGridLines(
                        "   ",
                        " T ",
                        "   "
                )
                .setRelayLines(
                        "G   G",
                        "I   I",
                        "     ",
                        "I   I",
                        "G   G"
                )
                .addInput('T', ItemsAS.CRYSTAL_PICKAXE)
                .addInput('G', ItemsAS.RESONATING_GEM)
                .addInput('I', ItemsAS.ILLUMINATION_POWDER)
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addOutputModifier(AltarOutputCopyDataComponents.ofAll(AltarOutputReplaceWithInput.SlotType.ALTAR_GRID, 4))
                .setBaseFocusShatterChance(1F)
                .addFocusConstellation(ConstellationsAS.AEVITAS)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.LUMINANCE)
                .setRecipeId(ItemsAS.IRIDESCENT_CRYSTAL_AXE)
                .addOutput(ItemsAS.IRIDESCENT_CRYSTAL_AXE)
                .setGridLines(
                        "   ",
                        " T ",
                        "   "
                )
                .setRelayLines(
                        "G   G",
                        "I   I",
                        "     ",
                        "I   I",
                        "G   G"
                )
                .addInput('T', ItemsAS.CRYSTAL_AXE)
                .addInput('G', ItemsAS.RESONATING_GEM)
                .addInput('I', ItemsAS.ILLUMINATION_POWDER)
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addOutputModifier(AltarOutputCopyDataComponents.ofAll(AltarOutputReplaceWithInput.SlotType.ALTAR_GRID, 4))
                .setBaseFocusShatterChance(1F)
                .addFocusConstellation(ConstellationsAS.EVORSIO)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.LUMINANCE)
                .setRecipeId(ItemsAS.IRIDESCENT_CRYSTAL_SHOVEL)
                .addOutput(ItemsAS.IRIDESCENT_CRYSTAL_SHOVEL)
                .setGridLines(
                        "   ",
                        " T ",
                        "   "
                )
                .setRelayLines(
                        "G   G",
                        "I   I",
                        "     ",
                        "I   I",
                        "G   G"
                )
                .addInput('T', ItemsAS.CRYSTAL_SHOVEL)
                .addInput('G', ItemsAS.RESONATING_GEM)
                .addInput('I', ItemsAS.ILLUMINATION_POWDER)
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addOutputModifier(AltarOutputCopyDataComponents.ofAll(AltarOutputReplaceWithInput.SlotType.ALTAR_GRID, 4))
                .setBaseFocusShatterChance(1F)
                .addFocusConstellation(ConstellationsAS.VICIO)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.LUMINANCE)
                .setRecipeId(ItemsAS.IRIDESCENT_CRYSTAL_SWORD)
                .addOutput(ItemsAS.IRIDESCENT_CRYSTAL_SWORD)
                .setGridLines(
                        "   ",
                        " T ",
                        "   "
                )
                .setRelayLines(
                        "G   G",
                        "I   I",
                        "     ",
                        "I   I",
                        "G   G"
                )
                .addInput('T', ItemsAS.CRYSTAL_SWORD)
                .addInput('G', ItemsAS.RESONATING_GEM)
                .addInput('I', ItemsAS.ILLUMINATION_POWDER)
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addOutputModifier(AltarOutputCopyDataComponents.ofAll(AltarOutputReplaceWithInput.SlotType.ALTAR_GRID, 4))
                .setBaseFocusShatterChance(1F)
                .addFocusConstellation(ConstellationsAS.DISCIDIA)
                .save(recipeOutput);

        AltarRecipeBuilder.builder(TileAltar.AltarType.LUMINANCE)
                .setRecipeId(ItemsAS.SHIFTING_STAR_AEVITAS)
                .addOutput(ItemsAS.SHIFTING_STAR_AEVITAS)
                .setGridLines(
                        " S ",
                        "STS",
                        " S "
                )
                .setRelayLines(
                        "  O  ",
                        "  L  ",
                        "OM MO",
                        "  L  ",
                        "  O  "
                )
                .addInput('T', ItemsAS.SHIFTING_STAR)
                .addInput('S', ItemsAS.STARDUST)
                .addInput('M', ItemsAS.STARMETAL_INGOT)
                .addInput('L', ConventionalItemTags.SEEDS)
                .addInput('O', ItemTags.SAPLINGS)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.LUMINANCE)
                .setRecipeId(ItemsAS.SHIFTING_STAR_ARMARA)
                .addOutput(ItemsAS.SHIFTING_STAR_ARMARA)
                .setGridLines(
                        " S ",
                        "STS",
                        " S "
                )
                .setRelayLines(
                        "  O  ",
                        "  L  ",
                        "OM MO",
                        "  L  ",
                        "  O  "
                )
                .addInput('T', ItemsAS.SHIFTING_STAR)
                .addInput('S', ItemsAS.STARDUST)
                .addInput('M', ItemsAS.STARMETAL_INGOT)
                .addInput('L', ConventionalItemTags.LEATHERS)
                .addInput('O', ConventionalItemTags.IRON_INGOTS)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.LUMINANCE)
                .setRecipeId(ItemsAS.SHIFTING_STAR_DISCIDIA)
                .addOutput(ItemsAS.SHIFTING_STAR_DISCIDIA)
                .setGridLines(
                        " S ",
                        "STS",
                        " S "
                )
                .setRelayLines(
                        "  O  ",
                        "  L  ",
                        "OM MO",
                        "  L  ",
                        "  O  "
                )
                .addInput('T', ItemsAS.SHIFTING_STAR)
                .addInput('S', ItemsAS.STARDUST)
                .addInput('M', ItemsAS.STARMETAL_INGOT)
                .addInput('L', Items.FLINT)
                .addInput('O', ConventionalItemTags.REDSTONE_DUSTS)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.LUMINANCE)
                .setRecipeId(ItemsAS.SHIFTING_STAR_EVORSIO)
                .addOutput(ItemsAS.SHIFTING_STAR_EVORSIO)
                .setGridLines(
                        " S ",
                        "STS",
                        " S "
                )
                .setRelayLines(
                        "  O  ",
                        "  L  ",
                        "OM MO",
                        "  L  ",
                        "  O  "
                )
                .addInput('T', ItemsAS.SHIFTING_STAR)
                .addInput('S', ItemsAS.STARDUST)
                .addInput('M', ItemsAS.STARMETAL_INGOT)
                .addInput('L', ConventionalItemTags.COBBLESTONES)
                .addInput('O', ConventionalItemTags.GUNPOWDERS)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.LUMINANCE)
                .setRecipeId(ItemsAS.SHIFTING_STAR_VICIO)
                .addOutput(ItemsAS.SHIFTING_STAR_VICIO)
                .setGridLines(
                        " S ",
                        "STS",
                        " S "
                )
                .setRelayLines(
                        "  O  ",
                        "  L  ",
                        "OM MO",
                        "  L  ",
                        "  O  "
                )
                .addInput('T', ItemsAS.SHIFTING_STAR)
                .addInput('S', ItemsAS.STARDUST)
                .addInput('M', ItemsAS.STARMETAL_INGOT)
                .addInput('L', Items.SUGAR)
                .addInput('O', ConventionalItemTags.FEATHERS)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.LUMINANCE)
                .setRecipeId(ItemsAS.ILLUMINATION_WAND)
                .addOutput(ItemsAS.ILLUMINATION_WAND)
                .setGridLines(
                        " IX",
                        "IR ",
                        "R  "
                )
                .setRelayLines(
                        " I I ",
                        "I   I",
                        "A   A",
                        "I   I",
                        " I I "
                )
                .addInput('X', ItemsAS.SHIFTING_STAR)
                .addInput('R', ItemsAS.BLOCK_MARBLE_RUNED)
                .addInput('I', ItemsAS.ILLUMINATION_POWDER)
                .addInput('A', TagsAS.Items.GEMS_AQUAMARINE)
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.ILLUMINATION_POWDER)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.ILLUMINATION_POWDER)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.ILLUMINATION_POWDER)
                .addFocusConstellation(ConstellationsAS.LUCERNA)
                .save(recipeOutput);

        AltarRecipeBuilder.builder(TileAltar.AltarType.LUMINANCE)
                .setRecipeId(ItemsAS.ENCHANTMENT_AMULET, "_initial")
                .addOutput(ItemsAS.ENCHANTMENT_AMULET)
                .setGridLines(
                        " F ",
                        "GEG",
                        " Z "
                )
                .setRelayLines(
                        "S   S",
                        "S   S",
                        "     ",
                        "S   S",
                        "S   S"
                )
                .addInput('E', Items.ENDER_EYE)
                .addInput('F', ConventionalItemTags.STRINGS)
                .addInput('G', ConventionalItemTags.GOLD_INGOTS)
                .addInput('Z', ItemsAS.SHIFTING_STAR)
                .addInput('S', ItemsAS.STARDUST)
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.LUMINANCE)
                .setRecipeId(ItemsAS.ENCHANTMENT_AMULET, "_reroll")
                .addOutput(ItemsAS.ENCHANTMENT_AMULET)
                .setGridLines(
                        "   ",
                        " E ",
                        "   "
                )
                .setRelayLines(
                        "     ",
                        "  R  ",
                        "     ",
                        "SSLSS",
                        "     "
                )
                .addInput('E', ItemsAS.ENCHANTMENT_AMULET)
                .addInput('S', ItemsAS.STARDUST)
                .addInput('R', ItemsAS.RESONATING_GEM)
                .addInput('L', FluidsAS.LIQUID_STARLIGHT.stack(FluidConstants.BUCKET))
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .save(recipeOutput);
    }

    private static void registerRadianceRecipes(RecipeOutput recipeOutput) {
        AltarRecipeBuilder.builder(TileAltar.AltarType.RADIANCE)
                .setRecipeId(ItemsAS.BLOCK_STELLAR_FILAMENT)
                .addOutput(new ItemStack(BlocksAS.STELLAR_FILAMENT, 3))
                .setGridLines(
                        "   ",
                        " C ",
                        "   "
                )
                .setRelayLines(
                        " S S ",
                        "S R S",
                        " R R ",
                        "S R S",
                        " S S "
                )
                .addInput('C', TagsAS.Items.CRYSTAL)
                .addInput('R', ItemsAS.RESONATING_GEM)
                .addInput('S', ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredAdditionalInput(1, ItemsAS.STARDUST)
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(1000 * 81))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(1000 * 81))
                .addFocusConstellation(ConstellationsAS.AEVITAS)
                .addRequiredLumen(LumenAS.VICIO.stack(300))
                .save(recipeOutput);

        AltarRecipeBuilder.builder(TileAltar.AltarType.RADIANCE)
                .setRecipeId(AstralSorcery.key("enchantment_amulet_artifact_enhance"))
                .addOutput(ItemsAS.ENCHANTMENT_AMULET)
                .setGridLines(
                        "   ",
                        " E ",
                        "   "
                )
                .setRelayLines(
                        "  I  ",
                        " GGG ",
                        "IG GI",
                        " GGG ",
                        "  I  "
                )
                .addInput('E', DefaultCustomIngredients.all(
                        Ingredient.of(ItemsAS.ENCHANTMENT_AMULET),
                        IsFlagSetIngredient.of(FlagsComponent.Flag.IS_ARTIFACT_ENHANCED, false).toVanilla()
                ))
                .addInput('S', ItemsAS.STARDUST)
                .addInput('G', ConventionalItemTags.GOLD_NUGGETS)
                .addInput('I', ItemsAS.ILLUMINATION_POWDER)
                .addOutputModifier(AltarOutputReplaceWithInput.of(AltarOutputReplaceWithInput.SlotType.ALTAR_GRID, 4))
                .addOutputModifier(AltarOutputGenerateIdentifier.INSTANCE)
                .addOutputModifier(AltarOutputAddEnchantmentModifier.INSTANCE)
                .addOutputModifier(AltarOutputSetDataComponent.of(DataComponents.RARITY, EnumExtensions.RARITY_ARTIFACT))
                .addOutputModifier(AltarOutputSetFlag.of(FlagsComponent.Flag.IS_ARTIFACT_ENHANCED))
                .addRequiredAdditionalInput(1, IsStableArtifactIngredient.INSTANCE.toVanilla())
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(1000 * 81))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(1000 * 81))
                .addRequiredLumen(LumenAS.PRISMATIC.stack(600))
                .save(recipeOutput);

        AltarRecipeBuilder.builder(TileAltar.AltarType.RADIANCE)
                .setRecipeId(AstralSorcery.key("enchanted_item_artifact_enhance"))
                .addOutput(Items.IRON_PICKAXE)
                .setGridLines(
                        "   ",
                        " E ",
                        "   "
                )
                .setRelayLines(
                        "  S  ",
                        "R   R",
                        "S   S",
                        "     ",
                        "  S  "
                )
                .addInput('E', DefaultCustomIngredients.all(
                        IsEnchantedIngredient.INSTANCE.toVanilla(),
                        IsFlagSetIngredient.of(FlagsComponent.Flag.IS_ARTIFACT_ENHANCED, false).toVanilla()
                ))
                .addInput('S', ItemsAS.STARDUST)
                .addInput('R', ItemsAS.RESONATING_GEM)
                .addOutputModifier(AltarOutputReplaceWithInput.of(AltarOutputReplaceWithInput.SlotType.ALTAR_GRID, 4))
                .addOutputModifier(AltarOutputGenerateIdentifier.INSTANCE)
                .addOutputModifier(AltarOutputIncreaseEnchantments.of(0.1F))
                .addOutputModifier(AltarOutputSetDataComponent.of(DataComponents.RARITY, EnumExtensions.RARITY_ARTIFACT))
                .addOutputModifier(AltarOutputSetFlag.of(FlagsComponent.Flag.IS_ARTIFACT_ENHANCED))
                .addRequiredAdditionalInput(1, IsStableArtifactIngredient.INSTANCE.toVanilla())
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(1000 * 81))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(1000 * 81))
                .addRequiredLumen(LumenAS.PRISMATIC.stack(600))
                .save(recipeOutput);

        AltarRecipeBuilder.builder(TileAltar.AltarType.RADIANCE)
                .setRecipeId(AstralSorcery.key("dynamism_gem_artifact_enhance"))
                .addOutput(ItemsAS.DYNAMISM_GEM_SKY)
                .setGridLines(
                        "   ",
                        " E ",
                        "   "
                )
                .setRelayLines(
                        " R R ",
                        "     ",
                        "     ",
                        "     ",
                        " R R "
                )
                .addInput('R', ItemsAS.RESONATING_GEM)
                .addInput('E', DefaultCustomIngredients.all(
                        Ingredient.of(TagsAS.Items.FUNCTIONAL_PERKTREE_SOCKETABLE_ITEM),
                        IsFlagSetIngredient.of(FlagsComponent.Flag.IS_ARTIFACT_ENHANCED, false).toVanilla()
                ))
                .addOutputModifier(AltarOutputReplaceWithInput.of(AltarOutputReplaceWithInput.SlotType.ALTAR_GRID, 4))
                .addOutputModifier(AltarOutputGenerateIdentifier.INSTANCE)
                .addOutputModifier(AltarOutputAddGemModifier.INSTANCE)
                .addOutputModifier(AltarOutputSetDataComponent.of(DataComponents.RARITY, EnumExtensions.RARITY_ARTIFACT))
                .addOutputModifier(AltarOutputSetFlag.of(FlagsComponent.Flag.IS_ARTIFACT_ENHANCED))
                .addRequiredAdditionalInput(1, IsStableArtifactIngredient.INSTANCE.toVanilla())
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(1000 * 81))
                .addRequiredFluid(FluidsAS.LIQUID_STARLIGHT.stack(1000 * 81))
                .addRequiredLumen(LumenAS.PRISMATIC.stack(600))
                .save(recipeOutput);
    }

    private static void registerVanillaConversionRecipes(RecipeOutput recipeOutput) {
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.TOME)
                .addOutput(ItemsAS.TOME)
                .setGridLines(
                        " A ",
                        " B ",
                        "   "
                )
                .addInput('A', TagsAS.Items.GEMS_AQUAMARINE)
                .addInput('B', Items.BOOK)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.ASTROLABE)
                .addOutput(ItemsAS.ASTROLABE)
                .setGridLines(
                        "GGG",
                        "PAS",
                        "GGG"
                )
                .addInput('S', Items.SPYGLASS)
                .addInput('P', ConventionalItemTags.GLASS_PANES)
                .addInput('G', ConventionalItemTags.GOLD_INGOTS)
                .addInput('A', TagsAS.Items.GEMS_AQUAMARINE)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_SOOTY_MARBLE_RAW)
                .addOutput(new ItemStack(ItemsAS.BLOCK_SOOTY_MARBLE_RAW, 8))
                .setGridLines(
                        "MMM",
                        "MCM",
                        "MMM"
                )
                .addInput('M', ItemsAS.BLOCK_MARBLE_RAW)
                .addInput('C', ItemTags.COALS)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.PARCHMENT)
                .addOutput(new ItemStack(ItemsAS.PARCHMENT, 2))
                .setGridLines(
                        " A ",
                        " P ",
                        "   "
                )
                .addInput('A', TagsAS.Items.GEMS_AQUAMARINE)
                .addInput('P', Items.PAPER)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.GLASS_LENS)
                .addOutput(ItemsAS.GLASS_LENS)
                .setGridLines(
                        " A ",
                        "AGA",
                        " A "
                )
                .addInput('A', TagsAS.Items.GEMS_AQUAMARINE)
                .addInput('G', ConventionalItemTags.GLASS_PANES)
                .mayChain()
                .save(recipeOutput);

        registerVanillaMarbleConversionRecipes(recipeOutput);
        registerVanillaSootyMarbleConversionRecipes(recipeOutput);
        registerVanillaInfusedWoodConversionRecipes(recipeOutput);
    }

    private static void registerVanillaMarbleConversionRecipes(RecipeOutput recipeOutput) {
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_MARBLE_ARCH)
                .addOutput(new ItemStack(ItemsAS.BLOCK_MARBLE_ARCH, 2))
                .setGridLines(
                        "MM ",
                        "   ",
                        "   "
                )
                .addInput('M', ItemsAS.BLOCK_MARBLE_RAW)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_MARBLE_BRICKS)
                .addOutput(new ItemStack(ItemsAS.BLOCK_MARBLE_BRICKS, 4))
                .setGridLines(
                        "MM ",
                        "MM ",
                        "   "
                )
                .addInput('M', ItemsAS.BLOCK_MARBLE_RAW)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_MARBLE_CHISELED)
                .addOutput(new ItemStack(ItemsAS.BLOCK_MARBLE_CHISELED, 4))
                .setGridLines(
                        " M ",
                        "M M",
                        " M "
                )
                .addInput('M', ItemsAS.BLOCK_MARBLE_RAW)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_MARBLE_ENGRAVED)
                .addOutput(new ItemStack(ItemsAS.BLOCK_MARBLE_ENGRAVED, 5))
                .setGridLines(
                        " M ",
                        "MMM",
                        " M "
                )
                .addInput('M', ItemsAS.BLOCK_MARBLE_RAW)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_MARBLE_PILLAR)
                .addOutput(new ItemStack(ItemsAS.BLOCK_MARBLE_PILLAR, 2))
                .setGridLines(
                        " M ",
                        " M ",
                        "   "
                )
                .addInput('M', ItemsAS.BLOCK_MARBLE_RAW)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_MARBLE_RUNED)
                .addOutput(new ItemStack(ItemsAS.BLOCK_MARBLE_RUNED, 3))
                .setGridLines(
                        "MCM",
                        "   ",
                        "   "
                )
                .addInput('M', ItemsAS.BLOCK_MARBLE_RAW)
                .addInput('C', ItemsAS.BLOCK_MARBLE_CHISELED)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_MARBLE_SLAB)
                .addOutput(new ItemStack(ItemsAS.BLOCK_MARBLE_SLAB, 6))
                .setGridLines(
                        "MMM",
                        "   ",
                        "   "
                )
                .addInput('M', ItemsAS.BLOCK_MARBLE_RAW)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_MARBLE_SLAB, "_from_bricks")
                .addOutput(new ItemStack(ItemsAS.BLOCK_MARBLE_SLAB, 6))
                .setGridLines(
                        "MMM",
                        "   ",
                        "   "
                )
                .addInput('M', ItemsAS.BLOCK_MARBLE_BRICKS)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_MARBLE_STAIRS)
                .addOutput(new ItemStack(ItemsAS.BLOCK_MARBLE_STAIRS, 8))
                .setGridLines(
                        "M  ",
                        "MM ",
                        "MMM"
                )
                .addInput('M', ItemsAS.BLOCK_MARBLE_RAW)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_MARBLE_STAIRS, "_from_bricks")
                .addOutput(new ItemStack(ItemsAS.BLOCK_MARBLE_STAIRS, 8))
                .setGridLines(
                        "M  ",
                        "MM ",
                        "MMM"
                )
                .addInput('M', ItemsAS.BLOCK_MARBLE_BRICKS)
                .mayChain()
                .save(recipeOutput);
    }

    private static void registerVanillaSootyMarbleConversionRecipes(RecipeOutput recipeOutput) {
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_SOOTY_MARBLE_ARCH)
                .addOutput(new ItemStack(ItemsAS.BLOCK_SOOTY_MARBLE_ARCH, 2))
                .setGridLines(
                        "MM ",
                        "   ",
                        "   "
                )
                .addInput('M', ItemsAS.BLOCK_SOOTY_MARBLE_RAW)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_SOOTY_MARBLE_BRICKS)
                .addOutput(new ItemStack(ItemsAS.BLOCK_SOOTY_MARBLE_BRICKS, 4))
                .setGridLines(
                        "MM ",
                        "MM ",
                        "   "
                )
                .addInput('M', ItemsAS.BLOCK_SOOTY_MARBLE_RAW)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_SOOTY_MARBLE_CHISELED)
                .addOutput(new ItemStack(ItemsAS.BLOCK_SOOTY_MARBLE_CHISELED, 4))
                .setGridLines(
                        " M ",
                        "M M",
                        " M "
                )
                .addInput('M', ItemsAS.BLOCK_SOOTY_MARBLE_RAW)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_SOOTY_MARBLE_ENGRAVED)
                .addOutput(new ItemStack(ItemsAS.BLOCK_SOOTY_MARBLE_ENGRAVED, 5))
                .setGridLines(
                        " M ",
                        "MMM",
                        " M "
                )
                .addInput('M', ItemsAS.BLOCK_SOOTY_MARBLE_RAW)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_SOOTY_MARBLE_PILLAR)
                .addOutput(new ItemStack(ItemsAS.BLOCK_SOOTY_MARBLE_PILLAR, 2))
                .setGridLines(
                        " M ",
                        " M ",
                        "   "
                )
                .addInput('M', ItemsAS.BLOCK_SOOTY_MARBLE_RAW)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_SOOTY_MARBLE_RUNED)
                .addOutput(new ItemStack(ItemsAS.BLOCK_SOOTY_MARBLE_RUNED, 3))
                .setGridLines(
                        "MCM",
                        "   ",
                        "   "
                )
                .addInput('M', ItemsAS.BLOCK_SOOTY_MARBLE_RAW)
                .addInput('C', ItemsAS.BLOCK_SOOTY_MARBLE_CHISELED)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_SOOTY_MARBLE_SLAB)
                .addOutput(new ItemStack(ItemsAS.BLOCK_SOOTY_MARBLE_SLAB, 6))
                .setGridLines(
                        "MMM",
                        "   ",
                        "   "
                )
                .addInput('M', ItemsAS.BLOCK_SOOTY_MARBLE_RAW)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_SOOTY_MARBLE_SLAB, "_from_bricks")
                .addOutput(new ItemStack(ItemsAS.BLOCK_SOOTY_MARBLE_SLAB, 6))
                .setGridLines(
                        "MMM",
                        "   ",
                        "   "
                )
                .addInput('M', ItemsAS.BLOCK_SOOTY_MARBLE_BRICKS)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_SOOTY_MARBLE_STAIRS)
                .addOutput(new ItemStack(ItemsAS.BLOCK_SOOTY_MARBLE_STAIRS, 8))
                .setGridLines(
                        "M  ",
                        "MM ",
                        "MMM"
                )
                .addInput('M', ItemsAS.BLOCK_SOOTY_MARBLE_RAW)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_SOOTY_MARBLE_STAIRS, "_from_bricks")
                .addOutput(new ItemStack(ItemsAS.BLOCK_SOOTY_MARBLE_STAIRS, 8))
                .setGridLines(
                        "M  ",
                        "MM ",
                        "MMM"
                )
                .addInput('M', ItemsAS.BLOCK_SOOTY_MARBLE_BRICKS)
                .mayChain()
                .save(recipeOutput);
    }

    private static void registerVanillaInfusedWoodConversionRecipes(RecipeOutput recipeOutput) {
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_INFUSED_WOOD_PLANKS)
                .addOutput(new ItemStack(ItemsAS.BLOCK_INFUSED_WOOD_PLANKS, 4))
                .setGridLines(
                        "   ",
                        " W ",
                        "   "
                )
                .addInput('W', ItemsAS.BLOCK_INFUSED_WOOD_RAW)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_INFUSED_WOOD_ARCH)
                .addOutput(new ItemStack(ItemsAS.BLOCK_INFUSED_WOOD_ARCH, 2))
                .setGridLines(
                        "WW ",
                        "   ",
                        "   "
                )
                .addInput('W', ItemsAS.BLOCK_INFUSED_WOOD_PLANKS)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_INFUSED_WOOD_COLUMN)
                .addOutput(new ItemStack(ItemsAS.BLOCK_INFUSED_WOOD_COLUMN, 4))
                .setGridLines(
                        " W ",
                        " W ",
                        "   "
                )
                .addInput('W', ItemsAS.BLOCK_INFUSED_WOOD_PLANKS)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_INFUSED_WOOD_ENGRAVED)
                .addOutput(new ItemStack(ItemsAS.BLOCK_INFUSED_WOOD_ENGRAVED, 4))
                .setGridLines(
                        " W ",
                        "W W",
                        " W "
                )
                .addInput('W', ItemsAS.BLOCK_INFUSED_WOOD_PLANKS)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_INFUSED_WOOD_ENRICHED)
                .addOutput(new ItemStack(ItemsAS.BLOCK_INFUSED_WOOD_ENRICHED, 5))
                .setGridLines(
                        " W ",
                        "WAW",
                        " W "
                )
                .addInput('W', ItemsAS.BLOCK_INFUSED_WOOD_PLANKS)
                .mayChain()
                .addInput('A', TagsAS.Items.GEMS_AQUAMARINE)
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_INFUSED_WOOD_SLAB)
                .addOutput(new ItemStack(ItemsAS.BLOCK_INFUSED_WOOD_SLAB, 6))
                .setGridLines(
                        "WWW",
                        "   ",
                        "   "
                )
                .addInput('W', ItemsAS.BLOCK_INFUSED_WOOD_PLANKS)
                .mayChain()
                .save(recipeOutput);
        AltarRecipeBuilder.builder(TileAltar.AltarType.ILLUMINATION)
                .setRecipeId(ItemsAS.BLOCK_INFUSED_WOOD_STAIRS)
                .addOutput(new ItemStack(ItemsAS.BLOCK_INFUSED_WOOD_STAIRS, 6))
                .setGridLines(
                        "W  ",
                        "WW ",
                        "WWW"
                )
                .addInput('W', ItemsAS.BLOCK_INFUSED_WOOD_PLANKS)
                .mayChain()
                .save(recipeOutput);
    }
}
