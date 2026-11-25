package xyz.pixelatedw.mineminenomi.world.spawners;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.NaturalSpawner;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class NewWorldSpawner {
	// Fields
	private static final int COOLDOWN = 6000;
	public static final WeightedList<NewWorldSpawner$NewWorldSpawn> SPAWNS = createSpawns();
	private int cooldown;

	private static final WeightedList<NewWorldSpawner$NewWorldSpawn> createSpawns() {
		WeightedList var0 = new WeightedList(new Object[0]);
		var0.add(new NewWorldSpawner$NewWorldSpawn(ModMobs.MARINE_BRUTE,2,3),200);
		var0.add(new NewWorldSpawner$NewWorldSpawn(ModMobs.MARINE_SNIPER,1,2),200);
		var0.add(new NewWorldSpawner$NewWorldSpawn(ModMobs.MARINE_CAPTAIN,1,1),100);
		var0.add(new NewWorldSpawner$NewWorldSpawn(ModMobs.PACIFISTA,1,2),100);
		var0.add(new NewWorldSpawner$NewWorldSpawn(ModMobs.PIRATE_BRUTE,2,3),200);
		var0.add(new NewWorldSpawner$NewWorldSpawn(ModMobs.PIRATE_CAPTAIN,1,1),100);
		var0.add(new NewWorldSpawner$NewWorldSpawn(ModMobs.BANDIT_BRUTE,2,3),200);
		var0.add(new NewWorldSpawner$NewWorldSpawn(ModMobs.BANDIT_SNIPER,1,2),200);
		var0.add(new NewWorldSpawner$NewWorldSpawn(ModMobs.BANDIT_CAPTAIN,1,1),100);
		return var0;
	}
	public void tick(ServerLevel var1) {
		int var2 = (this.cooldown) - (1);
		this.cooldown = var2;
		if ((var2) <= (0)){
			this.cooldown = 6000;
			this.spawn(var1);
		}
	}
	public void spawn(ServerLevel var1) {
		ServerPlayer var2 = var1.getRandomPlayer();
		if ((var2) == (null)){
			return;
		}else{
			if (((var1.getTimeOfDay(0F)) > (0.25F)) && ((var1.getTimeOfDay(0F)) < (0.8F))){
				return;
			}else{
				BlockPos var3 = var2.blockPosition();
				if (!(var1.canSeeSky(var3))){
					return;
				}else{
					if ((Math.sqrt(var3.distSqr((Vec3i)(BlockPos.ZERO)))) < (ServerConfig.getNewWorldStartRadius())){
						return;
					}else{
						NewWorldSpawner$NewWorldSpawn var4 = ((NewWorldSpawner$NewWorldSpawn)(SPAWNS.pick(var1.random)));
						MobCategory var5 = ((EntityType)(var4.entityType.get())).getCategory();
						if ((var1.getChunkSource().getLastSpawnState().getMobCategoryCounts().getInt(var5)) >= ((var5.getMaxInstancesPerChunk()) * (2))){
							String var6 = String.valueOf((Object)(var5));
							String var7 = var6 + " reached its limit for New World spawns!";
							WyDebug.debug(var7);
							return;
						}else{
							int var6_1 = WyHelper.randomWithRange(var1.random,var4.min,var4.max);
							BlockPos var7_1 = WyHelper.findOnGroundSpawnLocation(var1,((EntityType)(var4.entityType.get())),var3,60,30);
							if ((var7_1) != (null)){
								int var8 = 0;
								do{
									if ((var8) < (var6_1)){
										((EntityType)(var4.entityType.get())).spawn(var1,((ItemStack)(null)),((Player)(null)),var7_1,MobSpawnType.EVENT,false,false);
										var8++;
										continue;
									}else{
										break;
									}
								} while (true);
							}
							String var8_1 = String.valueOf((Object)(var3));
							String var9 = "New World group spawned around these coords: " + var8_1;
							WyDebug.debug(var9);
							return;
						}
					}
				}
			}
		}
	}
}