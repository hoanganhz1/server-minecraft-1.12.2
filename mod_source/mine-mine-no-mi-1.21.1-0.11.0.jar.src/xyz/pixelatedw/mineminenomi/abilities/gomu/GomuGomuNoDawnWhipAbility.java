package xyz.pixelatedw.mineminenomi.abilities.gomu;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IVisibilityPredicate;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GomuGomuNoDawnWhipAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 140;
	private static final int HOLD_TIME = 100;
	private static final float RANGE = 2.5F;
	private static final int DAMAGE = 30;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<GomuGomuNoDawnWhipAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final HitTrackerComponent hitTrackerComponent;
	private final AnimationComponent animationComponent;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;

	public GomuGomuNoDawnWhipAbility(AbilityCore<GomuGomuNoDawnWhipAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.hitTrackerComponent = new HitTrackerComponent((IAbility)(this));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.hitTrackerComponent,this.animationComponent,this.rangeComponent,this.dealDamageComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(GomuHelper::requiresGearFifth))));
		this.addContinueUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(GomuHelper::requiresGearFifth))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,100F);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.hitTrackerComponent.clearHits();
		this.animationComponent.start(var1,ModAnimations.GOMU_DAWN_WHIP);
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if (((this.continuousComponent.getContinueTime()) % (20F)) == (0F)){
			this.hitTrackerComponent.clearHits();
		}
		Vec3 var3 = var1.getLookAngle().multiply(1.35D,1D,1.35D);
		AbilityHelper.setDeltaMovement((Entity)(var1),var3.x,var1.getDeltaMovement().y,var3.z);
		Iterator var4 = this.rangeComponent.getTargetsInArea(var1,2.5F).iterator();
		do{
			if (var4.hasNext()){
				LivingEntity var5 = ((LivingEntity)(var4.next()));
				if (this.hitTrackerComponent.canHit((Entity)(var5))){
					this.dealDamageComponent.hurtTarget(var1,var5,30F);
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.stop(var1);
		this.cooldownComponent.startCooldown(var1,140F);
	}
	private static boolean canUnlock(LivingEntity var0) {
		return ((Boolean)(DevilFruitData.get(var0).map((Function)(((Function<DevilFruitData, Boolean>)(DevilFruitData::hasAwakenedFruit)))).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Launches the user forward hitting everybody in their path."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(140F),ContinuousComponent.getTooltip(100F),RangeComponent.getTooltip(2.5F,RangeComponent$RangeType.AOE),DealDamageComponent.getTooltip(30F)};
			SourceType[] lv1_5 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GomuGomuNoDawnWhipAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv1_5).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(GomuGomuNoDawnWhipAbility::canUnlock)))).setHidden((AbilityCore$IVisibilityPredicate)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IVisibilityPredicate)(ServerConfig::hasAwakeningsEnabled)))).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("gomu_gomu_no_dawn_whip","Gomu Gomu no Dawn Whip",var0));
	}
}