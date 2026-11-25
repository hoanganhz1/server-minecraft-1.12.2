package xyz.pixelatedw.mineminenomi.abilities.gomu;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IVisibilityPredicate;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.MorphAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GomuGomuNoGigantAbility extends MorphAbility {
	// Fields
	private static final int HOLD_TIME = 1200;
	private static final int MIN_COOLDOWN = 100;
	private static final int MAX_COOLDOWN = 1200;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<GomuGomuNoGigantAbility>> INSTANCE;
	private static final AttributeModifier SPEED_MODIFIER;
	private static final AttributeModifier JUMP_MODIFIER;
	private static final AttributeModifier ARMOR_MODIFIER;
	private static final AttributeModifier STRENGTH_MODIFIER;
	private static final AttributeModifier REACH_MODIFIER;
	private static final AttributeModifier STEP_HEIGHT;
	private static final AttributeModifier KNOCKBACK_RESISTANCE;
	private static final AttributeModifier FALL_RESISTANCE_MODIFIER;
	private static final AttributeModifier TOUGHNESS_MODIFIER;

	public GomuGomuNoGigantAbility(AbilityCore<GomuGomuNoGigantAbility> var1) {
		super((AbilityCore)(var1));
		this.statsComponent.addAttributeModifier(Attributes.MOVEMENT_SPEED,SPEED_MODIFIER);
		this.statsComponent.addAttributeModifier(ModAttributes.JUMP_HEIGHT,JUMP_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.ARMOR,ARMOR_MODIFIER);
		this.statsComponent.addAttributeModifier(ModAttributes.PUNCH_DAMAGE,STRENGTH_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE,REACH_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE,REACH_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.STEP_HEIGHT,STEP_HEIGHT);
		this.statsComponent.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE,KNOCKBACK_RESISTANCE);
		this.statsComponent.addAttributeModifier(ModAttributes.FALL_RESISTANCE,FALL_RESISTANCE_MODIFIER);
		this.statsComponent.addAttributeModifier(ModAttributes.TOUGHNESS,TOUGHNESS_MODIFIER);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(GomuHelper::requiresGearFifth))));
		this.addContinueUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(GomuHelper::requiresGearFifth))));
		this.poolComponent.removeFromPool(ModAbilityPools.MORPHS);
		this.continuousComponent.addEndEvent(100,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		float var3 = Math.max(100F,this.continuousComponent.getContinueTime());
		this.cooldownComponent.startCooldown(var1,var3);
	}
	public MorphInfo getTransformation() {
		return ((MorphInfo)(ModMorphs.GOMU_GIGANT.get()));
	}
	public float getContinuityHoldTime() {
		return 1200F;
	}
	private static boolean canUnlock(LivingEntity var0) {
		return ((Boolean)(DevilFruitData.get(var0).map((Function)(((Function<DevilFruitData, Boolean>)(DevilFruitData::hasAwakenedFruit)))).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Allows the user to increase their size to that of a giant."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(100F,1200F),ContinuousComponent.getTooltip(1200F),ChangeStatsComponent.getTooltip()};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GomuGomuNoGigantAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(GomuGomuNoGigantAbility::canUnlock)))).setHidden((AbilityCore$IVisibilityPredicate)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IVisibilityPredicate)(ServerConfig::hasAwakeningsEnabled)))).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("gomu_gomu_no_gigant","Gomu Gomu no Gigant",var0));
		SPEED_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_MOVEMENT_SPEED_UUID,1.0199999809265137D,AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
		JUMP_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_JUMP_BOOST_UUID,2D,AttributeModifier.Operation.ADD_VALUE);
		ARMOR_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_ARMOR_UUID,5D,AttributeModifier.Operation.ADD_VALUE);
		STRENGTH_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_STRENGTH_UUID,3D,AttributeModifier.Operation.ADD_VALUE);
		REACH_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_ATTACK_REACH_UUID,5D,AttributeModifier.Operation.ADD_VALUE);
		STEP_HEIGHT = new AttributeModifier(AttributeHelper.MORPH_STEP_HEIGHT_UUID,1.5D,AttributeModifier.Operation.ADD_VALUE);
		KNOCKBACK_RESISTANCE = new AttributeModifier(AttributeHelper.MORPH_KNOCKBACK_RESISTANCE_UUID,1D,AttributeModifier.Operation.ADD_VALUE);
		FALL_RESISTANCE_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_FALL_RESISTANCE_UUID,10D,AttributeModifier.Operation.ADD_VALUE);
		TOUGHNESS_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_TOUGHNESS_UUID,2D,AttributeModifier.Operation.ADD_VALUE);
	}
}