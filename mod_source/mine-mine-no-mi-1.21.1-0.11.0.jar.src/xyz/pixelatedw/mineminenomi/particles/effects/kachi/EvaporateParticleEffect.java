package xyz.pixelatedw.mineminenomi.particles.effects.kachi;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class EvaporateParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (3)){
				double var8 = WyHelper.randomDouble();
				double var9 = WyHelper.randomDouble();
				double var10 = WyHelper.randomDouble();
				double var11 = ((double)(WyHelper.randomWithRange(0,1))) + (WyHelper.randomDouble());
				double var12 = ((double)(WyHelper.randomWithRange(0,1))) + (WyHelper.randomDouble());
				double var13 = ((double)(WyHelper.randomWithRange(0,1))) + (WyHelper.randomDouble());
				double var14 = 0.05D;
				var14 = (var14) * (((WyHelper.randomDouble()) * (2D)) + (0.30000001192092896D));
				var11 = (var11) * ((var14) / (2D));
				var12 = (var12) * ((var14) / (2D));
				var13 = (var13) * ((var14) / (2D));
				SimpleParticleData var15 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MOKU.get())));
				var15.setLife(10);
				var15.setSize(2.5F);
				var15.setMotion(var11,(var12) + (0.05D),var13);
				var2.addParticle((ParticleOptions)(var15),true,((var3) + (1.5D)) + (var8),(var4) + (var9),(var5) + (var10),0D,0D,0D);
				var8 = WyHelper.randomDouble();
				var9 = WyHelper.randomDouble();
				var10 = WyHelper.randomDouble();
				SimpleParticleData var16 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MERA.get())));
				var16.setLife(10);
				var16.setSize(2.5F);
				var16.setMotion(var11,(var12) + (0.05D),var13);
				var2.addParticle((ParticleOptions)(var16),true,((var3) + (1.5D)) + (var8),(var4) + (var9),(var5) + (var10),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}