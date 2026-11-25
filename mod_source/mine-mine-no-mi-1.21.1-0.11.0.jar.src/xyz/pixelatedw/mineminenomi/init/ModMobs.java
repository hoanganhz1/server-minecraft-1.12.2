package xyz.pixelatedw.mineminenomi.init;

import java.util.function.Supplier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.config.WorldEventsConfig;
import xyz.pixelatedw.mineminenomi.entities.BottomHalfBodyEntity;
import xyz.pixelatedw.mineminenomi.entities.PhysicalBodyEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.BarkeeperEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.BruteEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.CaptainEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.GruntEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.NotoriousEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.PacifistaEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.SniperEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.TraderEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.BlackKnightEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.DoppelmanEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.MirageCloneEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.NightmareSoldierEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.WaxCloneEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractDugongEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BananawaniEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BigDuckEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BlagoriEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BlugoriEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BoxingDugongEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.DenDenMushiEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.FightingFishEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.FlyingFishEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.HumandrillEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.KungFuDugongEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.LapahnEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.LegendaryMasterDugongEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.PandaSharkEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.SeaCowEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WanderingDugongEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WhiteWalkieEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WrestlingDugongEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.YagaraBullEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.bandits.HigumaEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.ArtOfWeatherTrainerEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.BlackLegTrainerEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.BrawlerTrainerEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.DoctorTrainerEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.SkypieanCivilianEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.SkypieanTraderEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.SniperTrainerEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.SwordsmanTrainerEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.marines.MarineTraderEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.marines.MorganEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.PirateTraderEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.arlongpirates.ArlongEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.arlongpirates.ChewEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.arlongpirates.KuroobiEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.MissValentineEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr0Entity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr1Entity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr3Entity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr5Entity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.blackcatpirates.BuchiEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.blackcatpirates.JangoEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.blackcatpirates.KuroEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.blackcatpirates.ShamEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates.AlvidaEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates.BuggyEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates.CabajiEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.kriegpirates.DonKriegEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.kriegpirates.GinEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.kriegpirates.PearlEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.worldgov.CelestialDragonEntity;

public class ModMobs {
	// Fields
	public static final EnumProxy<MobCategory> MARINES = new EnumProxy(MobCategory.class,new Object[]{"mineminenomi:marines",Integer.valueOf(5),Boolean.valueOf(false),Boolean.valueOf(false),Integer.valueOf(128)});
	public static final EnumProxy<MobCategory> PIRATES = new EnumProxy(MobCategory.class,new Object[]{"mineminenomi:pirates",Integer.valueOf(5),Boolean.valueOf(false),Boolean.valueOf(false),Integer.valueOf(128)});
	public static final EnumProxy<MobCategory> BANDITS = new EnumProxy(MobCategory.class,new Object[]{"mineminenomi:bandits",Integer.valueOf(5),Boolean.valueOf(false),Boolean.valueOf(false),Integer.valueOf(128)});
	public static final DeferredHolder<EntityType<?>, EntityType<GruntEntity>> MARINE_GRUNT;
	public static final DeferredHolder<EntityType<?>, EntityType<BruteEntity>> MARINE_BRUTE;
	public static final DeferredHolder<EntityType<?>, EntityType<SniperEntity>> MARINE_SNIPER;
	public static final DeferredHolder<EntityType<?>, EntityType<CaptainEntity>> MARINE_CAPTAIN;
	public static final DeferredHolder<EntityType<?>, EntityType<MarineTraderEntity>> MARINE_TRADER;
	public static final DeferredHolder<EntityType<?>, EntityType<PacifistaEntity>> PACIFISTA;
	public static final DeferredHolder<EntityType<?>, EntityType<NotoriousEntity>> MARINE_VICE_ADMIRAL;
	public static final DeferredHolder<EntityType<?>, EntityType<MorganEntity>> MORGAN;
	public static final DeferredHolder<EntityType<?>, EntityType<CelestialDragonEntity>> CELESTIAL_DRAGON;
	public static final DeferredHolder<EntityType<?>, EntityType<GruntEntity>> PIRATE_GRUNT;
	public static final DeferredHolder<EntityType<?>, EntityType<BruteEntity>> PIRATE_BRUTE;
	public static final DeferredHolder<EntityType<?>, EntityType<CaptainEntity>> PIRATE_CAPTAIN;
	public static final DeferredHolder<EntityType<?>, EntityType<PirateTraderEntity>> PIRATE_TRADER;
	public static final DeferredHolder<EntityType<?>, EntityType<NotoriousEntity>> PIRATE_NOTORIOUS_CAPTAIN;
	public static final DeferredHolder<EntityType<?>, EntityType<BuggyEntity>> BUGGY;
	public static final DeferredHolder<EntityType<?>, EntityType<AlvidaEntity>> ALVIDA;
	public static final DeferredHolder<EntityType<?>, EntityType<AlvidaEntity>> ALVIDA_SLIM;
	public static final DeferredHolder<EntityType<?>, EntityType<CabajiEntity>> CABAJI;
	public static final DeferredHolder<EntityType<?>, EntityType<ShamEntity>> SHAM;
	public static final DeferredHolder<EntityType<?>, EntityType<BuchiEntity>> BUCHI;
	public static final DeferredHolder<EntityType<?>, EntityType<JangoEntity>> JANGO;
	public static final DeferredHolder<EntityType<?>, EntityType<KuroEntity>> KURO;
	public static final DeferredHolder<EntityType<?>, EntityType<GinEntity>> GIN;
	public static final DeferredHolder<EntityType<?>, EntityType<PearlEntity>> PEARL;
	public static final DeferredHolder<EntityType<?>, EntityType<DonKriegEntity>> DON_KRIEG;
	public static final DeferredHolder<EntityType<?>, EntityType<ChewEntity>> CHEW;
	public static final DeferredHolder<EntityType<?>, EntityType<KuroobiEntity>> KUROOBI;
	public static final DeferredHolder<EntityType<?>, EntityType<ArlongEntity>> ARLONG;
	public static final DeferredHolder<EntityType<?>, EntityType<Mr5Entity>> MR5;
	public static final DeferredHolder<EntityType<?>, EntityType<MissValentineEntity>> MISS_VALENTINE;
	public static final DeferredHolder<EntityType<?>, EntityType<Mr3Entity>> MR3;
	public static final DeferredHolder<EntityType<?>, EntityType<Mr1Entity>> MR1;
	public static final DeferredHolder<EntityType<?>, EntityType<Mr0Entity>> MR0;
	public static final DeferredHolder<EntityType<?>, EntityType<GruntEntity>> BANDIT_GRUNT;
	public static final DeferredHolder<EntityType<?>, EntityType<BruteEntity>> BANDIT_BRUTE;
	public static final DeferredHolder<EntityType<?>, EntityType<SniperEntity>> BANDIT_SNIPER;
	public static final DeferredHolder<EntityType<?>, EntityType<CaptainEntity>> BANDIT_CAPTAIN;
	public static final DeferredHolder<EntityType<?>, EntityType<HigumaEntity>> HIGUMA;
	public static final DeferredHolder<EntityType<?>, EntityType<SwordsmanTrainerEntity>> SWORDSMAN_TRAINER;
	public static final DeferredHolder<EntityType<?>, EntityType<SniperTrainerEntity>> SNIPER_TRAINER;
	public static final DeferredHolder<EntityType<?>, EntityType<ArtOfWeatherTrainerEntity>> ART_OF_WEATHER_TRAINER;
	public static final DeferredHolder<EntityType<?>, EntityType<DoctorTrainerEntity>> DOCTOR_TRAINER;
	public static final DeferredHolder<EntityType<?>, EntityType<BrawlerTrainerEntity>> BRAWLER_TRAINER;
	public static final DeferredHolder<EntityType<?>, EntityType<BlackLegTrainerEntity>> BLACK_LEG_TRAINER;
	public static final DeferredHolder<EntityType<?>, EntityType<SkypieanCivilianEntity>> SKYPIEAN_CIVILIAN;
	public static final DeferredHolder<EntityType<?>, EntityType<SkypieanTraderEntity>> SKYPIEAN_TRADER;
	public static final DeferredHolder<EntityType<?>, EntityType<BarkeeperEntity>> BARKEEPER;
	public static final DeferredHolder<EntityType<?>, EntityType<DenDenMushiEntity>> DEN_DEN_MUSHI;
	public static final DeferredHolder<EntityType<?>, EntityType<LapahnEntity>> LAPAHN;
	public static final DeferredHolder<EntityType<?>, EntityType<YagaraBullEntity>> YAGARA_BULL;
	public static final DeferredHolder<EntityType<?>, EntityType<HumandrillEntity>> HUMANDRILL;
	public static final DeferredHolder<EntityType<?>, EntityType<FightingFishEntity>> FIGHTING_FISH;
	public static final DeferredHolder<EntityType<?>, EntityType<BananawaniEntity>> BANANAWANI;
	public static final DeferredHolder<EntityType<?>, EntityType<BigDuckEntity>> BIG_DUCK;
	public static final DeferredHolder<EntityType<?>, EntityType<SeaCowEntity>> SEA_COW;
	public static final DeferredHolder<EntityType<?>, EntityType<WhiteWalkieEntity>> WHITE_WALKIE;
	public static final DeferredHolder<EntityType<?>, EntityType<PandaSharkEntity>> PANDA_SHARK;
	public static final DeferredHolder<EntityType<?>, EntityType<FlyingFishEntity>> FLYING_FISH;
	public static final DeferredHolder<EntityType<?>, EntityType<BlugoriEntity>> BLUGORI;
	public static final DeferredHolder<EntityType<?>, EntityType<BlagoriEntity>> BLAGORI;
	public static final DeferredHolder<EntityType<?>, EntityType<KungFuDugongEntity>> KUNG_FU_DUGONG;
	public static final DeferredHolder<EntityType<?>, EntityType<WrestlingDugongEntity>> WRESTLING_DUGONG;
	public static final DeferredHolder<EntityType<?>, EntityType<BoxingDugongEntity>> BOXING_DUGONG;
	public static final DeferredHolder<EntityType<?>, EntityType<LegendaryMasterDugongEntity>> LEGENDARY_MASTER_DUGONG;
	public static final DeferredHolder<EntityType<?>, EntityType<WanderingDugongEntity>> WANDERING_DUGONG;
	public static final DeferredHolder<EntityType<?>, EntityType<MirageCloneEntity>> MIRAGE_CLONE;
	public static final DeferredHolder<EntityType<?>, EntityType<WaxCloneEntity>> WAX_CLONE;
	public static final DeferredHolder<EntityType<?>, EntityType<BlackKnightEntity>> BLACK_KNIGHT;
	public static final DeferredHolder<EntityType<?>, EntityType<DoppelmanEntity>> DOPPELMAN;
	public static final DeferredHolder<EntityType<?>, EntityType<NightmareSoldierEntity>> NIGHTMARE_SOLDIER;
	public static final DeferredHolder<EntityType<?>, EntityType<PhysicalBodyEntity>> PHYSICAL_BODY;
	public static final DeferredHolder<EntityType<?>, EntityType<PhysicalBodyEntity>> SKELLY_BODY;
	public static final DeferredHolder<EntityType<?>, EntityType<BottomHalfBodyEntity>> BOTTOM_HALF;
	public static void init() {
	}
	public static void createAttributes(EntityAttributeCreationEvent var0) {
		var0.put(((EntityType)(MARINE_GRUNT.get())),GruntEntity.createAttributes().build());
		var0.put(((EntityType)(MARINE_BRUTE.get())),BruteEntity.createAttributes().build());
		var0.put(((EntityType)(MARINE_CAPTAIN.get())),CaptainEntity.createAttributes().build());
		var0.put(((EntityType)(MARINE_SNIPER.get())),SniperEntity.createAttributes().build());
		var0.put(((EntityType)(MARINE_TRADER.get())),TraderEntity.createAttributes().build());
		var0.put(((EntityType)(PACIFISTA.get())),PacifistaEntity.createAttributes().build());
		var0.put(((EntityType)(MARINE_VICE_ADMIRAL.get())),NotoriousEntity.createAttributes().build());
		var0.put(((EntityType)(MORGAN.get())),MorganEntity.createAttributes().build());
		var0.put(((EntityType)(CELESTIAL_DRAGON.get())),CelestialDragonEntity.createAttributes().build());
		var0.put(((EntityType)(PIRATE_GRUNT.get())),GruntEntity.createAttributes().build());
		var0.put(((EntityType)(PIRATE_BRUTE.get())),BruteEntity.createAttributes().build());
		var0.put(((EntityType)(PIRATE_CAPTAIN.get())),CaptainEntity.createAttributes().build());
		var0.put(((EntityType)(PIRATE_TRADER.get())),TraderEntity.createAttributes().build());
		var0.put(((EntityType)(PIRATE_NOTORIOUS_CAPTAIN.get())),NotoriousEntity.createAttributes().build());
		var0.put(((EntityType)(BUGGY.get())),BuggyEntity.createAttributes().build());
		var0.put(((EntityType)(ALVIDA.get())),AlvidaEntity.createAttributes().build());
		var0.put(((EntityType)(ALVIDA_SLIM.get())),AlvidaEntity.createAttributes().build());
		var0.put(((EntityType)(CABAJI.get())),CabajiEntity.createAttributes().build());
		var0.put(((EntityType)(SHAM.get())),ShamEntity.createAttributes().build());
		var0.put(((EntityType)(BUCHI.get())),BuchiEntity.createAttributes().build());
		var0.put(((EntityType)(JANGO.get())),JangoEntity.createAttributes().build());
		var0.put(((EntityType)(KURO.get())),KuroEntity.createAttributes().build());
		var0.put(((EntityType)(GIN.get())),GinEntity.createAttributes().build());
		var0.put(((EntityType)(PEARL.get())),PearlEntity.createAttributes().build());
		var0.put(((EntityType)(DON_KRIEG.get())),DonKriegEntity.createAttributes().build());
		var0.put(((EntityType)(CHEW.get())),ChewEntity.createAttributes().build());
		var0.put(((EntityType)(KUROOBI.get())),KuroobiEntity.createAttributes().build());
		var0.put(((EntityType)(ARLONG.get())),ArlongEntity.createAttributes().build());
		var0.put(((EntityType)(MR5.get())),Mr5Entity.createAttributes().build());
		var0.put(((EntityType)(MISS_VALENTINE.get())),MissValentineEntity.createAttributes().build());
		var0.put(((EntityType)(MR3.get())),Mr3Entity.createAttributes().build());
		var0.put(((EntityType)(MR1.get())),Mr1Entity.createAttributes().build());
		var0.put(((EntityType)(MR0.get())),Mr0Entity.createAttributes().build());
		var0.put(((EntityType)(BANDIT_GRUNT.get())),GruntEntity.createAttributes().build());
		var0.put(((EntityType)(BANDIT_BRUTE.get())),BruteEntity.createAttributes().build());
		var0.put(((EntityType)(BANDIT_SNIPER.get())),SniperEntity.createAttributes().build());
		var0.put(((EntityType)(BANDIT_CAPTAIN.get())),CaptainEntity.createAttributes().build());
		var0.put(((EntityType)(HIGUMA.get())),HigumaEntity.createAttributes().build());
		var0.put(((EntityType)(SWORDSMAN_TRAINER.get())),SwordsmanTrainerEntity.createAttributes().build());
		var0.put(((EntityType)(SNIPER_TRAINER.get())),SniperTrainerEntity.createAttributes().build());
		var0.put(((EntityType)(DOCTOR_TRAINER.get())),DoctorTrainerEntity.createAttributes().build());
		var0.put(((EntityType)(ART_OF_WEATHER_TRAINER.get())),ArtOfWeatherTrainerEntity.createAttributes().build());
		var0.put(((EntityType)(BRAWLER_TRAINER.get())),BrawlerTrainerEntity.createAttributes().build());
		var0.put(((EntityType)(BLACK_LEG_TRAINER.get())),BlackLegTrainerEntity.createAttributes().build());
		var0.put(((EntityType)(SKYPIEAN_CIVILIAN.get())),SkypieanCivilianEntity.createAttributes().build());
		var0.put(((EntityType)(SKYPIEAN_TRADER.get())),TraderEntity.createAttributes().build());
		var0.put(((EntityType)(BARKEEPER.get())),BarkeeperEntity.createAttributes().build());
		var0.put(((EntityType)(DEN_DEN_MUSHI.get())),DenDenMushiEntity.createAttributes().build());
		var0.put(((EntityType)(LAPAHN.get())),LapahnEntity.createAttributes().build());
		var0.put(((EntityType)(YAGARA_BULL.get())),YagaraBullEntity.createAttributes().build());
		var0.put(((EntityType)(HUMANDRILL.get())),HumandrillEntity.createAttributes().build());
		var0.put(((EntityType)(FIGHTING_FISH.get())),FightingFishEntity.createAttributes().build());
		var0.put(((EntityType)(BANANAWANI.get())),BananawaniEntity.createAttributes().build());
		var0.put(((EntityType)(BIG_DUCK.get())),BigDuckEntity.createAttributes().build());
		var0.put(((EntityType)(SEA_COW.get())),SeaCowEntity.createAttributes().build());
		var0.put(((EntityType)(WHITE_WALKIE.get())),WhiteWalkieEntity.createAttributes().build());
		var0.put(((EntityType)(PANDA_SHARK.get())),PandaSharkEntity.createAttributes().build());
		var0.put(((EntityType)(FLYING_FISH.get())),FlyingFishEntity.createAttributes().build());
		var0.put(((EntityType)(BLUGORI.get())),BlugoriEntity.createAttributes().build());
		var0.put(((EntityType)(BLAGORI.get())),BlagoriEntity.createAttributes().build());
		var0.put(((EntityType)(KUNG_FU_DUGONG.get())),AbstractDugongEntity.createAttributes().build());
		var0.put(((EntityType)(WRESTLING_DUGONG.get())),WrestlingDugongEntity.createAttributes().build());
		var0.put(((EntityType)(BOXING_DUGONG.get())),BoxingDugongEntity.createAttributes().build());
		var0.put(((EntityType)(LEGENDARY_MASTER_DUGONG.get())),LegendaryMasterDugongEntity.createAttributes().build());
		var0.put(((EntityType)(WANDERING_DUGONG.get())),WanderingDugongEntity.createAttributes().build());
		var0.put(((EntityType)(MIRAGE_CLONE.get())),MirageCloneEntity.createAttributes().build());
		var0.put(((EntityType)(WAX_CLONE.get())),WaxCloneEntity.createAttributes().build());
		var0.put(((EntityType)(PHYSICAL_BODY.get())),PhysicalBodyEntity.createAttributes().build());
		var0.put(((EntityType)(SKELLY_BODY.get())),PhysicalBodyEntity.createAttributes().build());
		var0.put(((EntityType)(BOTTOM_HALF.get())),BottomHalfBodyEntity.createAttributes().build());
		var0.put(((EntityType)(BLACK_KNIGHT.get())),BlackKnightEntity.createAttributes().build());
		var0.put(((EntityType)(DOPPELMAN.get())),DoppelmanEntity.createAttributes().build());
		var0.put(((EntityType)(NIGHTMARE_SOLDIER.get())),NightmareSoldierEntity.createAttributes().build());
	}
	public static void setupSpawnPlacement(RegisterSpawnPlacementsEvent var0) {
		if (((Boolean)(WorldEventsConfig.SPAWN_WORLD_HUMANOIDS.get())).booleanValue()){
			EntityType var1 = ((EntityType)(MARINE_GRUNT.get()));
			SpawnPlacements.SpawnPredicate var2 = GruntEntity::checkSpawnRules;
			var0.register(var1,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var2,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var3 = ((EntityType)(MARINE_BRUTE.get()));
			SpawnPlacements.SpawnPredicate var4 = BruteEntity::checkSpawnRules;
			var0.register(var3,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var4,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var5 = ((EntityType)(MARINE_SNIPER.get()));
			SpawnPlacements.SpawnPredicate var6 = SniperEntity::checkSpawnRules;
			var0.register(var5,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var6,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var7 = ((EntityType)(MARINE_CAPTAIN.get()));
			SpawnPlacements.SpawnPredicate var8 = CaptainEntity::checkSpawnRules;
			var0.register(var7,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var8,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var9 = ((EntityType)(MARINE_TRADER.get()));
			SpawnPlacements.SpawnPredicate var10 = OPEntity::checkSpawnRules;
			var0.register(var9,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var10,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var11 = ((EntityType)(PACIFISTA.get()));
			SpawnPlacements.SpawnPredicate var12 = PacifistaEntity::checkSpawnRules;
			var0.register(var11,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var12,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var13 = ((EntityType)(MARINE_VICE_ADMIRAL.get()));
			SpawnPlacements.SpawnPredicate var14 = OPEntity::checkSpawnRules;
			var0.register(var13,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var14,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var15 = ((EntityType)(PIRATE_GRUNT.get()));
			SpawnPlacements.SpawnPredicate var16 = GruntEntity::checkSpawnRules;
			var0.register(var15,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var16,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var17 = ((EntityType)(PIRATE_BRUTE.get()));
			SpawnPlacements.SpawnPredicate var18 = BruteEntity::checkSpawnRules;
			var0.register(var17,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var18,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var19 = ((EntityType)(PIRATE_CAPTAIN.get()));
			SpawnPlacements.SpawnPredicate var20 = CaptainEntity::checkSpawnRules;
			var0.register(var19,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var20,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var21 = ((EntityType)(PIRATE_TRADER.get()));
			SpawnPlacements.SpawnPredicate var22 = OPEntity::checkSpawnRules;
			var0.register(var21,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var22,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var23 = ((EntityType)(PIRATE_NOTORIOUS_CAPTAIN.get()));
			SpawnPlacements.SpawnPredicate var24 = OPEntity::checkSpawnRules;
			var0.register(var23,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var24,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var25 = ((EntityType)(BANDIT_GRUNT.get()));
			SpawnPlacements.SpawnPredicate var26 = GruntEntity::checkSpawnRules;
			var0.register(var25,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var26,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var27 = ((EntityType)(BANDIT_BRUTE.get()));
			SpawnPlacements.SpawnPredicate var28 = BruteEntity::checkSpawnRules;
			var0.register(var27,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var28,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var29 = ((EntityType)(BANDIT_SNIPER.get()));
			SpawnPlacements.SpawnPredicate var30 = SniperEntity::checkSpawnRules;
			var0.register(var29,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var30,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var31 = ((EntityType)(BANDIT_CAPTAIN.get()));
			SpawnPlacements.SpawnPredicate var32 = CaptainEntity::checkSpawnRules;
			var0.register(var31,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var32,RegisterSpawnPlacementsEvent.Operation.REPLACE);
		}
		if (((Boolean)(WorldEventsConfig.SPAWN_WORLD_ANIMALS.get())).booleanValue()){
			EntityType var1_1 = ((EntityType)(DEN_DEN_MUSHI.get()));
			SpawnPlacements.SpawnPredicate var2_1 = Animal::checkAnimalSpawnRules;
			var0.register(var1_1,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var2_1,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var3_1 = ((EntityType)(LAPAHN.get()));
			SpawnPlacements.SpawnPredicate var4_1 = Mob::checkMobSpawnRules;
			var0.register(var3_1,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var4_1,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var5_1 = ((EntityType)(YAGARA_BULL.get()));
			SpawnPlacements.SpawnPredicate var6_1 = YagaraBullEntity::checkSpawnRules;
			var0.register(var5_1,SpawnPlacementTypes.NO_RESTRICTIONS,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var6_1,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var7_1 = ((EntityType)(HUMANDRILL.get()));
			SpawnPlacements.SpawnPredicate var8_1 = Mob::checkMobSpawnRules;
			var0.register(var7_1,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var8_1,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			SpawnPlacementType var9_1 = (WyHelper.isAprilFirst()) ? (SpawnPlacementTypes.NO_RESTRICTIONS) : (SpawnPlacementTypes.IN_WATER);
			EntityType var10_1 = ((EntityType)(FIGHTING_FISH.get()));
			SpawnPlacements.SpawnPredicate var11_1 = FightingFishEntity::checkSpawnRules;
			var0.register(var10_1,var9_1,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var11_1,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var12_1 = ((EntityType)(BANANAWANI.get()));
			SpawnPlacements.SpawnPredicate var13_1 = Mob::checkMobSpawnRules;
			var0.register(var12_1,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var13_1,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var14_1 = ((EntityType)(BIG_DUCK.get()));
			SpawnPlacements.SpawnPredicate var15_1 = Mob::checkMobSpawnRules;
			var0.register(var14_1,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var15_1,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var16_1 = ((EntityType)(WHITE_WALKIE.get()));
			SpawnPlacements.SpawnPredicate var17_1 = Mob::checkMobSpawnRules;
			var0.register(var16_1,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var17_1,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var18_1 = ((EntityType)(PANDA_SHARK.get()));
			SpawnPlacements.SpawnPredicate var19_1 = PandaSharkEntity::checkSpawnRules;
			var0.register(var18_1,SpawnPlacementTypes.IN_WATER,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var19_1,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var20_1 = ((EntityType)(FLYING_FISH.get()));
			SpawnPlacements.SpawnPredicate var21_1 = FlyingFishEntity::checkSpawnRules;
			var0.register(var20_1,SpawnPlacementTypes.IN_WATER,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var21_1,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var22_1 = ((EntityType)(SEA_COW.get()));
			SpawnPlacements.SpawnPredicate var23_1 = SeaCowEntity::checkSpawnRules;
			var0.register(var22_1,SpawnPlacementTypes.IN_WATER,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var23_1,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var24_1 = ((EntityType)(BLUGORI.get()));
			SpawnPlacements.SpawnPredicate var25_1 = Mob::checkMobSpawnRules;
			var0.register(var24_1,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var25_1,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var26_1 = ((EntityType)(BLAGORI.get()));
			SpawnPlacements.SpawnPredicate var27_1 = Mob::checkMobSpawnRules;
			var0.register(var26_1,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var27_1,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var28_1 = ((EntityType)(KUNG_FU_DUGONG.get()));
			SpawnPlacements.SpawnPredicate var29_1 = Mob::checkMobSpawnRules;
			var0.register(var28_1,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var29_1,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var30_1 = ((EntityType)(WRESTLING_DUGONG.get()));
			SpawnPlacements.SpawnPredicate var31_1 = Mob::checkMobSpawnRules;
			var0.register(var30_1,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var31_1,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var32_1 = ((EntityType)(BOXING_DUGONG.get()));
			SpawnPlacements.SpawnPredicate var33 = Mob::checkMobSpawnRules;
			var0.register(var32_1,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var33,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var34 = ((EntityType)(LEGENDARY_MASTER_DUGONG.get()));
			SpawnPlacements.SpawnPredicate var35 = Mob::checkMobSpawnRules;
			var0.register(var34,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var35,RegisterSpawnPlacementsEvent.Operation.REPLACE);
			EntityType var36 = ((EntityType)(WANDERING_DUGONG.get()));
			SpawnPlacements.SpawnPredicate var37 = WanderingDugongEntity::checkSpawnRules;
			var0.register(var36,SpawnPlacementTypes.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var37,RegisterSpawnPlacementsEvent.Operation.REPLACE);
		}
	}
	static  {
		Supplier<EntityType> var0 = () -> {
			EntityType.EntityFactory lv8_0 = GruntEntity::createMarineGrunt;
			return ModRegistry.createMobType(lv8_0,MARINES).build("mineminenomi:marine_grunt");
		};
		MARINE_GRUNT = (DeferredHolder) (registerMarineWithSpawnEgg("Marine Grunt",var0));
		Supplier<EntityType> var1 = () -> {
			EntityType.EntityFactory lv9_0 = BruteEntity::createMarineBrute;
			return ModRegistry.createMobType(lv9_0,MARINES).sized(0.8F,2.3F).build("mineminenomi:marine_brute");
		};
		MARINE_BRUTE = (DeferredHolder) (registerMarineWithSpawnEgg("Marine Brute",var1));
		Supplier<EntityType> var2 = () -> {
			EntityType.EntityFactory lv10_0 = SniperEntity::createMarineSniper;
			return ModRegistry.createMobType(lv10_0,MARINES).build("mineminenomi:marine_sniper");
		};
		MARINE_SNIPER = (DeferredHolder) (registerMarineWithSpawnEgg("Marine Sniper",var2));
		Supplier<EntityType> var3 = () -> {
			EntityType.EntityFactory lv11_0 = CaptainEntity::createMarineCaptain;
			return ModRegistry.createMobType(lv11_0,MARINES).build("mineminenomi:marine_captain");
		};
		MARINE_CAPTAIN = (DeferredHolder) (registerMarineWithSpawnEgg("Marine Captain",var3));
		Supplier<EntityType> var4 = () -> {
			EntityType.EntityFactory lv12_0 = MarineTraderEntity::new;
			return ModRegistry.createMobType(lv12_0,MARINES).build("mineminenomi:marine_trader");
		};
		MARINE_TRADER = (DeferredHolder) (registerMarineWithSpawnEgg("Marine Trader",var4));
		Supplier<EntityType> var5 = () -> {
			EntityType.EntityFactory lv13_0 = PacifistaEntity::new;
			return ModRegistry.createMobType(lv13_0,MARINES).sized(1.5F,3.5F).build("mineminenomi:pacifista");
		};
		PACIFISTA = (DeferredHolder) (registerMarineWithSpawnEgg("Pacifista",var5));
		Supplier<EntityType> var6 = () -> {
			EntityType.EntityFactory lv14_0 = NotoriousEntity::createMarine;
			return ModRegistry.createMobType(lv14_0,MARINES).sized(0.6F,2F).build("mineminenomi:marine_vice_admiral");
		};
		MARINE_VICE_ADMIRAL = (DeferredHolder) (registerMarineWithSpawnEgg("Marine Vice Admiral",var6));
		Supplier<EntityType> var7 = () -> {
			EntityType.EntityFactory lv15_0 = MorganEntity::new;
			return ModRegistry.createMobType(lv15_0,MARINES).sized(0.8F,2.7F).build("mineminenomi:morgan");
		};
		MORGAN = (DeferredHolder) (ModRegistry.registerEntityType("Morgan",var7));
		Supplier<EntityType> var8 = () -> {
			EntityType.EntityFactory lv16_0 = CelestialDragonEntity::new;
			return ModRegistry.createMobType(lv16_0,MARINES).build("mineminenomi:celestial_dragon");
		};
		CELESTIAL_DRAGON = (DeferredHolder) (registerWorldGovWithSpawnEgg("Celestial Dragon",var8));
		Supplier<EntityType> var9 = () -> {
			EntityType.EntityFactory lv17_0 = GruntEntity::createPirateGrunt;
			return ModRegistry.createMobType(lv17_0,PIRATES).build("mineminenomi:pirate_grunt");
		};
		PIRATE_GRUNT = (DeferredHolder) (registerPirateWithSpawnEgg("Pirate Grunt",var9));
		Supplier<EntityType> var10 = () -> {
			EntityType.EntityFactory lv18_0 = BruteEntity::createPirateBrute;
			return ModRegistry.createMobType(lv18_0,PIRATES).sized(0.8F,2.3F).build("mineminenomi:pirate_brute");
		};
		PIRATE_BRUTE = (DeferredHolder) (registerPirateWithSpawnEgg("Pirate Brute",var10));
		Supplier<EntityType> var11 = () -> {
			EntityType.EntityFactory lv19_0 = CaptainEntity::createPirateCaptain;
			return ModRegistry.createMobType(lv19_0,PIRATES).build("mineminenomi:pirate_captain");
		};
		PIRATE_CAPTAIN = (DeferredHolder) (registerPirateWithSpawnEgg("Pirate Captain",var11));
		Supplier<EntityType> var12 = () -> {
			EntityType.EntityFactory lv20_0 = PirateTraderEntity::new;
			return ModRegistry.createMobType(lv20_0,PIRATES).build("mineminenomi:pirate_trader");
		};
		PIRATE_TRADER = (DeferredHolder) (registerPirateWithSpawnEgg("Pirate Trader",var12));
		Supplier<EntityType> var13 = () -> {
			EntityType.EntityFactory lv21_0 = NotoriousEntity::createPirate;
			return ModRegistry.createMobType(lv21_0,MARINES).sized(0.6F,2F).build("mineminenomi:pirate_notorious_captain");
		};
		PIRATE_NOTORIOUS_CAPTAIN = (DeferredHolder) (registerPirateWithSpawnEgg("Pirate Notorious Captain",var13));
		Supplier<EntityType> var14 = () -> {
			EntityType.EntityFactory lv22_0 = BuggyEntity::new;
			return ModRegistry.createMobType(lv22_0,PIRATES).build("mineminenomi:buggy");
		};
		BUGGY = (DeferredHolder) (ModRegistry.registerEntityType("Buggy",var14));
		Supplier<EntityType> var15 = () -> {
			EntityType.EntityFactory lv23_0 = AlvidaEntity::new;
			return ModRegistry.createMobType(lv23_0,PIRATES).build("mineminenomi:alvida");
		};
		ALVIDA = (DeferredHolder) (ModRegistry.registerEntityType("Alvida",var15));
		Supplier<EntityType> var16 = () -> {
			EntityType.EntityFactory lv24_0 = AlvidaEntity::new;
			return ModRegistry.createMobType(lv24_0,PIRATES).build("mineminenomi:alvida2");
		};
		ALVIDA_SLIM = (DeferredHolder) (ModRegistry.registerEntityType("Alvida","alvida2",var16));
		Supplier<EntityType> var17 = () -> {
			EntityType.EntityFactory lv25_0 = CabajiEntity::new;
			return ModRegistry.createMobType(lv25_0,PIRATES).build("mineminenomi:cabaji");
		};
		CABAJI = (DeferredHolder) (ModRegistry.registerEntityType("Cabaji",var17));
		Supplier<EntityType> var18 = () -> {
			EntityType.EntityFactory lv26_0 = ShamEntity::new;
			return ModRegistry.createMobType(lv26_0,PIRATES).build("mineminenomi:sham");
		};
		SHAM = (DeferredHolder) (ModRegistry.registerEntityType("Sham",var18));
		Supplier<EntityType> var19 = () -> {
			EntityType.EntityFactory lv27_0 = BuchiEntity::new;
			return ModRegistry.createMobType(lv27_0,PIRATES).build("mineminenomi:buchi");
		};
		BUCHI = (DeferredHolder) (ModRegistry.registerEntityType("Buchi",var19));
		Supplier<EntityType> var20 = () -> {
			EntityType.EntityFactory lv28_0 = JangoEntity::new;
			return ModRegistry.createMobType(lv28_0,PIRATES).build("mineminenomi:jango");
		};
		JANGO = (DeferredHolder) (ModRegistry.registerEntityType("Jango",var20));
		Supplier<EntityType> var21 = () -> {
			EntityType.EntityFactory lv29_0 = KuroEntity::new;
			return ModRegistry.createMobType(lv29_0,PIRATES).build("mineminenomi:kuro");
		};
		KURO = (DeferredHolder) (ModRegistry.registerEntityType("Kuro",var21));
		Supplier<EntityType> var22 = () -> {
			EntityType.EntityFactory lv30_0 = GinEntity::new;
			return ModRegistry.createMobType(lv30_0,PIRATES).build("mineminenomi:gin");
		};
		GIN = (DeferredHolder) (ModRegistry.registerEntityType("Gin",var22));
		Supplier<EntityType> var23 = () -> {
			EntityType.EntityFactory lv31_0 = PearlEntity::new;
			return ModRegistry.createMobType(lv31_0,PIRATES).sized(0.8F,2.5F).build("mineminenomi:pearl");
		};
		PEARL = (DeferredHolder) (ModRegistry.registerEntityType("Pearl",var23));
		Supplier<EntityType> var24 = () -> {
			EntityType.EntityFactory lv32_0 = DonKriegEntity::new;
			return ModRegistry.createMobType(lv32_0,PIRATES).sized(0.8F,2.5F).build("mineminenomi:don_krieg");
		};
		DON_KRIEG = (DeferredHolder) (ModRegistry.registerEntityType("Don Krieg",var24));
		Supplier<EntityType> var25 = () -> {
			EntityType.EntityFactory lv33_0 = ChewEntity::new;
			return ModRegistry.createMobType(lv33_0,PIRATES).sized(0.75F,2.75F).build("mineminenomi:chew");
		};
		CHEW = (DeferredHolder) (ModRegistry.registerEntityType("Chew",var25));
		Supplier<EntityType> var26 = () -> {
			EntityType.EntityFactory lv34_0 = KuroobiEntity::new;
			return ModRegistry.createMobType(lv34_0,PIRATES).sized(0.75F,2.7F).build("mineminenomi:kuroobi");
		};
		KUROOBI = (DeferredHolder) (ModRegistry.registerEntityType("Kuroobi",var26));
		Supplier<EntityType> var27 = () -> {
			EntityType.EntityFactory lv35_0 = ArlongEntity::new;
			return ModRegistry.createMobType(lv35_0,PIRATES).sized(0.75F,2.75F).build("mineminenomi:arlong");
		};
		ARLONG = (DeferredHolder) (ModRegistry.registerEntityType("Arlong",var27));
		Supplier<EntityType> var28 = () -> {
			EntityType.EntityFactory lv36_0 = Mr5Entity::new;
			return ModRegistry.createMobType(lv36_0,PIRATES).build("mineminenomi:mr5");
		};
		MR5 = (DeferredHolder) (ModRegistry.registerEntityType("Mr 5",var28));
		Supplier<EntityType> var29 = () -> {
			EntityType.EntityFactory lv37_0 = MissValentineEntity::new;
			return ModRegistry.createMobType(lv37_0,PIRATES).build("mineminenomi:miss_valentine");
		};
		MISS_VALENTINE = (DeferredHolder) (ModRegistry.registerEntityType("Miss Valentine",var29));
		Supplier<EntityType> var30 = () -> {
			EntityType.EntityFactory lv38_0 = Mr3Entity::new;
			return ModRegistry.createMobType(lv38_0,PIRATES).build("mineminenomi:mr3");
		};
		MR3 = (DeferredHolder) (ModRegistry.registerEntityType("Mr 3",var30));
		Supplier<EntityType> var31 = () -> {
			EntityType.EntityFactory lv39_0 = Mr1Entity::new;
			return ModRegistry.createMobType(lv39_0,PIRATES).sized(0.9F,2.3F).build("mineminenomi:mr1");
		};
		MR1 = (DeferredHolder) (ModRegistry.registerEntityType("Mr 1",var31));
		Supplier<EntityType> var32 = () -> {
			EntityType.EntityFactory lv40_0 = Mr0Entity::new;
			return ModRegistry.createMobType(lv40_0,PIRATES).sized(0.7F,2F).build("mineminenomi:mr0");
		};
		MR0 = (DeferredHolder) (ModRegistry.registerEntityType("Mr 0",var32));
		Supplier<EntityType> var33 = () -> {
			EntityType.EntityFactory lv41_0 = GruntEntity::createBanditGrunt;
			return ModRegistry.createMobType(lv41_0,BANDITS).build("mineminenomi:bandit_grunt");
		};
		BANDIT_GRUNT = (DeferredHolder) (registerBanditWithSpawnEgg("Bandit Grunt",var33));
		Supplier<EntityType> var34 = () -> {
			EntityType.EntityFactory lv42_0 = BruteEntity::createBanditBrute;
			return ModRegistry.createMobType(lv42_0,BANDITS).sized(0.8F,2.3F).build("mineminenomi:bandit_brute");
		};
		BANDIT_BRUTE = (DeferredHolder) (registerBanditWithSpawnEgg("Bandit Brute",var34));
		Supplier<EntityType> var35 = () -> {
			EntityType.EntityFactory lv43_0 = SniperEntity::createBanditSniper;
			return ModRegistry.createMobType(lv43_0,BANDITS).build("mineminenomi:bandit_sniper");
		};
		BANDIT_SNIPER = (DeferredHolder) (registerBanditWithSpawnEgg("Bandit Sniper",var35));
		Supplier<EntityType> var36 = () -> {
			EntityType.EntityFactory lv44_0 = CaptainEntity::createBanditCaptain;
			return ModRegistry.createMobType(lv44_0,BANDITS).build("mineminenomi:bandit_captain");
		};
		BANDIT_CAPTAIN = (DeferredHolder) (registerBanditWithSpawnEgg("Bandit Leader",var36));
		Supplier<EntityType> var37 = () -> {
			EntityType.EntityFactory lv45_0 = HigumaEntity::new;
			return ModRegistry.createMobType(lv45_0,BANDITS).sized(0.8F,1.9F).build("mineminenomi:higuma");
		};
		HIGUMA = (DeferredHolder) (ModRegistry.registerEntityType("Higuma",var37));
		Supplier<EntityType> var38 = () -> {
			EntityType.EntityFactory lv46_0 = SwordsmanTrainerEntity::new;
			return ModRegistry.createMobType(lv46_0,MobCategory.MISC).build("mineminenomi:swordsman_trainer");
		};
		SWORDSMAN_TRAINER = (DeferredHolder) (registerFactionlessWithSpawnEgg("Swordsman Trainer",var38));
		Supplier<EntityType> var39 = () -> {
			EntityType.EntityFactory lv47_0 = SniperTrainerEntity::new;
			return ModRegistry.createMobType(lv47_0,MobCategory.MISC).build("mineminenomi:sniper_trainer");
		};
		SNIPER_TRAINER = (DeferredHolder) (registerFactionlessWithSpawnEgg("Sniper Trainer",var39));
		Supplier<EntityType> var40 = () -> {
			EntityType.EntityFactory lv48_0 = ArtOfWeatherTrainerEntity::new;
			return ModRegistry.createMobType(lv48_0,MobCategory.MISC).build("mineminenomi:art_of_weather_trainer");
		};
		ART_OF_WEATHER_TRAINER = (DeferredHolder) (registerFactionlessWithSpawnEgg("Art of Weather Trainer",var40));
		Supplier<EntityType> var41 = () -> {
			EntityType.EntityFactory lv49_0 = DoctorTrainerEntity::new;
			return ModRegistry.createMobType(lv49_0,MobCategory.MISC).build("mineminenomi:doctor_trainer");
		};
		DOCTOR_TRAINER = (DeferredHolder) (registerFactionlessWithSpawnEgg("Doctor Trainer",var41));
		Supplier<EntityType> var42 = () -> {
			EntityType.EntityFactory lv50_0 = BrawlerTrainerEntity::new;
			return ModRegistry.createMobType(lv50_0,MobCategory.MISC).build("mineminenomi:brawler_trainer");
		};
		BRAWLER_TRAINER = (DeferredHolder) (registerFactionlessWithSpawnEgg("Brawler Trainer",var42));
		Supplier<EntityType> var43 = () -> {
			EntityType.EntityFactory lv51_0 = BlackLegTrainerEntity::new;
			return ModRegistry.createMobType(lv51_0,MobCategory.MISC).build("mineminenomi:black_leg_trainer");
		};
		BLACK_LEG_TRAINER = (DeferredHolder) (registerFactionlessWithSpawnEgg("Black Leg Trainer",var43));
		Supplier<EntityType> var44 = () -> {
			EntityType.EntityFactory lv52_0 = SkypieanCivilianEntity::new;
			return ModRegistry.createMobType(lv52_0,MobCategory.MISC).build("mineminenomi:skypiean_civilian");
		};
		SKYPIEAN_CIVILIAN = (DeferredHolder) (registerFactionlessWithSpawnEgg("Skypiean Civilian",var44));
		Supplier<EntityType> var45 = () -> {
			EntityType.EntityFactory lv53_0 = SkypieanTraderEntity::new;
			return ModRegistry.createMobType(lv53_0,MobCategory.MISC).build("mineminenomi:skypiean_trader");
		};
		SKYPIEAN_TRADER = (DeferredHolder) (registerFactionlessWithSpawnEgg("Skypiean Trader",var45));
		Supplier<EntityType> var46 = () -> {
			EntityType.EntityFactory lv54_0 = BarkeeperEntity::new;
			return ModRegistry.createMobType(lv54_0,MobCategory.MISC).build("mineminenomi:barkeeper");
		};
		BARKEEPER = (DeferredHolder) (registerFactionlessWithSpawnEgg("Barkeeper",var46));
		Supplier<EntityType> var47 = () -> {
			EntityType.EntityFactory lv55_0 = DenDenMushiEntity::new;
			return ModRegistry.createMobType(lv55_0,MobCategory.CREATURE).sized(0.8F,0.8F).build("mineminenomi:den_den_mushi");
		};
		DEN_DEN_MUSHI = (DeferredHolder) (registerAnimalWithSpawnEgg("Den Den Mushi",var47));
		Supplier<EntityType> var48 = () -> {
			EntityType.EntityFactory lv56_0 = LapahnEntity::new;
			return ModRegistry.createMobType(lv56_0,MobCategory.CREATURE).sized(1.5F,2.3F).build("mineminenomi:lapahn");
		};
		LAPAHN = (DeferredHolder) (registerAnimalWithSpawnEgg("Lapahn",var48));
		Supplier<EntityType> var49 = () -> {
			EntityType.EntityFactory lv57_0 = YagaraBullEntity::new;
			return ModRegistry.createMobType(lv57_0,MobCategory.WATER_CREATURE).sized(1.4F,1.6F).clientTrackingRange(10).build("mineminenomi:yagara_bull");
		};
		YAGARA_BULL = (DeferredHolder) (registerAnimalWithSpawnEgg("Yagara Bull",var49));
		Supplier<EntityType> var50 = () -> {
			EntityType.EntityFactory lv58_0 = HumandrillEntity::new;
			return ModRegistry.createMobType(lv58_0,MobCategory.CREATURE).sized(1F,2.5F).build("mineminenomi:humandrill");
		};
		HUMANDRILL = (DeferredHolder) (registerAnimalWithSpawnEgg("Humandrill",var50));
		Supplier<EntityType> var51 = () -> {
			EntityType.EntityFactory lv59_0 = FightingFishEntity::new;
			return ModRegistry.createMobType(lv59_0,MobCategory.WATER_CREATURE).sized(3F,3F).build("mineminenomi:fighting_fish");
		};
		FIGHTING_FISH = (DeferredHolder) (registerAnimalWithSpawnEgg("Fighting Fish",var51));
		Supplier<EntityType> var52 = () -> {
			EntityType.EntityFactory lv60_0 = BananawaniEntity::new;
			return ModRegistry.createMobType(lv60_0,MobCategory.CREATURE).sized(2F,1.75F).build("mineminenomi:bananwani");
		};
		BANANAWANI = (DeferredHolder) (registerAnimalWithSpawnEgg("Bananawani",var52));
		Supplier<EntityType> var53 = () -> {
			EntityType.EntityFactory lv61_0 = BigDuckEntity::new;
			return ModRegistry.createMobType(lv61_0,MobCategory.CREATURE).sized(1.25F,1.75F).build("mineminenomi:big_duck");
		};
		BIG_DUCK = (DeferredHolder) (registerAnimalWithSpawnEgg("Super Spot-Billed Duck",var53));
		Supplier<EntityType> var54 = () -> {
			EntityType.EntityFactory lv62_0 = SeaCowEntity::new;
			return ModRegistry.createMobType(lv62_0,MobCategory.WATER_CREATURE).sized(1.5F,1.5F).build("mineminenomi:sea_cow");
		};
		SEA_COW = (DeferredHolder) (registerAnimalWithSpawnEgg("Sea Cow",var54));
		Supplier<EntityType> var55 = () -> {
			EntityType.EntityFactory lv63_0 = WhiteWalkieEntity::new;
			return ModRegistry.createMobType(lv63_0,MobCategory.CREATURE).sized(2F,2F).build("mineminenomi:white_walkie");
		};
		WHITE_WALKIE = (DeferredHolder) (registerAnimalWithSpawnEgg("White Walkie",var55));
		Supplier<EntityType> var56 = () -> {
			EntityType.EntityFactory lv64_0 = PandaSharkEntity::new;
			return ModRegistry.createMobType(lv64_0,MobCategory.WATER_CREATURE).sized(1.5F,1.5F).build("mineminenomi:panda_shark");
		};
		PANDA_SHARK = (DeferredHolder) (registerAnimalWithSpawnEgg("Panda Shark",var56));
		Supplier<EntityType> var57 = () -> {
			EntityType.EntityFactory lv65_0 = FlyingFishEntity::new;
			return ModRegistry.createMobType(lv65_0,MobCategory.WATER_CREATURE).sized(1.5F,1.5F).build("mineminenomi:flying_fish");
		};
		FLYING_FISH = (DeferredHolder) (registerAnimalWithSpawnEgg("Flying Fish",var57));
		Supplier<EntityType> var58 = () -> {
			EntityType.EntityFactory lv66_0 = BlugoriEntity::new;
			return ModRegistry.createMobType(lv66_0,MobCategory.CREATURE).sized(1F,1.5F).build("mineminenomi:blugori");
		};
		BLUGORI = (DeferredHolder) (registerAnimalWithSpawnEgg("Blugori",var58));
		Supplier<EntityType> var59 = () -> {
			EntityType.EntityFactory lv67_0 = BlagoriEntity::new;
			return ModRegistry.createMobType(lv67_0,MobCategory.CREATURE).sized(1F,1.5F).build("mineminenomi:blagori");
		};
		BLAGORI = (DeferredHolder) (registerAnimalWithSpawnEgg("Blagori",var59));
		Supplier<EntityType> var60 = () -> {
			EntityType.EntityFactory lv68_0 = KungFuDugongEntity::new;
			return ModRegistry.createMobType(lv68_0,MobCategory.CREATURE).sized(0.6F,1.2F).build("mineminenomi:kung_fu_dugong");
		};
		KUNG_FU_DUGONG = (DeferredHolder) (registerAnimalWithSpawnEgg("Kung Fu Dugong",var60));
		Supplier<EntityType> var61 = () -> {
			EntityType.EntityFactory lv69_0 = WrestlingDugongEntity::new;
			return ModRegistry.createMobType(lv69_0,MobCategory.CREATURE).sized(0.6F,1.2F).build("mineminenomi:wrestling_dugong");
		};
		WRESTLING_DUGONG = (DeferredHolder) (registerAnimalWithSpawnEgg("Wrestling Dugong",var61));
		Supplier<EntityType> var62 = () -> {
			EntityType.EntityFactory lv70_0 = BoxingDugongEntity::new;
			return ModRegistry.createMobType(lv70_0,MobCategory.CREATURE).sized(0.6F,1.2F).build("mineminenomi:boxing_dugong");
		};
		BOXING_DUGONG = (DeferredHolder) (registerAnimalWithSpawnEgg("Boxing Dugong",var62));
		Supplier<EntityType> var63 = () -> {
			EntityType.EntityFactory lv71_0 = LegendaryMasterDugongEntity::new;
			return ModRegistry.createMobType(lv71_0,MobCategory.CREATURE).sized(0.6F,1.2F).build("mineminenomi:legendary_master_dugong");
		};
		LEGENDARY_MASTER_DUGONG = (DeferredHolder) (registerAnimalWithSpawnEgg("Legendary Master Dugong",var63));
		Supplier<EntityType> var64 = () -> {
			EntityType.EntityFactory lv72_0 = WanderingDugongEntity::new;
			return ModRegistry.createMobType(lv72_0,MobCategory.CREATURE).sized(0.6F,1.2F).build("mineminenomi:wandering_dugong");
		};
		WANDERING_DUGONG = (DeferredHolder) (registerAnimalWithSpawnEgg("Wandering Dugong",var64));
		Supplier<EntityType> var65 = () -> {
			EntityType.EntityFactory lv73_0 = MirageCloneEntity::new;
			return ModRegistry.createMobType(lv73_0,MobCategory.MISC).build("mineminenomi:mirage_clone");
		};
		MIRAGE_CLONE = (DeferredHolder) (ModRegistry.registerEntityType("Mirage Clone",var65));
		Supplier<EntityType> var66 = () -> {
			EntityType.EntityFactory lv74_0 = WaxCloneEntity::new;
			return ModRegistry.createMobType(lv74_0,MobCategory.MISC).build("mineminenomi:wax_clone");
		};
		WAX_CLONE = (DeferredHolder) (ModRegistry.registerEntityType("Wax Clone",var66));
		Supplier<EntityType> var67 = () -> {
			EntityType.EntityFactory lv75_0 = BlackKnightEntity::new;
			return ModRegistry.createMobType(lv75_0,MobCategory.MISC).build("mineminenomi:black_knight");
		};
		BLACK_KNIGHT = (DeferredHolder) (ModRegistry.registerEntityType("Black Knight",var67));
		Supplier<EntityType> var68 = () -> {
			EntityType.EntityFactory lv76_0 = DoppelmanEntity::new;
			return ModRegistry.createMobType(lv76_0,MobCategory.MISC).build("mineminenomi:doppelman");
		};
		DOPPELMAN = (DeferredHolder) (ModRegistry.registerEntityType("Doppelman",var68));
		Supplier<EntityType> var69 = () -> {
			EntityType.EntityFactory lv77_0 = NightmareSoldierEntity::new;
			return ModRegistry.createMobType(lv77_0,MobCategory.MISC).build("mineminenomi:nightmare_soldier");
		};
		NIGHTMARE_SOLDIER = (DeferredHolder) (ModRegistry.registerEntityType("Nightmare Soldier",var69));
		Supplier<EntityType> var70 = () -> {
			EntityType.EntityFactory lv78_0 = PhysicalBodyEntity::new;
			return ModRegistry.createEntityType(lv78_0,MobCategory.MISC).setUpdateInterval(10).sized(0.6F,1.6F).build("mineminenomi:physical_body");
		};
		PHYSICAL_BODY = (DeferredHolder) (ModRegistry.registerEntityType("Physical Body",var70));
		Supplier<EntityType> var71 = () -> {
			EntityType.EntityFactory lv79_0 = PhysicalBodyEntity::new;
			return ModRegistry.createEntityType(lv79_0,MobCategory.MISC).setUpdateInterval(10).sized(0.6F,1.6F).build("mineminenomi:skelly_body");
		};
		SKELLY_BODY = (DeferredHolder) (ModRegistry.registerEntityType("Skelly Body",var71));
		Supplier<EntityType> var72 = () -> {
			EntityType.EntityFactory lv80_0 = BottomHalfBodyEntity::new;
			return ModRegistry.createEntityType(lv80_0,MobCategory.MISC).setUpdateInterval(1).build("mineminenomi:bottom_half");
		};
		BOTTOM_HALF = (DeferredHolder) (ModRegistry.registerEntityType("Bottom Half",var72));
	}


	private static <T extends EntityType<? extends Mob>> DeferredHolder<EntityType<?>, T> registerMarineWithSpawnEgg(String var0, Supplier<T> var1) {
		DeferredHolder var2 = ModRegistry.registerEntityType(var0,var1);
		DeferredHolder var3 = var2;
		final DeferredHolder var3_f1 = var3;
		Supplier<DeferredSpawnEggItem> var4 = () -> {
			return new DeferredSpawnEggItem((Supplier)(var3_f1),150145,16250871,new Item.Properties());
		};
		ModRegistry.registerSpawnEggItem(var0,var4);
		return var2;
	}
	private static <T extends EntityType<? extends Mob>> DeferredHolder<EntityType<?>, T> registerWorldGovWithSpawnEgg(String var0, Supplier<T> var1) {
		DeferredHolder var2 = ModRegistry.registerEntityType(var0,var1);
		DeferredHolder var3 = var2;
		final DeferredHolder var3_f1 = var3;
		Supplier<DeferredSpawnEggItem> var4 = () -> {
			return new DeferredSpawnEggItem((Supplier)(var3_f1),12434877,3092271,new Item.Properties());
		};
		ModRegistry.registerSpawnEggItem(var0,var4);
		return var2;
	}
	private static <T extends EntityType<? extends Mob>> DeferredHolder<EntityType<?>, T> registerPirateWithSpawnEgg(String var0, Supplier<T> var1) {
		DeferredHolder var2 = ModRegistry.registerEntityType(var0,var1);
		DeferredHolder var3 = var2;
		final DeferredHolder var3_f1 = var3;
		Supplier<DeferredSpawnEggItem> var4 = () -> {
			return new DeferredSpawnEggItem((Supplier)(var3_f1),12655644,16250871,new Item.Properties());
		};
		ModRegistry.registerSpawnEggItem(var0,var4);
		return var2;
	}
	private static <T extends EntityType<? extends Mob>> DeferredHolder<EntityType<?>, T> registerBanditWithSpawnEgg(String var0, Supplier<T> var1) {
		DeferredHolder var2 = ModRegistry.registerEntityType(var0,var1);
		DeferredHolder var3 = var2;
		final DeferredHolder var3_f1 = var3;
		Supplier<DeferredSpawnEggItem> var4 = () -> {
			return new DeferredSpawnEggItem((Supplier)(var3_f1),7885653,16250871,new Item.Properties());
		};
		ModRegistry.registerSpawnEggItem(var0,var4);
		return var2;
	}
	private static <T extends EntityType<? extends Mob>> DeferredHolder<EntityType<?>, T> registerFactionlessWithSpawnEgg(String var0, Supplier<T> var1) {
		DeferredHolder var2 = ModRegistry.registerEntityType(var0,var1);
		DeferredHolder var3 = var2;
		final DeferredHolder var3_f1 = var3;
		Supplier<DeferredSpawnEggItem> var4 = () -> {
			return new DeferredSpawnEggItem((Supplier)(var3_f1),16498508,16250871,new Item.Properties());
		};
		ModRegistry.registerSpawnEggItem(var0,var4);
		return var2;
	}
	private static <T extends EntityType<? extends Mob>> DeferredHolder<EntityType<?>, T> registerAnimalWithSpawnEgg(String var0, Supplier<T> var1) {
		DeferredHolder var2 = ModRegistry.registerEntityType(var0,var1);
		DeferredHolder var3 = var2;
		final DeferredHolder var3_f1 = var3;
		Supplier<DeferredSpawnEggItem> var4 = () -> {
			return new DeferredSpawnEggItem((Supplier)(var3_f1),10996276,10680264,new Item.Properties());
		};
		ModRegistry.registerSpawnEggItem(var0,var4);
		return var2;
	}
	private static <T extends EntityType<? extends Mob>> DeferredHolder<EntityType<?>, T> registerDummyWithSpawnEgg(String var0, Supplier<T> var1) {
		DeferredHolder var2 = ModRegistry.registerEntityType(var0,var1);
		DeferredHolder var3 = var2;
		final DeferredHolder var3_f1 = var3;
		Supplier<DeferredSpawnEggItem> var4 = () -> {
			return new DeferredSpawnEggItem((Supplier)(var3_f1),16711901,15662848,new Item.Properties());
		};
		ModRegistry.registerSpawnEggItem(var0,var4);
		return var2;
	}}