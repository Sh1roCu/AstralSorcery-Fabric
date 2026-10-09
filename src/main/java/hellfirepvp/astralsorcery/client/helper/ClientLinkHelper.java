/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.client.helper;

import hellfirepvp.astralsorcery.common.linking.session.ActiveLinkSession;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: ClientLinkHelper
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class ClientLinkHelper {

    private static ActiveLinkSession activeSession;

    public static Optional<ActiveLinkSession> getActiveSession() {
        return Optional.ofNullable(activeSession);
    }

    public static void setActiveSession(@Nullable ActiveLinkSession session) {
        activeSession = session;
    }

    public static void clearActiveSession() {
        setActiveSession(null);
    }
}
