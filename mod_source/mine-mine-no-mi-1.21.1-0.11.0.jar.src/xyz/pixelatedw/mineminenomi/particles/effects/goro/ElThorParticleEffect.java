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

public class ElThorParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		double var10 = 0.0;
		double var11 = 0.0;
		double var12 = 0.0;
		ParticleType var7 = (MineMineNoMi.hasBlueGoroPackSelected()) ? (((ParticleType)(ModParticleTypes.GORO2.get()))) : ((ParticleType)(((ParticleType)(ModParticleTypes.GORO2_YELLOW.get()))));
		ParticleType var8 = (MineMineNoMi.hasBlueGoroPackSelected()) ? (((ParticleType)(ModParticleTypes.GORO3.get()))) : ((ParticleType)(((ParticleType)(ModParticleTypes.GORO3_YELLOW.get()))));
		int var9 = 0;
		do{
			if ((var9) < (30)){
				var10 = ((double)(WyHelper.randomWithRange(-32,32))) + (WyHelper.randomDouble());
				var11 = (72D) + (((double)(WyHelper.randomWithRange(-3,3))) + (WyHelper.randomDouble()));
				var12 = ((double)(WyHelper.randomWithRange(-32,32))) + (WyHelper.randomDouble());
				SimpleParticleData var13 = new SimpleParticleData(var8);
				var13.setLife(140);
				var13.setSize(50F);
				if (((var9) % (2)) == (0)){
					var13.setColor(0.4F,0.4F,0.4F);
				}else{
					var13.setColor(0.3F,0.3F,0.3F);
				}
				var2.addParticle((ParticleOptions)(var13),true,((var3) + (0.5D)) + (var10),(var4) + (var11),(var5) + (var12),0D,0D,0D);
				var9++;
				continue;
			}else{
				break;
			}
		} while (true);
		var9 = 0;
		do{
			if ((var9) < (16)){
				var10 = ((double)(WyHelper.randomWithRange(-4,4))) + (WyHelper.randomDouble());
				var11 = (72D) + (WyHelper.randomDouble());
				var12 = ((double)(WyHelper.randomWithRange(-4,4))) + (WyHelper.randomDouble());
				SimpleParticleData var13_1 = new SimpleParticleData(var7);
				var13_1.setLife(100);
				var13_1.setSize(15F);
				var2.addParticle((ParticleOptions)(var13_1),true,((var3) + (0.5D)) + (var10),(var4) + (var11),(var5) + (var12),0D,0D,0D);
				var9++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}