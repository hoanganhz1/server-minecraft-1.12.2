package xyz.pixelatedw.mineminenomi.entities.mobs;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.AntiMannerKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.BlackLegPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.ConcasseAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.ExtraHachisAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.PartyTableKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.BrawlerPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.GenkotsuMeteorAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.JishinHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.PunchRushAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SpinningBrawlAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SuplexAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.FishmanPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.KachiageHaisokuAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.KarakusagawaraSeikenAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.MizuOsuAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.MizuShuryudanAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.PackOfSharksAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.SamehadaShoteiAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.UchimizuAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.KenbunshokuHakiFutureSightAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.HiryuKaenAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.SanbyakurokujuPoundHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.YakkodoriAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.GeppoAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.TekkaiAbility;
import xyz.pixelatedw.mineminenomi.abilities.santoryu.OTatsumakiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.KaenBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.KemuriBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.NemuriBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.RenpatsuNamariBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.SakuretsuSabotenBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.TetsuBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.TokuyoAburaBoshiAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.IMobDetails;
import xyz.pixelatedw.mineminenomi.api.entities.ServerOPBossEvent;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.api.poi.INotoriousTarget;
import xyz.pixelatedw.mineminenomi.api.poi.TrackedNPC;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;
import xyz.pixelatedw.mineminenomi.config.AbilitiesConfig;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.GapCloserGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.PersonalSpaceTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.GrabAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveRetreatAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.RepeaterAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class NotoriousEntity extends OPEntity implements INotoriousTarget {
	// Fields
	private static final List<IMobDetails<NotoriousEntity>> STYLES;
	private static final List<IMobDetails<NotoriousEntity>> RACES;
	private static final Map<Faction, ArrayList<Supplier<? extends Item>>> MELEE_FACTION_WEAPONS;
	private static final String[] PIRATE_NAMES;
	private static final String[] PIRATE_PRE_TITLES;
	private static final String[] PIRATE_POST_TITLES;
	private static final String[] MARINE_NAMES;
	private final ServerOPBossEvent bossInfo;
	private final NPCPhaseManager phaseManager;
	private float difficulty;
	private boolean isTriggered;
	private TrackedNPC trackedData;
	private RandomSource trackedRandom;

	private static Map<Faction, ArrayList<Supplier<? extends Item>>> createFactionWeaponsMap() {
		HashMap var0 = new HashMap();
		var0.put(((Faction)(ModFactions.PIRATE.get())),MobsHelper.PIRATE_SWORDS);
		var0.put(((Faction)(ModFactions.MARINE.get())),MobsHelper.MARINE_CAPTAIN_SWORDS);
		var0.put(((Faction)(ModFactions.BANDIT.get())),MobsHelper.BANDIT_SWORDS);
		return var0;
	}
	public NotoriousEntity(EntityType<? extends NotoriousEntity> var1, Level var2, IMobDetails<NotoriousEntity> var3) {
		this(var1,var2,var3,null);
	}
	public NotoriousEntity(EntityType<? extends NotoriousEntity> var1, Level var2, IMobDetails<NotoriousEntity> var3, @Nullable() TrackedNPC var4) {
		super((EntityType)(var1),var2);
		this.bossInfo = new ServerOPBossEvent(this.getCustomName());
		this.phaseManager = new NPCPhaseManager((Mob)(this));
		if (((var2) != (null)) && (!(var2.isClientSide))){
			if ((var4) != (null)){
				this.trackedData = var4;
				this.trackedRandom = RandomSource.create(this.trackedData.getSeed());
				this.setUUID(var4.getUUID());
				this.getEntityStats().setFightingStyle(var4.getStyle());
				this.getEntityStats().setRace(var4.getRace());
				this.getEntityStats().setSubRace(var4.getSubRace());
			}else{
				this.trackedRandom = this.random;
				this.setUUID(new UUID(this.trackedRandom.nextLong(),this.trackedRandom.nextLong()));
				((IMobDetails)(STYLES.get(this.getRandom().nextInt(STYLES.size())))).init(this);
				((IMobDetails)(RACES.get(this.getRandom().nextInt(RACES.size())))).init(this);
			}
			var3.init(this);
			if ((this.trackedRandom.nextFloat()) < (0.5F)){
				if ((this.trackedRandom.nextFloat()) < (0.3F)){
					this.setItemSlot(EquipmentSlot.HEAD,new ItemStack(((ItemLike)(ModItems.THREE_CIGARS.get()))));
				}else{
					this.setItemSlot(EquipmentSlot.HEAD,new ItemStack(((ItemLike)(ModItems.CIGAR.get()))));
				}
			}
			this.setCustomName(this.trackedRandom);
			this.chooseTexture(this.trackedRandom);
			boolean var5 = this.isAboveNormalDifficulty();
			AttributeHelper.updateToughnessAttribute((LivingEntity)(this));
			this.getAttribute(ModAttributes.GCD).setBaseValue((var5) ? (20D) : (40D));
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(8D);
			MobsHelper.addBasicNPCGoals(this);
			this.goalSelector.addGoal(0,(Goal)(new JumpOutOfHoleGoal((Mob)(this))));
			this.goalSelector.addGoal(0,(Goal)(new GapCloserGoal((Mob)(this))));
			this.goalSelector.addGoal(0,(Goal)(new DashDodgeProjectilesGoal((Mob)(this),200F,2.5F)));
			this.goalSelector.addGoal(0,(Goal)(new DashDodgeTargetGoal((Mob)(this),250F,2.5F)));
			this.goalSelector.addGoal(0,(Goal)(new SprintTowardsTargetGoal((Mob)(this))));
			this.goalSelector.addGoal(1,(Goal)(new ImprovedMeleeAttackGoal((PathfinderMob)(this),1D,true)));
			if (this.getEntityStats().isBandit()){

			}else{
				this.targetSelector.addGoal(1,(Goal)(new PersonalSpaceTargetGoal((Mob)(this))));
				NotoriousEntity var6 = this;
				Consumer var7 = (Consumer) ((Consumer<AbilityCore>) ((l0) -> {
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),l0)));
		}));
				MobsHelper.getBasicHakiAbilities((Mob)(this),100).forEach(var7);
				NotoriousEntity var8 = this;
				Consumer var9 = (Consumer) ((Consumer<AbilityCore>) ((l0) -> {
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),l0)));
		}));
				MobsHelper.getAdvancedHakiAbilities((Mob)(this),100).forEach(var9);
				this.goalSelector.addGoal(1,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(KenbunshokuHakiFutureSightAbility.INSTANCE.get())))));
				int var10 = ((Integer)(AbilitiesConfig.OPEN_WORLD_FRUIT_USERS.get())).intValue();
				if (((var10) > (0)) && ((this.trackedRandom.nextInt(100)) <= (var10))){
					NotoriousEntity var11 = this;
					Consumer<ImmutablePair> var12 = (l0) -> {
			ImmutablePair lv5_2 = l0;
			final ImmutablePair lv5_2_f1 = lv5_2;
			Consumer<DevilFruitData> lv5_3 = (l2_0) -> {
				l2_0.setDevilFruit(((AkumaNoMiItem)(lv5_2_f1.getKey())));
			};
			((java.util.Optional)(DevilFruitData.get((LivingEntity)(this)))).ifPresent((Consumer)(lv5_3));
			List lv5_4 = ((List)(l0.getValue()));
			NotoriousEntity lv5_5 = this;
			Consumer lv5_6 = (Consumer) ((Consumer<AbilityWrapperGoal>) ((l2_0) -> {
				this.goalSelector.addGoal(2,(Goal)(l2_0));
			}));
			lv5_4.forEach(lv5_6);
		};
					((java.util.Optional)(MobsHelper.getRandomDevilFruitAbilities((Mob)(this)))).ifPresent((Consumer)(var12));
				}else{
					return;
				}
			}
		}
	}
	public static NotoriousEntity createMarine(EntityType<? extends NotoriousEntity> var0, Level var1) {
		return new NotoriousEntity(var0,var1,(IMobDetails)(((xyz.pixelatedw.mineminenomi.api.entities.IMobDetails)(NotoriousEntity::initMarine))));
	}
	public static NotoriousEntity createPirate(EntityType<? extends NotoriousEntity> var0, Level var1) {
		return new NotoriousEntity(var0,var1,(IMobDetails)(((xyz.pixelatedw.mineminenomi.api.entities.IMobDetails)(NotoriousEntity::initPirate))));
	}
	public static NotoriousEntity createBandit(EntityType<? extends NotoriousEntity> var0, Level var1) {
		return new NotoriousEntity(var0,var1,(IMobDetails)(((xyz.pixelatedw.mineminenomi.api.entities.IMobDetails)(NotoriousEntity::initBandit))));
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.30000001192092896D).add(Attributes.ATTACK_DAMAGE,6D).add(Attributes.MAX_HEALTH,(double)(WyHelper.randomWithRange(200,250))).add(Attributes.ARMOR,20D).add(Attributes.ARMOR_TOUGHNESS,10D);
	}
	private void setCustomName(RandomSource var1) {
		EntityStatsData var2 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(this)))).orElse((Object)(null))));
		if ((var2) == (null)){
			return;
		}else{
			String var3 = null;
			if (var2.isMarine()){
				var3 = MARINE_NAMES[var1.nextInt(MARINE_NAMES.length)];
				String var4 = var3;
				String var5 = "Vice Admiral " + var4;
				this.setCustomName((Component)(Component.literal(var5)));
			}else{
				if ((var1.nextInt(64)) == (0)){
					if (var1.nextBoolean()){
						this.setCustomName((Component)(Component.literal("Jack the Sparrow")));
					}else{
						this.setCustomName((Component)(Component.literal("Captain Jack Sparrow")));
					}
				}else{
					var3 = PIRATE_NAMES[var1.nextInt(PIRATE_NAMES.length)];
					String var4_1 = "";
					String var5_1 = "";
					if ((var1.nextInt(3)) == (0)){
						if (var1.nextBoolean()){
							String var6 = PIRATE_PRE_TITLES[var1.nextInt(PIRATE_PRE_TITLES.length)];
							String var7 = var6 + " ";
							var4_1 = var7;
							if ((var1.nextInt(4)) == (0)){

							}
						}else{
							var5_1 = PIRATE_POST_TITLES[var1.nextInt(PIRATE_POST_TITLES.length)];
						}
					}else{
						var4_1 = "Captain ";
					}
					String var6_1 = var5_1;
					String var7_1 = " " + var6_1;
					String var8 = var4_1;
					String var9 = var3;
					String var10 = (var5_1.isEmpty()) ? ("") : (var7_1);
					String var11 = var8 + var9 + var10;
					this.setCustomName((Component)(Component.literal(var11)));
				}
			}
			this.bossInfo.setName(this.getCustomName());
			return;
		}
	}
	public void tick() {
		this.bossInfo.tick((LivingEntity)(this));
		super.tick();
	}
	public void startSeenByPlayer(ServerPlayer var1) {
		super.startSeenByPlayer(var1);
		if ((this.bossInfo) != (null)){
			this.bossInfo.addPlayer(var1);
		}
		if (!(this.isTriggered)){
			this.isTriggered = true;
		}
		this.bossInfo.setName(this.getCustomName());
	}
	public void stopSeenByPlayer(ServerPlayer var1) {
		super.stopSeenByPlayer(var1);
		if ((this.bossInfo) != (null)){
			this.bossInfo.removePlayer(var1);
		}
		if (this.isTriggered){
			this.isTriggered = false;
		}
	}
	private static void initSwordsman(NotoriousEntity var0) {
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
			int var4 = (4) + (var0.trackedRandom.nextInt(2));
			WeightedList var5 = new WeightedList(new Object[0]);
			NotoriousEntity var6 = var0;
			final NotoriousEntity var6_f1 = var6;
			Supplier<Goal> var7 = () -> {
			return new DashAbilityWrapperGoal((Mob)(var6_f1),((AbilityCore)(ShiShishiSonsonAbility.INSTANCE.get())));
		};
			var5.addEntry(var7,4);
			NotoriousEntity var8 = var0;
			final NotoriousEntity var8_f2 = var8;
			Supplier<Goal> var9 = () -> {
			return new JumpAbilityWrapperGoal((Mob)(var8_f2),((AbilityCore)(HiryuKaenAbility.INSTANCE.get())));
		};
			var5.addEntry(var9,4);
			NotoriousEntity var10 = var0;
			final NotoriousEntity var10_f3 = var10;
			Supplier<Goal> var11 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(var10_f3),((AbilityCore)(OTatsumakiAbility.INSTANCE.get())));
		};
			var5.addEntry(var11,3);
			NotoriousEntity var12 = var0;
			final NotoriousEntity var12_f4 = var12;
			Supplier<Goal> var13 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var12_f4),((AbilityCore)(SanbyakurokujuPoundHoAbility.INSTANCE.get())));
		};
			var5.addEntry(var13,3);
			NotoriousEntity var14 = var0;
			final NotoriousEntity var14_f5 = var14;
			Supplier<Goal> var15 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var14_f5),((AbilityCore)(YakkodoriAbility.INSTANCE.get())));
		};
			var5.addEntry(var15,3);
			Set var16 = var5.pickN(var0.trackedRandom,var4);
			NotoriousEntity var17 = var0;
			final NotoriousEntity var17_f6 = var17;
			Consumer var18 = (Consumer) ((Consumer<Supplier>) ((l0) -> {
			var17_f6.goalSelector.addGoal(3,((Goal)(l0.get())));
		}));
			var16.forEach(var18);
			return;
		}
	}
	private static void initBrawler(NotoriousEntity var0) {
		var0.getEntityStats().setFightingStyle(((FightingStyle)(ModFightingStyles.BRAWLER.get())));
		int var1 = (4) + (var0.trackedRandom.nextInt(2));
		var0.goalSelector.addGoal(0,(Goal)(new AlwaysActiveAbilityWrapperGoal((Mob)(var0),((AbilityCore)(BrawlerPassiveBonusesAbility.INSTANCE.get())))));
		WeightedList var2 = new WeightedList(new Object[0]);
		NotoriousEntity var3 = var0;
		final NotoriousEntity var3_f1 = var3;
		Supplier<Goal> var4 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var3_f1),((AbilityCore)(GenkotsuMeteorAbility.INSTANCE.get())));
		};
		var2.addEntry(var4,5);
		NotoriousEntity var5 = var0;
		final NotoriousEntity var5_f2 = var5;
		Supplier<Goal> var6 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(var5_f2),((AbilityCore)(ChargedPunchAbility.INSTANCE.get())));
		};
		var2.addEntry(var6,5);
		NotoriousEntity var7 = var0;
		final NotoriousEntity var7_f3 = var7;
		Supplier<Goal> var8 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(var7_f3),((AbilityCore)(HakaiHoAbility.INSTANCE.get())));
		};
		var2.addEntry(var8,4);
		NotoriousEntity var9 = var0;
		final NotoriousEntity var9_f4 = var9;
		Supplier<Goal> var10 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(var9_f4),((AbilityCore)(JishinHoAbility.INSTANCE.get())));
		};
		var2.addEntry(var10,4);
		NotoriousEntity var11 = var0;
		final NotoriousEntity var11_f5 = var11;
		Supplier<Goal> var12 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(var11_f5),((AbilityCore)(PunchRushAbility.INSTANCE.get())));
		};
		var2.addEntry(var12,3);
		NotoriousEntity var13 = var0;
		final NotoriousEntity var13_f6 = var13;
		Supplier<Goal> var14 = () -> {
			return new GrabAbilityWrapperGoal((Mob)(var13_f6),((AbilityCore)(SpinningBrawlAbility.INSTANCE.get())));
		};
		var2.addEntry(var14,2);
		NotoriousEntity var15 = var0;
		final NotoriousEntity var15_f7 = var15;
		Supplier<Goal> var16 = () -> {
			return new GrabAbilityWrapperGoal((Mob)(var15_f7),((AbilityCore)(SuplexAbility.INSTANCE.get())));
		};
		var2.addEntry(var16,2);
		Set var17 = var2.pickN(var0.trackedRandom,var1);
		NotoriousEntity var18 = var0;
		final NotoriousEntity var18_f8 = var18;
		Consumer var19 = (Consumer) ((Consumer<Supplier>) ((l0) -> {
			var18_f8.goalSelector.addGoal(3,((Goal)(l0.get())));
		}));
		var17.forEach(var19);
	}
	private static void initBlackLeg(NotoriousEntity var0) {
		var0.getEntityStats().setFightingStyle(((FightingStyle)(ModFightingStyles.BLACK_LEG.get())));
		int var1 = (4) + (var0.trackedRandom.nextInt(2));
		var0.goalSelector.addGoal(0,(Goal)(new AlwaysActiveAbilityWrapperGoal((Mob)(var0),((AbilityCore)(BlackLegPassiveBonusesAbility.INSTANCE.get())))));
		WeightedList var2 = new WeightedList(new Object[0]);
		NotoriousEntity var3 = var0;
		final NotoriousEntity var3_f1 = var3;
		Supplier<Goal> var4 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(var3_f1),((AbilityCore)(PartyTableKickCourseAbility.INSTANCE.get())));
		};
		var2.addEntry(var4,3);
		NotoriousEntity var5 = var0;
		final NotoriousEntity var5_f2 = var5;
		Supplier<Goal> var6 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(var5_f2),((AbilityCore)(AntiMannerKickCourseAbility.INSTANCE.get())));
		};
		var2.addEntry(var6,3);
		NotoriousEntity var7 = var0;
		final NotoriousEntity var7_f3 = var7;
		Supplier<Goal> var8 = () -> {
			return new JumpAbilityWrapperGoal((Mob)(var7_f3),((AbilityCore)(ConcasseAbility.INSTANCE.get())));
		};
		var2.addEntry(var8,3);
		NotoriousEntity var9 = var0;
		final NotoriousEntity var9_f4 = var9;
		Supplier<Goal> var10 = () -> {
			return new RepeaterAbilityWrapperGoal((Mob)(var9_f4),((AbilityCore)(ExtraHachisAbility.INSTANCE.get())));
		};
		var2.addEntry(var10,3);
		Set var11 = var2.pickN(var0.trackedRandom,var1);
		NotoriousEntity var12 = var0;
		final NotoriousEntity var12_f5 = var12;
		Consumer var13 = (Consumer) ((Consumer<Supplier>) ((l0) -> {
			var12_f5.goalSelector.addGoal(3,((Goal)(l0.get())));
		}));
		var11.forEach(var13);
	}
	private static void initSniper(NotoriousEntity var0) {
		var0.getEntityStats().setFightingStyle(((FightingStyle)(ModFightingStyles.SNIPER.get())));
		int var1 = (4) + (var0.trackedRandom.nextInt(2));
		var0.goalSelector.addGoal(0,(Goal)(new AlwaysActiveAbilityWrapperGoal((Mob)(var0),((AbilityCore)(BlackLegPassiveBonusesAbility.INSTANCE.get())))));
		WeightedList var2 = new WeightedList(new Object[0]);
		NotoriousEntity var3 = var0;
		final NotoriousEntity var3_f1 = var3;
		Supplier<Goal> var4 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var3_f1),((AbilityCore)(KaenBoshiAbility.INSTANCE.get())));
		};
		var2.addEntry(var4,3);
		NotoriousEntity var5 = var0;
		final NotoriousEntity var5_f2 = var5;
		Supplier<Goal> var6 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var5_f2),((AbilityCore)(SakuretsuSabotenBoshiAbility.INSTANCE.get())));
		};
		var2.addEntry(var6,3);
		NotoriousEntity var7 = var0;
		final NotoriousEntity var7_f3 = var7;
		Supplier<Goal> var8 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var7_f3),((AbilityCore)(TokuyoAburaBoshiAbility.INSTANCE.get())));
		};
		var2.addEntry(var8,3);
		NotoriousEntity var9 = var0;
		final NotoriousEntity var9_f4 = var9;
		Supplier<Goal> var10 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var9_f4),((AbilityCore)(TetsuBoshiAbility.INSTANCE.get())));
		};
		var2.addEntry(var10,2);
		NotoriousEntity var11 = var0;
		final NotoriousEntity var11_f5 = var11;
		Supplier<Goal> var12 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var11_f5),((AbilityCore)(RenpatsuNamariBoshiAbility.INSTANCE.get())));
		};
		var2.addEntry(var12,2);
		NotoriousEntity var13 = var0;
		final NotoriousEntity var13_f6 = var13;
		Supplier<Goal> var14 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var13_f6),((AbilityCore)(KemuriBoshiAbility.INSTANCE.get())));
		};
		var2.addEntry(var14,1);
		NotoriousEntity var15 = var0;
		final NotoriousEntity var15_f7 = var15;
		Supplier<Goal> var16 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var15_f7),((AbilityCore)(NemuriBoshiAbility.INSTANCE.get())));
		};
		var2.addEntry(var16,1);
		Set var17 = var2.pickN(var0.trackedRandom,var1);
		NotoriousEntity var18 = var0;
		final NotoriousEntity var18_f8 = var18;
		Consumer var19 = (Consumer) ((Consumer<Supplier>) ((l0) -> {
			var18_f8.goalSelector.addGoal(3,((Goal)(l0.get())));
		}));
		var17.forEach(var19);
	}
	private static void initHuman(NotoriousEntity var0) {
		var0.getEntityStats().setRace(((Race)(ModRaces.HUMAN.get())));
		int var1 = (3) + ((var0.isAboveNormalDifficulty()) ? (2) : (0));
		WeightedList var2 = new WeightedList(new Object[0]);
		NotoriousEntity var3 = var0;
		final NotoriousEntity var3_f1 = var3;
		Supplier<Goal> var4 = () -> {
			return new DashAbilityWrapperGoal((Mob)(var3_f1),((AbilityCore)(SoruAbility.INSTANCE.get())));
		};
		var2.addEntry(var4,5);
		NotoriousEntity var5 = var0;
		final NotoriousEntity var5_f2 = var5;
		Supplier<Goal> var6 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(var5_f2),((AbilityCore)(ShiganAbility.INSTANCE.get())));
		};
		var2.addEntry(var6,5);
		NotoriousEntity var7 = var0;
		final NotoriousEntity var7_f3 = var7;
		Supplier<Goal> var8 = () -> {
			return new ReactiveGuardAbilityWrapperGoal((Mob)(var7_f3),((AbilityCore)(TekkaiAbility.INSTANCE.get())));
		};
		var2.addEntry(var8,5);
		NotoriousEntity var9 = var0;
		final NotoriousEntity var9_f4 = var9;
		Supplier<Goal> var10 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var9_f4),((AbilityCore)(RankyakuAbility.INSTANCE.get())));
		};
		var2.addEntry(var10,4);
		NotoriousEntity var11 = var0;
		final NotoriousEntity var11_f5 = var11;
		Supplier<Goal> var12 = () -> {
			return new ReactiveRetreatAbilityWrapperGoal((Mob)(var11_f5),((AbilityCore)(GeppoAbility.INSTANCE.get())));
		};
		var2.addEntry(var12,4);
		NotoriousEntity var13 = var0;
		final NotoriousEntity var13_f6 = var13;
		Supplier<Goal> var14 = () -> {
			return new ReactiveGuardAbilityWrapperGoal((Mob)(var13_f6),((AbilityCore)(KamieAbility.INSTANCE.get())));
		};
		var2.addEntry(var14,4);
		Set var15 = var2.pickN(var0.trackedRandom,var1);
		NotoriousEntity var16 = var0;
		final NotoriousEntity var16_f7 = var16;
		Consumer var17 = (Consumer) ((Consumer<Supplier>) ((l0) -> {
			var16_f7.goalSelector.addGoal(3,((Goal)(l0.get())));
		}));
		var15.forEach(var17);
	}
	private static void initFishman(NotoriousEntity var0) {
		var0.getEntityStats().setRace(((Race)(ModRaces.FISHMAN.get())));
		int var1 = (3) + ((var0.isAboveNormalDifficulty()) ? (2) : (0));
		var0.goalSelector.addGoal(0,(Goal)(new AlwaysActiveAbilityWrapperGoal((Mob)(var0),((AbilityCore)(FishmanPassiveBonusesAbility.INSTANCE.get())))));
		WeightedList var2 = new WeightedList(new Object[0]);
		NotoriousEntity var3 = var0;
		final NotoriousEntity var3_f1 = var3;
		Supplier<Goal> var4 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(var3_f1),((AbilityCore)(KarakusagawaraSeikenAbility.INSTANCE.get())));
		};
		var2.addEntry(var4,5);
		NotoriousEntity var5 = var0;
		final NotoriousEntity var5_f2 = var5;
		Supplier<Goal> var6 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(var5_f2),((AbilityCore)(KachiageHaisokuAbility.INSTANCE.get())));
		};
		var2.addEntry(var6,5);
		NotoriousEntity var7 = var0;
		final NotoriousEntity var7_f3 = var7;
		Supplier<Goal> var8 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var7_f3),((AbilityCore)(PackOfSharksAbility.INSTANCE.get())));
		};
		var2.addEntry(var8,5);
		NotoriousEntity var9 = var0;
		final NotoriousEntity var9_f4 = var9;
		Supplier<Goal> var10 = () -> {
			return new ReactiveGuardAbilityWrapperGoal((Mob)(var9_f4),((AbilityCore)(MizuOsuAbility.INSTANCE.get())));
		};
		var2.addEntry(var10,3);
		NotoriousEntity var11 = var0;
		final NotoriousEntity var11_f5 = var11;
		Supplier<Goal> var12 = () -> {
			return new ReactiveGuardAbilityWrapperGoal((Mob)(var11_f5),((AbilityCore)(SamehadaShoteiAbility.INSTANCE.get())));
		};
		var2.addEntry(var12,5);
		NotoriousEntity var13 = var0;
		final NotoriousEntity var13_f6 = var13;
		Supplier<Goal> var14 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var13_f6),((AbilityCore)(UchimizuAbility.INSTANCE.get())));
		};
		var2.addEntry(var14,5);
		NotoriousEntity var15 = var0;
		final NotoriousEntity var15_f7 = var15;
		Supplier<Goal> var16 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(var15_f7),((AbilityCore)(MizuShuryudanAbility.INSTANCE.get())));
		};
		var2.addEntry(var16,3);
		Set var17 = var2.pickN(var0.trackedRandom,var1);
		NotoriousEntity var18 = var0;
		final NotoriousEntity var18_f8 = var18;
		Consumer var19 = (Consumer) ((Consumer<Supplier>) ((l0) -> {
			var18_f8.goalSelector.addGoal(3,((Goal)(l0.get())));
		}));
		var17.forEach(var19);
	}
	public static void initMarine(NotoriousEntity var0) {
		var0.getEntityStats().setFaction(((Faction)(ModFactions.MARINE.get())));
		var0.setTextures(MobsHelper.MARINE_VICE_ADMIRAL_TEXTURES);
		ItemStack var1 = new ItemStack(((ItemLike)(ModArmors.MARINE_CAPTAIN_CAPE.get())));
		int[] var2 = new int[]{33980};
		var1.set(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(var2));
		var0.setItemSlot(EquipmentSlot.CHEST,var1);
	}
	public static void initPirate(NotoriousEntity var0) {
		var0.getEntityStats().setFaction(((Faction)(ModFactions.PIRATE.get())));
		var0.setTextures(MobsHelper.PIRATE_CAPTAINS_TEXTURES);
		if ((var0.trackedRandom.nextFloat()) < (0.4F)){
			ItemStack var1 = new ItemStack(((ItemLike)(ModArmors.PIRATE_CAPTAIN_CAPE.get())));
			var0.setItemSlot(EquipmentSlot.CHEST,var1);
		}
		FactionsWorldData.get().addTemporaryCrewMember(ModNPCGroups.WORLD_PIRATES,(LivingEntity)(var0));
	}
	private static void initBandit(NotoriousEntity var0) {
		var0.getEntityStats().setFaction(((Faction)(ModFactions.BANDIT.get())));
		var0.setTextures(MobsHelper.BANDIT_TEXTURES);
	}
	public RandomSource getTrackedRandom() {
		return this.trackedRandom;
	}
	@Nullable()
	public TrackedNPC getTrackedData() {
		return this.trackedData;
	}
	static  {
		IMobDetails[] var0 = new IMobDetails[4];
		IMobDetails var1 = NotoriousEntity::initSwordsman;
		var0[0] = var1;
		IMobDetails var2 = NotoriousEntity::initBrawler;
		var0[1] = var2;
		IMobDetails var3 = NotoriousEntity::initBlackLeg;
		var0[2] = var3;
		IMobDetails var4 = NotoriousEntity::initSniper;
		var0[3] = var4;
		STYLES = Lists.newArrayList(var0);
		IMobDetails[] var5 = new IMobDetails[2];
		IMobDetails var6 = NotoriousEntity::initHuman;
		var5[0] = var6;
		IMobDetails var7 = NotoriousEntity::initFishman;
		var5[1] = var7;
		RACES = Lists.newArrayList(var5);
		MELEE_FACTION_WEAPONS = createFactionWeaponsMap();
		String[] var8 = new String[]{"Allison","Bellamy","Benito","Barrow","Caesar","Bonnet","Burke","Chivers","Coward","Derdrake","Frowd","Hands","Julian","Low","Lyne","Pargo","Roberts","Almeida","Shih","Abbas","Willems","Veale","Lewis","Hamlin","Andreson","Wittebol","Worst"};
		PIRATE_NAMES = var8;
		String[] var9 = new String[]{"Tyrant","Sir","Don","Iron Hand","Boss","Commander","Black Arm","Mountain","Red-Eye","Dead-Eye","Savage","Captain","Silver Blade","Black Beard","Mountain Slasher","General","Commodore","Sharp Fang","Dreadful"};
		PIRATE_PRE_TITLES = var9;
		String[] var10 = new String[]{"the Hyena","the Beheader","the Cannibal","the Mad","the Mighty","the Superior","the Lonely","the Sea Weasel","the Commander","the Merciless","the Wicked","the Usurper","the Mercenary","the Great","the Undying","of the North Blue","of the South Blue","of the East Blue","of the West Blue","the Sea King","the Burglar","the Devil","the Devil Child","the Gentleman Pirate","the Surgeon of Death","the Massacre Soldier"};
		PIRATE_POST_TITLES = var10;
		String[] var11 = new String[]{"Golden","Goffe","Griffin","Hoar","John","Liddell","Penniston","Aregnaudeau","Jennings","Enriquez","Day","Crapo","Burches","Barnet","Ashworth","Wright","Woolerly","Ras","Pardal","Paine","Morgan","Mansfield","Henley","Essex","Browne","Bequel"};
		MARINE_NAMES = var11;
	}
}