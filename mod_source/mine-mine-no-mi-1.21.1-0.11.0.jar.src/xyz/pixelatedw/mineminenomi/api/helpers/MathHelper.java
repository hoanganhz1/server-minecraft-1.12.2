package xyz.pixelatedw.mineminenomi.api.helpers;

import net.minecraft.world.phys.Vec3;

public class MathHelper {
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