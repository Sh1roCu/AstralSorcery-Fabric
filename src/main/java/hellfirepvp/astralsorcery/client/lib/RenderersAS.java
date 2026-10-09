/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.client.lib;

import hellfirepvp.astralsorcery.client.render.entity.RenderEntityAltarFluidInput;
import hellfirepvp.astralsorcery.client.render.entity.RenderEntityEmpty;
import hellfirepvp.astralsorcery.client.render.entity.RenderEntityGrapplingHook;
import hellfirepvp.astralsorcery.client.render.entity.RenderItemEntityHighlighted;
import hellfirepvp.astralsorcery.client.tile.*;
import hellfirepvp.astralsorcery.common.lib.EntitiesAS;
import hellfirepvp.astralsorcery.common.lib.TileEntitiesAS;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: RenderersAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class RenderersAS {

    public static void registerTileEntityRenders() {
        BlockEntityRenderers.register(TileEntitiesAS.ALTAR.type(),
                ctx -> new TileAltarRenderer());

        BlockEntityRenderers.register(TileEntitiesAS.FOCUS_RELAY.type(),
                ctx -> new TileFocusRelayRenderer(ctx.getItemRenderer()));
        BlockEntityRenderers.register(TileEntitiesAS.LUMEN_CRYSTAL_CLUSTER.type(),
                ctx -> new TileLumenCrystalClusterRenderer(ctx.getBlockRenderDispatcher()));

        BlockEntityRenderers.register(TileEntitiesAS.LUMEN_ARRAY.type(),
                ctx -> new TileLumenArrayRenderer(ctx.getItemRenderer()));
        BlockEntityRenderers.register(TileEntitiesAS.LUMEN_ALCHEMY_ARRAY.type(),
                ctx -> new TileLumenAlchemyArrayRenderer(ctx.getItemRenderer()));
        BlockEntityRenderers.register(TileEntitiesAS.LUMEN_CRYSTALLIZER.type(),
                ctx -> new TileLumenCrystallizerRenderer());
        BlockEntityRenderers.register(TileEntitiesAS.LIGHTWELL.type(),
                ctx -> new TileLightwellRenderer(ctx.getItemRenderer()));
        BlockEntityRenderers.register(TileEntitiesAS.INFUSER.type(),
                ctx -> new TileInfuserRenderer(ctx.getItemRenderer()));
        BlockEntityRenderers.register(TileEntitiesAS.CHALICE.type(),
                ctx -> new TileChaliceRenderer());
        BlockEntityRenderers.register(TileEntitiesAS.ATTUNEMENT_ALTAR.type(),
                ctx -> new TileAttunementAltarRenderer(ctx.getModelSet()));
        BlockEntityRenderers.register(TileEntitiesAS.TRANSLUCENT_BLOCK.type(),
                ctx -> new TileTranslucentBlockRenderer());
        BlockEntityRenderers.register(TileEntitiesAS.TRANSLUCENT_TREE.type(),
                ctx -> new TileTranslucentTreeRenderer());
        BlockEntityRenderers.register(TileEntitiesAS.STARLIGHT_FOCUS_CRYSTAL.type(),
                ctx -> new TileStarlightFocusCrystalRenderer());
        BlockEntityRenderers.register(TileEntitiesAS.LENS.type(),
                ctx -> new TileLensRenderer(ctx.getModelSet()));
        BlockEntityRenderers.register(TileEntitiesAS.PRISM.type(),
                ctx -> new TilePrismRenderer());

        EntityRendererRegistry.register(EntitiesAS.ITEM_HIGHLIGHTED, RenderItemEntityHighlighted::new);
        EntityRendererRegistry.register(EntitiesAS.ITEM_STARMETAL, RenderItemEntityHighlighted::new);
        EntityRendererRegistry.register(EntitiesAS.ITEM_CRYSTAL, RenderItemEntityHighlighted::new);
        EntityRendererRegistry.register(EntitiesAS.ITEM_ARTIFACT, RenderItemEntityHighlighted::new);
        EntityRendererRegistry.register(EntitiesAS.ITEM_ALTAR_INPUT, RenderItemEntityHighlighted::new);
        EntityRendererRegistry.register(EntitiesAS.FLUID_ALTAR_INPUT, RenderEntityAltarFluidInput::new);

        EntityRendererRegistry.register(EntitiesAS.FLARE, RenderEntityEmpty::new);
        EntityRendererRegistry.register(EntitiesAS.ILLUMINATION_SPARK, RenderEntityEmpty::new);
        EntityRendererRegistry.register(EntitiesAS.NOCTURNAL_SPARK, RenderEntityEmpty::new);
        EntityRendererRegistry.register(EntitiesAS.VIVID_SPARK, RenderEntityEmpty::new);
        EntityRendererRegistry.register(EntitiesAS.GRAPPLING_HOOK, RenderEntityGrapplingHook::new);
        EntityRendererRegistry.register(EntitiesAS.SHOOTING_STAR, RenderEntityEmpty::new);
    }
}
