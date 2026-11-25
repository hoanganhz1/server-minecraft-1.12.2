package xyz.pixelatedw.mineminenomi.api.challenges;

import net.minecraft.world.entity.LivingEntity;

public class ChallengeArena$EnemySpawn {
	// Fields
	private final LivingEntity entity;
	private final ChallengeArena$SpawnPosition spawnPos;

	public ChallengeArena$EnemySpawn(LivingEntity var1, ChallengeArena$SpawnPosition var2) {
		this.entity = var1;
		this.spawnPos = var2;
	}
	public LivingEntity getEntity() {
		return this.entity;
	}
	public ChallengeArena$SpawnPosition getSpawnPos() {
		return this.spawnPos;
	}
}