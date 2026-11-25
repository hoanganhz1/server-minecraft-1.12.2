package xyz.pixelatedw.mineminenomi.models.morphs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
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

public class LeopardWalkModel<T extends LivingEntity> extends QuadrupedMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","leopard_walk"),"main");
	private final ModelPart lowerLeftFrontLeg;
	private final ModelPart leftFrontPaw;
	private final ModelPart lowerRightFrontLeg;
	private final ModelPart rightFrontPaw;
	private final ModelPart lowerLeftHindLeg;
	private final ModelPart leftHindPaw;
	private final ModelPart lowrRightHindLeg;
	private final ModelPart rightHindPaw;
	private final ModelPart tail;
	private final ModelPart tail2;
	private final ModelPart tail3;
	private final ModelPart leftEye;
	private final ModelPart rightEye;
	private final ModelPart upperMouth;
	private final ModelPart lowerMouth;
	private final ModelPart rightEar;
	private final ModelPart leftEar;

	public LeopardWalkModel(ModelPart var1) {
		super(var1);
		this.lowerLeftFrontLeg = this.leftFrontLeg.getChild("lowerLeftFrontLeg");
		this.leftFrontPaw = this.lowerLeftFrontLeg.getChild("leftFrontPaw");
		this.lowerRightFrontLeg = this.rightFrontLeg.getChild("lowerRightFrontLeg");
		this.rightFrontPaw = this.lowerRightFrontLeg.getChild("rightFrontPaw");
		this.lowerLeftHindLeg = this.leftHindLeg.getChild("lowerLeftHindLeg");
		this.leftHindPaw = this.lowerLeftHindLeg.getChild("leftHindPaw");
		this.lowrRightHindLeg = this.rightHindLeg.getChild("lowrRightHindLeg");
		this.rightHindPaw = this.lowrRightHindLeg.getChild("rightHindPaw");
		this.tail = this.body.getChild("tail");
		this.tail2 = this.tail.getChild("tail2");
		this.tail3 = this.tail2.getChild("tail3");
		this.leftEye = this.head.getChild("leftEye");
		this.rightEye = this.head.getChild("rightEye");
		this.upperMouth = this.head.getChild("upperMouth");
		this.lowerMouth = this.head.getChild("lowerMouth");
		this.rightEar = this.head.getChild("rightEar");
		this.leftEar = this.head.getChild("leftEar");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = QuadrupedMorphModel.createBodyMesh(0,CubeDeformation.NONE);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("left_front_leg",CubeListBuilder.create().texOffs(15,55).addBox(-1.4564F,-1.5186F,-1.7829F,3F,5F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(2.8939F,15.2061F,-2.9046F,0.0873F,0.0015F,-0.0174F)).addOrReplaceChild("lowerLeftFrontLeg",CubeListBuilder.create(),PartPose.offsetAndRotation(0.0858F,3.0291F,-0.1806F,-0.096F,0F,-0.0175F));
		var2.addOrReplaceChild("lowerfrontleftleg1_r1",CubeListBuilder.create().texOffs(18,46).addBox(-1F,-3.3125F,-2.25F,2F,6F,2F,new CubeDeformation(0.2F)),PartPose.offsetAndRotation(0.0102F,2.5856F,0.8352F,-0.2269F,0F,-0.0175F));
		var2.addOrReplaceChild("leftFrontPaw",CubeListBuilder.create().texOffs(17,41).addBox(-1F,-0.5F,-2.5F,2F,1F,3F,new CubeDeformation(0.3F)),PartPose.offset(0.0102F,4.9606F,-0.6023F));
		PartDefinition var3 = var1.addOrReplaceChild("right_front_leg",CubeListBuilder.create().texOffs(0,55).addBox(-1.5436F,-1.5186F,-1.7829F,3F,5F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2.8939F,15.2061F,-2.9046F,0.0873F,-0.0015F,0.0174F)).addOrReplaceChild("lowerRightFrontLeg",CubeListBuilder.create(),PartPose.offsetAndRotation(-0.0858F,3.0291F,-0.1806F,-0.096F,0F,0.0175F));
		var3.addOrReplaceChild("lowerfrontrightleg1_r1",CubeListBuilder.create().texOffs(3,46).addBox(-1F,-3.3125F,-2.25F,2F,6F,2F,new CubeDeformation(0.2F)),PartPose.offsetAndRotation(-0.0102F,2.5856F,0.8352F,-0.2269F,0F,0.0175F));
		var3.addOrReplaceChild("rightFrontPaw",CubeListBuilder.create().texOffs(2,41).addBox(-1F,-0.5F,-2.5F,2F,1F,3F,new CubeDeformation(0.3F)),PartPose.offset(-0.0102F,4.9606F,-0.6023F));
		var1.addOrReplaceChild("left_hind_leg",CubeListBuilder.create().texOffs(43,54).addBox(-1.3687F,-0.4976F,-2.0755F,3F,7F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(2.2437F,13.1851F,6.0755F,-0.1046F,-0.0055F,-0.0521F)).addOrReplaceChild("lowerLeftHindLeg",CubeListBuilder.create().texOffs(45,46).addBox(-1.0011F,-0.517F,-0.8984F,2F,5F,2F,new CubeDeformation(0.2F)),PartPose.offsetAndRotation(0.1654F,5.7725F,0.179F,-0.2264F,0.0157F,0.0506F)).addOrReplaceChild("leftHindPaw",CubeListBuilder.create().texOffs(44,41).addBox(-1.0011F,-0.1875F,-1.8806F,2F,1F,3F,new CubeDeformation(0.3F)),PartPose.offsetAndRotation(0.0184F,4.2382F,-0.2545F,0.3316F,0F,0F));
		var1.addOrReplaceChild("right_hind_leg",CubeListBuilder.create().texOffs(30,54).addBox(-1.6313F,-0.4976F,-2.0755F,3F,7F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2.2437F,13.1851F,6.0755F,-0.1046F,0.0055F,0.0521F)).addOrReplaceChild("lowrRightHindLeg",CubeListBuilder.create().texOffs(32,46).addBox(-0.9989F,-0.517F,-0.8984F,2F,5F,2F,new CubeDeformation(0.2F)),PartPose.offsetAndRotation(-0.1654F,5.7725F,0.179F,-0.2264F,-0.0157F,-0.0506F)).addOrReplaceChild("rightHindPaw",CubeListBuilder.create().texOffs(31,41).addBox(-0.9989F,-0.1875F,-1.8806F,2F,1F,3F,new CubeDeformation(0.3F)),PartPose.offsetAndRotation(-0.0184F,4.2382F,-0.2545F,0.3316F,0F,0F));
		var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(9,21).addBox(-3F,-3.4063F,-7F,6F,7F,10F,new CubeDeformation(0F)).texOffs(33,12).addBox(-2.5F,-3.0938F,-3F,5F,6F,10F,new CubeDeformation(0.2F)),PartPose.offset(-0.0625F,14.4688F,1.75F)).addOrReplaceChild("tail",CubeListBuilder.create().texOffs(3,0).addBox(-1F,-1F,0F,2F,2F,6F,new CubeDeformation(0F)),PartPose.offset(-0.125F,-1.5938F,6.5625F)).addOrReplaceChild("tail2",CubeListBuilder.create().texOffs(0,9).addBox(-1F,-1F,0F,2F,2F,7F,new CubeDeformation(-0.01F)),PartPose.offset(0F,0F,5.5F)).addOrReplaceChild("tail3",CubeListBuilder.create().texOffs(1,19).addBox(-1F,-1F,0F,2F,2F,6F,new CubeDeformation(0F)),PartPose.offset(0F,0F,6.5F));
		PartDefinition var4 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(37,0).addBox(-2.5F,-2.0627F,-5.2758F,5F,5F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.125F,13.3127F,-5.4117F,0.1309F,0F,0F));
		var4.addOrReplaceChild("leftEye",CubeListBuilder.create().texOffs(1,0).mirror().addBox(-1F,-0.5F,0F,2F,1F,0F,new CubeDeformation(0F)).mirror(false),PartPose.offset(1.4F,-0.3627F,-5.2883F));
		var4.addOrReplaceChild("rightEye",CubeListBuilder.create().texOffs(1,0).addBox(-1F,-0.5F,0F,2F,1F,0F,new CubeDeformation(0F)),PartPose.offset(-1.4F,-0.3627F,-5.2883F));
		var4.addOrReplaceChild("upperMouth",CubeListBuilder.create().texOffs(20,5).addBox(-1.5F,-1.8903F,-2.424F,3F,2F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,1.9146F,-5.2678F,0.0873F,0F,0F));
		var4.addOrReplaceChild("lowerMouth",CubeListBuilder.create().texOffs(20,12).addBox(-1.5F,-0.1364F,-2.4333F,3F,1F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,2.1589F,-5.237F,0.0873F,0F,0F));
		var4.addOrReplaceChild("rightEar",CubeListBuilder.create().texOffs(0,2).mirror().addBox(-1.947F,-0.9366F,-0.6477F,3F,2F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-1.7725F,-1.7151F,-1.7654F,0.2004F,0.5236F,-0.0775F));
		var4.addOrReplaceChild("leftEar",CubeListBuilder.create().texOffs(0,2).addBox(-1.053F,-0.9366F,-0.6477F,3F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(2.0225F,-1.7151F,-1.7654F,0.2004F,-0.5236F,0.0775F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		this.setupHeadRotation(var1,var6,var5);
		float var7 = 0.7F;
		float var8 = 0.7F;
		if (var1.isSprinting()){
			var7 = 0.9F;
			var8 = 0.9F;
		}
		this.rightFrontLeg.xRot = (0.1F) + (((Mth.cos((var2) * (var8))) * (var7)) * (var3));
		this.leftFrontLeg.xRot = (0.1F) + (((Mth.cos((var2) * (var8))) * (var7)) * (var3));
		this.rightHindLeg.xRot = (-0.1F) + (((Mth.cos(((var2) * (var8)) + (3.1415927F))) * (var7)) * (var3));
		this.leftHindLeg.xRot = (-0.1F) + (((Mth.cos(((var2) * (var8)) + (3.1415927F))) * (var7)) * (var3));
		ModelPart var9 = this.tail;
		var9.yRot = (float)(((double)(var9.yRot)) + ((Math.sin(((double)(var4)) * (0.06D))) / (5D)));
		ModelPart var10 = this.tail;
		var10.xRot = (float)(((double)(var10.xRot)) + ((Math.sin(((double)(var4)) * (0.06D))) / (5D)));
		ModelPart var11 = this.tail2;
		var11.yRot = (float)(((double)(var11.yRot)) + ((Math.cos(((double)(var4)) * (0.06D))) / (7D)));
		ModelPart var12 = this.tail2;
		var12.xRot = (float)(((double)(var12.xRot)) + ((Math.cos(((double)(var4)) * (0.06D))) / (2D)));
		ModelPart var13 = this.tail3;
		var13.yRot = (float)(((double)(var13.yRot)) + ((Math.sin(((double)(var4)) * (0.06D))) / (7D)));
		ModelPart var14 = this.tail3;
		var14.xRot = (float)(((double)(var14.xRot)) + ((Math.sin(((double)(var4)) * (0.06D))) / (2D)));
		if (var1.isSprinting()){
			this.tail.xRot = ((float)(Math.sin(((double)(var4)) * (0.6D)))) / (7F);
			this.tail.yRot = 0F;
			this.tail2.xRot = ((float)(Math.sin(((double)(var4)) * (0.6D)))) / (10F);
			this.tail2.yRot = 0F;
			this.tail3.xRot = ((float)(Math.sin(((double)(var4)) * (0.6D)))) / (12F);
			this.tail3.yRot = 0F;
		}
		this.setupAttackAnimation(var1,var4);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.leftFrontLeg.render(var1,var2,var3,var4,var5);
		this.rightFrontLeg.render(var1,var2,var3,var4,var5);
		this.leftHindLeg.render(var1,var2,var3,var4,var5);
		this.rightHindLeg.render(var1,var2,var3,var4,var5);
		this.body.render(var1,var2,var3,var4,var5);
		this.head.render(var1,var2,var3,var4,var5);
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		this.head.translateAndRotate(var2);
		var2.mulPose(Axis.ZP.rotationDegrees(90F));
		var2.mulPose(Axis.XP.rotationDegrees(260F));
		var2.translate(0.25D,-0.2D,0D);
	}
	public ModelPart getHead() {
		return this.head;
	}
}