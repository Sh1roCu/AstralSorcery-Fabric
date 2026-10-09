/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.util.inventory;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: FilteredInventoryView
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class FilteredInventoryView extends InventoryView {

    private InputFilter inputFilter;
    private ExtractFilter extractFilter;

    protected FilteredInventoryView(int size,
                                    InventoryStackList contents,
                                    Set<Direction> applicableSides,
                                    Consumer<Integer> changeListener,
                                    BiFunction<Integer, ItemStack, Integer> stackSizeLimiter,
                                    InputFilter inputFilter,
                                    ExtractFilter extractFilter) {
        super(size, contents, applicableSides, changeListener, stackSizeLimiter);
        this.inputFilter = inputFilter;
        this.extractFilter = extractFilter;
    }

    public void bypassFilters(Consumer<FilteredInventoryView> run) {
        InputFilter in = this.inputFilter;
        ExtractFilter ex = this.extractFilter;
        this.inputFilter = InputFilter.NO_FILTER;
        this.extractFilter = ExtractFilter.NO_FILTER;
        try {
            run.accept(this);
        } finally {
            this.inputFilter = in;
            this.extractFilter = ex;
        }
    }

    @Override
    public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        if (!this.canInsertItem(maxAmount, resource)) {
            return 0;
        }
        return super.insert(resource, maxAmount, transaction);
    }

    @Override
    public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        if (!this.canExtractItem(maxAmount, resource)) {
            return 0;
        }
        return super.extract(resource, maxAmount, transaction);
    }

    private boolean canInsertItem(long amount, @Nonnull ItemVariant toAdd) {
        return this.inputFilter == null || this.inputFilter.canInsert(amount, toAdd);
    }

    private boolean canExtractItem(long amount, @Nonnull ItemVariant existing) {
        return this.extractFilter == null || this.extractFilter.canExtract(amount, existing);
    }

    @FunctionalInterface
    public interface InputFilter {

        InputFilter NO_FILTER = (amount, toAdd) -> true;

        boolean canInsert(long amount, @Nonnull ItemVariant toAdd);

        default InputFilter and(InputFilter other) {
            return (amount, toAdd) ->
                    other.canInsert(amount, toAdd) && this.canInsert(amount, toAdd);
        }
    }

    @FunctionalInterface
    public interface ExtractFilter {

        ExtractFilter NO_FILTER = (amount, existing) -> true;

        boolean canExtract(long amount, @Nonnull ItemVariant existing);

        default ExtractFilter and(ExtractFilter other) {
            return (amount, existing) ->
                    other.canExtract(amount, existing) && this.canExtract(amount, existing);
        }
    }
}
