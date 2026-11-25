package xyz.pixelatedw.mineminenomi.entities.mobs;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.LevelData;
import xyz.pixelatedw.mineminenomi.abilities.sniper.KaenBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.KemuriBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.SakuretsuSabotenBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.TetsuBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.TokuyoAburaBoshiAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.IMobDetails;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ClimbOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedRangedAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.StyleSwitchGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.StyleSwitchGoal$IStyleSwitchEvent;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.projectiles.KairosekiBulletProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.NormalBulletProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class SniperEntity extends OPEntity {
	// Fields
	private static final List<IMobDetails<SniperEntity>> STYLES;
	private static final int SWITCH_RANGE = 5;
	private final NPCPhaseManager phaseManager;
	private NPCPhase<SniperEntity> meleePhase;
	private NPCPhase<SniperEntity> rangePhase;

	public SniperEntity(EntityType<? extends SniperEntity> var1, Level var2) {
		super((EntityType)(var1),var2,null);
		this.phaseManager = new NPCPhaseManager((Mob)(this));
		if (((var2) != (null)) && (!(var2.isClientSide))){
			this.meleePhase = new SimplePhase("Melee Phase",this);
			this.rangePhase = new SimplePhase("Range Phase",this);
			this.getEntityStats().setDoriki((3000D) + (this.getRandomDorikiBonus(0,1500)));
			this.getEntityStats().setBelly((15L) + ((long)(WyHelper.randomWithRange(0,30))));
			SniperEntity var3 = this;
			Consumer<HakiData> var4 = (l0) -> {
			l0.setBusoshokuHakiExp((25F) + (this.getRandomHakiBonus(0,10)));
			l0.setKenbunshokuHakiExp((35F) + (this.getRandomHakiBonus(0,10)));
		};
			((java.util.Optional)(HakiData.get((LivingEntity)(this)))).ifPresent((Consumer)(var4));
			this.getAttribute(ModAttributes.GCD).setBaseValue(60D);
			if ((this.level().getDifficulty().getId()) <= (2)){
				switch (SniperEntity$1.$SwitchMap$net$minecraft$world$Difficulty[this.level().getDifficulty().ordinal()]){
				case 1:

				case 2:

				default:
					float var5 = 1F;
					AttributeInstance var6 = this.getAttribute(Attributes.FOLLOW_RANGE);
					var6.setBaseValue((var6.getBaseValue()) / ((double)(var5)));
				}
			}
			MobsHelper.addBasicNPCGoals(this);
			SniperEntity var5_1 = this;
			Consumer var6_1 = (Consumer) ((Consumer<AbilityCore>) ((l0) -> {
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),l0)));
		}));
			MobsHelper.getBasicHakiAbilities((Mob)(this),30).forEach(var6_1);
			this.goalSelector.addGoal(0,(Goal)(new ClimbOutOfHoleGoal((Mob)(this))));
			this.meleePhase.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1D,true)));
			this.phaseManager.setPhase(this.rangePhase);
		}
	}
	public static SniperEntity createMarineSniper(EntityType<? extends SniperEntity> var0, Level var1) {
		SniperEntity var2 = new SniperEntity(var0,var1);
		var2.setFactionInit((IMobDetails)(((xyz.pixelatedw.mineminenomi.api.entities.IMobDetails)(SniperEntity::initMarine))));
		return var2;
	}
	public static SniperEntity createPirateSniper(EntityType<? extends SniperEntity> var0, Level var1) {
		SniperEntity var2 = new SniperEntity(var0,var1);
		var2.setFactionInit((IMobDetails)(((xyz.pixelatedw.mineminenomi.api.entities.IMobDetails)(SniperEntity::initPirate))));
		return var2;
	}
	public static SniperEntity createBanditSniper(EntityType<? extends SniperEntity> var0, Level var1) {
		SniperEntity var2 = new SniperEntity(var0,var1);
		var2.setFactionInit((IMobDetails)(((xyz.pixelatedw.mineminenomi.api.entities.IMobDetails)(SniperEntity::initBandit))));
		return var2;
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,50D).add(Attributes.MOVEMENT_SPEED,0.25D).add(Attributes.ATTACK_DAMAGE,(double)(WyHelper.randomWithRange(2,4))).add(Attributes.MAX_HEALTH,(double)(WyHelper.randomWithRange(20,40))).add(Attributes.ARMOR,(double)(WyHelper.randomWithRange(1,3)));
	}
	private FightingStyle onStyleSwitched(Mob var1, LivingEntity var2, FightingStyle var3) {
		boolean var4 = false;
		if (((java.lang.Object)(var3)).equals(ModFightingStyles.SNIPER.get())){
			var4 = GoalHelper.isWithinDistance((LivingEntity)(var1),var2,5D);
			if (var4){
				this.phaseManager.setPhase(this.meleePhase);
				return ((FightingStyle)(ModFightingStyles.BRAWLER.get()));
			}
		}else{
			var4 = GoalHelper.isOutsideDistance((LivingEntity)(var1),var2,5D);
			if (var4){
				this.phaseManager.setPhase(this.rangePhase);
				return ((FightingStyle)(ModFightingStyles.SNIPER.get()));
			}
		}
		return var3;
	}
	public void customServerAiStep() {
		this.phaseManager.tick();
	}
	protected boolean shouldDespawnInPeaceful() {
		return true;
	}
	@Nullable()
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable() SpawnGroupData var4) {
		var4 = super.finalizeSpawn(var1,var2,var3,var4);
		((IMobDetails)(STYLES.get(this.getRandom().nextInt(STYLES.size())))).init(this);
		initHuman((OPEntity)(this));
		this.initFaction();
		this.chooseTexture();
		return var4;
	}
	public static boolean checkSpawnRules(EntityType<? extends OPEntity> var0, ServerLevelAccessor var1, MobSpawnType var2, BlockPos var3, RandomSource var4) {
		if (((var2) != (MobSpawnType.SPAWNER)) && (var3.closerThan((Vec3i)(var1.getLevelData().getSpawnPos()),256D))){
			return false;
		}else{
			if (((var4.nextDouble()) * (100D)) > (ServerConfig.getSniperSpawnChance())){
				return false;
			}else{
				return OPEntity.checkSpawnRules(var0,var1,var2,var3,var4);
			}
		}
	}
	private static void initHuman(OPEntity var0) {
		var0.getEntityStats().setRace(((Race)(ModRaces.HUMAN.get())));
	}
	private static void initFishman(OPEntity var0) {
		var0.getEntityStats().setRace(((Race)(ModRaces.FISHMAN.get())));
		if (var0.getEntityStats().isPirate()){
			var0.setTextures(MobsHelper.PIRATE_FISHMEN_TEXTURES);
		}
	}
	private static void initSniper(SniperEntity var0) {
		var0.getEntityStats().setFightingStyle(((FightingStyle)(ModFightingStyles.SNIPER.get())));
		var0.setItemSlot(EquipmentSlot.MAINHAND,new ItemStack(((ItemLike)(ModWeapons.SENRIKU.get()))));
		Objects.requireNonNull(var0);
		var0.goalSelector.addGoal(0,(Goal)(new StyleSwitchGoal((Mob)(var0),(StyleSwitchGoal$IStyleSwitchEvent)(((xyz.pixelatedw.mineminenomi.entities.ai.goals.StyleSwitchGoal$IStyleSwitchEvent)(var0::onStyleSwitched))))));
		var0.rangePhase.addGoal(1,new ImprovedRangedAttackGoal((RangedAttackMob)(var0),1D,60,60F));
		WeightedList var1 = new WeightedList(new Object[0]);
		SniperEntity var2 = var0;
		final SniperEntity var2_f1 = var2;
		Supplier<Goal> var3 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var2_f1),((AbilityCore)(KaenBoshiAbility.INSTANCE.get())));
		};
		var1.addEntry(var3,6);
		SniperEntity var4 = var0;
		final SniperEntity var4_f2 = var4;
		Supplier<Goal> var5 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var4_f2),((AbilityCore)(TokuyoAburaBoshiAbility.INSTANCE.get())));
		};
		var1.addEntry(var5,4);
		SniperEntity var6 = var0;
		final SniperEntity var6_f3 = var6;
		Supplier<Goal> var7 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var6_f3),((AbilityCore)(KemuriBoshiAbility.INSTANCE.get())));
		};
		var1.addEntry(var7,4);
		SniperEntity var8 = var0;
		final SniperEntity var8_f4 = var8;
		Supplier<Goal> var9 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var8_f4),((AbilityCore)(TetsuBoshiAbility.INSTANCE.get())));
		};
		var1.addEntry(var9,3);
		SniperEntity var10 = var0;
		final SniperEntity var10_f5 = var10;
		Supplier<Goal> var11 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var10_f5),((AbilityCore)(SakuretsuSabotenBoshiAbility.INSTANCE.get())));
		};
		var1.addEntry(var11,2);
		SniperEntity var12 = var0;
		final SniperEntity var12_f6 = var12;
		Consumer var13 = (Consumer) ((Consumer<Supplier>) ((l0) -> {
			var12_f6.rangePhase.addGoal(2,((Goal)(l0.get())));
		}));
		var1.pickN(var0.getRandom(),(var0.isAboveNormalDifficulty()) ? (3) : (2)).forEach(var13);
	}
	public static void initMarine(OPEntity var0) {
		var0.getEntityStats().setFaction(((Faction)(ModFactions.MARINE.get())));
		var0.setTextures(MobsHelper.MARINE_TEXTURES);
	}
	private static void initPirate(OPEntity var0) {
		var0.getEntityStats().setFaction(((Faction)(ModFactions.PIRATE.get())));
		var0.setTextures(MobsHelper.PIRATE_TEXTURES);
		if ((var0.level().getRandom().nextInt(10)) < (3)){
			initFishman(var0);
		}
		FactionsWorldData.get().addTemporaryCrewMember(ModNPCGroups.WORLD_PIRATES,(LivingEntity)(var0));
	}
	private static void initBandit(OPEntity var0) {
		var0.getEntityStats().setFaction(((Faction)(ModFactions.BANDIT.get())));
		var0.setTextures(MobsHelper.BANDIT_TEXTURES);
	}
	public boolean canPerformRangedAttack(LivingEntity var1) {
		if (ItemsHelper.isBowOrGun(this.getMainHandItem())){
			return true;
		}else{
			return false;
		}
	}
	@Nullable()
	public Projectile getRangedProjectile(LivingEntity var1) {
		NuProjectileEntity var2 = new NormalBulletProjectile((Level)(this.level()),(LivingEntity)(this));
		if (this.isAboveNormalDifficulty()){
			var2 = new KairosekiBulletProjectile((Level)(this.level()),(LivingEntity)(this));
		}
		return var2;
	}
	static  {
		IMobDetails[] var0 = new IMobDetails[1];
		IMobDetails var1 = SniperEntity::initSniper;
		var0[0] = var1;
		STYLES = Lists.newArrayList(var0);
	}
}