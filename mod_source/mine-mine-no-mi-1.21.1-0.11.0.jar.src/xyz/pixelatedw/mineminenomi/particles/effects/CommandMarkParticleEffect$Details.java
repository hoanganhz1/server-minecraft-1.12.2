package xyz.pixelatedw.mineminenomi.particles.effects;

import java.awt.Color;
import net.minecraft.nbt.CompoundTag;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class CommandMarkParticleEffect$Details extends ParticleEffect$Details {
	// Fields
	private boolean isMain;
	private int rgb;

	public CommandMarkParticleEffect$Details() {
	}
	public CommandMarkParticleEffect$Details(int var1, boolean var2) {
		this.rgb = var1;
		this.isMain = true;
	}
	public CommandMarkParticleEffect$Details(String var1, boolean var2) {
		this(WyHelper.hexToRGB(var1).getRGB(),var2);
	}
	public void save(CompoundTag var1) {
		var1.putInt("rgb",this.rgb);
	}
	public void load(CompoundTag var1) {
		this.rgb = var1.getInt("rgb");
	}
	public int getRGB() {
		return this.rgb;
	}
	public Color getColor() {
		return new Color(this.rgb);
	}
	public void setRGB(int var1) {
		this.rgb = var1;
	}
	public boolean isMainMark() {
		return this.isMain;
	}
	public void setMainMark() {
		this.isMain = true;
	}
}