/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.Mods;
import hellfirepvp.astralsorcery.common.effect.BasicMobEffect;
import hellfirepvp.astralsorcery.common.effect.RevivalMobEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: MobEffectsAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class MobEffectsAS {

    public static void init() {

    }

    public static final Holder<MobEffect> RAMPAGE =
            register("rampage", () -> new BasicMobEffect(MobEffectCategory.BENEFICIAL, 0xBB4400)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE, Mods.MINECRAFT.key("effect.rampage.damage"), 0.2F, AttributeModifier.Operation.ADD_VALUE)
                    .addAttributeModifier(Attributes.ATTACK_SPEED, Mods.MINECRAFT.key("effect.rampage.speed"), 0.03F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, Mods.MINECRAFT.key("effect.rampage.movement"), 0.03F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    public static final Holder<MobEffect> PHOENIX_BLESSING =
            register("phoenix_blessing", () -> new RevivalMobEffect(MobEffectCategory.BENEFICIAL, 0xFF9944));

    private static Holder<MobEffect> register(String name, Supplier<MobEffect> effectFn) {
        MobEffect effect = Registry.register(BuiltInRegistries.MOB_EFFECT, AstralSorcery.key(name), effectFn.get());
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect);
    }
}
