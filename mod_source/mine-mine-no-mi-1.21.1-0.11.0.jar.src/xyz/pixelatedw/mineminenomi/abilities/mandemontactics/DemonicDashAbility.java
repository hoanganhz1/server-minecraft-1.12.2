package xyz.pixelatedw.mineminenomi.abilities.mandemontactics;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class DemonicDashAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 160;
	private static final float RANGE = 1.6F;
	private static final float DAMAGE = 30F;
	private static final int CHARGE_TIME = 20;
	public static final TargetPredicate TARGET_TEST = new TargetPredicate().testEnemyFaction().testSimpleInView();
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<DemonicDashAbility>> INSTANCE;
	private final HitTrackerComponent hitTrackerComponent;
	private final ChargeComponent chargeComponent;
	private final ContinuousComponent continuousComponent;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;
	private final AnimationComponent animationComponent;
	@Nullable() private LivingEntity target;

	public DemonicDashAbility(AbilityCore<DemonicDashAbility> var1) {
		super((AbilityCore)(var1));
		this.hitTrackerComponent = new HitTrackerComponent((IAbility)(this));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addStartEvent((ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::startChargeEvent)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargeEvent))));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::tickContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.rangeComponent,this.dealDamageComponent,this.animationComponent,this.continuousComponent,this.hitTrackerComponent,this.chargeComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresBluntWeapon))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.chargeComponent.startCharging(var1,20F);
	}
	private void startChargeEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.start(var1,ModAnimations.DEMONIC_DASH);
	}
	private void endChargeEvent(LivingEntity var1, IAbility var2) {
		this.hitTrackerComponent.clearHits();
		Vec3 var3 = null;
		if ((this.target) == (null)){
			var3 = var1.getLookAngle().multiply(5.5D,0D,5.5D);
		}else{
			var3 = var1.position().subtract(this.target.position()).normalize().multiply(5.5D,2.5D,5.5D);
		}
		AbilityHelper.setDeltaMovement((Entity)(var1),var3.x,0.15D,var3.z);
		this.continuousComponent.startContinuity(var1,10F);
	}
	private void tickContinuityEvent(LivingEntity var1, IAbility var2) {
		if (var1.isAlive()){
			Iterator var3 = this.rangeComponent.getTargetsInLine(var1,1.6F,1.5F).iterator();
			do{
				if (var3.hasNext()){
					LivingEntity var4 = ((LivingEntity)(var3.next()));
					if (this.hitTrackerComponent.canHit((Entity)(var4))){
						this.dealDamageComponent.hurtTarget(var1,var4,30F);
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
		this.cooldownComponent.startCooldown(var1,160F);
	}
	public void setTarget(LivingEntity var1) {
		this.target = var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			AbilityDescriptionLine$IDescriptionLine[] lv1_2 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(160F),ChargeComponent.getTooltip(20F),RangeComponent.getTooltip(1.6F,RangeComponent$RangeType.LINE),DealDamageComponent.getTooltip(30F)};
			SourceType[] lv1_3 = new SourceType[]{SourceType.BLUNT};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DemonicDashAbility::new)))).addAdvancedDescriptionLine(lv1_2).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv1_3).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("demonic_dash","Demonic Dash",var0));
	}
}