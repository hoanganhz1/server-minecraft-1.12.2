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

public class SoulKingCrownModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","soul_king_crown"),"main");
	private final ModelPart crown;

	public SoulKingCrownModel(ModelPart var1) {
		super(var1);
		this.crown = this.head.getChild("crown");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		var0.getRoot().addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).addBox(-7.5F,-6.525F,-7.5F,15F,1F,15F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F)).addOrReplaceChild("crown",CubeListBuilder.create().texOffs(0,17).addBox(-4.5F,-37.155F,-4.5F,9F,6F,9F,new CubeDeformation(0.15F)).texOffs(16,29).addBox(-4.5F,-39.155F,-4.5F,2F,2F,1F,new CubeDeformation(0.14F)).texOffs(21,29).addBox(-0.5F,-39.155F,-4.5F,2F,2F,1F,new CubeDeformation(0.14F)).texOffs(6,28).addBox(3.5F,-39.155F,-4.5F,1F,2F,2F,new CubeDeformation(0.14F)).texOffs(11,28).addBox(3.5F,-39.155F,-0.5F,1F,2F,2F,new CubeDeformation(0.14F)).texOffs(23,29).addBox(2.5F,-39.155F,3.5F,2F,2F,1F,new CubeDeformation(0.14F)).texOffs(1,29).addBox(-1.5F,-39.155F,3.5F,2F,2F,1F,new CubeDeformation(0.14F)).texOffs(28,28).addBox(-4.5F,-39.155F,2.5F,1F,2F,2F,new CubeDeformation(0.14F)).texOffs(14,28).addBox(-4.5F,-39.155F,-1.5F,1F,2F,2F,new CubeDeformation(0.14F)).texOffs(1,6).addBox(-3F,-33.455F,-5F,1F,1F,1F,new CubeDeformation(0F)).texOffs(1,6).addBox(-0.5F,-33.455F,-5F,1F,1F,1F,new CubeDeformation(0F)).texOffs(1,6).addBox(2F,-33.455F,-5F,1F,1F,1F,new CubeDeformation(0F)).texOffs(1,6).addBox(-3F,-33.455F,4F,1F,1F,1F,new CubeDeformation(0F)).texOffs(1,6).addBox(-0.5F,-33.455F,4F,1F,1F,1F,new CubeDeformation(0F)).texOffs(1,6).addBox(2F,-33.455F,4F,1F,1F,1F,new CubeDeformation(0F)).texOffs(1,6).addBox(-5F,-33.455F,-0.5F,1F,1F,1F,new CubeDeformation(0F)).texOffs(1,6).addBox(-5F,-33.455F,2F,1F,1F,1F,new CubeDeformation(0F)).texOffs(1,6).addBox(-5F,-33.455F,-3F,1F,1F,1F,new CubeDeformation(0F)).texOffs(1,6).addBox(4F,-33.455F,-0.5F,1F,1F,1F,new CubeDeformation(0F)).texOffs(1,6).addBox(4F,-33.455F,2F,1F,1F,1F,new CubeDeformation(0F)).texOffs(1,6).addBox(4F,-33.455F,-3F,1F,1F,1F,new CubeDeformation(0F)),PartPose.offset(0F,25.475F,0F));
		return LayerDefinition.create(var0,64,32);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.head.render(var1,var2,var3,var4,var5);
	}
}