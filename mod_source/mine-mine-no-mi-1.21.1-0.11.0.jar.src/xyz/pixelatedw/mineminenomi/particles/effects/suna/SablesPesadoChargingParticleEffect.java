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

public class SablesPesadoChargingParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		double var7 = 1D;
		double var8 = 0D;
		SimpleParticleData var9 = new SimpleParticleData(((ParticleType)(ModParticleTypes.SUNA2.get())));
		var9.setLife(20);
		var9.setSize(1.3F);
		LOOP_1:
		do{
			if ((var8) < (6.283185307179586D)){
				var8 = (var8) + (0.19634954084936207D);
				double var10 = 0D;
				do{
					if ((var10) <= (6.283185307179586D)){
						double var11 = ((((0.45D) * (var7)) * ((6.283185307179586D) + (var10))) * (Math.cos(((var10) * (var8)) * (3.141592653589793D)))) + (WyHelper.randomDouble());
						double var12 = ((1.5D) * (var7)) * (var10);
						double var13 = ((((0.45D) * (var7)) * ((6.283185307179586D) + (var10))) * (Math.sin(((var10) * (var8)) * (3.141592653589793D)))) + (WyHelper.randomDouble());
						var9.setMotion(0D,(0.01D) + ((Math.abs(WyHelper.randomDouble())) / (5D)),0D);
						var1.level().addParticle((ParticleOptions)(var9),true,(var3) + (var11),(var4) + (var12),(var5) + (var13),0D,0D,0D);
						var10 = (var10) + (0.19634954084936207D);
						continue;
					}else{
						continue LOOP_1;
					}
				} while (true);
			}else{
				break;
			}
		} while (true);
	}
}