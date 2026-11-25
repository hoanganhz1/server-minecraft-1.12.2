package xyz.pixelatedw.mineminenomi.particles.effects.hana;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class BloomParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (15)){
				double var8 = (WyHelper.randomDouble()) / (2D);
				double var9 = (WyHelper.randomDouble()) / (2D);
				double var10 = (WyHelper.randomDouble()) / (2D);
				double var11 = WyHelper.randomDouble();
				double var12 = WyHelper.randomDouble();
				double var13 = WyHelper.randomDouble();
				double var14 = 0.15D;
				var14 = (var14) * (((WyHelper.randomDouble()) * (2D)) + (0.30000001192092896D));
				var11 = (var11) * ((var14) / (5D));
				var12 = (var12) * ((var14) / (8D));
				var13 = (var13) * ((var14) / (5D));
				SimpleParticleData var15 = new SimpleParticleData(((ParticleType)(ModParticleTypes.HANA.get())));
				var15.setLife(20);
				var15.setSize(3F);
				var15.setMotion(Math.sin(var11),(var12) - (0.015D),Math.sin(var13));
				var2.addParticle((ParticleOptions)(var15),true,(var3) + (var8),((var4) + (var9)) + (1.25D),(var5) + (var10),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}