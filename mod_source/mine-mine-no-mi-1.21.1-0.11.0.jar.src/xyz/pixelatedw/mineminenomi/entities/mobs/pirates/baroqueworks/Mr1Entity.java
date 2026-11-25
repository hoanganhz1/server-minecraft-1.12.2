package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.TakedownKickAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.PartyTableKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.abilities.supa.AtomicRushAbility;
import xyz.pixelatedw.mineminenomi.abilities.supa.SparClawAbility;
import xyz.pixelatedw.mineminenomi.abilities.supa.SparklingDaisyAbility;
import xyz.pixelatedw.mineminenomi.abilities.supa.SpiderAbility;
import xyz.pixelatedw.mineminenomi.abilities.supa.SpiralHollowAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
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
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveBuffAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AntiAirAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.mr1.Mr1PhaseSwitcherGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class Mr1Entity extends OPBossEntity {
	// Fields
	private static final UUID STANDARD_SPIDER_COOLDOWN_BONUS_UUID = UUID.fromString("1f2944fa-fd28-41b9-8f67-392929011bbd");
	private static final ResourceLocation GCD_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","npc.mr1.gcd");
	private static final AttributeModifier GCD_MOD = new AttributeModifier(GCD_ID,-5D,AttributeModifier.Operation.ADD_VALUE);
	private NPCPhase<Mr1Entity> firstPhase;
	private NPCPhase<Mr1Entity> secondPhase;
	private NPCPhase<Mr1Entity> ultiPhase;
	private ReactiveGuardAbilityWrapperGoal<SpiderAbility> spiderWrapper;
	private AtomicRushAbility atomicRushAbility;

	public Mr1Entity(EntityType<Mr1Entity> var1, Level var2) {
		super(var1,var2);
	}
	public Mr1Entity(InProgressChallenge var1) {
		super(((EntityType)(ModMobs.MR1.get())),var1);
	}
	public void initBoss() {
		Mr1Entity var1 = this;
		SimplePhase$IStartPhaseEvent var2 = var1::startFirstPhaseEvent;
		this.firstPhase = new SimplePhase("First Phase",this,var2,(SimplePhase$IStopPhaseEvent)(((xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStopPhaseEvent)(this::stopFirstPhaseEvent))));
		this.secondPhase = new SimplePhase("Second Phase",this,(SimplePhase$IStartPhaseEvent)(((xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStartPhaseEvent)(this::startSecondPhaseEvent))));
		this.ultiPhase = new SimplePhase("Ultimate Phase",this);
		FactionsWorldData var3 = FactionsWorldData.get();
		this.entityStats.setFaction(((Faction)(ModFactions.PIRATE.get())));
		this.entityStats.setRace(((Race)(ModRaces.HUMAN.get())));
		this.devilFruitData.setDevilFruit(((AkumaNoMiItem)(ModFruits.SUPA_SUPA_NO_MI.get())));
		var3.addTemporaryCrewMember(ModNPCGroups.BAROQUE_WORKS,(LivingEntity)(this));
		this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(4D);
		this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(6D);
		this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(1D);
		MobsHelper.addBasicNPCGoals(this);
		CloseMeleeAbilityWrapperGoal var4 = new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(PartyTableKickCourseAbility.INSTANCE.get())));
		this.spiderWrapper = (ReactiveGuardAbilityWrapperGoal) (new ReactiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(SpiderAbility.INSTANCE.get()))));
		SpiderAbility var5 = ((SpiderAbility)(this.spiderWrapper.getAbility()));
		Mr1Entity var6 = this;
		CloseMeleeAbilityWrapperGoal var7 = var4;
		final CloseMeleeAbilityWrapperGoal var7_f1 = var7;
		Consumer<ContinuousComponent> var8 = (l0) -> {
			Mr1Entity lv2_3 = this;
			CloseMeleeAbilityWrapperGoal lv2_4 = var7_f1;
			ContinuousComponent lv2_5 = l0;
			final CloseMeleeAbilityWrapperGoal lv2_4_f1 = lv2_4;
			final ContinuousComponent lv2_5_f2 = lv2_5;
			ContinuousComponent$IDuringContinuousEvent lv2_6 = (l2_0, l2_1) -> {
				if (((l2_0.level().getGameTime()) >= ((((SpiderAbility)(this.spiderWrapper.getAbility())).getLastUseGametime()) + (60L))) && ((GoalHelper.hasEnoughTargetsAround(((Mob)(l2_0)),1,4F)) && (((PartyTableKickCourseAbility)(lv2_4_f1.getAbility())).canUse(l2_0).isSuccess()))){
					lv2_5_f2.stopContinuity(l2_0);
					((PartyTableKickCourseAbility)(lv2_4_f1.getAbility())).use(l2_0);
				}
			};
			l0.addTickEvent(lv2_6);
		};
		((java.util.Optional)(var5.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))))).ifPresent((Consumer)(var8));
		DashAbilityWrapperGoal var9 = new DashAbilityWrapperGoal((Mob)(this),((AbilityCore)(SparklingDaisyAbility.INSTANCE.get())));
		SparklingDaisyAbility var10 = ((SparklingDaisyAbility)(var9.getAbility()));
		var10.setProjectilesAmount((this.getChallengeInfo().isDifficultyHard()) ? (2) : (1));
		DashAbilityWrapperGoal var11 = new DashAbilityWrapperGoal((Mob)(this),((AbilityCore)(AtomicRushAbility.INSTANCE.get())));
		this.atomicRushAbility = ((AtomicRushAbility)(var11.getAbility()));
		SprintTowardsTargetGoal var12 = new SprintTowardsTargetGoal((Mob)(this));
		AntiAirAbilityWrapperGoal var13 = new AntiAirAbilityWrapperGoal((Mob)(this),((AbilityCore)(TakedownKickAbility.INSTANCE.get())));
		ActiveBuffAbilityWrapperGoal var14 = new ActiveBuffAbilityWrapperGoal((Mob)(this),((AbilityCore)(SparClawAbility.INSTANCE.get())));
		ProjectileAbilityWrapperGoal var15 = new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(SpiralHollowAbility.INSTANCE.get())));
		this.goalSelector.addGoal(0,(Goal)(new Mr1PhaseSwitcherGoal(this)));
		this.goalSelector.addGoal(0,(Goal)(new JumpOutOfHoleGoal((Mob)(this))));
		this.firstPhase.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1D,true)));
		this.firstPhase.addGoal(1,var12);
		this.firstPhase.addGoal(2,var13);
		this.firstPhase.addGoal(3,var14);
		this.firstPhase.addGoal(3,this.spiderWrapper);
		this.firstPhase.addGoal(3,var15);
		this.firstPhase.addGoal(4,var4);
		this.secondPhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),200F,2.5F));
		this.secondPhase.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1.2D,true)));
		this.secondPhase.addGoal(1,var12);
		this.secondPhase.addGoal(2,var13);
		this.secondPhase.addGoal(3,var14);
		this.secondPhase.addGoal(3,this.spiderWrapper);
		this.secondPhase.addGoal(3,var15);
		this.secondPhase.addGoal(4,var4);
		this.secondPhase.addGoal(4,var9);
		this.ultiPhase.addGoal(1,var11);
		this.getPhaseManager().setPhase(this.firstPhase);
		if (this.getChallengeInfo().isDifficultyStandard()){
			this.entityStats.setDoriki(2000D);
			this.getAttribute(ModAttributes.GCD).setBaseValue(40D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(5D);
			this.atomicRushAbility.setDashWaitTime(10);
			SpiderAbility var16 = ((SpiderAbility)(this.spiderWrapper.getAbility()));
			Consumer<CooldownComponent> var17 = (l0) -> {
			l0.getBonusManager().addBonus(STANDARD_SPIDER_COOLDOWN_BONUS_UUID,"Standard Spider Cooldown Bonus",BonusOperation.MUL,1.75F);
		};
			((java.util.Optional)(var16.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var17));
			this.secondPhase.addGoal(3,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(HakaiHoAbility.INSTANCE.get()))));
		}else{
			this.entityStats.setDoriki(10000D);
			this.hakiCapability.setBusoshokuHakiExp(100F);
			this.hakiCapability.setKenbunshokuHakiExp(100F);
			this.getAttribute(ModAttributes.GCD).setBaseValue(20D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(5D);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5D);
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(6D);
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(400D);
			this.getAttribute(Attributes.ARMOR).setBaseValue(20D);
			this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(8D);
			CloseMeleeAbilityWrapperGoal var16_1 = new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(HakaiHoAbility.INSTANCE.get())));
			HakaiHoAbility var17_1 = ((HakaiHoAbility)(var16_1.getAbility()));
			Mr1Entity var18 = this;
			Consumer<CooldownComponent> var19 = (l0) -> {
			l0.startCooldown((LivingEntity)(this),60F);
		};
			((java.util.Optional)(var17_1.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var19));
			CloseMeleeAbilityWrapperGoal var20 = new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(ShiganAbility.INSTANCE.get())));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get())))));
			this.firstPhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),200F,2.5F));
			this.firstPhase.addGoal(3,var16_1);
			this.firstPhase.addGoal(3,var20);
			this.firstPhase.addGoal(3,new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(RankyakuAbility.INSTANCE.get()))));
			this.secondPhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),150F,2.5F));
			this.secondPhase.addGoal(3,var16_1);
			this.secondPhase.addGoal(3,var20);
			this.secondPhase.addGoal(3,new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(RankyakuAbility.INSTANCE.get()))));
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.30000001192092896D).add(Attributes.ATTACK_DAMAGE,5D).add(Attributes.MAX_HEALTH,300D).add(Attributes.ARMOR,4D).add(Attributes.ARMOR_TOUGHNESS,2D).add(Attributes.KNOCKBACK_RESISTANCE,0.8D);
	}
	public void startUltiPhase() {
		this.getPhaseManager().setPhase(this.ultiPhase);
	}
	public void startSecondPhase() {
		this.getPhaseManager().setPhase(this.secondPhase);
	}
	public void tick() {
		super.tick();
		if ((!(this.level().isClientSide)) && ((this.getTarget()) != (null))){
			boolean var1 = this.hasLineOfSight((Entity)(this.getTarget()));
			if (((Math.abs((this.getY()) - (this.getTarget().getY()))) > (20D)) && (!(var1))){
				BlockPos var2 = WyHelper.findValidGroundLocation((Entity)(this),this.getTarget().blockPosition(),10,10);
				this.teleportTo((double)(var2.getX()),(double)((var2.getY()) + (5)),(double)(var2.getZ()));
			}else{
				return;
			}
		}
	}
	public void swing(InteractionHand var1, boolean var2) {
		if ((this.getTarget()) != (null)){
			WyHelper.spawnParticles((ParticleOptions)(ParticleTypes.SWEEP_ATTACK),((ServerLevel)(this.level())),this.getTarget().getX(),(this.getTarget().getY()) + ((double)(this.getTarget().getEyeHeight())),this.getTarget().getZ());
			this.level().playSound((Player)(null),this.getTarget().blockPosition(),((SoundEvent)(ModSounds.DASH_ABILITY_SWOOSH_SFX.get())),SoundSource.PLAYERS,1F,1F);
		}
		super.swing(var1,var2);
	}
	public boolean isUltiAvailable() {
		return !(((Boolean)(this.atomicRushAbility.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))).map((Function)(((Function<CooldownComponent, Boolean>)(CooldownComponent::isOnCooldown)))).orElse(Boolean.valueOf(true)))).booleanValue());
	}
	public double getUltiTicks() {
		return (double)(((Float)(this.atomicRushAbility.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))).map((Function)(((Function<ContinuousComponent, Float>)(ContinuousComponent::getThresholdTime)))).orElse(Float.valueOf(-1F)))).floatValue());
	}
	private void startFirstPhaseEvent(Mr1Entity var1) {
		if (this.isDifficultyHardOrAbove()){
			SpiderAbility var2 = ((SpiderAbility)(this.spiderWrapper.getAbility()));
			Consumer<CooldownComponent> var3 = (l0) -> {
			l0.getBonusManager().addBonus(STANDARD_SPIDER_COOLDOWN_BONUS_UUID,"Standard Spider Cooldown Bonus",BonusOperation.MUL,1.5F);
		};
			((java.util.Optional)(var2.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var3));
		}
	}
	private void stopFirstPhaseEvent(Mr1Entity var1) {
		if (this.isDifficultyHardOrAbove()){
			SpiderAbility var2 = ((SpiderAbility)(this.spiderWrapper.getAbility()));
			Consumer<CooldownComponent> var3 = (l0) -> {
			l0.getBonusManager().removeBonus(STANDARD_SPIDER_COOLDOWN_BONUS_UUID);
		};
			((java.util.Optional)(var2.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var3));
		}
	}
	private void startSecondPhaseEvent(Mr1Entity var1) {
		if (this.isDifficultyHardOrAbove()){
			AttributeInstance var2 = var1.getAttribute(ModAttributes.GCD);
			if (((var2) != (null)) && (!(var2.hasModifier(GCD_ID)))){
				var2.addTransientModifier(GCD_MOD);
			}else{
				return;
			}
		}
	}
}