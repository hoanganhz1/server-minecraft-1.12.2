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

public class MizuOsuParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		Vec3 var7 = var1.getLookAngle();
		SimpleParticleData var8 = new SimpleParticleData(((ParticleType)(ModParticleTypes.SIMPLE_CIRCLE.get())));
		var8.setFunction(EasingFunction.SINE_IN_OUT);
		var8.setEaseDirection(EasingDirection.POSITIVE);
		var8.setEaseStrength(10F);
		var8.setLookVec(var1.getXRot(),var1.getYRot());
		var8.setColor(0.2F,0.7F,0.7F,0.75F);
		var8.setLife(12);
		var8.setSize(1F);
		var8.setMotion((var7.x) / (9D),0D,(var7.z) / (9D));
		var2.addParticle((ParticleOptions)(var8),true,(var7.x) + (var3),(var7.y) + (var4),(var7.z) + (var5),0D,0D,0D);
		var8.setLife(10);
		var8.setSize(2F);
		var8.setMotion((var7.x) / (7D),0D,(var7.z) / (7D));
		var2.addParticle((ParticleOptions)(var8),true,(var7.x) + (var3),(var7.y) + (var4),(var7.z) + (var5),0D,0D,0D);
		var8.setLife(8);
		var8.setSize(3F);
		var8.setMotion((var7.x) / (5D),0D,(var7.z) / (5D));
		var2.addParticle((ParticleOptions)(var8),true,(var7.x) + (var3),(var7.y) + (var4),(var7.z) + (var5),0D,0D,0D);
	}
}