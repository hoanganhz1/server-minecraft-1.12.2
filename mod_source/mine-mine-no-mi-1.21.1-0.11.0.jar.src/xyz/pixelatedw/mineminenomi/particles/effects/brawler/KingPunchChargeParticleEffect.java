package xyz.pixelatedw.mineminenomi.particles.effects.brawler;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class KingPunchChargeParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		double var8 = 0D;
		RandomSource var9 = var2.random;
		LOOP_1:
		do{
			if ((var8) < (1D)){
				var8 = (var8) + (1.5707963267948966D);
				double var10 = 0D;
				do{
					if ((var10) <= (12.566370614359172D)){
						double var11 = (var8) * (Math.cos(var10));
						double var12 = (double)(var9.nextInt(1));
						double var13 = (var8) * (Math.sin(var10));
						double var14 = (-(var11)) / (20D);
						double var15 = (0.05D) + ((var9.nextDouble()) / (10D));
						double var16 = (-(var13)) / (20D);
						SimpleParticleData var17 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MOKU.get())));
						if (((var7) % (2)) == (0)){
							var17 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MOKU2.get())));
						}
						double var18 = (var2.random.nextDouble()) * ((double)(WyHelper.randomWithRange(7,9)));
						double var19 = 1D;
						double var20 = (var2.random.nextDouble()) * ((double)(WyHelper.randomWithRange(7,9)));
						BlockState var21 = var2.getBlockState(new BlockPos((int)((var3) + (var18)),(int)(var4),(int)((var5) + (var20))).below());
						var2.addParticle((ParticleOptions)(new BlockParticleOption(ParticleTypes.BLOCK,var21)),((var3) - (4D)) + (var18),((var4) - (1D)) + (var19),((var5) - (4D)) + (var20),0D,0D,0D);
						var17.setLife(1);
						var17.setSize(1F);
						var17.setMotion(var14,var15,var16);
						var2.addParticle((ParticleOptions)(var17),true,((var3) + ((var11) * (1.25D))) + ((WyHelper.randomDouble()) * (2D)),(var4) + (var12),((var5) + ((var13) * (1.25D))) + ((WyHelper.randomDouble()) * (2D)),0D,0D,0D);
						var17.setLife(3);
						var17.setSize(1F);
						var17.setMotion(var14,(var15) + (0.001D),var16);
						var2.addParticle((ParticleOptions)(var17),true,((var3) + ((var11) * (1.5D))) + (WyHelper.randomDouble()),(var4) + (var12),((var5) + ((var13) * (1.5D))) + (WyHelper.randomDouble()),0D,0D,0D);
						var17.setLife(5);
						var17.setSize(1F);
						var17.setMotion(var14,(var15) + (0.005D),var16);
						var2.addParticle((ParticleOptions)(var17),true,((var3) + ((var11) * (1.75D))) + ((WyHelper.randomDouble()) * (2D)),(var4) + (var12),((var5) + ((var13) * (1.75D))) + ((WyHelper.randomDouble()) * (2D)),0D,0D,0D);
						var7++;
						var10 = (var10) + (0.19634954084936207D);
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