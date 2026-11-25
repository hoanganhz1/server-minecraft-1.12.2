package xyz.pixelatedw.mineminenomi.animations;

import javax.annotation.Nullable;
import net.minecraft.client.model.geom.ModelPart;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;

public class TestAnimation$AnimationFrame {
	// Fields
	private String name;
	 int duration;
	final int maxDuration;
	private boolean markedDefaults;
	private float defaultX;
	private float defaultY;
	private float defaultZ;
	private float x;
	private float y;
	private float z;
	private float defaultXRot;
	private float defaultYRot;
	private float defaultZRot;
	private float xRot;
	private float yRot;
	private float zRot;

	public TestAnimation$AnimationFrame(final TestAnimation var1, String var2, int var3) {
		this.markedDefaults = false;
		this.name = var2;
		this.duration = var3;
		this.maxDuration = var3;
	}
	public TestAnimation$AnimationFrame setPosition(float var1, float var2, float var3) {
		this.x = var1;
		this.y = var2;
		this.z = var3;
		return this;
	}
	public TestAnimation$AnimationFrame setRotation(float var1, float var2, float var3) {
		this.xRot = (float)(Math.toRadians((double)(var1)));
		this.yRot = (float)(Math.toRadians((double)(var2)));
		this.zRot = (float)(Math.toRadians((double)(var3)));
		return this;
	}
	private void setDefaults(ModelPart var1, @Nullable() TestAnimation$AnimationFrame var2) {
		if (this.markedDefaults){
			return;
		}else{
			float var3 = ((var2) != (null)) ? (var2.defaultXRot) : (0F);
			float var4 = ((var2) != (null)) ? (var2.defaultYRot) : (0F);
			float var5 = ((var2) != (null)) ? (var2.defaultZRot) : (0F);
			this.defaultX = var1.x;
			this.defaultY = var1.y;
			this.defaultZ = var1.z;
			this.defaultXRot = (var1.xRot) - (var3);
			this.defaultYRot = (var1.yRot) - (var4);
			this.defaultZRot = var1.zRot;
			this.markedDefaults = true;
			return;
		}
	}
	public boolean play(ModelPart var1, float var2, @Nullable() TestAnimation$AnimationFrame var3) {
		float var4 = EasingFunctionHelper.easeOutCubic(Float.valueOf(var2));
		var1.x = (this.defaultX) + ((this.x) * (var4));
		var1.y = (this.defaultY) + ((this.y) * (var4));
		var1.z = (this.defaultZ) + ((this.z) * (var4));
		float var5 = ((var3) != (null)) ? (var3.defaultXRot) : (0F);
		float var6 = ((var3) != (null)) ? (var3.defaultYRot) : (0F);
		float var7 = ((var3) != (null)) ? (var3.defaultZRot) : (0F);
		var1.xRot = ((this.defaultXRot) + (this.xRot)) * (var4);
		var1.yRot = ((this.defaultYRot) + (this.yRot)) * (var4);
		var1.zRot = ((this.defaultZRot) + ((this.zRot) - (var7))) * (var4);
		int var8 = (this.duration) - (1);
		this.duration = var8;
		return (var8) <= (0);
	}
	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f616e696d6174696f6e732f54657374416e696d6174696f6e24416e696d6174696f6e4672616d65$0(ModelPart arg0, TestAnimation$AnimationFrame arg1) { ((TestAnimation$AnimationFrame)this).setDefaults(arg0, arg1); }

}