/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.client.helper;

import cn.sh1rocu.astralsorcery.api.event.BaseEvent;
import cn.sh1rocu.astralsorcery.api.event.RenderTooltipEvent;
import com.mojang.datafixers.util.Either;
import hellfirepvp.astralsorcery.EnumExtensions;
import hellfirepvp.astralsorcery.common.component.IdentifierComponent;
import hellfirepvp.astralsorcery.common.lib.DataComponentsAS;
import hellfirepvp.astralsorcery.common.util.tooltip.ArtifactDecoratedTooltip;
import net.minecraft.Util;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: ArtifactTooltipHelper
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class ArtifactTooltipHelper {

    public static void attachEventListeners() {
        RenderTooltipEvent.GATHER_COMPONENTS.register(BaseEvent.LOW, ArtifactTooltipHelper::onTooltipGather);
    }

    public static void onTooltipGather(RenderTooltipEvent.GatherComponents event) {
        ItemStack stack = event.getItemStack();
        Rarity rarity = stack.getRarity();
        if (rarity == EnumExtensions.RARITY_ARTIFACT && stack.has(DataComponentsAS.IDENTIFIER)) {
            IdentifierComponent idCmp = stack.getOrDefault(DataComponentsAS.IDENTIFIER, IdentifierComponent.NONE);
            if (idCmp.id().equals(Util.NIL_UUID)) return;

            String nameStr = stack.getHoverName().getString();
            var tooltip = event.getTooltipElements();

            for (int i = 0; i < tooltip.size(); i++) {
                var element = tooltip.get(i);
                if (element.left().isPresent()) {
                    if (element.map(txt -> txt.getString().equals(nameStr), ttCmp -> false)) {
                        int index = i;
                        element.left().ifPresent(txt -> tooltip.set(index, Either.right(new ArtifactDecoratedTooltip(idCmp, txt))));
                    }
                    break;
                }
            }
        }
    }
}
