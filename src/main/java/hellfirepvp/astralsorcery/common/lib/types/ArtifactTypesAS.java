/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib.types;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.artifact.ArtifactType;
import hellfirepvp.astralsorcery.common.lib.RegistriesAS;
import net.minecraft.core.Registry;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: ArtifactTypesAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class ArtifactTypesAS {

    public static void init() {

    }

    public static final ArtifactType SIDEREAL = simpleType("sidereal");
    public static final ArtifactType LUMINOUS = simpleType("luminous");
    public static final ArtifactType CHRONAL = simpleType("chronal");
    public static final ArtifactType ASTRIFORM = simpleType("astriform");
    public static final ArtifactType AXIOMATIC = simpleType("axiomatic");
    public static final ArtifactType SPECTRAL = simpleType("spectral");

    private static ArtifactType simpleType(String name) {
        return Registry.register(RegistriesAS.REGISTRY_ARTIFACT_TYPES, AstralSorcery.key(name), new ArtifactType());
    }
}
