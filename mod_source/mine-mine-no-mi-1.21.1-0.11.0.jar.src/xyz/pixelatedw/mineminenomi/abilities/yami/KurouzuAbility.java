package xyz.pixelatedw.mineminenomi.abilities.yami;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.math.VectorHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KurouzuAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 240F;
	private static final float DAMAGE = 30F;
	private static final float CONTINUITY_TIME = 200F;
	private static final float CHARGE_TIME = 20F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<KurouzuAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final GrabEntityComponent grabEntityComponent;
	private final ChargeComponent chargeComponent;
	private final DealDamageComponent dealDamageComponent;
	private final AnimationComponent animationComponent;
	private Interval particleInterval;

	public KurouzuAbility(AbilityCore<KurouzuAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::onContinuityStart)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onContinuityTick)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		this.grabEntityComponent = new GrabEntityComponent((IAbility)(this),false,false,1F);
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addStartEvent((ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::onChargeStart)))).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::onChargeTick)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::onChargeEnd))));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.particleInterval = new Interval(5);
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.grabEntityComponent,this.chargeComponent,this.dealDamageComponent,this.animationComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		if (this.chargeComponent.isCharging()){
			return;
		}else{
			this.continuousComponent.triggerContinuity(var1,200F);
			return;
		}
	}
	private void onContinuityStart(LivingEntity var1, IAbility var2) {
		this.animationComponent.start(var1,ModAnimations.POINT_LEFT_ARM);
	}
	private void onContinuityTick(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			HitResult var3 = WyHelper.rayTraceBlocksAndEntities((Entity)(var1),128D);
			double var4 = var3.getLocation().x;
			double var5 = (var3.getLocation().y) - ((var3 instanceof EntityHitResult) ? (1D) : (0D));
			double var6 = var3.getLocation().z;
			boolean var7 = this.particleInterval.canTick();
			if (var7){
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.KUROUZU.get())),(Entity)(var1),var4,var5,var6);
				Vec3 var8 = var1.getLookAngle().normalize();
				Vec3 var9 = var1.position().add(var8.x,(var8.y) + (1.5D),var8.z);
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.KUROUZU_CHARGING.get())),(Entity)(var1),var9.x(),var9.y(),var9.z());
			}
			if ((var3.getType()) == (HitResult.Type.MISS)){
				return;
			}else{
				Iterator var8_1 = TargetHelper.getEntitiesInArea((LevelAccessor)(var1.level()),var1,new Vec3(var4,var5,var6),5D,TargetPredicate.DEFAULT_AREA_CHECK,LivingEntity.class).iterator();
				do{
					if (var8_1.hasNext()){
						LivingEntity var9_1 = ((LivingEntity)(var8_1.next()));
						AbilityHelper.setDeltaMovement((Entity)(var9_1),var1.position().subtract(var9_1.position()).normalize());
						if (var7){
							var9_1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,10,5));
							var9_1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),10,5));
							if (this.grabEntityComponent.grabNearest(var1,false)){
								this.continuousComponent.stopContinuity(var1);
								this.chargeComponent.startCharging(var1,20F);
								continue;
							}else{
								continue;
							}
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
				return;
			}
		}
	}
	private void onContinuityEnd(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if (!(this.grabEntityComponent.hasGrabbedEntity())){
				this.animationComponent.stop(var1);
				this.cooldownComponent.startCooldown(var1,240F);
			}
			return;
		}
	}
	
	private void onChargeTick(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if (!(this.grabEntityComponent.canContinueGrab(var1))){
				this.chargeComponent.stopCharging(var1);
				return;
			}else{
				LivingEntity var3 = this.grabEntityComponent.getGrabbedEntity();
				AbilityHelper.setDeltaMovement((Entity)(var3),VectorHelper.getRelativeOffset(var1.position(),var1.yBodyRot,new Vec3(-0.5D,((double)(-(var3.getBbHeight()))) / (2D),((double)(var3.getBbWidth())) - (0.2D))).subtract(var3.position()),true);
				return;
			}
		}
	}
	private void onChargeEnd(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if (this.grabEntityComponent.canContinueGrab(var1)){
				LivingEntity var3 = this.grabEntityComponent.getGrabbedEntity();
				if (this.dealDamageComponent.hurtTarget(var1,var3,30F)){
					Vec3 var4 = var1.getLookAngle().multiply(3D,0D,3D);
					AbilityHelper.setDeltaMovement((Entity)(var3),var3.getDeltaMovement().add(var4.x,0.2D,var4.z));
				}
			}
			this.animationComponent.stop(var1);
			this.cooldownComponent.startCooldown(var1,240F);
			return;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("Creates a strong gravitational force, that pulls the opponent towards the user."),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityDescriptionLine$IDescriptionLine[] lv2_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(240F),ContinuousComponent.getTooltip(200F),ChargeComponent.getTooltip(20F),DealDamageComponent.getTooltip(30F)};
			SourceType[] lv2_5 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KurouzuAbility::new)))).addDescriptionLine(lv2_3).addAdvancedDescriptionLine(lv2_4).setSourceType(lv2_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("kurouzu","Kurouzu",var0));
	}
private void onChargeStart(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if (!(this.grabEntityComponent.canContinueGrab(var1))){
				this.chargeComponent.stopCharging(var1);
				return;
			}else{
				LivingEntity var3 = this.grabEntityComponent.getGrabbedEntity();
				var3.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN,100,5));
				if (var3 instanceof Player){
					Predicate<IAbility> var4 = (l0) -> {
			return (l0.getCore().getCategory()) == (AbilityCategory.DEVIL_FRUITS);
		};
					AbilityHelper.disableAbilities(var3,20,var4);
				}
				return;
			}
		}
	}}