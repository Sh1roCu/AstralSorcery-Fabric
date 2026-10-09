/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.datagen.data.damage;

import hellfirepvp.astralsorcery.common.lib.constants.TagsAS;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.impl.tag.convention.v2.TagRegistration;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

import java.util.concurrent.CompletableFuture;

import static hellfirepvp.astralsorcery.common.lib.DamageTypesAS.STELLAR;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: AstralDamageTypeTagProvider
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class AstralDamageTypeTagProvider extends FabricTagProvider<DamageType> {

    public static final TagRegistration<DamageType> DAMAGE_TYPE_TAG = new TagRegistration<>(Registries.DAMAGE_TYPE);

    public static final TagKey<DamageType> IS_MAGIC = DAMAGE_TYPE_TAG.registerC("is_magic");

    public AstralDamageTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.DAMAGE_TYPE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR)
                .addOptional(STELLAR.idLocation());
        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_RESISTANCE)
                .addOptional(STELLAR.idLocation());
        this.getOrCreateTagBuilder(DamageTypeTags.WITCH_RESISTANT_TO)
                .addOptional(STELLAR.idLocation());
        this.getOrCreateTagBuilder(IS_MAGIC)
                .addOptional(STELLAR.idLocation());

        this.getOrCreateTagBuilder(TagsAS.DamageTypes.IS_ELEMENTAL)
                .forceAddTag(DamageTypeTags.IS_FIRE)
                .forceAddTag(DamageTypeTags.IS_FREEZING)
                .forceAddTag(DamageTypeTags.IS_LIGHTNING)
                .forceAddTag(DamageTypeTags.IS_DROWNING);
    }
}
