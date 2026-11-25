package xyz.pixelatedw.mineminenomi.particles.effects.goro;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.MineMineNoMi;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunction;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class ElThorAimParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		ParticleType var7 = (MineMineNoMi.hasBlueGoroPackSelected()) ? (((ParticleType)(ModParticleTypes.GORO.get()))) : ((ParticleType)(((ParticleType)(ModParticleTypes.GORO_YELLOW.get()))));
		int var8 = 0;
		do{
			if ((var8) < (128)){
				double var9 = (WyHelper.randomDouble()) * (10D);
				double var10 = (WyHelper.randomDouble()) * (10D);
				SimpleParticleData var11 = new SimpleParticleData(var7);
				var11.setLife(10);
				var11.setSize(10F);
				var11.setFunction(EasingFunction.CUBIC_OUT);
				var2.addParticle((ParticleOptions)(var11),true,((var3) + (0.5D)) + (var9),(var4) + (0.5D),(var5) + (var10),0D,0D,0D);
				var8++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}