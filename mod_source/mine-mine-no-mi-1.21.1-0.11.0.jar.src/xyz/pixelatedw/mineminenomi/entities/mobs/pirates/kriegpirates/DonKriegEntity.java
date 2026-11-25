package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.kriegpirates;

import com.google.common.collect.Iterables;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.BakudanAbility;
import xyz.pixelatedw.mineminenomi.abilities.ChargedCleaveAbility;
import xyz.pixelatedw.mineminenomi.abilities.GunArrayAbility;
import xyz.pixelatedw.mineminenomi.abilities.MH5Ability;
import xyz.pixelatedw.mineminenomi.abilities.SlamAbility;
import xyz.pixelatedw.mineminenomi.abilities.WootzNetLauncherAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.JishinHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInfo;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.IPhasesEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStartPhaseEvent;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveBuffAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.donkrieg.DonKriegPhaseSwitcherGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.donkrieg.MH5PhaseGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class DonKriegEntity extends OPBossEntity {
	// Fields
	private static final UUID STANDARD_BAKUDAN_COOLDOWN_BONUS_UUID = UUID.fromString("5eeb8a76-d0d9-4fdd-816d-7a8ff4e903ad");
	private static final UUID STANDARD_KAEN_HOSHA_COOLDOWN_BONUS_UUID = UUID.fromString("6445513d-f018-42db-9be5-4f3f08037a50");
	private static final ResourceLocation GCD_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","npc.don_krieg.gcd");
	private static final AttributeModifier GCD_MOD = new AttributeModifier(GCD_ID,-5D,AttributeModifier.Operation.ADD_VALUE);
	private final NPCPhase<DonKriegEntity> fistPhase;
	private final NPCPhase<DonKriegEntity> daisensoPhase;
	private final NPCPhase<DonKriegEntity> mh5Phase;
	private final NonNullList<ItemStack> bulletsStash;
	private final NonNullList<ItemStack> netsStash;

	public DonKriegEntity(EntityType<DonKriegEntity> var1, Level var2) {
		super(var1,var2);
		this.fistPhase = new SimplePhase("Fist Phase",this);
		this.daisensoPhase = new SimplePhase("Daisenso Phase",this,(SimplePhase$IStartPhaseEvent)(((xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStartPhaseEvent)(this::startDaisensoPhaseEvent))));
		this.mh5Phase = new SimplePhase("MH5 Phase",this,(SimplePhase$IStartPhaseEvent)(((xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStartPhaseEvent)(this::startMH5PhaseEvent))));
		this.bulletsStash = (NonNullList) (NonNullList.withSize(5,ItemStack.EMPTY));
		this.netsStash = (NonNullList) (NonNullList.withSize(5,ItemStack.EMPTY));
	}
	public DonKriegEntity(InProgressChallenge var1) {
		super(((EntityType)(ModMobs.DON_KRIEG.get())),var1);
		this.fistPhase = new SimplePhase("Fist Phase",this);
		this.daisensoPhase = new SimplePhase("Daisenso Phase",this,(SimplePhase$IStartPhaseEvent)(((xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStartPhaseEvent)(this::startDaisensoPhaseEvent))));
		this.mh5Phase = new SimplePhase("MH5 Phase",this,(SimplePhase$IStartPhaseEvent)(((xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStartPhaseEvent)(this::startMH5PhaseEvent))));
		this.bulletsStash = (NonNullList) (NonNullList.withSize(5,ItemStack.EMPTY));
		this.netsStash = (NonNullList) (NonNullList.withSize(5,ItemStack.EMPTY));
	}
	public void initBoss() {
		this.entityStats.setFaction(((Faction)(ModFactions.PIRATE.get())));
		this.entityStats.setRace(((Race)(ModRaces.HUMAN.get())));
		this.entityStats.setFightingStyle(((FightingStyle)(ModFightingStyles.BRAWLER.get())));
		FactionsWorldData.get().addTemporaryCrewMember(ModNPCGroups.KRIEG_PIRATES,(LivingEntity)(this));
		this.setItemSlot(EquipmentSlot.CHEST,new ItemStack(((ItemLike)(ModArmors.WOOTZ_STEEL_ARMOR.get()))));
		this.getAttribute(Attributes.ENTITY_INTERACTION_RANGE).setBaseValue(1.1D);
		this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(2D);
		this.getAttribute(NeoForgeMod.SWIM_SPEED).setBaseValue(1.5D);
		SprintTowardsTargetGoal var1 = new SprintTowardsTargetGoal((Mob)(this));
		ActiveGuardAbilityWrapperGoal var2 = new ActiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(BakudanAbility.INSTANCE.get())));
		BakudanAbility var3 = ((BakudanAbility)(var2.getAbility()));
		DonKriegEntity var4 = this;
		Consumer<CooldownComponent> var5 = (l0) -> {
			l0.startCooldown((LivingEntity)(this),100F);
		};
		((java.util.Optional)(var3.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var5));
		ActiveBuffAbilityWrapperGoal var6 = new ActiveBuffAbilityWrapperGoal((Mob)(this),((AbilityCore)(MH5Ability.INSTANCE.get())));
		MH5Ability var7 = ((MH5Ability)(var6.getAbility()));
		DonKriegEntity var8 = this;
		ActiveBuffAbilityWrapperGoal var9 = var6;
		final ActiveBuffAbilityWrapperGoal var9_f1 = var9;
		Consumer<ChargeComponent> var10 = (l0) -> {
			DonKriegEntity lv3_3 = this;
			ActiveBuffAbilityWrapperGoal lv3_4 = var9_f1;
			final ActiveBuffAbilityWrapperGoal lv3_4_f1 = lv3_4;
			ChargeComponent$IStartChargingEvent lv3_5 = (l2_0, l2_1) -> {
				((MH5Ability)(lv3_4_f1.getAbility())).setTargetPosition(this.blockPosition());
			};
			l0.addStartEvent(90,lv3_5);
		};
		((java.util.Optional)(var7.getComponent(((AbilityComponentKey)(ModAbilityComponents.CHARGE.get()))))).ifPresent((Consumer)(var10));
		ImprovedMeleeAttackGoal var11 = new ImprovedMeleeAttackGoal((PathfinderMob)(this),1D,true);
		ActiveBuffAbilityWrapperGoal var12 = var6;
		final ActiveBuffAbilityWrapperGoal var12_f2 = var12;
		Supplier<Boolean> var13 = () -> {
			return Boolean.valueOf(((MH5Ability)(var12_f2.getAbility())).isCharging());
		};
		var11.setEarlyStop(var13);
		ActiveGuardAbilityWrapperGoal var14 = new ActiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(GunArrayAbility.INSTANCE.get())));
		ActiveGuardAbilityWrapperGoal var15 = new ActiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(WootzNetLauncherAbility.INSTANCE.get())));
		MobsHelper.addBasicNPCGoals(this);
		this.goalSelector.addGoal(0,(Goal)(new DonKriegPhaseSwitcherGoal(this)));
		this.goalSelector.addGoal(0,(Goal)(new MH5PhaseGoal(this)));
		this.fistPhase.addGoal(1,(Goal)(var11));
		this.fistPhase.addGoal(1,var1);
		this.fistPhase.addGoal(3,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(ChargedPunchAbility.INSTANCE.get()))));
		this.fistPhase.addGoal(3,var2);
		this.daisensoPhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),350F,2.5F));
		this.daisensoPhase.addGoal(0,new DashDodgeTargetGoal((Mob)(this),350F,2.5F));
		this.daisensoPhase.addGoal(1,(Goal)(var11));
		this.daisensoPhase.addGoal(1,var1);
		this.daisensoPhase.addGoal(1,var15);
		this.daisensoPhase.addGoal(2,var14);
		this.daisensoPhase.addGoal(3,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(ChargedCleaveAbility.INSTANCE.get()))));
		this.daisensoPhase.addGoal(3,var2);
		this.mh5Phase.addGoal(1,var6);
		if (this.getChallengeInfo().isDifficultyStandard()){
			this.entityStats.setDoriki(2000D);
			this.getAttribute(ModAttributes.GCD).setBaseValue(40D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(4D);
			this.fillBulletStash(((Item)(ModItems.BULLET.get())));
			this.fillNetStash(((Item)(ModItems.ROPE_NET.get())));
			BakudanAbility var16 = ((BakudanAbility)(var2.getAbility()));
			Consumer<CooldownComponent> var17 = (l0) -> {
			l0.getBonusManager().addBonus(STANDARD_BAKUDAN_COOLDOWN_BONUS_UUID,"Standard Bakudan Cooldown Bonus",BonusOperation.MUL,2F);
		};
			((java.util.Optional)(var16.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var17));
			this.getPhaseManager().setPhase(this.fistPhase);
		}else{
			this.entityStats.setDoriki(10000D);
			this.hakiCapability.setBusoshokuHakiExp(100F);
			this.hakiCapability.setKenbunshokuHakiExp(100F);
			this.getAttribute(ModAttributes.GCD).setBaseValue(20D);
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(3D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(4D);
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(400D);
			this.getAttribute(Attributes.ARMOR).setBaseValue(20D);
			this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(8D);
			this.fillBulletStash(((Item)(ModItems.KAIROSEKI_BULLET.get())));
			this.fillNetStash(((Item)(ModItems.KAIROSEKI_NET.get())));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
			this.fistPhase.addGoal(0,new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiHardeningAbility.INSTANCE.get()))));
			this.fistPhase.addGoal(3,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(HakaiHoAbility.INSTANCE.get()))));
			this.fistPhase.addGoal(3,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(JishinHoAbility.INSTANCE.get()))));
			this.daisensoPhase.addGoal(0,new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiImbuingAbility.INSTANCE.get()))));
			this.daisensoPhase.addGoal(3,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(JishinHoAbility.INSTANCE.get()))));
			this.daisensoPhase.addGoal(3,new JumpAbilityWrapperGoal((Mob)(this),((AbilityCore)(SlamAbility.INSTANCE.get()))));
			this.mh5Phase.addGoal(0,new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get()))));
			this.mh5Phase.addGoal(0,new ReactiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(KamieAbility.INSTANCE.get()))));
			this.getPhaseManager().setPhase(this.daisensoPhase);
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.30000001192092896D).add(Attributes.ATTACK_DAMAGE,6D).add(Attributes.MAX_HEALTH,200D).add(Attributes.ARMOR,10D).add(Attributes.KNOCKBACK_RESISTANCE,0.9D).add(Attributes.ATTACK_KNOCKBACK,1D);
	}
	private void fillBulletStash(Item var1) {
		int var2 = 0;
		do{
			if ((var2) < (this.bulletsStash.size())){
				ItemStack var3 = var1.getDefaultInstance();
				var3.setCount(1);
				this.bulletsStash.set(var2,var3);
				var2++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	private void fillNetStash(Item var1) {
		int var2 = 0;
		do{
			if ((var2) < (this.netsStash.size())){
				ItemStack var3 = var1.getDefaultInstance();
				var3.setCount(64);
				this.netsStash.set(var2,var3);
				var2++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public void tick() {
		super.tick();
	}
	public Iterable<ItemStack> getAllSlots() {
		return Iterables.concat(super.getAllSlots(),(Iterable)(this.bulletsStash),(Iterable)(this.netsStash));
	}
	public void startDaisensoPhase() {
		this.getPhaseManager().setPhase(this.daisensoPhase);
		MH5Ability var1 = ((MH5Ability)(this.abilityData.getEquippedAbility(((AbilityCore)(MH5Ability.INSTANCE.get())))));
		if ((var1) != (null)){
			int var2 = (this.isDifficultyHardOrAbove()) ? (800) : (400);
			DonKriegEntity var3 = this;
			int var4 = var2;
			final int var4_f1 = var4;
			Consumer<CooldownComponent> var5 = (l0) -> {
			l0.startCooldown((LivingEntity)(this),(float)(var4_f1));
		};
			((java.util.Optional)(var1.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var5));
		}
	}
	public void startMH5Phase() {
		this.getPhaseManager().setPhase(this.mh5Phase);
	}
	public boolean hasFistPhaseActive() {
		return this.fistPhase.isActive(this);
	}
	public boolean hasDaisensoPhaseActive() {
		return this.daisensoPhase.isActive(this);
	}
	public boolean hasMH5PhaseActive() {
		return this.mh5Phase.isActive(this);
	}
	private void startDaisensoPhaseEvent(DonKriegEntity var1) {
		this.setItemSlot(EquipmentSlot.MAINHAND,new ItemStack(((ItemLike)(ModWeapons.DAISENSO.get()))));
		if (this.isDifficultyHardOrAbove()){
			AttributeInstance var2 = var1.getAttribute(ModAttributes.GCD);
			if (((var2) != (null)) && (!(var2.hasModifier(GCD_ID)))){
				var2.addTransientModifier(GCD_MOD);
			}else{
				return;
			}
		}
	}
	private void startMH5PhaseEvent(DonKriegEntity var1) {
		this.setItemSlot(EquipmentSlot.HEAD,((Item)(ModArmors.MH5_GAS_MASK.get())).getDefaultInstance());
		this.setItemSlot(EquipmentSlot.MAINHAND,ItemStack.EMPTY);
	}
}