/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.client.helper;

import cn.sh1rocu.astralsorcery.api.event.RenderTooltipEvent;
import com.mojang.datafixers.util.Either;
import hellfirepvp.astralsorcery.common.component.IdentifierComponent;
import hellfirepvp.astralsorcery.common.component.StoredLumenComponent;
import hellfirepvp.astralsorcery.common.lib.DataComponentsAS;
import hellfirepvp.astralsorcery.common.util.tooltip.StoredLumenDisplayTooltip;
import net.minecraft.Util;
import net.minecraft.world.item.ItemStack;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: StoredLumenTooltipHelper
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class StoredLumenTooltipHelper {

    public static void attachEventListeners() {
        RenderTooltipEvent.GATHER_COMPONENTS.register(StoredLumenTooltipHelper::onTooltipGather);
    }

    public static void onTooltipGather(RenderTooltipEvent.GatherComponents event) {
        ItemStack stack = event.getItemStack();
        if (stack.has(DataComponentsAS.STORED_LUMEN) && stack.has(DataComponentsAS.IDENTIFIER)) {
            IdentifierComponent idCmp = stack.getOrDefault(DataComponentsAS.IDENTIFIER, IdentifierComponent.NONE);
            StoredLumenComponent cmp = stack.getOrDefault(DataComponentsAS.STORED_LUMEN, StoredLumenComponent.EMPTY);
            if (idCmp.id().equals(Util.NIL_UUID)) return;
            String idStr = idCmp.id().toString();

            var tooltip = event.getTooltipElements();
            int index = -1;
            for (int i = 0; i < tooltip.size(); i++) {
                var element = tooltip.get(i);
                if (element.map(txt -> txt.getString().equals(idStr), ttCmp -> false)) {
                    index = i;
                    break;
                }
            }

            if (index != -1) {
                tooltip.set(index, Either.right(StoredLumenDisplayTooltip.of(stack.copy(), idCmp, cmp)));
            }
        }
    }
}
