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

public class AxolotlHeavyArmorModel<T extends LivingEntity> extends MorphArmorModel<T> {
	// Fields
	public static final ModelLayerLocation INNER_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","axolotl_heavy_inner_armor"),"main");
	public static final ModelLayerLocation OUTER_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","axolotl_heavy_outer_armor"),"main");

	public AxolotlHeavyArmorModel(ModelPart var1) {
		super(var1);
	}
	public static LayerDefinition createLayer(CubeDeformation var0) {
		MeshDefinition var1 = MorphArmorModel.createBodyLayer(var0);
		PartDefinition var2 = var1.getRoot();
		var2.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-8.0387F,-3.0517F,8F,8F,8F,new CubeDeformation(0.4F)),PartPose.offsetAndRotation(0F,-7F,-4F,0.3491F,0F,0F));
		var2.addOrReplaceChild("body",CubeListBuilder.create().texOffs(16,16).addBox(-8F,-30.0967F,-26.953F,8F,12F,4F,var0.extend(8.1F,6.8F,6.8F)),PartPose.offsetAndRotation(4F,14F,35F,0.4363F,0F,0F));
		var2.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(40,16).mirror().addBox(3.3465F,0.7124F,-1.0764F,4F,12F,4F,new CubeDeformation(4.5F)).mirror(false),PartPose.offsetAndRotation(12F,-4F,-1F,-0.3491F,0F,-0.1309F));
		var2.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(40,16).addBox(-7.3465F,0.7124F,-1.0764F,4F,12F,4F,new CubeDeformation(4.5F)),PartPose.offsetAndRotation(-12F,-4F,-1F,-0.3491F,0F,0.1309F));
		var2.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(0,16).mirror().addBox(-2F,-2.3954F,-0.8741F,4F,6F,4F,new CubeDeformation(1.6F)).mirror(false),PartPose.offsetAndRotation(7F,14.25F,3F,-0.829F,0F,0F)).addOrReplaceChild("left_boot",CubeListBuilder.create().texOffs(0,23).mirror().addBox(3F,5.5F,-0.75F,4F,5F,4F,new CubeDeformation(1.51F)).mirror(false).texOffs(0,16).mirror().addBox(3F,10.5F,-0.75F,4F,0F,4F,new CubeDeformation(1.51F)).mirror(false),PartPose.offsetAndRotation(-5F,0.8546F,-3.8741F,0.829F,0F,0F));
		var2.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(0,16).addBox(-2F,-2.3954F,-0.8741F,4F,6F,4F,new CubeDeformation(1.6F)),PartPose.offsetAndRotation(-7F,14.25F,3F,-0.829F,0F,0F)).addOrReplaceChild("right_boot",CubeListBuilder.create().texOffs(0,23).addBox(-7F,5.5F,-0.75F,4F,5F,4F,new CubeDeformation(1.51F)).texOffs(0,16).addBox(-7F,10.5F,-0.75F,4F,0F,4F,new CubeDeformation(1.51F)),PartPose.offsetAndRotation(5F,0.8546F,-3.8741F,0.829F,0F,0F));
		return LayerDefinition.create(var1,64,32);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		ModelPart var6 = this.rightArm;
		var6.zRot = (var6.zRot) + (0.1309F);
		ModelPart var7 = this.leftArm;
		var7.zRot = (var7.zRot) - (0.1309F);
		ModelPart var8 = this.rightLeg;
		var8.xRot = (var8.xRot) - (0.5672F);
		ModelPart var9 = this.leftLeg;
		var9.xRot = (var9.xRot) - (0.5672F);
		ModelPart var10 = this.body;
		var10.xRot = (var10.xRot) + (0.08727F);
		super.renderToBuffer(var1,var2,var3,var4,var5);
	}
}