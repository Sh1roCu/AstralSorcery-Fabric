/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.client.util;

import cn.sh1rocu.astralsorcery.util.fluid.FluidStack;
import hellfirepvp.astralsorcery.common.util.data.ColorWrapper;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: RenderSpriteUtil
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class RenderSpriteUtil {

    public static TextureAtlasSprite getTexture(ItemStack stack) {
        ItemRenderer ir = Minecraft.getInstance().getItemRenderer();
        BakedModel model = ir.getModel(stack, null, null, 0);
        return model.getParticleIcon();
    }

    public static TextureAtlasSprite getTexture(FluidStack stack) {
        return FluidVariantRendering.getSprite(stack.getFluidVariant());
    }

    public static ColorWrapper getColorOverlay(FluidStack stack) {
        return ColorWrapper.transparent(FluidVariantRendering.getColor(stack.getFluidVariant()));
    }
}
