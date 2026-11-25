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
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$IFactory;

public class KamakuraParticleEffect extends ParticleEffect<KamakuraParticleEffect$Details> {
	public KamakuraParticleEffect() {
		super((ParticleEffect$IFactory) ((ParticleEffect$IFactory<KamakuraParticleEffect$Details>) (KamakuraParticleEffect$Details::new)));
	}
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, KamakuraParticleEffect$Details var6) {
		int var7 = var6.getSize();
		int var8 = 0;
		do{
			if ((var8) < (60)){
				double var9 = ((double)((2) * (WyHelper.randomWithRange(-(var7),var7)))) + (WyHelper.randomDouble());
				double var10 = ((double)((2) * (WyHelper.randomWithRange(0,var7)))) + (WyHelper.randomDouble());
				double var11 = ((double)((2) * (WyHelper.randomWithRange(-(var7),var7)))) + (WyHelper.randomDouble());
				double var12 = ((double)(WyHelper.randomWithRange(-1,1))) + (WyHelper.randomDouble());
				double var13 = ((double)(WyHelper.randomWithRange(-1,1))) + (WyHelper.randomDouble());
				double var14 = 1.2D;
				var12 = (var12) * ((var14) / (25D));
				var13 = (var13) * ((var14) / (25D));
				float var15 = (float)((1) + (WyHelper.randomWithRange(5,7)));
				ParticleType var16 = ((ParticleType)(ModParticleTypes.YUKI.get()));
				if (((var8) % (5)) == (0)){
					var16 = ((ParticleType)(ModParticleTypes.YUKI3.get()));
				}else{
					if (((var8) % (2)) == (0)){
						var16 = ((ParticleType)(ModParticleTypes.YUKI2.get()));
					}else{
						var16 = ((ParticleType)(ModParticleTypes.YUKI.get()));
					}
				}
				float var17 = (var2.getRandom().nextFloat()) / (4F);
				SimpleParticleData var18 = new SimpleParticleData(var16);
				var18.setLife(100);
				var18.setSize(var15);
				var18.setMotion(var12,-0.05D,var13);
				var18.setRotation(0F,0F,1F);
				var18.setRotationSpeed(var17);
				var18.setHasMotionDecay(false);
				var2.addParticle((ParticleOptions)(var18),true,(var3) + (var9),(var4) + (var10),(var5) + (var11),0D,0D,0D);
				var8++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}