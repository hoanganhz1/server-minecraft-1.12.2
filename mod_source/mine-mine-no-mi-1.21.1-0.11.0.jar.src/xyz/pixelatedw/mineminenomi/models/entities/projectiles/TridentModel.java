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

public class TridentModel<T extends Entity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","trident"),"main");
	private final ModelPart spear;
	private final ModelPart tip;
	private final ModelPart spearTip1;
	private final ModelPart spearTip2;
	private final ModelPart spearTip3;
	private final ModelPart spearTip4;
	private final ModelPart spearTip5;
	private final ModelPart spearTip6;
	private final ModelPart spearTip7;

	public TridentModel(ModelPart var1) {
		this.spear = var1.getChild("spear");
		this.tip = this.spear.getChild("tip");
		this.spearTip1 = this.tip.getChild("spearTip1");
		this.spearTip2 = this.tip.getChild("spearTip2");
		this.spearTip3 = this.tip.getChild("spearTip3");
		this.spearTip4 = this.tip.getChild("spearTip4");
		this.spearTip5 = this.tip.getChild("spearTip5");
		this.spearTip6 = this.tip.getChild("spearTip6");
		this.spearTip7 = this.tip.getChild("spearTip7");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("spear",CubeListBuilder.create().texOffs(0,0).addBox(-0.5F,-0.5F,-10F,1F,1F,30F,new CubeDeformation(0F)).texOffs(0,0).addBox(-1F,-1F,-8F,2F,2F,2F,new CubeDeformation(0F)).texOffs(0,5).addBox(-3F,-0.5F,-12F,6F,1F,2F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F)).addOrReplaceChild("tip",CubeListBuilder.create(),PartPose.offset(0F,14F,-5F));
		PartDefinition var2 = var1.addOrReplaceChild("spearTip1",CubeListBuilder.create().texOffs(0,9).addBox(-0.5F,-0.5F,-2F,1F,1F,4F,new CubeDeformation(0F)),PartPose.offset(0F,-14F,-9F));
		PartDefinition var3 = var1.addOrReplaceChild("spearTip2",CubeListBuilder.create().texOffs(0,15).addBox(-0.5F,-0.5F,-2.5F,1F,1F,5F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0.4659F,-14F,-8.462F,0F,0.1745F,0F));
		PartDefinition var4 = var1.addOrReplaceChild("spearTip3",CubeListBuilder.create().texOffs(0,22).addBox(-0.5F,-0.5F,-2.5F,1F,1F,5F,new CubeDeformation(0.02F)),PartPose.offsetAndRotation(-0.4659F,-14F,-8.462F,0F,-0.1745F,0F));
		PartDefinition var5 = var1.addOrReplaceChild("spearTip4",CubeListBuilder.create().texOffs(13,19).addBox(-0.5F,-0.5F,-0.5F,1F,1F,1F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(-2.5F,-14F,-7.5F,3.1416F,1.5708F,3.1416F));
		PartDefinition var6 = var1.addOrReplaceChild("spearTip5",CubeListBuilder.create().texOffs(18,19).addBox(-1F,-0.5F,-0.5F,2F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2.1881F,-14F,-8.7687F,0F,1.2217F,0F));
		PartDefinition var7 = var1.addOrReplaceChild("spearTip6",CubeListBuilder.create().texOffs(13,22).addBox(-0.5F,-0.5F,-0.5F,1F,1F,1F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(2.5F,-14F,-7.5F,3.1416F,1.5708F,3.1416F));
		PartDefinition var8 = var1.addOrReplaceChild("spearTip7",CubeListBuilder.create().texOffs(18,22).addBox(-1F,-0.5F,-0.5F,2F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(2.1881F,-14F,-8.7687F,0F,-1.2217F,0F));
		return LayerDefinition.create(var0,64,32);
	}
	public void setupAnim(Entity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.spear.render(var1,var2,var3,var4,var5);
	}
}