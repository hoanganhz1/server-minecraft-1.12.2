package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.CandleChampionAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.CandleLockAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.CandleWallAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.DoruDoruArtsKenAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.DoruDoruArtsMoriAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.DoruDoruBallAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.DoruDoruNoYakataAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.TokudaiCandleAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.IPhasesEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStartPhaseEvent;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStopPhaseEvent;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ClimbOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.RunAwayFromTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.TauntTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveBuffAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AntiProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.UseAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.mr3.Mr3PhaseSwitcherGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class Mr3Entity extends OPBossEntity {
	// Fields
	private static final UUID TOKUDAI_YAKATA_COOLDOWN_BONUS = UUID.fromString("26687dbb-506c-4531-996c-ee35db1a2bb3");
	private static final UUID DORU_ARTS_MORI_COOLDOWN_BONUS = UUID.fromString("9b7ff21e-639a-4f1a-9919-6748fc9cf8b2");
	private static final UUID CHAMPION_FAUX_PROT_BONUS = UUID.fromString("6f0e2075-2051-4a3d-8854-0ea55e29b4e9");
	private static final ResourceLocation CHAMPION_TOUGHNESS_BONUS_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","npc.mr3.champion_toughness");
	private boolean hasColorPaletteBonus;
	private NPCPhase<Mr3Entity> standardPhase;
	private NPCPhase<Mr3Entity> championPhase;
	private NPCPhase<Mr3Entity> tokudaiPhase;
	private NPCPhase<Mr3Entity> tauntPhase;
	private UseAbilityWrapperGoal<DoruDoruNoYakataAbility> doruYakataWrapper;
	private UseAbilityWrapperGoal<TokudaiCandleAbility> tokudaiCandleWrapper;
	private ActiveBuffAbilityWrapperGoal<CandleChampionAbility> candleChampionWrapper;
	private ProjectileAbilityWrapperGoal<DoruDoruArtsMoriAbility> doruDoruArtsMoriWrapper;

	public Mr3Entity(EntityType<Mr3Entity> var1, Level var2) {
		super(var1,var2);
	}
	public Mr3Entity(InProgressChallenge var1) {
		super(((EntityType)(ModMobs.MR3.get())),var1);
	}
	public void initBoss() {
		FactionsWorldData var1 = FactionsWorldData.get();
		this.entityStats.setFaction(((Faction)(ModFactions.PIRATE.get())));
		this.entityStats.setRace(((Race)(ModRaces.HUMAN.get())));
		this.devilFruitData.setDevilFruit(((AkumaNoMiItem)(ModFruits.DORU_DORU_NO_MI.get())));
		var1.addTemporaryCrewMember(ModNPCGroups.BAROQUE_WORKS,(LivingEntity)(this));
		this.entityStats.setDoriki(2000D);
		this.getAttribute(ModAttributes.GCD).setBaseValue(40D);
		this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(5D);
		this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(1D);
		this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(1D);
		if (this.isDifficultyHardOrAbove()){
			this.entityStats.setDoriki(10000D);
			this.hakiCapability.setBusoshokuHakiExp(100F);
			this.hakiCapability.setKenbunshokuHakiExp(100F);
			this.getAttribute(ModAttributes.GCD).setBaseValue(20D);
			this.getAttribute(ModAttributes.PUNCH_DAMAGE).setBaseValue(5D);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5D);
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(6D);
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(400D);
			this.getAttribute(Attributes.ARMOR).setBaseValue(20D);
			this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(8D);
		}
		this.standardPhase = new SimplePhase("Standard Phase",this);
		Mr3Entity var2 = this;
		SimplePhase$IStartPhaseEvent var3 = var2::onStartChampionPhaseEvent;
		this.championPhase = new SimplePhase("Champion Phase",this,var3,(SimplePhase$IStopPhaseEvent)(((xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStopPhaseEvent)(this::onStopChampionPhaseEvent))));
		Mr3Entity var4 = this;
		SimplePhase$IStartPhaseEvent var5 = var4::onStartTokudaiPhaseEvent;
		this.tokudaiPhase = new SimplePhase("Tokudai Phase",this,var5,(SimplePhase$IStopPhaseEvent)(((xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStopPhaseEvent)(this::onStopTokudaiPhaseEvent))));
		Mr3Entity var6 = this;
		SimplePhase$IStartPhaseEvent var7 = var6::onStartTauntPhaseEvent;
		this.tauntPhase = new SimplePhase("Taunt Phase",this,var7,(SimplePhase$IStopPhaseEvent)(((xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStopPhaseEvent)(this::onStopTauntPhaseEvent))));
		this.doruYakataWrapper = (UseAbilityWrapperGoal) (new UseAbilityWrapperGoal((Mob)(this),((AbilityCore)(DoruDoruNoYakataAbility.INSTANCE.get()))));
		this.tokudaiCandleWrapper = (UseAbilityWrapperGoal) (new UseAbilityWrapperGoal((Mob)(this),((AbilityCore)(TokudaiCandleAbility.INSTANCE.get()))));
		((TokudaiCandleAbility)(this.tokudaiCandleWrapper.getAbility())).setBlocksPerTick(100);
		this.candleChampionWrapper = (ActiveBuffAbilityWrapperGoal) (new ActiveBuffAbilityWrapperGoal((Mob)(this),((AbilityCore)(CandleChampionAbility.INSTANCE.get()))));
		this.doruDoruArtsMoriWrapper = (ProjectileAbilityWrapperGoal) (new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(DoruDoruArtsMoriAbility.INSTANCE.get()))));
		ReactiveGuardAbilityWrapperGoal var8 = new ReactiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(DoruDoruBallAbility.INSTANCE.get())));
		ProjectileAbilityWrapperGoal var9 = new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(CandleLockAbility.INSTANCE.get())));
		CandleLockAbility var10 = ((CandleLockAbility)(var9.getAbility()));
		Mr3Entity var11 = this;
		Consumer<CooldownComponent> var12 = (l0) -> {
			l0.startCooldown((LivingEntity)(this),60F);
		};
		((java.util.Optional)(var10.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var12));
		MobsHelper.addBasicNPCGoals(this);
		this.goalSelector.addGoal(0,(Goal)(new Mr3PhaseSwitcherGoal(this)));
		this.goalSelector.addGoal(0,(Goal)(new ClimbOutOfHoleGoal((Mob)(this))));
		this.standardPhase.addGoal(1,new DashDodgeTargetGoal((Mob)(this),100F,3F));
		this.standardPhase.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1D,true)));
		this.standardPhase.addGoal(2,new AntiProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(CandleWallAbility.INSTANCE.get()))));
		this.standardPhase.addGoal(2,var8);
		this.standardPhase.addGoal(3,new ActiveBuffAbilityWrapperGoal((Mob)(this),((AbilityCore)(DoruDoruArtsKenAbility.INSTANCE.get()))));
		this.standardPhase.addGoal(3,this.doruDoruArtsMoriWrapper);
		this.standardPhase.addGoal(3,var9);
		this.standardPhase.addGoal(3,this.doruYakataWrapper);
		this.championPhase.addGoal(0,new SprintTowardsTargetGoal((Mob)(this)));
		this.championPhase.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1.100000023841858D,true)));
		this.championPhase.addGoal(3,this.candleChampionWrapper);
		this.championPhase.addGoal(4,new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(CandleLockAbility.INSTANCE.get()))));
		this.championPhase.addGoal(4,new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(DoruDoruArtsMoriAbility.INSTANCE.get()))));
		this.tokudaiPhase.addGoal(0,new RunAwayFromTargetGoal(this,1.7D,500,250));
		this.tokudaiPhase.addGoal(3,this.doruYakataWrapper);
		this.tokudaiPhase.addGoal(4,this.tokudaiCandleWrapper);
		this.getPhaseManager().setPhase(this.standardPhase);
		if (this.isDifficultyStandard()){
			this.standardPhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),200F,3F));
			this.tokudaiPhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),100F,3F));
		}else{
			var8.setActivationHits(3);
			((TokudaiCandleAbility)(this.tokudaiCandleWrapper.getAbility())).setBlocksPerTick(200);
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get())))));
			this.standardPhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),100F,3F));
			this.standardPhase.addGoal(3,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(ShiganAbility.INSTANCE.get()))));
			this.tauntPhase.addGoal(0,new TauntTargetGoal((Mob)(this)));
			this.tauntPhase.addGoal(1,(Goal)(new ImprovedMeleeAttackGoal((PathfinderMob)(this),1D,true).setMoveTowardsTarget(false)));
			this.tauntPhase.addGoal(1,new DashDodgeTargetGoal((Mob)(this),60F,4.5F));
			this.tauntPhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),20F,4.5F));
			this.tauntPhase.addGoal(3,new ReactiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(KamieAbility.INSTANCE.get()))));
			this.tauntPhase.addGoal(3,this.doruDoruArtsMoriWrapper);
			this.championPhase.addGoal(3,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(HakaiHoAbility.INSTANCE.get()))));
			this.tokudaiPhase.addGoal(0,new DashDodgeProjectilesGoal((Mob)(this),80F,4F));
			this.tokudaiPhase.addGoal(3,new ReactiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(KamieAbility.INSTANCE.get()))));
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.30000001192092896D).add(Attributes.ATTACK_DAMAGE,2D).add(Attributes.MAX_HEALTH,300D).add(Attributes.ARMOR,2D);
	}
	public void tick() {
		super.tick();
		if ((!(this.level().isClientSide)) && (((this.getTarget()) != (null)) && ((this.getTarget().isAlive()) && (this.isDifficultyHardOrAbove())))){
			if ((this.standardPhase.isActive(this)) && ((this.getTarget().hasEffect((Holder)(ModEffects.CANDLE_LOCK))) && (GoalHelper.hasHealthAbovePercentage((LivingEntity)(this),70D)))){
				this.getPhaseManager().setPhase(this.tauntPhase);
			}else{
				if ((this.tauntPhase.isActive(this)) && (!(this.getTarget().hasEffect((Holder)(ModEffects.CANDLE_LOCK))))){
					this.getPhaseManager().setPhase(this.standardPhase);
				}else{
					return;
				}
			}
		}
	}
	public boolean hasColorPaletteBonus() {
		return this.hasColorPaletteBonus;
	}
	public boolean isStandardPhase() {
		return this.standardPhase.isActive(this);
	}
	public boolean isChampionPhase() {
		return this.championPhase.isActive(this);
	}
	public boolean isTokudaiPhase() {
		return this.tokudaiPhase.isActive(this);
	}
	public void startStandardPhase() {
		this.getPhaseManager().setPhase(this.standardPhase);
	}
	public void startChampionPhase() {
		this.getPhaseManager().setPhase(this.championPhase);
	}
	public void startTokudaiPhase() {
		this.getPhaseManager().setPhase(this.tokudaiPhase);
	}
	public void onStartChampionPhaseEvent(Mr3Entity var1) {
		this.getAttribute(ModAttributes.TOUGHNESS).addTransientModifier(new AttributeModifier(CHAMPION_TOUGHNESS_BONUS_ID,4D,AttributeModifier.Operation.ADD_VALUE));
		DoruDoruArtsMoriAbility var2 = ((DoruDoruArtsMoriAbility)(this.doruDoruArtsMoriWrapper.getAbility()));
		Mr3Entity var3 = var1;
		final Mr3Entity var3_f1 = var3;
		Consumer<CooldownComponent> var4 = (l0) -> {
			l0.stopCooldown((LivingEntity)(var3_f1));
		};
		((java.util.Optional)(var2.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var4));
	}
	public void onStopChampionPhaseEvent(Mr3Entity var1) {
		this.getAttribute(ModAttributes.TOUGHNESS).removeModifier(CHAMPION_TOUGHNESS_BONUS_ID);
	}
	public void onStartTokudaiPhaseEvent(Mr3Entity var1) {
		DoruDoruNoYakataAbility var2 = ((DoruDoruNoYakataAbility)(this.doruYakataWrapper.getAbility()));
		Mr3Entity var3 = var1;
		final Mr3Entity var3_f1 = var3;
		Consumer<CooldownComponent> var4 = (l0) -> {
			l0.stopCooldown((LivingEntity)(var3_f1));
		};
		((java.util.Optional)(var2.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var4));
		DoruDoruNoYakataAbility var5 = ((DoruDoruNoYakataAbility)(this.doruYakataWrapper.getAbility()));
		Consumer<CooldownComponent> var6 = (l0) -> {
			l0.getBonusManager().addBonus(TOKUDAI_YAKATA_COOLDOWN_BONUS,"Tokudai Phase Doru Yakata Cooldown Bonus",BonusOperation.ADD,-400F);
		};
		((java.util.Optional)(var5.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var6));
	}
	public void onStopTokudaiPhaseEvent(Mr3Entity var1) {
		DoruDoruNoYakataAbility var2 = ((DoruDoruNoYakataAbility)(this.doruYakataWrapper.getAbility()));
		Consumer<CooldownComponent> var3 = (l0) -> {
			l0.getBonusManager().removeBonus(TOKUDAI_YAKATA_COOLDOWN_BONUS);
		};
		((java.util.Optional)(var2.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var3));
	}
	public void onStartTauntPhaseEvent(Mr3Entity var1) {
		DoruDoruArtsMoriAbility var2 = ((DoruDoruArtsMoriAbility)(this.doruDoruArtsMoriWrapper.getAbility()));
		Mr3Entity var3 = var1;
		final Mr3Entity var3_f1 = var3;
		Consumer<CooldownComponent> var4 = (l0) -> {
			l0.getBonusManager().addBonus(DORU_ARTS_MORI_COOLDOWN_BONUS,"Taunt Doru Arts Mori Cooldown Bonus",BonusOperation.MUL,0.5F);
			l0.stopCooldown((LivingEntity)(var3_f1));
		};
		((java.util.Optional)(var2.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var4));
	}
	public void onStopTauntPhaseEvent(Mr3Entity var1) {
		DoruDoruArtsMoriAbility var2 = ((DoruDoruArtsMoriAbility)(this.doruDoruArtsMoriWrapper.getAbility()));
		Consumer<CooldownComponent> var3 = (l0) -> {
			l0.getBonusManager().removeBonus(DORU_ARTS_MORI_COOLDOWN_BONUS);
		};
		((java.util.Optional)(var2.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var3));
	}
	public boolean isChampionOnCooldown() {
		return ((CooldownComponent)(((CandleChampionAbility)(this.candleChampionWrapper.getAbility())).getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))).get())).isOnCooldown();
	}
	public boolean isTokudaiOnCooldown() {
		return ((CooldownComponent)(((TokudaiCandleAbility)(this.tokudaiCandleWrapper.getAbility())).getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))).get())).isOnCooldown();
	}
}