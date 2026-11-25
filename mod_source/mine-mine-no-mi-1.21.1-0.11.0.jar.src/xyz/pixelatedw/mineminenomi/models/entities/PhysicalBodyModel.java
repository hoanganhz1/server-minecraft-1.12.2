package xyz.pixelatedw.mineminenomi.models.entities;

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
import xyz.pixelatedw.mineminenomi.entities.PhysicalBodyEntity;

public class PhysicalBodyModel extends HumanoidModel<PhysicalBodyEntity> {
	// Fields
	public static final ModelLayerLocation WIDE_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","physical_body_wide"),"main");
	public static final ModelLayerLocation SLIM_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","physical_body_slim"),"main");

	public PhysicalBodyModel(ModelPart var1) {
		super(var1);
	}
	public static LayerDefinition createBodyLayer(boolean var0) {
		MeshDefinition var1 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var2 = var1.getRoot();
		float var3 = (var0) ? (-2F) : (-3F);
		float var4 = (var0) ? (3F) : (4F);
		var2.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-8F,-4F,8F,8F,8F,new CubeDeformation(0F)).texOffs(32,0).addBox(-4F,-8F,-4F,8F,8F,8F,new CubeDeformation(0.5F)),PartPose.offsetAndRotation(0F,12F,0F,0.7854F,0F,0F));
		var2.addOrReplaceChild("body",CubeListBuilder.create().texOffs(16,16).addBox(-4F,0F,-2F,8F,12F,4F,new CubeDeformation(0F)).texOffs(16,32).addBox(-4F,0F,-2F,8F,12F,4F,new CubeDeformation(0.25F)),PartPose.offset(0F,12F,0F));
		var2.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(40,16).addBox(var3,-2F,-2F,var4,12F,4F,new CubeDeformation(0F)).texOffs(40,32).addBox(var3,-2F,-2F,var4,12F,4F,new CubeDeformation(0.25F)),PartPose.offset(-5F,14F,0F));
		var2.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(32,48).addBox(-1F,-2F,-2F,var4,12F,4F,new CubeDeformation(0F)).texOffs(48,48).addBox(-1F,-2F,-2F,var4,12F,4F,new CubeDeformation(0.25F)),PartPose.offset(5F,14F,0F));
		var2.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(0,16).addBox(-2F,0F,-2F,4F,12F,4F,new CubeDeformation(0F)).texOffs(0,32).addBox(-2F,0F,-2F,4F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(-1.9F,22F,-2F,-1.5708F,0F,0F));
		var2.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(16,48).addBox(-2F,0F,-2F,4F,12F,4F,new CubeDeformation(0F)).texOffs(0,48).addBox(-2F,0F,-2F,4F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(1.9F,22F,-2F,-1.5708F,0F,0F));
		return LayerDefinition.create(var1,64,64);
	}
	public void setupAnim(PhysicalBodyEntity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.hat.copyFrom(this.head);
		super.renderToBuffer(var1,var2,var3,var4,var5);
	}
}