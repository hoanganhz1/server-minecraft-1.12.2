package xyz.pixelatedw.mineminenomi.particles.effects.gasu;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class GastanetParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (256)){
				double var8 = WyHelper.randomDouble();
				double var9 = WyHelper.randomDouble();
				double var10 = WyHelper.randomDouble();
				double var11 = ((double)(WyHelper.randomWithRange(-5,5))) + (WyHelper.randomDouble());
				double var12 = ((double)(WyHelper.randomWithRange(0,5))) + (WyHelper.randomDouble());
				double var13 = ((double)(WyHelper.randomWithRange(-5,5))) + (WyHelper.randomDouble());
				double var14 = 2.8D;
				var11 = (var11) * ((var14) / (15D));
				var12 = (var12) * ((var14) / (25D));
				var13 = (var13) * ((var14) / (15D));
				var12 = Math.abs(var12);
				SimpleParticleData var15 = null;
				if (((var7) % (4)) == (0)){
					var15 = new SimpleParticleData(((ParticleType)(ModParticleTypes.GASU.get())));
				}else{
					var15 = new SimpleParticleData(((ParticleType)(ModParticleTypes.GASU2.get())));
				}
				var15.setLife(7);
				var15.setSize(1.5F);
				var15.setMotion(var11,var12,var13);
				var2.addParticle((ParticleOptions)(var15),true,(var3) + (var8),((var4) + (0.25D)) + (var9),(var5) + (var10),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}