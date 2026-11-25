package xyz.pixelatedw.mineminenomi.abilities.doctor;

import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HealComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$HitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ContinuousComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.HealComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.data.entity.CameraLockData;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class FirstAidAbility extends Ability {
	// Fields
	private static final int MIN_HEAL = 5;
	private static final int MAX_HEAL = 50;
	private static final float HOLD_TIME = 1000F;
	private static final float MIN_COOLDOWN = 300F;
	private static final float MAX_COOLDOWN = 1300F;
	private static final int NODE_POINTS_TE = 3;
	private final HealComponent healComponent;
	private final ContinuousComponent continuousComponent;
	private final HitTriggerComponent hitTriggerComponent;
	private final DamageTakenComponent damageTakenComponent;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<FirstAidAbility>> INSTANCE;
	private LivingEntity target;
	private Interval particleInterval;

	public FirstAidAbility(AbilityCore<FirstAidAbility> var1) {
		super((AbilityCore)(var1));
		this.healComponent = new HealComponent((IAbility)(this));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.hitTriggerComponent = new HitTriggerComponent((IAbility)(this)).addTryHitEvent((HitTriggerComponent$ITryHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent)(this::tryHitEvent)))).addOnHitEvent((HitTriggerComponent$IOnHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent)(this::hitEvent))));
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnDamageEvent((DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::onDamageTaken))));
		this.particleInterval = Interval.startAtMax(20);
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.hitTriggerComponent,this.healComponent,this.damageTakenComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresMedicBag))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		if (var1.isCrouching()){
			this.target = var1;
			this.continuousComponent.startContinuity(var1,1000F);
		}else{
			this.continuousComponent.triggerContinuity(var1);
		}
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.particleInterval.restartIntervalToZero();
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if ((this.target) == (null)){
			return;
		}else{
			if (this.continuousComponent.isInfinite()){
				this.continuousComponent.setThresholdTime(var1,1000F);
				return;
			}else{
				double var3 = var1.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE);
				if ((this.target.isAlive()) && ((!(this.target.isRemoved())) && (!(((double)(var1.distanceTo((Entity)(this.target)))) > (var3))))){
					if ((this.target) != (var1)){
						var1.lookAt(EntityAnchorArgument.Anchor.EYES,this.target.position());
					}
					var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),5,1));
					if (this.particleInterval.canTick()){
						this.applyFirstAid(var1,this.target,this);
					}
					return;
				}else{
					this.continuousComponent.stopContinuity(var1);
					return;
				}
			}
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.target = null;
		if (var1 instanceof ServerPlayer){
			CameraLockData.resetLock((Player)(((ServerPlayer)(var1))));
		}
		this.cooldownComponent.startCooldown(var1,(300F) + (this.continuousComponent.getContinueTime()));
	}
	private HitTriggerComponent$HitResult tryHitEvent(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		if ((this.continuousComponent.isContinuous()) && ((this.continuousComponent.isInfinite()) && (var1.getMainHandItem().isEmpty()))){
			this.target = var2;
			return HitTriggerComponent$HitResult.HIT;
		}else{
			return HitTriggerComponent$HitResult.PASS;
		}
	}
	private boolean hitEvent(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		return true;
	}
	private float onDamageTaken(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		if ((this.continuousComponent.isContinuous()) && ((var4) >= (10F))){
			this.continuousComponent.stopContinuity(var1);
		}
		return var4;
	}
	private void applyFirstAid(LivingEntity var1, LivingEntity var2, FirstAidAbility var3) {
		if (!(ItemsHelper.hasItemInSlot(var1,EquipmentSlot.CHEST,((Item)(ModArmors.MEDIC_BAG.get()))))){
			return;
		}else{
			float var4 = (float)(WyHelper.percentage(1D,(double)(var2.getMaxHealth())));
			var3.healComponent.healTarget(var1,var2,var4);
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.FIRST_AID.get())),(Entity)(var2),var2.getX(),var2.getY(),var2.getZ());
			if ((var2.getHealth()) >= (var2.getMaxHealth())){
				var3.continuousComponent.stopContinuity(var1);
			}
			return;
		}
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(-9F,-4F));
		RequiredStyleUnlockCondition var2 = RequiredStyleUnlockCondition.requires(((FightingStyle)(ModFightingStyles.DOCTOR.get())));
		NodeUnlockCondition var3 = ((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).or((NodeUnlockCondition)(TrainingPointsUnlockCondition.technology(3F)));
		var1.setUnlockRule(var3,SubtractPointsAction.technology(3F).onlyIf(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).not()).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE))));
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv4_2 = new Pair[]{ImmutablePair.of((Object)("Heals the user by shift-clicking or an ally by punching them."),(Object)(null))};
			Component[] lv4_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv4_2);
			SourceType[] lv4_4 = new SourceType[]{SourceType.FIST,SourceType.FRIENDLY};
			AbilityCore$INodeFactory[] lv4_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv4_6 = FirstAidAbility::createNode;
			lv4_5[0] = lv4_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(FirstAidAbility::new)))).addDescriptionLine(lv4_3).setSourceType(lv4_4).setNodeFactories(lv4_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("first_aid","First Aid",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(300F,1300F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<ContinuousComponentTooltip> var4 = (l0) -> {
			l0.holdTime(1000F);
		};
		ContinuousComponentTooltip.generate(var2,var4);
		Consumer<HealComponentTooltip> var5 = (l0) -> {
			l0.heal(5F,50F);
		};
		HealComponentTooltip.generate(var2,var5);
	}
}