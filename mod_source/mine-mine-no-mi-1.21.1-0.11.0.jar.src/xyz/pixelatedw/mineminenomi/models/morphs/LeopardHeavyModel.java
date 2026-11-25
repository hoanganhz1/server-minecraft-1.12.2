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
import xyz.pixelatedw.mineminenomi.api.morph.HumanoidMorphModel;

public class LeopardHeavyModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","leopard_heavy"),"main");
	private final ModelPart nose;
	private final ModelPart leftEye;
	private final ModelPart rightEye;
	private final ModelPart leftEar;
	private final ModelPart rightEar;
	private final ModelPart tail;
	private final ModelPart tail2;
	private final ModelPart tail3;
	private final ModelPart lowerBody;
	private final ModelPart upperBody;
	private final ModelPart leftLeg2;
	private final ModelPart leftLeg3;
	private final ModelPart leftFoot;
	private final ModelPart rightLeg2;
	private final ModelPart rightLeg3;
	private final ModelPart rightFoot;
	private final ModelPart lowerLeftArm;
	private final ModelPart lowerRightArm;

	public LeopardHeavyModel(ModelPart var1) {
		super(var1);
		this.nose = this.head.getChild("nose");
		this.leftEye = this.head.getChild("leftEye");
		this.rightEye = this.head.getChild("rightEye");
		this.leftEar = this.head.getChild("leftEar");
		this.rightEar = this.head.getChild("rightEar");
		this.upperBody = this.body.getChild("upperBody");
		this.lowerBody = this.body.getChild("lowerBody");
		this.tail = this.lowerBody.getChild("tail");
		this.tail2 = this.tail.getChild("tail2");
		this.tail3 = this.tail2.getChild("tail3");
		this.leftLeg2 = this.leftLeg.getChild("leftLeg2");
		this.leftLeg3 = this.leftLeg2.getChild("leftLeg3");
		this.leftFoot = this.leftLeg3.getChild("leftFoot");
		this.rightLeg2 = this.rightLeg.getChild("rightLeg2");
		this.rightLeg3 = this.rightLeg2.getChild("rightLeg3");
		this.rightFoot = this.rightLeg3.getChild("rightFoot");
		this.lowerLeftArm = this.leftArm.getChild("lowerLeftArm");
		this.lowerRightArm = this.rightArm.getChild("lowerRightArm");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidMorphModel.createMesh(CubeDeformation.NONE,0F);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(72,0).addBox(-3F,-6.8788F,-6.2998F,6F,6F,6F,new CubeDeformation(0.55F)),PartPose.offset(0F,-6F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("nose",CubeListBuilder.create().texOffs(81,13).addBox(-0.5F,0F,-0.8F,1F,1F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-3.8038F,-6.2373F,-0.2618F,0F,0F));
		PartDefinition var4 = var2.addOrReplaceChild("leftEye",CubeListBuilder.create().texOffs(80,28).mirror().addBox(-0.975F,-1.5F,0.015F,3F,2F,0F,new CubeDeformation(0F)).mirror(false),PartPose.offset(1.775F,-4.4788F,-6.885F));
		PartDefinition var5 = var2.addOrReplaceChild("rightEye",CubeListBuilder.create().texOffs(80,28).addBox(-2.1F,-1.5F,0.015F,3F,2F,0F,new CubeDeformation(0F)),PartPose.offset(-1.7F,-4.4788F,-6.885F));
		PartDefinition var6 = var2.addOrReplaceChild("leftEar",CubeListBuilder.create().texOffs(80,23).addBox(-1.0317F,-1.1108F,-0.5993F,3F,3F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(2.6725F,-7.6099F,-2.8633F,0.2004F,-0.562F,0.0775F));
		PartDefinition var7 = var2.addOrReplaceChild("rightEar",CubeListBuilder.create().texOffs(80,18).addBox(-1.9683F,-1.1108F,-0.5993F,3F,3F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2.6225F,-7.6099F,-2.8633F,0.2004F,0.562F,-0.0775F));
		PartDefinition var8 = var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,95).addBox(-6.5F,-13.0097F,-4.0767F,13F,14F,7F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,4F,3F,0.1396F,0F,0F));
		PartDefinition var9 = var8.addOrReplaceChild("upperBody",CubeListBuilder.create().texOffs(0,75).addBox(-7.5F,-5.8931F,-4.8572F,15F,12F,7F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-7.75F,-0.75F,0.0873F,0F,0F));
		PartDefinition var10 = var8.addOrReplaceChild("lowerBody",CubeListBuilder.create().texOffs(0,117).addBox(-5.5F,-4F,-2.375F,11F,5F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,4F,-1.5F,-0.0873F,0F,0F)).addOrReplaceChild("tail",CubeListBuilder.create().texOffs(99,25).addBox(-1F,-1F,-1F,2F,2F,12F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1F,3.4375F,-0.0524F,0F,0F)).addOrReplaceChild("tail2",CubeListBuilder.create().texOffs(103,13).addBox(-1F,-1F,0F,2F,2F,9F,new CubeDeformation(0.001F)),PartPose.offset(0F,0F,10.5F)).addOrReplaceChild("tail3",CubeListBuilder.create().texOffs(102,0).addBox(-1F,-1F,0F,2F,2F,10F,new CubeDeformation(0F)),PartPose.offset(0F,0F,8.5F));
		PartDefinition var11 = var1.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(3,0).addBox(-1.5F,-2.0282F,-3.39F,4F,8F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(3.5F,9F,2F,-0.3491F,0F,0F)).addOrReplaceChild("leftLeg2",CubeListBuilder.create(),PartPose.offsetAndRotation(0F,4.7592F,-2.3129F,-0.0349F,0F,0F));
		PartDefinition var12 = var11.addOrReplaceChild("lowerleftleg1_r1",CubeListBuilder.create().texOffs(4,14).addBox(2.5F,-1.5F,-2.5F,3F,3F,5F,new CubeDeformation(0.2F)),PartPose.offsetAndRotation(-3.5F,-0.2936F,2.5072F,-0.1309F,0F,0F));
		PartDefinition var13 = var11.addOrReplaceChild("leftLeg3",CubeListBuilder.create().texOffs(7,23).addBox(-1F,-0.5005F,-0.9999F,2F,9F,3F,new CubeDeformation(0.2F)),PartPose.offsetAndRotation(0.5F,0.5156F,3.7208F,0.5236F,0F,0F)).addOrReplaceChild("leftFoot",CubeListBuilder.create().texOffs(0,36).addBox(-2.0625F,-0.3125F,-6F,4F,2F,8F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.0625F,7.7493F,-0.4146F,-0.1309F,0F,0F));
		PartDefinition var14 = var1.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(3,0).mirror().addBox(-2.5F,-2.0282F,-3.39F,4F,8F,5F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-3.5F,9F,2F,-0.3491F,0F,0F)).addOrReplaceChild("rightLeg2",CubeListBuilder.create(),PartPose.offsetAndRotation(0F,4.7592F,-2.3129F,-0.0349F,0F,0F));
		PartDefinition var15 = var14.addOrReplaceChild("lowerrightleg_r1",CubeListBuilder.create().texOffs(4,14).mirror().addBox(-5.5F,-1.5F,-2.5F,3F,3F,5F,new CubeDeformation(0.2F)).mirror(false),PartPose.offsetAndRotation(3.5F,-0.2936F,2.5072F,-0.1309F,0F,0F));
		PartDefinition var16 = var14.addOrReplaceChild("rightLeg3",CubeListBuilder.create().texOffs(7,23).mirror().addBox(-1F,-0.5005F,-0.9999F,2F,9F,3F,new CubeDeformation(0.2F)).mirror(false),PartPose.offsetAndRotation(-0.5F,0.5156F,3.7208F,0.5236F,0F,0F)).addOrReplaceChild("rightFoot",CubeListBuilder.create().texOffs(0,36).mirror().addBox(-1.9375F,-0.3125F,-6F,4F,2F,8F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-0.0625F,7.7493F,-0.4146F,-0.1309F,0F,0F));
		PartDefinition var17 = var1.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(25,0).addBox(-1F,-1.5955F,-3.463F,6F,10F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(8F,-6F,0F,0.1745F,0F,0F)).addOrReplaceChild("lowerLeftArm",CubeListBuilder.create().texOffs(27,17).addBox(-2.5F,-0.3074F,-2.2925F,5F,11F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(2F,7.1589F,-0.3361F,-0.3054F,0F,0F));
		PartDefinition var18 = var1.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(25,0).mirror().addBox(-5F,-1.5955F,-3.463F,6F,10F,6F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-8F,-6F,0F,0.1745F,0F,0F)).addOrReplaceChild("lowerRightArm",CubeListBuilder.create().texOffs(27,17).mirror().addBox(-2.5F,-0.3074F,-2.2925F,5F,11F,5F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-2F,7.1589F,-0.3361F,-0.3054F,0F,0F));
		return LayerDefinition.create(var0,128,128);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		this.setupHeadRotation(var1,var6,var5);
		float var7 = 1F;
		this.rightArm.xRot = ((((Mth.cos(((var2) * (0.6662F)) + (3.1415927F))) * (0.8F)) * (var3)) * (0.5F)) / (var7);
		this.leftArm.xRot = ((((Mth.cos((var2) * (0.6662F))) * (0.8F)) * (var3)) * (0.5F)) / (var7);
		this.rightLeg.xRot = (-0.34F) + ((((Mth.cos((var2) * (0.6662F))) * (0.7F)) * (var3)) / (var7));
		this.leftLeg.xRot = (-0.34F) + ((((Mth.cos(((var2) * (0.6662F)) + (3.1415927F))) * (0.7F)) * (var3)) / (var7));
		ModelPart var8 = this.tail;
		var8.yRot = (float)(((double)(var8.yRot)) + ((Math.sin(((double)(var4)) * (0.06D))) / (5D)));
		ModelPart var9 = this.tail;
		var9.xRot = (float)(((double)(var9.xRot)) + ((Math.sin(((double)(var4)) * (0.06D))) / (5D)));
		ModelPart var10 = this.tail2;
		var10.yRot = (float)(((double)(var10.yRot)) + ((Math.cos(((double)(var4)) * (0.06D))) / (7D)));
		ModelPart var11 = this.tail2;
		var11.xRot = (float)(((double)(var11.xRot)) + ((Math.cos(((double)(var4)) * (0.06D))) / (2D)));
		ModelPart var12 = this.tail3;
		var12.yRot = (float)(((double)(var12.yRot)) + ((Math.sin(((double)(var4)) * (0.06D))) / (7D)));
		ModelPart var13 = this.tail3;
		var13.xRot = (float)(((double)(var13.xRot)) + ((Math.sin(((double)(var4)) * (0.06D))) / (2D)));
		this.setupAttackAnimation(var1,var4,3F);
		if (var1.isCrouching()){
			this.head.z = -5F;
			this.head.y = -1F;
			this.body.xRot = 0.2F;
			this.body.y = 7F;
			this.body.z = 1F;
			ModelPart var14 = this.rightArm;
			var14.xRot = (var14.xRot) + (0.4F);
			this.rightArm.z = -4F;
			this.rightArm.y = -3F;
			ModelPart var15 = this.leftArm;
			var15.xRot = (var15.xRot) + (0.4F);
			this.leftArm.z = -4F;
			this.leftArm.y = -3F;
			this.rightLeg.y = 13F;
			this.leftLeg.y = 13F;
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.head.render(var1,var2,var3,var4,var5);
		this.body.render(var1,var2,var3,var4,var5);
		this.leftLeg.render(var1,var2,var3,var4,var5);
		this.rightLeg.render(var1,var2,var3,var4,var5);
		this.leftArm.render(var1,var2,var3,var4,var5);
		this.rightArm.render(var1,var2,var3,var4,var5);
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		super.translateToHand(var1,var2);
		var2.translate((-0.05D) * ((double)(((var1) == (HumanoidArm.RIGHT)) ? (1F) : (-1F))),0.5D,-0.25D);
	}
	public boolean renderFirstPersonArm(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5, HumanoidArm var6, boolean var7) {
		float var8 = ((var6) == (HumanoidArm.RIGHT)) ? (1F) : (-1F);
		var1.translate((0.3D) * ((double)(var8)),0.3D,0.2D);
		var1.mulPose(Axis.XP.rotationDegrees(20F));
		if (var7){
			var1.translate((-0.1D) * ((double)(var8)),0D,-0.5D);
		}
		return super.renderFirstPersonArm(var1,var2,var3,var4,var5,var6,var7);
	}
}