/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.container.*;
import hellfirepvp.astralsorcery.common.container.provider.ContainerAltarProvider;
import hellfirepvp.astralsorcery.common.container.provider.ContainerTomePapersProvider;
import hellfirepvp.astralsorcery.common.util.data.MenuTypeRegistryObject;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: MenuTypesAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class MenuTypesAS {

    public static void init() {

    }

    public static final MenuTypeRegistryObject<ContainerTomePapers> TOME_PAPERS =
            register("tome_papers", ContainerTomePapersProvider.TYPE);

    public static final MenuTypeRegistryObject<ContainerAltarIllumination> ALTAR_ILLUMINATION =
            register("altar_illumination", ContainerAltarProvider.ILLUMINATION_TYPE);
    public static final MenuTypeRegistryObject<ContainerAltarResonance> ALTAR_RESONANCE =
            register("altar_resonance", ContainerAltarProvider.Resonance.TYPE);
    public static final MenuTypeRegistryObject<ContainerAltarLuminance> ALTAR_LUMINANCE =
            register("altar_luminance", ContainerAltarProvider.LUMINANCE_TYPE);
    public static final MenuTypeRegistryObject<ContainerAltarRadiance> ALTAR_RADIANCE =
            register("altar_radiance", ContainerAltarProvider.RADIANCE_TYPE);

    private static <T extends AbstractContainerMenu, D> MenuTypeRegistryObject<T> register(String name, ExtendedScreenHandlerType<T, D> type) {

        MenuType<T> menu = Registry.register(BuiltInRegistries.MENU, AstralSorcery.key(name), type);
        return new MenuTypeRegistryObject<>(menu);
    }
}
