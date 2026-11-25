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
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.QuadrupedMorphModel;

public class ZouGuardModel<T extends LivingEntity> extends QuadrupedMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","zou_guard"),"main");
	private final ModelPart tail;
	private final ModelPart tail2;
	private final ModelPart body4;
	private final ModelPart body5;
	private final ModelPart body3;
	private final ModelPart body2;
	private final ModelPart snout;
	private final ModelPart snout2;
	private final ModelPart snout3;
	private final ModelPart rightEar;
	private final ModelPart leftEar;
	private final ModelPart leftTusk;
	private final ModelPart leftTusk2;
	private final ModelPart rightTusk;
	private final ModelPart rightTusk2;

	public ZouGuardModel(ModelPart var1) {
		super(var1);
		this.tail = this.body.getChild("tail");
		this.tail2 = this.tail.getChild("tail2");
		this.body4 = this.body.getChild("body4");
		this.body5 = this.body.getChild("body5");
		this.body3 = this.body.getChild("body3");
		this.body2 = this.body.getChild("body2");
		this.snout = this.head.getChild("snout");
		this.snout2 = this.snout.getChild("snout2");
		this.snout3 = this.snout2.getChild("snout3");
		this.rightEar = this.head.getChild("rightEar");
		this.leftEar = this.head.getChild("leftEar");
		this.leftTusk = this.head.getChild("leftTusk");
		this.leftTusk2 = this.leftTusk.getChild("leftTusk2");
		this.rightTusk = this.head.getChild("rightTusk");
		this.rightTusk2 = this.rightTusk.getChild("rightTusk2");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = QuadrupedModel.createBodyMesh(0,CubeDeformation.NONE);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(36,25).addBox(-7.5F,-12F,-8F,15F,15F,24F,new CubeDeformation(0F)),PartPose.offset(0F,9F,-4F));
		var2.addOrReplaceChild("tail",CubeListBuilder.create().texOffs(108,0).addBox(-0.5F,0F,-0.5F,1F,6F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-7F,15.75F,0.3491F,0F,0F)).addOrReplaceChild("tail2",CubeListBuilder.create().texOffs(113,0).addBox(-1F,0F,-1F,2F,3F,2F,new CubeDeformation(0F)),PartPose.offset(0F,5.5F,0F));
		var2.addOrReplaceChild("body4",CubeListBuilder.create().texOffs(61,49).addBox(-6.5F,-7F,-0.5F,13F,14F,1F,new CubeDeformation(0F)),PartPose.offset(0F,-4.5F,16F));
		var2.addOrReplaceChild("body5",CubeListBuilder.create().texOffs(61,49).addBox(-6.5F,-7F,-0.5F,13F,14F,1F,new CubeDeformation(0F)),PartPose.offset(0F,-4.5F,-8.25F));
		var2.addOrReplaceChild("body3",CubeListBuilder.create().texOffs(36,0).addBox(-6.5F,-1F,-11F,13F,2F,22F,new CubeDeformation(0F)),PartPose.offset(0F,2.5F,4F));
		var2.addOrReplaceChild("body2",CubeListBuilder.create().texOffs(36,0).addBox(-6.5F,-13F,-7F,13F,2F,22F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		var1.addOrReplaceChild("right_hind_leg",CubeListBuilder.create().texOffs(0,46).addBox(-2.25F,0F,-2.75F,5F,13F,5F,new CubeDeformation(0F)),PartPose.offset(-5F,11F,9.5F));
		var1.addOrReplaceChild("left_hind_leg",CubeListBuilder.create().texOffs(0,46).addBox(-2.75F,0F,-2.75F,5F,13F,5F,new CubeDeformation(0F)),PartPose.offset(5F,11F,9.5F));
		var1.addOrReplaceChild("right_front_leg",CubeListBuilder.create().texOffs(0,46).addBox(-2.25F,0F,-2.25F,5F,13F,5F,new CubeDeformation(0F)),PartPose.offset(-5F,11F,-9.5F));
		var1.addOrReplaceChild("left_front_leg",CubeListBuilder.create().texOffs(0,46).addBox(-2.75F,0F,-2.25F,5F,13F,5F,new CubeDeformation(0F)),PartPose.offset(5F,11F,-9.5F));
		PartDefinition var3 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-6F,-7F,8F,11F,9F,new CubeDeformation(0F)),PartPose.offset(0F,-0.875F,-12.4375F));
		var3.addOrReplaceChild("snout",CubeListBuilder.create().texOffs(108,8).addBox(-2F,-3.671F,-2.9673F,4F,7F,4F,new CubeDeformation(0.5F)),PartPose.offsetAndRotation(0F,4F,-5.5F,-0.3054F,0F,0F)).addOrReplaceChild("snout2",CubeListBuilder.create().texOffs(108,8).addBox(-2F,-0.875F,-1.625F,4F,7F,4F,new CubeDeformation(0.2F)),PartPose.offsetAndRotation(0F,3.602F,-1.3293F,0.2618F,0F,0F)).addOrReplaceChild("snout3",CubeListBuilder.create().texOffs(108,20).addBox(-2F,-0.9353F,-1.554F,4F,6F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,6.4876F,-0.0527F,0.1745F,0F,0F));
		var3.addOrReplaceChild("rightEar",CubeListBuilder.create().texOffs(0,22).addBox(-8F,-4.5F,-0.5F,8F,9F,0F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2.5F,-2.5F,-2.75F,-0.1368F,0.4707F,-0.2946F));
		var3.addOrReplaceChild("leftEar",CubeListBuilder.create().texOffs(0,22).mirror().addBox(0F,-4.5F,-0.5F,8F,9F,0F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(2.5F,-2.5F,-2.75F,-0.1368F,-0.4707F,0.2946F));
		var3.addOrReplaceChild("leftTusk",CubeListBuilder.create().texOffs(0,33).addBox(-0.5F,0F,-0.5F,1F,4F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2.3F,3F,-6F,-0.3491F,0.2094F,0F)).addOrReplaceChild("leftTusk2",CubeListBuilder.create().texOffs(0,33).addBox(-0.461F,-0.1505F,-0.5192F,1F,4F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.039F,4.0647F,0.0004F,-0.1745F,0F,0F));
		var3.addOrReplaceChild("rightTusk",CubeListBuilder.create().texOffs(0,33).mirror().addBox(-0.5F,0F,-0.5F,1F,4F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(2.3F,3F,-6F,-0.3491F,-0.2094F,0F)).addOrReplaceChild("rightTusk2",CubeListBuilder.create().texOffs(0,33).mirror().addBox(-0.539F,-0.1505F,-0.5192F,1F,4F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0.039F,4.0647F,0.0004F,-0.1745F,0F,0F));
		return LayerDefinition.create(var0,128,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		boolean var7 = var1.isVisuallySwimming();
		this.head.yRot = (var5) * (0.017453292F);
		if ((this.swimAmount) > (0F)){
			if (var7){
				this.head.xRot = this.rotlerpRad(this.head.xRot,-0.7853982F,this.swimAmount);
			}else{
				this.head.xRot = this.rotlerpRad(this.head.xRot,(var6) * (0.017453292F),this.swimAmount);
			}
		}else{
			this.head.xRot = (var6) * (0.017453292F);
			if (((double)(this.head.xRot)) > (0.6D)){
				this.head.xRot = 0.6F;
			}
		}
		this.rightFrontLeg.xRot = ((Mth.cos((var2) * (0.6662F))) * (0.3F)) * (var3);
		this.leftFrontLeg.xRot = ((Mth.cos(((var2) * (0.6662F)) + (3.1415927F))) * (0.3F)) * (var3);
		this.rightHindLeg.xRot = ((Mth.cos((var2) * (0.6662F))) * (0.4F)) * (var3);
		this.leftHindLeg.xRot = ((Mth.cos(((var2) * (0.6662F)) + (3.1415927F))) * (0.4F)) * (var3);
		if (var1.isSprinting()){
			this.tail.xRot = (0.6F) + (((Mth.cos((var2) * (0.6662F))) * (0.2F)) * (var3));
			this.leftEar.yRot = (-0.3F) - (((Mth.cos((var2) * (0.6662F))) * (0.2F)) * (var3));
			this.rightEar.yRot = (0.3F) + (((Mth.cos((var2) * (0.6662F))) * (0.2F)) * (var3));
		}
		this.setupAttackAnimation(var1,var4);
		boolean var8 = var1.isFallFlying();
		if (var1 instanceof Player){
			var8 = (var8) | (((Player)(var1)).getAbilities().flying);
		}
		if (var8){
			this.rightEar.xRot = 1.2F;
			this.rightEar.yRot = 0.1F;
			this.rightEar.zRot = (Mth.cos(var4)) * (0.6F);
			this.leftEar.xRot = 1.2F;
			this.leftEar.yRot = -0.1F;
			this.leftEar.zRot = (-(Mth.cos(var4))) * (0.6F);
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		super.renderToBuffer(var1,var2,var3,var4,var5);
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		this.head.translateAndRotate(var2);
		var2.mulPose(Axis.ZP.rotationDegrees(90F));
		var2.mulPose(Axis.XP.rotationDegrees(260F));
		var2.translate(1D,-0.2D,0D);
	}
	public ModelPart getHead() {
		return this.head;
	}
}