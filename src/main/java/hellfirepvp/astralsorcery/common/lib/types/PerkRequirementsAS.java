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
import hellfirepvp.astralsorcery.common.perk.tree.requirement.PerkRequirement;
import hellfirepvp.astralsorcery.common.perk.tree.requirement.PerkRequirementConstellation;
import hellfirepvp.astralsorcery.common.perk.tree.requirement.PerkRequirementProgress;
import net.minecraft.core.Registry;

import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: PerkRequirementsAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class PerkRequirementsAS {

    public static void init() {

    }

    public static final PerkRequirement.Type<PerkRequirementConstellation> CONSTELLATION =
            register("constellation", () -> PerkRequirementConstellation.TYPE);
    public static final PerkRequirement.Type<PerkRequirementProgress> PROGRESS =
            register("progress", () -> PerkRequirementProgress.TYPE);

    private static <T extends PerkRequirement> PerkRequirement.Type<T> register(String name, Supplier<PerkRequirement.Type<T>> supplier) {
        return Registry.register(RegistriesAS.REGISTRY_PERK_REQUIREMENT_TYPES, AstralSorcery.key(name), supplier.get());
    }
}
