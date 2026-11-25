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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class BrickBatModel<T extends Entity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","brick_bat"),"main");
	private final ModelPart body;
	private final ModelPart body3;
	private final ModelPart leftWing;
	private final ModelPart leftWing2;
	private final ModelPart rightWing;
	private final ModelPart rightWing2;
	private final ModelPart body2;
	private final ModelPart body4;
	private final ModelPart leftEar;
	private final ModelPart rightEar;

	public BrickBatModel(ModelPart var1) {
		this.body = var1.getChild("body");
		this.body3 = this.body.getChild("body3");
		this.leftWing = this.body.getChild("leftWing");
		this.leftWing2 = this.leftWing.getChild("leftWing2");
		this.rightWing = this.body.getChild("rightWing");
		this.rightWing2 = this.rightWing.getChild("rightWing2");
		this.body2 = this.body.getChild("body2");
		this.body4 = this.body.getChild("body4");
		this.leftEar = this.body.getChild("leftEar");
		this.rightEar = this.body.getChild("rightEar");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,0).addBox(-2.5F,-2.5F,-2.5F,5F,5F,5F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		var1.addOrReplaceChild("body3",CubeListBuilder.create().texOffs(44,0).addBox(-2F,-2F,-3F,4F,4F,6F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		var1.addOrReplaceChild("leftWing",CubeListBuilder.create().texOffs(41,12).addBox(0F,0F,0F,6F,4F,0F,new CubeDeformation(0F)),PartPose.offsetAndRotation(1F,0F,-1F,0.8727F,0F,-0.4363F)).addOrReplaceChild("leftWing2",CubeListBuilder.create().texOffs(54,12).addBox(0F,0F,0F,5F,4F,0F,new CubeDeformation(0F)),PartPose.offsetAndRotation(6F,0F,0F,0F,0.5236F,0F));
		var1.addOrReplaceChild("rightWing",CubeListBuilder.create().texOffs(41,12).mirror().addBox(-6F,0F,0F,6F,4F,0F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-1F,0F,-1F,0.8727F,0F,0.4363F)).addOrReplaceChild("rightWing2",CubeListBuilder.create().texOffs(54,12).mirror().addBox(-5F,0F,0F,5F,4F,0F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-6F,0F,0F,0F,-0.5236F,0F));
		var1.addOrReplaceChild("body2",CubeListBuilder.create().texOffs(21,0).addBox(-3F,-2F,-2F,6F,4F,4F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		var1.addOrReplaceChild("body4",CubeListBuilder.create().texOffs(24,9).addBox(-2F,-3F,-2F,4F,6F,4F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		var1.addOrReplaceChild("leftEar",CubeListBuilder.create().texOffs(0,11).addBox(-0.5F,-1F,-0.5F,1F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(1.9F,-2.6F,0F,0F,0F,0.5236F));
		var1.addOrReplaceChild("rightEar",CubeListBuilder.create().texOffs(0,11).mirror().addBox(-0.5F,-1F,-0.5F,1F,1F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-1.9F,-2.6F,0F,0F,0F,-0.5236F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		this.rightWing.zRot = (0.3F) + ((Mth.cos((float)(var1.tickCount))) * (0.8F));
		this.leftWing.zRot = (-0.3F) - ((Mth.cos((float)(var1.tickCount))) * (0.8F));
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.body.render(var1,var2,var3,var4,var5);
	}
}