package cn.sh1rocu.astralsorcery.mixin.common;

import cn.sh1rocu.astralsorcery.api.event.LevelEvent;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Shadow
    @Final
    private Map<ResourceKey<Level>, ServerLevel> levels;

    @Inject(method = "createLevels", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/ServerLevelData;isInitialized()Z"))
    private void as$onLoadOverworld(ChunkProgressListener chunkProgressListener, CallbackInfo ci) {
        var event = new LevelEvent.Load(this.levels.get(Level.OVERWORLD));
        LevelEvent.LOAD.invoker().post(event);
    }

    @Inject(method = "createLevels", at = @At(
            value = "INVOKE",
            target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
            ordinal = 1,
            shift = At.Shift.AFTER
    ))
    private void as$onLoadWorld(ChunkProgressListener chunkProgressListener, CallbackInfo ci, @Local(index = 18) ResourceKey<Level> key) {
        var event = new LevelEvent.Load(levels.get(key));
        LevelEvent.LOAD.invoker().post(event);
    }

    @Inject(method = "stopServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;close()V"))
    private void as$onStopServer(CallbackInfo ci, @Local(index = 2) ServerLevel serverLevel) {
        var event = new LevelEvent.Unload(serverLevel);
        LevelEvent.UNLOAD.invoker().post(event);
    }
}