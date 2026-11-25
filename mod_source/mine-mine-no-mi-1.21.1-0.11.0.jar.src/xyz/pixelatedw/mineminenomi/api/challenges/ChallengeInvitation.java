package xyz.pixelatedw.mineminenomi.api.challenges;

import java.util.UUID;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ChallengeInvitation {
	// Fields
	private static final int INVITE_TIME = 2400;
	private UUID senderId;
	private ChallengeCore<?> challenge;
	private long gameTime;
	private int groupSlot;

	public ChallengeInvitation(UUID var1, ChallengeCore<?> var2, long var3, int var4) {
		this.senderId = var1;
		this.challenge = var2;
		this.gameTime = var3;
		this.groupSlot = Mth.clamp(var4,0,2);
	}
	public boolean isExpired(Level var1) {
		return ((this.gameTime) + (2400L)) < (var1.getGameTime());
	}
	@Nullable()
	public @Nullable() Player getSender(Level var1) {
		return var1.getPlayerByUUID(this.senderId);
	}
	public UUID getSenderId() {
		return this.senderId;
	}
	public ChallengeCore<?> getChallenge() {
		return (ChallengeCore<?>) (ChallengeCore) (this.challenge);
	}
	public long getSendTime() {
		return this.gameTime;
	}
	public int getGroupSlot() {
		return this.groupSlot;
	}
}