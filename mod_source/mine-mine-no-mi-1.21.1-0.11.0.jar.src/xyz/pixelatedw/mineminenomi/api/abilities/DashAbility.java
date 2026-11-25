package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.Iterator;
import java.util.List;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DashComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;

public abstract class DashAbility extends Ability {
	// Fields
	private static final TargetPredicate TARGET_CHECK = TargetPredicate.DEFAULT_AREA_CHECK;
	public final ContinuousComponent continuousComponent;
	public final HitTrackerComponent hitTrackerComponent;
	public final RangeComponent rangeComponent;
	public final DealDamageComponent dealDamageComponent;
	public final DashComponent dashComponent;

	public DashAbility(AbilityCore<? extends DashAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),this.isParallel()).addStartEvent(200,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent(200,(ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuity)))).addEndEvent(200,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.hitTrackerComponent = new HitTrackerComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.dashComponent = new DashComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.hitTrackerComponent,this.rangeComponent,this.dealDamageComponent,this.dashComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		this.addUseEvent(200,(Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.startContinuity(var1,(float)(this.getHoldTime()));
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.hitTrackerComponent.clearHits();
		float var3 = this.getSpeed();
		this.dashComponent.dashXYZ(var1,var3,this.getYSpeed(),this.getYBump());
	}
	private void duringContinuity(LivingEntity var1, IAbility var2) {
		if (var1.isAlive()){
			List var3 = this.rangeComponent.getTargetsInArea(var1,(this.getRange()) + ((var1.getBbWidth()) / (2F)),this.getTargetCheck());
			float var4 = this.getDamage();
			DamageSource var5 = this.getDamageSource(var1);
			Iterator var6 = var3.iterator();
			do{
				if (var6.hasNext()){
					LivingEntity var7 = ((LivingEntity)(var6.next()));
					if ((this.hitTrackerComponent.canHit((Entity)(var7))) && (this.dealDamageComponent.hurtTarget(var1,var7,var4,var5))){
						this.onTargetHit(var1,var7,var4,var5);
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.cooldownComponent.startCooldown(var1,this.getDashCooldown());
	}
	public abstract void onTargetHit(LivingEntity var0, LivingEntity var1, float var2, DamageSource var3);
	public DamageSource getDamageSource(LivingEntity var1) {
		return this.dealDamageComponent.getDamageSource(var1);
	}
	public TargetPredicate getTargetCheck() {
		return TARGET_CHECK;
	}
	public boolean isParallel() {
		return false;
	}
	public abstract float getDashCooldown();
	public abstract float getDamage();
	public float getRange() {
		return 0F;
	}
	public abstract float getSpeed();
	public float getYSpeed() {
		return 0.2F;
	}
	public float getYBump() {
		return 0F;
	}
	public int getHoldTime() {
		return 10;
	}
}