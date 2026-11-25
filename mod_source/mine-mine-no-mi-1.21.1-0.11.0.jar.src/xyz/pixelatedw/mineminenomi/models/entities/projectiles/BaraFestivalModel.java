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
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bara.BaraFestivalEntity;

public class BaraFestivalModel extends EntityModel<BaraFestivalEntity> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","bara_festival"),"main");
	private final ModelPart limb1;
	private final ModelPart limb2;
	private final ModelPart limb3;
	private final ModelPart limb4;

	public BaraFestivalModel(ModelPart var1) {
		this.limb1 = var1.getChild("limb1");
		this.limb2 = var1.getChild("limb2");
		this.limb3 = var1.getChild("limb3");
		this.limb4 = var1.getChild("limb4");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot();
		var1.addOrReplaceChild("limb1",CubeListBuilder.create().texOffs(0,16).addBox(-18F,-6F,-6F,4F,12F,4F,new CubeDeformation(0F)),PartPose.offset(0F,18F,0F));
		var1.addOrReplaceChild("limb2",CubeListBuilder.create().texOffs(0,16).addBox(4F,-6F,12F,4F,12F,4F,new CubeDeformation(0F)),PartPose.offset(0F,18F,0F));
		var1.addOrReplaceChild("limb3",CubeListBuilder.create().texOffs(40,16).addBox(-4F,-6F,-20F,4F,12F,4F,new CubeDeformation(0F)),PartPose.offset(0F,18F,0F));
		var1.addOrReplaceChild("limb4",CubeListBuilder.create().texOffs(40,16).addBox(16F,-6F,2F,4F,12F,4F,new CubeDeformation(0F)),PartPose.offset(0F,18F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(BaraFestivalEntity var1, float var2, float var3, float var4, float var5, float var6) {
		ModelPart var7 = this.limb1;
		var7.yRot = (float)(((double)(var7.yRot)) + ((Math.sin(((double)(var4)) * (0.02D))) / (10D)));
		ModelPart var8 = this.limb1;
		var8.zRot = (float)(((double)(var8.zRot)) + ((Math.sin(((double)(var4)) * (0.1D))) / (3D)));
		ModelPart var9 = this.limb2;
		var9.yRot = (float)(((double)(var9.yRot)) + ((Math.cos(((double)(var4)) * (0.2D))) / (10D)));
		ModelPart var10 = this.limb2;
		var10.xRot = (float)(((double)(var10.xRot)) + ((Math.sin(((double)(var4)) * (0.1D))) / (3D)));
		ModelPart var11 = this.limb3;
		var11.yRot = (float)(((double)(var11.yRot)) - ((Math.cos(((double)(var4)) * (0.02D))) / (10D)));
		ModelPart var12 = this.limb3;
		var12.zRot = (float)(((double)(var12.zRot)) + ((Math.sin(((double)(var4)) * (0.1D))) / (3D)));
		ModelPart var13 = this.limb4;
		var13.yRot = (float)(((double)(var13.yRot)) - ((Math.sin(((double)(var4)) * (0.5D))) / (10D)));
		ModelPart var14 = this.limb4;
		var14.xRot = (float)(((double)(var14.xRot)) - ((Math.sin(((double)(var4)) * (0.1D))) / (3D)));
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.limb1.render(var1,var2,var3,var4,var5);
		this.limb2.render(var1,var2,var3,var4,var5);
		this.limb3.render(var1,var2,var3,var4,var5);
		this.limb4.render(var1,var2,var3,var4,var5);
	}
}