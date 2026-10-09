/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.datagen;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.datagen.assets.AstralConstellationPositionProvider;
import hellfirepvp.astralsorcery.datagen.assets.AstralCustomSpriteProvider;
import hellfirepvp.astralsorcery.datagen.assets.AstralLumenDisplayPositionProvider;
import hellfirepvp.astralsorcery.datagen.assets.AstralSoundsProvider;
import hellfirepvp.astralsorcery.datagen.data.AstralRegistriesDataProvider;
import hellfirepvp.astralsorcery.datagen.data.advancement.AstralAdvancementProvider;
import hellfirepvp.astralsorcery.datagen.data.artifact.AstralArtifactConditionProvider;
import hellfirepvp.astralsorcery.datagen.data.artifact.AstralArtifactEffectProvider;
import hellfirepvp.astralsorcery.datagen.data.artifact.AstralArtifactPenaltyProvider;
import hellfirepvp.astralsorcery.datagen.data.damage.AstralDamageTypeTagProvider;
import hellfirepvp.astralsorcery.datagen.data.loot.AstralBlockLootTableProvider;
import hellfirepvp.astralsorcery.datagen.data.loot.AstralGameplayLootTableProvider;
import hellfirepvp.astralsorcery.datagen.data.lumen.AstralLumenBindingDataProvider;
import hellfirepvp.astralsorcery.datagen.data.perks.AstralPerkTreeProvider;
import hellfirepvp.astralsorcery.datagen.data.recipes.AstralRecipeProvider;
import hellfirepvp.astralsorcery.datagen.data.research.AstralResearchNodeProvider;
import hellfirepvp.astralsorcery.datagen.data.tags.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AstralDataGenerator
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AstralDataGenerator {

    public static void gather(FabricDataGenerator.Pack pack) {
        if (!AstralSorcery.isDoingDataGeneration()) {
            return;
        }
        pack.addProvider(AstralCustomSpriteProvider::new);
        pack.addProvider(AstralModelProvider::new);
        //pack.addProvider(AstralBlockStateProvider::new);
        pack.addProvider(AstralConstellationPositionProvider::new);
        pack.addProvider(AstralLumenDisplayPositionProvider::new);
        pack.addProvider(AstralSoundsProvider::new);

        pack.addProvider(AstralRegistriesDataProvider::new);

        pack.addProvider(AstralFluidTagsProvider::new);
        pack.addProvider(AstralBlockTagsProvider::new);
        pack.addProvider(AstralItemTagsProvider::new);
        pack.addProvider(AstralBiomeTagsProvider::new);
        pack.addProvider(AstralEntityTagsProvider::new);
        pack.addProvider(AstralConstellationTagsProvider::new);
        pack.addProvider(AstralRecipeProvider::new);
        pack.addProvider(AstralResearchNodeProvider::new);
        pack.addProvider(AstralBlockLootTableProvider::new);
        pack.addProvider(AstralGameplayLootTableProvider::new);
        pack.addProvider(AstralArtifactConditionProvider::new);
        pack.addProvider(AstralArtifactEffectProvider::new);
        pack.addProvider(AstralArtifactPenaltyProvider::new);
        pack.addProvider(AstralDamageTypeTagProvider::new);
        pack.addProvider(AstralLumenBindingDataProvider::new);
        //pack.addProvider(DebugPerkTreeProvider::new);
        pack.addProvider(AstralPerkTreeProvider::new);
        pack.addProvider(AstralAdvancementProvider::new);
    }

    private static class AstralModelProvider extends FabricModelProvider {

        public AstralModelProvider(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockModelGenerators generators) {
            // AstralBlockModelProvider.registerModels(generators);
        }

        @Override
        public void generateItemModels(ItemModelGenerators generators) {
            // AstralItemModelProvider.registerModels(generators);
        }
    }
}
