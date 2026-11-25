package xyz.pixelatedw.mineminenomi.particles.effects;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class CommonExplosionParticleEffect extends ParticleEffect<CommonExplosionParticleEffect$Details> {
	// Fields
	public static final CommonExplosionParticleEffect$Details EXPLOSION1 = new CommonExplosionParticleEffect$Details(1D);
	public static final CommonExplosionParticleEffect$Details EXPLOSION2 = new CommonExplosionParticleEffect$Details(2D);
	public static final CommonExplosionParticleEffect$Details EXPLOSION4 = new CommonExplosionParticleEffect$Details(4D);
	public static final CommonExplosionParticleEffect$Details EXPLOSION5 = new CommonExplosionParticleEffect$Details(5D);
	public static final CommonExplosionParticleEffect$Details EXPLOSION6 = new CommonExplosionParticleEffect$Details(6D);
	public static final CommonExplosionParticleEffect$Details EXPLOSION8 = new CommonExplosionParticleEffect$Details(8D);
	public static final CommonExplosionParticleEffect$Details EXPLOSION10 = new CommonExplosionParticleEffect$Details(10D);

	public CommonExplosionParticleEffect() {
		super((ParticleEffect$IFactory) ((ParticleEffect$IFactory<CommonExplosionParticleEffect$Details>) (CommonExplosionParticleEffect$Details::new)));
	}
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, CommonExplosionParticleEffect$Details var6) {
		int var7 = 0;
		do{
			if (((double)(var7)) < ((var6.explosionSize) * (2D))){
				double var8 = ((var3) + (WyHelper.randomWithRange((-(var6.explosionSize)) / (2D),(var6.explosionSize) / (2D)))) + (WyHelper.randomDouble());
				double var9 = (var4) + (WyHelper.randomDouble());
				double var10 = ((var5) + (WyHelper.randomWithRange((-(var6.explosionSize)) / (2D),(var6.explosionSize) / (2D)))) + (WyHelper.randomDouble());
				double var11 = ((double)(WyHelper.randomWithRange(-2,2))) + (WyHelper.randomDouble());
				double var12 = ((double)(WyHelper.randomWithRange(-2,2))) + (WyHelper.randomDouble());
				double var13 = ((double)(WyHelper.randomWithRange(-2,2))) + (WyHelper.randomDouble());
				double var14 = (0.5D) / (((5D) / (var6.explosionSize)) + (0.1D));
				var14 = (var14) * (((WyHelper.randomDouble()) * (2D)) + (0.30000001192092896D));
				var11 = (var11) * ((var14) / (2D));
				var12 = (var12) * ((var14) / (2D));
				var13 = (var13) * ((var14) / (2D));
				var2.addParticle((ParticleOptions)(ParticleTypes.EXPLOSION),var8,(var9) + (1D),var10,0D,0D,0D);
				var2.addParticle((ParticleOptions)(ParticleTypes.POOF),var3,(var4) + (1D),var5,0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}