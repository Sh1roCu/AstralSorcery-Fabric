package cn.sh1rocu.astralsorcery.mixin.common;

import cn.sh1rocu.astralsorcery.api.event.EntityTickEvent;
import cn.sh1rocu.astralsorcery.api.extension.IEntityPersistentData;
import cn.sh1rocu.astralsorcery.api.extension.IRunningEffectsBlock;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements IEntityPersistentData {

    @Shadow
    private Level level;

    // From PortingLib
    @Definition(id = "blockState", local = @Local(type = BlockState.class))
    @Definition(id = "getRenderShape", method = "Lnet/minecraft/world/level/block/state/BlockState;getRenderShape()Lnet/minecraft/world/level/block/RenderShape;")
    @Definition(id = "INVISIBLE", field = "Lnet/minecraft/world/level/block/RenderShape;INVISIBLE:Lnet/minecraft/world/level/block/RenderShape;")
    @Expression("blockState.getRenderShape() != INVISIBLE")
    @ModifyExpressionValue(method = "spawnSprintParticle", at = @At("MIXINEXTRAS:EXPRESSION"))
    public boolean as$spawnSprintParticle(boolean original, @Local BlockPos pos, @Local BlockState state) {
        //noinspection ConstantValue
        return original && !(state.getBlock() instanceof IRunningEffectsBlock custom &&
                custom.addRunningEffects(state, this.level, pos, (Entity) (Object) this));
    }

    @Unique
    private CompoundTag as$persistentData;

    @Unique
    @Override
    public CompoundTag as$getPersistentData() {
        if (this.as$persistentData == null) {
            this.as$persistentData = new CompoundTag();
        }
        return as$persistentData;
    }

    @Inject(method = "saveWithoutId", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"))
    private void as$savePersistentData(CompoundTag nbt, CallbackInfoReturnable<CompoundTag> cir) {
        if (this.as$persistentData != null) {
            nbt.put("NeoForgeData", this.as$persistentData.copy());
        }
    }

    @Inject(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"))
    private void as$loadPersistentData(CompoundTag nbt, CallbackInfo ci) {
        if (nbt.contains("NeoForgeData", 10)) {
            as$persistentData = nbt.getCompound("NeoForgeData");
        }
    }

    @WrapOperation(method = "rideTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;tick()V"))
    private void as$onEntityTick(Entity instance, Operation<Void> original) {
        var preEvent = new EntityTickEvent.Pre(instance);
        EntityTickEvent.PRE.invoker().post(preEvent);
        if (!preEvent.isCanceled()) {
            original.call(instance);
            EntityTickEvent.POST.invoker().post(new EntityTickEvent.Post(instance));
        }
    }
}