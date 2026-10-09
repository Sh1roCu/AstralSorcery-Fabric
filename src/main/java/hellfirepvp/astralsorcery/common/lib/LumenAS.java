/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.lib.constants.ColorsAS;
import hellfirepvp.astralsorcery.common.lumen.Lumen;
import hellfirepvp.astralsorcery.common.lumen.LumenLike;
import hellfirepvp.astralsorcery.common.lumen.LumenPrismatic;
import hellfirepvp.astralsorcery.common.lumen.LumenStack;
import hellfirepvp.astralsorcery.common.util.data.ColorWrapper;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: LumenAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class LumenAS {
    public static void init() {

    }

    public static final DeferredLumen NONE = registerSimple("none", ColorWrapper.WHITE);

    public static final DeferredLumen AEVITAS = registerElementary("aevitas", ColorsAS.LUMEN_AEVITAS);
    public static final DeferredLumen ARMARA = registerElementary("armara", ColorsAS.LUMEN_ARMARA);
    public static final DeferredLumen DISCIDIA = registerElementary("discidia", ColorsAS.LUMEN_DISCIDIA);
    public static final DeferredLumen EVORSIO = registerElementary("evorsio", ColorsAS.LUMEN_EVORSIO);
    public static final DeferredLumen VICIO = registerElementary("vicio", ColorsAS.LUMEN_VICIO);

    public static final DeferredLumen VIREL = registerSimple("virel", ColorsAS.LUMEN_VIREL); //life
    public static final DeferredLumen SOLYN = registerSimple("solyn", ColorsAS.LUMEN_SOLYN); //light
    public static final DeferredLumen NULLAE = registerSimple("nullae", ColorsAS.LUMEN_NULLAE); //emptiness
    public static final DeferredLumen CALDOR = registerSimple("caldor", ColorsAS.LUMEN_CALDOR); //order

    public static final DeferredLumen HYLE = registerSimple("hyle", ColorsAS.LUMEN_HYLE); //matter
    public static final DeferredLumen DYNAMIS = registerSimple("dynamis", ColorsAS.LUMEN_DYNAMIS); //energy
    public static final DeferredLumen AION = registerSimple("aion", ColorsAS.LUMEN_AION); //time
    public static final DeferredLumen AKASHA = registerSimple("akasha", ColorsAS.LUMEN_AKASHA); //space

    public static final DeferredLumen PRISMATIC = register("prismatic", LumenPrismatic::new);

    private static DeferredLumen registerElementary(String name, ColorWrapper color) {
        return register(name, () -> new Lumen(color, true));
    }

    private static DeferredLumen registerSimple(String name, ColorWrapper color) {
        return register(name, () -> new Lumen(color));
    }

    private static <T extends Lumen> DeferredLumen register(String name, Supplier<T> supplier) {
        Lumen lumen = Registry.register(RegistriesAS.REGISTRY_LUMEN, AstralSorcery.key(name), supplier.get());
        return new DeferredLumen(lumen.getRegistryKey().orElseThrow());
    }

    public static class DeferredLumen implements LumenLike {
        protected final ResourceKey<Lumen> key;
        @Nullable
        private Holder<Lumen> holder = null;

        protected DeferredLumen(ResourceKey<Lumen> key) {
            this.key = key;
            this.holder = getRegistry().getHolderOrThrow(this.key);
        }

        @SuppressWarnings("unchecked")
        protected Registry<Lumen> getRegistry() {
            return (Registry<Lumen>) BuiltInRegistries.REGISTRY.get(this.key.registry());
        }

        public Holder<Lumen> holder() {
            return this.holder;
        }

        public ResourceKey<Lumen> getKey() {
            return this.key;
        }

        public @NotNull Lumen value() {
            if (this.holder == null) {
                throw new NullPointerException("Trying to access unbound value: " + this.key);
            }

            return this.holder.value();
        }

        public Lumen get() {
            return this.value();
        }

        @Override
        public Lumen asLumen() {
            return this.get();
        }

        public LumenStack stack() {
            return LumenStack.of(this.get(), LumenStack.FLASK_VALUE);
        }

        public LumenStack stack(int amount) {
            return LumenStack.of(this.get(), amount);
        }
    }
}
