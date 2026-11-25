package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates;

import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.BombThrowAbility;
import xyz.pixelatedw.mineminenomi.abilities.SlashDamageImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.bara.BaraBaraCarAbility;
import xyz.pixelatedw.mineminenomi.abilities.bara.BaraBaraFestivalAbility;
import xyz.pixelatedw.mineminenomi.abilities.bara.BaraBaraHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.bara.BaraSplitAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.KenbunshokuHakiFutureSightAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent$IDuringCooldownEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent$IStartCooldownEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IAfterShootEvent;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInfo;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.IPhasesEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.DeadzoneRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.IRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.TargetFlyStallingRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.TargetRunningAwayRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.BombEntity;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ClimbOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveBuffAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.buggy.BuggyDashGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.buggy.BuggyPhaseSwitcherGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.buggy.BuggyRunningGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class BuggyEntity extends OPBossEntity {
	// Fields
	private static final byte BARA_RESET_EVENT = 100;
	private static final byte BARA_HO_EVENT = 101;
	private static final byte BARA_SPLIT_EVENT = 102;
	private static final byte BARA_FESTIVAL_EVENT = 103;
	private static final byte BARA_CAR_START_EVENT = 104;
	private static final byte BARA_CAR_END_EVENT = 105;
	private static final byte FIREWORKS_EVENT = 110;
	private static final UUID BARA_CAR_COOLDOWN_UUID = UUID.fromString("1d375261-e119-484c-8df3-c92da926cd6c");
	public int clientCarState;
	public int clientState;
	private int clientTick;
	private boolean tickClient;
	private NPCPhase<BuggyEntity> normalPhase;
	private NPCPhase<BuggyEntity> carPhase;
	private final RevengeMeter kitingMeter;
	private ActiveGuardAbilityWrapperGoal<BaraBaraFestivalAbility> baraFestivalWrapper;
	private ActiveBuffAbilityWrapperGoal<BaraBaraCarAbility> baraCarWrapper;
	private boolean splitBombs;

	public BuggyEntity(EntityType<BuggyEntity> var1, Level var2) {
		super(var1,var2);
		this.clientCarState = 0;
		this.clientState = 0;
		this.clientTick = 0;
		this.tickClient = false;
		this.normalPhase = new SimplePhase("Normal Phase",this);
		this.carPhase = new SimplePhase("Car Phase",this);
		this.kitingMeter = new RevengeMeter((LivingEntity)(this),100,0);
		this.splitBombs = false;
	}
	public BuggyEntity(InProgressChallenge var1) {
		super(((EntityType)(ModMobs.BUGGY.get())),var1);
		this.clientCarState = 0;
		this.clientState = 0;
		this.clientTick = 0;
		this.tickClient = false;
		this.normalPhase = new SimplePhase("Normal Phase",this);
		this.carPhase = new SimplePhase("Car Phase",this);
		this.kitingMeter = new RevengeMeter((LivingEntity)(this),100,0);
		this.splitBombs = false;
	}
	public void initBoss() {
		ItemStack var1 = new ItemStack(((ItemLike)(ModArmors.PIRATE_CAPTAIN_CAPE.get())));
		int[] var2 = new int[]{16146690};
		var1.set(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(var2));
		ItemStack var3 = ((Item)(ModArmors.BICORNE.get())).getDefaultInstance();
		int[] var4 = new int[]{13459968};
		var3.set(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(var4));
		this.setItemSlot(EquipmentSlot.HEAD,var3);
		this.setItemSlot(EquipmentSlot.CHEST,var1);
		FactionsWorldData var5 = FactionsWorldData.get();
		this.entityStats.setFaction(((Faction)(ModFactions.PIRATE.get())));
		this.entityStats.setRace(((Race)(ModRaces.HUMAN.get())));
		this.devilFruitData.setDevilFruit(((AkumaNoMiItem)(ModFruits.BARA_BARA_NO_MI.get())));
		var5.addTemporaryCrewMember(ModNPCGroups.BUGGY_PIRATES,(LivingEntity)(this));
		this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(2D);
		if (this.getChallengeInfo().isDifficultyStandard()){
			this.entityStats.setDoriki(2000D);
			this.getAttribute(ModAttributes.GCD).setBaseValue(40D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(4D);
		}else{
			this.entityStats.setDoriki(10000D);
			this.hakiCapability.setBusoshokuHakiExp(100F);
			this.hakiCapability.setKenbunshokuHakiExp(100F);
			this.getAttribute(ModAttributes.GCD).setBaseValue(20D);
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(4D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(4D);
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(300D);
			this.getAttribute(Attributes.ARMOR).setBaseValue(10D);
			this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(8D);
		}
		MobsHelper.addBasicNPCGoals(this);
		ProjectileAbilityWrapperGoal var6 = new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(BaraBaraHoAbility.INSTANCE.get())));
		BaraBaraHoAbility var7 = ((BaraBaraHoAbility)(var6.getAbility()));
		BuggyEntity var8 = this;
		Consumer<CooldownComponent> var9 = (l0) -> {
			BuggyEntity lv3_2 = this;
			CooldownComponent$IStartCooldownEvent lv3_3 = (l2_0, l2_1) -> {
				this.level().broadcastEntityEvent((Entity)(this),(byte)(101));
				this.clientTick = 20;
				this.tickClient = true;
			};
			l0.addStartEvent(lv3_3);
			BuggyEntity lv3_4 = this;
			CooldownComponent$IDuringCooldownEvent lv3_5 = (l2_0, l2_1) -> {
				if ((this.tickClient) && ((this.clientTick--) <= (0))){
					this.level().broadcastEntityEvent((Entity)(this),(byte)(100));
					this.tickClient = false;
				}
			};
			l0.addTickEvent(lv3_5);
		};
		((java.util.Optional)(var7.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var9));
		BaraBaraHoAbility var10 = ((BaraBaraHoAbility)(var6.getAbility()));
		BuggyEntity var11 = this;
		Consumer<ProjectileComponent> var12 = (l0) -> {
			BuggyEntity lv5_2 = this;
			ProjectileComponent$IAfterShootEvent lv5_3 = (l2_0) -> {
				this.level().broadcastEntityEvent((Entity)(this),(byte)(100));
			};
			l0.addAfterShootEvent(100,lv5_3);
		};
		((java.util.Optional)(var10.getComponent(((AbilityComponentKey)(ModAbilityComponents.PROJECTILE.get()))))).ifPresent((Consumer)(var12));
		this.baraFestivalWrapper = (ActiveGuardAbilityWrapperGoal) (new ActiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(BaraBaraFestivalAbility.INSTANCE.get()))));
		BaraBaraFestivalAbility var13 = ((BaraBaraFestivalAbility)(this.baraFestivalWrapper.getAbility()));
		BuggyEntity var14 = this;
		Ability$IOnTickEvent var15 = (l0, l1) -> {
			LivingEntity lv6_3 = this.getTarget();
			if ((lv6_3) != (null)){
				int lv6_4 = (((this.getLastHurtByMobTimestamp()) > (0)) && (((this.level().getGameTime()) - ((long)(this.getLastHurtByMobTimestamp()))) > (100L))) ? (1) : (0);
				boolean lv6_5 = GoalHelper.isWithinDistance((LivingEntity)(this),lv6_3,5D);
				if (((lv6_4) != (0)) && (lv6_5)){
					((BaraBaraFestivalAbility)(this.baraFestivalWrapper.getAbility())).switchModeToShield((LivingEntity)(this));
				}else{
					((BaraBaraFestivalAbility)(this.baraFestivalWrapper.getAbility())).switchModeToAttack((LivingEntity)(this));
				}
			}
		};
		var13.addTickEvent(var15);
		BaraBaraFestivalAbility var16 = ((BaraBaraFestivalAbility)(this.baraFestivalWrapper.getAbility()));
		BuggyEntity var17 = this;
		Consumer<ContinuousComponent> var18 = (l0) -> {
			BuggyEntity lv9_2 = this;
			ContinuousComponent$IStartContinuousEvent lv9_3 = (l2_0, l2_1) -> {
				this.level().broadcastEntityEvent((Entity)(this),(byte)(103));
			};
			l0.addStartEvent(lv9_3);
			BuggyEntity lv9_4 = this;
			ContinuousComponent$IEndContinuousEvent lv9_5 = (l2_0, l2_1) -> {
				this.level().broadcastEntityEvent((Entity)(this),(byte)(100));
			};
			l0.addEndEvent(lv9_5);
		};
		((java.util.Optional)(var16.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))))).ifPresent((Consumer)(var18));
		CloseMeleeAbilityWrapperGoal var19 = new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(BaraSplitAbility.INSTANCE.get())));
		BaraSplitAbility var20 = ((BaraSplitAbility)(var19.getAbility()));
		BuggyEntity var21 = this;
		Ability$ICanUseEvent var22 = (l0, l1) -> {
			if (GoalHelper.isOutsideDistance((LivingEntity)(this),this.getTarget(),20D)){
				return Result.fail(null);
			}else{
				return Result.success();
			}
		};
		var20.addCanUseCheck(var22);
		((BaraSplitAbility)(var19.getAbility())).getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))).ifPresent((Consumer)(((Consumer<ContinuousComponent>)(this::setBaraSplitLogic))));
		ProjectileAbilityWrapperGoal var23 = new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(BombThrowAbility.INSTANCE.get())));
		BombThrowAbility var24 = ((BombThrowAbility)(var23.getAbility()));
		Consumer<CooldownComponent> var25 = (l0) -> {
			l0.getBonusManager().addBonus(BARA_CAR_COOLDOWN_UUID,"Bara Car Cooldown Bonus",BonusOperation.MUL,0.5F);
		};
		((java.util.Optional)(var24.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var25));
		this.baraCarWrapper = (ActiveBuffAbilityWrapperGoal) (new ActiveBuffAbilityWrapperGoal((Mob)(this),((AbilityCore)(BaraBaraCarAbility.INSTANCE.get()))));
		BuggyEntity var26 = this;
		CloseMeleeAbilityWrapperGoal var27 = var19;
		final CloseMeleeAbilityWrapperGoal var27_f1 = var27;
		Predicate<LivingEntity> var28 = (l0) -> {
			if (((BaraBaraCarAbility)(this.baraCarWrapper.getAbility())).isContinuous()){
				return false;
			}else{
				if (((BaraSplitAbility)(var27_f1.getAbility())).isContinuous()){
					return false;
				}else{
					return true;
				}
			}
		};
		Predicate<LivingEntity> var29 = var28;
		this.kitingMeter.addCheck(new TargetFlyStallingRevengeCheck(1));
		this.kitingMeter.addCheck(new TargetRunningAwayRevengeCheck(10,10F));
		this.kitingMeter.addCheck(new DeadzoneRevengeCheck(5));
		this.goalSelector.addGoal(0,(Goal)(new ClimbOutOfHoleGoal((Mob)(this))));
		this.goalSelector.addGoal(0,(Goal)(new BuggyPhaseSwitcherGoal(this)));
		this.goalSelector.addGoal(0,(Goal)(new AlwaysActiveAbilityWrapperGoal((Mob)(this),((AbilityCore)(SlashDamageImmunityAbility.INSTANCE.get())))));
		this.normalPhase.addGoal(0,new BuggyRunningGoal(this,1.5D));
		this.normalPhase.addGoal(1,(Goal)(new ImprovedMeleeAttackGoal((PathfinderMob)(this),1D,true)));
		this.normalPhase.addGoal(2,var6);
		this.normalPhase.addGoal(2,this.baraFestivalWrapper);
		this.normalPhase.addGoal(2,var23);
		this.normalPhase.addGoal(3,var19);
		this.carPhase.addGoal(0,new BuggyRunningGoal(this,2.5D));
		this.carPhase.addGoal(2,var23);
		this.getPhaseManager().setPhase(this.normalPhase);
		if (this.getChallengeInfo().isDifficultyStandard()){
			this.normalPhase.addGoal(1,new DashDodgeTargetGoal((Mob)(this),500F,2F).setCanUseTest(var29));
			this.normalPhase.addGoal(1,new DashDodgeProjectilesGoal((Mob)(this),500F,2F).setCanUseTest(var29));
			this.carPhase.addGoal(0,new BuggyDashGoal(this,2.5D,10F));
		}else{
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(KenbunshokuHakiFutureSightAbility.INSTANCE.get())))));
			this.normalPhase.addGoal(1,new DashDodgeTargetGoal((Mob)(this),250F,3F).setCanUseTest(var29));
			this.normalPhase.addGoal(1,new DashDodgeProjectilesGoal((Mob)(this),250F,3F).setCanUseTest(var29));
			this.carPhase.addGoal(0,new BuggyDashGoal(this,5D,25F));
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,30D).add(Attributes.MOVEMENT_SPEED,0.30000001192092896D).add(Attributes.ATTACK_DAMAGE,2D).add(Attributes.MAX_HEALTH,200D).add(Attributes.ARMOR,2D);
	}
	public EntityDimensions getDefaultDimensions(Pose var1) {
		if ((!(this.isCarPhaseActive())) && ((this.clientCarState) != (1))){
			return super.getDefaultDimensions(var1);
		}else{
			return EntityDimensions.fixed(1.3F,0.9F);
		}
	}
	public void tick() {
		this.fallDistance = 0F;
		super.tick();
		if (!(this.level().isClientSide)){
			this.kitingMeter.tick();
		}
		if ((!(this.isCarPhaseActive())) && ((this.clientCarState) != (1))){

		}else{
			this.refreshDimensions();
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	public void handleEntityEvent(byte var1) {
		switch (var1){
		case 110:
			this.level().createFireworks(this.getX(),this.getY(),this.getZ(),0D,0D,0D,this.createFireworksList());
			break;
		case 100:

		case 101:

		case 102:

		case 103:
			this.clientState = (var1) - (100);
			break;
		case 104:
			this.clientState = 4;
			this.clientCarState = 1;
			break;
		case 105:
			this.clientState = 4;
			this.clientCarState = -1;
			break;
		case 106:
			break;
		case 107:
			break;
		case 108:
			break;
		case 109:
			break;
		}
		super.handleEntityEvent(var1);
	}
	private List<FireworkExplosion> createFireworksList() {
		FireworkExplosion var1 = new FireworkExplosion(FireworkExplosion.Shape.LARGE_BALL,IntList.of(16351261,11546150),IntList.of(16701501,16383998),false,true);
		return (List<FireworkExplosion>) (List) (List.of(var1,new FireworkExplosion(FireworkExplosion.Shape.BURST,IntList.of(16701501,16383998),IntList.of(16701501,16383998),true,false)));
	}
	public boolean shouldRun() {
		if (this.getPhaseManager().isCurrentPhase(this.carPhase)){
			return true;
		}else{
			boolean var1 = ((Boolean)(((BaraBaraFestivalAbility)(this.baraFestivalWrapper.getAbility())).getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))).map((Function)(((Function<ContinuousComponent, Boolean>)(ContinuousComponent::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue();
			boolean var2 = ((BaraBaraFestivalAbility)(this.baraFestivalWrapper.getAbility())).isAttackMode();
			if ((var1) && (var2)){
				return true;
			}else{
				return false;
			}
		}
	}
	private void setBaraSplitLogic(ContinuousComponent var1) {
		BuggyEntity var2 = this;
		ContinuousComponent$IStartContinuousEvent var3 = (l0, l1) -> {
			this.splitBombs = false;
			this.level().broadcastEntityEvent((Entity)(this),(byte)(102));
			this.removeEffect((Holder)(ModEffects.MOVEMENT_BLOCKED));
			AbilityHelper.setDeltaMovement((Entity)(this),0D,1.5D,0D);
			Iterator lv13_3 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.level()),(LivingEntity)(this),3D,TargetPredicate.DEFAULT_AREA_CHECK,LivingEntity.class).iterator();
			do{
				if (lv13_3.hasNext()){
					LivingEntity lv13_4 = ((LivingEntity)(lv13_3.next()));
					Vec3 lv13_5 = this.getLookAngle().multiply(2.5D,1D,2.5D);
					AbilityHelper.setDeltaMovement((Entity)(lv13_4),lv13_5.x,0.25D,lv13_5.z);
					continue;
				}else{
					break;
				}
			} while (true);
		};
		var1.addStartEvent(101,var3);
		BuggyEntity var4 = this;
		ContinuousComponent var5 = var1;
		final ContinuousComponent var5_f1 = var5;
		ContinuousComponent$IDuringContinuousEvent var6 = (l0, l1) -> {
			if ((!(this.splitBombs)) && (!((var5_f1.getContinueTime()) < (20F)))){
				boolean lv14_4 = false;
				int lv14_5 = 3;
				int lv14_6 = 4;
				if (this.isDifficultyHardOrAbove()){
					lv14_6 = 12;
					lv14_5 = 5;
					lv14_4 = true;
				}else{
					if (((double)(this.getHealth())) < (WyHelper.percentage(40D,(double)(this.getMaxHealth())))){
						lv14_6 = 8;
					}
				}
				if ((lv14_6) <= (0)){
					return;
				}else{
					if (lv14_4){
						double lv14_7 = 0D;
						do{
							if ((lv14_7) <= (6.283185307179586D)){
								double lv14_8 = ((((double)(lv14_5)) / (2D)) * (Math.cos(lv14_7))) + ((WyHelper.randomDouble()) / (5D));
								double lv14_9 = ((((double)(lv14_5)) / (2D)) * (Math.sin(lv14_7))) + ((WyHelper.randomDouble()) / (5D));
								Vec3 lv14_10 = this.position().add(new Vec3(lv14_8,this.position().y,lv14_9));
								Vec3 lv14_11 = this.position().subtract(lv14_10).normalize().multiply(7.5D,1D,7.5D);
								BombEntity lv14_12 = new BombEntity((Level)(this.level()),(LivingEntity)(this));
								lv14_12.setExplodeOnImpact();
								lv14_12.moveTo((this.position().x) + (lv14_8),this.position().y,(this.position().z) + (lv14_9),(float)(this.getRandom().nextInt(360)),(float)(this.getRandom().nextInt(360)));
								AbilityHelper.setDeltaMovement(lv14_12,-(lv14_11.x),1D,-(lv14_11.z));
								this.level().addFreshEntity((Entity)(lv14_12));
								lv14_7 = (lv14_7) + ((3.141592653589793D) / (((double)(lv14_6)) / (2D)));
								continue;
							}else{
								break;
							}
						} while (true);
					}
					double lv14_7_1 = 0D;
					do{
						if ((lv14_7_1) <= (6.283185307179586D)){
							double lv14_8 = (((double)(lv14_5)) * (Math.cos(lv14_7_1))) + ((WyHelper.randomDouble()) / (5D));
							double lv14_9 = (((double)(lv14_5)) * (Math.sin(lv14_7_1))) + ((WyHelper.randomDouble()) / (5D));
							Vec3 lv14_10_1 = this.position().add(new Vec3(lv14_8,this.position().y,lv14_9));
							Vec3 lv14_11_1 = this.position().subtract(lv14_10_1).normalize().multiply(7.5D,1D,7.5D);
							BombEntity lv14_12_1 = new BombEntity((Level)(this.level()),(LivingEntity)(this));
							lv14_12_1.setExplodeOnImpact();
							lv14_12_1.moveTo((this.position().x) + (lv14_8),this.position().y,(this.position().z) + (lv14_9),(float)(this.getRandom().nextInt(360)),(float)(this.getRandom().nextInt(360)));
							AbilityHelper.setDeltaMovement(lv14_12_1,-(lv14_11_1.x),1D,-(lv14_11_1.z));
							this.level().addFreshEntity((Entity)(lv14_12_1));
							lv14_7_1 = (lv14_7_1) + ((3.141592653589793D) / ((double)(lv14_6)));
							continue;
						}else{
							break;
						}
					} while (true);
					this.level().broadcastEntityEvent((Entity)(this),(byte)(110));
					this.splitBombs = true;
					return;
				}
			}else{
				return;
			}
		};
		var1.addTickEvent(var6);
		BuggyEntity var7 = this;
		ContinuousComponent$IEndContinuousEvent var8 = (l0, l1) -> {
			this.level().broadcastEntityEvent((Entity)(this),(byte)(100));
		};
		var1.addEndEvent(var8);
	}
	public void startNormalPhase() {
		this.getPhaseManager().setPhase(this.normalPhase);
		this.level().broadcastEntityEvent((Entity)(this),(byte)(105));
	}
	public void startCarPhase() {
		this.getPhaseManager().setPhase(this.carPhase);
		this.level().broadcastEntityEvent((Entity)(this),(byte)(104));
		this.kitingMeter.reduceRevengeValue(-10);
	}
	public boolean isNormalPhaseActive() {
		return this.normalPhase.isActive(this);
	}
	public boolean isCarPhaseActive() {
		if ((this.carPhase) == (null)){
			return false;
		}else{
			return this.carPhase.isActive(this);
		}
	}
	public float getKitingMeterCompletion() {
		return this.kitingMeter.getRevengePercentage();
	}
}