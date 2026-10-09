/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lumen.transfer;

import hellfirepvp.astralsorcery.common.lumen.Lumen;
import net.minecraft.server.level.ServerLevel;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: LumenTransferNotifiable
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public interface LumenTransferNotifiable {

    void onTransfer(ServerLevel sLevel, Lumen type);

}
