package xyz.pixelatedw.mineminenomi.api.challenges;

import net.minecraft.core.BlockPos;

public class ChallengeArena$SpawnPosition {
	// Fields
	private BlockPos pos;
	private float pitch;
	private float yaw;

	public ChallengeArena$SpawnPosition(BlockPos var1, float var2, float var3) {
		this.pos = var1;
		this.pitch = var3;
		this.yaw = var2;
	}
	public BlockPos getPos() {
		return this.pos;
	}
	public float getPitch() {
		return this.pitch;
	}
	public float getYaw() {
		return this.yaw;
	}
}