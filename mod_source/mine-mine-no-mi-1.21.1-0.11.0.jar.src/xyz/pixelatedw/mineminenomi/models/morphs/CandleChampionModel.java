package xyz.pixelatedw.mineminenomi.models.morphs;

import com.google.common.collect.ImmutableSet;
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

public class CandleChampionModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","candle_champion"),"main");
	private final ModelPart rightBoot;
	private final ModelPart leftBoot;
	private final ModelPart leftGlove;
	private final ModelPart rightGlove;
	private final ModelPart upperBody;
	private final ModelPart spikes;

	public CandleChampionModel(ModelPart var1) {
		super(var1);
		this.rightBoot = this.rightLeg.getChild("RightBoot");
		this.leftBoot = this.leftLeg.getChild("LeftBoot");
		this.leftGlove = this.leftArm.getChild("LeftGlove");
		this.rightGlove = this.rightArm.getChild("RightGlove");
		this.upperBody = this.body.getChild("UpperBody");
		this.spikes = this.upperBody.getChild("Spikes");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot();
		var1.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(84,44).addBox(1.3139F,-2.0468F,-0.5179F,1F,7F,1F,new CubeDeformation(0F)),PartPose.offset(2.5449F,10.9997F,4.0179F)).addOrReplaceChild("RightBoot",CubeListBuilder.create().texOffs(22,83).addBox(-1.45F,-0.1625F,-1.4625F,3F,2F,3F,new CubeDeformation(0F)).texOffs(0,65).addBox(-2.95F,1.8375F,-2.9625F,6F,6F,6F,new CubeDeformation(0F)).texOffs(52,67).addBox(-2.95F,1.8375F,-10.9625F,6F,6F,6F,new CubeDeformation(0F)).texOffs(24,72).addBox(-2.45F,2.775F,-7.9625F,5F,5F,6F,new CubeDeformation(0F)).texOffs(56,79).addBox(-3.2F,2.8375F,-1.9625F,1F,4F,4F,new CubeDeformation(0F)),PartPose.offset(1.6551F,5.1628F,-0.0554F));
		var1.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(70,87).addBox(-2.3139F,-2.0468F,-0.5179F,1F,7F,1F,new CubeDeformation(0F)),PartPose.offset(-2.5449F,10.9997F,4.0179F)).addOrReplaceChild("LeftBoot",CubeListBuilder.create().texOffs(34,83).addBox(-1.55F,-0.1625F,-1.4625F,3F,2F,3F,new CubeDeformation(0F)).texOffs(68,20).addBox(-3.05F,1.8375F,-2.9625F,6F,6F,6F,new CubeDeformation(0F)).texOffs(68,32).addBox(-3.05F,1.8375F,-10.9625F,6F,6F,6F,new CubeDeformation(0F)).texOffs(0,77).addBox(-2.55F,2.775F,-7.9625F,5F,5F,6F,new CubeDeformation(0F)).texOffs(66,79).addBox(2.2F,2.8375F,-1.9625F,1F,4F,4F,new CubeDeformation(0F)),PartPose.offset(-1.6551F,5.1628F,-0.0554F));
		var1.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(52,60).addBox(-0.525F,-0.2F,-0.5F,1F,4F,1F,new CubeDeformation(0F)).texOffs(24,65).addBox(-0.525F,3.8F,-0.5F,1F,4F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(11.2F,-1.525F,4F,0F,0F,-0.5236F)).addOrReplaceChild("LeftGlove",CubeListBuilder.create().texOffs(76,78).addBox(-3.5F,3.8333F,-2F,1F,4F,4F,new CubeDeformation(0F)).texOffs(82,55).addBox(-1.5F,0.8333F,-1.5F,3F,2F,3F,new CubeDeformation(0F)).texOffs(58,55).addBox(-3F,2.8333F,-3F,6F,6F,6F,new CubeDeformation(0F)),PartPose.offset(-0.025F,5.9667F,0F));
		var1.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(46,72).addBox(-0.475F,-0.2F,-0.5F,1F,4F,1F,new CubeDeformation(0F)).texOffs(8,88).addBox(-0.475F,3.8F,-0.5F,1F,4F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-11.2F,-1.525F,4F,0F,0F,0.5236F)).addOrReplaceChild("RightGlove",CubeListBuilder.create().texOffs(82,60).addBox(-1.5F,0.8333F,-1.5F,3F,2F,3F,new CubeDeformation(0F)).texOffs(28,60).addBox(-3F,2.8333F,-3F,6F,6F,6F,new CubeDeformation(0F)).texOffs(46,79).addBox(2.5F,3.8333F,-2F,1F,4F,4F,new CubeDeformation(0F)),PartPose.offset(0.025F,5.9667F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(58,44).addBox(-2.5F,-0.5F,-4F,5F,3F,8F,new CubeDeformation(0F)).texOffs(58,9).addBox(-4F,-2F,-3.5F,8F,4F,7F,new CubeDeformation(0F)).texOffs(58,0).addBox(-4.5F,-2.1F,-4F,9F,1F,8F,new CubeDeformation(0F)).texOffs(76,67).addBox(-2.5F,-7.1F,-3F,5F,5F,6F,new CubeDeformation(0F)).texOffs(58,20).addBox(-2F,-3.7F,-4.01F,4F,3F,0F,new CubeDeformation(0F)),PartPose.offset(0F,11F,4F)).addOrReplaceChild("UpperBody",CubeListBuilder.create().texOffs(28,44).addBox(-3F,-6.84F,-4.45F,6F,7F,9F,new CubeDeformation(0F)).texOffs(0,0).addBox(-8.5F,-11.84F,-5.95F,17F,11F,12F,new CubeDeformation(0F)).texOffs(0,23).addBox(-4.5F,-12.84F,-6.95F,9F,10F,11F,new CubeDeformation(0F)).texOffs(40,23).addBox(-11.5F,-11.24F,-5.45F,3F,10F,11F,new CubeDeformation(0F)).texOffs(0,44).addBox(8.5F,-11.24F,-5.45F,3F,10F,11F,new CubeDeformation(0F)),PartPose.offset(0F,-6.26F,-0.05F)).addOrReplaceChild("Spikes",CubeListBuilder.create().texOffs(84,86).addBox(-11F,-1F,-7F,2F,2F,2F,new CubeDeformation(0F)).texOffs(86,82).addBox(-11F,-1F,5F,2F,2F,2F,new CubeDeformation(0F)).texOffs(11,87).addBox(-10.5F,-0.5F,7F,1F,1F,2F,new CubeDeformation(0F)).texOffs(11,87).addBox(-10.5F,-0.5F,-9F,1F,1F,2F,new CubeDeformation(0F)).texOffs(86,78).addBox(-11F,5F,-1F,2F,2F,2F,new CubeDeformation(0F)).texOffs(88,9).addBox(-10.5F,7F,-0.5F,1F,2F,1F,new CubeDeformation(0F)).texOffs(76,86).addBox(-11F,-7F,-1F,2F,2F,2F,new CubeDeformation(0F)).texOffs(84,52).addBox(-10.5F,-9F,-0.5F,1F,2F,1F,new CubeDeformation(0F)).texOffs(0,88).addBox(9F,-1F,-7F,2F,2F,2F,new CubeDeformation(0F)).texOffs(23,87).addBox(9.5F,-0.5F,-9F,1F,1F,2F,new CubeDeformation(0F)).texOffs(62,87).addBox(9F,-1F,5F,2F,2F,2F,new CubeDeformation(0F)).texOffs(19,87).addBox(9.5F,-0.5F,7F,1F,1F,2F,new CubeDeformation(0F)).texOffs(54,87).addBox(9F,5F,-1F,2F,2F,2F,new CubeDeformation(0F)).texOffs(16,88).addBox(9.5F,7F,-0.5F,1F,2F,1F,new CubeDeformation(0F)).texOffs(46,87).addBox(9F,-7F,-1F,2F,2F,2F,new CubeDeformation(0F)).texOffs(88,15).addBox(9.5F,-9F,-0.5F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offset(0F,-6.24F,0.05F));
		return LayerDefinition.create(var0,128,128);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		float var7 = 1F;
		this.rightArm.xRot = ((((Mth.cos(((var2) * (0.6F)) + (3.1415927F))) * (0.8F)) * (var3)) * (0.5F)) / (var7);
		this.leftArm.xRot = ((((Mth.cos((var2) * (0.6F))) * (0.8F)) * (var3)) * (0.5F)) / (var7);
		this.rightLeg.xRot = (((Mth.cos((var2) * (0.6F))) * (0.7F)) * (var3)) / (var7);
		this.leftLeg.xRot = (((Mth.cos(((var2) * (0.6F)) + (3.1415927F))) * (0.7F)) * (var3)) / (var7);
		this.setupAttackAnimation(var1,var4);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.rightLeg.render(var1,var2,var3,var4,var5);
		this.leftLeg.render(var1,var2,var3,var4,var5);
		this.leftArm.render(var1,var2,var3,var4,var5);
		this.rightArm.render(var1,var2,var3,var4,var5);
		this.body.render(var1,var2,var3,var4,var5);
	}
	protected Iterable<ModelPart> bodyParts() {
		ModelPart[] var1 = new ModelPart[]{this.rightGlove,this.leftLeg,this.rightLeg};
		return ImmutableSet.of(this.body,this.upperBody,this.spikes,this.leftArm,this.leftGlove,this.rightArm,var1);
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		super.translateToHand(var1,var2);
		var2.translate((0.05D) * ((double)(((var1) == (HumanoidArm.RIGHT)) ? (1F) : (-1F))),0.35D,0D);
	}
	public boolean renderFirstPersonArm(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5, HumanoidArm var6, boolean var7) {
		var1.translate((0.4D) * ((double)(((var6) == (HumanoidArm.RIGHT)) ? (1F) : (-1F))),0D,-0.1D);
		var1.mulPose(Axis.ZP.rotationDegrees(-20F));
		return super.renderFirstPersonArm(var1,var2,var3,var4,var5,var6,var7);
	}
}