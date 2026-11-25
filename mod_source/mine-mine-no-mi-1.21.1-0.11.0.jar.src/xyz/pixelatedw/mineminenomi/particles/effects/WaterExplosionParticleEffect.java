package xyz.pixelatedw.mineminenomi.particles.effects;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class WaterExplosionParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (12)){
				double var8 = ((double)(WyHelper.randomWithRange(-3,3))) + (WyHelper.randomDouble());
				double var9 = ((double)(WyHelper.randomWithRange(-3,3))) + (WyHelper.randomDouble());
				double var10 = ((double)(WyHelper.randomWithRange(-3,3))) + (WyHelper.randomDouble());
				double var11 = 0.25D;
				var11 = (var11) * (((WyHelper.randomDouble()) * (2D)) + (0.30000001192092896D));
				var8 = (var8) * ((var11) / (2D));
				var9 = (var9) * ((var11) / (2D));
				var10 = (var10) * ((var11) / (2D));
				var2.addParticle((ParticleOptions)(ParticleTypes.FISHING),var3,var4,var5,var8,var9,var10);
				var2.addParticle((ParticleOptions)(ParticleTypes.FISHING),var3,var4,var5,var8,var9,var10);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}