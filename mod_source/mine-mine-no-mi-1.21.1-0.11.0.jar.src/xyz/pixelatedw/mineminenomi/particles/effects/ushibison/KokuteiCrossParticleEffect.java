package xyz.pixelatedw.mineminenomi.particles.effects.ushibison;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class KokuteiCrossParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		double var7 = 0D;
		RandomSource var8 = var2.random;
		LOOP_1:
		do{
			if ((var7) < (1D)){
				var7 = (var7) + (1.5707963267948966D);
				double var9 = 0D;
				do{
					if ((var9) <= (12.566370614359172D)){
						double var10 = (var7) * (Math.cos(var9));
						double var11 = (var7) * (Math.sin(var9));
						double var12 = (var10) / (6D);
						double var13 = (-0.1D) + ((var8.nextDouble()) / (10D));
						double var14 = (var11) / (6D);
						var2.addParticle((ParticleOptions)(ParticleTypes.CLOUD),true,((var3) + ((var10) * (1.85D))) + (var12),((var4) + (1.2D)) + (var13),((var5) + ((var11) * (1.85D))) + (var14),0D,0D,0D);
						var2.addParticle((ParticleOptions)(ParticleTypes.CLOUD),true,((var3) + ((var10) * (1.85D))) + (var12),((var4) + (2.2D)) + (var13),((var5) + ((var11) * (1.85D))) + (var14),0D,0D,0D);
						var9 = (var9) + (0.09817477042468103D);
						continue;
					}else{
						continue LOOP_1;
					}
				} while (true);
			}else{
				break;
			}
		} while (true);
	}
}