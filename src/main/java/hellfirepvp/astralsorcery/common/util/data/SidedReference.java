/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.util.data;


import net.fabricmc.api.EnvType;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: SidedReference
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class SidedReference<T> {

    private T clientData = null;
    private T serverData = null;

    public SidedReference() {
    }

    public static <T> SidedReference<T> create(Supplier<T> defaults) {
        SidedReference<T> ref = new SidedReference<>();
        ref.setData(EnvType.CLIENT, defaults.get());
        ref.setData(EnvType.SERVER, defaults.get());
        return ref;
    }

    public Optional<T> getData(EnvType side) {
        if (side == EnvType.CLIENT) {
            return Optional.ofNullable(this.clientData);
        }
        return Optional.ofNullable(this.serverData);
    }

    public void setData(EnvType side, T data) {
        if (side == EnvType.CLIENT) {
            this.clientData = data;
        } else {
            this.serverData = data;
        }
    }
}
