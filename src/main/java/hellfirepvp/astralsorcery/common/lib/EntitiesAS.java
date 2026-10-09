/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.entity.*;
import hellfirepvp.astralsorcery.common.entity.item.ItemEntityAltarInput;
import hellfirepvp.astralsorcery.common.entity.item.ItemEntityArtifact;
import hellfirepvp.astralsorcery.common.entity.item.ItemEntityCrystal;
import hellfirepvp.astralsorcery.common.entity.item.ItemEntityStarmetal;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: EntitiesAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class EntitiesAS {

    public static void init() {

    }

    public static final EntityType<ItemEntityHighlighted> ITEM_HIGHLIGHTED =
            register("item_highlighted", () ->
                    EntityType.Builder.of(ItemEntityHighlighted.factoryHighlighted(), MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .eyeHeight(0.25F)
                            .clientTrackingRange(8)
                            .alwaysUpdateVelocity(true)
                            .updateInterval(10)
                            .build(AstralSorcery.key("item_highlighted").toString()));
    public static final EntityType<ItemEntityStarmetal> ITEM_STARMETAL =
            register("item_starmetal", () ->
                    EntityType.Builder.of(ItemEntityStarmetal.factory(), MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .eyeHeight(0.25F)
                            .clientTrackingRange(8)
                            .alwaysUpdateVelocity(true)
                            .updateInterval(10)
                            .build(AstralSorcery.key("item_starmetal").toString()));
    public static final EntityType<ItemEntityCrystal> ITEM_CRYSTAL =
            register("item_crystal", () ->
                    EntityType.Builder.of(ItemEntityCrystal.factory(), MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .eyeHeight(0.25F)
                            .clientTrackingRange(8)
                            .alwaysUpdateVelocity(true)
                            .updateInterval(10)
                            .build(AstralSorcery.key("item_crystal").toString()));
    public static final EntityType<ItemEntityArtifact> ITEM_ARTIFACT =
            register("item_artifact", () ->
                    EntityType.Builder.of(ItemEntityArtifact.factory(), MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .eyeHeight(0.25F)
                            .clientTrackingRange(8)
                            .alwaysUpdateVelocity(true)
                            .updateInterval(10)
                            .build(AstralSorcery.key("item_artifact").toString()));
    public static final EntityType<ItemEntityAltarInput> ITEM_ALTAR_INPUT =
            register("item_altar_input", () ->
                    EntityType.Builder.of(ItemEntityAltarInput.factory(), MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .eyeHeight(0.2125F)
                            .clientTrackingRange(6)
                            .alwaysUpdateVelocity(true)
                            .updateInterval(2)
                            .build(AstralSorcery.key("item_altar_input").toString()));
    public static final EntityType<EntityAltarFluidInput> FLUID_ALTAR_INPUT =
            register("fluid_altar_input", () ->
                    EntityType.Builder.of(EntityAltarFluidInput.factory(), MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .eyeHeight(0.2125F)
                            .clientTrackingRange(6)
                            .fireImmune()
                            .alwaysUpdateVelocity(true)
                            .updateInterval(2)
                            .build(AstralSorcery.key("fluid_altar_input").toString()));

    public static final EntityType<EntityFlare> FLARE =
            register("flare", () ->
                    EntityType.Builder.of(EntityFlare.factory(), MobCategory.AMBIENT)
                            .sized(0.4F, 0.4F)
                            .fireImmune()
                            .clientTrackingRange(64)
                            .alwaysUpdateVelocity(true)
                            .updateInterval(1)
                            .build(AstralSorcery.key("flare").toString()));

    public static final EntityType<EntityIlluminationSpark> ILLUMINATION_SPARK =
            register("illumination_spark", () ->
                    EntityType.Builder.of(EntityIlluminationSpark.factory(), MobCategory.MISC)
                            .sized(0.1F, 0.1F)
                            .fireImmune()
                            .noSummon()
                            .noSave()
                            .clientTrackingRange(32)
                            .alwaysUpdateVelocity(true)
                            .updateInterval(1)
                            .build(AstralSorcery.key("illumination_spark").toString()));
    public static final EntityType<EntityNocturnalSpark> NOCTURNAL_SPARK =
            register("nocturnal_spark", () ->
                    EntityType.Builder.of(EntityNocturnalSpark.factory(), MobCategory.MISC)
                            .sized(0.1F, 0.1F)
                            .fireImmune()
                            .noSummon()
                            .noSave()
                            .clientTrackingRange(32)
                            .alwaysUpdateVelocity(true)
                            .updateInterval(1)
                            .build(AstralSorcery.key("nocturnal_spark").toString()));
    public static final EntityType<EntityVividSpark> VIVID_SPARK =
            register("vivid_spark", () ->
                    EntityType.Builder.of(EntityVividSpark.factory(), MobCategory.MISC)
                            .sized(0.1F, 0.1F)
                            .fireImmune()
                            .noSummon()
                            .noSave()
                            .clientTrackingRange(32)
                            .alwaysUpdateVelocity(true)
                            .updateInterval(1)
                            .build(AstralSorcery.key("vivid_spark").toString()));

    public static final EntityType<EntityGrapplingHook> GRAPPLING_HOOK =
            register("grappling_hook", () ->
                    EntityType.Builder.of(EntityGrapplingHook.factory(), MobCategory.MISC)
                            .sized(0.1F, 0.1F)
                            .fireImmune()
                            .noSummon()
                            .clientTrackingRange(64)
                            .alwaysUpdateVelocity(true)
                            .updateInterval(1)
                            .build(AstralSorcery.key("grappling_hook").toString()));

    public static final EntityType<EntityShootingStar> SHOOTING_STAR =
            register("shooting_star", () ->
                    EntityType.Builder.of(EntityShootingStar.factory(), MobCategory.MISC)
                            .sized(0.1F, 0.1F)
                            .fireImmune()
                            .noSave()
                            .clientTrackingRange(96)
                            .alwaysUpdateVelocity(true)
                            .updateInterval(20)
                            .build(AstralSorcery.key("shooting_star").toString()));

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(FLARE, EntityFlare.createAttributes());
    }

    private static <T extends Entity> EntityType<T> register(String name, Supplier<EntityType<T>> supplier) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, AstralSorcery.key(name), supplier.get());
    }
}
