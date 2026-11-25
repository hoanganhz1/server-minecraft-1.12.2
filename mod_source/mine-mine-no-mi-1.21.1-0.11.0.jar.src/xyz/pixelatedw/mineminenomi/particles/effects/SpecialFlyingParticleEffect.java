package xyz.pixelatedw.mineminenomi.particles.effects;

import java.util.function.Supplier;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class SpecialFlyingParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	// Fields
	private Supplier<ParticleType<SimpleParticleData>> particle;

	public SpecialFlyingParticleEffect(Supplier<ParticleType<SimpleParticleData>> var1) {
		this.particle = var1;
	}
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (WyHelper.randomWithRange(5,10))){
				double var8 = (WyHelper.randomDouble()) / (1.7D);
				double var9 = (-0.55D) + ((WyHelper.randomDouble()) / (3D));
				double var10 = (WyHelper.randomDouble()) / (1.7D);
				int var11 = (1) + (WyHelper.randomWithRange(0,20));
				double var12 = ((WyHelper.randomDouble()) / (10D)) * (-1D);
				if ((var12) > (0D)){
					var12 = -0.005D;
				}
				SimpleParticleData var13 = new SimpleParticleData(((ParticleType)(this.particle.get())));
				var13.setLife(var11);
				var13.setSize(2.5F);
				var13.setMotion(0D,var12,0D);
				var2.addParticle((ParticleOptions)(var13),true,(var3) + (var8),((var4) + (1D)) + (var9),(var5) + (var10),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}