package xyz.pixelatedw.mineminenomi.particles.effects.moku;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class SmokeDebuffParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (80)){
				double var8 = (WyHelper.randomDouble()) / (2D);
				double var9 = (1D) + ((WyHelper.randomDouble()) / (2D));
				double var10 = (WyHelper.randomDouble()) / (2D);
				ParticleType var11 = ((ParticleType)(ModParticleTypes.MOKU2.get()));
				if (((var7) % (3)) == (0)){
					var11 = ((ParticleType)(ModParticleTypes.MOKU.get()));
				}
				SimpleParticleData var12 = new SimpleParticleData(var11);
				var12.setLife(WyHelper.randomWithRange(1,10));
				var12.setSize((float)(WyHelper.randomWithRange(1,4)));
				var2.addParticle((ParticleOptions)(var12),true,(var1.getX()) + (var8),(var1.getY()) + (var9),(var1.getZ()) + (var10),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}