package xyz.pixelatedw.mineminenomi.particles.effects.doku;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class VenomDemonParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (3)){
				double var8 = ((double)(WyHelper.randomWithRange(-2,2))) + (WyHelper.randomDouble());
				double var9 = ((double)(WyHelper.randomWithRange(0,2))) + (WyHelper.randomDouble());
				double var10 = ((double)(WyHelper.randomWithRange(-2,2))) + (WyHelper.randomDouble());
				int var11 = (5) + (WyHelper.randomWithRange(0,5));
				double var12 = (0.015D) + (Math.abs((WyHelper.randomDouble()) / (8D)));
				SimpleParticleData var13 = new SimpleParticleData(((ParticleType)(ModParticleTypes.DOKU.get())));
				var13.setLife(var11);
				var13.setSize(3F);
				var13.setColor(1F,0F,0F);
				var13.setMotion(0D,var12,0D);
				var1.level().addParticle((ParticleOptions)(var13),true,(var3) + (var8),((var4) + (0.25D)) + (var9),(var5) + (var10),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}