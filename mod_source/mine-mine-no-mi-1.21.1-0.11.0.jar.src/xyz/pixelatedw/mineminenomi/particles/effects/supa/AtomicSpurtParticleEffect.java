package xyz.pixelatedw.mineminenomi.particles.effects.supa;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class AtomicSpurtParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (2)){
				double var8 = (WyHelper.randomDouble()) / (2D);
				double var9 = (WyHelper.randomDouble()) / (2D);
				BlockState var10 = var2.getBlockState(new BlockPos((int)(var3),(int)(var4),(int)(var5)).below());
				var2.addParticle((ParticleOptions)(new BlockParticleOption(ParticleTypes.BLOCK,var10)),(var3) + (var8),var4,(var5) + (var9),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}