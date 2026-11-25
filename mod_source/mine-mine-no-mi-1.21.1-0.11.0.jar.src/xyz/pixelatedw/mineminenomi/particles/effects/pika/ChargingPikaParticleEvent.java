package xyz.pixelatedw.mineminenomi.particles.effects.pika;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class ChargingPikaParticleEvent extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (2)){
				double var8 = (WyHelper.randomDouble()) * (1.55D);
				double var9 = WyHelper.randomDouble();
				double var10 = (WyHelper.randomDouble()) * (1.55D);
				SimpleParticleData var11 = new SimpleParticleData(((ParticleType)(ModParticleTypes.PIKA.get())));
				var11.setLife(5);
				var11.setSize(3F);
				var11.setMotion(0D,0.15D,0D);
				var11.setRotation(0F,1F,0F);
				var2.addParticle((ParticleOptions)(var11),true,(var3) + (var8),((var4) + (0.5D)) + (var9),(var5) + (var10),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}