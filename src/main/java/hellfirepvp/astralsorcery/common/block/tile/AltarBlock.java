/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.block.tile;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import hellfirepvp.astralsorcery.common.block.tile.base.BaseTickTileBlock;
import hellfirepvp.astralsorcery.common.container.provider.ContainerAltarProvider;
import hellfirepvp.astralsorcery.common.lib.ItemsAS;
import hellfirepvp.astralsorcery.common.lib.TileEntitiesAS;
import hellfirepvp.astralsorcery.common.tile.TileAltar;
import hellfirepvp.astralsorcery.common.util.MiscUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AltarBlock
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AltarBlock extends BaseTickTileBlock<TileAltar> {

    public static final Supplier<MapCodec<AltarBlock>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.mapCodec(inst -> inst.group(
            propertiesCodec(),
            TileAltar.AltarType.CODEC.fieldOf("altar_type").forGetter(AltarBlock::getAltarType)
    ).apply(inst, (a, b) -> new AltarBlock(a, () -> b))));

    private final Supplier<TileAltar.AltarType> altarTypeSupplier;

    public AltarBlock(Properties properties, Supplier<TileAltar.AltarType> altarTypeSupplier) {
        super(properties, () -> TileEntitiesAS.ALTAR);
        this.altarTypeSupplier = altarTypeSupplier;
    }

    public TileAltar.AltarType getAltarType() {
        return this.altarTypeSupplier.get();
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.getAltarType().getShape();
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (hand == InteractionHand.MAIN_HAND && player instanceof ServerPlayer sPlayer) {
            return MiscUtil.getTileAt(level, pos, TileAltar.class, true).map(altar -> {
                if (stack.is(ItemsAS.WAND)) {
                    if (altar.getTileData().getActiveRecipe().isPresent()) return ItemInteractionResult.CONSUME;
                    if (!altar.getTileData().hasStructure()) return ItemInteractionResult.CONSUME;
                    return altar.findMatchingRecipe(level, sPlayer.getUUID()).map(recipe -> {
                        altar.startCrafting(recipe, sPlayer.getUUID());
                        return ItemInteractionResult.SUCCESS;
                    }).orElse(ItemInteractionResult.CONSUME);
                }
                ContainerAltarProvider.openAltar(altar).open(sPlayer);
                return ItemInteractionResult.CONSUME;
            }).orElse(ItemInteractionResult.CONSUME);
        }
        return ItemInteractionResult.CONSUME;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!(newState.getBlock() instanceof AltarBlock)) {
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    @Override
    protected void dropTileContents(BlockState state, Level level, BlockPos pos) {
        super.dropTileContents(state, level, pos);

        MiscUtil.getTileAt(level, pos, TileAltar.class, true).ifPresent(altar -> {
            ItemStack focusStack = altar.getTileData().getFocusItem();
            if (!focusStack.isEmpty()) {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), focusStack);
            }
        });
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected BlockEntityTicker<TileAltar> createTicker() {
        return this.ticker();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC.get();
    }
}
