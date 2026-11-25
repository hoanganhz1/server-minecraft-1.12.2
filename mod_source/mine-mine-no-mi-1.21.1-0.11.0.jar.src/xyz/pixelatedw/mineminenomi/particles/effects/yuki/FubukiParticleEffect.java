package xyz.pixelatedw.mineminenomi.particles.effects.yuki;

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

public class FubukiParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (1024)){
				double var8 = ((double)(WyHelper.randomWithRange(-15,15))) + (WyHelper.randomDouble());
				double var9 = ((double)(WyHelper.randomWithRange(0,20))) + (WyHelper.randomDouble());
				double var10 = ((double)(WyHelper.randomWithRange(-15,15))) + (WyHelper.randomDouble());
				double var11 = ((double)(WyHelper.randomWithRange(-1,1))) + (WyHelper.randomDouble());
				double var12 = ((double)(WyHelper.randomWithRange(-1,1))) + (WyHelper.randomDouble());
				double var13 = 1.2D;
				var11 = (var11) * ((var13) / (25D));
				var12 = (var12) * ((var13) / (25D));
				float var14 = (float)((1) + (WyHelper.randomWithRange(0,3)));
				ParticleType var15 = ((ParticleType)(ModParticleTypes.YUKI.get()));
				if (((var7) % (5)) == (0)){
					var15 = ((ParticleType)(ModParticleTypes.YUKI3.get()));
				}else{
					if (((var7) % (2)) == (0)){
						var15 = ((ParticleType)(ModParticleTypes.YUKI2.get()));
					}else{
						var15 = ((ParticleType)(ModParticleTypes.YUKI.get()));
					}
				}
				float var16 = (var2.getRandom().nextFloat()) / (4F);
				SimpleParticleData var17 = new SimpleParticleData(var15);
				var17.setLife(300);
				var17.setSize(var14);
				var17.setRotation(0F,0F,1F);
				var17.setRotationSpeed(var16);
				var17.setMotion(var11,-0.05D,var12);
				var17.setHasMotionDecay(false);
				var2.addParticle((ParticleOptions)(var17),true,(var3) + (var8),(var4) + (var9),(var5) + (var10),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}