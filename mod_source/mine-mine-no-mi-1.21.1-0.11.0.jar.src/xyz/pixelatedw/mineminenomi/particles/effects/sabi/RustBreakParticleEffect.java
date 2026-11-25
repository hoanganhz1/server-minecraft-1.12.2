package xyz.pixelatedw.mineminenomi.particles.effects.sabi;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class RustBreakParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (55)){
				double var8 = (0.5D) + ((WyHelper.randomDouble()) / (2D));
				double var9 = (0.5D) + ((WyHelper.randomDouble()) / (2D));
				double var10 = (0.5D) + ((WyHelper.randomDouble()) / (2D));
				double var11 = (var2.getRandom().nextGaussian()) * (0.02D);
				double var12 = (var2.getRandom().nextGaussian()) * (0.02D);
				double var13 = (var2.getRandom().nextGaussian()) * (0.02D);
				SimpleParticleData var14 = new SimpleParticleData(((ParticleType)(ModParticleTypes.RUST.get())));
				var14.setMotion(var11,var12,var13);
				var14.setLife(30);
				var14.setSize(4F);
				var2.addParticle((ParticleOptions)(var14),true,(var3) + (var8),(var4) + (var9),(var5) + (var10),0D,0D,0D);
				var2.addParticle((ParticleOptions)(ParticleTypes.POOF),true,var3,var4,var5,0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}