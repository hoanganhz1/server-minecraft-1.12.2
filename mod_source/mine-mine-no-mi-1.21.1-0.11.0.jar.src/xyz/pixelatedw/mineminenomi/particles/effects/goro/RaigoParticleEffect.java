package xyz.pixelatedw.mineminenomi.particles.effects.goro;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.MineMineNoMi;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class RaigoParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		double var11 = 0.0;
		double var12 = 0.0;
		double var13 = 0.0;
		ParticleType var7 = (MineMineNoMi.hasBlueGoroPackSelected()) ? (((ParticleType)(ModParticleTypes.GORO2.get()))) : ((ParticleType)(((ParticleType)(ModParticleTypes.GORO2_YELLOW.get()))));
		ParticleType var8 = (MineMineNoMi.hasBlueGoroPackSelected()) ? (((ParticleType)(ModParticleTypes.GORO3.get()))) : ((ParticleType)(((ParticleType)(ModParticleTypes.GORO3_YELLOW.get()))));
		int var9 = 128;
		int var10 = 0;
		do{
			if ((var10) < (var9)){
				var11 = ((double)(WyHelper.randomWithRange(-(var9),var9))) + (WyHelper.randomDouble());
				var12 = (40D) + (((double)(WyHelper.randomWithRange(-5,5))) + (WyHelper.randomDouble()));
				var13 = ((double)(WyHelper.randomWithRange(-(var9),var9))) + (WyHelper.randomDouble());
				SimpleParticleData var14 = new SimpleParticleData(var8);
				var14.setLife(100);
				var14.setSize(100F);
				var2.addParticle((ParticleOptions)(var14),true,(var3) + (var11),(var4) + (var12),(var5) + (var13),0D,0D,0D);
				if (((var10) % (2)) == (0)){
					var14.setColor(0.4F,0.4F,0.4F);
				}else{
					var14.setColor(0.3F,0.3F,0.3F);
				}
				var10++;
				continue;
			}else{
				break;
			}
		} while (true);
		var10 = 0;
		do{
			if ((var10) < ((var9) / (2))){
				var11 = ((double)(WyHelper.randomWithRange(-(var9),var9))) + (WyHelper.randomDouble());
				var12 = (30D) + (((double)(WyHelper.randomWithRange(-5,0))) + (WyHelper.randomDouble()));
				var13 = ((double)(WyHelper.randomWithRange(-(var9),var9))) + (WyHelper.randomDouble());
				SimpleParticleData var14_1 = new SimpleParticleData(var7);
				var14_1.setLife(10);
				var14_1.setSize(40F);
				var2.addParticle((ParticleOptions)(var14_1),true,(var3) + (var11),(var4) + (var12),(var5) + (var13),0D,0D,0D);
				var10++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}