package xyz.pixelatedw.mineminenomi.particles.effects.haki;

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

public class InternalDestructionBurstParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		Vec3 var7 = var1.getLookAngle().scale(-1.2D);
		Vec3 var8 = var1.getLookAngle().scale(-1.6D);
		SimpleParticleData var9 = new SimpleParticleData(((ParticleType)(ModParticleTypes.SIMPLE_CIRCLE.get())));
		var9.setFunction(EasingFunction.ELASTIC_OUT);
		var9.setEaseDirection(EasingDirection.POSITIVE);
		var9.setLookVec(var1.getXRot(),var1.getYRot());
		var9.setColor(0.9F,0.7F,0.15F,0.25F);
		Vec3 var10 = var1.getLookAngle().normalize().reverse().multiply(0.08D,0.08D,0.08D);
		var9.setLife(10);
		var9.setSize(10F);
		var9.setMotion(var10.x,var10.y,var10.z);
		var2.addParticle((ParticleOptions)(var9),true,(var7.x) + (var3),(var7.y) + (var4),(var7.z) + (var5),0D,0D,0D);
		SimpleParticleData var11 = new SimpleParticleData(((ParticleType)(ModParticleTypes.DOUBLE_CIRCLE.get())));
		var11.setFunction(EasingFunction.ELASTIC_OUT);
		var11.setEaseDirection(EasingDirection.POSITIVE);
		var11.setLookVec(var1.getXRot(),var1.getYRot());
		var11.setColor(0.9F,0.7F,0.15F,0.25F);
		var11.setLife(15);
		var11.setSize(4F);
		var2.addParticle((ParticleOptions)(var11),true,(var8.x) + (var3),(var8.y) + (var4),(var8.z) + (var5),0D,0D,0D);
	}
}