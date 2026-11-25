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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.TakedownKickAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.PartyTableKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.FishmanPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.KachiageHaisokuAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.PackOfSharksAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.SamehadaShoteiAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.SharkOnToothAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.UchimizuAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
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
import xyz.pixelatedw.mineminenomi.api.entities.revenge.GankingRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.IRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.PhysicalHitRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
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
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
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
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class ArlongEntity extends OPBossEntity {
	// Fields
	private static final UUID STANDARD_UCHIMIZU_COOLDOWN_UUID = UUID.fromString("efa25144-6946-48c2-8e2a-cb2135b98783");
	private static final UUID HARD_MURASAME_COOLDOWN_UUID = UUID.fromString("08737d15-31f0-4103-b09a-99cd98e72cda");
	private static final UUID WATER_UCHIMIZU_COOLDOWN_UUID = UUID.fromString("55220e18-d068-4913-9cf5-ba7c57eeeb4e");
	private static final UUID WATER_SHARK_ON_TOOTH_COOLDOWN_UUID = UUID.fromString("c5c03564-411b-4a21-85e9-e9b003c91123");
	private static final ResourceLocation GCD_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","npc.arlong.gcd");
	private static final AttributeModifier GCD_MOD = new AttributeModifier(GCD_ID,-5D,AttributeModifier.Operation.ADD_VALUE);
	private RevengeMeter revengeMeter;
	private float revengeThreshold;
	private MoveControl groundMoveControl;
	private MoveControl waterMoveControl;
	private NPCPhase<ArlongEntity> firstPhase;
	private NPCPhase<ArlongEntity> secondPhase;
	private NPCPhase<ArlongEntity> waterPhase;
	private Optional<CooldownComponent> uchimizuCooldownComponent;
	private DashAbilityWrapperGoal<SharkOnToothAbility> sharkOnToothWrapper;
	private Optional<CooldownComponent> sharkOnToothCooldownComponent;

	public ArlongEntity(EntityType<ArlongEntity> var1, Level var2) {
		super(var1,var2);
	}
	public ArlongEntity(InProgressChallenge var1) {
		super(((EntityType)(ModMobs.ARLONG.get())),var1);
		this.revengeThreshold = (var1.isStandardDifficulty()) ? (0.5F) : (0.3F);
	}
	public void preInit() {
		this.entityStats.setFaction(((Faction)(ModFactions.PIRATE.get())));
		this.entityStats.setRace(((Race)(ModRaces.FISHMAN.get())));
		this.entityStats.setSubRace(((Race)(ModRaces.FISHMAN_SAWSHARK.get())));
		this.entityStats.setSkinTint(12633078);
		this.entityStats.setFightingStyle(((FightingStyle)(ModFightingStyles.BRAWLER.get())));
	}
	public void initBoss() {
		this.revengeMeter = new RevengeMeter((LivingEntity)(this),100,1);
		this.groundMoveControl = new MoveControl((Mob)(this));
		this.waterMoveControl = new HumanoidSwimMoveControl((Mob)(this));
		this.firstPhase = new SimplePhase("First Phase",this);
		this.secondPhase = new SimplePhase("Second Phase",this,(SimplePhase$IStartPhaseEvent)(((xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStartPhaseEvent)(this::startSecondPhaseEvent))));
		ArlongEntity var1 = this;
		SimplePhase$IStartPhaseEvent var2 = var1::startWaterPhaseEvent;
		this.waterPhase = new SimplePhase("Water Phase",this,var2,(SimplePhase$IStopPhaseEvent)(((xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStopPhaseEvent)(this::stopWaterPhaseEvent))));
		ModNetwork.sendToAllAround(new SSyncEntityStatsPacket((LivingEntity)(this)),(Entity)(this));
		FactionsWorldData.get().addTemporaryCrewMember(ModNPCGroups.ARLONG_PIRATES,(LivingEntity)(this));
		this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(2D);
		this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(1D);
		this.revengeMeter.addCheck(new PhysicalHitRevengeCheck(1));
		this.revengeMeter.addCheck(new GankingRevengeCheck(5,5F));
		ProjectileAbilityWrapperGoal var3 = new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(UchimizuAbility.INSTANCE.get())));
		Optional var4 = ((UchimizuAbility)(var3.getAbility())).getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get())));
		ArlongEntity var5 = this;
		Consumer<CooldownComponent> var6 = (l0) -> {
			l0.getBonusManager().addBonus(STANDARD_UCHIMIZU_COOLDOWN_UUID,"Standard Uchimizu Bonus",BonusOperation.MUL,2F);
			l0.startCooldown((LivingEntity)(this),120F);
		};
		((java.util.Optional)(var4)).ifPresent((Consumer)(var6));
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
		CloseMeleeAbilityWrapperGoal var10 = new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(ChargedPunchAbility.INSTANCE.get())));
		ChargedPunchAbility var11 = ((ChargedPunchAbility)(var10.getAbility()));
		Ability$ICanUseEvent var12 = (l0, l1) -> {
			Function<AbilityData, KachiageHaisokuAbility> lv5_2 = (l2_0) -> {
				return ((KachiageHaisokuAbility)(l2_0.getEquippedAbility(((AbilityCore)(KachiageHaisokuAbility.INSTANCE.get())))));
			};
			KachiageHaisokuAbility lv5_3 = ((KachiageHaisokuAbility)(((java.util.Optional)(AbilityData.get(l0))).map((Function)(lv5_2)).orElse(null)));
			if (((lv5_3) != (null)) && (lv5_3.isContinuous())){
				return Result.fail(null);
			}else{
				return Result.success();
			}
		};
		var11.addCanUseCheck(var12);
		ReactiveGuardAbilityWrapperGoal var13 = new ReactiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(SamehadaShoteiAbility.INSTANCE.get())));
		SamehadaShoteiAbility var14 = ((SamehadaShoteiAbility)(var13.getAbility()));
		Ability$ICanUseEvent var15 = (l0, l1) -> {
			Function<AbilityData, SharkOnToothAbility> lv7_2 = (l2_0) -> {
				return ((SharkOnToothAbility)(l2_0.getEquippedAbility(((AbilityCore)(SharkOnToothAbility.INSTANCE.get())))));
			};
			SharkOnToothAbility lv7_3 = ((SharkOnToothAbility)(((java.util.Optional)(AbilityData.get(l0))).map((Function)(lv7_2)).orElse(null)));
			if (((lv7_3) != (null)) && (lv7_3.isContinuous())){
				return Result.fail(null);
			}else{
				return Result.success();
			}
		};
		var14.addCanUseCheck(var15);
		this.sharkOnToothWrapper = (DashAbilityWrapperGoal) (new DashAbilityWrapperGoal((Mob)(this),((AbilityCore)(SharkOnToothAbility.INSTANCE.get()))));
		SharkOnToothAbility var16 = ((SharkOnToothAbility)(this.sharkOnToothWrapper.getAbility()));
		this.sharkOnToothCooldownComponent = (Optional) (var16.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))));
		UchimizuAbility var17 = ((UchimizuAbility)(var3.getAbility()));
		this.uchimizuCooldownComponent = (Optional) (var17.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))));
		SharkOnToothAbility var18 = ((SharkOnToothAbility)(this.sharkOnToothWrapper.getAbility()));
		ArlongEntity var19 = this;
		Ability$ICanUseEvent var20 = (l0, l1) -> {
			LivingEntity lv8_3 = this.getTarget();
			if ((this.waterPhase.isActive(this)) && ((lv8_3) != (null))){
				if (((Boolean)(EntityStatsData.get(lv8_3).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isFishman)))).orElse(Boolean.valueOf(false)))).booleanValue()){
					return Result.success();
				}else{
					if (((lv8_3.getY()) - (this.getY())) <= (2D)){
						return Result.fail(null);
					}
				}
			}
			return Result.success();
		};
		var18.addCanUseCheck(var20);
		ArlongEntity var21 = this;
		SharkOnToothAbility var22 = var18;
		final SharkOnToothAbility var22_f1 = var22;
		Ability$IOnTickEvent var23 = (l0, l1) -> {
			if ((this.waterPhase.isActive(this)) && ((var22_f1.isContinuous()) && (var22_f1.hasHitTarget()))){
				LivingEntity lv9_4 = ((DealDamageComponent)(var22_f1.getComponent(((AbilityComponentKey)(ModAbilityComponents.DAMAGE.get()))).get())).getLastTarget();
				if ((lv9_4) != (null)){
					((ContinuousComponent)(var22_f1.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))).get())).stopContinuity((LivingEntity)(this));
					((AnimationComponent)(var22_f1.getComponent(((AbilityComponentKey)(ModAbilityComponents.ANIMATION.get()))).get())).start((LivingEntity)(this),ModAnimations.TAKEDOWN_KICK,7);
					AbilityHelper.setDeltaMovement((Entity)(lv9_4),l0.getDeltaMovement().x,-5D,l0.getDeltaMovement().z);
				}else{
					return;
				}
			}
		};
		var18.addTickEvent(var23);
		((GroundPathNavigation)(this.getNavigation())).setCanOpenDoors(true);
		this.goalSelector.addGoal(0,(Goal)(new OpenDoorGoal((Mob)(this),true)));
		this.goalSelector.addGoal(0,(Goal)(new JumpOutOfHoleGoal((Mob)(this))));
		this.goalSelector.addGoal(3,(Goal)(new LookAtPlayerGoal((Mob)(this),Player.class,8F)));
		this.goalSelector.addGoal(3,(Goal)(new RandomLookAroundGoal((Mob)(this))));
		Predicate var24 = ModEntityPredicates.getEnemyFactions((LivingEntity)(this)).and(ModEntityPredicates.IS_ENTITY_HARMLESS.negate());
		Predicate var25 = var24;
		final Predicate var25_f2 = var25;
		Predicate<LivingEntity> var26 = (l0) -> {
			return var25_f2.and(ModEntityPredicates.IS_INVISIBLE.negate()).test(l0);
		};
		Predicate<LivingEntity> var27 = var26;
		this.targetSelector.addGoal(1,(Goal)(new FactionHurtByTargetGoal((PathfinderMob)(this),var24,new Class[0])));
		this.targetSelector.addGoal(2,(Goal)(new NearestAttackableTargetGoal((Mob)(this),Mob.class,10,true,true,var27)));
		this.targetSelector.addGoal(2,(Goal)(new NearestAttackableTargetGoal((Mob)(this),Player.class,10,true,true,var27)));
		this.goalSelector.addGoal(0,(Goal)(new AlwaysActiveAbilityWrapperGoal((Mob)(this),((AbilityCore)(FishmanPassiveBonusesAbility.INSTANCE.get())))));
		this.goalSelector.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1D,true)));
		this.firstPhase.addGoal(1,new SprintTowardsTargetGoal((Mob)(this)));
		this.firstPhase.addGoal(3,var3);
		this.firstPhase.addGoal(3,new AntiAirAbilityWrapperGoal((Mob)(this),((AbilityCore)(TakedownKickAbility.INSTANCE.get()))));
		this.firstPhase.addGoal(4,var10);
		this.firstPhase.addGoal(4,var13);
		this.firstPhase.addGoal(4,var7);
		this.firstPhase.addGoal(4,this.sharkOnToothWrapper);
		this.secondPhase.addGoal(1,new SprintTowardsTargetGoal((Mob)(this)));
		this.secondPhase.addGoal(3,var3);
		this.secondPhase.addGoal(3,new AntiAirAbilityWrapperGoal((Mob)(this),((AbilityCore)(TakedownKickAbility.INSTANCE.get()))));
		this.secondPhase.addGoal(4,var13);
		this.secondPhase.addGoal(4,var7);
		this.secondPhase.addGoal(4,this.sharkOnToothWrapper);
		this.waterPhase.addGoal(0,new DashDodgeTargetGoal((Mob)(this),100F,5F));
		this.waterPhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),70F,2.25F));
		this.waterPhase.addGoal(3,var3);
		this.waterPhase.addGoal(4,this.sharkOnToothWrapper);
		this.getPhaseManager().setPhase(this.firstPhase);
		if (this.getChallengeInfo().isDifficultyStandard()){
			this.entityStats.setDoriki(2000D);
			this.getAttribute(ModAttributes.GCD).setBaseValue(40D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(2D);
			this.getAttribute(NeoForgeMod.SWIM_SPEED).setBaseValue(3D);
			this.revengeMeter.addCheck(new DeadzoneRevengeCheck(5));
		}else{
			this.entityStats.setDoriki(10000D);
			this.hakiCapability.setBusoshokuHakiExp(100F);
			this.hakiCapability.setKenbunshokuHakiExp(100F);
			this.getAttribute(ModAttributes.GCD).setBaseValue(20D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(5D);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5D);
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(8D);
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(400D);
			this.getAttribute(Attributes.ARMOR).setBaseValue(20D);
			this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(8D);
			this.getAttribute(NeoForgeMod.SWIM_SPEED).setBaseValue(5D);
			this.revengeMeter.addCheck(new DeadzoneRevengeCheck(10));
			ProjectileAbilityWrapperGoal var28 = new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(PackOfSharksAbility.INSTANCE.get())));
			Optional var29 = ((PackOfSharksAbility)(var28.getAbility())).getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get())));
			ArlongEntity var30 = this;
			Consumer<CooldownComponent> var31 = (l0) -> {
			l0.getBonusManager().addBonus(HARD_MURASAME_COOLDOWN_UUID,"Murasame Cooldown Bonus",BonusOperation.ADD,-200F);
			l0.startCooldown((LivingEntity)(this),100F);
		};
			((java.util.Optional)(var29)).ifPresent((Consumer)(var31));
			Optional var32 = ((UchimizuAbility)(var3.getAbility())).getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get())));
			Consumer<CooldownComponent> var33 = (l0) -> {
			l0.getBonusManager().removeBonus(STANDARD_UCHIMIZU_COOLDOWN_UUID);
		};
			((java.util.Optional)(var32)).ifPresent((Consumer)(var33));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(3,(Goal)(var28));
			this.firstPhase.addGoal(3,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(PartyTableKickCourseAbility.INSTANCE.get()))));
			this.secondPhase.addGoal(0,new DashDodgeTargetGoal((Mob)(this),120F,5F));
			this.secondPhase.addGoal(3,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(PartyTableKickCourseAbility.INSTANCE.get()))));
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.30000001192092896D).add(Attributes.ATTACK_DAMAGE,4D).add(Attributes.MAX_HEALTH,300D).add(Attributes.KNOCKBACK_RESISTANCE,0.2D);
	}
	public void tick() {
		super.tick();
		if ((!(this.level().isClientSide)) && (this.isAlive())){
			this.revengeMeter.tick();
			if ((this.secondPhase.isActive(this)) && (((this.revengeMeter.getRevengePercentage()) > (this.revengeThreshold)) && ((this.sharkOnToothCooldownComponent.isPresent()) && (((CooldownComponent)(this.sharkOnToothCooldownComponent.get())).isOnCooldown())))){
				((CooldownComponent)(this.sharkOnToothCooldownComponent.get())).stopCooldown((LivingEntity)(this));
				this.revengeMeter.reduceRevengeValue(25);
			}
			if (this.isInWater()){
				this.getPhaseManager().setPhase(this.waterPhase);
			}else{
				if ((this.waterPhase.isActive(this)) && ((this.getPhaseManager().getPreviousPhase()) != (null))){
					this.getPhaseManager().setPhase(this.getPhaseManager().getPreviousPhase());
				}else{
					if ((this.firstPhase.isActive(this)) && (((double)(this.getHealth())) <= (WyHelper.percentage(50D,(double)(this.getMaxHealth()))))){
						this.getPhaseManager().setPhase(this.secondPhase);
					}else{
						if (!(this.secondPhase.isActive(this))){
							this.getPhaseManager().setPhase(this.firstPhase);
						}else{
							return;
						}
					}
				}
			}
		}
	}
	private void startSecondPhaseEvent(ArlongEntity var1) {
		this.setItemSlot(EquipmentSlot.MAINHAND,new ItemStack(((ItemLike)(ModWeapons.KIRIBACHI.get()))));
		this.getAttribute(Attributes.ENTITY_INTERACTION_RANGE).setBaseValue(1D);
		if (this.isDifficultyHardOrAbove()){
			AttributeInstance var2 = var1.getAttribute(ModAttributes.GCD);
			if (((var2) != (null)) && (!(var2.hasModifier(GCD_ID)))){
				var2.addTransientModifier(GCD_MOD);
			}else{
				return;
			}
		}
	}
	private void startWaterPhaseEvent(ArlongEntity var1) {
		this.moveControl = this.waterMoveControl;
		Consumer<? super CooldownComponent> var2 = (l0) -> {
			if (!(l0.getBonusManager().hasBonus(WATER_UCHIMIZU_COOLDOWN_UUID))){
				l0.getBonusManager().addBonus(WATER_UCHIMIZU_COOLDOWN_UUID,"Uchimizu Cooldown Water Bonus",BonusOperation.MUL,0.5F);
			}
		};
		((java.util.Optional)(this.uchimizuCooldownComponent)).ifPresent((Consumer)(var2));
		Consumer<? super CooldownComponent> var3 = (l0) -> {
			if (!(l0.getBonusManager().hasBonus(WATER_SHARK_ON_TOOTH_COOLDOWN_UUID))){
				l0.getBonusManager().addBonus(WATER_SHARK_ON_TOOTH_COOLDOWN_UUID,"Shark on Tooth Cooldown Water Bonus",BonusOperation.MUL,0.5F);
			}
		};
		((java.util.Optional)(this.sharkOnToothCooldownComponent)).ifPresent((Consumer)(var3));
	}
	private void stopWaterPhaseEvent(ArlongEntity var1) {
		this.moveControl = this.groundMoveControl;
		Consumer<? super CooldownComponent> var2 = (l0) -> {
			l0.getBonusManager().removeBonus(WATER_UCHIMIZU_COOLDOWN_UUID);
		};
		((java.util.Optional)(this.uchimizuCooldownComponent)).ifPresent((Consumer)(var2));
		Consumer<? super CooldownComponent> var3 = (l0) -> {
			l0.getBonusManager().removeBonus(WATER_SHARK_ON_TOOTH_COOLDOWN_UUID);
		};
		((java.util.Optional)(this.sharkOnToothCooldownComponent)).ifPresent((Consumer)(var3));
	}
	public Set<RevengeMeter> getRevengeMeters() {
		RevengeMeter[] var1 = new RevengeMeter[]{this.revengeMeter};
		return Sets.newHashSet(var1);
	}
}