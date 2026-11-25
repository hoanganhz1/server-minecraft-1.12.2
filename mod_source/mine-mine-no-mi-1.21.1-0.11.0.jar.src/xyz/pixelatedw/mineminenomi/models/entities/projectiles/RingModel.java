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

public class RingModel<T extends Entity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","ring"),"main");
	private final ModelPart ring;
	private final ModelPart circle1;
	private final ModelPart circle2;
	private final ModelPart circle3;
	private final ModelPart circle4;
	private final ModelPart circle5;
	private final ModelPart circle6;
	private final ModelPart circle7;
	private final ModelPart circle8;
	private final ModelPart circle9;
	private final ModelPart circle10;
	private final ModelPart circle11;
	private final ModelPart circle12;
	private final ModelPart pellicle;

	public RingModel(ModelPart var1) {
		this.ring = var1.getChild("ring");
		this.circle1 = this.ring.getChild("circle1");
		this.circle2 = this.ring.getChild("circle2");
		this.circle3 = this.ring.getChild("circle3");
		this.circle4 = this.ring.getChild("circle4");
		this.circle5 = this.ring.getChild("circle5");
		this.circle6 = this.ring.getChild("circle6");
		this.circle7 = this.ring.getChild("circle7");
		this.circle8 = this.ring.getChild("circle8");
		this.circle9 = this.ring.getChild("circle9");
		this.circle10 = this.ring.getChild("circle10");
		this.circle11 = this.ring.getChild("circle11");
		this.circle12 = this.ring.getChild("circle12");
		this.pellicle = this.ring.getChild("pellicle");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("ring",CubeListBuilder.create(),PartPose.offset(0F,0F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("circle1",CubeListBuilder.create().texOffs(0,0).addBox(-1.5F,-5.6F,-0.5F,3F,1F,1F,new CubeDeformation(0.001F)),PartPose.offsetAndRotation(0F,0F,0F,0F,0F,-0.2618F));
		PartDefinition var3 = var1.addOrReplaceChild("circle2",CubeListBuilder.create().texOffs(0,0).addBox(-1.5F,-5.6F,-0.5F,3F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0F,0F,0.2618F));
		PartDefinition var4 = var1.addOrReplaceChild("circle3",CubeListBuilder.create().texOffs(0,0).addBox(-1.5F,-5.6F,-0.5F,3F,1F,1F,new CubeDeformation(0.001F)),PartPose.offsetAndRotation(0F,0F,0F,0F,0F,0.7854F));
		PartDefinition var5 = var1.addOrReplaceChild("circle4",CubeListBuilder.create().texOffs(0,0).addBox(-1.5F,-5.6F,-0.5F,3F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0F,0F,1.309F));
		PartDefinition var6 = var1.addOrReplaceChild("circle5",CubeListBuilder.create().texOffs(0,0).addBox(-1.5F,-5.6F,-0.5F,3F,1F,1F,new CubeDeformation(0.001F)),PartPose.offsetAndRotation(0F,0F,0F,0F,0F,1.8326F));
		PartDefinition var7 = var1.addOrReplaceChild("circle6",CubeListBuilder.create().texOffs(0,0).addBox(-1.5F,-5.6F,-0.5F,3F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0F,0F,2.3562F));
		PartDefinition var8 = var1.addOrReplaceChild("circle7",CubeListBuilder.create().texOffs(0,0).addBox(-1.6F,-5.6F,-0.5F,3F,1F,1F,new CubeDeformation(0.001F)),PartPose.offsetAndRotation(0F,0F,0F,0F,0F,2.8972F));
		PartDefinition var9 = var1.addOrReplaceChild("circle8",CubeListBuilder.create().texOffs(0,0).addBox(-1.5F,-5.6F,-0.5F,3F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0F,0F,-2.8972F));
		PartDefinition var10 = var1.addOrReplaceChild("circle9",CubeListBuilder.create().texOffs(0,0).addBox(-1.6F,-5.6F,-0.5F,3F,1F,1F,new CubeDeformation(0.001F)),PartPose.offsetAndRotation(0F,0F,0F,0F,0F,-2.3562F));
		PartDefinition var11 = var1.addOrReplaceChild("circle10",CubeListBuilder.create().texOffs(0,0).addBox(-1.5F,-0.5F,-0.5F,3F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-4.9003F,1.3166F,0F,0F,0F,-1.8326F));
		PartDefinition var12 = var1.addOrReplaceChild("circle11",CubeListBuilder.create().texOffs(0,0).addBox(-1.5F,-5.6F,-0.5F,3F,1F,1F,new CubeDeformation(0.001F)),PartPose.offsetAndRotation(0F,0F,0F,0F,0F,-1.309F));
		PartDefinition var13 = var1.addOrReplaceChild("circle12",CubeListBuilder.create().texOffs(0,0).addBox(-1.5F,-5.6F,-0.5F,3F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0F,0F,-0.7854F));
		PartDefinition var14 = var1.addOrReplaceChild("pellicle",CubeListBuilder.create().texOffs(5,10).addBox(-5F,-3F,0F,10F,6F,0F,new CubeDeformation(0F)).texOffs(9,0).addBox(-3F,-5F,0F,6F,2F,0F,new CubeDeformation(0.001F)).texOffs(9,0).addBox(-3F,3F,0F,6F,2F,0F,new CubeDeformation(0.001F)).texOffs(6,8).addBox(3F,3F,0F,1F,1F,0F,new CubeDeformation(0.001F)).texOffs(6,8).addBox(3F,-4F,0F,1F,1F,0F,new CubeDeformation(0.001F)).texOffs(6,8).addBox(-4F,3F,0F,1F,1F,0F,new CubeDeformation(0.001F)).texOffs(6,8).addBox(-4F,-4F,0F,1F,1F,0F,new CubeDeformation(0.001F)),PartPose.offset(0F,0F,0F));
		return LayerDefinition.create(var0,32,16);
	}
	public void setupAnim(Entity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.ring.render(var1,var2,var3,var4,var5);
	}
}