/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.sound;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: CategorizedSoundEvent
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public record CategorizedSoundEvent(SoundEvent sound, SoundSource category) {

    public ResourceLocation getId() {
        return BuiltInRegistries.SOUND_EVENT.getKey(this.sound());
    }
}
