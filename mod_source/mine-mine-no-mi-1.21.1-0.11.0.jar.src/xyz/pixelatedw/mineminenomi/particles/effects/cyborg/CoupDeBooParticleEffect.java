package xyz.pixelatedw.mineminenomi.particles.effects.cyborg;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class CoupDeBooParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (200)){
				SimpleParticleData var8 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MOKU.get())));
				var8.setColor(0.48F,0.36F,0F);
				var8.setLife(30);
				var8.setSize(3F);
				var8.setMotion((WyHelper.randomDouble()) / (4D),WyHelper.randomDouble(),(WyHelper.randomDouble()) / (4D));
				double var9 = ((double)(WyHelper.randomWithRange(-3,3))) * (WyHelper.randomDouble());
				double var10 = ((double)(WyHelper.randomWithRange(-2,2))) * (WyHelper.randomDouble());
				var2.addParticle((ParticleOptions)(var8),true,(var3) + (var9),(var4) + (var10),(var5) + (((double)(WyHelper.randomWithRange(-3,3))) * (WyHelper.randomDouble())),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}