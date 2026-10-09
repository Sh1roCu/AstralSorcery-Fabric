/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib.types;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.lib.RegistriesAS;
import hellfirepvp.astralsorcery.common.research.tome.*;
import net.minecraft.core.Registry;

import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: TomePageTypesAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class TomePageTypesAS {

    public static void init() {

    }

    public static final TomePage.TomePageType<TomePageEmpty> EMPTY_PAGE =
            register("empty", () -> new TomePage.TomePageType<>(TomePageEmpty.CODEC, TomePageEmpty.STREAM_CODEC));
    public static final TomePage.TomePageType<TomePageText> TEXT_PAGE =
            register("text", () -> new TomePage.TomePageType<>(TomePageText.CODEC, TomePageText.STREAM_CODEC));
    public static final TomePage.TomePageType<?> STRUCTURE_PAGE =
            register("structure", () -> new TomePage.TomePageType<>(TomePageStructure.CODEC, TomePageStructure.STREAM_CODEC));
    public static final TomePage.TomePageType<?> RECIPE_PAGE =
            register("recipe", () -> new TomePage.TomePageType<>(TomePageRecipe.CODEC, TomePageRecipe.STREAM_CODEC));
    public static final TomePage.TomePageType<?> CONSTELLATION_PAGE =
            register("constellation", () -> new TomePage.TomePageType<>(TomePageConstellation.CODEC, TomePageConstellation.STREAM_CODEC));
    public static final TomePage.TomePageType<?> LUMEN_DESCRIPTION_PAGE =
            register("lumen_description", () -> new TomePage.TomePageType<>(TomePageLumenDescription.CODEC, TomePageLumenDescription.STREAM_CODEC));

    private static <T extends TomePage> TomePage.TomePageType<T> register(String name, Supplier<TomePage.TomePageType<T>> supplier) {
        return Registry.register(RegistriesAS.REGISTRY_TOME_PAGE_TYPES, AstralSorcery.key(name), supplier.get());
    }

}
