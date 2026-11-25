package xyz.pixelatedw.mineminenomi.particles.effects.fishkarate;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.math.EasingDirection;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunction;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class MizuTaihoParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		Vec3 var7 = var1.getLookAngle().scale(2D);
		Vec3 var8 = var1.getLookAngle().scale(3D);
		Vec3 var9 = var1.getLookAngle().scale(4D);
		SimpleParticleData var10 = new SimpleParticleData(((ParticleType)(ModParticleTypes.DOUBLE_CIRCLE.get())));
		var10.setFunction(EasingFunction.ELASTIC_IN_OUT);
		var10.setEaseDirection(EasingDirection.NEGATIVE);
		var10.setLookVec(var1.getXRot(),var1.getYRot());
		var10.setColor(0.2F,0.7F,0.7F,0.75F);
		var10.setLife(30);
		var10.setSize(30F);
		var2.addParticle((ParticleOptions)(var10),true,(var9.x) + (var3),(var9.y) + (var4),(var9.z) + (var5),0D,0D,0D);
		var10.setLife(20);
		var10.setSize(20F);
		var2.addParticle((ParticleOptions)(var10),true,(var8.x) + (var3),(var8.y) + (var4),(var8.z) + (var5),0D,0D,0D);
		var10.setLife(10);
		var10.setSize(10F);
		var2.addParticle((ParticleOptions)(var10),true,(var7.x) + (var3),(var7.y) + (var4),(var7.z) + (var5),0D,0D,0D);
	}
}