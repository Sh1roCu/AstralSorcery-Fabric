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
import hellfirepvp.astralsorcery.common.recipe.altar.effect.*;
import net.minecraft.core.Registry;

import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AltarEffectsAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AltarEffectsAS {

    public static void init() {

    }

    public static final DefaultAltarEffectCentralBeam DEFAULT_CENTRAL_BEAM =
            register("default_central_beam", DefaultAltarEffectCentralBeam::new);
    public static final DefaultAltarEffectAltarSparkle DEFAULT_ALTAR_SPARKLE =
            register("default_altar_sparkle", DefaultAltarEffectAltarSparkle::new);
    public static final DefaultAltarEffectRelayInput DEFAULT_RELAY_INPUT =
            register("default_relay_input", DefaultAltarEffectRelayInput::new);
    public static final DefaultAltarEffectLumenInput DEFAULT_LUMEN_INPUT =
            register("default_lumen_input", DefaultAltarEffectLumenInput::new);

    private static <T extends AltarEffect> T register(String name, Supplier<T> supplier) {
        return Registry.register(RegistriesAS.REGISTRY_ALTAR_EFFECTS, AstralSorcery.key(name), supplier.get());
    }
}
