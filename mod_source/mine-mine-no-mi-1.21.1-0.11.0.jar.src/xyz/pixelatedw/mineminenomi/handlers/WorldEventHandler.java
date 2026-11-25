package xyz.pixelatedw.mineminenomi.handlers;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.crafting.SmithingDialRecipe;
import xyz.pixelatedw.mineminenomi.api.crafting.SmithingEnchantmentRecipe;
import xyz.pixelatedw.mineminenomi.api.events.SmithingTableEvent;
import xyz.pixelatedw.mineminenomi.api.util.TPSDelta;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;
import xyz.pixelatedw.mineminenomi.data.world.EventsWorldData;
import xyz.pixelatedw.mineminenomi.data.world.ExtendedWorldData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.data.world.NPCWorldData;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.handlers.entity.BountyHandler;
import xyz.pixelatedw.mineminenomi.handlers.protection.AbilityProtectionHandler;
import xyz.pixelatedw.mineminenomi.handlers.world.LootTablesHandler;
import xyz.pixelatedw.mineminenomi.handlers.world.OneFruitPerWorldHandler;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.world.spawners.AmbushSpawner;
import xyz.pixelatedw.mineminenomi.world.spawners.NewWorldSpawner;
import xyz.pixelatedw.mineminenomi.world.spawners.TraderSpawner;
import xyz.pixelatedw.mineminenomi.world.spawners.TrainerSpawner;

@EventBusSubscriber(modid="mineminenomi")
public class WorldEventHandler {
	// Fields
	private static final TraderSpawner TRADER_SPAWNER = new TraderSpawner();
	private static final TrainerSpawner TRAINER_SPAWNER = new TrainerSpawner();
	private static final AmbushSpawner AMBUSH_SPAWNER = new AmbushSpawner();
	private static final NewWorldSpawner NEW_WORLD_SPAWNER = new NewWorldSpawner();
	private static final Set<ResourceKey<LootTable>> APPENDED_VANILLA_LOOT;

	@SubscribeEvent()
	public static void changeItemFuel(FurnaceFuelBurnTimeEvent var0) {
		Item var1 = var0.getItemStack().getItem();
		if (((java.lang.Object)(var1)).equals((Object)(((Block)(ModBlocks.FLAME_DIAL.get())).asItem()))){
			var0.setBurnTime(10000);
		}else{
			if (((java.lang.Object)(var1)).equals((Object)(((Item)(ModItems.WANTED_POSTER_PACKAGE.get())).asItem()))){
				var0.setBurnTime(300);
			}else{
				if ((!(((java.lang.Object)(var1)).equals((Object)(((Block)(ModBlocks.WANTED_POSTER.get())).asItem())))) && ((!(((java.lang.Object)(var1)).equals((Object)(((Block)(ModBlocks.FLAG.get())).asItem())))) && ((!(((java.lang.Object)(var1)).equals(ModItems.COLOR_PALETTE.get()))) && ((!(((java.lang.Object)(var1)).equals(ModItems.CHALLENGE_POSTER.get()))) && (!(((java.lang.Object)(var1)).equals(ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get()))))))){
					if (((java.lang.Object)(var1)).equals(ModItems.VIVRE_CARD.get())){
						var0.setBurnTime(50);
					}else{
						return;
					}
				}else{
					var0.setBurnTime(200);
				}
			}
		}
	}
	@SubscribeEvent()
	public static void onLevelLoad(LevelEvent.Load var0) {
		ModDamageSources.init(var0.getLevel());
	}
	@SubscribeEvent()
	public static void onLevelPreTick(LevelTickEvent.Pre var0) {
		if ((var0.getLevel()) == (null)){
			return;
		}else{
			TPSDelta.INSTANCE.tick();
			if (var0.getLevel().isClientSide){
				return;
			}else{
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onLevelPostTick(LevelTickEvent.Post var0) {
		if (((var0.getLevel()) != (null)) && (!(var0.getLevel().isClientSide))){
			ServerLevel var1 = ((ServerLevel)(var0.getLevel()));
			ProtectedAreasData.get(var1).tick();
			if ((var1.dimension()) == (Level.OVERWORLD)){
				if (((var1.getGameTime()) % (20L)) == (0L)){
					var0.getLevel().getProfiler().push("faction");
					FactionsWorldData.get().tick((Level)(var1));
					var0.getLevel().getProfiler().pop();
					var0.getLevel().getProfiler().push("worldEvents");
					EventsWorldData.get().tick(var1);
					var0.getLevel().getProfiler().pop();
					var0.getLevel().getProfiler().push("trackedNPCs");
					NPCWorldData.get().tick(var1);
					var0.getLevel().getProfiler().pop();
				}
				if (ServerConfig.isWantedPosterPackagesEnabled()){
					if (((var1.getGameTime()) % ((long)(ServerConfig.getTimeBetweenPackages()))) == (0L)){
						BountyHandler.updateEverybodysBounty(var1);
						BountyHandler.dropWantedPosters(var1);
					}
					if (((var1.getGameTime()) % (72000L)) == (0L)){
						BountyHandler.clearDropCache();
					}
				}
				if (ServerConfig.hasChunkUnloadingRealisticLogic()){
					OneFruitPerWorldHandler.tick();
				}
				if (var0.getLevel().getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)){
					var0.getLevel().getProfiler().push("worldSpawners");
					if (ServerConfig.canSpawnTraders()){
						TRADER_SPAWNER.tick(var1);
					}
					if (ServerConfig.canSpawnTrainers()){
						TRAINER_SPAWNER.tick(var1);
					}
					if (ServerConfig.canSpawnAmbushes()){
						AMBUSH_SPAWNER.tick(var1);
					}
					if (ExtendedWorldData.get().isNewWorldActive()){
						NEW_WORLD_SPAWNER.tick(var1);
					}
					var0.getLevel().getProfiler().pop();
				}else{
					return;
				}
			}else{
				if (NuWorld.isChallengeDimension((Level)(var1))){
					var1.getProfiler().push("challengesManager");
					ChallengesWorldData.get().tick(var1);
					var1.getProfiler().pop();
				}else{
					return;
				}
			}
			return;
		}else{
			return;
		}
	}
	@SubscribeEvent()
	public static void onExplosionDetonate(ExplosionEvent.Detonate var0) {
		Level var1 = var0.getLevel();
		if (!(var1.isClientSide)){
			ServerLevel var2 = ((ServerLevel)(var1));
			List var3 = var0.getAffectedBlocks();
			List var4 = var0.getAffectedEntities();
			AbilityProtectionHandler.handleExplosions(var2,var0.getExplosion(),var3,var4);
		}
	}
	@SubscribeEvent()
	public static void onSmithingUpdate(SmithingTableEvent var0) {
		Recipe var1 = var0.getRecipe().value();
		if (var1 instanceof SmithingDialRecipe){
			SmithingDialRecipe var2 = ((SmithingDialRecipe)(var1));
			var0.getAdditionSlot().shrink((var2.getAdditionalAmount()) - (1));
		}else{
			Recipe var2_1 = var0.getRecipe().value();
			if (var2_1 instanceof SmithingEnchantmentRecipe){
				SmithingEnchantmentRecipe var3 = ((SmithingEnchantmentRecipe)(var2_1));
				var0.getAdditionSlot().shrink((var3.getAdditionalAmount(var0.getContainer())) - (1));
			}else{
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onLootTableLoad(LootTableLoadEvent var0) {
		if (APPENDED_VANILLA_LOOT.contains(var0.getKey())){
			LootTablesHandler.appendVanillaLootTables(var0.getName(),var0.getTable());
		}
	}
	static  {
		ResourceKey[] var0 = new ResourceKey[]{BuiltInLootTables.WOODLAND_MANSION,BuiltInLootTables.UNDERWATER_RUIN_BIG,BuiltInLootTables.UNDERWATER_RUIN_SMALL,BuiltInLootTables.STRONGHOLD_CORRIDOR,BuiltInLootTables.SPAWN_BONUS_CHEST,BuiltInLootTables.SHIPWRECK_TREASURE,BuiltInLootTables.SHIPWRECK_SUPPLY,BuiltInLootTables.PILLAGER_OUTPOST,BuiltInLootTables.JUNGLE_TEMPLE,BuiltInLootTables.DESERT_PYRAMID,BuiltInLootTables.BURIED_TREASURE,BuiltInLootTables.ABANDONED_MINESHAFT,BuiltInLootTables.VILLAGE_WEAPONSMITH,BuiltInLootTables.VILLAGE_TEMPLE,BuiltInLootTables.VILLAGE_TANNERY,BuiltInLootTables.VILLAGE_SHEPHERD,BuiltInLootTables.VILLAGE_FLETCHER,BuiltInLootTables.VILLAGE_FISHER,BuiltInLootTables.VILLAGE_BUTCHER,BuiltInLootTables.VILLAGE_ARMORER,ResourceKey.create(Registries.LOOT_TABLE,ResourceLocation.parse("blocks/brain_coral_block")),BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY,BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY,BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE};
		APPENDED_VANILLA_LOOT = Sets.newHashSet(var0);
	}
}