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

public class MoguHeavyModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","mogu_heavy"),"main");
	private final ModelPart leftEye;
	private final ModelPart rightEye;
	private final ModelPart nose;
	private final ModelPart nose2;
	private final ModelPart nose3;
	private final ModelPart lowerLeftArm;
	private final ModelPart leftArmClaw1;
	private final ModelPart leftArmClaw1b;
	private final ModelPart leftArmClaw1c;
	private final ModelPart leftArmClaw2;
	private final ModelPart leftArmClaw2b;
	private final ModelPart leftArmClaw2c;
	private final ModelPart leftArmClaw3;
	private final ModelPart leftArmClaw3b;
	private final ModelPart leftArmClaw3c;
	private final ModelPart leftArmClaw4;
	private final ModelPart leftArmClaw4b;
	private final ModelPart leftArmClaw4c;
	private final ModelPart lowerRightArm;
	private final ModelPart rightArmClaw1;
	private final ModelPart rightArmClaw1b;
	private final ModelPart rightArmClaw1c;
	private final ModelPart rightArmClaw2;
	private final ModelPart rightArmClaw2b;
	private final ModelPart rightArmClaw2c;
	private final ModelPart rightArmClaw3;
	private final ModelPart rightArmClaw3b;
	private final ModelPart rightArmClaw3c;
	private final ModelPart rightArmClaw4;
	private final ModelPart rightArmClaw4b;
	private final ModelPart rightArmClaw4c;
	private final ModelPart lowerLeftLeg;
	private final ModelPart leftFoot;
	private final ModelPart lowerRightLeg;
	private final ModelPart rightFoot;

	public MoguHeavyModel(ModelPart var1) {
		super(var1);
		this.leftEye = this.head.getChild("leftEye");
		this.rightEye = this.head.getChild("rightEye");
		this.nose = this.head.getChild("nose");
		this.nose2 = this.nose.getChild("nose2");
		this.nose3 = this.nose2.getChild("nose3");
		this.lowerLeftArm = this.leftArm.getChild("lowerLeftArm");
		this.leftArmClaw1 = this.lowerLeftArm.getChild("leftArmClaw1");
		this.leftArmClaw1b = this.leftArmClaw1.getChild("leftArmClaw1b");
		this.leftArmClaw1c = this.leftArmClaw1b.getChild("leftArmClaw1c");
		this.leftArmClaw2 = this.lowerLeftArm.getChild("leftArmClaw2");
		this.leftArmClaw2b = this.leftArmClaw2.getChild("leftArmClaw2b");
		this.leftArmClaw2c = this.leftArmClaw2b.getChild("leftArmClaw2c");
		this.leftArmClaw3 = this.lowerLeftArm.getChild("leftArmClaw3");
		this.leftArmClaw3b = this.leftArmClaw3.getChild("leftArmClaw3b");
		this.leftArmClaw3c = this.leftArmClaw3b.getChild("leftArmClaw3c");
		this.leftArmClaw4 = this.lowerLeftArm.getChild("leftArmClaw4");
		this.leftArmClaw4b = this.leftArmClaw4.getChild("leftArmClaw4b");
		this.leftArmClaw4c = this.leftArmClaw4b.getChild("leftArmClaw4c");
		this.lowerRightArm = this.rightArm.getChild("lowerRightArm");
		this.rightArmClaw1 = this.lowerRightArm.getChild("rightArmClaw1");
		this.rightArmClaw1b = this.rightArmClaw1.getChild("rightArmClaw1b");
		this.rightArmClaw1c = this.rightArmClaw1b.getChild("rightArmClaw1c");
		this.rightArmClaw2 = this.lowerRightArm.getChild("rightArmClaw2");
		this.rightArmClaw2b = this.rightArmClaw2.getChild("rightArmClaw2b");
		this.rightArmClaw2c = this.rightArmClaw2b.getChild("rightArmClaw2c");
		this.rightArmClaw3 = this.lowerRightArm.getChild("rightArmClaw3");
		this.rightArmClaw3b = this.rightArmClaw3.getChild("rightArmClaw3b");
		this.rightArmClaw3c = this.rightArmClaw3b.getChild("rightArmClaw3c");
		this.rightArmClaw4 = this.lowerRightArm.getChild("rightArmClaw4");
		this.rightArmClaw4b = this.rightArmClaw4.getChild("rightArmClaw4b");
		this.rightArmClaw4c = this.rightArmClaw4b.getChild("rightArmClaw4c");
		this.lowerLeftLeg = this.leftLeg.getChild("lowerLeftLeg");
		this.leftFoot = this.lowerLeftLeg.getChild("leftFoot");
		this.lowerRightLeg = this.rightLeg.getChild("lowerRightLeg");
		this.rightFoot = this.lowerRightLeg.getChild("rightFoot");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidMorphModel.createMesh(CubeDeformation.NONE,0F);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(2,3).addBox(-2.375F,-5.125F,-1.875F,5F,5F,5F,new CubeDeformation(0F)),PartPose.offset(-0.25F,7F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("leftEye",CubeListBuilder.create().texOffs(18,5).addBox(0.5625F,-9.1875F,-3.9375F,2F,2F,0F,new CubeDeformation(0F)),PartPose.offset(0.0625F,5.0625F,2.0375F));
		PartDefinition var4 = var2.addOrReplaceChild("rightEye",CubeListBuilder.create().texOffs(18,5).mirror().addBox(-2.5625F,-9.1875F,-3.9375F,2F,2F,0F,new CubeDeformation(0F)).mirror(false),PartPose.offset(0.1875F,5.0625F,2.0375F));
		PartDefinition var5 = var2.addOrReplaceChild("nose",CubeListBuilder.create().texOffs(0,5).addBox(-1F,-0.661F,-0.0027F,2F,1F,1F,new CubeDeformation(0.2F)),PartPose.offsetAndRotation(0.125F,-1.5314F,-2.5481F,-0.0873F,0F,0F)).addOrReplaceChild("nose2",CubeListBuilder.create().texOffs(0,2).addBox(-1F,-0.6651F,0.6625F,2F,1F,1F,new CubeDeformation(0.05F)),PartPose.offsetAndRotation(0F,-0.1314F,-1.4981F,-0.0873F,0F,0F)).addOrReplaceChild("nose3",CubeListBuilder.create().texOffs(1,0).addBox(-0.5F,-1.0436F,-0.2019F,1F,1F,1F,new CubeDeformation(-0.3F)),PartPose.offsetAndRotation(0F,-0.0966F,0.3144F,-0.0873F,0F,0F));
		PartDefinition var6 = var1.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(24,34).mirror().addBox(-0.6174F,-1.4638F,-1.7052F,4F,9F,4F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(4.5F,7.5F,2F,0.0516F,0.0091F,-0.1743F)).addOrReplaceChild("lowerLeftArm",CubeListBuilder.create().texOffs(23,48).mirror().addBox(-1.9375F,-0.0214F,-2.1737F,4F,5F,5F,new CubeDeformation(0.2F)).mirror(false),PartPose.offsetAndRotation(1.375F,6.4076F,0.0257F,-0.1309F,0F,0F));
		PartDefinition var7 = var6.addOrReplaceChild("leftArmClaw1",CubeListBuilder.create().texOffs(18,45).mirror().addBox(-0.5F,0F,-0.5F,1F,3F,1F,new CubeDeformation(0.2F)).mirror(false),PartPose.offsetAndRotation(-0.7474F,3.7877F,-2.1621F,-0.3054F,0F,0F)).addOrReplaceChild("leftArmClaw1b",CubeListBuilder.create().texOffs(18,41).addBox(-0.5507F,-0.076F,-0.5417F,1F,2F,1F,new CubeDeformation(0.1F)),PartPose.offsetAndRotation(0.0248F,2.9761F,0.0163F,0.2618F,0F,0F)).addOrReplaceChild("leftArmClaw1c",CubeListBuilder.create().texOffs(18,37).addBox(-0.5507F,-0.5745F,-0.5325F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.0162F,1.9507F,-0.0258F,0.1309F,0F,0F));
		PartDefinition var8 = var6.addOrReplaceChild("leftArmClaw2",CubeListBuilder.create().texOffs(18,45).mirror().addBox(-0.3839F,-0.718F,-0.5F,1F,3F,1F,new CubeDeformation(0.2F)).mirror(false),PartPose.offsetAndRotation(1.895F,4.3238F,-1.5287F,0F,0F,-0.1743F)).addOrReplaceChild("leftArmClaw2b",CubeListBuilder.create().texOffs(18,41).mirror().addBox(-0.4494F,0.0065F,-0.5F,1F,2F,1F,new CubeDeformation(0.1F)).mirror(false),PartPose.offsetAndRotation(0.1062F,2.3638F,-0.0249F,0F,0F,0.1745F)).addOrReplaceChild("leftArmClaw2c",CubeListBuilder.create().texOffs(18,37).mirror().addBox(-0.4597F,-0.3657F,-0.5F,1F,2F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0.0026F,2.0696F,-0.0204F,0F,0F,0.2182F));
		PartDefinition var9 = var6.addOrReplaceChild("leftArmClaw3",CubeListBuilder.create().texOffs(18,45).mirror().addBox(-0.3839F,-0.718F,-0.5F,1F,3F,1F,new CubeDeformation(0.2F)).mirror(false),PartPose.offsetAndRotation(1.895F,4.3238F,0.2838F,0F,0F,-0.1743F)).addOrReplaceChild("leftArmClaw3b",CubeListBuilder.create().texOffs(18,41).mirror().addBox(-0.4494F,0.0065F,-0.5F,1F,2F,1F,new CubeDeformation(0.1F)).mirror(false),PartPose.offsetAndRotation(0.1062F,2.3638F,-0.0249F,0F,0F,0.1745F)).addOrReplaceChild("leftArmClaw3c",CubeListBuilder.create().texOffs(18,37).mirror().addBox(-0.4597F,-0.2407F,-0.5F,1F,2F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0.0026F,2.0696F,-0.0204F,0F,0F,0.2182F));
		PartDefinition var10 = var6.addOrReplaceChild("leftArmClaw4",CubeListBuilder.create().texOffs(18,45).mirror().addBox(-0.3839F,-0.718F,-0.5F,1F,3F,1F,new CubeDeformation(0.2F)).mirror(false),PartPose.offsetAndRotation(1.895F,4.3238F,2.1588F,0F,0F,-0.1743F)).addOrReplaceChild("leftArmClaw4b",CubeListBuilder.create().texOffs(18,41).mirror().addBox(-0.4494F,0.0065F,-0.5F,1F,2F,1F,new CubeDeformation(0.1F)).mirror(false),PartPose.offsetAndRotation(0.1062F,2.3638F,-0.0249F,0F,0F,0.1745F)).addOrReplaceChild("leftArmClaw4c",CubeListBuilder.create().texOffs(18,37).mirror().addBox(-0.4597F,-0.4907F,-0.5F,1F,2F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0.0026F,2.0696F,-0.0204F,0F,0F,0.2182F));
		PartDefinition var11 = var1.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(24,34).addBox(-3.3826F,-1.4638F,-1.7052F,4F,9F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-4.5F,7.5F,2F,0.0516F,-0.0091F,0.1743F)).addOrReplaceChild("lowerRightArm",CubeListBuilder.create().texOffs(23,48).addBox(-2.0625F,-0.0214F,-2.1737F,4F,5F,5F,new CubeDeformation(0.2F)),PartPose.offsetAndRotation(-1.375F,6.4076F,0.0257F,-0.1309F,0F,0F));
		PartDefinition var12 = var11.addOrReplaceChild("rightArmClaw1",CubeListBuilder.create().texOffs(18,45).addBox(-0.5F,0F,-0.5F,1F,3F,1F,new CubeDeformation(0.2F)),PartPose.offsetAndRotation(0.7474F,3.7877F,-2.1621F,-0.3054F,0F,0F)).addOrReplaceChild("rightArmClaw1b",CubeListBuilder.create().texOffs(18,41).mirror().addBox(-0.4493F,-0.076F,-0.5417F,1F,2F,1F,new CubeDeformation(0.1F)).mirror(false),PartPose.offsetAndRotation(-0.0248F,2.9761F,0.0163F,0.2618F,0F,0F)).addOrReplaceChild("rightArmClaw1c",CubeListBuilder.create().texOffs(18,37).mirror().addBox(-0.4493F,-0.5745F,-0.5325F,1F,2F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0.0162F,1.9507F,-0.0258F,0.1309F,0F,0F));
		PartDefinition var13 = var11.addOrReplaceChild("rightArmClaw2",CubeListBuilder.create().texOffs(18,45).addBox(-0.6161F,-0.718F,-0.5F,1F,3F,1F,new CubeDeformation(0.2F)),PartPose.offsetAndRotation(-1.895F,4.3238F,-1.5287F,0F,0F,0.1743F)).addOrReplaceChild("rightArmClaw2b",CubeListBuilder.create().texOffs(18,41).addBox(-0.5505F,0.0065F,-0.5F,1F,2F,1F,new CubeDeformation(0.1F)),PartPose.offsetAndRotation(-0.1062F,2.3638F,-0.0249F,0F,0F,-0.1745F)).addOrReplaceChild("rightArmClaw2c",CubeListBuilder.create().texOffs(18,37).addBox(-0.5403F,-0.3657F,-0.5F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.0026F,2.0696F,-0.0204F,0F,0F,-0.2182F));
		PartDefinition var14 = var11.addOrReplaceChild("rightArmClaw3",CubeListBuilder.create().texOffs(18,45).addBox(-0.6161F,-0.718F,-0.5F,1F,3F,1F,new CubeDeformation(0.2F)),PartPose.offsetAndRotation(-1.895F,4.3238F,0.2838F,0F,0F,0.1743F)).addOrReplaceChild("rightArmClaw3b",CubeListBuilder.create().texOffs(18,41).addBox(-0.5505F,0.0065F,-0.5F,1F,2F,1F,new CubeDeformation(0.1F)),PartPose.offsetAndRotation(-0.1062F,2.3638F,-0.0249F,0F,0F,-0.1745F)).addOrReplaceChild("rightArmClaw3c",CubeListBuilder.create().texOffs(18,37).addBox(-0.5403F,-0.2407F,-0.5F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.0026F,2.0696F,-0.0204F,0F,0F,-0.2182F));
		PartDefinition var15 = var11.addOrReplaceChild("rightArmClaw4",CubeListBuilder.create().texOffs(18,45).addBox(-0.6161F,-0.718F,-0.5F,1F,3F,1F,new CubeDeformation(0.2F)),PartPose.offsetAndRotation(-1.895F,4.3238F,2.1588F,0F,0F,0.1743F)).addOrReplaceChild("rightArmClaw4b",CubeListBuilder.create().texOffs(18,41).addBox(-0.5505F,0.0065F,-0.5F,1F,2F,1F,new CubeDeformation(0.1F)),PartPose.offsetAndRotation(-0.1062F,2.3638F,-0.0249F,0F,0F,-0.1745F)).addOrReplaceChild("rightArmClaw4c",CubeListBuilder.create().texOffs(18,37).addBox(-0.5403F,-0.4907F,-0.5F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.0026F,2.0696F,-0.0204F,0F,0F,-0.2182F));
		PartDefinition var16 = var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,28).addBox(-4F,5.5625F,-2.375F,8F,3F,5F,new CubeDeformation(0.1F)).texOffs(0,14).addBox(-4.4375F,-0.4375F,-2.9375F,9F,7F,6F,new CubeDeformation(0F)),PartPose.offset(0F,7F,2F));
		PartDefinition var17 = var1.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(0,39).addBox(-1.85F,-0.5F,-2F,4F,5F,4F,new CubeDeformation(0.1F)),PartPose.offset(2.5F,15.5F,2F)).addOrReplaceChild("lowerLeftLeg",CubeListBuilder.create().texOffs(2,49).addBox(-1.5F,-2.5F,-1.5F,3F,5F,3F,new CubeDeformation(0.2F)),PartPose.offset(0.15F,4.5625F,0F)).addOrReplaceChild("leftFoot",CubeListBuilder.create().texOffs(0,58).addBox(-1.5F,-0.5F,-3.5F,3F,1F,5F,new CubeDeformation(0.4F)),PartPose.offset(0F,3F,0F));
		PartDefinition var18 = var1.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(0,39).mirror().addBox(-2.15F,-0.5F,-2F,4F,5F,4F,new CubeDeformation(0.1F)).mirror(false),PartPose.offset(-2.5F,15.5F,2F)).addOrReplaceChild("lowerRightLeg",CubeListBuilder.create().texOffs(2,49).mirror().addBox(-1.5F,-2.5F,-1.5F,3F,5F,3F,new CubeDeformation(0.2F)).mirror(false),PartPose.offset(-0.15F,4.5625F,0F)).addOrReplaceChild("rightFoot",CubeListBuilder.create().texOffs(0,58).mirror().addBox(-1.5F,-0.5F,-3.5F,3F,1F,5F,new CubeDeformation(0.4F)).mirror(false),PartPose.offset(0F,3F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		this.setupHeadRotation(var1,var6,var5);
		float var7 = 1F;
		this.rightArm.xRot = ((((Mth.cos(((var2) * (0.6662F)) + (3.1415927F))) * (0.8F)) * (var3)) * (0.5F)) / (var7);
		this.leftArm.xRot = ((((Mth.cos((var2) * (0.6662F))) * (0.8F)) * (var3)) * (0.5F)) / (var7);
		this.rightLeg.xRot = (((Mth.cos((var2) * (0.6662F))) * (0.7F)) * (var3)) / (var7);
		this.leftLeg.xRot = (((Mth.cos(((var2) * (0.6662F)) + (3.1415927F))) * (0.7F)) * (var3)) / (var7);
		if (var1.isCrouching()){
			this.head.y = 11F;
			this.head.z = -3F;
			this.body.xRot = 0.5F;
			this.body.z = -2F;
			this.body.y = 9F;
			this.rightLeg.y = 17F;
			this.leftLeg.y = 17F;
			this.rightArm.y = 10F;
			this.rightArm.z = 0F;
			this.leftArm.y = 10F;
			this.leftArm.z = 0F;
		}
		this.setupAttackAnimation(var1,var4);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.head.render(var1,var2,var3,var4,var5);
		this.leftArm.render(var1,var2,var3,var4,var5);
		this.rightArm.render(var1,var2,var3,var4,var5);
		this.body.render(var1,var2,var3,var4,var5);
		this.leftLeg.render(var1,var2,var3,var4,var5);
		this.rightLeg.render(var1,var2,var3,var4,var5);
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		super.translateToHand(var1,var2);
		var2.translate(0D,0.2D,-0.1D);
		var2.mulPose(Axis.ZP.rotationDegrees(((var1) == (HumanoidArm.RIGHT)) ? (5F) : (-5F)));
	}
	public boolean renderFirstPersonArm(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5, HumanoidArm var6, boolean var7) {
		var1.translate(0D,-0.3D,-0.3D);
		var1.mulPose(Axis.XP.rotationDegrees(20F));
		return super.renderFirstPersonArm(var1,var2,var3,var4,var5,var6,var7);
	}
}