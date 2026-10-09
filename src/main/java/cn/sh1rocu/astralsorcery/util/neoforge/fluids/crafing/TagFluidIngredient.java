/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package cn.sh1rocu.astralsorcery.util.neoforge.fluids.crafing;

import cn.sh1rocu.astralsorcery.util.fluid.FluidStack;
import com.mojang.serialization.MapCodec;
import java.util.stream.Stream;

import hellfirepvp.astralsorcery.common.lib.RegistriesAS;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

/**
 * Fluid ingredient that matches all fluids within the given tag.
 * <p>
 * Unlike with ingredients, this is an explicit "type" of fluid ingredient,
 * though it may still be written without a type field, see {@link FluidIngredient#MAP_CODEC_NONEMPTY}
 */
public class TagFluidIngredient extends FluidIngredient {
    public static final MapCodec<TagFluidIngredient> CODEC = TagKey.codec(Registries.FLUID)
            .xmap(TagFluidIngredient::new, TagFluidIngredient::tag).fieldOf("tag");

    private final TagKey<Fluid> tag;

    public TagFluidIngredient(TagKey<Fluid> tag) {
        this.tag = tag;
    }

    @Override
    public boolean test(FluidStack fluidStack) {
        return fluidStack.is(tag);
    }

    @Override
    protected Stream<FluidStack> generateStacks() {
        return BuiltInRegistries.FLUID.getTag(tag)
                .stream()
                .flatMap(HolderSet::stream)
                .map(fluid -> new FluidStack(fluid, FluidConstants.BUCKET));
    }

    @Override
    public boolean isSimple() {
        return true;
    }

    @Override
    public FluidIngredientType<?> getType() {
        return RegistriesAS.TAG_FLUID_INGREDIENT_TYPE;
    }

    @Override
    public int hashCode() {
        return tag.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof TagFluidIngredient tag && tag.tag.equals(this.tag);
    }

    public TagKey<Fluid> tag() {
        return tag;
    }
}
