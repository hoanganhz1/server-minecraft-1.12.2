package xyz.pixelatedw.mineminenomi.models.entities;

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
import xyz.pixelatedw.mineminenomi.entities.TornadoEntity;

public class TornadoModel<T extends TornadoEntity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","tornado"),"main");
	private final ModelPart base;
	private final ModelPart funnel1;
	private final ModelPart funnel2;
	private final ModelPart funnel3;
	private final ModelPart top;

	public TornadoModel(ModelPart var1) {
		this.base = var1.getChild("base");
		this.funnel1 = this.base.getChild("funnel1");
		this.funnel2 = this.funnel1.getChild("funnel2");
		this.funnel3 = this.funnel2.getChild("funnel3");
		this.top = this.funnel3.getChild("top");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("base",CubeListBuilder.create().texOffs(0,42).addBox(-1.5F,-2.5F,-1.5F,3F,5F,3F,new CubeDeformation(0F)),PartPose.offset(0.5F,21.5F,0.5F)).addOrReplaceChild("funnel1",CubeListBuilder.create().texOffs(0,33).addBox(-2.5F,-2F,-2.5F,5F,4F,5F,new CubeDeformation(0F)),PartPose.offset(0F,-4.5F,0F)).addOrReplaceChild("funnel2",CubeListBuilder.create().texOffs(0,18).addBox(-4.5F,-3F,-4.5F,9F,6F,9F,new CubeDeformation(0F)),PartPose.offset(0F,-5F,0F)).addOrReplaceChild("funnel3",CubeListBuilder.create().texOffs(20,33).addBox(-5.5F,-4.5F,-5.5F,11F,9F,11F,new CubeDeformation(0F)),PartPose.offset(0F,-7.5F,0F)).addOrReplaceChild("top",CubeListBuilder.create().texOffs(0,0).addBox(-7.5F,-1.5F,-7.5F,15F,3F,15F,new CubeDeformation(0F)),PartPose.offset(0F,-6F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		this.base.yRot = (((var4) * (0.1F)) * (var1.getSpeed())) % (360F);
		this.funnel1.yRot = (((var4) * (-0.2F)) * (var1.getSpeed())) % (360F);
		this.funnel2.yRot = (((var4) * (0.2F)) * (var1.getSpeed())) % (360F);
		this.funnel3.yRot = (((var4) * (-0.2F)) * (var1.getSpeed())) % (360F);
		this.top.yRot = (((var4) * (0.2F)) * (var1.getSpeed())) % (360F);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.base.render(var1,var2,var3,var4,var5);
	}
}