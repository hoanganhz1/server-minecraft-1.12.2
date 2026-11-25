package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public abstract class SlidingAbility extends Ability {
	// Fields
	private static final ResourceLocation SLIDING_FRICTION_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","sliding_ability.friction");
	private static final ResourceLocation SLIDING_SPEED_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","sliding_ability.speed");
	protected final ContinuousComponent continuousComponent;
	protected final ChangeStatsComponent changeStatsComponent;

	public SlidingAbility(AbilityCore<? extends SlidingAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent(200,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addEndEvent(200,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.changeStatsComponent = new ChangeStatsComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.changeStatsComponent};
		this.addComponents(var2);
		this.addUseEvent(200,(Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		AttributeModifier var3 = new AttributeModifier(SLIDING_FRICTION_ID,(1D) + (this.getSlidingModifier()),AttributeModifier.Operation.ADD_VALUE);
		AttributeModifier var4 = new AttributeModifier(SLIDING_SPEED_ID,this.getSpeedModifier(),AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
		this.changeStatsComponent.addAttributeModifier(ModAttributes.FRICTION,var3);
		this.changeStatsComponent.addAttributeModifier(Attributes.MOVEMENT_SPEED,var4);
		this.changeStatsComponent.applyModifiers(var1);
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.changeStatsComponent.removeModifiers(var1);
	}
	public abstract double getSpeedModifier();
	public abstract double getSlidingModifier();
}