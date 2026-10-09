/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.util.data;

import hellfirepvp.astralsorcery.AstralSorcery;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: MenuTypeRegistryObject
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public record MenuTypeRegistryObject<T extends AbstractContainerMenu>(MenuType<T> menuType) {

    public MenuType<T> type() {
        return this.menuType();
    }

    public Component getDisplayName() {
        return Component.translatable("screen.%s.%s".formatted(AstralSorcery.MODID, BuiltInRegistries.MENU.getKey(this.menuType()).getPath()));
    }
}
