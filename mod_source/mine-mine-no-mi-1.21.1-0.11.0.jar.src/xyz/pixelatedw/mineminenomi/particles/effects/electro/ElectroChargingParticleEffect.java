package xyz.pixelatedw.mineminenomi.particles.effects.electro;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class ElectroChargingParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		double var7 = 0D;
		RandomSource var8 = var2.random;
		double var9 = 0.75D;
		LOOP_1:
		do{
			if ((var7) < (1D)){
				var7 = (var7) + ((var9) * (3.141592653589793D));
				double var10 = 0D;
				do{
					if ((var10) <= (12.566370614359172D)){
						double var11 = (var7) * (Math.cos(var10));
						double var12 = (var7) * (Math.sin(var10));
						double var13 = (-(var11)) / (20D);
						double var14 = (0.01D) + ((var8.nextDouble()) / (15D));
						double var15 = (-(var12)) / (20D);
						SimpleParticleData var16 = null;
						if (((var8.nextInt(10)) % (2)) == (0)){
							var16 = new SimpleParticleData(((ParticleType)(ModParticleTypes.GORO.get())));
						}else{
							var16 = new SimpleParticleData(((ParticleType)(ModParticleTypes.GORO2.get())));
						}
						var16.setLife(WyHelper.randomWithRange(5,10));
						var16.setSize((float)(WyHelper.randomWithRange(3,5)));
						var16.setColor(1F,1F,1F,0.5F);
						var16.setMotion(var13,var14,var15);
						var2.addParticle((ParticleOptions)(var16),true,((var3) + (var11)) + ((WyHelper.randomDouble()) / (2D)),(var4) + (0.25D),((var5) + (var12)) + ((WyHelper.randomDouble()) / (2D)),0D,0D,0D);
						var10 = (var10) + (0.19634954084936207D);
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