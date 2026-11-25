package xyz.pixelatedw.mineminenomi.particles.effects.mera;

import net.minecraft.nbt.CompoundTag;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class HibashiraParticleEffect$Details extends ParticleEffect$Details {
	// Fields
	private float radiusScale;
	private float densityScale;
	private float motionScale;

	public HibashiraParticleEffect$Details() {
		this.radiusScale = 1F;
		this.densityScale = 1F;
		this.motionScale = 1F;
	}
	public HibashiraParticleEffect$Details(float var1, float var2, float var3) {
		this.radiusScale = 1F;
		this.densityScale = 1F;
		this.motionScale = 1F;
		this.radiusScale = var1;
		this.densityScale = var2;
		this.motionScale = var3;
	}
	public void save(CompoundTag var1) {
		var1.putFloat("radiusScale",this.radiusScale);
		var1.putFloat("densityScale",this.densityScale);
		var1.putFloat("motionScale",this.motionScale);
	}
	public void load(CompoundTag var1) {
		this.radiusScale = var1.getFloat("radiusScale");
		this.densityScale = var1.getFloat("densityScale");
		this.motionScale = var1.getFloat("motionScale");
	}
	public float getRadiusScale() {
		return this.radiusScale;
	}
	public float getDensityScale() {
		return this.densityScale;
	}
	public float getMotionScale() {
		return this.motionScale;
	}
}