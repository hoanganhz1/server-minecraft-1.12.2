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

public class GenericUseYellowLightningParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		ParticleType var7 = (MineMineNoMi.hasBlueGoroPackSelected()) ? (((ParticleType)(ModParticleTypes.GORO.get()))) : ((ParticleType)(((ParticleType)(ModParticleTypes.GORO_YELLOW.get()))));
		int var8 = 0;
		do{
			if ((var8) < (25)){
				double var9 = WyHelper.randomDouble();
				double var10 = ((double)(WyHelper.randomWithRange(-1,1))) + ((WyHelper.randomDouble()) / (2D));
				double var11 = WyHelper.randomDouble();
				int var12 = WyHelper.randomWithRange(2,8);
				SimpleParticleData var13 = new SimpleParticleData(var7);
				var13.setLife(var12);
				var13.setSize(0.4F);
				var13.setMotion(0D,0.01D,0D);
				var2.addParticle((ParticleOptions)(var13),true,(var3) + (var9),((var4) + (1D)) + (var10),(var5) + (var11),0D,0D,0D);
				var8++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}