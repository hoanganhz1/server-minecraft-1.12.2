package xyz.pixelatedw.mineminenomi.particles.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.ParticleStatus;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public abstract class ParticleEffect<A extends ParticleEffect$Details> {
	// Fields
	public static final ParticleEffect$NoDetails EMPTY_DETAILS = new ParticleEffect$NoDetails();
	private ParticleEffect$IFactory<A> factory;

	public ParticleEffect() {
	}
	public ParticleEffect(ParticleEffect$IFactory<A> var1) {
		this.factory = var1;
	}
	public abstract void spawn(Entity var0, Level var1, double var2, double var3, double var4, A var5);
	@OnlyIn(value=Dist.CLIENT)
	public boolean canParticlesSpawn(Level var1) {
		OptionInstance var2 = Minecraft.getInstance().options.particles();
		if ((((var2.get()) == (ParticleStatus.DECREASED)) && ((var1.getRandom().nextInt(10)) < (2))) || (((var2.get()) == (ParticleStatus.MINIMAL)) && ((var1.getRandom().nextInt(10)) < (8)))){
			return false;
		}else{
			return true;
		}
	}
	public A createDetails() {
		if ((this.factory) != (null)){
			return (A) (this.factory.create());
		}else{
			return (A) (EMPTY_DETAILS);
		}
	}
}