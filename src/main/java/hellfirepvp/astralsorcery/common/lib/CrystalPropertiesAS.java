/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.crystal.CrystalProperty;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;

import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: CrystalPropertiesAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class CrystalPropertiesAS {

    public static void init() {

    }

    public static final CrystalProperty SIZE = register("size", () -> new CrystalProperty(ChatFormatting.GRAY, 8));
    public static final CrystalProperty PURITY = register("purity", () -> new CrystalProperty(ChatFormatting.GRAY, 4));
    public static final CrystalProperty CUT = register("cut", () -> new CrystalProperty(ChatFormatting.GRAY, 4));

    public static final CrystalProperty TOOL_EFFICIENCY = register("tool_efficiency", () -> new CrystalProperty(ChatFormatting.GRAY, 3));
    public static final CrystalProperty TOOL_DURABILITY = register("tool_durability", () -> new CrystalProperty(ChatFormatting.GRAY, 3));

    private static CrystalProperty register(String name, Supplier<CrystalProperty> supplier) {
        return Registry.register(RegistriesAS.REGISTRY_CRYSTAL_PROPERTIES, AstralSorcery.key(name), supplier.get());
    }
}
