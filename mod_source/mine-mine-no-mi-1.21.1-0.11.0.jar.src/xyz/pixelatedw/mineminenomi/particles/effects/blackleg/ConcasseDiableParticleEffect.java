package xyz.pixelatedw.mineminenomi.particles.effects.blackleg;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class ConcasseDiableParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		double var7 = 0.5D;
		double var8 = 0D;
		LOOP_1:
		do{
			if ((var8) < (3.141592653589793D)){
				var8 = (var8) + (1.5707963267948966D);
				double var9 = 0D;
				do{
					if ((var9) <= (12.566370614359172D)){
						double var10 = (((var7) * (Math.cos(var9))) * (Math.sin(var8))) + ((WyHelper.randomDouble()) / (2D));
						double var11 = ((var7) * (Math.cos(var8))) + ((WyHelper.randomDouble()) / (2D));
						double var12 = (((var7) * (Math.sin(var9))) * (Math.sin(var8))) + ((WyHelper.randomDouble()) / (2D));
						SimpleParticleData var13 = null;
						if (((var2.random.nextInt(10)) % (2)) == (0)){
							var13 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MERA.get())));
						}else{
							var13 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MERA2.get())));
						}
						var13.setLife(WyHelper.randomWithRange(5,10));
						var13.setSize((float)(WyHelper.randomWithRange(1,3)));
						var13.setColor(1F,1F,1F,0.5F);
						var2.addParticle((ParticleOptions)(var13),(var3) + (var10),(var4) + (var11),(var5) + (var12),0D,0D,0D);
						var9 = (var9) + (1.5707963267948966D);
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