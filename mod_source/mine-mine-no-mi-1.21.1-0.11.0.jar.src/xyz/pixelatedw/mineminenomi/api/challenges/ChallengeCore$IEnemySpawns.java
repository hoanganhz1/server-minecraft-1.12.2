package xyz.pixelatedw.mineminenomi.api.challenges;

import java.util.Set;

@FunctionalInterface()
public interface ChallengeCore$IEnemySpawns {
	public abstract Set<ChallengeArena$EnemySpawn> getEnemySpawns(InProgressChallenge var0, ChallengeArena$SpawnPosition[] var1);
}