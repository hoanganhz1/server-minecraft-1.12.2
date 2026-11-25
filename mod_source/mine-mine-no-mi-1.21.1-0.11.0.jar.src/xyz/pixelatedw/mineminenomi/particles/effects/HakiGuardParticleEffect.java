package xyz.pixelatedw.mineminenomi.particles.effects;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class HakiGuardParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (25)){
				double var8 = (var3) + ((WyHelper.randomDouble()) / (1.2D));
				double var9 = (var4) + ((WyHelper.randomDouble()) / (1.2D));
				double var10 = (var5) + ((WyHelper.randomDouble()) / (1.2D));
				if (((var7) % (2)) == (0)){
					var2.addParticle((ParticleOptions)(ParticleTypes.CRIT),var8,(var9) + (1D),var10,0D,0D,0D);
				}
				var2.addParticle((ParticleOptions)(ParticleTypes.WITCH),var8,(var9) + (1D),var10,0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}