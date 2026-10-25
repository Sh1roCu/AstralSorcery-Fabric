/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.constellation.BaseConstellation;
import hellfirepvp.astralsorcery.common.crystal.CrystalProperty;
import hellfirepvp.astralsorcery.common.crystal.CrystalPropertyConstellation;
import hellfirepvp.astralsorcery.common.crystal.CrystalPropertyLumen;
import hellfirepvp.astralsorcery.common.init.InitConstellations;
import hellfirepvp.astralsorcery.common.lumen.Lumen;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    private static final List<CrystalPropertyConstellation> CONSTELLATION_PROPERTIES = new ArrayList<>();
    private static final List<CrystalPropertyLumen> LUMEN_PROPERTIES = new ArrayList<>();

    public static final CrystalProperty SIZE = register("size", () -> new CrystalProperty(ChatFormatting.GRAY, 8));
    public static final CrystalProperty PURITY = register("purity", () -> new CrystalProperty(ChatFormatting.GRAY, 4));
    public static final CrystalProperty CUT = register("cut", () -> new CrystalProperty(ChatFormatting.GRAY, 4));

    public static final CrystalProperty TOOL_EFFICIENCY = register("tool_efficiency", () -> new CrystalProperty(ChatFormatting.GRAY, 3));
    public static final CrystalProperty TOOL_DURABILITY = register("tool_durability", () -> new CrystalProperty(ChatFormatting.GRAY, 3));
    public static final CrystalProperty STARLIGHT_FOCUS = register("starlight_focus", () -> new CrystalProperty(ChatFormatting.GRAY, 4));
    public static final CrystalProperty LUMEN_CHANNELING = register("lumen_channeling", () -> new CrystalProperty(ChatFormatting.GRAY, 4));

    public static Optional<CrystalPropertyConstellation> getProperty(BaseConstellation cst) {
        return CONSTELLATION_PROPERTIES.stream()
                .filter(property -> property.getConstellation().equals(cst))
                .findFirst();
    }

    public static Optional<CrystalPropertyLumen> getProperty(Lumen lumen) {
        return LUMEN_PROPERTIES.stream()
                .filter(property -> property.getLumen().equals(lumen))
                .findFirst();
    }

    static {
        InitConstellations.REGISTERED_CONSTELLATIONS.forEach(value -> {
            CONSTELLATION_PROPERTIES.add((CrystalPropertyConstellation) register("constellation_" + RegistriesAS.REGISTRY_CONSTELLATIONS.getKey(value).getPath(),
                    () -> new CrystalPropertyConstellation(value))
            );
        });

        LumenAS.REGISTERED_LUMEN.forEach(holder -> {
            if (LumenAS.NONE.getKey().equals(holder.getKey())) return;
            LUMEN_PROPERTIES.add((CrystalPropertyLumen) register("lumen_" + holder.getKey().location().getPath(),
                    () -> new CrystalPropertyLumen(holder.get()))
            );
        });
    }

    private static CrystalProperty register(String name, Supplier<CrystalProperty> supplier) {
        return Registry.register(RegistriesAS.REGISTRY_CRYSTAL_PROPERTIES, AstralSorcery.key(name), supplier.get());
    }
}
