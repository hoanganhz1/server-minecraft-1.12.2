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

public class ZouHeavyArmorModel<T extends LivingEntity> extends MorphArmorModel<T> {
	// Fields
	public static final ModelLayerLocation INNER_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","zou_heavy_inner_armor"),"main");
	public static final ModelLayerLocation OUTER_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","zou_heavy_outer_armor"),"main");

	public ZouHeavyArmorModel(ModelPart var1) {
		super(var1);
	}
	public static LayerDefinition createLayer(CubeDeformation var0) {
		MeshDefinition var1 = MorphArmorModel.createBodyLayer(var0);
		PartDefinition var2 = var1.getRoot();
		var2.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-7F,-6.5F,8F,8F,8F,new CubeDeformation(1F)),PartPose.offset(0F,-10F,-1.5F));
		var2.addOrReplaceChild("body",CubeListBuilder.create().texOffs(16,16).addBox(-4F,-12.5F,-1.95F,8F,12F,4F,var0.extend(6.5F)),PartPose.offset(0F,11F,1F));
		var2.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(40,16).mirror().addBox(0.0076F,-1.0868F,-2F,4F,8F,4F,new CubeDeformation(1.7F)).mirror(false),PartPose.offsetAndRotation(9.5F,-4F,1F,0F,0F,-0.1745F));
		var2.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(40,16).addBox(-4.0076F,-1.0868F,-2F,4F,8F,4F,new CubeDeformation(1.7F)),PartPose.offsetAndRotation(-9.5F,-4F,1F,0F,0F,0.1745F));
		var2.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(0,16).mirror().addBox(-2F,-0.5F,-2.65F,4F,12F,4F,new CubeDeformation(1.49F)).mirror(false),PartPose.offset(5F,11F,1F));
		var2.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(0,16).addBox(-2F,-0.5F,-2.65F,4F,12F,4F,new CubeDeformation(1.49F)),PartPose.offset(-5F,11F,1F));
		return LayerDefinition.create(var1,64,32);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		ModelPart var6 = this.body;
		var6.xRot = (var6.xRot) + (0.04363F);
		ModelPart var7 = this.leftLeg;
		var7.xRot = (var7.xRot) + (0.1396F);
		ModelPart var8 = this.rightLeg;
		var8.xRot = (var8.xRot) + (0.1396F);
		super.renderToBuffer(var1,var2,var3,var4,var5);
	}
}