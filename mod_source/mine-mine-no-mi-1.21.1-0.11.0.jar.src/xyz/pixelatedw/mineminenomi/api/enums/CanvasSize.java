package xyz.pixelatedw.mineminenomi.api.enums;

import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import net.minecraft.util.StringRepresentable;

public enum CanvasSize implements StringRepresentable {
	// Fields
	SMALL("small"),
	MEDIUM("medium"),
	LARGE("large"),
	HUGE("huge");
	public static final Codec<CanvasSize> CODEC = StringRepresentable.fromEnum((Supplier)(((Supplier<CanvasSize[]>)(CanvasSize::values))));
	private final String name;

	private CanvasSize(String var3) {
		this.name = var3;
	}
	public String getSerializedName() {
		return this.name;
	}
	public boolean isMaximumSize() {
		return (this.ordinal()) >= ((values().length) - (1));
	}
}