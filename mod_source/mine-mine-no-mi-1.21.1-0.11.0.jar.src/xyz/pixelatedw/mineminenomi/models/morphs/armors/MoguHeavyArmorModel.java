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

public class MoguHeavyArmorModel<T extends LivingEntity> extends MorphArmorModel<T> {
	// Fields
	public static final ModelLayerLocation INNER_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","mogu_heavy_inner_armor"),"main");
	public static final ModelLayerLocation OUTER_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","mogu_heavy_outer_armor"),"main");

	public MoguHeavyArmorModel(ModelPart var1) {
		super(var1);
	}
	public static LayerDefinition createLayer(CubeDeformation var0) {
		MeshDefinition var1 = MorphArmorModel.createBodyLayer(var0);
		PartDefinition var2 = var1.getRoot();
		var2.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).addBox(-3.75F,-7F,-3.5F,8F,8F,8F,var0.extend(-0.8F)),PartPose.offset(-0.25F,7F,0F));
		var2.addOrReplaceChild("body",CubeListBuilder.create().texOffs(16,16).addBox(-4F,-0.7F,-2F,8F,12F,4F,var0.extend(1F,0F,1F)),PartPose.offset(0F,7F,2F));
		var2.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(40,16).mirror().addBox(-0.5434F,-1.2538F,-1.9F,4F,8F,4F,var0.extend(0.5F)).mirror(false),PartPose.offsetAndRotation(4.5F,7.5F,2F,0F,0F,-0.1745F));
		var2.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(40,16).addBox(-3.4566F,-1.2538F,-1.9F,4F,8F,4F,var0.extend(0.5F)),PartPose.offsetAndRotation(-4.5F,7.5F,2F,0F,0F,0.1745F));
		var2.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(0,16).mirror().addBox(-1.75F,-1F,-2F,4F,9F,4F,var0.extend(0.5F)).mirror(false),PartPose.offset(2.5F,15.5F,2F));
		var2.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(0,16).addBox(-2.25F,-1F,-2F,4F,9F,4F,var0.extend(0.5F)),PartPose.offset(-2.5F,15.5F,2F));
		return LayerDefinition.create(var1,64,32);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		super.renderToBuffer(var1,var2,var3,var4,var5);
	}
}