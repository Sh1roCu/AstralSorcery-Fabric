/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.ingredient;

import cn.sh1rocu.astralsorcery.util.neoforge.fluids.FluidUtil;
import cn.sh1rocu.astralsorcery.util.neoforge.fluids.crafing.SizedFluidIngredient;
import cn.sh1rocu.astralsorcery.util.transfer.ItemStackStorage;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.util.MiscUtil;
import hellfirepvp.astralsorcery.common.util.codec.CodecUtil;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: IngredientBridge
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public final class IngredientBridge implements CustomIngredient {

    public static final IngredientBridge EMPTY = of(Ingredient.EMPTY);
    public static final MapCodec<IngredientBridge> CODEC = StringRepresentable.fromEnum(Type::values)
            .dispatchMap(ingredient -> ingredient.type, Type::getIngredientCodec);
    public static final StreamCodec<RegistryFriendlyByteBuf, IngredientBridge> STREAM_CODEC =
            Type.STREAM_CODEC.dispatch(ing -> ing.type, Type::getIngredientStreamCodec);

    public static final CustomIngredientSerializer<IngredientBridge> SERIALIZER = new CustomIngredientSerializer<>() {
        private static final ResourceLocation ID = AstralSorcery.key("bridge");

        @Override
        public ResourceLocation getIdentifier() {
            return ID;
        }

        @Override
        public MapCodec<IngredientBridge> getCodec(boolean allowEmpty) {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, IngredientBridge> getPacketCodec() {
            return STREAM_CODEC;
        }
    };

    private final Type type;
    private final Ingredient ingredient;
    private final SizedFluidIngredient fluidIngredient;

    private IngredientBridge(Type type, Ingredient ingredient, SizedFluidIngredient fluidIngredient) {
        this.type = type;
        this.ingredient = ingredient;
        this.fluidIngredient = fluidIngredient;
    }

    public static IngredientBridge of(Ingredient ingredient) {
        return new IngredientBridge(Type.ITEM, ingredient, null);
    }

    public static IngredientBridge of(SizedFluidIngredient fluidIngredient) {
        return new IngredientBridge(Type.FLUID, null, fluidIngredient);
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public SizedFluidIngredient getFluidIngredient() {
        return this.fluidIngredient;
    }

    public Type getIngredientType() {
        return this.type;
    }

    @Override
    public boolean test(ItemStack stack) {
        return switch (this.type) {
            case ITEM -> this.ingredient.test(stack);
            case FLUID -> FluidUtil.getFluidContained(stack).map(this.fluidIngredient::test).orElse(false);
        };
    }

    @Override
    public List<ItemStack> getMatchingStacks() {
        return switch (this.type) {
            case ITEM -> Arrays.asList(this.ingredient.getItems());
            case FLUID -> Arrays.stream(this.fluidIngredient.getFluids()).map(FluidUtil::getFilledBucket).toList();
        };
    }

    @Override
    public boolean requiresTesting() {
        return switch (this.type) {
            case ITEM -> this.ingredient.requiresTesting();
            case FLUID -> !this.fluidIngredient.ingredient().isSimple();
        };
    }

    @Override
    public CustomIngredientSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public boolean isEmpty() {
        return switch (this.type) {
            case ITEM -> this.ingredient.isEmpty();
            case FLUID -> this.fluidIngredient.amount() <= 0 ||
                    this.fluidIngredient.ingredient().isEmpty() ||
                    this.fluidIngredient.ingredient().hasNoFluids();
        };
    }

    public boolean consume(Supplier<ItemStack> getter, Function<ItemStack, ItemStack> setter, Consumer<ItemStack> onRemainder, boolean simulate) {
        ItemStack extracted = getter.get();
        if (extracted.isEmpty()) return this.isEmpty();

        return switch (this.type) {
            case ITEM -> {
                ItemStack remainder = extracted.getRecipeRemainder().copy();
                if (!remainder.isEmpty()) {
                    if (!setter.apply(remainder).isEmpty() && !simulate) {
                        onRemainder.accept(remainder);
                    }
                }
                yield true;
            }
            case FLUID -> {
                ContainerItemContext context = ContainerItemContext.ofSingleSlot(new ItemStackStorage(extracted));
                var storage = context.find(FluidStorage.ITEM);
                if (storage == null) yield false;
                try (Transaction tx = Transaction.openOuter()) {
                    StorageUtil.extractAny(storage, this.fluidIngredient.amount(), tx);
                    if (!simulate) {
                        tx.commit();
                    }
                }

                ItemStack remainder = context.getItemVariant().toStack(extracted.getCount()).copy();
                if (!remainder.isEmpty()) {
                    if (!setter.apply(remainder).isEmpty() && !simulate) {
                        onRemainder.accept(remainder);
                    }
                }
                yield true;
            }
        };
    }

    public enum Type implements StringRepresentable {

        ITEM(RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(IngredientBridge::getIngredient)
        ).apply(inst, IngredientBridge::of)), StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC,
                IngredientBridge::getIngredient,
                IngredientBridge::of
        )),
        FLUID(RecordCodecBuilder.mapCodec(inst -> inst.group(
                SizedFluidIngredient.NESTED_CODEC.fieldOf("ingredient").forGetter(IngredientBridge::getFluidIngredient)
        ).apply(inst, IngredientBridge::of)), StreamCodec.composite(
                SizedFluidIngredient.STREAM_CODEC,
                IngredientBridge::getFluidIngredient,
                IngredientBridge::of
        ));

        private static final StreamCodec<RegistryFriendlyByteBuf, Type> STREAM_CODEC = MiscUtil.cast(CodecUtil.enumStreamCodec(Type.class));

        private final MapCodec<IngredientBridge> ingredientCodec;
        private final StreamCodec<RegistryFriendlyByteBuf, IngredientBridge> ingredientStreamCodec;

        Type(MapCodec<IngredientBridge> ingredientCodec, StreamCodec<RegistryFriendlyByteBuf, IngredientBridge> ingredientStreamCodec) {
            this.ingredientCodec = ingredientCodec;
            this.ingredientStreamCodec = ingredientStreamCodec;
        }

        public MapCodec<? extends IngredientBridge> getIngredientCodec() {
            return this.ingredientCodec;
        }

        public StreamCodec<RegistryFriendlyByteBuf, ? extends IngredientBridge> getIngredientStreamCodec() {
            return this.ingredientStreamCodec;
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
