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
import hellfirepvp.astralsorcery.common.artifact.ArtifactStability;
import hellfirepvp.astralsorcery.common.component.ArtifactComponent;
import hellfirepvp.astralsorcery.common.item.ArtifactItem;
import hellfirepvp.astralsorcery.common.lib.DataComponentsAS;
import hellfirepvp.astralsorcery.common.lib.RegistriesAS;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.ChatFormatting;
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
 * Class: IsStableArtifactIngredient
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class IsStableArtifactIngredient implements CustomIngredient {

    public static final IsStableArtifactIngredient INSTANCE = new IsStableArtifactIngredient();
    public static final MapCodec<IsStableArtifactIngredient> CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, IsStableArtifactIngredient> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    public static final CustomIngredientSerializer<IsStableArtifactIngredient> SERIALIZER = new CustomIngredientSerializer<>() {
        private static final ResourceLocation ID = AstralSorcery.key("is_stable_artifact");

        @Override
        public ResourceLocation getIdentifier() {
            return ID;
        }

        @Override
        public MapCodec<IsStableArtifactIngredient> getCodec(boolean allowEmpty) {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, IsStableArtifactIngredient> getPacketCodec() {
            return STREAM_CODEC;
        }
    };

    private IsStableArtifactIngredient() {
    }

    @Override
    public boolean test(ItemStack stack) {
        ArtifactComponent cmp = stack.get(DataComponentsAS.ARTIFACT);
        return cmp != null && cmp.stability() == ArtifactStability.STABLE;
    }

    @Override
    public List<ItemStack> getMatchingStacks() {
        Component display = Component.translatable("ingredient.astralsorcery.stable_artifact.description")
                .withStyle(ChatFormatting.GOLD);
        return RegistriesAS.REGISTRY_ARTIFACT_TYPES.stream()
                .map(ArtifactItem::create)
                .peek(artifact -> {
                    ArtifactComponent cmp = artifact.get(DataComponentsAS.ARTIFACT);
                    if (cmp != null) {
                        cmp = cmp.changeStability(ArtifactStability.STABLE);
                        artifact.set(DataComponentsAS.ARTIFACT, cmp);
                    }
                    artifact.set(DataComponents.ITEM_NAME, display);
                }).toList();
    }

    @Override
    public boolean requiresTesting() {
        return true;
    }

    @Override
    public CustomIngredientSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}
