/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.advancement.*;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AdvancementsAS
 * Created by HellFirePvP
 * Date: 07.10.2026 / 23:38
 */
public class AdvancementsAS {

    public static void init() {
    }

    public static final PerkLevelTrigger PERK_LEVEL =
            register("perk_level", PerkLevelTrigger::new);

    public static final PlayerAttunementTrigger PLAYER_ATTUNEMENT =
            register("player_attunement", PlayerAttunementTrigger::new);

    public static final ItemAttunementTrigger ITEM_ATTUNEMENT =
            register("item_attunement", ItemAttunementTrigger::new);

    public static final ConstellationDiscoveryTrigger CONSTELLATION_DISCOVERY =
            register("constellation_discovery", ConstellationDiscoveryTrigger::new);

    public static final LumenDiscoveryTrigger LUMEN_DISCOVERY =
            register("lumen_discovery", LumenDiscoveryTrigger::new);

    public static final FocalPointDiscoveryTrigger FOCAL_POINT_DISCOVERY =
            register("focal_point_discovery", FocalPointDiscoveryTrigger::new);

    public static final GemSocketTrigger GEM_SOCKET =
            register("gem_socket", GemSocketTrigger::new);

    private static <T extends CriterionTrigger<C>, C extends SimpleCriterionTrigger.SimpleInstance> T register(String name, Supplier<T> supplier) {
        return Registry.register(BuiltInRegistries.TRIGGER_TYPES, AstralSorcery.key(name), supplier.get());
    }
}
