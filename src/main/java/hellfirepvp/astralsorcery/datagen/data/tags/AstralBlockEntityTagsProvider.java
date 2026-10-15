/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.datagen.data.tags;

import hellfirepvp.astralsorcery.common.lib.TileEntitiesAS;
import hellfirepvp.astralsorcery.common.lib.constants.TagsAS;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AstralBlockEntityTagsProvider
 * Created by HellFirePvP
 * Date: 09.10.2026 / 12:45
 */
public class AstralBlockEntityTagsProvider extends FabricTagProvider.BlockEntityTypeTagProvider {

    public AstralBlockEntityTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(TagsAS.BlockEntity.MAY_NOT_TICK_MANIPULATE)
                .add(TileEntitiesAS.ALTAR.key())
                .add(TileEntitiesAS.ATTUNEMENT_ALTAR.key());
    }
}
