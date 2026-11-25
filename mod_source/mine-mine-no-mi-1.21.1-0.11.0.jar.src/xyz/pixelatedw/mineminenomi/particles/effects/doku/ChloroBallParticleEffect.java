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

public class ChloroBallParticleEffect extends ParticleEffect<DokuParticleEffectDetails> {
	public ChloroBallParticleEffect() {
		super((ParticleEffect$IFactory) ((ParticleEffect$IFactory<DokuParticleEffectDetails>) (DokuParticleEffectDetails::new)));
	}
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, DokuParticleEffectDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (12)){
				double var8 = ((double)(WyHelper.randomWithRange(-3,3))) + (WyHelper.randomDouble());
				double var9 = ((double)(WyHelper.randomWithRange(-3,3))) + (WyHelper.randomDouble());
				double var10 = ((double)(WyHelper.randomWithRange(-3,3))) + (WyHelper.randomDouble());
				double var11 = 0.25D;
				var11 = (var11) * (((WyHelper.randomDouble()) * (2D)) + (0.30000001192092896D));
				var8 = (var8) * ((var11) / (2D));
				var9 = (var9) * ((var11) / (2D));
				var10 = (var10) * ((var11) / (2D));
				SimpleParticleData var12 = new SimpleParticleData(((ParticleType)(ModParticleTypes.DOKU.get())));
				if (var6.hasVenomDemon()){
					var12.setColor(1F,0F,0F);
				}
				var12.setLife(8);
				var12.setSize(0.8F);
				var12.setMotion(var8,var9,var10);
				var1.level().addParticle((ParticleOptions)(var12),true,var3,var4,var5,0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}