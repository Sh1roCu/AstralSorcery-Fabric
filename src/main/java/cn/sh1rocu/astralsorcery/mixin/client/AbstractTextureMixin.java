package cn.sh1rocu.astralsorcery.mixin.client;

import cn.sh1rocu.astralsorcery.api.mixin.IBlurMipmap;
import net.minecraft.client.renderer.texture.AbstractTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractTexture.class)
public abstract class AbstractTextureMixin implements IBlurMipmap {

    @Shadow
    protected boolean blur;
    @Shadow
    protected boolean mipmap;

    @Shadow
    public abstract void setFilter(boolean blur, boolean mipmap);

    @Unique
    private boolean as$lastBlur;
    @Unique
    private boolean as$lastMipmap;

    @Override
    public void as$setBlurMipmap(boolean blur, boolean mipmap) {
        this.as$lastBlur = this.blur;
        this.as$lastMipmap = this.mipmap;
        this.setFilter(blur, mipmap);
    }

    @Override
    public void as$restoreLastBlurMipmap() {
        this.setFilter(this.as$lastBlur, this.as$lastMipmap);
    }
}