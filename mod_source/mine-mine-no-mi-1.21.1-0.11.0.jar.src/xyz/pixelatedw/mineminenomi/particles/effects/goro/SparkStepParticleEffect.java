package xyz.pixelatedw.mineminenomi.particles.effects.goro;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.MineMineNoMi;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.math.VectorHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class SparkStepParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		ParticleType var7 = (MineMineNoMi.hasBlueGoroPackSelected()) ? (((ParticleType)(ModParticleTypes.GORO.get()))) : ((ParticleType)(((ParticleType)(ModParticleTypes.GORO_YELLOW.get()))));
		Vec3 var8 = new Vec3(var3,var4,var5);
		double var9 = 2.5D;
		double var10 = 0D;
		do{
			if ((var10) <= (6.283185307179586D)){
				double var11 = ((var9) * (Math.sin(var10))) + ((WyHelper.randomDouble()) / (2D));
				double var12 = ((var9) * (Math.cos(var10))) + ((WyHelper.randomDouble()) / (2D));
				double var13 = 0D;
				Vec3 var14 = new Vec3(var11,var12,var13);
				Vec3 var15 = VectorHelper.rotateAroundX(var14,Math.toRadians((double)(var1.getXRot())));
				Vec3 var16 = VectorHelper.rotateAroundY(var15,Math.toRadians((double)(-(var1.getYRot()))));
				Vec3 var17 = var8.subtract(var8.add(var16));
				double var18 = (var17.x) * (0.18D);
				double var19 = (var17.y) * (0.18D);
				double var20 = (var17.z) * (0.18D);
				SimpleParticleData var21 = new SimpleParticleData(var7);
				var21.setLife(7);
				var21.setSize(8F);
				var21.setMotion(var18,var19,var20);
				var2.addParticle((ParticleOptions)(var21),(var3) + (var16.x),(var4) + (var16.y),(var5) + (var16.z),0D,0D,0D);
				var10 = (var10) + (0.39269908169872414D);
				continue;
			}else{
				break;
			}
		} while (true);
	}
}