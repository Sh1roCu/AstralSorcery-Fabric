package cn.sh1rocu.astralsorcery.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;

public class RegisterMaterialAtlasesEvent extends BaseEvent implements ICancellableEvent {
    private final Map<ResourceLocation, ResourceLocation> atlases;

    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    });

    @ApiStatus.Internal
    public RegisterMaterialAtlasesEvent(Map<ResourceLocation, ResourceLocation> atlases) {
        this.atlases = atlases;
    }

    public void register(ResourceLocation atlasLocation, ResourceLocation atlasInfoLocation) {
        ResourceLocation oldAtlasInfoLoc = this.atlases.putIfAbsent(atlasLocation, atlasInfoLocation);
        if (oldAtlasInfoLoc != null) {
            throw new IllegalStateException(String.format(
                    "Duplicate registration of atlas: %s (old info: %s, new info: %s)",
                    atlasLocation,
                    oldAtlasInfoLoc,
                    atlasInfoLocation));
        }
    }

    public interface Callback {
        void post(RegisterMaterialAtlasesEvent event);
    }
}
