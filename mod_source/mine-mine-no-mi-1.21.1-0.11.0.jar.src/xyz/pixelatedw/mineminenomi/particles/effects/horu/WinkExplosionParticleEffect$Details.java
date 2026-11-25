package xyz.pixelatedw.mineminenomi.particles.effects.horu;

import net.minecraft.nbt.CompoundTag;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class WinkExplosionParticleEffect$Details extends ParticleEffect$Details {
	// Fields
	 double explosionSize;

	public WinkExplosionParticleEffect$Details() {
		this.explosionSize = 1D;
	}
	public WinkExplosionParticleEffect$Details(double var1) {
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