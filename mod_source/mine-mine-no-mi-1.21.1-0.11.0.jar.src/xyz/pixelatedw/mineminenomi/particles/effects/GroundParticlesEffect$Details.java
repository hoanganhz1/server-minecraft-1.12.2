package xyz.pixelatedw.mineminenomi.particles.effects;

import net.minecraft.nbt.CompoundTag;

public class GroundParticlesEffect$Details extends ParticleEffect$Details {
	// Fields
	 int offset;
	 int amount;

	public GroundParticlesEffect$Details() {
		this.offset = 1;
		this.amount = 10;
	}
	public GroundParticlesEffect$Details(int var1, int var2) {
		this.offset = 1;
		this.amount = 10;
		this.offset = var1;
		this.amount = var2;
	}
	public void save(CompoundTag var1) {
		var1.putInt("offset",this.offset);
		var1.putInt("amount",this.amount);
	}
	public void load(CompoundTag var1) {
		this.offset = var1.getInt("offset");
		this.amount = var1.getInt("amount");
	}
}