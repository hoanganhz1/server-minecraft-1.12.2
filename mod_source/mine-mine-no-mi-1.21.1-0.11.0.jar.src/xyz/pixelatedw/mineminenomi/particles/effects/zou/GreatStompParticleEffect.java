package xyz.pixelatedw.mineminenomi.particles.effects.zou;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class GreatStompParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		double var7 = 0D;
		LOOP_1:
		do{
			if ((var7) < (10D)){
				var7 = (var7) + (0.3141592653589793D);
				double var8 = 0D;
				do{
					if ((var8) <= (12.566370614359172D)){
						double var9 = (var7) * (Math.cos(var8));
						double var10 = WyHelper.randomDouble();
						double var11 = (var7) * (Math.sin(var8));
						BlockState var12 = var2.getBlockState(new BlockPos((int)(var3),(int)(var4),(int)(var5)).below());
						if (var12.isAir()){
							var12 = Blocks.DIRT.defaultBlockState();
						}
						var2.addParticle((ParticleOptions)(new BlockParticleOption(ParticleTypes.BLOCK,var12)),true,((var3) + ((double)(WyHelper.randomWithRange(-3,3)))) + (var9),(var4) + (var10),((var5) + ((double)(WyHelper.randomWithRange(-3,3)))) + (var11),0D,0D,0D);
						var8 = (var8) + (0.19634954084936207D);
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