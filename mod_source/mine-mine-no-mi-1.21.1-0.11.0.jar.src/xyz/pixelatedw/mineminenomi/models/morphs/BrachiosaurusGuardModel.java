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

public class BrachiosaurusGuardModel<T extends LivingEntity> extends QuadrupedMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","brachiosaurus_guard"),"main");
	private final ModelPart rightHindLeg2;
	private final ModelPart rightHindLeg3;
	private final ModelPart leftHindLeg2;
	private final ModelPart leftHindLeg3;
	private final ModelPart rightFrontLeg2;
	private final ModelPart rightFrontLeg3;
	private final ModelPart leftFrontLeg2;
	private final ModelPart leftFrontLeg3;
	private final ModelPart tail;
	private final ModelPart tail2;
	private final ModelPart tail3;
	private final ModelPart mouth;
	private final ModelPart upperMouth;
	private final ModelPart upperMouthTeeth;
	private final ModelPart lowerMouth;
	private final ModelPart lowerMouthTeeth;
	private final ModelPart neck;
	private final ModelPart neck2;
	private final ModelPart neck3;
	private final ModelPart neck4;
	private final ModelPart neck5;
	private final ModelPart neck6;
	private final ModelPart neck7;
	private final ModelPart neck8;
	private final ModelPart neck9;

	public BrachiosaurusGuardModel(ModelPart var1) {
		super(var1);
		this.rightHindLeg2 = this.rightHindLeg.getChild("rightHindLeg2");
		this.rightHindLeg3 = this.rightHindLeg.getChild("rightHindLeg3");
		this.leftHindLeg2 = this.leftHindLeg.getChild("leftHindLeg2");
		this.leftHindLeg3 = this.leftHindLeg.getChild("leftHindLeg3");
		this.rightFrontLeg2 = this.rightFrontLeg.getChild("rightFrontLeg2");
		this.rightFrontLeg3 = this.rightFrontLeg.getChild("rightFrontLeg3");
		this.leftFrontLeg2 = this.leftFrontLeg.getChild("leftFrontLeg2");
		this.leftFrontLeg3 = this.leftFrontLeg.getChild("leftFrontLeg3");
		this.tail = this.body.getChild("tail");
		this.tail2 = this.tail.getChild("tail2");
		this.tail3 = this.tail2.getChild("tail3");
		this.mouth = this.head.getChild("mouth");
		this.upperMouth = this.mouth.getChild("upperMouth");
		this.upperMouthTeeth = this.upperMouth.getChild("upperMouthTeeth");
		this.lowerMouth = this.mouth.getChild("lowerMouth");
		this.lowerMouthTeeth = this.lowerMouth.getChild("lowerMouthTeeth");
		this.neck = this.head.getChild("neck");
		this.neck2 = this.neck.getChild("neck2");
		this.neck3 = this.neck.getChild("neck3");
		this.neck4 = this.neck.getChild("neck4");
		this.neck5 = this.neck.getChild("neck5");
		this.neck6 = this.neck.getChild("neck6");
		this.neck7 = this.neck.getChild("neck7");
		this.neck8 = this.neck.getChild("neck8");
		this.neck9 = this.neck.getChild("neck9");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = QuadrupedModel.createBodyMesh(0,CubeDeformation.NONE);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("right_hind_leg",CubeListBuilder.create().texOffs(21,119).addBox(-4F,9.8079F,-2.2404F,4F,4F,5F,new CubeDeformation(-0.01F)),PartPose.offset(-3F,10.3009F,6.3044F));
		var2.addOrReplaceChild("rightHindLeg2",CubeListBuilder.create().texOffs(22,92).addBox(-2F,-3.5F,-3F,4F,7F,6F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(-2F,1F,0F,-0.1745F,0F,0F));
		var2.addOrReplaceChild("rightHindLeg3",CubeListBuilder.create().texOffs(21,106).addBox(-2F,-3.5F,-2.5F,4F,7F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2F,6.9763F,-0.3474F,0.1745F,0F,0F));
		PartDefinition var3 = var1.addOrReplaceChild("left_hind_leg",CubeListBuilder.create().texOffs(21,119).addBox(0F,9.8079F,-2.2404F,4F,4F,5F,new CubeDeformation(-0.01F)),PartPose.offset(3F,10.3009F,6.3044F));
		var3.addOrReplaceChild("leftHindLeg2",CubeListBuilder.create().texOffs(22,92).addBox(-2F,-3.5F,-3F,4F,7F,6F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(2F,1F,0F,-0.1745F,0F,0F));
		var3.addOrReplaceChild("leftHindLeg3",CubeListBuilder.create().texOffs(21,106).addBox(-2F,-3.5F,-2.5F,4F,7F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(2F,6.9763F,-0.3474F,0.1745F,0F,0F));
		PartDefinition var4 = var1.addOrReplaceChild("right_front_leg",CubeListBuilder.create().texOffs(0,118).addBox(-4F,9.9532F,-2.9875F,4F,4F,5F,new CubeDeformation(0.01F)),PartPose.offset(-4F,10.9056F,-7.0765F));
		var4.addOrReplaceChild("rightFrontLeg2",CubeListBuilder.create().texOffs(0,91).addBox(-1.5F,-3.5F,-3F,4F,7F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2.5F,1F,0F,0.1745F,0F,0F));
		var4.addOrReplaceChild("rightFrontLeg3",CubeListBuilder.create().texOffs(0,105).addBox(-2F,-3.5F,-2.5F,4F,7F,5F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(-2F,6.9404F,0.0823F,-0.1745F,0F,0F));
		PartDefinition var5 = var1.addOrReplaceChild("left_front_leg",CubeListBuilder.create().texOffs(0,118).addBox(0F,9.9532F,-2.9875F,4F,4F,5F,new CubeDeformation(0.01F)),PartPose.offset(4F,10.9056F,-7.0765F));
		var5.addOrReplaceChild("leftFrontLeg2",CubeListBuilder.create().texOffs(0,91).addBox(-1.5F,-3.5F,-3F,4F,7F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(1.5F,1F,0F,0.1745F,0F,0F));
		var5.addOrReplaceChild("leftFrontLeg3",CubeListBuilder.create().texOffs(0,105).addBox(-2F,-3.5F,-2.5F,4F,7F,5F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(2F,6.9404F,0.0823F,-0.1745F,0F,0F));
		var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,0).addBox(-5F,-8.375F,-4F,10F,17F,14F,new CubeDeformation(0F)).texOffs(0,32).addBox(-4.5F,-7.375F,-7F,9F,15F,3F,new CubeDeformation(0F)).texOffs(0,51).addBox(-4F,-6.875F,-9F,8F,13F,2F,new CubeDeformation(0F)).texOffs(50,0).addBox(-4.5F,-7.375F,9F,9F,15F,3F,new CubeDeformation(0F)).texOffs(50,19).addBox(-4F,-6.875F,12F,8F,13F,3F,new CubeDeformation(0F)).texOffs(28,33).addBox(-3.5F,-6.375F,15F,7F,12F,3F,new CubeDeformation(0F)),PartPose.offset(0F,7.375F,-4F)).addOrReplaceChild("tail",CubeListBuilder.create().texOffs(77,1).addBox(-2F,-3F,-1F,4F,6F,10F,new CubeDeformation(0F)),PartPose.offset(0F,0F,18F)).addOrReplaceChild("tail2",CubeListBuilder.create().texOffs(74,18).addBox(-1F,-2F,0F,3F,5F,14F,new CubeDeformation(0F)),PartPose.offset(-0.5F,-0.75F,6F)).addOrReplaceChild("tail3",CubeListBuilder.create().texOffs(72,38).addBox(0F,-1F,0F,2F,4F,15F,new CubeDeformation(0F)),PartPose.offset(-0.5F,-0.75F,8F));
		PartDefinition var6 = var1.addOrReplaceChild("head",CubeListBuilder.create(),PartPose.offset(0F,4F,-11F));
		PartDefinition var7 = var6.addOrReplaceChild("mouth",CubeListBuilder.create(),PartPose.offset(-1F,-109F,-14F));
		var7.addOrReplaceChild("upperMouth",CubeListBuilder.create().texOffs(68,100).addBox(-2.5F,-3.0999F,-2.6049F,5F,4F,3F,new CubeDeformation(0F)).texOffs(68,108).addBox(-2F,-2.0999F,-3.6049F,4F,3F,1F,new CubeDeformation(0F)).texOffs(68,113).addBox(-1.5F,-1.0999F,-5.6049F,3F,2F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(1F,86.1616F,4.9651F,-0.1309F,0F,0F)).addOrReplaceChild("upperMouthTeeth",CubeListBuilder.create().texOffs(68,118).addBox(2.25F,79.8469F,18.1993F,0F,1F,5F,new CubeDeformation(0F)).texOffs(68,118).addBox(-0.25F,79.8469F,18.1993F,0F,1F,5F,new CubeDeformation(0F)).texOffs(72,126).addBox(0F,79.8469F,18.1993F,2F,1F,0F,new CubeDeformation(0F)),PartPose.offset(-1F,-79.1968F,-23.5542F));
		var7.addOrReplaceChild("lowerMouth",CubeListBuilder.create().texOffs(85,104).addBox(-2.5F,0.0434F,-1.3357F,5F,1F,2F,new CubeDeformation(0F)).texOffs(85,109).addBox(-2F,0.0434F,-2.3357F,4F,1F,1F,new CubeDeformation(0F)).texOffs(84,112).addBox(-1.5F,0.0434F,-5.3357F,3F,1F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(1F,87.2041F,4.72F,0.2182F,0F,0F)).addOrReplaceChild("lowerMouthTeeth",CubeListBuilder.create().texOffs(84,118).addBox(6.3F,139.9865F,14.3257F,0F,1F,5F,new CubeDeformation(0F)).texOffs(84,118).addBox(3.7F,139.9865F,14.3257F,0F,1F,5F,new CubeDeformation(0F)).texOffs(87,126).addBox(4F,139.9865F,14.3257F,2F,1F,0F,new CubeDeformation(0F)),PartPose.offset(-5F,-140.6931F,-19.4114F));
		PartDefinition var8 = var6.addOrReplaceChild("neck",CubeListBuilder.create(),PartPose.offset(-0.5F,-1.7431F,-0.7119F));
		var8.addOrReplaceChild("neck2",CubeListBuilder.create().texOffs(45,112).addBox(-2F,-4F,-2.5F,5F,10F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1.5359F,-2F,0.5236F,0F,0F));
		var8.addOrReplaceChild("neck3",CubeListBuilder.create().texOffs(45,100).addBox(-2F,-1.25F,-1.75F,5F,6F,5F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0F,-8.4641F,-6F,0.3491F,0F,0F));
		var8.addOrReplaceChild("neck4",CubeListBuilder.create().texOffs(45,90).addBox(-2F,-1F,-2.5F,5F,4F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-11.8793F,-5.6723F,0.0436F,0F,0F));
		var8.addOrReplaceChild("neck5",CubeListBuilder.create().texOffs(45,76).addBox(-2F,-1.2296F,-3.6273F,5F,7F,5F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,-16.3313F,-2.5418F,-0.4363F,0F,0F));
		var8.addOrReplaceChild("neck6",CubeListBuilder.create().texOffs(46,77).addBox(-2.5F,-2.5F,-1.4F,5F,4F,4F,new CubeDeformation(-0.01F)),PartPose.offset(0.5F,-18.0554F,-3.3864F));
		var8.addOrReplaceChild("neck7",CubeListBuilder.create().texOffs(45,67).addBox(-2.5F,-1.5F,-2.5F,5F,3F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.5F,-20.3554F,-3.6864F,0.5672F,0F,0F));
		var8.addOrReplaceChild("neck8",CubeListBuilder.create().texOffs(45,49).addBox(-2.5F,-1.5F,-2.5F,5F,3F,5F,new CubeDeformation(0.017F)),PartPose.offsetAndRotation(0.5F,-21.3674F,-4.847F,1.1344F,0F,0F));
		var8.addOrReplaceChild("neck9",CubeListBuilder.create().texOffs(45,58).addBox(-2.5F,-1.5F,-2.5F,5F,3F,5F,new CubeDeformation(0.015F)),PartPose.offsetAndRotation(0.5F,-21.7145F,-6.6078F,1.6144F,0F,0F));
		return LayerDefinition.create(var0,128,128);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		float var7 = 0.3F;
		if (var1.isSprinting()){
			var7 = 0.4F;
		}
		this.rightFrontLeg.xRot = ((Mth.cos((var2) * (var7))) * (0.3F)) * (var3);
		this.leftFrontLeg.xRot = ((Mth.cos((var2) * (var7))) * (0.4F)) * (var3);
		this.rightHindLeg.xRot = ((Mth.cos(((var2) * (var7)) + (3.1415927F))) * (0.3F)) * (var3);
		this.leftHindLeg.xRot = ((Mth.cos(((var2) * (var7)) + (3.1415927F))) * (0.4F)) * (var3);
		ModelPart var8 = this.tail;
		var8.yRot = (float)(((double)(var8.yRot)) + ((Math.sin(((double)(var4)) * (0.01D))) / (20D)));
		ModelPart var9 = this.tail;
		var9.xRot = (float)(((double)(var9.xRot)) + ((Math.sin(((double)(var4)) * (0.005D))) / (10D)));
		ModelPart var10 = this.tail2;
		var10.yRot = (float)(((double)(var10.yRot)) + ((Math.sin(((double)(var4)) * (0.01D))) / (10D)));
		ModelPart var11 = this.tail2;
		var11.xRot = (float)(((double)(var11.xRot)) + ((Math.sin(((double)(var4)) * (0.005D))) / (5D)));
		ModelPart var12 = this.tail3;
		var12.yRot = (float)(((double)(var12.yRot)) + ((Math.sin(((double)(var4)) * (0.01D))) / (10D)));
		ModelPart var13 = this.tail3;
		var13.xRot = (float)(((double)(var13.xRot)) + ((Math.sin(((double)(var4)) * (0.005D))) / (5D)));
		this.setupAttackAnimation(var1,var4);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.rightHindLeg.render(var1,var2,var3,var4,var5);
		this.leftHindLeg.render(var1,var2,var3,var4,var5);
		this.rightFrontLeg.render(var1,var2,var3,var4,var5);
		this.leftFrontLeg.render(var1,var2,var3,var4,var5);
		this.body.render(var1,var2,var3,var4,var5);
		this.head.render(var1,var2,var3,var4,var5);
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		this.head.translateAndRotate(var2);
		var2.mulPose(Axis.YP.rotationDegrees(-90F));
		var2.mulPose(Axis.ZP.rotationDegrees(90F));
		var2.translate(-1.3D,0.25D,-0.05D);
	}
	public ModelPart getHead() {
		return this.head;
	}
}