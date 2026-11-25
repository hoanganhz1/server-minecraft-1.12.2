package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
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
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.ExplosionImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.bomu.BreezeBreathBombAbility;
import xyz.pixelatedw.mineminenomi.abilities.bomu.ExplosivePunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.bomu.KickBombAbility;
import xyz.pixelatedw.mineminenomi.abilities.bomu.NoseFancyCannonAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
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
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.IGoalPartner;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.FindPartnerGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.StyleSwitchGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.StyleSwitchGoal$IStyleSwitchEvent;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.KairosekiBulletProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.NormalBulletProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.items.weapons.ModGunItem;

public class Mr5Entity extends OPBossEntity implements RangedAttackMob, IGoalPartner<MissValentineEntity> {
	// Fields
	private static final UUID HARD_BREEZE_BREATH_COOLDOWN_BONUS = UUID.fromString("f417a4af-840f-4f69-86f4-c44a15da5e9a");
	private static final int PAUSE_TICKS_AFTER_NOSE_FANCY = 40;
	private static final int SWITCH_RANGE = 5;
	private MissValentineEntity partner;
	private boolean lostGun;
	private ProjectileAbilityWrapperGoal<NoseFancyCannonAbility> noseFancyCannonWrapper;
	private ProjectileAbilityWrapperGoal<BreezeBreathBombAbility> breezeBreathBombWrapper;
	private NPCPhase<Mr5Entity> meleePhase;
	private NPCPhase<Mr5Entity> rangePhase;

	public Mr5Entity(EntityType<Mr5Entity> var1, Level var2) {
		super(var1,var2);
	}
	public Mr5Entity(InProgressChallenge var1) {
		super(((EntityType)(ModMobs.MR5.get())),var1);
	}
	public void initBoss() {
		this.meleePhase = new SimplePhase("Melee Phase",this);
		this.rangePhase = new SimplePhase("Range Phase",this);
		FactionsWorldData var1 = FactionsWorldData.get();
		this.entityStats.setFaction(((Faction)(ModFactions.PIRATE.get())));
		this.entityStats.setRace(((Race)(ModRaces.HUMAN.get())));
		this.devilFruitData.setDevilFruit(((AkumaNoMiItem)(ModFruits.BOMU_BOMU_NO_MI.get())));
		this.entityStats.setDoriki(2000D);
		var1.addTemporaryCrewMember(ModNPCGroups.BAROQUE_WORKS,(LivingEntity)(this));
		this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(2D);
		this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(1D);
		boolean var2 = this.getChallengeInfo().isDifficultyStandard();
		MobsHelper.addBasicNPCGoals(this);
		this.noseFancyCannonWrapper = (ProjectileAbilityWrapperGoal) (new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(NoseFancyCannonAbility.INSTANCE.get()))));
		this.breezeBreathBombWrapper = (ProjectileAbilityWrapperGoal) (new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(BreezeBreathBombAbility.INSTANCE.get()))));
		this.goalSelector.addGoal(0,(Goal)(new JumpOutOfHoleGoal((Mob)(this))));
		this.goalSelector.addGoal(0,(Goal)(new FindPartnerGoal(this,((EntityType)(ModMobs.MISS_VALENTINE.get())))));
		this.goalSelector.addGoal(0,(Goal)(new StyleSwitchGoal((Mob)(this),(StyleSwitchGoal$IStyleSwitchEvent)(((xyz.pixelatedw.mineminenomi.entities.ai.goals.StyleSwitchGoal$IStyleSwitchEvent)(this::onStyleSwitched))))));
		this.goalSelector.addGoal(0,(Goal)(new AlwaysActiveAbilityWrapperGoal((Mob)(this),((AbilityCore)(ExplosionImmunityAbility.INSTANCE.get())))));
		this.goalSelector.addGoal(3,(Goal)(this.noseFancyCannonWrapper));
		this.rangePhase.addGoal(3,this.breezeBreathBombWrapper);
		this.meleePhase.addGoal(1,(Goal)(new ImprovedMeleeAttackGoal((PathfinderMob)(this),0.949999988079071D,true).setEarlyStop((Supplier)(((Supplier<Boolean>)(this::isInRangeForAbilities))))));
		this.meleePhase.addGoal(3,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(KickBombAbility.INSTANCE.get()))));
		this.meleePhase.addGoal(3,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(ExplosivePunchAbility.INSTANCE.get()))));
		this.getPhaseManager().setPhase(this.rangePhase);
		this.spawnGunIfPossible();
		if (var2){
			this.entityStats.setDoriki(2000D);
			this.getAttribute(ModAttributes.GCD).setBaseValue(40D);
			this.rangePhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),150F,2.5F));
			this.rangePhase.addGoal(0,new DashDodgeTargetGoal((Mob)(this),80F,5F));
			this.rangePhase.addGoal(1,(Goal)(new RangedAttackGoal((RangedAttackMob)(this),1D,20,50F)));
		}else{
			this.entityStats.setDoriki(10000D);
			this.hakiCapability.setBusoshokuHakiExp(100F);
			this.hakiCapability.setKenbunshokuHakiExp(100F);
			this.getAttribute(ModAttributes.GCD).setBaseValue(20D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(3D);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3D);
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(6D);
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(400D);
			this.getAttribute(Attributes.ARMOR).setBaseValue(20D);
			this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(8D);
			BreezeBreathBombAbility var3 = ((BreezeBreathBombAbility)(this.breezeBreathBombWrapper.getAbility()));
			Consumer<CooldownComponent> var4 = (l0) -> {
			l0.getBonusManager().addBonus(HARD_BREEZE_BREATH_COOLDOWN_BONUS,"Hard Difficulty Breeze Breath Bonus",BonusOperation.ADD,-140F);
		};
			((java.util.Optional)(var3.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var4));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
			this.rangePhase.addGoal(0,new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiImbuingAbility.INSTANCE.get()))));
			this.rangePhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),100F,2.5F));
			this.rangePhase.addGoal(0,new DashDodgeTargetGoal((Mob)(this),40F,5F));
			this.rangePhase.addGoal(1,(Goal)(new RangedAttackGoal((RangedAttackMob)(this),1D,15,60F)));
			this.rangePhase.addGoal(0,new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiHardeningAbility.INSTANCE.get()))));
			this.meleePhase.addGoal(3,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(HakaiHoAbility.INSTANCE.get()))));
			this.meleePhase.addGoal(3,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(ShiganAbility.INSTANCE.get()))));
		}
	}
	private void spawnGunIfPossible() {
		if (this.lostGun){
			return;
		}else{
			if (this.getChallengeInfo().isDifficultyStandard()){
				this.setItemSlot(EquipmentSlot.MAINHAND,new ItemStack(((ItemLike)(ModWeapons.FLINTLOCK.get()))));
			}else{
				this.setItemSlot(EquipmentSlot.MAINHAND,new ItemStack(((ItemLike)(ModWeapons.WALKER.get()))));
				this.setItemSlot(EquipmentSlot.OFFHAND,new ItemStack(((ItemLike)(ModWeapons.WALKER.get()))));
			}
			return;
		}
	}
	private boolean isInRangePhase() {
		return ((java.lang.Object)(this.getPhaseManager().getCurrentPhase())).equals((Object)(this.rangePhase));
	}
	public void setItemSlot(EquipmentSlot var1, ItemStack var2) {
		if (((!(this.level().isClientSide())) && (!(((var2) != (null)) && (!(var2.isEmpty()))))) && (this.isInRangePhase())){
			this.lostGun = true;
		}
		super.setItemSlot(var1,var2);
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.2800000011920929D).add(Attributes.ATTACK_DAMAGE,4D).add(Attributes.MAX_HEALTH,150D).add(Attributes.KNOCKBACK_RESISTANCE,0.2D).add(Attributes.ARMOR,4D);
	}
	public void tick() {
		super.tick();
		if ((!(this.level().isClientSide)) && ((this.getTarget()) != (null))){
			boolean var1 = this.hasLineOfSight((Entity)(this.getTarget()));
			if (((Math.abs((this.getY()) - (this.getTarget().getY()))) > (5D)) && (!(var1))){
				BreezeBreathBombAbility var2 = ((BreezeBreathBombAbility)(this.breezeBreathBombWrapper.getAbility()));
				Mr5Entity var3 = this;
				Consumer<CooldownComponent> var4 = (l0) -> {
			l0.stopCooldown((LivingEntity)(this));
		};
				((java.util.Optional)(var2.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var4));
			}else{
				return;
			}
		}
	}
	private FightingStyle onStyleSwitched(Mob var1, LivingEntity var2, FightingStyle var3) {
		boolean var4 = false;
		if (((java.lang.Object)(var3)).equals(ModFightingStyles.SNIPER.get())){
			var4 = GoalHelper.isWithinDistance((LivingEntity)(var1),var2,5D);
			if (var4){
				this.getPhaseManager().setPhase(this.meleePhase);
				this.setItemSlot(EquipmentSlot.MAINHAND,ItemStack.EMPTY);
				if (this.getChallengeInfo().isDifficultyHard()){
					this.setItemSlot(EquipmentSlot.OFFHAND,ItemStack.EMPTY);
				}
				return ((FightingStyle)(ModFightingStyles.BRAWLER.get()));
			}
		}else{
			var4 = GoalHelper.isOutsideDistance((LivingEntity)(var1),var2,5D);
			if (var4){
				this.getPhaseManager().setPhase(this.rangePhase);
				this.spawnGunIfPossible();
				return ((FightingStyle)(ModFightingStyles.SNIPER.get()));
			}
		}
		return var3;
	}
	public void performRangedAttack(LivingEntity var1, float var2) {
		ItemStack var3 = this.getMainHandItem();
		if ((!(var3.isEmpty())) && ((var3.getItem() instanceof ModGunItem) && (this.noseFancyCannonWrapper.hasTimePassedSinceLastEnd(40F)))){
			if (((var1) != (null)) && ((var1.isAlive()) && (!(AbilityHelper.isInCreativeOrSpectator(var1))))){
				NuProjectileEntity var4 = new NormalBulletProjectile((Level)(this.level()),(LivingEntity)(this));
				float var5 = 8F;
				float var6 = 2F;
				if (this.getChallengeInfo().isDifficultyHard()){
					var4 = new KairosekiBulletProjectile((Level)(this.level()),(LivingEntity)(this));
					var5 = 10F;
					var6 = 2.5F;
				}
				var4.setDamage(var5);
				this.level().addFreshEntity((Entity)(var4));
				double var7 = (var1.getX()) - (this.getX());
				double var8 = (var1.getEyeY()) - (var4.getY());
				var4.shoot(var7,var8,(var1.getZ()) - (this.getZ()),var6,0F);
				return;
			}else{
				return;
			}
		}else{
			return;
		}
	}
	private boolean isInRangeForAbilities() {
		if (!(GoalHelper.hasAliveTarget((Mob)(this)))){
			return true;
		}else{
			return (Math.abs(this.distanceTo((Entity)(this.getTarget())))) < (30F);
		}
	}
	public MissValentineEntity getPartner() {
		return this.partner;
	}
	public void setPartner(MissValentineEntity var1) {
		this.partner = var1;
	}
}