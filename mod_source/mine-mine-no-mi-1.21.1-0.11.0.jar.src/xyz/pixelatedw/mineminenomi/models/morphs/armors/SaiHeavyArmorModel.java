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

public class SaiHeavyArmorModel<T extends LivingEntity> extends MorphArmorModel<T> {
	// Fields
	public static final ModelLayerLocation INNER_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","sai_heavy_inner_armor"),"main");
	public static final ModelLayerLocation OUTER_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","sai_heavy_outer_armor"),"main");

	public SaiHeavyArmorModel(ModelPart var1) {
		super(var1);
	}
	public static LayerDefinition createLayer(CubeDeformation var0) {
		MeshDefinition var1 = MorphArmorModel.createBodyLayer(var0);
		PartDefinition var2 = var1.getRoot();
		var2.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-8.9637F,-6.8317F,8F,8F,8F,var0.extend(0F)),PartPose.offset(0F,0F,1F));
		var2.addOrReplaceChild("body",CubeListBuilder.create().texOffs(16,16).addBox(-4F,-14.2424F,-2.3922F,8F,12F,4F,var0.extend(3.7F)),PartPose.offsetAndRotation(0F,15F,3F,0.1745F,0F,0F));
		var2.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(40,16).mirror().addBox(-0.25F,-0.5014F,-2.0309F,4F,10F,4F,var0.extend(1.2F)).mirror(false),PartPose.offsetAndRotation(7.25F,1F,1F,0.0873F,0F,0F));
		var2.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(40,16).addBox(-3.75F,-0.5014F,-2.0309F,4F,10F,4F,var0.extend(1.2F)),PartPose.offsetAndRotation(-7.25F,1F,1F,0.0873F,0F,0F));
		var2.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(0,16).mirror().addBox(-2F,-2.5F,-1F,4F,12F,4F,var0.extend(1.1F)).mirror(false),PartPose.offset(4.5F,13.5F,1F));
		var2.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(0,16).addBox(-2F,-2.5F,-1F,4F,12F,4F,var0.extend(1.1F)),PartPose.offset(-4.5F,13.5F,1F));
		return LayerDefinition.create(var1,64,32);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		ModelPart var6 = this.head;
		var6.xRot = (var6.xRot) + (0.6545F);
		this.body.xRot = 0.1745F;
		ModelPart var7 = this.leftLeg;
		var7.xRot = (var7.xRot) - (0.1745F);
		ModelPart var8 = this.rightLeg;
		var8.xRot = (var8.xRot) - (0.1745F);
		super.renderToBuffer(var1,var2,var3,var4,var5);
	}
}