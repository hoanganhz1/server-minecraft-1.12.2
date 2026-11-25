package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.blackcatpirates;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.ShakushiAbility;
import xyz.pixelatedw.mineminenomi.abilities.StealthFootAbility;
import xyz.pixelatedw.mineminenomi.abilities.TakedownKickAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.AntiMannerKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.GeppoAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent$IStartCooldownEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
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
import xyz.pixelatedw.mineminenomi.api.entities.revenge.GankingRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.IRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.PhysicalHitRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.TargetRunningAwayRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AntiAirAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
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

public class KuroEntity extends OPBossEntity {
	// Fields
	private static final UUID SHAKUSHI_RANGE_BONUS_UUID = UUID.fromString("80e205c9-4fc8-4fed-97ad-f3d8cec36bd6");
	private static final UUID HARD_SORU_COOLDOWN_BONUS_UUID = UUID.fromString("600830a0-5796-489b-892e-45432d273d29");
	private RevengeMeter revengeMeter;
	private NPCPhase<KuroEntity> normalPhase;
	private NPCPhase<KuroEntity> ultiPhase;
	private ActiveGuardAbilityWrapperGoal<ShakushiAbility> shakushiWrapper;

	public KuroEntity(EntityType<KuroEntity> var1, Level var2) {
		super(var1,var2);
	}
	public KuroEntity(InProgressChallenge var1) {
		super(((EntityType)(ModMobs.KURO.get())),var1);
	}
	public void initBoss() {
		this.revengeMeter = new RevengeMeter((LivingEntity)(this),100,1);
		this.normalPhase = new SimplePhase("Normal Phase",this);
		this.ultiPhase = new SimplePhase("Ulti Phase",this);
		this.entityStats.setFaction(((Faction)(ModFactions.PIRATE.get())));
		this.entityStats.setRace(((Race)(ModRaces.HUMAN.get())));
		this.entityStats.setFightingStyle(((FightingStyle)(ModFightingStyles.SWORDSMAN.get())));
		FactionsWorldData.get().addTemporaryCrewMember(ModNPCGroups.BLACK_CAT_PIRATES,(LivingEntity)(this));
		this.setItemSlot(EquipmentSlot.MAINHAND,new ItemStack(((ItemLike)(ModWeapons.CAT_CLAWS.get()))));
		this.setItemSlot(EquipmentSlot.OFFHAND,new ItemStack(((ItemLike)(ModWeapons.CAT_CLAWS.get()))));
		this.getAttribute(Attributes.ENTITY_INTERACTION_RANGE).setBaseValue(3.5D);
		this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(2D);
		this.getAttribute(NeoForgeMod.SWIM_SPEED).setBaseValue(3D);
		this.revengeMeter.addCheck(new DeadzoneRevengeCheck(3));
		this.revengeMeter.addCheck(new GankingRevengeCheck(10,5F));
		this.shakushiWrapper = (ActiveGuardAbilityWrapperGoal) (new ActiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(ShakushiAbility.INSTANCE.get()))));
		this.shakushiWrapper.setMaxDistance(100F);
		ShakushiAbility var1 = ((ShakushiAbility)(this.shakushiWrapper.getAbility()));
		KuroEntity var2 = this;
		Consumer<CooldownComponent> var3 = (l0) -> {
			KuroEntity lv2_2 = this;
			CooldownComponent lv2_3 = l0;
			final CooldownComponent lv2_3_f1 = lv2_3;
			CooldownComponent$IStartCooldownEvent lv2_4 = (l2_0, l2_1) -> {
				if ((this.ultiPhase.isActive(this)) && (lv2_3_f1.isOnCooldown())){
					this.getPhaseManager().setPhase(this.normalPhase);
				}
			};
			l0.addStartEvent(lv2_4);
		};
		((java.util.Optional)(var1.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var3));
		ShakushiAbility var4 = ((ShakushiAbility)(this.shakushiWrapper.getAbility()));
		Consumer<RangeComponent> var5 = (l0) -> {
			l0.getBonusManager().addBonus(SHAKUSHI_RANGE_BONUS_UUID,"Shakushi Range Bonus",BonusOperation.ADD,40F);
		};
		((java.util.Optional)(var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.RANGE.get()))))).ifPresent((Consumer)(var5));
		MobsHelper.addBasicNPCGoals(this);
		this.goalSelector.addGoal(0,(Goal)(new JumpOutOfHoleGoal((Mob)(this))));
		this.normalPhase.addGoal(0,new SprintTowardsTargetGoal((Mob)(this)));
		this.normalPhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),300F,2.5F));
		this.normalPhase.addGoal(0,new DashDodgeTargetGoal((Mob)(this),350F,3F));
		this.normalPhase.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1D,true)));
		this.normalPhase.addGoal(2,new AntiAirAbilityWrapperGoal((Mob)(this),((AbilityCore)(TakedownKickAbility.INSTANCE.get()))));
		this.normalPhase.addGoal(2,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(StealthFootAbility.INSTANCE.get()))).setActivationDistance(10F));
		this.ultiPhase.addGoal(0,this.shakushiWrapper);
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
			this.getAttribute(Attributes.ATTACK_SPEED).setBaseValue(6D);
			this.revengeMeter.addCheck(new PhysicalHitRevengeCheck(2));
			this.revengeMeter.addCheck(new TargetRunningAwayRevengeCheck(5,10F));
			ProjectileAbilityWrapperGoal var6 = new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(RankyakuAbility.INSTANCE.get())));
			RankyakuAbility var7 = ((RankyakuAbility)(var6.getAbility()));
			KuroEntity var8 = this;
			Consumer<CooldownComponent> var9 = (l0) -> {
			l0.startCooldown((LivingEntity)(this),100F);
		};
			((java.util.Optional)(var7.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var9));
			SoruAbility var10 = ((SoruAbility)(new DashAbilityWrapperGoal((Mob)(this),((AbilityCore)(SoruAbility.INSTANCE.get()))).getAbility()));
			Consumer<CooldownComponent> var11 = (l0) -> {
			l0.getBonusManager().addBonus(HARD_SORU_COOLDOWN_BONUS_UUID,"Hard Soru Cooldown Bonus",BonusOperation.MUL,0.5F);
		};
			((java.util.Optional)(var10.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var11));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiImbuingAbility.INSTANCE.get())))));
			this.normalPhase.addGoal(1,new ReactiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(GeppoAbility.INSTANCE.get()))));
			this.normalPhase.addGoal(2,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(AntiMannerKickCourseAbility.INSTANCE.get()))));
			this.normalPhase.addGoal(2,new DashAbilityWrapperGoal((Mob)(this),((AbilityCore)(SoruAbility.INSTANCE.get()))));
			this.normalPhase.addGoal(2,new ReactiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(KamieAbility.INSTANCE.get()))));
			this.normalPhase.addGoal(3,var6);
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.3100000023841858D).add(Attributes.ATTACK_DAMAGE,4D).add(Attributes.MAX_HEALTH,200D).add(Attributes.ARMOR,8D).add(Attributes.KNOCKBACK_RESISTANCE,0.15D).add(Attributes.ATTACK_SPEED,5D);
	}
	public void tick() {
		super.tick();
		if ((!(this.level().isClientSide)) && (this.isAlive())){
			boolean var1 = ((Boolean)(((ShakushiAbility)(this.shakushiWrapper.getAbility())).getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))).map((Function)(((Function<CooldownComponent, Boolean>)(CooldownComponent::isOnCooldown)))).orElse(Boolean.valueOf(false)))).booleanValue();
			if ((this.normalPhase.isActive(this)) && ((!(var1)) && (!((!(this.revengeMeter.isRevengeMaxed())) && (!(((double)(this.getHealth())) <= (WyHelper.percentage(20D,(double)(this.getMaxHealth()))))))))){
				this.getPhaseManager().setPhase(this.ultiPhase);
				this.revengeMeter.setRevengeValue(0);
			}else{
				return;
			}
		}
	}
	public Set<RevengeMeter> getRevengeMeters() {
		RevengeMeter[] var1 = new RevengeMeter[]{this.revengeMeter};
		return Sets.newHashSet(var1);
	}
}