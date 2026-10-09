/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.perk.tree;

import com.mojang.serialization.MapCodec;
import hellfirepvp.astralsorcery.common.util.TriFunction;
import net.minecraft.resources.ResourceLocation;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: PerkType
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public abstract class PerkType<T extends AbstractPerk<?>> {

    private final MapCodec<T> codec;
    private final PerkDataType<?> dataType;

    public PerkType(MapCodec<T> codec, PerkDataType<?> dataType) {
        this.codec = codec;
        this.dataType = dataType;
    }

    public static <T extends AbstractPerk<?>> PerkType<T> of(MapCodec<T> codec, PerkDataType<?> dataTypeSupplier, TriFunction<ResourceLocation, Float, Float, T> constructor) {
        return new PerkType<T>(codec, dataTypeSupplier) {
            @Override
            public T newBlankPerk(ResourceLocation key, float x, float y) {
                return constructor.apply(key, x, y);
            }
        };
    }

    ;

    public abstract T newBlankPerk(ResourceLocation key, float x, float y);

    public MapCodec<T> perkCodec() {
        return this.codec;
    }

    public PerkDataType<?> dataType() {
        return this.dataType;
    }
}
