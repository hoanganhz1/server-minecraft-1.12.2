package xyz.pixelatedw.mineminenomi.models.entities.mobs.animals;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.minecraft.client.model.AgeableListModel;
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
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.LapahnEntity;

public class LapahnModel<T extends LapahnEntity> extends AgeableListModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","lapahn"),"main");
	private final ModelPart head;
	private final ModelPart leftEar;
	private final ModelPart rightEar;
	private final ModelPart wiskers;
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart rightLeg;
	private final ModelPart rightLeg2;
	private final ModelPart rightFoot;
	private final ModelPart leftLeg;
	private final ModelPart leftLeg2;
	private final ModelPart leftFoot;
	private final ModelPart rightArm;
	private final ModelPart rightArm2;
	private final ModelPart leftArm;
	private final ModelPart leftArm2;
	protected float jumpAnimationProgress;

	public LapahnModel(ModelPart var1) {
		this.head = var1.getChild("head");
		this.leftEar = this.head.getChild("leftEar");
		this.rightEar = this.head.getChild("rightEar");
		this.wiskers = this.head.getChild("wiskers");
		this.body = var1.getChild("body");
		this.tail = this.body.getChild("tail");
		this.rightLeg = var1.getChild("rightLeg");
		this.rightLeg2 = this.rightLeg.getChild("rightLeg2");
		this.rightFoot = this.rightLeg2.getChild("rightFoot");
		this.leftLeg = var1.getChild("leftLeg");
		this.leftLeg2 = this.leftLeg.getChild("leftLeg2");
		this.leftFoot = this.leftLeg2.getChild("leftFoot");
		this.rightArm = var1.getChild("rightArm");
		this.rightArm2 = this.rightArm.getChild("rightArm2");
		this.leftArm = var1.getChild("leftArm");
		this.leftArm2 = this.leftArm.getChild("leftArm2");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).addBox(-3F,-6F,-1F,6F,6F,6F,new CubeDeformation(0F)),PartPose.offset(0F,-7F,0F));
		var2.addOrReplaceChild("leftEar",CubeListBuilder.create().texOffs(25,0).addBox(-0.6541F,-5.8585F,1.4648F,2F,6F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(1.7F,-5.5F,0F,0.0719F,-0.1738F,0.0887F));
		var2.addOrReplaceChild("rightEar",CubeListBuilder.create().texOffs(25,0).addBox(-1.3459F,-5.8585F,1.4648F,2F,6F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-1.7F,-5.5F,0F,0.0719F,0.1738F,-0.0887F));
		var2.addOrReplaceChild("wiskers",CubeListBuilder.create().texOffs(26,13).addBox(-4.5F,-6F,-1F,9F,9F,0F,new CubeDeformation(0F)),PartPose.offset(0F,-1F,-0.1F));
		var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(35,13).addBox(-8.5F,0F,-4F,17F,10F,12F,new CubeDeformation(0F)).texOffs(35,36).addBox(-8F,-6F,-3.5F,16F,6F,11F,new CubeDeformation(0F)).texOffs(80,49).addBox(-7F,-11F,-3F,14F,5F,10F,new CubeDeformation(0F)).texOffs(84,0).addBox(-6.5F,-13F,-2.5F,13F,2F,9F,new CubeDeformation(0F)).texOffs(35,0).addBox(-8F,10F,-3.5F,16F,1F,11F,new CubeDeformation(0F)),PartPose.offset(0F,6F,0F)).addOrReplaceChild("tail",CubeListBuilder.create().texOffs(0,50).addBox(-1F,-1.209F,1.989F,2F,2F,2F,new CubeDeformation(1F)),PartPose.offsetAndRotation(0F,7F,5.8F,-0.1047F,0F,0F));
		var1.addOrReplaceChild("rightLeg",CubeListBuilder.create().texOffs(0,13).addBox(-4F,-1.5847F,-2.0874F,8F,6F,9F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-4.8F,14.9F,-2.2F,-0.2967F,0F,0F)).addOrReplaceChild("rightLeg2",CubeListBuilder.create().texOffs(0,29).addBox(2.8F,-16.3302F,2.1988F,4F,3F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-4.8F,20.5228F,5.2592F,0.3316F,0F,0F)).addOrReplaceChild("rightFoot",CubeListBuilder.create().texOffs(0,37).addBox(-2.5F,0.0692F,-4.9639F,5F,2F,10F,new CubeDeformation(0F)),PartPose.offsetAndRotation(4.85F,-13.9053F,2.0669F,-0.0349F,0F,0F));
		var1.addOrReplaceChild("leftLeg",CubeListBuilder.create().texOffs(0,13).addBox(-4F,-1.5847F,-2.0874F,8F,6F,9F,new CubeDeformation(0F)),PartPose.offsetAndRotation(4.8F,14.9F,-2.2F,-0.2967F,0F,0F)).addOrReplaceChild("leftLeg2",CubeListBuilder.create().texOffs(0,29).addBox(-2F,0.3198F,-0.0012F,4F,3F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,3.8F,2F,0.3316F,0F,0F)).addOrReplaceChild("leftFoot",CubeListBuilder.create().texOffs(0,37).addBox(-2.5F,-0.1719F,-5.2225F,5F,2F,10F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.05F,3.2447F,0.1169F,-0.0349F,0F,0F));
		var1.addOrReplaceChild("rightArm",CubeListBuilder.create().texOffs(93,12).addBox(-6F,-2F,-0.5F,6F,10F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-6.5F,-3F,0F,0F,0F,0.2618F)).addOrReplaceChild("rightArm2",CubeListBuilder.create().texOffs(93,29).addBox(-3.5F,0F,-0.5F,6F,9F,6F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(-2.5F,7.5F,0F,0F,0F,-0.1222F));
		var1.addOrReplaceChild("leftArm",CubeListBuilder.create().texOffs(93,12).addBox(0F,-2F,-0.5F,6F,10F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(6.5F,-3F,0F,0F,0F,-0.2618F)).addOrReplaceChild("leftArm2",CubeListBuilder.create().texOffs(93,29).addBox(-2.5F,-1F,-0.5F,6F,9F,6F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(2.5F,8.5F,0F,0F,0F,0.1222F));
		return LayerDefinition.create(var0,128,64);
	}
	public void prepareMobModel(T var1, float var2, float var3, float var4) {
		this.jumpAnimationProgress = var1.getJumpAnimationProgress(var4);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		this.head.getAllParts().forEach((Consumer)(((Consumer<ModelPart>)(ModelPart::resetPose))));
		this.body.getAllParts().forEach((Consumer)(((Consumer<ModelPart>)(ModelPart::resetPose))));
		this.rightArm.getAllParts().forEach((Consumer)(((Consumer<ModelPart>)(ModelPart::resetPose))));
		this.leftArm.getAllParts().forEach((Consumer)(((Consumer<ModelPart>)(ModelPart::resetPose))));
		this.rightLeg.getAllParts().forEach((Consumer)(((Consumer<ModelPart>)(ModelPart::resetPose))));
		this.leftLeg.getAllParts().forEach((Consumer)(((Consumer<ModelPart>)(ModelPart::resetPose))));
		this.head.yRot = (var5) * (0.017453292F);
		this.head.xRot = (var6) * (0.017453292F);
		if ((this.jumpAnimationProgress) > (0F)){
			this.rightLeg.xRot = (Mth.sin(((this.jumpAnimationProgress) * (2F)) * (3.1415927F))) * (0.8F);
			this.leftLeg.xRot = (Mth.sin(((this.jumpAnimationProgress) * (2F)) * (3.1415927F))) * (0.8F);
		}
		if (var1.isChasing()){
			this.head.z = -19F;
			this.head.y = 7F;
			this.body.y = 11F;
			this.body.z = -4F;
			this.body.xRot = (float)(Math.toRadians(80D));
			this.rightArm.z = -16F;
			this.rightArm.x = -3.5F;
			this.rightArm.y = 8F;
			this.rightArm.xRot = ((float)(Math.toRadians(-20D))) + (((((Mth.cos(((var2) * (0.9F)) + (3.1415927F))) * (0.7F)) * (var3)) * (0.7F)) / (1F));
			this.leftArm.z = -16F;
			this.leftArm.x = 3.5F;
			this.leftArm.y = 8F;
			this.leftArm.xRot = ((float)(Math.toRadians(-20D))) + (((((Mth.cos(((var2) * (0.9F)) + (3.1415927F))) * (0.7F)) * (var3)) * (0.7F)) / (1F));
			this.rightLeg.x = -7F;
			this.rightLeg.xRot = ((((-(Mth.cos(((var2) * (0.9F)) + (3.1415927F)))) * (0.7F)) * (var3)) * (0.7F)) / (1F);
			this.leftLeg.x = 7F;
			this.leftLeg.xRot = ((((-(Mth.cos(((var2) * (0.9F)) + (3.1415927F)))) * (0.7F)) * (var3)) * (0.7F)) / (1F);
		}
		if ((((var1.isResting()) || (this.riding)) ? (1) : (0)) != (0)){
			this.rightLeg.xRot = (float)(Math.toRadians(-90D));
			this.leftLeg.xRot = (float)(Math.toRadians(-90D));
			this.rightLeg.y = 10.9F;
			this.leftLeg.y = 10.9F;
		}
		if ((((var4) % (300F)) > (0F)) && (((var4) % (300F)) < (100F))){
			this.leftEar.xRot = (0.1F) * ((0.3F) + (Mth.cos((var4) * (0.55F))));
		}
	}
	public void setRotationAngle(ModelPart var1, float var2, float var3, float var4) {
		var1.xRot = var2;
		var1.yRot = var3;
		var1.zRot = var4;
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.head.render(var1,var2,var3,var4,var5);
		this.body.render(var1,var2,var3,var4,var5);
		this.rightLeg.render(var1,var2,var3,var4,var5);
		this.leftLeg.render(var1,var2,var3,var4,var5);
		this.rightArm.render(var1,var2,var3,var4,var5);
		this.leftArm.render(var1,var2,var3,var4,var5);
	}
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.head);
	}
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body,this.rightArm,this.leftArm,this.rightLeg,this.leftLeg);
	}
}