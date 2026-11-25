package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeArena$SpawnPosition;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;

public class ChallengesHelper {
	public static ChallengeArena$SpawnPosition[] get4DefaultSpawnPoints(BlockPos var0, float var1) {
		return get4DefaultSpawnPoints(var0,var1,0F);
	}
	public static ChallengeArena$SpawnPosition[] get4DefaultSpawnPoints(BlockPos var0, float var1, float var2) {
		return get4DefaultSpawnPoints(var0,var1,0F,1);
	}
	public static ChallengeArena$SpawnPosition[] get4DefaultSpawnPoints(BlockPos var0, float var1, float var2, int var3) {
		ChallengeArena$SpawnPosition var4 = new ChallengeArena$SpawnPosition(var0,var1,0F);
		ChallengeArena$SpawnPosition var5 = new ChallengeArena$SpawnPosition(var0.offset(-(var3),0,0),var1,0F);
		ChallengeArena$SpawnPosition var6 = new ChallengeArena$SpawnPosition(var0.offset(0,0,-(var3)),var1,0F);
		ChallengeArena$SpawnPosition var7 = new ChallengeArena$SpawnPosition(var0.offset(-(var3),0,-(var3)),var1,0F);
		ChallengeArena$SpawnPosition[] var8 = new ChallengeArena$SpawnPosition[]{var4,var5,var6,var7};
		return var8;
	}
	public static Map<String, List<ChallengeCore<?>>> getChallengesByGroup(String var0) {
		ArrayList var1 = new ArrayList();
		List var2 = var1;
		final List var2_f1 = var2;
		Consumer var3 = (Consumer) ((Consumer<ChallengeCore>) ((l0) -> {
			var2_f1.add(l0);
		}));
		WyRegistry.CHALLENGES.stream().forEach(var3);
		return ((Map)(((java.util.stream.Stream)(var1.stream())).collect((Collector)(Collectors.groupingBy((Function)(((Function<ChallengeCore, String>)(ChallengeCore::getCategory))))))));
	}
}