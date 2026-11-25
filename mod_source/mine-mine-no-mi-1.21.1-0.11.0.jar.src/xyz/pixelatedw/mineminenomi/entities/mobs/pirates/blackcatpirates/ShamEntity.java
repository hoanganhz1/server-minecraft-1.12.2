package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.blackcatpirates;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.LeapAbility;
import xyz.pixelatedw.mineminenomi.abilities.StealPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.StealthFootAbility;
import xyz.pixelatedw.mineminenomi.abilities.TakedownKickAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.PartyTableKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
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
import xyz.pixelatedw.mineminenomi.api.entities.ai.IGoalPartner;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ClimbOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.FindPartnerGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AntiAirAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
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

public class ShamEntity extends OPBossEntity implements IGoalPartner<BuchiEntity> {
	// Fields
	private static final UUID STANDARD_STEAL_PUNCH_COOLDOWN_BONUS_UUID = UUID.fromString("2d213d2a-2b98-406e-b4dc-984865936edd");
	private static final UUID HARD_TAKEDOWN_KICK_COOLDOWN_BONUS_UUID = UUID.fromString("ebb94c72-15fd-4b10-b5be-ece547a81259");
	private static final UUID HARD_STEALTH_FOOT_COOLDOWN_BONUS_UUID = UUID.fromString("8b63c5b3-6b2f-4ef6-9397-96c3c5b6946d");
	private BuchiEntity partner;

	public ShamEntity(EntityType<ShamEntity> var1, Level var2) {
		super(var1,var2);
	}
	public ShamEntity(InProgressChallenge var1) {
		super(((EntityType)(ModMobs.SHAM.get())),var1);
	}
	public void initBoss() {
		this.entityStats.setFaction(((Faction)(ModFactions.PIRATE.get())));
		this.entityStats.setRace(((Race)(ModRaces.HUMAN.get())));
		this.entityStats.setFightingStyle(((FightingStyle)(ModFightingStyles.BRAWLER.get())));
		FactionsWorldData.get().addTemporaryCrewMember(ModNPCGroups.BLACK_CAT_PIRATES,(LivingEntity)(this));
		this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(1D);
		this.getAttribute(NeoForgeMod.SWIM_SPEED).setBaseValue(3D);
		CloseMeleeAbilityWrapperGoal var1 = new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(StealPunchAbility.INSTANCE.get())));
		AntiAirAbilityWrapperGoal var2 = new AntiAirAbilityWrapperGoal((Mob)(this),((AbilityCore)(TakedownKickAbility.INSTANCE.get())));
		JumpAbilityWrapperGoal var3 = new JumpAbilityWrapperGoal((Mob)(this),((AbilityCore)(LeapAbility.INSTANCE.get())));
		((LeapAbility)(var3.getAbility())).setLeapHeight(0.30000001192092896D);
		MobsHelper.addBasicNPCGoals(this);
		this.goalSelector.addGoal(0,(Goal)(new FindPartnerGoal(this,((EntityType)(ModMobs.BUCHI.get())))));
		this.goalSelector.addGoal(0,(Goal)(new SprintTowardsTargetGoal((Mob)(this))));
		this.goalSelector.addGoal(0,(Goal)(new ClimbOutOfHoleGoal((Mob)(this))));
		this.goalSelector.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1D,true)));
		this.goalSelector.addGoal(2,(Goal)(var1));
		this.goalSelector.addGoal(2,(Goal)(var3));
		this.goalSelector.addGoal(2,(Goal)(var2));
		if (this.getChallengeInfo().isDifficultyStandard()){
			this.entityStats.setDoriki(2000D);
			this.getAttribute(ModAttributes.GCD).setBaseValue(40D);
			StealPunchAbility var4 = ((StealPunchAbility)(var1.getAbility()));
			Consumer<CooldownComponent> var5 = (l0) -> {
			l0.getBonusManager().addBonus(STANDARD_STEAL_PUNCH_COOLDOWN_BONUS_UUID,"Standard Steal Punch Cooldown Bonus",BonusOperation.ADD,80F);
		};
			((java.util.Optional)(var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var5));
			this.goalSelector.addGoal(0,(Goal)(new DashDodgeProjectilesGoal((Mob)(this),300F,2.5F)));
			this.goalSelector.addGoal(0,(Goal)(new DashDodgeTargetGoal((Mob)(this),350F,3F)));
		}else{
			this.entityStats.setDoriki(10000D);
			this.hakiCapability.setBusoshokuHakiExp(100F);
			this.hakiCapability.setKenbunshokuHakiExp(100F);
			this.getAttribute(ModAttributes.GCD).setBaseValue(20D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(3D);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3D);
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(5D);
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(250D);
			this.getAttribute(Attributes.ARMOR).setBaseValue(20D);
			this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(4D);
			this.getAttribute(Attributes.ATTACK_SPEED).setBaseValue(6D);
			CloseMeleeAbilityWrapperGoal var4_1 = new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(StealthFootAbility.INSTANCE.get()))).setActivationDistance(10F);
			StealthFootAbility var5_1 = ((StealthFootAbility)(var4_1.getAbility()));
			ShamEntity var6 = this;
			Ability$ICanUseEvent var7 = (l0, l1) -> {
			if (((double)(this.getHealth())) <= (WyHelper.percentage(30D,(double)(this.getMaxHealth())))){
				return Result.success();
			}else{
				return Result.fail(null);
			}
		};
			var5_1.addCanUseCheck(var7);
			StealthFootAbility var8 = ((StealthFootAbility)(var4_1.getAbility()));
			Consumer<CooldownComponent> var9 = (l0) -> {
			l0.getBonusManager().addBonus(HARD_STEALTH_FOOT_COOLDOWN_BONUS_UUID,"Hard Stealth Foot Cooldown Bonus",BonusOperation.ADD,200F);
		};
			((java.util.Optional)(var8.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var9));
			TakedownKickAbility var10 = ((TakedownKickAbility)(var2.getAbility()));
			Consumer<CooldownComponent> var11 = (l0) -> {
			l0.getBonusManager().addBonus(HARD_TAKEDOWN_KICK_COOLDOWN_BONUS_UUID,"Hard Takedown Kick Cooldown Bonus",BonusOperation.ADD,-40F);
		};
			((java.util.Optional)(var10.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var11));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiHardeningAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(new DashDodgeProjectilesGoal((Mob)(this),150F,3F)));
			this.goalSelector.addGoal(0,(Goal)(new DashDodgeTargetGoal((Mob)(this),300F,3F)));
			this.goalSelector.addGoal(1,(Goal)(new DashAbilityWrapperGoal((Mob)(this),((AbilityCore)(SoruAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(1,(Goal)(new ReactiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(KamieAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(2,(Goal)(new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(ShiganAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(2,(Goal)(new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(PartyTableKickCourseAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(2,(Goal)(var4_1));
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.3100000023841858D).add(Attributes.ATTACK_DAMAGE,4D).add(Attributes.MAX_HEALTH,150D).add(Attributes.ATTACK_SPEED,5D);
	}
	@Nullable()
	public BuchiEntity getPartner() {
		return this.partner;
	}
	public void setPartner(BuchiEntity var1) {
		this.partner = var1;
	}
}