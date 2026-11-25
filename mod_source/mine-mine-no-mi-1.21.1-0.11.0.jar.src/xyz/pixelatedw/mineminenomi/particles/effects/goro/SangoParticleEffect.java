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

public class SangoParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		ParticleType var7 = (MineMineNoMi.hasBlueGoroPackSelected()) ? (((ParticleType)(ModParticleTypes.GORO.get()))) : ((ParticleType)(((ParticleType)(ModParticleTypes.GORO_YELLOW.get()))));
		ParticleType var8 = (MineMineNoMi.hasBlueGoroPackSelected()) ? (((ParticleType)(ModParticleTypes.GORO2.get()))) : ((ParticleType)(((ParticleType)(ModParticleTypes.GORO2_YELLOW.get()))));
		int var9 = 0;
		do{
			if ((var9) < (16)){
				double var10 = WyHelper.randomDouble();
				double var11 = WyHelper.randomDouble();
				double var12 = WyHelper.randomDouble();
				SimpleParticleData var13 = new SimpleParticleData(var8);
				var13.setLife(5);
				var13.setSize(2F);
				var13.setColor(1F,1F,1F,0.7F);
				var2.addParticle((ParticleOptions)(var13),true,(var3) + (var10),(var4) + (var11),(var5) + (var12),0D,0D,0D);
				SimpleParticleData var14 = new SimpleParticleData(var7);
				var14.setLife(5);
				var14.setSize(2F);
				var14.setColor(1F,1F,1F,0.7F);
				var14.setRotation(0F,0F,1F);
				var2.addParticle((ParticleOptions)(var13),true,(var3) + (var10),(var4) + (var11),(var5) + (var12),0D,0D,0D);
				var9++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}