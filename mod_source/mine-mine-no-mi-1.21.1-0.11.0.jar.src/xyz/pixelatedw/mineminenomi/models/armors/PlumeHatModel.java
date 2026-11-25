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

public class PlumeHatModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","plume_hat"),"main");
	private final ModelPart plumeHat;
	private final ModelPart top;
	private final ModelPart feather;
	private final ModelPart feather2;
	private final ModelPart feather3;
	private final ModelPart feather4;
	private final ModelPart feather5;
	private final ModelPart feather6;
	private final ModelPart leftSide;
	private final ModelPart rightSide;
	private final ModelPart leftSide2;
	private final ModelPart rightSide2;

	public PlumeHatModel(ModelPart var1) {
		super(var1);
		this.plumeHat = var1.getChild("plumeHat");
		this.top = this.plumeHat.getChild("top");
		this.feather = this.plumeHat.getChild("feather");
		this.feather2 = this.feather.getChild("feather2");
		this.feather3 = this.feather.getChild("feather3");
		this.feather4 = this.feather.getChild("feather4");
		this.feather5 = this.feather.getChild("feather5");
		this.feather6 = this.feather.getChild("feather6");
		this.leftSide = this.plumeHat.getChild("leftSide");
		this.rightSide = this.plumeHat.getChild("rightSide");
		this.leftSide2 = this.plumeHat.getChild("leftSide2");
		this.rightSide2 = this.plumeHat.getChild("rightSide2");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("plumeHat",CubeListBuilder.create().texOffs(0,26).addBox(-3F,-6.5F,-6F,6F,1F,12F,new CubeDeformation(-0.001F)),PartPose.offset(0F,0F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("top",CubeListBuilder.create().texOffs(0,50).addBox(-4F,-5F,-4F,8F,5F,8F,new CubeDeformation(0.52F)).texOffs(4,41).addBox(-3F,-5.75F,-3F,6F,1F,6F,new CubeDeformation(1F)),PartPose.offset(0F,-6.5F,0F));
		PartDefinition var3 = var1.addOrReplaceChild("feather",CubeListBuilder.create().texOffs(38,26).addBox(0F,-4F,0F,2F,3F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(4.25F,-6F,0.75F,0.1745F,0F,0F));
		PartDefinition var4 = var3.addOrReplaceChild("feather2",CubeListBuilder.create().texOffs(39,36).addBox(0F,-4F,-1F,2F,3F,5F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0F,0F,0F,0.2182F,0F,0F));
		PartDefinition var5 = var3.addOrReplaceChild("feather3",CubeListBuilder.create().texOffs(40,46).addBox(0F,-4.04F,-1.75F,2F,2F,2F,new CubeDeformation(-0.02F)),PartPose.offsetAndRotation(0F,-0.25F,0.25F,0.6109F,0F,0F));
		PartDefinition var6 = var3.addOrReplaceChild("feather4",CubeListBuilder.create().texOffs(39,52).addBox(0F,-4.2F,-2.25F,2F,1F,2F,new CubeDeformation(-0.03F)),PartPose.offsetAndRotation(0F,0F,0F,0.7854F,0F,0F));
		PartDefinition var7 = var3.addOrReplaceChild("feather5",CubeListBuilder.create().texOffs(39,16).addBox(0F,-4F,-1F,2F,3F,5F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0F,0.25F,3.75F,-0.2182F,0F,0F));
		PartDefinition var8 = var3.addOrReplaceChild("feather6",CubeListBuilder.create().texOffs(40,8).addBox(0F,-2.25F,-2F,2F,3F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0.8602F,8.2891F,-0.7418F,0F,0F));
		PartDefinition var9 = var1.addOrReplaceChild("leftSide",CubeListBuilder.create().texOffs(0,13).addBox(-4F,-1.15F,-8F,6F,1F,11F,new CubeDeformation(-0.01F)).texOffs(0,0).addBox(-4F,-1.55F,-8F,6F,1F,11F,new CubeDeformation(-0.1F)),PartPose.offsetAndRotation(-5F,-5.25F,-1F,-0.1745F,-0.4363F,0.3491F));
		PartDefinition var10 = var1.addOrReplaceChild("rightSide",CubeListBuilder.create().texOffs(0,13).addBox(-2F,-1.15F,-8F,6F,1F,11F,new CubeDeformation(-0.01F)).texOffs(0,0).addBox(-2F,-1.55F,-8F,6F,1F,11F,new CubeDeformation(-0.1F)),PartPose.offsetAndRotation(5F,-5.25F,-1F,-0.1745F,0.4363F,-0.3491F));
		PartDefinition var11 = var1.addOrReplaceChild("leftSide2",CubeListBuilder.create().texOffs(0,13).addBox(-4F,-1.15F,-9F,6F,1F,11F,new CubeDeformation(-0.01F)).texOffs(0,0).addBox(-4F,-1.55F,-9F,6F,1F,11F,new CubeDeformation(-0.1F)),PartPose.offsetAndRotation(-2.25F,-5.25F,6.5F,0.1745F,0.4363F,0.3491F));
		PartDefinition var12 = var1.addOrReplaceChild("rightSide2",CubeListBuilder.create().texOffs(0,13).addBox(-2F,-1.15F,-9F,6F,1F,11F,new CubeDeformation(-0.01F)).texOffs(0,0).addBox(-2F,-1.55F,-9F,6F,1F,11F,new CubeDeformation(-0.1F)),PartPose.offsetAndRotation(2.25F,-5.25F,6.5F,0.1745F,-0.4363F,-0.3491F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.plumeHat.copyFrom(this.head);
		this.plumeHat.render(var1,var2,var3,var4,var5);
	}
}