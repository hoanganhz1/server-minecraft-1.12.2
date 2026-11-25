package xyz.pixelatedw.mineminenomi.models.armors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class TomoeDrumsModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","tomoe_drums"),"main");
	private final ModelPart tomoeDrums;
	private final ModelPart bars;
	private final ModelPart bars6;
	private final ModelPart bars7;
	private final ModelPart bars8;
	private final ModelPart bars9;
	private final ModelPart bars10;
	private final ModelPart bars1;
	private final ModelPart bars2;
	private final ModelPart bars3;
	private final ModelPart bars4;
	private final ModelPart bars5;
	private final ModelPart drums;
	private final ModelPart drum2;
	private final ModelPart drum4;
	private final ModelPart drum1;
	private final ModelPart drum3;

	public TomoeDrumsModel(ModelPart var1) {
		super(var1);
		this.tomoeDrums = var1.getChild("tomoeDrums");
		this.bars = this.tomoeDrums.getChild("bars");
		this.bars6 = this.bars.getChild("bars6");
		this.bars7 = this.bars.getChild("bars7");
		this.bars8 = this.bars.getChild("bars8");
		this.bars9 = this.bars.getChild("bars9");
		this.bars10 = this.bars.getChild("bars10");
		this.bars1 = this.bars.getChild("bars1");
		this.bars2 = this.bars.getChild("bars2");
		this.bars3 = this.bars.getChild("bars3");
		this.bars4 = this.bars.getChild("bars4");
		this.bars5 = this.bars.getChild("bars5");
		this.drums = this.tomoeDrums.getChild("drums");
		this.drum2 = this.drums.getChild("drum2");
		this.drum4 = this.drums.getChild("drum4");
		this.drum1 = this.drums.getChild("drum1");
		this.drum3 = this.drums.getChild("drum3");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("tomoeDrums",CubeListBuilder.create(),PartPose.offset(0F,1F,3F));
		PartDefinition var2 = var1.addOrReplaceChild("bars",CubeListBuilder.create().texOffs(0,0).addBox(-1.5F,-18.07F,3F,3F,1F,1F,new CubeDeformation(0.01F)),PartPose.offset(0F,4F,-1F));
		PartDefinition var3 = var2.addOrReplaceChild("bars6",CubeListBuilder.create().texOffs(0,0).mirror().addBox(5.7F,-34.6F,2F,5F,1F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(23F,-46F,1F,0F,0F,-2.7925F));
		PartDefinition var4 = var2.addOrReplaceChild("bars7",CubeListBuilder.create().texOffs(0,0).mirror().addBox(7F,-30.5F,2F,5F,1F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(38F,-20.75F,1F,0F,0F,-2.0944F));
		PartDefinition var5 = var2.addOrReplaceChild("bars8",CubeListBuilder.create().texOffs(0,0).mirror().addBox(-2.45F,-0.5501F,-0.5F,5F,1F,1F,new CubeDeformation(0.01F)).mirror(false),PartPose.offsetAndRotation(8.5F,-9.4999F,3.5F,0F,0F,-1.5708F));
		PartDefinition var6 = var2.addOrReplaceChild("bars9",CubeListBuilder.create().texOffs(0,0).mirror().addBox(-2.5F,-0.5F,-0.5F,5F,1F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(7.0392F,-5.4773F,3.5F,0F,0F,-0.8727F));
		PartDefinition var7 = var2.addOrReplaceChild("bars10",CubeListBuilder.create().texOffs(0,0).mirror().addBox(1.1F,-21.05F,1.5019F,5F,1F,1F,new CubeDeformation(0.01F)).mirror(false),PartPose.offsetAndRotation(10F,17.25F,1F,0F,-0.0873F,-0.4887F));
		PartDefinition var8 = var2.addOrReplaceChild("bars1",CubeListBuilder.create().texOffs(0,0).addBox(-10.7F,-34.6F,2F,5F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-23F,-46F,1F,0F,0F,2.7925F));
		PartDefinition var9 = var2.addOrReplaceChild("bars2",CubeListBuilder.create().texOffs(0,0).addBox(-12F,-30.5F,2F,5F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-38F,-20.75F,1F,0F,0F,2.0944F));
		PartDefinition var10 = var2.addOrReplaceChild("bars3",CubeListBuilder.create().texOffs(0,0).addBox(-2.55F,-0.5501F,-0.5F,5F,1F,1F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(-8.5F,-9.4999F,3.5F,0F,0F,1.5708F));
		PartDefinition var11 = var2.addOrReplaceChild("bars4",CubeListBuilder.create().texOffs(0,0).addBox(-2.5F,-0.5F,-0.5F,5F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-7.0392F,-5.4773F,3.5F,0F,0F,0.8727F));
		PartDefinition var12 = var2.addOrReplaceChild("bars5",CubeListBuilder.create().texOffs(0,0).addBox(-6.1F,-21.05F,1.5019F,5F,1F,1F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(-10F,17.25F,1F,0F,0.0873F,0.4887F));
		PartDefinition var13 = var1.addOrReplaceChild("drums",CubeListBuilder.create(),PartPose.offset(0F,2F,-1F));
		PartDefinition var14 = var13.addOrReplaceChild("drum2",CubeListBuilder.create().texOffs(0,5).addBox(-1.5F,-1.5F,-1.5F,3F,3F,3F,new CubeDeformation(0F)),PartPose.offset(6.5F,-13.5F,3.5F));
		PartDefinition var15 = var13.addOrReplaceChild("drum4",CubeListBuilder.create().texOffs(0,5).addBox(-1.5F,-1.5F,-1.5F,3F,3F,3F,new CubeDeformation(0F)),PartPose.offset(8.5F,-5.5F,3.5F));
		PartDefinition var16 = var13.addOrReplaceChild("drum1",CubeListBuilder.create().texOffs(0,5).addBox(-1.5F,-1.5F,-1.5F,3F,3F,3F,new CubeDeformation(0F)),PartPose.offset(-8.5F,-5.5F,3.5F));
		PartDefinition var17 = var13.addOrReplaceChild("drum3",CubeListBuilder.create().texOffs(0,5).addBox(-1.5F,-1.5F,-1.5F,3F,3F,3F,new CubeDeformation(0F)),PartPose.offset(-6.5F,-13.5F,3.5F));
		return LayerDefinition.create(var0,16,16);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.tomoeDrums.copyFrom(this.body);
		this.tomoeDrums.render(var1,var2,var3,var4,var5);
	}
}