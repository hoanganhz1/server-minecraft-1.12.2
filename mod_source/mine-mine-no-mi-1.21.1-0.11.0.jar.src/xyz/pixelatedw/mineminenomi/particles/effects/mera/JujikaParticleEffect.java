package xyz.pixelatedw.mineminenomi.particles.effects.mera;

import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class JujikaParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		Direction var7 = Direction.fromYRot((double)(var1.getYRot()));
		RandomSource var8 = var1.level().random;
		float var9 = 0F;
		do{
			if ((var9) <= (5F)){
				int var10 = 0;
				int var11 = 0;
				var11 = (int)(((float)(var11)) + ((var9) * ((float)(var7.getStepX()))));
				var10 = (int)(((float)(var10)) + ((var9) * ((float)(var7.getStepZ()))));
				SimpleParticleData var12 = null;
				if (((var8.nextInt(10)) % (2)) == (0)){
					var12 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MERA.get())));
				}else{
					var12 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MERA2.get())));
				}
				var12.setLife(WyHelper.randomWithRange(1,3));
				var12.setSize((float)(WyHelper.randomWithRange(0,2)));
				var12.setColor(1F,1F,1F,1F);
				var12.setMotion(var1.getDeltaMovement().x,var1.getDeltaMovement().y,var1.getDeltaMovement().z);
				var1.level().addParticle((ParticleOptions)(var12),true,(var3) - ((double)(var10)),var4,(var5) - ((double)(var11)),0D,0D,0D);
				var1.level().addParticle((ParticleOptions)(var12),true,(var3) + ((double)(var10)),var4,(var5) + ((double)(var11)),0D,0D,0D);
				var1.level().addParticle((ParticleOptions)(var12),true,var3,(var4) + ((double)(var9)),var5,0D,0D,0D);
				var1.level().addParticle((ParticleOptions)(var12),true,var3,(var4) - ((double)(var9)),var5,0D,0D,0D);
				var9 = (var9) + (0.5F);
				continue;
			}else{
				break;
			}
		} while (true);
	}
}