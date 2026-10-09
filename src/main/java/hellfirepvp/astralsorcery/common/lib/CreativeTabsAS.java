/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.item.ArtifactItem;
import hellfirepvp.astralsorcery.common.item.block.LumenCrystalClusterBlockItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: CreativeTabsAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class CreativeTabsAS {

    public static void init() {

    }

    public static CreativeModeTab CREATIVE_TAB_AS = register("common", () -> FabricItemGroup.builder()
            .title(Component.translatable("itemGroup.astralsorcery"))
            .icon(ItemsAS.TOME::getDefaultInstance)
            .build());
    public static CreativeModeTab CREATIVE_TAB_AS_PAPERS = register("papers", () -> FabricItemGroup.builder()
            .title(Component.translatable("itemGroup.astralsorcery.papers"))
            .icon(ItemsAS.CONSTELLATION_PAPER::getDefaultInstance)
            // .withTabsBefore(AstralSorcery.key("common"))
            .build());
    public static CreativeModeTab CREATIVE_TAB_AS_LUMEN = register("lumen", () -> FabricItemGroup.builder()
            .title(Component.translatable("itemGroup.astralsorcery.lumen"))
            .icon(() -> LumenCrystalClusterBlockItem.getCluster(LumenAS.AEVITAS.holder(), 4))
            // .withTabsBefore(AstralSorcery.key("papers"))
            .build());
    public static CreativeModeTab CREATIVE_TAB_AS_ATTUNED_CRYSTALS = register("attuned_crystals", () -> FabricItemGroup.builder()
            .title(Component.translatable("itemGroup.astralsorcery.attuned_crystals"))
            .icon(ItemsAS.CELESTIAL_CRYSTAL::getDefaultInstance)
            // .withTabsBefore(AstralSorcery.key("lumen"))
            .build());
    public static CreativeModeTab CREATIVE_TAB_AS_ARTIFACTS = register("artifacts", () -> FabricItemGroup.builder()
            .title(Component.translatable("itemGroup.astralsorcery.artifacts"))
            .icon(() -> ArtifactItem.defaultStack().orElseGet(ItemsAS.AQUAMARINE::getDefaultInstance))
            // .withTabsBefore(AstralSorcery.key("attuned_crystals"))
            .build());

    private static CreativeModeTab register(String name, Supplier<CreativeModeTab> supplier) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, AstralSorcery.key(name), supplier.get());
    }
}
