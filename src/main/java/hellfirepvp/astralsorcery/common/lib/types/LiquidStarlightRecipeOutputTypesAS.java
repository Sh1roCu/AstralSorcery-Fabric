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
import hellfirepvp.astralsorcery.common.recipe.liquid.output.*;
import net.minecraft.core.Registry;

import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: LiquidStarlightRecipeOutputTypesAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class LiquidStarlightRecipeOutputTypesAS {

    public static void init() {

    }

    public static final LiquidStarlightRecipeOutputModifier.Type<LiquidStarlightOutputDropItem> DROP_ITEM =
            register("drop_item", () -> LiquidStarlightOutputDropItem.TYPE);
    public static final LiquidStarlightRecipeOutputModifier.Type<LiquidStarlightOutputMergeCrystal> MERGE_CRYSTAL =
            register("merge_crystal", () -> LiquidStarlightOutputMergeCrystal.TYPE);
    public static final LiquidStarlightRecipeOutputModifier.Type<LiquidStarlightOutputFormCrystalCluster> FORM_CRYSTAL_CLUSTER =
            register("form_crystal_cluster", () -> LiquidStarlightOutputFormCrystalCluster.TYPE);
    public static final LiquidStarlightRecipeOutputModifier.Type<LiquidStarlightOutputFormGemCrystalCluster> FORM_GEM_CRYSTAL_CLUSTER =
            register("form_gem_crystal_cluster", () -> LiquidStarlightOutputFormGemCrystalCluster.TYPE);
    public static final LiquidStarlightRecipeOutputModifier.Type<LiquidStarlightOutputGrowSize> GROW_SIZE =
            register("grow_size", () -> LiquidStarlightOutputGrowSize.TYPE);
    public static final LiquidStarlightRecipeOutputModifier.Type<LiquidStarlightOutputBindLumen> BIND_LUMEN =
            register("bind_lumen", () -> LiquidStarlightOutputBindLumen.TYPE);
    public static final LiquidStarlightRecipeOutputModifier.Type<LiquidStarlightOutputFillLumen> FILL_LUMEN =
            register("fill_lumen", () -> LiquidStarlightOutputFillLumen.TYPE);

    private static <T extends LiquidStarlightRecipeOutputModifier> LiquidStarlightRecipeOutputModifier.Type<T> register(String name, Supplier<LiquidStarlightRecipeOutputModifier.Type<T>> supplier) {
        return Registry.register(RegistriesAS.REGISTRY_LIQUID_STARLIGHT_OUTPUT_MODIFIER_TYPES, AstralSorcery.key(name), supplier.get());
    }
}
