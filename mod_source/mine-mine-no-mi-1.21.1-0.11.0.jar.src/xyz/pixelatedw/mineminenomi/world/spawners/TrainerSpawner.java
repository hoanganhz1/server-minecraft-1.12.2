package xyz.pixelatedw.mineminenomi.world.spawners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.ITrainer;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModTags$Biomes;

public class TrainerSpawner {
	// Fields
	public static final HashMap<Supplier<FightingStyle>, Supplier<? extends EntityType<? extends ITrainer>>> STYLE_TO_TRAINER_MAP = createStyleTrainerMap();
	private static final Predicate<Entity> TRAINER_CHECK;
	private Random random;
	private int cooldown;

	public TrainerSpawner() {
		this.random = new Random();
	}
	private static final HashMap<Supplier<FightingStyle>, Supplier<? extends EntityType<? extends ITrainer>>> createStyleTrainerMap() {
		HashMap var0 = new HashMap();
		var0.put(ModFightingStyles.SWORDSMAN,ModMobs.SWORDSMAN_TRAINER);
		var0.put(ModFightingStyles.BRAWLER,ModMobs.BRAWLER_TRAINER);
		var0.put(ModFightingStyles.BLACK_LEG,ModMobs.BLACK_LEG_TRAINER);
		var0.put(ModFightingStyles.SNIPER,ModMobs.SNIPER_TRAINER);
		var0.put(ModFightingStyles.DOCTOR,ModMobs.DOCTOR_TRAINER);
		var0.put(ModFightingStyles.ART_OF_WEATHER,ModMobs.ART_OF_WEATHER_TRAINER);
		return var0;
	}
	public void tick(ServerLevel var1) {
		var1.getProfiler().push("trainerSpawnerTick");
		int var2 = (this.cooldown) - (1);
		this.cooldown = var2;
		if ((var2) <= (0)){
			this.cooldown = ServerConfig.getTimeBetweenTrainerSpawns();
			if ((this.random.nextInt(100)) <= (ServerConfig.getChanceForTrainerSpawn())){
				this.spawn(var1);
			}
		}
		var1.getProfiler().pop();
	}
	public void spawn(ServerLevel var1) {
		FightingStyle var10 = null;
		ServerPlayer var6 = null;
		var1.getProfiler().push("trainerSpawnerSpawn");
		int var2 = Mth.clamp((var1.players().size()) / (3),1,10);
		Player[] var3 = new Player[var2];
		int var4 = 0;
		do{
			if ((var4) < (var3.length)){
				ServerPlayer var5 = var1.getRandomPlayer();
				if ((var5) == (null)){
					var4++;
					continue;
				}else{
					var6 = var5;
					final ServerPlayer var6_f1 = var6;
					Predicate<? super Player> var7 = (l0) -> {
			return (l0) == (var6_f1);
		};
					if (Arrays.stream(var3).anyMatch(var7)){
						var4++;
						continue;
					}else{
						((java.lang.Object[]) (var3))[var4] = var5;
						EntityStatsData var8 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var5)))).orElse((Object)(null))));
						if ((var8) == (null)){
							var4++;
							continue;
						}else{
							FightingStyle var9 = ((FightingStyle)(((java.util.Optional)(var8.getFightingStyle())).orElse((Object)(((FightingStyle)(ModFightingStyles.SWORDSMAN.get()))))));
							var10 = var9;
							final FightingStyle var10_f2 = var10;
							Predicate var11 = (Predicate) ((Predicate<Map.Entry>) ((l0) -> {
			return ((java.lang.Object)(((FightingStyle)(((Supplier)(l0.getKey())).get())))).equals((Object)(var10_f2));
		}));
							Function var12 = (Function) ((Function<Map.Entry, EntityType>) ((l0) -> {
			return ((EntityType)(((Supplier)(l0.getValue())).get()));
		}));
							EntityType var13 = ((EntityType)(((java.util.Optional)(((java.util.stream.Stream)(STYLE_TO_TRAINER_MAP.entrySet().stream())).filter((Predicate)(var11)).map(var12).findAny())).orElse((Object)(null))));
							BlockPos var14 = var5.blockPosition();
							Holder var15 = var1.getBiome(var14);
							if (((((var13) == (null)) || ((var1.getRandom().nextFloat()) <= (0.33F))) ? (1) : (0)) != (0)){
								var13 = ((EntityType)(((Supplier)(new ArrayList(STYLE_TO_TRAINER_MAP.values()).get(var1.getRandom().nextInt(STYLE_TO_TRAINER_MAP.size())))).get()));
							}
							if ((((((double)(var1.getRandom().nextFloat())) <= (0.25D)) ? (1) : (0)) != (0)) && (var15.is(ModTags$Biomes.CAN_SPAWN_DUGONGS))){
								var13 = ((EntityType)(ModMobs.LEGENDARY_MASTER_DUGONG.get()));
							}
							if ((var13) == (null)){
								return;
							}else{
								BlockPos var16 = WyHelper.findOnGroundSpawnLocation(var1,var13,var14,20,5);
								if ((var16) == (null)){
									return;
								}else{
									if (((((TargetHelper.getEntitiesInArea((LevelAccessor)(var1),(LivingEntity)(var5),Vec3.atCenterOf((Vec3i)(var16)),100D,new TargetPredicate().selector(TRAINER_CHECK),LivingEntity.class).size()) < (2)) && (SpawnPlacements.isSpawnPositionOk(var13,(LevelReader)(var1),var16))) ? (1) : (0)) != (0)){
										var13.spawn(var1,((ItemStack)(null)),((Player)(null)),var16,MobSpawnType.EVENT,false,false);
										String var17 = String.valueOf((Object)(var13));
										String var18 = String.valueOf((Object)(var16));
										String var19 = "Trainer (" + var17 + ") spawned at: " + var18;
										WyDebug.debug(var19);
										var4++;
										continue;
									}else{
										var4++;
										continue;
									}
								}
							}
						}
					}
				}
			}else{
				break;
			}
		} while (true);
		var1.getProfiler().pop();
	}
	static  {
		Class var0 = ITrainer.class;
		Objects.requireNonNull(var0);
		Class var1 = var0;
		Predicate<Entity> var2 = var1::isInstance;
		TRAINER_CHECK = var2;
	}
}