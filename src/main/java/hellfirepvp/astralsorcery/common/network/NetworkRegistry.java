/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.network;

import hellfirepvp.astralsorcery.common.network.play.*;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: NetworkRegistry
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class NetworkRegistry {

    public static void registerPackets() {
        // Server -> Client
        PktSyncPlayerProgress.HANDLER.register();
        PktSyncResearchNodes.HANDLER.register();
        PktSyncData.HANDLER.register();
        PktUpdateLinkSession.HANDLER.register();
        PktPlayVisualEffect.HANDLER.register();
        PktSyncModifierSource.HANDLER.register();
        PktSyncPerkTree.HANDLER.register();
        PktSyncPerkLevels.HANDLER.register();
        PktSyncPerkActivity.HANDLER.register();
        PktSyncCustomDestroyProgress.HANDLER.register();
        PktSyncAuxiliaryLightManager.HANDLER.register();
        PktSyncLumenBindingTypes.HANDLER.register();
        PktPlayStructurePreview.HANDLER.register();
        PktOpenClientScreen.HANDLER.register();

        // Client -> Server
        PktAdjustAstrolabeAngle.HANDLER.register();
        PktAttunePlayer.HANDLER.register();
        PktDiscoverConstellation.HANDLER.register();
        PktRequestSocketPerkItem.HANDLER.register();
        PktRequestCancelLinkSession.HANDLER.register();
        PktRequestGatewayTeleport.HANDLER.register();
        PktRequestLearnedTomeNavigation.HANDLER.register();
        PktSetStructureMarker.HANDLER.register();

        // Bi-Directional
        PktRequestSeed.HANDLER.register();
        PktRequestUnlockPerk.HANDLER.register();
        PktRequestPerkSealAction.HANDLER.register();
        PktRequestRemovePerk.HANDLER.register();
    }
}
