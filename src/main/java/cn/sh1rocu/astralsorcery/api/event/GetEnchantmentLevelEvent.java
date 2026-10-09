package cn.sh1rocu.astralsorcery.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class GetEnchantmentLevelEvent extends BaseEvent {
    protected final ItemStack stack;
    protected final ItemEnchantments.Mutable enchantments;
    @Nullable
    protected final Holder<Enchantment> targetEnchant;
    protected final RegistryLookup<Enchantment> lookup;

    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class,callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    });

    public GetEnchantmentLevelEvent(ItemStack stack, ItemEnchantments.Mutable enchantments, @Nullable Holder<Enchantment> targetEnchant, RegistryLookup<Enchantment> lookup) {
        this.stack = stack;
        this.enchantments = enchantments;
        this.targetEnchant = targetEnchant;
        this.lookup = lookup;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public ItemEnchantments.Mutable getEnchantments() {
        return this.enchantments;
    }

    @Nullable
    public Holder<Enchantment> getTargetEnchant() {
        return this.targetEnchant;
    }

    public boolean isTargetting(Holder<Enchantment> ench) {
        return this.targetEnchant == null || this.targetEnchant.is(ench);
    }

    public boolean isTargetting(ResourceKey<Enchantment> ench) {
        return this.targetEnchant == null || this.targetEnchant.is(ench);
    }

    public Optional<Holder.Reference<Enchantment>> getHolder(ResourceKey<Enchantment> key) {
        return this.lookup.get(key);
    }

    public RegistryLookup<Enchantment> getLookup() {
        return lookup;
    }

    public interface Callback {
        void post(GetEnchantmentLevelEvent event);
    }
}
