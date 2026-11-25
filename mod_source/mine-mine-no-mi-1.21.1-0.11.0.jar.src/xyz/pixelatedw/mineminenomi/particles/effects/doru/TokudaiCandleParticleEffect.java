package xyz.pixelatedw.mineminenomi.particles.effects.doru;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class TokudaiCandleParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (2048)){
				double var8 = ((double)(WyHelper.randomWithRange(-50,50))) + (WyHelper.randomDouble());
				double var9 = ((double)(WyHelper.randomWithRange(0,50))) + (WyHelper.randomDouble());
				double var10 = ((double)(WyHelper.randomWithRange(-50,50))) + (WyHelper.randomDouble());
				double var11 = ((double)(WyHelper.randomWithRange(-1,1))) + (WyHelper.randomDouble());
				double var12 = ((double)(WyHelper.randomWithRange(-1,1))) + (WyHelper.randomDouble());
				double var13 = 1.2D;
				var11 = (var11) * ((var13) / (25D));
				var12 = (var12) * ((var13) / (25D));
				float var14 = (float)((1) + (WyHelper.randomWithRange(0,2)));
				float var15 = (var2.getRandom().nextFloat()) / (4F);
				SimpleParticleData var16 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MOKU.get())));
				var16.setLife(300);
				var16.setSize(var14);
				var16.setRotation(0F,0F,1F);
				var16.setRotationSpeed(var15);
				var16.setMotion(var11,-0.05D,var12);
				var16.setHasMotionDecay(false);
				var2.addParticle((ParticleOptions)(var16),true,(var3) + (var8),(var4) + (var9),(var5) + (var10),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}