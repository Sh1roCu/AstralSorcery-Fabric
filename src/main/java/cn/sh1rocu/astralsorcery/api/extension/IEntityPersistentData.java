package cn.sh1rocu.astralsorcery.api.extension;

import net.minecraft.nbt.CompoundTag;

public interface IEntityPersistentData {
    default CompoundTag as$getPersistentData() {
        throw new AssertionError();
    }
}