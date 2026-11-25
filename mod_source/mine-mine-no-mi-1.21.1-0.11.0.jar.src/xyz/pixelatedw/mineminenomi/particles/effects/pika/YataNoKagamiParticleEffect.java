package xyz.pixelatedw.mineminenomi.particles.effects.pika;

import java.util.Random;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class YataNoKagamiParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (20)){
				double var8 = ((((double)(new Random().nextInt(40))) + (1D)) - (20D)) / (20D);
				double var9 = (((double)(new Random().nextInt(40))) + (1D)) / (20D);
				double var10 = ((((double)(new Random().nextInt(40))) + (1D)) - (20D)) / (20D);
				SimpleParticleData var11 = new SimpleParticleData(((ParticleType)(ModParticleTypes.PIKA.get())));
				var11.setLife(20);
				var11.setSize(4F);
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