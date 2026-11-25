package xyz.pixelatedw.mineminenomi.renderers.morphs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class FlyPointRenderer<T extends LivingEntity, M extends EntityModel<T> & ArmedModel & HeadedModel> extends MorphRenderer<T, M> {
	public FlyPointRenderer(EntityRendererProvider.Context var1, MorphInfo var2, M var3) {
		super(var1,var2,var3);
		this.setCullingState(true);
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		super.render(var1,var2,var3,var4,var5,var6);
	}
	protected void renderModel(T var1, PoseStack var2, int var3, MultiBufferSource var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		var2.pushPose();
		if (!(var1.onGround())){
			var2.translate(0D,1.5010000467300415D,0D);
			var2.mulPose(Axis.XP.rotationDegrees(var10));
			double var11 = (var1.getX()) - (var1.xo);
			double var12 = (var1.getZ()) - (var1.zo);
			float var13 = (Mth.sqrt((float)(((var11) * (var11)) + ((var12) * (var12))))) * (20F);
			float var14 = (-(var9)) * (3F);
			var14 = Mth.clamp(var14,-(var13),var13);
			var2.mulPose(Axis.ZP.rotationDegrees(var14));
			var2.translate(0D,-1.5010000467300415D,0D);
		}
		super.renderModel(var1,var2,var3,var4,var5,var6,var7,var8,var9,var10);
		var2.popPose();
	}
}