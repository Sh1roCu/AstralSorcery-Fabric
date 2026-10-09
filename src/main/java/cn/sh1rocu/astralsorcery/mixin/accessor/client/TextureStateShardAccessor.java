package cn.sh1rocu.astralsorcery.mixin.accessor.client;

import net.minecraft.client.renderer.RenderStateShard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderStateShard.TextureStateShard.class)
public interface TextureStateShardAccessor {
    @Accessor("blur")
    boolean as$isBlur();

    @Accessor("mipmap")
    boolean as$isMipmap();
}
