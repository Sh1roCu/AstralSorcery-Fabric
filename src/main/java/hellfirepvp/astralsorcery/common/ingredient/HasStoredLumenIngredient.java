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
import hellfirepvp.astralsorcery.common.component.StoredLumenComponent;
import hellfirepvp.astralsorcery.common.lib.DataComponentsAS;
import hellfirepvp.astralsorcery.common.lib.ItemsAS;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: HasStoredLumenIngredient
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class HasStoredLumenIngredient implements CustomIngredient {

    public static final HasStoredLumenIngredient INSTANCE = new HasStoredLumenIngredient();
    public static final MapCodec<HasStoredLumenIngredient> CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, HasStoredLumenIngredient> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    public static final CustomIngredientSerializer<HasStoredLumenIngredient> SERIALIZER = new CustomIngredientSerializer<>() {
        private static final ResourceLocation ID = AstralSorcery.key("has_stored_lumen");

        @Override
        public ResourceLocation getIdentifier() {
            return ID;
        }

        @Override
        public MapCodec<HasStoredLumenIngredient> getCodec(boolean allowEmpty) {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, HasStoredLumenIngredient> getPacketCodec() {
            return STREAM_CODEC;
        }
    };

    private static ItemStack displayStack = null;

    private HasStoredLumenIngredient() {
    }

    @Override
    public boolean test(ItemStack stack) {
        if (stack.equals(displayStack)) return true; //Hard-test against specifically created display objects
        return !stack.getOrDefault(DataComponentsAS.STORED_LUMEN, StoredLumenComponent.EMPTY).isEmpty();
    }

    @Override
    public List<ItemStack> getMatchingStacks() {
        if (displayStack != null) return List.of(displayStack);

        displayStack = ItemsAS.STARDUST.getDefaultInstance();
        displayStack.set(DataComponents.ITEM_NAME, Component.translatable("ingredient.astralsorcery.has_stored_lumen.description"));

        return List.of(displayStack);
    }

    @Override
    public boolean requiresTesting() {
        return true;
    }

    @Override
    public CustomIngredientSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static void clearDisplayCache() {
        displayStack = null;
    }
}
