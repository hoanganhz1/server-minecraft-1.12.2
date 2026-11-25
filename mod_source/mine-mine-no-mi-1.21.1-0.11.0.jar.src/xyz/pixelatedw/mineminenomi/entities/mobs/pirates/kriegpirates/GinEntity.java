package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.kriegpirates;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.TakedownKickAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.AntiMannerKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.ConcasseAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.PartyTableKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.JishinHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.mandemontactics.DemonicDanceAbility;
import xyz.pixelatedw.mineminenomi.abilities.mandemontactics.DemonicDashAbility;
import xyz.pixelatedw.mineminenomi.abilities.mandemontactics.DemonicSmashAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
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
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.DeadzoneRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.IRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.PhysicalHitRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AntiAirAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class GinEntity extends OPBossEntity {
	// Fields
	private static final UUID HARD_DEMON_DANCE_CHARGE_BONUS_UUID = UUID.fromString("8bde2608-50af-4a26-89a1-28e27ccdb933");
	private static final UUID HARD_DEMON_DASH_CHARGE_BONUS_UUID = UUID.fromString("f62f6b91-677f-4612-8464-f840fd7752a4");
	private static final UUID HARD_CONCASSE_COOLDOWN_BONUS_UUID = UUID.fromString("b88e2563-a90e-4976-b83e-1094b411b0cd");
	private static final UUID HARD_TAKEDOWN_KICK_COOLDOWN_BONUS_UUID = UUID.fromString("6f76d9e5-94ad-48bd-ab28-aae1860ea8f2");
	private RevengeMeter revengeMeter;
	private NPCPhase<GinEntity> normalPhase;
	private NPCPhase<GinEntity> emptyHandedPhase;

	public GinEntity(EntityType<GinEntity> var1, Level var2) {
		super(var1,var2);
	}
	public GinEntity(InProgressChallenge var1) {
		super(((EntityType)(ModMobs.GIN.get())),var1);
	}
	public void initBoss() {
		this.revengeMeter = new RevengeMeter((LivingEntity)(this),100,1);
		this.normalPhase = new SimplePhase("Normal Phase",this);
		this.emptyHandedPhase = new SimplePhase("Empty Handed Phase",this,(SimplePhase$IStartPhaseEvent)(((xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStartPhaseEvent)(this::startEmptyHandedPhaseEvent))));
		this.entityStats.setFaction(((Faction)(ModFactions.PIRATE.get())));
		this.entityStats.setRace(((Race)(ModRaces.HUMAN.get())));
		this.entityStats.setFightingStyle(((FightingStyle)(ModFightingStyles.BRAWLER.get())));
		FactionsWorldData.get().addTemporaryCrewMember(ModNPCGroups.KRIEG_PIRATES,(LivingEntity)(this));
		this.setItemSlot(EquipmentSlot.MAINHAND,new ItemStack(((ItemLike)(ModWeapons.TONFA.get()))));
		this.setItemSlot(EquipmentSlot.OFFHAND,new ItemStack(((ItemLike)(ModWeapons.TONFA.get()))));
		this.getAttribute(Attributes.ENTITY_INTERACTION_RANGE).setBaseValue(1.2D);
		this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(2D);
		this.getAttribute(NeoForgeMod.SWIM_SPEED).setBaseValue(3D);
		this.revengeMeter.addCheck(new PhysicalHitRevengeCheck(2));
		this.revengeMeter.addCheck(new DeadzoneRevengeCheck(10));
		JumpAbilityWrapperGoal var1 = new JumpAbilityWrapperGoal((Mob)(this),((AbilityCore)(DemonicDanceAbility.INSTANCE.get())));
		DashAbilityWrapperGoal var2 = new DashAbilityWrapperGoal((Mob)(this),((AbilityCore)(DemonicDashAbility.INSTANCE.get())));
		DemonicDashAbility var3 = ((DemonicDashAbility)(var2.getAbility()));
		JumpAbilityWrapperGoal var4 = var1;
		final JumpAbilityWrapperGoal var4_f1 = var4;
		Ability$ICanUseEvent var5 = (l0, l1) -> {
			if ((!(((DemonicDanceAbility)(var4_f1.getAbility())).isCharging())) && (!(((DemonicDanceAbility)(var4_f1.getAbility())).isContinuous()))){
				return Result.success();
			}else{
				return Result.fail(null);
			}
		};
		var3.addCanUseCheck(var5);
		CloseMeleeAbilityWrapperGoal var6 = new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(DemonicSmashAbility.INSTANCE.get())));
		DemonicSmashAbility var7 = ((DemonicSmashAbility)(var6.getAbility()));
		JumpAbilityWrapperGoal var8 = var1;
		final JumpAbilityWrapperGoal var8_f2 = var8;
		Ability$ICanUseEvent var9 = (l0, l1) -> {
			if ((!(((DemonicDanceAbility)(var8_f2.getAbility())).isCharging())) && (!(((DemonicDanceAbility)(var8_f2.getAbility())).isContinuous()))){
				return Result.success();
			}else{
				return Result.fail(null);
			}
		};
		var7.addCanUseCheck(var9);
		ImprovedMeleeAttackGoal var10 = new ImprovedMeleeAttackGoal((PathfinderMob)(this),1D,true);
		JumpAbilityWrapperGoal var11 = var1;
		final JumpAbilityWrapperGoal var11_f3 = var11;
		Supplier<Boolean> var12 = () -> {
			return Boolean.valueOf(((DemonicDanceAbility)(var11_f3.getAbility())).isCharging());
		};
		var10.setEarlyStop(var12);
		AntiAirAbilityWrapperGoal var13 = new AntiAirAbilityWrapperGoal((Mob)(this),((AbilityCore)(TakedownKickAbility.INSTANCE.get())));
		MobsHelper.addBasicNPCGoals(this);
		this.goalSelector.addGoal(0,(Goal)(new JumpOutOfHoleGoal((Mob)(this))));
		this.goalSelector.addGoal(0,(Goal)(new DashDodgeProjectilesGoal((Mob)(this),300F,2.5F)));
		this.goalSelector.addGoal(0,(Goal)(new DashDodgeTargetGoal((Mob)(this),350F,3F)));
		this.goalSelector.addGoal(0,(Goal)(new SprintTowardsTargetGoal((Mob)(this))));
		this.goalSelector.addGoal(1,(Goal)(var10));
		this.goalSelector.addGoal(2,(Goal)(var13));
		this.normalPhase.addGoal(2,var1);
		this.normalPhase.addGoal(2,var2);
		this.normalPhase.addGoal(2,var6);
		this.getPhaseManager().setPhase(this.normalPhase);
		if (this.getChallengeInfo().isDifficultyStandard()){
			this.entityStats.setDoriki(2000D);
			this.getAttribute(ModAttributes.GCD).setBaseValue(40D);
		}else{
			this.entityStats.setDoriki(10000D);
			this.hakiCapability.setBusoshokuHakiExp(100F);
			this.hakiCapability.setKenbunshokuHakiExp(100F);
			this.getAttribute(ModAttributes.GCD).setBaseValue(20D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(3D);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3D);
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(6D);
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(300D);
			this.getAttribute(Attributes.ARMOR).setBaseValue(20D);
			this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(8D);
			this.getAttribute(NeoForgeMod.SWIM_SPEED).setBaseValue(3.5D);
			DemonicDanceAbility var14 = ((DemonicDanceAbility)(var1.getAbility()));
			Consumer<ChargeComponent> var15 = (l0) -> {
			l0.getMaxChargeBonusManager().addBonus(HARD_DEMON_DANCE_CHARGE_BONUS_UUID,"Hard Demon Dance Charge Bonus",BonusOperation.MUL,0.5F);
		};
			((java.util.Optional)(var14.getComponent(((AbilityComponentKey)(ModAbilityComponents.CHARGE.get()))))).ifPresent((Consumer)(var15));
			DemonicDashAbility var16 = ((DemonicDashAbility)(var2.getAbility()));
			Consumer<ChargeComponent> var17 = (l0) -> {
			l0.getMaxChargeBonusManager().addBonus(HARD_DEMON_DASH_CHARGE_BONUS_UUID,"Hard Demon Dash Charge Bonus",BonusOperation.MUL,0F);
		};
			((java.util.Optional)(var16.getComponent(((AbilityComponentKey)(ModAbilityComponents.CHARGE.get()))))).ifPresent((Consumer)(var17));
			JumpAbilityWrapperGoal var18 = new JumpAbilityWrapperGoal((Mob)(this),((AbilityCore)(ConcasseAbility.INSTANCE.get())));
			ConcasseAbility var19 = ((ConcasseAbility)(var18.getAbility()));
			Consumer<CooldownComponent> var20 = (l0) -> {
			l0.getBonusManager().addBonus(HARD_CONCASSE_COOLDOWN_BONUS_UUID,"Hard Concasse Cooldown Bonus",BonusOperation.ADD,100F);
		};
			((java.util.Optional)(var19.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var20));
			TakedownKickAbility var21 = ((TakedownKickAbility)(var13.getAbility()));
			Consumer<CooldownComponent> var22 = (l0) -> {
			l0.getBonusManager().addBonus(HARD_TAKEDOWN_KICK_COOLDOWN_BONUS_UUID,"Hard Takedown Kick Cooldown Bonus",BonusOperation.ADD,-40F);
		};
			((java.util.Optional)(var21.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var22));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiImbuingAbility.INSTANCE.get())))));
			this.emptyHandedPhase.addGoal(0,new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get()))));
			this.emptyHandedPhase.addGoal(2,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(HakaiHoAbility.INSTANCE.get()))));
			this.emptyHandedPhase.addGoal(2,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(JishinHoAbility.INSTANCE.get()))));
			this.emptyHandedPhase.addGoal(2,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(ShiganAbility.INSTANCE.get()))));
			this.emptyHandedPhase.addGoal(2,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(AntiMannerKickCourseAbility.INSTANCE.get()))));
			this.goalSelector.addGoal(2,(Goal)(new DashAbilityWrapperGoal((Mob)(this),((AbilityCore)(SoruAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(2,(Goal)(new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(PartyTableKickCourseAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(3,(Goal)(new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(RankyakuAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(3,(Goal)(new ReactiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(KamieAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(3,(Goal)(var18));
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.3100000023841858D).add(Attributes.ATTACK_DAMAGE,4D).add(Attributes.MAX_HEALTH,200D).add(Attributes.ARMOR,8D).add(Attributes.KNOCKBACK_RESISTANCE,0.1D).add(Attributes.ATTACK_KNOCKBACK,1D).add(Attributes.ATTACK_SPEED,5D);
	}
	public boolean doHurtTarget(Entity var1) {
		if (var1 instanceof LivingEntity){
			LivingEntity var2 = ((LivingEntity)(var1));
			int var3 = ((!(this.getMainHandItem().isEmpty())) && ((this.getMainHandItem().getItem()) == (ModWeapons.TONFA.get()))) ? (1) : (0);
			int var4 = ((!(this.getOffhandItem().isEmpty())) && ((this.getOffhandItem().getItem()) == (ModWeapons.TONFA.get()))) ? (1) : (0);
			if (((var3) == (0)) && ((var4) == (0))){

			}else{
				ItemsHelper.stopShieldAndStartCooldown(var2,100);
			}
		}
		return super.doHurtTarget(var1);
	}
	private void startEmptyHandedPhaseEvent(GinEntity var1) {
		this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(4D);
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4D);
	}
	public void tick() {
		super.tick();
		if ((!(this.level().isClientSide)) && ((this.isAlive()) && ((!(this.emptyHandedPhase.isActive(this))) && (this.getMainHandItem().isEmpty())))){
			this.getPhaseManager().setPhase(this.emptyHandedPhase);
		}
	}
	public Set<RevengeMeter> getRevengeMeters() {
		RevengeMeter[] var1 = new RevengeMeter[]{this.revengeMeter};
		return Sets.newHashSet(var1);
	}
}