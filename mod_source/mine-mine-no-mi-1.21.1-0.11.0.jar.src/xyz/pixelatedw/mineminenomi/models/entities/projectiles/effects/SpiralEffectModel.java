package xyz.pixelatedw.mineminenomi.models.entities.projectiles.effects;

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

public class SpiralEffectModel<T extends Entity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","spiral_effect"),"main");
	private final ModelPart effect1;
	private final ModelPart effect1a;
	private final ModelPart effect1b;
	private final ModelPart effect1c;
	private final ModelPart effect1d;
	private final ModelPart effect2;
	private final ModelPart effect2a;
	private final ModelPart effect2b;
	private final ModelPart effect2c;
	private final ModelPart effect2d;
	private final ModelPart effect3;
	private final ModelPart effect3a;
	private final ModelPart effect3b;
	private final ModelPart effect3c;
	private final ModelPart effect3d;

	public SpiralEffectModel(ModelPart var1) {
		this.effect1 = var1.getChild("effect1");
		this.effect1a = this.effect1.getChild("effect1a");
		this.effect1b = this.effect1.getChild("effect1b");
		this.effect1c = this.effect1.getChild("effect1c");
		this.effect1d = this.effect1.getChild("effect1d");
		this.effect2 = var1.getChild("effect2");
		this.effect2a = this.effect2.getChild("effect2a");
		this.effect2b = this.effect2.getChild("effect2b");
		this.effect2c = this.effect2.getChild("effect2c");
		this.effect2d = this.effect2.getChild("effect2d");
		this.effect3 = var1.getChild("effect3");
		this.effect3a = this.effect3.getChild("effect3a");
		this.effect3b = this.effect3.getChild("effect3b");
		this.effect3c = this.effect3.getChild("effect3c");
		this.effect3d = this.effect3.getChild("effect3d");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("effect1",CubeListBuilder.create(),PartPose.offset(0F,-1.5F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("effect1a",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-2.4435F,0.2658F,8F,0F,9F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0.4363F,0F,0F));
		PartDefinition var4 = var2.addOrReplaceChild("effect1b",CubeListBuilder.create().texOffs(0,0).addBox(-4F,2.4435F,0.2658F,8F,0F,9F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,-0.4363F,0F,0F));
		PartDefinition var5 = var2.addOrReplaceChild("effect1c",CubeListBuilder.create().texOffs(9,0).addBox(2.9435F,-4.5F,0.2658F,0F,8F,9F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0F,0.4363F,0F));
		PartDefinition var6 = var2.addOrReplaceChild("effect1d",CubeListBuilder.create().texOffs(9,0).addBox(-2.9435F,-4.5F,0.2658F,0F,8F,9F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0F,-0.4363F,0F));
		PartDefinition var7 = var1.addOrReplaceChild("effect2",CubeListBuilder.create(),PartPose.offsetAndRotation(0F,-1.5F,2F,0F,0F,0.3491F));
		PartDefinition var8 = var7.addOrReplaceChild("effect2a",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-2.4435F,0.2658F,8F,0F,9F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0.4363F,0F,0F));
		PartDefinition var9 = var7.addOrReplaceChild("effect2b",CubeListBuilder.create().texOffs(0,0).addBox(-4F,2.4435F,0.2658F,8F,0F,9F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,-0.4363F,0F,0F));
		PartDefinition var10 = var7.addOrReplaceChild("effect2c",CubeListBuilder.create().texOffs(9,0).addBox(2.9435F,-4.5F,0.2658F,0F,8F,9F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0F,0.4363F,0F));
		PartDefinition var11 = var7.addOrReplaceChild("effect2d",CubeListBuilder.create().texOffs(9,0).addBox(-2.9435F,-4.5F,0.2658F,0F,8F,9F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0F,-0.4363F,0F));
		PartDefinition var12 = var1.addOrReplaceChild("effect3",CubeListBuilder.create(),PartPose.offsetAndRotation(0F,-1.5F,5F,0F,0F,-0.3491F));
		PartDefinition var13 = var12.addOrReplaceChild("effect3a",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-2.4435F,0.2658F,8F,0F,9F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0.4363F,0F,0F));
		PartDefinition var14 = var12.addOrReplaceChild("effect3b",CubeListBuilder.create().texOffs(0,0).addBox(-4F,2.4435F,0.2658F,8F,0F,9F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,-0.4363F,0F,0F));
		PartDefinition var15 = var12.addOrReplaceChild("effect3c",CubeListBuilder.create().texOffs(9,0).addBox(2.9435F,-4.5F,0.2658F,0F,8F,9F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0F,0.4363F,0F));
		PartDefinition var16 = var12.addOrReplaceChild("effect3d",CubeListBuilder.create().texOffs(9,0).addBox(-2.9435F,-4.5F,0.2658F,0F,8F,9F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0F,-0.4363F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(Entity var1, float var2, float var3, float var4, float var5, float var6) {
		this.effect1.zRot = ((var4) * (0.6F)) % (360F);
		this.effect2.zRot = ((var4) * (-0.6F)) % (360F);
		this.effect3.zRot = ((var4) * (0.5F)) % (360F);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.effect1.render(var1,var2,var3,var4,var5);
		this.effect2.render(var1,var2,var3,var4,var5);
		this.effect3.render(var1,var2,var3,var4,var5);
	}
}