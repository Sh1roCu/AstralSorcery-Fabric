/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lumen.binding.usage;

import cn.sh1rocu.astralsorcery.api.event.BaseEvent;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import hellfirepvp.astralsorcery.common.lib.types.LumenBindingUsageTypesAS;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: LumenBindingUsageBlockBreak
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class LumenBindingUsageBlockBreak extends LumenBindingUsage {

    public static final MapCodec<LumenBindingUsageBlockBreak> CODEC = RecordCodecBuilder.mapCodec(inst ->
            codecFields(inst).apply(inst, LumenBindingUsageBlockBreak::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, LumenBindingUsageBlockBreak> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            LumenBindingUsageBlockBreak::getLumenCost,
            ByteBufCodecs.FLOAT,
            LumenBindingUsageBlockBreak::getConsumptionChance,
            LumenBindingUsageBlockBreak::new);

    protected LumenBindingUsageBlockBreak(int lumenCost, float consumptionChance) {
        super(lumenCost, consumptionChance);
    }

    public static LumenBindingUsageBlockBreak of(int lumenCost, float consumptionChance) {
        return new LumenBindingUsageBlockBreak(lumenCost, consumptionChance);
    }

    public static void attachEventListeners() {
        PlayerBlockBreakEvents.BEFORE.register(BaseEvent.LOWEST, LumenBindingUsageBlockBreak::onBlockBreak);
    }

    private static boolean onBlockBreak(Level world, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        if (world.isClientSide()) return true;
        drainAll(player, 1F, LumenBindingUsageBlockBreak.class);
        return true;
    }

    @Override
    public DeferredType<?> getType() {
        return LumenBindingUsageTypesAS.BLOCK_BREAK;
    }
}
