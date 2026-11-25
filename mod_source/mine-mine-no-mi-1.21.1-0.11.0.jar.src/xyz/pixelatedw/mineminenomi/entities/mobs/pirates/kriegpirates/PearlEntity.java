package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.kriegpirates;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
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
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.BellyFlopAbility;
import xyz.pixelatedw.mineminenomi.abilities.PearlFireAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.JishinHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.TekkaiAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInfo;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.IPhasesEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStartPhaseEvent;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStopPhaseEvent;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.IRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.PhysicalHitRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveBuffAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class PearlEntity extends OPBossEntity {
	// Fields
	private static final ResourceLocation ATTACK_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","npc.pearl.attack");
	private static final ResourceLocation PUNCH_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","npc.pearl.punch");
	private static final ResourceLocation ARMOR_TOUGHNESS_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","npc.pearl.armor_toughness");
	private static final ResourceLocation REVENGE_ATTACK_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","npc.pearl.revenge_attack");
	private static final ResourceLocation REVENGE_PUNCH_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","npc.pearl.revenge_punch");
	private static final AttributeModifier ATTACK_MODIFIER = new AttributeModifier(ATTACK_ID,2D,AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier PUNCH_MODIFIER = new AttributeModifier(PUNCH_ID,2D,AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier ARMOR_TOUGHNESS_MODIFIER = new AttributeModifier(ARMOR_TOUGHNESS_ID,2D,AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
	private RevengeMeter revengeMeter;
	private NPCPhase<PearlEntity> normalPhase;
	private NPCPhase<PearlEntity> flamingPhase;
	private int tekkaiAbuseStartCounter;
	private int tekkaiWaitCounter;
	private int prevRevengeVal;

	public PearlEntity(EntityType<PearlEntity> var1, Level var2) {
		super(var1,var2);
	}
	public PearlEntity(InProgressChallenge var1) {
		super(((EntityType)(ModMobs.PEARL.get())),var1);
	}
	public void initBoss() {
		this.revengeMeter = new RevengeMeter((LivingEntity)(this),1000,1);
		this.normalPhase = new SimplePhase("Normal Phase",this);
		PearlEntity var1 = this;
		SimplePhase$IStartPhaseEvent var2 = var1::startFlamingPhaseEvent;
		this.flamingPhase = new SimplePhase("Flaming Phase",this,var2,(SimplePhase$IStopPhaseEvent)(((xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStopPhaseEvent)(this::stopFlamingPhaseEvent))));
		this.entityStats.setFaction(((Faction)(ModFactions.PIRATE.get())));
		this.entityStats.setRace(((Race)(ModRaces.HUMAN.get())));
		this.entityStats.setFightingStyle(((FightingStyle)(ModFightingStyles.BRAWLER.get())));
		FactionsWorldData.get().addTemporaryCrewMember(ModNPCGroups.KRIEG_PIRATES,(LivingEntity)(this));
		this.setItemSlot(EquipmentSlot.HEAD,new ItemStack(((ItemLike)(ModArmors.PEARL_HAT.get()))));
		this.setItemSlot(EquipmentSlot.CHEST,new ItemStack(((ItemLike)(ModArmors.PEARL_ARMOR.get()))));
		this.setItemSlot(EquipmentSlot.LEGS,new ItemStack(((ItemLike)(ModArmors.PEARL_LEGS.get()))));
		this.getAttribute(Attributes.ENTITY_INTERACTION_RANGE).setBaseValue(1.1D);
		this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(2D);
		this.getAttribute(NeoForgeMod.SWIM_SPEED).setBaseValue(1.5D);
		this.revengeMeter.addCheck(new PhysicalHitRevengeCheck(5));
		CloseMeleeAbilityWrapperGoal var3 = new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(ChargedPunchAbility.INSTANCE.get())));
		ActiveGuardAbilityWrapperGoal var4 = new ActiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(TekkaiAbility.INSTANCE.get())));
		TekkaiAbility var5 = ((TekkaiAbility)(var4.getAbility()));
		PearlEntity var6 = this;
		Consumer<ContinuousComponent> var7 = (l0) -> {
			PearlEntity lv3_2 = this;
			ContinuousComponent$IStartContinuousEvent lv3_3 = (l2_0, l2_1) -> {
				this.tekkaiWaitCounter = 0;
				this.tekkaiAbuseStartCounter = this.revengeMeter.countCheckTriggers(PhysicalHitRevengeCheck.class);
			};
			l0.addStartEvent(110,lv3_3);
			PearlEntity lv3_4 = this;
			ContinuousComponent lv3_5 = l0;
			final ContinuousComponent lv3_5_f1 = lv3_5;
			ContinuousComponent$IDuringContinuousEvent lv3_6 = (l2_0, l2_1) -> {
				if (((this.tickCount) % (10)) == (0)){
					List lv2_4 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.level()),(LivingEntity)(this),7D,TargetPredicate.DEFAULT_AREA_CHECK,LivingEntity.class);
					if ((lv2_4.size()) > (0)){
						this.tekkaiWaitCounter = (this.tekkaiWaitCounter) + (lv2_4.size());
					}
					int lv2_5 = this.revengeMeter.countCheckTriggers(PhysicalHitRevengeCheck.class);
					if (((this.tekkaiWaitCounter) <= (6)) && ((lv2_5) < ((this.tekkaiAbuseStartCounter) + (5)))){
						return;
					}else{
						this.pushEnemiesAway((this.isDifficultyHard()) ? (7D) : (5D));
						this.resetCooldowns();
						this.revengeMeter.addRevengeValue(30);
						lv3_5_f1.stopContinuity((LivingEntity)(this));
					}
				}
			};
			l0.addTickEvent(110,lv3_6);
		};
		((java.util.Optional)(var5.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))))).ifPresent((Consumer)(var7));
		ActiveBuffAbilityWrapperGoal var8 = new ActiveBuffAbilityWrapperGoal((Mob)(this),((AbilityCore)(PearlFireAbility.INSTANCE.get())));
		PearlFireAbility var9 = ((PearlFireAbility)(var8.getAbility()));
		Consumer<ContinuousComponent> var10 = (l0) -> {
			ContinuousComponent lv5_1 = l0;
			final ContinuousComponent lv5_1_f1 = lv5_1;
			ContinuousComponent$IDuringContinuousEvent lv5_2 = (l2_0, l2_1) -> {
				lv5_1_f1.increaseContinuityTime(2F);
			};
			l0.addTickEvent(lv5_2);
		};
		((java.util.Optional)(var9.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))))).ifPresent((Consumer)(var10));
		MobsHelper.addBasicNPCGoals(this);
		this.goalSelector.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1D,true)));
		this.goalSelector.addGoal(2,(Goal)(var3));
		this.goalSelector.addGoal(2,(Goal)(var4));
		this.flamingPhase.addGoal(0,var8);
		this.getPhaseManager().setPhase(this.normalPhase);
		if (this.getChallengeInfo().isDifficultyStandard()){
			this.entityStats.setDoriki(2000D);
			this.getAttribute(ModAttributes.GCD).setBaseValue(40D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(4D);
		}else{
			this.entityStats.setDoriki(10000D);
			this.hakiCapability.setBusoshokuHakiExp(100F);
			this.hakiCapability.setKenbunshokuHakiExp(100F);
			this.getAttribute(ModAttributes.GCD).setBaseValue(20D);
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(3D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(4D);
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(250D);
			this.getAttribute(Attributes.ARMOR).setBaseValue(20D);
			this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(8D);
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(2,(Goal)(new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(HakaiHoAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(2,(Goal)(new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(JishinHoAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(2,(Goal)(new JumpAbilityWrapperGoal((Mob)(this),((AbilityCore)(BellyFlopAbility.INSTANCE.get())))));
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.2800000011920929D).add(Attributes.ATTACK_DAMAGE,4D).add(Attributes.MAX_HEALTH,200D).add(Attributes.ARMOR,10D).add(Attributes.ARMOR_TOUGHNESS,4D).add(Attributes.KNOCKBACK_RESISTANCE,1D).add(Attributes.ATTACK_KNOCKBACK,1D).add(Attributes.ATTACK_SPEED,3D);
	}
	public void tick() {
		super.tick();
		if ((!(this.level().isClientSide)) && ((this.isAlive()) && (((this.tickCount) % (10)) == (0)))){
			int var1 = this.revengeMeter.countCheckTriggers(PhysicalHitRevengeCheck.class);
			if ((this.normalPhase.isActive(this)) && (!((!(((double)(this.getHealth())) <= (WyHelper.percentage(30D,(double)(this.getMaxHealth()))))) && ((var1) < (20))))){
				this.getPhaseManager().setPhase(this.flamingPhase);
			}
			if ((this.flamingPhase.isActive(this)) && (!((!(this.getItemBySlot(EquipmentSlot.HEAD).isEmpty())) && ((!(this.getItemBySlot(EquipmentSlot.CHEST).isEmpty())) && (!(this.getItemBySlot(EquipmentSlot.LEGS).isEmpty())))))){
				this.getPhaseManager().setPhase(this.normalPhase);
			}
			if (this.isDifficultyHard()){
				int var2 = this.revengeMeter.getRevengeValue();
				if (((var2) > (10)) && ((var2) != (this.prevRevengeVal))){
					this.prevRevengeVal = var2;
					double var3 = ((double)(var2)) / (15D);
					this.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(REVENGE_ATTACK_ID);
					this.getAttribute(ModAttributes.PUNCH_DAMAGE).removeModifier(REVENGE_PUNCH_ID);
					this.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(new AttributeModifier(REVENGE_ATTACK_ID,var3,AttributeModifier.Operation.ADD_VALUE));
					this.getAttribute(ModAttributes.PUNCH_DAMAGE).addTransientModifier(new AttributeModifier(REVENGE_PUNCH_ID,var3,AttributeModifier.Operation.ADD_VALUE));
				}else{
					return;
				}
			}else{
				return;
			}
		}
	}
	private void startFlamingPhaseEvent(PearlEntity var1) {
		this.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(ATTACK_MODIFIER);
		this.getAttribute(ModAttributes.PUNCH_DAMAGE).addTransientModifier(PUNCH_MODIFIER);
		this.getAttribute(Attributes.ARMOR_TOUGHNESS).addTransientModifier(ARMOR_TOUGHNESS_MODIFIER);
		this.pushEnemiesAway(5D);
	}
	private void stopFlamingPhaseEvent(PearlEntity var1) {
		this.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(ATTACK_MODIFIER);
		this.getAttribute(ModAttributes.PUNCH_DAMAGE).removeModifier(PUNCH_MODIFIER);
		this.getAttribute(Attributes.ARMOR_TOUGHNESS).removeModifier(ARMOR_TOUGHNESS_MODIFIER);
	}
	private void pushEnemiesAway(double var1) {
		Iterator var2 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.level()),(LivingEntity)(this),15D,TargetPredicate.DEFAULT_AREA_CHECK,LivingEntity.class).iterator();
		do{
			if (var2.hasNext()){
				LivingEntity var3 = ((LivingEntity)(var2.next()));
				Vec3 var4 = var3.position().subtract(this.position()).normalize().multiply(var1,1D,var1);
				AbilityHelper.setDeltaMovement((Entity)(var3),var4.x,0D,var4.z);
				continue;
			}else{
				break;
			}
		} while (true);
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.BERSERKER_POWERUP.get())),(Entity)(this),this.getX(),this.getY(),this.getZ());
	}
	private void resetCooldowns() {
		Iterator var1 = ((AbilityData)(AbilityData.get((LivingEntity)(this)).get())).getEquippedAbilities().iterator();
		do{
			if (var1.hasNext()){
				IAbility var2 = ((IAbility)(var1.next()));
				if ((var2) == (null)){
					continue;
				}else{
					PearlEntity var3 = this;
					Consumer<CooldownComponent> var4 = (l0) -> {
			l0.stopCooldown((LivingEntity)(this));
		};
					((java.util.Optional)(var2.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var4));
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	public Set<RevengeMeter> getRevengeMeters() {
		RevengeMeter[] var1 = new RevengeMeter[]{this.revengeMeter};
		return Sets.newHashSet(var1);
	}
}