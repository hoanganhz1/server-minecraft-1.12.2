package xyz.pixelatedw.mineminenomi.entities.mobs.bandits;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
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
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.HiryuKaenAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.YakkodoriAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.GeppoAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.abilities.santoryu.OTatsumakiAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInfo;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.DeadzoneRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.IRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.PhysicalHitRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.TargetFlyStallingRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ClimbOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveRetreatAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.items.weapons.ModSwordItem;

public class HigumaEntity extends OPBossEntity {
	// Fields
	private RevengeMeter revengeMeter;
	private RevengeMeter hitsMeter;

	public HigumaEntity(EntityType<? extends HigumaEntity> var1, Level var2) {
		super(var1,var2);
	}
	public HigumaEntity(InProgressChallenge var1) {
		super(((EntityType)(ModMobs.HIGUMA.get())),var1);
	}
	public void initBoss() {
		this.revengeMeter = new RevengeMeter((LivingEntity)(this),100,1);
		this.hitsMeter = new RevengeMeter((LivingEntity)(this),3,0);
		this.entityStats.setFaction(((Faction)(ModFactions.BANDIT.get())));
		this.entityStats.setRace(((Race)(ModRaces.HUMAN.get())));
		this.entityStats.setFightingStyle(((FightingStyle)(ModFightingStyles.SWORDSMAN.get())));
		this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(2D);
		this.getAttribute(NeoForgeMod.SWIM_SPEED).setBaseValue(2.5D);
		this.revengeMeter.addCheck(new PhysicalHitRevengeCheck(1));
		this.revengeMeter.addCheck(new DeadzoneRevengeCheck(10));
		this.revengeMeter.addCheck(new TargetFlyStallingRevengeCheck(10));
		this.hitsMeter.addCheck(new PhysicalHitRevengeCheck(1));
		DashAbilityWrapperGoal var1 = new DashAbilityWrapperGoal((Mob)(this),((AbilityCore)(ShiShishiSonsonAbility.INSTANCE.get())));
		MobsHelper.addBasicNPCGoals(this);
		this.goalSelector.addGoal(0,(Goal)(new ClimbOutOfHoleGoal((Mob)(this))));
		this.goalSelector.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1D,true)));
		this.goalSelector.addGoal(1,(Goal)(new SprintTowardsTargetGoal((Mob)(this))));
		this.goalSelector.addGoal(2,(Goal)(var1));
		if (this.getChallengeInfo().isDifficultyStandard()){
			this.setItemSlot(EquipmentSlot.MAINHAND,((ModSwordItem)(ModWeapons.CUTLASS.get())).getDefaultInstance());
			this.entityStats.setDoriki(100D);
			this.getAttribute(ModAttributes.GCD).setBaseValue(40D);
			ShiShishiSonsonAbility var2 = ((ShiShishiSonsonAbility)(var1.getAbility()));
			HigumaEntity var3 = this;
			Ability$ICanUseEvent var4 = (l0, l1) -> {
			if ((this.revengeMeter.getRevengeValue()) >= (10)){
				return Result.success();
			}else{
				return Result.fail(null);
			}
		};
			var2.addCanUseCheck(var4);
			ShiShishiSonsonAbility var5 = ((ShiShishiSonsonAbility)(var1.getAbility()));
			HigumaEntity var6 = this;
			Ability$IOnUseEvent var7 = (l0, l1) -> {
			this.revengeMeter.reduceRevengeValue(10);
		};
			var5.addUseEvent(var7);
			this.goalSelector.addGoal(0,(Goal)(new DashDodgeTargetGoal((Mob)(this),250F,2F)));
		}else{
			this.entityStats.setDoriki(10000D);
			this.hakiCapability.setBusoshokuHakiExp(100F);
			this.hakiCapability.setKenbunshokuHakiExp(100F);
			this.getAttribute(ModAttributes.GCD).setBaseValue(20D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(6D);
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(4D);
			this.getAttribute(Attributes.ARMOR).setBaseValue(16D);
			this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(8D);
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(250D);
			this.getAttribute(NeoForgeMod.SWIM_SPEED).setBaseValue(3D);
			this.setItemSlot(EquipmentSlot.CHEST,new ItemStack(((ItemLike)(ModArmors.PIRATE_CAPTAIN_CAPE.get()))));
			this.setItemSlot(EquipmentSlot.MAINHAND,((ModSwordItem)(ModWeapons.KATANA.get())).getDefaultInstance());
			ReactiveRetreatAbilityWrapperGoal var2_1 = new ReactiveRetreatAbilityWrapperGoal((Mob)(this),this.hitsMeter,((AbilityCore)(SoruAbility.INSTANCE.get())));
			var2_1.setMaxDistance(100F);
			ReactiveRetreatAbilityWrapperGoal var3_1 = new ReactiveRetreatAbilityWrapperGoal((Mob)(this),this.hitsMeter,((AbilityCore)(GeppoAbility.INSTANCE.get())));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiImbuingAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(new DashDodgeTargetGoal((Mob)(this),200F,3F)));
			this.goalSelector.addGoal(0,(Goal)(new DashDodgeProjectilesGoal((Mob)(this),200F,1.25F)));
			this.goalSelector.addGoal(0,(Goal)(var2_1));
			this.goalSelector.addGoal(0,(Goal)(var3_1));
			this.goalSelector.addGoal(2,(Goal)(new DashAbilityWrapperGoal((Mob)(this),((AbilityCore)(SoruAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(2,(Goal)(new ActiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(KamieAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(2,(Goal)(new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(YakkodoriAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(2,(Goal)(new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(OTatsumakiAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(2,(Goal)(new JumpAbilityWrapperGoal((Mob)(this),((AbilityCore)(HiryuKaenAbility.INSTANCE.get())))));
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.3100000023841858D).add(Attributes.ATTACK_DAMAGE,2D).add(Attributes.MAX_HEALTH,100D).add(Attributes.ARMOR,2D);
	}
	public Set<RevengeMeter> getRevengeMeters() {
		RevengeMeter[] var1 = new RevengeMeter[]{this.revengeMeter,this.hitsMeter};
		return Sets.newHashSet(var1);
	}
}