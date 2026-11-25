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

public class PawModel<T extends Entity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","paw"),"main");
	private final ModelPart paw;

	public PawModel(ModelPart var1) {
		this.paw = var1.getChild("paw");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("paw",CubeListBuilder.create().texOffs(48,21).addBox(8F,-11.119F,-2.5F,3F,3F,5F,new CubeDeformation(0F)).texOffs(0,2).addBox(-8F,-6.619F,-4F,16F,14F,8F,new CubeDeformation(0F)).texOffs(29,26).addBox(-5.5F,-13.119F,-1.5F,5F,3F,3F,new CubeDeformation(0F)).texOffs(48,0).addBox(1F,-13.619F,-2F,4F,4F,4F,new CubeDeformation(0F)).texOffs(48,0).addBox(-11.5F,-11.619F,-2F,4F,4F,4F,new CubeDeformation(0F)).texOffs(2,0).addBox(-7F,-7.619F,-4F,14F,16F,8F,new CubeDeformation(0F)).texOffs(48,21).addBox(1.5F,-13.119F,-2.5F,3F,3F,5F,new CubeDeformation(0F)).texOffs(50,10).addBox(1.5F,-14.119F,-1.5F,3F,5F,3F,new CubeDeformation(0F)).texOffs(48,0).addBox(-5F,-13.619F,-2F,4F,4F,4F,new CubeDeformation(0F)).texOffs(0,0).addBox(-7.5F,-7.119F,-4.5F,15F,15F,9F,new CubeDeformation(0F)).texOffs(0,0).addBox(-7F,-6.619F,-5F,14F,14F,10F,new CubeDeformation(0F)).texOffs(29,26).addBox(7F,-11.119F,-1.5F,5F,3F,3F,new CubeDeformation(0F)).texOffs(48,21).addBox(-4.5F,-13.119F,-2.5F,3F,3F,5F,new CubeDeformation(0F)).texOffs(50,10).addBox(-11F,-12.119F,-1.5F,3F,5F,3F,new CubeDeformation(0F)).texOffs(29,26).addBox(0.5F,-13.119F,-1.5F,5F,3F,3F,new CubeDeformation(0F)).texOffs(50,10).addBox(-4.5F,-14.119F,-1.5F,3F,5F,3F,new CubeDeformation(0F)).texOffs(29,26).addBox(-12F,-11.119F,-1.5F,5F,3F,3F,new CubeDeformation(0F)).texOffs(48,21).addBox(-11F,-11.119F,-2.5F,3F,3F,5F,new CubeDeformation(0F)).texOffs(48,0).addBox(7.5F,-11.619F,-2F,4F,4F,4F,new CubeDeformation(0F)).texOffs(0,0).addBox(-6.5F,-6.119F,-5.5F,13F,13F,11F,new CubeDeformation(0F)).texOffs(50,10).addBox(8F,-12.119F,-1.5F,3F,5F,3F,new CubeDeformation(0F)),PartPose.offset(0F,-0.381F,0F));
		return LayerDefinition.create(var0,64,32);
	}
	public void setupAnim(Entity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.paw.render(var1,var2,var3,var4,var5);
	}
}