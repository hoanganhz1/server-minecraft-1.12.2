package xyz.pixelatedw.mineminenomi.challenges.arenas;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockQueue;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeArena;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeArena$SpawnPosition;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.helpers.ChallengesHelper;

public class CircusArena extends ChallengeArena {
	// Fields
	public static final CircusArena INSTANCE = new CircusArena();
	private static final int ARENA_SIZE_RADIUS = 50;
	private static final int ARENA_SIZE = 100;

	private CircusArena() {
		super(ArenaStyle.SIMPLE);
	}
	public void buildArena(InProgressChallenge var1, BlockQueue var2) {
		PoneglyphLowspecArena.INSTANCE.buildArena(var1,var2);
	}
	public BoundingBox getArenaLimits() {
		return new BoundingBox(-50,-50,-50,50,50,50);
	}
	public static ChallengeArena$SpawnPosition getChallengerSpawnPos(int var0, InProgressChallenge var1) {
		BlockPos var2 = new BlockPos(((var1.getArenaPos().getX()) + (15)) - ((var0) + (1)),var1.getArenaPos().getY(),(var1.getArenaPos().getZ()) + (15));
		return new ChallengeArena$SpawnPosition(var2,135F,0F);
	}
	public static ChallengeArena$SpawnPosition[] getEnemySpawnPos(InProgressChallenge var0) {
		return ChallengesHelper.get4DefaultSpawnPoints(new BlockPos((var0.getArenaPos().getX()) - (15),var0.getArenaPos().getY(),(var0.getArenaPos().getZ()) - (15)),140F);
	}
}