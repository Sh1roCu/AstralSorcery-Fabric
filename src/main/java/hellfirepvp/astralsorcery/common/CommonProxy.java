/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common;

import cn.sh1rocu.observerlib.ObserverLibFabric;
import cn.sh1rocu.observerlib.mixin.accessor.LevelResourceAccessor;
import com.mojang.brigadier.CommandDispatcher;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeModConfigEvents;
import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.artifact.condition.data.ArtifactConditionLoader;
import hellfirepvp.astralsorcery.common.artifact.effect.data.ArtifactEffectLoader;
import hellfirepvp.astralsorcery.common.command.CommandsAS;
import hellfirepvp.astralsorcery.common.config.BaseConfiguration;
import hellfirepvp.astralsorcery.common.config.json.JsonConfigurationManager;
import hellfirepvp.astralsorcery.common.config.json.data.AmuletEnchantmentDataRegistry;
import hellfirepvp.astralsorcery.common.config.json.data.KnownTreeRegistry;
import hellfirepvp.astralsorcery.common.config.json.data.PerkGemModifierRegistry;
import hellfirepvp.astralsorcery.common.config.server.*;
import hellfirepvp.astralsorcery.common.constellation.level.LevelSkyHandler;
import hellfirepvp.astralsorcery.common.data.sync.SyncDataManager;
import hellfirepvp.astralsorcery.common.data.sync.server.CelestialGatewaySyncData;
import hellfirepvp.astralsorcery.common.enchantment.EnchantmentAmuletGenerator;
import hellfirepvp.astralsorcery.common.event.handler.InteractEventHandler;
import hellfirepvp.astralsorcery.common.event.handler.LootEventHandler;
import hellfirepvp.astralsorcery.common.event.handler.PlayerEventHandler;
import hellfirepvp.astralsorcery.common.event.handler.TooltipEventHandler;
import hellfirepvp.astralsorcery.common.event.helper.*;
import hellfirepvp.astralsorcery.common.focal.FocalPointManager;
import hellfirepvp.astralsorcery.common.init.InitCapabilities;
import hellfirepvp.astralsorcery.common.item.base.CauldronInteractableItem;
import hellfirepvp.astralsorcery.common.item.base.CreativeTabItem;
import hellfirepvp.astralsorcery.common.lib.DataAS;
import hellfirepvp.astralsorcery.common.lib.EntitiesAS;
import hellfirepvp.astralsorcery.common.lib.FluidsAS;
import hellfirepvp.astralsorcery.common.lib.ItemsAS;
import hellfirepvp.astralsorcery.common.linking.session.LinkSessionHelper;
import hellfirepvp.astralsorcery.common.lumen.binding.data.LumenBindingTypeLoader;
import hellfirepvp.astralsorcery.common.lumen.binding.effect.*;
import hellfirepvp.astralsorcery.common.lumen.binding.usage.*;
import hellfirepvp.astralsorcery.common.network.NetworkRegistry;
import hellfirepvp.astralsorcery.common.patreon.PatreonDataManager;
import hellfirepvp.astralsorcery.common.patreon.PatreonManager;
import hellfirepvp.astralsorcery.common.perk.PerkAttributeLimiter;
import hellfirepvp.astralsorcery.common.perk.PerkLevelManager;
import hellfirepvp.astralsorcery.common.perk.PerkManager;
import hellfirepvp.astralsorcery.common.perk.data.PerkTree;
import hellfirepvp.astralsorcery.common.perk.data.PerkTreeLoader;
import hellfirepvp.astralsorcery.common.perk.source.ModifierManager;
import hellfirepvp.astralsorcery.common.perk.source.provider.equipment.EquipmentSourceProvider;
import hellfirepvp.astralsorcery.common.perk.source.provider.lumen.LumenBindingSourceProvider;
import hellfirepvp.astralsorcery.common.perk.tick.PerkCooldownHelper;
import hellfirepvp.astralsorcery.common.perk.tick.PerkTickHelper;
import hellfirepvp.astralsorcery.common.perk.type.base.PerkAttributeType;
import hellfirepvp.astralsorcery.common.recipe.attunement.AttunementRecipe;
import hellfirepvp.astralsorcery.common.registry.RegistryBootstrap;
import hellfirepvp.astralsorcery.common.research.ResearchManager;
import hellfirepvp.astralsorcery.common.research.data.ResearchNodeLoader;
import hellfirepvp.astralsorcery.common.research.io.ResearchIOThread;
import hellfirepvp.astralsorcery.common.starlight.StarlightNetworkLinkHelper;
import hellfirepvp.astralsorcery.common.starlight.StarlightNetworkTickHelper;
import hellfirepvp.astralsorcery.common.starlight.transmission.StarlightTransmissionLevelHelper;
import hellfirepvp.astralsorcery.common.tile.TileTreeBeacon;
import hellfirepvp.astralsorcery.common.util.TreeGrowUtil;
import hellfirepvp.astralsorcery.common.util.listener.ServerLifecycleListener;
import hellfirepvp.astralsorcery.common.visual.VisualEffectTypes;
import hellfirepvp.observerlib.common.event.BlockChangeNotifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.fml.config.ModConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: CommonProxy
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class CommonProxy {

    private BaseConfiguration commonConfig;
    private BaseConfiguration serverConfig;
    private BaseConfiguration startupConfig;

    private final List<ServerLifecycleListener> serverLifecycleListeners = new ArrayList<>();

    public void init() {
        initLifecycle();

        this.commonConfig = BaseConfiguration.simple(ModConfig.Type.COMMON);
        this.serverConfig = BaseConfiguration.simple(ModConfig.Type.SERVER);
        this.startupConfig = BaseConfiguration.simple(ModConfig.Type.STARTUP);

        DataAS.init();
        VisualEffectTypes.init();
        AttunementRecipe.initRecipes();

        this.serverLifecycleListeners.add(ResearchIOThread.getInstance());
        this.serverLifecycleListeners.add(ServerLifecycleListener.stop(() -> SyncDataManager.getInstance().clear(EnvType.SERVER)));
        this.serverLifecycleListeners.add(ServerLifecycleListener.stop(ResearchManager::clearServerCache));
        this.serverLifecycleListeners.add(ServerLifecycleListener.stop(LinkSessionHelper::clearServerCache));
        this.serverLifecycleListeners.add(ServerLifecycleListener.stop(InvulnerabilityHelper::clearServer));
        this.serverLifecycleListeners.add(ServerLifecycleListener.stop(DamageCancellingHelper::clearServer));
        this.serverLifecycleListeners.add(ServerLifecycleListener.stop(SwordParryHelper::clearServer));
        this.serverLifecycleListeners.add(ServerLifecycleListener.stop(LumenBindingAbsorbDamageEffect::clearServer));
        this.serverLifecycleListeners.add(ServerLifecycleListener.stop(LumenBindingExtendMobEffectsEffect::clearServer));
        this.serverLifecycleListeners.add(ServerLifecycleListener.stop(PerkManager.getInstance()::clearServer));
        this.serverLifecycleListeners.add(ServerLifecycleListener.stop(() -> PerkAttributeType.clearCache(EnvType.SERVER)));
        this.serverLifecycleListeners.add(ServerLifecycleListener.stop(() -> PerkCooldownHelper.clearCache(EnvType.SERVER)));
        this.serverLifecycleListeners.add(ServerLifecycleListener.stop(() -> PerkTree.getInstance().clearCache(EnvType.SERVER)));
        this.serverLifecycleListeners.add(ServerLifecycleListener.stop(() -> PerkLevelManager.getInstance().clearCache(EnvType.SERVER)));
        this.serverLifecycleListeners.add(ServerLifecycleListener.stop(() -> StarlightTransmissionLevelHelper.getInstance().clearServer()));

        this.serverLifecycleListeners.add(ServerLifecycleListener.start(JsonConfigurationManager.getInstance()::loadRegistries));
        this.serverLifecycleListeners.add(ServerLifecycleListener.start(PerkTree.getInstance()::setupServerPerkTree));
        this.serverLifecycleListeners.add(ServerLifecycleListener.start(PerkLevelManager.getInstance()::initializeServerLevels));

        this.initConfigurations();
        this.commonConfig.build();
        this.serverConfig.build();
        this.startupConfig.build();
    }

    public void initConfigurations() {
        this.serverConfig.addConfigEntry(GeneralConfig.CONFIG);
        this.serverConfig.addConfigEntry(PerkConfig.CONFIG);
        PerkConfig.addPerkConfigs();
        this.serverConfig.addConfigEntry(EnchantmentAmuletGenerator.CONFIG);
        this.serverConfig.addConfigEntry(TilesConfig.CONFIG);
        this.serverConfig.addConfigEntry(ArtifactConfig.CONFIG);

        this.startupConfig.addConfigEntry(LootTableConfig.CONFIG);

        TilesConfig.CONFIG.newSubSection(TileTreeBeacon.CONFIG);

        JsonConfigurationManager.getInstance().addRegistry("amulet_enchantments", AmuletEnchantmentDataRegistry.getInstance());
        JsonConfigurationManager.getInstance().addRegistry("perk_gem_modifiers", PerkGemModifierRegistry.getInstance());
        JsonConfigurationManager.getInstance().addRegistry("known_trees", KnownTreeRegistry.getInstance());
    }

    public void initLifecycle() {
        //Registry
        NeoForgeModConfigEvents.reloading(AstralSorcery.MODID).register(BaseConfiguration::reloadConfigurations);
        RegistryBootstrap.initAll();
        InitCapabilities.init();
        NetworkRegistry.registerPackets();
        ItemGroupEvents.MODIFY_ENTRIES_ALL.register(this::onBuildCreativeTabContents);
        this.onCommonSetup();
        EntitiesAS.registerAttributes();

        //Feature
    }

    public void initListeners() {
        ServerLifecycleEvents.SERVER_STOPPING.register(this::onServerStopping);
        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);
        CommandRegistrationCallback.EVENT.register(this::onRegisterCommands);
        this.onRegisterReloadListeners();

        PlayerEventHandler.attachListeners();
        TooltipEventHandler.attachListeners();
        InteractEventHandler.attachListeners();
        LootEventHandler.attachListeners();
        InvulnerabilityHelper.attachListeners();
        TemporaryFlightHelper.attachListeners();
        DamageCancellingHelper.attachListeners();
        SwordParryHelper.attachListeners();
        SyncDataManager.getInstance().attachEventListeners();
        LevelSkyHandler.getInstance().attachEventListeners();
        StarlightNetworkTickHelper.getInstance().attachEventListeners();
        FocalPointManager.getInstance().attachEventListeners();
        ModifierManager.getInstance().attachEventListeners();
        PerkManager.getInstance().attachEventListeners();
        PerkCooldownHelper.attachEventListeners();
        PerkAttributeLimiter.attachEventListeners();
        PerkTickHelper.attachEventListeners();
        EquipmentSourceProvider.attachEventListeners();
        LumenBindingSourceProvider.attachEventListeners();
        EnchantmentModifierHelper.attachListeners();
        LinkSessionHelper.attachEventListeners();
        StarlightTransmissionLevelHelper.getInstance().attachEventListeners();
        TreeGrowUtil.attachEventListeners();
        CelestialGatewaySyncData.attachListeners();
        PatreonManager.attachListeners();
        LumenBindingHitAddEffectEffect.attachEventListeners();
        LumenBindingPlaceLightEffect.attachEventListeners();
        LumenBindingCollectDropsEffect.attachEventListeners();
        LumenBindingAbsorbDamageEffect.attachEventListeners();
        LumenBindingDamageBurstEffect.attachEventListeners();
        LumenBindingExtendMobEffectsEffect.attachEventListeners();
        LumenBindingAoeCropGrowthEffect.attachEventListeners();
        LumenBindingProjectileAccuracyEffect.attachEventListeners();
        LumenBindingUsageBlockBreak.attachEventListeners();
        LumenBindingUsageDamageDealt.attachEventListeners();
        LumenBindingUsageDamageTaken.attachEventListeners();
        LumenBindingUsageHealthRecovered.attachEventListeners();
        LumenBindingUsageMovement.attachEventListeners();

        BlockChangeNotifier.addListener(StarlightNetworkLinkHelper.getInstance());
    }

    public File getServerDataDirectory() {
        MinecraftServer server = ObserverLibFabric.getServer();
        if (server == null) {
            return null;
        }

        File asDataDir = server.getWorldPath(LevelResourceAccessor.ol$create(AstralSorcery.MODID)).toFile();
        if (!asDataDir.exists()) asDataDir.mkdirs();
        return asDataDir;
    }

    private void onServerStarted(MinecraftServer server) {
        this.serverLifecycleListeners.forEach(listener -> listener.onServerStart(server));
    }

    private void onServerStopping(MinecraftServer server) {
        this.serverLifecycleListeners.forEach(listener -> listener.onServerStop(server));
    }

    private void onCommonSetup() {
        FluidsAS.addLiquidInteractions();
        PatreonDataManager.loadPatreonEffects();
        ItemsAS.REGISTERED_ITEMS.forEach(item -> {
            if (item instanceof DispenseItemBehavior behavior) {
                DispenserBlock.registerBehavior(item, behavior);
            }
            if (item instanceof CauldronInteractableItem interactableItem) {
                interactableItem.getInteractionMap().map().put(item, interactableItem.getInteraction());
            }
        });
    }

    private void onRegisterCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment) {
        CommandsAS.register(dispatcher);
    }

    private void onRegisterReloadListeners() {
        ResourceManagerHelper helper = ResourceManagerHelper.get(PackType.SERVER_DATA);
        helper.registerReloadListener(ResearchNodeLoader.getInstance());
        helper.registerReloadListener(PerkTreeLoader.ID, PerkTreeLoader::new);
        helper.registerReloadListener(ArtifactConditionLoader.ID, provider -> {
            ArtifactConditionLoader.getInstance().provider = provider;
            return ArtifactConditionLoader.getInstance();
        });
        helper.registerReloadListener(ArtifactEffectLoader.getPositiveInstance().id, provider -> {
            ArtifactEffectLoader.getPositiveInstance().provider = provider;
            return ArtifactEffectLoader.getPositiveInstance();
        });
        helper.registerReloadListener(ArtifactEffectLoader.getNegativeInstance().id, provider -> {
            ArtifactEffectLoader.getNegativeInstance().provider = provider;
            return ArtifactEffectLoader.getNegativeInstance();
        });
        helper.registerReloadListener(LumenBindingTypeLoader.ID, provider -> {
            LumenBindingTypeLoader.getInstance().provider = provider;
            return LumenBindingTypeLoader.getInstance();
        });
    }

    private void onBuildCreativeTabContents(CreativeModeTab tab, FabricItemGroupEntries entries) {
        ItemsAS.REGISTERED_ITEMS.stream()
                .filter(item -> item instanceof CreativeTabItem ci && ci.isInTab(tab))
                .toList().forEach(item -> {
                    ((CreativeTabItem) item).fillCreativeTab(entries::accept);
                });
    }
}
