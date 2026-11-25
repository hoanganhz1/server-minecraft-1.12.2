package xyz.pixelatedw.mineminenomi.particles.effects.goro;

import net.minecraft.nbt.CompoundTag;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class KariParticleEffect$Details extends ParticleEffect$Details {
	// Fields
	private int range;
	private float size;

	public void save(CompoundTag var1) {
		var1.putInt("range",this.range);
		var1.putFloat("size",this.size);
	}
	public void load(CompoundTag var1) {
		this.range = var1.getInt("range");
		this.size = var1.getFloat("size");
	}
	public int getRange() {
		return this.range;
	}
	public float getSize() {
		return this.size;
	}
	public void setRange(int var1) {
		this.range = var1;
	}
	public void setSize(float var1) {
		this.size = var1;
	}
}