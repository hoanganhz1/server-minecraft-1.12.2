package xyz.pixelatedw.mineminenomi.abilities.kira;

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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$OverlayPart;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class DiamondBodyAbility extends Ability {
	// Fields
	private static final int HOLD_TIME = 2400;
	private static final int MIN_COOLDOWN = 40;
	private static final int MAX_COOLDOWN = 840;
	private static final AbilityOverlay OVERLAY = new AbilityOverlay$Builder().setOverlayPart(AbilityOverlay$OverlayPart.BODY).setTexture(ModResources.DIAMOND_BODY).setColor(new Color(255,255,255,165)).build();
	public static final ResourceLocation DIAMOND_BODY_ARMOR_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.diamond_body.armor");
	public static final ResourceLocation DIAMOND_BODY_ARMOR_TOUGHNESS_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.diamond_body.armor_toughness");
	public static final ResourceLocation DIAMOND_BODY_ATTACK_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.diamond_body.attack");
	public static final ResourceLocation DIAMOND_BODY_TOUGHNESS_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.diamond_body.toughness");
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<DiamondBodyAbility>> INSTANCE;
	private static final AttributeModifier ARMOR_MODIFIER;
	private static final AttributeModifier ARMOR_TOUGHNESS_MODIFIER;
	private static final AttributeModifier ATTACK_MODIFIER;
	private static final AttributeModifier TOUGHNESS_MODIFIER;
	private final ContinuousComponent continuousComponent;
	private final SkinOverlayComponent skinOverlayComponent;
	private final ChangeStatsComponent changeStatsComponent;

	public DiamondBodyAbility(AbilityCore<DiamondBodyAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.skinOverlayComponent = new SkinOverlayComponent((IAbility)(this),OVERLAY,new AbilityOverlay[0]);
		this.changeStatsComponent = new ChangeStatsComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.skinOverlayComponent,this.changeStatsComponent};
		this.addComponents(var2);
		this.changeStatsComponent.addAttributeModifier(Attributes.ARMOR,ARMOR_MODIFIER);
		this.changeStatsComponent.addAttributeModifier(Attributes.ARMOR_TOUGHNESS,ARMOR_TOUGHNESS_MODIFIER);
		this.changeStatsComponent.addAttributeModifier(ModAttributes.PUNCH_DAMAGE,ATTACK_MODIFIER);
		this.changeStatsComponent.addAttributeModifier(ModAttributes.TOUGHNESS,TOUGHNESS_MODIFIER);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,2400F);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.changeStatsComponent.applyModifiers(var1);
		this.skinOverlayComponent.showAll(var1);
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.changeStatsComponent.removeModifiers(var1);
		this.skinOverlayComponent.hideAll(var1);
		float var3 = (40F) + ((this.continuousComponent.getContinueTime()) / (3F));
		this.cooldownComponent.startCooldown(var1,var3);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Allows the user's body to become diamond, gaining high strength and defence."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(40F,840F),ContinuousComponent.getTooltip(2400F),ChangeStatsComponent.getTooltip()};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DiamondBodyAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("diamond_body","Diamond Body",var0));
		ARMOR_MODIFIER = new AttributeModifier(DIAMOND_BODY_ARMOR_ID,20D,AttributeModifier.Operation.ADD_VALUE);
		ARMOR_TOUGHNESS_MODIFIER = new AttributeModifier(DIAMOND_BODY_ARMOR_TOUGHNESS_ID,12D,AttributeModifier.Operation.ADD_VALUE);
		ATTACK_MODIFIER = new AttributeModifier(DIAMOND_BODY_ATTACK_ID,6D,AttributeModifier.Operation.ADD_VALUE);
		TOUGHNESS_MODIFIER = new AttributeModifier(DIAMOND_BODY_TOUGHNESS_ID,8D,AttributeModifier.Operation.ADD_VALUE);
	}
}