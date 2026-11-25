package xyz.pixelatedw.mineminenomi.particles.effects.artofweather;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class WeatherCloudChargedParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		double var8 = 0D;
		double var9 = 15D;
		LOOP_1:
		do{
			if ((var8) < (3.141592653589793D)){
				var8 = (var8) + (2.0943951023931953D);
				double var10 = 0D;
				do{
					if ((var10) <= (6.283185307179586D)){
						double var11 = (((var9) * (Math.cos(var10))) * (Math.sin(var8))) + ((WyHelper.randomDouble()) * (var9));
						double var12 = (var9) * (Math.cos(var8));
						double var13 = (((var9) * (Math.sin(var10))) * (Math.sin(var8))) + ((WyHelper.randomDouble()) * (var9));
						ParticleType var14 = null;
						if (((var7) % (4)) == (0)){
							var14 = ((ParticleType)(ModParticleTypes.GORO_YELLOW.get()));
						}else{
							var14 = ((ParticleType)(ModParticleTypes.GORO2_YELLOW.get()));
						}
						SimpleParticleData var15 = new SimpleParticleData(var14);
						var15.setLife((int)((20D) + (WyHelper.randomDouble())));
						var15.setSize(10F);
						var15.setHasScaleDecay(true);
						var2.addParticle((ParticleOptions)(var15),true,(var3) + (var11),((var4) + (5D)) + (var12),(var5) + (var13),0D,0D,0D);
						var7++;
						var10 = (var10) + (0.7853981633974483D);
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