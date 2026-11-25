package xyz.pixelatedw.mineminenomi.particles.effects.mera;

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

public class HibashiraParticleEffect extends ParticleEffect<HibashiraParticleEffect$Details> {
	// Fields
	public static final HibashiraParticleEffect$Details NO_DETAILS = new HibashiraParticleEffect$Details();

	public HibashiraParticleEffect() {
		super((ParticleEffect$IFactory) ((ParticleEffect$IFactory<HibashiraParticleEffect$Details>) (HibashiraParticleEffect$Details::new)));
	}
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, HibashiraParticleEffect$Details var6) {
		double var7 = 0D;
		double var8 = (3.5D) * ((double)(var6.getRadiusScale()));
		RandomSource var9 = var2.random;
		LOOP_1:
		do{
			if ((var7) < (1D)){
				var7 = (var7) + (1.5707963267948966D);
				double var10 = 0D;
				do{
					if ((var10) <= (6.283185307179586D)){
						double var11 = (var7) * (Math.cos(var10));
						double var12 = (var7) * (Math.sin(var10));
						double var13 = ((Math.sin(var10)) / (10D)) * ((double)(var6.getMotionScale()));
						double var14 = (0.3D) + (((var9.nextDouble()) / (10D)) * ((double)(var6.getMotionScale())));
						double var15 = ((-(Math.cos(var10))) / (10D)) * ((double)(var6.getMotionScale()));
						SimpleParticleData var16 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MERA.get())));
						var16.setLife(WyHelper.randomWithRange(40,50));
						var16.setSize(((float)(WyHelper.randomWithRange(7,10))) * (var6.getDensityScale()));
						float var17 = WyHelper.colorTolerance(0.7F);
						var16.setColor(var17,var17,var17,0.5F);
						var16.setMotion(var13,var14,var15);
						var2.addParticle((ParticleOptions)(var16),true,((var3) + ((var11) * (var8))) + ((WyHelper.randomDouble()) / (2D)),(var4) - (10D),((var5) + ((var12) * (var8))) + ((WyHelper.randomDouble()) / (2D)),0D,0D,0D);
						var2.addParticle((ParticleOptions)(var16),true,((var3) + ((var11) * (var8))) + ((WyHelper.randomDouble()) / (2D)),(var4) - (5D),((var5) + ((var12) * (var8))) + ((WyHelper.randomDouble()) / (2D)),0D,0D,0D);
						var2.addParticle((ParticleOptions)(var16),true,((var3) + ((var11) * (var8))) + ((WyHelper.randomDouble()) / (2D)),var4,((var5) + ((var12) * (var8))) + ((WyHelper.randomDouble()) / (2D)),0D,0D,0D);
						var2.addParticle((ParticleOptions)(var16),true,((var3) + ((var11) * (var8))) + ((WyHelper.randomDouble()) / (2D)),((var4) + (5D)) + ((double)(WyHelper.randomWithRange(-2,0))),((var5) + ((var12) * (var8))) + ((WyHelper.randomDouble()) / (2D)),0D,0D,0D);
						var2.addParticle((ParticleOptions)(var16),true,((var3) + ((var11) * (var8))) + ((WyHelper.randomDouble()) / (2D)),((var4) + (10D)) + ((double)(WyHelper.randomWithRange(-2,0))),((var5) + ((var12) * (var8))) + ((WyHelper.randomDouble()) / (2D)),0D,0D,0D);
						var10 = (var10) + ((3.141592653589793D) / ((8D) * ((double)(var6.getRadiusScale()))));
						continue;
					}else{
						continue LOOP_1;
					}
				} while (true);
			}else{
				break;
			}
		} while (true);
	}
}