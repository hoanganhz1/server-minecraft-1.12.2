package xyz.pixelatedw.mineminenomi.particles.effects.haki;

import net.minecraft.nbt.CompoundTag;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class HaoshokuHakiParticleEffect$Details extends ParticleEffect$Details {
	// Fields
	 int color;
	 int size;

	public void save(CompoundTag var1) {
		var1.putInt("color",this.color);
		var1.putInt("size",this.size);
	}
	public void load(CompoundTag var1) {
		this.color = var1.getInt("color");
		this.size = var1.getInt("size");
	}
	public void setColor(int var1) {
		this.color = var1;
	}
	public int getColor() {
		return this.color;
	}
	public void setSize(int var1) {
		this.size = var1;
	}
	public int getSize() {
		return this.size;
	}
}