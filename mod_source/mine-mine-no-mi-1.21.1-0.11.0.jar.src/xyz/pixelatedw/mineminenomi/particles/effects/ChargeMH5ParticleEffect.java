package xyz.pixelatedw.mineminenomi.particles.effects;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.math.VectorHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class ChargeMH5ParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		Vec3 var7 = var1.getLookAngle().multiply(1D,1D,1D).add(0D,((double)(var1.getBbHeight())) / (1.3D),0D);
		double var8 = 0.8D;
		double var9 = 0D;
		do{
			if ((var9) <= (6.283185307179586D)){
				if ((var1.level().getRandom().nextInt(20)) != (0)){

				}else{
					double var10 = ((var8) * (Math.sin(var9))) + ((WyHelper.randomDouble()) / (2D));
					double var11 = ((var8) * (Math.cos(var9))) + ((WyHelper.randomDouble()) / (2D));
					double var12 = 0D;
					Vec3 var13 = new Vec3(var10,var11,var12);
					Vec3 var14 = VectorHelper.rotateAroundX(var13,Math.toRadians((double)(var1.getXRot())));
					Vec3 var15 = VectorHelper.rotateAroundY(var14,Math.toRadians((double)(-(var1.getYRot()))));
					Vec3 var16 = var1.position().add(var7.add(0D,-(((double)(var1.getBbHeight())) / (1.3D)),0D)).add(var15);
					Vec3 var17 = var1.position().subtract(var16);
					double var18 = (var17.x) * (0.03D);
					double var19 = (var17.y) * (0.03D);
					double var20 = (var17.z) * (0.03D);
					int var21 = var1.level().getRandom().nextInt(4);
					if ((var21) <= (1)){
						SimpleParticleData var22 = new SimpleParticleData(((ParticleType)(ModParticleTypes.PIKA.get())));
						var22.setLife(20);
						var22.setSize(1F);
						var22.setRotation(0F,1F,0F);
						var22.setMotion(var18,var19,var20);
						var2.addParticle((ParticleOptions)(var22),((var3) + (var7.x)) + (var15.x),((var4) + (var7.y)) + (var15.y),((var5) + (var7.z)) + (var15.z),0D,0D,0D);
					}else{
						if ((var21) == (2)){
							SimpleParticleData var22_1 = new SimpleParticleData(((ParticleType)(ModParticleTypes.AWA_FOAM.get())));
							var22_1.setLife(20);
							var22_1.setSize(1.5F);
							var22_1.setColor(1F,0F,1F,0.5F);
							var22_1.setMotion(var18,var19,var20);
							var22_1.setRotation(0F,0F,1F);
							var22_1.setRotationSpeed(0.1F);
							var2.addParticle((ParticleOptions)(var22_1),((var3) + (var7.x)) + (var15.x),((var4) + (var7.y)) + (var15.y),((var5) + (var7.z)) + (var15.z),0D,0D,0D);
						}else{
							if ((var21) == (3)){
								SimpleParticleData var22_2 = new SimpleParticleData(((ParticleType)(ModParticleTypes.DOKU.get())));
								var22_2.setLife(20);
								var22_2.setSize(1.5F);
								var22_2.setColor(1F,0F,1F,0.5F);
								var22_2.setMotion(var18,var19,var20);
								var22_2.setRotation(0F,0F,1F);
								var22_2.setRotationSpeed(0.05F);
								var2.addParticle((ParticleOptions)(var22_2),((var3) + (var7.x)) + (var15.x),((var4) + (var7.y)) + (var15.y),((var5) + (var7.z)) + (var15.z),0D,0D,0D);
							}
						}
					}
				}
				var9 = (var9) + (0.39269908169872414D);
				continue;
			}else{
				break;
			}
		} while (true);
	}
}