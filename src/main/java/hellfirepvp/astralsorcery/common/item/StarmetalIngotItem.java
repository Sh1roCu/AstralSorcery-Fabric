/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.item;

import cn.sh1rocu.astralsorcery.api.extension.ICustomEntityItem;
import hellfirepvp.astralsorcery.common.entity.ItemEntityReplacement;
import hellfirepvp.astralsorcery.common.item.base.ItemCustom;
import hellfirepvp.astralsorcery.common.lib.EntitiesAS;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: StarmetalIngotItem
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class StarmetalIngotItem extends ItemCustom implements ICustomEntityItem {

    public StarmetalIngotItem() {
        super(new Properties());
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Override
    @Nullable
    public Entity createEntity(Level level, Entity location, ItemStack stack) {
        if (location instanceof ItemEntity itemEntity) {
            return ItemEntityReplacement.replace(EntitiesAS.ITEM_STARMETAL, itemEntity);
        }
        return null;
    }
}
