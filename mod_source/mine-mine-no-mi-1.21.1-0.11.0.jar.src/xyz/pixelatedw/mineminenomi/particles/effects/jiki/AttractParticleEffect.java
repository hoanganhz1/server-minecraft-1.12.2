package xyz.pixelatedw.mineminenomi.particles.effects.jiki;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class AttractParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (WyHelper.randomWithRange(6,9))){
				double var8 = (WyHelper.randomDouble()) / (1.7D);
				double var9 = (WyHelper.randomDouble()) * (1.55D);
				double var10 = (WyHelper.randomDouble()) / (1.7D);
				int var11 = (3) + (WyHelper.randomWithRange(0,4));
				double var12 = (WyHelper.randomDouble()) / (50D);
				if ((var12) < (0D)){
					var12 = 0.02D;
				}
				SimpleParticleData var13 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MAGNET.get())));
				var13.setLife(var11);
				var13.setSize(4F);
				var13.setMotion(0D,var12,0D);
				var2.addParticle((ParticleOptions)(var13),true,(var3) + (var8),((var4) + (1.25D)) + (var9),(var5) + (var10),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}