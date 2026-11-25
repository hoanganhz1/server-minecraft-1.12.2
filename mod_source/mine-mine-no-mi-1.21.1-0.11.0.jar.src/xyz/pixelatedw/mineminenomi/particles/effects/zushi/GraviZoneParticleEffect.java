package xyz.pixelatedw.mineminenomi.particles.effects.zushi;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$IFactory;

public class GraviZoneParticleEffect extends ParticleEffect<GraviZoneParticleEffect$Details> {
	public GraviZoneParticleEffect() {
		super((ParticleEffect$IFactory) ((ParticleEffect$IFactory<GraviZoneParticleEffect$Details>) (GraviZoneParticleEffect$Details::new)));
	}
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, GraviZoneParticleEffect$Details var6) {
		double var7 = 0D;
		do{
			if ((var7) < (7.283185307179586D)){
				SimpleParticleData var8 = new SimpleParticleData(((ParticleType)(ModParticleTypes.GASU.get())));
				var8.setLife(12);
				var8.setSize(2F);
				double var9 = (Math.cos(var7)) * ((double)(var6.getRange()));
				double var10 = (Math.sin(var7)) * ((double)(var6.getRange()));
				var8.setMotion((var9) / (20D),0D,(var10) / (20D));
				var8.setRotation(0F,0F,1F);
				var8.setRotationSpeed((var2.getRandom().nextFloat()) / (2F));
				var2.addParticle((ParticleOptions)(var8),true,(var3) + (var9),((var4) + (1D)) + ((double)(var6.getYOffset())),(var5) + (var10),0D,0D,0D);
				var7 = (var7) + (0.09817477042468103D);
				continue;
			}else{
				break;
			}
		} while (true);
	}
}