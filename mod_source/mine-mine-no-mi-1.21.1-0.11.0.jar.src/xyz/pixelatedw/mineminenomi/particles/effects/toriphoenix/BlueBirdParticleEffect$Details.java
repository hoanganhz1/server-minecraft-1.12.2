package xyz.pixelatedw.mineminenomi.particles.effects.toriphoenix;

import net.minecraft.nbt.CompoundTag;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class BlueBirdParticleEffect$Details extends ParticleEffect$Details {
	// Fields
	private double currentChargeTime;
	private double maxChargeTime;

	public void save(CompoundTag var1) {
		var1.putDouble("currentChargeTime",this.currentChargeTime);
		var1.putDouble("maxChargeTime",this.maxChargeTime);
	}
	public void load(CompoundTag var1) {
		this.currentChargeTime = var1.getDouble("currentChargeTime");
		this.maxChargeTime = var1.getDouble("maxChargeTime");
	}
	public void setCurrentChargeTime(double var1) {
		this.currentChargeTime = var1;
	}
	public void setMaxChargeTime(double var1) {
		this.maxChargeTime = var1;
	}
	public double getMaxChargeTime() {
		return this.maxChargeTime;
	}
	public double getCurrentChargeTime() {
		return this.currentChargeTime;
	}
}