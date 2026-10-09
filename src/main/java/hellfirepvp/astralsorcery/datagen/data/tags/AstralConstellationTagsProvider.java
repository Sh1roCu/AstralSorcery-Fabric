/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.datagen.data.tags;

import hellfirepvp.astralsorcery.common.constellation.BaseConstellation;
import hellfirepvp.astralsorcery.common.lib.ConstellationsAS;
import hellfirepvp.astralsorcery.common.lib.RegistriesAS;
import hellfirepvp.astralsorcery.common.lib.constants.TagsAS;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AstralConstellationTagsProvider
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AstralConstellationTagsProvider extends FabricTagProvider<BaseConstellation> {

    public AstralConstellationTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, RegistriesAS.KEY_CONSTELLATIONS, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.getOrCreateTagBuilder(TagsAS.Constellations.MAY_BE_FOCAL_POINT)
                .add(ConstellationsAS.AEVITAS)
                .add(ConstellationsAS.ARMARA)
                .add(ConstellationsAS.DISCIDIA)
                .add(ConstellationsAS.EVORSIO)
                .add(ConstellationsAS.VICIO);
    }
}
