package xyz.pixelatedw.mineminenomi.particles.effects.hitodaibutsu;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class ImpactWaveParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		double var7 = 1D;
		int var8 = 0;
		LOOP_2:
		do{
			if ((var8) < (10)){
				double var9 = 0D;
				LOOP_1:
				do{
					if ((var9) < (3.141592653589793D)){
						var9 = (var9) + (0.19634954084936207D);
						double var10 = 0D;
						do{
							if ((var10) <= (6.283185307179586D)){
								double var11 = ((var7) * (Math.cos(var10))) * (Math.sin(var9));
								double var12 = (var7) * (Math.cos(var9));
								double var13 = ((var7) * (Math.sin(var10))) * (Math.sin(var9));
								SimpleParticleData var14 = new SimpleParticleData(((ParticleType)(ModParticleTypes.SUNA2.get())));
								var14.setLife(50);
								double var15 = ((double)(WyHelper.randomWithRange(-1,1))) + (WyHelper.randomDouble());
								double var16 = ((double)(WyHelper.randomWithRange(-1,1))) + (WyHelper.randomDouble());
								double var17 = 1.2D;
								var15 = (var15) * ((var17) / (25D));
								var16 = (var16) * ((var17) / (25D));
								var14.setMotion(var15,0.10000000149011612D,var16);
								var14.setSize(10F);
								var14.setColor(1F,1F,0.2F,0.5F);
								var2.addParticle((ParticleOptions)(var14),true,(var3) + (var11),(var4) + (var12),(var5) + (var13),0D,0D,0D);
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
				var7 = (var7) + (0.8D);
				var8++;
				continue LOOP_2;
			}else{
				break;
			}
		} while (true);
	}
}