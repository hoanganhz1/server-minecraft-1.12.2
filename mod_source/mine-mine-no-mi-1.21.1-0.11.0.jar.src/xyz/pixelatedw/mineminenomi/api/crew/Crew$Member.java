package xyz.pixelatedw.mineminenomi.api.crew;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

public class Crew$Member {
	// Fields
	private UUID uuid;
	private String username;
	private boolean isCaptain;
	private boolean isSaved;
	 boolean markedForDeletion;

	public Crew$Member(LivingEntity var1) {
		this(var1.getUUID(),var1.getDisplayName().getString());
	}
	public Crew$Member(UUID var1, String var2) {
		this.uuid = var1;
		this.username = var2;
	}
	public Crew$Member setIsCaptain(boolean var1) {
		this.isCaptain = var1;
		return this;
	}
	public Crew$Member setSaved(boolean var1) {
		this.isSaved = var1;
		return this;
	}
	public boolean isCaptain() {
		return this.isCaptain;
	}
	public boolean isSaved() {
		return this.isSaved;
	}
	public UUID getUUID() {
		return this.uuid;
	}
	public String getUsername() {
		return this.username;
	}
}