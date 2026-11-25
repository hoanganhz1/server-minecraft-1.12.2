package xyz.pixelatedw.mineminenomi.abilities.kage;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
import xyz.pixelatedw.mineminenomi.api.abilities.MorphAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class ShadowsAsgardAbility extends MorphAbility {
	// Fields
	private static final float HOLD_TIME = 1200F;
	private static final float MIN_COOLDOWN = 200F;
	private static final float MAX_COOLDOWN = 1400F;
	public static final int MAX_SHADOWS = 30;
	private static final float MIN_STAT = 0F;
	private static final float MAX_ARMOR_STAT = 10F;
	private static final float MAX_ATTACK_STAT = 10F;
	private static final float MAX_REACH_STAT = 2F;
	private static final float MAX_TOUGHNESS_STAT = 5F;
	private static final float MAX_STEP_ASSIST_STAT = 1F;
	private static final float MAX_KNOCKBACK_RES_STAT = 1F;
	private static final float MAX_HEALTH_STAT = 1F;
	private static final ResourceLocation ARMOR_MODIFIER_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.shadows_asgard.armor");
	private static final ResourceLocation ATTACK_MODIFIER_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.shadows_asgard.attack");
	private static final ResourceLocation REACH_MODIFIER_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.shadows_asgard.entity_reach");
	private static final ResourceLocation TOUGHNESS_MODIFIER_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.shadows_asgard.toughness");
	private static final ResourceLocation STEP_ASSIST_MODIFIER_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.shadows_asgard.step_assist");
	private static final ResourceLocation KNOCKBACK_RES_MODIFIER_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.shadows_asgard.knockback_resistance");
	private static final ResourceLocation MAX_HEALTH_MODIFIER_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.shadows_asgard.max_health");
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<ShadowsAsgardAbility>> INSTANCE;
	private final StackComponent stackComponent;
	private int shadows;

	public ShadowsAsgardAbility(AbilityCore<ShadowsAsgardAbility> var1) {
		super((AbilityCore)(var1));
		this.stackComponent = new StackComponent((IAbility)(this));
		this.shadows = 0;
		AbilityComponent[] var2 = new AbilityComponent[]{this.stackComponent};
		this.addComponents(var2);
		this.continuousComponent.addStartEvent(120,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::onStartContinuityEvent))));
		this.continuousComponent.addTickEvent(120,(ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onTickContinuityEvent))));
		this.continuousComponent.addEndEvent(120,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onEndContinuityEvent))));
	}
	private void onStartContinuityEvent(LivingEntity var1, IAbility var2) {
		this.shadows = 0;
		this.morphComponent.updateMorphSize(var1);
	}
	private void onTickContinuityEvent(LivingEntity var1, IAbility var2) {
		if (KageHelper.hasEnoughShadows(var1,this,1).isFail()){
			this.continuousComponent.stopContinuity(var1);
			return;
		}else{
			if ((((this.continuousComponent.getContinueTime()) % (10F)) == (0F)) && ((this.shadows) < (30))){
				this.statsComponent.removeModifiers(var1);
				this.statsComponent.clearAttributeModifiers();
				Iterator var3 = this.getAttributes().entries().iterator();
				do{
					if (var3.hasNext()){
						Map.Entry var4 = ((Map.Entry)(var3.next()));
						this.statsComponent.addAttributeModifier(((Holder)(var4.getKey())),((AttributeModifier)(var4.getValue())));
						continue;
					}else{
						break;
					}
				} while (true);
				this.statsComponent.applyModifiers(var1);
				this.shadows = (this.shadows) + (1);
				this.stackComponent.setStacks(var1,this,this.shadows);
				KageHelper.removeShadows(var1,1);
				this.morphComponent.updateMorphSize(var1);
			}
			return;
		}
	}
	private void onEndContinuityEvent(LivingEntity var1, IAbility var2) {
		this.stackComponent.setStacks(var1,var2,0);
		this.cooldownComponent.startCooldown(var1,(200F) + (this.continuousComponent.getContinueTime()));
	}
	private Multimap<Holder<Attribute>, AttributeModifier> getAttributes() {
		HashMultimap var1 = HashMultimap.create();
		var1.put(Attributes.ARMOR,new AttributeModifier(ARMOR_MODIFIER_UUID,this.getArmorValue(),AttributeModifier.Operation.ADD_VALUE));
		var1.put(Attributes.ATTACK_DAMAGE,new AttributeModifier(ATTACK_MODIFIER_UUID,this.getDamageValue(),AttributeModifier.Operation.ADD_VALUE));
		AttributeModifier var2 = new AttributeModifier(REACH_MODIFIER_UUID,this.getReachValue(),AttributeModifier.Operation.ADD_VALUE);
		var1.put(Attributes.ENTITY_INTERACTION_RANGE,var2);
		var1.put(Attributes.BLOCK_INTERACTION_RANGE,var2);
		var1.put(ModAttributes.TOUGHNESS,new AttributeModifier(TOUGHNESS_MODIFIER_UUID,this.getToughnessValue(),AttributeModifier.Operation.ADD_VALUE));
		var1.put(Attributes.STEP_HEIGHT,new AttributeModifier(STEP_ASSIST_MODIFIER_UUID,this.getStepAssistValue(),AttributeModifier.Operation.ADD_VALUE));
		var1.put(Attributes.KNOCKBACK_RESISTANCE,new AttributeModifier(KNOCKBACK_RES_MODIFIER_UUID,this.getKnockbackResAssistValue(),AttributeModifier.Operation.ADD_VALUE));
		var1.put(Attributes.MAX_HEALTH,new AttributeModifier(MAX_HEALTH_MODIFIER_UUID,this.getMaxHealthValue(),AttributeModifier.Operation.ADD_VALUE));
		return var1;
	}
	private double getArmorValue() {
		return (double)(Math.min((((float)((this.shadows) + (1))) / (30F)) * (10F),10F));
	}
	private double getDamageValue() {
		return (double)(Math.min((((float)((this.shadows) + (1))) / (30F)) * (10F),10F));
	}
	private double getReachValue() {
		return (double)(Math.min((((float)((this.shadows) + (1))) / (30F)) * (2F),2F));
	}
	private double getToughnessValue() {
		return (double)(Math.min((((float)((this.shadows) + (1))) / (30F)) * (5F),5F));
	}
	private double getStepAssistValue() {
		return (double)(Math.min((((float)(((this.shadows) + (1)) + (15))) / (30F)) * (1F),1F));
	}
	private double getKnockbackResAssistValue() {
		return (double)(Math.min((((float)(((this.shadows) + (1)) + (15))) / (30F)) * (1F),1F));
	}
	private double getMaxHealthValue() {
		return (double)(Math.min((((float)((this.shadows) + (1))) / (30F)) * (1F),1F));
	}
	public void saveAdditional(CompoundTag var1) {
		var1.putInt("shadows",this.shadows);
	}
	public void loadAdditional(CompoundTag var1) {
		this.shadows = var1.getInt("shadows");
	}
	public MorphInfo getTransformation() {
		return ((MorphInfo)(ModMorphs.SHADOWS_ASGARD.get()));
	}
	public int getShadows() {
		return this.shadows;
	}
	public float getContinuityHoldTime() {
		return 1200F;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("By continuously absorbing Shadows the user's strenght increases as well as their size"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F,1400F),ContinuousComponent.getTooltip(1200F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(ShadowsAsgardAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("shadows_asgard","Shadow's Asgard",var0));
	}
}