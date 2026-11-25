package xyz.pixelatedw.mineminenomi.particles.effects.cyborg;

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
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class ChargeRadicalBeamParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		Vec3 var7 = var1.getLookAngle().multiply(1D,1D,1D).add(0D,(double)(var1.getEyeHeight()),0D);
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
					Vec3 var16 = var1.position().add(var7.add(0D,(double)(-(var1.getEyeHeight())),0D)).add(var15);
					Vec3 var17 = var1.position().subtract(var16);
					double var18 = (var17.x) * (0.03D);
					double var19 = (var17.y) * (0.03D);
					double var20 = (var17.z) * (0.03D);
					SimpleParticleData var21 = new SimpleParticleData(((ParticleType)(ModParticleTypes.PIKA.get())));
					var21.setLife(20);
					var21.setSize(1F);
					var21.setRotation(0F,1F,0F);
					var21.setMotion(var18,var19,var20);
					var2.addParticle((ParticleOptions)(var21),true,((var3) + (var7.x)) + (var15.x),((var4) + (var7.y)) + (var15.y),((var5) + (var7.z)) + (var15.z),0D,0D,0D);
				}
				var9 = (var9) + (0.39269908169872414D);
				continue;
			}else{
				break;
			}
		} while (true);
	}
}