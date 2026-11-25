package xyz.pixelatedw.mineminenomi.particles.effects.doctor;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class FirstAidParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (45)){
				double var8 = ((double)(WyHelper.randomWithRange(-5,5))) + (WyHelper.randomDouble());
				double var9 = (double)(WyHelper.randomWithRange(1,2));
				double var10 = ((double)(WyHelper.randomWithRange(-5,5))) + (WyHelper.randomDouble());
				double var11 = 0.1D;
				var11 = (var11) * ((double)(((var2.random.nextFloat()) * (2F)) + (0.3F)));
				var8 = (var8) * ((var11) / (50D));
				var9 = (var9) * ((var11) / (2D));
				var10 = (var10) * ((var11) / (50D));
				double var12 = (WyHelper.randomDouble()) / (1.2D);
				double var13 = (WyHelper.randomDouble()) / (1.2D);
				SimpleParticleData var14 = null;
				if (((var7) % (2)) == (0)){
					var14 = new SimpleParticleData(((ParticleType)(ModParticleTypes.CHIYU.get())));
				}else{
					var14 = new SimpleParticleData(((ParticleType)(ModParticleTypes.PIKA.get())));
				}
				var14.setLife(10);
				var14.setSize(1.5F);
				var14.setColor(0F,0.8F,0F);
				var14.setMotion(var8,var9,var10);
				var2.addParticle((ParticleOptions)(var14),true,(var3) + (var12),(var4) + (0.5D),(var5) + (var13),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}