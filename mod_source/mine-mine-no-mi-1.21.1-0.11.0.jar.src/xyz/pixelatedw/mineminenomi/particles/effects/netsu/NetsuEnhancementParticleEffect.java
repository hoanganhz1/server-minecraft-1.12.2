package xyz.pixelatedw.mineminenomi.particles.effects.netsu;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class NetsuEnhancementParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (8)){
				double var8 = (WyHelper.randomDouble()) / (1.25D);
				double var9 = (WyHelper.randomDouble()) / (1.25D);
				double var10 = (WyHelper.randomDouble()) / (1.25D);
				ParticleType var11 = ((ParticleType)(ModParticleTypes.NETSU.get()));
				if (((var7) % (3)) == (0)){
					var11 = ((ParticleType)(ModParticleTypes.NETSU2.get()));
				}
				if (((var7) % (7)) == (0)){
					var11 = ((ParticleType)(ModParticleTypes.MERA.get()));
				}
				SimpleParticleData var12 = new SimpleParticleData(var11);
				var12.setLife(10);
				var12.setSize(1.3F);
				var2.addParticle((ParticleOptions)(var12),true,(var3) + (var8),((var4) + (1D)) + (var9),(var5) + (var10),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}