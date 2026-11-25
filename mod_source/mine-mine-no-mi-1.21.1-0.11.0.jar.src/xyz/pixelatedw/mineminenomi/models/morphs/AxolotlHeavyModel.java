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

public class AxolotlHeavyModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","axolotl_heavy"),"main");
	private final ModelPart backFin;
	private final ModelPart upperBody;
	private final ModelPart hips;
	private final ModelPart lowerBody;
	private final ModelPart tail;
	private final ModelPart tail1Detail;
	private final ModelPart tail2;
	private final ModelPart tail2Detail;
	private final ModelPart tail3;
	private final ModelPart tail3Detail;
	private final ModelPart tail4;
	private final ModelPart tail4Detail;
	private final ModelPart neck;
	private final ModelPart lowerLeftLeg;
	private final ModelPart lowerRightLeg;
	private final ModelPart leftPunch;
	private final ModelPart leftArmMid;
	private final ModelPart rightPunch;
	private final ModelPart rightArmMid;

	public AxolotlHeavyModel(ModelPart var1) {
		super(var1);
		this.backFin = this.body.getChild("backFin");
		this.upperBody = this.body.getChild("upperBody");
		this.hips = this.body.getChild("hips");
		this.lowerBody = this.body.getChild("lowerBody");
		this.tail = var1.getChild("tail");
		this.tail1Detail = this.tail.getChild("tail1Detail");
		this.tail2 = this.tail.getChild("tail2");
		this.tail2Detail = this.tail2.getChild("tail2Detail");
		this.tail3 = this.tail2.getChild("tail3");
		this.tail3Detail = this.tail3.getChild("tail3Detail");
		this.tail4 = this.tail3.getChild("tail4");
		this.tail4Detail = this.tail4.getChild("tail4Detail");
		this.neck = this.head.getChild("neck");
		this.lowerLeftLeg = this.leftLeg.getChild("lowerLeftLeg");
		this.lowerRightLeg = this.rightLeg.getChild("lowerRightLeg");
		this.leftPunch = this.leftArm.getChild("leftPunch");
		this.leftArmMid = this.leftArm.getChild("leftArmMid");
		this.rightPunch = this.rightArm.getChild("rightPunch");
		this.rightArmMid = this.rightArm.getChild("rightArmMid");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("body",CubeListBuilder.create(),PartPose.offsetAndRotation(4F,14F,35F,0.3491F,0F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("backFin",CubeListBuilder.create().texOffs(82,1).addBox(1F,-12F,-6.4F,0F,25F,23F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-5F,-27F,-19F,0.0873F,0F,0F));
		PartDefinition var4 = var2.addOrReplaceChild("upperBody",CubeListBuilder.create().texOffs(0,8).addBox(-13F,-30F,-32.6F,3F,2F,2F,new CubeDeformation(0F)).texOffs(0,8).addBox(2F,-30F,-32.6F,3F,2F,2F,new CubeDeformation(0F)).texOffs(47,50).addBox(-15F,-36.3F,-31.3F,22F,3F,13F,new CubeDeformation(0F)).texOffs(0,0).addBox(-16F,-36F,-32.3F,24F,16F,15F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0.0873F,0F,0F));
		PartDefinition var5 = var2.addOrReplaceChild("hips",CubeListBuilder.create().texOffs(8,32).addBox(-15.5F,-20F,-31.5F,23F,4F,13F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0.0436F,0F,0F));
		PartDefinition var6 = var2.addOrReplaceChild("lowerBody",CubeListBuilder.create().texOffs(47,67).addBox(-13F,-10F,-10F,18F,10F,11F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-7F,-25F,-0.48F,0F,0F));
		PartDefinition var7 = var1.addOrReplaceChild("tail",CubeListBuilder.create(),PartPose.offsetAndRotation(0F,11F,13F,-0.1309F,0F,0F));
		PartDefinition var8 = var7.addOrReplaceChild("tail1_r1",CubeListBuilder.create().texOffs(39,101).addBox(-3.5F,-8.5035F,-0.8045F,7F,8F,19F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,3.1345F,-10.7133F,-0.3054F,0F,0F));
		PartDefinition var9 = var7.addOrReplaceChild("tail1Detail",CubeListBuilder.create().texOffs(98,-14).addBox(-1F,-9F,-8F,0F,14F,15F,new CubeDeformation(0F)),PartPose.offsetAndRotation(1F,0.4965F,2.1955F,-0.2182F,0F,0F));
		PartDefinition var10 = var7.addOrReplaceChild("tail2",CubeListBuilder.create(),PartPose.offset(0F,5.5F,8F));
		PartDefinition var11 = var10.addOrReplaceChild("tail2_r1",CubeListBuilder.create().texOffs(98,112).addBox(-2.5F,-9.0035F,18.1955F,5F,6F,10F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,2.2879F,-20.1948F,-0.1745F,0F,0F));
		PartDefinition var12 = var10.addOrReplaceChild("tail2Detail",CubeListBuilder.create().texOffs(38,93).addBox(-1F,-8.8987F,-6.0675F,0F,14F,10F,new CubeDeformation(0F)),PartPose.offsetAndRotation(1F,-2.5035F,5.1955F,-0.1745F,0F,0F));
		PartDefinition var13 = var10.addOrReplaceChild("tail3",CubeListBuilder.create(),PartPose.offset(0F,2F,13F));
		PartDefinition var14 = var13.addOrReplaceChild("tail3_r1_r1",CubeListBuilder.create().texOffs(95,78).addBox(-1.5F,-8.0035F,27.1955F,3F,4F,12F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,2.8749F,-33.7109F,-0.0873F,0F,0F));
		PartDefinition var15 = var13.addOrReplaceChild("tail3Detail",CubeListBuilder.create().texOffs(84,96).addBox(-1F,-6.9629F,-6.9734F,0F,13F,10F,new CubeDeformation(0F)),PartPose.offsetAndRotation(1F,-4.5035F,3.1955F,-0.1309F,0F,0F));
		PartDefinition var16 = var13.addOrReplaceChild("tail4",CubeListBuilder.create().texOffs(0,0).addBox(-0.5F,-1.0035F,-0.8045F,1F,2F,5F,new CubeDeformation(0F)),PartPose.offset(0F,1F,6F)).addOrReplaceChild("tail4Detail",CubeListBuilder.create().texOffs(115,70).addBox(-1F,-6.4629F,4.0266F,0F,12F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(1F,-5.0535F,-5.8545F,-0.0436F,0F,0F));
		PartDefinition var17 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(28,51).addBox(-8F,-10.9277F,1.4656F,16F,9F,0F,new CubeDeformation(0F)).texOffs(64,0).addBox(-4F,-6.4476F,-3.122F,8F,5F,8F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-7F,-4F,0.3491F,0F,0F)).addOrReplaceChild("neck",CubeListBuilder.create().texOffs(78,13).addBox(-3.5F,-2F,-2F,7F,5F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1.9277F,0.4656F,0.1745F,0F,0F));
		PartDefinition var18 = var1.addOrReplaceChild("left_leg",CubeListBuilder.create(),PartPose.offsetAndRotation(7F,14.25F,3F,-0.2618F,0F,0F));
		PartDefinition var19 = var18.addOrReplaceChild("leftLeg_r1",CubeListBuilder.create().texOffs(73,91).mirror().addBox(3.5F,-12.75F,2.5F,7F,8F,6F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-7F,5.761F,-8.6866F,-0.5672F,0F,0F));
		PartDefinition var20 = var18.addOrReplaceChild("lowerLeftLeg",CubeListBuilder.create().texOffs(105,96).mirror().addBox(0F,6F,-7.5F,6F,8F,5F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-3F,-5F,2F,0.2618F,0F,0F));
		PartDefinition var21 = var1.addOrReplaceChild("right_leg",CubeListBuilder.create(),PartPose.offsetAndRotation(-7F,14.25F,3F,-0.2618F,0F,0F));
		PartDefinition var22 = var21.addOrReplaceChild("rightLeg_r1",CubeListBuilder.create().texOffs(73,91).addBox(-10.5F,-12.75F,2.5F,7F,8F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(7F,5.761F,-8.6866F,-0.5672F,0F,0F));
		PartDefinition var23 = var21.addOrReplaceChild("lowerRightLeg",CubeListBuilder.create().texOffs(105,96).addBox(-6F,6F,-7.5F,6F,8F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(3F,-5F,2F,0.2618F,0F,0F));
		PartDefinition var24 = var1.addOrReplaceChild("left_arm",CubeListBuilder.create(),PartPose.offsetAndRotation(12F,-4F,-1F,-0.4363F,0F,0F));
		PartDefinition var25 = var24.addOrReplaceChild("leftArm_r1",CubeListBuilder.create().texOffs(1,52).mirror().addBox(12F,-37.0076F,-5.1743F,9F,8F,9F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-7.9475F,29.6235F,3.7702F,0.0865F,0.0114F,-0.1304F));
		PartDefinition var26 = var24.addOrReplaceChild("leftPunch",CubeListBuilder.create().texOffs(0,104).mirror().addBox(-6F,-2.1134F,-4.5979F,10F,15F,9F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(6F,16F,2F,0.0843F,-0.0226F,0.2608F));
		PartDefinition var27 = var24.addOrReplaceChild("leftArmMid",CubeListBuilder.create().texOffs(0,71).mirror().addBox(-1F,-3F,-5F,12F,20F,11F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0F,0F,0F,0.0865F,0.0114F,-0.1304F));
		PartDefinition var28 = var1.addOrReplaceChild("right_arm",CubeListBuilder.create(),PartPose.offsetAndRotation(-12F,-4F,-1F,-0.4363F,0F,0F));
		PartDefinition var29 = var28.addOrReplaceChild("rightArm_r1",CubeListBuilder.create().texOffs(1,52).addBox(-21F,-37.0076F,-5.1743F,9F,8F,9F,new CubeDeformation(0F)),PartPose.offsetAndRotation(7.9475F,29.6235F,3.7702F,0.0865F,-0.0114F,0.1304F));
		PartDefinition var30 = var28.addOrReplaceChild("rightPunch",CubeListBuilder.create().texOffs(0,104).addBox(-4F,-2.1134F,-4.5979F,10F,15F,9F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-6F,16F,2F,0.0843F,0.0226F,-0.2608F));
		PartDefinition var31 = var28.addOrReplaceChild("rightArmMid",CubeListBuilder.create().texOffs(0,71).addBox(-11F,-3F,-5F,12F,20F,11F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0.0865F,-0.0114F,0.1304F));
		return LayerDefinition.create(var0,128,128);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		this.setupHeadRotation(var1,var6,var5);
		float var7 = 1F;
		this.leftArm.xRot = (-0.34F) + (((((Mth.cos(((var2) * (0.6662F)) + (3.1415927F))) * (0.8F)) * (var3)) * (0.5F)) / (var7));
		this.rightArm.xRot = (-0.34F) + (((((Mth.cos(((var2) * (0.6662F)) + (3.1415927F))) * (0.8F)) * (var3)) * (0.5F)) / (var7));
		this.leftLeg.xRot = (((Mth.cos((var2) * (0.6662F))) * (0.7F)) * (var3)) / (var7);
		this.rightLeg.xRot = (((Mth.cos((var2) * (0.6662F))) * (0.7F)) * (var3)) / (var7);
		this.tail.yRot = ((float)(Math.sin(((double)(var4)) * (0.06D)))) / (10F);
		this.tail.xRot = ((float)(Math.sin(((double)(var4)) * (0.02D)))) / (10F);
		this.setupAttackAnimation(var1,var4);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		super.renderToBuffer(var1,var2,var3,var4,var5);
		this.tail.render(var1,var2,var3,var4,var5);
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		super.translateToHand(var1,var2);
		var2.translate(((var1) == (HumanoidArm.RIGHT)) ? (-0.3D) : (0.3D),1.1D,0.1D);
		var2.mulPose(Axis.XP.rotationDegrees(-20F));
		var2.mulPose(Axis.ZP.rotationDegrees(((var1) == (HumanoidArm.RIGHT)) ? (-20F) : (20F)));
	}
	public boolean renderFirstPersonArm(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5, HumanoidArm var6, boolean var7) {
		float var8 = ((var6) == (HumanoidArm.RIGHT)) ? (1F) : (-1F);
		var1.translate((1D) * ((double)(var8)),0.2D,0.4D);
		var1.mulPose(Axis.XP.rotationDegrees(20F));
		if (var7){
			var1.translate((-0.6D) * ((double)(var8)),-0.3D,-1D);
		}
		return super.renderFirstPersonArm(var1,var2,var3,var4,var5,var6,var7);
	}
}