package xyz.pixelatedw.mineminenomi.entities.mobs;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.BlackLegPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.BrawlerPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInfo;
import xyz.pixelatedw.mineminenomi.api.challenges.IChallengeBoss;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.ServerOPBossEvent;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.controllers.HumanoidSwimMoveControl;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class OPBossEntity extends PathfinderMob implements IChallengeBoss, IEntityWithComplexSpawn {
	// Fields
	private final ServerOPBossEvent bossInfo;
	private final ChallengeInfo challengeInfo;
	private final NPCPhaseManager phaseManager;
	protected final EntityStatsData entityStats;
	protected final AbilityData abilityData;
	protected final DevilFruitData devilFruitData;
	protected final HakiData hakiCapability;
	private boolean isInitialized;
	private float damageCeiling;
	private boolean hasEmptyTargetsList;
	private int movementType;
	private BlockPos lastSafePosition;
	private MoveControl groundMovementController;
	private MoveControl waterMovementController;

	public OPBossEntity(EntityType<? extends OPBossEntity> var1, Level var2) {
		this(var1,var2,null);
	}
	public OPBossEntity(EntityType<? extends OPBossEntity> var1, InProgressChallenge var2) {
		this(var1,(Level)(var2.getShard()),var2);
	}
	private OPBossEntity(EntityType<? extends OPBossEntity> var1, Level var2, @Nullable() InProgressChallenge var3) {
		super(var1,var2);
		this.bossInfo = new ServerOPBossEvent(this.getDisplayName());
		this.challengeInfo = new ChallengeInfo();
		this.phaseManager = new NPCPhaseManager((Mob)(this));
		this.movementType = 0;
		this.groundMovementController = new MoveControl((Mob)(this));
		this.waterMovementController = new HumanoidSwimMoveControl((Mob)(this));
		this.entityStats = EntityStatsData.getOrCreate((LivingEntity)(this));
		this.abilityData = AbilityData.getOrCreate((LivingEntity)(this));
		this.devilFruitData = DevilFruitData.getOrCreate((LivingEntity)(this));
		this.hakiCapability = HakiData.getOrCreate((LivingEntity)(this));
		this.preInit();
		this.challengeInfo.setInProgressChallenge(var3);
	}
	public void preInit() {
	}
	public void initBoss() {
	}
	private final void baseInitBoss() {
		this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(1D);
		this.setDamageCeiling((this.isDifficultyStandard()) ? (20F) : (30F));
		this.initBoss();
		this.applyDifficultyModifiers((LivingEntity)(this));
		if (this.entityStats.isBrawler()){
			this.goalSelector.addGoal(0,(Goal)(new AlwaysActiveAbilityWrapperGoal((Mob)(this),((AbilityCore)(BrawlerPassiveBonusesAbility.INSTANCE.get())))));
		}else{
			if (this.entityStats.isBlackLeg()){
				this.goalSelector.addGoal(0,(Goal)(new AlwaysActiveAbilityWrapperGoal((Mob)(this),((AbilityCore)(BlackLegPassiveBonusesAbility.INSTANCE.get())))));
			}
		}
		AttributeHelper.updateToughnessAttribute((LivingEntity)(this));
		this.heal(this.getMaxHealth());
	}
	public void tick() {
		if (!(this.isInitialized)){
			if (!(this.level().isClientSide)){
				this.baseInitBoss();
				this.lastSafePosition = this.blockPosition();
			}
			this.isInitialized = true;
		}
		this.bossInfo.tick((LivingEntity)(this));
		super.tick();
		if (!(this.level().isClientSide)){
			if (this.isAlive()){
				Iterator var1 = this.getRevengeMeters().iterator();
				do{
					if (var1.hasNext()){
						((RevengeMeter)(var1.next())).tick();
						continue;
					}else{
						break;
					}
				} while (true);
			}
			this.trySwitchingMovementMode();
			if (((this.level().getGameTime()) % (20L)) == (0L)){
				boolean var1_1 = this.level().getBlockState(this.blockPosition()).isSuffocating((BlockGetter)(this.level()),this.blockPosition());
				int var2 = (!(NuWorld.isWithinChallengeArenaBounds(this.level(),this.blockPosition()))) ? (1) : (0);
				if (!(var1_1)){
					this.lastSafePosition = this.blockPosition();
				}else{
					if ((!(var1_1)) && ((var2) == (0))){

					}else{
						this.moveTo(this.lastSafePosition,this.getYRot(),this.getXRot());
					}
				}
			}
			if (((this.getTarget()) == (null)) && (!(this.hasEmptyTargetsList))){
				List var1_2 = this.challengeInfo.getChallengerGroup();
				if (var1_2.isEmpty()){
					this.hasEmptyTargetsList = true;
					return;
				}else{
					Iterator var2_1 = var1_2.iterator();
					do{
						if (var2_1.hasNext()){
							LivingEntity var3 = ((LivingEntity)(var2_1.next()));
							if (this.canTarget(var3)){
								this.setTarget(var3);
								break;
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
				}
			}else{
				if (((this.getTarget()) != (null)) && (!(this.canTarget(this.getTarget())))){
					this.setTarget((LivingEntity)(null));
				}else{
					return;
				}
			}
		}
	}
	private boolean canTarget(LivingEntity var1) {
		if (var1.hasEffect((Holder)(ModEffects.CHALLENGE_FAILED))){
			return false;
		}else{
			return true;
		}
	}
	private void trySwitchingMovementMode() {
		if ((this.isInWater()) && ((this.movementType) == (0))){
			this.switchToWaterMovement();
		}else{
			if ((!(this.isInWater())) && ((this.movementType) == (1))){
				this.switchToGroundMovement();
			}else{
				return;
			}
		}
	}
	public void aiStep() {
		this.updateSwingTime();
		super.aiStep();
	}
	public void customServerAiStep() {
		this.phaseManager.tick();
	}
	public void startSeenByPlayer(ServerPlayer var1) {
		super.startSeenByPlayer(var1);
		if ((this.bossInfo) != (null)){
			this.bossInfo.addPlayer(var1);
		}
	}
	public void stopSeenByPlayer(ServerPlayer var1) {
		super.stopSeenByPlayer(var1);
		if ((this.bossInfo) != (null)){
			this.bossInfo.removePlayer(var1);
		}
	}
	public void remove(Entity.RemovalReason var1) {
		if (this.entityStats.isPirate()){
			FactionsWorldData var2 = FactionsWorldData.get();
			if ((var2) != (null)){
				Crew var3 = var2.getCrewWithMember(this.getUUID());
				if ((var3) != (null)){
					var3.removeMember(this.level(),this.getUUID());
				}
			}
		}
		super.remove(var1);
	}
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable() SpawnGroupData var4) {
		var4 = super.finalizeSpawn(var1,var2,var3,var4);
		this.setLeftHanded(false);
		return var4;
	}
	public boolean removeWhenFarAway(double var1) {
		return false;
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
	}
	public ChallengeInfo getChallengeInfo() {
		return this.challengeInfo;
	}
	public NPCPhaseManager getPhaseManager() {
		return this.phaseManager;
	}
	public void setDamageCeiling(float var1) {
		this.damageCeiling = var1;
	}
	public float getDamageCeiling() {
		return this.damageCeiling;
	}
	public float getDifficultyScaling() {
		return this.challengeInfo.getScaling();
	}
	public void switchToWaterMovement() {
		this.moveControl = this.waterMovementController;
		this.movementType = 1;
	}
	public void switchToGroundMovement() {
		this.moveControl = this.groundMovementController;
		this.movementType = 0;
	}
}