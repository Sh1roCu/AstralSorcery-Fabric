/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib;

import cn.sh1rocu.astralsorcery.util.fluid.FluidStack;
import hellfirepvp.astralsorcery.common.recipe.altar.ActiveAltarRecipe;
import hellfirepvp.astralsorcery.common.util.data.Vector3;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: EntityDataSerializersAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class EntityDataSerializersAS {

    public static void init() {

    }

    public static final EntityDataSerializer<Vector3> VECTOR =
            register("vector", EntityDataSerializer.forValueType(Vector3.STREAM_CODEC));
    public static final EntityDataSerializer<FluidStack> FLUID_STACK =
            register("fluid_variant", EntityDataSerializer.forValueType(FluidStack.STREAM_CODEC));

    public static final EntityDataSerializer<ActiveAltarRecipe.AdditionalInput> ALTAR_INPUT_REFERENCE =
            register("altar_input_reference", EntityDataSerializer.forValueType(ActiveAltarRecipe.AdditionalInput.STREAM_CODEC));

    private static <T> EntityDataSerializer<T> register(String name, EntityDataSerializer<T> value) {
        EntityDataSerializers.registerSerializer(value);
        return value;
    }
}
