package xyz.pixelatedw.mineminenomi.particles.effects.gura;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class AirCrackParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		SimpleParticleData var7 = new SimpleParticleData(((ParticleType)(ModParticleTypes.GURA.get())));
		var7.setLife(20);
		var7.setSize(16F);
		var7.setColor(1F,1F,1F,0.7F);
		var2.addParticle((ParticleOptions)(var7),true,(var3) + ((Math.random()) / (2D)),(var4) + ((Math.random()) / (2D)),(var5) + ((Math.random()) / (2D)),0D,0D,0D);
	}
}