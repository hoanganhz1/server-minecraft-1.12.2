package xyz.pixelatedw.mineminenomi.abilities.ope;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.data.entity.CameraLockData;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class InjectionShotAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 200F;
	private static final float DAMAGE = 40F;
	private static final float RANGE = 1.6F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<InjectionShotAbility>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final ContinuousComponent continuousComponent;
	private final DealDamageComponent dealDamageComponent;
	private final AnimationComponent animationComponent;
	private final HitTrackerComponent hitTrackerComponent;
	private final RangeComponent rangeComponent;

	public InjectionShotAbility(AbilityCore<InjectionShotAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addStartEvent((ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::onChargeStart)))).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::onChargeTick)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::onChargeEnd))));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::onContinuityStart)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onContinuityTick)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.hitTrackerComponent = new HitTrackerComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.continuousComponent,this.dealDamageComponent,this.hitTrackerComponent,this.animationComponent,this.rangeComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(RoomAbility::hasRoomActive))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresSword))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		this.addContinueUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(RoomAbility::hasRoomActive))));
		this.addContinueUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresSword))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		if ((!(this.chargeComponent.isCharging())) && (!(this.continuousComponent.isContinuous()))){
			this.chargeComponent.startCharging(var1,20F);
		}
	}
	private void onChargeStart(LivingEntity var1, IAbility var2) {
		this.animationComponent.start(var1,ModAnimations.POINT_WEAPON);
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),100,0));
	}
	private void onChargeTick(LivingEntity var1, IAbility var2) {
		if (var1 instanceof ServerPlayer){
			ServerPlayer var3 = ((ServerPlayer)(var1));
			if (!(CameraLockData.hasCameraLocked((Player)(var3)))){
				CameraLockData.pinFixed((Player)(var3));
			}else{
				return;
			}
		}
	}
	private void onChargeEnd(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			var1.removeEffect((Holder)(ModEffects.MOVEMENT_BLOCKED));
			if (var1 instanceof ServerPlayer){
				CameraLockData.resetLock((Player)(((ServerPlayer)(var1))));
			}
			this.continuousComponent.startContinuity(var1,20F);
			return;
		}
	}
	private void onContinuityStart(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			Vec3 var3 = var1.getLookAngle().multiply(4D,1D,4D);
			AbilityHelper.setDeltaMovement((Entity)(var1),var3.x,0.35D,var3.z);
			this.hitTrackerComponent.clearHits();
			return;
		}
	}
	private void onContinuityTick(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if (var1.isAlive()){
				List var3 = this.rangeComponent.getTargetsInArea(var1,1.6F);
				AbilityDamageSource var4 = this.dealDamageComponent.getDamageSource(var1);
				IDamageSourceHandler.getHandler(var4).addAbilityPiercing(0.5F,this.getCore());
				var3.remove(var1);
				Iterator var5 = var3.iterator();
				do{
					if (var5.hasNext()){
						LivingEntity var6 = ((LivingEntity)(var5.next()));
						if ((this.hitTrackerComponent.canHit((Entity)(var1))) && (this.dealDamageComponent.hurtTarget(var1,var6,40F,var4))){
							var6.addEffect(new MobEffectInstance(MobEffects.CONFUSION,60,0));
							continue;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
			}
			return;
		}
	}
	private void onContinuityEnd(LivingEntity var1, IAbility var2) {
		this.animationComponent.stop(var1);
		this.cooldownComponent.startCooldown(var1,200F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("While holding a weapon, the user charges at the enemy, leaving them confused."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F),RangeComponent.getTooltip(1.6F,RangeComponent$RangeType.LINE),DealDamageComponent.getTooltip(40F)};
			SourceType[] lv1_5 = new SourceType[]{SourceType.SLASH};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(InjectionShotAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(lv1_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("injection_shot","Injection Shot",var0));
	}
}