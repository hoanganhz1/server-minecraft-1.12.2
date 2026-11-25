package xyz.pixelatedw.mineminenomi.particles.effects.gasu;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$IFactory;

public class KoroParticleEffect extends ParticleEffect<KoroParticleEffect$Details> {
	public KoroParticleEffect() {
		super((ParticleEffect$IFactory) ((ParticleEffect$IFactory<KoroParticleEffect$Details>) (KoroParticleEffect$Details::new)));
	}
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, KoroParticleEffect$Details var6) {
		int var7 = 0;
		do{
			if ((var7) < (25)){
				double var8 = (WyHelper.randomDouble()) / (1.3D);
				double var9 = (WyHelper.randomDouble()) / (1.3D);
				double var10 = (WyHelper.randomDouble()) / (1.3D);
				Vec3 var11 = new Vec3(var3,var4,var5);
				Vec3 var12 = var11.subtract(var1.position()).normalize();
				int var13 = ((MobEffect)(var6.effect.getEffect().value())).getColor();
				float var14 = ((float)(((var13) >> (16)) & (255))) / (255F);
				float var15 = ((float)(((var13) >> (8)) & (255))) / (255F);
				float var16 = ((float)(((var13) >> (0)) & (255))) / (255F);
				SimpleParticleData var17 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MOKU2.get())));
				var17.setLife(10);
				var17.setSize(1.3F);
				var17.setMotion((-(var12.x)) / (5D),(-(var12.y)) / (5D),(-(var12.z)) / (5D));
				var17.setRotation(0F,0F,1F);
				var17.setColor(var14,var15,var16);
				var2.addParticle((ParticleOptions)(var17),true,(var11.x) + (var8),((var11.y) + (1D)) + (var9),(var11.z) + (var10),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}