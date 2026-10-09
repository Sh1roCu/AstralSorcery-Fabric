/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.ingredient;

import cn.sh1rocu.astralsorcery.util.neoforge.network.codec.NeoForgeStreamCodecs;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.component.FlagsComponent;
import hellfirepvp.astralsorcery.common.lib.DataComponentsAS;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Locale;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: IsFlagSetIngredient
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public record IsFlagSetIngredient(FlagsComponent.Flag flag, FlagState state) implements CustomIngredient {

    public static final MapCodec<IsFlagSetIngredient> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            FlagsComponent.Flag.CODEC.fieldOf("flag").forGetter(IsFlagSetIngredient::flag),
            StringRepresentable.fromEnum(FlagState::values).fieldOf("state").forGetter(IsFlagSetIngredient::state)
    ).apply(inst, IsFlagSetIngredient::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, IsFlagSetIngredient> STREAM_CODEC = StreamCodec.composite(
            NeoForgeStreamCodecs.enumCodec(FlagsComponent.Flag.class), IsFlagSetIngredient::flag,
            NeoForgeStreamCodecs.enumCodec(FlagState.class), IsFlagSetIngredient::state,
            IsFlagSetIngredient::new
    );

    public static final CustomIngredientSerializer<IsFlagSetIngredient> SERIALIZER = new CustomIngredientSerializer<>() {
        private static final ResourceLocation ID = AstralSorcery.key("is_flag_set");

        @Override
        public ResourceLocation getIdentifier() {
            return ID;
        }

        @Override
        public MapCodec<IsFlagSetIngredient> getCodec(boolean allowEmpty) {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, IsFlagSetIngredient> getPacketCodec() {
            return STREAM_CODEC;
        }
    };

    public static IsFlagSetIngredient of(FlagsComponent.Flag flag, boolean set) {
        return new IsFlagSetIngredient(flag, set ? FlagState.IS_SET : FlagState.IS_NOT_SET);
    }

    @Override
    public boolean test(ItemStack stack) {
        FlagsComponent cmp = stack.getOrDefault(DataComponentsAS.FLAGS, FlagsComponent.EMPTY);
        return switch (this.state) {
            case IS_SET -> cmp.isSet(this.flag);
            case IS_NOT_SET -> !cmp.isSet(this.flag);
        };
    }

    @Override
    public List<ItemStack> getMatchingStacks() {
        return List.of();
    }

    @Override
    public boolean requiresTesting() {
        return true;
    }

    @Override
    public CustomIngredientSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public enum FlagState implements StringRepresentable {

        IS_SET,
        IS_NOT_SET;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
