package xyz.pixelatedw.mineminenomi.models.morphs.armors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

public class LeopardHeavyArmorModel<T extends LivingEntity> extends MorphArmorModel<T> {
	// Fields
	public static final ModelLayerLocation INNER_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","leopard_heavy_inner_armor"),"main");
	public static final ModelLayerLocation OUTER_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","leopard_heavy_outer_armor"),"main");

	public LeopardHeavyArmorModel(ModelPart var1) {
		super(var1);
	}
	public static LayerDefinition createLayer(CubeDeformation var0) {
		MeshDefinition var1 = MorphArmorModel.createBodyLayer(var0);
		PartDefinition var2 = var1.getRoot();
		var2.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-8F,-7.5F,8F,8F,8F,var0.extend(0F)),PartPose.offset(0F,-6F,0F));
		var2.addOrReplaceChild("body",CubeListBuilder.create().texOffs(16,16).addBox(-4F,-10.3194F,-3.7025F,8F,11F,4F,var0.extend(3.51F)),PartPose.offsetAndRotation(0F,4F,3F,0.1396F,0F,0F));
		var2.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(40,16).mirror().addBox(0F,-0.5128F,-2.1958F,4F,10F,4F,var0.extend(1.3F)).mirror(false),PartPose.offsetAndRotation(8F,-6F,0F,0.1309F,0F,0F));
		var2.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(40,16).addBox(-4F,-0.5128F,-2.1958F,4F,10F,4F,var0.extend(1.3F)),PartPose.offsetAndRotation(-8F,-6F,0F,0.1309F,0F,0F));
		var2.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(5,17).addBox(2.75F,-2.1483F,-3.7123F,0F,9F,3F,new CubeDeformation(0.01F)).texOffs(6,17).addBox(2.75F,-2.1483F,-1.2123F,0F,9F,3F,new CubeDeformation(0.011F)).texOffs(1,17).addBox(-1.75F,-2.1483F,-3.7123F,0F,9F,3F,new CubeDeformation(0.011F)).texOffs(2,17).mirror().addBox(-1.75F,-2.1483F,-1.2123F,0F,9F,3F,new CubeDeformation(0.01F)).mirror(false).texOffs(4,20).mirror().addBox(-0.25F,-2.1483F,-3.7123F,3F,9F,0F,new CubeDeformation(0.011F)).mirror(false).texOffs(6,20).mirror().addBox(-1.75F,-2.1483F,-3.7123F,2F,9F,0F,new CubeDeformation(0.01F)).mirror(false).texOffs(9,20).mirror().addBox(-1.8F,-2.1483F,1.7877F,3F,9F,0F,new CubeDeformation(0.011F)).mirror(false).texOffs(10,20).mirror().addBox(-0.2F,-2.1483F,1.7877F,3F,9F,0F,new CubeDeformation(0.01F)).mirror(false),PartPose.offsetAndRotation(3.5F,9F,2F,-0.3491F,0F,0F));
		var2.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(5,17).mirror().addBox(-2.75F,-2.1483F,-3.7123F,0F,9F,3F,new CubeDeformation(0.01F)).mirror(false).texOffs(6,17).mirror().addBox(-2.75F,-2.1483F,-1.2123F,0F,9F,3F,new CubeDeformation(0.011F)).mirror(false).texOffs(1,17).mirror().addBox(1.75F,-2.1483F,-3.7123F,0F,9F,3F,new CubeDeformation(0.011F)).mirror(false).texOffs(2,17).addBox(1.75F,-2.1483F,-1.2123F,0F,9F,3F,new CubeDeformation(0.01F)).texOffs(4,20).addBox(-2.75F,-2.1483F,-3.7123F,3F,9F,0F,new CubeDeformation(0.011F)).texOffs(6,20).addBox(-0.25F,-2.1483F,-3.7123F,2F,9F,0F,new CubeDeformation(0.01F)).texOffs(9,20).addBox(-1.2F,-2.1483F,1.7877F,3F,9F,0F,new CubeDeformation(0.011F)).texOffs(10,20).addBox(-2.8F,-2.1483F,1.7877F,3F,9F,0F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(-3.5F,9F,2F,-0.3491F,0F,0F));
		return LayerDefinition.create(var1,64,32);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		super.setupAnim(var1,var2,var3,var4,var5,var6);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		super.renderToBuffer(var1,var2,var3,var4,var5);
	}
}