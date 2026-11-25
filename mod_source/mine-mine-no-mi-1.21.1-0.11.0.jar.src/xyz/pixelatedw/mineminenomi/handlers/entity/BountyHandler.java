package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.helpers.BountyHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.items.components.WantedPosterData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.WantedPosterPackageEntity;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class BountyHandler {
	// Fields
	private static HashMap<Player, double[]> cachedPositions;

	public static boolean redeemBounty(ServerLevel var0, Player var1, LivingEntity var2) {
		EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var1)))).orElse((Object)(null))));
		EntityStatsData var4 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var2))).orElse((Object)(null))));
		FactionsWorldData var5 = FactionsWorldData.get();
		if (((var3) != (null)) && (((var4) != (null)) && (var3.isBountyHunter()))){
			StatChangeSource var6 = StatChangeSource.KILL_NPC;
			if (var2 instanceof Player){
				var6 = StatChangeSource.KILL_PLAYER;
			}
			int var7 = 0;
			int var8 = 0;
			do{
				if ((var8) < (var1.getInventory().getContainerSize())){
					ItemStack var9 = var1.getInventory().getItem(var8);
					if (var9.has(ModDataComponents.WANTED_POSTER_DATA)){
						WantedPosterData var10 = ((WantedPosterData)(var9.get(ModDataComponents.WANTED_POSTER_DATA)));
						if ((var10) == (null)){
							var8++;
							continue;
						}else{
							UUID var11 = var10.owner();
							if ((var11) == (null)){
								var8++;
								continue;
							}else{
								boolean var12 = var11.equals(var2.getUUID());
								long var13 = var5.getBounty(var2.getUUID());
								int var14 = ((var13) > (0L)) ? (1) : (0);
								if ((var12) && ((var14) != (0))){
									var5.issueBounty(var11,0L);
									var4.setBounty(0L);
									ItemsHelper.removeItemStackFromInventory((LivingEntity)(var1),var9);
									if (var3.alterBelly(var13,var6)){
										ModNetwork.sendTo(new SSyncEntityStatsPacket((LivingEntity)(var1)),var1);
										var7++;
										var8++;
										continue;
									}else{
										var8++;
										continue;
									}
								}else{
									var8++;
									continue;
								}
							}
						}
					}else{
						var8++;
						continue;
					}
				}else{
					break;
				}
			} while (true);
			if ((var7) > (0)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	public static void dropWantedPosters(ServerLevel var0) {
		ServerPlayer var5 = null;
		int var1 = Mth.clamp((var0.players().size()) / (2),1,10);
		Player[] var2 = new Player[var1];
		int var3 = 0;
		do{
			if ((var3) < (var2.length)){
				ServerPlayer var4 = var0.getRandomPlayer();
				if ((var4) == (null)){
					var3++;
					continue;
				}else{
					var5 = var4;
					final ServerPlayer var5_f1 = var5;
					Predicate<? super Player> var6 = (l0) -> {
			return (l0) == (var5_f1);
		};
					if (Arrays.stream(var2).anyMatch(var6)){
						var3++;
						continue;
					}else{
						double var7 = var4.getX();
						double var8 = var4.getZ();
						boolean var9 = false;
						double[] var10 = null;
						if (!(cachedPositions.containsKey(var4))){
							var10 = new double[]{var7,var8};
							((java.util.HashMap)(cachedPositions)).put((Object)(var4),(Object)(var10));
							var9 = true;
						}else{
							double[] var10_1 = ((double[])(cachedPositions.get(var4)));
							double var11 = var10_1[0];
							double var12 = var10_1[1];
							int var13 = ((Math.abs((var7) - (var11))) > (100D)) ? (1) : (0);
							int var14 = ((Math.abs((var8) - (var12))) > (100D)) ? (1) : (0);
							if (((var13) == (0)) && ((var14) == (0))){

							}else{
								cachedPositions.remove(var4);
								double[] var15 = new double[]{var7,var8};
								((java.util.HashMap)(cachedPositions)).put((Object)(var4),(Object)(var15));
								var9 = true;
							}
						}
						if (var9){
							WantedPosterPackageEntity var10_1_1 = new WantedPosterPackageEntity((Level)(var0));
							var10_1_1.moveTo((var4.getX()) + ((double)(WyHelper.randomWithRange(-10,10))),(var4.getY()) + (30D),(var4.getZ()) + ((double)(WyHelper.randomWithRange(-10,10))),0F,0F);
							var0.addFreshEntity((Entity)(var10_1_1));
							var3++;
							continue;
						}else{
							var3++;
							continue;
						}
					}
				}
			}else{
				break;
			}
		} while (true);
	}
	public static void updateEverybodysBounty(ServerLevel var0) {
		Iterator var1 = var0.players().iterator();
		do{
			if (var1.hasNext()){
				BountyHelper.issueBountyForPlayer(((Player)(var1.next())));
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public static void clearDropCache() {
		cachedPositions.clear();
	}
	static  {
		cachedPositions = (HashMap) (new HashMap());
	}
}