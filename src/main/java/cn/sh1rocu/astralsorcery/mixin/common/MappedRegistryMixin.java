package cn.sh1rocu.astralsorcery.mixin.common;

import cn.sh1rocu.astralsorcery.api.mixin.IRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(MappedRegistry.class)
public class MappedRegistryMixin<T> implements IRegistry<T> {

    @Shadow
    @Final
    private Map<T, Holder.Reference<T>> byValue;

    @Override
    public boolean as$containsValue(T value) {
        return this.byValue.containsKey(value);
    }
}
