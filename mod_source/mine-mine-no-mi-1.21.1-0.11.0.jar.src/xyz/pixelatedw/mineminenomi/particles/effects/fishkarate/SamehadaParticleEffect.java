package xyz.pixelatedw.mineminenomi.particles.effects.fishkarate;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class SamehadaParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		var4 = (var4) + (1.5D);
		double var7 = 1D;
		double var8 = 0D;
		LOOP_1:
		do{
			if ((var8) < (3.141592653589793D)){
				var8 = (var8) + (0.7853981633974483D);
				double var9 = 0D;
				do{
					if ((var9) <= (6.283185307179586D)){
						double var10 = ((var7) * (Math.cos(var9))) * (Math.sin(var8));
						double var11 = (var7) * (Math.cos(var8));
						double var12 = ((var7) * (Math.sin(var9))) * (Math.sin(var8));
						var2.addParticle((ParticleOptions)(ParticleTypes.SPLASH),true,(var3) + (var10),(var4) + (var11),(var5) + (var12),0D,0D,0D);
						var9 = (var9) + (0.5235987755982988D);
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