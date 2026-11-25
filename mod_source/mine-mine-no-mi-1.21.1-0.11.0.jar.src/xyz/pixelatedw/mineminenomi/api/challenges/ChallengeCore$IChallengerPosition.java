package xyz.pixelatedw.mineminenomi.api.challenges;

@FunctionalInterface()
public interface ChallengeCore$IChallengerPosition {
	public abstract ChallengeArena$SpawnPosition getChallengerSpawnPos(int var0, InProgressChallenge var1);
}