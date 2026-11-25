package xyz.pixelatedw.mineminenomi.particles.effects.suna;

import net.minecraft.nbt.CompoundTag;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class SablesNewParticleEffect$Details extends ParticleEffect$Details {
	// Fields
	 float size;

	public void save(CompoundTag var1) {
		var1.putFloat("size",this.size);
	}
	public void load(CompoundTag var1) {
		this.size = var1.getFloat("size");
	}
	public float getSize() {
		return this.size;
	}
	public void setSize(float var1) {
		this.size = var1;
	}
}