package xyz.pixelatedw.mineminenomi.particles.effects;

import java.util.Iterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class BreakingBlocksParticleEffect extends ParticleEffect<BreakingBlocksParticleEffect$Details> {
	public BreakingBlocksParticleEffect() {
		super((ParticleEffect$IFactory) ((ParticleEffect$IFactory<BreakingBlocksParticleEffect$Details>) (BreakingBlocksParticleEffect$Details::new)));
	}
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, BreakingBlocksParticleEffect$Details var6) {
		double var10 = 0.0;
		double var11 = 0.0;
		double var12 = 0.0;
		int var9 = 0;
		if (var6.isVecList){
			Iterator var7 = var6.vecPositions.iterator();
			LOOP_1:
			do{
				if (var7.hasNext()){
					Vec3 var8 = ((Vec3)(var7.next()));
					var3 = var8.x();
					var4 = var8.y();
					var5 = var8.z();
					var9 = 0;
					do{
						if ((var9) < (var6.amount)){
							var10 = var2.random.nextDouble();
							var11 = 1D;
							var12 = var2.random.nextDouble();
							BlockState var13 = var2.getBlockState(new BlockPos((int)((var3) + (var10)),(int)(var4),(int)((var5) + (var12))).below());
							BlockParticleOption var14 = new BlockParticleOption(ParticleTypes.BLOCK,var13);
							var2.addParticle((ParticleOptions)(var14),true,(var3) + (var10),(var4) + (var11),(var5) + (var12),0D,0D,0D);
							var9++;
							continue;
						}else{
							continue LOOP_1;
						}
					} while (true);
				}else{
					break;
				}
			} while (true);
			return;
		}else{
			Iterator var7_1 = var6.positions.iterator();
			LOOP_2:
			do{
				if (var7_1.hasNext()){
					BlockPos var8_1 = ((BlockPos)(var7_1.next()));
					var3 = (double)(var8_1.getX());
					var4 = (double)(var8_1.getY());
					var5 = (double)(var8_1.getZ());
					var9 = 0;
					do{
						if ((var9) < (var6.amount)){
							var10 = var2.random.nextDouble();
							var11 = 1D;
							var12 = var2.random.nextDouble();
							BlockState var13_1 = var2.getBlockState(new BlockPos((int)((var3) + (var10)),(int)(var4),(int)((var5) + (var12))).below());
							BlockParticleOption var14_1 = new BlockParticleOption(ParticleTypes.BLOCK,var13_1);
							var2.addParticle((ParticleOptions)(var14_1),true,(var3) + (var10),(var4) + (var11),(var5) + (var12),0D,0D,0D);
							var9++;
							continue;
						}else{
							continue LOOP_2;
						}
					} while (true);
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
}