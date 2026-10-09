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
import hellfirepvp.astralsorcery.common.perk.tree.AbstractPerk;
import hellfirepvp.astralsorcery.common.perk.tree.PerkDataType;
import hellfirepvp.astralsorcery.common.perk.tree.perk.key.KeyPerkTreeConnector;
import hellfirepvp.astralsorcery.common.perk.tree.perk.socket.GemSocketPerk;
import net.minecraft.core.Registry;

import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: PerkDataTypesAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class PerkDataTypesAS {

    public static void init() {

    }

    public static final PerkDataType<AbstractPerk.Data> DEFAULT_DATA = register("default", () ->
                    new PerkDataType<>(AbstractPerk.Data.CODEC, AbstractPerk.Data.SYNC_CODEC, AbstractPerk.Data::create));
    public static final PerkDataType<GemSocketPerk.Data> GEM_SOCKET_DATA = register("gem_socket", () ->
                    new PerkDataType<>(GemSocketPerk.Data.CODEC, GemSocketPerk.Data.SYNC_CODEC, GemSocketPerk.Data::create));
    public static final PerkDataType<KeyPerkTreeConnector.Data> KEY_TREE_CONNECTOR_DATA = register("key_tree_connector", () ->
                    new PerkDataType<>(KeyPerkTreeConnector.Data.CODEC, KeyPerkTreeConnector.Data.SYNC_CODEC, KeyPerkTreeConnector.Data::create));

    private static <D extends AbstractPerk.Data> PerkDataType<D> register(String name, Supplier<PerkDataType<D>> supplier) {
        return Registry.register(RegistriesAS.REGISTRY_PERK_DATA_TYPES, AstralSorcery.key(name), supplier.get());
    }
}
