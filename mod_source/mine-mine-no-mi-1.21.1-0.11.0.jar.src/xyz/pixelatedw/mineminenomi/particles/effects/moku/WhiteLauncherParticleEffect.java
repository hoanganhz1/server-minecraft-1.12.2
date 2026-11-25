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

public class WhiteLauncherParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (20)){
				double var8 = (WyHelper.randomDouble()) / (2D);
				double var9 = (WyHelper.randomDouble()) / (2D);
				double var10 = (WyHelper.randomDouble()) / (2D);
				SimpleParticleData var11 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MOKU.get())));
				var11.setLife(20);
				var11.setSize(1.3F);
				var11.setMotion(0D,0.05D,0D);
				var2.addParticle((ParticleOptions)(var11),true,(var3) + (var8),((var4) + (0.5D)) + (var9),(var5) + (var10),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}