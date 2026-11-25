package xyz.pixelatedw.mineminenomi.abilities.goe;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.CameraLockData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.goe.TodorokiProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class TodorokiAbility extends Ability {
	// Fields
	public static final int SCREAM_TIME = 20;
	public static final float DAMAGE = 15F;
	private static final int CHARGE_TIME = 5;
	private static final float COOLDOWN = 160F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<TodorokiAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final ChargeComponent chargeComponent;
	private final AnimationComponent animationComponent;
	private final ProjectileComponent projectileComponent;

	public TodorokiAbility(AbilityCore<TodorokiAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::tickContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addEndEvent(100,(ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargeEvent))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<TodorokiProjectile>) (this::createProjectile))))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.animationComponent,this.continuousComponent,this.projectileComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.chargeComponent.startCharging(var1,5F);
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),25,0));
		this.animationComponent.start(var1,ModAnimations.SCREAM,25);
	}
	private void endChargeEvent(LivingEntity var1, IAbility var2) {
		TodorokiProjectile var3 = ((TodorokiProjectile)(this.projectileComponent.getNewProjectile(var1)));
		TodorokiProjectile var4 = ((TodorokiProjectile)(this.projectileComponent.getNewProjectile(var1)));
		var4.setOuterRings();
		this.projectileComponent.shoot(var3,var1,var1.getXRot(),var1.getYRot(),1F,1F);
		this.projectileComponent.shoot(var4,var1,var1.getXRot(),var1.getYRot(),1F,1F);
		this.continuousComponent.startContinuity(var1,20F);
	}
	private void tickContinuityEvent(LivingEntity var1, IAbility var2) {
		if (var1 instanceof ServerPlayer){
			ServerPlayer var3 = ((ServerPlayer)(var1));
			if (!(CameraLockData.hasCameraLocked((Player)(var3)))){
				CameraLockData.pinFixed((Player)(var3));
			}else{
				return;
			}
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		if (var1 instanceof ServerPlayer){
			CameraLockData.resetLock((Player)(((ServerPlayer)(var1))));
		}
		this.cooldownComponent.startCooldown(var1,160F);
	}
	private TodorokiProjectile createProjectile(LivingEntity var1) {
		return new TodorokiProjectile(var1.level(),var1,(IAbility)(this));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("The user shouts and creates a powerful sound blast."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(160F)};
			AbilityCore$IAbilityGoalFactory[] lv1_5 = new AbilityCore$IAbilityGoalFactory[1];
			AbilityCore$IAbilityGoalFactory lv1_6 = ProjectileAbilityWrapperGoal::new;
			lv1_5[0] = lv1_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(TodorokiAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.SHOCKWAVE).addGoalWrapper(lv1_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("todoroki","Todoroki",var0));
	}
}