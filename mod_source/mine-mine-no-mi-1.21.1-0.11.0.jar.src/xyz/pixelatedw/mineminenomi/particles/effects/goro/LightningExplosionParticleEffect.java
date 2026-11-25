package xyz.pixelatedw.mineminenomi.particles.effects.goro;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.MineMineNoMi;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class LightningExplosionParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	// Fields
	private int explosionSize;

	public LightningExplosionParticleEffect() {
		this(2);
	}
	public LightningExplosionParticleEffect(int var1) {
		this.explosionSize = var1;
	}
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		ParticleType var7 = (MineMineNoMi.hasBlueGoroPackSelected()) ? (((ParticleType)(ModParticleTypes.GORO.get()))) : ((ParticleType)(((ParticleType)(ModParticleTypes.GORO_YELLOW.get()))));
		ParticleType var8 = (MineMineNoMi.hasBlueGoroPackSelected()) ? (((ParticleType)(ModParticleTypes.GORO2.get()))) : ((ParticleType)(((ParticleType)(ModParticleTypes.GORO2_YELLOW.get()))));
		int var9 = 0;
		do{
			if ((var9) < ((this.explosionSize) * (2))){
				double var10 = ((var3) + ((double)(WyHelper.randomWithRange((-(this.explosionSize)) / (2),(this.explosionSize) / (2))))) + (WyHelper.randomDouble());
				double var11 = (var4) + (WyHelper.randomDouble());
				double var12 = ((var5) + ((double)(WyHelper.randomWithRange((-(this.explosionSize)) / (2),(this.explosionSize) / (2))))) + (WyHelper.randomDouble());
				double var13 = ((double)(WyHelper.randomWithRange(-3,3))) + (WyHelper.randomDouble());
				double var14 = ((double)(WyHelper.randomWithRange(-3,3))) + (WyHelper.randomDouble());
				double var15 = ((double)(WyHelper.randomWithRange(-3,3))) + (WyHelper.randomDouble());
				double var16 = (0.5D) / (((5D) / ((double)(this.explosionSize))) + (0.1D));
				var16 = (var16) * (((WyHelper.randomDouble()) * (2D)) + (0.30000001192092896D));
				var13 = (var13) * ((var16) / (2D));
				var14 = (var14) * ((var16) / (2D));
				var15 = (var15) * ((var16) / (2D));
				float var17 = (float)((1) + (WyHelper.randomWithRange(2,5)));
				ParticleType var18 = var7;
				if (((var9) % (2)) == (0)){
					var18 = var8;
				}
				SimpleParticleData var19 = new SimpleParticleData(var18);
				var19.setLife(30);
				var19.setSize(var17);
				var19.setMotion(var13,var14,var15);
				var2.addParticle((ParticleOptions)(var19),true,var10,(var11) + (1D),var12,0D,0D,0D);
				var2.addParticle((ParticleOptions)(ParticleTypes.POOF),true,var1.getX(),(var1.getY()) + (1D),var1.getZ(),0D,0D,0D);
				var9++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}