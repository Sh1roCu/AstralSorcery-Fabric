package hellfirepvp.astralsorcery.datagen.data.tags;

import hellfirepvp.astralsorcery.common.lib.FluidsAS;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;

import java.util.concurrent.CompletableFuture;

public class AstralFluidTagsProvider extends FabricTagProvider<Fluid> {

    public AstralFluidTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.FLUID, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.getOrCreateTagBuilder(FluidsAS.LIQUID_STARLIGHT_KEY).add(FluidsAS.LIQUID_STARLIGHT.getSource(), FluidsAS.LIQUID_STARLIGHT.getFlowing());
    }
}
