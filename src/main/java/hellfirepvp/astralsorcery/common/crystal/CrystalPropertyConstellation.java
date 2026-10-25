/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.crystal;

import hellfirepvp.astralsorcery.common.constellation.BaseConstellation;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: CrystalPropertyConstellation
 * Created by HellFirePvP
 * Date: 17.10.2026 / 15:50
 */
public class CrystalPropertyConstellation extends CrystalProperty {

    private final BaseConstellation constellation;

    public CrystalPropertyConstellation(BaseConstellation constellation) {
        super(ChatFormatting.GRAY, 1);
        this.constellation = constellation;
    }

    public BaseConstellation getConstellation() {
        return this.constellation;
    }

    @Override
    public MutableComponent getName(int currentTier, long tick) {
        return Component.translatable("crystal.property.astralsorcery.constellation_focus",
                        this.constellation.getColoredName())
                .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(this.getColor().getColor())));
    }
}
