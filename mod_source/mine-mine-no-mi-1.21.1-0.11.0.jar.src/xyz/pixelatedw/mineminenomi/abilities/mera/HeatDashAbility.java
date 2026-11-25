package xyz.pixelatedw.mineminenomi.abilities.mera;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class HeatDashAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 200;
	private static final int ON_HOLD = 15;
	private static final float RANGE = 1.4F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<HeatDashAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final HitTrackerComponent hitTrackerComponent;
	private final RangeComponent rangeComponent;
	private final DashComponent dashComponent;
	private final Interval particleInterval;

	public HeatDashAbility(AbilityCore<HeatDashAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::onContinuityStart)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onContinuityTick)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		this.hitTrackerComponent = new HitTrackerComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dashComponent = new DashComponent((IAbility)(this));
		this.particleInterval = new Interval(2);
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.hitTrackerComponent,this.rangeComponent,this.dashComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(MeraHelper::canUseMeraAbilities))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.startContinuity(var1);
	}
	private void onContinuityStart(LivingEntity var1, IAbility var2) {
		this.hitTrackerComponent.clearHits();
		this.particleInterval.restartIntervalToZero();
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.MERA_SFX.get())),SoundSource.PLAYERS,2F,(0.5F) + ((this.random.nextFloat()) / (3F)));
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
					WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.HEAT_DASH.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
				}
				Iterator var3 = this.rangeComponent.getTargetsInArea(var1,1.4F).iterator();
				do{
					if (var3.hasNext()){
						LivingEntity var4 = ((LivingEntity)(var3.next()));
						if ((this.hitTrackerComponent.canHit((Entity)(var4))) && (var1.hasLineOfSight((Entity)(var4)))){
							var4.igniteForSeconds(2F);
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
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Transforms the user into fire and launches them forward, setting on fire all enemies around the user."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F),ContinuousComponent.getTooltip(15F),RangeComponent.getTooltip(1.4F,RangeComponent$RangeType.AOE)};
			AbilityCore$IAbilityGoalFactory[] lv1_5 = new AbilityCore$IAbilityGoalFactory[1];
			AbilityCore$IAbilityGoalFactory lv1_6 = AntiAirAbilityWrapperGoal::new;
			lv1_5[0] = lv1_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(HeatDashAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).addGoalWrapper(lv1_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("heat_dash","Heat Dash",var0));
	}
}