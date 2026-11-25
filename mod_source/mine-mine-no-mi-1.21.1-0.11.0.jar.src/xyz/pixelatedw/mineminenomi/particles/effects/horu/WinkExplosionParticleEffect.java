package xyz.pixelatedw.mineminenomi.particles.effects.horu;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$IFactory;

public class WinkExplosionParticleEffect extends ParticleEffect<WinkExplosionParticleEffect$Details> {
	// Fields
	public static final WinkExplosionParticleEffect$Details EXPLOSION2 = new WinkExplosionParticleEffect$Details(2D);

	public WinkExplosionParticleEffect() {
		super((ParticleEffect$IFactory) ((ParticleEffect$IFactory<WinkExplosionParticleEffect$Details>) (WinkExplosionParticleEffect$Details::new)));
	}
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, WinkExplosionParticleEffect$Details var6) {
		int var7 = 0;
		do{
			if (((double)(var7)) < ((var6.explosionSize) * (2D))){
				double var8 = ((var3) + (WyHelper.randomWithRange((-(var6.explosionSize)) / (2D),(var6.explosionSize) / (2D)))) + (WyHelper.randomDouble());
				double var9 = (var4) + (WyHelper.randomDouble());
				double var10 = ((var5) + (WyHelper.randomWithRange((-(var6.explosionSize)) / (2D),(var6.explosionSize) / (2D)))) + (WyHelper.randomDouble());
				double var11 = ((double)(WyHelper.randomWithRange(-3,3))) + (WyHelper.randomDouble());
				double var12 = ((double)(WyHelper.randomWithRange(-3,3))) + (WyHelper.randomDouble());
				double var13 = ((double)(WyHelper.randomWithRange(-3,3))) + (WyHelper.randomDouble());
				double var14 = (0.5D) / (((5D) / (var6.explosionSize)) + (0.1D));
				var14 = (var14) * (((WyHelper.randomDouble()) * (2D)) + (0.30000001192092896D));
				var11 = (var11) * ((var14) / (2D));
				var12 = (var12) * ((var14) / (2D));
				var13 = (var13) * ((var14) / (2D));
				SimpleParticleData var15 = new SimpleParticleData(((ParticleType)(ModParticleTypes.HORU.get())));
				var15.setLife(20);
				var15.setSize(6F);
				var15.setMotion(var11,var12,var13);
				var2.addParticle((ParticleOptions)(var15),true,var8,(var9) + (1D),var10,0D,0D,0D);
				var2.addParticle((ParticleOptions)(ParticleTypes.EXPLOSION),true,var8,var9,var10,0D,0D,0D);
				var2.addParticle((ParticleOptions)(ParticleTypes.POOF),true,var3,(var4) + (1D),var5,0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}