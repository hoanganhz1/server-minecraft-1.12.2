package xyz.pixelatedw.mineminenomi.api.helpers;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.Crew$Member;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class FactionHelper {
	// Fields
	public static final Color CIVILIAN_COLOR = WyHelper.hexToRGB("#55FF55");
	public static final Color MARINE_COLOR = WyHelper.hexToRGB("#55DDFF");
	public static final Color PIRATE_COLOR = WyHelper.hexToRGB("#FF2200");
	public static final Color REVO_COLOR = PIRATE_COLOR;
	public static final Color BOUNTY_HUNTER_COLOR = WyHelper.hexToRGB("#BBFF88");
	public static final Color BANDIT_COLOR = WyHelper.hexToRGB("#925959");

	@Nullable()
	public static @Nullable() ResourceLocation getFactionIcon(EntityStatsData var0) {
		ResourceLocation var1 = null;
		if (var0.isPirate()){
			var1 = ModResources.PIRATE_ICON;
		}else{
			if (var0.isMarine()){
				var1 = ModResources.MARINE_ICON_GREYSCALE;
			}else{
				if (var0.isBountyHunter()){
					var1 = ModResources.BOUNTY_HUNTER_ICON_GREYSCALE;
				}else{
					if (var0.isRevolutionary()){
						var1 = ModResources.REVOLUTIONARY_ARMY_ICON_GREYSCALE;
					}
				}
			}
		}
		return var1;
	}
	public static Color getFactionColor(EntityStatsData var0) {
		if ((var0) == (null)){
			return CIVILIAN_COLOR;
		}else{
			if (var0.isPirate()){
				return PIRATE_COLOR;
			}else{
				if (var0.isRevolutionary()){
					return REVO_COLOR;
				}else{
					if (var0.isMarine()){
						return MARINE_COLOR;
					}else{
						if (var0.isBountyHunter()){
							return BOUNTY_HUNTER_COLOR;
						}else{
							if (var0.isBandit()){
								return BANDIT_COLOR;
							}else{
								return CIVILIAN_COLOR;
							}
						}
					}
				}
			}
		}
	}
	public static int getFactionRGBColor(EntityStatsData var0) {
		return getFactionColor(var0).getRGB();
	}
	public static void sendMessageToCrew(Level var0, Crew var1, Component var2) {
		Iterator var3 = var1.getMembers().iterator();
		do{
			if (var3.hasNext()){
				Player var4 = var0.getPlayerByUUID(((Crew$Member)(var3.next())).getUUID());
				if (((var4) != (null)) && (var4.isAlive())){
					var4.sendSystemMessage(var2);
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	public static boolean isFriendlyFactions(LivingEntity var0, LivingEntity var1) {
		return !(isEnemyFactions(var0,var1));
	}
	public static boolean isEnemyFactions(LivingEntity var0, LivingEntity var1) {
		if ((var0) == (null)){
			return true;
		}else{
			if ((var0) == (var1)){
				return false;
			}else{
				if (((!(EntitySelector.NO_SPECTATORS.test(var1))) ? (1) : (0)) != (0)){
					return false;
				}else{
					if ((var0.level() instanceof ServerLevel) && ((NuWorld.isChallengeDimension(var0.level())) && ((ChallengesWorldData.get().getInProgressChallengeFor(((ServerLevel)(var0.level())))) != (null)))){
						LivingEntity var2 = MobsHelper.getTamer(var0);
						LivingEntity var3 = MobsHelper.getTamer(var1);
						if (((var2) != (null)) && (((var3) != (null)) && ((var2) == (var3)))){
							return false;
						}else{
							if ((var2) != (null)){
								var0 = var2;
							}
							if ((var3) != (null)){
								var1 = var3;
							}
						}
					}
					if ((var1.isVehicle()) && (((var1.getControllingPassenger()) != (null)) && (((java.util.function.Predicate)(ModEntityPredicates.getFriendlyFactions(var0))).test((Object)(var1.getControllingPassenger()))))){
						return false;
					}else{
						EntityStatsData var2_1 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var0))).orElse((Object)(null))));
						EntityStatsData var3_1 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var1))).orElse((Object)(null))));
						if (((var2_1) != (null)) && ((var3_1) != (null))){
							boolean var4 = var2_1.isRogue();
							LivingEntity var5 = MobsHelper.getTamer(var0);
							if ((var5) != (null)){
								var4 = ((Boolean)(EntityStatsData.get(var5).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isRogue)))).orElse(Boolean.valueOf(false)))).booleanValue();
							}
							if ((((var5) != (null)) && ((var5) == (var1))) || ((!(var4)) && (!(var3_1.isRogue())))){
								if ((!(MobsHelper.isTamedBy(var0,var1))) && (!(MobsHelper.isTamedBy(var1,var0)))){
									if ((MobsHelper.canBeTamed(var0)) && (MobsHelper.isTamedBy(var1,MobsHelper.getTamer(var0)))){
										return false;
									}else{
										Faction var6 = ((Faction)(((java.util.Optional)(var2_1.getFaction())).orElse((Object)(null))));
										if ((var6) == (null)){
											return true;
										}else{
											return var6.canHurt(var0,var1);
										}
									}
								}else{
									return false;
								}
							}else{
								return true;
							}
						}else{
							return true;
						}
					}
				}
			}
		}
	}
}