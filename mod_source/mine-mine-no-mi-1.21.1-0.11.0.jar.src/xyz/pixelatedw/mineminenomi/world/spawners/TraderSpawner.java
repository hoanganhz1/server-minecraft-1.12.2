package xyz.pixelatedw.mineminenomi.world.spawners;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
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
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.entities.mobs.TraderEntity;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class TraderSpawner {
	// Fields
	private Random random;
	private int cooldown;
	private static final List<Supplier<? extends EntityType<? extends TraderEntity>>> TRADER_TYPES;

	public TraderSpawner() {
		this.random = new Random();
	}
	public void tick(ServerLevel var1) {
		var1.getProfiler().push("traderSpawnerTick");
		int var2 = (this.cooldown) - (1);
		this.cooldown = var2;
		if ((var2) <= (0)){
			this.cooldown = ServerConfig.getTimeBetweenTraderSpawns();
			if ((this.random.nextInt(100)) <= (ServerConfig.getChanceForTraderSpawn())){
				this.spawn(var1);
			}
		}
		var1.getProfiler().pop();
	}
	public void spawn(ServerLevel var1) {
		ServerPlayer var6 = null;
		var1.getProfiler().push("traderSpawnerSpawn");
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
						int var8 = this.random.nextInt(TRADER_TYPES.size());
						EntityType var9 = ((EntityType)(((Supplier)(TRADER_TYPES.get(var8))).get()));
						BlockPos var10 = WyHelper.findOnGroundSpawnLocation(var1,var9,var5.blockPosition(),20,5);
						List var11 = TargetHelper.getEntitiesInArea((LevelAccessor)(var1),(LivingEntity)(var5),40D,TargetPredicate.EVERYTHING,TraderEntity.class);
						if ((var10) == (null)){
							return;
						}else{
							if (((((var11.size()) < (3)) && (SpawnPlacements.isSpawnPositionOk(var9,(LevelReader)(var1),var10))) ? (1) : (0)) != (0)){
								var9.spawn(var1,((ItemStack)(null)),((Player)(null)),var10,MobSpawnType.EVENT,false,false);
								String var12 = String.valueOf((Object)(var9));
								String var13 = String.valueOf((Object)(var10));
								String var14 = "Trader (" + var12 + ") spawned at: " + var13;
								WyDebug.debug(var14);
								var4++;
								continue;
							}else{
								var4++;
								continue;
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
		Supplier[] var0 = new Supplier[]{ModMobs.MARINE_TRADER,ModMobs.PIRATE_TRADER};
		TRADER_TYPES = (List) (Arrays.asList(var0));
	}
}