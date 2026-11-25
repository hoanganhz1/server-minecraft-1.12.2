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

public class TopModel<T extends Entity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","top"),"main");
	private final ModelPart top;

	public TopModel(ModelPart var1) {
		this.top = var1.getChild("top");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		var0.getRoot().addOrReplaceChild("top",CubeListBuilder.create().texOffs(0,0).addBox(-1F,-1F,-1F,2F,1F,2F,new CubeDeformation(0F)).texOffs(0,3).addBox(-0.5F,-1.25F,-0.5F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		return LayerDefinition.create(var0,16,16);
	}
	public void setupAnim(Entity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.top.render(var1,var2,var3,var4,var5);
	}
}