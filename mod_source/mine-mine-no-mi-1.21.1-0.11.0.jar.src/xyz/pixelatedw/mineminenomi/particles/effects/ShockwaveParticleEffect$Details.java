package xyz.pixelatedw.mineminenomi.particles.effects;

import net.minecraft.nbt.CompoundTag;

public class ShockwaveParticleEffect$Details extends ParticleEffect$Details {
	// Fields
	private int size;

	public ShockwaveParticleEffect$Details() {
	}
	public ShockwaveParticleEffect$Details(int var1) {
		this.size = var1;
	}
	public void save(CompoundTag var1) {
		var1.putInt("size",this.size);
	}
	public void load(CompoundTag var1) {
		this.size = var1.getInt("size");
	}
	public int getSize() {
		return this.size;
	}
}