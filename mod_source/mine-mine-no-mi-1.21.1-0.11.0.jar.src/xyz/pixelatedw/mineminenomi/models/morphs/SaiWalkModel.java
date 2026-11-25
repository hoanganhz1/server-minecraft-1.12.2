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

public class SaiWalkModel<T extends LivingEntity> extends QuadrupedMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","sai_walk"),"main");
	private final ModelPart back;
	private final ModelPart tail;
	private final ModelPart tail2;
	private final ModelPart tail3;
	private final ModelPart lowerbackleftleg;
	private final ModelPart backleftfoot;
	private final ModelPart lowerbackrightleg;
	private final ModelPart backrightfoot;
	private final ModelPart leftLowerFrontLeg;
	private final ModelPart leftFoot;
	private final ModelPart rightLowerFrontLeg;
	private final ModelPart rightFoot;
	private final ModelPart head2;
	private final ModelPart leftEar;
	private final ModelPart rightEar;
	private final ModelPart mouth;
	private final ModelPart frontHorn;
	private final ModelPart frontUpperHorn;
	private final ModelPart backHorn;
	private final ModelPart backUpperHorn;

	public SaiWalkModel(ModelPart var1) {
		super(var1);
		this.back = this.body.getChild("back");
		this.tail = this.back.getChild("tail");
		this.tail2 = this.tail.getChild("tail2");
		this.tail3 = this.tail2.getChild("tail3");
		this.lowerbackleftleg = this.leftHindLeg.getChild("lowerbackleftleg");
		this.backleftfoot = this.lowerbackleftleg.getChild("backleftfoot");
		this.lowerbackrightleg = this.rightHindLeg.getChild("lowerbackrightleg");
		this.backrightfoot = this.lowerbackrightleg.getChild("backrightfoot");
		this.leftLowerFrontLeg = this.leftFrontLeg.getChild("leftLowerFrontLeg");
		this.leftFoot = this.leftLowerFrontLeg.getChild("leftFoot");
		this.rightLowerFrontLeg = this.rightFrontLeg.getChild("rightLowerFrontLeg");
		this.rightFoot = this.rightLowerFrontLeg.getChild("rightFoot");
		this.head2 = this.head.getChild("head2");
		this.leftEar = this.head2.getChild("leftEar");
		this.rightEar = this.head2.getChild("rightEar");
		this.mouth = this.head2.getChild("mouth");
		this.frontHorn = this.mouth.getChild("frontHorn");
		this.frontUpperHorn = this.frontHorn.getChild("frontUpperHorn");
		this.backHorn = this.mouth.getChild("backHorn");
		this.backUpperHorn = this.backHorn.getChild("backUpperHorn");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = QuadrupedModel.createBodyMesh(0,CubeDeformation.NONE);
		PartDefinition var1 = var0.getRoot();
		var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,0).addBox(-6.5F,-4.1649F,-2.8546F,13F,10F,11F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,10.5525F,-5.7809F,-0.0873F,0F,0F)).addOrReplaceChild("back",CubeListBuilder.create().texOffs(0,21).addBox(-5.5F,-5.1071F,-1.6826F,11F,9F,11F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,1.6509F,7.9088F,0.0873F,0F,0F)).addOrReplaceChild("tail",CubeListBuilder.create().texOffs(0,22).addBox(-1F,-0.2701F,-0.5734F,2F,3F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-4.3698F,8.7659F,0.2618F,0F,0F)).addOrReplaceChild("tail2",CubeListBuilder.create().texOffs(0,22).addBox(-1F,2.7299F,0.4266F,2F,3F,1F,new CubeDeformation(0F)),PartPose.offset(0F,0F,-1F)).addOrReplaceChild("tail3",CubeListBuilder.create().texOffs(0,26).addBox(-1F,5.7299F,0.4266F,2F,2F,1F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		var1.addOrReplaceChild("left_hind_leg",CubeListBuilder.create().texOffs(49,0).addBox(-2.5F,-1.25F,-3F,5F,10F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(4F,10.8132F,7.4987F,0.0873F,0F,0F)).addOrReplaceChild("lowerbackleftleg",CubeListBuilder.create().texOffs(48,17).addBox(-2F,-0.9317F,-2.1749F,4F,5F,4F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,8.6102F,0.1315F,-0.1309F,0F,0F)).addOrReplaceChild("backleftfoot",CubeListBuilder.create().texOffs(66,16).addBox(-2F,-0.5625F,-2.75F,4F,2F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,3.2399F,-0.7598F,0.0436F,0F,0F));
		var1.addOrReplaceChild("right_hind_leg",CubeListBuilder.create().texOffs(49,0).mirror().addBox(-2.5F,-1.25F,-3F,5F,10F,6F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-4F,10.8132F,7.4987F,0.0873F,0F,0F)).addOrReplaceChild("lowerbackrightleg",CubeListBuilder.create().texOffs(48,17).mirror().addBox(-2F,-0.9317F,-2.1749F,4F,5F,4F,new CubeDeformation(0.01F)).mirror(false),PartPose.offsetAndRotation(0F,8.6102F,0.1315F,-0.1309F,0F,0F)).addOrReplaceChild("backrightfoot",CubeListBuilder.create().texOffs(66,16).mirror().addBox(-2F,-0.5625F,-2.75F,4F,2F,5F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0F,3.2399F,-0.7598F,0.0436F,0F,0F));
		var1.addOrReplaceChild("left_front_leg",CubeListBuilder.create().texOffs(49,0).addBox(0.1039F,-1.0906F,-2.18F,5F,10F,6F,new CubeDeformation(0F)),PartPose.offset(2.3961F,10.0401F,-5.4796F)).addOrReplaceChild("leftLowerFrontLeg",CubeListBuilder.create().texOffs(48,17).addBox(-2F,-1.4802F,-2.0521F,4F,6F,4F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(2.6039F,8.5295F,0.9206F,-0.1309F,0F,0F)).addOrReplaceChild("leftFoot",CubeListBuilder.create().texOffs(66,16).addBox(-2F,-0.4375F,-2.8125F,4F,2F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,3.9316F,-0.5312F,0.1309F,0F,0F));
		var1.addOrReplaceChild("right_front_leg",CubeListBuilder.create().texOffs(49,0).mirror().addBox(-5.1039F,-1.0906F,-2.18F,5F,10F,6F,new CubeDeformation(0F)).mirror(false),PartPose.offset(-2.3961F,10.0401F,-5.4796F)).addOrReplaceChild("rightLowerFrontLeg",CubeListBuilder.create().texOffs(48,17).mirror().addBox(-2F,-1.4802F,-2.0521F,4F,6F,4F,new CubeDeformation(0.01F)).mirror(false),PartPose.offsetAndRotation(-2.6039F,8.5295F,0.9206F,-0.1309F,0F,0F)).addOrReplaceChild("rightFoot",CubeListBuilder.create().texOffs(66,16).mirror().addBox(-2F,-0.4375F,-2.8125F,4F,2F,5F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0F,3.9316F,-0.5312F,0.1309F,0F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(34,41).addBox(-3F,-4.3982F,-4.3874F,6F,8F,8F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,10.1988F,-7.475F,-0.5236F,0F,0F)).addOrReplaceChild("head2",CubeListBuilder.create().texOffs(45,28).addBox(-3.5F,-4.1989F,-3.9161F,7F,8F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-0.6032F,-3.3915F,0.4363F,0F,0F));
		var2.addOrReplaceChild("leftEar",CubeListBuilder.create(),PartPose.offsetAndRotation(2.4911F,-4.2587F,-0.3525F,0.4363F,0F,0.1745F)).addOrReplaceChild("leftear1_r1",CubeListBuilder.create().texOffs(0,6).addBox(-1F,-1.5F,-0.5F,2F,3F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1F,-0.3125F,0F,0F,0F));
		var2.addOrReplaceChild("rightEar",CubeListBuilder.create(),PartPose.offsetAndRotation(-2.4911F,-4.2587F,-0.3525F,0.4363F,0F,-0.1745F)).addOrReplaceChild("rightear_r1",CubeListBuilder.create().texOffs(0,6).mirror().addBox(-1F,-1.5F,-0.5F,2F,3F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0F,-1F,-0.3125F,0F,0F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("mouth",CubeListBuilder.create().texOffs(0,41).addBox(-3.5F,-2.1467F,-8.5031F,7F,6F,10F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,-0.6664F,-1.7917F,0.3491F,0F,0F));
		var3.addOrReplaceChild("frontHorn",CubeListBuilder.create().texOffs(0,0).addBox(-1F,-1.0616F,-0.9891F,2F,2F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1.8739F,-6.9549F,0.1745F,0F,0F)).addOrReplaceChild("frontUpperHorn",CubeListBuilder.create().texOffs(0,0).addBox(-1F,-1.5F,-1F,2F,3F,2F,new CubeDeformation(-0.1F)),PartPose.offsetAndRotation(0F,-2.0914F,0.3156F,-0.3054F,0F,0F));
		var3.addOrReplaceChild("backHorn",CubeListBuilder.create().texOffs(0,0).addBox(-1F,-1.4204F,-0.7742F,2F,2F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1.9626F,-4.7885F,0.0873F,0F,0F)).addOrReplaceChild("backUpperHorn",CubeListBuilder.create().texOffs(0,0).addBox(-1F,-0.8625F,-0.3546F,2F,1F,2F,new CubeDeformation(-0.1F)),PartPose.offsetAndRotation(0F,-1.2778F,-0.4983F,-0.2182F,0F,0F));
		return LayerDefinition.create(var0,128,128);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		this.tail.xRot = 0.3F;
		this.rightFrontLeg.xRot = ((Mth.cos((var2) * (0.77F))) * (0.5F)) * (var3);
		this.leftFrontLeg.xRot = ((Mth.cos((var2) * (0.75F))) * (0.5F)) * (var3);
		this.rightHindLeg.xRot = (0.15F) + (((Mth.cos((var2) * (0.78F))) * (0.5F)) * (var3));
		this.leftHindLeg.xRot = (0.15F) + (((Mth.cos((var2) * (0.76F))) * (0.5F)) * (var3));
		if (var1.isSprinting()){
			this.tail.xRot = (1.2F) + (((Mth.cos((var2) * (0.6662F))) * (0.2F)) * (var3));
		}
		this.setupAttackAnimation(var1,var4);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		super.renderToBuffer(var1,var2,var3,var4,var5);
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		this.head.translateAndRotate(var2);
		var2.mulPose(Axis.XP.rotationDegrees(25F));
		var2.mulPose(Axis.YN.rotationDegrees(90F));
		var2.mulPose(Axis.ZP.rotationDegrees(75F));
		var2.translate(0.05D,0.15D,-0.1D);
	}
	public ModelPart getHead() {
		return this.head;
	}
}