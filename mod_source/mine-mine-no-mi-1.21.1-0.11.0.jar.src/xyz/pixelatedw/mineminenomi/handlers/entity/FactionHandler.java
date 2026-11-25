package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.factions.IFactionRank;
import xyz.pixelatedw.mineminenomi.api.factions.MarineRank;
import xyz.pixelatedw.mineminenomi.api.helpers.StructuresHelper;
import xyz.pixelatedw.mineminenomi.commands.FGCommand;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.items.SakeCupItem;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class FactionHandler {
	public static void tryLowerLoyaltyFromStealing(Player var0) {
		Player var13 = null;
		if ((!(var0.level().isClientSide)) && ((!(var0.isCreative())) && ((!(var0.isSpectator())) && (!(var0.hasEffect((Holder)(ModEffects.SILENT))))))){
			EntityStatsData var1 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var0)))).orElse((Object)(null))));
			if ((var1) == (null)){
				return;
			}else{
				ServerPlayer var2 = ((ServerPlayer)(var0));
				int var3 = 0;
				boolean var4 = StructuresHelper.isInsideMarineStructure(var2);
				boolean var5 = StructuresHelper.isInsideRevoStructure(var2);
				if (var4){
					var3 = (var3) | (((var1.isMarine()) && (!(var1.hasRank(MarineRank.CAPTAIN)))) ? (1) : (0));
					var3 = (var3) | (((var1.isBountyHunter()) ? 1 : 0));
				}else{
					if (var5){
						var3 = (var3) | (((var1.isRevolutionary()) ? 1 : 0));
					}
				}
				int var6 = ((var4) || (var5)) ? (1) : (0);
				if ((var3) != (0)){
					var1.alterLoyalty(-0.25D,StatChangeSource.NATURAL);
					ModNetwork.sendTo(new SSyncEntityStatsPacket((LivingEntity)(var0),(EntityStatsData)(var1)),var0);
				}else{
					if ((var6) != (0)){
						boolean var7 = var4;
						boolean var8 = var5;
						final boolean var7_f1 = var7;
						final boolean var8_f2 = var8;
						Predicate<Mob> var9 = (l0) -> {
			EntityStatsData lv1_3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(l0)))).orElse((Object)(null))));
			if ((lv1_3) == (null)){
				return false;
			}else{
				if ((var7_f1) && (lv1_3.isMarine())){
					return true;
				}else{
					if ((var8_f2) && (lv1_3.isRevolutionary())){
						return true;
					}else{
						return false;
					}
				}
			}
		};
						Predicate<Mob> var10 = var9;
						double var11 = ((var0.level().getDifficulty()) == (Difficulty.HARD)) ? (64D) : (32D);
						List var12 = ((List)(((java.util.stream.Stream)(((java.util.stream.Stream)(var0.level().getEntitiesOfClass(Mob.class,var0.getBoundingBox().inflate(var11)).stream())).filter((Predicate)(var10)))).collect((Collector)(Collectors.toList()))));
						if ((var12.size()) > (0)){
							var13 = var0;
							final Player var13_f1 = var13;
							Consumer<Mob> var14 = (l0) -> {
			l0.setTarget((LivingEntity)(var13_f1));
		};
							((java.util.stream.Stream)(var12.stream())).forEach((Consumer)(var14));
						}else{
							return;
						}
					}else{
						return;
					}
				}
				return;
			}
		}else{
			return;
		}
	}
	public static boolean tryInvitingToCrew(Player var0, LivingEntity var1) {
		if ((var0.getMainHandItem().getItem()) != (ModItems.SAKE_BOTTLE.get())){
			return false;
		}else{
			if ((var1.getMainHandItem().getItem()) != (ModItems.SAKE_CUP.get())){
				return false;
			}else{
				EntityStatsData var2 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var1))).orElse((Object)(null))));
				if ((var2) == (null)){
					return false;
				}else{
					ItemStack var3 = var1.getMainHandItem();
					if (SakeCupItem.hasLeaderSet(var3)){
						return false;
					}else{
						var2.setFaction(((Faction)(ModFactions.PIRATE.get())));
						SakeCupItem.setLeader(var3,var0);
						var0.getMainHandItem().hurtAndBreak(1,(LivingEntity)(var0),EquipmentSlot.MAINHAND);
						return true;
					}
				}
			}
		}
	}
	public static boolean tryBlockingFriendlyFire(LivingEntity var0, LivingEntity var1, DamageSource var2, float var3) {
		if (!(ServerConfig.isFriendlyDamageDisabled())){
			return false;
		}else{
			if (IDamageSourceHandler.getHandler(var2).isBypassingFriendly()){
				return false;
			}else{
				if (((java.util.function.Predicate)(ModEntityPredicates.getFriendlyFactions(var0))).test((Object)(var1))){
					if (FGCommand.SHOW_DEBUG_FRIENDLY){
						String var4 = var1.getDisplayName().getString();
						String var5 = var0.getDisplayName().getString();
						String var6 = var4 + " is in the same group as the attacker (" + var5 + ")!";
						WyDebug.debug(var6);
					}
					return true;
				}else{
					return false;
				}
			}
		}
	}
}