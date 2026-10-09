package cn.sh1rocu.astralsorcery.mixin.client;

import hellfirepvp.astralsorcery.client.screen.tome.TomeLumenScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @Inject(method = "handleUpdateRecipes", at = @At("TAIL"))
    private void as$handleUpdateRecipes(CallbackInfo ci) {
        TomeLumenScreen.recipesSyncedFromServer();
    }
}
