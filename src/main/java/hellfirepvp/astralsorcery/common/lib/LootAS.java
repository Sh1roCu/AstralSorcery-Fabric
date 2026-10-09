/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.loot.*;
import hellfirepvp.astralsorcery.common.loot.condition.LockableTileEntityCondition;
import hellfirepvp.astralsorcery.common.loot.condition.PlayerNearbyCondition;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: LootAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class LootAS {

    public static void init() {

    }

    public static final LootItemFunctionType<LinearLuckFunction> LINEAR_LUCK_FUNCTION =
            registerFunc("linear_luck", () -> new LootItemFunctionType<>(LinearLuckFunction.CODEC));
    public static final LootItemFunctionType<GenerateCrystalPropertiesFunction> GENERATE_CRYSTAL_PROPERTIES_FUNCTION =
            registerFunc("generate_crystal_properties", () -> new LootItemFunctionType<>(GenerateCrystalPropertiesFunction.CODEC));
    public static final LootItemFunctionType<CopyCrystalPropertiesFunction> COPY_CRYSTAL_PROPERTIES_FUNCTION =
            registerFunc("copy_crystal_properties", () -> new LootItemFunctionType<>(CopyCrystalPropertiesFunction.CODEC));
    public static final LootItemFunctionType<CopyConstellationFunction> COPY_CONSTELLATION_FUNCTION =
            registerFunc("copy_constellation", () -> new LootItemFunctionType<>(CopyConstellationFunction.CODEC));
    public static final LootItemFunctionType<CopyLumenFunction> COPY_LUMEN_FUNCTION =
            registerFunc("copy_lumen", () -> new LootItemFunctionType<>(CopyLumenFunction.CODEC));
    public static final LootItemFunctionType<GenerateRandomArtifactFunction> GENERATE_RANDOM_ARTIFACT_FUNCTION =
            registerFunc("generate_random_artifact", () -> new LootItemFunctionType<>(GenerateRandomArtifactFunction.CODEC));
    public static final LootItemFunctionType<GenerateRandomArtifactTypeFunction> GENERATE_RANDOM_ARTIFACT_TYPE_FUNCTION =
            registerFunc("generate_random_artifact_type", () -> new LootItemFunctionType<>(GenerateRandomArtifactTypeFunction.CODEC));
    public static final LootItemFunctionType<GenerateDynamismGemRollsFunction> GENERATE_DYNAMISM_GEM_ROLLS_FUNCTION =
            registerFunc("generate_dynamism_gem_rolls", () -> new LootItemFunctionType<>(GenerateDynamismGemRollsFunction.CODEC));


    public static final LootItemConditionType PLAYER_NEARBY_CONDITION =
            registerCon("player_nearby", () -> new LootItemConditionType(PlayerNearbyCondition.CODEC));
    public static final LootItemConditionType LOCKABLE_TILE_ENTITY_CONDITION =
            registerCon("lockable_tile_entity", () -> new LootItemConditionType(LockableTileEntityCondition.CODEC));

    private static <T extends LootItemFunction> LootItemFunctionType<T> registerFunc(String name, Supplier<LootItemFunctionType<T>> supplier) {
        return Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, AstralSorcery.key(name), supplier.get());
    }

    private static <T extends LootItemConditionType> T registerCon(String name, Supplier<T> supplier) {
        return Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, AstralSorcery.key(name), supplier.get());
    }

}
