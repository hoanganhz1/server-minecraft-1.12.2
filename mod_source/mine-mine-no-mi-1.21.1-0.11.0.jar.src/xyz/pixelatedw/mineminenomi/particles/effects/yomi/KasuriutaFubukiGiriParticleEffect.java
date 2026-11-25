package xyz.pixelatedw.mineminenomi.particles.effects.yomi;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class KasuriutaFubukiGiriParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (20)){
				double var8 = WyHelper.randomDouble();
				double var9 = WyHelper.randomDouble();
				double var10 = WyHelper.randomDouble();
				SimpleParticleData var11 = new SimpleParticleData(((ParticleType)(ModParticleTypes.HIE.get())));
				var11.setLife(14);
				var11.setAnimationSpeed(2);
				var11.setRotation(0F,0F,1F);
				var11.setRotationSpeed((((var7) % (2)) == (0)) ? (0.07F) : (-0.07F));
				var11.setSize(1.3F);
				var2.addParticle((ParticleOptions)(var11),true,(var3) + (var8),((var4) + (1.5D)) + (var9),(var5) + (var10),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}