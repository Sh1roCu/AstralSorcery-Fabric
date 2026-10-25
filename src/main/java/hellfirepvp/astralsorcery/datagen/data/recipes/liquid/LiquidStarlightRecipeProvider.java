/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.datagen.data.recipes.liquid;

import cn.sh1rocu.astralsorcery.util.neoforge.common.crafting.SizedIngredient;
import hellfirepvp.astralsorcery.common.component.LumenComponent;
import hellfirepvp.astralsorcery.common.constellation.BaseConstellation;
import hellfirepvp.astralsorcery.common.ingredient.HasStoredLumenIngredient;
import hellfirepvp.astralsorcery.common.ingredient.IsLumenBindableIngredient;
import hellfirepvp.astralsorcery.common.lib.*;
import hellfirepvp.astralsorcery.common.lib.constants.TagsAS;
import hellfirepvp.astralsorcery.common.recipe.builder.LiquidStarlightRecipeBuilder;
import hellfirepvp.astralsorcery.common.recipe.liquid.output.*;
import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: LiquidStarlightRecipeProvider
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class LiquidStarlightRecipeProvider {

    public static void registerRecipes(RecipeOutput recipeOutput, HolderLookup.Provider provider) {
        LiquidStarlightRecipeBuilder.builder("infused_wood", new SizedIngredient(Ingredient.of(ItemTags.LOGS), 1))
                .addOutputModifier(LiquidStarlightOutputDropItem.create(ItemsAS.BLOCK_INFUSED_WOOD_RAW.getDefaultInstance()))
                .duration(6)
                .randomAdditionalDuration(10)
                .save(recipeOutput);
        LiquidStarlightRecipeBuilder.builder("merge_crystals",
                        new SizedIngredient(Ingredient.of(TagsAS.Items.CRYSTAL), 1),
                        List.of(new SizedIngredient(Ingredient.of(TagsAS.Items.CRYSTAL), 1)))
                .addOutputModifier(LiquidStarlightOutputMergeCrystal.getInstance())
                .duration(60)
                .randomAdditionalDuration(40)
                .consumesLiquid()
                .doesntConsumeInputs()
                .save(recipeOutput);
        LiquidStarlightRecipeBuilder.builder("form_crystal_cluster",
                        new SizedIngredient(Ingredient.of(ItemsAS.STARDUST), 1),
                        List.of(new SizedIngredient(Ingredient.of(ItemsAS.STARDUST), 1)))
                .addOutputModifier(LiquidStarlightOutputFormCrystalCluster.getInstance())
                .duration(80)
                .randomAdditionalDuration(40)
                .consumesLiquid()
                .doesntConsumeInputs()
                .save(recipeOutput);
        LiquidStarlightRecipeBuilder.builder("form_gem_crystal_cluster",
                        new SizedIngredient(Ingredient.of(ItemsAS.ILLUMINATION_POWDER), 1),
                        List.of(new SizedIngredient(Ingredient.of(TagsAS.Items.CRYSTAL), 1)))
                .addOutputModifier(LiquidStarlightOutputFormGemCrystalCluster.getInstance())
                .duration(80)
                .randomAdditionalDuration(40)
                .consumesLiquid()
                .doesntConsumeInputs()
                .save(recipeOutput);
        LiquidStarlightRecipeBuilder.builder("grow_crystal_size",
                        new SizedIngredient(Ingredient.of(TagsAS.Items.CRYSTAL), 1))
                .addOutputModifier(LiquidStarlightOutputGrowSize.getInstance())
                .duration(60)
                .randomAdditionalDuration(30)
                .consumesLiquid()
                .doesntConsumeInputs()
                .save(recipeOutput);

        LiquidStarlightRecipeBuilder.builder("bind_lumen",
                        new SizedIngredient(Ingredient.of(ItemsAS.LUMEN_CRYSTAL), 1),
                        List.of(
                                SizedIngredient.of(ItemsAS.STARDUST, 1),
                                new SizedIngredient(IsLumenBindableIngredient.INSTANCE.toVanilla(), 1)
                        ))
                .addOutputModifier(LiquidStarlightOutputBindLumen.getInstance())
                .duration(80)
                .randomAdditionalDuration(40)
                .consumesLiquid()
                .doesntConsumeInputs()
                .save(recipeOutput);
        LiquidStarlightRecipeBuilder.builder("fill_lumen",
                        new SizedIngredient(Ingredient.of(ItemsAS.LUMEN_CRYSTAL), 1),
                        List.of(new SizedIngredient(HasStoredLumenIngredient.INSTANCE.toVanilla(), 1)))
                .addOutputModifier(LiquidStarlightOutputFillLumen.getInstance())
                .duration(80)
                .randomAdditionalDuration(40)
                .consumesLiquid()
                .doesntConsumeInputs()
                .save(recipeOutput);

        LiquidStarlightRecipeBuilder.builder("engrave_lumen_virel",
                        new SizedIngredient(Ingredient.of(TagsAS.Items.CRYSTAL), 1),
                        List.of(
                                new SizedIngredient(DefaultCustomIngredients.components(
                                        Ingredient.of(ItemsAS.LUMEN_CRYSTAL),
                                        DataComponentPredicate.builder()
                                                .expect(DataComponentsAS.LUMEN, new LumenComponent(LumenAS.VIREL.holder()))
                                                .build().asPatch()
                                ), 1),
                                new SizedIngredient(Ingredient.of(ItemsAS.RESONATING_GEM), 1)
                        ))
                .addOutputModifier(LiquidStarlightOutputEngraveLumen.builder()
                        .add(CrystalPropertiesAS.TOOL_EFFICIENCY, 7)
                        .add(CrystalPropertiesAS.TOOL_DURABILITY, 1)
                        .add(CrystalPropertiesAS.STARLIGHT_FOCUS, 1)
                        .add(CrystalPropertiesAS.LUMEN_CHANNELING, 1)
                        .build())
                .duration(80)
                .randomAdditionalDuration(40)
                .consumesLiquid()
                .doesntConsumeInputs()
                .save(recipeOutput);
        LiquidStarlightRecipeBuilder.builder("engrave_lumen_solyn",
                        new SizedIngredient(Ingredient.of(TagsAS.Items.CRYSTAL), 1),
                        List.of(
                                new SizedIngredient(DefaultCustomIngredients.components(
                                        Ingredient.of(ItemsAS.LUMEN_CRYSTAL),
                                        DataComponentPredicate.builder()
                                                .expect(DataComponentsAS.LUMEN, new LumenComponent(LumenAS.SOLYN.holder()))
                                                .build().asPatch()
                                ), 1),
                                new SizedIngredient(Ingredient.of(ItemsAS.RESONATING_GEM), 1)
                        ))
                .addOutputModifier(LiquidStarlightOutputEngraveLumen.builder()
                        .add(CrystalPropertiesAS.TOOL_EFFICIENCY, 1)
                        .add(CrystalPropertiesAS.TOOL_DURABILITY, 1)
                        .add(CrystalPropertiesAS.STARLIGHT_FOCUS, 7)
                        .add(CrystalPropertiesAS.LUMEN_CHANNELING, 1)
                        .build())
                .duration(80)
                .randomAdditionalDuration(40)
                .consumesLiquid()
                .doesntConsumeInputs()
                .save(recipeOutput);
        LiquidStarlightRecipeBuilder.builder("engrave_lumen_nullae",
                        new SizedIngredient(Ingredient.of(TagsAS.Items.CRYSTAL), 1),
                        List.of(
                                new SizedIngredient(DefaultCustomIngredients.components(
                                        Ingredient.of(ItemsAS.LUMEN_CRYSTAL),
                                        DataComponentPredicate.builder()
                                                .expect(DataComponentsAS.LUMEN, new LumenComponent(LumenAS.NULLAE.holder()))
                                                .build().asPatch()
                                ), 1),
                                new SizedIngredient(Ingredient.of(ItemsAS.RESONATING_GEM), 1)
                        ))
                .addOutputModifier(LiquidStarlightOutputEngraveLumen.builder()
                        .add(CrystalPropertiesAS.TOOL_EFFICIENCY, 1)
                        .add(CrystalPropertiesAS.TOOL_DURABILITY, 1)
                        .add(CrystalPropertiesAS.STARLIGHT_FOCUS, 1)
                        .add(CrystalPropertiesAS.LUMEN_CHANNELING, 7)
                        .build())
                .duration(80)
                .randomAdditionalDuration(40)
                .consumesLiquid()
                .doesntConsumeInputs()
                .save(recipeOutput);
        LiquidStarlightRecipeBuilder.builder("engrave_lumen_caldor",
                        new SizedIngredient(Ingredient.of(TagsAS.Items.CRYSTAL), 1),
                        List.of(
                                new SizedIngredient(DefaultCustomIngredients.components(
                                        Ingredient.of(ItemsAS.LUMEN_CRYSTAL),
                                        DataComponentPredicate.builder()
                                                .expect(DataComponentsAS.LUMEN, new LumenComponent(LumenAS.CALDOR.holder()))
                                                .build().asPatch()
                                ), 1),
                                new SizedIngredient(Ingredient.of(ItemsAS.RESONATING_GEM), 1)
                        ))
                .addOutputModifier(LiquidStarlightOutputEngraveLumen.builder()
                        .add(CrystalPropertiesAS.TOOL_EFFICIENCY, 1)
                        .add(CrystalPropertiesAS.TOOL_DURABILITY, 7)
                        .add(CrystalPropertiesAS.STARLIGHT_FOCUS, 1)
                        .add(CrystalPropertiesAS.LUMEN_CHANNELING, 1)
                        .build())
                .duration(80)
                .randomAdditionalDuration(40)
                .consumesLiquid()
                .doesntConsumeInputs()
                .save(recipeOutput);

        LiquidStarlightOutputEngraveLumen.Builder majorBuilder = LiquidStarlightOutputEngraveLumen.builder();
        LiquidStarlightOutputEngraveLumen.Builder minorBuilder = LiquidStarlightOutputEngraveLumen.builder();
        provider.lookupOrThrow(RegistriesAS.KEY_CONSTELLATIONS).listElements().forEach(ref -> {
            CrystalPropertiesAS.getProperty(ref.value()).ifPresent(prop -> {
                if (ref.value().getTier() == BaseConstellation.Tier.MAJOR) {
                    majorBuilder.add(prop, 5);
                    minorBuilder.add(prop, 1);
                } else {
                    majorBuilder.add(prop, 1);
                    minorBuilder.add(prop, 4);
                }
            });
        });
        LiquidStarlightRecipeBuilder.builder("engrave_lumen_dynamis",
                        new SizedIngredient(Ingredient.of(TagsAS.Items.CRYSTAL), 1),
                        List.of(
                                new SizedIngredient(DefaultCustomIngredients.components(
                                        Ingredient.of(ItemsAS.LUMEN_CRYSTAL),
                                        DataComponentPredicate.builder()
                                                .expect(DataComponentsAS.LUMEN, new LumenComponent(LumenAS.DYNAMIS.holder()))
                                                .build().asPatch()
                                ), 1),
                                new SizedIngredient(Ingredient.of(ItemsAS.RESONATING_GEM), 1)
                        ))
                .addOutputModifier(minorBuilder.build())
                .duration(80)
                .randomAdditionalDuration(40)
                .consumesLiquid()
                .doesntConsumeInputs()
                .save(recipeOutput);
        LiquidStarlightRecipeBuilder.builder("engrave_lumen_aion",
                        new SizedIngredient(Ingredient.of(TagsAS.Items.CRYSTAL), 1),
                        List.of(
                                new SizedIngredient(DefaultCustomIngredients.components(
                                        Ingredient.of(ItemsAS.LUMEN_CRYSTAL),
                                        DataComponentPredicate.builder()
                                                .expect(DataComponentsAS.LUMEN, new LumenComponent(LumenAS.AION.holder()))
                                                .build().asPatch()
                                ), 1),
                                new SizedIngredient(Ingredient.of(ItemsAS.RESONATING_GEM), 1)
                        ))
                .addOutputModifier(majorBuilder.build())
                .duration(80)
                .randomAdditionalDuration(40)
                .consumesLiquid()
                .doesntConsumeInputs()
                .save(recipeOutput);

        LiquidStarlightOutputEngraveLumen.Builder elementaryBuilder = LiquidStarlightOutputEngraveLumen.builder();
        LiquidStarlightOutputEngraveLumen.Builder complexBuilder = LiquidStarlightOutputEngraveLumen.builder();
        provider.lookupOrThrow(RegistriesAS.KEY_LUMEN).listElements().forEach(ref -> {
            CrystalPropertiesAS.getProperty(ref.value()).ifPresent(prop -> {
                if (ref.value().isElementary()) {
                    elementaryBuilder.add(prop, 3);
                    complexBuilder.add(prop, 1);
                } else {
                    elementaryBuilder.add(prop, 1);
                    complexBuilder.add(prop, 3);
                }
            });
        });
        LiquidStarlightRecipeBuilder.builder("engrave_lumen_hyle",
                        new SizedIngredient(Ingredient.of(TagsAS.Items.CRYSTAL), 1),
                        List.of(
                                new SizedIngredient(DefaultCustomIngredients.components(
                                        Ingredient.of(ItemsAS.LUMEN_CRYSTAL),
                                        DataComponentPredicate.builder()
                                                .expect(DataComponentsAS.LUMEN, new LumenComponent(LumenAS.HYLE.holder()))
                                                .build().asPatch()
                                ), 1),
                                new SizedIngredient(Ingredient.of(ItemsAS.RESONATING_GEM), 1)
                        ))
                .addOutputModifier(elementaryBuilder.build())
                .duration(80)
                .randomAdditionalDuration(40)
                .consumesLiquid()
                .doesntConsumeInputs()
                .save(recipeOutput);
        LiquidStarlightRecipeBuilder.builder("engrave_lumen_akasha",
                        new SizedIngredient(Ingredient.of(TagsAS.Items.CRYSTAL), 1),
                        List.of(
                                new SizedIngredient(DefaultCustomIngredients.components(
                                        Ingredient.of(ItemsAS.LUMEN_CRYSTAL),
                                        DataComponentPredicate.builder()
                                                .expect(DataComponentsAS.LUMEN, new LumenComponent(LumenAS.AKASHA.holder()))
                                                .build().asPatch()
                                ), 1),
                                new SizedIngredient(Ingredient.of(ItemsAS.RESONATING_GEM), 1)
                        ))
                .addOutputModifier(complexBuilder.build())
                .duration(80)
                .randomAdditionalDuration(40)
                .consumesLiquid()
                .doesntConsumeInputs()
                .save(recipeOutput);
    }
}
