/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.starlight.api.provider.TransmissionNodeProvider;
import hellfirepvp.astralsorcery.common.tile.network.provider.FocusCrystalSourceNodeProvider;
import hellfirepvp.astralsorcery.common.tile.network.provider.ForwardingStarlightReceiverNodeProvider;
import hellfirepvp.astralsorcery.common.tile.network.provider.SimpleSingleTransmissionNodeProvider;
import hellfirepvp.astralsorcery.common.tile.network.provider.SimpleTransmissionNodeProvider;
import net.minecraft.core.Registry;

import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: StarlightNetworkNodesAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class StarlightNetworkNodesAS {

    public static void init() {

    }

    public static final SimpleTransmissionNodeProvider SIMPLE_NODE = register("simple", SimpleTransmissionNodeProvider::new);
    public static final SimpleSingleTransmissionNodeProvider SIMPLE_SINGLE_NODE = register("simple_single", SimpleSingleTransmissionNodeProvider::new);
    public static final ForwardingStarlightReceiverNodeProvider FORWARDING_RECEIVER_NODE = register("forwarding_receiver", ForwardingStarlightReceiverNodeProvider::new);

    public static final FocusCrystalSourceNodeProvider FOCUS_CRYSTAL_SOURCE_NODE = register("source_focus_crystal", FocusCrystalSourceNodeProvider::new);

    private static <T extends TransmissionNodeProvider<?>> T register(String name, Supplier<T> supplier) {
        return Registry.register(RegistriesAS.REGISTRY_TRANSMISSION_NODES, AstralSorcery.key(name), supplier.get());
    }
}
