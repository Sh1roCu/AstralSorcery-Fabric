/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.structure;

import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import hellfirepvp.observerlib.api.util.StructureBlockArray;
import net.minecraft.world.level.block.state.BlockState;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: PatternCelestialGateway
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class PatternCelestialGateway extends StructureBlockArray {

    public PatternCelestialGateway() {
        this.addBlock(BlocksAS.CELESTIAL_GATEWAY, 0, 0, 0);

        this.addBlockCube(BlocksAS.MARBLE_ARCH.defaultBlockState(), -3, -1, -3, 3, -1, 3);
        this.addBlockCube(BlocksAS.SOOTY_MARBLE_RAW.defaultBlockState(), -2, -1, -2, 2, -1, 2);

        BlockState runed = BlocksAS.MARBLE_RUNED.defaultBlockState();
        this.addBlock(runed, -3, -1, -3);
        this.addBlock(runed,  3, -1, -3);
        this.addBlock(runed,  3, -1,  3);
        this.addBlock(runed, -3, -1,  3);

        BlockState engraved = BlocksAS.MARBLE_ENGRAVED.defaultBlockState();
        this.addBlock(engraved, -3, 0, -3);
        this.addBlock(engraved,  3, 0, -3);
        this.addBlock(engraved,  3, 0,  3);
        this.addBlock(engraved, -3, 0,  3);
    }
}
