/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.item;

import cn.sh1rocu.astralsorcery.api.extension.ICustomEntityItem;
import hellfirepvp.astralsorcery.EnumExtensions;
import hellfirepvp.astralsorcery.common.artifact.ArtifactType;
import hellfirepvp.astralsorcery.common.component.ArtifactComponent;
import hellfirepvp.astralsorcery.common.component.ArtifactTypeComponent;
import hellfirepvp.astralsorcery.common.entity.ItemEntityReplacement;
import hellfirepvp.astralsorcery.common.item.base.ItemCustom;
import hellfirepvp.astralsorcery.common.lib.*;
import hellfirepvp.astralsorcery.common.research.ResearchFlag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: ArtifactItem
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class ArtifactItem extends ItemCustom implements ICustomEntityItem {

    public ArtifactItem() {
        super(new Properties()
                .rarity(EnumExtensions.RARITY_RELIC)
                .stacksTo(1));
    }

    @Override
    public CreativeModeTab getCreativeTab() {
        return CreativeTabsAS.CREATIVE_TAB_AS_ARTIFACTS;
    }

    @Override
    public void fillCreativeTab(Consumer<ItemStack> tabItems) {
        RegistriesAS.REGISTRY_ARTIFACT_TYPES.forEach(type -> {
            tabItems.accept(create(type));
        });
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (level instanceof ServerLevel) {
            if (!stack.has(DataComponentsAS.ARTIFACT)) {
                stack.shrink(stack.getCount());
            }
        }
        if (entity instanceof ServerPlayer sPlayer) {
            ResearchFlag.HAS_OBTAINED_ARTIFACT.setIfAbsent(sPlayer);
        }
    }

    public static Optional<ItemStack> defaultStack() {
        return RegistriesAS.REGISTRY_ARTIFACT_TYPES.stream().findFirst().map(ArtifactItem::create);
    }

    public static ItemStack create(ArtifactType type) {
        ItemStack stack = ItemsAS.ARTIFACT.getDefaultInstance();
        stack.set(DataComponentsAS.ARTIFACT, ArtifactComponent.initialize(type));
        return stack;
    }

    public static ItemStack createForDisplay(ArtifactType type) {
        ItemStack stack = ItemsAS.ARTIFACT.getDefaultInstance();
        stack.set(DataComponentsAS.ARTIFACT, ArtifactComponent.initializeBlank(type));
        return stack;
    }

    public Optional<ItemStack> createShard(ItemStack stack) {
        if (stack.has(DataComponentsAS.ARTIFACT)) {
            ArtifactType type = stack.get(DataComponentsAS.ARTIFACT).artifactType();
            ItemStack shard = ItemsAS.ARTIFACT_SHARD.getDefaultInstance();
            shard.set(DataComponentsAS.ARTIFACT_TYPE, new ArtifactTypeComponent(type));
            return Optional.of(shard);
        }
        return Optional.empty();
    }

    @Override
    public Component getName(ItemStack stack) {
        if (stack.has(DataComponentsAS.ARTIFACT)) {
            ArtifactType type = stack.get(DataComponentsAS.ARTIFACT).artifactType();
            return Component.translatable("item.astralsorcery.artifact.format", type.getName());
        }
        return super.getName(stack);
    }

    @Override
    public boolean allowComponentsUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        return !oldStack.is(newStack.getItem());
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Override
    @Nullable
    public Entity createEntity(Level level, Entity location, ItemStack stack) {
        if (location instanceof ItemEntity itemEntity) {
            return ItemEntityReplacement.replace(EntitiesAS.ITEM_ARTIFACT, itemEntity);
        }
        return null;
    }
}
