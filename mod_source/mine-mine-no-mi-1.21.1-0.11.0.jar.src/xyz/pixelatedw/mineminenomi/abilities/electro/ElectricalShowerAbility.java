package xyz.pixelatedw.mineminenomi.abilities.electro;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterStopEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterTriggerEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ChargeComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ProjectileComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.RepeaterComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.entities.LightningDischargeEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro.ElectricalShowerProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ElectricalShowerAbility extends Ability {
	// Fields
	private static final float COOLDOWN_BONUS = 0.3F;
	private static final float DAMAGE_BONUS = 1.25F;
	private static final int COOLDOWN = 240;
	private static final int CHARGE_TIME = 60;
	private static final int DAMAGE = 20;
	private static final int ELECLAW_STACKS = 2;
	private static final int REPEATER_TRIGGERS = 10;
	private static final int REPEATER_INTERVAL = 5;
	private static final int DORIKI = 7000;
	private static final int MARTIAL_ARTS_POINTS = 10;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<ElectricalShowerAbility>> INSTANCE;
	private final StackComponent stackComponent;
	private final ChargeComponent chargeComponent;
	private final DamageTakenComponent damageTakenComponent;
	private final ProjectileComponent projectileComponent;
	private final ContinuousComponent continuousComponent;
	private final RepeaterComponent repeaterComponent;
	private LightningDischargeEntity ballEntity;
	 boolean hasFallDamage;

	public ElectricalShowerAbility(AbilityCore<ElectricalShowerAbility> var1) {
		super((AbilityCore)(var1));
		this.stackComponent = new StackComponent((IAbility)(this));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::duringChargeEvent)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargeEvent))));
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnAttackEvent((DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::onDamageTaken))));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<ElectricalShowerProjectile>) (this::createProjectile))))));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.repeaterComponent = new RepeaterComponent((IAbility)(this)).addTriggerEvent((RepeaterComponent$IRepeaterTriggerEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterTriggerEvent)(this::triggerRepeaterEvent)))).addStopEvent((RepeaterComponent$IRepeaterStopEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterStopEvent)(this::stopRepeaterEvent))));
		this.ballEntity = null;
		this.hasFallDamage = true;
		AbilityComponent[] var2 = new AbilityComponent[]{this.stackComponent,this.chargeComponent,this.damageTakenComponent,this.projectileComponent,this.continuousComponent,this.repeaterComponent};
		this.addComponents(var2);
		this.addCanUseCheck(ElectroHelper.requireEleclaw(2));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		if (this.isContinuous()){
			this.continuousComponent.stopContinuity(var1);
			return;
		}else{
			this.chargeComponent.startCharging(var1,60F);
			return;
		}
	}
	private void duringChargeEvent(LivingEntity var1, IAbility var2) {
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),5,0,false,false));
		if (((this.chargeComponent.getChargeTime()) % (5F)) == (0F)){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.ELECTRO_CHARGING.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
		}
		float var3 = (1F) - ((this.chargeComponent.getChargeTime()) / (this.chargeComponent.getMaxChargeTime()));
		if ((this.ballEntity) == (null)){
			LightningDischargeEntity var4 = new LightningDischargeEntity((Entity)(var1),var1.getX(),var1.getY(),var1.getZ(),var1.getYRot(),var1.getXRot());
			this.ballEntity = var4;
		}else{
			float var4_1 = (var3) * (2F);
			Vec3 var5 = var1.getLookAngle();
			double var6 = (var1.getX()) + ((var5.x) * ((double)(var4_1)));
			double var7 = ((var1.getEyeY()) * (0.85D)) + ((var5.y) * ((double)(var4_1)));
			double var8 = (var1.getZ()) + ((var5.z) * ((double)(var4_1)));
			Vec3 var9 = new Vec3(var6,var7,var8);
			this.ballEntity.setSize((var3) * (0.3F));
			this.ballEntity.setLightningLength(3F);
			this.ballEntity.moveTo(var9.x(),var9.y(),var9.z(),var1.getYRot(),var1.getXRot());
		}
		if (((double)(var3)) > (0.65D)){
			Vec3 var4_2 = var1.position();
			if (var1.level().clip(new ClipContext(var4_2,var4_2.add(0D,-15D,0D),ClipContext.Block.COLLIDER,ClipContext.Fluid.NONE,(Entity)(var1))).getType().equals(HitResult.Type.BLOCK)){
				AbilityHelper.setDeltaMovement((Entity)(var1),var1.getDeltaMovement().x,1D,var1.getDeltaMovement().z);
			}
		}
		AbilityHelper.slowEntityFall(var1);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.hasFallDamage = false;
		this.repeaterComponent.start(var1,10,5);
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		AbilityHelper.slowEntityFall(var1);
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.cooldownComponent.startCooldown(var1,240F);
	}
	private void triggerRepeaterEvent(LivingEntity var1, IAbility var2) {
		this.projectileComponent.shoot(var1,3F,2F);
		var1.swing(InteractionHand.MAIN_HAND,true);
	}
	private void stopRepeaterEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.stopContinuity(var1);
	}
	private float onDamageTaken(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		if ((!(this.hasFallDamage)) && (var3.is(DamageTypes.FALL))){
			this.hasFallDamage = true;
			return 0F;
		}else{
			return var4;
		}
	}
	private ElectricalShowerProjectile createProjectile(LivingEntity var1) {
		ElectricalShowerProjectile var2 = new ElectricalShowerProjectile(var1.level(),var1,(IAbility)(this));
		var2.setDamage(20F);
		var2.shootFromRotation((Entity)(var1),var1.getXRot(),var1.getYRot(),0F,3F,0F);
		return var2;
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(11F,11F));
		DorikiUnlockCondition var2 = DorikiUnlockCondition.atLeast(7000D);
		RequiredRaceUnlockCondition var3 = RequiredRaceUnlockCondition.requires(((Race)(ModRaces.MINK.get())));
		NodeUnlockCondition var4 = ((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).and(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(10F)))).or(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(15F))));
		NodeUnlockAction var5 = SubtractPointsAction.martialArts(10F).onlyIf(var3).andThen(SubtractPointsAction.martialArts(15F).onlyIf(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).not())).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE)));
		AbilityNode[] var6 = new AbilityNode[1];
		var6[0] = ((AbilityCore)(ElectricalTempestaAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var6);
		var1.setUnlockRule(var4,var5);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv6_2 = new Pair[2];
			((java.lang.Object[]) (lv6_2))[0] = ImmutablePair.of((Object)("Launches the user into the air and showers down lightning bolts underneath."),(Object)(null));
			Object[] lv6_3 = new Object[3];
			lv6_3[0] = MentionHelper.tryMention(SulongAbility.INSTANCE);
			int lv6_4 = Math.round(70F);
			String lv6_5 = "" + lv6_4 + "%";
			lv6_3[1] = MentionHelper.mentionText(lv6_5);
			int lv6_6 = Math.round((Math.abs(-0.25F)) * (100F));
			String lv6_7 = "" + lv6_6 + "%";
			lv6_3[2] = MentionHelper.mentionText(lv6_7);
			((java.lang.Object[]) (lv6_2))[1] = ImmutablePair.of((Object)("While %s is active the cooldown of this ability is reduced by %s and the damage is increased by %s."),lv6_3);
			Component[] lv6_8 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv6_2);
			Component[] lv6_9 = new Component[]{lv6_8[0]};
			AbilityDescriptionLine$IDescriptionLine[] lv6_10 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine$IDescriptionLine.of(lv6_8[1])};
			AbilityCore$INodeFactory[] lv6_11 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv6_12 = ElectricalShowerAbility::createNode;
			lv6_11[0] = lv6_12;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.RACIAL,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(ElectricalShowerAbility::new)))).addDescriptionLine(lv6_9).addAdvancedDescriptionLine(lv6_10).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.LIGHTNING).setNodeFactories(lv6_11).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("electrical_shower","Electrical Shower",var0));
	}
	private void endChargeEvent(LivingEntity var1, IAbility var2) {
		if ((this.ballEntity) != (null)){
			this.ballEntity.discard();
			this.ballEntity = null;
		}
		Function<AbilityData, EleclawAbility> var3 = (l0) -> {
			return ((EleclawAbility)(l0.getEquippedAbility(((AbilityCore)(EleclawAbility.INSTANCE.get())))));
		};
		EleclawAbility var4 = ((EleclawAbility)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var3)).orElse(null)));
		if ((var4) != (null)){
			var4.reduceUsage(var1,2);
		}
		this.continuousComponent.startContinuity(var1);
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(240F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<ChargeComponentTooltip> var4 = (l0) -> {
			l0.charge(60F);
		};
		ChargeComponentTooltip.generate(var2,var4);
		Consumer<ProjectileComponentTooltip> var5 = (l0) -> {
		};
		ProjectileComponentTooltip.generate(var2,var5);
		Consumer<RepeaterComponentTooltip> var6 = (l0) -> {
			l0.triggers(10,5F);
		};
		RepeaterComponentTooltip.generate(var2,var6);
	}
}