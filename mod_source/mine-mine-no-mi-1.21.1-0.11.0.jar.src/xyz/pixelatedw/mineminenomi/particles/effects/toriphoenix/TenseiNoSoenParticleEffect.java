package xyz.pixelatedw.mineminenomi.particles.effects.toriphoenix;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class TenseiNoSoenParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		var4 = (var4) + (1.5D);
		double var7 = 1D;
		double var8 = 0D;
		LOOP_1:
		do{
			if ((var8) < (3.141592653589793D)){
				var8 = (var8) + (0.7853981633974483D);
				double var9 = 0D;
				do{
					if ((var9) <= (6.283185307179586D)){
						double var10 = (((var7) * (Math.cos(var9))) * (Math.sin(var8))) + (WyHelper.randomDouble());
						double var11 = ((var7) * (Math.cos(var8))) + (WyHelper.randomDouble());
						double var12 = (((var7) * (Math.sin(var9))) * (Math.sin(var8))) + (WyHelper.randomDouble());
						SimpleParticleData var13 = new SimpleParticleData(((ParticleType)(ModParticleTypes.BLUE_FLAME.get())));
						var13.setLife(20);
						var13.setSize(2F);
						var13.setMotion(0D,0.02D,0D);
						var2.addParticle((ParticleOptions)(var13),true,(var3) + (var10),((var4) - (1D)) + (var11),(var5) + (var12),0D,0D,0D);
						var9 = (var9) + (1.5707963267948966D);
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