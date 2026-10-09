package cn.sh1rocu.astralsorcery.mixin.common;

import cn.sh1rocu.astralsorcery.api.mixin.LevelInjection;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// From Kilt
@Mixin(Block.class)
public class BlockInject {

    @ModifyExpressionValue(method = "popResource(Lnet/minecraft/world/level/Level;Ljava/util/function/Supplier;Lnet/minecraft/world/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z"))
    private static boolean as$checkIsRestoringBlockSnapshots(boolean original, @Local(argsOnly = true) Level level) {
        return original && !((LevelInjection) level).as$getRestoringBlockSnapshots();
    }

    @ModifyExpressionValue(method = "popExperience", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z"))
    private boolean as$checkIsRestoringBlockSnapshots(boolean original, @Local(argsOnly = true) ServerLevel level) {
        return original && !((LevelInjection) level).as$getRestoringBlockSnapshots();
    }
}
