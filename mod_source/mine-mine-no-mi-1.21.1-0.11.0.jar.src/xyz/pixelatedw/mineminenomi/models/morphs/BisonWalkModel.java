package xyz.pixelatedw.mineminenomi.models.morphs;

import com.google.common.collect.ImmutableList;
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

public class BisonWalkModel<T extends LivingEntity> extends QuadrupedMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","bison_walk"),"main");
	private final ModelPart snout;
	private final ModelPart leftHorn;
	private final ModelPart leftHorn2;
	private final ModelPart rightHorn;
	private final ModelPart rightHorn2;
	private final ModelPart leftFrontLegLower;
	private final ModelPart rightFrontLegLower;
	private final ModelPart hump;
	private final ModelPart backBody;
	private final ModelPart tail;

	public BisonWalkModel(ModelPart var1) {
		super(var1);
		this.snout = this.head.getChild("snout");
		this.leftHorn = this.head.getChild("leftHorn");
		this.leftHorn2 = this.leftHorn.getChild("leftHorn2");
		this.rightHorn = this.head.getChild("rightHorn");
		this.rightHorn2 = this.rightHorn.getChild("rightHorn2");
		this.leftFrontLegLower = this.leftFrontLeg.getChild("leftFrontLegLower");
		this.rightFrontLegLower = this.rightFrontLeg.getChild("rightFrontLegLower");
		this.hump = this.body.getChild("hump");
		this.backBody = this.body.getChild("backBody");
		this.tail = this.backBody.getChild("tail");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = QuadrupedModel.createBodyMesh(0,new CubeDeformation(0F));
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(13,29).addBox(-2.5F,-2.5F,-5F,5F,5F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,12.0486F,-8.5856F,0.2618F,0F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("snout",CubeListBuilder.create(),PartPose.offsetAndRotation(0F,-0.0862F,-4.3249F,0.9163F,0F,0F)).addOrReplaceChild("snout1_r1",CubeListBuilder.create().texOffs(11,40).addBox(-2F,-2F,-2.55F,4F,4F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,-1F,-0.1309F,0F,0F));
		PartDefinition var4 = var2.addOrReplaceChild("leftHorn",CubeListBuilder.create(),PartPose.offsetAndRotation(2.2197F,-2.1335F,-1.6514F,-0.0457F,-0.3051F,-0.6844F));
		PartDefinition var5 = var4.addOrReplaceChild("lefthorn1_r1",CubeListBuilder.create().texOffs(34,5).addBox(-0.5F,-1F,-0.5F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.5F,0F,0F,0F,0F,-1.5708F));
		PartDefinition var6 = var4.addOrReplaceChild("leftHorn2",CubeListBuilder.create().texOffs(34,5).addBox(-0.49F,-2.115F,-0.51F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offset(0.99F,-0.385F,0.01F));
		PartDefinition var7 = var2.addOrReplaceChild("rightHorn",CubeListBuilder.create(),PartPose.offsetAndRotation(-2.2197F,-2.1335F,-1.6514F,-0.0457F,0.3051F,0.6844F));
		PartDefinition var8 = var7.addOrReplaceChild("righthorn_r1",CubeListBuilder.create().texOffs(34,5).mirror().addBox(-0.5F,-1F,-0.5F,1F,2F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-0.5F,0F,0F,0F,0F,1.5708F));
		PartDefinition var9 = var7.addOrReplaceChild("rightHorn2",CubeListBuilder.create().texOffs(34,5).mirror().addBox(-0.51F,-2.115F,-0.51F,1F,2F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offset(-0.99F,-0.385F,0.01F));
		PartDefinition var10 = var1.addOrReplaceChild("left_front_leg",CubeListBuilder.create().texOffs(0,29).addBox(-1.5F,-3F,-1.5F,3F,6F,3F,new CubeDeformation(0F)),PartPose.offset(2.75F,17F,-6.0625F)).addOrReplaceChild("leftFrontLegLower",CubeListBuilder.create().texOffs(0,21).addBox(-1F,-1F,-1F,2F,5F,2F,new CubeDeformation(0F)),PartPose.offset(0F,3F,0F));
		PartDefinition var11 = var1.addOrReplaceChild("right_front_leg",CubeListBuilder.create().texOffs(0,29).addBox(-1.5F,-3F,-1.5F,3F,6F,3F,new CubeDeformation(0F)),PartPose.offset(-2.75F,17F,-6.0625F)).addOrReplaceChild("rightFrontLegLower",CubeListBuilder.create().texOffs(0,21).addBox(-1F,-1F,-1F,2F,5F,2F,new CubeDeformation(0F)),PartPose.offset(0F,3F,0F));
		PartDefinition var12 = var1.addOrReplaceChild("left_hind_leg",CubeListBuilder.create().texOffs(0,39).addBox(-1F,-3.1875F,-1F,2F,10F,2F,new CubeDeformation(0.2F)),PartPose.offset(2.75F,16.6875F,5.9375F));
		PartDefinition var13 = var1.addOrReplaceChild("right_hind_leg",CubeListBuilder.create().texOffs(0,39).addBox(-1F,-3.1875F,-1F,2F,10F,2F,new CubeDeformation(0.2F)),PartPose.offset(-2.75F,16.6875F,5.9375F));
		PartDefinition var14 = var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,0).addBox(0.5F,0.0625F,-7F,8F,8F,11F,new CubeDeformation(0.3F)),PartPose.offset(-4.5F,9F,-2.0625F));
		PartDefinition var15 = var14.addOrReplaceChild("hump",CubeListBuilder.create().texOffs(32,17).addBox(-3.5F,-2.5F,-4.5F,7F,5F,9F,new CubeDeformation(0F)),PartPose.offsetAndRotation(4.5F,1.0181F,-2.1693F,-0.3927F,0F,0F));
		PartDefinition var16 = var14.addOrReplaceChild("backBody",CubeListBuilder.create().texOffs(32,33).addBox(-4F,-3.7298F,-1.6068F,8F,8F,8F,new CubeDeformation(0F)),PartPose.offsetAndRotation(4.5F,3.9173F,4.1693F,-0.0873F,0F,0F)).addOrReplaceChild("tail",CubeListBuilder.create().texOffs(29,2).addBox(-0.5F,-0.1272F,-0.4762F,1F,4F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-2.3923F,6.0182F,0.3491F,0F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		this.setupHeadRotation(var1,var6,var5);
		this.rightFrontLeg.xRot = ((Mth.cos((var2) * (0.9F))) * (0.5F)) * (var3);
		this.leftFrontLeg.xRot = ((Mth.cos((var2) * (0.9F))) * (0.5F)) * (var3);
		this.rightHindLeg.xRot = ((Mth.cos(((var2) * (0.9F)) + (3.1415927F))) * (0.5F)) * (var3);
		this.leftHindLeg.xRot = ((Mth.cos(((var2) * (0.9F)) + (3.1415927F))) * (0.5F)) * (var3);
		if (var1.isSprinting()){
			this.tail.xRot = (1.2F) + (((Mth.cos((var2) * (0.6662F))) * (0.2F)) * (var3));
		}
		this.setupAttackAnimation(var1,var4);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		super.renderToBuffer(var1,var2,var3,var4,var5);
	}
	public ModelPart getHead() {
		return this.head;
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		this.head.translateAndRotate(var2);
		var2.mulPose(Axis.ZP.rotationDegrees(90F));
		var2.mulPose(Axis.XP.rotationDegrees(260F));
		var2.translate(0.25D,-0.2D,0D);
	}
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.head);
	}
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body,this.rightFrontLeg,this.leftFrontLeg,this.rightHindLeg,this.leftHindLeg);
	}
}