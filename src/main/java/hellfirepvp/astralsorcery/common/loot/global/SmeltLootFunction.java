/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.loot.global;

import cn.sh1rocu.observerlib.ObserverLibFabric;
import com.google.common.collect.Lists;
import hellfirepvp.astralsorcery.common.lib.EnchantmentsAS;
import hellfirepvp.astralsorcery.common.util.LootUtil;
import hellfirepvp.astralsorcery.common.util.MiscUtil;
import hellfirepvp.astralsorcery.common.util.RecipeFinder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: SmeltLootFunction
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class SmeltLootFunction {

    private static final List<LootItemCondition> LOOT_CONDITIONS = Lists.newArrayList(new MatchTool(Optional.of(ItemPredicate.Builder.item()
            .withSubPredicate(ItemSubPredicates.ENCHANTMENTS,
                    ItemEnchantmentsPredicate.enchantments(List.of(
                            new EnchantmentPredicate(ObserverLibFabric.getServer().registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
                                    .getOrThrow(EnchantmentsAS.SCORCHING_HEAT.id()), MinMaxBounds.Ints.atLeast(1))
                    )))
            .build())));

    public static ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for (LootItemCondition condition : LOOT_CONDITIONS) {
            if (!condition.test(context)) {
                return generatedLoot;
            }
        }
        if (!LootUtil.doesContextFulfill(context, LootContextParamSets.BLOCK)) return generatedLoot;
        ServerLevel level = context.getLevel();

        return generatedLoot.stream()
                .filter(stack -> !stack.isEmpty())
                .map(lootStack -> {
                    return RecipeFinder.of(level).findSmeltingRecipe(level, lootStack).map(recipeHolder -> {
                        SingleRecipeInput input = new SingleRecipeInput(lootStack);
                        AbstractCookingRecipe recipe = recipeHolder.value();
                        ItemStack result = recipe.assemble(input, level.registryAccess());
                        float exp = recipe.getExperience();

                        if (context.hasParam(LootContextParams.THIS_ENTITY)) {
                            Entity e = context.getParam(LootContextParams.THIS_ENTITY);
                            if (e instanceof Player player) {
                                // TODO?
                                // EventHooks.firePlayerSmeltedEvent(player, result);
                            }
                        }

                        ItemStack tool = context.getParam(LootContextParams.TOOL);
                        if (!tool.isEmpty() && !(result.getItem() instanceof BlockItem)) {
                            var holder = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
                            Holder<Enchantment> silkTouch = holder.getOrThrow(Enchantments.SILK_TOUCH);
                            int silkTouchLevel = EnchantmentHelper.getItemEnchantmentLevel(silkTouch, tool);
                            if (silkTouchLevel <= 0) {

                                int extraCount = 0;
                                Holder<Enchantment> fortune = holder.getOrThrow(Enchantments.FORTUNE);
                                int fortuneLevel = EnchantmentHelper.getItemEnchantmentLevel(fortune, tool);
                                if (fortuneLevel > 0) {
                                    extraCount = Math.max(context.getRandom().nextInt(fortuneLevel + 2) - 1, 0);
                                    result.setCount(result.getCount() * (extraCount + 1));
                                }

                                exp *= (extraCount + 1);
                                if (exp > 0) {
                                    int expCount = MiscUtil.roundChanced(exp, context.getRandom());
                                    if (expCount > 0) {
                                        Vec3 dropPos = context.getParamOrNull(LootContextParams.ORIGIN);
                                        if (dropPos != null) {
                                            level.addFreshEntity(new ExperienceOrb(level, dropPos.x(), dropPos.y(), dropPos.z(), expCount));
                                        }
                                    }
                                }
                            }
                        }

                        return result;
                    }).orElse(lootStack);
                })
                .collect(Collectors.toCollection(ObjectArrayList::of));
    }
}
