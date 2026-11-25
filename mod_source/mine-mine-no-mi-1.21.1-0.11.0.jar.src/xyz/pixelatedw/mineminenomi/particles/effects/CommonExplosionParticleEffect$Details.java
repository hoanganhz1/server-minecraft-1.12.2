package xyz.pixelatedw.mineminenomi.particles.effects;

import net.minecraft.nbt.CompoundTag;

public class CommonExplosionParticleEffect$Details extends ParticleEffect$Details {
	// Fields
	 double explosionSize;

	public CommonExplosionParticleEffect$Details() {
		this.explosionSize = 1D;
	}
	public CommonExplosionParticleEffect$Details(double var1) {
		this.explosionSize = 1D;
		this.explosionSize = var1;
	}
	public void save(CompoundTag var1) {
		var1.putDouble("explosionSize",this.explosionSize);
	}
	public void load(CompoundTag var1) {
		this.explosionSize = var1.getDouble("explosionSize");
	}
}