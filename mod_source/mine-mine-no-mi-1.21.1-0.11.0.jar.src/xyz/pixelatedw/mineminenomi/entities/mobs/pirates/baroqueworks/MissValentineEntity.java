package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
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
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.kilo.KiloPress10000Ability;
import xyz.pixelatedw.mineminenomi.abilities.kilo.KiloPress1Ability;
import xyz.pixelatedw.mineminenomi.abilities.kilo.KiloPunch5000Ability;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInfo;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.IPhasesEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.IGoalPartner;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.FindPartnerGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.RunAwayFromTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveBuffAbilityWrapperGoal;
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
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class MissValentineEntity extends OPBossEntity implements IGoalPartner<Mr5Entity> {
	// Fields
	private static final UUID STANDARD_KILO_PRESS_DAMAGE_BONUS = UUID.fromString("6ee8ad2c-3e36-4a3a-a194-169387e32339");
	private Mr5Entity partner;
	private ActiveBuffAbilityWrapperGoal<KiloPress10000Ability> kiloPress10k;
	private JumpAbilityWrapperGoal<KiloPress1Ability> kiloPress1;
	private NPCPhase<MissValentineEntity> helperPhase;
	private NPCPhase<MissValentineEntity> soloPhase;

	public MissValentineEntity(EntityType<MissValentineEntity> var1, Level var2) {
		super(var1,var2);
	}
	public MissValentineEntity(InProgressChallenge var1) {
		super(((EntityType)(ModMobs.MISS_VALENTINE.get())),var1);
	}
	public void initBoss() {
		this.helperPhase = new SimplePhase("Helper Phase",this);
		this.soloPhase = new SimplePhase("Solo Phase",this);
		FactionsWorldData var1 = FactionsWorldData.get();
		this.entityStats.setFaction(((Faction)(ModFactions.PIRATE.get())));
		this.entityStats.setRace(((Race)(ModRaces.HUMAN.get())));
		this.devilFruitData.setDevilFruit(((AkumaNoMiItem)(ModFruits.KILO_KILO_NO_MI.get())));
		var1.addTemporaryCrewMember(ModNPCGroups.BAROQUE_WORKS,(LivingEntity)(this));
		this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(1D);
		this.setItemSlot(EquipmentSlot.MAINHAND,new ItemStack(((ItemLike)(ModWeapons.UMBRELLA.get()))));
		this.kiloPress10k = (ActiveBuffAbilityWrapperGoal) (KiloPress10000Ability.createWrapper((Mob)(this),((AbilityCore)(KiloPress10000Ability.INSTANCE.get()))));
		this.kiloPress1 = (JumpAbilityWrapperGoal) (KiloPress1Ability.createWrapper((Mob)(this),((AbilityCore)(KiloPress1Ability.INSTANCE.get()))));
		CloseMeleeAbilityWrapperGoal var2 = KiloPunch5000Ability.createWrapper((Mob)(this),((AbilityCore)(KiloPunch5000Ability.INSTANCE.get())));
		MobsHelper.addBasicNPCGoals(this);
		this.goalSelector.addGoal(0,(Goal)(new JumpOutOfHoleGoal((Mob)(this))));
		this.goalSelector.addGoal(3,(Goal)(var2));
		this.goalSelector.addGoal(4,(Goal)(this.kiloPress1));
		this.goalSelector.addGoal(5,(Goal)(this.kiloPress10k));
		this.helperPhase.addGoal(0,new FindPartnerGoal(this,((EntityType)(ModMobs.MR5.get()))));
		this.helperPhase.addGoal(1,(Goal)(new ImprovedMeleeAttackGoal((PathfinderMob)(this),0.8500000238418579D,true).setEarlyStop((Supplier)(((Supplier<Boolean>)(this::isInRangeForAbilities))))));
		this.soloPhase.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1.100000023841858D,true)));
		this.getPhaseManager().setPhase(this.helperPhase);
		if (this.getChallengeInfo().isDifficultyStandard()){
			this.entityStats.setDoriki(2000D);
			this.getAttribute(ModAttributes.GCD).setBaseValue(40D);
			KiloPress10000Ability var3 = ((KiloPress10000Ability)(this.kiloPress10k.getAbility()));
			Consumer<DealDamageComponent> var4 = (l0) -> {
			l0.getBonusManager().addBonus(STANDARD_KILO_PRESS_DAMAGE_BONUS,"Standard Kilo Press Damage Bonus",BonusOperation.MUL,0.5F);
		};
			((java.util.Optional)(var3.getComponent(((AbilityComponentKey)(ModAbilityComponents.DAMAGE.get()))))).ifPresent((Consumer)(var4));
			this.helperPhase.addGoal(0,new DashDodgeTargetGoal((Mob)(this),200F,4F));
			this.helperPhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),100F,3F));
			this.helperPhase.addGoal(1,new RunAwayFromTargetGoal(this,1.5D,100,200));
			MissValentineEntity var5 = this;
			Predicate<LivingEntity> var6 = (l0) -> {
			return this.onGround();
		};
			this.soloPhase.addGoal(0,new DashDodgeTargetGoal((Mob)(this),250F,3F).setCanUseTest(var6));
			MissValentineEntity var7 = this;
			Predicate<LivingEntity> var8 = (l0) -> {
			return this.onGround();
		};
			this.soloPhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),150F,2.5F).setCanUseTest(var8));
		}else{
			this.entityStats.setDoriki(10000D);
			this.hakiCapability.setBusoshokuHakiExp(100F);
			this.hakiCapability.setKenbunshokuHakiExp(100F);
			this.getAttribute(ModAttributes.GCD).setBaseValue(20D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(3D);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3D);
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(4D);
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(350D);
			this.getAttribute(Attributes.ARMOR).setBaseValue(20D);
			this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(8D);
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(3,(Goal)(new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(ShiganAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(3,(Goal)(new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(RankyakuAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(3,(Goal)(new DashAbilityWrapperGoal((Mob)(this),((AbilityCore)(SoruAbility.INSTANCE.get())))));
			this.helperPhase.addGoal(0,new DashDodgeTargetGoal((Mob)(this),100F,4F));
			this.helperPhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),60F,3F));
			this.helperPhase.addGoal(1,new RunAwayFromTargetGoal(this,1.7D,100,100));
			this.soloPhase.addGoal(0,new DashDodgeTargetGoal((Mob)(this),200F,4F));
			this.soloPhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),150F,3F));
			this.soloPhase.addGoal(3,new ReactiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(KamieAbility.INSTANCE.get()))));
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.30000001192092896D).add(Attributes.ATTACK_DAMAGE,2D).add(Attributes.MAX_HEALTH,80D).add(Attributes.ARMOR,0D);
	}
	public void tick() {
		super.tick();
		if ((!(this.level().isClientSide)) && ((this.getTarget()) != (null))){
			if (((this.tickCount) > (100)) && ((this.helperPhase.isActive(this)) && (!(((this.partner) != (null)) && (this.partner.isAlive()))))){
				this.getPhaseManager().setPhase(this.soloPhase);
			}
			boolean var1 = this.hasLineOfSight((Entity)(this.getTarget()));
			if (((Math.abs((this.getY()) - (this.getTarget().getY()))) > (10D)) && (!(var1))){
				KiloPress1Ability var2 = ((KiloPress1Ability)(this.kiloPress1.getAbility()));
				MissValentineEntity var3 = this;
				Consumer<CooldownComponent> var4 = (l0) -> {
			l0.stopCooldown((LivingEntity)(this));
		};
				((java.util.Optional)(var2.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var4));
				KiloPress10000Ability var5 = ((KiloPress10000Ability)(this.kiloPress10k.getAbility()));
				MissValentineEntity var6 = this;
				Consumer<CooldownComponent> var7 = (l0) -> {
			l0.stopCooldown((LivingEntity)(this));
		};
				((java.util.Optional)(var5.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var7));
				BlockPos var8 = WyHelper.findValidGroundLocation((Entity)(this),this.getTarget().blockPosition(),10,10);
				this.teleportTo((double)(var8.getX()),(double)((var8.getY()) + (35)),(double)(var8.getZ()));
			}else{
				return;
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
	public Mr5Entity getPartner() {
		return this.partner;
	}
	public void setPartner(Mr5Entity var1) {
		this.partner = var1;
	}
}