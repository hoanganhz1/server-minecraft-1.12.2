package xyz.pixelatedw.mineminenomi.particles.effects;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class BerserkerPowerupParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		Vec3 var7 = new Vec3(var3,var4,var5);
		double var8 = 2D;
		double var9 = 0D;
		do{
			if ((var9) <= (6.283185307179586D)){
				double var10 = ((var8) * (Math.cos(var9))) + ((WyHelper.randomDouble()) / (5D));
				double var11 = ((var8) * (Math.sin(var9))) + ((WyHelper.randomDouble()) / (5D));
				Vec3 var12 = var7.subtract(var7.add(new Vec3(var10,var4,var11))).normalize().multiply(1.25D,1D,1.25D);
				SimpleParticleData var13 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MOKU.get())));
				var13.setLife(15);
				var13.setSize(7F);
				var13.setMotion(-(var12.x),0D,-(var12.z));
				var13.setRotation(0F,0F,1F);
				var13.setRotationSpeed(1F);
				var2.addParticle((ParticleOptions)(var13),true,(var3) + (var10),(var4) + (0.3D),(var5) + (var11),0D,0D,0D);
				SimpleParticleData var14 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MOKU2.get())));
				var14.setLife(10);
				var14.setSize(5F);
				var14.setMotion(-(var12.x),0D,-(var12.z));
				var2.addParticle((ParticleOptions)(var14),true,(var3) + (var10),(var4) + (0.3D),(var5) + (var11),0D,0D,0D);
				var9 = (var9) + (0.09817477042468103D);
				continue;
			}else{
				break;
			}
		} while (true);
	}
}