/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.client.screen.container.altar;

import hellfirepvp.astralsorcery.client.lib.TexturesAS;
import hellfirepvp.astralsorcery.client.resource.AbstractRenderTexture;
import hellfirepvp.astralsorcery.common.container.ContainerAltarIllumination;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: ScreenContainerAltarIllumination
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class ScreenContainerAltarIllumination extends ScreenContainerAltar<ContainerAltarIllumination> {

    public ScreenContainerAltarIllumination(ContainerAltarIllumination menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 216, 215);
    }

    @Override
    public AbstractRenderTexture getBackgroundTexture() {
        return TexturesAS.SCREEN_CONTAINER_ALTAR_ILLUMINATION;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        this.renderAltarOutput(guiGraphics, 154, 55, partialTick);
    }
}
