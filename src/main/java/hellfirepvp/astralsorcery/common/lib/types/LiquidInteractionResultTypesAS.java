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
import hellfirepvp.astralsorcery.common.recipe.liquid.interaction.result.LiquidInteractionResult;
import hellfirepvp.astralsorcery.common.recipe.liquid.interaction.result.LiquidInteractionResultDropItem;
import hellfirepvp.astralsorcery.common.recipe.liquid.interaction.result.LiquidInteractionResultSpawnEntity;
import net.minecraft.core.Registry;

import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: LiquidInteractionResultTypesAS
 * Created by HellFirePvP
 * Date: 11.04.2026
 */
public class LiquidInteractionResultTypesAS {

    public static void init() {

    }

    public static final LiquidInteractionResult.Type<LiquidInteractionResultDropItem> DROP_ITEM =
            register("drop_item", () -> LiquidInteractionResultDropItem.TYPE);

    public static final LiquidInteractionResult.Type<LiquidInteractionResultSpawnEntity> SPAWN_ENTITY =
            register("spawn_entity", () -> LiquidInteractionResultSpawnEntity.TYPE);

    private static <T extends LiquidInteractionResult> LiquidInteractionResult.Type<T> register(String name, Supplier<LiquidInteractionResult.Type<T>> supplier) {
        return Registry.register(RegistriesAS.REGISTRY_LIQUID_INTERACTION_RESULT_TYPES, AstralSorcery.key(name), supplier.get());
    }
}
