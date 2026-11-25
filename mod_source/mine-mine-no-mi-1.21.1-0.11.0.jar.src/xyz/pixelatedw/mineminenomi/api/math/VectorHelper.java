package xyz.pixelatedw.mineminenomi.api.math;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class VectorHelper {
	public static Vec3 rotateAroundX(Vec3 var0, double var1) {
		double var2 = ((Math.cos(var1)) * (var0.y)) - ((Math.sin(var1)) * (var0.z));
		double var3 = ((Math.sin(var1)) * (var0.y)) + ((Math.cos(var1)) * (var0.z));
		var0 = new Vec3(var0.x,var2,var3);
		return var0;
	}
	public static Vec3 rotateAroundY(Vec3 var0, double var1) {
		double var2 = ((Math.cos(var1)) * (var0.x)) + ((Math.sin(var1)) * (var0.z));
		double var3 = ((-(Math.sin(var1))) * (var0.x)) + ((Math.cos(var1)) * (var0.z));
		var0 = new Vec3(var2,var0.y,var3);
		return var0;
	}
	public static Vec3 rotateAroundZ(Vec3 var0, double var1) {
		double var2 = ((Math.cos(var1)) * (var0.x)) - ((Math.sin(var1)) * (var0.y));
		double var3 = ((Math.sin(var1)) * (var0.x)) + ((Math.cos(var1)) * (var0.y));
		var0 = new Vec3(var2,var3,var0.z);
		return var0;
	}
	public static Vec3 calculateViewVectorFromBodyRot(float var0, float var1) {
		float var2 = (var0) * (0.017453292F);
		float var3 = (-(var1)) * (0.017453292F);
		float var4 = Mth.cos(var3);
		float var5 = Mth.sin(var3);
		float var6 = Mth.cos(var2);
		float var7 = Mth.sin(var2);
		return new Vec3((double)((var5) * (var6)),(double)(-(var7)),(double)((var4) * (var6)));
	}
	public static Vec3 getRelativeOffset(Vec3 var0, float var1, Vec3 var2) {
		return getRelativeOffset(var0,var1,0F,var2);
	}
	public static Vec3 getRelativeOffset(Vec3 var0, float var1, float var2, Vec3 var3) {
		double var4 = Math.toRadians((double)(var1));
		double var5 = Math.toRadians((double)(var2));
		double var6 = var3.x;
		double var7 = ((var3.y) * (Math.cos(var5))) - ((var3.z) * (Math.sin(var5)));
		double var8 = ((var3.y) * (Math.sin(var5))) + ((var3.z) * (Math.cos(var5)));
		double var9 = ((var6) * (Math.cos(var4))) - ((var8) * (Math.sin(var4)));
		return var0.add(var9,var7,((var6) * (Math.sin(var4))) + ((var8) * (Math.cos(var4))));
	}
}