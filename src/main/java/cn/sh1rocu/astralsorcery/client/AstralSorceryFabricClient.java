package cn.sh1rocu.astralsorcery.client;

import cn.sh1rocu.astralsorcery.util.neoforge.common.util.LogicalSidedProvider;
import hellfirepvp.astralsorcery.client.lib.CustomModelsAS;
import hellfirepvp.astralsorcery.common.lib.FluidsAS;
import hellfirepvp.astralsorcery.common.network.play.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;

public class AstralSorceryFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            LogicalSidedProvider.setClient(() -> client);
        });
        ModelLoadingPlugin.register(CustomModelsAS.getInstance());

        PktSyncPlayerProgress.HANDLER.registerReceiver();
        PktSyncResearchNodes.HANDLER.registerReceiver();
        PktSyncData.HANDLER.registerReceiver();
        PktUpdateLinkSession.HANDLER.registerReceiver();
        PktPlayVisualEffect.HANDLER.registerReceiver();
        PktSyncModifierSource.HANDLER.registerReceiver();
        PktSyncPerkTree.HANDLER.registerReceiver();
        PktSyncPerkLevels.HANDLER.registerReceiver();
        PktSyncPerkActivity.HANDLER.registerReceiver();
        PktSyncCustomDestroyProgress.HANDLER.registerReceiver();
        PktSyncAuxiliaryLightManager.HANDLER.registerReceiver();
        PktSyncLumenBindingTypes.HANDLER.registerReceiver();
        PktPlayStructurePreview.HANDLER.registerReceiver();
        PktOpenClientScreen.HANDLER.registerReceiver();

        PktRequestSeed.HANDLER.registerReceiver();
        PktRequestUnlockPerk.HANDLER.registerReceiver();
        PktRequestPerkSealAction.HANDLER.registerReceiver();
        PktRequestRemovePerk.HANDLER.registerReceiver();

        FluidRenderHandlerRegistry.INSTANCE.register(FluidsAS.LIQUID_STARLIGHT_SOURCE, FluidsAS.LIQUID_STARLIGHT_FLOWING, new SimpleFluidRenderHandler(
                FluidsAS.LIQUID_STARLIGHT.getStillTexture(), FluidsAS.LIQUID_STARLIGHT.getFlowingTexture(), FluidsAS.LIQUID_STARLIGHT.getTint()
        ));
    }
}
