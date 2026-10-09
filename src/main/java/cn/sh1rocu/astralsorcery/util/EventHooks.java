package cn.sh1rocu.astralsorcery.util;

import cn.sh1rocu.astralsorcery.AstralSorceryFabric;
import cn.sh1rocu.astralsorcery.api.event.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;

public class EventHooks {
    private EventHooks() {
    }

    public static boolean onProjectileImpact(Projectile projectile, HitResult ray) {
        var event = new ProjectileImpactEvent(projectile, ray);
        ProjectileImpactEvent.EVENT.invoker().post(event);
        return event.isCanceled();
    }

    public static int getEnchantmentLevelSpecific(int level, ItemStack stack, Holder<Enchantment> ench) {
        HolderLookup.RegistryLookup<Enchantment> lookup = AstralSorceryFabric.LOOKUP.lookup(Registries.ENCHANTMENT).orElse(null) /*ench.unwrapLookup()*/;
        if (lookup == null) { // Pretty sure this is never null, but I can't *prove* that it isn't.
            return level;
        }

        var enchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        enchantments.set(ench, level);
        var event = new GetEnchantmentLevelEvent(stack, enchantments, ench, lookup /*ench.unwrapLookup()*/);
        GetEnchantmentLevelEvent.EVENT.invoker().post(event);
        return enchantments.getLevel(ench);
    }

    public static ItemEnchantments getAllEnchantmentLevels(ItemEnchantments enchantments, ItemStack stack, HolderLookup.RegistryLookup<Enchantment> lookup) {
        var mutableEnchantments = new ItemEnchantments.Mutable(enchantments);
        var event = new GetEnchantmentLevelEvent(stack, mutableEnchantments, null, lookup);
        GetEnchantmentLevelEvent.EVENT.invoker().post(event);
        return mutableEnchantments.toImmutable();
    }

    public static BlockGrowFeatureEvent fireBlockGrowFeature(LevelAccessor level, RandomSource rand, BlockPos pos/*, @Nullable Holder<ConfiguredFeature<?, ?>> holder*/) {
        var event = new BlockGrowFeatureEvent(level, rand, pos/*, holder*/);
        BlockGrowFeatureEvent.EVENT.invoker().post(event);
        return event;
    }

    public static float onLivingHeal(LivingEntity entity, float amount) {
        var event = new LivingHealEvent(entity, amount);
        LivingHealEvent.EVENT.invoker().post(event);
        return (event.isCanceled() ? 0 : event.getAmount());
    }

    public static float getBreakSpeed(Player player, BlockState state, float original, @Nullable BlockPos pos) {
        var event = new PlayerEvent.BreakSpeed(player, state, original, pos);
        PlayerEvent.BreakSpeed.EVENT.invoker().post(event);
        return (event.isCanceled() ? -1 : event.getNewSpeed());
    }

    public static CriticalHitEvent fireCriticalHit(Player player, Entity target, boolean vanillaCritical, float damageModifier) {
        var event = new CriticalHitEvent(player, target, damageModifier, vanillaCritical);
        CriticalHitEvent.EVENT.invoker().post(event);
        return event;
    }


}
