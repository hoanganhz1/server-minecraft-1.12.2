package xyz.pixelatedw.mineminenomi.api.challenges;

@FunctionalInterface()
public interface ChallengeCore$IEnemyPosition {
	public abstract ChallengeArena$SpawnPosition[] getEnemySpawnPos(InProgressChallenge var0);
}