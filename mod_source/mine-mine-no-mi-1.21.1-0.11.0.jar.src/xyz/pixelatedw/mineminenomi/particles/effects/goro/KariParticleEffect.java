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
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$IFactory;

public class KariParticleEffect extends ParticleEffect<KariParticleEffect$Details> {
	public KariParticleEffect() {
		super((ParticleEffect$IFactory) ((ParticleEffect$IFactory<KariParticleEffect$Details>) (KariParticleEffect$Details::new)));
	}
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, KariParticleEffect$Details var6) {
		ParticleType var7 = (MineMineNoMi.hasBlueGoroPackSelected()) ? (((ParticleType)(ModParticleTypes.GORO.get()))) : ((ParticleType)(((ParticleType)(ModParticleTypes.GORO_YELLOW.get()))));
		ParticleType var8 = (MineMineNoMi.hasBlueGoroPackSelected()) ? (((ParticleType)(ModParticleTypes.GORO2.get()))) : ((ParticleType)(((ParticleType)(ModParticleTypes.GORO2_YELLOW.get()))));
		int var9 = 0;
		do{
			if ((var9) < ((16) * (var6.getRange()))){
				double var10 = ((double)(WyHelper.randomWithRange(-(var6.getRange()),var6.getRange()))) + (WyHelper.randomDouble());
				double var11 = ((double)(WyHelper.randomWithRange(-(var6.getRange()),(2) + (var6.getRange())))) + (WyHelper.randomDouble());
				double var12 = ((double)(WyHelper.randomWithRange(-(var6.getRange()),var6.getRange()))) + (WyHelper.randomDouble());
				SimpleParticleData var13 = new SimpleParticleData(var8);
				var13.setLife(5);
				var13.setSize(var6.getSize());
				var2.addParticle((ParticleOptions)(var13),true,(var3) + (var10),(var4) + (var11),(var5) + (var12),0D,0D,0D);
				SimpleParticleData var14 = new SimpleParticleData(var7);
				var14.setLife(5);
				var14.setSize(var6.getSize());
				var13.setRotation(0F,1F,0F);
				var2.addParticle((ParticleOptions)(var13),true,(var3) + (var10),(var4) + (var11),(var5) + (var12),0D,0D,0D);
				var9++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}