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

public class WhiteWalkieCoatModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","white_walkie_coat"),"main");
	private final ModelPart hoodie;
	private final ModelPart hoodieTop;
	private final ModelPart leftEar;
	private final ModelPart rightEar;
	private final ModelPart leftFoodString;
	private final ModelPart rightFootString;
	private final ModelPart coat;
	private final ModelPart coatBack;
	private final ModelPart coatbacksection3;
	private final ModelPart leftSleeve;
	private final ModelPart rightSleeve;
	private final ModelPart leftShoulderPad;
	private final ModelPart rightShoulderPad;
	private final ModelPart neck;

	public WhiteWalkieCoatModel(ModelPart var1) {
		super(var1);
		this.hoodie = var1.getChild("hoodie");
		this.hoodieTop = this.hoodie.getChild("hoodieTop");
		this.leftEar = this.hoodieTop.getChild("leftEar");
		this.rightEar = this.hoodieTop.getChild("rightEar");
		this.leftFoodString = this.hoodie.getChild("leftFoodString");
		this.rightFootString = this.hoodie.getChild("rightFootString");
		this.coat = var1.getChild("coat");
		this.coatBack = this.coat.getChild("coatBack");
		this.coatbacksection3 = this.coatBack.getChild("coatbacksection3");
		this.leftSleeve = this.coat.getChild("leftSleeve");
		this.rightSleeve = this.coat.getChild("rightSleeve");
		this.leftShoulderPad = this.coat.getChild("leftShoulderPad");
		this.rightShoulderPad = this.coat.getChild("rightShoulderPad");
		this.neck = this.coat.getChild("neck");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("hoodie",CubeListBuilder.create().texOffs(24,33).addBox(-5F,-9F,-5F,10F,10F,10F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("hoodieTop",CubeListBuilder.create().texOffs(32,54).addBox(-4F,-32.25F,-4F,8F,2F,8F,new CubeDeformation(0F)),PartPose.offset(0F,22F,0F));
		PartDefinition var4 = var3.addOrReplaceChild("leftEar",CubeListBuilder.create().texOffs(50,1).mirror().addBox(-0.5018F,-0.5884F,-1.5F,4F,1F,3F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(2.5623F,-31.902F,0.125F,0F,0F,-0.2182F));
		PartDefinition var5 = var3.addOrReplaceChild("rightEar",CubeListBuilder.create().texOffs(50,1).addBox(-3.4982F,-0.5884F,-1.5F,4F,1F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2.5623F,-31.902F,0.125F,0F,0F,0.2182F));
		PartDefinition var6 = var2.addOrReplaceChild("leftFoodString",CubeListBuilder.create().texOffs(22,55).mirror().addBox(-1F,-1F,-1F,3F,7F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(1F,1F,-3F,-0.0873F,0F,-0.1309F));
		PartDefinition var7 = var2.addOrReplaceChild("rightFootString",CubeListBuilder.create().texOffs(22,55).addBox(-2F,-1F,-1F,3F,7F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-1F,1F,-3F,-0.0873F,0F,0.1309F));
		PartDefinition var8 = var1.addOrReplaceChild("coat",CubeListBuilder.create().texOffs(0,0).addBox(-10.5F,-0.9962F,0.0872F,21F,22F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,1.5F,0.0873F,0F,0F));
		PartDefinition var9 = var8.addOrReplaceChild("coatBack",CubeListBuilder.create().texOffs(0,1).addBox(-10.5F,0F,0F,21F,21F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-0.9962F,0.0872F,0.1309F,0F,0F)).addOrReplaceChild("coatbacksection3",CubeListBuilder.create().texOffs(0,2).addBox(-10.5F,0F,0F,21F,20F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0.1309F,0F,0F));
		PartDefinition var10 = var8.addOrReplaceChild("leftSleeve",CubeListBuilder.create().texOffs(0,41).mirror().addBox(-0.1F,0F,-4.95F,4F,16F,7F,new CubeDeformation(0F)).mirror(false),PartPose.offset(7.6F,-0.7462F,0.5372F));
		PartDefinition var11 = var8.addOrReplaceChild("rightSleeve",CubeListBuilder.create().texOffs(0,41).addBox(-3.9F,0F,-4.95F,4F,16F,7F,new CubeDeformation(0F)),PartPose.offset(-7.6F,-0.7462F,0.5372F));
		PartDefinition var12 = var8.addOrReplaceChild("leftShoulderPad",CubeListBuilder.create().texOffs(1,28).addBox(-2F,-1.5F,-4F,5F,3F,8F,new CubeDeformation(0F)),PartPose.offsetAndRotation(6.6F,-1.4608F,-0.2066F,-0.192F,0F,0F));
		PartDefinition var13 = var8.addOrReplaceChild("rightShoulderPad",CubeListBuilder.create().texOffs(0,28).mirror().addBox(-3F,-1.5F,-4F,5F,3F,8F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-6.6F,-1.4608F,-0.2066F,-0.192F,0F,0F));
		PartDefinition var14 = var8.addOrReplaceChild("neck",CubeListBuilder.create().texOffs(0,17).addBox(-8.21F,-1.696F,-3.5329F,16F,3F,4F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0.2F,-0.1022F,3.2357F,-0.192F,0F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.hoodie.copyFrom(this.head);
		this.hoodie.render(var1,var2,var3,var4,var5);
		this.coat.render(var1,var2,var3,var4,var5);
	}
}