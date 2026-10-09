package cn.sh1rocu.astralsorcery.mixin.common;

import cn.sh1rocu.astralsorcery.api.mixin.IDyeColor;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.MapColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DyeColor.class)
public class DyeColorMixin implements IDyeColor {

    @Unique
    private TagKey<Item> as$tag;

    @Override
    public TagKey<Item> as$getTag() {
        return as$tag;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void as$init(String string, int i, int id, String name, int textureDefuseColor, MapColor mapColor, int fireworkColor, int textColor, CallbackInfo ci) {
        this.as$tag = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "dyes/" + name));
    }
}
