package xyz.pixelatedw.mineminenomi.abilities.pero;

import java.awt.Color;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class CandyArmorAbility extends Ability {
	// Fields
	private static final AbilityOverlay OVERLAY = new AbilityOverlay$Builder().setTexture(ModResources.CANDY_ARMOR).setColor(WyHelper.hexToRGB("#FFFFFF99")).build();
	private static final int HOLD_TIME = 300;
	private static final int MIN_COOLDOWN = 40;
	private static final float MAX_COOLDOWN = 200F;
	private static final ResourceLocation MINING_SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.candy_armor.mining_speed");
	private static final ResourceLocation SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.candy_armor.speed");
	private static final ResourceLocation ARMOR_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.candy_armor.armor");
	private static final ResourceLocation ARMOR_TOUGHNESS_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.candy_armor.armor_toughness");
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<CandyArmorAbility>> INSTANCE;
	private static final AttributeModifier MINING_SPEED_MODIFIER;
	private static final AttributeModifier MOVEMENT_SPEED_MODIFIER;
	private static final AttributeModifier ARMOR_MODIFIER;
	private static final AttributeModifier ARMOR_TOUGHNESS_MODIFIER;
	private final ContinuousComponent continuousComponent;
	private final ChangeStatsComponent changeStatsComponent;
	private final SkinOverlayComponent skinOverlayComponent;

	public CandyArmorAbility(AbilityCore<CandyArmorAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.changeStatsComponent = new ChangeStatsComponent((IAbility)(this));
		this.skinOverlayComponent = new SkinOverlayComponent((IAbility)(this),OVERLAY,new AbilityOverlay[0]);
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.changeStatsComponent,this.skinOverlayComponent};
		this.addComponents(var2);
		this.changeStatsComponent.addAttributeModifier(ModAttributes.MINING_SPEED,MINING_SPEED_MODIFIER);
		this.changeStatsComponent.addAttributeModifier(Attributes.MOVEMENT_SPEED,MOVEMENT_SPEED_MODIFIER);
		this.changeStatsComponent.addAttributeModifier(Attributes.ARMOR,ARMOR_MODIFIER);
		this.changeStatsComponent.addAttributeModifier(Attributes.ARMOR_TOUGHNESS,ARMOR_TOUGHNESS_MODIFIER);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,300F);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.changeStatsComponent.applyModifiers(var1);
		this.skinOverlayComponent.showAll(var1);
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.changeStatsComponent.removeModifiers(var1);
		this.skinOverlayComponent.hideAll(var1);
		float var3 = Math.max(40F,(this.continuousComponent.getContinueTime()) / (1.5F));
		this.cooldownComponent.startCooldown(var1,var3);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Coats the user with a hard candy armor, which boosts defense, but reduces mobility."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(40F,200F),ContinuousComponent.getTooltip(300F),ChangeStatsComponent.getTooltip()};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(CandyArmorAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("candy_armor","Candy Armor",var0));
		MINING_SPEED_MODIFIER = new AttributeModifier(MINING_SPEED_MODIFIER_ID,-0.05000000074505806D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		MOVEMENT_SPEED_MODIFIER = new AttributeModifier(SPEED_MODIFIER_ID,-0.05000000074505806D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		ARMOR_MODIFIER = new AttributeModifier(ARMOR_MODIFIER_ID,18D,AttributeModifier.Operation.ADD_VALUE);
		ARMOR_TOUGHNESS_MODIFIER = new AttributeModifier(ARMOR_TOUGHNESS_MODIFIER_ID,10D,AttributeModifier.Operation.ADD_VALUE);
	}
}