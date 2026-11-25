package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$GrabState;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$IOnPullEndEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$IOnPullStartEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$HitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ChargeComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.DealDamageComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ExplosionComponentTooltip;
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
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class SouthlandSuplexAbility extends Ability {
	// Fields
	private static final int PULL_TIME = 200;
	private static final int CHARGE_TIME = 20;
	private static final int COOLDOWN = 140;
	private static final int DAMAGE = 20;
	private static final int COLLISION_DAMAGE = 10;
	private static final float EXPLOSION_RADIUS = 1F;
	private static final float EXPLOSION_DAMAGE = 6F;
	private static final int MARTIAL_ARTS_POINTS = 5;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SouthlandSuplexAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final DealDamageComponent dealDamageComponent;
	private final ChargeComponent chargeComponent;
	private final AnimationComponent animationComponent;
	private final GrabEntityComponent grabComponent;
	private final HitTriggerComponent hitTriggerComponent;
	private final PoolComponent poolComponent;
	private final ExplosionComponent explosionComponent;

	public SouthlandSuplexAbility(AbilityCore<SouthlandSuplexAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addStartEvent((ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::onChargeStart)))).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::onChargeTick)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::onChargeEnd))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.grabComponent = new GrabEntityComponent((IAbility)(this),true,false,true,2F).addPullStartEvent((GrabEntityComponent$IOnPullStartEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$IOnPullStartEvent)(this::onPullStart)))).addPullEndEvent((GrabEntityComponent$IOnPullEndEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$IOnPullEndEvent)(this::onPullEnd))));
		this.hitTriggerComponent = new HitTriggerComponent((IAbility)(this)).addTryHitEvent((HitTriggerComponent$ITryHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent)(this::tryHitEvent)))).addOnHitEvent((HitTriggerComponent$IOnHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent)(this::onHitEvent))));
		this.poolComponent = new PoolComponent((IAbility)(this),ModAbilityPools.GRAB_ABILITY,new AbilityPool[0]);
		this.explosionComponent = new ExplosionComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.dealDamageComponent,this.chargeComponent,this.animationComponent,this.grabComponent,this.hitTriggerComponent,this.poolComponent,this.explosionComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresEmptyHand))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		if (this.chargeComponent.isCharging()){
			return;
		}else{
			if (this.continuousComponent.isContinuous()){
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
			this.chargeComponent.startCharging(var1,20F);
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
				this.cooldownComponent.startCooldown(var1,140F);
			}
			return;
		}
	}
	private void onChargeStart(LivingEntity var1, IAbility var2) {
		this.animationComponent.start(var1,ModAnimations.POINT_ARMS);
	}
	public void onChargeTick(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if ((!(super.canUse(var1).isFail())) && (this.grabComponent.canContinueGrab(var1))){
				LivingEntity var3 = this.grabComponent.getGrabbedEntity();
				var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),2,1));
				var3.addEffect(new MobEffectInstance((Holder)(ModEffects.GRABBED),2,3));
				float var4 = 1F;
				Vec3 var5 = var1.getLookAngle().normalize();
				Vec3 var6 = new Vec3((var5.x) * ((double)(var4)),(double)(var1.getBbHeight()),(var5.z) * ((double)(var4))).scale((double)(this.chargeComponent.getChargePercentage()));
				AbilityHelper.setDeltaMovement((Entity)(var3),var1.position().subtract(var6.x,(double)(-(EasingFunctionHelper.easeInOutSine(Float.valueOf((float)(var6.y))))),var6.z).subtract(var3.position()),true);
				return;
			}else{
				this.chargeComponent.stopCharging(var1);
				return;
			}
		}
	}
	
	
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(11F,-4F));
		TrainingPointsUnlockCondition var2 = TrainingPointsUnlockCondition.martialArts(5F);
		NodeUnlockAction var3 = SubtractPointsAction.martialArts(5F).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE)));
		AbilityNode[] var4 = new AbilityNode[1];
		var4[0] = ((AbilityCore)(StrongRightAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var4);
		var1.setUnlockRule(var2,var3);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv6_2 = new Pair[]{ImmutablePair.of((Object)("The user grabs a person with both of their arms and smashes the opponent's head by throwing them backwards"),(Object)(null))};
			Component[] lv6_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv6_2);
			SourceType[] lv6_4 = new SourceType[]{SourceType.FIST,SourceType.TECHNOLOGY};
			AbilityCore$INodeFactory[] lv6_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv6_6 = SouthlandSuplexAbility::createNode;
			lv6_5[0] = lv6_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.RACIAL,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SouthlandSuplexAbility::new)))).addDescriptionLine(lv6_3).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv6_4).setNodeFactories(lv6_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("southland_suplex","Southland Suplex",var0));
	}
public void onChargeEnd(LivingEntity var1, IAbility var2) {
		if (this.grabComponent.canContinueGrab(var1)){
			LivingEntity var3 = this.grabComponent.getGrabbedEntity();
			if (this.dealDamageComponent.hurtTarget(var1,var3,20F)){
				var3.addEffect(new MobEffectInstance((Holder)(ModEffects.DIZZY),40,0));
			}
			List var4 = TargetHelper.getEntitiesInArea((LevelAccessor)(var1.level()),var1,var3.blockPosition(),(double)(var3.getBbWidth()),(double)(var3.getBbHeight()),(double)(var3.getBbWidth()),(TargetPredicate)(null),LivingEntity.class);
			var4.remove(var3);
			if (!(HakiHelper.hasHardeningActive(var1))){
				var4.removeIf((Predicate)(((Predicate<LivingEntity>)((l0) -> {
			return ((Boolean)(DevilFruitData.get(var1).map((Function)(((Function<DevilFruitData, Boolean>)(DevilFruitData::isLogia)))).orElse(Boolean.valueOf(false)))).booleanValue();
		}))));
			}
			Iterator var5 = var4.iterator();
			do{
				if (var5.hasNext()){
					LivingEntity var6 = ((LivingEntity)(var5.next()));
					if (this.dealDamageComponent.hurtTarget(var1,var6,10F)){
						AbilityHelper.setDeltaMovement((Entity)(var6),0D,-1D,0D);
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			AbilityExplosion var6_1 = this.explosionComponent.createExplosion((Entity)(var1),var3.getX(),var3.getY(),var3.getZ(),1F);
			var6_1.setStaticDamage(6F);
			var6_1.setExplosionSound(true);
			var6_1.setDamageOwner(false);
			var6_1.setDestroyBlocks(false);
			var6_1.setFireAfterExplosion(false);
			var6_1.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION2));
			var6_1.setDamageEntities(true);
			var6_1.explode();
			this.grabComponent.release(var1);
		}
		this.animationComponent.stop(var1);
		this.cooldownComponent.startCooldown(var1,140F);
	}public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(140F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<ChargeComponentTooltip> var4 = (l0) -> {
			l0.charge(20F);
		};
		ChargeComponentTooltip.generate(var2,var4);
		Consumer<DealDamageComponentTooltip> var5 = (l0) -> {
			l0.damage(20F);
		};
		DealDamageComponentTooltip.generate(var2,var5);
		Consumer<ExplosionComponentTooltip> var6 = (l0) -> {
			l0.damage(6F).range(1F);
		};
		ExplosionComponentTooltip.generate(var2,var6);
	}}