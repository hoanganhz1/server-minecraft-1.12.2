package xyz.pixelatedw.mineminenomi.particles.effects.toriphoenix;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class TenseiNoSoen2ParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		double var7 = 0D;
		RandomSource var8 = var2.random;
		LOOP_1:
		do{
			if ((var7) < (3D)){
				var7 = (var7) + (0.3141592653589793D);
				double var9 = 0D;
				do{
					if ((var9) <= (12.566370614359172D)){
						double var10 = (var7) * (Math.cos(var9));
						double var11 = (double)(var8.nextInt(1));
						double var12 = (var7) * (Math.sin(var9));
						double var13 = (var10) / (4D);
						double var14 = (0.05D) + ((double)(Mth.abs(((float)(WyHelper.randomDouble())) / (12F))));
						double var15 = (var12) / (4D);
						SimpleParticleData var16 = new SimpleParticleData(((ParticleType)(ModParticleTypes.BLUE_FLAME.get())));
						var16.setLife(20);
						var16.setSize(2F);
						var16.setMotion(var13,var14,var15);
						var2.addParticle((ParticleOptions)(var16),true,((var3) + ((var10) * (1.25D))) + (WyHelper.randomDouble()),((var4) + (0.5D)) + (var11),((var5) + ((var12) * (1.25D))) + (WyHelper.randomDouble()),0D,0D,0D);
						var9 = (var9) + (0.19634954084936207D);
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