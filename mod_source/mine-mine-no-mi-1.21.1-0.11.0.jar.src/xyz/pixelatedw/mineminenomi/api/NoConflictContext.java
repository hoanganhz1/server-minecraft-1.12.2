package xyz.pixelatedw.mineminenomi.api;

import net.neoforged.neoforge.client.settings.IKeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyConflictContext;

public class NoConflictContext implements IKeyConflictContext {
	// Fields
	public static final NoConflictContext INSTANCE = new NoConflictContext();

	public boolean isActive() {
		return KeyConflictContext.IN_GAME.isActive();
	}
	public boolean conflicts(IKeyConflictContext var1) {
		return false;
	}
}