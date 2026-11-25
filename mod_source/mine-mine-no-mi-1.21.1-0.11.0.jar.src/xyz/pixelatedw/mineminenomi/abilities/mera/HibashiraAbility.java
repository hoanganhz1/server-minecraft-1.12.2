package xyz.pixelatedw.mineminenomi.abilities.mera;

import java.util.Iterator;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
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
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AreaAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.mera.HibashiraParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.mera.HibashiraParticleEffect$Details;

public class HibashiraAbility extends Ability {
	// Fields
	private static final int ON_HOLD = 100;
	private static final int MIN_COOLDOWN = 200;
	private static final int MAX_COOLDOWN = 250;
	public static final double PILLAR_SIZE = 3.5D;
	private static final float DAMAGE = 5F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<HibashiraAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final HitTrackerComponent hitTrackerComponent;
	private final DealDamageComponent dealDamageComponent;
	private final Interval particleInterval;
	private final Interval clearHitsInterval;

	public HibashiraAbility(AbilityCore<HibashiraAbility> var1) {
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
		this.hitTrackerComponent.clearHits();
		this.particleInterval.restartIntervalToZero();
	}
	private void onContinuityTick(LivingEntity var1, IAbility var2) {
		if (this.canUse(var1).isFail()){
			this.continuousComponent.stopContinuity(var1);
		}
		if (this.particleInterval.canTick()){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.HIBASHIRA.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ(),(ParticleEffect$Details)(HibashiraParticleEffect.NO_DETAILS));
		}
		List var3 = TargetHelper.getEntitiesInArea((LevelAccessor)(var1.level()),var1,var1.position(),3.5D,10D,3.5D,TargetPredicate.DEFAULT_AREA_CHECK,LivingEntity.class);
		AbilityDamageSource var4 = this.dealDamageComponent.getDamageSource(var1);
		IDamageSourceHandler.getHandler(var4).setUnavoidable();
		Iterator var5 = var3.iterator();
		do{
			if (var5.hasNext()){
				LivingEntity var6 = ((LivingEntity)(var5.next()));
				if ((this.hitTrackerComponent.canHit((Entity)(var6))) && (this.dealDamageComponent.hurtTarget(var1,var6,5F,var4))){
					var6.igniteForSeconds(4F);
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
		AbilityHelper.slowEntityFall(var1);
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),5,1,false,false));
	}
	private void onContinuityEnd(LivingEntity var1, IAbility var2) {
		float var3 = (200F) + ((this.continuousComponent.getContinueTime()) / (2F));
		this.cooldownComponent.startCooldown(var1,var3);
	}
	public static AbilityWrapperGoal<Mob, HibashiraAbility> createGuardWrapper(Mob var0, AbilityCore<HibashiraAbility> var1) {
		return new ActiveGuardAbilityWrapperGoal(var0,var1);
	}
	public static AbilityWrapperGoal<Mob, HibashiraAbility> createAOEWrapper(Mob var0, AbilityCore<HibashiraAbility> var1) {
		return new AreaAbilityWrapperGoal(var0,var1).setMaxDistance(20F).setMinTargetsRequired(2);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Creates a fire pillar extending both upwards and downwards, burning every enemy within it"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F,250F),ContinuousComponent.getTooltip(100F),DealDamageComponent.getTooltip(5F)};
			AbilityCore$IAbilityGoalFactory[] lv1_5 = new AbilityCore$IAbilityGoalFactory[2];
			AbilityCore$IAbilityGoalFactory lv1_6 = HibashiraAbility::createGuardWrapper;
			lv1_5[0] = lv1_6;
			AbilityCore$IAbilityGoalFactory lv1_7 = HibashiraAbility::createAOEWrapper;
			lv1_5[1] = lv1_7;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(HibashiraAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.FIRE).addGoalWrapper(lv1_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("hibashira","Hibashira",var0));
	}
}