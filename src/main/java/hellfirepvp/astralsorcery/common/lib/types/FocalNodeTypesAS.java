/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib.types;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.focal.node.BasicFocalPointNode;
import hellfirepvp.astralsorcery.common.focal.node.FocalPointNode;
import hellfirepvp.astralsorcery.common.lib.RegistriesAS;
import net.minecraft.core.Registry;

import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: FocalNodeTypesAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class FocalNodeTypesAS {

    public static void init() {

    }

    public static final FocalPointNode.Type<BasicFocalPointNode> BASIC =
            register("basic", () -> new FocalPointNode.Type<>(BasicFocalPointNode.CODEC, BasicFocalPointNode.STREAM_CODEC));

    private static <T extends FocalPointNode> FocalPointNode.Type<T> register(String name, Supplier<FocalPointNode.Type<T>> supplier) {
        return Registry.register(RegistriesAS.REGISTRY_FOCAL_NODE_TYPES, AstralSorcery.key(name), supplier.get());
    }
}
