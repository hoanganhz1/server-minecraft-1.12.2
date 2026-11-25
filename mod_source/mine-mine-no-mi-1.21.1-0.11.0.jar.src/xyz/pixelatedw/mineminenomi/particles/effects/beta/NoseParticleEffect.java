package xyz.pixelatedw.mineminenomi.particles.effects.beta;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class NoseParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (10)){
				SimpleParticleData var8 = new SimpleParticleData(((ParticleType)(ModParticleTypes.BETA.get())));
				var8.setLife(20);
				var8.setSize(0.6F);
				var8.setMotion(0D,0.03D,0D);
				var2.addParticle((ParticleOptions)(var8),true,var3,(var4) + (1.45D),var5,0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}