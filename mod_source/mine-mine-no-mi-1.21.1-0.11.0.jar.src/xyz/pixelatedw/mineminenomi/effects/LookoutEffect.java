package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import net.minecraft.world.effect.MobEffectCategory;
import xyz.pixelatedw.mineminenomi.api.effects.IColorOverlayEffect;

public class LookoutEffect extends BaseEffect implements IColorOverlayEffect {
	// Fields
	private static final Color OVERLAY_COLOR = new Color(0.97F,0.8F,1F,0.6F);

	public LookoutEffect() {
		super(MobEffectCategory.NEUTRAL,OVERLAY_COLOR.getRGB());
	}
	public boolean shouldUpdateClient() {
		return false;
	}
	public Color getBodyOverlayColor(int var1, int var2) {
		return OVERLAY_COLOR;
	}
	public boolean isRemoveable() {
		return false;
	}
}