package xyz.pixelatedw.mineminenomi.particles.effects.suna;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class DesertGirasoleParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (64)){
				double var8 = ((double)(WyHelper.randomWithRange(-15,15))) + (WyHelper.randomDouble());
				double var9 = ((double)(WyHelper.randomWithRange(-15,15))) + (WyHelper.randomDouble());
				SimpleParticleData var10 = new SimpleParticleData(((ParticleType)(ModParticleTypes.SUNA2.get())));
				var10.setLife((int)((80D) + ((0.2D) * ((double)(var7)))));
				var10.setSize(4F);
				var10.setMotion(0D,0.3D,0D);
				var2.addParticle((ParticleOptions)(var10),true,(var3) + (var8),((var4) + (0.5D)) - ((0.15D) * ((double)(var7))),(var5) + (var9),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}