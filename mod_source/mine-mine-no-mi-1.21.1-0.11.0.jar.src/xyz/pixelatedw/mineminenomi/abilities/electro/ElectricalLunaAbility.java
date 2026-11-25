package xyz.pixelatedw.mineminenomi.abilities.electro;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent$IOnSwingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ChargeComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ProjectileComponentTooltip;
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
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro.ElectricalLunaProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ElectricalLunaAbility extends Ability {
	// Fields
	private static final float COOLDOWN_BONUS = 0.3F;
	private static final float DAMAGE_BONUS = 2F;
	private static final int COOLDOWN = 200;
	private static final int CHARGE_TIME = 40;
	public static final int RANGE = 6;
	private static final int DAMAGE = 40;
	private static final int ELECLAW_STACKS = 2;
	private static final int DORIKI = 3600;
	private static final int MARTIAL_ARTS_POINTS = 10;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<ElectricalLunaAbility>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final ContinuousComponent continuousComponent;
	private final SwingTriggerComponent swingTriggerComponent;
	private final ProjectileComponent projectileComponent;

	public ElectricalLunaAbility(AbilityCore<ElectricalLunaAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::duringChargeEvent)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargeEvent))));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.swingTriggerComponent = new SwingTriggerComponent((IAbility)(this)).addSwingEvent((SwingTriggerComponent$IOnSwingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent$IOnSwingEvent)(this::swingEvent))));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<ElectricalLunaProjectile>) (this::createProjectile))))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.continuousComponent,this.swingTriggerComponent,this.projectileComponent};
		this.addComponents(var2);
		this.addCanUseCheck(ElectroHelper.requireEleclaw(2));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		if ((!(this.chargeComponent.isCharging())) && (!(this.continuousComponent.isContinuous()))){
			this.chargeComponent.startCharging(var1,40F);
			return;
		}else{
			return;
		}
	}
	private void duringChargeEvent(LivingEntity var1, IAbility var2) {
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),5,0,false,false));
		if (((this.chargeComponent.getChargeTime()) % (2F)) == (0F)){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.ELECTRO_CHARGING.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.cooldownComponent.startCooldown(var1,200F);
	}
	private void swingEvent(LivingEntity var1, IAbility var2) {
		if (this.continuousComponent.isContinuous()){
			this.continuousComponent.stopContinuity(var1);
			this.projectileComponent.shoot(var1,3F,0F);
		}
	}
	private ElectricalLunaProjectile createProjectile(LivingEntity var1) {
		ElectricalLunaProjectile var2 = new ElectricalLunaProjectile(var1.level(),var1,this);
		var2.setDamage(40F);
		var2.shootFromRotation((Entity)(var1),var1.getXRot(),var1.getYRot(),0F,3F,0F);
		return var2;
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(11F,7F));
		DorikiUnlockCondition var2 = DorikiUnlockCondition.atLeast(3600D);
		RequiredRaceUnlockCondition var3 = RequiredRaceUnlockCondition.requires(((Race)(ModRaces.MINK.get())));
		NodeUnlockCondition var4 = ((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).and(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(10F)))).or(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(15F))));
		NodeUnlockAction var5 = SubtractPointsAction.martialArts(10F).onlyIf(var3).andThen(SubtractPointsAction.martialArts(15F).onlyIf(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).not())).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE)));
		AbilityNode[] var6 = new AbilityNode[1];
		var6[0] = ((AbilityCore)(ElectricalMissileAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var6);
		var1.setUnlockRule(var4,var5);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv5_2 = new Pair[2];
			((java.lang.Object[]) (lv5_2))[0] = ImmutablePair.of((Object)("Charges a lightning ball in the user's hand which will be used whenever the user swings their arm, shooting the ball of electricity towards their target. On impact the ball will cause a small area of effect that stuns all nearby enemies but only damages the main target."),(Object)(null));
			Object[] lv5_3 = new Object[3];
			lv5_3[0] = MentionHelper.tryMention(SulongAbility.INSTANCE);
			int lv5_4 = Math.round(70F);
			String lv5_5 = "" + lv5_4 + "%";
			lv5_3[1] = MentionHelper.mentionText(lv5_5);
			int lv5_6 = Math.round((Math.abs(-1F)) * (100F));
			String lv5_7 = "" + lv5_6 + "%";
			lv5_3[2] = MentionHelper.mentionText(lv5_7);
			((java.lang.Object[]) (lv5_2))[1] = ImmutablePair.of((Object)("While %s is active the cooldown of this ability is reduced by %s and the damage is increased by %s."),lv5_3);
			Component[] lv5_8 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv5_2);
			Component[] lv5_9 = new Component[]{lv5_8[0]};
			AbilityDescriptionLine$IDescriptionLine[] lv5_10 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine$IDescriptionLine.of(lv5_8[1])};
			AbilityCore$INodeFactory[] lv5_11 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv5_12 = ElectricalLunaAbility::createNode;
			lv5_11[0] = lv5_12;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.RACIAL,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(ElectricalLunaAbility::new)))).addDescriptionLine(lv5_9).addAdvancedDescriptionLine(lv5_10).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.LIGHTNING).setNodeFactories(lv5_11).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("electrical_luna","Electrical Luna",var0));
	}
	private void endChargeEvent(LivingEntity var1, IAbility var2) {
		boolean var3 = ElectroHelper.hasSulongActive(var1);
		this.projectileComponent.getDamageBonusManager().removeBonus(ElectroHelper.SULONG_DAMAGE_BONUS);
		this.cooldownComponent.getBonusManager().removeBonus(ElectroHelper.SULONG_COOLDOWN_BONUS);
		if (var3){
			this.projectileComponent.getDamageBonusManager().addBonus(ElectroHelper.SULONG_DAMAGE_BONUS,"Sulong Damage Bonus",BonusOperation.MUL,2F);
			this.cooldownComponent.getBonusManager().addBonus(ElectroHelper.SULONG_COOLDOWN_BONUS,"Sulong Cooldown Bonus",BonusOperation.MUL,0.3F);
		}
		this.continuousComponent.startContinuity(var1);
		Function<AbilityData, EleclawAbility> var4 = (l0) -> {
			return ((EleclawAbility)(l0.getEquippedAbility(((AbilityCore)(EleclawAbility.INSTANCE.get())))));
		};
		EleclawAbility var5 = ((EleclawAbility)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var4)).orElse(null)));
		if ((var5) != (null)){
			var5.reduceUsage(var1,2);
		}
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(200F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<ChargeComponentTooltip> var4 = (l0) -> {
			l0.charge(40F);
		};
		ChargeComponentTooltip.generate(var2,var4);
		Consumer<ProjectileComponentTooltip> var5 = (l0) -> {
		};
		ProjectileComponentTooltip.generate(var2,var5);
	}
}