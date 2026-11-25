package xyz.pixelatedw.mineminenomi.entities.mobs.marines;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
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
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.ChargedCleaveAbility;
import xyz.pixelatedw.mineminenomi.abilities.SlamAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.JishinHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.TackleAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.TekkaiAbility;
import xyz.pixelatedw.mineminenomi.abilities.santoryu.OTatsumakiAbility;
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
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ClimbOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.morgan.MorganMusterGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.items.weapons.ModSwordItem;

public class MorganEntity extends OPBossEntity {
	// Fields
	private static final UUID HARD_TATSUMAKI_COOLDOWN_BONUS_UUID = UUID.fromString("4bd574cc-c5a2-4e73-8230-763ccf4544d5");
	private boolean usedMuster;

	public MorganEntity(EntityType<? extends MorganEntity> var1, Level var2) {
		super(var1,var2);
	}
	public MorganEntity(InProgressChallenge var1) {
		super(((EntityType)(ModMobs.MORGAN.get())),var1);
	}
	public void initBoss() {
		this.entityStats.setFaction(((Faction)(ModFactions.MARINE.get())));
		this.entityStats.setRace(((Race)(ModRaces.HUMAN.get())));
		this.entityStats.setFightingStyle(((FightingStyle)(ModFightingStyles.BRAWLER.get())));
		this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(2D);
		this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(1D);
		this.getAttribute(NeoForgeMod.SWIM_SPEED).setBaseValue(2.5D);
		this.setItemSlot(EquipmentSlot.HEAD,((Item)(ModArmors.IRON_JAW.get())).getDefaultInstance());
		this.setItemSlot(EquipmentSlot.MAINHAND,((ModSwordItem)(ModWeapons.AXE_HAND.get())).getDefaultInstance());
		if (this.isDifficultyHardOrAbove()){
			ItemStack var1 = new ItemStack(((ItemLike)(ModArmors.MARINE_CAPTAIN_CAPE.get())));
			int[] var2 = new int[]{33980};
			var1.set(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(var2));
			this.setItemSlot(EquipmentSlot.CHEST,var1);
		}
		MobsHelper.addBasicNPCGoals(this);
		CloseMeleeAbilityWrapperGoal var1_1 = new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(ChargedCleaveAbility.INSTANCE.get())));
		ChargedCleaveAbility var2_1 = ((ChargedCleaveAbility)(var1_1.getAbility()));
		Ability$ICanUseEvent var3 = (l0, l1) -> {
			TackleAbility lv1_2 = ((TackleAbility)(AbilityData.getEquippedAbility(l0,((AbilityCore)(TackleAbility.INSTANCE.get())))));
			if (((lv1_2) != (null)) && (lv1_2.isContinuous())){
				return Result.fail(null);
			}else{
				return Result.success();
			}
		};
		var2_1.addCanUseCheck(var3);
		DashAbilityWrapperGoal var4 = new DashAbilityWrapperGoal((Mob)(this),((AbilityCore)(TackleAbility.INSTANCE.get())));
		this.goalSelector.addGoal(0,(Goal)(new ClimbOutOfHoleGoal((Mob)(this))));
		this.goalSelector.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1D,true)));
		this.goalSelector.addGoal(1,(Goal)(new SprintTowardsTargetGoal((Mob)(this))));
		this.goalSelector.addGoal(3,(Goal)(var4));
		this.goalSelector.addGoal(3,(Goal)(var1_1));
		this.goalSelector.addGoal(3,(Goal)(new JumpAbilityWrapperGoal((Mob)(this),((AbilityCore)(SlamAbility.INSTANCE.get())))));
		if (this.getChallengeInfo().isDifficultyStandard()){
			this.entityStats.setDoriki(500D);
			this.getAttribute(ModAttributes.GCD).setBaseValue(40D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(3D);
		}else{
			this.entityStats.setDoriki(10000D);
			this.hakiCapability.setBusoshokuHakiExp(100F);
			this.hakiCapability.setKenbunshokuHakiExp(100F);
			this.getAttribute(ModAttributes.GCD).setBaseValue(20D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(6D);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(6D);
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(4D);
			this.getAttribute(Attributes.ARMOR).setBaseValue(10D);
			this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(8D);
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(250D);
			CloseMeleeAbilityWrapperGoal var5 = new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(OTatsumakiAbility.INSTANCE.get())));
			OTatsumakiAbility var6 = ((OTatsumakiAbility)(var5.getAbility()));
			Consumer<CooldownComponent> var7 = (l0) -> {
			l0.getBonusManager().addBonus(HARD_TATSUMAKI_COOLDOWN_BONUS_UUID,"Tatsumaki Cooldown Bonus",BonusOperation.ADD,-80F);
		};
			((java.util.Optional)(var6.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var7));
			ChargedCleaveAbility var8 = ((ChargedCleaveAbility)(var1_1.getAbility()));
			Ability$ICanUseEvent var9 = (l0, l1) -> {
			TekkaiAbility lv3_2 = ((TekkaiAbility)(AbilityData.getEquippedAbility(l0,((AbilityCore)(TekkaiAbility.INSTANCE.get())))));
			if (((lv3_2) != (null)) && (lv3_2.isContinuous())){
				return Result.fail(null);
			}else{
				return Result.success();
			}
		};
			var8.addCanUseCheck(var9);
			CloseMeleeAbilityWrapperGoal var10 = new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(ShiganAbility.INSTANCE.get())));
			ShiganAbility var11 = ((ShiganAbility)(var10.getAbility()));
			Ability$ICanUseEvent var12 = (l0, l1) -> {
			ChargedCleaveAbility lv4_2 = ((ChargedCleaveAbility)(AbilityData.getEquippedAbility(l0,((AbilityCore)(ChargedCleaveAbility.INSTANCE.get())))));
			if (((lv4_2) != (null)) && (lv4_2.isCharging())){
				return Result.fail(null);
			}else{
				return Result.success();
			}
		};
			var11.addCanUseCheck(var12);
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(new MorganMusterGoal(this)));
			this.goalSelector.addGoal(2,(Goal)(var10));
			this.goalSelector.addGoal(2,(Goal)(new DashAbilityWrapperGoal((Mob)(this),((AbilityCore)(SoruAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(2,(Goal)(new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(RankyakuAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(3,(Goal)(var5));
			this.goalSelector.addGoal(3,(Goal)(new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(JishinHoAbility.INSTANCE.get())))));
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,200D).add(Attributes.MOVEMENT_SPEED,0.28999999165534973D).add(Attributes.ATTACK_DAMAGE,6D).add(Attributes.MAX_HEALTH,200D).add(Attributes.ARMOR,10D).add(Attributes.KNOCKBACK_RESISTANCE,0.3D).add(Attributes.ATTACK_KNOCKBACK,1.5D);
	}
	public void tick() {
		super.tick();
	}
	public void swing(InteractionHand var1, boolean var2) {
		super.swing(var1,var2);
	}
}