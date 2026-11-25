package xyz.pixelatedw.mineminenomi.models.morphs;

import com.google.common.collect.ImmutableList;
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
import xyz.pixelatedw.mineminenomi.models.morphs.armors.BisonHeavyArmorModel;

public class BisonHeavyModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","bison_heavy"),"main");
	private final ModelPart lowerRightArm;
	private final ModelPart lowerLeftArm;
	private final ModelPart snout;
	private final ModelPart leftHorn;
	private final ModelPart leftHornTip;
	private final ModelPart rightHorn;
	private final ModelPart rightHornTip;
	private final ModelPart torso;
	private final ModelPart hump;
	private final ModelPart lowerBody;
	private final ModelPart lowerRightLeg;
	private final ModelPart lowerRightLeg2;
	private final ModelPart rightFoot;
	private final ModelPart lowerLeftLeg;
	private final ModelPart lowerLeftLeg2;
	private final ModelPart leftFoot;

	public BisonHeavyModel(ModelPart var1) {
		super(var1);
		this.lowerRightArm = this.rightArm.getChild("lowerRightArm");
		this.lowerLeftArm = this.leftArm.getChild("lowerLeftArm");
		this.snout = this.head.getChild("snout");
		this.leftHorn = this.head.getChild("leftHorn");
		this.leftHornTip = this.leftHorn.getChild("leftHornTip");
		this.rightHorn = this.head.getChild("rightHorn");
		this.rightHornTip = this.rightHorn.getChild("rightHornTip");
		this.torso = this.body.getChild("torso");
		this.hump = this.body.getChild("hump");
		this.lowerBody = this.body.getChild("lowerBody");
		this.lowerRightLeg = this.rightLeg.getChild("lowerRightLeg");
		this.lowerRightLeg2 = this.lowerRightLeg.getChild("lowerRightLeg2");
		this.rightFoot = this.lowerRightLeg2.getChild("rightFoot");
		this.lowerLeftLeg = this.leftLeg.getChild("lowerLeftLeg");
		this.lowerLeftLeg2 = this.lowerLeftLeg.getChild("lowerLeftLeg2");
		this.leftFoot = this.lowerLeftLeg2.getChild("leftFoot");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(14,32).addBox(-3.8091F,-0.4864F,-2.336F,4F,8F,4F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(-4.75F,1F,1F,0.2182F,0F,0.192F)).addOrReplaceChild("lowerRightArm",CubeListBuilder.create().texOffs(16,45).addBox(-1.6014F,-0.3577F,-1.5625F,3F,8F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-1.7862F,6.913F,-0.336F,-0.2618F,0F,-0.192F));
		PartDefinition var3 = var1.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(14,32).mirror().addBox(-0.1909F,-0.4864F,-2.336F,4F,8F,4F,new CubeDeformation(-0.01F)).mirror(false),PartPose.offsetAndRotation(4.75F,1F,1F,0.2182F,0F,-0.192F)).addOrReplaceChild("lowerLeftArm",CubeListBuilder.create().texOffs(16,45).mirror().addBox(-1.3986F,-0.3577F,-1.5625F,3F,8F,3F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(1.7862F,6.913F,-0.336F,-0.2618F,0F,0.192F));
		PartDefinition var4 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(32,31).addBox(-2.5F,-3.5236F,-4.3875F,5F,5F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0.1745F,0F,0F));
		PartDefinition var5 = var4.addOrReplaceChild("snout",CubeListBuilder.create().texOffs(37,4).addBox(-1.5F,-1.5F,-2F,3F,3F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-0.2736F,-3.075F,0.0873F,0F,0F));
		PartDefinition var6 = var4.addOrReplaceChild("leftHorn",CubeListBuilder.create(),PartPose.offsetAndRotation(2.2197F,-3.1571F,-1.2264F,-0.1309F,0F,-0.6981F));
		PartDefinition var7 = var6.addOrReplaceChild("lefthorn1_r1",CubeListBuilder.create().texOffs(0,0).mirror().addBox(-0.5F,-0.5F,-0.5F,1F,2F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0F,0F,0F,0F,0F,-1.5708F));
		PartDefinition var8 = var6.addOrReplaceChild("leftHornTip",CubeListBuilder.create().texOffs(0,0).mirror().addBox(-0.49F,-2.115F,-0.51F,1F,2F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offset(0.99F,-0.385F,0.01F));
		PartDefinition var9 = var4.addOrReplaceChild("rightHorn",CubeListBuilder.create(),PartPose.offsetAndRotation(-2.2197F,-3.1571F,-1.2264F,-0.1309F,0F,0.6981F));
		PartDefinition var10 = var9.addOrReplaceChild("righthorn_r1",CubeListBuilder.create().texOffs(0,0).addBox(-0.5F,-0.5F,-0.5F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0F,0F,1.5708F));
		PartDefinition var11 = var9.addOrReplaceChild("rightHornTip",CubeListBuilder.create().texOffs(0,0).addBox(-0.51F,-2.115F,-0.51F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offset(-0.99F,-0.385F,0.01F));
		PartDefinition var12 = var1.addOrReplaceChild("body",CubeListBuilder.create(),PartPose.offsetAndRotation(0F,0F,2F,0.0873F,0F,0F));
		PartDefinition var13 = var12.addOrReplaceChild("torso",CubeListBuilder.create().texOffs(0,0).addBox(-5.5F,-10.9425F,-3.2198F,11F,11F,7F,new CubeDeformation(0F)),PartPose.offset(0F,9.9619F,-0.8716F));
		PartDefinition var14 = var12.addOrReplaceChild("hump",CubeListBuilder.create().texOffs(31,13).addBox(-4.5F,-5.1132F,-3.5123F,9F,10F,7F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,2.9619F,1.6284F,0.4363F,0F,0F));
		PartDefinition var15 = var12.addOrReplaceChild("lowerBody",CubeListBuilder.create().texOffs(0,23).addBox(-4F,-0.4634F,-2.6114F,8F,4F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,9.9619F,-0.8716F,-0.1745F,0F,0F));
		PartDefinition var16 = var1.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(0,33).addBox(-1.5F,-0.7362F,-1.7242F,3F,7F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-3F,12F,2F,-0.3491F,0F,0F)).addOrReplaceChild("lowerRightLeg",CubeListBuilder.create().texOffs(2,43).addBox(-1F,-1.2214F,-0.3413F,2F,4F,2F,new CubeDeformation(0.2F)),PartPose.offsetAndRotation(0F,5.9517F,0.4088F,1.4399F,0F,0F)).addOrReplaceChild("lowerRightLeg2",CubeListBuilder.create().texOffs(2,49).addBox(-1F,-0.2661F,-1.3023F,2F,5F,2F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0F,1.9941F,0.3566F,-1.5184F,0F,0F)).addOrReplaceChild("rightFoot",CubeListBuilder.create().texOffs(1,57).addBox(-1F,-0.3186F,-1.9769F,2F,2F,3F,new CubeDeformation(0.1F)),PartPose.offsetAndRotation(0F,4.3025F,-0.1388F,0.4276F,0F,0F));
		PartDefinition var17 = var1.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(0,33).mirror().addBox(-1.5F,-0.7362F,-1.7242F,3F,7F,3F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(3F,12F,2F,-0.3491F,0F,0F)).addOrReplaceChild("lowerLeftLeg",CubeListBuilder.create().texOffs(2,43).mirror().addBox(-1F,-1.2214F,-0.3413F,2F,4F,2F,new CubeDeformation(0.2F)).mirror(false),PartPose.offsetAndRotation(0F,5.9517F,0.4088F,1.4399F,0F,0F)).addOrReplaceChild("lowerLeftLeg2",CubeListBuilder.create().texOffs(2,49).mirror().addBox(-1F,-0.2661F,-1.3023F,2F,5F,2F,new CubeDeformation(-0.01F)).mirror(false),PartPose.offsetAndRotation(0F,1.9941F,0.3566F,-1.5184F,0F,0F)).addOrReplaceChild("leftFoot",CubeListBuilder.create().texOffs(1,57).mirror().addBox(-1F,-0.3186F,-1.9769F,2F,2F,3F,new CubeDeformation(0.1F)).mirror(false),PartPose.offsetAndRotation(0F,4.3025F,-0.1388F,0.4276F,0F,0F));
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
		this.setupAttackAnimation(var1,var4);
		if (var1.isCrouching()){
			this.head.z = -7F;
			this.head.y = 4F;
			this.body.xRot = 0.5F;
			this.body.y = 1F;
			this.body.z = -2F;
			ModelPart var8 = this.rightArm;
			var8.xRot = (var8.xRot) + (0.4F);
			this.rightArm.z = -4F;
			this.rightArm.y = 3F;
			ModelPart var9 = this.leftArm;
			var9.xRot = (var9.xRot) + (0.4F);
			this.leftArm.z = -4F;
			this.leftArm.y = 3F;
			this.rightLeg.y = 13F;
			this.leftLeg.y = 13F;
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		super.renderToBuffer(var1,var2,var3,var4,var5);
	}
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.head);
	}
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body,this.rightArm,this.leftArm,this.leftLeg,this.rightLeg);
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		super.translateToHand(var1,var2);
		var2.translate((-0.05D) * ((double)(((var1) == (HumanoidArm.RIGHT)) ? (1F) : (-1F))),0.35D,-0.1D);
		var2.mulPose(Axis.ZP.rotationDegrees(((var1) == (HumanoidArm.RIGHT)) ? (-10F) : (10F)));
	}
	public boolean renderFirstPersonArm(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5, HumanoidArm var6, boolean var7) {
		float var8 = ((var6) == (HumanoidArm.RIGHT)) ? (1F) : (-1F);
		var1.translate((-0.2D) * ((double)(var8)),-0.4D,-0.4D);
		var1.mulPose(Axis.XP.rotationDegrees(20F));
		ModelPart var9 = this.getArm(var6);
		if (var7){
			var1.translate((0.2D) * ((double)(var8)),0.1D,0.2D);
		}
		return super.renderFirstPersonArm(var1,var2,var3,var4,var5,var6,var7);
	}
	public void copyPropertiesTo(HumanoidModel<T> var1) {
		super.copyPropertiesTo(var1);
		if (var1 instanceof BisonHeavyArmorModel){
			BisonHeavyArmorModel var2 = ((BisonHeavyArmorModel)(var1));
			var1.body.copyFrom(this.body);
			var1.body.yScale = 0.9F;
			var1.body.zScale = 1.1F;
			var1.rightArm.copyFrom(this.rightArm);
			var1.rightArm.yScale = 1.25F;
			var1.leftArm.copyFrom(this.leftArm);
			var1.leftArm.yScale = 1.25F;
		}
	}
}