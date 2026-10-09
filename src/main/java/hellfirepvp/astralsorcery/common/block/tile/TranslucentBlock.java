/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.block.tile;

import cn.sh1rocu.astralsorcery.api.extension.ILandingEffectsBlock;
import cn.sh1rocu.astralsorcery.api.extension.IRunningEffectsBlock;
import com.mojang.serialization.MapCodec;
import hellfirepvp.astralsorcery.client.effect.EffectHelper;
import hellfirepvp.astralsorcery.client.effect.function.FXAlphaFunction;
import hellfirepvp.astralsorcery.client.effect.function.FXColorFunction;
import hellfirepvp.astralsorcery.client.lib.EffectTemplatesAS;
import hellfirepvp.astralsorcery.common.block.tile.base.BaseTileBlock;
import hellfirepvp.astralsorcery.common.lib.TileEntitiesAS;
import hellfirepvp.astralsorcery.common.lib.constants.ColorsAS;
import hellfirepvp.astralsorcery.common.tile.TileTranslucentBlock;
import hellfirepvp.astralsorcery.common.tile.base.TileEntitySynchronized;
import hellfirepvp.astralsorcery.common.util.MiscUtil;
import hellfirepvp.astralsorcery.common.util.data.Vector3;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: TranslucentBlock
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class TranslucentBlock extends BaseTileBlock<TileTranslucentBlock> implements ILandingEffectsBlock, IRunningEffectsBlock {

    public static MapCodec<TranslucentBlock> CODEC = simpleCodec(TranslucentBlock::new);

    public TranslucentBlock(Properties properties) {
        super(properties, () -> TileEntitiesAS.TRANSLUCENT_BLOCK);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        MiscUtil.getTileAt(level, pos, TileTranslucentBlock.class, false).ifPresent(tile -> {
            BlockState storedState = tile.getTileData().getStoredState();
            try {
                storedState.getBlock().animateTick(state, level, pos, random);
            } catch (Exception ignored) {
            }
        });

        if (random.nextInt(10) != 0) return;

        FXColorFunction<?> colorFn = MiscUtil.getTileAt(level, pos, TileTranslucentBlock.class, false)
                .map(TileEntitySynchronized::getTileData)
                .map(TileTranslucentBlock.Data::getDyeColor)
                .map(color -> ColorsAS.DYE_COLORS[color.getId()])
                .map(FXColorFunction::constant)
                .orElse(MiscUtil.cast(FXColorFunction.WHITE));
        EffectHelper.of(EffectTemplatesAS.GENERIC_PARTICLE)
                .spawn(Vector3.random(random).abs().add(pos))
                .alpha(FXAlphaFunction.FADE_OUT)
                .color(colorFn)
                .setScale(0.2F + random.nextFloat() * 0.1F)
                .setMaxAge(25 + random.nextInt(10));
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return MiscUtil.getTileAt(level, pos, TileTranslucentBlock.class, true).map(tile -> {
            BlockState stored = tile.getTileData().getStoredState();
            try {
                return stored.getBlock().getCloneItemStack(level, pos, stored);
            } catch (Exception exc) {
                return new ItemStack(stored.getBlock());
            }
        }).orElse(ItemStack.EMPTY);
    }

    @Override
    public boolean addLandingEffects(BlockState state1, ServerLevel level, BlockPos pos, BlockState state2, LivingEntity entity, int numberOfParticles) {
        return true;
    }

    @Override
    public boolean addRunningEffects(BlockState state, Level level, BlockPos pos, Entity entity) {
        return true;
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1F;
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
