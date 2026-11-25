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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.LevelData;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.abilities.brawler.BrawlerPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.GenkotsuMeteorAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.JishinHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.PunchRushAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SpinningBrawlAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SuplexAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.HiryuKaenAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.SanbyakurokujuPoundHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.YakkodoriAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.TekkaiAbility;
import xyz.pixelatedw.mineminenomi.abilities.santoryu.OTatsumakiAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.IMobDetails;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;
import xyz.pixelatedw.mineminenomi.config.AbilitiesConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ThrowKnifeGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.GrabAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class CaptainEntity extends OPEntity {
	// Fields
	private static final List<IMobDetails<OPEntity>> STYLES;
	private static final Map<Faction, ArrayList<Supplier<? extends Item>>> MELEE_FACTION_WEAPONS;

	private static Map<Faction, ArrayList<Supplier<? extends Item>>> createFactionWeaponsMap() {
		HashMap var0 = new HashMap();
		var0.put(((Faction)(ModFactions.PIRATE.get())),MobsHelper.PIRATE_SWORDS);
		var0.put(((Faction)(ModFactions.MARINE.get())),MobsHelper.MARINE_CAPTAIN_SWORDS);
		var0.put(((Faction)(ModFactions.BANDIT.get())),MobsHelper.BANDIT_SWORDS);
		return var0;
	}
	public CaptainEntity(EntityType<? extends CaptainEntity> var1, Level var2) {
		super((EntityType)(var1),var2,null);
		if (((var2) != (null)) && (!(var2.isClientSide))){
			if ((this.random.nextFloat()) < (0.7F)){
				if ((this.random.nextFloat()) < (0.3F)){
					this.setItemSlot(EquipmentSlot.HEAD,new ItemStack(((ItemLike)(ModItems.THREE_CIGARS.get()))));
				}else{
					this.setItemSlot(EquipmentSlot.HEAD,new ItemStack(((ItemLike)(ModItems.CIGAR.get()))));
				}
			}
			boolean var3 = this.isAboveNormalDifficulty();
			this.getEntityStats().setDoriki((4000D) + (this.getRandomDorikiBonus(0,2000)));
			this.getEntityStats().setBelly((50L) + ((long)(WyHelper.randomWithRange(0,20))));
			CaptainEntity var4 = this;
			Consumer<HakiData> var5 = (l0) -> {
			l0.setBusoshokuHakiExp((55F) + (this.getRandomHakiBonus(0,10)));
			l0.setKenbunshokuHakiExp((55F) + (this.getRandomHakiBonus(0,10)));
		};
			((java.util.Optional)(HakiData.get((LivingEntity)(this)))).ifPresent((Consumer)(var5));
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(6D);
			this.getAttribute(ModAttributes.GCD).setBaseValue(40D);
			if ((this.level().getDifficulty().getId()) <= (2)){
				switch (CaptainEntity$1.$SwitchMap$net$minecraft$world$Difficulty[this.level().getDifficulty().ordinal()]){
				case 1:

				case 2:

				default:
					float var6 = 1F;
					AttributeInstance var7 = this.getAttribute(Attributes.FOLLOW_RANGE);
					var7.setBaseValue((var7.getBaseValue()) / ((double)(var6)));
				}
			}
			MobsHelper.addBasicNPCGoals(this);
			CaptainEntity var6_1 = this;
			Consumer var7_1 = (Consumer) ((Consumer<AbilityCore>) ((l0) -> {
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),l0)));
		}));
			MobsHelper.getBasicHakiAbilities((Mob)(this),50).forEach(var7_1);
			if (var3){
				CaptainEntity var8 = this;
				Consumer var9 = (Consumer) ((Consumer<AbilityCore>) ((l0) -> {
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),l0)));
		}));
				MobsHelper.getAdvancedHakiAbilities((Mob)(this),10).forEach(var9);
			}
			this.goalSelector.addGoal(0,(Goal)(new JumpOutOfHoleGoal((Mob)(this))));
			if ((this.random.nextFloat()) < (0.6F)){
				this.goalSelector.addGoal(0,(Goal)(new SprintTowardsTargetGoal((Mob)(this))));
			}
			this.goalSelector.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1D,true)));
		}
	}
	public static CaptainEntity createMarineCaptain(EntityType<? extends CaptainEntity> var0, Level var1) {
		CaptainEntity var2 = new CaptainEntity(var0,var1);
		var2.setFactionInit((IMobDetails)(((xyz.pixelatedw.mineminenomi.api.entities.IMobDetails)(CaptainEntity::initMarine))));
		return var2;
	}
	public static CaptainEntity createPirateCaptain(EntityType<? extends CaptainEntity> var0, Level var1) {
		CaptainEntity var2 = new CaptainEntity(var0,var1);
		var2.setFactionInit((IMobDetails)(((xyz.pixelatedw.mineminenomi.api.entities.IMobDetails)(CaptainEntity::initPirate))));
		return var2;
	}
	public static CaptainEntity createBanditCaptain(EntityType<? extends CaptainEntity> var0, Level var1) {
		CaptainEntity var2 = new CaptainEntity(var0,var1);
		var2.setFactionInit((IMobDetails)(((xyz.pixelatedw.mineminenomi.api.entities.IMobDetails)(CaptainEntity::initBandit))));
		return var2;
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,50D).add(Attributes.MOVEMENT_SPEED,0.30000001192092896D).add(Attributes.ATTACK_DAMAGE,(double)(WyHelper.randomWithRange(3,6))).add(Attributes.MAX_HEALTH,(double)(WyHelper.randomWithRange(80,120))).add(Attributes.ARMOR,(double)(WyHelper.randomWithRange(8,12)));
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
		int var5 = ((Integer)(AbilitiesConfig.OPEN_WORLD_FRUIT_USERS.get())).intValue();
		if (((var5) > (0)) && ((this.random.nextInt(100)) <= (var5))){
			CaptainEntity var6 = this;
			Consumer<ImmutablePair> var7 = (l0) -> {
			ImmutablePair lv6_2 = l0;
			final ImmutablePair lv6_2_f1 = lv6_2;
			Consumer<DevilFruitData> lv6_3 = (l2_0) -> {
				l2_0.setDevilFruit(((AkumaNoMiItem)(lv6_2_f1.getKey())));
			};
			((java.util.Optional)(DevilFruitData.get((LivingEntity)(this)))).ifPresent((Consumer)(lv6_3));
			List lv6_4 = ((List)(l0.getValue()));
			CaptainEntity lv6_5 = this;
			Consumer lv6_6 = (Consumer) ((Consumer<AbilityWrapperGoal>) ((l2_0) -> {
				this.goalSelector.addGoal(2,(Goal)(l2_0));
			}));
			lv6_4.forEach(lv6_6);
		};
			((java.util.Optional)(MobsHelper.getRandomDevilFruitAbilities((Mob)(this)))).ifPresent((Consumer)(var7));
		}
		return var4;
	}
	public static boolean checkSpawnRules(EntityType<? extends OPEntity> var0, ServerLevelAccessor var1, MobSpawnType var2, BlockPos var3, RandomSource var4) {
		if (((var2) != (MobSpawnType.SPAWNER)) && (var3.closerThan((Vec3i)(var1.getLevelData().getSpawnPos()),512D))){
			return false;
		}else{
			if (((var4.nextDouble()) * (100D)) > (ServerConfig.getCaptainSpawnChance())){
				return false;
			}else{
				return OPEntity.checkSpawnRules(var0,var1,var2,var3,var4);
			}
		}
	}
	private static void initMarine(OPEntity var0) {
		var0.getEntityStats().setFaction(((Faction)(ModFactions.MARINE.get())));
		var0.setTextures(MobsHelper.MARINE_CAPTAINS_TEXTURES);
		ItemStack var1 = new ItemStack(((ItemLike)(ModArmors.MARINE_CAPTAIN_CAPE.get())));
		MultiChannelItemColor.setLayerColor(var1,0,33980);
		var0.setItemSlot(EquipmentSlot.CHEST,var1);
	}
	private static void initPirate(OPEntity var0) {
		var0.getEntityStats().setFaction(((Faction)(ModFactions.PIRATE.get())));
		var0.setTextures(MobsHelper.PIRATE_CAPTAINS_TEXTURES);
		if ((var0.getRandom().nextFloat()) < (0.7F)){
			ItemStack var1 = new ItemStack(((ItemLike)(ModArmors.PIRATE_CAPTAIN_CAPE.get())));
			var0.setItemSlot(EquipmentSlot.CHEST,var1);
		}
		FactionsWorldData.get().addTemporaryCrewMember(ModNPCGroups.WORLD_PIRATES,(LivingEntity)(var0));
	}
	private static void initBandit(OPEntity var0) {
		var0.getEntityStats().setFaction(((Faction)(ModFactions.BANDIT.get())));
		var0.setTextures(MobsHelper.BANDIT_TEXTURES);
		var0.goalSelector.addGoal(2,(Goal)(new ThrowKnifeGoal((Mob)(var0)).setAmount(3)));
	}
	private static void initSwordsman(OPEntity var0) {
		var0.getEntityStats().setFightingStyle(((FightingStyle)(ModFightingStyles.SWORDSMAN.get())));
		Faction var1 = ((Faction)(((java.util.Optional)(var0.getEntityStats().getFaction())).orElse((Object)(null))));
		if ((var1) == (null)){
			return;
		}else{
			ItemStack var2 = var0.getRandomSword(((List)(MELEE_FACTION_WEAPONS.get(var1))));
			var0.setItemSlot(EquipmentSlot.MAINHAND,var2);
			float var3 = (var0.getEntityStats().isPirate()) ? (0.4F) : (0.2F);
			if ((var0.getRandom().nextFloat()) < (var3)){
				var0.setItemSlot(EquipmentSlot.OFFHAND,var2.copy());
			}
			int var4 = (var0.getEntityStats().isBandit()) ? (2) : (4);
			WeightedList var5 = new WeightedList(new Object[0]);
			OPEntity var6 = var0;
			final OPEntity var6_f1 = var6;
			Supplier<Goal> var7 = () -> {
			return new DashAbilityWrapperGoal((Mob)(var6_f1),((AbilityCore)(ShiShishiSonsonAbility.INSTANCE.get())));
		};
			var5.addEntry(var7,4);
			OPEntity var8 = var0;
			final OPEntity var8_f2 = var8;
			Supplier<Goal> var9 = () -> {
			return new JumpAbilityWrapperGoal((Mob)(var8_f2),((AbilityCore)(HiryuKaenAbility.INSTANCE.get())));
		};
			var5.addEntry(var9,4);
			OPEntity var10 = var0;
			final OPEntity var10_f3 = var10;
			Supplier<Goal> var11 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(var10_f3),((AbilityCore)(OTatsumakiAbility.INSTANCE.get())));
		};
			var5.addEntry(var11,2);
			OPEntity var12 = var0;
			final OPEntity var12_f4 = var12;
			Supplier<Goal> var13 = () -> {
			ProjectileAbilityWrapperGoal lv10_1 = new ProjectileAbilityWrapperGoal((Mob)(var12_f4),((AbilityCore)(SanbyakurokujuPoundHoAbility.INSTANCE.get())));
			((SanbyakurokujuPoundHoAbility)(lv10_1.getAbility())).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
			return lv10_1;
		};
			var5.addEntry(var13,2);
			OPEntity var14 = var0;
			final OPEntity var14_f5 = var14;
			Supplier<Goal> var15 = () -> {
			ProjectileAbilityWrapperGoal lv11_1 = new ProjectileAbilityWrapperGoal((Mob)(var14_f5),((AbilityCore)(YakkodoriAbility.INSTANCE.get())));
			((YakkodoriAbility)(lv11_1.getAbility())).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
			return lv11_1;
		};
			var5.addEntry(var15,1);
			Set var16 = var5.pickN(var0.getRandom(),var4);
			OPEntity var17 = var0;
			final OPEntity var17_f6 = var17;
			Consumer var18 = (Consumer) ((Consumer<Supplier>) ((l0) -> {
			var17_f6.goalSelector.addGoal(4,((Goal)(l0.get())));
		}));
			var16.forEach(var18);
			return;
		}
	}
	private static void initBrawler(OPEntity var0) {
		var0.getEntityStats().setFightingStyle(((FightingStyle)(ModFightingStyles.BRAWLER.get())));
		int var1 = (var0.getEntityStats().isBandit()) ? (4) : (3);
		var0.goalSelector.addGoal(0,(Goal)(new AlwaysActiveAbilityWrapperGoal((Mob)(var0),((AbilityCore)(BrawlerPassiveBonusesAbility.INSTANCE.get())))));
		WeightedList var2 = new WeightedList(new Object[0]);
		OPEntity var3 = var0;
		final OPEntity var3_f1 = var3;
		Supplier<Goal> var4 = () -> {
			ProjectileAbilityWrapperGoal lv13_1 = new ProjectileAbilityWrapperGoal((Mob)(var3_f1),((AbilityCore)(GenkotsuMeteorAbility.INSTANCE.get())));
			((GenkotsuMeteorAbility)(lv13_1.getAbility())).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
			return lv13_1;
		};
		var2.addEntry(var4,5);
		OPEntity var5 = var0;
		final OPEntity var5_f2 = var5;
		Supplier<Goal> var6 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(var5_f2),((AbilityCore)(ChargedPunchAbility.INSTANCE.get())));
		};
		var2.addEntry(var6,5);
		OPEntity var7 = var0;
		final OPEntity var7_f3 = var7;
		Supplier<Goal> var8 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(var7_f3),((AbilityCore)(HakaiHoAbility.INSTANCE.get())));
		};
		var2.addEntry(var8,4);
		OPEntity var9 = var0;
		final OPEntity var9_f4 = var9;
		Supplier<Goal> var10 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(var9_f4),((AbilityCore)(JishinHoAbility.INSTANCE.get())));
		};
		var2.addEntry(var10,4);
		OPEntity var11 = var0;
		final OPEntity var11_f5 = var11;
		Supplier<Goal> var12 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(var11_f5),((AbilityCore)(PunchRushAbility.INSTANCE.get())));
		};
		var2.addEntry(var12,3);
		OPEntity var13 = var0;
		final OPEntity var13_f6 = var13;
		Supplier<Goal> var14 = () -> {
			return new GrabAbilityWrapperGoal((Mob)(var13_f6),((AbilityCore)(SpinningBrawlAbility.INSTANCE.get())));
		};
		var2.addEntry(var14,2);
		OPEntity var15 = var0;
		final OPEntity var15_f7 = var15;
		Supplier<Goal> var16 = () -> {
			GrabAbilityWrapperGoal lv19_1 = new GrabAbilityWrapperGoal((Mob)(var15_f7),((AbilityCore)(SuplexAbility.INSTANCE.get())));
			((SuplexAbility)(lv19_1.getAbility())).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
			return lv19_1;
		};
		var2.addEntry(var16,2);
		Set var17 = var2.pickN(var0.getRandom(),var1);
		OPEntity var18 = var0;
		final OPEntity var18_f8 = var18;
		Consumer var19 = (Consumer) ((Consumer<Supplier>) ((l0) -> {
			var18_f8.goalSelector.addGoal(4,((Goal)(l0.get())));
		}));
		var17.forEach(var19);
	}
	private static void initHuman(OPEntity var0) {
		var0.getEntityStats().setRace(((Race)(ModRaces.HUMAN.get())));
		int var1 = (var0.getEntityStats().isBandit()) ? (2) : (3);
		WeightedList var2 = new WeightedList(new Object[0]);
		OPEntity var3 = var0;
		final OPEntity var3_f1 = var3;
		Supplier<Goal> var4 = () -> {
			return new DashAbilityWrapperGoal((Mob)(var3_f1),((AbilityCore)(SoruAbility.INSTANCE.get())));
		};
		var2.addEntry(var4,10);
		OPEntity var5 = var0;
		final OPEntity var5_f2 = var5;
		Supplier<Goal> var6 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(var5_f2),((AbilityCore)(ShiganAbility.INSTANCE.get())));
		};
		var2.addEntry(var6,7);
		OPEntity var7 = var0;
		final OPEntity var7_f3 = var7;
		Supplier<Goal> var8 = () -> {
			return new ReactiveGuardAbilityWrapperGoal((Mob)(var7_f3),((AbilityCore)(TekkaiAbility.INSTANCE.get())));
		};
		var2.addEntry(var8,7);
		OPEntity var9 = var0;
		final OPEntity var9_f4 = var9;
		Supplier<Goal> var10 = () -> {
			ProjectileAbilityWrapperGoal lv24_1 = new ProjectileAbilityWrapperGoal((Mob)(var9_f4),((AbilityCore)(RankyakuAbility.INSTANCE.get())));
			((RankyakuAbility)(lv24_1.getAbility())).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
			return lv24_1;
		};
		var2.addEntry(var10,4);
		OPEntity var11 = var0;
		final OPEntity var11_f5 = var11;
		Supplier<Goal> var12 = () -> {
			return new ReactiveGuardAbilityWrapperGoal((Mob)(var11_f5),((AbilityCore)(KamieAbility.INSTANCE.get())));
		};
		var2.addEntry(var12,1);
		Set var13 = var2.pickN(var0.getRandom(),var1);
		OPEntity var14 = var0;
		final OPEntity var14_f6 = var14;
		Consumer var15 = (Consumer) ((Consumer<Supplier>) ((l0) -> {
			var14_f6.goalSelector.addGoal(4,((Goal)(l0.get())));
		}));
		var13.forEach(var15);
	}
	static  {
		IMobDetails[] var0 = new IMobDetails[2];
		IMobDetails var1 = CaptainEntity::initSwordsman;
		var0[0] = var1;
		IMobDetails var2 = CaptainEntity::initBrawler;
		var0[1] = var2;
		STYLES = Lists.newArrayList(var0);
		MELEE_FACTION_WEAPONS = createFactionWeaponsMap();
	}
}