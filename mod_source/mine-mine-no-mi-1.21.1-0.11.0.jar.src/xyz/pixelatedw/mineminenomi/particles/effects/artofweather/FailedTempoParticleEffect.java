package xyz.pixelatedw.mineminenomi.particles.effects.artofweather;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class FailedTempoParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (10)){
				double var8 = WyHelper.randomDouble();
				double var9 = WyHelper.randomDouble();
				double var10 = WyHelper.randomDouble();
				if (((var7) % (2)) == (0)){
					var2.addParticle((ParticleOptions)(ParticleTypes.POOF),true,(var3) + (var8),((var4) + (1D)) + (var9),(var5) + (var10),0D,0D,0D);
				}else{
					var2.addParticle((ParticleOptions)(ParticleTypes.SMOKE),true,(var3) + (var8),((var4) + (1D)) + (var9),(var5) + (var10),0D,0D,0D);
				}
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}