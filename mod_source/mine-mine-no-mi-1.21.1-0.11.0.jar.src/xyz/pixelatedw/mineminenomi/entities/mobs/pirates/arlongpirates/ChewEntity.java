package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.arlongpirates;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
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
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.FishmanPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.MizuOsuAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.MizuShuryudanAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.MizuTaihoAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.PackOfSharksAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.UchimizuAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInfo;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStartPhaseEvent;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStopPhaseEvent;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.controllers.HumanoidSwimMoveControl;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ClimbOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.FactionHurtByTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.RunAwayFromTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.RepeaterAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
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

public class ChewEntity extends OPBossEntity {
	// Fields
	private static final UUID MIZU_SHURYUDAN_COOLDOWN_UUID = UUID.fromString("01fa80f8-1ce1-4456-8696-ed2a6ebd1468");
	private static final UUID MIZU_OSU_COOLDOWN_UUID = UUID.fromString("a5f2b30b-1526-4af7-a38b-ec1b0e1922a1");
	private static final UUID HARD_MIZU_TAIHO_COOLDOWN_UUID = UUID.fromString("beccb13e-c813-4d8e-b846-0b05b4ee7557");
	private static final UUID HARD_MURSAMAE_COOLDOWN_UUID = UUID.fromString("52afe78f-6be1-43be-89d5-12f3e405f9ff");
	private RevengeMeter revengeMeter;
	private MoveControl groundMovementController;
	private MoveControl waterMovementController;
	private NPCPhase<ChewEntity> groundPhase;
	private NPCPhase<ChewEntity> waterPhase;

	public ChewEntity(EntityType<ChewEntity> var1, Level var2) {
		super(var1,var2);
	}
	public ChewEntity(InProgressChallenge var1) {
		super(((EntityType)(ModMobs.CHEW.get())),var1);
	}
	public void preInit() {
		this.entityStats.setFaction(((Faction)(ModFactions.PIRATE.get())));
		this.entityStats.setRace(((Race)(ModRaces.FISHMAN.get())));
		this.entityStats.setSubRace(((Race)(ModRaces.FISHMAN_GARFISH.get())));
		this.entityStats.setSkinTint(11394009);
		this.entityStats.setFightingStyle(((FightingStyle)(ModFightingStyles.SNIPER.get())));
	}
	public void initBoss() {
		this.revengeMeter = new RevengeMeter((LivingEntity)(this),100,1);
		this.groundMovementController = new MoveControl((Mob)(this));
		this.waterMovementController = new HumanoidSwimMoveControl((Mob)(this));
		this.groundPhase = new SimplePhase("Ground Phase",this);
		ChewEntity var1 = this;
		SimplePhase$IStartPhaseEvent var2 = var1::startWaterPhaseEvent;
		this.waterPhase = new SimplePhase("Water Phase",this,var2,(SimplePhase$IStopPhaseEvent)(((xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStopPhaseEvent)(this::stopWaterPhaseEvent))));
		ModNetwork.sendToAllAround(new SSyncEntityStatsPacket((LivingEntity)(this)),(Entity)(this));
		FactionsWorldData.get().addTemporaryCrewMember(ModNPCGroups.ARLONG_PIRATES,(LivingEntity)(this));
		this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(1D);
		this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(1D);
		ProjectileAbilityWrapperGoal var3 = new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(UchimizuAbility.INSTANCE.get())));
		UchimizuAbility var4 = ((UchimizuAbility)(var3.getAbility()));
		ChewEntity var5 = this;
		Consumer<CooldownComponent> var6 = (l0) -> {
			l0.startCooldown((LivingEntity)(this),60F);
		};
		((java.util.Optional)(var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var6));
		ProjectileAbilityWrapperGoal var7 = new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(MizuTaihoAbility.INSTANCE.get())));
		ReactiveGuardAbilityWrapperGoal var8 = new ReactiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(MizuOsuAbility.INSTANCE.get())));
		MizuOsuAbility var9 = ((MizuOsuAbility)(var8.getAbility()));
		Consumer<CooldownComponent> var10 = (l0) -> {
			l0.getBonusManager().addBonus(MIZU_OSU_COOLDOWN_UUID,"Mizu Osu Cooldown Bonus",BonusOperation.ADD,-60F);
		};
		((java.util.Optional)(var9.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var10));
		RepeaterAbilityWrapperGoal var11 = new RepeaterAbilityWrapperGoal((Mob)(this),((AbilityCore)(MizuShuryudanAbility.INSTANCE.get())));
		MizuShuryudanAbility var12 = ((MizuShuryudanAbility)(var11.getAbility()));
		Consumer<CooldownComponent> var13 = (l0) -> {
			l0.getBonusManager().addBonus(MIZU_SHURYUDAN_COOLDOWN_UUID,"Mizu Shuryudan Cooldown Bonus",BonusOperation.ADD,-40F);
		};
		((java.util.Optional)(var12.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var13));
		((GroundPathNavigation)(this.getNavigation())).setCanOpenDoors(true);
		this.goalSelector.addGoal(0,(Goal)(new OpenDoorGoal((Mob)(this),true)));
		this.goalSelector.addGoal(0,(Goal)(new ClimbOutOfHoleGoal((Mob)(this))));
		this.goalSelector.addGoal(3,(Goal)(new LookAtPlayerGoal((Mob)(this),Player.class,8F)));
		this.goalSelector.addGoal(3,(Goal)(new RandomLookAroundGoal((Mob)(this))));
		Predicate var14 = ModEntityPredicates.getEnemyFactions((LivingEntity)(this)).and(ModEntityPredicates.IS_ENTITY_HARMLESS.negate());
		Predicate var15 = var14;
		final Predicate var15_f1 = var15;
		Predicate<LivingEntity> var16 = (l0) -> {
			return var15_f1.and(ModEntityPredicates.IS_INVISIBLE.negate()).test(l0);
		};
		Predicate<LivingEntity> var17 = var16;
		this.targetSelector.addGoal(1,(Goal)(new FactionHurtByTargetGoal((PathfinderMob)(this),var14,new Class[0])));
		this.targetSelector.addGoal(2,(Goal)(new NearestAttackableTargetGoal((Mob)(this),Mob.class,10,true,true,var17)));
		this.targetSelector.addGoal(2,(Goal)(new NearestAttackableTargetGoal((Mob)(this),Player.class,10,true,true,var17)));
		this.goalSelector.addGoal(0,(Goal)(new AlwaysActiveAbilityWrapperGoal((Mob)(this),((AbilityCore)(FishmanPassiveBonusesAbility.INSTANCE.get())))));
		this.goalSelector.addGoal(1,(Goal)(new ImprovedMeleeAttackGoal((PathfinderMob)(this),0.800000011920929D,true).setEarlyStop((Supplier)(((Supplier<Boolean>)(this::isInRangeForAbilities))))));
		this.goalSelector.addGoal(3,(Goal)(var3));
		this.goalSelector.addGoal(4,(Goal)(var11));
		this.goalSelector.addGoal(4,(Goal)(var8));
		this.goalSelector.addGoal(4,(Goal)(var7));
		this.getPhaseManager().setPhase(this.groundPhase);
		if (this.getChallengeInfo().isDifficultyStandard()){
			this.entityStats.setDoriki(2000D);
			this.getAttribute(ModAttributes.GCD).setBaseValue(40D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(2D);
			this.getAttribute(NeoForgeMod.SWIM_SPEED).setBaseValue(3D);
			this.goalSelector.addGoal(0,(Goal)(new RunAwayFromTargetGoal(this,1.5D,100,200)));
			this.goalSelector.addGoal(0,(Goal)(new DashDodgeTargetGoal((Mob)(this),200F,5F)));
			this.goalSelector.addGoal(0,(Goal)(new DashDodgeProjectilesGoal((Mob)(this),70F,2.25F)));
		}else{
			this.entityStats.setDoriki(10000D);
			this.hakiCapability.setBusoshokuHakiExp(100F);
			this.hakiCapability.setKenbunshokuHakiExp(100F);
			this.getAttribute(ModAttributes.GCD).setBaseValue(20D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(3D);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3D);
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(5D);
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(300D);
			this.getAttribute(Attributes.ARMOR).setBaseValue(20D);
			this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(4D);
			this.getAttribute(NeoForgeMod.SWIM_SPEED).setBaseValue(5D);
			MizuTaihoAbility var18 = ((MizuTaihoAbility)(var7.getAbility()));
			Consumer<CooldownComponent> var19 = (l0) -> {
			l0.getBonusManager().addBonus(HARD_MIZU_TAIHO_COOLDOWN_UUID,"Hard Mizu Taiho Cooldown Bonus",BonusOperation.ADD,-100F);
		};
			((java.util.Optional)(var18.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var19));
			ProjectileAbilityWrapperGoal var20 = new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(PackOfSharksAbility.INSTANCE.get())));
			PackOfSharksAbility var21 = ((PackOfSharksAbility)(var20.getAbility()));
			ChewEntity var22 = this;
			Consumer<CooldownComponent> var23 = (l0) -> {
			l0.getBonusManager().addBonus(HARD_MURSAMAE_COOLDOWN_UUID,"Hard Murasame Cooldown Bonus",BonusOperation.ADD,-150F);
			l0.startCooldown((LivingEntity)(this),80F);
		};
			((java.util.Optional)(var21.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var23));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiImbuingAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(new RunAwayFromTargetGoal(this,1.7D,100,100)));
			this.goalSelector.addGoal(0,(Goal)(new DashDodgeTargetGoal((Mob)(this),100F,4F)));
			this.goalSelector.addGoal(0,(Goal)(new DashDodgeProjectilesGoal((Mob)(this),60F,3F)));
			this.goalSelector.addGoal(3,(Goal)(var20));
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.30000001192092896D).add(Attributes.ATTACK_DAMAGE,3D).add(Attributes.MAX_HEALTH,250D).add(Attributes.KNOCKBACK_RESISTANCE,0.1D);
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
	private boolean isInRangeForAbilities() {
		if (!(GoalHelper.hasAliveTarget((Mob)(this)))){
			return true;
		}else{
			return (Math.abs(this.distanceTo((Entity)(this.getTarget())))) < (30F);
		}
	}
	private void startWaterPhaseEvent(ChewEntity var1) {
		this.moveControl = this.waterMovementController;
	}
	private void stopWaterPhaseEvent(ChewEntity var1) {
		this.moveControl = this.groundMovementController;
	}
	public Set<RevengeMeter> getRevengeMeters() {
		RevengeMeter[] var1 = new RevengeMeter[]{this.revengeMeter};
		return Sets.newHashSet(var1);
	}
}