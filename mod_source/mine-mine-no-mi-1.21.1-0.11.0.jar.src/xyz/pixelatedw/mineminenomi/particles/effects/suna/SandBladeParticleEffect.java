package xyz.pixelatedw.mineminenomi.particles.effects.suna;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class SandBladeParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	// Fields
	private boolean isActive;

	public SandBladeParticleEffect(boolean var1) {
		this.isActive = false;
		this.isActive = var1;
	}
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 7;
		int var8 = 0;
		do{
			if ((var8) < (128)){
				double var10 = 0.0;
				if (this.isActive){
					var10 = (((double)(WyHelper.randomWithRange(1,10))) + (var2.getRandom().nextDouble())) * (0.05D);
				}else{
					var10 = (WyHelper.randomDouble()) / (30D);
					var7 = 5;
				}
				var3 = (var3) + ((WyHelper.randomDouble()) / ((double)(var7)));
				var5 = (var5) + ((WyHelper.randomDouble()) / ((double)(var7)));
				float var11 = ((var2.getRandom().nextFloat()) + (2.5F)) / (2F);
				float var12 = WyHelper.colorTolerance(0.65F);
				SimpleParticleData var13 = new SimpleParticleData(((ParticleType)(ModParticleTypes.SUNA2.get())));
				var13.setMotion(0D,var10,0D);
				var13.setColor(var12,var12,var12);
				var13.setLife(20);
				var13.setRotation(0F,0F,1F);
				var13.setSize(var11);
				var2.addParticle((ParticleOptions)(var13),true,var3,(var4) + (0.2D),var5,0D,0D,0D);
				var8++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}