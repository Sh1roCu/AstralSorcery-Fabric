/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib.types;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.lib.RegistriesAS;
import hellfirepvp.astralsorcery.common.recipe.altar.output.*;
import net.minecraft.core.Registry;

import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AltarRecipeOutputTypesAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AltarRecipeOutputTypesAS {

    public static void init() {

    }

    public static final AltarRecipeOutputModifier.Type<AltarOutputUpdateResearchTier> UPDATE_RESEARCH_TIER =
            register("update_research_tier", () -> AltarOutputUpdateResearchTier.TYPE);
    public static final AltarRecipeOutputModifier.Type<AltarOutputSetBlock> SET_BLOCK =
            register("set_block", () -> AltarOutputSetBlock.TYPE);
    public static final AltarRecipeOutputModifier.Type<AltarOutputSetDataComponent<?>> SET_DATA_COMPONENT =
            register("set_data_component", () -> AltarOutputSetDataComponent.TYPE);
    public static final AltarRecipeOutputModifier.Type<AltarOutputReplaceWithInput> REPLACE_WITH_INPUT =
            register("replace_with_input", () -> AltarOutputReplaceWithInput.TYPE);
    public static final AltarRecipeOutputModifier.Type<AltarOutputIncreaseEnchantments> INCREASE_ENCHANTMENTS =
            register("increase_enchantments", () -> AltarOutputIncreaseEnchantments.TYPE);
    public static final AltarRecipeOutputModifier.Type<AltarOutputAddGemModifier> ADD_GEM_MODIFIER =
            register("add_gem_modifier", () -> AltarOutputAddGemModifier.TYPE);
    public static final AltarRecipeOutputModifier.Type<AltarOutputAddEnchantmentModifier> ADD_ENCHANTMENT_MODIFIER =
            register("add_enchantment_modifier", () -> AltarOutputAddEnchantmentModifier.TYPE);
    public static final AltarRecipeOutputModifier.Type<AltarOutputGenerateIdentifier> GENERATE_IDENTIFIER =
            register("generate_identifier", () -> AltarOutputGenerateIdentifier.TYPE);
    public static final AltarRecipeOutputModifier.Type<AltarOutputMergeCrystalProperties> MERGE_CRYSTAL_PROPERTIES =
            register("merge_crystal_properties", () -> AltarOutputMergeCrystalProperties.TYPE);
    public static final AltarRecipeOutputModifier.Type<AltarOutputSetFlag> SET_FLAG =
            register("set_flag", () -> AltarOutputSetFlag.TYPE);
    public static final AltarRecipeOutputModifier.Type<AltarOutputGenerateArtifactShardLoot> GENERATE_ARTIFACT_SHARD_LOOT =
            register("generate_artifact_shard_loot", () -> AltarOutputGenerateArtifactShardLoot.TYPE);
    public static final AltarRecipeOutputModifier.Type<AltarOutputCopyDataComponents> COPY_DATA_COMPONENTS =
            register("copy_data_components", () -> AltarOutputCopyDataComponents.TYPE);
    public static final AltarRecipeOutputModifier.Type<AltarOutputSetCrystalCount> SET_CRYSTAL_COUNT =
            register("set_crystal_count", () -> AltarOutputSetCrystalCount.TYPE);

    private static <T extends AltarRecipeOutputModifier> AltarRecipeOutputModifier.Type<T> register(String name, Supplier<AltarRecipeOutputModifier.Type<T>> supplier) {
        return Registry.register(RegistriesAS.REGISTRY_ALTAR_OUTPUT_MODIFIER_TYPES, AstralSorcery.key(name), supplier.get());
    }

}
