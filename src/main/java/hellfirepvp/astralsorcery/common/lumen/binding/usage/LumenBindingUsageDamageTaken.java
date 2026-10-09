/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lumen.binding.usage;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import hellfirepvp.astralsorcery.common.lib.types.LumenBindingUsageTypesAS;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: LumenBindingUsageBlockBreak
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class LumenBindingUsageDamageTaken extends LumenBindingUsage {

    public static final MapCodec<LumenBindingUsageDamageTaken> CODEC = RecordCodecBuilder.mapCodec(inst ->
            codecFields(inst).apply(inst, LumenBindingUsageDamageTaken::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, LumenBindingUsageDamageTaken> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            LumenBindingUsageDamageTaken::getLumenCost,
            ByteBufCodecs.FLOAT,
            LumenBindingUsageDamageTaken::getConsumptionChance,
            LumenBindingUsageDamageTaken::new);

    protected LumenBindingUsageDamageTaken(int lumenCost, float consumptionChance) {
        super(lumenCost, consumptionChance);
    }

    public static LumenBindingUsageDamageTaken of(int lumenCost, float consumptionChance) {
        return new LumenBindingUsageDamageTaken(lumenCost, consumptionChance);
    }

    public static void attachEventListeners() {
        ServerLivingEntityEvents.AFTER_DAMAGE.register(LumenBindingUsageDamageTaken::onDamageTaken);
    }

    private static void onDamageTaken(Entity entity, DamageSource source, float baseDamageTaken, float damageTaken, boolean blocked) {
        if (entity instanceof LivingEntity attacked) {
            if (attacked.level().isClientSide()) return;

            drainAll(attacked, 1F, LumenBindingUsageDamageTaken.class);
        }
    }

    @Override
    public DeferredType<?> getType() {
        return LumenBindingUsageTypesAS.DAMAGE_TAKEN;
    }
}
