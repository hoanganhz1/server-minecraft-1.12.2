package xyz.pixelatedw.mineminenomi.particles.effects.doru;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class CandleLockParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (40)){
				double var8 = (WyHelper.randomDouble()) * (2D);
				double var9 = (WyHelper.randomDouble()) * (2D);
				SimpleParticleData var10 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MOKU.get())));
				var10.setLife(8);
				var10.setSize(1.4F);
				var10.setMotion(0D,(0.2D) + ((Math.abs(WyHelper.randomDouble())) / (3D)),0D);
				var2.addParticle((ParticleOptions)(var10),true,(var3) + (var8),var4,(var5) + (var9),0D,0D,0D);
				if (((var7) % (5)) == (0)){
					var2.addParticle((ParticleOptions)(ParticleTypes.END_ROD),true,(var3) + (var8),var4,(var5) + (var9),0D,0D,0D);
				}
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}