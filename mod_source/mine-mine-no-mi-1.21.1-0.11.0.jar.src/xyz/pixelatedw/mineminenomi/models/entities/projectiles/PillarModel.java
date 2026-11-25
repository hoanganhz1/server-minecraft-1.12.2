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

public class PillarModel<T extends Entity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","pillar"),"main");
	private final ModelPart pillar;

	public PillarModel(ModelPart var1) {
		this.pillar = var1.getChild("pillar");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		var0.getRoot().addOrReplaceChild("pillar",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-4F,3F,8F,8F,21F,new CubeDeformation(0F)).texOffs(0,29).addBox(-3F,-3F,-10F,6F,6F,13F,new CubeDeformation(0F)).texOffs(0,0).addBox(-2F,-2F,-16F,4F,4F,6F,new CubeDeformation(0F)).texOffs(0,13).addBox(-1F,-1F,-21F,2F,2F,5F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(Entity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.pillar.render(var1,var2,var3,var4,var5);
	}
}