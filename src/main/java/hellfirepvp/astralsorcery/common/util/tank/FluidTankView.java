/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.util.tank;

import cn.sh1rocu.astralsorcery.util.fluid.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.Direction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: FluidTankView
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class FluidTankView extends CombinedStorage<FluidVariant, Storage<FluidVariant>> {

    private final int tanks;
    private final FluidContainerList contents;
    private final Set<Direction> applicableSides;
    private final Consumer<Integer> changeListener;
    private final Function<Integer, Long> tankCapacityGetter;
    private InputFilter inputFilter;
    private ExtractFilter extractFilter;

    public FluidTankView(int tanks,
                         FluidContainerList contents,
                         Set<Direction> applicableSides,
                         Consumer<Integer> changeListener,
                         Function<Integer, Long> tankCapacityGetter,
                         InputFilter inputFilter,
                         ExtractFilter extractFilter) {
        super(List.of());
        this.tanks = tanks;
        this.contents = contents;
        this.applicableSides = applicableSides;
        this.changeListener = changeListener;
        this.tankCapacityGetter = tankCapacityGetter;
        this.inputFilter = inputFilter;
        this.extractFilter = extractFilter;
        this.parts = storages();
    }

    protected List<Storage<FluidVariant>> storages() {
        List<Storage<FluidVariant>> storages = new ArrayList<>();
        for (int i = 0; i < this.tanks; i++) {
            final int slot = i;
            storages.add(new SingleFluidStorage() {
                private FluidStack outerContent() {
                    return FluidTankView.this.contents.getTank(slot).getModifiableContent();
                }

                private void sync() {
                    FluidStack stack = outerContent();
                    this.variant = stack.getFluidVariant();
                    this.amount = stack.getAmount();
                }

                @Override
                public long insert(FluidVariant resource, long maxAmount, TransactionContext tx) {
                    sync();
                    return super.insert(resource, maxAmount, tx);
                }

                @Override
                public long extract(FluidVariant resource, long maxAmount, TransactionContext tx) {
                    sync();
                    return super.extract(resource, maxAmount, tx);
                }

                @Override
                protected long getCapacity(FluidVariant variant) {
                    return FluidTankView.this.getTankCapacity(slot);
                }

                @Override
                public FluidVariant getResource() {
                    sync();
                    return this.variant;
                }

                @Override
                public long getAmount() {
                    sync();
                    return this.amount;
                }

                @Override
                public boolean isResourceBlank() {
                    return getResource().isBlank();
                }

                @Override
                protected boolean canInsert(FluidVariant variant) {
                    return super.canInsert(variant) && FluidTankView.this.inputFilter.canInsert(slot, variant, getResource());
                }

                @Override
                protected boolean canExtract(FluidVariant variant) {
                    return super.canExtract(variant) && FluidTankView.this.extractFilter.canExtract(slot, variant);
                }

                @Override
                protected void onFinalCommit() {
                    FluidContainer container = FluidTankView.this.contents.getTank(slot);
                    if (this.variant.isBlank() || this.amount <= 0) {
                        container.clear();
                    } else {
                        container.setContent(new FluidStack(this.variant, this.amount));
                    }
                    FluidTankView.this.onContentChanged(slot);
                }
            });
        }
        return storages;
    }

    private FluidContainer getTank(int tank) {

        this.validateTankAccess(tank);
        return this.contents.getTank(tank);
    }

    protected void validateTankAccess(int tank) {
        if (tank >= this.getTanks()) {
            throw new IndexOutOfBoundsException("Tank " + tank + " not in valid range - [0, " + this.getTanks() + ")");
        }
    }

    public void withoutFilters(Consumer<FluidTankView> run) {
        this.getWithoutFilters(tank -> {
            run.accept(tank);
            return null;
        });
    }

    public <T> T getWithoutFilters(Function<FluidTankView, T> fn) {
        InputFilter in = this.inputFilter;
        ExtractFilter ex = this.extractFilter;
        this.inputFilter = InputFilter.NO_FILTER;
        this.extractFilter = ExtractFilter.NO_FILTER;
        try {
            return fn.apply(this);
        } finally {
            this.inputFilter = in;
            this.extractFilter = ex;
        }
    }

    public int getTanks() {
        return this.tanks;
    }

    public FluidStack getFluidInTank(int tank) {
        this.validateTankAccess(tank);
        return this.getTank(tank).getContent();
    }

    public long getTankCapacity(int tank) {
        this.validateTankAccess(tank);
        return this.tankCapacityGetter.apply(tank);
    }

    public boolean isFluidValid(int tank, FluidVariant fluidVariant) {
        this.validateTankAccess(tank);
        return this.inputFilter.canInsert(tank, fluidVariant, this.getFluidInTank(tank).getFluidVariant());
    }

    public void clearTanks() {
        IntStream.range(0, this.getTanks()).forEach(this::clearTank);
    }

    public void clearTank(int tank) {
        this.getTank(tank).clear();
        this.onContentChanged(tank);
    }

    private void onContentChanged(int tank) {
        this.changeListener.accept(tank);
    }

    protected boolean hasHandlerForSide(@Nullable Direction facing) {
        return facing == null || this.applicableSides.contains(facing);
    }

    @Nullable
    public FluidTankView getFluidAccess(@Nullable Direction direction) {
        if (!this.hasHandlerForSide(direction)) {
            return null;
        }
        return this;
    }

    @FunctionalInterface
    public interface InputFilter {

        InputFilter NO_FILTER = (tank, toAdd, existing) -> true;

        boolean canInsert(int tank, FluidVariant toAdd, @Nonnull FluidVariant existing);

        default InputFilter and(InputFilter other) {
            return (tank, toAdd, existing) ->
                    other.canInsert(tank, toAdd, existing) && this.canInsert(tank, toAdd, existing);
        }
    }

    @FunctionalInterface
    public interface ExtractFilter {

        ExtractFilter NO_FILTER = (tank, existing) -> true;

        boolean canExtract(int tank, @Nonnull FluidVariant existing);

        default ExtractFilter and(ExtractFilter other) {
            return (tank, existing) ->
                    other.canExtract(tank, existing) && this.canExtract(tank, existing);
        }
    }
}
