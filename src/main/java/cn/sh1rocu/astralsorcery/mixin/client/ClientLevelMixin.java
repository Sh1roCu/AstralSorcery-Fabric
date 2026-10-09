package cn.sh1rocu.astralsorcery.mixin.client;

import cn.sh1rocu.astralsorcery.api.event.EntityJoinLevelEvent;
import cn.sh1rocu.astralsorcery.api.event.LevelEvent;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin extends Level {
    protected ClientLevelMixin(WritableLevelData writableLevelData, ResourceKey<Level> resourceKey, RegistryAccess registryAccess, Holder<DimensionType> holder, Supplier<ProfilerFiller> supplier, boolean bl, boolean bl2, long l, int i) {
        super(writableLevelData, resourceKey, registryAccess, holder, supplier, bl, bl2, l, i);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void as$onLoad(ClientPacketListener clientPacketListener, ClientLevel.ClientLevelData clientLevelData, ResourceKey<Level> resourceKey, Holder<DimensionType> holder, int i, int j, Supplier<ProfilerFiller> supplier, LevelRenderer levelRenderer, boolean bl, long l, CallbackInfo ci) {
        var event = new LevelEvent.Load(this);
        LevelEvent.LOAD.invoker().post(event);
    }

    @Inject(method = "addEntity", at = @At("HEAD"), cancellable = true)
    public void as$entityJoinLevelEvent(Entity entityToSpawn, CallbackInfo ci) {
        var event = new EntityJoinLevelEvent(entityToSpawn, this);
        EntityJoinLevelEvent.EVENT.invoker().post(event);
        if (event.isCanceled())
            ci.cancel();
    }
}