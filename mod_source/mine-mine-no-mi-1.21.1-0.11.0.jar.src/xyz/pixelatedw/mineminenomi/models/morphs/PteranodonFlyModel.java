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
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.QuadrupedMorphModel;

public class PteranodonFlyModel<T extends LivingEntity> extends QuadrupedMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","pteranodon_fly"),"main");
	private final ModelPart rightEye;
	private final ModelPart leftEye;
	private final ModelPart horn;
	private final ModelPart horn2;
	private final ModelPart horn3;
	private final ModelPart horn4;
	private final ModelPart horn5;
	private final ModelPart horn6;
	private final ModelPart upperBeak;
	public final ModelPart lowerBeak;
	private final ModelPart neck;
	private final ModelPart rightWingSegment2;
	private final ModelPart rightWingSegment3;
	private final ModelPart leftWingSegment2;
	private final ModelPart leftWingSegment3;
	private final ModelPart rightLowerLeg;
	private final ModelPart rightFoot;
	private final ModelPart rightUpperThight;
	private final ModelPart leftLowerLeg;
	private final ModelPart leftFoot;
	private final ModelPart leftUpperThight;
	private final ModelPart lowerBody;

	public PteranodonFlyModel(ModelPart var1) {
		super(var1);
		this.rightEye = this.head.getChild("rightEye");
		this.leftEye = this.head.getChild("leftEye");
		this.horn = this.head.getChild("horn");
		this.horn2 = this.horn.getChild("horn2");
		this.horn3 = this.horn2.getChild("horn3");
		this.horn4 = this.horn3.getChild("horn4");
		this.horn5 = this.horn4.getChild("horn5");
		this.horn6 = this.horn5.getChild("horn6");
		this.upperBeak = this.head.getChild("upperBeak");
		this.lowerBeak = this.head.getChild("lowerBeak");
		this.neck = var1.getChild("neck");
		this.rightWingSegment2 = this.rightFrontLeg.getChild("rightWingSegment2");
		this.rightWingSegment3 = this.rightWingSegment2.getChild("rightWingSegment3");
		this.leftWingSegment2 = this.leftFrontLeg.getChild("leftWingSegment2");
		this.leftWingSegment3 = this.leftWingSegment2.getChild("leftWingSegment3");
		this.rightLowerLeg = this.rightHindLeg.getChild("rightLowerLeg");
		this.rightFoot = this.rightLowerLeg.getChild("rightFoot");
		this.rightUpperThight = this.rightHindLeg.getChild("rightUpperThight");
		this.leftLowerLeg = this.leftHindLeg.getChild("leftLowerLeg");
		this.leftFoot = this.leftLowerLeg.getChild("leftFoot");
		this.leftUpperThight = this.leftHindLeg.getChild("leftUpperThight");
		this.lowerBody = this.body.getChild("lowerBody");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = QuadrupedMorphModel.createBodyMesh(0,CubeDeformation.NONE);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(30,14).addBox(-1.5F,-1.2522F,-5.8546F,3F,4F,7F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,16.8298F,-10.5783F,0.0873F,0F,0F));
		var2.addOrReplaceChild("rightEye",CubeListBuilder.create().texOffs(43,6).mirror().addBox(0F,-1F,-1.75F,0F,2F,3F,new CubeDeformation(0F)).mirror(false),PartPose.offset(-1.525F,0.2478F,-1.1046F));
		var2.addOrReplaceChild("leftEye",CubeListBuilder.create().texOffs(43,6).mirror().addBox(0F,-1F,-1.75F,0F,2F,3F,new CubeDeformation(0F)).mirror(false),PartPose.offset(1.525F,0.2478F,-1.1046F));
		var2.addOrReplaceChild("horn",CubeListBuilder.create().texOffs(30,26).addBox(-1F,-0.5F,-1F,2F,1F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-0.9656F,-2.4745F,0.5236F,0F,0F)).addOrReplaceChild("horn2",CubeListBuilder.create().texOffs(30,29).addBox(-1F,-1.6862F,-1.1801F,2F,2F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0.739F,2.3141F,0.3491F,0F,0F)).addOrReplaceChild("horn3",CubeListBuilder.create().texOffs(31,33).addBox(-0.5F,-1.1862F,-1.1801F,1F,1F,2F,new CubeDeformation(0F)),PartPose.offset(0F,-0.4484F,1.351F)).addOrReplaceChild("horn4",CubeListBuilder.create().texOffs(31,36).addBox(-0.5F,0.0554F,-0.7619F,1F,1F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1.304F,1.4869F,0.0873F,0F,0F)).addOrReplaceChild("horn5",CubeListBuilder.create().texOffs(31,39).addBox(-0.5F,1.4156F,-0.3558F,1F,1F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1.382F,1.382F,0.0873F,0F,0F)).addOrReplaceChild("horn6",CubeListBuilder.create().texOffs(31,42).addBox(-0.5F,2.9544F,0.1402F,1F,1F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1.5115F,1.1598F,0.0873F,0F,0F));
		var2.addOrReplaceChild("upperBeak",CubeListBuilder.create().texOffs(54,0).addBox(-1F,-1.1625F,-2F,2F,2F,3F,new CubeDeformation(0.1F)).texOffs(38,0).addBox(-0.5F,-1.0625F,-13F,1F,2F,12F,new CubeDeformation(0F)),PartPose.offset(0F,0.7478F,-5.8546F));
		var2.addOrReplaceChild("lowerBeak",CubeListBuilder.create().texOffs(54,5).addBox(-1F,-0.5625F,-2F,2F,1F,3F,new CubeDeformation(0F)).texOffs(38,14).addBox(-0.5F,-0.5625F,-13F,1F,1F,12F,new CubeDeformation(0F)),PartPose.offset(0F,2.2478F,-5.8546F));
		var1.addOrReplaceChild("neck",CubeListBuilder.create().texOffs(12,16).addBox(-0.5F,-1.6712F,-7.1834F,1F,3F,8F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,18.625F,-4F,-0.1309F,0F,0F));
		PartDefinition var3 = var1.addOrReplaceChild("right_front_leg",CubeListBuilder.create().texOffs(-9,0).addBox(-5.75F,0F,1.9375F,6F,0F,9F,new CubeDeformation(0F)).texOffs(0,9).addBox(-6.75F,-0.5F,-1.0625F,7F,1F,2F,new CubeDeformation(0F)).texOffs(19,11).addBox(-5.75F,-0.5F,0.9375F,6F,1F,1F,new CubeDeformation(0F)),PartPose.offset(-2.75F,18.5F,-2.9375F)).addOrReplaceChild("rightWingSegment2",CubeListBuilder.create().texOffs(16,10).addBox(-8.75F,-0.5F,-4.0625F,9F,1F,2F,new CubeDeformation(0.01F)).texOffs(3,0).addBox(-8.75F,0F,-2.0625F,9F,0F,9F,new CubeDeformation(0.01F)),PartPose.offset(-5.75F,0F,5F)).addOrReplaceChild("rightWingSegment3",CubeListBuilder.create().texOffs(0,12).addBox(-6.75F,-0.5F,-3.0625F,7F,1F,2F,new CubeDeformation(0F)).texOffs(18,13).addBox(-7.75F,-0.5F,-1.0625F,5F,1F,1F,new CubeDeformation(0F)).texOffs(22,0).addBox(-7.65F,0F,-1.0625F,8F,0F,8F,new CubeDeformation(0F)),PartPose.offset(-8.75F,0F,0F));
		PartDefinition var4 = var1.addOrReplaceChild("left_front_leg",CubeListBuilder.create().texOffs(-9,0).mirror().addBox(-0.25F,0F,1.9375F,6F,0F,9F,new CubeDeformation(0F)).mirror(false).texOffs(0,9).mirror().addBox(-0.25F,-0.5F,-1.0625F,7F,1F,2F,new CubeDeformation(0F)).mirror(false).texOffs(19,11).mirror().addBox(-0.25F,-0.5F,0.9375F,6F,1F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offset(2.75F,18.5F,-2.9375F)).addOrReplaceChild("leftWingSegment2",CubeListBuilder.create().texOffs(16,10).mirror().addBox(-0.25F,-0.5F,-4.0625F,9F,1F,2F,new CubeDeformation(0.01F)).mirror(false).texOffs(3,0).mirror().addBox(-0.25F,0F,-2.0625F,9F,0F,9F,new CubeDeformation(0.01F)).mirror(false),PartPose.offset(5.75F,0F,5F)).addOrReplaceChild("leftWingSegment3",CubeListBuilder.create().texOffs(0,12).mirror().addBox(-0.25F,-0.5F,-3.0625F,7F,1F,2F,new CubeDeformation(0F)).mirror(false).texOffs(18,13).mirror().addBox(2.75F,-0.5F,-1.0625F,5F,1F,1F,new CubeDeformation(0F)).mirror(false).texOffs(22,0).mirror().addBox(-0.35F,0F,-1.0625F,8F,0F,8F,new CubeDeformation(0F)).mirror(false),PartPose.offset(8.75F,0F,0F));
		PartDefinition var5 = var1.addOrReplaceChild("right_hind_leg",CubeListBuilder.create(),PartPose.offsetAndRotation(-3F,20F,13.7F,1.3526F,0F,0F));
		var5.addOrReplaceChild("rightLowerLeg",CubeListBuilder.create().texOffs(0,23).addBox(-5.5F,0.6958F,-20.7223F,1F,2F,5F,new CubeDeformation(0F)).texOffs(0,15).addBox(-5.5F,0.6958F,-15.7223F,1F,4F,1F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(5F,12.1847F,7.6754F,-1.0472F,0F,0F)).addOrReplaceChild("rightFoot",CubeListBuilder.create().texOffs(9,15).addBox(-7.5F,4.6958F,-15.7354F,5F,4F,0F,new CubeDeformation(0F)).texOffs(0,31).addBox(-6.5F,3.93F,-15.7223F,3F,3F,1F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		var5.addOrReplaceChild("rightUpperThight",CubeListBuilder.create().texOffs(0,15).addBox(-6F,18.0963F,-11.8567F,2F,3F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(5F,13.8689F,7.5424F,-2.2253F,0F,0F));
		PartDefinition var6 = var1.addOrReplaceChild("left_hind_leg",CubeListBuilder.create(),PartPose.offsetAndRotation(3F,20F,13.7F,1.3526F,0F,0F));
		var6.addOrReplaceChild("leftLowerLeg",CubeListBuilder.create().texOffs(0,23).mirror().addBox(4.5F,0.6958F,-20.7223F,1F,2F,5F,new CubeDeformation(0F)).mirror(false).texOffs(0,15).mirror().addBox(4.5F,0.6958F,-15.7223F,1F,4F,1F,new CubeDeformation(-0.01F)).mirror(false),PartPose.offsetAndRotation(-5F,12.1847F,7.6754F,-1.0472F,0F,0F)).addOrReplaceChild("leftFoot",CubeListBuilder.create().texOffs(9,15).mirror().addBox(2.5F,4.6958F,-15.7354F,5F,4F,0F,new CubeDeformation(0F)).mirror(false).texOffs(0,31).mirror().addBox(3.5F,3.93F,-15.7223F,3F,3F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offset(0F,0F,0F));
		var6.addOrReplaceChild("leftUpperThight",CubeListBuilder.create().texOffs(0,15).mirror().addBox(4F,18.0963F,-11.8567F,2F,3F,5F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-5F,13.8689F,7.5424F,-2.2253F,0F,0F));
		var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(2,41).addBox(-3F,-1.4344F,-2.2227F,6F,5F,7F,new CubeDeformation(0F)),PartPose.offset(0F,17.9344F,-2.7773F)).addOrReplaceChild("lowerBody",CubeListBuilder.create().texOffs(2,30).addBox(-2.5F,-1.3995F,-0.3291F,5F,4F,7F,new CubeDeformation(0F)).texOffs(0,41).addBox(-1.5F,-0.9995F,6.6709F,3F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0.2172F,4.7364F,-0.0873F,0F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		if ((var1.getDeltaMovement().y) < (-1.7D)){
			ModelPart var7 = this.leftFrontLeg;
			var7.xRot = (float)(((double)(var7.xRot)) + (Math.toRadians(-90D)));
			ModelPart var8 = this.leftFrontLeg;
			var8.yRot = (float)(((double)(var8.yRot)) + (Math.toRadians(-85D)));
			ModelPart var9 = this.leftFrontLeg;
			var9.yRot = (var9.yRot) + ((Mth.cos(((float)(var1.tickCount)) * (0.9F))) / (50F));
			ModelPart var10 = this.leftFrontLeg;
			var10.zRot = (var10.zRot) + ((Mth.cos(((float)(var1.tickCount)) * (2.9F))) / (50F));
			ModelPart var11 = this.rightFrontLeg;
			var11.xRot = (float)(((double)(var11.xRot)) + (Math.toRadians(-90D)));
			ModelPart var12 = this.rightFrontLeg;
			var12.yRot = (float)(((double)(var12.yRot)) + (Math.toRadians(85D)));
			ModelPart var13 = this.rightFrontLeg;
			var13.yRot = (var13.yRot) + ((Mth.cos(((float)(var1.tickCount)) * (0.9F))) / (50F));
			ModelPart var14 = this.rightFrontLeg;
			var14.zRot = (var14.zRot) + ((Mth.cos(((float)(var1.tickCount)) * (2.9F))) / (50F));
		}else{
			this.leftFrontLeg.zRot = (Mth.cos((var4) * (0.4F))) * (0.5F);
			this.leftWingSegment2.zRot = (Mth.cos((var4) * (0.4F))) * (0.5F);
			this.leftWingSegment3.zRot = (Mth.cos((var4) * (0.4F))) * (0.3F);
			this.rightFrontLeg.zRot = (Mth.cos(((var4) * (0.4F)) + (3.1415927F))) * (0.5F);
			this.rightWingSegment2.zRot = (Mth.cos(((var4) * (0.4F)) + (3.1415927F))) * (0.5F);
			this.rightWingSegment3.zRot = (Mth.cos(((var4) * (0.4F)) + (3.1415927F))) * (0.3F);
		}
		this.setupAttackAnimation(var1,var4);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.head.render(var1,var2,var3,var4,var5);
		this.neck.render(var1,var2,var3,var4,var5);
		this.rightFrontLeg.render(var1,var2,var3,var4,var5);
		this.leftFrontLeg.render(var1,var2,var3,var4,var5);
		this.rightHindLeg.render(var1,var2,var3,var4,var5);
		this.leftHindLeg.render(var1,var2,var3,var4,var5);
		this.body.render(var1,var2,var3,var4,var5);
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		this.head.translateAndRotate(var2);
		var2.scale(0.7F,0.7F,0.7F);
		var2.mulPose(Axis.ZP.rotationDegrees(90F));
		var2.mulPose(Axis.XP.rotationDegrees(260F));
		var2.translate(0.25D,0.6D,0.2D);
	}
	public ModelPart getHead() {
		return this.head;
	}
}