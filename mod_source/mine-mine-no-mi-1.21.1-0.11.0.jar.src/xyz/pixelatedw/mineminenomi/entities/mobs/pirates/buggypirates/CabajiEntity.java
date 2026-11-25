package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates;

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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.carnivaltricks.IchirinZashiAbility;
import xyz.pixelatedw.mineminenomi.abilities.carnivaltricks.KajiOyajiAbility;
import xyz.pixelatedw.mineminenomi.abilities.carnivaltricks.KamikazeHyakkomaGekijoAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.HiryuKaenAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.YakkodoriAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.GeppoAbility;
import xyz.pixelatedw.mineminenomi.abilities.santoryu.OTatsumakiAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInfo;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.IPhasesEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.DeadzoneRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.IRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.PhysicalDamageDealtRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.ProjectileHitRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.TargetRunningAwayRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOverProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.RepeaterAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.items.weapons.ModSwordItem;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SToggleAnimationPacket;

public class CabajiEntity extends OPBossEntity {
	// Fields
	private static final byte NO_UNICYCLE_EVENT = 120;
	private NPCPhase<CabajiEntity> normalPhase;
	private NPCPhase<CabajiEntity> noUnicyclePhase;
	private RevengeMeter damageTakenMeter;
	private boolean hasUnicycle;

	public CabajiEntity(EntityType<CabajiEntity> var1, Level var2) {
		super(var1,var2);
		this.normalPhase = new SimplePhase("Normal Phase",this);
		this.noUnicyclePhase = new SimplePhase("No Unicycle Phase",this);
		this.damageTakenMeter = new RevengeMeter((LivingEntity)(this),200,0);
		this.hasUnicycle = true;
	}
	public CabajiEntity(InProgressChallenge var1) {
		super(((EntityType)(ModMobs.CABAJI.get())),var1);
		this.normalPhase = new SimplePhase("Normal Phase",this);
		this.noUnicyclePhase = new SimplePhase("No Unicycle Phase",this);
		this.damageTakenMeter = new RevengeMeter((LivingEntity)(this),200,0);
		this.hasUnicycle = true;
	}
	public void initBoss() {
		FactionsWorldData var1 = FactionsWorldData.get();
		this.entityStats.setFaction(((Faction)(ModFactions.PIRATE.get())));
		this.entityStats.setRace(((Race)(ModRaces.HUMAN.get())));
		this.entityStats.setFightingStyle(((FightingStyle)(ModFightingStyles.SWORDSMAN.get())));
		var1.addTemporaryCrewMember(ModNPCGroups.BUGGY_PIRATES,(LivingEntity)(this));
		this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(2D);
		this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(1D);
		this.getAttribute(NeoForgeMod.SWIM_SPEED).setBaseValue(2.5D);
		this.setItemSlot(EquipmentSlot.HEAD,((Item)(ModArmors.CABAJI_SCARF.get())).getDefaultInstance());
		this.setItemSlot(EquipmentSlot.MAINHAND,((ModSwordItem)(ModWeapons.CUTLASS.get())).getDefaultInstance());
		ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.playAnimation((LivingEntity)(this),ModAnimations.RIDE_UNICYCLE,-1,true),(LivingEntity)(this));
		MobsHelper.addBasicNPCGoals(this);
		this.goalSelector.addGoal(0,(Goal)(new JumpOutOfHoleGoal((Mob)(this))));
		this.goalSelector.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1.3D,true)));
		this.goalSelector.addGoal(1,(Goal)(new SprintTowardsTargetGoal((Mob)(this))));
		this.goalSelector.addGoal(2,(Goal)(new RepeaterAbilityWrapperGoal((Mob)(this),((AbilityCore)(KajiOyajiAbility.INSTANCE.get())))));
		this.goalSelector.addGoal(2,(Goal)(new RepeaterAbilityWrapperGoal((Mob)(this),((AbilityCore)(KamikazeHyakkomaGekijoAbility.INSTANCE.get())))));
		this.normalPhase.addGoal(3,new JumpAbilityWrapperGoal((Mob)(this),((AbilityCore)(IchirinZashiAbility.INSTANCE.get()))));
		this.getPhaseManager().setPhase(this.normalPhase);
		if (this.getChallengeInfo().isDifficultyStandard()){
			this.damageTakenMeter.addCheck(new PhysicalDamageDealtRevengeCheck());
			this.damageTakenMeter.addCheck(new ProjectileHitRevengeCheck(10));
			this.damageTakenMeter.addCheck(new DeadzoneRevengeCheck(10));
			this.damageTakenMeter.addCheck(new TargetRunningAwayRevengeCheck(10,10F));
			this.entityStats.setDoriki(1000D);
			this.getAttribute(ModAttributes.GCD).setBaseValue(40D);
			this.normalPhase.addGoal(0,new JumpOverProjectilesGoal((Mob)(this),150F,1.5F));
			this.noUnicyclePhase.addGoal(0,new DashDodgeTargetGoal((Mob)(this),250F,1.5F));
			this.noUnicyclePhase.addGoal(2,new DashAbilityWrapperGoal((Mob)(this),((AbilityCore)(ShiShishiSonsonAbility.INSTANCE.get()))));
		}else{
			this.damageTakenMeter.addCheck(new PhysicalDamageDealtRevengeCheck(1.25F));
			this.damageTakenMeter.addCheck(new ProjectileHitRevengeCheck(25));
			this.damageTakenMeter.addCheck(new DeadzoneRevengeCheck(25));
			this.damageTakenMeter.addCheck(new TargetRunningAwayRevengeCheck(25,10F));
			this.entityStats.setDoriki(10000D);
			this.hakiCapability.setBusoshokuHakiExp(100F);
			this.hakiCapability.setKenbunshokuHakiExp(100F);
			this.getAttribute(ModAttributes.GCD).setBaseValue(20D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(6D);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(12D);
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(4D);
			this.getAttribute(Attributes.ARMOR).setBaseValue(16D);
			this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(8D);
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(250D);
			this.getAttribute(NeoForgeMod.SWIM_SPEED).setBaseValue(3D);
			this.setItemSlot(EquipmentSlot.OFFHAND,((ModSwordItem)(ModWeapons.CUTLASS.get())).getDefaultInstance());
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiImbuingAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(2,(Goal)(new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(YakkodoriAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(2,(Goal)(new JumpAbilityWrapperGoal((Mob)(this),((AbilityCore)(HiryuKaenAbility.INSTANCE.get())))));
			this.normalPhase.addGoal(0,new JumpOverProjectilesGoal((Mob)(this),70F,1.5F));
			this.noUnicyclePhase.addGoal(0,new DashDodgeTargetGoal((Mob)(this),200F,3F));
			this.noUnicyclePhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),200F,1.25F));
			this.noUnicyclePhase.addGoal(0,new ReactiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(GeppoAbility.INSTANCE.get()))));
			this.noUnicyclePhase.addGoal(2,new DashAbilityWrapperGoal((Mob)(this),((AbilityCore)(ShiShishiSonsonAbility.INSTANCE.get()))));
			this.noUnicyclePhase.addGoal(2,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(OTatsumakiAbility.INSTANCE.get()))));
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.3100000023841858D).add(Attributes.ATTACK_DAMAGE,2D).add(Attributes.MAX_HEALTH,150D).add(Attributes.ARMOR,2D);
	}
	public void tick() {
		super.tick();
		this.damageTakenMeter.tick();
		if ((!(this.level().isClientSide)) && ((this.isDifficultyHardOrAbove()) && ((this.normalPhase.isActive(this)) && (this.damageTakenMeter.isRevengeMaxed())))){
			this.getPhaseManager().setPhase(this.noUnicyclePhase);
			this.level().broadcastEntityEvent((Entity)(this),(byte)(120));
			ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.stopAnimation((LivingEntity)(this),ModAnimations.RIDE_UNICYCLE),(LivingEntity)(this));
			this.hasUnicycle = false;
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	public void handleEntityEvent(byte var1) {
		switch (var1){
		case 120:
			this.hasUnicycle = false;
		default:
			super.handleEntityEvent(var1);
			return;
		}
	}
	public boolean hasUnicycle() {
		return this.hasUnicycle;
	}
}