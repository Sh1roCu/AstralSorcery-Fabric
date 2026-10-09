/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.perk.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.util.data.MapStream;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.List;
import java.util.Map;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: PerkTreeLoader
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class PerkTreeLoader extends SimpleJsonResourceReloadListener implements IdentifiableResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static final ResourceLocation ID = AstralSorcery.key("perks");
    public HolderLookup.Provider provider;

    public PerkTreeLoader(HolderLookup.Provider provider) {
        super(GSON, "perks");
        this.provider = provider;
    }

    @Override
    public ResourceLocation getFabricId() {
        return ID;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> dataMap, ResourceManager resourceManager, ProfilerFiller profiler) {
        List<JsonObject> perkObjects = MapStream.of(dataMap)
                .filterValue(JsonElement::isJsonObject)
                .mapValue(JsonElement::getAsJsonObject)
                .valueStream()
                .toList();

        AstralSorcery.LOG.info("Loading perk tree with {} perks.", perkObjects.size());
        PerkTreeData data = PerkTreeData.load(perkObjects, this.provider);
        PerkTree.getInstance().updateOriginPerkTree(data);
    }
}
