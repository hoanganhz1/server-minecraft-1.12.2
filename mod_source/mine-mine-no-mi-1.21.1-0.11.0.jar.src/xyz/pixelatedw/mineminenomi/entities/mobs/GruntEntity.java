package xyz.pixelatedw.mineminenomi.entities.mobs;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
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
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import xyz.pixelatedw.mineminenomi.abilities.SlamAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.PunchRushAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.HiryuKaenAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.YakkodoriAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.KaenBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.SakuretsuSabotenBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.TetsuBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.TokuyoAburaBoshiAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.IMobDetails;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.data.world.ExtendedWorldData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.HandleCannonGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.RunAwayFromFearGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.projectiles.KairosekiBulletProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.NormalBulletProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class GruntEntity extends OPEntity {
	// Fields
	private static final List<IMobDetails<OPEntity>> STYLES;
	private static final Map<Faction, ArrayList<Supplier<? extends Item>>> MELEE_FACTION_WEAPONS;

	private static Map<Faction, ArrayList<Supplier<? extends Item>>> createFactionWeaponsMap() {
		HashMap var0 = new HashMap();
		var0.put(((Faction)(ModFactions.PIRATE.get())),MobsHelper.PIRATE_SWORDS);
		var0.put(((Faction)(ModFactions.MARINE.get())),MobsHelper.MARINE_SWORDS);
		var0.put(((Faction)(ModFactions.BANDIT.get())),MobsHelper.BANDIT_SWORDS);
		return var0;
	}
	public GruntEntity(EntityType<? extends GruntEntity> var1, Level var2) {
		super((EntityType)(var1),var2,null);
		if (((var2) != (null)) && (!(var2.isClientSide))){
			boolean var3 = this.isAboveNormalDifficulty();
			boolean var4 = ExtendedWorldData.get().isNewWorldActive(this.blockPosition());
			this.getEntityStats().setDoriki((500D) + (this.getRandomDorikiBonus(0,1500)));
			this.getEntityStats().setBelly((5L) + ((long)(WyHelper.randomWithRange(0,5))));
			GruntEntity var5 = this;
			boolean var6 = var4;
			final boolean var6_f1 = var6;
			Consumer<HakiData> var7 = (l0) -> {
			int lv1_3 = 0;
			if (var6_f1){
				lv1_3 = (lv1_3) + (5);
			}
			l0.setBusoshokuHakiExp(this.getRandomHakiBonus(lv1_3,10));
			l0.setKenbunshokuHakiExp(this.getRandomHakiBonus(lv1_3,10));
		};
			((java.util.Optional)(HakiData.get((LivingEntity)(this)))).ifPresent((Consumer)(var7));
			this.getAttribute(ModAttributes.GCD).setBaseValue(60D);
			if ((this.level().getDifficulty().getId()) <= (2)){
				switch (GruntEntity$1.$SwitchMap$net$minecraft$world$Difficulty[this.level().getDifficulty().ordinal()]){
				case 1:

				case 2:

				default:
					float var8 = 1F;
					AttributeInstance var9 = this.getAttribute(Attributes.FOLLOW_RANGE);
					var9.setBaseValue((var9.getBaseValue()) / ((double)(var8)));
				}
			}
			MobsHelper.addBasicNPCGoals(this);
			if ((!(var3)) && (!(var4))){

			}else{
				int var8_1 = 0;
				if (var3){
					var8_1 = (var8_1) + (10);
				}
				if (var4){
					var8_1 = (var8_1) + (10);
				}
				GruntEntity var9_1 = this;
				Consumer var10 = (Consumer) ((Consumer<AbilityCore>) ((l0) -> {
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),l0)));
		}));
				MobsHelper.getBasicHakiAbilities((Mob)(this),var8_1).forEach(var10);
			}
			if ((this.getRandom().nextInt(10)) < (3)){
				this.goalSelector.addGoal(0,(Goal)(new RunAwayFromFearGoal((Mob)(this),1.5D,3D)));
			}
			this.goalSelector.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1.15D,true)));
			if ((!(this.getEntityStats().isMarine())) && (!(this.getEntityStats().isPirate()))){
				return;
			}else{
				this.goalSelector.addGoal(2,(Goal)(new HandleCannonGoal((Mob)(this))));
			}
		}
	}
	public static GruntEntity createMarineGrunt(EntityType<? extends GruntEntity> var0, Level var1) {
		GruntEntity var2 = new GruntEntity(var0,var1);
		var2.setFactionInit((IMobDetails)(((xyz.pixelatedw.mineminenomi.api.entities.IMobDetails)(GruntEntity::initMarine))));
		return var2;
	}
	public static GruntEntity createPirateGrunt(EntityType<? extends GruntEntity> var0, Level var1) {
		GruntEntity var2 = new GruntEntity(var0,var1);
		var2.setFactionInit((IMobDetails)(((xyz.pixelatedw.mineminenomi.api.entities.IMobDetails)(GruntEntity::initPirate))));
		return var2;
	}
	public static GruntEntity createBanditGrunt(EntityType<? extends GruntEntity> var0, Level var1) {
		GruntEntity var2 = new GruntEntity(var0,var1);
		var2.setFactionInit((IMobDetails)(((xyz.pixelatedw.mineminenomi.api.entities.IMobDetails)(GruntEntity::initBandit))));
		return var2;
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,30D).add(Attributes.MOVEMENT_SPEED,0.30000001192092896D).add(Attributes.ATTACK_DAMAGE,(double)(WyHelper.randomWithRange(1,2))).add(Attributes.MAX_HEALTH,(double)(WyHelper.randomWithRange(10,20)));
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
		if (((var4.nextDouble()) * (100D)) > (ServerConfig.getGruntSpawnChance())){
			return false;
		}else{
			return OPEntity.checkSpawnRules(var0,var1,var2,var3,var4);
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
	private static void initSwordsman(OPEntity var0) {
		OPEntity var10 = null;
		OPEntity var4 = null;
		OPEntity var6 = null;
		OPEntity var8 = null;
		var0.getEntityStats().setFightingStyle(((FightingStyle)(ModFightingStyles.SWORDSMAN.get())));
		Faction var1 = ((Faction)(((java.util.Optional)(var0.getEntityStats().getFaction())).orElse((Object)(null))));
		if ((var1) == (null)){
			return;
		}else{
			ItemStack var2 = var0.getRandomSword(((List)(MELEE_FACTION_WEAPONS.get(var1))));
			if ((var0.getEntityStats().isMarine()) && (var2.has(((DataComponentType)(ModDataComponents.MULTI_CHANNEL_DYES.get()))))){
				MultiChannelItemColor.setLayerColor(var2,0,33980);
			}
			var0.setItemSlot(EquipmentSlot.MAINHAND,var2);
			if (!(var0.isAboveNormalDifficulty())){
				return;
			}else{
				WeightedList var3 = new WeightedList(new Object[0]);
				var4 = var0;
				final OPEntity var4_f1 = var4;
				Supplier<Goal> var5 = () -> {
			return new JumpAbilityWrapperGoal((Mob)(var4_f1),((AbilityCore)(HiryuKaenAbility.INSTANCE.get())));
		};
				var3.addEntry(var5,3);
				var6 = var0;
				final OPEntity var6_f2 = var6;
				Supplier<Goal> var7 = () -> {
			return new DashAbilityWrapperGoal((Mob)(var6_f2),((AbilityCore)(ShiShishiSonsonAbility.INSTANCE.get())));
		};
				var3.addEntry(var7,3);
				var8 = var0;
				final OPEntity var8_f3 = var8;
				Supplier<Goal> var9 = () -> {
			ProjectileAbilityWrapperGoal lv5_1 = new ProjectileAbilityWrapperGoal((Mob)(var8_f3),((AbilityCore)(YakkodoriAbility.INSTANCE.get())));
			((YakkodoriAbility)(lv5_1.getAbility())).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
			return lv5_1;
		};
				var3.addEntry(var9,1);
				var10 = var0;
				final OPEntity var10_f4 = var10;
				Consumer var11 = (Consumer) ((Consumer<Supplier>) ((l0) -> {
			var10_f4.goalSelector.addGoal(2,((Goal)(l0.get())));
		}));
				var3.pickN(var0.getRandom(),1).forEach(var11);
				return;
			}
		}
	}
	private static void initSniper(OPEntity var0) {
		var0.getEntityStats().setFightingStyle(((FightingStyle)(ModFightingStyles.SNIPER.get())));
		var0.setItemSlot(EquipmentSlot.MAINHAND,new ItemStack(((ItemLike)(ModWeapons.FLINTLOCK.get()))));
		var0.goalSelector.addGoal(1,(Goal)(new RangedAttackGoal((RangedAttackMob)(var0),1D,40,15F)));
		if (!(var0.isAboveNormalDifficulty())){
			return;
		}else{
			WeightedList var1 = new WeightedList(new Object[0]);
			OPEntity var2 = var0;
			final OPEntity var2_f1 = var2;
			Supplier<Goal> var3 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var2_f1),((AbilityCore)(KaenBoshiAbility.INSTANCE.get())));
		};
			var1.addEntry(var3,5);
			OPEntity var4 = var0;
			final OPEntity var4_f2 = var4;
			Supplier<Goal> var5 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var4_f2),((AbilityCore)(TokuyoAburaBoshiAbility.INSTANCE.get())));
		};
			var1.addEntry(var5,3);
			OPEntity var6 = var0;
			final OPEntity var6_f3 = var6;
			Supplier<Goal> var7 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var6_f3),((AbilityCore)(TetsuBoshiAbility.INSTANCE.get())));
		};
			var1.addEntry(var7,1);
			OPEntity var8 = var0;
			final OPEntity var8_f4 = var8;
			Supplier<Goal> var9 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var8_f4),((AbilityCore)(SakuretsuSabotenBoshiAbility.INSTANCE.get())));
		};
			var1.addEntry(var9,1);
			OPEntity var10 = var0;
			final OPEntity var10_f5 = var10;
			Consumer var11 = (Consumer) ((Consumer<Supplier>) ((l0) -> {
			var10_f5.goalSelector.addGoal(2,((Goal)(l0.get())));
		}));
			var1.pickN(var0.getRandom(),1).forEach(var11);
			return;
		}
	}
	private static void initBrawler(OPEntity var0) {
		var0.getEntityStats().setFightingStyle(((FightingStyle)(ModFightingStyles.BRAWLER.get())));
		if (!(var0.isAboveNormalDifficulty())){
			return;
		}else{
			WeightedList var1 = new WeightedList(new Object[0]);
			OPEntity var2 = var0;
			final OPEntity var2_f1 = var2;
			Supplier<Goal> var3 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(var2_f1),((AbilityCore)(ChargedPunchAbility.INSTANCE.get())));
		};
			var1.addEntry(var3,5);
			OPEntity var4 = var0;
			final OPEntity var4_f2 = var4;
			Supplier<Goal> var5 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(var4_f2),((AbilityCore)(PunchRushAbility.INSTANCE.get())));
		};
			var1.addEntry(var5,3);
			OPEntity var6 = var0;
			final OPEntity var6_f3 = var6;
			Supplier<Goal> var7 = () -> {
			return new JumpAbilityWrapperGoal((Mob)(var6_f3),((AbilityCore)(SlamAbility.INSTANCE.get())));
		};
			var1.addEntry(var7,3);
			OPEntity var8 = var0;
			final OPEntity var8_f4 = var8;
			Supplier<Goal> var9 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(var8_f4),((AbilityCore)(HakaiHoAbility.INSTANCE.get())));
		};
			var1.addEntry(var9,1);
			OPEntity var10 = var0;
			final OPEntity var10_f5 = var10;
			Consumer var11 = (Consumer) ((Consumer<Supplier>) ((l0) -> {
			var10_f5.goalSelector.addGoal(2,((Goal)(l0.get())));
		}));
			var1.pickN(var0.getRandom(),1).forEach(var11);
			return;
		}
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
		if (ItemsHelper.isBowOrGun(this.getMainHandItem())){
			return true;
		}else{
			return false;
		}
	}
	@Nullable()
	public Projectile getRangedProjectile(LivingEntity var1) {
		NuProjectileEntity var2 = new NormalBulletProjectile((Level)(this.level()),(LivingEntity)(this));
		var2.setDamage(3F);
		if (this.isAboveNormalDifficulty()){
			var2 = new KairosekiBulletProjectile((Level)(this.level()),(LivingEntity)(this));
			var2.setDamage(6F);
		}
		return var2;
	}
	static  {
		IMobDetails[] var0 = new IMobDetails[3];
		IMobDetails var1 = GruntEntity::initSwordsman;
		var0[0] = var1;
		IMobDetails var2 = GruntEntity::initSniper;
		var0[1] = var2;
		IMobDetails var3 = GruntEntity::initBrawler;
		var0[2] = var3;
		STYLES = Lists.newArrayList(var0);
		MELEE_FACTION_WEAPONS = createFactionWeaponsMap();
	}
}