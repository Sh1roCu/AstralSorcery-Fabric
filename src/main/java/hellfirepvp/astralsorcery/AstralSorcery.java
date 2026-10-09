/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery;

import hellfirepvp.astralsorcery.client.ClientProxy;
import hellfirepvp.astralsorcery.common.CommonProxy;
import hellfirepvp.observerlib.common.util.DistUtil;
import net.fabricmc.fabric.impl.datagen.FabricDataGenHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AstralSorcery
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AstralSorcery {

    public static final String MODID = "astralsorcery";
    public static final String NAME = "Astral Sorcery";

    public static final Logger LOG = LogManager.getLogger(NAME);

    private static AstralSorcery instance;
    private static ModContainer modContainer;
    private final CommonProxy proxy;

    public static void init() {
        new AstralSorcery();
    }

    private AstralSorcery() {
        instance = this;
        modContainer = FabricLoader.getInstance().getModContainer(MODID).orElseThrow();

        this.proxy = DistUtil.unsafeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
        this.proxy.init();
        this.proxy.initListeners();
    }

    public static AstralSorcery getInstance() {
        return instance;
    }

    public static ModContainer getModContainer() {
        return modContainer;
    }

    public CommonProxy getProxy() {
        return this.proxy;
    }

    public static ResourceLocation key(String path) {
        return ResourceLocation.fromNamespaceAndPath(AstralSorcery.MODID, path);
    }

    @SuppressWarnings("UnstableApiUsage")
    public static boolean isDoingDataGeneration() {
        return FabricDataGenHelper.ENABLED;
    }

    public static void assertDataGeneration() {
        if (!isDoingDataGeneration()) {
            throw new IllegalStateException("This method may only be called during data generation!");
        }
    }
}
