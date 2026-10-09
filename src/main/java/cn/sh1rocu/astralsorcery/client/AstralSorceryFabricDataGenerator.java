package cn.sh1rocu.astralsorcery.client;

import hellfirepvp.astralsorcery.datagen.AstralDataGenerator;
import hellfirepvp.astralsorcery.datagen.data.AstralRegistriesDataProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;

public class AstralSorceryFabricDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        AstralDataGenerator.gather(pack);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        AstralRegistriesDataProvider.addRegistries(registryBuilder);
    }
}
