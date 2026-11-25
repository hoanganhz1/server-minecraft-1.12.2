package xyz.pixelatedw.mineminenomi.particles.effects.sniper;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class KemuriBoshiParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (512)){
				double var8 = ((double)(WyHelper.randomWithRange(-4,4))) + (WyHelper.randomDouble());
				double var9 = ((double)(WyHelper.randomWithRange(-2,3))) + (WyHelper.randomDouble());
				double var10 = ((double)(WyHelper.randomWithRange(-4,4))) + (WyHelper.randomDouble());
				if (((var7) % (2)) == (0)){
					var2.addParticle((ParticleOptions)(ParticleTypes.CLOUD),true,((var3) + (var8)) + ((double)(WyHelper.randomWithRange(-7,7))),(((var4) + (0.5D)) + (var9)) + ((double)(WyHelper.randomWithRange(-1,3))),((var5) + (var10)) + ((double)(WyHelper.randomWithRange(-7,7))),0D,0D,0D);
				}else{
					var2.addParticle((ParticleOptions)(ParticleTypes.CLOUD),true,((var3) + (var8)) + ((double)(WyHelper.randomWithRange(-7,7))),(((var4) + (0.5D)) + (var9)) + ((double)(WyHelper.randomWithRange(-1,3))),((var5) + (var10)) + ((double)(WyHelper.randomWithRange(-7,7))),0D,0D,0D);
				}
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}