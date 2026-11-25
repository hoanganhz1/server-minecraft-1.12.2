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

public class GroundParticlesEffect extends ParticleEffect<GroundParticlesEffect$Details> {
	public GroundParticlesEffect() {
		super((ParticleEffect$IFactory) ((ParticleEffect$IFactory<GroundParticlesEffect$Details>) (GroundParticlesEffect$Details::new)));
	}
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, GroundParticlesEffect$Details var6) {
		int var7 = 0;
		LOOP_1:
		do{
			if ((var7) < (var6.amount)){
				double var8 = ((double)(WyHelper.randomWithRange(-(var6.offset),var6.offset))) + (WyHelper.randomDouble());
				double var9 = ((double)(WyHelper.randomWithRange(-(var6.offset),var6.offset))) + (WyHelper.randomDouble());
				int var10 = 0;
				do{
					if ((var10) < (2)){
						BlockState var11 = var2.getBlockState(new BlockPos((int)((var3) + (var8)),(int)((var4) - ((double)(var10))),(int)((var5) + (var9))).below());
						var2.addParticle((ParticleOptions)(new BlockParticleOption(ParticleTypes.BLOCK,var11)),(var3) + (var8),var4,(var5) + (var9),0D,0D,0D);
						var10++;
						continue;
					}else{
						break;
					}
				} while (true);
				var7++;
				continue LOOP_1;
			}else{
				break;
			}
		} while (true);
	}
}