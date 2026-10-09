/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.perk.tree.perk.key;

import cn.sh1rocu.astralsorcery.api.event.SimpleHarvestCheckCallback;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import hellfirepvp.astralsorcery.common.lib.types.PerkDataTypesAS;
import hellfirepvp.astralsorcery.common.perk.convert.PerkAttributeConverter;
import hellfirepvp.astralsorcery.common.perk.modifier.PerkAttributeModifier;
import hellfirepvp.astralsorcery.common.perk.tree.PerkCategory;
import hellfirepvp.astralsorcery.common.perk.tree.PerkType;
import hellfirepvp.astralsorcery.common.perk.tree.perk.KeyPerk;
import hellfirepvp.astralsorcery.common.perk.tree.requirement.PerkRequirement;
import hellfirepvp.astralsorcery.common.research.PlayerProgress;
import hellfirepvp.astralsorcery.common.research.ResearchManager;
import hellfirepvp.astralsorcery.common.util.SidedHelper;
import net.fabricmc.api.EnvType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: KeyPerkAllToolTypes
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class KeyPerkAllToolTypes extends KeyPerk {

    public static final MapCodec<KeyPerkAllToolTypes> CODEC = RecordCodecBuilder.mapCodec(inst -> perkModifierFields(inst).apply(inst, KeyPerkAllToolTypes::new));
    public static final PerkType<KeyPerkAllToolTypes> TYPE =
            PerkType.of(KeyPerkAllToolTypes.CODEC, PerkDataTypesAS.DEFAULT_DATA, KeyPerkAllToolTypes::new);
    private static final Tool EMPTY = new Tool(List.of(), 1F, 1);

    private KeyPerkAllToolTypes(ResourceLocation key, float x, float y) {
        this(key, defaultNameKey(key), x, y, PerkCategory.MAJOR, Collections.emptySet(), Collections.emptySet(), Collections.emptySet());
    }

    protected KeyPerkAllToolTypes(ResourceLocation key, String nameKey, float x, float y, PerkCategory category, Collection<PerkRequirement> requirements, Collection<PerkAttributeConverter> converters, Collection<PerkAttributeModifier> modifiers) {
        super(key, nameKey, x, y, category, requirements, converters, modifiers);
    }

    @Override
    protected void attachEventListeners() {
        super.attachEventListeners();
        // PlayerEvent.HarvestCheck.EVENT.register(this::onHarvestCheck);
        SimpleHarvestCheckCallback.EVENT.register(this::onHarvestCheck);
    }

    @Override
    public PerkType<?> getType() {
        return TYPE;
    }

    private boolean onHarvestCheck(boolean canHarvest, Player player, BlockState state) {
        if (canHarvest) return true;

        boolean[] result = {canHarvest};

        EnvType side = this.getSide(player);
        PlayerProgress progress = ResearchManager.getProgress(player, side);
        if (progress.getPerkData().hasPerkEffect(this)) {
            wrapMineableStack(player.getInventory().getSelected()).ifPresent(checkTool -> {
                result[0] = checkTool.isCorrectToolForDrops(state);
            });
        }

        return result[0];
    }

    public static void adjustHarvestSpeed(Inventory playerInv, BlockState state, CallbackInfoReturnable<Float> returnOvr) {
        Player thisPlayer = playerInv.player;
        EnvType side = SidedHelper.getSide(thisPlayer);
        PlayerProgress progress = ResearchManager.getProgress(thisPlayer, side);
        if (progress.getPerkData().hasPerkEffect(perk -> perk instanceof KeyPerkAllToolTypes)) {
            wrapMineableStack(playerInv.getSelected()).ifPresent(checkTool -> {
                float existingSpeed = returnOvr.getReturnValueF();
                float newSpeed = checkTool.getDestroySpeed(state);
                if (newSpeed > existingSpeed) {
                    returnOvr.setReturnValue(newSpeed);
                }
            });
        }
    }

    private static Optional<ItemStack> wrapMineableStack(ItemStack tool) {
        if (tool.isEmpty()) return Optional.empty();
        if (!tool.has(DataComponents.TOOL)) return Optional.empty();
        // if (!tool.canPerformAction(ItemAbilities.PICKAXE_DIG)) return Optional.empty();
        if (!(tool.getItem() instanceof PickaxeItem || tool.is(ItemTags.PICKAXES))) return Optional.empty();

        Tool toolCmp = tool.getOrDefault(DataComponents.TOOL, EMPTY);
        float fastestSpeed = (float) toolCmp.rules().stream()
                .filter(rule -> rule.speed().isPresent())
                .mapToDouble(rule -> rule.speed().get())
                .max().orElse(1.0D);
        List<Tool.Rule> extendedRules = new ArrayList<>(toolCmp.rules());
        extendedRules.add(Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_PICKAXE, fastestSpeed));
        extendedRules.add(Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_AXE, fastestSpeed));
        extendedRules.add(Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_SHOVEL, fastestSpeed));
        extendedRules.add(Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_HOE, fastestSpeed));
        Tool extendedTool = new Tool(extendedRules, toolCmp.defaultMiningSpeed(), toolCmp.damagePerBlock());

        ItemStack checkStack = tool.copy();
        checkStack.set(DataComponents.TOOL, extendedTool);
        return Optional.of(checkStack);
    }
}
