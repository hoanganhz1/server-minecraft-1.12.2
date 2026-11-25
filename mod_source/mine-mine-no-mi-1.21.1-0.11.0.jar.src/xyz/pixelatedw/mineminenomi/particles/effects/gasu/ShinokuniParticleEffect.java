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

public class ShinokuniParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (10)){
				double var8 = (WyHelper.randomDouble()) * (2D);
				double var9 = (WyHelper.randomDouble()) * (2D);
				double var10 = (WyHelper.randomDouble()) * (2D);
				ParticleType var11 = null;
				if (((var7) % (5)) == (0)){
					var11 = ((ParticleType)(ModParticleTypes.GASU2.get()));
				}else{
					var11 = ((ParticleType)(ModParticleTypes.GASU.get()));
				}
				SimpleParticleData var12 = new SimpleParticleData(var11);
				var12.setMotion(0D,0.1D,0D);
				var12.setLife(40);
				var12.setSize(3F);
				var2.addParticle((ParticleOptions)(var12),true,(var3) + (var8),(var4) + (var9),(var5) + (var10),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}