package xyz.pixelatedw.mineminenomi.api.morph;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.entity.HumanoidArm;

public interface IFirstPersonHandReplacer {
	@Deprecated()
	public default boolean renderFirstPersonArm(PoseStack var1, VertexConsumer var2, int var3, int var4, float var5, float var6, float var7, float var8, HumanoidArm var9, boolean var10) {
		return this.renderFirstPersonArm(var1,var2,var3,var4,((((((int)((var5) * (255F))) & (255)) << (24)) | ((((int)((var6) * (255F))) & (255)) << (16))) | ((((int)((var7) * (255F))) & (255)) << (8))) | (((int)((var8) * (255F))) & (255)),var9,var10);
	}
	public abstract boolean renderFirstPersonArm(PoseStack var0, VertexConsumer var1, int var2, int var3, int var4, HumanoidArm var5, boolean var6);
}