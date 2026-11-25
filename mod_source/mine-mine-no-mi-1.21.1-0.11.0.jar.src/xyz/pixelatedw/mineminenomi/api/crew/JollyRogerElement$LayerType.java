package xyz.pixelatedw.mineminenomi.api.crew;

import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import net.minecraft.util.StringRepresentable;

public enum JollyRogerElement$LayerType implements StringRepresentable {
	// Fields
	BASE,
	BACKGROUND,
	DETAIL;
	public static final Codec<JollyRogerElement$LayerType> CODEC = StringRepresentable.fromEnum((Supplier)(((Supplier<JollyRogerElement$LayerType[]>)(JollyRogerElement$LayerType::values))));

	private JollyRogerElement$LayerType() {
	}
	public String getSerializedName() {
		return this.name();
	}
}