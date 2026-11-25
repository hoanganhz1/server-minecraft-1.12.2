package xyz.pixelatedw.mineminenomi.particles.effects.suna;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class GroundDeathParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		double var7 = 0D;
		LOOP_1:
		do{
			if ((var7) < (1D)){
				var7 = (var7) + (0.3141592653589793D);
				double var8 = 0D;
				do{
					if ((var8) <= (12.566370614359172D)){
						double var9 = (var7) * (Math.cos(var8));
						double var10 = WyHelper.randomDouble();
						double var11 = (var7) * (Math.sin(var8));
						double var12 = (WyHelper.randomDouble()) / (3D);
						double var13 = 0D;
						double var14 = (WyHelper.randomDouble()) / (3D);
						SimpleParticleData var15 = new SimpleParticleData(((ParticleType)(ModParticleTypes.SUNA2.get())));
						var15.setLife(10);
						var15.setSize(2.5F);
						var15.setMotion(var12,var13,var14);
						var1.level().addParticle((ParticleOptions)(var15),true,(var3) + ((var9) * (2.25D)),((var4) + (0.5D)) + (var10),(var5) + ((var11) * (2.25D)),0D,0D,0D);
						var8 = (var8) + (0.19634954084936207D);
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