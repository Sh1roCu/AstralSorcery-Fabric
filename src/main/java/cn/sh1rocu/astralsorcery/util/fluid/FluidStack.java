package cn.sh1rocu.astralsorcery.util.fluid;

import cn.sh1rocu.astralsorcery.util.CodecUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Predicate;

public class FluidStack {
    public static final Codec<Holder<Fluid>> FLUID_NON_EMPTY_CODEC = BuiltInRegistries.FLUID.holderByNameCodec().validate(
            holder -> holder.is(Fluids.EMPTY.builtInRegistryHolder()) ? DataResult.error(() -> "Fluid must not be minecraft:empty") :
                    DataResult.success(holder));
    public static final Codec<FluidStack> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    FluidVariant.CODEC.fieldOf("fluid_variant").forGetter(FluidStack::getFluidVariant),
                    CodecUtil.POSITIVE_LONG.fieldOf("amount").forGetter(FluidStack::getAmount))
            .apply(instance, FluidStack::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, FluidStack> STREAM_CODEC = StreamCodec.composite(
            FluidVariant.PACKET_CODEC, FluidStack::getFluidVariant,
            ByteBufCodecs.VAR_LONG, FluidStack::getAmount,
            FluidStack::new
    );

    private FluidVariant fluidVariant;
    private long amount;

    public static final FluidStack EMPTY = new FluidStack(FluidVariant.blank(), 0L);

    public FluidStack(FluidVariant fluidVariant, long amount) {
        this.fluidVariant = fluidVariant;
        this.amount = amount;
    }

    public FluidStack(Holder<Fluid> fluid, long amount) {
        this(FluidVariant.of(fluid.value()), amount);
    }

    public FluidStack(Fluid fluid, long amount) {
        this(FluidVariant.of(fluid), amount);
    }

    public static int hashFluidAndComponents(@Nullable FluidStack stack) {
        if (stack != null) {
            int i = 31 + stack.getFluid().hashCode();
            return 31 * i + stack.fluidVariant.getComponents().hashCode();
        } else {
            return 0;
        }
    }

    public static boolean isSameFluidSameComponents(FluidStack first, FluidStack second) {
        if (!first.is(second.getFluid())) {
            return false;
        } else {
            return first.isEmpty() && second.isEmpty() || Objects.equals(first.fluidVariant.getComponents(), second.fluidVariant.getComponents());
        }
    }

    public FluidVariant getFluidVariant() {
        return fluidVariant;
    }

    public void setFluidVariant(FluidVariant fluidVariant) {
        this.fluidVariant = fluidVariant;
    }

    public Fluid getFluid() {
        return this.isEmpty() ? Fluids.EMPTY : this.fluidVariant.getFluid();
    }

    public Holder<Fluid> getFluidHolder() {
        return this.getFluid().builtInRegistryHolder();
    }

    public boolean is(TagKey<Fluid> tag) {
        return this.getFluid().builtInRegistryHolder().is(tag);
    }

    public boolean is(Fluid fluid) {
        return this.getFluid() == fluid;
    }

    public boolean is(Predicate<Holder<Fluid>> holderPredicate) {
        return holderPredicate.test(this.getFluidHolder());
    }

    public boolean is(Holder<Fluid> holder) {
        return is(holder.value());
    }

    public boolean is(HolderSet<Fluid> holderSet) {
        return holderSet.contains(this.getFluidHolder());
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }


    public void grow(long addedAmount) {
        this.setAmount(this.getAmount() + addedAmount);
    }

    public void shrink(long removedAmount) {
        this.grow(-removedAmount);
    }

    public Component getHoverName() {
        return FluidVariantAttributes.getName(fluidVariant);
    }

    public boolean isEmpty() {
        return this == EMPTY || this.fluidVariant.isBlank() || this.amount <= 0;
    }

    public FluidStack copy() {
        if (this.isEmpty()) {
            return EMPTY;
        }
        return new FluidStack(fluidVariant, amount);
    }

    public FluidStack copyWithAmount(long amount) {
        if (this.isEmpty()) {
            return EMPTY;
        } else {
            FluidStack fluidStack = this.copy();
            fluidStack.setAmount(amount);
            return fluidStack;
        }
    }
}
