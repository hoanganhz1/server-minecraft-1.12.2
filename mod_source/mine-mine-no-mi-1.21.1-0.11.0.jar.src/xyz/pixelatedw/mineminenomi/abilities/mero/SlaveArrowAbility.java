package xyz.pixelatedw.mineminenomi.abilities.mero;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterStopEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterTriggerEvent;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mero.SlaveArrowProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SlaveArrowAbility extends Ability {
	// Fields
	private static final int CHARGE_TIME = 60;
	private static final int COOLDOWN = 260;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SlaveArrowAbility>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final ContinuousComponent continuousComponent;
	private final RepeaterComponent repeaterComponent;
	private final ProjectileComponent projectileComponent;
	private final AnimationComponent animationComponent;

	public SlaveArrowAbility(AbilityCore<SlaveArrowAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addStartEvent((ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::startChargingEvent)))).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::duringChargingEvent)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargingEvent))));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.repeaterComponent = new RepeaterComponent((IAbility)(this)).addStopEvent((RepeaterComponent$IRepeaterStopEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterStopEvent)(this::endRepeaterEvent)))).addTriggerEvent((RepeaterComponent$IRepeaterTriggerEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterTriggerEvent)(this::triggerRepeaterEvent))));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<SlaveArrowProjectile>) (this::createProjectile))))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.continuousComponent,this.repeaterComponent,this.projectileComponent,this.animationComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.chargeComponent.startCharging(var1,60F);
	}
	private void startChargingEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.start(var1,ModAnimations.AIM_SNIPER);
		Vec3 var3 = var1.getLookAngle().normalize().scale(2.5D);
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.SLAVE_ARROW.get())),(Entity)(var1),(var1.getX()) + (var3.x),((var1.getY()) + (0.25D)) + (var3.y),(var1.getZ()) + (var3.z));
	}
	private void duringChargingEvent(LivingEntity var1, IAbility var2) {
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),5,1,false,false));
	}
	private void endChargingEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.repeaterComponent.start(var1,10,2);
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.stop(var1);
		this.cooldownComponent.startCooldown(var1,260F);
	}
	private void triggerRepeaterEvent(LivingEntity var1, IAbility var2) {
		this.projectileComponent.shoot(var1,3F,4F);
	}
	private void endRepeaterEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.stopContinuity(var1);
	}
	private SlaveArrowProjectile createProjectile(LivingEntity var1) {
		return new SlaveArrowProjectile(var1.level(),var1,(Ability)(this));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Creates a big heart from which the user shoots multiple love arrows, petrifying enemies."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(260F),ChargeComponent.getTooltip(60F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SlaveArrowAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("slave_arrow","Slave Arrow",var0));
	}
}