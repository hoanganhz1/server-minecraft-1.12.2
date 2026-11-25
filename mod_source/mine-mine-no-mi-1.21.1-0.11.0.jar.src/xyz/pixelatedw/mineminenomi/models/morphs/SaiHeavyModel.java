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

public class SaiHeavyModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","sai_heavy"),"main");
	private final ModelPart leftLowerArm;
	private final ModelPart rightLowerArm;
	private final ModelPart neck;
	private final ModelPart head2;
	private final ModelPart leftEar;
	private final ModelPart rightEar;
	private final ModelPart mouth;
	private final ModelPart frontHorn;
	private final ModelPart frontUpperHorn;
	private final ModelPart backHorn;
	private final ModelPart backUpperHorn;
	private final ModelPart body2;
	private final ModelPart body3;
	private final ModelPart lowerleftleg;
	private final ModelPart leftfoot;
	private final ModelPart lowerrightleg;
	private final ModelPart rightfoot;

	public SaiHeavyModel(ModelPart var1) {
		super(var1);
		this.leftLowerArm = this.leftArm.getChild("leftLowerArm");
		this.rightLowerArm = this.rightArm.getChild("rightLowerArm");
		this.neck = this.head.getChild("neck");
		this.head2 = this.head.getChild("head2");
		this.leftEar = this.head2.getChild("leftEar");
		this.rightEar = this.head2.getChild("rightEar");
		this.mouth = this.head2.getChild("mouth");
		this.frontHorn = this.head2.getChild("frontHorn");
		this.frontUpperHorn = this.frontHorn.getChild("frontUpperHorn");
		this.backHorn = this.head2.getChild("backHorn");
		this.backUpperHorn = this.backHorn.getChild("backUpperHorn");
		this.body2 = this.body.getChild("body2");
		this.body3 = this.body2.getChild("body3");
		this.lowerleftleg = this.leftLeg.getChild("lowerleftleg");
		this.leftfoot = this.lowerleftleg.getChild("leftfoot");
		this.lowerrightleg = this.rightLeg.getChild("lowerrightleg");
		this.rightfoot = this.lowerrightleg.getChild("rightfoot");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(0,60).mirror().addBox(-0.25F,-1.33F,-2.9987F,5F,10F,6F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(7.25F,1F,1F,0.0873F,0F,0F)).addOrReplaceChild("leftLowerArm",CubeListBuilder.create().texOffs(22,61).mirror().addBox(-2F,-1.5F,-2.5F,4F,9F,5F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(2.25F,8.3101F,0.1657F,-0.2182F,0F,0F));
		PartDefinition var3 = var1.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(0,60).addBox(-4.75F,-1.33F,-2.9987F,5F,10F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-7.25F,1F,1F,0.0873F,0F,0F)).addOrReplaceChild("rightLowerArm",CubeListBuilder.create().texOffs(22,61).addBox(-2F,-1.5F,-2.5F,4F,9F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2.25F,8.3101F,0.1657F,-0.2182F,0F,0F));
		PartDefinition var4 = var1.addOrReplaceChild("head",CubeListBuilder.create(),PartPose.offsetAndRotation(0F,0F,0F,-0.6545F,0F,0F));
		PartDefinition var5 = var4.addOrReplaceChild("neck",CubeListBuilder.create().texOffs(50,30).addBox(-3F,-4.3982F,-4.3874F,6F,8F,7F,new CubeDeformation(0.2F)),PartPose.offsetAndRotation(0F,-0.6002F,-0.7848F,-0.2182F,0F,0F));
		PartDefinition var6 = var4.addOrReplaceChild("head2",CubeListBuilder.create().texOffs(46,16).addBox(-3.5F,-4.1989F,-3.9161F,7F,8F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1.9533F,-3.4263F,0.4363F,0F,0F));
		PartDefinition var7 = var6.addOrReplaceChild("leftEar",CubeListBuilder.create().texOffs(0,6).addBox(-1F,-2.5F,-0.8125F,2F,3F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(2.4911F,-4.2587F,-0.3525F,0.4363F,0F,0.1745F));
		PartDefinition var8 = var6.addOrReplaceChild("rightEar",CubeListBuilder.create().texOffs(0,6).addBox(-1F,-2.5F,-0.8125F,2F,3F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2.4911F,-4.2587F,-0.3525F,0.4363F,0F,-0.1745F));
		PartDefinition var9 = var6.addOrReplaceChild("mouth",CubeListBuilder.create().texOffs(46,0).addBox(-3.5F,-2.1467F,-8.5031F,7F,6F,10F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,-0.6664F,-1.7917F,0.3491F,0F,0F));
		PartDefinition var10 = var6.addOrReplaceChild("frontHorn",CubeListBuilder.create().texOffs(0,0).addBox(-1F,-1.0616F,-0.9891F,2F,2F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-0.7496F,-9.1317F,0.1745F,0F,0F)).addOrReplaceChild("frontUpperHorn",CubeListBuilder.create().texOffs(0,0).addBox(-1F,-1.5F,-1F,2F,3F,2F,new CubeDeformation(-0.1F)),PartPose.offsetAndRotation(0F,-2.0914F,0.3156F,-0.3054F,0F,0F));
		PartDefinition var11 = var6.addOrReplaceChild("backHorn",CubeListBuilder.create().texOffs(0,0).addBox(-1F,-1.4204F,-0.7742F,2F,2F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-0.8384F,-6.9653F,0.0873F,0F,0F)).addOrReplaceChild("backUpperHorn",CubeListBuilder.create().texOffs(0,0).addBox(-1F,-0.8625F,-0.3546F,2F,1F,2F,new CubeDeformation(-0.1F)),PartPose.offsetAndRotation(0F,-1.2779F,-0.4983F,-0.2182F,0F,0F));
		PartDefinition var12 = var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,46).addBox(-5.5F,-3.5154F,-4.6359F,11F,6F,8F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,15F,3F,-0.1309F,0F,0F)).addOrReplaceChild("body2",CubeListBuilder.create().texOffs(0,0).addBox(-6.5F,-14.9331F,-2.2106F,13F,15F,10F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-0.6408F,-3.2441F,0.1745F,0F,0F)).addOrReplaceChild("body3",CubeListBuilder.create().texOffs(0,25).addBox(-7F,-6.3959F,-2.7615F,14F,10F,11F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-9.4901F,-1.2439F,0.1745F,0F,0F));
		PartDefinition var13 = var1.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(46,45).addBox(-2.5F,-0.6436F,-1.8949F,5F,6F,5F,new CubeDeformation(0.1F)),PartPose.offsetAndRotation(4.5F,13.5F,1F,0.1745F,0F,0F)).addOrReplaceChild("lowerleftleg",CubeListBuilder.create().texOffs(46,57).addBox(-2.0313F,-0.1803F,-1.7752F,4F,6F,4F,new CubeDeformation(0.2F)),PartPose.offsetAndRotation(-0.0313F,3.9459F,0.672F,-0.2618F,0F,0F)).addOrReplaceChild("leftfoot",CubeListBuilder.create().texOffs(44,68).addBox(-2.5625F,-0.0625F,-2.5F,5F,2F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.0313F,4.7541F,-0.4595F,0.0873F,0F,0F));
		PartDefinition var14 = var1.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(46,45).mirror().addBox(-2.5F,-0.6436F,-1.8949F,5F,6F,5F,new CubeDeformation(0.1F)).mirror(false),PartPose.offsetAndRotation(-4.5F,13.5F,1F,0.1745F,0F,0F)).addOrReplaceChild("lowerrightleg",CubeListBuilder.create().texOffs(46,57).mirror().addBox(-1.9688F,-0.1803F,-1.7752F,4F,6F,4F,new CubeDeformation(0.2F)).mirror(false),PartPose.offsetAndRotation(0.0313F,3.9459F,0.672F,-0.2618F,0F,0F)).addOrReplaceChild("rightfoot",CubeListBuilder.create().texOffs(44,68).mirror().addBox(-2.4375F,-0.0625F,-2.5F,5F,2F,5F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-0.0313F,4.7541F,-0.4595F,0.0873F,0F,0F));
		return LayerDefinition.create(var0,128,128);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		this.setupHeadRotation(var1,var6,var5);
		float var7 = 1F;
		this.rightArm.xRot = ((((Mth.cos(((var2) * (0.6F)) + (3.1415927F))) * (0.8F)) * (var3)) * (0.5F)) / (var7);
		this.leftArm.xRot = ((((Mth.cos((var2) * (0.6F))) * (0.8F)) * (var3)) * (0.5F)) / (var7);
		this.rightLeg.xRot = ((float)(Math.toRadians(10D))) + ((((Mth.cos((var2) * (0.6F))) * (0.7F)) * (var3)) / (var7));
		this.leftLeg.xRot = ((float)(Math.toRadians(10D))) + ((((Mth.cos(((var2) * (0.6F)) + (3.1415927F))) * (0.7F)) * (var3)) / (var7));
		if (var1.isCrouching()){
			this.body.y = 17F;
			this.body.z = 5F;
			this.body.xRot = 0.1F;
			this.head.y = 3F;
			this.head.z = -2F;
			this.leftArm.xRot = 0.3F;
			this.leftArm.y = 4F;
			this.leftArm.z = 0F;
			this.rightArm.xRot = 0.3F;
			this.rightArm.y = 4F;
			this.rightArm.z = 0F;
		}
		this.setupAttackAnimation(var1,var4);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		super.renderToBuffer(var1,var2,var3,var4,var5);
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		super.translateToHand(var1,var2);
		var2.translate(((var1) == (HumanoidArm.RIGHT)) ? (-0.1D) : (0.1D),0.35D,-0.1D);
	}
	public boolean renderFirstPersonArm(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5, HumanoidArm var6, boolean var7) {
		var1.translate(0D,-0.3D,-0.3D);
		var1.mulPose(Axis.XP.rotationDegrees(20F));
		return super.renderFirstPersonArm(var1,var2,var3,var4,var5,var6,var7);
	}
}