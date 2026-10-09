/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib;

import hellfirepvp.astralsorcery.common.ingredient.*;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: IngredientsAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class IngredientsAS {

    public static void init() {
        register(IngredientBridge.SERIALIZER);
        register(IsEnchantedIngredient.SERIALIZER);
        register(IsStableArtifactIngredient.SERIALIZER);
        register(IsFlagSetIngredient.SERIALIZER);
        register(IsLumenBindableIngredient.SERIALIZER);
        register(HasStoredLumenIngredient.SERIALIZER);
    }

    private static <T extends CustomIngredient> void register(CustomIngredientSerializer<T> serializer) {
        CustomIngredientSerializer.register(serializer);
    }
}
