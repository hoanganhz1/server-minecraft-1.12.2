package xyz.pixelatedw.mineminenomi.particles.effects;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class ShockwaveParticleEffect extends ParticleEffect<ShockwaveParticleEffect$Details> {
	public ShockwaveParticleEffect() {
		super((ParticleEffect$IFactory) ((ParticleEffect$IFactory<ShockwaveParticleEffect$Details>) (ShockwaveParticleEffect$Details::new)));
	}
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ShockwaveParticleEffect$Details var6) {
		int var7 = var6.getSize();
		int var8 = -(var7);
		LOOP_2:
		do{
			if ((var8) < (var7)){
				int var9 = -(var7);
				LOOP_1:
				do{
					if ((var9) < (var7)){
						int var10 = 0;
						do{
							if ((var10) < (60)){
								double var11 = (WyHelper.randomDouble()) / (2D);
								BlockState var12 = var2.getBlockState(new BlockPos((int)((var3) + ((double)(var8))),(int)(var4),(int)((var5) + ((double)(var9)))).below());
								var2.addParticle((ParticleOptions)(new BlockParticleOption(ParticleTypes.BLOCK,var12)),true,((var3) + ((double)(WyHelper.randomWithRange(-1,1)))) + ((WyHelper.randomDouble()) / (20D)),((var4) + (var11)) + (0.5D),((var5) + ((double)(WyHelper.randomWithRange(-1,1)))) + ((WyHelper.randomDouble()) / (20D)),0D,0D,0D);
								var10++;
								continue;
							}else{
								break;
							}
						} while (true);
						var9++;
						continue LOOP_1;
					}else{
						break;
					}
				} while (true);
				var8++;
				continue LOOP_2;
			}else{
				break;
			}
		} while (true);
	}
}