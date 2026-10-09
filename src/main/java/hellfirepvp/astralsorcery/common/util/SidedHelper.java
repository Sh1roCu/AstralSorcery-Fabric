/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.util;


import net.fabricmc.api.EnvType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: SidedHelper
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class SidedHelper {

    public static EnvType getSide(Level level) {
        return level.isClientSide() ? EnvType.CLIENT : EnvType.SERVER;
    }

    public static EnvType getSide(Entity entity) {
        return entity.getCommandSenderWorld().isClientSide() ? EnvType.CLIENT : EnvType.SERVER;
    }

}
