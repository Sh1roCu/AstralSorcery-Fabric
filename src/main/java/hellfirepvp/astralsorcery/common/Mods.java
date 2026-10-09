/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common;

import hellfirepvp.astralsorcery.AstralSorcery;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Optional;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: Mods
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public enum Mods {

    MINECRAFT("minecraft", true),
    FABRIC("fabric-api", true),
    ASTRAL_SORCERY(AstralSorcery.MODID, true),
    DRACONIC_EVOLUTION("draconicevolution"),
    TRINKETS("trinkets"),
    SIMULATED("simulated");

    private final String modid;
    private final boolean loaded;

    Mods(String modid) {
        this(modid, FabricLoader.getInstance().isModLoaded(modid));
    }

    Mods(String modid, boolean loaded) {
        this.modid = modid;
        this.loaded = loaded;
    }

    public String getModId() {
        return this.modid;
    }

    public boolean isPresent() {
        return loaded;
    }

    @Nonnull
    public ResourceLocation key(String path) {
        return ResourceLocation.fromNamespaceAndPath(this.getModId(), path);
    }

    public static Optional<Mods> byModId(String modId) {
        return Arrays.stream(values())
                .filter(mod -> mod.getModId().equals(modId))
                .findFirst();
    }
}
