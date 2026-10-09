package cn.sh1rocu.astralsorcery.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.shaders.Program;
import hellfirepvp.astralsorcery.AstralSorcery;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * From MoonlightLib
 *
 * @link <a href="https://github.com/MehVahdJukaar/Moonlight/blob/1.21/fabric/src/main/java/net/mehvahdjukaar/moonlight/core/mixins/platform/EffectInstanceMixin.java">...</a>
 */
@Mixin(EffectInstance.class)
public class EffectInstanceMixin {

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;withDefaultNamespace(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;"))
    private ResourceLocation as$allowModShaderRes(String string, Operation<ResourceLocation> original,
                                                  @Local(argsOnly = true) String name) {
        try {
            ResourceLocation id = ResourceLocation.tryParse(name);
            if (id != null && id.getNamespace().equals(AstralSorcery.MODID)) {
                return id.withPath("shaders/program/" + id.getPath() + ".json");
            }
        } catch (Exception e) {
            //ignore
        }
        return original.call(string);
    }

    @WrapOperation(method = "getOrCreate", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;withDefaultNamespace(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;"))
    private static ResourceLocation as$allowModPostShaderRes(String string, Operation<ResourceLocation> original,
                                                             @Local(argsOnly = true) String name, @Local(argsOnly = true) Program.Type type) {
        try {
            ResourceLocation id = ResourceLocation.tryParse(name);
            if (id != null && id.getNamespace().equals(AstralSorcery.MODID)) {
                return id.withPath("shaders/program/" + id.getPath() + type.getExtension());
            }
        } catch (Exception e) {
            //ignore
        }
        return original.call(string);
    }
}