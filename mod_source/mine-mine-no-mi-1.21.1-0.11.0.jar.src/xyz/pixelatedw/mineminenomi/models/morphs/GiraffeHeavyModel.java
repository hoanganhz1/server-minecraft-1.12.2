package xyz.pixelatedw.mineminenomi.models.morphs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
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
import xyz.pixelatedw.mineminenomi.api.morph.HumanoidMorphModel;

public class GiraffeHeavyModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","giraffe_heavy"),"main");
	private final ModelPart neck;
	private final ModelPart face;
	private final ModelPart rightEar;
	private final ModelPart mouth;
	private final ModelPart leftHorn;
	private final ModelPart rightHorn;
	private final ModelPart leftEar;
	private final ModelPart mane;
	private final ModelPart mane2;
	private final ModelPart leftShoulder;
	private final ModelPart rightShoulder;
	private final ModelPart rightArm2;
	private final ModelPart rightHand2;
	private final ModelPart rightHand1;
	private final ModelPart leftArm2;
	private final ModelPart leftHand2;
	private final ModelPart leftHand1;
	private final ModelPart rightLeg3;
	private final ModelPart rightLeg2;
	private final ModelPart rightHoof;
	private final ModelPart rightHoof2;
	private final ModelPart rightHoof3;
	private final ModelPart tail;
	private final ModelPart tail2;
	private final ModelPart tail3;
	private final ModelPart leftLeg2;
	private final ModelPart leftLeg3;
	private final ModelPart leftHoof;
	private final ModelPart leftHoof2;
	private final ModelPart leftHoof3;

	public GiraffeHeavyModel(ModelPart var1) {
		super(var1);
		this.neck = this.head.getChild("neck");
		this.face = this.neck.getChild("face");
		this.rightEar = this.face.getChild("rightEar");
		this.mouth = this.face.getChild("mouth");
		this.leftHorn = this.face.getChild("leftHorn");
		this.rightHorn = this.face.getChild("rightHorn");
		this.leftEar = this.face.getChild("leftEar");
		this.mane = this.neck.getChild("mane");
		this.mane2 = this.head.getChild("mane2");
		this.rightShoulder = this.body.getChild("rightShoulder");
		this.leftShoulder = this.body.getChild("leftShoulder");
		this.rightArm2 = this.rightArm.getChild("rightArm2");
		this.rightHand2 = this.rightArm2.getChild("rightHand2");
		this.rightHand1 = this.rightArm2.getChild("rightHand1");
		this.leftArm2 = this.leftArm.getChild("leftArm2");
		this.leftHand2 = this.leftArm2.getChild("leftHand2");
		this.leftHand1 = this.leftArm2.getChild("leftHand1");
		this.leftLeg2 = this.leftLeg.getChild("leftLeg2");
		this.leftLeg3 = this.leftLeg2.getChild("leftLeg3");
		this.leftHoof = this.leftLeg3.getChild("leftHoof");
		this.leftHoof2 = this.leftHoof.getChild("leftHoof2");
		this.leftHoof3 = this.leftHoof.getChild("leftHoof3");
		this.rightLeg2 = this.rightLeg.getChild("rightLeg2");
		this.rightLeg3 = this.rightLeg2.getChild("rightLeg3");
		this.rightHoof = this.rightLeg3.getChild("rightHoof");
		this.rightHoof2 = this.rightHoof.getChild("rightHoof2");
		this.rightHoof3 = this.rightHoof.getChild("rightHoof3");
		this.tail = var1.getChild("tail");
		this.tail2 = this.tail.getChild("tail2");
		this.tail3 = this.tail2.getChild("tail3");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(48,33).mirror().addBox(-2F,-6.7514F,-1.7313F,4F,8F,4F,new CubeDeformation(0.5F)).mirror(false),PartPose.offsetAndRotation(0F,-4F,0F,0.1745F,0F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("neck",CubeListBuilder.create().texOffs(48,33).addBox(0F,0F,0F,4F,8F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2F,-14.5014F,-2.7313F,0.1396F,0F,0F));
		PartDefinition var4 = var3.addOrReplaceChild("face",CubeListBuilder.create().texOffs(32,18).addBox(-2F,-4F,-6F,4F,3F,8F,new CubeDeformation(0F)),PartPose.offsetAndRotation(2F,0.5F,1.5F,-0.3142F,0F,0F));
		PartDefinition var5 = var4.addOrReplaceChild("rightEar",CubeListBuilder.create().texOffs(32,0).addBox(-3F,0F,0F,3F,2F,0F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2F,-3.5F,0F,0F,0F,-0.2618F));
		PartDefinition var6 = var4.addOrReplaceChild("mouth",CubeListBuilder.create().texOffs(31,29).addBox(0.01F,0F,-4F,4F,2F,5F,new CubeDeformation(0F)),PartPose.offset(-2F,-1F,0.5F));
		PartDefinition var7 = var4.addOrReplaceChild("leftHorn",CubeListBuilder.create().texOffs(39,13).addBox(0F,0F,0F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offset(0.5F,-6F,-0.5F));
		PartDefinition var8 = var4.addOrReplaceChild("rightHorn",CubeListBuilder.create().texOffs(39,13).addBox(0F,0F,0F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offset(-1.5F,-6F,-0.5F));
		PartDefinition var9 = var4.addOrReplaceChild("leftEar",CubeListBuilder.create().texOffs(32,0).mirror().addBox(0F,0F,0F,3F,2F,0F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(2F,-3.5F,0F,0F,0F,0.2618F));
		PartDefinition var10 = var3.addOrReplaceChild("mane",CubeListBuilder.create().texOffs(33,37).addBox(0F,0F,0.2F,0F,10F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(2F,-2F,3.5F,0.0349F,0F,0F));
		PartDefinition var11 = var2.addOrReplaceChild("mane2",CubeListBuilder.create().texOffs(33,37).addBox(0F,0F,0F,0F,8F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-7.5014F,2.3687F,0.0175F,0F,0F));
		PartDefinition var12 = var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,0).addBox(-4.5F,0F,-2F,10F,16F,5F,new CubeDeformation(0F)),PartPose.offset(-0.5F,-4F,0F));
		PartDefinition var13 = var12.addOrReplaceChild("rightShoulder",CubeListBuilder.create().texOffs(36,0).addBox(0F,-7.5F,-0.09F,9F,7F,5F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(2F,7.4F,-1.91F,0F,0F,-2.2689F));
		PartDefinition var14 = var12.addOrReplaceChild("leftShoulder",CubeListBuilder.create().texOffs(36,0).mirror().addBox(-9F,-7.5F,-0.09F,9F,7F,5F,new CubeDeformation(-0.01F)).mirror(false),PartPose.offsetAndRotation(-1F,7.4F,-1.91F,0F,0F,2.2689F));
		PartDefinition var15 = var1.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(23,30).addBox(0F,0F,0F,2F,6F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-8F,1F,-1F,0F,0F,0.2793F)).addOrReplaceChild("rightArm2",CubeListBuilder.create().texOffs(23,39).addBox(0F,0F,0.1F,2F,6F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,6F,0F,0F,0F,-0.4189F));
		PartDefinition var16 = var15.addOrReplaceChild("rightHand2",CubeListBuilder.create().texOffs(27,2).addBox(0F,-0.0868F,-0.4924F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(1.1392F,5.1097F,0.8F,0.1745F,0F,-0.3491F));
		PartDefinition var17 = var15.addOrReplaceChild("rightHand1",CubeListBuilder.create().texOffs(26,2).addBox(-0.1F,0F,0.1F,2F,2F,1F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0F,5.5F,2F,0.1745F,1.5708F,0.1396F));
		PartDefinition var18 = var1.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(23,30).mirror().addBox(-2F,0F,0F,2F,6F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(8F,1F,-1F,0F,0F,-0.2793F)).addOrReplaceChild("leftArm2",CubeListBuilder.create().texOffs(23,39).mirror().addBox(-2F,0F,0.1F,2F,6F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0F,6F,0F,0F,0F,0.4189F));
		PartDefinition var19 = var18.addOrReplaceChild("leftHand2",CubeListBuilder.create().texOffs(27,2).mirror().addBox(-1F,-0.0868F,-0.4924F,1F,2F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-1.1392F,5.1097F,0.8F,0.1745F,0F,0.3491F));
		PartDefinition var20 = var18.addOrReplaceChild("leftHand1",CubeListBuilder.create().texOffs(26,2).mirror().addBox(-1.9F,0F,0.1F,2F,2F,1F,new CubeDeformation(-0.01F)).mirror(false),PartPose.offsetAndRotation(0F,5.5F,2F,0.1745F,-1.5708F,-0.1396F));
		PartDefinition var21 = var1.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(10,30).addBox(-2F,-0.4F,-2F,3F,7F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(3.5F,12F,1F,-0.3491F,0F,0F)).addOrReplaceChild("leftLeg2",CubeListBuilder.create().texOffs(10,41).addBox(-2F,0F,-2F,2F,4F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.5F,4.6F,0F,1.7453F,0F,0F)).addOrReplaceChild("leftLeg3",CubeListBuilder.create().texOffs(0,30).addBox(-2F,0F,-2F,2F,6F,2F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,5.2065F,-0.5747F,-1.9199F,0F,0F)).addOrReplaceChild("leftHoof",CubeListBuilder.create().texOffs(25,1).addBox(-2F,0F,-2F,2F,2F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,5F,-0.5F,0.5236F,0F,0F));
		PartDefinition var22 = var21.addOrReplaceChild("leftHoof2",CubeListBuilder.create().texOffs(27,2).addBox(0F,0F,0F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-1F,0F,-2.5F,-0.1211F,-0.4883F,-0.0394F));
		PartDefinition var23 = var21.addOrReplaceChild("leftHoof3",CubeListBuilder.create().texOffs(27,2).addBox(0F,0F,0F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2F,0F,-2F,-0.1211F,0.4883F,0.0394F));
		PartDefinition var24 = var1.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(10,30).mirror().addBox(-1F,-0.4F,-2F,3F,7F,3F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-3.5F,12F,1F,-0.3491F,0F,0F)).addOrReplaceChild("rightLeg2",CubeListBuilder.create().texOffs(10,41).mirror().addBox(0F,0F,-2F,2F,4F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-0.5F,4.6F,0F,1.7453F,0F,0F)).addOrReplaceChild("rightLeg3",CubeListBuilder.create().texOffs(0,30).mirror().addBox(0F,0F,-2F,2F,6F,2F,new CubeDeformation(0.01F)).mirror(false),PartPose.offsetAndRotation(0F,5.2065F,-0.5747F,-1.9199F,0F,0F)).addOrReplaceChild("rightHoof",CubeListBuilder.create().texOffs(25,1).mirror().addBox(0F,0F,-2F,2F,2F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0F,5F,-0.5F,0.5236F,0F,0F));
		PartDefinition var25 = var24.addOrReplaceChild("rightHoof2",CubeListBuilder.create().texOffs(27,2).mirror().addBox(-1F,0F,0F,1F,2F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(1F,0F,-2.5F,-0.1211F,0.4883F,0.0394F));
		PartDefinition var26 = var24.addOrReplaceChild("rightHoof3",CubeListBuilder.create().texOffs(27,2).mirror().addBox(-1F,0F,0F,1F,2F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(2F,0F,-2F,-0.1211F,-0.4883F,-0.0394F));
		PartDefinition var27 = var1.addOrReplaceChild("tail",CubeListBuilder.create().texOffs(30,12).addBox(0F,0F,0F,1F,1F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.5F,10F,3F,-0.733F,0F,0F)).addOrReplaceChild("tail2",CubeListBuilder.create().texOffs(30,12).addBox(0F,0.15F,0F,1F,1F,5F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0F,0F,4.5F,0.4712F,0F,0F)).addOrReplaceChild("tail3",CubeListBuilder.create().texOffs(43,13).addBox(0F,-0.8803F,0.1871F,2F,2F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.5F,0.5422F,4.4176F,0.2967F,0F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		this.setupHeadRotation(var1,var6,var5);
		float var7 = 1F;
		this.rightArm.xRot = ((((Mth.cos(((var2) * (0.6662F)) + (3.1415927F))) * (0.8F)) * (var3)) * (0.5F)) / (var7);
		this.leftArm.xRot = ((((Mth.cos((var2) * (0.6662F))) * (0.8F)) * (var3)) * (0.5F)) / (var7);
		this.rightLeg.xRot = (-0.34F) + ((((Mth.cos((var2) * (0.6662F))) * (0.7F)) * (var3)) / (var7));
		this.leftLeg.xRot = (-0.34F) + ((((Mth.cos(((var2) * (0.6662F)) + (3.1415927F))) * (0.7F)) * (var3)) / (var7));
		if (var1.isSprinting()){
			this.tail.xRot = ((Mth.cos((var2) * (0.6662F))) * (0.2F)) * (var3);
			this.leftEar.yRot = (-0.3F) - (((Mth.cos((var2) * (0.6662F))) * (0.2F)) * (var3));
			this.rightEar.yRot = (0.3F) + (((Mth.cos((var2) * (0.6662F))) * (0.2F)) * (var3));
		}
		this.setupAttackAnimation(var1,var4);
		if (var1.isCrouching()){
			this.head.xRot = 0.35F;
			this.head.z = -8F;
			this.head.y = -5F;
			ModelPart var8 = this.head;
			var8.xScale = (var8.xScale) + (0.2F);
			ModelPart var9 = this.head;
			var9.yScale = (var9.yScale) - (0.3F);
			ModelPart var10 = this.head;
			var10.zScale = (var10.zScale) + (0.2F);
			this.neck.y = -3F;
			ModelPart var11 = this.face;
			var11.xScale = (var11.xScale) - (0.2F);
			ModelPart var12 = this.face;
			var12.yScale = (var12.yScale) + (0.3F);
			ModelPart var13 = this.face;
			var13.zScale = (var13.zScale) - (0.2F);
			this.body.xRot = 0.25F;
			this.body.z = -4F;
			this.body.y = -1F;
			this.rightArm.xRot = 0.25F;
			this.rightArm.z = -3F;
			this.rightArm.y = 4F;
			this.leftArm.xRot = 0.25F;
			this.leftArm.z = -3F;
			this.leftArm.y = 4F;
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		super.renderToBuffer(var1,var2,var3,var4,var5);
		this.tail.render(var1,var2,var3,var4,var5);
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		super.translateToHand(var1,var2);
		var2.translate(((var1) == (HumanoidArm.RIGHT)) ? (0.12D) : (-0.12D),0.2D,0.1D);
		var2.mulPose(Axis.ZP.rotationDegrees(((var1) == (HumanoidArm.RIGHT)) ? (-20F) : (20F)));
	}
	public boolean renderFirstPersonArm(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5, HumanoidArm var6, boolean var7) {
		float var8 = ((var6) == (HumanoidArm.RIGHT)) ? (1F) : (-1F);
		var1.translate((-0.2D) * ((double)(var8)),-0.1D,-0.3D);
		var1.mulPose(Axis.XP.rotationDegrees(20F));
		if (var7){
			var1.translate((0.2D) * ((double)(var8)),0D,0D);
		}
		return super.renderFirstPersonArm(var1,var2,var3,var4,var5,var6,var7);
	}
}