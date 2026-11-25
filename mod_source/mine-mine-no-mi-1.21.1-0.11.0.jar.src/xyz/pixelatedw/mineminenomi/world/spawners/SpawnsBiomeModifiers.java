package xyz.pixelatedw.mineminenomi.world.spawners;

import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModTags$Biomes;

public class SpawnsBiomeModifiers {
	// Fields
	public static final ResourceKey<BiomeModifier> SPAWN_DUGONGS = registerKey("spawn_dugongs");
	public static final ResourceKey<BiomeModifier> SPAWN_WANDERING_DUGONG = registerKey("spawn_wandering_dugong");
	public static final ResourceKey<BiomeModifier> SPAWN_BIG_DUCK = registerKey("spawn_big_duck");
	public static final ResourceKey<BiomeModifier> SPAWN_BANANAWANI = registerKey("spawn_bananawani");
	public static final ResourceKey<BiomeModifier> SPAWN_DEN_DEN_MUSHI = registerKey("spawn_den_den_mushi");
	public static final ResourceKey<BiomeModifier> SPAWN_GORILLAS = registerKey("spawn_gorillas");
	public static final ResourceKey<BiomeModifier> SPAWN_HUMANDRILL = registerKey("spawn_humandrill");
	public static final ResourceKey<BiomeModifier> SPAWN_LAPAHN = registerKey("spawn_lapahn");
	public static final ResourceKey<BiomeModifier> SPAWN_WHITE_WALKIE = registerKey("spawn_white_walkie");
	public static final ResourceKey<BiomeModifier> SPAWN_FIGHTING_FISH = registerKey("spawn_fighting_fish");
	public static final ResourceKey<BiomeModifier> SPAWN_NORMAL_FISH = registerKey("spawn_normal_fish");
	public static final ResourceKey<BiomeModifier> SPAWN_FLYING_FISH = registerKey("spawn_flying_fish");
	public static final ResourceKey<BiomeModifier> SPAWN_MARINES = registerKey("spawn_marines");
	public static final ResourceKey<BiomeModifier> SPAWN_PIRATES = registerKey("spawn_pirates");
	public static final ResourceKey<BiomeModifier> SPAWN_BANDITS = registerKey("spawn_bandits");

	public static void bootstrap(BootstrapContext<BiomeModifier> var0) {
		HolderGetter var1 = var0.lookup(Registries.BIOME);
		var0.register(SPAWN_FIGHTING_FISH,new BiomeModifiers.AddSpawnsBiomeModifier((HolderSet)(var1.getOrThrow(ModTags$Biomes.CAN_SPAWN_FIGHTING_FISH)),List.of((Object)(new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.FIGHTING_FISH.get())),1,1,1)))));
		var0.register(SPAWN_NORMAL_FISH,new BiomeModifiers.AddSpawnsBiomeModifier((HolderSet)(var1.getOrThrow(ModTags$Biomes.CAN_SPAWN_FISH)),List.of(new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.YAGARA_BULL.get())),4,1,2),new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.PANDA_SHARK.get())),4,1,1),new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.SEA_COW.get())),3,1,3))));
		var0.register(SPAWN_FLYING_FISH,new BiomeModifiers.AddSpawnsBiomeModifier((HolderSet)(var1.getOrThrow(ModTags$Biomes.CAN_SPAWN_FLYING_FISH)),List.of((Object)(new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.FLYING_FISH.get())),1,1,1)))));
		var0.register(SPAWN_DUGONGS,new BiomeModifiers.AddSpawnsBiomeModifier((HolderSet)(var1.getOrThrow(ModTags$Biomes.CAN_SPAWN_DUGONGS)),List.of(new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.KUNG_FU_DUGONG.get())),12,3,5),new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.BOXING_DUGONG.get())),7,1,3),new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.WRESTLING_DUGONG.get())),7,1,3),new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.LEGENDARY_MASTER_DUGONG.get())),2,1,1))));
		var0.register(SPAWN_WANDERING_DUGONG,new BiomeModifiers.AddSpawnsBiomeModifier((HolderSet)(var1.getOrThrow(ModTags$Biomes.CAN_SPAWN_WANDERING_DUGONG)),List.of((Object)(new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.WANDERING_DUGONG.get())),1,1,1)))));
		var0.register(SPAWN_BIG_DUCK,new BiomeModifiers.AddSpawnsBiomeModifier((HolderSet)(var1.getOrThrow(ModTags$Biomes.CAN_SPAWN_BIG_DUCK)),List.of((Object)(new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.BIG_DUCK.get())),5,1,4)))));
		var0.register(SPAWN_BANANAWANI,new BiomeModifiers.AddSpawnsBiomeModifier((HolderSet)(var1.getOrThrow(ModTags$Biomes.CAN_SPAWN_BANANAWANI)),List.of((Object)(new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.BANANAWANI.get())),7,1,2)))));
		var0.register(SPAWN_DEN_DEN_MUSHI,new BiomeModifiers.AddSpawnsBiomeModifier((HolderSet)(var1.getOrThrow(ModTags$Biomes.CAN_SPAWN_DEN_DEN_MUSHI)),List.of((Object)(new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.DEN_DEN_MUSHI.get())),8,1,4)))));
		var0.register(SPAWN_GORILLAS,new BiomeModifiers.AddSpawnsBiomeModifier((HolderSet)(var1.getOrThrow(ModTags$Biomes.CAN_SPAWN_GORILLAS)),List.of(new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.BLUGORI.get())),3,1,2),new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.BLAGORI.get())),1,1,1))));
		var0.register(SPAWN_HUMANDRILL,new BiomeModifiers.AddSpawnsBiomeModifier((HolderSet)(var1.getOrThrow(ModTags$Biomes.CAN_SPAWN_HUMANDRILL)),List.of((Object)(new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.HUMANDRILL.get())),5,1,3)))));
		var0.register(SPAWN_LAPAHN,new BiomeModifiers.AddSpawnsBiomeModifier((HolderSet)(var1.getOrThrow(ModTags$Biomes.CAN_SPAWN_LAPAHN)),List.of((Object)(new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.LAPAHN.get())),5,1,3)))));
		var0.register(SPAWN_WHITE_WALKIE,new BiomeModifiers.AddSpawnsBiomeModifier((HolderSet)(var1.getOrThrow(ModTags$Biomes.CAN_SPAWN_WHITE_WALKIE)),List.of((Object)(new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.WHITE_WALKIE.get())),5,1,2)))));
		var0.register(SPAWN_MARINES,new BiomeModifiers.AddSpawnsBiomeModifier((HolderSet)(var1.getOrThrow(ModTags$Biomes.CAN_SPAWN_HUMANS)),List.of(new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.MARINE_GRUNT.get())),929,2,4),new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.MARINE_BRUTE.get())),40,1,2),new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.MARINE_TRADER.get())),20,1,1),new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.MARINE_SNIPER.get())),10,1,1),new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.PACIFISTA.get())),1,1,1))));
		var0.register(SPAWN_PIRATES,new BiomeModifiers.AddSpawnsBiomeModifier((HolderSet)(var1.getOrThrow(ModTags$Biomes.CAN_SPAWN_HUMANS)),List.of(new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.PIRATE_GRUNT.get())),94,2,4),new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.PIRATE_BRUTE.get())),4,1,2),new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.PIRATE_TRADER.get())),2,1,1))));
		var0.register(SPAWN_BANDITS,new BiomeModifiers.AddSpawnsBiomeModifier((HolderSet)(var1.getOrThrow(ModTags$Biomes.CAN_SPAWN_HUMANS)),List.of(new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.BANDIT_GRUNT.get())),94,2,4),new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.BANDIT_BRUTE.get())),5,1,2),new MobSpawnSettings.SpawnerData(((EntityType)(ModMobs.BANDIT_SNIPER.get())),1,1,1))));
	}
	private static ResourceKey<BiomeModifier> registerKey(String var0) {
		return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,ResourceLocation.fromNamespaceAndPath("mineminenomi",var0));
	}
}