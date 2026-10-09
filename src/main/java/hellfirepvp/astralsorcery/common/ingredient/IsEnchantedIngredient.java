/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.ingredient;

import com.mojang.serialization.MapCodec;
import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.util.MiscUtil;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: IsEnchantedIngredient
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class IsEnchantedIngredient implements CustomIngredient {

    public static final IsEnchantedIngredient INSTANCE = new IsEnchantedIngredient();
    public static final MapCodec<IsEnchantedIngredient> CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, IsEnchantedIngredient> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    public static final CustomIngredientSerializer<IsEnchantedIngredient> SERIALIZER = new CustomIngredientSerializer<>() {
        private static final ResourceLocation ID = AstralSorcery.key("is_enchanted");

        @Override
        public ResourceLocation getIdentifier() {
            return ID;
        }

        @Override
        public MapCodec<IsEnchantedIngredient> getCodec(boolean allowEmpty) {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, IsEnchantedIngredient> getPacketCodec() {
            return STREAM_CODEC;
        }
    };

    private static List<ItemStack> inputDisplayCache = null;

    private IsEnchantedIngredient() {
    }

    @Override
    public boolean test(ItemStack stack) {
        if (getDisplayCache().contains(stack)) return true; //Hard-test against specifically created display objects
        return EnchantmentHelper.hasAnyEnchantments(stack);
    }

    @Override
    public List<ItemStack> getMatchingStacks() {
        return getDisplayCache();
    }

    @Override
    public boolean requiresTesting() {
        return true;
    }

    @Override
    public CustomIngredientSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    private static List<ItemStack> getDisplayCache() {
        if (inputDisplayCache != null) return inputDisplayCache;

        Component display = Component.translatable("ingredient.astralsorcery.is_enchanted.description");
        inputDisplayCache = BuiltInRegistries.ITEM.stream()
                .map(Item::getDefaultInstance)
                .filter(ItemStack::isEnchantable)
                .peek(stack -> stack.set(DataComponents.ITEM_NAME, display))
                .collect(MiscUtil.collectShuffledList(ArrayList::new));
        return inputDisplayCache;
    }

    public static void clearDisplayCache() {
        inputDisplayCache = null;
    }
}
