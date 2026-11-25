package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates;

import java.util.Optional;
import java.util.function.Consumer;
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
import xyz.pixelatedw.mineminenomi.abilities.BattoStrikeAbility;
import xyz.pixelatedw.mineminenomi.abilities.BellyFlopAbility;
import xyz.pixelatedw.mineminenomi.abilities.SlamAbility;
import xyz.pixelatedw.mineminenomi.abilities.WeaponSpinAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.GeppoAbility;
import xyz.pixelatedw.mineminenomi.abilities.sube.SubeSubeDeflectAbility;
import xyz.pixelatedw.mineminenomi.abilities.sube.SubeSubeSpurAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInfo;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ClimbOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveRetreatAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.items.weapons.ModSwordItem;

public class AlvidaEntity extends OPBossEntity {
	// Fields
	private static final AbilityPool ALVIDA = new AbilityPool().addFlag("ignoreCooldown",true);
	private NPCPhase<AlvidaEntity> standardPhase;
	private NPCPhase<AlvidaEntity> slidePhase;

	public AlvidaEntity(EntityType<AlvidaEntity> var1, Level var2) {
		super(var1,var2);
		this.standardPhase = new SimplePhase("Standard Phase",this);
		this.slidePhase = new SimplePhase("Slide Phase",this);
	}
	public AlvidaEntity(EntityType<AlvidaEntity> var1, InProgressChallenge var2) {
		super(var1,var2);
		this.standardPhase = new SimplePhase("Standard Phase",this);
		this.slidePhase = new SimplePhase("Slide Phase",this);
	}
	public void initBoss() {
		ItemStack var1 = ((Item)(ModArmors.PLUME_HAT.get())).getDefaultInstance();
		int[] var2 = new int[]{12788538};
		var1.set(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(var2));
		this.setItemSlot(EquipmentSlot.HEAD,var1);
		this.setItemSlot(EquipmentSlot.MAINHAND,((ModSwordItem)(ModWeapons.MACE.get())).getDefaultInstance());
		FactionsWorldData var3 = FactionsWorldData.get();
		this.entityStats.setFaction(((Faction)(ModFactions.PIRATE.get())));
		this.entityStats.setRace(((Race)(ModRaces.HUMAN.get())));
		var3.addTemporaryCrewMember(ModNPCGroups.BUGGY_PIRATES,(LivingEntity)(this));
		this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(3D);
		this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(1D);
		this.goalSelector.addGoal(0,(Goal)(new ClimbOutOfHoleGoal((Mob)(this))));
		if (this.getChallengeInfo().isDifficultyStandard()){
			this.entityStats.setDoriki(1000D);
			this.getAttribute(ModAttributes.GCD).setBaseValue(40D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(4D);
		}else{
			this.devilFruitData.setDevilFruit(((AkumaNoMiItem)(ModFruits.SUBE_SUBE_NO_MI.get())));
			this.entityStats.setDoriki(10000D);
			this.hakiCapability.setBusoshokuHakiExp(100F);
			this.hakiCapability.setKenbunshokuHakiExp(100F);
			this.getAttribute(ModAttributes.GCD).setBaseValue(20D);
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(3D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(4D);
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(300D);
			this.getAttribute(Attributes.ARMOR).setBaseValue(10D);
			this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(8D);
		}
		MobsHelper.addBasicNPCGoals(this);
		JumpAbilityWrapperGoal var4 = new JumpAbilityWrapperGoal((Mob)(this),((AbilityCore)(SlamAbility.INSTANCE.get())));
		SlamAbility var5 = ((SlamAbility)(var4.getAbility()));
		AbilityComponent[] var6 = new AbilityComponent[]{new PoolComponent(var4.getAbility(),ALVIDA,new AbilityPool[0])};
		var5.addComponents(var6);
		CloseMeleeAbilityWrapperGoal var7 = new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(WeaponSpinAbility.INSTANCE.get())));
		WeaponSpinAbility var8 = ((WeaponSpinAbility)(var7.getAbility()));
		AbilityComponent[] var9 = new AbilityComponent[]{new PoolComponent(var7.getAbility(),ALVIDA,new AbilityPool[0])};
		var8.addComponents(var9);
		this.standardPhase.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1D,true)));
		this.standardPhase.addGoal(2,var4);
		this.standardPhase.addGoal(2,var7);
		this.standardPhase.addGoal(2,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(BattoStrikeAbility.INSTANCE.get()))));
		this.getPhaseManager().setPhase(this.standardPhase);
		if (this.getChallengeInfo().isDifficultyStandard()){
			JumpAbilityWrapperGoal var10 = new JumpAbilityWrapperGoal((Mob)(this),((AbilityCore)(BellyFlopAbility.INSTANCE.get())));
			BellyFlopAbility var11 = ((BellyFlopAbility)(var10.getAbility()));
			AbilityComponent[] var12 = new AbilityComponent[]{new PoolComponent(var10.getAbility(),ALVIDA,new AbilityPool[0])};
			var11.addComponents(var12);
			BellyFlopAbility var13 = ((BellyFlopAbility)(var10.getAbility()));
			AlvidaEntity var14 = this;
			Consumer<CooldownComponent> var15 = (l0) -> {
			l0.startCooldown((LivingEntity)(this),100F);
		};
			((java.util.Optional)(var13.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var15));
			this.standardPhase.addGoal(2,var10);
		}else{
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiHardeningAbility.INSTANCE.get())))));
			this.standardPhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),250F,1.25F));
			this.standardPhase.addGoal(0,new ReactiveRetreatAbilityWrapperGoal((Mob)(this),((AbilityCore)(GeppoAbility.INSTANCE.get()))));
			this.standardPhase.addGoal(2,new DashAbilityWrapperGoal((Mob)(this),((AbilityCore)(SubeSubeSpurAbility.INSTANCE.get()))));
			this.standardPhase.addGoal(2,new ReactiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(SubeSubeDeflectAbility.INSTANCE.get()))));
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.28999999165534973D).add(Attributes.ATTACK_DAMAGE,2D).add(Attributes.MAX_HEALTH,200D).add(Attributes.ARMOR,4D);
	}
}