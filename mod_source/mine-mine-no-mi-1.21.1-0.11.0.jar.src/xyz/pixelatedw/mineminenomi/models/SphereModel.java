package xyz.pixelatedw.mineminenomi.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class SphereModel<T extends Entity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","sphere"),"main");
	public final ModelPart sphere;

	public SphereModel(ModelPart var1) {
		this.sphere = var1.getChild("sphere");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		var0.getRoot().addOrReplaceChild("sphere",CubeListBuilder.create().texOffs(0,0).addBox(-2F,-2F,-2F,4F,4F,4F,new CubeDeformation(0F)).texOffs(0,8).addBox(-1.5F,-2.5F,-1.5F,3F,1F,3F,new CubeDeformation(0F)).texOffs(0,12).addBox(-1.5F,1.5F,-1.5F,3F,1F,3F,new CubeDeformation(0F)).texOffs(0,16).addBox(-1.5F,-1.5F,-2.5F,3F,3F,1F,new CubeDeformation(0F)).texOffs(16,0).addBox(-1.5F,-1.5F,1.5F,3F,3F,1F,new CubeDeformation(0F)).texOffs(12,8).addBox(-2.5F,-1.5F,-1.5F,1F,3F,3F,new CubeDeformation(0F)).texOffs(12,14).addBox(1.5F,-1.5F,-1.5F,1F,3F,3F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		return LayerDefinition.create(var0,32,32);
	}
	public void setupAnim(Entity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.sphere.render(var1,var2,var3,var4,var5);
	}
	public void setRotateAngle(ModelPart var1, float var2, float var3, float var4) {
		var1.xRot = var2;
		var1.yRot = var3;
		var1.zRot = var4;
	}
}