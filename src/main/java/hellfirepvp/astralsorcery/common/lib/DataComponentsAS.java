/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.component.*;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;

import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: DataComponentsAS
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class DataComponentsAS {

    public static void init() {

    }

    public static DataComponentType<CrystalAttributesComponent> CRYSTAL_ATTRIBUTES =
            register("crystal_attributes", () -> DataComponentType.<CrystalAttributesComponent>builder()
                    .persistent(CrystalAttributesComponent.CODEC)
                    .networkSynchronized(CrystalAttributesComponent.STREAM_CODEC)
                    .build());

    public static DataComponentType<AstrolabeAngleComponent> ASTROLABE_ANGLE =
            register("astrolabe_angle", () -> DataComponentType.<AstrolabeAngleComponent>builder()
                    .persistent(AstrolabeAngleComponent.CODEC)
                    .networkSynchronized(AstrolabeAngleComponent.STREAM_CODEC)
                    .build());

    public static DataComponentType<ArtifactComponent> ARTIFACT =
            register("artifact", () -> DataComponentType.<ArtifactComponent>builder()
                    .persistent(ArtifactComponent.CODEC)
                    .build());
    public static DataComponentType<ArtifactTypeComponent> ARTIFACT_TYPE =
            register("artifact_type", () -> DataComponentType.<ArtifactTypeComponent>builder()
                    .persistent(ArtifactTypeComponent.CODEC)
                    .networkSynchronized(ArtifactTypeComponent.STREAM_CODEC)
                    .build());

    public static DataComponentType<ConstellationPaperComponent> CONSTELLATION_PAPER =
            register("constellation_paper", () -> DataComponentType.<ConstellationPaperComponent>builder()
                    .persistent(ConstellationPaperComponent.CODEC)
                    .networkSynchronized(ConstellationPaperComponent.STREAM_CODEC)
                    .build());

    public static DataComponentType<AttunedConstellationComponent> ATTUNED_CONSTELLATION =
            register("attuned_constellation", () -> DataComponentType.<AttunedConstellationComponent>builder()
                    .persistent(AttunedConstellationComponent.CODEC)
                    .networkSynchronized(AttunedConstellationComponent.STREAM_CODEC)
                    .build());

    public static DataComponentType<LumenComponent> LUMEN =
            register("lumen", () -> DataComponentType.<LumenComponent>builder()
                    .persistent(LumenComponent.CODEC)
                    .networkSynchronized(LumenComponent.STREAM_CODEC)
                    .build());

    public static DataComponentType<StoredLumenComponent> STORED_LUMEN =
            register("stored_lumen", () -> DataComponentType.<StoredLumenComponent>builder()
                    .persistent(StoredLumenComponent.CODEC)
                    .networkSynchronized(StoredLumenComponent.STREAM_CODEC)
                    .build());

    public static DataComponentType<IdentifierComponent> IDENTIFIER =
            register("identifier", () -> DataComponentType.<IdentifierComponent>builder()
                    .persistent(IdentifierComponent.CODEC)
                    .networkSynchronized(IdentifierComponent.STREAM_CODEC)
                    .build());

    public static DataComponentType<ColorComponent> COLOR =
            register("color", () -> DataComponentType.<ColorComponent>builder()
                    .persistent(ColorComponent.CODEC)
                    .networkSynchronized(ColorComponent.STREAM_CODEC)
                    .build());

    public static DataComponentType<DynamicModifiersComponent> DYNAMIC_MODIFIERS =
            register("dynamic_modifiers", () -> DataComponentType.<DynamicModifiersComponent>builder()
                    .persistent(DynamicModifiersComponent.CODEC)
                    .networkSynchronized(DynamicModifiersComponent.STREAM_CODEC)
                    .build());

    public static DataComponentType<EnchantmentModifierComponent> ENCHANTMENT_MODIFIERS =
            register("enchantment_modifiers", () -> DataComponentType.<EnchantmentModifierComponent>builder()
                    .persistent(EnchantmentModifierComponent.CODEC)
                    .networkSynchronized(EnchantmentModifierComponent.STREAM_CODEC)
                    .build());

    public static DataComponentType<WeakPlayerReferenceComponent> WEAK_PLAYER_REFERENCE =
            register("weak_player_reference", () -> DataComponentType.<WeakPlayerReferenceComponent>builder()
                    .networkSynchronized(WeakPlayerReferenceComponent.STREAM_CODEC)
                    .build());

    public static DataComponentType<BlockStateStorageComponent> BLOCK_STATE_STORAGE =
            register("block_state_storage", () -> DataComponentType.<BlockStateStorageComponent>builder()
                    .persistent(BlockStateStorageComponent.CODEC)
                    .networkSynchronized(BlockStateStorageComponent.STREAM_CODEC)
                    .build());

    public static DataComponentType<StoredItemsComponent> STORED_ITEMS =
            register("stored_items", () -> DataComponentType.<StoredItemsComponent>builder()
                    .persistent(StoredItemsComponent.CODEC)
                    .networkSynchronized(StoredItemsComponent.STREAM_CODEC)
                    .build());

    public static DataComponentType<IntegerModeComponent> MODE =
            register("mode", () -> DataComponentType.<IntegerModeComponent>builder()
                    .persistent(IntegerModeComponent.CODEC)
                    .networkSynchronized(IntegerModeComponent.STREAM_CODEC)
                    .build());

    public static DataComponentType<FlagsComponent> FLAGS =
            register("flags", () -> DataComponentType.<FlagsComponent>builder()
                    .persistent(FlagsComponent.CODEC)
                    .networkSynchronized(FlagsComponent.STREAM_CODEC)
                    .build());

    public static DataComponentType<StoredPlayerProgressComponent> STORED_PLAYER_PROGRESS =
            register("stored_player_progress", () -> DataComponentType.<StoredPlayerProgressComponent>builder()
                    .persistent(StoredPlayerProgressComponent.CODEC)
                    .networkSynchronized(ByteBufCodecs.fromCodecWithRegistriesTrusted(StoredPlayerProgressComponent.CODEC))
                    .build());

    private static <T> DataComponentType<T> register(String name, Supplier<DataComponentType<T>> supplier) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, AstralSorcery.key(name), supplier.get());
    }
}
