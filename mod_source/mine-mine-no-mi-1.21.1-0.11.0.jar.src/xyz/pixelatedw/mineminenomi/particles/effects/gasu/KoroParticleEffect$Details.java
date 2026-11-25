package xyz.pixelatedw.mineminenomi.particles.effects.gasu;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.effect.MobEffectInstance;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class KoroParticleEffect$Details extends ParticleEffect$Details {
	// Fields
	 MobEffectInstance effect;

	public void save(CompoundTag var1) {
		var1.put("effect",this.effect.save());
	}
	public void load(CompoundTag var1) {
		CompoundTag var2 = var1.getCompound("effect");
		this.effect = MobEffectInstance.load(var2);
	}
	public void setEffect(MobEffectInstance var1) {
		this.effect = var1;
	}
	public MobEffectInstance getEffect() {
		return this.effect;
	}
}