/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lumen.binding.effect;

import cn.sh1rocu.astralsorcery.api.event.BaseEvent;
import com.mojang.serialization.MapCodec;
import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import hellfirepvp.astralsorcery.common.lib.types.LumenBindingEffectTypesAS;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: LumenBindingPlaceLightEffect
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class LumenBindingPlaceLightEffect extends LumenBindingEffect {

    public static final LumenBindingPlaceLightEffect INSTANCE = new LumenBindingPlaceLightEffect();
    public static final MapCodec<LumenBindingPlaceLightEffect> CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, LumenBindingPlaceLightEffect> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    public static void attachEventListeners() {
        PlayerBlockBreakEvents.AFTER.register(BaseEvent.LOWEST, LumenBindingPlaceLightEffect::onBlockBreak);
    }

    private static void onBlockBreak(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        if (level.isClientSide()) return;
        int light = level.getRawBrightness(pos, 0);
        if (light >= 2) return;

        forEachEffect(player, LumenBindingPlaceLightEffect.class, (stack, effect) -> {
            level.setBlock(pos, BlocksAS.FLARE_LIGHT.defaultBlockState(), Block.UPDATE_ALL);
        });
    }

    @Override
    public List<Component> getDisplayText(EnvType side, ItemStack stack) {
        return List.of(Component.translatable("lumen.binding.astralsorcery.place_light"));
    }

    @Override
    public DeferredType<?> getType() {
        return LumenBindingEffectTypesAS.PLACE_LIGHT;
    }
}
