package xyz.pixelatedw.mineminenomi.models.entities.mobs.animals;

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
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.FlyingFishEntity;

public class FlyingFishModel<T extends FlyingFishEntity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","flying_fish"),"main");
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart leftLowerFin;
	private final ModelPart rightLowerFin;
	private final ModelPart saddle;
	private final ModelPart leftFin;
	private final ModelPart rightFin;
	private final ModelPart tail;
	private final ModelPart tail2;
	private final ModelPart tail3;
	private final ModelPart tail4;
	private final ModelPart tail5;

	public FlyingFishModel(ModelPart var1) {
		this.head = var1.getChild("head");
		this.body = var1.getChild("body");
		this.leftLowerFin = this.body.getChild("leftLowerFin");
		this.rightLowerFin = this.body.getChild("rightLowerFin");
		this.saddle = this.body.getChild("saddle");
		this.leftFin = this.body.getChild("leftFin");
		this.rightFin = this.body.getChild("rightFin");
		this.tail = this.body.getChild("tail");
		this.tail2 = this.tail.getChild("tail2");
		this.tail3 = this.tail2.getChild("tail3");
		this.tail4 = this.tail3.getChild("tail4");
		this.tail5 = this.tail4.getChild("tail5");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,90).addBox(-6F,-5.625F,-25.625F,12F,15F,9F,new CubeDeformation(0F)).texOffs(0,62).addBox(-6.5F,-6.125F,-19.625F,13F,16F,10F,new CubeDeformation(0F)),PartPose.offset(0F,8.625F,-5.375F));
		PartDefinition var3 = var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,22).addBox(-7.5F,-6.625F,-11.625F,15F,17F,22F,new CubeDeformation(0F)),PartPose.offset(0F,8.625F,-5.375F));
		PartDefinition var4 = var3.addOrReplaceChild("leftLowerFin",CubeListBuilder.create().texOffs(-16,24).addBox(-0.0442F,1.9208F,-20F,10F,0F,16F,new CubeDeformation(0F)),PartPose.offsetAndRotation(7.5F,6.875F,11.375F,0F,0F,0.3491F));
		PartDefinition var5 = var3.addOrReplaceChild("rightLowerFin",CubeListBuilder.create().texOffs(-16,24).addBox(-9.9558F,1.9208F,-20F,10F,0F,16F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-7.5F,6.875F,11.375F,0F,0F,-0.3491F));
		PartDefinition var6 = var3.addOrReplaceChild("saddle",CubeListBuilder.create().texOffs(0,158).addBox(-7.5F,-6.5F,-15F,15F,17F,10F,new CubeDeformation(0.25F)).texOffs(0,132).addBox(-5.5F,-8F,-19.8125F,11F,2F,22F,new CubeDeformation(0F)).texOffs(46,132).addBox(-5.5F,-16F,-9.8125F,11F,8F,12F,new CubeDeformation(0F)).texOffs(52,158).addBox(-4.5F,-20F,-9.8125F,9F,4F,12F,new CubeDeformation(0F)),PartPose.offset(0F,-0.125F,11.375F));
		PartDefinition var7 = var3.addOrReplaceChild("leftFin",CubeListBuilder.create().texOffs(-22,0).mirror().addBox(-48.5F,2F,-15F,49F,0F,22F,new CubeDeformation(0F)).mirror(false),PartPose.offset(-7F,6.375F,3.375F));
		PartDefinition var8 = var3.addOrReplaceChild("rightFin",CubeListBuilder.create().texOffs(-22,0).addBox(-0.5F,2F,-15F,49F,0F,22F,new CubeDeformation(0F)),PartPose.offset(7F,6.375F,3.375F));
		PartDefinition var9 = var3.addOrReplaceChild("tail",CubeListBuilder.create().texOffs(79,23).addBox(-7F,-6F,-1F,14F,16F,10F,new CubeDeformation(0F)).texOffs(116,98).addBox(0F,-18F,3F,0F,24F,6F,new CubeDeformation(0F)),PartPose.offset(0F,-0.125F,10.375F)).addOrReplaceChild("tail2",CubeListBuilder.create().texOffs(90,50).addBox(-6F,-5F,-2F,12F,15F,7F,new CubeDeformation(0F)).texOffs(104,102).addBox(0F,-15F,0F,0F,21F,5F,new CubeDeformation(0F)),PartPose.offset(0F,-0.5F,9F)).addOrReplaceChild("tail3",CubeListBuilder.create().texOffs(94,73).addBox(-5.5F,-5F,-2F,11F,14F,6F,new CubeDeformation(0F)).texOffs(92,106).addBox(0F,-13F,0F,0F,18F,4F,new CubeDeformation(0F)),PartPose.offset(0F,0.5F,5F)).addOrReplaceChild("tail4",CubeListBuilder.create().texOffs(99,0).addBox(-4F,-3.25F,-0.5F,8F,11F,3F,new CubeDeformation(0F)).texOffs(84,111).addBox(0F,-10.25F,-0.5F,0F,14F,3F,new CubeDeformation(0F)),PartPose.offset(0F,-0.25F,4.5F)).addOrReplaceChild("tail5",CubeListBuilder.create().texOffs(42,68).addBox(0F,-21.25F,0F,0F,39F,21F,new CubeDeformation(0F)),PartPose.offset(0F,0F,2.5F));
		return LayerDefinition.create(var0,256,256);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		this.rightFin.zRot = (Mth.cos(((float)(var1.tickCount)) * (0.9F))) * (0.2F);
		this.rightFin.xRot = (Mth.cos(((float)(var1.tickCount)) * (0.5F))) * (0.4F);
		this.leftFin.zRot = (-(Mth.cos(((float)(var1.tickCount)) * (0.9F)))) * (0.2F);
		this.leftFin.xRot = (Mth.cos(((float)(var1.tickCount)) * (0.5F))) * (0.4F);
		this.rightLowerFin.xRot = (Mth.cos(((float)(var1.tickCount)) * (0.5F))) * (0.2F);
		this.leftLowerFin.xRot = (Mth.cos(((float)(var1.tickCount)) * (0.5F))) * (0.2F);
		this.tail.yRot = (Mth.cos(((float)(var1.tickCount)) * (0.3F))) * (0.05F);
		this.tail2.yRot = (Mth.cos(((float)(var1.tickCount)) * (0.3F))) * (0.05F);
		this.tail3.yRot = (Mth.cos(((float)(var1.tickCount)) * (0.3F))) * (0.05F);
		this.tail4.yRot = (Mth.cos(((float)(var1.tickCount)) * (0.3F))) * (0.05F);
		this.tail5.yRot = (Mth.cos(((float)(var1.tickCount)) * (0.3F))) * (0.05F);
	}
	public void renderSaddle() {
		this.saddle.visible = true;
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.head.render(var1,var2,var3,var4,var5);
		this.body.render(var1,var2,var3,var4,var5);
		this.saddle.visible = false;
	}
}