/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.registry;

import hellfirepvp.astralsorcery.common.lib.*;
import hellfirepvp.astralsorcery.common.lib.types.*;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: RegistryBootstrap
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class RegistryBootstrap {

    public static void initAll() {
        RegistriesAS.init();

        // ------------------ VANILLA ------------------ //
        BlocksAS.init();
        ItemsAS.init();
        EntityDataSerializersAS.init();
        EntitiesAS.init();
        TileEntitiesAS.init();
        MobEffectsAS.init();
        CreativeTabsAS.init();
        DataComponentsAS.init();
        LootAS.init();
        MenuTypesAS.init();
        IngredientsAS.init();
        RecipeTypesAS.init();
        FluidsAS.init();
        SoundsAS.init();
        AdvancementsAS.init();
        WorldGenAS.init();
        DamageTypesAS.init();

        // ------------------ CUSTOM ------------------- //
        ConstellationsAS.init();
        LumenAS.init();
        CrystalPropertiesAS.init();
        StarlightNetworkNodesAS.init();
        //TODO altar effect registry
        PerkTypesAS.init();
        PerkDataTypesAS.init();
        PerksAS.init();

        TomePageTypesAS.init();
        SyncDataTypesAS.init();
        FocalNodeTypesAS.init();
        AltarEffectsAS.init();
        ResearchNodeConditionTypesAS.init();
        AltarRecipeOutputTypesAS.init();
        LiquidStarlightRecipeOutputTypesAS.init();
        LiquidInteractionResultTypesAS.init();
        PerkRequirementsAS.init();
        ArtifactTypesAS.init();
        ArtifactConditionTypesAS.init();
        ArtifactEffectTypesAS.init();
        LumenBindingUsageTypesAS.init();
        LumenBindingEffectTypesAS.init();
        StructureMarkerReplacementTypesAS.init();

        // ------------------ OBSERVER ----------------- //
        ObserversAS.init();
    }
}
