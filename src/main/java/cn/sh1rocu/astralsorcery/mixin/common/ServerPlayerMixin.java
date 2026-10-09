package cn.sh1rocu.astralsorcery.mixin.common;

import cn.sh1rocu.astralsorcery.AstralSorceryFabric;
import com.mojang.authlib.GameProfile;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;

import net.minecraft.world.entity.player.Player;

import net.minecraft.world.level.Level;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {
    public ServerPlayerMixin(Level level, BlockPos pos, float yRot, GameProfile gameProfile) {
        super(level, pos, yRot, gameProfile);
    }

    @Inject(method = "doTick", at = @At(value = "FIELD", target = "Lnet/minecraft/server/level/ServerPlayer;tickCount:I", opcode = Opcodes.GETFIELD))
    private void as$updateFlyAbility(CallbackInfo ci) {
        if (getAbilities().flying && !(this.getAbilities().mayfly || this.getAttributeValue(AstralSorceryFabric.CREATIVE_FLIGHT) > 0)) {
            getAbilities().flying = false;
            onUpdateAbilities();
        }
    }
}