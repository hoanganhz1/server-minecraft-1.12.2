package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;

public abstract class DropHitAbility extends Ability {
	// Fields
	protected final ContinuousComponent continuousComponent;
	protected final DamageTakenComponent damageTakenComponent;
	protected final HitTrackerComponent hitTrackerComponent;
	private boolean hasLanded;
	private boolean hasFallDamage;
	private boolean startsInLiquid;
	private boolean isCancelable;
	private int ticksOnGround;

	public DropHitAbility(AbilityCore<? extends DropHitAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent(90,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent(90,(ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::tickContinuityEvent)))).addEndEvent(90,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnAttackEvent((DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::onDamageTaken))));
		this.hitTrackerComponent = new HitTrackerComponent((IAbility)(this));
		this.hasLanded = true;
		this.hasFallDamage = true;
		this.startsInLiquid = false;
		this.isCancelable = true;
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.damageTakenComponent,this.hitTrackerComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
		this.addTickEvent(100,(Ability$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnTickEvent)(this::onTickEvent))));
	}
	protected void onUseEvent(LivingEntity var1, IAbility var2) {
		if ((this.continuousComponent.isContinuous()) && (!(this.isCancelable))){
			return;
		}else{
			this.continuousComponent.triggerContinuity(var1);
			return;
		}
	}
	private void onTickEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if ((!(this.hasFallDamage)) && (var1.onGround())){
				this.ticksOnGround = (this.ticksOnGround) + (1);
				if ((this.ticksOnGround) > (10)){
					this.hasFallDamage = true;
				}else{
					return;
				}
			}
			return;
		}
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.hasLanded = false;
		this.hasFallDamage = false;
		this.startsInLiquid = var1.isInWater();
		this.ticksOnGround = 0;
	}
	private void tickContinuityEvent(LivingEntity var1, IAbility var2) {
		if ((!(this.startsInLiquid)) && ((var1.isInWater()) && (!(this.hasLanded)))){
			this.hasLanded = true;
		}
		if ((this.startsInLiquid) && ((var1.isInWater()) && ((var1.getDeltaMovement().y) <= (0D)))){
			this.hasLanded = true;
		}
		if ((this.hasLanded) && ((!(var1.onGround())) && (!(var1.isInWater())))){
			this.hasLanded = false;
		}
		if ((var1.onGround()) && (((this.continuousComponent.getContinueTime()) > (10F)) && (!(this.hasLanded)))){
			this.hasLanded = true;
		}
		if (this.hasLanded){
			this.onLanding(var1);
			this.continuousComponent.stopContinuity(var1);
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.hitTrackerComponent.clearHits();
	}
	public abstract void onLanding(LivingEntity var0);
	private float onDamageTaken(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		if ((!(this.hasFallDamage)) && (var3.is(DamageTypes.FALL))){
			this.hasFallDamage = true;
			return 0F;
		}else{
			return var4;
		}
	}
	public void saveAdditional(CompoundTag var1) {
		var1.putBoolean("hasFallDamage",this.hasFallDamage);
	}
	public void loadAdditional(CompoundTag var1) {
		this.hasFallDamage = var1.getBoolean("hasFallDamage");
	}
	public void setLanded() {
		this.hasLanded = true;
	}
	public boolean hasLanded() {
		return this.hasLanded;
	}
	public void setCancelable(boolean var1) {
		this.isCancelable = var1;
	}
}