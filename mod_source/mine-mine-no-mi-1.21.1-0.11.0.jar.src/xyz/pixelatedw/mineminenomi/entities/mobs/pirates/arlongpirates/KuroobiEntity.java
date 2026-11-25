package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.arlongpirates;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.TakedownKickAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.AntiMannerKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.PartyTableKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SpinningBrawlAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SuplexAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.FishmanPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.KachiageHaisokuAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.KarakusagawaraSeikenAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.SamehadaShoteiAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInfo;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.IPhasesEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStartPhaseEvent;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStopPhaseEvent;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.DeadzoneRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.IRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.PhysicalHitRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.controllers.HumanoidSwimMoveControl;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.FactionHurtByTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AntiAirAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.GrabAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class KuroobiEntity extends OPBossEntity {
	// Fields
	private static final UUID STANDARD_SUPLEX_COOLDOWN_UUID = UUID.fromString("91faa9f6-2082-4c39-acee-bd1468241742");
	private static final UUID HARD_SAMEHADA_COOLDOWN_UUID = UUID.fromString("de69810e-d16f-49d6-8951-240215fe6582");
	private static final UUID HARD_SAMEHADA_DAMAGE_UUID = UUID.fromString("56963b0c-5966-482e-b023-e439905e5146");
	private static final UUID HARD_CHARGED_PUNCH_CHARGING_UUID = UUID.fromString("4e9fd1ef-fce7-4ee5-b6c9-0b15142599b6");
	private RevengeMeter revengeMeter;
	private MoveControl groundMovementController;
	private MoveControl waterMovementController;
	private NPCPhase<KuroobiEntity> groundPhase;
	private NPCPhase<KuroobiEntity> waterPhase;

	public KuroobiEntity(EntityType<KuroobiEntity> var1, Level var2) {
		super(var1,var2);
	}
	public KuroobiEntity(InProgressChallenge var1) {
		super(((EntityType)(ModMobs.KUROOBI.get())),var1);
	}
	public void preInit() {
		this.entityStats.setFaction(((Faction)(ModFactions.PIRATE.get())));
		this.entityStats.setRace(((Race)(ModRaces.FISHMAN.get())));
		this.entityStats.setSubRace(((Race)(ModRaces.FISHMAN_RAY.get())));
		this.entityStats.setSkinTint(13555155);
		this.entityStats.setFightingStyle(((FightingStyle)(ModFightingStyles.BRAWLER.get())));
	}
	public void initBoss() {
		this.revengeMeter = new RevengeMeter((LivingEntity)(this),100,1);
		this.groundMovementController = new MoveControl((Mob)(this));
		this.waterMovementController = new HumanoidSwimMoveControl((Mob)(this));
		this.groundPhase = new SimplePhase("Ground Phase",this);
		KuroobiEntity var1 = this;
		SimplePhase$IStartPhaseEvent var2 = var1::startWaterPhaseEvent;
		this.waterPhase = new SimplePhase("Water Phase",this,var2,(SimplePhase$IStopPhaseEvent)(((xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStopPhaseEvent)(this::stopWaterPhaseEvent))));
		ModNetwork.sendToAllAround(new SSyncEntityStatsPacket((LivingEntity)(this)),(Entity)(this));
		FactionsWorldData.get().addTemporaryCrewMember(ModNPCGroups.ARLONG_PIRATES,(LivingEntity)(this));
		this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(2D);
		this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(1D);
		this.revengeMeter.addCheck(new PhysicalHitRevengeCheck(2));
		this.revengeMeter.addCheck(new DeadzoneRevengeCheck(10));
		((GroundPathNavigation)(this.getNavigation())).setCanOpenDoors(true);
		this.goalSelector.addGoal(0,(Goal)(new OpenDoorGoal((Mob)(this),true)));
		this.goalSelector.addGoal(0,(Goal)(new JumpOutOfHoleGoal((Mob)(this))));
		this.goalSelector.addGoal(3,(Goal)(new LookAtPlayerGoal((Mob)(this),Player.class,8F)));
		this.goalSelector.addGoal(3,(Goal)(new RandomLookAroundGoal((Mob)(this))));
		Predicate var3 = ModEntityPredicates.getEnemyFactions((LivingEntity)(this)).and(ModEntityPredicates.IS_ENTITY_HARMLESS.negate());
		Predicate var4 = var3;
		final Predicate var4_f1 = var4;
		Predicate<LivingEntity> var5 = (l0) -> {
			return var4_f1.and(ModEntityPredicates.IS_INVISIBLE.negate()).test(l0);
		};
		Predicate<LivingEntity> var6 = var5;
		this.targetSelector.addGoal(1,(Goal)(new FactionHurtByTargetGoal((PathfinderMob)(this),var3,new Class[0])));
		this.targetSelector.addGoal(2,(Goal)(new NearestAttackableTargetGoal((Mob)(this),Mob.class,10,true,true,var6)));
		this.targetSelector.addGoal(2,(Goal)(new NearestAttackableTargetGoal((Mob)(this),Player.class,10,true,true,var6)));
		CloseMeleeAbilityWrapperGoal var7 = new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(KachiageHaisokuAbility.INSTANCE.get())));
		KachiageHaisokuAbility var8 = ((KachiageHaisokuAbility)(var7.getAbility()));
		Ability$ICanUseEvent var9 = (l0, l1) -> {
			Function<AbilityData, ChargedPunchAbility> lv3_2 = (l2_0) -> {
				return ((ChargedPunchAbility)(l2_0.getEquippedAbility(((AbilityCore)(ChargedPunchAbility.INSTANCE.get())))));
			};
			ChargedPunchAbility lv3_3 = ((ChargedPunchAbility)(((java.util.Optional)(AbilityData.get(l0))).map((Function)(lv3_2)).orElse(null)));
			if (((lv3_3) != (null)) && (lv3_3.isCharging())){
				return Result.fail(null);
			}else{
				return Result.success();
			}
		};
		var8.addCanUseCheck(var9);
		KachiageHaisokuAbility var10 = ((KachiageHaisokuAbility)(var7.getAbility()));
		KuroobiEntity var11 = this;
		Consumer<DealDamageComponent> var12 = (l0) -> {
			if (this.waterPhase.isActive(this)){
				LivingEntity lv4_2 = l0.getLastTarget();
				if ((lv4_2) != (null)){
					AbilityHelper.setDeltaMovement((Entity)(lv4_2),this.getDeltaMovement().x,-2D,this.getDeltaMovement().z);
				}else{
					return;
				}
			}
		};
		((java.util.Optional)(var10.getComponent(((AbilityComponentKey)(ModAbilityComponents.DAMAGE.get()))))).ifPresent((Consumer)(var12));
		CloseMeleeAbilityWrapperGoal var13 = new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(ChargedPunchAbility.INSTANCE.get())));
		ChargedPunchAbility var14 = ((ChargedPunchAbility)(var13.getAbility()));
		Ability$ICanUseEvent var15 = (l0, l1) -> {
			Function<AbilityData, KachiageHaisokuAbility> lv7_2 = (l2_0) -> {
				return ((KachiageHaisokuAbility)(l2_0.getEquippedAbility(((AbilityCore)(KachiageHaisokuAbility.INSTANCE.get())))));
			};
			KachiageHaisokuAbility lv7_3 = ((KachiageHaisokuAbility)(((java.util.Optional)(AbilityData.get(l0))).map((Function)(lv7_2)).orElse(null)));
			Function<AbilityData, SamehadaShoteiAbility> lv7_4 = (l2_0) -> {
				return ((SamehadaShoteiAbility)(l2_0.getEquippedAbility(((AbilityCore)(SamehadaShoteiAbility.INSTANCE.get())))));
			};
			SamehadaShoteiAbility lv7_5 = ((SamehadaShoteiAbility)(((java.util.Optional)(AbilityData.get(l0))).map((Function)(lv7_4)).orElse(null)));
			if (((lv7_3) != (null)) && (lv7_3.isContinuous())){
				return Result.fail(null);
			}else{
				if (((lv7_5) != (null)) && (lv7_5.isContinuous())){
					return Result.fail(null);
				}else{
					return Result.success();
				}
			}
		};
		var14.addCanUseCheck(var15);
		ReactiveGuardAbilityWrapperGoal var16 = new ReactiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(SamehadaShoteiAbility.INSTANCE.get())));
		CloseMeleeAbilityWrapperGoal var17 = new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(KarakusagawaraSeikenAbility.INSTANCE.get())));
		KarakusagawaraSeikenAbility var18 = ((KarakusagawaraSeikenAbility)(var17.getAbility()));
		KuroobiEntity var19 = this;
		Consumer<CooldownComponent> var20 = (l0) -> {
			l0.startCooldown((LivingEntity)(this),100F);
		};
		((java.util.Optional)(var18.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var20));
		GrabAbilityWrapperGoal var21 = new GrabAbilityWrapperGoal((Mob)(this),((AbilityCore)(SuplexAbility.INSTANCE.get())));
		SuplexAbility var22 = ((SuplexAbility)(var21.getAbility()));
		Ability$ICanUseEvent var23 = (l0, l1) -> {
			Function<AbilityData, SamehadaShoteiAbility> lv10_2 = (l2_0) -> {
				return ((SamehadaShoteiAbility)(l2_0.getEquippedAbility(((AbilityCore)(SamehadaShoteiAbility.INSTANCE.get())))));
			};
			SamehadaShoteiAbility lv10_3 = ((SamehadaShoteiAbility)(((java.util.Optional)(AbilityData.get(l0))).map((Function)(lv10_2)).orElse(null)));
			if (((lv10_3) != (null)) && (lv10_3.isContinuous())){
				return Result.fail(null);
			}else{
				return Result.success();
			}
		};
		var22.addCanUseCheck(var23);
		SuplexAbility var24 = ((SuplexAbility)(var21.getAbility()));
		KuroobiEntity var25 = this;
		GrabAbilityWrapperGoal var26 = var21;
		final GrabAbilityWrapperGoal var26_f2 = var26;
		Consumer<ChargeComponent> var27 = (l0) -> {
			KuroobiEntity lv13_3 = this;
			ChargeComponent lv13_4 = l0;
			GrabAbilityWrapperGoal lv13_5 = var26_f2;
			final ChargeComponent lv13_4_f1 = lv13_4;
			final GrabAbilityWrapperGoal lv13_5_f2 = lv13_5;
			ChargeComponent$IDuringChargingEvent lv13_6 = (l2_0, l2_1) -> {
				if ((this.waterPhase.isActive(this)) && (!((lv13_4_f1.getChargePercentage()) < (0.8F)))){
					SuplexAbility lv12_5 = ((SuplexAbility)(lv13_5_f2.getAbility()));
					Function lv12_6 = (Function) ((Function<GrabEntityComponent, Optional>) ((l3_0) -> {
					return Optional.ofNullable(l3_0.getGrabbedEntity());
				}));
					if ((((LivingEntity)(lv12_5.getComponent(((AbilityComponentKey)(ModAbilityComponents.GRAB.get()))).flatMap(lv12_6).orElse(null)))) != (null)){
						AbilityHelper.setDeltaMovement((Entity)(this),this.getDeltaMovement().add(0D,-1.3D,0D));
					}
					return;
				}else{
					return;
				}
			};
			l0.addTickEvent(90,lv13_6);
		};
		((java.util.Optional)(var24.getComponent(((AbilityComponentKey)(ModAbilityComponents.CHARGE.get()))))).ifPresent((Consumer)(var27));
		this.goalSelector.addGoal(0,(Goal)(new AlwaysActiveAbilityWrapperGoal((Mob)(this),((AbilityCore)(FishmanPassiveBonusesAbility.INSTANCE.get())))));
		this.goalSelector.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1D,true)));
		this.groundPhase.addGoal(1,new SprintTowardsTargetGoal((Mob)(this)));
		this.groundPhase.addGoal(3,new AntiAirAbilityWrapperGoal((Mob)(this),((AbilityCore)(TakedownKickAbility.INSTANCE.get()))));
		this.groundPhase.addGoal(3,var21);
		this.groundPhase.addGoal(4,var16);
		this.groundPhase.addGoal(4,var13);
		this.groundPhase.addGoal(4,var7);
		this.groundPhase.addGoal(5,var17);
		this.waterPhase.addGoal(0,new DashDodgeTargetGoal((Mob)(this),120F,5F));
		this.waterPhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),100F,2.25F));
		this.waterPhase.addGoal(3,var21);
		this.waterPhase.addGoal(4,var13);
		this.waterPhase.addGoal(4,var7);
		this.waterPhase.addGoal(5,var17);
		this.getPhaseManager().setPhase(this.groundPhase);
		if (this.getChallengeInfo().isDifficultyStandard()){
			this.entityStats.setDoriki(2000D);
			this.getAttribute(ModAttributes.GCD).setBaseValue(40D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(2D);
			this.getAttribute(NeoForgeMod.SWIM_SPEED).setBaseValue(3D);
			this.revengeMeter.addCheck(new DeadzoneRevengeCheck(5));
			KarakusagawaraSeikenAbility var28 = ((KarakusagawaraSeikenAbility)(var17.getAbility()));
			Ability$ICanUseEvent var29 = (l0, l1) -> {
			if (((double)(l0.getHealth())) <= (WyHelper.percentage(30D,(double)(l0.getMaxHealth())))){
				return Result.success();
			}else{
				return Result.fail(null);
			}
		};
			var28.addCanUseCheck(var29);
			SuplexAbility var30 = ((SuplexAbility)(var21.getAbility()));
			Consumer<CooldownComponent> var31 = (l0) -> {
			l0.getBonusManager().addBonus(STANDARD_SUPLEX_COOLDOWN_UUID,"Standard Suplex Cooldown Bonus",BonusOperation.ADD,40F);
		};
			((java.util.Optional)(var30.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var31));
		}else{
			this.entityStats.setDoriki(10000D);
			this.hakiCapability.setBusoshokuHakiExp(100F);
			this.hakiCapability.setKenbunshokuHakiExp(100F);
			this.getAttribute(ModAttributes.GCD).setBaseValue(20D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(5D);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5D);
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(7D);
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(350D);
			this.getAttribute(Attributes.ARMOR).setBaseValue(20D);
			this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(8D);
			this.getAttribute(NeoForgeMod.SWIM_SPEED).setBaseValue(5D);
			this.revengeMeter.addCheck(new DeadzoneRevengeCheck(10));
			SamehadaShoteiAbility var28_1 = ((SamehadaShoteiAbility)(var16.getAbility()));
			Consumer<CooldownComponent> var29_1 = (l0) -> {
			l0.getBonusManager().addBonus(HARD_SAMEHADA_COOLDOWN_UUID,"Hard Samehada Cooldown Bonus",BonusOperation.MUL,0.75F);
		};
			((java.util.Optional)(var28_1.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var29_1));
			SamehadaShoteiAbility var30_1 = ((SamehadaShoteiAbility)(var16.getAbility()));
			Consumer<DealDamageComponent> var31_1 = (l0) -> {
			l0.getBonusManager().addBonus(HARD_SAMEHADA_DAMAGE_UUID,"Hard Samehada Damage Bonus",BonusOperation.MUL,2F);
		};
			((java.util.Optional)(var30_1.getComponent(((AbilityComponentKey)(ModAbilityComponents.DAMAGE.get()))))).ifPresent((Consumer)(var31_1));
			ChargedPunchAbility var32 = ((ChargedPunchAbility)(var13.getAbility()));
			Consumer<ChargeComponent> var33 = (l0) -> {
			l0.getMaxChargeBonusManager().addBonus(HARD_CHARGED_PUNCH_CHARGING_UUID,"Hard Charged Punch Charging Bonus",BonusOperation.MUL,0.5F);
		};
			((java.util.Optional)(var32.getComponent(((AbilityComponentKey)(ModAbilityComponents.CHARGE.get()))))).ifPresent((Consumer)(var33));
			CloseMeleeAbilityWrapperGoal var34 = new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(HakaiHoAbility.INSTANCE.get())));
			HakaiHoAbility var35 = ((HakaiHoAbility)(var34.getAbility()));
			KuroobiEntity var36 = this;
			Consumer<CooldownComponent> var37 = (l0) -> {
			l0.startCooldown((LivingEntity)(this),100F);
		};
			((java.util.Optional)(var35.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var37));
			HakaiHoAbility var38 = ((HakaiHoAbility)(var34.getAbility()));
			KuroobiEntity var39 = this;
			Consumer<DealDamageComponent> var40 = (l0) -> {
			if (this.waterPhase.isActive(this)){
				LivingEntity lv20_2 = l0.getLastTarget();
				if ((lv20_2) != (null)){
					AbilityHelper.setDeltaMovement((Entity)(lv20_2),this.getDeltaMovement().x,-2D,this.getDeltaMovement().z);
				}else{
					return;
				}
			}
		};
			((java.util.Optional)(var38.getComponent(((AbilityComponentKey)(ModAbilityComponents.DAMAGE.get()))))).ifPresent((Consumer)(var40));
			CloseMeleeAbilityWrapperGoal var41 = new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(AntiMannerKickCourseAbility.INSTANCE.get())));
			AnimationComponent var42 = new AnimationComponent(var41.getAbility());
			AntiMannerKickCourseAbility var43 = ((AntiMannerKickCourseAbility)(var41.getAbility()));
			AbilityComponent[] var44 = new AbilityComponent[]{var42};
			var43.addComponents(var44);
			AntiMannerKickCourseAbility var45 = ((AntiMannerKickCourseAbility)(var41.getAbility()));
			KuroobiEntity var46 = this;
			Consumer<CooldownComponent> var47 = (l0) -> {
			l0.startCooldown((LivingEntity)(this),120F);
		};
			((java.util.Optional)(var45.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var47));
			AntiMannerKickCourseAbility var48 = ((AntiMannerKickCourseAbility)(var41.getAbility()));
			KuroobiEntity var49 = this;
			AnimationComponent var50 = var42;
			final AnimationComponent var50_f1 = var50;
			Consumer<DealDamageComponent> var51 = (l0) -> {
			if (this.waterPhase.isActive(this)){
				LivingEntity lv22_3 = l0.getLastTarget();
				if ((lv22_3) != (null)){
					var50_f1.start((LivingEntity)(this),ModAnimations.TAKEDOWN_KICK,7);
					AbilityHelper.setDeltaMovement((Entity)(lv22_3),this.getDeltaMovement().x,-2D,this.getDeltaMovement().z);
				}else{
					return;
				}
			}
		};
			((java.util.Optional)(var48.getComponent(((AbilityComponentKey)(ModAbilityComponents.DAMAGE.get()))))).ifPresent((Consumer)(var51));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get())))));
			this.groundPhase.addGoal(3,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(PartyTableKickCourseAbility.INSTANCE.get()))));
			this.groundPhase.addGoal(3,var41);
			this.groundPhase.addGoal(3,var34);
			this.groundPhase.addGoal(3,new GrabAbilityWrapperGoal((Mob)(this),((AbilityCore)(SpinningBrawlAbility.INSTANCE.get()))));
			this.waterPhase.addGoal(3,var41);
			this.waterPhase.addGoal(3,var34);
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.28999999165534973D).add(Attributes.ATTACK_DAMAGE,3D).add(Attributes.MAX_HEALTH,250D).add(Attributes.KNOCKBACK_RESISTANCE,0.2D);
	}
	public void tick() {
		super.tick();
		if ((!(this.level().isClientSide)) && (this.isAlive())){
			this.revengeMeter.tick();
			if (this.isInWater()){
				this.getPhaseManager().setPhase(this.waterPhase);
			}else{
				this.getPhaseManager().setPhase(this.groundPhase);
			}
		}
	}
	private void startWaterPhaseEvent(KuroobiEntity var1) {
		this.moveControl = this.waterMovementController;
	}
	private void stopWaterPhaseEvent(KuroobiEntity var1) {
		this.moveControl = this.groundMovementController;
	}
	public Set<RevengeMeter> getRevengeMeters() {
		RevengeMeter[] var1 = new RevengeMeter[]{this.revengeMeter};
		return Sets.newHashSet(var1);
	}
}