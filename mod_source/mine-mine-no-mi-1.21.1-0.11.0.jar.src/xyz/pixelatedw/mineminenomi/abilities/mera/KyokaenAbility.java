package xyz.pixelatedw.mineminenomi.abilities.mera;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IAbilityGoalFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.projectiles.KairosekiBulletProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.NormalBulletProjectile;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KyokaenAbility extends Ability {
	// Fields
	private static final float DAMAGE = 3F;
	private static final int ON_HOLD = 100;
	private static final int MIN_COOLDOWN = 40;
	private static final int MAX_COOLDOWN = 140;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<KyokaenAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final HitTrackerComponent hitTrackerComponent;
	private final DealDamageComponent dealDamageComponent;
	private final Interval particleInterval;
	private final Interval clearHitsInterval;

	public KyokaenAbility(AbilityCore<KyokaenAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::onContinuityStart)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onContinuityTick)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		this.hitTrackerComponent = new HitTrackerComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.particleInterval = new Interval(2);
		this.clearHitsInterval = new Interval(20);
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.hitTrackerComponent,this.dealDamageComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(MeraHelper::canUseMeraAbilities))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,100F);
	}
	private void onContinuityStart(LivingEntity var1, IAbility var2) {
		this.particleInterval.restartIntervalToZero();
		this.hitTrackerComponent.clearHits();
	}
	private void onContinuityEnd(LivingEntity var1, IAbility var2) {
		float var3 = (40F) + (this.continuousComponent.getContinueTime());
		this.cooldownComponent.startCooldown(var1,var3);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("Creates a wall of fire protecting the user"),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityDescriptionLine$IDescriptionLine[] lv2_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(40F,140F),ContinuousComponent.getTooltip(100F),DealDamageComponent.getTooltip(3F)};
			AbilityCore$IAbilityGoalFactory[] lv2_5 = new AbilityCore$IAbilityGoalFactory[1];
			AbilityCore$IAbilityGoalFactory lv2_6 = ActiveGuardAbilityWrapperGoal::new;
			lv2_5[0] = lv2_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KyokaenAbility::new)))).addDescriptionLine(lv2_3).addAdvancedDescriptionLine(lv2_4).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.FIRE).addGoalWrapper(lv2_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("kyokaen","Kyokaen",var0));
	}

	private void onContinuityTick(LivingEntity var1, IAbility var2) {
		LivingEntity var10 = null;
		if (var1.level().isClientSide){
			return;
		}else{
			if (super.canUse(var1).isFail()){
				this.continuousComponent.stopContinuity(var1);
			}
			int var3 = 2;
			double var4 = 1.1D;
			int var5 = 0;
			LOOP_1:
			do{
				if ((var5) < ((var3) * (2))){
					double var6 = ((double)(var5)) / (2D);
					Vec3 var7 = var1.getLookAngle();
					Vec3 var8 = new Vec3((var1.getX()) + ((var7.x) * (var6)),((var1.getY()) + ((double)(var1.getEyeHeight()))) + ((var7.y) * (var6)),(var1.getZ()) + ((var7.z) * (var6)));
					AABB var9 = new AABB((var8.x) - (var4),(var8.y) - (var4),(var8.z) - (var4),(var8.x) + (var4),(var8.y) + ((var4) * (2D)),(var8.z) + (var4));
					var10 = var1;
					final LivingEntity var10_f1 = var10;
					Predicate<Entity> var11 = (l0) -> {
			return (l0) != (var10_f1);
		};
					Iterator var12 = var1.level().getEntities((Entity)(var1),var9,var11).iterator();
					do{
						if (var12.hasNext()){
							Entity var13 = ((Entity)(var12.next()));
							if (var13 instanceof LivingEntity){
								if ((!(var13.isOnFire())) && (this.hitTrackerComponent.canHit(var13))){
									var13.igniteForSeconds(3F);
									this.dealDamageComponent.hurtTarget(var1,((LivingEntity)(var13)),3F);
								}
								Vec3 var14 = var1.getLookAngle().normalize().scale(3D);
								AbilityHelper.setDeltaMovement(var13,var14.x,0.5D,var14.z);
								continue;
							}else{
								if ((!(var13 instanceof AbstractArrow)) && ((!(var13 instanceof KairosekiBulletProjectile)) && (!(var13 instanceof NormalBulletProjectile)))){
									if (var13 instanceof ThrowableProjectile){
										AbilityHelper.setDeltaMovement(var13,var13.getDeltaMovement().multiply(-1.35D,1D,-1.35D));
										continue;
									}else{
										continue;
									}
								}else{
									var13.remove(Entity.RemovalReason.KILLED);
									continue;
								}
							}
						}else{
							break;
						}
					} while (true);
					var5++;
					continue LOOP_1;
				}else{
					break;
				}
			} while (true);
			if (this.particleInterval.canTick()){
				EntityHitResult var6_1 = WyHelper.rayTraceEntities((Entity)(var1),(double)(var3));
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.KYOKAEN.get())),(Entity)(var1),var6_1.getLocation().x(),var1.getY(),var6_1.getLocation().z());
			}
			if (this.clearHitsInterval.canTick()){
				this.hitTrackerComponent.clearHits();
			}
			return;
		}
	}}