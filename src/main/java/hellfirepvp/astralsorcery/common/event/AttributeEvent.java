/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.event;

import cn.sh1rocu.astralsorcery.api.event.BaseEvent;
import hellfirepvp.astralsorcery.common.lib.RegistriesAS;
import hellfirepvp.astralsorcery.common.perk.type.base.PerkAttributeType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AttributeEvent
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AttributeEvent {

    public static class PostProcessVanilla extends BaseEvent {

        private final AttributeInstance instance;
        private final double originalValue;
        private double value;

        public static final Event<Callback> EVENT = EventFactory.createWithPhases(Callback.class, callbacks -> event -> {
            for (Callback callback : callbacks) {
                callback.post(event);
            }
        }, HIGHEST, HIGH, Event.DEFAULT_PHASE, LOW, LOWEST);

        public PostProcessVanilla(AttributeInstance instance, double value) {
            this.instance = instance;
            this.originalValue = value;
            this.value = value;
        }

        public double getOriginalValue() {
            return this.originalValue;
        }

        public double getValue() {
            return this.value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public AttributeInstance getInstance() {
            return this.instance;
        }

        public Holder<Attribute> getAttribute() {
            return this.instance.getAttribute();
        }

        public Optional<PerkAttributeType> resolveAttributeType() {
            return PerkAttributeType.fromVanillaType(this.getAttribute());
        }

        public interface Callback {
            void post(PostProcessVanilla event);
        }
    }

    public static class PostProcessModded extends BaseEvent {

        private final Player player;
        private final PerkAttributeType type;
        private final double originalValue;
        private double value;

        public static final Event<Callback> EVENT = EventFactory.createWithPhases(Callback.class, callbacks -> event -> {
            for (Callback callback : callbacks) {
                callback.post(event);
            }
        }, HIGHEST, HIGH, Event.DEFAULT_PHASE, LOW, LOWEST);

        public PostProcessModded(double value, PerkAttributeType type, Player player) {
            this.player = player;
            this.type = type;
            this.originalValue = value;
            this.value = value;
        }

        public double getOriginalValue() {
            return this.originalValue;
        }

        public double getValue() {
            return this.value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public PerkAttributeType getType() {
            return this.type;
        }

        public Player getPlayer() {
            return this.player;
        }

        public interface Callback {
            void post(PostProcessModded event);
        }
    }

    public static double postProcessModded(Player player, Supplier<? extends PerkAttributeType> type, double value) {
        var ev = new PostProcessModded(value, type.get(), player);
        PostProcessModded.EVENT.invoker().post(ev);
        return ev.getValue();
    }

    public static float postProcessModded(Player player, Supplier<? extends PerkAttributeType> type, float value) {
        return (float) postProcessModded(player, type, (double) value);
    }

    public static double postProcessModded(Player player, PerkAttributeType type, double value) {
        var ev = new PostProcessModded(value, type, player);
        PostProcessModded.EVENT.invoker().post(ev);
        return ev.getValue();
    }

    public static float postProcessModded(Player player, PerkAttributeType type, float value) {
        return (float) postProcessModded(player, type, (double) value);
    }

    public static double postProcessModded(Player player, ResourceLocation key, double value) {
        PerkAttributeType pType = RegistriesAS.REGISTRY_PERK_ATTRIBUTE_TYPES.get(key);
        if (pType == null) return value;
        return postProcessModded(player, pType, value);
    }

    public static float postProcessModded(Player player, ResourceLocation key, float value) {
        return (float) postProcessModded(player, key, (double) value);
    }

    public static double postProcessVanilla(double value, AttributeInstance attribute) {
        var event = new AttributeEvent.PostProcessVanilla(attribute, value);
        PostProcessVanilla.EVENT.invoker().post(event);
        return attribute.getAttribute().value().sanitizeValue(event.getValue());
    }
}
