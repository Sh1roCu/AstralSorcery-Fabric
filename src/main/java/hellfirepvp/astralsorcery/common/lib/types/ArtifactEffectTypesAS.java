/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib.types;

import com.mojang.serialization.MapCodec;
import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.artifact.ArtifactEffect;
import hellfirepvp.astralsorcery.common.artifact.effect.*;
import hellfirepvp.astralsorcery.common.lib.RegistriesAS;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: ArtifactEffectTypesAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class ArtifactEffectTypesAS {

    public static void init() {

    }

    public static final ArtifactEffect.DeferredType<ArtifactEffectEffects> EFFECTS =
            register("effects", ArtifactEffectEffects.CODEC, ArtifactEffectEffects.STREAM_CODEC);
    public static final ArtifactEffect.DeferredType<ArtifactEffectExplode> EXPLODE =
            register("explode", ArtifactEffectExplode.CODEC, ArtifactEffectExplode.STREAM_CODEC);
    public static final ArtifactEffect.DeferredType<ArtifactEffectPlaceBlocks> PLACE_BLOCKS =
            register("place_blocks", ArtifactEffectPlaceBlocks.CODEC, ArtifactEffectPlaceBlocks.STREAM_CODEC);
    public static final ArtifactEffect.DeferredType<ArtifactEffectRandomMove> RANDOM_MOVE =
            register("random_move", ArtifactEffectRandomMove.CODEC, ArtifactEffectRandomMove.STREAM_CODEC);
    public static final ArtifactEffect.DeferredType<ArtifactEffectRandomTeleport> RANDOM_TELEPORT =
            register("random_teleport", ArtifactEffectRandomTeleport.CODEC, ArtifactEffectRandomTeleport.STREAM_CODEC);
    public static final ArtifactEffect.DeferredType<ArtifactEffectSpawnMobs> SPAWN_MOBS =
            register("spawn_mobs", ArtifactEffectSpawnMobs.CODEC, ArtifactEffectSpawnMobs.STREAM_CODEC);
    public static final ArtifactEffect.DeferredType<ArtifactEffectSpawnNaturalMobs> SPAWN_NATURAL_MOBS =
            register("spawn_natural_mobs", ArtifactEffectSpawnNaturalMobs.CODEC, ArtifactEffectSpawnNaturalMobs.STREAM_CODEC);

    private static <T extends ArtifactEffect> ArtifactEffect.DeferredType<T> register(String name,
                                                                                      MapCodec<T> codec,
                                                                                      StreamCodec<RegistryFriendlyByteBuf, T> streamCodec) {
        var effect = Registry.register(RegistriesAS.REGISTRY_ARTIFACT_EFFECT_TYPES, AstralSorcery.key(name),
                new ArtifactEffect.Type<>(codec, streamCodec));
        return new ArtifactEffect.DeferredType<>(effect);
    }
}
