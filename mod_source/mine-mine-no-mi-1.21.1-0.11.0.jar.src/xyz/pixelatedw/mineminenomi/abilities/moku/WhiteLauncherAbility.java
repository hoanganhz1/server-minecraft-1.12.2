package xyz.pixelatedw.mineminenomi.abilities.moku;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.DashComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AntiAirAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class WhiteLauncherAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 200;
	private static final float RANGE = 1.6F;
	private static final float DAMAGE = 15F;
	public static final int ON_HOLD = 15;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<WhiteLauncherAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final HitTrackerComponent hitTrackerComponent;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;
	private final DashComponent dashComponent;
	private final Interval particleInterval;

	public WhiteLauncherAbility(AbilityCore<WhiteLauncherAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::onContinuityStart)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onContinuityTick)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		this.hitTrackerComponent = new HitTrackerComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.dashComponent = new DashComponent((IAbility)(this));
		this.particleInterval = new Interval(2);
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.hitTrackerComponent,this.rangeComponent,this.dealDamageComponent,this.dashComponent};
		super.addComponents(var2);
		super.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		super.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.startContinuity(var1);
	}
	private void onContinuityStart(LivingEntity var1, IAbility var2) {
		this.particleInterval.restartIntervalToZero();
		this.hitTrackerComponent.clearHits();
	}
	private void onContinuityTick(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if ((this.continuousComponent.getContinueTime()) > (15F)){
				if (var1.onGround()){
					this.continuousComponent.stopContinuity(var1);
				}
				return;
			}else{
				if (super.canUse(var1).isFail()){
					this.continuousComponent.stopContinuity(var1);
				}
				this.dashComponent.dashXYZ(var1,3F);
				if (this.particleInterval.canTick()){
					WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.WHITE_LAUNCHER.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
				}
				Iterator var3 = this.rangeComponent.getTargetsInArea(var1,1.6F).iterator();
				do{
					if (var3.hasNext()){
						LivingEntity var4 = ((LivingEntity)(var3.next()));
						if ((this.hitTrackerComponent.canHit((Entity)(var4))) && (var1.hasLineOfSight((Entity)(var4)))){
							this.dealDamageComponent.hurtTarget(var1,var4,15F);
							continue;
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
		this.cooldownComponent.startCooldown(var1,200F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Transforms the user into smoke and launches them forward"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F),ContinuousComponent.getTooltip(15F),RangeComponent.getTooltip(1.6F,RangeComponent$RangeType.AOE),DealDamageComponent.getTooltip(15F)};
			AbilityCore$IAbilityGoalFactory[] lv1_5 = new AbilityCore$IAbilityGoalFactory[1];
			AbilityCore$IAbilityGoalFactory lv1_6 = AntiAirAbilityWrapperGoal::new;
			lv1_5[0] = lv1_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(WhiteLauncherAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).addGoalWrapper(lv1_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("white_launcher","White Launcher",var0));
	}
}