/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.datagen.data.tags;

import hellfirepvp.astralsorcery.common.lib.EntitiesAS;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEntityTypeTags;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AstralEntityTagsProvider
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AstralEntityTagsProvider extends FabricTagProvider.EntityTypeTagProvider {

    public AstralEntityTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(ConventionalEntityTypeTags.CAPTURING_NOT_SUPPORTED)
                .add(EntitiesAS.FLARE)
                .add(EntitiesAS.ILLUMINATION_SPARK)
                .add(EntitiesAS.NOCTURNAL_SPARK)
                .add(EntitiesAS.VIVID_SPARK);

        getOrCreateTagBuilder(ConventionalEntityTypeTags.TELEPORTING_NOT_SUPPORTED)
                .add(EntitiesAS.FLARE)
                .add(EntitiesAS.ILLUMINATION_SPARK)
                .add(EntitiesAS.NOCTURNAL_SPARK)
                .add(EntitiesAS.VIVID_SPARK);
    }
}
