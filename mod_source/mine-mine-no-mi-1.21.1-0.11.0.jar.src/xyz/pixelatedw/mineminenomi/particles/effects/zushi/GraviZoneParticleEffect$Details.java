package xyz.pixelatedw.mineminenomi.particles.effects.zushi;

import net.minecraft.nbt.CompoundTag;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class GraviZoneParticleEffect$Details extends ParticleEffect$Details {
	// Fields
	private int range;
	private int yOffset;

	public void save(CompoundTag var1) {
		var1.putInt("range",this.range);
		var1.putInt("yOffset",this.yOffset);
	}
	public void load(CompoundTag var1) {
		this.range = var1.getInt("range");
		this.yOffset = var1.getInt("yOffset");
	}
	public void setYOffset(int var1) {
		this.yOffset = var1;
	}
	public int getYOffset() {
		return this.yOffset;
	}
	public void setRange(int var1) {
		this.range = var1;
	}
	public int getRange() {
		return this.range;
	}
}