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

public class DesertSpadaParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (70)){
				double var8 = (WyHelper.randomDouble()) * (0.5D);
				double var9 = (WyHelper.randomDouble()) * (0.5D);
				SimpleParticleData var10 = new SimpleParticleData(((ParticleType)(ModParticleTypes.SUNA2.get())));
				var10.setLife(20);
				var10.setSize(1.5F);
				var10.setMotion(var1.getDeltaMovement().x,((var1.getDeltaMovement().y) + (0.25D)) + ((Math.abs(WyHelper.randomDouble())) / (7D)),var1.getDeltaMovement().z);
				var2.addParticle((ParticleOptions)(var10),true,(var3) + (var8),var4,(var5) + (var9),0D,0D,0D);
				var10.setLife(60);
				var10.setMotion(0D,(0.05D) + ((Math.abs(WyHelper.randomDouble())) / (8D)),0D);
				var2.addParticle((ParticleOptions)(var10),true,(var3) + (var8),var4,(var5) + (var9),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}