package xyz.pixelatedw.mineminenomi.particles.effects.blackleg;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class BienCuitGrillShotParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		double var7 = var1.getDeltaMovement().x;
		double var8 = var1.getDeltaMovement().y;
		double var9 = var1.getDeltaMovement().z;
		int var10 = -2;
		LOOP_2:
		do{
			if ((var10) <= (2)){
				int var11 = -10;
				LOOP_1:
				do{
					if ((var11) <= (10)){
						int var12 = 0;
						do{
							if ((var12) < (3)){
								double var13 = (WyHelper.randomDouble()) / (2D);
								double var14 = (WyHelper.randomDouble()) / (2D);
								double var15 = (WyHelper.randomDouble()) / (2D);
								SimpleParticleData var16 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MERA.get())));
								var16.setLife(10);
								var16.setSize(1F);
								var16.setMotion((var7) / (1.7D),(var8) / (1.7D),(var9) / (1.7D));
								var2.addParticle((ParticleOptions)(var16),true,((var3) - ((double)((var11) / (5)))) + (var13),(((var4) + (1.5D)) + (((double)(var10)) / (1.2D))) + (var14),((var5) - ((double)((var11) / (5)))) + (var15),0D,0D,0D);
								var12++;
								continue;
							}else{
								break;
							}
						} while (true);
						var11++;
						continue LOOP_1;
					}else{
						break;
					}
				} while (true);
				var10++;
				continue LOOP_2;
			}else{
				break;
			}
		} while (true);
		SimpleParticleData var11_1 = new SimpleParticleData(((ParticleType)(ModParticleTypes.GRILL.get())));
		var11_1.setLife(10);
		var11_1.setSize(40F);
		var11_1.setMotion((var7) / (1.4D),(var8) / (1.4D),(var9) / (1.4D));
		var2.addParticle((ParticleOptions)(var11_1),true,var3,(var4) + (1.5D),var5,0D,0D,0D);
	}
}