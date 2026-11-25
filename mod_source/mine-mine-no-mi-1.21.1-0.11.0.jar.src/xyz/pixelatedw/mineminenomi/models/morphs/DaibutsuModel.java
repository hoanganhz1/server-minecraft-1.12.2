package xyz.pixelatedw.mineminenomi.models.morphs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class DaibutsuModel extends PlayerModel<LivingEntity> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","daibutsu"),"main");

	public DaibutsuModel(ModelPart var1, boolean var2) {
		super(var1,var2);
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		PartDefinition var1 = var0.getRoot();
		var1.addOrReplaceChild("ear",CubeListBuilder.create(),PartPose.ZERO);
		var1.addOrReplaceChild("cloak",CubeListBuilder.create(),PartPose.ZERO);
		var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-8F,-4F,8F,8F,8F,new CubeDeformation(0F)),PartPose.offset(0F,-8F,0F));
		var1.addOrReplaceChild("headwear",CubeListBuilder.create().texOffs(32,0).addBox(-4F,-8F,-4F,8F,8F,8F,new CubeDeformation(0.5F)),PartPose.offset(0F,-8F,0F));
		var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(16,16).addBox(-4F,0F,-2F,8F,12F,4F,new CubeDeformation(3F)),PartPose.offset(0F,-5F,0F));
		var1.addOrReplaceChild("jacket",CubeListBuilder.create().texOffs(16,32).addBox(-4F,0F,-2F,8F,12F,4F,new CubeDeformation(3.25F)),PartPose.offset(0F,-5F,0F));
		var1.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(32,48).addBox(-1F,-2F,-2F,4F,12F,4F,new CubeDeformation(2F)),PartPose.offset(11F,-3F,0F));
		var1.addOrReplaceChild("left_sleeve",CubeListBuilder.create().texOffs(48,48).addBox(-1F,-2F,-2F,4F,12F,4F,new CubeDeformation(2.25F)),PartPose.offset(11F,-3F,0F));
		var1.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(40,16).addBox(-3F,-2F,-2F,4F,12F,4F,new CubeDeformation(2F)),PartPose.offset(-11.25F,-3F,0F));
		var1.addOrReplaceChild("right_sleeve",CubeListBuilder.create().texOffs(40,32).addBox(-3F,-2F,-2F,4F,12F,4F,new CubeDeformation(2.25F)),PartPose.offset(-11.25F,-3F,0F));
		var1.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(16,48).addBox(-2F,0F,-2F,4F,12F,4F,new CubeDeformation(1F)),PartPose.offset(3F,11F,0F));
		var1.addOrReplaceChild("left_pants",CubeListBuilder.create().texOffs(0,48).addBox(-2F,0F,-2F,4F,12F,4F,new CubeDeformation(1.25F)),PartPose.offset(3F,11F,0F));
		var1.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(0,16).addBox(-2F,0F,-2F,4F,12F,4F,new CubeDeformation(1F)),PartPose.offset(-3F,11F,0F));
		var1.addOrReplaceChild("right_pants",CubeListBuilder.create().texOffs(0,32).addBox(-2F,0F,-2F,4F,12F,4F,new CubeDeformation(1.25F)),PartPose.offset(-3F,11F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(LivingEntity var1, float var2, float var3, float var4, float var5, float var6) {
		super.setupAnim(var1,var2,var3,var4,var5,var6);
		this.head.y = -7F;
		this.hat.y = -7F;
		this.body.z = -1F;
		this.jacket.z = -1F;
		this.body.y = -4F;
		this.jacket.y = -4F;
		this.leftLeg.y = 11F;
		this.leftPants.y = 11F;
		this.rightLeg.y = 11F;
		this.rightPants.y = 11F;
		this.rightArm.x = -12.5F;
		this.rightSleeve.x = -12.5F;
		this.rightArm.y = -2F;
		this.rightSleeve.y = -2F;
		this.leftArm.x = 12.5F;
		this.leftSleeve.x = 12.5F;
		this.leftArm.y = -2F;
		this.leftSleeve.y = -2F;
		this.body.xScale = 1.25F;
		this.body.zScale = 1.5F;
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		super.renderToBuffer(var1,var2,var3,var4,var5);
	}
}