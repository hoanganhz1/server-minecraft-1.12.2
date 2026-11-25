package xyz.pixelatedw.mineminenomi.particles.effects.doku;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$IFactory;

public class ChloroBallCloudParticleEffect extends ParticleEffect<DokuParticleEffectDetails> {
	public ChloroBallCloudParticleEffect() {
		super((ParticleEffect$IFactory) ((ParticleEffect$IFactory<DokuParticleEffectDetails>) (DokuParticleEffectDetails::new)));
	}
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, DokuParticleEffectDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (32)){
				double var8 = ((double)(WyHelper.randomWithRange(-2,2))) + (WyHelper.randomDouble());
				double var9 = ((double)(WyHelper.randomWithRange(-1,2))) + (WyHelper.randomDouble());
				double var10 = ((double)(WyHelper.randomWithRange(-2,2))) + (WyHelper.randomDouble());
				SimpleParticleData var11 = new SimpleParticleData(((ParticleType)(ModParticleTypes.DOKU.get())));
				if (var6.hasVenomDemon()){
					var11.setColor(1F,0F,0F);
				}
				var11.setLife(10);
				var11.setSize(2.5F);
				var11.setMotion(0D,0.02D,0D);
				var1.level().addParticle((ParticleOptions)(var11),true,(var3) + (var8),(var4) + (var9),(var5) + (var10),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}