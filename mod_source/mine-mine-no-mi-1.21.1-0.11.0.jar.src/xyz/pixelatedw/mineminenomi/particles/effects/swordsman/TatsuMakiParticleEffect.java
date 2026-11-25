package xyz.pixelatedw.mineminenomi.particles.effects.swordsman;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class TatsuMakiParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		double var7 = 0D;
		LOOP_1:
		do{
			if ((var7) < (3D)){
				var7 = (var7) + (1.5707963267948966D);
				double var8 = 0D;
				do{
					if ((var8) <= (12.566370614359172D)){
						double var9 = (var7) * (Math.cos(var8));
						double var10 = (var7) * (Math.sin(var8));
						var2.addParticle((ParticleOptions)(ParticleTypes.SWEEP_ATTACK),true,(var3) + ((var9) * (1.85D)),((var4) + (1.2D)) + (WyHelper.randomDouble()),(var5) + ((var10) * (1.85D)),0D,0D,0D);
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