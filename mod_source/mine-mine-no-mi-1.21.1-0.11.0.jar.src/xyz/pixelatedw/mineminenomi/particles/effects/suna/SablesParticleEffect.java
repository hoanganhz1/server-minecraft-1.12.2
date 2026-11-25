package xyz.pixelatedw.mineminenomi.particles.effects.suna;

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

public class SablesParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	// Fields
	private float size;

	public SablesParticleEffect() {
		this.size = 1F;
		this.size = 1F;
	}
	public SablesParticleEffect(float var1) {
		this.size = 1F;
		this.size = var1;
	}
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		int var8 = (35) + ((int)((2F) * (this.size)));
		double var9 = (double)((1F) * (this.size));
		double var10 = (double)((20F) * (this.size));
		int var11 = 12;
		double var12 = 0.25D;
		double var13 = ((var10) / ((double)(var8))) * ((double)((1F) - ((this.size) / (2F))));
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
						SimpleParticleData var20 = new SimpleParticleData(((ParticleType)(ModParticleTypes.SUNA2.get())));
						var20.setLife(70);
						var20.setSize((2.5F) * (this.size));
						var20.setMotion(var1.getDeltaMovement().x,((var1.getDeltaMovement().y) + (0.1D)) - (Math.abs((WyHelper.randomDouble()) / (5D))),var1.getDeltaMovement().z);
						var1.level().addParticle((ParticleOptions)(var20),true,(var3) + (var18),(var4) + (var15),(var5) + (var19),0D,0D,0D);
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