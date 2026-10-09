/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.container.slot;

import hellfirepvp.astralsorcery.common.util.MiscUtil;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: TileDisplaySlot
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class TileDisplaySlot extends ReadOnlySlot {

    private final Supplier<Level> level;
    private final BlockPos tilePos;
    private final Class<? extends BlockEntity> expectedTileClass;

    public TileDisplaySlot(int x, int y, Supplier<Level> level, BlockPos tilePos) {
        this(x, y, level, tilePos, BlockEntity.class);
    }

    public TileDisplaySlot(int x, int y, Supplier<Level> level, BlockPos tilePos, Class<? extends BlockEntity> expectedTileClass) {
        super(new SimpleContainer(0), 0, x, y);
        this.level = level;
        this.tilePos = tilePos;
        this.expectedTileClass = expectedTileClass;
    }

    @Override
    public ItemStack getItem() {
        Level level = this.level.get();
        return MiscUtil.getTileAt(level, this.tilePos, this.expectedTileClass, false).map(tile -> {
            var handler = ItemStorage.SIDED.find(level, this.tilePos, null);
            if (handler == null) return ItemStack.EMPTY;
            ItemStack stack = ItemStack.EMPTY;
            for (var view : handler) {
                if (!view.isResourceBlank()) {
                    stack = view.getResource().toStack((int) view.getAmount());
                    break;
                }
            }
            return stack.copy();
        }).orElse(ItemStack.EMPTY);
    }
}
