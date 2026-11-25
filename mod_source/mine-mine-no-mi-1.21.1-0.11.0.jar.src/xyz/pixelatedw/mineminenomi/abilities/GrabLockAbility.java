package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$GrabState;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$IOnGrabEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$IOnPullEndEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$IOnPullStartEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$IOnReleaseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$HitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GrabLockAbility extends Ability {
	// Fields
	private static final int HOLD_TIME = 40;
	private static final int PULL_TIME = 200;
	private static final float COOLDOWN = 200F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<GrabLockAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final AnimationComponent animationComponent;
	private final GrabEntityComponent grabComponent;
	private final HitTriggerComponent hitTriggerComponent;
	private final PoolComponent poolComponent;

	public GrabLockAbility(AbilityCore<GrabLockAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.grabComponent = new GrabEntityComponent((IAbility)(this),true,true,true,2F).addPullStartEvent((GrabEntityComponent$IOnPullStartEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$IOnPullStartEvent)(this::onPullStart)))).addPullEndEvent((GrabEntityComponent$IOnPullEndEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$IOnPullEndEvent)(this::onPullEnd)))).addGrabEvent((GrabEntityComponent$IOnGrabEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$IOnGrabEvent)(this::grabEvent)))).addReleaseEvent((GrabEntityComponent$IOnReleaseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$IOnReleaseEvent)(this::releaseEvent))));
		this.hitTriggerComponent = new HitTriggerComponent((IAbility)(this)).addTryHitEvent((HitTriggerComponent$ITryHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent)(this::tryHitEvent)))).addOnHitEvent((HitTriggerComponent$IOnHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent)(this::onHitEvent))));
		this.poolComponent = new PoolComponent((IAbility)(this),ModAbilityPools.GRAB_ABILITY,new AbilityPool[0]);
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.animationComponent,this.grabComponent,this.hitTriggerComponent,this.poolComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresEmptyHand))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1);
	}
	private HitTriggerComponent$HitResult tryHitEvent(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		if ((this.continuousComponent.isContinuous()) && (!(this.grabComponent.hasGrabbedEntity()))){
			return HitTriggerComponent$HitResult.HIT;
		}else{
			return HitTriggerComponent$HitResult.PASS;
		}
	}
	private boolean onHitEvent(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		if (this.grabComponent.grabManually(var1,var2)){
			this.grabComponent.startPulling(var1);
		}
		var2.addEffect(new MobEffectInstance((Holder)(ModEffects.ANTI_KNOCKBACK),1));
		return false;
	}
	public void onPullStart(LivingEntity var1, IAbility var2) {
		this.continuousComponent.setThresholdTime(var1,200F);
	}
	public void onPullEnd(LivingEntity var1, IAbility var2) {
		if (this.grabComponent.canContinueGrab(var1)){
			this.continuousComponent.setThresholdTime(var1,40F);
		}
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if ((this.grabComponent.getState()) == (GrabEntityComponent$GrabState.IDLE)){
			return;
		}else{
			if ((!(super.canUse(var1).isFail())) && (this.grabComponent.canContinueGrab(var1))){
				LivingEntity var3 = this.grabComponent.getGrabbedEntity();
				if ((var3) != (null)){
					var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),5,1));
					var3.addEffect(new MobEffectInstance((Holder)(ModEffects.GRABBED),5,3));
				}
				return;
			}else{
				this.continuousComponent.stopContinuity(var1);
				return;
			}
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			this.grabComponent.release(var1);
			this.animationComponent.stop(var1);
			this.cooldownComponent.startCooldown(var1,200F);
			return;
		}
	}
	private boolean grabEvent(LivingEntity var1, LivingEntity var2, IAbility var3) {
		this.animationComponent.start(var1,ModAnimations.POINT_ARMS);
		return true;
	}
	private void releaseEvent(LivingEntity var1, LivingEntity var2, IAbility var3) {
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Grabs an opponent from the back and keeps them pinned like that."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F),ContinuousComponent.getTooltip(40F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GrabLockAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("grab_lock","Grab Lock",var0));
	}
}