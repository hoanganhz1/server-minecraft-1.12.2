package xyz.pixelatedw.mineminenomi.models.morphs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.HumanoidMorphModel;

public class GearFourthModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","gear_fourth"),"main");
	private boolean gomuAnimations;

	public GearFourthModel(ModelPart var1) {
		super(var1);
		this.gomuAnimations = true;
	}
	public static LayerDefinition createBodyLayer() {
		return LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE,0F),64,64);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		float var6 = 1.5F;
		var1.pushPose();
		var1.scale(var6,var6,var6);
		var1.translate(0D,-0.8D,0D);
		this.head.render(var1,var2,var3,var4,var5);
		if (this.gomuAnimations){
			this.hat.copyFrom(this.head);
			this.hat.render(var1,var2,var3,var4,var5);
		}
		var1.pushPose();
		var1.scale(2F,1.7F,3F);
		this.body.render(var1,var2,var3,var4,var5);
		var1.popPose();
		if (this.gomuAnimations){
			float var7 = 0.2F;
			var1.pushPose();
			var1.scale((1.75F) * ((5F) * (var7)),(1.75F) * ((5F) * (var7)),(1.75F) * ((5F) * (var7)));
		}else{
			var1.pushPose();
			var1.scale(1.75F,1.75F,1.75F);
			var1.translate(0.25D,-0.07D,0D);
		}
		this.rightArm.render(var1,var2,var3,var4,var5);
		var1.popPose();
		var1.pushPose();
		var1.scale(1.75F,1.75F,1.75F);
		this.leftArm.render(var1,var2,var3,var4,var5);
		var1.popPose();
		this.rightLeg.render(var1,var2,var3,var4,var5);
		this.leftLeg.render(var1,var2,var3,var4,var5);
		var1.popPose();
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		int var7 = (((Math.sqrt(((var1.getDeltaMovement().x) * (var1.getDeltaMovement().x)) + ((var1.getDeltaMovement().z) * (var1.getDeltaMovement().z)))) * ((double)(var1.zza))) > (0.05000000074505806D)) ? (1) : (0);
		if (this.gomuAnimations){
			if ((var7) != (0)){
				this.rightArm.zRot = (float)(Math.toRadians(90D));
				this.leftArm.zRot = (float)(Math.toRadians(-90D));
			}
			this.setupHeadRotation(var1,var6,var5);
			float var8 = 1F;
			if ((var7) == (0)){
				this.rightArm.xRot = ((((Mth.cos(((var2) * (0.6662F)) + (3.1415927F))) * (0.8F)) * (var3)) * (0.5F)) / (var8);
				this.leftArm.xRot = ((((Mth.cos((var2) * (0.6662F))) * (0.8F)) * (var3)) * (0.5F)) / (var8);
			}
			float var9 = 0.4F;
			if (var1.isSprinting()){
				var9 = 0.7F;
			}
			ModelPart var10 = this.rightLeg;
			var10.y = (var10.y) + ((-2F) + ((Mth.cos((var4) * (var9))) * (2F)));
			ModelPart var11 = this.leftLeg;
			var11.y = (var11.y) + ((-2F) + ((Mth.cos((var4) * (var9))) * (2F)));
			if (!(var1.getMainHandItem().isEmpty())){
				ModelPart var12 = this.rightArm;
				var12.xRot = (var12.xRot) + (-0.15F);
			}
			this.setupAttackAnimation(var1,var4);
			if ((var7) == (0)){
				this.rightArm.xRot = (float)(Math.toRadians(-90D));
				this.leftArm.xRot = (float)(Math.toRadians(-90D));
				this.leftArm.zRot = (float)(Math.toRadians(10D));
				this.leftArm.yRot = (float)(Math.toRadians(-5D));
				ModelPart var12_1 = this.leftArm;
				var12_1.z = (var12_1.z) + (3F);
			}
			ModelPart var12_2 = this.rightLeg;
			var12_2.y = (var12_2.y) + (0.5F);
			ModelPart var13 = this.leftLeg;
			var13.y = (var13.y) + (0.5F);
			ModelPart var14 = this.rightLeg;
			var14.x = (var14.x) - (0.5F);
			ModelPart var15 = this.leftLeg;
			var15.x = (var15.x) + (0.5F);
		}else{
			if (((var7) != (0)) && (var1.isSprinting())){
				this.rightArm.zRot = (float)(Math.toRadians(90D));
				this.leftArm.zRot = (float)(Math.toRadians(-90D));
			}else{
				return;
			}
		}
	}
	public boolean renderFirstPersonArm(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5, HumanoidArm var6, boolean var7) {
		if (!(var7)){
			if ((var6) == (HumanoidArm.RIGHT)){
				var1.translate(0.2D,0.3D,0D);
				this.rightArm.render(var1,var2,var3,OverlayTexture.NO_OVERLAY,WyHelper.packColor(1F,0.7F,0F,1F));
			}else{
				var1.translate(-0.2D,0.3D,0D);
				this.leftArm.render(var1,var2,var3,OverlayTexture.NO_OVERLAY,WyHelper.packColor(1F,0.7F,0F,1F));
			}
		}else{
			if ((var6) == (HumanoidArm.RIGHT)){
				var1.translate(0D,-1.2D,0.3D);
				var1.scale(1.5F,1.5F,1.5F);
				var1.mulPose(Axis.YP.rotationDegrees(-60F));
				this.rightLeg.render(var1,var2,var3,var4,var5);
			}else{
				var1.translate(0D,-1.2D,0.3D);
				var1.scale(1.5F,1.5F,1.5F);
				var1.mulPose(Axis.YP.rotationDegrees(60F));
				this.leftLeg.render(var1,var2,var3,var4,var5);
			}
		}
		return true;
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		super.translateToHand(var1,var2);
		var2.translate(((var1) == (HumanoidArm.RIGHT)) ? (-0.6D) : (0.6D),-0.5D,-0.2D);
	}
}