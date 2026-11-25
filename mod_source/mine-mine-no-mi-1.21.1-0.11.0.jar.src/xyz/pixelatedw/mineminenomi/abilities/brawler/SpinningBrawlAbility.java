package xyz.pixelatedw.mineminenomi.abilities.brawler;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$GrabState;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$IOnPullEndEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$IOnPullStartEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$HitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ChargeComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.DealDamageComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class SpinningBrawlAbility extends Ability {
	// Fields
	private static final double THROW_POWER_XZ = 2D;
	private static final double THROW_POWER_Y = 0.5D;
	private static final int SPIN_DAMAGE = 10;
	private static final int MAIN_DAMAGE = 30;
	private static final int COOLDOWN = 240;
	private static final int CHARGE_TIME = 60;
	private static final int THROW_TIME = 40;
	private static final int PULL_TIME = 200;
	private static final int NODE_POINTS_MA = 10;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SpinningBrawlAbility>> INSTANCE;
	private final DealDamageComponent dealDamageComponent;
	private final ChargeComponent chargeComponent;
	private final ContinuousComponent continuousComponent;
	private final HitTrackerComponent hitTrackerComponent;
	private final AnimationComponent animationComponent;
	private final GrabEntityComponent grabComponent;
	private final HitTriggerComponent hitTriggerComponent;
	private final PoolComponent poolComponent;
	private final Interval clearHitsInterval;

	public SpinningBrawlAbility(AbilityCore<SpinningBrawlAbility> var1) {
		super((AbilityCore)(var1));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addStartEvent(100,(ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::onChargeStart)))).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::onChargeTick)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::onChargeEnd))));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onContinuityTick)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		this.hitTrackerComponent = new HitTrackerComponent((IAbility)(this));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.grabComponent = new GrabEntityComponent((IAbility)(this),true,false,true,2F).addPullStartEvent((GrabEntityComponent$IOnPullStartEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$IOnPullStartEvent)(this::onPullStart)))).addPullEndEvent((GrabEntityComponent$IOnPullEndEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$IOnPullEndEvent)(this::onPullEnd))));
		this.hitTriggerComponent = new HitTriggerComponent((IAbility)(this)).addTryHitEvent((HitTriggerComponent$ITryHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent)(this::tryHitEvent)))).addOnHitEvent((HitTriggerComponent$IOnHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent)(this::onHitEvent))));
		this.poolComponent = new PoolComponent((IAbility)(this),ModAbilityPools.GRAB_ABILITY,new AbilityPool[0]);
		this.clearHitsInterval = new Interval(20);
		AbilityComponent[] var2 = new AbilityComponent[]{this.dealDamageComponent,this.chargeComponent,this.continuousComponent,this.hitTrackerComponent,this.animationComponent,this.grabComponent,this.hitTriggerComponent,this.poolComponent};
		super.addComponents(var2);
		super.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresEmptyHand))));
		super.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		if (this.chargeComponent.isCharging()){
			this.chargeComponent.stopCharging(var1);
			return;
		}else{
			this.clearHitsInterval.restartIntervalToZero();
			if ((this.continuousComponent.isContinuous()) && (!(((this.grabComponent.getState()) != (GrabEntityComponent$GrabState.IDLE)) && ((this.grabComponent.getState()) != (GrabEntityComponent$GrabState.PULLING))))){
				this.grabComponent.release(var1);
				this.continuousComponent.stopContinuity(var1);
			}else{
				if (((this.grabComponent.getState()) == (GrabEntityComponent$GrabState.IDLE)) && (this.grabComponent.grabNearest(var1,false))){
					this.grabComponent.triggerPulling(var1);
				}else{
					this.continuousComponent.startContinuity(var1);
				}
			}
			return;
		}
	}
	private HitTriggerComponent$HitResult tryHitEvent(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		if ((this.continuousComponent.isContinuous()) && (!(this.grabComponent.hasGrabbedEntity()))){
			return HitTriggerComponent$HitResult.HIT;
		}else{
			return HitTriggerComponent$HitResult.PASS;
		}
	}
	private boolean onHitEvent(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		if ((this.continuousComponent.isContinuous()) && (!(this.grabComponent.hasGrabbedEntity()))){
			if (this.grabComponent.grabManually(var1,var2)){
				this.grabComponent.startPulling(var1);
			}
			var2.addEffect(new MobEffectInstance((Holder)(ModEffects.ANTI_KNOCKBACK),1));
			return false;
		}else{
			return true;
		}
	}
	public void onPullStart(LivingEntity var1, IAbility var2) {
		this.continuousComponent.setThresholdTime(var1,200F);
	}
	public void onPullEnd(LivingEntity var1, IAbility var2) {
		this.continuousComponent.stopContinuity(var1);
		if (this.grabComponent.canContinueGrab(var1)){
			this.chargeComponent.startCharging(var1,60F);
		}
	}
	private void onContinuityTick(LivingEntity var1, IAbility var2) {
		if ((this.grabComponent.getState()) == (GrabEntityComponent$GrabState.IDLE)){
			return;
		}else{
			if (!(this.grabComponent.canContinueGrab(var1))){
				this.continuousComponent.stopContinuity(var1);
				return;
			}else{
				if ((this.grabComponent.getState()) != (GrabEntityComponent$GrabState.THROWN)){
					return;
				}else{
					LivingEntity var3 = this.grabComponent.getGrabbedEntity();
					if (var3.onGround()){
						this.grabComponent.release(var1);
						this.continuousComponent.stopContinuity(var1);
						return;
					}else{
						List var4 = TargetHelper.getEntitiesInArea((LevelAccessor)(var1.level()),var3,var3.blockPosition(),(double)(var3.getBbWidth()),(double)(var3.getBbHeight()),(double)(var3.getBbWidth()),(TargetPredicate)(null),LivingEntity.class);
						var4.remove(var3);
						Vec3 var5 = var1.getLookAngle().normalize().scale(2D);
						Iterator var6 = var4.iterator();
						do{
							if (var6.hasNext()){
								LivingEntity var7 = ((LivingEntity)(var6.next()));
								if ((this.hitTrackerComponent.canHit((Entity)(var7))) && (this.dealDamageComponent.hurtTarget(var1,var7,10F))){
									AbilityHelper.setDeltaMovement((Entity)(var7),var5.x,0.5D,var5.z);
									continue;
								}else{
									continue;
								}
							}else{
								break;
							}
						} while (true);
						if (this.clearHitsInterval.canTick()){
							this.hitTrackerComponent.clearHits();
						}
						return;
					}
				}
			}
		}
	}
	private void onContinuityEnd(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if ((this.grabComponent.getState()) != (GrabEntityComponent$GrabState.GRABBED)){
				this.grabComponent.release(var1);
			}
			if (!(this.grabComponent.canContinueGrab(var1))){
				this.cooldownComponent.startCooldown(var1,240F);
			}
			return;
		}
	}
	private void onChargeStart(LivingEntity var1, IAbility var2) {
		this.animationComponent.start(var1,ModAnimations.POINT_ARMS);
	}
	
	public void onChargeEnd(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if (this.grabComponent.canContinueGrab(var1)){
				LivingEntity var3 = this.grabComponent.getGrabbedEntity();
				if (this.dealDamageComponent.hurtTarget(var1,var3,30F)){
					var3.addEffect(new MobEffectInstance((Holder)(ModEffects.DIZZY),80,0));
				}
				this.grabComponent.throwTarget(var1,2D,0.5D);
				this.continuousComponent.startContinuity(var1,40F);
			}else{
				this.cooldownComponent.startCooldown(var1,240F);
			}
			this.animationComponent.stop(var1);
			return;
		}
	}
	
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(-6F,6F));
		TrainingPointsUnlockCondition var2 = TrainingPointsUnlockCondition.martialArts(10F);
		NodeUnlockAction var3 = SubtractPointsAction.martialArts(10F).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE)));
		AbilityNode[] var4 = new AbilityNode[1];
		var4[0] = ((AbilityCore)(SuplexAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var4);
		var1.setUnlockRule(var2,var3);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv5_2 = new Pair[]{ImmutablePair.of((Object)("Grabs a nearby enemy spinning them around damaging any nearby entity it touches, and ending by throwing the grabbed entity a few blocks away."),(Object)(null))};
			Component[] lv5_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv5_2);
			SourceType[] lv5_4 = new SourceType[]{SourceType.FIST};
			AbilityCore$INodeFactory[] lv5_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv5_6 = SpinningBrawlAbility::createNode;
			lv5_5[0] = lv5_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SpinningBrawlAbility::new)))).addDescriptionLine(lv5_3).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv5_4).setNodeFactories(lv5_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("spinning_brawl","Spinning Brawl",var0));
	}
public void onChargeTick(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if ((!(super.canUse(var1).isFail())) && (this.grabComponent.canContinueGrab(var1))){
				LivingEntity var3 = this.grabComponent.getGrabbedEntity();
				var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),2,1));
				var3.addEffect(new MobEffectInstance((Holder)(ModEffects.GRABBED),2,3));
				Vec3 var4 = var3.position().subtract(var1.position()).normalize();
				float var5 = ((((float)(Math.toDegrees(Math.atan2(var4.z,var4.x)))) - (90F)) + (10F)) % (360F);
				var1.setYRot(var5);
				var1.yRotO = var5;
				var1.setXRot(0F);
				var1.xRotO = 0F;
				if (var1 instanceof ServerPlayer){
					ServerPlayer var6 = ((ServerPlayer)(var1));
					EnumSet var7 = EnumSet.of(RelativeMovement.X,RelativeMovement.Y,RelativeMovement.Z);
					var6.connection.teleport(var1.getX(),var1.getY(),var1.getZ(),var1.getYRot(),var1.getXRot(),(Set)(var7));
				}
				float var6_1 = 2F;
				Vec3 var7_1 = var1.getLookAngle().normalize();
				Vec3 var8 = new Vec3((var7_1.x) * ((double)(var6_1)),((double)((var1.getEyeHeight()) / (2F))) + ((var7_1.y) * ((double)(var6_1))),(var7_1.z) * ((double)(var6_1)));
				AbilityHelper.setDeltaMovement((Entity)(var3),var1.position().add(var8).subtract(var3.position()),true);
				List var9 = TargetHelper.getEntitiesInArea((LevelAccessor)(var1.level()),var3,var3.blockPosition(),(double)(var3.getBbWidth()),(double)(var3.getBbHeight()),(double)(var3.getBbWidth()),TargetPredicate.DEFAULT_AREA_CHECK,LivingEntity.class);
				var9.remove(var3);
				if (!(HakiHelper.hasHardeningActive(var1))){
					var9.removeIf((Predicate)(((Predicate<LivingEntity>)((l0) -> {
			return ((Boolean)(DevilFruitData.get(var1).map((Function)(((Function<DevilFruitData, Boolean>)(DevilFruitData::isLogia)))).orElse(Boolean.valueOf(false)))).booleanValue();
		}))));
				}
				Vec3 var10 = var7_1.scale(2D);
				Iterator var11 = var9.iterator();
				do{
					if (var11.hasNext()){
						LivingEntity var12 = ((LivingEntity)(var11.next()));
						if ((this.hitTrackerComponent.canHit((Entity)(var12))) && (this.dealDamageComponent.hurtTarget(var1,var12,10F))){
							AbilityHelper.setDeltaMovement((Entity)(var12),var10.x,0.5D,var10.z);
							continue;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
				if (this.clearHitsInterval.canTick()){
					this.hitTrackerComponent.clearHits();
				}
				return;
			}else{
				this.chargeComponent.stopCharging(var1);
				return;
			}
		}
	}public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(240F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<ChargeComponentTooltip> var4 = (l0) -> {
			l0.charge(60F);
		};
		ChargeComponentTooltip.generate(var2,var4);
		Consumer<DealDamageComponentTooltip> var5 = (l0) -> {
			l0.damage(30F);
		};
		DealDamageComponentTooltip.generate(var2,var5);
		var2.add(((AbilityComponentKey)(ModAbilityComponents.DAMAGE.get())),new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_SPIN_DAMAGE),10).buildComponent());
	}}