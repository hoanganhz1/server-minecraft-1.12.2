package xyz.pixelatedw.mineminenomi.entities.mobs;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
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
import net.minecraft.world.entity.Entity;
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
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.abilities.ChargedCleaveAbility;
import xyz.pixelatedw.mineminenomi.abilities.SlamAbility;
import xyz.pixelatedw.mineminenomi.abilities.WeaponSpinAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.BrawlerPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.GenkotsuMeteorAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SpinningBrawlAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SuplexAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.TackleAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.YakkodoriAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.IMobDetails;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ClimbOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.GrabAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.projectiles.CannonBallProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class BruteEntity extends OPEntity {
	// Fields
	private static final UUID TACKLE_COOLDOWN_BONUS_UUID = UUID.fromString("2839290d-9070-45fb-a7ac-465405562ec6");
	private static final List<IMobDetails<OPEntity>> STYLES;

	public BruteEntity(EntityType<? extends BruteEntity> var1, Level var2) {
		super((EntityType)(var1),var2,null);
		if (((var2) != (null)) && (!(var2.isClientSide))){
			this.getEntityStats().setDoriki((3500D) + (this.getRandomDorikiBonus(0,1500)));
			this.getEntityStats().setBelly((15L) + ((long)(WyHelper.randomWithRange(0,30))));
			BruteEntity var3 = this;
			Consumer<HakiData> var4 = (l0) -> {
			l0.setBusoshokuHakiExp((35F) + (this.getRandomHakiBonus(0,10)));
			l0.setKenbunshokuHakiExp((25F) + (this.getRandomHakiBonus(0,10)));
		};
			((java.util.Optional)(HakiData.get((LivingEntity)(this)))).ifPresent((Consumer)(var4));
			this.getAttribute(ModAttributes.GCD).setBaseValue(60D);
			if ((this.level().getDifficulty().getId()) <= (2)){
				switch (BruteEntity$1.$SwitchMap$net$minecraft$world$Difficulty[this.level().getDifficulty().ordinal()]){
				case 1:

				case 2:

				default:
					float var5 = 1F;
					AttributeInstance var6 = this.getAttribute(Attributes.FOLLOW_RANGE);
					var6.setBaseValue((var6.getBaseValue()) / ((double)(var5)));
				}
			}
			MobsHelper.addBasicNPCGoals(this);
			BruteEntity var5_1 = this;
			Consumer var6_1 = (Consumer) ((Consumer<AbilityCore>) ((l0) -> {
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),l0)));
		}));
			MobsHelper.getBasicHakiAbilities((Mob)(this),30).forEach(var6_1);
			this.goalSelector.addGoal(0,(Goal)(new ClimbOutOfHoleGoal((Mob)(this))));
			this.goalSelector.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1D,true)));
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(4D);
		}
	}
	public static BruteEntity createMarineBrute(EntityType<? extends BruteEntity> var0, Level var1) {
		BruteEntity var2 = new BruteEntity(var0,var1);
		var2.setFactionInit((IMobDetails)(((xyz.pixelatedw.mineminenomi.api.entities.IMobDetails)(BruteEntity::initMarine))));
		return var2;
	}
	public static BruteEntity createPirateBrute(EntityType<? extends BruteEntity> var0, Level var1) {
		BruteEntity var2 = new BruteEntity(var0,var1);
		var2.setFactionInit((IMobDetails)(((xyz.pixelatedw.mineminenomi.api.entities.IMobDetails)(BruteEntity::initPirate))));
		return var2;
	}
	public static BruteEntity createBanditBrute(EntityType<? extends BruteEntity> var0, Level var1) {
		BruteEntity var2 = new BruteEntity(var0,var1);
		var2.setFactionInit((IMobDetails)(((xyz.pixelatedw.mineminenomi.api.entities.IMobDetails)(BruteEntity::initBandit))));
		return var2;
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,40D).add(Attributes.MOVEMENT_SPEED,0.25D).add(Attributes.ATTACK_DAMAGE,(double)(WyHelper.randomWithRange(3,5))).add(Attributes.MAX_HEALTH,(double)(WyHelper.randomWithRange(50,80))).add(Attributes.ARMOR,(double)(WyHelper.randomWithRange(8,12)));
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
			if (((var4.nextDouble()) * (100D)) > (ServerConfig.getBruteSpawnChance())){
				return false;
			}else{
				return OPEntity.checkSpawnRules(var0,var1,var2,var3,var4);
			}
		}
	}
	private static void initSwordsman(OPEntity var0) {
		var0.getEntityStats().setFightingStyle(((FightingStyle)(ModFightingStyles.SWORDSMAN.get())));
		ItemStack var1 = var0.getRandomSword((List)(MobsHelper.BRUTE_SWORDS));
		if ((var0.getEntityStats().isMarine()) && (var1.has(ModDataComponents.MULTI_CHANNEL_DYES))){
			MultiChannelItemColor.setLayerColor(var1,0,33980);
		}
		var0.setItemSlot(EquipmentSlot.MAINHAND,var1);
		WeightedList var2 = new WeightedList(new Object[0]);
		OPEntity var3 = var0;
		final OPEntity var3_f1 = var3;
		Supplier<Goal> var4 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(var3_f1),((AbilityCore)(ChargedCleaveAbility.INSTANCE.get())));
		};
		var2.addEntry(var4,3);
		OPEntity var5 = var0;
		final OPEntity var5_f2 = var5;
		Supplier<Goal> var6 = () -> {
			return new JumpAbilityWrapperGoal((Mob)(var5_f2),((AbilityCore)(SlamAbility.INSTANCE.get())));
		};
		var2.addEntry(var6,3);
		OPEntity var7 = var0;
		final OPEntity var7_f3 = var7;
		Supplier<Goal> var8 = () -> {
			return new DashAbilityWrapperGoal((Mob)(var7_f3),((AbilityCore)(ShiShishiSonsonAbility.INSTANCE.get())));
		};
		var2.addEntry(var8,2);
		OPEntity var9 = var0;
		final OPEntity var9_f4 = var9;
		Supplier<Goal> var10 = () -> {
			ProjectileAbilityWrapperGoal lv6_1 = new ProjectileAbilityWrapperGoal((Mob)(var9_f4),((AbilityCore)(YakkodoriAbility.INSTANCE.get())));
			((YakkodoriAbility)(lv6_1.getAbility())).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
			return lv6_1;
		};
		var2.addEntry(var10,2);
		OPEntity var11 = var0;
		final OPEntity var11_f5 = var11;
		Supplier<Goal> var12 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(var11_f5),((AbilityCore)(WeaponSpinAbility.INSTANCE.get())));
		};
		var2.addEntry(var12,2);
		OPEntity var13 = var0;
		final OPEntity var13_f6 = var13;
		Consumer var14 = (Consumer) ((Consumer<Supplier>) ((l0) -> {
			var13_f6.goalSelector.addGoal(2,((Goal)(l0.get())));
		}));
		var2.pickN(var0.getRandom(),(var0.isAboveNormalDifficulty()) ? (2) : (1)).forEach(var14);
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
	private static void initSniper(OPEntity var0) {
		var0.getEntityStats().setFightingStyle(((FightingStyle)(ModFightingStyles.SNIPER.get())));
		var0.setItemSlot(EquipmentSlot.MAINHAND,new ItemStack(((ItemLike)(ModWeapons.BAZOOKA.get()))));
		var0.goalSelector.addGoal(1,(Goal)(new RangedAttackGoal((RangedAttackMob)(var0),1D,(var0.isAboveNormalDifficulty()) ? (40) : (60),40F)));
	}
	private static void initBrawler(OPEntity var0) {
		var0.getEntityStats().setFightingStyle(((FightingStyle)(ModFightingStyles.BRAWLER.get())));
		var0.goalSelector.addGoal(0,(Goal)(new AlwaysActiveAbilityWrapperGoal((Mob)(var0),((AbilityCore)(BrawlerPassiveBonusesAbility.INSTANCE.get())))));
		WeightedList var1 = new WeightedList(new Object[0]);
		OPEntity var2 = var0;
		final OPEntity var2_f1 = var2;
		Supplier<Goal> var3 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(var2_f1),((AbilityCore)(ChargedPunchAbility.INSTANCE.get())));
		};
		var1.addEntry(var3,4);
		OPEntity var4 = var0;
		final OPEntity var4_f2 = var4;
		Supplier<Goal> var5 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var4_f2),((AbilityCore)(GenkotsuMeteorAbility.INSTANCE.get())));
		};
		var1.addEntry(var5,4);
		OPEntity var6 = var0;
		final OPEntity var6_f3 = var6;
		Supplier<Goal> var7 = () -> {
			return new JumpAbilityWrapperGoal((Mob)(var6_f3),((AbilityCore)(SlamAbility.INSTANCE.get())));
		};
		var1.addEntry(var7,3);
		OPEntity var8 = var0;
		final OPEntity var8_f4 = var8;
		Supplier<Goal> var9 = () -> {
			return new GrabAbilityWrapperGoal((Mob)(var8_f4),((AbilityCore)(SuplexAbility.INSTANCE.get())));
		};
		var1.addEntry(var9,2);
		OPEntity var10 = var0;
		final OPEntity var10_f5 = var10;
		Supplier<Goal> var11 = () -> {
			return new GrabAbilityWrapperGoal((Mob)(var10_f5),((AbilityCore)(SpinningBrawlAbility.INSTANCE.get())));
		};
		var1.addEntry(var11,2);
		OPEntity var12 = var0;
		final OPEntity var12_f6 = var12;
		Supplier<Goal> var13 = () -> {
			DashAbilityWrapperGoal lv15_1 = new DashAbilityWrapperGoal((Mob)(var12_f6),((AbilityCore)(TackleAbility.INSTANCE.get())));
			TackleAbility lv15_2 = ((TackleAbility)(lv15_1.getAbility()));
			Consumer<CooldownComponent> lv15_3 = (l2_0) -> {
				l2_0.getBonusManager().addBonus(TACKLE_COOLDOWN_BONUS_UUID,"Tackle Cooldown Bonus",BonusOperation.MUL,2F);
			};
			((java.util.Optional)(lv15_2.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(lv15_3));
			return lv15_1;
		};
		var1.addEntry(var13,2);
		OPEntity var14 = var0;
		final OPEntity var14_f7 = var14;
		Supplier<Goal> var15 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(var14_f7),((AbilityCore)(HakaiHoAbility.INSTANCE.get())));
		};
		var1.addEntry(var15,2);
		OPEntity var16 = var0;
		final OPEntity var16_f8 = var16;
		Consumer var17 = (Consumer) ((Consumer<Supplier>) ((l0) -> {
			var16_f8.goalSelector.addGoal(2,((Goal)(l0.get())));
		}));
		var1.pickN(var0.getRandom(),(var0.isAboveNormalDifficulty()) ? (2) : (1)).forEach(var17);
	}
	private static void initMarine(OPEntity var0) {
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
		if (((java.lang.Object)(this.getMainHandItem().getItem())).equals(ModWeapons.BAZOOKA.get())){
			return true;
		}else{
			return false;
		}
	}
	@Nullable()
	public Projectile getRangedProjectile(LivingEntity var1) {
		CannonBallProjectile var2 = new CannonBallProjectile((Level)(this.level()),(LivingEntity)(this));
		var2.setDamage(5F);
		if (this.isAboveNormalDifficulty()){
			var2.setDamage(10F);
		}
		var2.clearBlockHitEvents();
		BruteEntity var3 = this;
		NuProjectileEntity var4 = var2;
		final NuProjectileEntity var4_f1 = var4;
		NuProjectileEntity$IOnHitBlockEvent var5 = (l0) -> {
			AbilityExplosion lv18_3 = new AbilityExplosion((Entity)(this),(IAbility)(null),var4_f1.getX(),var4_f1.getY(),var4_f1.getZ(),2F);
			lv18_3.setStaticDamage(5F);
			lv18_3.setDestroyBlocks(false);
			lv18_3.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION2));
			lv18_3.explode();
		};
		var2.addBlockHitEvent(100,var5);
		return var2;
	}
	static  {
		IMobDetails[] var0 = new IMobDetails[3];
		IMobDetails var1 = BruteEntity::initSwordsman;
		var0[0] = var1;
		IMobDetails var2 = BruteEntity::initSniper;
		var0[1] = var2;
		IMobDetails var3 = BruteEntity::initBrawler;
		var0[2] = var3;
		STYLES = Lists.newArrayList(var0);
	}
}