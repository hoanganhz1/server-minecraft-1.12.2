package xyz.pixelatedw.mineminenomi.particles.effects.extra;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class MH5GasParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (2048)){
				double var8 = ((double)(WyHelper.randomWithRange(-100,100))) + (WyHelper.randomDouble());
				double var9 = ((double)(WyHelper.randomWithRange(0,20))) + (WyHelper.randomDouble());
				double var10 = ((double)(WyHelper.randomWithRange(-100,100))) + (WyHelper.randomDouble());
				int var11 = (10) + (WyHelper.randomWithRange(0,15));
				double var12 = (WyHelper.randomDouble()) / (50D);
				if ((var12) < (0D)){
					var12 = 0.02D;
				}
				SimpleParticleData var13 = new SimpleParticleData(((ParticleType)(ModParticleTypes.DOKU.get())));
				if ((var2.getRandom().nextInt(3)) == (0)){
					var13 = new SimpleParticleData(((ParticleType)(ModParticleTypes.AWA_FOAM.get())));
					var13.setColor(1F,0F,1F,0.5F);
				}
				var13.setLife(var11);
				var13.setSize(2.5F);
				var13.setMotion(0D,(var12) / (2D),0D);
				var13.setRotation((var1.level().getRandom().nextBoolean()) ? (new Vec3(0D,0D,1D)) : (new Vec3(0D,0D,-1D)));
				var13.setRotationSpeed(0.05F);
				var2.addParticle((ParticleOptions)(var13),(var3) + ((var8) / (2D)),((var4) + (1D)) + ((var9) / (2D)),(var5) + ((var10) / (2D)),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}