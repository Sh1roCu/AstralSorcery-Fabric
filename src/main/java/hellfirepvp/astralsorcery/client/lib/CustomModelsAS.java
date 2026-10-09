/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.client.lib;

import hellfirepvp.astralsorcery.AstralSorcery;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.resources.ResourceLocation;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: CustomModelsAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class CustomModelsAS implements ModelLoadingPlugin {

    public static final CustomModelsAS INSTANCE = new CustomModelsAS();

    public static final ResourceLocation ASTROLABE_IN_HAND = AstralSorcery.key("item/astrolabe_in_hand");

    public static CustomModelsAS getInstance() {
        return INSTANCE;
    }

    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        pluginContext.addModels(ASTROLABE_IN_HAND);
    }
}
