/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.datagen.data.recipes.lumen;

import hellfirepvp.astralsorcery.common.component.ArtifactTypeComponent;
import hellfirepvp.astralsorcery.common.lib.DataComponentsAS;
import hellfirepvp.astralsorcery.common.lib.ItemsAS;
import hellfirepvp.astralsorcery.common.lib.LumenAS;
import hellfirepvp.astralsorcery.common.lib.RegistriesAS;
import hellfirepvp.astralsorcery.common.recipe.builder.LumenGenerationRecipeBuilder;
import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Objects;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: LumenGenerationRecipeProvider
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class LumenGenerationRecipeProvider {

    public static void registerRecipes(RecipeOutput recipeOutput) {
        LumenGenerationRecipeBuilder.builder(Ingredient.of(ItemsAS.BLOCK_HYACINTH), LumenAS.AEVITAS.get())
                .producedLumenAmount(4)
                .productionAttemptMultiplier(4F)
                .save(recipeOutput);
        LumenGenerationRecipeBuilder.builder(Ingredient.of(ItemsAS.BLOCK_IRIS), LumenAS.VICIO.get())
                .producedLumenAmount(4)
                .productionAttemptMultiplier(4F)
                .save(recipeOutput);
        LumenGenerationRecipeBuilder.builder(Ingredient.of(ItemsAS.BLOCK_ORCHID), LumenAS.ARMARA.get())
                .producedLumenAmount(4)
                .productionAttemptMultiplier(4F)
                .save(recipeOutput);
        LumenGenerationRecipeBuilder.builder(Ingredient.of(ItemsAS.BLOCK_PROTEA), LumenAS.EVORSIO.get())
                .producedLumenAmount(4)
                .productionAttemptMultiplier(4F)
                .save(recipeOutput);
        LumenGenerationRecipeBuilder.builder(Ingredient.of(ItemsAS.BLOCK_THISTLE), LumenAS.DISCIDIA.get())
                .producedLumenAmount(4)
                .productionAttemptMultiplier(4F)
                .save(recipeOutput);

        LumenGenerationRecipeBuilder.builder(Ingredient.of(ItemsAS.VIVID_POWDER), LumenAS.VIREL.get())
                .producedLumenAmount(3)
                .productionAttemptMultiplier(4F)
                .catalystShatterMultiplier(1.2F)
                .attemptStarlightConsumption(1.4F)
                .addLumenCombinationInput(LumenAS.AEVITAS.asLumen(), 9)
                .addLumenCombinationInput(LumenAS.VICIO.asLumen(), 5)
                .save(recipeOutput);
        LumenGenerationRecipeBuilder.builder(Ingredient.of(ItemsAS.ILLUMINATION_POWDER), LumenAS.SOLYN.get())
                .producedLumenAmount(3)
                .productionAttemptMultiplier(4F)
                .catalystShatterMultiplier(1.2F)
                .attemptStarlightConsumption(1.4F)
                .addLumenCombinationInput(LumenAS.AEVITAS.asLumen(), 5)
                .addLumenCombinationInput(LumenAS.ARMARA.asLumen(), 8)
                .save(recipeOutput);
        LumenGenerationRecipeBuilder.builder(Ingredient.of(ConventionalItemTags.QUARTZ_GEMS), LumenAS.CALDOR.get())
                .producedLumenAmount(3)
                .productionAttemptMultiplier(4F)
                .catalystShatterMultiplier(1.2F)
                .attemptStarlightConsumption(1.4F)
                .addLumenCombinationInput(LumenAS.EVORSIO.asLumen(), 6)
                .addLumenCombinationInput(LumenAS.ARMARA.asLumen(), 4)
                .addLumenCombinationInput(LumenAS.DISCIDIA.asLumen(), 10)
                .save(recipeOutput);
        LumenGenerationRecipeBuilder.builder(Ingredient.of(ItemsAS.NOCTURNAL_POWDER), LumenAS.NULLAE.get())
                .producedLumenAmount(3)
                .productionAttemptMultiplier(4F)
                .catalystShatterMultiplier(1.2F)
                .attemptStarlightConsumption(1.4F)
                .addLumenCombinationInput(LumenAS.EVORSIO.asLumen(), 5)
                .addLumenCombinationInput(LumenAS.VICIO.asLumen(), 7)
                .save(recipeOutput);

        LumenGenerationRecipeBuilder.builder(Ingredient.of(Items.ECHO_SHARD), LumenAS.HYLE.get())
                .producedLumenAmount(2)
                .productionAttemptMultiplier(3F)
                .catalystShatterMultiplier(1.5F)
                .attemptStarlightConsumption(2F)
                .addLumenCombinationInput(LumenAS.SOLYN.asLumen(), 5)
                .addLumenCombinationInput(LumenAS.VIREL.asLumen(), 4)
                .addLumenCombinationInput(LumenAS.ARMARA.asLumen(), 8)
                .save(recipeOutput);
        LumenGenerationRecipeBuilder.builder(Ingredient.of(ItemsAS.STARDUST), LumenAS.AKASHA.get())
                .producedLumenAmount(2)
                .productionAttemptMultiplier(3F)
                .catalystShatterMultiplier(1.5F)
                .attemptStarlightConsumption(2F)
                .addLumenCombinationInput(LumenAS.NULLAE.asLumen(), 4)
                .addLumenCombinationInput(LumenAS.CALDOR.asLumen(), 7)
                .addLumenCombinationInput(LumenAS.EVORSIO.asLumen(), 12)
                .save(recipeOutput);
        LumenGenerationRecipeBuilder.builder(Ingredient.of(ConventionalItemTags.AMETHYST_GEMS), LumenAS.DYNAMIS.get())
                .producedLumenAmount(2)
                .productionAttemptMultiplier(3F)
                .catalystShatterMultiplier(1.5F)
                .attemptStarlightConsumption(2F)
                .addLumenCombinationInput(LumenAS.SOLYN.asLumen(), 7)
                .addLumenCombinationInput(LumenAS.VIREL.asLumen(), 8)
                .addLumenCombinationInput(LumenAS.VICIO.asLumen(), 8)
                .save(recipeOutput);
        LumenGenerationRecipeBuilder.builder(Ingredient.of(ItemsAS.RESONATING_GEM), LumenAS.AION.get())
                .producedLumenAmount(2)
                .productionAttemptMultiplier(3F)
                .catalystShatterMultiplier(1.5F)
                .attemptStarlightConsumption(2F)
                .addLumenCombinationInput(LumenAS.NULLAE.asLumen(), 8)
                .addLumenCombinationInput(LumenAS.CALDOR.asLumen(), 5)
                .addLumenCombinationInput(LumenAS.DISCIDIA.asLumen(), 16)
                .save(recipeOutput);

        Ingredient matchAnyArtifactShard = DefaultCustomIngredients.any(RegistriesAS.REGISTRY_ARTIFACT_TYPES.stream()
                .map(ArtifactTypeComponent::new)
                .map(typeCmp -> {
                    ItemStack stack = new ItemStack(ItemsAS.ARTIFACT_SHARD);
                    stack.set(DataComponentsAS.ARTIFACT_TYPE, typeCmp);
                    return DefaultCustomIngredients.components(stack);
                }).toArray(Ingredient[]::new));
        LumenGenerationRecipeBuilder.builder(Objects.requireNonNull(matchAnyArtifactShard.getCustomIngredient()).toVanilla(), LumenAS.PRISMATIC.get())
                .producedLumenAmount(2)
                .productionAttemptMultiplier(2.5F)
                .catalystShatterMultiplier(2F)
                .attemptStarlightConsumption(3F)
                .addLumenCombinationInput(LumenAS.HYLE.get(), 3)
                .addLumenCombinationInput(LumenAS.DYNAMIS.get(), 3)
                .addLumenCombinationInput(LumenAS.AION.get(), 3)
                .addLumenCombinationInput(LumenAS.AKASHA.get(), 3)
                .save(recipeOutput);
    }
}
