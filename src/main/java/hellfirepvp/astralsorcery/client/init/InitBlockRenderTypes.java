/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.client.init;

import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import hellfirepvp.astralsorcery.common.lib.FluidsAS;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: InitBlockRenderTypes
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class InitBlockRenderTypes {

    public static void init() {
        BlockRenderLayerMap.INSTANCE.putFluid(FluidsAS.LIQUID_STARLIGHT_FLOWING, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(FluidsAS.LIQUID_STARLIGHT_SOURCE, RenderType.translucent());

        BlockRenderLayerMap.INSTANCE.putBlock(BlocksAS.ALTAR_ILLUMINATION, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksAS.ALTAR_LUMINANCE, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksAS.ALTAR_RADIANCE, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksAS.ALTAR_RESONANCE, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksAS.CHALICE, RenderType.solid());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksAS.GLIMMER_AMARANTH, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksAS.HYACINTH, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksAS.INFUSED_WOOD_COLUMN, RenderType.solid());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksAS.IRIS, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksAS.LUMEN_CRYSTALLIZER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksAS.MARBLE_PILLAR, RenderType.solid());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksAS.ORCHID, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksAS.POTTED_GLIMMER_AMARANTH, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksAS.POTTED_HYACINTH, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksAS.POTTED_IRIS, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksAS.POTTED_ORCHID, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksAS.POTTED_PROTEA, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksAS.POTTED_THISTLE, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksAS.PROTEA, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksAS.SOOTY_MARBLE_PILLAR, RenderType.solid());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksAS.THISTLE, RenderType.cutout());
    }

}
