package xyz.pixelatedw.mineminenomi.particles.effects.awa;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class GoldenHourParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
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
				SimpleParticleData var13 = new SimpleParticleData(((ParticleType)(ModParticleTypes.AWA2.get())));
				var13.setLife(var11);
				var13.setSize(1.5F);
				var13.setMotion(0D,var12,0D);
				var2.addParticle((ParticleOptions)(var13),true,(var3) + (var8),((var4) + (1.25D)) + (var9),(var5) + (var10),0D,0D,0D);
				SimpleParticleData var14 = new SimpleParticleData(((ParticleType)(ModParticleTypes.AWA_FOAM.get())));
				var14.setLife(var11);
				var14.setSize(1.5F);
				var14.setMotion(0D,(var12) / (2D),0D);
				var14.setColor(1F,1F,0F,0.5F);
				var2.addParticle((ParticleOptions)(var14),true,(var3) + ((var8) / (2D)),((var4) + (1D)) + ((var9) / (2D)),(var5) + ((var10) / (2D)),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}