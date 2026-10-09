package cn.sh1rocu.astralsorcery.api.mixin;

import cn.sh1rocu.astralsorcery.util.neoforge.common.util.BlockSnapshot;

import java.util.ArrayList;

// From Kilt
public interface LevelInjection {
    default ArrayList<BlockSnapshot> as$getCapturedBlockSnapshots() {
        throw new IllegalStateException();
    }

    default boolean as$getRestoringBlockSnapshots() {
        throw new AssertionError();
    }

    default boolean as$getCapturingBlockSnapshots() {
        throw new AssertionError();
    }

    default void as$setCapturingBlockSnapshots(boolean value) {
        throw new AssertionError();
    }

    default void as$setRestoringBlockSnapshots(boolean value) {
        throw new AssertionError();
    }
}