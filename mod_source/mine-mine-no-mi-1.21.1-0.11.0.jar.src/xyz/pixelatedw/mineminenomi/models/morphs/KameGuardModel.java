package xyz.pixelatedw.mineminenomi.models.morphs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.QuadrupedMorphModel;

public class KameGuardModel<T extends LivingEntity> extends QuadrupedMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","kame_guard"),"main");

	public KameGuardModel(ModelPart var1) {
		super(var1);
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = QuadrupedModel.createBodyMesh(0,CubeDeformation.NONE);
		PartDefinition var1 = var0.getRoot();
		var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-2F,-5F,10F,3F,12F,new CubeDeformation(0F)).texOffs(0,29).addBox(-3F,-3F,-4F,8F,1F,10F,new CubeDeformation(0F)).texOffs(0,16).addBox(-3F,1F,-4.5F,8F,1F,11F,new CubeDeformation(0F)),PartPose.offset(-1F,22F,-1F));
		var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(33,0).addBox(-2F,-1F,-4F,4F,3F,4F,new CubeDeformation(0F)),PartPose.offset(0F,22F,-5F));
		var1.addOrReplaceChild("left_hind_leg",CubeListBuilder.create().texOffs(28,16).addBox(-2F,0F,0F,3F,0F,6F,new CubeDeformation(0F)),PartPose.offset(3F,23F,5.25F));
		var1.addOrReplaceChild("right_hind_leg",CubeListBuilder.create().texOffs(27,29).addBox(-3F,1F,6F,3F,0F,6F,new CubeDeformation(0F)),PartPose.offset(-1F,22F,-0.75F));
		var1.addOrReplaceChild("right_front_leg",CubeListBuilder.create().texOffs(28,23).addBox(-9F,1F,-4F,6F,0F,3F,new CubeDeformation(0F)),PartPose.offset(-1F,22F,-1F));
		var1.addOrReplaceChild("left_front_leg",CubeListBuilder.create().texOffs(33,8).addBox(5F,0.85F,-4F,6F,0F,3F,new CubeDeformation(0F)),PartPose.offset(-1F,22F,-1F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(LivingEntity var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		if (var1.isCrouching()){
			this.head.visible = false;
			this.rightFrontLeg.visible = false;
			this.leftFrontLeg.visible = false;
			this.rightHindLeg.visible = false;
			this.leftHindLeg.visible = false;
		}else{
			this.head.visible = true;
			this.rightFrontLeg.visible = true;
			this.leftFrontLeg.visible = true;
			this.rightHindLeg.visible = true;
			this.leftHindLeg.visible = true;
		}
		this.head.xRot = (var6) * (0.017453292F);
		this.head.yRot = (var5) * (0.017453292F);
		this.rightFrontLeg.xRot = (Mth.cos((var2) * (0.4F))) * (0.1F);
		this.rightFrontLeg.zRot = (Mth.sin((var2) * (0.4F))) * (0.1F);
		this.leftFrontLeg.xRot = (-(Mth.cos((var2) * (0.4F)))) * (0.1F);
		this.leftFrontLeg.zRot = (-(Mth.sin((var2) * (0.4F)))) * (0.1F);
		this.rightHindLeg.yRot = (Mth.cos((var2) * (0.4F))) * (0.1F);
		this.rightHindLeg.zRot = (Mth.sin((var2) * (0.4F))) * (0.1F);
		this.leftHindLeg.yRot = (Mth.cos((var2) * (0.4F))) * (0.2F);
		this.leftHindLeg.zRot = (-(Mth.sin((var2) * (0.4F)))) * (0.1F);
		this.attackTime = (float)(var1.swingTime);
		if ((this.attackTime) > (0F)){
			this.head.yRot = (Mth.sin((float)(((double)(Mth.sqrt(this.attackTime))) * (3.141592653589793D)))) * (-0.2F);
			this.head.zRot = (Mth.cos((float)(((double)(Mth.sqrt(this.attackTime))) * (3.141592653589793D)))) * (-0.2F);
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		super.renderToBuffer(var1,var2,var3,var4,var5);
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		if (!(this.head.visible)){
			var2.scale(-10F,-10F,-10F);
		}
		this.head.translateAndRotate(var2);
		var2.mulPose(Axis.ZP.rotationDegrees(90F));
		var2.mulPose(Axis.XP.rotationDegrees(260F));
		var2.scale(0.5F,0.5F,0.5F);
		var2.translate(0.2D,0D,0.4D);
	}
	public ModelPart getHead() {
		return this.head;
	}
}