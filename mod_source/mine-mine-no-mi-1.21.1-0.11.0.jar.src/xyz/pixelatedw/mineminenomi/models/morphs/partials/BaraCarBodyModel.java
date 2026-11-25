package xyz.pixelatedw.mineminenomi.models.morphs.partials;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
import net.minecraft.world.entity.LivingEntity;

public class BaraCarBodyModel<T extends LivingEntity> extends HumanoidModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","bara_car_body"),"main");

	public BaraCarBodyModel(ModelPart var1) {
		super(var1);
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		PartDefinition var1 = var0.getRoot();
		var1.addOrReplaceChild("right_pants",CubeListBuilder.create().texOffs(0,32).addBox(-2F,0F,-2F,4F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(-3.5F,20F,4.75F,1.7279F,-0.2618F,0.1745F));
		var1.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(0,16).addBox(-2F,0F,-2F,4F,12F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-3.5F,20F,4.75F,1.7279F,-0.2618F,0.1745F));
		var1.addOrReplaceChild("left_pants",CubeListBuilder.create().texOffs(0,48).addBox(-2F,0F,-2F,4F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(3.5F,20F,4.75F,1.7279F,0.2618F,-0.1745F));
		var1.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(16,48).addBox(-2F,0F,-2F,4F,12F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(3.5F,20F,4.75F,1.7279F,0.2618F,-0.1745F));
		var1.addOrReplaceChild("right_sleeve",CubeListBuilder.create().texOffs(40,32).addBox(-3F,-2F,-2F,4F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(-5F,20F,-6.5F,-1.309F,0F,0F));
		var1.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(40,16).addBox(-3F,-2F,-2F,4F,12F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-5F,20F,-6.5F,-1.309F,0F,0F));
		var1.addOrReplaceChild("left_sleeve",CubeListBuilder.create().texOffs(48,48).addBox(-1F,-2F,-2F,4F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(5F,20F,-6.5F,-1.309F,0F,0F));
		var1.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(32,48).addBox(-1F,-2F,-2F,4F,12F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(5F,20F,-6.5F,-1.309F,0F,0F));
		var1.addOrReplaceChild("jacket",CubeListBuilder.create().texOffs(16,32).addBox(-4F,0F,-2F,8F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(0F,20F,-7F,1.5708F,0F,0F));
		var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(16,16).addBox(-4F,0F,-2F,8F,12F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,20F,-7F,1.5708F,0F,0F));
		var1.addOrReplaceChild("headwear",CubeListBuilder.create().texOffs(32,0).addBox(-4F,-8F,-4F,8F,8F,8F,new CubeDeformation(0.5F)),PartPose.offset(0F,18F,0F));
		var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-8F,-4F,8F,8F,8F,new CubeDeformation(0F)),PartPose.offset(0F,18F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		super.renderToBuffer(var1,var2,var3,var4,var5);
	}
}