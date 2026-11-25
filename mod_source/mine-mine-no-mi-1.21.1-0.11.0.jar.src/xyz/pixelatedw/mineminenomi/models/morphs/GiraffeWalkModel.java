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

public class GiraffeWalkModel<T extends LivingEntity> extends QuadrupedMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","giraffe_walk"),"main");
	private final ModelPart tail1;
	private final ModelPart hunch;
	private final ModelPart neck2;
	private final ModelPart mane2;
	private final ModelPart face;
	private final ModelPart face2;
	private final ModelPart rightHorn;
	private final ModelPart leftHorn;
	private final ModelPart rightEar;
	private final ModelPart leftEar;
	private final ModelPart mane;
	private final ModelPart leftLowerFrontLeg;
	private final ModelPart leftFrontHoof1;
	private final ModelPart leftFrontHoof2;
	private final ModelPart rightLowerFrontLeg;
	private final ModelPart rightFrontHoof1;
	private final ModelPart rightFrontHoof2;
	private final ModelPart leftLowerHindLeg;
	private final ModelPart leftRearHoof1;
	private final ModelPart leftRearHoof2;
	private final ModelPart rightLowerHindLeg;
	private final ModelPart rightRearHoof1;
	private final ModelPart rightRearHoof2;

	public GiraffeWalkModel(ModelPart var1) {
		super(var1);
		this.tail1 = this.body.getChild("tail1");
		this.hunch = this.body.getChild("hunch");
		this.neck2 = this.head.getChild("neck2");
		this.mane2 = this.neck2.getChild("mane2");
		this.face = this.neck2.getChild("face");
		this.face2 = this.face.getChild("face2");
		this.rightHorn = this.face2.getChild("rightHorn");
		this.leftHorn = this.face2.getChild("leftHorn");
		this.rightEar = this.face2.getChild("rightEar");
		this.leftEar = this.face2.getChild("leftEar");
		this.mane = this.head.getChild("mane");
		this.leftLowerFrontLeg = this.leftFrontLeg.getChild("leftLowerFrontLeg");
		this.leftFrontHoof1 = this.leftLowerFrontLeg.getChild("leftFrontHoof1");
		this.leftFrontHoof2 = this.leftLowerFrontLeg.getChild("leftFrontHoof2");
		this.rightLowerFrontLeg = this.rightFrontLeg.getChild("rightLowerFrontLeg");
		this.rightFrontHoof1 = this.rightLowerFrontLeg.getChild("rightFrontHoof1");
		this.rightFrontHoof2 = this.rightLowerFrontLeg.getChild("rightFrontHoof2");
		this.leftLowerHindLeg = this.leftHindLeg.getChild("leftLowerHindLeg");
		this.leftRearHoof1 = this.leftLowerHindLeg.getChild("leftRearHoof1");
		this.leftRearHoof2 = this.leftLowerHindLeg.getChild("leftRearHoof2");
		this.rightLowerHindLeg = this.rightHindLeg.getChild("rightLowerHindLeg");
		this.rightRearHoof1 = this.rightLowerHindLeg.getChild("rightRearHoof1");
		this.rightRearHoof2 = this.rightLowerHindLeg.getChild("rightRearHoof2");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = QuadrupedMorphModel.createBodyMesh(0,CubeDeformation.NONE);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(18,18).addBox(-6F,-10.4346F,-6.7471F,10F,16F,9F,new CubeDeformation(0F)),PartPose.offsetAndRotation(1F,7F,3F,1.4835F,0F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("tail1",CubeListBuilder.create().texOffs(58,28).addBox(-0.5F,0.4821F,-0.5745F,1F,5F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-1F,4.8247F,1.0142F,-1.0472F,0F,0F));
		PartDefinition var4 = var2.addOrReplaceChild("hunch",CubeListBuilder.create().texOffs(32,45).addBox(0.02F,0.5745F,-0.4821F,10F,9F,6F,new CubeDeformation(-0.02F)),PartPose.offsetAndRotation(-6.02F,-10.75F,2.25F,-0.5236F,0F,0F));
		PartDefinition var5 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).addBox(-2F,-11.9506F,-3.0052F,4F,11F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,3.75F,-3.25F,0.7418F,0F,0F));
		PartDefinition var6 = var5.addOrReplaceChild("neck2",CubeListBuilder.create().texOffs(0,0).mirror().addBox(-2F,-11.8809F,-2.2082F,4F,11F,4F,new CubeDeformation(-0.01F)).mirror(false),PartPose.offsetAndRotation(0F,-10.3F,-1.05F,-0.1309F,0F,0F));
		PartDefinition var7 = var6.addOrReplaceChild("mane2",CubeListBuilder.create().texOffs(14,17).addBox(0F,-6.75F,-0.5F,0F,12F,1F,new CubeDeformation(0.01F)),PartPose.offset(0F,-6.381F,2.2919F));
		PartDefinition var8 = var6.addOrReplaceChild("face",CubeListBuilder.create().texOffs(17,0).addBox(-4F,-3.25F,-6F,4F,3F,7F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(2F,-9.6613F,-1.1778F,-0.6109F,0F,0F)).addOrReplaceChild("face2",CubeListBuilder.create().texOffs(18,11).addBox(0F,0.75F,0F,4F,2F,4F,new CubeDeformation(0.01F)),PartPose.offset(-4F,-6F,-3F));
		PartDefinition var9 = var8.addOrReplaceChild("rightHorn",CubeListBuilder.create().texOffs(49,19).addBox(-0.5F,-1.25F,-0.5F,1F,2F,1F,new CubeDeformation(0.01F)),PartPose.offset(0.75F,0F,3F));
		PartDefinition var10 = var8.addOrReplaceChild("leftHorn",CubeListBuilder.create().texOffs(49,19).addBox(-0.5F,-1.25F,-0.5F,1F,2F,1F,new CubeDeformation(0.01F)),PartPose.offset(3.25F,0F,3F));
		PartDefinition var11 = var8.addOrReplaceChild("rightEar",CubeListBuilder.create().texOffs(49,23).addBox(-2.099F,-0.0129F,0F,3F,2F,0F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(-0.25F,1.25F,3F,0F,0F,0.2618F));
		PartDefinition var12 = var8.addOrReplaceChild("leftEar",CubeListBuilder.create().texOffs(49,23).mirror().addBox(-0.4183F,-0.3709F,0F,3F,2F,0F,new CubeDeformation(0.01F)).mirror(false),PartPose.offsetAndRotation(4F,1.7F,3F,0F,0F,-0.2618F));
		PartDefinition var13 = var5.addOrReplaceChild("mane",CubeListBuilder.create().texOffs(14,17).addBox(0F,-5.5F,-0.5F,0F,11F,1F,new CubeDeformation(0F)),PartPose.offset(0F,-6.4507F,1.4949F));
		PartDefinition var14 = var1.addOrReplaceChild("left_front_leg",CubeListBuilder.create().texOffs(0,34).addBox(-1F,0.6375F,-0.8567F,2F,6F,2F,new CubeDeformation(0.2F)),PartPose.offsetAndRotation(3.5F,11.5F,-6F,-0.1745F,0F,0F)).addOrReplaceChild("leftLowerFrontLeg",CubeListBuilder.create().texOffs(9,34).addBox(-1F,0F,-1F,2F,6F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,6.6375F,0.1433F,0.1745F,0F,0F));
		PartDefinition var15 = var14.addOrReplaceChild("leftFrontHoof1",CubeListBuilder.create().texOffs(54,19).addBox(0F,0F,0F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,4F,-1.5F,-0.1211F,-0.4883F,-0.0394F));
		PartDefinition var16 = var14.addOrReplaceChild("leftFrontHoof2",CubeListBuilder.create().texOffs(54,19).addBox(0F,0F,0F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-1F,4F,-1F,-0.1211F,0.4883F,0.0394F));
		PartDefinition var17 = var1.addOrReplaceChild("right_front_leg",CubeListBuilder.create().texOffs(0,34).mirror().addBox(-1F,0.6375F,-0.8567F,2F,6F,2F,new CubeDeformation(0.2F)).mirror(false),PartPose.offsetAndRotation(-3.5F,11.5F,-6F,-0.1745F,0F,0F)).addOrReplaceChild("rightLowerFrontLeg",CubeListBuilder.create().texOffs(9,34).mirror().addBox(-1F,0F,-1F,2F,6F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0F,6.6375F,0.1433F,0.1745F,0F,0F));
		PartDefinition var18 = var17.addOrReplaceChild("rightFrontHoof1",CubeListBuilder.create().texOffs(54,19).mirror().addBox(-1F,0F,0F,1F,2F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0F,4F,-1.5F,-0.1211F,0.4883F,0.0394F));
		PartDefinition var19 = var17.addOrReplaceChild("rightFrontHoof2",CubeListBuilder.create().texOffs(54,19).mirror().addBox(-1F,0F,0F,1F,2F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(1F,4F,-1F,-0.1211F,-0.4883F,-0.0394F));
		PartDefinition var20 = var1.addOrReplaceChild("left_hind_leg",CubeListBuilder.create().texOffs(0,34).addBox(-1F,0.2557F,-1.1307F,2F,5F,2F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(3.5F,13.5F,6.25F,-0.0873F,0F,0F)).addOrReplaceChild("leftLowerHindLeg",CubeListBuilder.create().texOffs(9,34).addBox(-1F,-1F,-1F,2F,6F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,5.5057F,-0.1307F,0.0873F,0F,0F));
		PartDefinition var21 = var20.addOrReplaceChild("leftRearHoof1",CubeListBuilder.create().texOffs(54,19).addBox(0F,0F,0F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,3F,-1.5F,-0.1211F,-0.4883F,-0.0394F));
		PartDefinition var22 = var20.addOrReplaceChild("leftRearHoof2",CubeListBuilder.create().texOffs(54,19).addBox(0F,0F,0F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-1F,3F,-1F,-0.1211F,0.4883F,0.0394F));
		PartDefinition var23 = var1.addOrReplaceChild("right_hind_leg",CubeListBuilder.create().texOffs(0,34).mirror().addBox(-1F,0.2557F,-1.1307F,2F,5F,2F,new CubeDeformation(0.25F)).mirror(false),PartPose.offsetAndRotation(-3.5F,13.5F,6.25F,-0.0873F,0F,0F)).addOrReplaceChild("rightLowerHindLeg",CubeListBuilder.create().texOffs(9,34).mirror().addBox(-1F,-1F,-1F,2F,6F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0F,5.5057F,-0.1307F,0.0873F,0F,0F));
		PartDefinition var24 = var23.addOrReplaceChild("rightRearHoof1",CubeListBuilder.create().texOffs(54,19).mirror().addBox(-1F,0F,0F,1F,2F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0F,3F,-1.5F,-0.1211F,0.4883F,0.0394F));
		PartDefinition var25 = var23.addOrReplaceChild("rightRearHoof2",CubeListBuilder.create().texOffs(54,19).mirror().addBox(-1F,0F,0F,1F,2F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(1F,3F,-1F,-0.1211F,-0.4883F,-0.0394F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		this.rightFrontLeg.xRot = (-0.15F) + (((Mth.cos((var2) * (0.6662F))) * (0.3F)) * (var3));
		this.leftFrontLeg.xRot = (-0.15F) + (((Mth.cos((var2) * (0.6662F))) * (0.4F)) * (var3));
		this.rightHindLeg.xRot = (-0.1F) + (((Mth.cos(((var2) * (0.6662F)) + (3.1415927F))) * (0.3F)) * (var3));
		this.leftHindLeg.xRot = (-0.1F) + (((Mth.cos(((var2) * (0.6662F)) + (3.1415927F))) * (0.4F)) * (var3));
		if (var1.isSprinting()){
			this.tail1.xRot = (0.2F) + (((Mth.cos((var2) * (0.6662F))) * (0.2F)) * (var3));
			this.leftEar.yRot = (-0.3F) - (((Mth.cos((var2) * (0.6662F))) * (0.2F)) * (var3));
			this.rightEar.yRot = (0.3F) + (((Mth.cos((var2) * (0.6662F))) * (0.2F)) * (var3));
		}
		this.setupAttackAnimation(var1,var6);
		if (var1.isCrouching()){
			this.head.xRot = 0.9F;
			this.head.z = -1F;
			this.head.y = 9F;
			ModelPart var7 = this.head;
			var7.xScale = (var7.xScale) + (0.2F);
			ModelPart var8 = this.head;
			var8.yScale = (var8.yScale) - (0.3F);
			ModelPart var9 = this.head;
			var9.zScale = (var9.zScale) + (0.2F);
			this.neck2.z = -1.2F;
			this.neck2.y = -7F;
			ModelPart var10 = this.face;
			var10.xScale = (var10.xScale) + (0.2F);
			ModelPart var11 = this.face;
			var11.yScale = (var11.yScale) + (0.3F);
			ModelPart var12 = this.face;
			var12.zScale = (var12.zScale) + (0.2F);
			this.body.y = 11F;
			this.rightFrontLeg.y = 13F;
			this.rightLowerFrontLeg.y = 5F;
			this.leftFrontLeg.y = 13F;
			this.leftLowerFrontLeg.y = 5F;
			this.leftHindLeg.y = 14F;
			this.leftLowerHindLeg.y = 5F;
			this.rightHindLeg.y = 14F;
			this.rightLowerHindLeg.y = 5F;
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		super.renderToBuffer(var1,var2,var3,var4,var5);
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		this.head.translateAndRotate(var2);
		var2.mulPose(Axis.ZP.rotationDegrees(90F));
		var2.mulPose(Axis.YP.rotationDegrees(50F));
		var2.mulPose(Axis.XP.rotationDegrees(270F));
		var2.translate(-0.7D,0.8D,0D);
	}
	public ModelPart getHead() {
		return this.head;
	}
}