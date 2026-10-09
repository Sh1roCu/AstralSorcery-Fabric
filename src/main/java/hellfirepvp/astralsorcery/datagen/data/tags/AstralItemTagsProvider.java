/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.datagen.data.tags;

import hellfirepvp.astralsorcery.common.lib.ItemsAS;
import hellfirepvp.astralsorcery.common.lib.constants.TagsAS;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;

import java.util.concurrent.CompletableFuture;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AstralItemTagsProvider
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AstralItemTagsProvider extends FabricTagProvider.ItemTagProvider {

    public AstralItemTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture, AstralBlockTagsProvider.getInstance());
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        copy(TagsAS.Blocks.INFUSED_WOOD, TagsAS.Items.INFUSED_WOOD);
        copy(TagsAS.Blocks.MARBLE, TagsAS.Items.MARBLE);
        copy(TagsAS.Blocks.SOOTY_MARBLE, TagsAS.Items.SOOTY_MARBLE);
        getOrCreateTagBuilder(TagsAS.Items.GEMS_AQUAMARINE)
                .add(ItemsAS.AQUAMARINE.asItem());
        getOrCreateTagBuilder(ConventionalItemTags.RAW_MATERIALS)
                .add(ItemsAS.RAW_STARMETAL.asItem());

        getOrCreateTagBuilder(ItemTags.LECTERN_BOOKS)
                .add(ItemsAS.TOME.asItem());

        getOrCreateTagBuilder(TagsAS.Items.CRYSTAL)
                .forceAddTag(TagsAS.Items.CELESTIAL_CRYSTAL)
                .forceAddTag(TagsAS.Items.ATTUNED_CRYSTAL)
                .add(ItemsAS.ROCK_CRYSTAL.asItem());
        getOrCreateTagBuilder(TagsAS.Items.CELESTIAL_CRYSTAL)
                .add(ItemsAS.CELESTIAL_CRYSTAL.asItem())
                .add(ItemsAS.ATTUNED_CELESTIAL_CRYSTAL.asItem());
        getOrCreateTagBuilder(TagsAS.Items.ROCK_CRYSTAL)
                .add(ItemsAS.ROCK_CRYSTAL.asItem())
                .add(ItemsAS.ATTUNED_ROCK_CRYSTAL.asItem());
        getOrCreateTagBuilder(TagsAS.Items.ATTUNED_CRYSTAL)
                .add(ItemsAS.ATTUNED_ROCK_CRYSTAL.asItem())
                .add(ItemsAS.ATTUNED_CELESTIAL_CRYSTAL.asItem());

        getOrCreateTagBuilder(TagsAS.Items.FUNCTIONAL_ALTAR_CONSTELLATION_ITEM)
                .forceAddTag(TagsAS.Items.ATTUNED_CRYSTAL);
        getOrCreateTagBuilder(TagsAS.Items.FUNCTIONAL_ATTUNEABLE_ITEM)
                .add(ItemsAS.ROCK_CRYSTAL.asItem())
                .add(ItemsAS.CELESTIAL_CRYSTAL.asItem());
        getOrCreateTagBuilder(TagsAS.Items.FUNCTIONAL_PERKTREE_SOCKETABLE_ITEM)
                .add(ItemsAS.DYNAMISM_GEM_SKY.asItem())
                .add(ItemsAS.DYNAMISM_GEM_DAY.asItem())
                .add(ItemsAS.DYNAMISM_GEM_NIGHT.asItem());

        getOrCreateTagBuilder(TagsAS.Items.CURIOS_NECKLACE)
                .add(ItemsAS.ENCHANTMENT_AMULET.asItem());

        getOrCreateTagBuilder(ItemTags.AXES)
                .add(ItemsAS.CRYSTAL_AXE.asItem())
                .add(ItemsAS.IRIDESCENT_CRYSTAL_AXE.asItem());
        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(ItemsAS.CRYSTAL_PICKAXE.asItem())
                .add(ItemsAS.IRIDESCENT_CRYSTAL_PICKAXE.asItem());
        getOrCreateTagBuilder(ItemTags.SHOVELS)
                .add(ItemsAS.CRYSTAL_SHOVEL.asItem())
                .add(ItemsAS.IRIDESCENT_CRYSTAL_SHOVEL.asItem());
        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(ItemsAS.CRYSTAL_SWORD.asItem())
                .add(ItemsAS.IRIDESCENT_CRYSTAL_SWORD.asItem());

        getOrCreateTagBuilder(ConventionalItemTags.INGOTS)
                .add(ItemsAS.STARMETAL_INGOT.asItem());

        getOrCreateTagBuilder(ItemTags.SMALL_FLOWERS)
                .add(ItemsAS.BLOCK_GLIMMER_AMARANTH)
                .add(ItemsAS.BLOCK_HYACINTH)
                .add(ItemsAS.BLOCK_IRIS)
                .add(ItemsAS.BLOCK_ORCHID)
                .add(ItemsAS.BLOCK_PROTEA)
                .add(ItemsAS.BLOCK_THISTLE);
    }
}
