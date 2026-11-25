package xyz.pixelatedw.mineminenomi.abilities.mandemontactics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.DropHitAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.BreakingBlocksParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class DemonicDanceAbility extends DropHitAbility {
	// Fields
	private static final int COOLDOWN = 260;
	private static final float RANGE = 1.25F;
	private static final float DAMAGE = 30F;
	private static final float CHARGE_TIME = 40F;
	public static final TargetPredicate TARGET_TEST = new TargetPredicate().testEnemyFaction().testSimpleInView();
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<DemonicDanceAbility>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final DealDamageComponent dealDamageComponent;
	private final RangeComponent rangeComponent;
	private final AnimationComponent animationComponent;
	private BreakingBlocksParticleEffect$Details details;
	@Nullable() private Entity target;
	private boolean isStandard;

	public DemonicDanceAbility(AbilityCore<DemonicDanceAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addStartEvent((ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::startChargeEvent)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargeEvent))));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.details = new BreakingBlocksParticleEffect$Details(40);
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.dealDamageComponent,this.rangeComponent,this.animationComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresBluntWeapon))));
		this.continuousComponent.addStartEvent(100,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent))));
		this.continuousComponent.addEndEvent(100,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.continuousComponent.addTickEvent(100,(ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::tickContinuityEvent))));
	}
	protected void onUseEvent(LivingEntity var1, IAbility var2) {
		this.chargeComponent.startCharging(var1,40F);
	}
	private void startChargeEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.start(var1,ModAnimations.DEMONIC_DANCE_CHARGE,(int)(this.chargeComponent.getMaxChargeTime()));
	}
	private void endChargeEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.stop(var1);
		this.continuousComponent.startContinuity(var1);
		this.animationComponent.start(var1,ModAnimations.DEMONIC_DANCE_LEAP);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.hitTrackerComponent.clearHits();
		if ((this.target) != (null)){
			Vec3 var3 = this.target.position().subtract(var1.position()).normalize().multiply(2D,1D,2D);
			AbilityHelper.setDeltaMovement((Entity)(var1),var3.x,0.5D,var3.z);
		}else{
			Vec3 var3_1 = var1.getLookAngle().multiply(2D,0D,2D);
			AbilityHelper.setDeltaMovement((Entity)(var1),var3_1.x,0.5D,var3_1.z);
		}
	}
	private void tickContinuityEvent(LivingEntity var1, IAbility var2) {
		if (((var1.fallDistance) > (0F)) && (!(this.hasLanded()))){
			Iterator var3 = this.rangeComponent.getTargetsInArea(var1,1.25F,TARGET_TEST).iterator();
			do{
				if (var3.hasNext()){
					LivingEntity var4 = ((LivingEntity)(var3.next()));
					if (this.hitTrackerComponent.canHit((Entity)(var4))){
						ItemsHelper.stopShieldAndStartCooldown(var4,100);
						this.dealDamageComponent.hurtTarget(var1,var4,15F);
						this.setLanded();
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			Iterator var4_1 = this.hitTrackerComponent.getHits().iterator();
			do{
				if (var4_1.hasNext()){
					Entity var5 = ((Entity)(var4_1.next()));
					if ((AbilityHelper.getDifferenceToFloor(var5)) > (1.5D)){
						var5.teleportTo(var1.getX(),(var1.getY()) - (1D),var1.getZ());
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
		this.target = null;
		this.animationComponent.stop(var1);
		this.cooldownComponent.startCooldown(var1,260F);
	}
	public void onLanding(LivingEntity var1) {
		Iterator var2 = this.hitTrackerComponent.getHits().iterator();
		do{
			if (var2.hasNext()){
				Entity var3 = ((Entity)(var2.next()));
				if (var3 instanceof LivingEntity){
					LivingEntity var4 = ((LivingEntity)(var3));
					if (!(this.isStandard)){
						var4.addEffect(new MobEffectInstance((Holder)(ModEffects.UNCONSCIOUS),20,0,false,false));
					}
					this.dealDamageComponent.hurtTarget(var1,var4,15F);
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		ArrayList var3_1 = new ArrayList();
		int var4_1 = (int)(Math.ceil(1.25D));
		int var5 = -(var4_1);
		LOOP_1:
		do{
			if ((var5) < (var4_1)){
				int var6 = -(var4_1);
				do{
					if ((var6) < (var4_1)){
						double var7 = (var1.getX()) + ((double)(var5));
						double var8 = (var1.getY()) - (1D);
						double var9 = (var1.getZ()) + ((double)(var6));
						var3_1.add(new Vec3(var7,var8,var9));
						var6++;
						continue;
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
		if ((((java.util.List)(var3_1)).size()) > (0)){
			this.details.setVecPositions((List)(var3_1));
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.BREAKING_BLOCKS.get())),(Entity)(var1),0D,0D,0D,(ParticleEffect$Details)(this.details));
		}
		this.continuousComponent.stopContinuity(var1);
	}
	public void setStandardMode() {
		this.isStandard = true;
	}
	public void setTarget(Entity var1) {
		this.target = var1;
	}
	public Entity getTarget() {
		return this.target;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			AbilityDescriptionLine$IDescriptionLine[] lv1_2 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(260F),ChargeComponent.getTooltip(40F),RangeComponent.getTooltip(1.25F,RangeComponent$RangeType.AOE),DealDamageComponent.getTooltip(30F)};
			SourceType[] lv1_3 = new SourceType[]{SourceType.BLUNT};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DemonicDanceAbility::new)))).addAdvancedDescriptionLine(lv1_2).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv1_3).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("demonic_dance","Demonic Dance",var0));
	}
}