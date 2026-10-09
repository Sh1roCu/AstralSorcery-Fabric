/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib;

import cn.sh1rocu.astralsorcery.AstralSorceryFabric;
import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.perk.reader.*;
import hellfirepvp.astralsorcery.common.perk.reader.custom.ReaderAttackLifeLeech;
import hellfirepvp.astralsorcery.common.perk.source.ModifierSourceProvider;
import hellfirepvp.astralsorcery.common.perk.source.provider.PerkSourceProvider;
import hellfirepvp.astralsorcery.common.perk.source.provider.equipment.EquipmentSourceProvider;
import hellfirepvp.astralsorcery.common.perk.source.provider.lumen.LumenBindingSourceProvider;
import hellfirepvp.astralsorcery.common.perk.type.*;
import hellfirepvp.astralsorcery.common.perk.type.base.PerkAttributeType;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: PerksAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class PerksAS {

    public static void init() {
    }

    public static class AttributeTypes {

        private static void init() {
        }

        public static final AttributeTypePerkEffect PERK_EFFECT =
                register("perk_effect", AttributeTypePerkEffect::new);
        public static final PerkAttributeType PERK_EXPERIENCE =
                register("perk_experience", () -> PerkAttributeType.create(true));

        public static final AttributeTypeArmor ARMOR =
                register("armor", AttributeTypeArmor::new);
        public static final AttributeTypeArmorToughness ARMOR_TOUGHNESS =
                register("armor_toughness", AttributeTypeArmorToughness::new);
        public static final AttributeTypeAttackDamage ATTACK_DAMAGE =
                register("attack_damage", AttributeTypeAttackDamage::new);
        public static final AttributeTypeAttackReach ATTACK_REACH =
                register("attack_reach", AttributeTypeAttackReach::new);
        public static final AttributeTypeAttackSpeed ATTACK_SPEED =
                register("attack_speed", AttributeTypeAttackSpeed::new);
        public static final AttributeTypeBlockBreakSpeed BLOCK_BREAK_SPEED =
                register("block_break_speed", AttributeTypeBlockBreakSpeed::new);
        public static final AttributeTypeBlockReach BLOCK_REACH =
                register("block_reach", AttributeTypeBlockReach::new);
        public static final AttributeTypeFallDamage FALL_DAMAGE =
                register("fall_damage", AttributeTypeFallDamage::new);
        public static final AttributeTypeLuck LUCK =
                register("luck", AttributeTypeLuck::new);
        public static final AttributeTypeMaxHealth MAX_HEALTH =
                register("max_health", AttributeTypeMaxHealth::new);
        public static final AttributeTypeMovementSpeed MOVEMENT_SPEED =
                register("movement_speed", AttributeTypeMovementSpeed::new);
        public static final AttributeTypeSafeFallDistance SAFE_FALL_DISTANCE =
                register("safe_fall_distance", AttributeTypeSafeFallDistance::new);
        public static final AttributeTypeScale SCALE =
                register("scale", AttributeTypeScale::new);
        public static final AttributeTypeStepHeight STEP_HEIGHT =
                register("step_height", AttributeTypeStepHeight::new);
        public static final AttributeTypeSwimSpeed SWIM_SPEED =
                register("swim_speed", AttributeTypeSwimSpeed::new);

        public static final AttributeTypeBlockChance BLOCK_CHANCE =
                register("block_chance", AttributeTypeBlockChance::new);
        public static final AttributeTypeCooldownReduction COOLDOWN_REDUCTION =
                register("cooldown_reduction", AttributeTypeCooldownReduction::new);
        public static final AttributeTypeCriticalHitChance CRITICAL_HIT_CHANCE =
                register("critical_hit_chance", AttributeTypeCriticalHitChance::new);
        public static final AttributeTypeCriticalHitDamage CRITICAL_HIT_DAMAGE =
                register("critical_hit_damage", AttributeTypeCriticalHitDamage::new);
        public static final AttributeTypeDamageReduction DAMAGE_REDUCTION =
                register("damage_reduction", AttributeTypeDamageReduction::new);
        public static final AttributeTypeDamageReflect DAMAGE_REFLECT =
                register("damage_reflect", AttributeTypeDamageReflect::new);
        public static final AttributeTypeDynamicEnchantmentEffect ENCHANTMENT_EFFECT =
                register("dynamic_enchantment_effect", AttributeTypeDynamicEnchantmentEffect::new);
        public static final AttributeTypeElementalResistance ELEMENTAL_RESISTANCE =
                register("elemental_resistance", AttributeTypeElementalResistance::new);
        public static final AttributeTypeAttackLifeLeech LIFE_LEECH =
                register("life_leech", AttributeTypeAttackLifeLeech::new);
        public static final AttributeTypeLifeRecovery LIFE_RECOVERY =
                register("life_recovery", AttributeTypeLifeRecovery::new);
        public static final AttributeTypeMiningSize MINING_SIZE =
                register("mining_size", AttributeTypeMiningSize::new);
        public static final AttributeTypePierceArmor PIERCE_ARMOR =
                register("pierce_armor", AttributeTypePierceArmor::new);
        public static final AttributeTypePotionDuration POTION_DURATION =
                register("potion_duration", AttributeTypePotionDuration::new);
        public static final AttributeTypeProjectileDamage PROJECTILE_DAMAGE =
                register("projectile_damage", AttributeTypeProjectileDamage::new);
        public static final AttributeTypeProjectileSpeed PROJECTILE_SPEED =
                register("projectile_speed", AttributeTypeProjectileSpeed::new);

        private static <T extends PerkAttributeType> T register(String name, Supplier<T> supplier) {
            return Registry.register(RegistriesAS.REGISTRY_PERK_ATTRIBUTE_TYPES, AstralSorcery.key(name), supplier.get());
        }
    }

    public static class Readers {

        private static void init() {
        }

        public static final PerkAttributeTypeReader.Type PERK_EFFECT =
                type("perk_effect", AttributeTypes.PERK_EFFECT, ReaderPercentageAttribute::new);
        public static final PerkAttributeTypeReader.Type PERK_EXPERIENCE =
                type("perk_experience", AttributeTypes.PERK_EXPERIENCE, ReaderPercentageAttribute::new);

        public static final PerkAttributeTypeReader.Type ARMOR =
                vanillaType("armor", AttributeTypes.ARMOR, Attributes.ARMOR);
        public static final PerkAttributeTypeReader.Type ARMOR_TOUGHNESS =
                vanillaType("armor_toughness", AttributeTypes.ARMOR_TOUGHNESS, Attributes.ARMOR_TOUGHNESS);
        public static final PerkAttributeTypeReader.Type ATTACK_DAMAGE =
                vanillaDecimalType("attack_damage", AttributeTypes.ATTACK_DAMAGE, Attributes.ATTACK_DAMAGE);
        public static final PerkAttributeTypeReader.Type ATTACK_REACH =
                vanillaDecimalType("attack_reach", AttributeTypes.ATTACK_REACH, Attributes.ENTITY_INTERACTION_RANGE);
        public static final PerkAttributeTypeReader.Type ATTACK_SPEED =
                vanillaDecimalType("attack_speed", AttributeTypes.ATTACK_SPEED, Attributes.ATTACK_SPEED);
        public static final PerkAttributeTypeReader.Type BLOCK_BREAK_SPEED =
                vanillaType("block_break_speed", AttributeTypes.BLOCK_BREAK_SPEED, Attributes.BLOCK_BREAK_SPEED);
        public static final PerkAttributeTypeReader.Type BLOCK_REACH =
                vanillaDecimalType("block_reach", AttributeTypes.BLOCK_REACH, Attributes.BLOCK_INTERACTION_RANGE);
        public static final PerkAttributeTypeReader.Type FALL_DAMAGE =
                vanillaDecimalType("fall_damage", AttributeTypes.FALL_DAMAGE, Attributes.FALL_DAMAGE_MULTIPLIER);
        public static final PerkAttributeTypeReader.Type LUCK =
                vanillaDecimalType("luck", AttributeTypes.LUCK, Attributes.LUCK);
        public static final PerkAttributeTypeReader.Type MAX_HEALTH =
                vanillaType("max_health", AttributeTypes.MAX_HEALTH, Attributes.MAX_HEALTH);
        public static final PerkAttributeTypeReader.Type MOVEMENT_SPEED =
                vanillaDecimalType("movement_speed", AttributeTypes.MOVEMENT_SPEED, Attributes.MOVEMENT_SPEED);
        public static final PerkAttributeTypeReader.Type SAFE_FALL_DISTANCE =
                vanillaType("safe_fall_distance", AttributeTypes.SAFE_FALL_DISTANCE, Attributes.SAFE_FALL_DISTANCE);
        public static final PerkAttributeTypeReader.Type SCALE =
                vanillaDecimalType("scale", AttributeTypes.SCALE, Attributes.SCALE);
        public static final PerkAttributeTypeReader.Type STEP_HEIGHT =
                vanillaType("step_height", AttributeTypes.STEP_HEIGHT, Attributes.STEP_HEIGHT);
        public static final PerkAttributeTypeReader.Type SWIM_SPEED =
                vanillaDecimalType("swim_speed", AttributeTypes.SWIM_SPEED, AstralSorceryFabric.SWIM_SPEED);

        public static final PerkAttributeTypeReader.Type BLOCK_CHANCE =
                type("block_chance", AttributeTypes.BLOCK_CHANCE, ReaderAddedPercentage::withPercent);
        public static final PerkAttributeTypeReader.Type COOLDOWN_REDUCTION =
                type("cooldown_reduction", AttributeTypes.COOLDOWN_REDUCTION, ReaderAddedSecondsPercentage::new);
        public static final PerkAttributeTypeReader.Type CRITICAL_HIT_CHANCE =
                type("critical_hit_chance", AttributeTypes.CRITICAL_HIT_CHANCE, ReaderAddedPercentage::withPercent);
        public static final PerkAttributeTypeReader.Type CRITICAL_HIT_DAMAGE =
                type("critical_hit_damage", AttributeTypes.CRITICAL_HIT_DAMAGE, ReaderPercentageAttribute::new);
        public static final PerkAttributeTypeReader.Type DAMAGE_REDUCTION =
                type("damage_reduction", AttributeTypes.DAMAGE_REDUCTION, type -> new ReaderPercentageAttribute(type).negate());
        public static final PerkAttributeTypeReader.Type DAMAGE_REFLECT =
                type("damage_reflect", AttributeTypes.DAMAGE_REFLECT, ReaderAddedPercentage::withPercent);
        public static final PerkAttributeTypeReader.Type ENCHANTMENT_EFFECT =
                type("dynamic_enchantment_effect", AttributeTypes.ENCHANTMENT_EFFECT, ReaderPercentageAttribute::new);
        public static final PerkAttributeTypeReader.Type ELEMENTAL_RESISTANCE =
                type("elemental_resistance", AttributeTypes.ELEMENTAL_RESISTANCE, ReaderPercentageAttribute::new);
        public static final PerkAttributeTypeReader.Type LIFE_LEECH =
                type("life_leech", AttributeTypes.LIFE_LEECH, ReaderAttackLifeLeech::new);
        public static final PerkAttributeTypeReader.Type LIFE_RECOVERY =
                type("life_recovery", AttributeTypes.LIFE_RECOVERY, ReaderPercentageAttribute::new);
        public static final PerkAttributeTypeReader.Type MINING_SIZE =
                type("mining_size", AttributeTypes.MINING_SIZE, ReaderFlatAttribute.withDefault(1));
        public static final PerkAttributeTypeReader.Type PIERCE_ARMOR =
                type("pierce_armor", AttributeTypes.PIERCE_ARMOR, ReaderAddedPercentage::withPercent);
        public static final PerkAttributeTypeReader.Type POTION_DURATION =
                type("potion_duration", AttributeTypes.POTION_DURATION, ReaderPercentageAttribute::new);
        public static final PerkAttributeTypeReader.Type PROJECTILE_DAMAGE =
                type("projectile_damage", AttributeTypes.PROJECTILE_DAMAGE, ReaderPercentageAttribute::new);
        public static final PerkAttributeTypeReader.Type PROJECTILE_SPEED =
                type("projectile_speed", AttributeTypes.PROJECTILE_SPEED, ReaderPercentageAttribute::new);


        private static PerkAttributeTypeReader.Type type(String name,
                                                         PerkAttributeType type,
                                                         Function<PerkAttributeType, ? extends PerkAttributeTypeReader> reader) {
            return Registry.register(RegistriesAS.REGISTRY_PERK_ATTRIBUTE_TYPE_READERS, AstralSorcery.key(name),
                    new PerkAttributeTypeReader.Type(type, reader.apply(type)));
        }

        private static PerkAttributeTypeReader.Type vanillaType(String name,
                                                                PerkAttributeType type,
                                                                Holder<Attribute> attribute) {
            return Registry.register(RegistriesAS.REGISTRY_PERK_ATTRIBUTE_TYPE_READERS, AstralSorcery.key(name),
                    new PerkAttributeTypeReader.Type(type, new ReaderVanillaAttribute(type, attribute)));
        }

        private static PerkAttributeTypeReader.Type vanillaDecimalType(String name,
                                                                       PerkAttributeType type,
                                                                       Holder<Attribute> attribute) {
            return Registry.register(RegistriesAS.REGISTRY_PERK_ATTRIBUTE_TYPE_READERS, AstralSorcery.key(name),
                    new PerkAttributeTypeReader.Type(type, new ReaderVanillaAttribute(type, attribute).formatAsDecimal()));
        }
    }

    public static class Converters {

        private static void init() {
        }


    }

    public static class CustomModifiers {

        private static void init() {
        }


    }

    public static class Sources {

        private static void init() {
        }

        public static final PerkSourceProvider PERKS = register("perks", PerkSourceProvider::new);
        public static final EquipmentSourceProvider EQUIPMENT = register("equipment", EquipmentSourceProvider::new);
        public static final LumenBindingSourceProvider LUMEN_BINDING = register("lumen_binding", LumenBindingSourceProvider::new);

        private static <T extends ModifierSourceProvider<?>> T register(String name, Supplier<T> supplier) {
            return Registry.register(RegistriesAS.REGISTRY_PERK_MODIFIER_SOURCES, AstralSorcery.key(name), supplier.get());
        }

    }

    public static class Limits {

        private static void init() {
        }

    }

    static {
        // Java doesn't load subclasses out the box, so we gotta manually reference them to run the registrations
        AttributeTypes.init();
        Readers.init();
        Converters.init();
        CustomModifiers.init();
        Sources.init();
        Limits.init();
    }
}
