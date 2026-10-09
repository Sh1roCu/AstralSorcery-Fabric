package cn.sh1rocu.astralsorcery.mixin.common;

import cn.sh1rocu.astralsorcery.api.mixin.LevelInjection;
import cn.sh1rocu.astralsorcery.util.neoforge.common.util.BlockSnapshot;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

// From Kilt
@Mixin(Level.class)
public abstract class LevelInject implements LevelAccessor, LevelInjection {
    @Shadow
    public abstract boolean isClientSide();

    @Shadow
    @Final
    private ResourceKey<Level> dimension;
    @Unique
    public boolean as$restoringBlockSnapshots = false;
    @Unique
    public boolean as$captureBlockSnapshots = false;

    @Unique
    public ArrayList<BlockSnapshot> as$capturedBlockSnapshots = new ArrayList<>();

    @Unique
    @Override
    public ArrayList<BlockSnapshot> as$getCapturedBlockSnapshots() {
        return as$capturedBlockSnapshots;
    }

    @Inject(method = "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getBlock()Lnet/minecraft/world/level/block/Block;", ordinal = 0, shift = At.Shift.AFTER))
    private void as$captureSnapshot(BlockPos pos, BlockState state, int flags, int recursionLeft, CallbackInfoReturnable<Boolean> cir, @Local(argsOnly = true) LocalRef<BlockPos> posRef, @Share("blockSnapshot") LocalRef<BlockSnapshot> blockSnapshot) {
        posRef.set(pos.immutable());

        if (this.as$captureBlockSnapshots && !this.isClientSide()) {
            blockSnapshot.set(BlockSnapshot.create(this.dimension, (Level) (Object) this, posRef.get()));
            this.as$capturedBlockSnapshots.add(blockSnapshot.get());
        }

//        // TODO: Kilt: what are these used for?
//        BlockState old = this.getBlockState(posRef.get());
//        int oldLight = old.getLightEmission((Level) (Object) this, posRef.get());
//        int oldOpacity = old.getLightBlock((Level) (Object) this, posRef.get());
    }

    @Inject(method = "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z", at = @At(value = "RETURN", ordinal = 2))
    private void as$removeCapturedSnapshot(BlockPos pos, BlockState state, int flags, int recursionLeft, CallbackInfoReturnable<Boolean> cir, @Share("blockSnapshot") LocalRef<BlockSnapshot> blockSnapshot) {
        if (blockSnapshot.get() != null) {
            this.as$capturedBlockSnapshots.remove(blockSnapshot.get());
        }
    }

    @Inject(method = "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"), cancellable = true)
    private void as$cancelIfCapturing(BlockPos pos, BlockState state, int flags, int recursionLeft, CallbackInfoReturnable<Boolean> cir, @Share("blockSnapshot") LocalRef<BlockSnapshot> blockSnapshot) {
        if (blockSnapshot.get() != null) {
            cir.setReturnValue(true);
        }
    }

    @Override
    public void as$setCapturingBlockSnapshots(boolean value) {
        this.as$captureBlockSnapshots = value;
    }

    @Override
    public void as$setRestoringBlockSnapshots(boolean value) {
        this.as$restoringBlockSnapshots = value;
    }

    @Override
    public boolean as$getCapturingBlockSnapshots() {
        return this.as$captureBlockSnapshots;
    }

    @Override
    public boolean as$getRestoringBlockSnapshots() {
        return this.as$restoringBlockSnapshots;
    }
}