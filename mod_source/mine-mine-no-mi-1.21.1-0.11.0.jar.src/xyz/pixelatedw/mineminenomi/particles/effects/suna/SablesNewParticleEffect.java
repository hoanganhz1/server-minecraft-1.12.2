package xyz.pixelatedw.mineminenomi.particles.effects.suna;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$IFactory;

public class SablesNewParticleEffect extends ParticleEffect<SablesNewParticleEffect$Details> {
	public SablesNewParticleEffect() {
		super((ParticleEffect$IFactory) ((ParticleEffect$IFactory<SablesNewParticleEffect$Details>) (SablesNewParticleEffect$Details::new)));
	}
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, SablesNewParticleEffect$Details var6) {
		int var7 = 0;
		int var8 = (int)((var6.size) * (1.25F));
		double var9 = (double)((var6.size) / (7F));
		double var10 = (double)((var6.size) * (3F));
		int var11 = 6;
		double var12 = 0.15D;
		double var13 = ((var10) / ((double)(var8))) / (4D);
		int var14 = 0;
		LOOP_1:
		do{
			if ((var14) < (var11)){
				double var15 = 0D;
				do{
					if ((var15) < ((double)(var8))){
						double var16 = (var15) * (var13);
						double var17 = ((double)(((360F) / ((float)(var11))) * ((float)(var14)))) + ((var15) * (25D));
						double var18 = (Math.cos(Math.toRadians((var17) - ((double)(var7))))) * (Math.max(var16,var9));
						double var19 = (Math.sin(Math.toRadians((var17) - ((double)(var7))))) * (Math.max(var16,var9));
						float var20 = WyHelper.colorTolerance(0.1F);
						SimpleParticleData var21 = new SimpleParticleData(((ParticleType)(ModParticleTypes.SUNA2.get())));
						var21.setLife(100);
						var21.setSize((2.5F) * ((var6.size) / (15F)));
						var21.setMotion(0D,(0.1D) + (Math.abs((WyHelper.randomDouble()) / (15D))),0D);
						var21.setColor((1F) - (var20),(1F) - (var20),(1F) - (var20));
						var1.level().addParticle((ParticleOptions)(var21),true,(var3) + (var18),(var4) + (var15),(var5) + (var19),0D,0D,0D);
						var15 = (var15) + (var12);
						continue;
					}else{
						break;
					}
				} while (true);
				var14++;
				continue LOOP_1;
			}else{
				break;
			}
		} while (true);
		var7 = (var7) + (2);
	}
}