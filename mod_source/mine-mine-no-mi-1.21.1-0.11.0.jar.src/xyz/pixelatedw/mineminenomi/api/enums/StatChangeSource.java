package xyz.pixelatedw.mineminenomi.api.enums;

import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import net.minecraft.util.StringRepresentable;

public enum StatChangeSource implements StringRepresentable {
	// Fields
	NATURAL,
	KILL_NPC,
	KILL_PLAYER,
	QUEST,
	CHALLENGE,
	COMMAND,
	DEATH,
	STORE;
	public static final Codec<StatChangeSource> CODEC = StringRepresentable.fromEnum((Supplier)(((Supplier<StatChangeSource[]>)(StatChangeSource::values))));

	private StatChangeSource() {
	}
	public String getSerializedName() {
		return this.name();
	}
}