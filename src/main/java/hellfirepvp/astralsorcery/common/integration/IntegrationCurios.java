/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.integration;

import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: IntegrationCurios
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class IntegrationCurios {

    public static List<ItemStack> getCurio(LivingEntity le, Predicate<ItemStack> stack) {
        List<ItemStack> stacks = new ArrayList<>();
        TrinketsApi.getTrinketComponent(le).ifPresent(component -> {
            var allEquipped = component.getAllEquipped();
            if (allEquipped != null) {
                allEquipped.forEach(tuple -> {
                    ItemStack curioSlotStack = tuple.getB();
                    if (stack.test(curioSlotStack)) {
                        stacks.add(curioSlotStack);
                    }
                });
            }
        });
        return stacks;
    }
}
