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

public class YukiRabiModel<T extends Entity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","yuki_rabi"),"main");
	private final ModelPart rabi;
	private final ModelPart body1;
	private final ModelPart body2;
	private final ModelPart body3;
	private final ModelPart body4;
	private final ModelPart rightEar;
	private final ModelPart leftEar;

	public YukiRabiModel(ModelPart var1) {
		this.rabi = var1.getChild("rabi");
		this.body1 = this.rabi.getChild("body1");
		this.body2 = this.rabi.getChild("body2");
		this.body3 = this.rabi.getChild("body3");
		this.body4 = this.rabi.getChild("body4");
		this.rightEar = this.rabi.getChild("rightEar");
		this.leftEar = this.rabi.getChild("leftEar");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("rabi",CubeListBuilder.create(),PartPose.offset(0F,0F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("body1",CubeListBuilder.create().texOffs(25,18).addBox(-3.5F,-3.5F,-3.5F,7F,7F,7F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		PartDefinition var3 = var1.addOrReplaceChild("body2",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-3F,-3F,8F,6F,6F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		PartDefinition var4 = var1.addOrReplaceChild("body3",CubeListBuilder.create().texOffs(20,4).addBox(-3F,-3F,-4F,6F,6F,8F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		PartDefinition var5 = var1.addOrReplaceChild("body4",CubeListBuilder.create().texOffs(0,14).addBox(-3F,-4F,-3F,6F,8F,6F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		PartDefinition var6 = var1.addOrReplaceChild("rightEar",CubeListBuilder.create().texOffs(41,1).addBox(-1.5F,-0.7F,-0.25F,2F,1F,7F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-1F,-3.3F,-0.5F,0.3665F,-0.0873F,-0.1745F));
		PartDefinition var7 = var1.addOrReplaceChild("leftEar",CubeListBuilder.create().texOffs(41,1).mirror().addBox(-0.5F,-0.7F,-0.25F,2F,1F,7F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(1F,-3.3F,-0.5F,0.3665F,0.0873F,0.1745F));
		return LayerDefinition.create(var0,64,32);
	}
	public void setupAnim(Entity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.rabi.render(var1,var2,var3,var4,var5);
	}
}