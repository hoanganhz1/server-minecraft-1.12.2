package xyz.pixelatedw.mineminenomi.particles.effects.toriphoenix;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class PhoenixGoenParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		float var7 = 1F;
		if (var1 instanceof NuProjectileEntity){
			NuProjectileEntity var8 = ((NuProjectileEntity)(var1));
			var7 = ((float)((var8.getLife()) / (var8.getMaxLife()))) * (1.25F);
		}
		int var8_1 = 0;
		do{
			if (((float)(var8_1)) < ((25F) * (var7))){
				double var9 = (WyHelper.randomDouble()) * ((double)(var7));
				double var10 = (WyHelper.randomDouble()) * ((double)(var7));
				double var11 = (WyHelper.randomDouble()) * ((double)(var7));
				SimpleParticleData var12 = new SimpleParticleData(((ParticleType)(ModParticleTypes.BLUE_FLAME.get())));
				var12.setLife(8);
				var12.setSize((3F) * (var7));
				var2.addParticle((ParticleOptions)(var12),true,(var3) + (var9),(var4) + (var10),(var5) + (var11),0D,0D,0D);
				var8_1++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}