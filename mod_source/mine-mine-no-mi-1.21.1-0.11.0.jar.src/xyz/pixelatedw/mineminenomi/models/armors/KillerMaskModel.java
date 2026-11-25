package xyz.pixelatedw.mineminenomi.models.armors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidArmorModel;
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

public class KillerMaskModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","killer_mask"),"main");
	private final ModelPart mask;

	public KillerMaskModel(ModelPart var1) {
		super(var1);
		this.mask = var1.getChild("mask");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("mask",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-8F,-4F,8F,8F,8F,new CubeDeformation(0.6F)),PartPose.offset(0F,0F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("rightEar",CubeListBuilder.create().texOffs(0,0).addBox(-0.25F,-1.75F,-1F,1F,3F,3F,new CubeDeformation(0F)).texOffs(0,16).addBox(-0.75F,-1.25F,-0.5F,1F,2F,2F,new CubeDeformation(0F)),PartPose.offset(-5F,-3.5F,0.25F));
		PartDefinition var3 = var1.addOrReplaceChild("leftEar",CubeListBuilder.create().texOffs(0,0).addBox(-0.25F,-1.75F,-1F,1F,3F,3F,new CubeDeformation(0F)).texOffs(0,16).addBox(0.25F,-1.25F,-0.5F,1F,2F,2F,new CubeDeformation(0F)),PartPose.offset(4.5F,-3.5F,0.25F));
		PartDefinition var4 = var1.addOrReplaceChild("bar",CubeListBuilder.create().texOffs(0,30).addBox(-7.25F,-3.25F,0.75F,2F,1F,1F,new CubeDeformation(0F)).texOffs(0,30).addBox(4.75F,-3.25F,0.75F,2F,1F,1F,new CubeDeformation(0F)).texOffs(0,27).addBox(-7.25F,-2F,1.7F,1F,1F,4F,new CubeDeformation(0.02F)).texOffs(0,28).addBox(5.75F,-2F,1.7F,1F,1F,4F,new CubeDeformation(0.02F)).texOffs(0,26).addBox(-7.25F,-2F,5.7F,14F,1F,1F,new CubeDeformation(0.01F)),PartPose.offset(0.25F,-1F,-0.5F));
		PartDefinition var5 = var4.addOrReplaceChild("bar2",CubeListBuilder.create(),PartPose.offset(-6.75F,-1.7932F,1.5548F)).addOrReplaceChild("bar2_r1",CubeListBuilder.create().texOffs(0,28).addBox(-0.5F,-1.25F,-0.25F,1F,2F,1F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,0F,0F,0.4363F,0F,0F));
		PartDefinition var6 = var4.addOrReplaceChild("bar6",CubeListBuilder.create(),PartPose.offset(0F,0F,0F)).addOrReplaceChild("bar6_r1",CubeListBuilder.create().texOffs(0,28).addBox(12.5F,-1.25F,-0.25F,1F,2F,1F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(-6.75F,-1.7932F,1.5548F,0.4363F,0F,0F));
		return LayerDefinition.create(var0,32,32);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.mask.copyFrom(this.head);
		this.mask.render(var1,var2,var3,var4,var5);
	}
}