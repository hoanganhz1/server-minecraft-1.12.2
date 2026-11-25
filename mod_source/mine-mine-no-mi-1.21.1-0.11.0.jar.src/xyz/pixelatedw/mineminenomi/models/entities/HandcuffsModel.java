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
import net.minecraft.world.entity.LivingEntity;

public class HandcuffsModel extends HumanoidModel<LivingEntity> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","handcuffs"),"main");
	private final ModelPart handcuffs;
	private final ModelPart rightCuff;
	private final ModelPart leftCuff;
	private final ModelPart chain;

	public HandcuffsModel(ModelPart var1) {
		super(var1);
		this.handcuffs = var1.getChild("handcuffs");
		this.rightCuff = this.handcuffs.getChild("rightCuff");
		this.leftCuff = this.handcuffs.getChild("leftCuff");
		this.chain = this.handcuffs.getChild("chain");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("handcuffs",CubeListBuilder.create(),PartPose.offsetAndRotation(0F,2F,0F,-0.6981F,0F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("rightCuff",CubeListBuilder.create().texOffs(0,0).addBox(2F,-1.5F,-2F,1F,3F,4F,new CubeDeformation(0.2F)).texOffs(0,3).addBox(-2F,-1.5F,2F,4F,3F,1F,new CubeDeformation(0.2F)).texOffs(0,3).addBox(-2F,-1.5F,-3F,4F,3F,1F,new CubeDeformation(0.2F)).texOffs(0,0).addBox(-3F,-1.5F,-2F,1F,3F,4F,new CubeDeformation(0.2F)),PartPose.offset(-6F,7.8411F,-0.2731F));
		PartDefinition var3 = var1.addOrReplaceChild("leftCuff",CubeListBuilder.create().texOffs(0,0).addBox(2F,-1.5F,-2F,1F,3F,4F,new CubeDeformation(0.2F)).texOffs(0,3).addBox(-2F,-1.5F,2F,4F,3F,1F,new CubeDeformation(0.2F)).texOffs(0,3).addBox(-2F,-1.5F,-3F,4F,3F,1F,new CubeDeformation(0.2F)).texOffs(0,0).addBox(-3F,-1.5F,-2F,1F,3F,4F,new CubeDeformation(0.2F)),PartPose.offset(6F,7.8411F,-0.2731F));
		PartDefinition var4 = var1.addOrReplaceChild("chain",CubeListBuilder.create().texOffs(0,8).addBox(-2F,-0.5F,-1.5F,4F,1F,1F,new CubeDeformation(0F)).texOffs(0,8).addBox(-2F,-0.5F,0.5F,4F,1F,1F,new CubeDeformation(0F)).texOffs(1,8).addBox(-3F,-0.5F,-0.5F,2F,1F,1F,new CubeDeformation(0F)).texOffs(4,8).addBox(1F,-0.5F,-0.5F,2F,1F,1F,new CubeDeformation(0F)),PartPose.offset(0F,7.9323F,-0.1499F));
		return LayerDefinition.create(var0,32,16);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.handcuffs.render(var1,var2,var3,var4,var5);
	}
}