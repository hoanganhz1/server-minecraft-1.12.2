package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.abilities.haki.KenbunshokuHakiAuraAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.KenbunshokuHakiFutureSightAbility;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.poi.INotoriousTarget;
import xyz.pixelatedw.mineminenomi.commands.FGCommand;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.entities.mobs.NotoriousEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.BlackKnightEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.DoppelmanEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.MirageCloneEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.NightmareSoldierEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.WaxCloneEntity;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class StatsGainHandler {
	// Fields
	private static final double HEALTH_STAT_MAX = 700D;
	private static final double ATTACK_STAT_MAX = 30D;

	public static void setSpawnStats(Mob var0) {
		AttributeInstance var1 = var0.getAttributes().getInstance(Attributes.ATTACK_DAMAGE);
		AttributeInstance var2 = var0.getAttributes().getInstance(Attributes.MAX_HEALTH);
		if (((var1) != (null)) && ((var2) != (null))){
			int var11 = 0;
			double var12 = 0.0;
			double var9 = 0.0;
			int var3 = ((var0.level().getDifficulty().getId()) > (Difficulty.NORMAL.getId())) ? (1) : (0);
			boolean var4 = false;
			double var5 = var1.getValue();
			double var6 = var2.getValue();
			EntityStatsData var7 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var0)))).orElse((Object)(null))));
			HakiData var8 = ((HakiData)(((java.util.Optional)(HakiData.get((LivingEntity)(var0)))).orElse((Object)(null))));
			double var10 = 0.0;
			long var13 = 0;
			if (((var7) != (null)) && ((var7.getDoriki()) <= (0D))){
				var9 = 10000D;
				var10 = ((((var6) / (700D)) * (0.3D)) + (((var5) / (30D)) * (0.7D))) * ((var9) * (0.8D));
				var11 = (int)(Math.round((var10) / (10D)));
				if ((var11) > (0)){
					var10 = (var10) + ((double)(var0.getRandom().nextInt(var11)));
				}
				if ((var3) != (0)){
					var10 = (var10) + ((double)(WyHelper.randomWithRange(250,750)));
				}
				var12 = Mth.clamp(var10,50D,var9);
				var13 = (long)((var12) / (100D));
				int var14 = Math.round(((float)(var13)) / (2F));
				if ((var14) > (0)){
					var13 = (var13) + ((long)(var0.getRandom().nextInt(var14)));
				}
				long var15 = Mth.clamp(var13,1L,999999999L);
				var7.setDoriki(var12);
				var7.setBelly(var15);
				var4 = true;
			}
			double var10_1 = 0.0;
			double var10_2 = 0.0;
			if ((var8) != (null)){
				var9 = 100D;
				if ((var8.getKenbunshokuHakiExp()) <= (0F)){
					var10_1 = ((((var6) / (700D)) * (0.6D)) + (((var5) / (30D)) * (0.4D))) * ((var9) * (0.8D));
					var11 = (int)(Math.round((var10_1) / (10D)));
					if ((var11) > (0)){
						var10_1 = (var10_1) + ((double)(var0.getRandom().nextInt(var11)));
					}
					if ((var3) != (0)){
						var10_1 = (var10_1) + ((double)(WyHelper.randomWithRange(5,10)));
					}
					var12 = Mth.clamp(var10_1,2.5D,var9);
					var8.setKenbunshokuHakiExp((float)(var12));
					var4 = true;
				}
				if ((var8.getBusoshokuHakiExp()) <= (0F)){
					var10_2 = ((((var6) / (700D)) * (0.4D)) + (((var5) / (30D)) * (0.6D))) * ((var9) * (0.8D));
					var11 = (int)(Math.round((var10_2) / (10D)));
					if ((var11) > (0)){
						var10_2 = (var10_2) + ((double)(var0.getRandom().nextInt(var11)));
					}
					if ((var3) != (0)){
						var10_2 = (var10_2) + ((double)(WyHelper.randomWithRange(5,10)));
					}
					var12 = Mth.clamp(var10_2,2.5D,var9);
					var8.setBusoshokuHakiExp((float)(var12));
					var4 = true;
				}
			}
			if ((var4) && ((FGCommand.SHOW_DEBUG_STATS) && (((var7) != (null)) && ((var8) != (null))))){
				String var9_1 = var0.getName().getString();
				double var10_3 = var7.getDoriki();
				long var11_1 = var7.getBelly();
				float var12_1 = var8.getBusoshokuHakiExp();
				float var13_1 = var8.getKenbunshokuHakiExp();
				String var14_1 = "\nTarget: " + var9_1 + "\n Doriki: " + var10_3 + "\n Belly: " + var11_1 + "\n Haki(B/K): " + var12_1 + "/" + var13_1;
				WyDebug.debug(var14_1);
			}
			return;
		}else{
			return;
		}
	}
	public static void givePassiveKenbunshokuExp(Player var0) {
		AbilityData var1 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		HakiData var2 = ((HakiData)(((java.util.Optional)(HakiData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if (((var1) != (null)) && ((var2) != (null))){
			KenbunshokuHakiFutureSightAbility var3 = ((KenbunshokuHakiFutureSightAbility)(var1.getEquippedAbility(((AbilityCore)(KenbunshokuHakiFutureSightAbility.INSTANCE.get())))));
			if (((var3) != (null)) && ((var3.isContinuous()) && (((var2.getKenbunshokuHakiExp()) >= (60F)) && (((var0.tickCount) % (600)) == (0))))){
				var2.alterKenbunshokuHakiExp((float)((0.02500000037252903D) * (ServerConfig.getHakiExpMultiplier())),StatChangeSource.NATURAL);
			}
			return;
		}else{
			return;
		}
	}
	public static void giveOnHitKenbunshokuExp(Player var0, DamageSource var1, float var2) {
		Entity var3 = var1.getDirectEntity();
		if ((var3 instanceof NuProjectileEntity) && (!(((NuProjectileEntity)(var3)).isPhysical()))){
			return;
		}else{
			AbilityData var4 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var0)))).orElse((Object)(null))));
			HakiData var5 = ((HakiData)(((java.util.Optional)(HakiData.get((LivingEntity)(var0)))).orElse((Object)(null))));
			if (((var4) != (null)) && ((var5) != (null))){
				KenbunshokuHakiAuraAbility var6 = ((KenbunshokuHakiAuraAbility)(var4.getEquippedAbility(((AbilityCore)(KenbunshokuHakiAuraAbility.INSTANCE.get())))));
				if ((((var6) != (null)) && (var6.isContinuous())) || (((double)(var5.getKenbunshokuHakiExp())) <= ((30D) + (HakiHelper.getKenbunshokuAuraExpNeeded((LivingEntity)(var0)))))){
					float var7 = (var2) / ((20F) + ((100F) * ((var5.getKenbunshokuHakiExp()) / (100F))));
					if ((var7) <= (0F)){
						var7 = 1e-05F;
					}
					var5.alterKenbunshokuHakiExp((var7) * ((float)(ServerConfig.getHakiExpMultiplier())),StatChangeSource.NATURAL);
				}
				return;
			}else{
				return;
			}
		}
	}
	public static void giveBusoshokuExp(LivingEntity var0, DamageSource var1) {
		Player var2 = null;
		boolean var3 = false;
		boolean var4 = false;
		Entity var5 = var1.getEntity();
		if (var5 instanceof Player){
			var2 = ((Player)(var5));
			ItemStack var6 = var2.getMainHandItem();
			var3 = var6.isEmpty();
			var4 = ItemsHelper.isImbuable(var6);
		}else{
			Entity var6_1 = var1.getEntity();
			if (var6_1 instanceof NuProjectileEntity){
				NuProjectileEntity var7 = ((NuProjectileEntity)(var6_1));
				LivingEntity var8 = var7.getOwner();
				if (var8 instanceof Player){
					Player var9 = ((Player)(var8));
					if (var7.isPhysical()){
						var2 = var9;
						var3 = var7.isAffectedByHardening();
						var4 = var7.isAffectedByImbuing();
					}
				}
			}
		}
		if ((var2) == (null)){
			return;
		}else{
			HakiData var6_2 = ((HakiData)(((java.util.Optional)(HakiData.get((LivingEntity)(var2)))).orElse((Object)(null))));
			if ((var6_2) == (null)){
				return;
			}else{
				AttributeInstance var7_1 = var0.getAttributes().getInstance(Attributes.ATTACK_DAMAGE);
				AttributeInstance var8_1 = var0.getAttributes().getInstance(Attributes.MAX_HEALTH);
				double var9_1 = ((var7_1) != (null)) ? (var7_1.getBaseValue()) : (0D);
				float var10 = (float)((var9_1) + (((var8_1) != (null)) ? (var8_1.getBaseValue()) : (0D)));
				int var11 = 2;
				boolean var12 = false;
				if (var1 instanceof AbilityDamageSource){
					AbilityDamageSource var13 = ((AbilityDamageSource)(var1));
					var3 = (var3) & ((var13.getHakiNature()) == (SourceHakiNature.HARDENING));
					var4 = (var4) & ((var13.getHakiNature()) == (SourceHakiNature.IMBUING));
				}
				if ((!(var3)) && (!(var4))){

				}else{
					if ((var10) < (var6_2.getBusoshokuHakiExp())){
						var11 = 10;
					}
					boolean var13_1 = HakiHelper.hasHardeningActive((LivingEntity)(var2));
					boolean var14 = HakiHelper.hasImbuingActive((LivingEntity)(var2));
					if ((!(var13_1)) && ((!(var14)) && (!(((double)(var6_2.getBusoshokuHakiExp())) <= ((30D) + (HakiHelper.getBusoshokuHardeningExpNeeded((LivingEntity)(var2)))))))){

					}else{
						var12 = true;
					}
				}
				if (var12){
					StatChangeSource var13_2 = (var0 instanceof Player) ? (StatChangeSource.KILL_PLAYER) : (StatChangeSource.KILL_NPC);
					float var14_1 = var6_2.getBusoshokuHakiExp();
					var6_2.alterBusoshokuHakiExp((float)(((double)(Math.max(0.001F,(((var10) * (((float)(ServerConfig.getHakiExpLimit())) - (var14_1))) / ((float)(var11))) / (10000F)))) * (ServerConfig.getHakiExpMultiplier())),var13_2);
				}
				return;
			}
		}
	}
	public static void increaseLoyaltyOverTime(Player var0) {
		EntityStatsData var1 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if ((var1) == (null)){
			return;
		}else{
			Player var2 = var0;
			final Player var2_f1 = var2;
			Function<Faction, Boolean> var3 = (l0) -> {
			return Boolean.valueOf(l0.canReceiveLoyalty((LivingEntity)(var2_f1)));
		};
			if ((((Boolean)(((java.util.Optional)(var1.getFaction())).map((Function)(var3)).orElse(Boolean.valueOf(false)))).booleanValue()) && (!((var1.getLoyalty()) >= (100D)))){
				if (((var1.getDoriki()) < (4000D)) && ((var1.getLoyalty()) > (15D))){
					return;
				}else{
					if (((var0.level().getGameTime()) % (24000L)) == (0L)){
						var1.alterLoyalty((double)(Mth.floor((1D) * (ServerConfig.getLoyaltyMultiplier()))),StatChangeSource.NATURAL);
					}
					return;
				}
			}else{
				return;
			}
		}
	}
	public static void giveStats(LivingEntity var0, DamageSource var1) {
		if (var0.level().isClientSide){
			return;
		}else{
			if (!(ServerConfig.isMobRewardsEnabled())){
				return;
			}else{
				if (NuWorld.isChallengeDimension(var0.level())){
					return;
				}else{
					Player var2 = null;
					Entity var3 = var1.getEntity();
					if (var3 instanceof Player){
						var2 = ((Player)(var3));
					}else{
						if (var1 instanceof AbilityDamageSource){
							Entity var4 = var1.getDirectEntity();
							if (var4 instanceof Player){
								var2 = ((Player)(var4));
							}
						}else{
							if ((var0.getLastAttacker()) != (null)){
								LivingEntity var4_1 = var0.getLastAttacker();
								if (var4_1 instanceof Player){
									var2 = ((Player)(var4_1));
								}
							}
						}
					}
					if ((var2) == (null)){
						return;
					}else{
						EntityStatsData var4_2 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var2)))).orElse((Object)(null))));
						if ((var4_2) == (null)){
							return;
						}else{
							long var5 = 0L;
							long var6 = 0L;
							double var7 = 0D;
							StatChangeSource var8 = StatChangeSource.KILL_NPC;
							Optional var9 = EntityStatsData.get(var0);
							boolean var10 = ((Boolean)(var9.map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isMarine)))).orElse(Boolean.valueOf(false)))).booleanValue();
							boolean var11 = ((Boolean)(var9.map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isBountyHunter)))).orElse(Boolean.valueOf(false)))).booleanValue();
							boolean var12 = ((Boolean)(var9.map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isRevolutionary)))).orElse(Boolean.valueOf(false)))).booleanValue();
							double var13 = ((Double)(var9.map((Function)(((Function<EntityStatsData, Double>)(EntityStatsData::getDoriki)))).orElse(Double.valueOf(0D)))).doubleValue();
							long var14 = ((Long)(var9.map((Function)(((Function<EntityStatsData, Long>)(EntityStatsData::getBounty)))).orElse(Long.valueOf(0L)))).longValue();
							long var15 = ((Long)(var9.map((Function)(((Function<EntityStatsData, Long>)(EntityStatsData::getBelly)))).orElse(Long.valueOf(0L)))).longValue();
							if ((!(var4_2.isMarine())) && (!(var4_2.isBountyHunter()))){

							}else{
								if ((!(var10)) && (!(var11))){
									if (var0 instanceof INotoriousTarget){
										var4_2.alterLoyalty(0.5D,(var0 instanceof Player) ? (StatChangeSource.KILL_PLAYER) : (StatChangeSource.KILL_NPC));
									}
								}else{
									var4_2.alterLoyalty(-0.5D,(var0 instanceof Player) ? (StatChangeSource.KILL_PLAYER) : (StatChangeSource.KILL_NPC));
								}
							}
							double var16 = 0.0;
							long var17 = 0;
							long var18 = 0;
							if (var0 instanceof Player){
								var16 = ((var13) - (WyHelper.percentage((double)(ServerConfig.getDorikiKeepPercentage()),var13))) / (4D);
								var18 = (long)((((double)(var14)) - (WyHelper.percentage((double)(ServerConfig.getBountyKeepPercentage()),(double)(var14)))) / (2D));
								var17 = (long)(((double)(var15)) - (WyHelper.percentage((double)(ServerConfig.getBellyKeepPercentage()),(double)(var15))));
								var8 = StatChangeSource.KILL_PLAYER;
							}else{
								if ((!(var0 instanceof DoppelmanEntity)) && ((!(var0 instanceof NightmareSoldierEntity)) && ((!(var0 instanceof MirageCloneEntity)) && ((!(var0 instanceof BlackKnightEntity)) && (!(var0 instanceof WaxCloneEntity)))))){
									if ((var4_2.isMarine()) && (var10)){
										return;
									}else{
										if ((var4_2.isRevolutionary()) && (var12)){
											return;
										}else{
											if ((var0.level().getServer().reloadableRegistries().getLootTable(var0.getLootTable()).getPool("mineminenomi:stats")) != (null)){
												return;
											}else{
												if ((var13) > (0D)){
													var7 = (var13) / (100D);
													if ((var4_2.getDoriki()) > (var13)){
														var7 = (var13) / (10000D);
													}
													if (((var7) < (1D)) && (ServerConfig.isMinimumDorikiPerKillEnabled())){
														var7 = 1D;
													}
													var6 = ((long)(var13)) / (20L);
													var5 = var15;
												}
												if (var0 instanceof Villager){
													var6 = 250L;
												}else{
													if ((var0 instanceof NotoriousEntity) && ((var7) < (100D))){
														var7 = 100D;
													}
												}
												var16 = (var7) * (ServerConfig.getDorikiRewardMultiplier());
												var18 = (long)(((double)(var6)) * (ServerConfig.getBountyRewardMultiplier()));
												var17 = (long)(((double)(var5)) * (ServerConfig.getBellyRewardMultiplier()));
												var8 = StatChangeSource.KILL_NPC;
											}
										}
									}
								}else{
									return;
								}
							}
							if (FGCommand.SHOW_DEBUG_REWARDS){
								double var19 = var16;
								long var20 = var17;
								long var21 = var18;
								String var22 = "Stats Gain\nDoriki: " + var19 + "\nBelly: " + var20 + "\nBounty: " + var21;
								WyDebug.debug(var22);
							}
							if ((var16) > (0D)){
								var4_2.alterDoriki(var16,var8);
							}
							boolean var19_1 = false;
							if (var4_2.getFaction().isPresent()){
								var19_1 = ((Faction)(var4_2.getFaction().get())).canReceiveBounty((LivingEntity)(var2));
							}
							if ((var19_1) && ((var18) > (0L))){
								var4_2.alterBounty(var18,var8);
							}
							if ((var17) > (0L)){
								var4_2.alterBelly(var17,var8);
							}
							ModNetwork.sendTo(new SSyncEntityStatsPacket((LivingEntity)(var2)),var2);
							return;
						}
					}
				}
			}
		}
	}
}