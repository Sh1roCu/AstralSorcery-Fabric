/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.datagen.data;

import hellfirepvp.astralsorcery.common.lib.DamageTypesAS;
import hellfirepvp.astralsorcery.common.lib.EnchantmentsAS;
import hellfirepvp.astralsorcery.datagen.data.world.AstralWorldGenProvider;
import hellfirepvp.astralsorcery.datagen.data.world.structure.AstralStructureProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AstralRegistriesDataProvider
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AstralRegistriesDataProvider extends FabricDynamicRegistryProvider {
    public AstralRegistriesDataProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        entries.addAll(registries.lookupOrThrow(Registries.DAMAGE_TYPE));
        entries.addAll(registries.lookupOrThrow(Registries.ENCHANTMENT));
        entries.addAll(registries.lookupOrThrow(Registries.CONFIGURED_FEATURE));
        entries.addAll(registries.lookupOrThrow(Registries.PLACED_FEATURE));
        entries.addAll(registries.lookupOrThrow(Registries.STRUCTURE));
        entries.addAll(registries.lookupOrThrow(Registries.STRUCTURE_SET));
        entries.addAll(registries.lookupOrThrow(Registries.TEMPLATE_POOL));
        entries.addAll(registries.lookupOrThrow(Registries.PROCESSOR_LIST));
    }

    @Override
    public @NotNull String getName() {
        return "AstralSorcery Dynamic Provider";
    }

    public static void addRegistries(RegistrySetBuilder builder) {
        builder
                .add(Registries.DAMAGE_TYPE, AstralRegistriesDataProvider::generateDamageTypes)
                .add(Registries.ENCHANTMENT, AstralRegistriesDataProvider::generateEnchantments)
                .add(Registries.CONFIGURED_FEATURE, AstralWorldGenProvider::generateConfiguredFeatures)
                .add(Registries.PLACED_FEATURE, AstralWorldGenProvider::generatePlacedFeatures)
                .add(Registries.STRUCTURE, AstralStructureProvider::generateStructures)
                .add(Registries.STRUCTURE_SET, AstralStructureProvider::generateStructureSets)
                .add(Registries.TEMPLATE_POOL, AstralStructureProvider::generateStructurePools)
                .add(Registries.PROCESSOR_LIST, AstralStructureProvider::generateStructureProcessorLists);
    }

    private static void generateDamageTypes(BootstrapContext<DamageType> context) {
        DamageTypesAS.STELLAR.register(context);
    }

    private static void generateEnchantments(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> items = context.lookup(Registries.ITEM);
        HolderGetter<Enchantment> enchantments = context.lookup(Registries.ENCHANTMENT);

        HolderSet<Enchantment> silkTouchSet = HolderSet.direct(enchantments.getOrThrow(Enchantments.SILK_TOUCH));
        EnchantmentsAS.SCORCHING_HEAT.register(context, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.MINING_LOOT_ENCHANTABLE),
                1,
                1,
                Enchantment.dynamicCost(30, 10),
                Enchantment.dynamicCost(60, 10),
                10,
                EquipmentSlotGroup.MAINHAND
        )).exclusiveWith(silkTouchSet));
    }
}
