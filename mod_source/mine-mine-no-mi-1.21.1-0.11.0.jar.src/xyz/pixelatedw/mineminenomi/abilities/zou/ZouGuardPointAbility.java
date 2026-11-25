package xyz.pixelatedw.mineminenomi.abilities.zou;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.MorphAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class ZouGuardPointAbility extends MorphAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<ZouGuardPointAbility>> INSTANCE;
	private static final AttributeModifier SPEED_MODIFIER;
	private static final AttributeModifier HEALTH_MODIFIER;
	private static final AttributeModifier ARMOR_MODIFIER;
	private static final AttributeModifier STRENGTH_MODIFIER;
	private static final AttributeModifier ATTACK_SPEED_MODIFIER;
	private static final AttributeModifier REACH_MODIFIER;
	private static final AttributeModifier KNOCKBACK_RESISTANCE;
	private static final AttributeModifier STEP_HEIGHT;
	private static final AttributeModifier FALL_RESISTANCE_MODIFIER;
	private static final AttributeModifier TOUGHNESS_MODIFIER;

	public ZouGuardPointAbility(AbilityCore<ZouGuardPointAbility> var1) {
		super((AbilityCore)(var1));
		this.statsComponent.addAttributeModifier(Attributes.MOVEMENT_SPEED,SPEED_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.ARMOR,ARMOR_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.MAX_HEALTH,HEALTH_MODIFIER);
		this.statsComponent.addAttributeModifier(ModAttributes.PUNCH_DAMAGE,STRENGTH_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.ATTACK_SPEED,ATTACK_SPEED_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE,REACH_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE,REACH_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE,KNOCKBACK_RESISTANCE);
		this.statsComponent.addAttributeModifier(Attributes.STEP_HEIGHT,STEP_HEIGHT);
		this.statsComponent.addAttributeModifier(ModAttributes.FALL_RESISTANCE,FALL_RESISTANCE_MODIFIER);
		this.statsComponent.addAttributeModifier(ModAttributes.TOUGHNESS,TOUGHNESS_MODIFIER);
	}
	public MorphInfo getTransformation() {
		return ((MorphInfo)(ModMorphs.ZOU_GUARD.get()));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Transforms the user into an elephant, which focuses on defense."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(ZouGuardPointAbility::new)))).addDescriptionLine(lv1_3).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("zou_guard_point","Zou Guard Point",var0));
		SPEED_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_MOVEMENT_SPEED_UUID,-0.25D,AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
		HEALTH_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_HEALTH_UUID,15D,AttributeModifier.Operation.ADD_VALUE);
		ARMOR_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_ARMOR_UUID,7D,AttributeModifier.Operation.ADD_VALUE);
		STRENGTH_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_STRENGTH_UUID,5D,AttributeModifier.Operation.ADD_VALUE);
		ATTACK_SPEED_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_ATTACK_SPEED_UUID,-0.25D,AttributeModifier.Operation.ADD_VALUE);
		REACH_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_ATTACK_REACH_UUID,0.8D,AttributeModifier.Operation.ADD_VALUE);
		KNOCKBACK_RESISTANCE = new AttributeModifier(AttributeHelper.MORPH_KNOCKBACK_RESISTANCE_UUID,1D,AttributeModifier.Operation.ADD_VALUE);
		STEP_HEIGHT = new AttributeModifier(AttributeHelper.MORPH_STEP_HEIGHT_UUID,1D,AttributeModifier.Operation.ADD_VALUE);
		FALL_RESISTANCE_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_FALL_RESISTANCE_UUID,10D,AttributeModifier.Operation.ADD_VALUE);
		TOUGHNESS_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_TOUGHNESS_UUID,4D,AttributeModifier.Operation.ADD_VALUE);
	}
}