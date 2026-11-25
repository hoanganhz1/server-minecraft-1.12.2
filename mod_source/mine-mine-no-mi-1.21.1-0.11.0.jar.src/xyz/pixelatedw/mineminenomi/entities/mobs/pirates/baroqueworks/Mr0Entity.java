package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.HookGrabAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.KenbunshokuHakiFutureSightAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.BarjanAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.DesertEncierroAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.DesertGrandeEspadaAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.DesertSpadaAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.GroundSeccoAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.SablesGuardAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.SablesPesadoAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.SunaLogiaAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
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
import xyz.pixelatedw.mineminenomi.api.entities.IPhasesEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStartPhaseEvent;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.IRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.PhysicalDamageDealtRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.PhysicalHitRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.TargetFlyStallingRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.TargetRunningAwayRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.GrabAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.UseAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.mr0.Mr0PhaseSwitcherGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.items.weapons.ModSwordItem;

public class Mr0Entity extends OPBossEntity {
	// Fields
	private static final UUID HARD_GRANDE_SPADA_COOLDOWN_BONUS = UUID.fromString("5f6e5fd9-7292-42f0-895c-f0b3ff8da686");
	private static final UUID HARD_SABLES_GUARD_COOLDOWN_BONUS = UUID.fromString("c467addb-27c4-4865-9a01-655c621690e8");
	private static final UUID HARD_SABLES_PESADO_CHARGE_BONUS = UUID.fromString("8f324bfd-f8cd-434d-9467-3001fe7c92df");
	private static final ResourceLocation GCD_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","npc.mr0.gcd");
	private static final AttributeModifier GCD_MOD = new AttributeModifier(GCD_ID,-5D,AttributeModifier.Operation.ADD_VALUE);
	private static final int STANDARD_PAIN_THRESHOLD = 100;
	private static final int HARD_PAIN_THRESHOLD = 150;
	private static final int PAIN_GUARD_COOLDOWN = 200;
	private static final int KITING_THRESHOLD = 20;
	private final NPCPhase<Mr0Entity> firstPhase;
	private final NPCPhase<Mr0Entity> secondPhase;
	private final NPCPhase<Mr0Entity> thirdPhase;
	private final RevengeMeter painMeter;
	private final RevengeMeter kitingMeter;
	private GrabAbilityWrapperGoal<HookGrabAbility> hookGrabWrapper;
	private ActiveGuardAbilityWrapperGoal<SablesGuardAbility> sablesGuardWrapper;
	private GroundSeccoAbility groundSeccoAbility;
	private int painThreshold;

	public Mr0Entity(EntityType<Mr0Entity> var1, Level var2) {
		super(var1,var2);
		this.firstPhase = new SimplePhase("First Phase",this);
		this.secondPhase = new SimplePhase("Second Phase",this,(SimplePhase$IStartPhaseEvent)(((xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStartPhaseEvent)(this::startSecondPhaseEvent))));
		this.thirdPhase = new SimplePhase("Third Phase",this,(SimplePhase$IStartPhaseEvent)(((xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStartPhaseEvent)(this::startThirdPhaseEvent))));
		this.painMeter = new RevengeMeter((LivingEntity)(this),999,0);
		this.kitingMeter = new RevengeMeter((LivingEntity)(this),100,1);
		this.painThreshold = 100;
	}
	public Mr0Entity(InProgressChallenge var1) {
		super(((EntityType)(ModMobs.MR0.get())),var1);
		this.firstPhase = new SimplePhase("First Phase",this);
		this.secondPhase = new SimplePhase("Second Phase",this,(SimplePhase$IStartPhaseEvent)(((xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStartPhaseEvent)(this::startSecondPhaseEvent))));
		this.thirdPhase = new SimplePhase("Third Phase",this,(SimplePhase$IStartPhaseEvent)(((xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStartPhaseEvent)(this::startThirdPhaseEvent))));
		this.painMeter = new RevengeMeter((LivingEntity)(this),999,0);
		this.kitingMeter = new RevengeMeter((LivingEntity)(this),100,1);
		this.painThreshold = 100;
	}
	public void initBoss() {
		FactionsWorldData var1 = FactionsWorldData.get();
		this.entityStats.setFaction(((Faction)(ModFactions.PIRATE.get())));
		this.entityStats.setRace(((Race)(ModRaces.HUMAN.get())));
		this.devilFruitData.setDevilFruit(((AkumaNoMiItem)(ModFruits.SUNA_SUNA_NO_MI.get())));
		var1.addTemporaryCrewMember(ModNPCGroups.BAROQUE_WORKS,(LivingEntity)(this));
		this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(2D);
		this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(1D);
		ItemStack var2 = new ItemStack(((ItemLike)(ModArmors.FLUFFY_CAPE.get())));
		int[] var3 = new int[]{2040097};
		var2.set(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(var3));
		this.setItemSlot(EquipmentSlot.CHEST,var2);
		this.setItemSlot(EquipmentSlot.OFFHAND,((ModSwordItem)(ModWeapons.HOOK.get())).getDefaultInstance());
		this.setItemSlot(EquipmentSlot.HEAD,((Item)(ModItems.CIGAR.get())).getDefaultInstance());
		this.painMeter.addCheck(new PhysicalHitRevengeCheck(1));
		this.painMeter.addCheck(new PhysicalDamageDealtRevengeCheck());
		MobsHelper.addBasicNPCGoals(this);
		ProjectileAbilityWrapperGoal var4 = new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(BarjanAbility.INSTANCE.get())));
		GrabAbilityWrapperGoal var5 = new GrabAbilityWrapperGoal((Mob)(this),((AbilityCore)(DesertEncierroAbility.INSTANCE.get())));
		DesertEncierroAbility var6 = ((DesertEncierroAbility)(var5.getAbility()));
		Mr0Entity var7 = this;
		Ability$ICanUseEvent var8 = (l0, l1) -> {
			if (((SablesGuardAbility)(this.sablesGuardWrapper.getAbility())).isContinuous()){
				return Result.fail(null);
			}else{
				if (GoalHelper.hasEnoughTargetsAround((Mob)(this),2,20F)){
					return Result.fail(null);
				}else{
					return Result.success();
				}
			}
		};
		var6.addCanUseCheck(var8);
		ProjectileAbilityWrapperGoal var9 = new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(DesertGrandeEspadaAbility.INSTANCE.get())));
		DesertGrandeEspadaAbility var10 = ((DesertGrandeEspadaAbility)(var9.getAbility()));
		Mr0Entity var11 = this;
		Consumer<CooldownComponent> var12 = (l0) -> {
			l0.startCooldown((LivingEntity)(this),400F);
		};
		((java.util.Optional)(var10.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var12));
		this.hookGrabWrapper = (GrabAbilityWrapperGoal) (new GrabAbilityWrapperGoal((Mob)(this),((AbilityCore)(HookGrabAbility.INSTANCE.get()))));
		UseAbilityWrapperGoal var13 = new UseAbilityWrapperGoal((Mob)(this),((AbilityCore)(GroundSeccoAbility.INSTANCE.get())));
		this.groundSeccoAbility = ((GroundSeccoAbility)(var13.getAbility()));
		Mr0Entity var14 = this;
		Consumer<CooldownComponent> var15 = (l0) -> {
			l0.startCooldown((LivingEntity)(this),600F);
		};
		((java.util.Optional)(this.groundSeccoAbility.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var15));
		Mr0Entity var16 = this;
		Ability$ICanUseEvent var17 = (l0, l1) -> {
			if (((SablesGuardAbility)(this.sablesGuardWrapper.getAbility())).isContinuous()){
				return Result.fail(null);
			}else{
				if (((HookGrabAbility)(this.hookGrabWrapper.getAbility())).isContinuous()){
					return Result.fail(null);
				}else{
					return Result.success();
				}
			}
		};
		this.groundSeccoAbility.addCanUseCheck(var17);
		ProjectileAbilityWrapperGoal var18 = new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(SablesPesadoAbility.INSTANCE.get())));
		DesertSpadaAbility var19 = ((DesertSpadaAbility)(new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(DesertSpadaAbility.INSTANCE.get()))).getAbility()));
		Mr0Entity var20 = this;
		Consumer<CooldownComponent> var21 = (l0) -> {
			l0.startCooldown((LivingEntity)(this),200F);
		};
		((java.util.Optional)(var19.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var21));
		this.sablesGuardWrapper = (ActiveGuardAbilityWrapperGoal) (new ActiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(SablesGuardAbility.INSTANCE.get()))));
		SablesGuardAbility var22 = ((SablesGuardAbility)(this.sablesGuardWrapper.getAbility()));
		GrabAbilityWrapperGoal var23 = var5;
		final GrabAbilityWrapperGoal var23_f1 = var23;
		Ability$ICanUseEvent var24 = (l0, l1) -> {
			if (((DesertEncierroAbility)(var23_f1.getAbility())).isContinuous()){
				return Result.fail(null);
			}else{
				return Result.success();
			}
		};
		var22.addCanUseCheck(var24);
		ImprovedMeleeAttackGoal var25 = new ImprovedMeleeAttackGoal((PathfinderMob)(this),1D,true);
		Mr0Entity var26 = this;
		UseAbilityWrapperGoal var27 = var13;
		ProjectileAbilityWrapperGoal var28 = var18;
		GrabAbilityWrapperGoal var29 = var5;
		final UseAbilityWrapperGoal var27_f2 = var27;
		final ProjectileAbilityWrapperGoal var28_f3 = var28;
		final GrabAbilityWrapperGoal var29_f4 = var29;
		Supplier<Boolean> var30 = () -> {
			return Boolean.valueOf((((GroundSeccoAbility)(var27_f2.getAbility())).isCharging()) || ((((SablesGuardAbility)(this.sablesGuardWrapper.getAbility())).isContinuous()) || ((((SablesPesadoAbility)(var28_f3.getAbility())).isCharging()) || ((((DesertEncierroAbility)(var29_f4.getAbility())).isCharging()) || (((HookGrabAbility)(this.hookGrabWrapper.getAbility())).isContinuous())))));
		};
		var25.setEarlyStop(var30);
		this.goalSelector.addGoal(0,(Goal)(new Mr0PhaseSwitcherGoal(this)));
		this.goalSelector.addGoal(0,(Goal)(new AlwaysActiveAbilityWrapperGoal((Mob)(this),((AbilityCore)(SunaLogiaAbility.INSTANCE.get())))));
		this.goalSelector.addGoal(0,(Goal)(this.hookGrabWrapper));
		this.goalSelector.addGoal(0,(Goal)(new JumpOutOfHoleGoal((Mob)(this))));
		this.firstPhase.addGoal(1,(Goal)(var25));
		this.firstPhase.addGoal(3,var4);
		this.firstPhase.addGoal(3,this.sablesGuardWrapper);
		this.secondPhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),300F,2F));
		this.secondPhase.addGoal(0,new DashDodgeTargetGoal((Mob)(this),300F,2F));
		this.secondPhase.addGoal(1,(Goal)(var25));
		this.secondPhase.addGoal(3,var4);
		this.secondPhase.addGoal(3,this.sablesGuardWrapper);
		this.secondPhase.addGoal(3,var5);
		this.secondPhase.addGoal(3,var13);
		this.secondPhase.addGoal(3,var9);
		if (this.getChallengeInfo().isDifficultyStandard()){
			this.entityStats.setDoriki(2000D);
			this.getAttribute(ModAttributes.GCD).setBaseValue(40D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(4D);
			this.getPhaseManager().setPhase(this.firstPhase);
			HookGrabAbility var31 = ((HookGrabAbility)(this.hookGrabWrapper.getAbility()));
			Mr0Entity var32 = this;
			Consumer<CooldownComponent> var33 = (l0) -> {
			l0.startCooldown((LivingEntity)(this),400F);
		};
			((java.util.Optional)(var31.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var33));
		}else{
			this.entityStats.setDoriki(10000D);
			this.hakiCapability.setBusoshokuHakiExp(100F);
			this.hakiCapability.setKenbunshokuHakiExp(100F);
			this.getAttribute(ModAttributes.GCD).setBaseValue(20D);
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(6D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(4D);
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(400D);
			this.getAttribute(Attributes.ARMOR).setBaseValue(20D);
			this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(8D);
			this.painThreshold = 150;
			this.kitingMeter.addCheck(new TargetFlyStallingRevengeCheck(1));
			this.kitingMeter.addCheck(new TargetRunningAwayRevengeCheck(1,10F));
			CloseMeleeAbilityWrapperGoal var31_1 = new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(HakaiHoAbility.INSTANCE.get())));
			HakaiHoAbility var32_1 = ((HakaiHoAbility)(var31_1.getAbility()));
			Ability$ICanUseEvent var33_1 = (l0, l1) -> {
			Function<AbilityData, DesertEncierroAbility> lv10_2 = (l2_0) -> {
				return ((DesertEncierroAbility)(l2_0.getEquippedAbility(((AbilityCore)(DesertEncierroAbility.INSTANCE.get())))));
			};
			DesertEncierroAbility lv10_3 = ((DesertEncierroAbility)(((java.util.Optional)(AbilityData.get(l0))).map((Function)(lv10_2)).orElse(null)));
			if (((lv10_3) != (null)) && (lv10_3.isContinuous())){
				return Result.fail(null);
			}else{
				return Result.success();
			}
		};
			var32_1.addCanUseCheck(var33_1);
			HakaiHoAbility var34 = ((HakaiHoAbility)(var31_1.getAbility()));
			Mr0Entity var35 = this;
			Consumer<CooldownComponent> var36 = (l0) -> {
			l0.startCooldown((LivingEntity)(this),200F);
		};
			((java.util.Optional)(var34.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var36));
			ReactiveGuardAbilityWrapperGoal var37 = new ReactiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(KamieAbility.INSTANCE.get())));
			KamieAbility var38 = ((KamieAbility)(var37.getAbility()));
			Mr0Entity var39 = this;
			Ability$ICanUseEvent var40 = (l0, l1) -> {
			if ((this.groundSeccoAbility.isOnCooldown()) && ((((Float)(this.groundSeccoAbility.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))).map((Function)(((Function<CooldownComponent, Float>)(CooldownComponent::getCooldown)))).orElse(Float.valueOf(0F)))).floatValue()) <= (100F))){
				return Result.fail(null);
			}else{
				return Result.success();
			}
		};
			var38.addCanUseCheck(var40);
			SablesPesadoAbility var41 = ((SablesPesadoAbility)(var18.getAbility()));
			Consumer<CooldownComponent> var42 = (l0) -> {
			l0.getBonusManager().addBonus(HARD_SABLES_PESADO_CHARGE_BONUS,"Hard Sables Pesado Charge Bonus",BonusOperation.ADD,-40F);
		};
			((java.util.Optional)(var41.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var42));
			DesertGrandeEspadaAbility var43 = ((DesertGrandeEspadaAbility)(var9.getAbility()));
			Consumer<CooldownComponent> var44 = (l0) -> {
			l0.getBonusManager().addBonus(HARD_GRANDE_SPADA_COOLDOWN_BONUS,"Hard Grande Spada Cooldown Bonus",BonusOperation.ADD,-100F);
		};
			((java.util.Optional)(var43.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var44));
			this.groundSeccoAbility.setSizeModifier(1.5F);
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get())))));
			this.secondPhase.addGoal(0,new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(KenbunshokuHakiFutureSightAbility.INSTANCE.get()))));
			this.secondPhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),250F,2.5F));
			this.secondPhase.addGoal(0,new DashDodgeTargetGoal((Mob)(this),250F,2.5F));
			this.secondPhase.addGoal(0,var37);
			this.secondPhase.addGoal(3,var31_1);
			this.thirdPhase.addGoal(0,new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(KenbunshokuHakiFutureSightAbility.INSTANCE.get()))));
			this.thirdPhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),250F,2.5F));
			this.thirdPhase.addGoal(0,new DashDodgeTargetGoal((Mob)(this),250F,2.5F));
			this.thirdPhase.addGoal(0,var37);
			this.thirdPhase.addGoal(1,(Goal)(var25));
			this.thirdPhase.addGoal(3,var31_1);
			this.thirdPhase.addGoal(3,var4);
			this.thirdPhase.addGoal(3,this.sablesGuardWrapper);
			this.thirdPhase.addGoal(3,var5);
			this.thirdPhase.addGoal(3,var13);
			this.thirdPhase.addGoal(3,var18);
			this.thirdPhase.addGoal(3,var9);
			this.getPhaseManager().setPhase(this.secondPhase);
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.2800000011920929D).add(Attributes.ATTACK_DAMAGE,6D).add(Attributes.MAX_HEALTH,300D).add(Attributes.ARMOR,2D).add(Attributes.KNOCKBACK_RESISTANCE,0.2D);
	}
	public void tick() {
		super.tick();
		if (!(this.level().isClientSide)){
			this.painMeter.tick();
			this.kitingMeter.tick();
			if ((!(((SablesGuardAbility)(this.sablesGuardWrapper.getAbility())).isContinuous())) && (((this.painMeter.getRevengeValue()) > (this.painThreshold)) && (this.sablesGuardWrapper.hasTimePassedSinceLastEnd(200F)))){
				this.painMeter.addRevengeValue(-(this.painThreshold));
				SablesGuardAbility var1 = ((SablesGuardAbility)(this.sablesGuardWrapper.getAbility()));
				Mr0Entity var2 = this;
				Consumer<CooldownComponent> var3 = (l0) -> {
			l0.stopCooldown((LivingEntity)(this));
		};
				((java.util.Optional)(var1.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var3));
				((SablesGuardAbility)(this.sablesGuardWrapper.getAbility())).use((LivingEntity)(this));
			}
			if ((!(((HookGrabAbility)(this.hookGrabWrapper.getAbility())).isContinuous())) && ((this.kitingMeter.getRevengeValue()) > (20))){
				this.kitingMeter.addRevengeValue(-20);
				HookGrabAbility var1_1 = ((HookGrabAbility)(this.hookGrabWrapper.getAbility()));
				Mr0Entity var2_1 = this;
				Consumer<CooldownComponent> var3_1 = (l0) -> {
			l0.stopCooldown((LivingEntity)(this));
		};
				((java.util.Optional)(var1_1.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var3_1));
			}else{
				return;
			}
		}
	}
	public void startSecondPhase() {
		this.getPhaseManager().setPhase(this.secondPhase);
	}
	public void startThirdPhase() {
		this.getPhaseManager().setPhase(this.thirdPhase);
	}
	public boolean isFirstPhaseActive() {
		return this.firstPhase.isActive(this);
	}
	public boolean isSecondPhaseActive() {
		return this.secondPhase.isActive(this);
	}
	public boolean isThirdPhaseActive() {
		return this.thirdPhase.isActive(this);
	}
	private void startSecondPhaseEvent(Mr0Entity var1) {
		if (this.isDifficultyHardOrAbove()){
			AttributeInstance var2 = var1.getAttribute(ModAttributes.GCD);
			if (((var2) != (null)) && (!(var2.hasModifier(GCD_ID)))){
				var2.addTransientModifier(GCD_MOD);
			}else{
				return;
			}
		}
	}
	private void startThirdPhaseEvent(Mr0Entity var1) {
		this.groundSeccoAbility.setSizeModifier(2F);
	}
	public RevengeMeter getPainMeter() {
		return this.painMeter;
	}
}