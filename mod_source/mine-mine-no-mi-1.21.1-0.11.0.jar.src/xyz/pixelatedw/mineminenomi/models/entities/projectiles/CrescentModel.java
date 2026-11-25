package xyz.pixelatedw.mineminenomi.models.entities.projectiles;

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

public class CrescentModel<T extends Entity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","crescent"),"main");
	private final ModelPart slash;

	public CrescentModel(ModelPart var1) {
		this.slash = var1.getChild("slash");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("slash",CubeListBuilder.create().texOffs(0,0).addBox(-0.5F,24.5F,6.5526F,1F,1F,8F,new CubeDeformation(0F)).texOffs(0,0).addBox(-0.5F,23.5F,4.5526F,1F,1F,8F,new CubeDeformation(0F)).texOffs(0,0).addBox(-0.5F,22.4F,3.5526F,1F,1F,7F,new CubeDeformation(0.1F)).texOffs(0,0).addBox(-0.5F,21.2F,2.5526F,1F,1F,7F,new CubeDeformation(0.1F)).texOffs(0,0).addBox(-0.5F,20F,1.5526F,1F,1F,7F,new CubeDeformation(0.1F)).texOffs(0,0).addBox(-0.5F,17.7F,0.5526F,1F,2F,7F,new CubeDeformation(0.2F)).texOffs(0,0).addBox(-0.5F,14.3F,-0.4474F,1F,3F,7F,new CubeDeformation(0.2F)).texOffs(0,0).addBox(-0.5F,10.8F,-1.4474F,1F,3F,7F,new CubeDeformation(0.3F)).texOffs(0,0).addBox(-0.5F,6.2F,-2.4474F,1F,4F,7F,new CubeDeformation(0.3F)).texOffs(0,0).addBox(-0.5F,-5.5F,-3.4474F,1F,11F,7F,new CubeDeformation(0.4F)).texOffs(0,0).addBox(-0.5F,-10.2F,-2.4474F,1F,4F,7F,new CubeDeformation(0.3F)).texOffs(0,0).addBox(-0.5F,-13.8F,-1.4474F,1F,3F,7F,new CubeDeformation(0.3F)).texOffs(0,0).addBox(-0.5F,-17.3F,-0.4474F,1F,3F,7F,new CubeDeformation(0.2F)).texOffs(0,0).addBox(-0.5F,-19.7F,0.5526F,1F,2F,7F,new CubeDeformation(0.2F)).texOffs(0,0).addBox(-0.5F,-21F,1.5526F,1F,1F,7F,new CubeDeformation(0.1F)).texOffs(0,0).addBox(-0.5F,-22.2F,2.5526F,1F,1F,7F,new CubeDeformation(0.1F)).texOffs(0,0).addBox(-0.5F,-23.4F,3.5526F,1F,1F,7F,new CubeDeformation(0.1F)).texOffs(0,0).addBox(-0.5F,-24.5F,4.5526F,1F,1F,8F,new CubeDeformation(0F)).texOffs(0,0).addBox(-0.5F,-25.5F,6.5526F,1F,1F,8F,new CubeDeformation(0F)),PartPose.offset(0.0526F,-1.5F,-5F));
		return LayerDefinition.create(var0,16,16);
	}
	public void setupAnim(Entity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.slash.render(var1,var2,var3,var4,var5);
	}
}