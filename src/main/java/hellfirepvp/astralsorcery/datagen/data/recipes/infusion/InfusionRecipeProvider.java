/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.datagen.data.recipes.infusion;

import hellfirepvp.astralsorcery.common.lib.FluidsAS;
import hellfirepvp.astralsorcery.common.lib.ItemsAS;
import hellfirepvp.astralsorcery.common.recipe.builder.InfusionRecipeBuilder;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: InfusionRecipeProvider
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class InfusionRecipeProvider {

    public static void registerRecipes(RecipeOutput recipeOutput) {
        InfusionRecipeBuilder.builder(Ingredient.of(ItemsAS.AQUAMARINE), FluidsAS.LIQUID_STARLIGHT.getSource(), ItemsAS.RESONATING_GEM.getDefaultInstance())
                .setConsumptionChance(0.03F)
                .save(recipeOutput);
        InfusionRecipeBuilder.builder(Ingredient.of(ItemsAS.BLOCK_INFUSED_WOOD_RAW), FluidsAS.LIQUID_STARLIGHT.getSource(), ItemsAS.BLOCK_INFUSED_WOOD_INFUSED.getDefaultInstance())
                .setConsumptionChance(0.015F)
                .save(recipeOutput);
        InfusionRecipeBuilder.builder(Ingredient.of(ConventionalItemTags.GLASS_PANES), FluidsAS.LIQUID_STARLIGHT.getSource(), ItemsAS.GLASS_LENS.getDefaultInstance())
                .setConsumptionChance(0.01F)
                .save(recipeOutput);
    }
}
