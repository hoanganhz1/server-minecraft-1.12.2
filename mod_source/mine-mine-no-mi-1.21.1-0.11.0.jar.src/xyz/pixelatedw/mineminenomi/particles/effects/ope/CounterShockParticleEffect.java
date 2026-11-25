package xyz.pixelatedw.mineminenomi.particles.effects.ope;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class CounterShockParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (20)){
				double var8 = WyHelper.randomDouble();
				double var9 = WyHelper.randomDouble();
				double var10 = WyHelper.randomDouble();
				double var11 = (0.005D) + (Math.abs((WyHelper.randomDouble()) / (8D)));
				SimpleParticleData var12 = new SimpleParticleData(((ParticleType)(ModParticleTypes.GORO2.get())));
				var12.setLife(5);
				var12.setSize(3.5F);
				var12.setMotion(0D,var11,0D);
				var2.addParticle((ParticleOptions)(var12),true,(var3) + (var8),((var4) + (1.5D)) + (var9),(var5) + (var10),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}