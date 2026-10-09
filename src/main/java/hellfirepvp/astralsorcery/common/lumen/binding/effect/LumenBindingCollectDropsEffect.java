/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lumen.binding.effect;

import com.mojang.serialization.MapCodec;
import hellfirepvp.astralsorcery.common.component.StoredItemsComponent;
import hellfirepvp.astralsorcery.common.lib.DataComponentsAS;
import hellfirepvp.astralsorcery.common.lib.ItemsAS;
import hellfirepvp.astralsorcery.common.lib.types.LumenBindingEffectTypesAS;
import hellfirepvp.astralsorcery.common.util.ItemUtil;
import net.fabricmc.api.EnvType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: LumenBindingCollectDropsEffect
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class LumenBindingCollectDropsEffect extends LumenBindingEffect {

    public static final LumenBindingCollectDropsEffect INSTANCE = new LumenBindingCollectDropsEffect();
    public static final MapCodec<LumenBindingCollectDropsEffect> CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, LumenBindingCollectDropsEffect> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    public static void attachEventListeners() {
        // impl via mixin
        // BlockDropsEvent.EVENT.register(BaseEvent.LOWEST, LumenBindingCollectDropsEffect::onBlockDrops);
    }

    public static void onBlockDrops(/*BlockDropsEvent event*/ @Nullable Entity breaker, List<ItemStack> toDrop) {
        if (breaker instanceof ServerPlayer sPlayer) {
            forEachEffect(sPlayer, LumenBindingCollectDropsEffect.class, (stack, effect) -> {
                int slotId = ItemUtil.findItemsInInventory(sPlayer, s -> s.is(ItemsAS.AKASHIC_SINGULARITY)).keySet()
                        .stream().findFirst().orElse(-1);
                ItemStack singularity;
                if (slotId == -1) {
                    singularity = ItemsAS.AKASHIC_SINGULARITY.getDefaultInstance();
                    slotId = sPlayer.getInventory().getFreeSlot();
                    if (slotId == -1) {
                        return; // Well, I GUESS we can't do fck all here. Player error or something.
                    }
                } else {
                    singularity = sPlayer.getInventory().getItem(slotId);
                }

                StoredItemsComponent cmp = singularity.getOrDefault(DataComponentsAS.STORED_ITEMS, StoredItemsComponent.EMPTY);
                // for (ItemEntity itemEntity : event.getDrops()) {
                // if (itemEntity.isRemoved()) continue;
                // cmp = cmp.accept(itemEntity.getItem());
                // itemEntity.discard();
                // }
                var copied = List.copyOf(toDrop);
                for (ItemStack itemStack : copied) {
                    if (itemStack.isEmpty()) continue;
                    cmp = cmp.accept(itemStack);
                    toDrop.remove(itemStack);
                }
                singularity.set(DataComponentsAS.STORED_ITEMS, cmp);
                sPlayer.getInventory().setItem(slotId, singularity);
            });
        }
    }

    @Override
    public List<Component> getDisplayText(EnvType side, ItemStack stack) {
        return List.of(Component.translatable("lumen.binding.astralsorcery.collect_drops"));
    }

    @Override
    public DeferredType<?> getType() {
        return LumenBindingEffectTypesAS.COLLECT_DROPS;
    }
}
