package xyz.pixelatedw.mineminenomi.world.spawners;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class AmbushSpawner {
	// Fields
	private Random random;
	private int cooldown;

	public AmbushSpawner() {
		this.random = new Random();
	}
	public void tick(ServerLevel var1) {
		var1.getProfiler().push("ambushSpawnerTick");
		int var2 = (this.cooldown) - (1);
		this.cooldown = var2;
		if ((var2) <= (0)){
			this.cooldown = ServerConfig.getTimeBetweenAmbushSpawns();
			if ((this.random.nextInt(100)) <= (ServerConfig.getChanceForAmbushSpawn())){
				this.spawn(var1);
			}
		}
		var1.getProfiler().pop();
	}
	public void spawn(ServerLevel var1) {
		var1.getProfiler().push("ambushSpawnerSpawn");
		ServerPlayer var2 = var1.getRandomPlayer();
		if ((var2) == (null)){
			return;
		}else{
			EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var2)))).orElse((Object)(null))));
			if ((var3) == (null)){
				return;
			}else{
				if ((!(var3.isPirate())) && ((!(var3.isBandit())) && (!(var3.isRevolutionary())))){

				}else{
					BlockPos var4 = var2.blockPosition();
					long var5 = var3.getBounty();
					boolean var6 = var2.level().canSeeSky(var4);
					if (((var5) >= (10000L)) && (var6)){
						EntityType var7 = ((EntityType)(ModMobs.MARINE_CAPTAIN.get()));
						EntityType var8 = ((EntityType)(ModMobs.MARINE_GRUNT.get()));
						int var9 = this.random.nextInt(2);
						int var10 = this.random.nextInt(2);
						if ((var9) == (1)){

						}
						if ((TargetHelper.getEntitiesInArea((LevelAccessor)(var1),(LivingEntity)(var2),80D,TargetPredicate.EVERYTHING,OPEntity.class).size()) > (50)){
							return;
						}else{
							int var11 = (0) + ((int)(Math.ceil((double)((var5) / (200000L)))));
							int var12 = (3) + ((int)(Math.ceil((double)((var5) / (100000L)))));
							String var13 = var7.getDescription().getString();
							if ((var11) > (3)){
								var11 = 3;
							}
							if ((var12) > (30)){
								var12 = 30;
							}
							if ((var11) > (0)){
								int var14 = 0;
								do{
									if ((var14) < (var11)){
										BlockPos var15 = WyHelper.findOnGroundSpawnLocation(var1,var7,var4,10,10);
										if ((var15) != (null)){
											var7.spawn(var1,((ItemStack)(null)),((Player)(null)),var15,MobSpawnType.EVENT,false,false);
										}
										var14++;
										continue;
									}else{
										break;
									}
								} while (true);
							}else{
								var13 = "Officer";
							}
							int var14 = 0;
							do{
								if ((var14) < (var12)){
									BlockPos var15_1 = WyHelper.findOnGroundSpawnLocation(var1,var8,var4,20,10);
									if ((var15_1) != (null)){
										Entity var16 = var8.spawn(var1,((ItemStack)(null)),((Player)(null)),var15_1,MobSpawnType.EVENT,false,false);
									}
									var14++;
									continue;
								}else{
									break;
								}
							} while (true);
							String var15_2 = var13;
							String var16_1 = "<" + var15_2 + "> We've come to arrest you, surrender now criminal scum!";
							MutableComponent var17 = Component.literal(var16_1);
							if ((var10) == (1)){
								String var18 = var13;
								String var19 = "<" + var18 + "> You're surrounded and have no escape, surrender now!";
								var17 = Component.literal(var19);
							}
							var2.level().playSound((Player)(null),var2.blockPosition(),SoundEvents.BELL_BLOCK,SoundSource.HOSTILE,1F,1F);
							WyHelper.sendMessage((LivingEntity)(var2),(Component)(var17));
							String var18_1 = String.valueOf((Object)(var4));
							String var19_1 = "Ambush spawned around these coords: " + var18_1;
							WyDebug.debug(var19_1);
						}
					}else{
						return;
					}
				}
				var1.getProfiler().pop();
				return;
			}
		}
	}
}