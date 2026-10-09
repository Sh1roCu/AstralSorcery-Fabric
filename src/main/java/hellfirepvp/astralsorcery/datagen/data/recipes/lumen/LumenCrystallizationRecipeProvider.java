/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.datagen.data.recipes.lumen;

import hellfirepvp.astralsorcery.common.component.LumenComponent;
import hellfirepvp.astralsorcery.common.lib.DataComponentsAS;
import hellfirepvp.astralsorcery.common.lib.ItemsAS;
import hellfirepvp.astralsorcery.common.lib.LumenAS;
import hellfirepvp.astralsorcery.common.lib.constants.TagsAS;
import hellfirepvp.astralsorcery.common.recipe.builder.LumenCrystallizationRecipeBuilder;
import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: LumenCrystallizationRecipeProvider
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class LumenCrystallizationRecipeProvider {

    public static void registerRecipes(RecipeOutput recipeOutput) {
        LumenCrystallizationRecipeBuilder.builder(Ingredient.of(ItemTags.SAPLINGS), LumenAS.AEVITAS.get())
                .catalystShatterMultiplier(5F)
                .save(recipeOutput);
        LumenCrystallizationRecipeBuilder.builder(lumenCrystal(LumenAS.AEVITAS), LumenAS.AEVITAS.get(), "_crystal")
                .catalystShatterMultiplier(0F)
                .save(recipeOutput);

        LumenCrystallizationRecipeBuilder.builder(Ingredient.of(ConventionalItemTags.INGOTS), LumenAS.ARMARA.get())
                .catalystShatterMultiplier(2F)
                .save(recipeOutput);
        LumenCrystallizationRecipeBuilder.builder(lumenCrystal(LumenAS.ARMARA), LumenAS.ARMARA.get(), "_crystal")
                .catalystShatterMultiplier(0F)
                .save(recipeOutput);

        LumenCrystallizationRecipeBuilder.builder(Ingredient.of(ItemTags.ARROWS), LumenAS.DISCIDIA.get())
                .catalystShatterMultiplier(3F)
                .save(recipeOutput);
        LumenCrystallizationRecipeBuilder.builder(lumenCrystal(LumenAS.DISCIDIA), LumenAS.DISCIDIA.get(), "_crystal")
                .catalystShatterMultiplier(0F)
                .save(recipeOutput);

        LumenCrystallizationRecipeBuilder.builder(Ingredient.of(ConventionalItemTags.STONES), LumenAS.EVORSIO.get())
                .catalystShatterMultiplier(5F)
                .save(recipeOutput);
        LumenCrystallizationRecipeBuilder.builder(lumenCrystal(LumenAS.EVORSIO), LumenAS.EVORSIO.get(), "_crystal")
                .catalystShatterMultiplier(0F)
                .save(recipeOutput);

        LumenCrystallizationRecipeBuilder.builder(Ingredient.of(ConventionalItemTags.FEATHERS), LumenAS.VICIO.get())
                .catalystShatterMultiplier(2F)
                .save(recipeOutput);
        LumenCrystallizationRecipeBuilder.builder(lumenCrystal(LumenAS.VICIO), LumenAS.VICIO.get(), "_crystal")
                .catalystShatterMultiplier(0F)
                .save(recipeOutput);

        // ---------------------------------------------------------------------------

        LumenCrystallizationRecipeBuilder.builder(Ingredient.of(ItemsAS.VIVID_POWDER), LumenAS.VIREL.get())
                .catalystShatterMultiplier(2F)
                .save(recipeOutput);
        LumenCrystallizationRecipeBuilder.builder(lumenCrystal(LumenAS.VIREL), LumenAS.VIREL.get(), "_crystal")
                .catalystShatterMultiplier(0F)
                .save(recipeOutput);

        LumenCrystallizationRecipeBuilder.builder(Ingredient.of(ItemsAS.ILLUMINATION_POWDER), LumenAS.SOLYN.get())
                .catalystShatterMultiplier(2F)
                .save(recipeOutput);
        LumenCrystallizationRecipeBuilder.builder(lumenCrystal(LumenAS.SOLYN), LumenAS.SOLYN.get(), "_crystal")
                .catalystShatterMultiplier(0F)
                .save(recipeOutput);

        LumenCrystallizationRecipeBuilder.builder(Ingredient.of(ItemsAS.NOCTURNAL_POWDER), LumenAS.NULLAE.get())
                .catalystShatterMultiplier(2F)
                .save(recipeOutput);
        LumenCrystallizationRecipeBuilder.builder(lumenCrystal(LumenAS.NULLAE), LumenAS.NULLAE.get(), "_crystal")
                .catalystShatterMultiplier(0F)
                .save(recipeOutput);

        LumenCrystallizationRecipeBuilder.builder(Ingredient.of(ConventionalItemTags.QUARTZ_GEMS), LumenAS.CALDOR.get())
                .catalystShatterMultiplier(3F)
                .save(recipeOutput);
        LumenCrystallizationRecipeBuilder.builder(lumenCrystal(LumenAS.CALDOR), LumenAS.CALDOR.get(), "_crystal")
                .catalystShatterMultiplier(0F)
                .save(recipeOutput);

        // ---------------------------------------------------------------------------

        LumenCrystallizationRecipeBuilder.builder(Ingredient.of(Items.ECHO_SHARD), LumenAS.HYLE.get())
                .catalystShatterMultiplier(3.5F)
                .save(recipeOutput);
        LumenCrystallizationRecipeBuilder.builder(lumenCrystal(LumenAS.HYLE), LumenAS.HYLE.get(), "_crystal")
                .catalystShatterMultiplier(0F)
                .save(recipeOutput);

        LumenCrystallizationRecipeBuilder.builder(Ingredient.of(ConventionalItemTags.AMETHYST_GEMS), LumenAS.DYNAMIS.get())
                .catalystShatterMultiplier(4F)
                .save(recipeOutput);
        LumenCrystallizationRecipeBuilder.builder(lumenCrystal(LumenAS.DYNAMIS), LumenAS.DYNAMIS.get(), "_crystal")
                .catalystShatterMultiplier(0F)
                .save(recipeOutput);

        LumenCrystallizationRecipeBuilder.builder(Ingredient.of(ItemsAS.RESONATING_GEM), LumenAS.AION.get())
                .catalystShatterMultiplier(3F)
                .save(recipeOutput);
        LumenCrystallizationRecipeBuilder.builder(lumenCrystal(LumenAS.AION), LumenAS.AION.get(), "_crystal")
                .catalystShatterMultiplier(0F)
                .save(recipeOutput);

        LumenCrystallizationRecipeBuilder.builder(Ingredient.of(ItemsAS.STARDUST), LumenAS.AKASHA.get())
                .catalystShatterMultiplier(3F)
                .save(recipeOutput);
        LumenCrystallizationRecipeBuilder.builder(lumenCrystal(LumenAS.AKASHA), LumenAS.AKASHA.get(), "_crystal")
                .catalystShatterMultiplier(0F)
                .save(recipeOutput);

        // ---------------------------------------------------------------------------

        LumenCrystallizationRecipeBuilder.builder(Ingredient.of(TagsAS.Items.CRYSTAL), LumenAS.PRISMATIC.get())
                .catalystShatterMultiplier(3F)
                .save(recipeOutput);
    }

    private static Ingredient lumenCrystal(LumenAS.DeferredLumen type) {
        ItemStack stack = new ItemStack(ItemsAS.LUMEN_CRYSTAL.asItem());
        stack.set(DataComponentsAS.LUMEN, new LumenComponent(type.holder()));
        return DefaultCustomIngredients.components(stack);
    }
}
