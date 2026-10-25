/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.event.helper;

import cn.sh1rocu.astralsorcery.api.event.GetEnchantmentLevelEvent;
import cn.sh1rocu.astralsorcery.api.event.PlayerTickEvent;
import cn.sh1rocu.astralsorcery.util.neoforge.common.util.LogicalSidedProvider;
import cn.sh1rocu.observerlib.ObserverLibFabric;
import com.mojang.datafixers.util.Pair;
import hellfirepvp.astralsorcery.common.Mods;
import hellfirepvp.astralsorcery.common.component.EnchantmentModifierComponent;
import hellfirepvp.astralsorcery.common.enchantment.CombinedEnchantmentModifiers;
import hellfirepvp.astralsorcery.common.event.DynamicEnchantmentEvent;
import hellfirepvp.astralsorcery.common.integration.IntegrationCurios;
import hellfirepvp.astralsorcery.common.item.base.ItemStackEquipmentReference;
import hellfirepvp.astralsorcery.common.lib.DataComponentsAS;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: EnchantmentModifierHelper
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class EnchantmentModifierHelper {

    private static final ResourceLocation AIR_ID = ResourceLocation.withDefaultNamespace("air");

    public static void attachListeners() {
        PlayerTickEvent.PRE.register(EnchantmentModifierHelper::onPlayerTick);
        GetEnchantmentLevelEvent.EVENT.register(EnchantmentModifierHelper::onEnchantmentAdd);
    }

    private static void onPlayerTick(PlayerTickEvent.Pre event) {
        UUID playerId = event.getEntity().getUUID();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = event.getEntity().getItemBySlot(slot);
            if (!stack.isEmpty() && canHaveModifiedEnchantments(stack)) {
                ItemStackEquipmentReference.setEquipmentReference(stack, playerId, slot);
            }
        }
    }

    private static void onEnchantmentAdd(GetEnchantmentLevelEvent event) {
        ItemEnchantments.Mutable enchants = event.getEnchantments();
        addEnchantments(enchants, event.getStack(), event::isTargetting);
    }

    public static ItemEnchantments addEnchantments(ItemStack hostStack, ItemEnchantments enchants) {
        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(enchants);
        addEnchantments(mutable, hostStack);
        return mutable.toImmutable();
    }

    public static void addEnchantments(ItemEnchantments.Mutable enchants, ItemStack hostStack) {
        addEnchantments(enchants, hostStack, ench -> true);
    }

    public static void addEnchantments(ItemEnchantments.Mutable enchants, ItemStack hostStack, Predicate<Holder<Enchantment>> filter) {
        CombinedEnchantmentModifiers toAdd = getModifiersToApply(hostStack);

        toAdd.getAddedToSpecific().forEach((ench, level) -> {
            if (filter.test(ench) && (hostStack.canBeEnchantedWith(ench, EnchantingContext.ACCEPTABLE) || hostStack.canBeEnchantedWith(ench, EnchantingContext.PRIMARY))) {
                enchants.set(ench, enchants.getLevel(ench) + level);
            }
        });
        toAdd.getAddedToExistingSpecific().forEach((ench, level) -> {
            if (filter.test(ench)) {
                int existing = enchants.getLevel(ench);
                if (existing > 0) {
                    enchants.set(ench, existing + level);
                }
            }
        });
        if (toAdd.getAddedToAll() != 0) {
            for (Holder<Enchantment> ench : enchants.keySet()) {
                int existing = enchants.getLevel(ench);
                if (existing > 0) {
                    enchants.set(ench, existing + toAdd.getAddedToAll());
                }
            }
        }
    }

    private static CombinedEnchantmentModifiers getModifiersToApply(ItemStack tool) {
        return getPlayer(tool).map(player -> {
            var createEvent = new DynamicEnchantmentEvent.Add(player);
            DynamicEnchantmentEvent.Add.EVENT.invoker().post(createEvent);
            CombinedEnchantmentModifiers modifiers = createEvent.getDynamicEnchantments().build();

            for (ItemStack modifierStack : findEnchantmentModifiers(player)) {
                EnchantmentModifierComponent mod = modifierStack.getOrDefault(DataComponentsAS.ENCHANTMENT_MODIFIERS, EnchantmentModifierComponent.EMPTY);
                modifiers = modifiers.combine(mod.combined());
            }

            var modifyEvent = new DynamicEnchantmentEvent.Modify(player, modifiers);
            DynamicEnchantmentEvent.Modify.EVENT.invoker().post(modifyEvent);
            return modifyEvent.getDynamicEnchantments().build();
        }).orElse(CombinedEnchantmentModifiers.of());
    }

    private static Optional<Player> getPlayer(ItemStack heldTool) {
        return getPlayerFromToolReference(heldTool).map(reference -> {
            Player player = reference.getFirst();
            ItemStack equipped = player.getItemBySlot(reference.getSecond());
            if (ItemStack.isSameItemSameComponents(equipped, heldTool)) {
                return player;
            }
            return null;
        });
    }

    private static Optional<Pair<Player, EquipmentSlot>> getPlayerFromToolReference(ItemStack tool) {
        Pair<UUID, EquipmentSlot> reference = ItemStackEquipmentReference.getEquipmentReference(tool);
        if (reference == null) return Optional.empty();

        UUID playerId = reference.getFirst();

        EnvType side = FabricLoader.getInstance().getEnvironmentType();
        if (side == EnvType.SERVER) {
            MinecraftServer srv = ObserverLibFabric.getServer();
            if (srv != null && srv.isSameThread()) {
                return Optional.ofNullable(srv.getPlayerList().getPlayer(playerId))
                        .map(player -> new Pair<>(player, reference.getSecond()));
            }
        } else {
            return LogicalSidedProvider.CLIENTWORLD.get(EnvType.CLIENT)
                    .flatMap(level -> resolveClientReference(level, playerId))
                    .map(player -> new Pair<>(player, reference.getSecond()));
        }

        return Optional.empty();
    }

    @Environment(EnvType.CLIENT)
    private static Optional<Player> resolveClientReference(Level clientLevel, UUID playerId) {
        if (Minecraft.getInstance().isSameThread()) {
            return Optional.ofNullable(clientLevel.getPlayerByUUID(playerId));
        }
        return Optional.empty();
    }

    private static List<ItemStack> findEnchantmentModifiers(Player player) {
        List<ItemStack> foundEnchantmentModifierItems = new ArrayList<>();
        Predicate<ItemStack> filter = stack -> {
            //TODO specific slot filters
            return !stack.getOrDefault(DataComponentsAS.ENCHANTMENT_MODIFIERS, EnchantmentModifierComponent.EMPTY).isEmpty();
        };

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.HAND) continue;

            ItemStack stack = player.getItemBySlot(slot);
            if (filter.test(stack)) {
                foundEnchantmentModifierItems.add(stack);
            }
        }

        foundEnchantmentModifierItems.addAll(IntegrationCurios.getCurio(player, filter));
        return foundEnchantmentModifierItems;
    }

    private static boolean canHaveModifiedEnchantments(ItemStack stack) {
        if (stack.isEmpty()) return false;
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (id.equals(AIR_ID)) return false;
        try {
            if (!stack.isEnchantable() || stack.getItem() instanceof BookItem) {
                return false;
            }
        } catch (Exception exc) {
            return false;
        }
        if (id.getNamespace().equals(Mods.DRACONIC_EVOLUTION.getModId())) {
            return false;
        }

        return true;
    }
}
