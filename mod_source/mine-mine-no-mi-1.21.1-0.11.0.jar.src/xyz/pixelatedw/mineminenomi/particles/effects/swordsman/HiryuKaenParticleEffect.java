package xyz.pixelatedw.mineminenomi.particles.effects.swordsman;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class HiryuKaenParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
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
						double var12 = (var10) / (8D);
						double var13 = (0.01D) + ((var8.nextDouble()) / (4D));
						double var14 = (var11) / (8D);
						SimpleParticleData var15 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MERA.get())));
						var15.setLife(7);
						var15.setAnimationSpeed(1);
						var15.setSize(2F);
						var15.setColor(1F,1F,1F,0.5F);
						var15.setMotion(var12,var13,var14);
						var2.addParticle((ParticleOptions)(var15),true,(var3) + ((var10) * (0.85D)),(var4) + (1.2D),(var5) + ((var11) * (0.85D)),0D,0D,0D);
						var2.addParticle((ParticleOptions)(var15),true,(var3) + ((var10) * (0.45D)),(var4) + (1.8D),(var5) + ((var11) * (0.45D)),0D,0D,0D);
						var2.addParticle((ParticleOptions)(var15),true,(var3) + ((var10) * (0.85D)),(var4) + (2.2D),(var5) + ((var11) * (0.85D)),0D,0D,0D);
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