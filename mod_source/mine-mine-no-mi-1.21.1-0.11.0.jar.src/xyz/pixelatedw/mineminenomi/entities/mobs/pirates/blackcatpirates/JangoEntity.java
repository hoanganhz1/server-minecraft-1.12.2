package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.blackcatpirates;

import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.OneTwoJangoAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.GeppoAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInfo;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.IPhasesEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ClimbOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.TauntTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ThrowChakramsGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveRetreatAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.items.weapons.ChakramItem;

public class JangoEntity extends OPBossEntity {
	// Fields
	private NPCPhase<JangoEntity> standardPhase;
	private NPCPhase<JangoEntity> tauntPhase;

	public JangoEntity(EntityType<JangoEntity> var1, Level var2) {
		super(var1,var2);
		this.standardPhase = new SimplePhase("Standard Phase",this);
		this.tauntPhase = new SimplePhase("Taunt Phase",this);
	}
	public JangoEntity(InProgressChallenge var1) {
		super(((EntityType)(ModMobs.JANGO.get())),var1);
		this.standardPhase = new SimplePhase("Standard Phase",this);
		this.tauntPhase = new SimplePhase("Taunt Phase",this);
	}
	public void initBoss() {
		ItemStack var1 = ((Item)(ModArmors.WIDE_BRIM_HAT.get())).getDefaultInstance();
		int[] var2 = new int[]{1646668};
		var1.set(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(var2));
		this.setItemSlot(EquipmentSlot.HEAD,var1);
		this.setItemSlot(EquipmentSlot.MAINHAND,((ChakramItem)(ModWeapons.CHAKRAM.get())).getDefaultInstance());
		if (this.isDifficultyHardOrAbove()){
			this.setItemSlot(EquipmentSlot.OFFHAND,((ChakramItem)(ModWeapons.CHAKRAM.get())).getDefaultInstance());
		}
		FactionsWorldData var3 = FactionsWorldData.get();
		this.entityStats.setFaction(((Faction)(ModFactions.PIRATE.get())));
		this.entityStats.setRace(((Race)(ModRaces.HUMAN.get())));
		var3.addTemporaryCrewMember(ModNPCGroups.BLACK_CAT_PIRATES,(LivingEntity)(this));
		this.goalSelector.addGoal(0,(Goal)(new ClimbOutOfHoleGoal((Mob)(this))));
		if (this.getChallengeInfo().isDifficultyStandard()){
			this.entityStats.setDoriki(1000D);
			this.getAttribute(ModAttributes.GCD).setBaseValue(40D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(4D);
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
		}
		MobsHelper.addBasicNPCGoals(this);
		ActiveGuardAbilityWrapperGoal var4 = new ActiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(OneTwoJangoAbility.INSTANCE.get())));
		this.standardPhase.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1D,true)));
		this.standardPhase.addGoal(1,var4);
		ActiveGuardAbilityWrapperGoal var5 = var4;
		final ActiveGuardAbilityWrapperGoal var5_f1 = var5;
		Predicate<LivingEntity> var6 = (l0) -> {
			if (((OneTwoJangoAbility)(var5_f1.getAbility())).isCharging()){
				return false;
			}else{
				return true;
			}
		};
		Predicate<LivingEntity> var7 = var6;
		this.getPhaseManager().setPhase(this.standardPhase);
		if (this.getChallengeInfo().isDifficultyStandard()){
			this.standardPhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),400F,1.5F).setCanUseTest(var7));
			this.standardPhase.addGoal(0,new DashDodgeTargetGoal((Mob)(this),450F,2F).setCanUseTest(var7));
			this.standardPhase.addGoal(2,new ThrowChakramsGoal((Mob)(this),200));
			((OneTwoJangoAbility)(var4.getAbility())).setAffectSelf(true);
		}else{
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiImbuingAbility.INSTANCE.get())))));
			this.standardPhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),200F,2.5F).setCanUseTest(var7));
			this.standardPhase.addGoal(0,new DashDodgeTargetGoal((Mob)(this),250F,2.5F).setCanUseTest(var7));
			this.standardPhase.addGoal(1,new DashAbilityWrapperGoal((Mob)(this),((AbilityCore)(SoruAbility.INSTANCE.get()))));
			this.standardPhase.addGoal(1,new ReactiveRetreatAbilityWrapperGoal((Mob)(this),((AbilityCore)(GeppoAbility.INSTANCE.get()))));
			this.standardPhase.addGoal(1,new ReactiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(KamieAbility.INSTANCE.get()))));
			this.standardPhase.addGoal(2,new ThrowChakramsGoal((Mob)(this),100));
			this.tauntPhase.addGoal(0,new TauntTargetGoal((Mob)(this)));
			this.tauntPhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),20F,4F));
			this.tauntPhase.addGoal(0,new ActiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(KamieAbility.INSTANCE.get()))));
			this.tauntPhase.addGoal(1,(Goal)(new ImprovedMeleeAttackGoal((PathfinderMob)(this),1D,true).setMoveTowardsTarget(false)));
			this.tauntPhase.addGoal(1,new DashDodgeTargetGoal((Mob)(this),60F,4F));
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.3100000023841858D).add(Attributes.ATTACK_DAMAGE,4D).add(Attributes.MAX_HEALTH,150D).add(Attributes.ATTACK_SPEED,5D);
	}
	public void tick() {
		super.tick();
		if ((!(this.level().isClientSide)) && (((this.getTarget()) != (null)) && ((this.getTarget().isAlive()) && (this.isDifficultyHardOrAbove())))){
			if ((this.standardPhase.isActive(this)) && ((this.getTarget().hasEffect((Holder)(ModEffects.UNCONSCIOUS))) && (GoalHelper.hasHealthAbovePercentage((LivingEntity)(this),70D)))){
				this.getPhaseManager().setPhase(this.tauntPhase);
			}else{
				if ((this.tauntPhase.isActive(this)) && (!(this.getTarget().hasEffect((Holder)(ModEffects.UNCONSCIOUS))))){
					this.getPhaseManager().setPhase(this.standardPhase);
				}else{
					return;
				}
			}
		}
	}
}