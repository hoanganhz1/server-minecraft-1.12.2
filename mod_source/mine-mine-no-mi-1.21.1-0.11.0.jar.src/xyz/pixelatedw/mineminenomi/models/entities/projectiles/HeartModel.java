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

public class HeartModel<T extends Entity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","heart"),"main");
	private final ModelPart pellicle;
	private final ModelPart heart4;
	private final ModelPart heart15;
	private final ModelPart heart9;
	private final ModelPart heart11;
	private final ModelPart heart16;
	private final ModelPart heart2;
	private final ModelPart heart6;
	private final ModelPart heart12;
	private final ModelPart heart7;
	private final ModelPart heart5;
	private final ModelPart heart13;
	private final ModelPart heart18;
	private final ModelPart heart8;
	private final ModelPart heart14;
	private final ModelPart heart1;
	private final ModelPart heart17;
	private final ModelPart heart3;
	private final ModelPart heart10;

	public HeartModel(ModelPart var1) {
		this.pellicle = var1.getChild("pellicle");
		this.heart4 = this.pellicle.getChild("heart4");
		this.heart15 = this.pellicle.getChild("heart15");
		this.heart9 = this.pellicle.getChild("heart9");
		this.heart11 = this.pellicle.getChild("heart11");
		this.heart16 = this.pellicle.getChild("heart16");
		this.heart2 = this.pellicle.getChild("heart2");
		this.heart6 = this.pellicle.getChild("heart6");
		this.heart12 = this.pellicle.getChild("heart12");
		this.heart7 = this.pellicle.getChild("heart7");
		this.heart5 = this.pellicle.getChild("heart5");
		this.heart13 = this.pellicle.getChild("heart13");
		this.heart18 = this.pellicle.getChild("heart18");
		this.heart8 = this.pellicle.getChild("heart8");
		this.heart14 = this.pellicle.getChild("heart14");
		this.heart1 = this.pellicle.getChild("heart1");
		this.heart17 = this.pellicle.getChild("heart17");
		this.heart3 = this.pellicle.getChild("heart3");
		this.heart10 = this.pellicle.getChild("heart10");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("pellicle",CubeListBuilder.create(),PartPose.offset(0F,0F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("heart4",CubeListBuilder.create().texOffs(0,0).addBox(-0.5F,-2.5F,-0.5F,1F,5F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(4.7045F,6.7034F,0F,0F,0F,-2.4958F));
		PartDefinition var3 = var1.addOrReplaceChild("heart15",CubeListBuilder.create().texOffs(10,0).addBox(-0.5F,-1.5F,-0.5F,1F,3F,1F,new CubeDeformation(0.001F)),PartPose.offsetAndRotation(-3.1279F,-7.2051F,0F,0F,0F,-1.0123F));
		PartDefinition var4 = var1.addOrReplaceChild("heart9",CubeListBuilder.create().texOffs(5,0).addBox(-0.5F,-2F,-0.5F,1F,4F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-9.3162F,-4.8406F,0F,0F,0F,-2.8972F));
		PartDefinition var5 = var1.addOrReplaceChild("heart11",CubeListBuilder.create().texOffs(10,0).addBox(-0.5F,-1.5F,-0.5F,1F,3F,1F,new CubeDeformation(0.001F)),PartPose.offsetAndRotation(-7.803F,-7.523F,0F,0F,0F,-2.3213F));
		PartDefinition var6 = var1.addOrReplaceChild("heart16",CubeListBuilder.create().texOffs(10,0).addBox(-0.5F,-1.5F,-0.5F,1F,3F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(3.1279F,-7.2051F,0F,0F,0F,1.0123F));
		PartDefinition var7 = var1.addOrReplaceChild("heart2",CubeListBuilder.create().texOffs(0,0).addBox(-0.5F,-2.5F,-0.5F,1F,5F,1F,new CubeDeformation(0.001F)),PartPose.offsetAndRotation(1.4983F,10.2634F,0F,0F,0F,-2.3387F));
		PartDefinition var8 = var1.addOrReplaceChild("heart6",CubeListBuilder.create().texOffs(0,0).addBox(-0.5F,-2.5F,-0.5F,1F,5F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(7.3737F,2.6926F,0F,0F,0F,-2.6093F));
		PartDefinition var9 = var1.addOrReplaceChild("heart12",CubeListBuilder.create().texOffs(10,0).addBox(-0.5F,-1.5F,-0.5F,1F,3F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(7.803F,-7.523F,0F,0F,0F,2.3213F));
		PartDefinition var10 = var1.addOrReplaceChild("heart7",CubeListBuilder.create().texOffs(0,0).addBox(-0.5F,-2F,-0.5F,1F,4F,1F,new CubeDeformation(0.001F)),PartPose.offsetAndRotation(-9.1513F,-1.2225F,0F,0F,0F,2.8623F));
		PartDefinition var11 = var1.addOrReplaceChild("heart5",CubeListBuilder.create().texOffs(0,0).addBox(-0.5F,-2.5F,-0.5F,1F,5F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-7.3737F,2.6926F,0F,0F,0F,2.6093F));
		PartDefinition var12 = var1.addOrReplaceChild("heart13",CubeListBuilder.create().texOffs(10,0).mirror().addBox(-0.5F,-1.5F,-0.5F,1F,3F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-5.6185F,-8.1653F,0F,0F,0F,-1.4137F));
		PartDefinition var13 = var1.addOrReplaceChild("heart18",CubeListBuilder.create().texOffs(10,0).addBox(-0.5F,-1.5F,-0.5F,1F,3F,1F,new CubeDeformation(0.001F)),PartPose.offsetAndRotation(0.8509F,-5.5358F,0F,0F,0F,0.8727F));
		PartDefinition var14 = var1.addOrReplaceChild("heart8",CubeListBuilder.create().texOffs(0,0).addBox(-0.5F,-2F,-0.5F,1F,4F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(9.1513F,-1.2225F,0F,0F,0F,-2.8623F));
		PartDefinition var15 = var1.addOrReplaceChild("heart14",CubeListBuilder.create().texOffs(10,0).addBox(-0.5F,-1.5F,-0.5F,1F,3F,1F,new CubeDeformation(0.001F)),PartPose.offsetAndRotation(5.6185F,-8.1653F,0F,0F,0F,1.4137F));
		PartDefinition var16 = var1.addOrReplaceChild("heart1",CubeListBuilder.create().texOffs(0,0).mirror().addBox(-0.5F,-2.5F,-0.5F,1F,5F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-1.4983F,10.2634F,0F,0F,0F,2.3387F));
		PartDefinition var17 = var1.addOrReplaceChild("heart17",CubeListBuilder.create().texOffs(10,0).addBox(-0.5F,-1.5F,-0.5F,1F,3F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.8509F,-5.5358F,0F,0F,0F,-0.8727F));
		PartDefinition var18 = var1.addOrReplaceChild("heart3",CubeListBuilder.create().texOffs(0,0).addBox(-0.5F,-2.5F,-0.5F,1F,5F,1F,new CubeDeformation(0.001F)),PartPose.offsetAndRotation(-4.7045F,6.7034F,0F,0F,0F,2.4958F));
		PartDefinition var19 = var1.addOrReplaceChild("heart10",CubeListBuilder.create().texOffs(5,0).addBox(-0.5F,-2F,-0.5F,1F,4F,1F,new CubeDeformation(0.001F)),PartPose.offsetAndRotation(9.3162F,-4.8406F,0F,0F,0F,2.8972F));
		return LayerDefinition.create(var0,64,32);
	}
	public void setupAnim(Entity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.pellicle.render(var1,var2,var3,var4,var5);
	}
}