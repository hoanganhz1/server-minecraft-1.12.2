package xyz.pixelatedw.mineminenomi.abilities.bari;

import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.WallAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule$Builder;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;

public class BarrierAbility extends WallAbility {
	// Fields
	private static final int HOLD_TIME = 600;
	private static final int MIN_COOLDOWN = 100;
	private static final int MAX_COOLDOWN = 400;
	public static final BlockProtectionRule GRIEF_RULE;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BarrierAbility>> INSTANCE;

	public BarrierAbility(AbilityCore<BarrierAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent.addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		float var3 = (100F) + ((this.continuousComponent.getContinueTime()) / (2F));
		this.cooldownComponent.startCooldown(var1,var3);
	}
	public int getHoldTime() {
		return 600;
	}
	public int getThickness() {
		return 1;
	}
	public int getHeight() {
		return 6;
	}
	public int getLength() {
		return 3;
	}
	public BlockState getWallBlock() {
		return ((Block)(ModBlocks.BARRIER.get())).defaultBlockState();
	}
	public BlockProtectionRule getGriefingRule() {
		return GRIEF_RULE;
	}
	public boolean stopAfterUse() {
		return false;
	}
	static  {
		BlockProtectionRule[] var0 = new BlockProtectionRule[]{DefaultProtectionRules.AIR};
		TagKey[] var1 = new TagKey[]{ModTags$Blocks.BLOCK_PROT_FOLIAGE};
		GRIEF_RULE = new BlockProtectionRule$Builder(var0).addApprovedTags(var1).setBypassGriefFlag().build();
		AbilityRegistryEntry var2 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("The user creates an impenetrable wall in front of themselves, which shields them from attacks."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(100F,400F),ContinuousComponent.getTooltip(600F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BarrierAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("barrier","Barrier",var2));
	}
}