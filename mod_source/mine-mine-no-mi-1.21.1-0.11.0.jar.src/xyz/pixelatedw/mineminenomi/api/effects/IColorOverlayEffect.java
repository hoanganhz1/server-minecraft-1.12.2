package xyz.pixelatedw.mineminenomi.api.effects;

import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public interface IColorOverlayEffect {
	@Nullable()
	public abstract @Nullable() Color getBodyOverlayColor(int var0, int var1);
	@Nullable()
	public default @Nullable() Color getViewOverlayColor(int var1, int var2) {
		return this.getBodyOverlayColor(var1,var2);
	}
}