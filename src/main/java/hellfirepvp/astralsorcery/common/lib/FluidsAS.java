/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib;

import cn.sh1rocu.astralsorcery.util.fluid.FluidStack;
import cn.sh1rocu.astralsorcery.util.neoforge.common.TriPredicate;
import cn.sh1rocu.astralsorcery.util.neoforge.fluids.BaseFlowingFluid;
import cn.sh1rocu.astralsorcery.util.neoforge.fluids.FluidInteractionRegistry;
import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.block.fluid.LiquidStarlightBlock;
import hellfirepvp.astralsorcery.common.item.base.CreativeTabBucketItem;
import hellfirepvp.astralsorcery.common.util.TriFunction;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributeHandler;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public class FluidsAS {

    public static void init() {
        FluidVariantAttributes.register(LIQUID_STARLIGHT.getSource(), new FluidVariantAttributeHandler() {
            @Override
            public Component getName(FluidVariant fluidVariant) {
                return FluidVariantAttributeHandler.super.getName(fluidVariant).copy().withStyle(Rarity.EPIC.color());
            }

            @Override
            public Optional<SoundEvent> getFillSound(FluidVariant variant) {
                return Optional.of(SoundEvents.BUCKET_FILL);
            }

            @Override
            public Optional<SoundEvent> getEmptySound(FluidVariant variant) {
                return Optional.of(SoundEvents.BUCKET_EMPTY);
            }

            @Override
            public int getLuminance(FluidVariant variant) {
                return 15;
            }

            @Override
            public int getTemperature(FluidVariant variant) {
                return 10;
            }

            @Override
            public int getViscosity(FluidVariant variant, @Nullable Level world) {
                return 300;
            }
        });
    }

    public static final TagKey<Fluid> LIQUID_STARLIGHT_KEY = TagKey.create(Registries.FLUID, AstralSorcery.key("liquid_starlight"));

    public static FluidLiquidStarlight LIQUID_STARLIGHT = new FluidLiquidStarlight("liquid_starlight");


    public static final FlowingFluid LIQUID_STARLIGHT_SOURCE = register("liquid_starlight", () -> new LiquidStarlightFluid.Source(createProperties()));
    public static final FlowingFluid LIQUID_STARLIGHT_FLOWING = register("liquid_starlight" + "_flowing", () -> new LiquidStarlightFluid.Flowing(createProperties()));
    public static final LiquidBlock LIQUID_STARLIGHT_BLOCK = BlocksAS.register("liquid_starlight", () -> new LiquidStarlightBlock(LIQUID_STARLIGHT_SOURCE));
    public static final BucketItem LIQUID_STARLIGHT_BUCKET = ItemsAS.register("liquid_starlight" + "_bucket", () -> new CreativeTabBucketItem(LIQUID_STARLIGHT_SOURCE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    private static <T extends Fluid> T register(String name, Supplier<T> supplier) {
        return Registry.register(BuiltInRegistries.FLUID, AstralSorcery.key(name), supplier.get());
    }

    private static BaseFlowingFluid.Properties createProperties() {
        return new BaseFlowingFluid.Properties()
                .tickRate(1);
    }

    public static class FluidLiquidStarlight {

        private final ResourceLocation stillTexture;
        private final ResourceLocation flowingTexture;
        private final int tint;

        public FluidLiquidStarlight(String fluidName) {
            this.stillTexture = AstralSorcery.key("block/" + fluidName + "_still");
            this.flowingTexture = AstralSorcery.key("block/" + fluidName + "_flowing");
            this.tint = 0xCCFFFFFF;
        }

        public ResourceLocation getStillTexture() {
            return stillTexture;
        }

        public ResourceLocation getFlowingTexture() {
            return flowingTexture;
        }

        public int getTint() {
            return tint;
        }

        public LiquidBlock getFluidBlock() {
            return LIQUID_STARLIGHT_BLOCK;
        }

        public BucketItem getBucket() {
            return LIQUID_STARLIGHT_BUCKET;
        }

        public FlowingFluid getSource() {
            return LIQUID_STARLIGHT_SOURCE;
        }

        public FlowingFluid getFlowing() {
            return LIQUID_STARLIGHT_FLOWING;
        }

        public boolean isSource(FluidState state) {
            return state.is(this.getSource());
        }

        public boolean isFlowing(FluidState state) {
            return state.is(this.getFlowing());
        }

        public FluidStack stack(long amount) {
            return new FluidStack(this.getSource(), amount);
        }
    }

    public static void addLiquidInteractions() {
        addInteractionNoSound(FluidsAS.LIQUID_STARLIGHT.getSource(),
                (level, pos, otherType) -> {
                    var handler = FluidVariantAttributes.getHandler(otherType);
                    return handler != null && handler.getTemperature(FluidVariant.of(otherType)) <= 600;
                },
                (level, pos, state) -> Blocks.PACKED_ICE.defaultBlockState());
        addInteractionNoSound(FluidsAS.LIQUID_STARLIGHT.getSource(),
                (level, pos, otherType) -> {
                    var handler = FluidVariantAttributes.getHandler(otherType);
                    return handler != null && handler.getTemperature(FluidVariant.of(otherType)) > 600;
                },
                (level, pos, state) -> {
                    if (level.getRandom().nextInt(800) == 0) {
                        return BlocksAS.AQUAMARINE_SHALE.defaultBlockState();
                    }
                    return Blocks.SAND.defaultBlockState();
                });
    }

    public static void addInteractionNoSound(Fluid fluid, TriPredicate<Level, BlockPos, Fluid> otherTypeTest, TriFunction<Level, BlockPos, FluidState, BlockState> getInteractedState) {
        FluidInteractionRegistry.addInteraction(fluid, new FluidInteractionRegistry.InteractionInformation(
                (level, currentPos, relativePos, currentState) -> {
                    Fluid otherType = level.getFluidState(relativePos).getType();
                    return otherType != Fluids.EMPTY && otherType != fluid && otherTypeTest.test(level, relativePos, otherType);
                }, (level, currentPos, relativePos, currentState) -> {
            level.setBlockAndUpdate(currentPos, /*EventHooks.fireFluidPlaceBlockEvent(level, currentPos, currentPos,*/
                    getInteractedState.apply(level, currentPos, currentState)/*)*/);
        }));
    }

    public abstract static class LiquidStarlightFluid extends BaseFlowingFluid {

        protected LiquidStarlightFluid(Properties properties) {
            super(properties);
        }

        @Override
        public Item getBucket() {
            return LIQUID_STARLIGHT_BUCKET;
        }

        @Override
        protected BlockState createLegacyBlock(FluidState state) {
            if (LIQUID_STARLIGHT_BLOCK != null)
                return LIQUID_STARLIGHT_BLOCK.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
            return Blocks.AIR.defaultBlockState();
        }

        @Override
        public Fluid getFlowing() {
            return LIQUID_STARLIGHT_FLOWING;
        }

        @Override
        public Fluid getSource() {
            return LIQUID_STARLIGHT_SOURCE;
        }

        public static class Flowing extends LiquidStarlightFluid {

            public Flowing(Properties properties) {
                super(properties);
                registerDefaultState(getStateDefinition().any().setValue(LEVEL, 7));
            }

            @Override
            protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
                super.createFluidStateDefinition(builder);
                builder.add(LEVEL);
            }

            @Override
            public int getAmount(FluidState state) {
                return state.getValue(LEVEL);
            }

            @Override
            public boolean isSource(FluidState state) {
                return false;
            }
        }

        public static class Source extends LiquidStarlightFluid {

            public Source(Properties properties) {
                super(properties);
            }

            @Override
            public int getAmount(FluidState state) {
                return 8;
            }

            @Override
            public boolean isSource(FluidState state) {
                return true;
            }
        }
    }
}
