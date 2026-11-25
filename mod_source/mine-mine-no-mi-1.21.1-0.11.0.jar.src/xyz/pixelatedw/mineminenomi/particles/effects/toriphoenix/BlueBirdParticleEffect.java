package xyz.pixelatedw.mineminenomi.particles.effects.toriphoenix;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$IFactory;

public class BlueBirdParticleEffect extends ParticleEffect<BlueBirdParticleEffect$Details> {
	public BlueBirdParticleEffect() {
		super((ParticleEffect$IFactory) ((ParticleEffect$IFactory<BlueBirdParticleEffect$Details>) (BlueBirdParticleEffect$Details::new)));
	}
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, BlueBirdParticleEffect$Details var6) {
		float var7 = 0.049087387F;
		float var8 = ((float)(fromRangeToRange(0D,var6.getMaxChargeTime(),0.25D,1.25D,(var6.getMaxChargeTime()) - (var6.getCurrentChargeTime())))) * (1.6F);
		float var9 = ((float)(fromRangeToRange(0D,var6.getMaxChargeTime(),0.15D,0.3D,(var6.getMaxChargeTime()) - (var6.getCurrentChargeTime())))) * (1.8F);
		float var10 = (float)(((double)(var7)) * (fromRangeToRange(0D,var6.getMaxChargeTime(),-128D,128D,var6.getCurrentChargeTime())));
		float var11 = (float)((((double)(var7)) * (fromRangeToRange(0D,var6.getMaxChargeTime(),-128D,128D,var6.getCurrentChargeTime()))) + (2.0943951023931953D));
		float var12 = (float)((((double)(var7)) * (fromRangeToRange(0D,var6.getMaxChargeTime(),-128D,128D,var6.getCurrentChargeTime()))) - (2.0943951023931953D));
		Vec3 var13 = getPerpendicularHorizontalLine(Vec3.ZERO,var1.getLookAngle(),var8).multiply(2D,2D,2D);
		Vec3 var14 = getPerpendicularVerticalLine(Vec3.ZERO,var1.getLookAngle(),var13.multiply(-3.141592653589793D,-3.141592653589793D,-3.141592653589793D),var9).multiply(2D,2D,2D);
		Vec3 var15 = getParticlePositionInSpiral(var10,var13,var14);
		Vec3 var16 = getParticlePositionInSpiral(var11,var13,var14);
		Vec3 var17 = getParticlePositionInSpiral(var12,var13,var14);
		SimpleParticleData var18 = new SimpleParticleData(((ParticleType)(ModParticleTypes.BLUE_FLAME.get())));
		var18.setLife(15);
		var18.setSize(4F);
		var18.setMotion((var1.getLookAngle().x) / (10D),(var1.getLookAngle().y) / (10D),(var1.getLookAngle().z) / (10D));
		double var19 = (var1.getX()) + (var15.x);
		double var20 = (var1.getEyeY()) + (var15.y);
		double var21 = (var1.getZ()) + (var15.z);
		double var22 = (var1.getX()) + (var16.x);
		double var23 = (var1.getEyeY()) + (var16.y);
		double var24 = (var1.getZ()) + (var16.z);
		double var25 = (var1.getX()) + (var17.x);
		double var26 = (var1.getEyeY()) + (var17.y);
		double var27 = (var1.getZ()) + (var17.z);
		var2.addParticle((ParticleOptions)(var18),true,var19,var20,var21,0D,0D,0D);
		var2.addParticle((ParticleOptions)(var18),true,var22,var23,var24,0D,0D,0D);
		var2.addParticle((ParticleOptions)(var18),true,var25,var26,var27,0D,0D,0D);
	}
	private static Vec3 getPerpendicularHorizontalLine(Vec3 var0, Vec3 var1, float var2) {
		Vec3 var3 = new Vec3(1D,0D,1D);
		Vec3 var4 = var0.multiply(var3);
		Vec3 var5 = var1.multiply(var3).subtract(var4);
		var5.add(((var5.x()) == (0D)) ? (0.001D) : (0D),0D,((var5.z()) == (0D)) ? (0.001D) : (0D));
		return new Vec3(var5.z,0D,-(var5.x)).normalize().multiply((double)(var2),(double)(var2),(double)(var2));
	}
	private static Vec3 getPerpendicularVerticalLine(Vec3 var0, Vec3 var1, Vec3 var2, float var3) {
		Vec3 var4 = var1.subtract(var0);
		return var4.cross(var2.subtract(var0)).normalize().multiply((double)(var3),(double)(var3),(double)(var3));
	}
	private static double fromRangeToRange(double var0, double var1, double var2, double var3, double var4) {
		return ((((var4) - (var0)) * ((var3) - (var2))) / ((var1) - (var0))) + (var2);
	}
	private static Vec3 getParticlePositionInSpiral(float var0, Vec3 var1, Vec3 var2) {
		Vec3 var3 = var1.multiply(Math.cos((double)(var0)),Math.cos((double)(var0)),Math.cos((double)(var0)));
		Vec3 var4 = var2.multiply(Math.sin((double)(var0)),Math.sin((double)(var0)),Math.sin((double)(var0)));
		return var4.add(var4.subtract(var3).multiply(0.5D,0.5D,0.5D));
	}
}