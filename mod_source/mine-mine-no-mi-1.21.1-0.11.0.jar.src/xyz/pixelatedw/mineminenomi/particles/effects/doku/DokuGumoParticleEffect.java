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

public class DokuGumoParticleEffect extends ParticleEffect<DokuParticleEffectDetails> {
	public DokuGumoParticleEffect() {
		super((ParticleEffect$IFactory) ((ParticleEffect$IFactory<DokuParticleEffectDetails>) (DokuParticleEffectDetails::new)));
	}
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, DokuParticleEffectDetails var6) {
		boolean var7 = var6.hasVenomDemon();
		int var8 = (var7) ? (5) : (3);
		int var9 = (var7) ? (6) : (3);
		int var10 = 0;
		do{
			if ((var10) < ((var7) ? (50) : (30))){
				double var11 = ((double)(WyHelper.randomWithRange(-(var8),var8))) + (WyHelper.randomDouble());
				double var12 = ((double)(WyHelper.randomWithRange(-(var9),var9))) + (WyHelper.randomDouble());
				double var13 = ((double)(WyHelper.randomWithRange(-(var8),var8))) + (WyHelper.randomDouble());
				int var14 = (5) + (WyHelper.randomWithRange(0,5));
				SimpleParticleData var15 = new SimpleParticleData(((ParticleType)(ModParticleTypes.DOKU.get())));
				if (var7){
					var15.setColor(1F,0F,0F);
				}
				var15.setLife(var14);
				var15.setSize(2F);
				var1.level().addParticle((ParticleOptions)(var15),true,(var3) + (var11),(var4) + (var12),(var5) + (var13),0D,0D,0D);
				var10++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}