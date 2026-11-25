package xyz.pixelatedw.mineminenomi.particles.effects.yuki;

import net.minecraft.nbt.CompoundTag;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class KamakuraParticleEffect$Details extends ParticleEffect$Details {
	// Fields
	private int size;

	public KamakuraParticleEffect$Details() {
	}
	public KamakuraParticleEffect$Details(int var1) {
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