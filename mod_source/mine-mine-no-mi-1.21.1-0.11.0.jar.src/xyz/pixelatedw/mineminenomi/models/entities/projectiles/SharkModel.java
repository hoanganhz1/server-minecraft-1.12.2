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

public class SharkModel<T extends Entity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","shark"),"main");
	private final ModelPart head1;
	private final ModelPart head3;
	private final ModelPart head4;
	private final ModelPart teeth1;
	private final ModelPart teeth2;
	private final ModelPart body1;
	private final ModelPart body2;
	private final ModelPart body3;
	private final ModelPart body4;
	private final ModelPart body5;
	private final ModelPart tail1;
	private final ModelPart tail2;
	private final ModelPart tail3;
	private final ModelPart tail4;
	private final ModelPart fin1;
	private final ModelPart rightFin;
	private final ModelPart leftFin;

	public SharkModel(ModelPart var1) {
		this.head1 = var1.getChild("head1");
		this.head3 = var1.getChild("head3");
		this.head4 = var1.getChild("head4");
		this.teeth1 = var1.getChild("teeth1");
		this.teeth2 = var1.getChild("teeth2");
		this.body1 = var1.getChild("body1");
		this.body2 = var1.getChild("body2");
		this.body3 = var1.getChild("body3");
		this.body4 = var1.getChild("body4");
		this.body5 = var1.getChild("body5");
		this.tail1 = var1.getChild("tail1");
		this.tail2 = var1.getChild("tail2");
		this.tail3 = var1.getChild("tail3");
		this.tail4 = var1.getChild("tail4");
		this.fin1 = var1.getChild("fin1");
		this.rightFin = var1.getChild("rightFin");
		this.leftFin = var1.getChild("leftFin");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("head1",CubeListBuilder.create().texOffs(37,0).addBox(-2.5F,-1F,-6.25F,5F,2F,6F,new CubeDeformation(0.001F)),PartPose.offsetAndRotation(0F,-2F,-6F,0.3491F,0F,0F));
		PartDefinition var3 = var1.addOrReplaceChild("head3",CubeListBuilder.create().texOffs(37,16).addBox(-2.5F,-1F,-5F,5F,1F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,3.2F,-6.4F,0.0873F,0F,0F));
		PartDefinition var4 = var1.addOrReplaceChild("head4",CubeListBuilder.create().texOffs(37,23).addBox(-2.5F,-1F,-3F,5F,2F,3F,new CubeDeformation(-0.01F)),PartPose.offset(0F,-0.9F,-6F));
		PartDefinition var5 = var1.addOrReplaceChild("teeth1",CubeListBuilder.create().texOffs(37,29).addBox(-2.5F,-0.5F,-6F,5F,1F,6F,new CubeDeformation(0F)),PartPose.offset(0F,0.6F,-5.9F));
		PartDefinition var6 = var1.addOrReplaceChild("teeth2",CubeListBuilder.create().texOffs(37,37).addBox(-2.5F,-2F,-5F,5F,1F,5F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,3.2F,-6.2F,0.1571F,0F,0F));
		PartDefinition var7 = var1.addOrReplaceChild("body1",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-4F,-3.5F,8F,8F,7F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		PartDefinition var8 = var1.addOrReplaceChild("body2",CubeListBuilder.create().texOffs(0,16).addBox(-3.5F,-3.5F,0F,7F,7F,4F,new CubeDeformation(0F)),PartPose.offset(0F,0F,3.5F));
		PartDefinition var9 = var1.addOrReplaceChild("body3",CubeListBuilder.create().texOffs(0,28).addBox(-3F,-3F,0F,6F,6F,3F,new CubeDeformation(0F)),PartPose.offset(0F,0F,7.5F));
		PartDefinition var10 = var1.addOrReplaceChild("body4",CubeListBuilder.create().texOffs(0,38).addBox(-2.5F,-2.5F,0F,5F,5F,3F,new CubeDeformation(0F)),PartPose.offset(0F,0F,10.5F));
		PartDefinition var11 = var1.addOrReplaceChild("body5",CubeListBuilder.create().texOffs(0,47).addBox(-3.5F,-3.5F,0F,7F,7F,3F,new CubeDeformation(0F)),PartPose.offset(0F,0F,-6.5F));
		PartDefinition var12 = var1.addOrReplaceChild("tail1",CubeListBuilder.create().texOffs(0,58).addBox(-0.5F,-2F,-1F,1F,2F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1.5F,13F,-0.9774F,0F,0F));
		PartDefinition var13 = var1.addOrReplaceChild("tail2",CubeListBuilder.create().texOffs(7,58).addBox(-0.5F,-3.1333F,-0.5F,1F,3F,1F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,-3.6F,14F,-2.3213F,0F,0F));
		PartDefinition var14 = var1.addOrReplaceChild("tail3",CubeListBuilder.create().texOffs(0,58).addBox(-0.5F,-2F,-1F,1F,2F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,1.5F,13F,-2.0595F,0F,0F));
		PartDefinition var15 = var1.addOrReplaceChild("tail4",CubeListBuilder.create().texOffs(7,58).addBox(-0.5F,-3F,-1.5F,1F,3F,1F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,3.8F,14.8F,-0.7854F,0F,0F));
		PartDefinition var16 = var1.addOrReplaceChild("fin1",CubeListBuilder.create().texOffs(70,0).addBox(-0.5F,-1.5F,0F,1F,3F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-2.5F,-2F,0.5585F,0F,0F));
		PartDefinition var17 = var1.addOrReplaceChild("rightFin",CubeListBuilder.create().texOffs(83,0).addBox(-5F,0F,0F,5F,1F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-3.5F,2F,-2F,-0.2745F,0.5148F,-0.2009F));
		PartDefinition var18 = var1.addOrReplaceChild("leftFin",CubeListBuilder.create().texOffs(83,5).addBox(0F,0F,0F,5F,1F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(3.5F,2F,-2F,0.0746F,-0.5148F,0.2009F));
		return LayerDefinition.create(var0,128,64);
	}
	public void setupAnim(Entity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.head1.render(var1,var2,var3,var4,var5);
		this.head3.render(var1,var2,var3,var4,var5);
		this.head4.render(var1,var2,var3,var4,var5);
		this.teeth1.render(var1,var2,var3,var4,var5);
		this.teeth2.render(var1,var2,var3,var4,var5);
		this.body1.render(var1,var2,var3,var4,var5);
		this.body2.render(var1,var2,var3,var4,var5);
		this.body3.render(var1,var2,var3,var4,var5);
		this.body4.render(var1,var2,var3,var4,var5);
		this.body5.render(var1,var2,var3,var4,var5);
		this.tail1.render(var1,var2,var3,var4,var5);
		this.tail2.render(var1,var2,var3,var4,var5);
		this.tail3.render(var1,var2,var3,var4,var5);
		this.tail4.render(var1,var2,var3,var4,var5);
		this.fin1.render(var1,var2,var3,var4,var5);
		this.rightFin.render(var1,var2,var3,var4,var5);
		this.leftFin.render(var1,var2,var3,var4,var5);
	}
}