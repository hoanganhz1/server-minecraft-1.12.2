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

public class BrachiosaurusHeavyArmorModel<T extends LivingEntity> extends MorphArmorModel<T> {
	// Fields
	public static final ModelLayerLocation INNER_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","brachio_heavy_inner_armor"),"main");
	public static final ModelLayerLocation OUTER_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","brachio_heavy_outer_armor"),"main");

	public BrachiosaurusHeavyArmorModel(ModelPart var1) {
		super(var1);
	}
	public static LayerDefinition createLayer(CubeDeformation var0) {
		MeshDefinition var1 = MorphArmorModel.createBodyLayer(var0);
		PartDefinition var2 = var1.getRoot();
		var2.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-14.5F,-11.25F,8F,8F,8F,new CubeDeformation(-0.5F)),PartPose.offset(0F,-2F,-5.75F));
		var2.addOrReplaceChild("body",CubeListBuilder.create().texOffs(16,16).addBox(-4F,-6F,-0.5F,8F,12F,4F,var0.extend(5F,5F,7F)),PartPose.offset(0F,6F,-3F));
		var2.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(40,16).mirror().addBox(0.6083F,-0.225F,-3.75F,4F,12F,4F,new CubeDeformation(2F)).mirror(false),PartPose.offsetAndRotation(7F,-1.5F,1.5F,0F,0F,-0.2618F));
		var2.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(40,16).addBox(-4.6083F,-0.225F,-3.75F,4F,12F,4F,new CubeDeformation(2F)),PartPose.offsetAndRotation(-7F,-1.5F,1.5F,0F,0F,0.2618F));
		var2.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(0,16).mirror().addBox(-2F,2.6F,-2.75F,4F,12F,4F,new CubeDeformation(1.5F)).mirror(false),PartPose.offset(6.5F,8F,-1F));
		var2.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(0,16).addBox(-2F,2.6F,-2.75F,4F,12F,4F,new CubeDeformation(1.5F)),PartPose.offset(-6.5F,8F,-1F));
		return LayerDefinition.create(var1,64,32);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		super.renderToBuffer(var1,var2,var3,var4,var5);
	}
}