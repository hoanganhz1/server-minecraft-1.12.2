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

public class ZouHeavyModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","zou_heavy"),"main");
	private final ModelPart snout;
	private final ModelPart snout2;
	private final ModelPart snout3;
	private final ModelPart rightEar;
	private final ModelPart leftEar;
	private final ModelPart leftTusk;
	private final ModelPart leftTusk2;
	private final ModelPart rightTusk;
	private final ModelPart rightTusk2;
	private final ModelPart lowerLeftArm;
	private final ModelPart lowerRightArm;
	private final ModelPart body2;
	private final ModelPart neck;
	private final ModelPart lowerleftleg;
	private final ModelPart lowerrightleg;

	public ZouHeavyModel(ModelPart var1) {
		super(var1);
		this.snout = this.head.getChild("snout");
		this.snout2 = this.snout.getChild("snout2");
		this.snout3 = this.snout2.getChild("snout3");
		this.rightEar = this.head.getChild("rightEar");
		this.leftEar = this.head.getChild("leftEar");
		this.leftTusk = this.head.getChild("leftTusk");
		this.leftTusk2 = this.leftTusk.getChild("leftTusk2");
		this.rightTusk = this.head.getChild("rightTusk");
		this.rightTusk2 = this.rightTusk.getChild("rightTusk2");
		this.lowerLeftArm = this.leftArm.getChild("lowerLeftArm");
		this.lowerRightArm = this.rightArm.getChild("lowerRightArm");
		this.body2 = this.body.getChild("body2");
		this.neck = this.body2.getChild("neck");
		this.lowerleftleg = this.leftLeg.getChild("lowerleftleg");
		this.lowerrightleg = this.rightLeg.getChild("lowerrightleg");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(94,0).addBox(-4F,-5.875F,-6.9375F,8F,11F,9F,new CubeDeformation(0F)),PartPose.offset(0F,-10F,-1.5F));
		PartDefinition var3 = var2.addOrReplaceChild("snout",CubeListBuilder.create().texOffs(94,21).addBox(-2F,-3.671F,-2.9673F,4F,7F,4F,new CubeDeformation(0.5F)),PartPose.offsetAndRotation(0F,4.125F,-5.4375F,-0.3054F,0F,0F)).addOrReplaceChild("snout2",CubeListBuilder.create().texOffs(94,21).addBox(-2F,-0.875F,-1.625F,4F,7F,4F,new CubeDeformation(0.2F)),PartPose.offsetAndRotation(0F,3.602F,-1.3293F,0.2618F,0F,0F)).addOrReplaceChild("snout3",CubeListBuilder.create().texOffs(94,33).addBox(-2F,-0.9353F,-1.554F,4F,6F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,6.4876F,-0.0527F,0.1745F,0F,0F));
		PartDefinition var4 = var2.addOrReplaceChild("rightEar",CubeListBuilder.create().texOffs(52,0).addBox(-8F,-4.5F,-0.5F,8F,9F,0F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2.5F,-2.375F,-0.4375F,-0.1368F,0.4707F,-0.2946F));
		PartDefinition var5 = var2.addOrReplaceChild("leftEar",CubeListBuilder.create().texOffs(52,0).mirror().addBox(0F,-4.5F,-0.5F,8F,9F,0F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(2.5F,-2.375F,-0.4375F,-0.1368F,-0.4707F,0.2946F));
		PartDefinition var6 = var2.addOrReplaceChild("leftTusk",CubeListBuilder.create().texOffs(0,0).addBox(-0.5F,0F,-0.5F,1F,4F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2.3F,3.125F,-5.9375F,-0.3491F,0.2094F,0F)).addOrReplaceChild("leftTusk2",CubeListBuilder.create().texOffs(0,0).addBox(-0.461F,-0.1505F,-0.5192F,1F,4F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.039F,4.0647F,0.0004F,-0.1745F,0F,0F));
		PartDefinition var7 = var2.addOrReplaceChild("rightTusk",CubeListBuilder.create().texOffs(0,0).mirror().addBox(-0.5F,0F,-0.5F,1F,4F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(2.3F,3.125F,-5.9375F,-0.3491F,-0.2094F,0F)).addOrReplaceChild("rightTusk2",CubeListBuilder.create().texOffs(0,0).mirror().addBox(-0.539F,-0.1505F,-0.5192F,1F,4F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0.039F,4.0647F,0.0004F,-0.1745F,0F,0F));
		PartDefinition var8 = var1.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(66,2).addBox(-1.0012F,-2.0397F,-3.4356F,6F,10F,7F,new CubeDeformation(0F)),PartPose.offsetAndRotation(9.5F,-4F,1F,0F,0F,-0.1745F)).addOrReplaceChild("lowerLeftArm",CubeListBuilder.create().texOffs(66,19).addBox(-2.3255F,-0.0899F,-3.0076F,5F,10F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(1.8228F,6.8009F,0.0625F,-0.1745F,0F,0.0873F));
		PartDefinition var9 = var1.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(66,2).mirror().addBox(-4.9988F,-2.0397F,-3.4356F,6F,10F,7F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-9.5F,-4F,1F,0F,0F,0.1745F)).addOrReplaceChild("lowerRightArm",CubeListBuilder.create().texOffs(66,19).mirror().addBox(-2.6745F,-0.0899F,-3.0076F,5F,10F,6F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-1.8228F,6.8009F,0.0625F,-0.1745F,0F,-0.0873F));
		PartDefinition var10 = var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,31).addBox(-8.5F,-12.164F,-8.299F,17F,14F,15F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,11F,1F,-0.0436F,0F,0F)).addOrReplaceChild("body2",CubeListBuilder.create().texOffs(0,0).addBox(-9.5F,-8.8743F,-6.9373F,19F,17F,14F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-8.6077F,0.4337F,0.1309F,0F,0F)).addOrReplaceChild("neck",CubeListBuilder.create().texOffs(84,50).addBox(-6F,-4.0902F,-4.2988F,12F,4F,10F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-7.3673F,-1.0712F,-0.0349F,0F,0F));
		PartDefinition var11 = var1.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(65,36).addBox(-3F,0.1262F,-2.9993F,6F,7F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(5F,11F,1F,-0.1396F,0F,0F)).addOrReplaceChild("lowerleftleg",CubeListBuilder.create().texOffs(64,50).addBox(-2.5F,-1.0041F,-2.6571F,5F,7F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,6.8959F,0.1571F,0.1396F,0F,0F));
		PartDefinition var12 = var1.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(65,36).mirror().addBox(-3F,0.1262F,-2.9993F,6F,7F,6F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-5F,11F,1F,-0.1396F,0F,0F)).addOrReplaceChild("lowerrightleg",CubeListBuilder.create().texOffs(64,50).mirror().addBox(-2.5F,-1.0041F,-2.6571F,5F,7F,5F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0F,6.8959F,0.1571F,0.1396F,0F,0F));
		return LayerDefinition.create(var0,128,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		this.setupHeadRotation(var1,var6,var5);
		float var7 = 1F;
		this.rightArm.xRot = ((((Mth.cos(((var2) * (0.6662F)) + (3.1415927F))) * (0.8F)) * (var3)) * (0.5F)) / (var7);
		this.leftArm.xRot = ((((Mth.cos((var2) * (0.6662F))) * (0.8F)) * (var3)) * (0.5F)) / (var7);
		this.rightLeg.xRot = (-0.15F) + ((((Mth.cos((var2) * (0.6662F))) * (0.7F)) * (var3)) / (var7));
		this.leftLeg.xRot = (-0.15F) + ((((Mth.cos(((var2) * (0.6662F)) + (3.1415927F))) * (0.7F)) * (var3)) / (var7));
		this.setupAttackAnimation(var1,var4,5F);
		if (var1.isCrouching()){
			this.body.xRot = 0.5F;
			ModelPart var8 = this.body;
			var8.z = (var8.z) - (4F);
			ModelPart var9 = this.rightArm;
			var9.xRot = (var9.xRot) + (0.4F);
			ModelPart var10 = this.rightArm;
			var10.z = (var10.z) - (8.5F);
			ModelPart var11 = this.rightArm;
			var11.y = (var11.y) + (1F);
			ModelPart var12 = this.leftArm;
			var12.xRot = (var12.xRot) + (0.4F);
			ModelPart var13 = this.leftArm;
			var13.z = (var13.z) - (8.5F);
			ModelPart var14 = this.leftArm;
			var14.y = (var14.y) + (1F);
			this.rightLeg.z = 1.5F;
			this.leftLeg.z = 1.5F;
			this.rightLeg.y = 9F;
			this.leftLeg.y = 9F;
			ModelPart var15 = this.head;
			var15.z = (var15.z) - (15F);
			ModelPart var16 = this.head;
			var16.y = (var16.y) + (5F);
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		var1.translate(0D,0.01D,0D);
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
		float var3 = ((var1) == (HumanoidArm.RIGHT)) ? (1F) : (-1F);
		var2.translate((-0.1D) * ((double)(var3)),0.35D,-0.1D);
		var2.mulPose(Axis.ZP.rotationDegrees((-10F) * (var3)));
	}
	public boolean renderFirstPersonArm(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5, HumanoidArm var6, boolean var7) {
		float var8 = ((var6) == (HumanoidArm.RIGHT)) ? (1F) : (-1F);
		var1.translate(0D,-0.3D,-0.3D);
		var1.mulPose(Axis.XP.rotationDegrees(20F));
		return super.renderFirstPersonArm(var1,var2,var3,var4,var5,var6,var7);
	}
}