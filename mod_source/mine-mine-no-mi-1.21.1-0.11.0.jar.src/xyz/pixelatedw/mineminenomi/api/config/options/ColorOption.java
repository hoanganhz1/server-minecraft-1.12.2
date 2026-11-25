package xyz.pixelatedw.mineminenomi.api.config.options;

import java.awt.Color;
import net.neoforged.neoforge.common.ModConfigSpec;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class ColorOption extends StringOption {
	public ColorOption(String var1, String var2, String var3) {
		super(var1,var2,var3);
	}
	public void saveColor(Color var1) {
		this.getValue().set(WyHelper.rgbToHex(var1.getRed(),var1.getGreen(),var1.getBlue()));
	}
	public Color getDefaultColor() {
		return WyHelper.hexToRGB(((String)(this.getDefault())));
	}
}