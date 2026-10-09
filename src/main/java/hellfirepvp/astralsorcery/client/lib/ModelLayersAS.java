/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.client.lib;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.client.model.builtin.ModelAttunementAltar;
import hellfirepvp.astralsorcery.client.model.builtin.ModelLens;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;

import java.util.function.BiConsumer;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: ModelLayersAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class ModelLayersAS {

    public static final PreparedModelLayer ATTUNEMENT_ALTAR = create("attunement_altar", ModelAttunementAltar::createLayer);
    public static final PreparedModelLayer LENS = create("lens", ModelLens::createLayer);

    public static void registerModelLayers() {
        BiConsumer<ModelLayerLocation, EntityModelLayerRegistry.TexturedModelDataProvider> registrar = EntityModelLayerRegistry::registerModelLayer;

        ATTUNEMENT_ALTAR.register(registrar);
        LENS.register(registrar);
    }

    private static PreparedModelLayer create(String name, EntityModelLayerRegistry.TexturedModelDataProvider layerDefinition) {
        return new PreparedModelLayer(new ModelLayerLocation(AstralSorcery.key(name), "main"), layerDefinition);
    }

    public record PreparedModelLayer(ModelLayerLocation layerLocation,
                                     EntityModelLayerRegistry.TexturedModelDataProvider layerDefinition) {

        private void register(BiConsumer<ModelLayerLocation, EntityModelLayerRegistry.TexturedModelDataProvider> registrar) {
            registrar.accept(this.layerLocation(), this.layerDefinition());
        }
    }
}
