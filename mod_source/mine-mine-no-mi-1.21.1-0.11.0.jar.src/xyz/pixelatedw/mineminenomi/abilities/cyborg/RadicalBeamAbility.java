package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ColaUsageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ChargeComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ColaUsageComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ExplosionComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ProjectileComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.RadicalBeamProjectile;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class RadicalBeamAbility extends Ability {
	// Fields
	private static final float CHARGE_TIME = 60F;
	private static final float COOLDOWN = 260F;
	private static final int COLA_REQUIRED = 30;
	public static final float DAMAGE = 50F;
	private static final int ACCURACY_POINTS = 5;
	private static final int TECHNOLOGY_POINTS = 5;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<RadicalBeamAbility>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final AnimationComponent animationComponent;
	private final ExplosionComponent explosionComponent;
	private final ColaUsageComponent colaUsageComponent;
	private final ProjectileComponent projectileComponent;

	public RadicalBeamAbility(AbilityCore<RadicalBeamAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addStartEvent(100,(ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::startChargingEvent)))).addTickEvent(100,(ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::tickChargingEvent)))).addEndEvent(100,(ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargingEvent))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.explosionComponent = new ExplosionComponent((IAbility)(this),1508);
		this.colaUsageComponent = new ColaUsageComponent((IAbility)(this));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<RadicalBeamProjectile>) (this::createProjectile))))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.animationComponent,this.explosionComponent,this.colaUsageComponent,this.projectileComponent};
		this.addComponents(var2);
		this.addCanUseCheck(ColaUsageComponent.hasEnoughCola(30));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.chargeComponent.startCharging(var1,60F);
	}
	private void startChargingEvent(LivingEntity var1, IAbility var2) {
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.CHARGE_CYBORG_BEAM_SFX.get())),SoundSource.PLAYERS,2F,1F);
	}
	private void tickChargingEvent(LivingEntity var1, IAbility var2) {
		if ((this.chargeComponent.getChargeTime()) == (39F)){
			var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.PRE_CYBORG_BEAM_SFX.get())),SoundSource.PLAYERS,2F,1F);
		}
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.CHARGE_RADICAL_BEAM.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
	}
	private void endChargingEvent(LivingEntity var1, IAbility var2) {
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.CYBORG_BEAM_SFX.get())),SoundSource.PLAYERS,3F,1F);
		this.projectileComponent.shoot(var1);
		this.colaUsageComponent.consumeCola(var1,30);
		this.cooldownComponent.startCooldown(var1,260F);
	}
	private RadicalBeamProjectile createProjectile(LivingEntity var1) {
		return new RadicalBeamProjectile(var1.level(),var1,(IAbility)(this));
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(15F,-6F));
		NodeUnlockCondition var2 = RequiredRaceUnlockCondition.requires(((Race)(ModRaces.CYBORG.get()))).and(TrainingPointsUnlockCondition.technology(5F)).and(TrainingPointsUnlockCondition.accuracy(5F));
		NodeUnlockAction var3 = SubtractPointsAction.technology(5F).andThen(SubtractPointsAction.accuracy(5F)).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE)));
		AbilityNode[] var4 = new AbilityNode[1];
		var4[0] = ((AbilityCore)(ColaFuelAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var4);
		var1.setUnlockRule(var2,var3);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv6_2 = new Pair[]{ImmutablePair.of((Object)("The user launches a powerful beam of energy at the opponent."),(Object)(null))};
			Component[] lv6_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv6_2);
			SourceType[] lv6_4 = new SourceType[]{SourceType.TECHNOLOGY};
			AbilityCore$INodeFactory[] lv6_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv6_6 = RadicalBeamAbility::createNode;
			lv6_5[0] = lv6_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.RACIAL,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(RadicalBeamAbility::new)))).addDescriptionLine(lv6_3).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.LIGHT).setSourceType(lv6_4).setNodeFactories(lv6_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("radical_beam","Radical Beam",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(260F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<ChargeComponentTooltip> var4 = (l0) -> {
			l0.charge(60F);
		};
		ChargeComponentTooltip.generate(var2,var4);
		Consumer<ColaUsageComponentTooltip> var5 = (l0) -> {
			l0.cola(30F);
		};
		ColaUsageComponentTooltip.generate(var2,var5);
		Consumer<ProjectileComponentTooltip> var6 = (l0) -> {
		};
		ProjectileComponentTooltip.generate(var2,var6);
		Consumer<ExplosionComponentTooltip> var7 = (l0) -> {
			l0.range(4F);
		};
		ExplosionComponentTooltip.generate(var2,var7);
	}
}