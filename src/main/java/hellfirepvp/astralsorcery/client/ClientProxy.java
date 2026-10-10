/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.client;

import cn.sh1rocu.astralsorcery.api.event.RegisterMaterialAtlasesEvent;
import cn.sh1rocu.astralsorcery.api.event.RenderFrameEvent;
import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.client.config.RenderingConfig;
import hellfirepvp.astralsorcery.client.effect.EffectHandler;
import hellfirepvp.astralsorcery.client.helper.*;
import hellfirepvp.astralsorcery.client.init.AssetInitializer;
import hellfirepvp.astralsorcery.client.init.InitBlockRenderTypes;
import hellfirepvp.astralsorcery.client.init.InitItemProperties;
import hellfirepvp.astralsorcery.client.lib.*;
import hellfirepvp.astralsorcery.client.resource.AssetLibrary;
import hellfirepvp.astralsorcery.client.screen.effect.ScreenEffectTicketManager;
import hellfirepvp.astralsorcery.client.screen.tome.*;
import hellfirepvp.astralsorcery.client.screen.tome.lumen.data.LumenDisplayPositionLoader;
import hellfirepvp.astralsorcery.client.screen.tome.page.*;
import hellfirepvp.astralsorcery.client.sky.constellation.SkyConstellationPositionLoader;
import hellfirepvp.astralsorcery.client.util.ColorExtractUtil;
import hellfirepvp.astralsorcery.client.util.camera.CameraManager;
import hellfirepvp.astralsorcery.client.util.structure.StructurePreviewHelper;
import hellfirepvp.astralsorcery.client.util.tooltip.ArtifactDecoratedClientComponent;
import hellfirepvp.astralsorcery.client.util.tooltip.ItemStackClientComponent;
import hellfirepvp.astralsorcery.client.util.tooltip.StoredLumenClientComponent;
import hellfirepvp.astralsorcery.common.CommonProxy;
import hellfirepvp.astralsorcery.common.block.BlockDynamicColor;
import hellfirepvp.astralsorcery.common.config.BaseConfiguration;
import hellfirepvp.astralsorcery.common.constellation.level.LevelSkyHandler;
import hellfirepvp.astralsorcery.common.data.sync.SyncDataManager;
import hellfirepvp.astralsorcery.common.ingredient.HasStoredLumenIngredient;
import hellfirepvp.astralsorcery.common.ingredient.IsEnchantedIngredient;
import hellfirepvp.astralsorcery.common.ingredient.IsLumenBindableIngredient;
import hellfirepvp.astralsorcery.common.item.base.ItemDynamicColor;
import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import hellfirepvp.astralsorcery.common.lib.ItemsAS;
import hellfirepvp.astralsorcery.common.lib.RecipeTypesAS;
import hellfirepvp.astralsorcery.common.lumen.binding.data.LumenBindingTypeLoader;
import hellfirepvp.astralsorcery.common.patreon.PatreonManagerClient;
import hellfirepvp.astralsorcery.common.perk.PerkLevelManager;
import hellfirepvp.astralsorcery.common.perk.PerkManager;
import hellfirepvp.astralsorcery.common.perk.data.PerkTree;
import hellfirepvp.astralsorcery.common.perk.source.ModifierManager;
import hellfirepvp.astralsorcery.common.perk.tick.PerkCooldownHelper;
import hellfirepvp.astralsorcery.common.perk.tree.AbstractPerk;
import hellfirepvp.astralsorcery.common.perk.tree.PerkTreePoint;
import hellfirepvp.astralsorcery.common.perk.type.base.PerkAttributeType;
import hellfirepvp.astralsorcery.common.research.ResearchManager;
import hellfirepvp.astralsorcery.common.util.level.LevelEffectSeedCache;
import hellfirepvp.astralsorcery.common.util.listener.ClientLifecycleListener;
import hellfirepvp.astralsorcery.common.util.tick.TimeoutList;
import hellfirepvp.astralsorcery.common.util.tooltip.ArtifactDecoratedTooltip;
import hellfirepvp.astralsorcery.common.util.tooltip.ItemStackTooltip;
import hellfirepvp.astralsorcery.common.util.tooltip.StoredLumenDisplayTooltip;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.fml.config.ModConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: ClientProxy
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class ClientProxy extends CommonProxy {

    private final TimeoutList<Runnable> effectTasks = new TimeoutList<>(Runnable::run);
    private final List<ClientLifecycleListener> clientLifecycleListeners = new ArrayList<>();
    private static long clientTick = 0;

    private BaseConfiguration clientConfig;

    @Override
    public void init() {
        this.clientConfig = BaseConfiguration.simple(ModConfig.Type.CLIENT);

        this.clientLifecycleListeners.add(ClientLifecycleListener.disconnect(LevelEffectSeedCache::clearClient));
        this.clientLifecycleListeners.add(ClientLifecycleListener.disconnect(LevelSkyHandler.getInstance()::clientClearCache));
        this.clientLifecycleListeners.add(ClientLifecycleListener.disconnect(() -> SyncDataManager.getInstance().clear(EnvType.CLIENT)));
        this.clientLifecycleListeners.add(ClientLifecycleListener.disconnect(ClientLinkHelper::clearActiveSession));
        this.clientLifecycleListeners.add(ClientLifecycleListener.disconnect(CameraManager.getInstance()::clearTransformers));
        this.clientLifecycleListeners.add(ClientLifecycleListener.disconnect(EffectHandler.getInstance()::clearAllEffects));
        this.clientLifecycleListeners.add(ClientLifecycleListener.disconnect(ScreenEffectTicketManager.getInstance()::clearAllEffects));
        this.clientLifecycleListeners.add(ClientLifecycleListener.disconnect(TomeResearchScreen::resetOpenTome));
        this.clientLifecycleListeners.add(ClientLifecycleListener.disconnect(PerkManager::clientClearAllPerks));
        this.clientLifecycleListeners.add(ClientLifecycleListener.disconnect(ModifierManager::clearClientCache));
        this.clientLifecycleListeners.add(ClientLifecycleListener.disconnect(PerkManager.getInstance()::clearClient));
        this.clientLifecycleListeners.add(ClientLifecycleListener.disconnect(LumenBindingTypeLoader.getInstance()::clearClientBindings));
        this.clientLifecycleListeners.add(ClientLifecycleListener.disconnect(() -> PerkAttributeType.clearCache(EnvType.CLIENT)));
        this.clientLifecycleListeners.add(ClientLifecycleListener.disconnect(() -> PerkCooldownHelper.clearCache(EnvType.CLIENT)));
        this.clientLifecycleListeners.add(ClientLifecycleListener.disconnect(() -> PerkTree.getInstance().clearCache(EnvType.CLIENT)));
        this.clientLifecycleListeners.add(ClientLifecycleListener.disconnect(() -> PerkLevelManager.getInstance().clearCache(EnvType.CLIENT)));
        this.clientLifecycleListeners.add(ClientLifecycleListener.disconnect(IsLumenBindableIngredient::clearDisplayCache));
        this.clientLifecycleListeners.add(ClientLifecycleListener.disconnect(IsEnchantedIngredient::clearDisplayCache));
        this.clientLifecycleListeners.add(ClientLifecycleListener.disconnect(HasStoredLumenIngredient::clearDisplayCache));
        this.clientLifecycleListeners.add(ClientLifecycleListener.disconnect(ResearchManager::clearClientCache));

        super.init();

        TomeScreen.addBookmark(TomeResearchScreen.BOOKMARK);
        TomeScreen.addBookmark(TomeConstellationScreen.BOOKMARK);
        TomeScreen.addBookmark(TomePerkTreeScreen.BOOKMARK);
        TomeScreen.addBookmark(TomeLumenScreen.BOOKMARK);

        RenderPageRecipe.registerPageFactory(RecipeTypesAS.LUMEN_GENERATION_TYPE.getKey(), RenderPageLumenGeneration::new);
        RenderPageRecipe.registerPageFactory(RecipeTypesAS.LUMEN_CRYSTALLIZATION_TYPE.getKey(), RenderPageLumenCrystallization::new);
        RenderPageRecipe.registerPageFactory(RecipeTypesAS.FOCAL_TRANSMUTATION_TYPE.getKey(), RenderPageFocalTransmutation::new);
        RenderPageRecipe.registerPageFactory(RecipeTypesAS.FOCAL_COMBINE_TYPE.getKey(), RenderPageFocalCombination::new);
        RenderPageRecipe.registerPageFactory(RecipeTypesAS.LIGHTWELL_TYPE.getKey(), RenderPageLightwell::new);
        RenderPageRecipe.registerPageFactory(RecipeTypesAS.INFUSION_TYPE.getKey(), RenderPageStarlightInfusion::new);
        RenderPageRecipe.registerPageFactory(RecipeTypesAS.ALTAR_CRAFTING_TYPE.getKey(), RenderPageAltar::new);
        RenderPageRecipe.registerPageFactory(ResourceKey.create(Registries.RECIPE_TYPE, ResourceLocation.withDefaultNamespace("crafting")), RenderPageCraftingRecipe::new);

        this.clientConfig.build();
    }

    @Override
    public void initConfigurations() {
        super.initConfigurations();

        this.clientConfig.addConfigEntry(RenderingConfig.CONFIG);
    }

    @Override
    public void initLifecycle() {
        super.initLifecycle();

        this.onRegisterClientReloadListeners();
        this.onItemColorSetup();
        this.onBlockColorSetup();
        this.onClientSetup();
        RegisterMaterialAtlasesEvent.EVENT.register(TexturesAS::registerAtlases);
        ShadersAS.registerShaders();
        RenderersAS.registerTileEntityRenders();
        ModelLayersAS.registerModelLayers();
        ClientExtensionsAS.registerExtensions();
        RenderLumenDisplayOverlay.registerLayers();
        RenderPerkExperienceOverlay.registerLayers();
        MenuScreensAS.registerScreens();

        this.registerCustomComponents();
    }

    @Override
    public void initListeners() {
        super.initListeners();

        ClientPlayConnectionEvents.JOIN.register(this::onClientConnect);
        ClientPlayConnectionEvents.DISCONNECT.register(this::onClientDisconnect);
        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);
        ClientTickEvents.END_CLIENT_TICK.register(client -> this.effectTasks.onClientTick());
        ClientTickEvents.END_CLIENT_TICK.register(EffectHandler.getInstance()::tick);
        ClientTickEvents.END_CLIENT_TICK.register(ScreenEffectTicketManager.getInstance()::tick);
        ClientTickEvents.START_CLIENT_TICK.register(RenderAstrolabeOverlay::overrideFov);
        ClientTickEvents.START_CLIENT_TICK.register(FocalPointEffectHelper::onClientTick);
        ClientTickEvents.START_CLIENT_TICK.register(CameraManager.getInstance()::onClientTick);
        RenderFrameEvent.PRE.register(CameraManager.getInstance()::onRenderTick);
        ClientTickEvents.END_CLIENT_TICK.register(GatewayInterfaceRenderHelper::onClientTick);
        ClientTickEvents.START_CLIENT_TICK.register(RenderPerkExperienceOverlay::onClientTick);
        WorldRenderEvents.END.register(StructurePreviewHelper::renderPreview);
        ClientTickEvents.END_CLIENT_TICK.register(StructurePreviewHelper::tickPreview);

        LinkSessionEffectHelper.attachEventListeners();
        StarlightTransmissionEffectHelper.attachEventListeners();
        StoredLumenTooltipHelper.attachEventListeners();
        ArtifactTooltipHelper.attachEventListeners();
        GatewayInterfaceInteractHelper.attachEventListeners();
        WandPreviewRenderHelper.attachEventListeners();
        PatreonManagerClient.attachListeners();
    }

    private void onRegisterClientReloadListeners() {
        var helper = ResourceManagerHelper.get(PackType.CLIENT_RESOURCES);
        helper.registerReloadListener(SkyConstellationPositionLoader.getInstance());
        helper.registerReloadListener(LumenDisplayPositionLoader.getInstance());
        helper.registerReloadListener(AssetLibrary.getInstance());
        helper.registerReloadListener(AssetInitializer.getInstance());
        helper.registerReloadListener(ColorExtractUtil.reload());
        helper.registerReloadListener(new IdentifiableResourceReloadListener() {
            static final ResourceLocation ID = AstralSorcery.key("perk_points");

            @Override
            public ResourceLocation getFabricId() {
                return ID;
            }

            @Override
            public CompletableFuture<Void> reload(PreparationBarrier stage, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
                return stage.wait(Unit.INSTANCE).thenRunAsync(() -> {
                    PerkTree.getInstance().getPerkPoints(EnvType.CLIENT).stream()
                            .map(PerkTreePoint::getPerk)
                            .forEach(AbstractPerk::clearTooltipCache);
                });
            }
        });
    }

    private void onItemColorSetup() {
        ItemsAS.REGISTERED_ITEMS.forEach(item -> {
            if (item instanceof ItemDynamicColor dynamicColorItem) {
                ColorProviderRegistry.ITEM.register((stack, tint) ->
                        dynamicColorItem.getColor(stack, ClientProxy.getClientTick(), tint), dynamicColorItem);
            }
        });
    }

    private void onBlockColorSetup() {
        BlocksAS.REGISTERED_BLOCKS.forEach(block -> {
            if (block instanceof BlockDynamicColor dynamicColorBlock) {
                ColorProviderRegistry.BLOCK.register((state, level, pos, tintIndex) ->
                        dynamicColorBlock.getColor(state, ClientProxy.getClientTick(), level, pos, tintIndex), block);
            }
        });
    }

    private void onClientSetup() {
        InitBlockRenderTypes.init();
        InitItemProperties.init();
    }

    private void registerCustomComponents() {
        TooltipComponentCallback.EVENT.register(data -> {
            if (data instanceof ItemStackTooltip itemStack) {
                return ItemStackClientComponent.create(itemStack);
            }
            if (data instanceof StoredLumenDisplayTooltip storedLumen) {
                return StoredLumenClientComponent.create(storedLumen);
            }
            if (data instanceof ArtifactDecoratedTooltip artifactDecorated) {
                return ArtifactDecoratedClientComponent.create(artifactDecorated);
            }
            return null;
        });

    }

    private void onClientConnect(ClientPacketListener handler, PacketSender sender, Minecraft client) {
        this.clientLifecycleListeners.forEach(ClientLifecycleListener::onClientConnect);
    }

    private void onClientDisconnect(ClientPacketListener handler, Minecraft client) {
        this.clientLifecycleListeners.forEach(ClientLifecycleListener::onClientDisconnect);
    }

    private void onClientTick(Minecraft client) {
        clientTick++;
    }

    public static long getClientTick() {
        return clientTick;
    }

    public static void scheduleEffectTask(Runnable task) {
        scheduleEffectTask(1, task);
    }

    public static void scheduleEffectTask(int delay, Runnable task) {
        if (AstralSorcery.getInstance().getProxy() instanceof ClientProxy clientProxy) {
            clientProxy.effectTasks.add(delay, task);
        }
    }
}
