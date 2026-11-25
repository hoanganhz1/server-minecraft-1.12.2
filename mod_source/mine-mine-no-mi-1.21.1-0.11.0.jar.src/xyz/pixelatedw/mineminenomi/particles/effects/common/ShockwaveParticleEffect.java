package xyz.pixelatedw.mineminenomi.particles.effects.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class ShockwaveParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (120)){
				double var8 = (WyHelper.randomDouble()) / (2D);
				BlockState var9 = var2.getBlockState(new BlockPos((int)(var3),(int)(var4),(int)(var5)));
				var2.addParticle((ParticleOptions)(new BlockParticleOption(ParticleTypes.BLOCK,var9)),((var3) + ((double)(WyHelper.randomWithRange(-1,1)))) + ((WyHelper.randomDouble()) / (20D)),((var4) + (var8)) + (0.5D),((var5) + ((double)(WyHelper.randomWithRange(-1,1)))) + ((WyHelper.randomDouble()) / (20D)),0D,0D,0D);
				double var10 = (WyHelper.randomDouble()) * (2D);
				double var11 = (WyHelper.randomDouble()) * (2D);
				double var12 = (WyHelper.randomDouble()) * (2D);
				SimpleParticleData var13 = null;
				if (((var7) % (5)) == (0)){
					var13 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MOKU2.get())));
				}else{
					var13 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MOKU.get())));
				}
				var13.setMotion((-(var10)) / (20D),0.015D,(-(var12)) / (20D));
				var13.setLife(10);
				var13.setSize(1F);
				var2.addParticle((ParticleOptions)(var13),(var3) + (var10),(var4) + (var11),(var5) + (var12),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}