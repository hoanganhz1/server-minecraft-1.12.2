package xyz.pixelatedw.mineminenomi.renderers.morphs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class GearFourthPointRenderer<T extends LivingEntity, M extends EntityModel<T> & ArmedModel & HeadedModel> extends MorphRenderer<T, M> {
	public GearFourthPointRenderer(EntityRendererProvider.Context var1, MorphInfo var2, M var3) {
		super(var1,var2,var3);
		this.setCullingState(true);
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		super.render(var1,var2,var3,var4,var5,var6);
	}
	protected void renderModel(T var1, PoseStack var2, int var3, MultiBufferSource var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		var2.pushPose();
		if (((((Math.sqrt(((var1.getDeltaMovement().x) * (var1.getDeltaMovement().x)) + ((var1.getDeltaMovement().z) * (var1.getDeltaMovement().z)))) * ((double)(var1.zza))) > (0.05000000074505806D)) ? (1) : (0)) != (0)){
			var2.mulPose(Axis.XP.rotationDegrees((var1.getXRot()) + (90F)));
		}
		super.renderModel(var1,var2,var3,var4,var5,var6,var7,var8,var9,var10);
		var2.popPose();
	}
}