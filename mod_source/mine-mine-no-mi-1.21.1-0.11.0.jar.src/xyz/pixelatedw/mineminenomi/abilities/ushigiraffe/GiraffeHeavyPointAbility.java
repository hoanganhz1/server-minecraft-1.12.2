package xyz.pixelatedw.mineminenomi.abilities.ushigiraffe;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IAbilityGoalFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.MorphAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.MorphAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GiraffeHeavyPointAbility extends MorphAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<GiraffeHeavyPointAbility>> INSTANCE;
	private static final AttributeModifier SPEED_MODIFIER;
	private static final AttributeModifier JUMP_BOOST_MODIFIER;
	private static final AttributeModifier STRENGTH_MODIFIER;
	private static final AttributeModifier ATTACK_SPEED_MODIFIER;
	private static final AttributeModifier REACH_MODIFIER;
	private static final AttributeModifier ARMOR_MODIFIER;
	private static final AttributeModifier TOUGHNESS_MODIFIER;
	private static final AttributeModifier FALL_RESISTANCE_MODIFIER;

	public GiraffeHeavyPointAbility(AbilityCore<GiraffeHeavyPointAbility> var1) {
		super((AbilityCore)(var1));
		this.statsComponent.addAttributeModifier(Attributes.MOVEMENT_SPEED,SPEED_MODIFIER);
		this.statsComponent.addAttributeModifier(ModAttributes.PUNCH_DAMAGE,STRENGTH_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.ATTACK_SPEED,ATTACK_SPEED_MODIFIER);
		this.statsComponent.addAttributeModifier(ModAttributes.JUMP_HEIGHT,JUMP_BOOST_MODIFIER);
		this.statsComponent.addAttributeModifier(ModAttributes.FALL_RESISTANCE,JUMP_BOOST_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE,REACH_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE,REACH_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.ARMOR,ARMOR_MODIFIER);
		this.statsComponent.addAttributeModifier(ModAttributes.TOUGHNESS,TOUGHNESS_MODIFIER);
		this.statsComponent.addAttributeModifier(ModAttributes.FALL_RESISTANCE,FALL_RESISTANCE_MODIFIER);
	}
	public MorphInfo getTransformation() {
		return ((MorphInfo)(ModMorphs.GIRAFFE_HEAVY.get()));
	}
	public static AbilityWrapperGoal<Mob, GiraffeHeavyPointAbility> createWrapper(Mob var0, AbilityCore<GiraffeHeavyPointAbility> var1) {
		return new MorphAbilityWrapperGoal(var0,var1).setMinDistance(0F).setMaxDistance(15F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Transforms the user into a half-giraffe hybrid, which focuses on strength."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityCore$IAbilityGoalFactory[] lv1_4 = new AbilityCore$IAbilityGoalFactory[1];
			AbilityCore$IAbilityGoalFactory lv1_5 = GiraffeHeavyPointAbility::createWrapper;
			lv1_4[0] = lv1_5;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GiraffeHeavyPointAbility::new)))).addDescriptionLine(lv1_3).addGoalWrapper(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("giraffe_heavy_point","Giraffe Heavy Point",var0));
		SPEED_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_MOVEMENT_SPEED_UUID,0.30000001192092896D,AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
		JUMP_BOOST_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_JUMP_BOOST_UUID,1D,AttributeModifier.Operation.ADD_VALUE);
		STRENGTH_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_STRENGTH_UUID,1D,AttributeModifier.Operation.ADD_VALUE);
		ATTACK_SPEED_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_ATTACK_SPEED_UUID,0.15000000596046448D,AttributeModifier.Operation.ADD_VALUE);
		REACH_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_ATTACK_REACH_UUID,2D,AttributeModifier.Operation.ADD_VALUE);
		ARMOR_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_ARMOR_UUID,10D,AttributeModifier.Operation.ADD_VALUE);
		TOUGHNESS_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_TOUGHNESS_UUID,5D,AttributeModifier.Operation.ADD_VALUE);
		FALL_RESISTANCE_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_FALL_RESISTANCE_UUID,0.75D,AttributeModifier.Operation.ADD_VALUE);
	}
}