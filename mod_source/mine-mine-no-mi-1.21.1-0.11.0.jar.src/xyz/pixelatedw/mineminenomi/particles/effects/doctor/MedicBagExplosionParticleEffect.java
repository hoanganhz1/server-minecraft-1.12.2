package xyz.pixelatedw.mineminenomi.particles.effects.doctor;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class MedicBagExplosionParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		double var7 = 0D;
		RandomSource var8 = var2.random;
		LOOP_1:
		do{
			if ((var7) < (1D)){
				var7 = (var7) + (1.5707963267948966D);
				double var9 = 0D;
				do{
					if ((var9) <= (12.566370614359172D)){
						double var10 = (var7) * (Math.cos(var9));
						double var11 = (double)(var8.nextInt(1));
						double var12 = (var7) * (Math.sin(var9));
						double var13 = (var10) / (4D);
						double var14 = (0.05D) + ((var8.nextDouble()) / (7D));
						double var15 = (var12) / (4D);
						SimpleParticleData var16 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MEDIC.get())));
						var16.setLife(4);
						var16.setSize(2F);
						var16.setMotion(var13,var14,var15);
						var16.setColor(0F,0.8F,0F);
						var2.addParticle((ParticleOptions)(var16),true,((var3) + ((var10) * (0.75D))) + (WyHelper.randomDouble()),(var4) + (var11),((var5) + ((var12) * (0.75D))) + (WyHelper.randomDouble()),0D,0D,0D);
						SimpleParticleData var17 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MEDIC.get())));
						var17.setLife(7);
						var17.setSize(2.5F);
						var17.setMotion(var13,var14,var15);
						var17.setColor(0F,0.8F,0F);
						var2.addParticle((ParticleOptions)(var17),true,((var3) + ((var10) * (2D))) + (WyHelper.randomDouble()),(var4) + (var11),((var5) + ((var12) * (2D))) + (WyHelper.randomDouble()),0D,0D,0D);
						SimpleParticleData var18 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MEDIC.get())));
						var18.setLife(10);
						var18.setSize(4.5F);
						var18.setMotion(var13,(var14) * (2.25D),var15);
						var18.setColor(0F,0.8F,0F);
						var2.addParticle((ParticleOptions)(var18),true,((var3) + ((var10) * (3.25D))) + (WyHelper.randomDouble()),(var4) + (var11),((var5) + ((var12) * (3.25D))) + (WyHelper.randomDouble()),0D,0D,0D);
						var9 = (var9) + (0.09817477042468103D);
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