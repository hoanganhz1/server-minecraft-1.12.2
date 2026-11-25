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

public class ThunderLanceParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		double var12 = 0.0;
		double var13 = 0.0;
		double var14 = 0.0;
		ParticleType var15 = null;
		int var7 = 0;
		double var8 = 0D;
		double var9 = 0D;
		double var10 = 2D;
		LOOP_1:
		do{
			if ((var8) < (3.141592653589793D)){
				var8 = (var8) + (2.0943951023931953D);
				double var11 = 0D;
				do{
					if ((var11) <= (6.283185307179586D)){
						var12 = (((var10) * (Math.cos(var11))) * (Math.sin(var8))) + ((WyHelper.randomDouble()) * (var10));
						var13 = (((var10) * (Math.tan(var11))) * (Math.sin(var8))) + ((WyHelper.randomDouble()) * (var10));
						var14 = (((var10) * (Math.sin(var11))) * (Math.sin(var8))) + ((WyHelper.randomDouble()) * (var10));
						float var15_1 = 0.0F;
						float var16 = 0.0F;
						float var17 = 0.0F;
						if (((var7) % (2)) == (0)){
							var15_1 = 0.5F;
							var16 = 0.5F;
							var17 = 0.5F;
							var15 = ((ParticleType)(ModParticleTypes.MOKU2.get()));
						}else{
							var15_1 = 0.3F;
							var16 = 0.3F;
							var17 = 0.3F;
							var15 = ((ParticleType)(ModParticleTypes.MOKU.get()));
						}
						SimpleParticleData var19 = new SimpleParticleData(var15);
						var19.setLife(25);
						var19.setSize(8F);
						var19.setColor(var15_1,var16,var17);
						var2.addParticle((ParticleOptions)(var19),true,(var3) + (var12),(var4) + (var13),(var5) + (var14),0D,0D,0D);
						var7++;
						var11 = (var11) + (0.7853981633974483D);
						continue;
					}else{
						continue LOOP_1;
					}
				} while (true);
			}else{
				break;
			}
		} while (true);
		LOOP_2:
		do{
			if ((var9) < (3.141592653589793D)){
				var9 = (var9) + (2.0943951023931953D);
				double var11_1 = 0D;
				do{
					if ((var11_1) <= (6.283185307179586D)){
						var12 = (((var10) * (Math.cos(var11_1))) * (Math.sin(var9))) + ((WyHelper.randomDouble()) * (var10));
						var13 = (((var10) * (Math.tan(var11_1))) * (Math.sin(var9))) + ((WyHelper.randomDouble()) * (var10));
						var14 = (((var10) * (Math.sin(var11_1))) * (Math.sin(var9))) + ((WyHelper.randomDouble()) * (var10));
						if (((var7) % (4)) == (0)){
							var15 = ((ParticleType)(ModParticleTypes.GORO_YELLOW.get()));
						}else{
							var15 = ((ParticleType)(ModParticleTypes.GORO2_YELLOW.get()));
						}
						SimpleParticleData var16_1 = new SimpleParticleData(var15);
						var16_1.setLife(15);
						var16_1.setSize(6F);
						var2.addParticle((ParticleOptions)(var16_1),true,(var3) + (var12),((var4) - (2D)) + (var13),(var5) + (var14),0D,0D,0D);
						var7++;
						var11_1 = (var11_1) + (0.7853981633974483D);
						continue;
					}else{
						continue LOOP_2;
					}
				} while (true);
			}else{
				break;
			}
		} while (true);
	}
}