/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.event;

import cn.sh1rocu.astralsorcery.api.event.BaseEvent;
import cn.sh1rocu.astralsorcery.api.event.ICancellableEvent;
import hellfirepvp.astralsorcery.common.lumen.LumenStack;
import hellfirepvp.astralsorcery.common.recipe.CustomRecipe;
import hellfirepvp.astralsorcery.common.recipe.CustomRecipeInput;
import hellfirepvp.astralsorcery.common.recipe.altar.ActiveAltarRecipe;
import hellfirepvp.astralsorcery.common.recipe.altar.AltarCraftingInput;
import hellfirepvp.astralsorcery.common.recipe.altar.AltarRecipe;
import hellfirepvp.astralsorcery.common.recipe.attunement.AttunementRecipe;
import hellfirepvp.astralsorcery.common.recipe.focal.drop.ActiveFocalCombineRecipe;
import hellfirepvp.astralsorcery.common.recipe.focal.drop.FocalCombineCraftingInput;
import hellfirepvp.astralsorcery.common.recipe.focal.drop.FocalCombineRecipe;
import hellfirepvp.astralsorcery.common.recipe.focal.place.ActiveFocalTransmutationRecipe;
import hellfirepvp.astralsorcery.common.recipe.focal.place.FocalTransmutationCraftingInput;
import hellfirepvp.astralsorcery.common.recipe.focal.place.FocalTransmutationRecipe;
import hellfirepvp.astralsorcery.common.recipe.infusion.ActiveInfusionRecipe;
import hellfirepvp.astralsorcery.common.recipe.infusion.InfusionRecipe;
import hellfirepvp.astralsorcery.common.recipe.infusion.InfusionRecipeInput;
import hellfirepvp.astralsorcery.common.recipe.liquid.LiquidStarlightRecipe;
import hellfirepvp.astralsorcery.common.recipe.liquid.LiquidStarlightRecipeInput;
import hellfirepvp.astralsorcery.common.recipe.lumen.LumenCrystallizationRecipe;
import hellfirepvp.astralsorcery.common.recipe.lumen.LumenGenerationRecipe;
import hellfirepvp.astralsorcery.common.tile.TileLumenArray;
import hellfirepvp.astralsorcery.common.tile.TileLumenCrystallizer;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.state.BlockState;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: RecipeEvent
 * Created by HellFirePvP
 * Date: 05.10.2026 / 10:11
 */
public class RecipeEvent<T extends CustomRecipe<T, I>, I extends CustomRecipeInput> extends BaseEvent {

    private final RecipeHolder<T> recipe;

    protected RecipeEvent(RecipeHolder<T> recipe) {
        this.recipe = recipe;
    }

    public RecipeHolder<T> getRecipe() {
        return this.recipe;
    }

    public static class Altar extends RecipeEvent<AltarRecipe, AltarCraftingInput> {

        private final ActiveAltarRecipe activeRecipe;

        protected Altar(RecipeHolder<AltarRecipe> recipe, ActiveAltarRecipe activeRecipe) {
            super(recipe);
            this.activeRecipe = activeRecipe;
        }

        public ActiveAltarRecipe getActiveRecipe() {
            return this.activeRecipe;
        }

        public static class Start extends Altar implements ICancellableEvent {

            public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
                for (Callback callback : callbacks) {
                    callback.post(event);
                }
            });

            public Start(RecipeHolder<AltarRecipe> recipe, ActiveAltarRecipe activeRecipe) {
                super(recipe, activeRecipe);
            }

            public interface Callback {
                void post(Start event);
            }
        }

        public static class End extends Altar {

            public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
                for (Callback callback : callbacks) {
                    callback.post(event);
                }
            });

            public End(AltarRecipe recipe, ActiveAltarRecipe activeRecipe) {
                super(new RecipeHolder<>(activeRecipe.getRecipeId(), recipe), activeRecipe);
            }

            public interface Callback {
                void post(End event);
            }
        }
    }

    public static class Attunement extends BaseEvent {

        private final AttunementRecipe<?> recipe;
        private final AttunementRecipe.Active<?, ?> activeRecipe;

        protected Attunement(AttunementRecipe<?> recipe, AttunementRecipe.Active<?, ?> activeRecipe) {
            this.recipe = recipe;
            this.activeRecipe = activeRecipe;
        }

        public AttunementRecipe<?> getRecipe() {
            return this.recipe;
        }

        public AttunementRecipe.Active<?, ?> getActiveRecipe() {
            return this.activeRecipe;
        }

        public static class Start extends Attunement implements ICancellableEvent {

            public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
                for (Callback callback : callbacks) {
                    callback.post(event);
                }
            });

            public Start(AttunementRecipe<?> recipe, AttunementRecipe.Active<?, ?> activeRecipe) {
                super(recipe, activeRecipe);
            }

            public interface Callback {
                void post(Start event);
            }
        }

        public static class End extends Attunement {

            public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
                for (Callback callback : callbacks) {
                    callback.post(event);
                }
            });

            public End(AttunementRecipe<?> recipe, AttunementRecipe.Active<?, ?> activeRecipe) {
                super(recipe, activeRecipe);
            }

            public interface Callback {
                void post(End event);
            }
        }
    }

    public static class FocalCombine extends RecipeEvent<FocalCombineRecipe, FocalCombineCraftingInput> {

        private final ActiveFocalCombineRecipe activeRecipe;

        protected FocalCombine(RecipeHolder<FocalCombineRecipe> recipe, ActiveFocalCombineRecipe activeRecipe) {
            super(recipe);
            this.activeRecipe = activeRecipe;
        }

        public ActiveFocalCombineRecipe getActiveRecipe() {
            return this.activeRecipe;
        }

        public static class Start extends FocalCombine implements ICancellableEvent {

            public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
                for (Callback callback : callbacks) {
                    callback.post(event);
                }
            });

            public Start(ActiveFocalCombineRecipe activeRecipe) {
                super(activeRecipe.getRecipeHolder(), activeRecipe);
            }

            public interface Callback {
                void post(Start event);
            }
        }

        public static class End extends FocalCombine {

            public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
                for (Callback callback : callbacks) {
                    callback.post(event);
                }
            });

            public End(ActiveFocalCombineRecipe activeRecipe) {
                super(activeRecipe.getRecipeHolder(), activeRecipe);
            }

            public interface Callback {
                void post(End event);
            }
        }
    }

    public static class FocalTransmutation extends RecipeEvent<FocalTransmutationRecipe, FocalTransmutationCraftingInput> {

        private final ActiveFocalTransmutationRecipe activeRecipe;

        protected FocalTransmutation(RecipeHolder<FocalTransmutationRecipe> recipe, ActiveFocalTransmutationRecipe activeRecipe) {
            super(recipe);
            this.activeRecipe = activeRecipe;
        }

        public ActiveFocalTransmutationRecipe getActiveRecipe() {
            return this.activeRecipe;
        }

        public static class Start extends FocalTransmutation implements ICancellableEvent {

            public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
                for (Callback callback : callbacks) {
                    callback.post(event);
                }
            });

            public Start(ActiveFocalTransmutationRecipe activeRecipe) {
                super(activeRecipe.getRecipeHolder(), activeRecipe);
            }

            public interface Callback {
                void post(Start event);
            }
        }

        public static class End extends FocalTransmutation {

            public static final Event<End.Callback> EVENT = EventFactory.createArrayBacked(End.Callback.class, callbacks -> event -> {
                for (End.Callback callback : callbacks) {
                    callback.post(event);
                }
            });

            public End(ActiveFocalTransmutationRecipe activeRecipe) {
                super(activeRecipe.getRecipeHolder(), activeRecipe);
            }

            public interface Callback {
                void post(End event);
            }
        }
    }

    public static class Infusion extends RecipeEvent<InfusionRecipe, InfusionRecipeInput> {

        private final ActiveInfusionRecipe activeRecipe;

        protected Infusion(RecipeHolder<InfusionRecipe> recipe, ActiveInfusionRecipe activeRecipe) {
            super(recipe);
            this.activeRecipe = activeRecipe;
        }

        public ActiveInfusionRecipe getActiveRecipe() {
            return this.activeRecipe;
        }

        public static class Start extends Infusion implements ICancellableEvent {

            public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
                for (Callback callback : callbacks) {
                    callback.post(event);
                }
            });

            public Start(RecipeHolder<InfusionRecipe> recipe, ActiveInfusionRecipe activeRecipe) {
                super(recipe, activeRecipe);
            }

            public interface Callback {
                void post(Start event);
            }
        }

        public static class End extends Infusion {

            public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
                for (Callback callback : callbacks) {
                    callback.post(event);
                }
            });

            public End(InfusionRecipe recipe, ActiveInfusionRecipe activeRecipe) {
                super(new RecipeHolder<>(activeRecipe.getRecipeId(), recipe), activeRecipe);
            }

            public interface Callback {
                void post(End event);
            }
        }
    }

    public static class LiquidStarlight extends RecipeEvent<LiquidStarlightRecipe, LiquidStarlightRecipeInput> {

        protected LiquidStarlight(RecipeHolder<LiquidStarlightRecipe> recipe) {
            super(recipe);
        }

        public static class Start extends LiquidStarlight implements ICancellableEvent {

            public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
                for (Callback callback : callbacks) {
                    callback.post(event);
                }
            });

            public Start(RecipeHolder<LiquidStarlightRecipe> recipe) {
                super(recipe);
            }

            public interface Callback {
                void post(Start event);
            }
        }

        public static class End extends LiquidStarlight {

            public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
                for (Callback callback : callbacks) {
                    callback.post(event);
                }
            });

            public End(RecipeHolder<LiquidStarlightRecipe> recipe) {
                super(recipe);
            }

            public interface Callback {
                void post(End event);
            }
        }
    }

    public static class LumenCrystallization extends BaseEvent {

        private final LumenCrystallizationRecipe recipe;
        private final TileLumenCrystallizer crystallizerTile;
        private final BlockState generatedCrystalState;

        public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
            for (Callback callback : callbacks) {
                callback.post(event);
            }
        });

        public LumenCrystallization(LumenCrystallizationRecipe recipe, TileLumenCrystallizer crystallizerTile, BlockState generatedCrystalState) {
            this.recipe = recipe;
            this.crystallizerTile = crystallizerTile;
            this.generatedCrystalState = generatedCrystalState;
        }

        public LumenCrystallizationRecipe getRecipe() {
            return this.recipe;
        }

        public TileLumenCrystallizer getCrystallizerTile() {
            return this.crystallizerTile;
        }

        public BlockState getGeneratedCrystalState() {
            return this.generatedCrystalState;
        }

        public interface Callback {
            void post(LumenCrystallization event);
        }
    }

    public static class LumenGeneration extends BaseEvent {

        private final LumenGenerationRecipe recipe;
        private final TileLumenArray lumenArrayTile;
        private final LumenStack generated;

        public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
            for (Callback callback : callbacks) {
                callback.post(event);
            }
        });

        public LumenGeneration(LumenGenerationRecipe recipe, TileLumenArray lumenArrayTile, LumenStack generated) {
            this.recipe = recipe;
            this.lumenArrayTile = lumenArrayTile;
            this.generated = generated;
        }

        public LumenGenerationRecipe getRecipe() {
            return this.recipe;
        }

        public TileLumenArray getLumenArrayTile() {
            return this.lumenArrayTile;
        }

        public LumenStack getGenerated() {
            return this.generated;
        }

        public interface Callback {
            void post(LumenGeneration event);
        }
    }
}
