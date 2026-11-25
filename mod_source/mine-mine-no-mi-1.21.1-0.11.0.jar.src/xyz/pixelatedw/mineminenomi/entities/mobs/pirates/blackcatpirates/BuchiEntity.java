package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.blackcatpirates;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
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
import xyz.pixelatedw.mineminenomi.abilities.BellyFlopAbility;
import xyz.pixelatedw.mineminenomi.abilities.StealthFootAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.JishinHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.TackleAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.TekkaiAbility;
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
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ClimbOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.FindPartnerGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;

public class BuchiEntity extends OPBossEntity implements IGoalPartner<ShamEntity> {
	// Fields
	private static final UUID HARD_BELLY_STOMP_COOLDOWN_BONUS_UUID = UUID.fromString("28edc8a0-6668-4abc-ad2d-885ef10d6806");
	private static final UUID HARD_STEALTH_FOOT_COOLDOWN_BONUS_UUID = UUID.fromString("8b63c5b3-6b2f-4ef6-9397-96c3c5b6946d");
	private ShamEntity partner;

	public BuchiEntity(EntityType<BuchiEntity> var1, Level var2) {
		super(var1,var2);
	}
	public BuchiEntity(InProgressChallenge var1) {
		super(((EntityType)(ModMobs.BUCHI.get())),var1);
	}
	public void initBoss() {
		this.entityStats.setFaction(((Faction)(ModFactions.PIRATE.get())));
		this.entityStats.setRace(((Race)(ModRaces.HUMAN.get())));
		this.entityStats.setFightingStyle(((FightingStyle)(ModFightingStyles.BRAWLER.get())));
		FactionsWorldData.get().addTemporaryCrewMember(ModNPCGroups.BLACK_CAT_PIRATES,(LivingEntity)(this));
		this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(2D);
		this.getAttribute(NeoForgeMod.SWIM_SPEED).setBaseValue(1D);
		ItemStack var1 = new ItemStack(((ItemLike)(ModArmors.FLUFFY_CAPE.get())));
		int[] var2 = new int[]{15114218};
		var1.set(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(var2));
		this.setItemSlot(EquipmentSlot.CHEST,var1);
		JumpAbilityWrapperGoal var3 = new JumpAbilityWrapperGoal((Mob)(this),((AbilityCore)(BellyFlopAbility.INSTANCE.get())));
		DashAbilityWrapperGoal var4 = new DashAbilityWrapperGoal((Mob)(this),((AbilityCore)(TackleAbility.INSTANCE.get())));
		MobsHelper.addBasicNPCGoals(this);
		this.goalSelector.addGoal(0,(Goal)(new FindPartnerGoal(this,((EntityType)(ModMobs.SHAM.get())))));
		this.goalSelector.addGoal(0,(Goal)(new ClimbOutOfHoleGoal((Mob)(this))));
		this.goalSelector.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1D,true)));
		this.goalSelector.addGoal(2,(Goal)(new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(ChargedPunchAbility.INSTANCE.get())))));
		this.goalSelector.addGoal(2,(Goal)(var3));
		this.goalSelector.addGoal(2,(Goal)(var4));
		if (this.getChallengeInfo().isDifficultyStandard()){
			this.entityStats.setDoriki(2000D);
			this.getAttribute(ModAttributes.GCD).setBaseValue(40D);
		}else{
			this.entityStats.setDoriki(10000D);
			this.hakiCapability.setBusoshokuHakiExp(100F);
			this.hakiCapability.setKenbunshokuHakiExp(100F);
			this.getAttribute(ModAttributes.GCD).setBaseValue(20D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(4D);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4D);
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(5D);
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(300D);
			this.getAttribute(Attributes.ARMOR).setBaseValue(20D);
			this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(6D);
			this.getAttribute(NeoForgeMod.SWIM_SPEED).setBaseValue(2D);
			CloseMeleeAbilityWrapperGoal var5 = new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(StealthFootAbility.INSTANCE.get())));
			StealthFootAbility var6 = ((StealthFootAbility)(var5.getAbility()));
			BuchiEntity var7 = this;
			Ability$ICanUseEvent var8 = (l0, l1) -> {
			if (((double)(this.getHealth())) <= (WyHelper.percentage(30D,(double)(this.getMaxHealth())))){
				return Result.success();
			}else{
				return Result.fail(null);
			}
		};
			var6.addCanUseCheck(var8);
			StealthFootAbility var9 = ((StealthFootAbility)(var5.getAbility()));
			Consumer<CooldownComponent> var10 = (l0) -> {
			l0.getBonusManager().addBonus(HARD_STEALTH_FOOT_COOLDOWN_BONUS_UUID,"Hard Stealth Foot Cooldown Bonus",BonusOperation.ADD,300F);
		};
			((java.util.Optional)(var9.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var10));
			BellyFlopAbility var11 = ((BellyFlopAbility)(var3.getAbility()));
			Consumer<CooldownComponent> var12 = (l0) -> {
			l0.getBonusManager().addBonus(HARD_BELLY_STOMP_COOLDOWN_BONUS_UUID,"Belly Stomp Cooldown Bonus",BonusOperation.ADD,-80F);
		};
			((java.util.Optional)(var11.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var12));
			DashDodgeTargetGoal var13 = new DashDodgeTargetGoal((Mob)(this),300F,3F);
			JumpAbilityWrapperGoal var14 = var3;
			DashAbilityWrapperGoal var15 = var4;
			final JumpAbilityWrapperGoal var14_f1 = var14;
			final DashAbilityWrapperGoal var15_f2 = var15;
			Predicate<LivingEntity> var16 = (l0) -> {
			if (((BellyFlopAbility)(var14_f1.getAbility())).isContinuous()){
				return false;
			}else{
				if (((TackleAbility)(var15_f2.getAbility())).isContinuous()){
					return false;
				}else{
					return true;
				}
			}
		};
			var13.setCanUseTest(var16);
			DashDodgeProjectilesGoal var17 = new DashDodgeProjectilesGoal((Mob)(this),250F,3F);
			JumpAbilityWrapperGoal var18 = var3;
			DashAbilityWrapperGoal var19 = var4;
			final JumpAbilityWrapperGoal var18_f3 = var18;
			final DashAbilityWrapperGoal var19_f4 = var19;
			Predicate<LivingEntity> var20 = (l0) -> {
			if (((BellyFlopAbility)(var18_f3.getAbility())).isContinuous()){
				return false;
			}else{
				if (((TackleAbility)(var19_f4.getAbility())).isContinuous()){
					return false;
				}else{
					return true;
				}
			}
		};
			var17.setCanUseTest(var20);
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(var17));
			this.goalSelector.addGoal(0,(Goal)(var13));
			this.goalSelector.addGoal(2,(Goal)(new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(JishinHoAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(2,(Goal)(new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(HakaiHoAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(2,(Goal)(new ReactiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(TekkaiAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(2,(Goal)(var5));
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.30000001192092896D).add(Attributes.ATTACK_DAMAGE,4D).add(Attributes.MAX_HEALTH,150D).add(Attributes.KNOCKBACK_RESISTANCE,0.1D).add(Attributes.ARMOR,4D);
	}
	@Nullable()
	public ShamEntity getPartner() {
		return this.partner;
	}
	public void setPartner(ShamEntity var1) {
		this.partner = var1;
	}
}