package xyz.pixelatedw.mineminenomi.abilities.supa;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class AtomicRushAbility extends Ability {
	// Fields
	private static final ResourceLocation ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/atomic_spurt.png");
	private static final float COOLDOWN = 400F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<AtomicRushAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final HitTrackerComponent hitTrackerComponent;
	private final AnimationComponent animationComponent;
	private final DealDamageComponent dealDamageComponent;
	private final RangeComponent rangeComponent;
	private Interval newPosInterval;
	private Interval dashWait;
	private int damageTimer;
	private LivingEntity target;

	public AtomicRushAbility(AbilityCore<AtomicRushAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.hitTrackerComponent = new HitTrackerComponent((IAbility)(this));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.newPosInterval = new Interval(20);
		this.dashWait = new Interval(5);
		this.damageTimer = 0;
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.hitTrackerComponent,this.animationComponent,this.dealDamageComponent,this.rangeComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.target = null;
		this.hitTrackerComponent.clearHits();
		this.continuousComponent.triggerContinuity(var1,WyHelper.secondsToTicks(10F));
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.dashWait.restartIntervalToZero();
		this.animationComponent.start(var1,ModAnimations.CROSSED_ARMS);
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.stop(var1);
		this.cooldownComponent.startCooldown(var1,400F);
	}
	public void setDashWaitTime(int var1) {
		this.dashWait = new Interval(var1);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			AbilityDescriptionLine$IDescriptionLine[] lv2_2 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(400F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(AtomicRushAbility::new)))).setIcon(ICON).addAdvancedDescriptionLine(lv2_2).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("atomic_rush","Atomic Rush",var0));
	}

	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		LivingEntity var5_1 = null;
		Level var3 = var1.level();
		if (((var3) != (null)) && (!(var3.isClientSide))){
			if (((this.target) != (null)) && (this.dashWait.canTick())){
				this.hitTrackerComponent.clearHits();
				Vec3 var4 = var1.position().subtract(this.target.position()).normalize().multiply(7D,0D,7D);
				AbilityHelper.setDeltaMovement((Entity)(var1),-(var4.x),0.15D,-(var4.z));
				this.target = null;
				this.damageTimer = 5;
			}
			if ((this.damageTimer) > (0)){
				this.damageTimer = (this.damageTimer) - (1);
				List var4_1 = this.rangeComponent.getTargetsInArea(var1,1.6F);
				Iterator var5 = var4_1.iterator();
				do{
					if (var5.hasNext()){
						LivingEntity var6 = ((LivingEntity)(var5.next()));
						if (this.hitTrackerComponent.canHit((Entity)(var6))){
							this.dealDamageComponent.hurtTarget(var1,var6,25F);
							continue;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
			}
			if (!(this.newPosInterval.canTick())){
				return;
			}else{
				List var4_2 = this.rangeComponent.getTargetsInArea(var1,40F);
				if ((var4_2.size()) <= (0)){
					this.continuousComponent.stopContinuity(var1);
					return;
				}else{
					var5_1 = var1;
					final LivingEntity var5_1_f1 = var5_1;
					Comparator<LivingEntity> var6_1 = (l0, l1) -> {
			return (int)((l0.distanceToSqr(var5_1_f1.position())) - (l1.distanceToSqr(var5_1_f1.position())));
		};
					LivingEntity var7 = ((LivingEntity)(((java.util.Optional)(((java.util.stream.Stream)(var4_2.stream())).sorted((Comparator)(var6_1)).findFirst())).orElse((Object)(null))));
					if ((var7) == (null)){
						return;
					}else{
						MobEffectInstance var8 = new MobEffectInstance((Holder)(ModEffects.VANISH),5,0);
						var1.addEffect(var8);
						WyHelper.sendApplyEffectToAllNearby(var1,var1.position(),100,var8);
						BlockPos var9 = WyHelper.findValidGroundLocation((Entity)(var1),var7.blockPosition(),5,5);
						var1.teleportTo((double)(var9.getX()),(double)(var9.getY()),(double)(var9.getZ()));
						this.target = var7;
						var1.lookAt(EntityAnchorArgument.Anchor.EYES,var7.position().add(0D,(double)(var7.getEyeHeight()),0D));
						return;
					}
				}
			}
		}else{
			return;
		}
	}}