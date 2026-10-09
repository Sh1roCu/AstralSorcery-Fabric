/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib;

import cn.sh1rocu.astralsorcery.util.neoforge.common.SimpleTier;
import com.google.common.collect.Sets;
import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.component.ColorComponent;
import hellfirepvp.astralsorcery.common.item.*;
import hellfirepvp.astralsorcery.common.item.base.BlockItemCustom;
import hellfirepvp.astralsorcery.common.item.base.ItemCustom;
import hellfirepvp.astralsorcery.common.item.block.*;
import hellfirepvp.astralsorcery.common.item.crystal.AttunedCelestialCrystalItem;
import hellfirepvp.astralsorcery.common.item.crystal.AttunedRockCrystalItem;
import hellfirepvp.astralsorcery.common.item.crystal.CelestialCrystalItem;
import hellfirepvp.astralsorcery.common.item.crystal.RockCrystalItem;
import hellfirepvp.astralsorcery.common.item.dust.IlluminationPowderItem;
import hellfirepvp.astralsorcery.common.item.dust.NocturnalPowderItem;
import hellfirepvp.astralsorcery.common.item.dust.VividPowderItem;
import hellfirepvp.astralsorcery.common.item.tool.*;
import hellfirepvp.astralsorcery.common.item.wand.ArchitectWandItem;
import hellfirepvp.astralsorcery.common.item.wand.BlinkWandItem;
import hellfirepvp.astralsorcery.common.item.wand.ExchangeWandItem;
import hellfirepvp.astralsorcery.common.item.wand.GrapplingWandItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: ItemsAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class ItemsAS {

    public static void init() {

    }

    public static final Set<Item> REGISTERED_ITEMS = Sets.newLinkedHashSet();

    public static final Tier CRYSTAL_TOOL_TIER = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            741, 4F, 1F, 26, () -> Ingredient.EMPTY);

    //------------------------------- ITEMS -------------------------------//

    public static TomeItem TOME = register("tome", TomeItem::new);
    public static AstrolabeItem ASTROLABE = register("astrolabe", AstrolabeItem::new);
    public static WandItem WAND = register("wand", WandItem::new);
    public static ChiselItem CHISEL = register("chisel", ChiselItem::new);
    public static LinkingToolItem LINKING_TOOL = register("linking_tool", LinkingToolItem::new);
    public static IlluminationWandItem ILLUMINATION_WAND = register("illumination_wand", IlluminationWandItem::new);
    public static ArchitectWandItem ARCHITECT_WAND = register("architect_wand", ArchitectWandItem::new);
    public static ExchangeWandItem EXCHANGE_WAND = register("exchange_wand", ExchangeWandItem::new);
    public static BlinkWandItem BLINK_WAND = register("blink_wand", BlinkWandItem::new);
    public static GrapplingWandItem GRAPPLING_WAND = register("grappling_wand", GrapplingWandItem::new);
    public static KnowledgeShareItem KNOWLEDGE_SHARE = register("knowledge_share", KnowledgeShareItem::new);
    public static ConstellationPaperItem CONSTELLATION_PAPER = register("constellation_paper", ConstellationPaperItem::new);
    public static ArtifactItem ARTIFACT = register("artifact", ArtifactItem::new);
    public static ArtifactShardItem ARTIFACT_SHARD = register("artifact_shard", ArtifactShardItem::new);

    public static Item AQUAMARINE = registerSimple("aquamarine");
    public static Item GLASS_LENS = registerSimple("glass_lens");
    public static Item RESONATING_GEM = registerSimple("resonating_gem");
    public static Item PARCHMENT = registerSimple("parchment");
    public static Item STARDUST = registerSimple("stardust");
    public static StarmetalIngotItem STARMETAL_INGOT = register("starmetal_ingot", StarmetalIngotItem::new);
    public static Item RAW_STARMETAL = registerSimple("raw_starmetal");
    public static PerkSealItem PERK_SEAL = register("perk_seal", PerkSealItem::new);
    public static PerkNullifierItem PERK_NULLIFIER = register("perk_nullifier", PerkNullifierItem::new);
    public static DynamismGemItem DYNAMISM_GEM_SKY = register("dynamism_gem_sky", () -> new DynamismGemItem(DynamismGemItem.GemType.SKY));
    public static DynamismGemItem DYNAMISM_GEM_DAY = register("dynamism_gem_day", () -> new DynamismGemItem(DynamismGemItem.GemType.DAY));
    public static DynamismGemItem DYNAMISM_GEM_NIGHT = register("dynamism_gem_night", () -> new DynamismGemItem(DynamismGemItem.GemType.NIGHT));

    public static IlluminationPowderItem ILLUMINATION_POWDER = register("illumination_powder", IlluminationPowderItem::new);
    public static NocturnalPowderItem NOCTURNAL_POWDER = register("nocturnal_powder", NocturnalPowderItem::new);
    public static VividPowderItem VIVID_POWDER = register("vivid_powder", VividPowderItem::new);
    public static ShiftingStarItem SHIFTING_STAR = register("shifting_star", ShiftingStarItem::new);
    public static ShiftingStarItem SHIFTING_STAR_AEVITAS = register("shifting_star_aevitas", () -> new ShiftingStarItem(() -> ConstellationsAS.AEVITAS));
    public static ShiftingStarItem SHIFTING_STAR_ARMARA = register("shifting_star_armara", () -> new ShiftingStarItem(() -> ConstellationsAS.ARMARA));
    public static ShiftingStarItem SHIFTING_STAR_DISCIDIA = register("shifting_star_discidia", () -> new ShiftingStarItem(() -> ConstellationsAS.DISCIDIA));
    public static ShiftingStarItem SHIFTING_STAR_EVORSIO = register("shifting_star_evorsio", () -> new ShiftingStarItem(() -> ConstellationsAS.EVORSIO));
    public static ShiftingStarItem SHIFTING_STAR_VICIO = register("shifting_star_vicio", () -> new ShiftingStarItem(() -> ConstellationsAS.VICIO));
    public static AkashicSingularityItem AKASHIC_SINGULARITY = register("akashic_singularity", AkashicSingularityItem::new);

    public static LumenCrystalItem LUMEN_CRYSTAL = register("lumen_crystal", LumenCrystalItem::new);
    public static RockCrystalItem ROCK_CRYSTAL = register("rock_crystal", RockCrystalItem::new);
    public static CelestialCrystalItem CELESTIAL_CRYSTAL = register("celestial_crystal", CelestialCrystalItem::new);
    public static AttunedRockCrystalItem ATTUNED_ROCK_CRYSTAL = register("attuned_rock_crystal", AttunedRockCrystalItem::new);
    public static AttunedCelestialCrystalItem ATTUNED_CELESTIAL_CRYSTAL = register("attuned_celestial_crystal", AttunedCelestialCrystalItem::new);

    public static CrystalAxeItem CRYSTAL_AXE = register("crystal_axe", CrystalAxeItem::new);
    public static CrystalPickaxeItem CRYSTAL_PICKAXE = register("crystal_pickaxe", CrystalPickaxeItem::new);
    public static CrystalShovelItem CRYSTAL_SHOVEL = register("crystal_shovel", CrystalShovelItem::new);
    public static CrystalSwordItem CRYSTAL_SWORD = register("crystal_sword", CrystalSwordItem::new);
    public static IridescentCrystalAxeItem IRIDESCENT_CRYSTAL_AXE = register("iridescent_crystal_axe", IridescentCrystalAxeItem::new);
    public static IridescentCrystalPickaxeItem IRIDESCENT_CRYSTAL_PICKAXE = register("iridescent_crystal_pickaxe", IridescentCrystalPickaxeItem::new);
    public static IridescentCrystalShovelItem IRIDESCENT_CRYSTAL_SHOVEL = register("iridescent_crystal_shovel", IridescentCrystalShovelItem::new);
    public static IridescentCrystalSwordItem IRIDESCENT_CRYSTAL_SWORD = register("iridescent_crystal_sword", IridescentCrystalSwordItem::new);
    public static EnchantmentAmuletItem ENCHANTMENT_AMULET = register("enchantment_amulet", EnchantmentAmuletItem::new);
    public static StardewItem STARDEW = register("stardew", StardewItem::new);

    //------------------------------- BLOCK -------------------------------//

    public static BlockItem BLOCK_MARBLE_ARCH = registerBlockItem(BlocksAS.MARBLE_ARCH);
    public static BlockItem BLOCK_MARBLE_BRICKS = registerBlockItem(BlocksAS.MARBLE_BRICKS);
    public static BlockItem BLOCK_MARBLE_CHISELED = registerBlockItem(BlocksAS.MARBLE_CHISELED);
    public static BlockItem BLOCK_MARBLE_ENGRAVED = registerBlockItem(BlocksAS.MARBLE_ENGRAVED);
    public static BlockItem BLOCK_MARBLE_PILLAR = registerBlockItem(BlocksAS.MARBLE_PILLAR);
    public static BlockItem BLOCK_MARBLE_RAW = registerBlockItem(BlocksAS.MARBLE_RAW);
    public static BlockItem BLOCK_MARBLE_RUNED = registerBlockItem(BlocksAS.MARBLE_RUNED);
    public static BlockItem BLOCK_MARBLE_SLAB = registerBlockItem(BlocksAS.MARBLE_SLAB);
    public static BlockItem BLOCK_MARBLE_STAIRS = registerBlockItem(BlocksAS.MARBLE_STAIRS);

    public static BlockItem BLOCK_SOOTY_MARBLE_ARCH = registerBlockItem(BlocksAS.SOOTY_MARBLE_ARCH);
    public static BlockItem BLOCK_SOOTY_MARBLE_BRICKS = registerBlockItem(BlocksAS.SOOTY_MARBLE_BRICKS);
    public static BlockItem BLOCK_SOOTY_MARBLE_CHISELED = registerBlockItem(BlocksAS.SOOTY_MARBLE_CHISELED);
    public static BlockItem BLOCK_SOOTY_MARBLE_ENGRAVED = registerBlockItem(BlocksAS.SOOTY_MARBLE_ENGRAVED);
    public static BlockItem BLOCK_SOOTY_MARBLE_PILLAR = registerBlockItem(BlocksAS.SOOTY_MARBLE_PILLAR);
    public static BlockItem BLOCK_SOOTY_MARBLE_RAW = registerBlockItem(BlocksAS.SOOTY_MARBLE_RAW);
    public static BlockItem BLOCK_SOOTY_MARBLE_RUNED = registerBlockItem(BlocksAS.SOOTY_MARBLE_RUNED);
    public static BlockItem BLOCK_SOOTY_MARBLE_SLAB = registerBlockItem(BlocksAS.SOOTY_MARBLE_SLAB);
    public static BlockItem BLOCK_SOOTY_MARBLE_STAIRS = registerBlockItem(BlocksAS.SOOTY_MARBLE_STAIRS);

    public static BlockItem BLOCK_INFUSED_WOOD_RAW = registerBlockItem(BlocksAS.INFUSED_WOOD_RAW);
    public static BlockItem BLOCK_INFUSED_WOOD_ARCH = registerBlockItem(BlocksAS.INFUSED_WOOD_ARCH);
    public static BlockItem BLOCK_INFUSED_WOOD_COLUMN = registerBlockItem(BlocksAS.INFUSED_WOOD_COLUMN);
    public static BlockItem BLOCK_INFUSED_WOOD_ENGRAVED = registerBlockItem(BlocksAS.INFUSED_WOOD_ENGRAVED);
    public static BlockItem BLOCK_INFUSED_WOOD_ENRICHED = registerBlockItem(BlocksAS.INFUSED_WOOD_ENRICHED);
    public static BlockItem BLOCK_INFUSED_WOOD_INFUSED = registerBlockItem(BlocksAS.INFUSED_WOOD_INFUSED);
    public static BlockItem BLOCK_INFUSED_WOOD_PLANKS = registerBlockItem(BlocksAS.INFUSED_WOOD_PLANKS);
    public static BlockItem BLOCK_INFUSED_WOOD_SLAB = registerBlockItem(BlocksAS.INFUSED_WOOD_SLAB);
    public static BlockItem BLOCK_INFUSED_WOOD_STAIRS = registerBlockItem(BlocksAS.INFUSED_WOOD_STAIRS);

    public static BlockItem BLOCK_AQUAMARINE_SHALE_ORE = registerBlockItem(BlocksAS.AQUAMARINE_SHALE);
    public static BlockItem BLOCK_ROCK_CRYSTAL_ORE = registerBlockItem(BlocksAS.ROCK_CRYSTAL_ORE);
    public static BlockItem BLOCK_STARMETAL_ORE = registerBlockItem(BlocksAS.STARMETAL_ORE);
    public static BlockItem BLOCK_RAW_STARMETAL_BLOCK = registerBlockItem(BlocksAS.RAW_STARMETAL_BLOCK);

    public static BlockItem BLOCK_GLIMMER_AMARANTH = registerBlockItem(BlocksAS.GLIMMER_AMARANTH);
    public static BlockItem BLOCK_HYACINTH = registerBlockItem(BlocksAS.HYACINTH);
    public static BlockItem BLOCK_IRIS = registerBlockItem(BlocksAS.IRIS);
    public static BlockItem BLOCK_ORCHID = registerBlockItem(BlocksAS.ORCHID);
    public static BlockItem BLOCK_PROTEA = registerBlockItem(BlocksAS.PROTEA);
    public static BlockItem BLOCK_THISTLE = registerBlockItem(BlocksAS.THISTLE);

    public static BlockItem BLOCK_ALTAR_ILLUMINATION = registerBlockItem(BlocksAS.ALTAR_ILLUMINATION);
    public static BlockItem BLOCK_ALTAR_RESONANCE = registerBlockItem(BlocksAS.ALTAR_RESONANCE);
    public static BlockItem BLOCK_ALTAR_LUMINANCE = registerBlockItem(BlocksAS.ALTAR_LUMINANCE);
    public static BlockItem BLOCK_ALTAR_RADIANCE = registerBlockItem(BlocksAS.ALTAR_RADIANCE);

    public static BlockItem BLOCK_FOCUS_RELAY = registerBlockItem(BlocksAS.FOCUS_RELAY);
    public static BlockItem BLOCK_CELESTIAL_CRYSTAL_CLUSTER = registerBlockItem(BlocksAS.CELESTIAL_CRYSTAL_CLUSTER, CelestialCrystalClusterBlockItem::new);
    public static BlockItem BLOCK_GEM_CRYSTAL_CLUSTER = registerBlockItem(BlocksAS.GEM_CRYSTAL_CLUSTER, GemCrystalClusterBlockItem::new);
    public static BlockItem BLOCK_LUMEN_CRYSTAL_CLUSTER = registerBlockItem(BlocksAS.LUMEN_CRYSTAL_CLUSTER, LumenCrystalClusterBlockItem::new);

    public static BlockItem BLOCK_LUMEN_ARRAY = registerBlockItem(BlocksAS.LUMEN_ARRAY);
    public static BlockItem BLOCK_LUMEN_ALCHEMY_ARRAY = registerBlockItem(BlocksAS.LUMEN_ALCHEMY_ARRAY);
    public static BlockItem BLOCK_LUMEN_FILAMENT = registerBlockItem(BlocksAS.LUMEN_FILAMENT);
    public static BlockItem BLOCK_LUMEN_CRYSTALLIZER = registerBlockItem(BlocksAS.LUMEN_CRYSTALLIZER);
    public static BlockItem BLOCK_LIGHTWELL = registerBlockItem(BlocksAS.LIGHTWELL);
    public static BlockItem BLOCK_INFUSER = registerBlockItem(BlocksAS.INFUSER);
    public static BlockItem BLOCK_CHALICE = registerBlockItem(BlocksAS.CHALICE);
    public static BlockItem BLOCK_ATTUNEMENT_ALTAR = registerBlockItem(BlocksAS.ATTUNEMENT_ALTAR);
    public static BlockItem BLOCK_TREE_BEACON = registerBlockItem(BlocksAS.TREE_BEACON);
    public static BlockItem BLOCK_CELESTIAL_GATEWAY = registerBlockItem(BlocksAS.CELESTIAL_GATEWAY,
            block -> new BlockItemCustom(block, new Item.Properties().component(DataComponentsAS.COLOR, ColorComponent.DEFAULT_YELLOW)));

    public static BlockItem BLOCK_LENS = registerBlockItem(BlocksAS.LENS, LensBlockItem::new);
    public static BlockItem BLOCK_PRISM = registerBlockItem(BlocksAS.PRISM, PrismBlockItem::new);
    public static BlockItem BLOCK_STARLIGHT_FOCUS_ROCK_CRYSTAL =
            registerBlockItem(BlocksAS.STARLIGHT_FOCUS_ROCK_CRYSTAL, StarlightFocusCrystalBlockItem::new);
    public static BlockItem BLOCK_STARLIGHT_FOCUS_CELESTIAL_CRYSTAL =
            registerBlockItem(BlocksAS.STARLIGHT_FOCUS_CELESTIAL_CRYSTAL, StarlightFocusCrystalBlockItem::new);
    public static BlockItem BLOCK_STELLAR_FILAMENT = registerBlockItem(BlocksAS.STELLAR_FILAMENT);
    public static BlockItem BLOCK_CAVE_ILLUMINATOR = registerBlockItem(BlocksAS.CAVE_ILLUMINATOR,
            block -> new BlockItemCustom(block, new Item.Properties().component(DataComponentsAS.COLOR, ColorComponent.DEFAULT_YELLOW)));

    public static BlockItem BLOCK_STRUCTURE_MARKER = registerBlockItem(BlocksAS.STRUCTURE_MARKER, BlockItem::new);

    //----------------------------- REGISTER ------------------------------//

    private static BlockItem registerBlockItem(Block block) {
        return registerBlockItem(block, BlockItemCustom::new);
    }

    private static <T extends BlockItem, B extends Block> T registerBlockItem(B block, Function<B, T> blockItem) {
        return register(BuiltInRegistries.BLOCK.getKey(block).getPath(), () -> blockItem.apply(block));
    }

    private static BlockItem registerBlockItem(Block block, BiFunction<Block, Item.Properties, BlockItem> ctor) {
        Item.Properties blockItemProperties = new Item.Properties();
        ResourceLocation key = BuiltInRegistries.BLOCK.getKey(block);
        return register(key.getPath(), () -> ctor.apply(block, blockItemProperties));
    }

    private static Item registerSimple(String name) {
        return register(name, () -> new ItemCustom(new Item.Properties()));
    }

    public static <T extends Item> T register(String name, Supplier<T> supplier) {
        T item = Registry.register(BuiltInRegistries.ITEM, AstralSorcery.key(name), supplier.get());
        REGISTERED_ITEMS.add(item);
        return item;
    }
}
