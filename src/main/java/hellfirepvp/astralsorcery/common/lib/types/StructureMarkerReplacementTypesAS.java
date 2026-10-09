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
import hellfirepvp.astralsorcery.common.worldgen.structure.marker.*;
import net.minecraft.core.Registry;

import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: StructureMarkerReplacementTypesAS
 * Created by HellFirePvP
 * Date: 06.10.2026 / 20:15
 */
public class StructureMarkerReplacementTypesAS {

    public static void init() {
    }

    public static final StructureMarkerReplacement.Type<MarkerReplacementNothing> NOTHING =
            register("nothing", () -> MarkerReplacementNothing.TYPE);
    public static final StructureMarkerReplacement.Type<MarkerReplacementBlock> BLOCK =
            register("block", () -> MarkerReplacementBlock.TYPE);
    public static final StructureMarkerReplacement.Type<MarkerReplacementLootContainer> LOOT_CONTAINER =
            register("loot_container", () -> MarkerReplacementLootContainer.TYPE);
    public static final StructureMarkerReplacement.Type<MarkerReplacementRandom> RANDOM =
            register("random", () -> MarkerReplacementRandom.TYPE);
    public static final StructureMarkerReplacement.Type<MarkerReplacementBiome> BIOME =
            register("biome", () -> MarkerReplacementBiome.TYPE);

    private static <T extends StructureMarkerReplacement> StructureMarkerReplacement.Type<T> register(String name, Supplier<StructureMarkerReplacement.Type<T>> supplier) {
        return Registry.register(RegistriesAS.REGISTRY_STRUCTURE_MARKER_REPLACEMENT_TYPES, AstralSorcery.key(name), supplier.get());
    }
}
