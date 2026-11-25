package xyz.pixelatedw.mineminenomi.datagen.loottables;

import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.CampLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.GhostShipLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.SkyIslandLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.TavernLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.bandit.BanditArenaLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.bandit.BanditCaveLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.bandit.BanditFortLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.caravan.EasyCaravanLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.caravan.HardCaravanLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.marine.MarineBattleshipLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.marine.MarineLargeBaseLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.marine.MarineSmallBaseLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.marine.MarineWatchTowerLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.pirate.PirateLargeShipLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.pirate.PirateMediumShipLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.pirate.PirateSmallShipLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.AbandonedMineshaftLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.BuriedTreasureLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.DesertPyramidLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.JungleTempleLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.PillagerOutpostLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.ShipwreckLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.SpawnBonusChestLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.StrongholdLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.UnderwaterRuinLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.WoodlandMansionLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.archaeology.DesertPyramidArchaeologyLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.archaeology.DesertWellArchaeologyLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.archaeology.TrailRuinsRareArchaeologyLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.village.VillageArmorerLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.village.VillageButcherLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.village.VillageFisherLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.village.VillageFletcherLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.village.VillageShepherdLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.village.VillageTanneryLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.village.VillageTempleLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.village.VillageWeaponsmithLootTable;

public final record StructuresLootTablesDataGen(HolderLookup.Provider registries) {
	public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> var1) {
		this.addLootTable(var1,"pirate/small_ship",PirateSmallShipLootTable.getTables());
		this.addLootTable(var1,"pirate/medium_ship",PirateMediumShipLootTable.getTables());
		this.addLootTable(var1,"pirate/large_ship",PirateLargeShipLootTable.getTables());
		this.addLootTable(var1,"marine/battleship",MarineBattleshipLootTable.getTables());
		this.addLootTable(var1,"ghost_ship",GhostShipLootTable.getTables());
		this.addLootTable(var1,"marine/small_base",MarineSmallBaseLootTable.getTables());
		this.addLootTable(var1,"marine/large_base",MarineLargeBaseLootTable.getTables());
		this.addLootTable(var1,"marine/watch_tower",MarineWatchTowerLootTable.getTables());
		this.addLootTable(var1,"marine/camp",CampLootTable.getTables());
		this.addLootTable(var1,"bandit/cave",BanditCaveLootTable.getTables(this.registries));
		this.addLootTable(var1,"bandit/arena",BanditArenaLootTable.getTables());
		this.addLootTable(var1,"bandit/fort",BanditFortLootTable.getTables());
		this.addLootTable(var1,"bandit/camp",CampLootTable.getTables());
		this.addLootTable(var1,"caravan",EasyCaravanLootTable.getTables());
		this.addLootTable(var1,"caravan",HardCaravanLootTable.getTables());
		this.addLootTable(var1,"tavern",TavernLootTable.getTables());
		this.addLootTable(var1,"sky_island",SkyIslandLootTable.getTables());
		this.addVanillaLootTable(var1,BuiltInLootTables.UNDERWATER_RUIN_BIG,UnderwaterRuinLootTable.getPool());
		this.addVanillaLootTable(var1,BuiltInLootTables.UNDERWATER_RUIN_SMALL,UnderwaterRuinLootTable.getPool());
		this.addVanillaLootTable(var1,BuiltInLootTables.SHIPWRECK_TREASURE,ShipwreckLootTable.getTreasurePool());
		this.addVanillaLootTable(var1,BuiltInLootTables.SHIPWRECK_SUPPLY,ShipwreckLootTable.getSupplyPool());
		this.addVanillaLootTable(var1,BuiltInLootTables.ABANDONED_MINESHAFT,AbandonedMineshaftLootTable.getPool());
		this.addVanillaLootTable(var1,BuiltInLootTables.BURIED_TREASURE,BuriedTreasureLootTable.getPool());
		this.addVanillaLootTable(var1,BuiltInLootTables.DESERT_PYRAMID,DesertPyramidLootTable.getPool());
		this.addVanillaLootTable(var1,BuiltInLootTables.JUNGLE_TEMPLE,JungleTempleLootTable.getPool());
		this.addVanillaLootTable(var1,BuiltInLootTables.PILLAGER_OUTPOST,PillagerOutpostLootTable.getPool());
		this.addVanillaLootTable(var1,BuiltInLootTables.WOODLAND_MANSION,WoodlandMansionLootTable.getPool());
		this.addVanillaLootTable(var1,BuiltInLootTables.SPAWN_BONUS_CHEST,SpawnBonusChestLootTable.getPool());
		this.addVanillaLootTable(var1,BuiltInLootTables.STRONGHOLD_CORRIDOR,StrongholdLootTable.getPool());
		this.addVanillaLootTable(var1,BuiltInLootTables.VILLAGE_ARMORER,VillageArmorerLootTable.getPool());
		this.addVanillaLootTable(var1,BuiltInLootTables.VILLAGE_BUTCHER,VillageButcherLootTable.getPool());
		this.addVanillaLootTable(var1,BuiltInLootTables.VILLAGE_FISHER,VillageFisherLootTable.getPool());
		this.addVanillaLootTable(var1,BuiltInLootTables.VILLAGE_FLETCHER,VillageFletcherLootTable.getPool());
		this.addVanillaLootTable(var1,BuiltInLootTables.VILLAGE_TANNERY,VillageTanneryLootTable.getPool());
		this.addVanillaLootTable(var1,BuiltInLootTables.VILLAGE_TEMPLE,VillageTempleLootTable.getPool());
		this.addVanillaLootTable(var1,BuiltInLootTables.VILLAGE_WEAPONSMITH,VillageWeaponsmithLootTable.getPool());
		this.addVanillaLootTable(var1,BuiltInLootTables.VILLAGE_SHEPHERD,VillageShepherdLootTable.getPool());
		this.addVanillaLootTable(var1,BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY,DesertPyramidArchaeologyLootTable.getPool());
		this.addVanillaLootTable(var1,BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY,DesertWellArchaeologyLootTable.getPool());
		this.addVanillaLootTable(var1,BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE,TrailRuinsRareArchaeologyLootTable.getPool());
	}
	protected void addLootTable(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> var1, String var2, Pair<String, LootTable.Builder>[] var3) {
		Pair<String, LootTable.Builder>[] var4 = var3;
		int var5 = var4.length;
		int var6 = 0;
		do{
			if ((var6) < (var5)){
				Pair<String, LootTable.Builder> var7 = var4[var6];
				String var8 = ((String)(var7.getLeft()));
				String var9 = var2;
				String var10 = var8;
				String var11 = "chests/" + var9 + "/" + var10;
				ResourceLocation var12 = ResourceLocation.fromNamespaceAndPath("mineminenomi",var11);
				var1.accept(ResourceKey.create(Registries.LOOT_TABLE,var12),((LootTable.Builder)(var7.getRight())));
				var6++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	protected void addVanillaLootTable(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> var1, ResourceKey<LootTable> var2, LootTable.Builder var3) {
		String var4 = var2.location().getPath();
		String var5 = "vanilla/" + var4;
		ResourceLocation var6 = ResourceLocation.fromNamespaceAndPath("mineminenomi",var5);
		var1.accept(ResourceKey.create(Registries.LOOT_TABLE,var6),var3);
	}
	public static LootTableProvider.SubProviderEntry create() {
		Function<HolderLookup.Provider, LootTableSubProvider> var0 = StructuresLootTablesDataGen::new;
		return new LootTableProvider.SubProviderEntry(var0,LootContextParamSets.CHEST);
	}
}