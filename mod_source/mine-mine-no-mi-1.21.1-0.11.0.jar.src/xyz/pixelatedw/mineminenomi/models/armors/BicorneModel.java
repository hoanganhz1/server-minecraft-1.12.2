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

public class BicorneModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","bicorne"),"main");
	private final ModelPart bicorne;
	private final ModelPart topFront;
	private final ModelPart topBack;
	private final ModelPart leftSide;
	private final ModelPart subLeftSide1;
	private final ModelPart subLeftSide3;
	private final ModelPart subLeftSide2;
	private final ModelPart rightSide;
	private final ModelPart rightSide1;
	private final ModelPart rightSide2;
	private final ModelPart rightSide3;

	public BicorneModel(ModelPart var1) {
		super(var1);
		this.bicorne = var1.getChild("bicorne");
		this.topFront = this.bicorne.getChild("topFront");
		this.topBack = this.bicorne.getChild("topBack");
		this.leftSide = this.bicorne.getChild("leftSide");
		this.subLeftSide1 = this.leftSide.getChild("subLeftSide1");
		this.subLeftSide3 = this.leftSide.getChild("subLeftSide3");
		this.subLeftSide2 = this.leftSide.getChild("subLeftSide2");
		this.rightSide = this.bicorne.getChild("rightSide");
		this.rightSide1 = this.rightSide.getChild("rightSide1");
		this.rightSide2 = this.rightSide.getChild("rightSide2");
		this.rightSide3 = this.rightSide.getChild("rightSide3");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("bicorne",CubeListBuilder.create().texOffs(0,23).addBox(-9F,-8F,-4F,18F,2F,8F,new CubeDeformation(0F)).texOffs(0,11).addBox(-4F,-9F,-4F,9F,2F,8F,new CubeDeformation(0F)).texOffs(0,0).addBox(-3F,-11F,-4F,6F,2F,8F,new CubeDeformation(0F)),PartPose.offset(0F,-2.5F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("topFront",CubeListBuilder.create().texOffs(0,37).addBox(-4F,-5F,-4.5F,8F,6F,1F,new CubeDeformation(0F)).texOffs(0,34).addBox(-4F,-6F,-4.5F,8F,1F,1F,new CubeDeformation(0F)).texOffs(19,40).addBox(-6F,-2F,-4.5F,2F,3F,1F,new CubeDeformation(0F)).texOffs(26,38).addBox(6F,-2F,-4.5F,2F,1F,1F,new CubeDeformation(0F)).texOffs(19,37).addBox(4F,-3F,-4.5F,2F,1F,1F,new CubeDeformation(0F)).texOffs(26,38).addBox(-8F,-2F,-4.5F,2F,1F,1F,new CubeDeformation(0F)).texOffs(19,37).addBox(-6F,-3F,-4.5F,2F,1F,1F,new CubeDeformation(0F)).texOffs(19,40).addBox(4F,-2F,-4.5F,2F,3F,1F,new CubeDeformation(0F)).texOffs(26,41).addBox(-8F,-1F,-4.5F,2F,2F,1F,new CubeDeformation(0F)).texOffs(26,41).addBox(6F,-1F,-4.5F,2F,2F,1F,new CubeDeformation(0F)).texOffs(19,34).addBox(-5F,-5F,-4.5F,1F,2F,1F,new CubeDeformation(0F)).texOffs(19,34).addBox(4F,-5F,-4.5F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offset(0F,-6.5F,-0.5F));
		PartDefinition var3 = var1.addOrReplaceChild("topBack",CubeListBuilder.create().texOffs(0,37).addBox(-4F,-5F,3.5F,8F,6F,1F,new CubeDeformation(0F)).texOffs(19,40).addBox(-6F,-2F,3.5F,2F,3F,1F,new CubeDeformation(0F)).texOffs(19,40).addBox(4F,-2F,3.5F,2F,3F,1F,new CubeDeformation(0F)).texOffs(26,41).addBox(-8F,-1F,3.5F,2F,2F,1F,new CubeDeformation(0F)).texOffs(26,41).addBox(6F,-1F,3.5F,2F,2F,1F,new CubeDeformation(0F)).texOffs(26,38).addBox(-8F,-2F,3.5F,2F,1F,1F,new CubeDeformation(0F)).texOffs(19,37).addBox(-6F,-3F,3.5F,2F,1F,1F,new CubeDeformation(0F)).texOffs(0,34).addBox(-4F,-6F,3.5F,8F,1F,1F,new CubeDeformation(0F)).texOffs(19,37).addBox(4F,-3F,3.5F,2F,1F,1F,new CubeDeformation(0F)).texOffs(19,34).addBox(4F,-5F,3.5F,1F,2F,1F,new CubeDeformation(0F)).texOffs(19,34).addBox(-5F,-5F,3.5F,1F,2F,1F,new CubeDeformation(0F)).texOffs(26,38).addBox(6F,-2F,3.5F,2F,1F,1F,new CubeDeformation(0F)),PartPose.offset(0F,-6.5F,0.5F));
		PartDefinition var4 = var1.addOrReplaceChild("leftSide",CubeListBuilder.create(),PartPose.offset(8.75F,-8F,4F));
		PartDefinition var5 = var4.addOrReplaceChild("subLeftSide1",CubeListBuilder.create().texOffs(33,41).addBox(-1.35F,0.5F,-0.2F,3F,2F,1F,new CubeDeformation(0F)).texOffs(33,38).addBox(-1.35F,-0.5F,-0.2F,3F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,-8F,0F,-0.7854F,0F));
		PartDefinition var6 = var4.addOrReplaceChild("subLeftSide3",CubeListBuilder.create().texOffs(33,41).addBox(-1.5F,0.5F,-0.5F,3F,2F,1F,new CubeDeformation(0F)).texOffs(33,38).addBox(-1.5F,-0.5F,-0.5F,3F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.1061F,0F,-0.3182F,0F,0.7854F,0F));
		PartDefinition var7 = var4.addOrReplaceChild("subLeftSide2",CubeListBuilder.create().texOffs(42,41).addBox(-0.05F,0.5F,-0.55F,6F,2F,1F,new CubeDeformation(0F)).texOffs(42,38).addBox(-0.05F,-0.5F,-0.55F,6F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.75F,0F,-1F,0F,1.5708F,0F));
		PartDefinition var8 = var1.addOrReplaceChild("rightSide",CubeListBuilder.create(),PartPose.offset(-8.75F,-8F,4F));
		PartDefinition var9 = var8.addOrReplaceChild("rightSide1",CubeListBuilder.create().texOffs(33,41).mirror().addBox(-1.65F,0.5F,-0.2F,3F,2F,1F,new CubeDeformation(0F)).mirror(false).texOffs(33,38).mirror().addBox(-1.65F,-0.5F,-0.2F,3F,1F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0F,0F,-8F,0F,0.7854F,0F));
		PartDefinition var10 = var8.addOrReplaceChild("rightSide2",CubeListBuilder.create().texOffs(33,41).mirror().addBox(-1.5F,0.5F,-0.5F,3F,2F,1F,new CubeDeformation(0F)).mirror(false).texOffs(33,38).mirror().addBox(-1.5F,-0.5F,-0.5F,3F,1F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0.1061F,0F,-0.3182F,0F,-0.7854F,0F));
		PartDefinition var11 = var8.addOrReplaceChild("rightSide3",CubeListBuilder.create().texOffs(42,41).mirror().addBox(-5.95F,0.5F,-0.55F,6F,2F,1F,new CubeDeformation(0F)).mirror(false).texOffs(42,38).mirror().addBox(-5.95F,-0.5F,-0.55F,6F,1F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-0.75F,0F,-1F,0F,-1.5708F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.bicorne.copyFrom(this.hat);
		this.bicorne.render(var1,var2,var3,var4,var5);
	}
}