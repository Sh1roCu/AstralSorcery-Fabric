package cn.sh1rocu.astralsorcery.mixin.common;

import cn.sh1rocu.astralsorcery.api.mixin.IRegistry;
import net.minecraft.core.Registry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Registry.class)
public interface RegistryMixin<T> extends IRegistry<T> {
}
