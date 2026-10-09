package cn.sh1rocu.astralsorcery.mixin.client;

import cn.sh1rocu.astralsorcery.AstralSorceryFabric;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Abilities;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {
    public LocalPlayerMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @WrapOperation(method = "aiStep", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Abilities;mayfly:Z", opcode = Opcodes.GETFIELD))
    private boolean as$mayFly1(Abilities instance, Operation<Boolean> original) {
        return original.call(instance) || this.getAttributeValue(AstralSorceryFabric.CREATIVE_FLIGHT) > 0;

    }

    @WrapOperation(method = "hasEnoughFoodToStartSprinting", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Abilities;mayfly:Z", opcode = Opcodes.GETFIELD))
    private boolean as$mayFly2(Abilities instance, Operation<Boolean> original) {
        return original.call(instance) || this.getAttributeValue(AstralSorceryFabric.CREATIVE_FLIGHT) > 0;
    }
}