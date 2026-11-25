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

public class MammothGuardModel<T extends LivingEntity> extends QuadrupedMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","mammoth_guard"),"main");
	private final ModelPart leftEye;
	private final ModelPart rightEye;
	private final ModelPart trunk;
	private final ModelPart trunk2;
	private final ModelPart trunk3;
	private final ModelPart leftEar;
	private final ModelPart rightEar;
	private final ModelPart leftTusk;
	private final ModelPart leftTusk2;
	private final ModelPart rightTusk;
	private final ModelPart rightTusk2;
	private final ModelPart body2;
	private final ModelPart body3;
	private final ModelPart tail;
	private final ModelPart tail2;

	public MammothGuardModel(ModelPart var1) {
		super(var1);
		this.leftEye = this.head.getChild("leftEye");
		this.rightEye = this.head.getChild("rightEye");
		this.trunk = this.head.getChild("trunk");
		this.trunk2 = this.trunk.getChild("trunk2");
		this.trunk3 = this.trunk2.getChild("trunk3");
		this.leftEar = this.head.getChild("leftEar");
		this.rightEar = this.head.getChild("rightEar");
		this.leftTusk = this.head.getChild("leftTusk");
		this.leftTusk2 = this.leftTusk.getChild("leftTusk2");
		this.rightTusk = this.head.getChild("rightTusk");
		this.rightTusk2 = this.rightTusk.getChild("rightTusk2");
		this.body2 = this.body.getChild("body2");
		this.body3 = this.body2.getChild("body3");
		this.tail = this.body3.getChild("tail");
		this.tail2 = this.tail.getChild("tail2");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = QuadrupedMorphModel.createBodyMesh(0,CubeDeformation.NONE);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(22,43).addBox(-5F,-6F,-7F,10F,12F,9F,new CubeDeformation(0F)),PartPose.offset(0F,4F,-15.75F));
		var2.addOrReplaceChild("leftEye",CubeListBuilder.create().texOffs(9,8).addBox(0F,-1F,-1F,0F,2F,2F,new CubeDeformation(0F)),PartPose.offset(5.025F,-2.225F,-5.3375F));
		var2.addOrReplaceChild("rightEye",CubeListBuilder.create().texOffs(9,8).mirror().addBox(0F,-1F,-1F,0F,2F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offset(-5.025F,-2.225F,-5.3375F));
		var2.addOrReplaceChild("trunk",CubeListBuilder.create().texOffs(15,31).addBox(-2F,-3.671F,-2.9673F,4F,7F,4F,new CubeDeformation(0.5F)),PartPose.offsetAndRotation(0F,4F,-5.5F,-0.3054F,0F,0F)).addOrReplaceChild("trunk2",CubeListBuilder.create().texOffs(32,31).addBox(-2F,-0.875F,-1.625F,4F,7F,4F,new CubeDeformation(0.2F)),PartPose.offsetAndRotation(0F,3.602F,-1.3293F,0.3491F,0F,0F)).addOrReplaceChild("trunk3",CubeListBuilder.create().texOffs(48,32).addBox(-2F,-0.9353F,-1.554F,4F,6F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,6.4876F,-0.0527F,0.2182F,0F,0F));
		var2.addOrReplaceChild("leftEar",CubeListBuilder.create().texOffs(0,54).mirror().addBox(0F,-4.5F,-0.5F,8F,9F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(4F,-2.5F,-3.5F,-0.1368F,-0.4707F,0.2946F));
		var2.addOrReplaceChild("rightEar",CubeListBuilder.create().texOffs(0,54).addBox(-8F,-4.5F,-0.5F,8F,9F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-4F,-2.5F,-3.5F,-0.1368F,0.4707F,-0.2946F));
		var2.addOrReplaceChild("leftTusk",CubeListBuilder.create().texOffs(0,0).mirror().addBox(-1.0151F,-2.6722F,-0.7564F,2F,8F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(2.8172F,4.2807F,-7.1282F,-1.1781F,-0.2094F,0F)).addOrReplaceChild("leftTusk2",CubeListBuilder.create().texOffs(0,1).mirror().addBox(-1F,-0.0387F,-0.8341F,2F,7F,2F,new CubeDeformation(-0.1F)).mirror(false),PartPose.offsetAndRotation(-0.0151F,5.0636F,0.0887F,-0.1745F,0F,0F));
		var2.addOrReplaceChild("rightTusk",CubeListBuilder.create().texOffs(0,0).addBox(-0.9849F,-2.6722F,-0.7564F,2F,8F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2.8172F,4.2807F,-7.1282F,-1.1781F,0.2094F,0F)).addOrReplaceChild("rightTusk2",CubeListBuilder.create().texOffs(0,1).addBox(-1F,-0.0387F,-0.8341F,2F,7F,2F,new CubeDeformation(-0.1F)),PartPose.offsetAndRotation(0.0151F,5.0636F,0.0887F,-0.1745F,0F,0F));
		var1.addOrReplaceChild("left_front_leg",CubeListBuilder.create().texOffs(0,38).addBox(-2.5F,-0.5F,-2.5F,5F,10F,5F,new CubeDeformation(0F)),PartPose.offset(5F,14.5F,-10F));
		var1.addOrReplaceChild("right_front_leg",CubeListBuilder.create().texOffs(0,38).mirror().addBox(-2.5F,-0.5F,-2.5F,5F,10F,5F,new CubeDeformation(0F)).mirror(false),PartPose.offset(-5F,14.5F,-10F));
		var1.addOrReplaceChild("left_hind_leg",CubeListBuilder.create().texOffs(0,38).addBox(-2.5F,-0.475F,-2.5F,5F,10F,5F,new CubeDeformation(0F)),PartPose.offset(3.75F,14.475F,9F));
		var1.addOrReplaceChild("right_hind_leg",CubeListBuilder.create().texOffs(0,38).mirror().addBox(-2.5F,-0.475F,-2.5F,5F,10F,5F,new CubeDeformation(0F)).mirror(false),PartPose.offset(-4F,14.475F,9F));
		var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(1,0).addBox(-8F,-9F,-6.5F,16F,17F,13F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,8.4874F,-9.199F,0.0873F,0F,0F)).addOrReplaceChild("body2",CubeListBuilder.create().texOffs(3,2).mirror().addBox(-7.5F,-8.6873F,-5.4916F,15F,16F,11F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0F,0.5488F,8.615F,-0.0873F,0F,0F)).addOrReplaceChild("body3",CubeListBuilder.create().texOffs(2,1).addBox(-7.1F,-6.754F,1.6015F,14F,15F,13F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1.625F,0F,-0.0873F,0F,0F)).addOrReplaceChild("tail",CubeListBuilder.create().texOffs(0,32).addBox(-0.5F,0F,-0.5F,1F,9F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-3.4112F,14.084F,0.3491F,0F,0F)).addOrReplaceChild("tail2",CubeListBuilder.create().texOffs(5,31).addBox(-1F,0F,-1F,2F,4F,2F,new CubeDeformation(0F)),PartPose.offset(0F,8F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		this.setupHeadRotation(var1,var6,var5);
		this.rightFrontLeg.xRot = ((Mth.cos((var2) * (0.4F))) * (0.3F)) * (var3);
		this.leftFrontLeg.xRot = ((Mth.cos(((var2) * (0.4F)) + (3.1415927F))) * (0.3F)) * (var3);
		this.rightHindLeg.xRot = ((Mth.cos((var2) * (0.4F))) * (0.4F)) * (var3);
		this.leftHindLeg.xRot = ((Mth.cos(((var2) * (0.4F)) + (3.1415927F))) * (0.4F)) * (var3);
		if (var1.isSprinting()){
			this.tail.xRot = (0.5F) + (((Mth.cos((var2) * (0.8F))) * (0.1F)) * (var3));
			this.leftEar.yRot = (-0.3F) - (((Mth.cos((var2) * (0.6662F))) * (0.2F)) * (var3));
			this.rightEar.yRot = (0.3F) + (((Mth.cos((var2) * (0.6662F))) * (0.2F)) * (var3));
		}
		this.setupAttackAnimation(var1,var4);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.head.render(var1,var2,var3,var4,var5);
		this.leftFrontLeg.render(var1,var2,var3,var4,var5);
		this.rightFrontLeg.render(var1,var2,var3,var4,var5);
		this.leftHindLeg.render(var1,var2,var3,var4,var5);
		this.rightHindLeg.render(var1,var2,var3,var4,var5);
		this.body.render(var1,var2,var3,var4,var5);
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		this.head.translateAndRotate(var2);
		var2.mulPose(Axis.ZP.rotationDegrees(90F));
		var2.mulPose(Axis.XP.rotationDegrees(260F));
		var2.translate(1D,-0.2D,0D);
	}
	public ModelPart getHead() {
		return this.head;
	}
}