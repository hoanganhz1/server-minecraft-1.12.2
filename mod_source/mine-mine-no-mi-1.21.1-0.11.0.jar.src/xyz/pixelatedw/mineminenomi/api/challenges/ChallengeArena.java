package xyz.pixelatedw.mineminenomi.api.challenges;

import net.minecraft.world.level.levelgen.structure.BoundingBox;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockQueue;

public abstract class ChallengeArena {
	// Fields
	private ArenaStyle style;

	public ChallengeArena(ArenaStyle var1) {
		this.style = var1;
	}
	public abstract void buildArena(InProgressChallenge var0, BlockQueue var1);
	public abstract BoundingBox getArenaLimits();
	public int getGroundLevel() {
		return 32;
	}
	public ArenaStyle getStyle() {
		return this.style;
	}
}