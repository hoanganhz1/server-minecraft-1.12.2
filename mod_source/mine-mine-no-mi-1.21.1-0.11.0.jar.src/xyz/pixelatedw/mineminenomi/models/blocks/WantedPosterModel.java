package xyz.pixelatedw.mineminenomi.models.blocks;

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

public class WantedPosterModel extends EntityModel<Entity> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","wanted_poster"),"main");
	public static final ModelLayerLocation FACE_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","wanted_poster_face"),"main");
	public static final ModelLayerLocation FACE_OVERLAY_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","wanted_poster_face_overlay"),"main");
	private final ModelPart poster;

	public WantedPosterModel(ModelPart var1) {
		this.poster = var1.getChild("poster");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		var0.getRoot().addOrReplaceChild("poster",CubeListBuilder.create().texOffs(0,0).addBox(-10F,-15F,0F,20F,30F,0F,new CubeDeformation(0F)),PartPose.offset(0F,1F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public static LayerDefinition createFaceLayer() {
		MeshDefinition var0 = new MeshDefinition();
		var0.getRoot().addOrReplaceChild("poster",CubeListBuilder.create().texOffs(7,7).addBox(0F,0F,0F,8F,8F,1F,new CubeDeformation(0F)),PartPose.offset(0F,1F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public static LayerDefinition createFaceOverlayLayer() {
		MeshDefinition var0 = new MeshDefinition();
		var0.getRoot().addOrReplaceChild("poster",CubeListBuilder.create().texOffs(39,7).addBox(0F,0F,0F,8F,8F,1F,new CubeDeformation(0F)),PartPose.offset(0F,1F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(Entity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.poster.render(var1,var2,var3,var4,var5);
	}
}