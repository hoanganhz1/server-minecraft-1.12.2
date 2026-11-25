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

public class WeatherCloudParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
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
						float var14 = 0.0F;
						float var15 = 0.0F;
						float var16 = 0.0F;
						ParticleType var17 = null;
						if (((var7) % (2)) == (0)){
							var14 = 0.5F;
							var15 = 0.5F;
							var16 = 0.5F;
							var17 = ((ParticleType)(ModParticleTypes.MOKU2.get()));
						}else{
							var14 = 0.3F;
							var15 = 0.3F;
							var16 = 0.3F;
							var17 = ((ParticleType)(ModParticleTypes.MOKU.get()));
						}
						SimpleParticleData var18 = new SimpleParticleData(var17);
						var18.setLife((int)((50D) + (WyHelper.randomDouble())));
						var18.setSize(45F);
						var18.setColor(var14,var15,var16);
						var18.setHasScaleDecay(true);
						var2.addParticle((ParticleOptions)(var18),true,(var3) + (var11),((var4) + (7D)) + (var12),(var5) + (var13),0D,0D,0D);
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