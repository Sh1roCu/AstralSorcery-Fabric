/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.init;

import hellfirepvp.astralsorcery.common.lib.FluidsAS;
import hellfirepvp.astralsorcery.common.lib.TileEntitiesAS;
import hellfirepvp.astralsorcery.common.lumen.ILumenHandler;
import hellfirepvp.astralsorcery.common.lumen.capability.LumenHandlerView;
import hellfirepvp.astralsorcery.common.tile.*;
import hellfirepvp.astralsorcery.common.tile.base.TileEntitySynchronized;
import hellfirepvp.astralsorcery.common.util.MiscUtil;
import hellfirepvp.astralsorcery.common.util.inventory.InventoryView;
import hellfirepvp.astralsorcery.common.util.tank.FluidTankView;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.EmptyItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Items;

import java.util.function.Function;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: InitCapabilities
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class InitCapabilities {

    public static void init() {
        // -------------------- BLOCKS/TILES --------------------
        ItemStorage.SIDED.registerForBlockEntity((be, direction) -> tileInventory(be, direction, TileFocusRelay.Data::getInventory),
                TileEntitiesAS.FOCUS_RELAY.type());
        ItemStorage.SIDED.registerForBlockEntity((be, direction) -> tileInventory(be, direction, TileAltar.Data::getAltarInventory),
                TileEntitiesAS.ALTAR.type());
        ItemStorage.SIDED.registerForBlockEntity((be, direction) -> tileInventory(be, direction, TileLumenArray.Data::getInventory),
                TileEntitiesAS.LUMEN_ARRAY.type());
        ItemStorage.SIDED.registerForBlockEntity((be, direction) -> tileInventory(be, direction, TileLumenArray.Data::getInventory),
                TileEntitiesAS.LUMEN_ALCHEMY_ARRAY.type());
        ItemStorage.SIDED.registerForBlockEntity((be, direction) -> tileInventory(be, direction, TileLumenCrystallizer.Data::getInventory),
                TileEntitiesAS.LUMEN_CRYSTALLIZER.type());
        ItemStorage.SIDED.registerForBlockEntity((be, direction) -> tileInventory(be, direction, TileLightwell.Data::getInventory),
                TileEntitiesAS.LIGHTWELL.type());
        ItemStorage.SIDED.registerForBlockEntity((be, direction) -> tileInventory(be, direction, TileInfuser.Data::getInventory),
                TileEntitiesAS.INFUSER.type());

        FluidStorage.SIDED.registerForBlockEntity((be, direction) -> fluidHandler(be, direction, TileLumenArray.Data::getFluidTank),
                TileEntitiesAS.LUMEN_ARRAY.type());
        FluidStorage.SIDED.registerForBlockEntity((be, direction) -> fluidHandler(be, direction, TileLumenArray.Data::getFluidTank),
                TileEntitiesAS.LUMEN_ALCHEMY_ARRAY.type());
        FluidStorage.SIDED.registerForBlockEntity((be, direction) -> fluidHandler(be, direction, TileLumenCrystallizer.Data::getFluidTank),
                TileEntitiesAS.LUMEN_CRYSTALLIZER.type());
        FluidStorage.SIDED.registerForBlockEntity((be, direction) -> fluidHandler(be, direction, TileLightwell.Data::getFluidTank),
                TileEntitiesAS.LIGHTWELL.type());
        FluidStorage.SIDED.registerForBlockEntity((be, direction) -> fluidHandler(be, direction, TileChalice.Data::getFluidTank),
                TileEntitiesAS.CHALICE.type());

        ILumenHandler.BLOCK.registerForBlockEntity((be, direction) -> lumenHandler(be, direction, TileLumenArray.Data::getLumenHandler),
                TileEntitiesAS.LUMEN_ARRAY.type());
        ILumenHandler.BLOCK.registerForBlockEntity((be, direction) -> lumenHandler(be, direction, TileLumenArray.Data::getLumenHandler),
                TileEntitiesAS.LUMEN_ALCHEMY_ARRAY.type());
        ILumenHandler.BLOCK.registerForBlockEntity((be, direction) -> lumenHandler(be, direction, TileLumenCrystallizer.Data::getLumenHandler),
                TileEntitiesAS.LUMEN_CRYSTALLIZER.type());
        ILumenHandler.BLOCK.registerForBlockEntity((be, direction) -> lumenHandler(be, direction, TileTreeBeacon.Data::getLumenHandler),
                TileEntitiesAS.TREE_BEACON.type());

        // -------------------- ITEMS --------------------
        FluidStorage.combinedItemApiProvider(Items.BUCKET).register(ctx ->
                new EmptyItemFluidStorage(ctx, bucket -> ItemVariant.of(FluidsAS.LIQUID_STARLIGHT.getBucket()), FluidsAS.LIQUID_STARLIGHT.getSource(), FluidConstants.BUCKET));
    }

    private static <O extends TileEntitySynchronized.Data> Storage<ItemVariant> tileInventory(TileEntitySynchronized<?> tile, Direction dir, Function<O, InventoryView> invFn) {
        return invFn.apply(MiscUtil.cast(tile.getTileData())).getInventoryAccess(dir);
    }

    private static <O extends TileEntitySynchronized.Data> Storage<FluidVariant> fluidHandler(TileEntitySynchronized<?> tile, Direction dir, Function<O, FluidTankView> handlerFn) {
        return handlerFn.apply(MiscUtil.cast(tile.getTileData())).getFluidAccess(dir);
    }

    private static <O extends TileEntitySynchronized.Data> ILumenHandler lumenHandler(TileEntitySynchronized<?> tile, Direction dir, Function<O, LumenHandlerView> handlerFn) {
        return handlerFn.apply(MiscUtil.cast(tile.getTileData())).getLumenAccess(dir);
    }
}
