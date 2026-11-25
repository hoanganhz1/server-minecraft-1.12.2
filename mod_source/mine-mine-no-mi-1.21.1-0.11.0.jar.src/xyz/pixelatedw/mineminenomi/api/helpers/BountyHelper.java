package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;

public class BountyHelper {
	public static boolean issueBountyForPlayer(Player var0) {
		Level var1 = var0.level();
		if (((var1) != (null)) && (!(var1.isClientSide))){
			FactionsWorldData var2 = FactionsWorldData.get();
			Optional var3 = EntityStatsData.get((LivingEntity)(var0));
			long var4 = ((Long)(var3.map((Function)(((Function<EntityStatsData, Long>)(EntityStatsData::getBounty)))).orElse(Long.valueOf(0L)))).longValue();
			Player var5 = var0;
			final Player var5_f1 = var5;
			Function<Faction, Boolean> var6 = (l0) -> {
			return Boolean.valueOf(l0.canReceiveBounty((LivingEntity)(var5_f1)));
		};
			if ((((Boolean)(((java.util.Optional)(var3.flatMap((Function)(((Function<EntityStatsData, Optional>)(EntityStatsData::getFaction)))))).map((Function)(var6)).orElse(Boolean.valueOf(false)))).booleanValue()) && ((var4) > (1000L))){
				var2.issueBounty(var0.getUUID(),var4);
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	public static void issueBountyForAllPlayers(Level var0) {
		if (!(var0 instanceof ServerLevel)){
			return;
		}else{
			Iterator var1 = var0.players().iterator();
			do{
				if (var1.hasNext()){
					issueBountyForPlayer(((Player)(var1.next())));
					continue;
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
}