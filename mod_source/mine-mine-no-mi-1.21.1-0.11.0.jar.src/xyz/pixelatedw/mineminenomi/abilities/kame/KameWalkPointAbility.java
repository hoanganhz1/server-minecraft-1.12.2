package xyz.pixelatedw.mineminenomi.abilities.kame;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.NeoForgeMod;
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

public class KameWalkPointAbility extends MorphAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<KameWalkPointAbility>> INSTANCE;
	private static final AttributeModifier SPEED_MODIFIER;
	private static final AttributeModifier ARMOR_MODIFIER;
	private static final AttributeModifier KNOCKBACK_RESISTANCE_MODIFIER;
	private static final AttributeModifier JUMP_BOOST_MODIFIER;
	private static final AttributeModifier WATER_SPEED_MODIFIER;
	private static final AttributeModifier TOUGHNESS_MODIFIER;

	public KameWalkPointAbility(AbilityCore<KameWalkPointAbility> var1) {
		super((AbilityCore)(var1));
		this.statsComponent.addAttributeModifier(Attributes.MOVEMENT_SPEED,SPEED_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.ARMOR,ARMOR_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE,KNOCKBACK_RESISTANCE_MODIFIER);
		this.statsComponent.addAttributeModifier(ModAttributes.JUMP_HEIGHT,JUMP_BOOST_MODIFIER);
		this.statsComponent.addAttributeModifier(NeoForgeMod.SWIM_SPEED,WATER_SPEED_MODIFIER);
		this.statsComponent.addAttributeModifier(ModAttributes.TOUGHNESS,TOUGHNESS_MODIFIER);
	}
	public MorphInfo getTransformation() {
		return ((MorphInfo)(ModMorphs.KAME_WALK.get()));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Transforms the user into a turtle hybrid, which focuses on defense."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KameWalkPointAbility::new)))).addDescriptionLine(lv1_3).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("kame_walk_point","Kame Walk Point",var0));
		SPEED_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_MOVEMENT_SPEED_UUID,-0.4000000059604645D,AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
		ARMOR_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_ARMOR_UUID,15D,AttributeModifier.Operation.ADD_VALUE);
		KNOCKBACK_RESISTANCE_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_KNOCKBACK_RESISTANCE_UUID,1D,AttributeModifier.Operation.ADD_VALUE);
		JUMP_BOOST_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_JUMP_BOOST_UUID,-0.25D,AttributeModifier.Operation.ADD_VALUE);
		WATER_SPEED_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_SWIM_SPEED_UUID,1D,AttributeModifier.Operation.ADD_VALUE);
		TOUGHNESS_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_TOUGHNESS_UUID,10D,AttributeModifier.Operation.ADD_VALUE);
	}
}