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

public class BrachiosaurusHeavyModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","brachiosaurus_heavy"),"main");
	private final ModelPart headBase;
	private final ModelPart headBase3_r1;
	private final ModelPart headBase1_r1;
	private final ModelPart neck;
	private final ModelPart neck2;
	private final ModelPart neck3;
	private final ModelPart neck4;
	private final ModelPart neck5;
	private final ModelPart upperMouth;
	private final ModelPart upperTeeth;
	private final ModelPart lowerMouth;
	private final ModelPart lowerTeeth;
	private final ModelPart leftArm2;
	private final ModelPart leftArm3;
	private final ModelPart rightArm2;
	private final ModelPart rightArm3;
	private final ModelPart rightLeg2;
	private final ModelPart rightLeg3;
	private final ModelPart leftLeg2;
	private final ModelPart leftLeg3;
	private final ModelPart tail;
	private final ModelPart tail2;

	public BrachiosaurusHeavyModel(ModelPart var1) {
		super(var1);
		this.headBase = this.head.getChild("headBase");
		this.headBase3_r1 = this.headBase.getChild("headBase3_r1");
		this.headBase1_r1 = this.headBase.getChild("headBase1_r1");
		this.neck = this.head.getChild("neck");
		this.neck2 = this.neck.getChild("neck2");
		this.neck3 = this.neck.getChild("neck3");
		this.neck4 = this.neck.getChild("neck4");
		this.neck5 = this.neck.getChild("neck5");
		this.upperMouth = this.head.getChild("upperMouth");
		this.upperTeeth = this.upperMouth.getChild("upperTeeth");
		this.lowerMouth = this.head.getChild("lowerMouth");
		this.lowerTeeth = this.lowerMouth.getChild("lowerTeeth");
		this.leftArm2 = this.leftArm.getChild("leftArm2");
		this.leftArm3 = this.leftArm.getChild("leftArm3");
		this.rightArm2 = this.rightArm.getChild("rightArm2");
		this.rightArm3 = this.rightArm.getChild("rightArm3");
		this.rightLeg2 = this.rightLeg.getChild("rightLeg2");
		this.rightLeg3 = this.rightLeg.getChild("rightLeg3");
		this.leftLeg2 = this.leftLeg.getChild("leftLeg2");
		this.leftLeg3 = this.leftLeg.getChild("leftLeg3");
		this.tail = var1.getChild("tail");
		this.tail2 = this.tail.getChild("tail2");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("head",CubeListBuilder.create(),PartPose.offset(0F,-2F,-5.75F));
		PartDefinition var3 = var2.addOrReplaceChild("headBase",CubeListBuilder.create().texOffs(0,0).addBox(-2.5F,-11.75F,-9F,5F,6F,2F,new CubeDeformation(0.01F)),PartPose.offset(0F,0F,0F));
		var3.addOrReplaceChild("headBase3_r1",CubeListBuilder.create().texOffs(0,60).addBox(-2.5F,-3.5F,-1.5728F,5F,5F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-8F,-9F,-0.0873F,0F,0F));
		var3.addOrReplaceChild("headBase1_r1",CubeListBuilder.create().texOffs(78,31).addBox(-2F,-1.4992F,-2.5824F,4F,3F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-8.0392F,-6.2777F,1.3963F,0F,0F));
		PartDefinition var4 = var2.addOrReplaceChild("neck",CubeListBuilder.create(),PartPose.offset(0F,0F,-0.1878F));
		var4.addOrReplaceChild("neck2",CubeListBuilder.create().texOffs(29,65).addBox(-2.5F,-4.0723F,-2.3627F,5F,4F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-0.3992F,0.1235F,0.48F,0F,0F));
		var4.addOrReplaceChild("neck3",CubeListBuilder.create().texOffs(93,51).addBox(-2F,-3.7189F,-0.6203F,4F,3F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1.9203F,-2.5618F,0.5672F,0F,0F));
		var4.addOrReplaceChild("neck4",CubeListBuilder.create().texOffs(0,96).addBox(-2F,-2.8466F,-1.9423F,4F,3F,4F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0F,-5.3328F,-2.302F,0.9599F,0F,0F));
		var4.addOrReplaceChild("neck5",CubeListBuilder.create().texOffs(92,23).addBox(-2F,-2.2568F,-1.8199F,4F,2F,5F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,-6.4513F,-4.0017F,1.2217F,0F,0F));
		var2.addOrReplaceChild("upperMouth",CubeListBuilder.create().texOffs(0,35).addBox(-2F,-2.4731F,-4.5535F,4F,3F,2F,new CubeDeformation(0F)).texOffs(0,30).addBox(-2F,-2.4731F,-6.5535F,4F,3F,2F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,-7F,-8F,-0.1309F,0F,0F)).addOrReplaceChild("upperTeeth",CubeListBuilder.create().texOffs(0,3).addBox(1.75F,1.5F,-5.75F,0F,1F,6F,new CubeDeformation(0F)).texOffs(0,2).addBox(-1.75F,1.5F,-5.75F,0F,1F,6F,new CubeDeformation(0F)).texOffs(0,13).addBox(-2F,1.5F,-5.75F,4F,1F,0F,new CubeDeformation(0F)),PartPose.offset(0F,-1.4731F,-0.5535F));
		var2.addOrReplaceChild("lowerMouth",CubeListBuilder.create().texOffs(0,45).addBox(-1.5F,-0.5F,-3F,3F,1F,2F,new CubeDeformation(0F)).texOffs(0,40).addBox(-2F,-0.5F,-1F,4F,1F,2F,new CubeDeformation(0F)).texOffs(0,10).addBox(-2F,-0.5F,1F,4F,1F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-5.3928F,-11.2171F,0.2182F,0F,0F)).addOrReplaceChild("lowerTeeth",CubeListBuilder.create().texOffs(8,44).addBox(-1.756F,-0.5F,0.4F,0F,1F,2F,new CubeDeformation(0F)).texOffs(8,43).addBox(-1.256F,-0.5F,-1.6F,0F,1F,2F,new CubeDeformation(0F)).texOffs(10,9).addBox(1.764F,-0.5F,0.4F,0F,1F,2F,new CubeDeformation(0F)).texOffs(10,8).addBox(1.244F,-0.5F,-1.6F,0F,1F,2F,new CubeDeformation(0F)).texOffs(8,13).addBox(-1.496F,-0.5F,-1.6F,3F,1F,0F,new CubeDeformation(0F)),PartPose.offset(-0.004F,-0.4557F,-1.2455F));
		PartDefinition var5 = var1.addOrReplaceChild("left_arm",CubeListBuilder.create(),PartPose.offsetAndRotation(7F,-1.5F,1.5F,0F,0F,-0.2618F));
		var5.addOrReplaceChild("leftArm2",CubeListBuilder.create().texOffs(0,81).addBox(-1.5331F,-4.4646F,-2.0528F,5F,9F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(1.5331F,3.4646F,-2.9471F,0.1745F,0F,0F));
		var5.addOrReplaceChild("leftArm3",CubeListBuilder.create().texOffs(22,81).mirror().addBox(-1.1991F,-4.5881F,-2.0528F,5F,9F,6F,new CubeDeformation(-0.01F)).mirror(false),PartPose.offsetAndRotation(1.1992F,11.3381F,-2.9471F,-0.0873F,0F,0F));
		PartDefinition var6 = var1.addOrReplaceChild("right_arm",CubeListBuilder.create(),PartPose.offsetAndRotation(-7F,-1.5F,1.5F,0F,0F,0.2618F));
		var6.addOrReplaceChild("rightArm2",CubeListBuilder.create().texOffs(0,81).mirror().addBox(-3.4669F,-4.4646F,-2.0528F,5F,9F,6F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-1.5331F,3.4646F,-2.9471F,0.1745F,0F,0F));
		var6.addOrReplaceChild("rightArm3",CubeListBuilder.create().texOffs(22,81).addBox(-3.8009F,-4.5881F,-2.0528F,5F,9F,6F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(-1.1992F,11.3381F,-2.9471F,-0.0873F,0F,0F));
		PartDefinition var7 = var1.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(60,92).mirror().addBox(-2F,12F,-3.25F,4F,4F,5F,new CubeDeformation(0.01F)).mirror(false),PartPose.offset(6.5F,8F,-1F));
		var7.addOrReplaceChild("leftLeg2",CubeListBuilder.create().texOffs(38,90).mirror().addBox(-14F,-31F,27F,5F,7F,6F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(11.5F,27F,-35F,-0.1745F,0F,0F));
		var7.addOrReplaceChild("leftLeg3",CubeListBuilder.create().texOffs(92,13).mirror().addBox(-13.5F,-29.1483F,28.6858F,4F,5F,5F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(11.5F,41.6483F,-27.1858F,0.1745F,0F,0F));
		PartDefinition var8 = var1.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(60,92).addBox(-2F,12F,-3.25F,4F,4F,5F,new CubeDeformation(0.01F)),PartPose.offset(-6.5F,8F,-1F));
		var8.addOrReplaceChild("rightLeg2",CubeListBuilder.create().texOffs(38,90).addBox(9F,-31F,27F,5F,7F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-11.5F,27F,-35F,-0.1745F,0F,0F));
		var8.addOrReplaceChild("rightLeg3",CubeListBuilder.create().texOffs(92,13).addBox(9.5F,-29.1483F,28.6858F,4F,5F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-11.5F,41.6483F,-27.1858F,0.1745F,0F,0F));
		var1.addOrReplaceChild("tail",CubeListBuilder.create().texOffs(0,60).addBox(-3.5F,-1.5F,-0.1667F,7F,6F,15F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0F,11.5F,4.1667F,-0.3491F,0F,0F)).addOrReplaceChild("tail2",CubeListBuilder.create().texOffs(39,41).addBox(-2.5F,-1F,0.5F,5F,5F,19F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0F,0.25F,13.3333F,0.2182F,0F,0F));
		var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,0).addBox(-8.5F,-8F,-5F,17F,16F,14F,new CubeDeformation(0F)).texOffs(0,45).addBox(-8F,-9.5F,-4.5F,16F,2F,13F,new CubeDeformation(0F)).texOffs(50,18).addBox(-7.5F,-10.25F,-3.75F,15F,1F,12F,new CubeDeformation(0F)).texOffs(44,65).addBox(-7F,-11F,-3.25F,14F,1F,11F,new CubeDeformation(0F)).texOffs(0,30).addBox(-8F,7F,-4.5F,16F,2F,13F,new CubeDeformation(0F)).texOffs(48,0).addBox(-7.5F,8.75F,-4F,15F,1F,12F,new CubeDeformation(0F)).texOffs(45,31).addBox(-6F,-12F,-2.25F,12F,1F,9F,new CubeDeformation(0F)).texOffs(68,41).addBox(-7F,-7F,-6F,14F,14F,1F,new CubeDeformation(0F)).texOffs(44,77).addBox(-6.5F,-6F,-6.75F,13F,12F,1F,new CubeDeformation(0F)),PartPose.offset(0F,6F,-3F));
		return LayerDefinition.create(var0,128,128);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		this.setupHeadRotation(var1,var6,var5);
		float var7 = 1F;
		this.rightArm.xRot = ((((Mth.cos(((var2) * (0.6662F)) + (3.1415927F))) * (0.4F)) * (var3)) * (0.5F)) / (var7);
		this.leftArm.xRot = ((((Mth.cos((var2) * (0.6662F))) * (0.4F)) * (var3)) * (0.5F)) / (var7);
		this.rightLeg.xRot = (((Mth.cos((var2) * (0.6662F))) * (0.3F)) * (var3)) / (var7);
		this.leftLeg.xRot = (((Mth.cos(((var2) * (0.6662F)) + (3.1415927F))) * (0.3F)) * (var3)) / (var7);
		if (var1.isSprinting()){
			this.tail.xRot = ((Mth.cos((var2) * (0.6662F))) * (0.2F)) * (var3);
		}
		this.setupAttackAnimation(var1,var4);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.head.render(var1,var2,var3,var4,var5);
		this.leftArm.render(var1,var2,var3,var4,var5);
		this.rightArm.render(var1,var2,var3,var4,var5);
		this.rightLeg.render(var1,var2,var3,var4,var5);
		this.leftLeg.render(var1,var2,var3,var4,var5);
		this.tail.render(var1,var2,var3,var4,var5);
		this.body.render(var1,var2,var3,var4,var5);
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		super.translateToHand(var1,var2);
		var2.translate(0D,0.45D,-0.05D);
		var2.mulPose(Axis.ZP.rotationDegrees(((var1) == (HumanoidArm.RIGHT)) ? (5F) : (-5F)));
	}
	public boolean renderFirstPersonArm(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5, HumanoidArm var6, boolean var7) {
		float var8 = ((var6) == (HumanoidArm.RIGHT)) ? (1F) : (-1F);
		var1.translate((0.5D) * ((double)(var8)),0.2D,0.1D);
		var1.mulPose(Axis.XP.rotationDegrees(20F));
		if (var7){
			var1.translate((-0.1D) * ((double)(var8)),0D,-0.5D);
		}
		return super.renderFirstPersonArm(var1,var2,var3,var4,var5,var6,var7);
	}
}