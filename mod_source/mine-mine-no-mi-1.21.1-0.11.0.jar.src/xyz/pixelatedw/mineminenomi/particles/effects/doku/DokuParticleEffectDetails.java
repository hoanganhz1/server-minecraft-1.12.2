package xyz.pixelatedw.mineminenomi.particles.effects.doku;

import net.minecraft.nbt.CompoundTag;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class DokuParticleEffectDetails extends ParticleEffect$Details {
	// Fields
	private boolean hasVenomDemon;

	public void save(CompoundTag var1) {
		var1.putBoolean("hasVenomDemon",this.hasVenomDemon);
	}
	public void load(CompoundTag var1) {
		this.hasVenomDemon = var1.getBoolean("hasVenomDemon");
	}
	public DokuParticleEffectDetails setHasVenomDemon() {
		this.hasVenomDemon = true;
		return this;
	}
	public boolean hasVenomDemon() {
		return this.hasVenomDemon;
	}
}