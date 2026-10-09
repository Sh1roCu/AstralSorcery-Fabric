/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.client.init;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.client.lib.*;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AssetInitializer
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AssetInitializer implements ResourceManagerReloadListener, IdentifiableResourceReloadListener {

    private static final AssetInitializer INSTANCE = new AssetInitializer();

    public static final ResourceLocation ID = AstralSorcery.key("asset_initializer");

    private boolean initialized = false;

    private AssetInitializer() {
    }

    public static AssetInitializer getInstance() {
        return INSTANCE;
    }

    @Override
    public ResourceLocation getFabricId() {
        return ID;
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        if (this.initialized) return;

        TexturesAS.init();
        SpritesAS.init();
        RenderTypesAS.init();
        EffectTemplatesAS.init();
        ShaderProgramsAS.init();

        this.initialized = true;
    }
}
