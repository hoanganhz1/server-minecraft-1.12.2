package xyz.pixelatedw.mineminenomi.models.entities.mobs.hair;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

public class BuggyHairModel<T extends LivingEntity> extends HumanoidModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","buggy_hair"),"main");
	private final ModelPart leftHair;
	private final ModelPart leftHair2;
	private final ModelPart leftHair4;
	private final ModelPart leftHair9;
	private final ModelPart leftHair8;
	private final ModelPart leftHair6;
	private final ModelPart leftHair7;
	private final ModelPart leftHair5;
	private final ModelPart leftHair3;
	private final ModelPart rightHair;
	private final ModelPart rightHair2;
	private final ModelPart rightHair3;
	private final ModelPart rightHair4;
	private final ModelPart rightHair5;
	private final ModelPart rightHair6;
	private final ModelPart rightHair7;
	private final ModelPart rightHair8;
	private final ModelPart rightHair9;

	public BuggyHairModel(ModelPart var1) {
		super(var1);
		this.leftHair = this.head.getChild("leftHair");
		this.leftHair2 = this.leftHair.getChild("leftHair2");
		this.leftHair4 = this.leftHair2.getChild("leftHair4");
		this.leftHair9 = this.leftHair2.getChild("leftHair9");
		this.leftHair8 = this.leftHair2.getChild("leftHair8");
		this.leftHair6 = this.leftHair2.getChild("leftHair6");
		this.leftHair7 = this.leftHair2.getChild("leftHair7");
		this.leftHair5 = this.leftHair2.getChild("leftHair5");
		this.leftHair3 = this.leftHair2.getChild("leftHair3");
		this.rightHair = this.head.getChild("rightHair");
		this.rightHair2 = this.rightHair.getChild("rightHair2");
		this.rightHair3 = this.rightHair2.getChild("rightHair3");
		this.rightHair4 = this.rightHair2.getChild("rightHair4");
		this.rightHair5 = this.rightHair2.getChild("rightHair5");
		this.rightHair6 = this.rightHair2.getChild("rightHair6");
		this.rightHair7 = this.rightHair2.getChild("rightHair7");
		this.rightHair8 = this.rightHair2.getChild("rightHair8");
		this.rightHair9 = this.rightHair2.getChild("rightHair9");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("head",CubeListBuilder.create(),PartPose.offset(0F,-2.5F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("leftHair",CubeListBuilder.create().texOffs(0,11).addBox(-2F,-5F,-2F,3F,6F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(4.5F,-7.5F,0F,0F,0F,0.9599F)).addOrReplaceChild("leftHair2",CubeListBuilder.create().texOffs(9,2).addBox(-1.85F,-2.9F,-2F,3F,4F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.25F,-4.5F,0F,0F,0F,0.5236F));
		var2.addOrReplaceChild("leftHair4",CubeListBuilder.create().texOffs(0,4).addBox(-1.35F,-3.9F,-1.5F,2F,4F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.5F,-1.5F,-0.25F,0.2477F,-0.1198F,0.2333F));
		var2.addOrReplaceChild("leftHair9",CubeListBuilder.create().texOffs(0,4).addBox(-1.35F,-3.9F,-1.5F,2F,4F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.5F,-1.5F,1.25F,-0.3768F,0.0288F,0.2172F));
		var2.addOrReplaceChild("leftHair8",CubeListBuilder.create().texOffs(0,4).addBox(-1.35F,-3.9F,-1.5F,2F,4F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.5F,-1.5F,1.25F,-0.289F,-0.0337F,0.0081F));
		var2.addOrReplaceChild("leftHair6",CubeListBuilder.create().texOffs(0,4).addBox(-1.35F,-3.9F,-1.5F,2F,4F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.5F,-1.5F,0.25F,0.1135F,-0.0827F,-0.1597F));
		var2.addOrReplaceChild("leftHair7",CubeListBuilder.create().texOffs(0,4).addBox(-1.35F,-3.9F,-1.5F,2F,4F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.25F,-1.5F,0.75F,-0.0577F,-0.1198F,0.2333F));
		var2.addOrReplaceChild("leftHair5",CubeListBuilder.create().texOffs(0,4).addBox(-1.35F,-3.9F,-1.5F,2F,4F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.5F,-1.5F,0.25F,0.0512F,-0.1307F,0.4089F));
		var2.addOrReplaceChild("leftHair3",CubeListBuilder.create().texOffs(0,4).addBox(-1.35F,-3.9F,-1.5F,2F,4F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.5F,-1.5F,-0.25F,0.4659F,-0.1198F,0.2333F));
		PartDefinition var3 = var1.addOrReplaceChild("rightHair",CubeListBuilder.create().texOffs(0,11).mirror().addBox(-1F,-5F,-1F,3F,6F,4F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-4.5F,-7.5F,-1F,0F,0F,-0.9599F)).addOrReplaceChild("rightHair2",CubeListBuilder.create().texOffs(9,2).mirror().addBox(-1.15F,-2.9F,-1F,3F,4F,4F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-0.25F,-4.5F,0F,0F,0F,-0.5236F));
		var3.addOrReplaceChild("rightHair3",CubeListBuilder.create().texOffs(0,4).mirror().addBox(-0.65F,-3.9F,-0.5F,2F,4F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0.5F,-1.5F,-0.25F,0.2477F,0.1198F,-0.2333F));
		var3.addOrReplaceChild("rightHair4",CubeListBuilder.create().texOffs(0,4).mirror().addBox(-0.65F,-3.9F,-0.5F,2F,4F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-0.5F,-1.5F,1.25F,-0.3768F,-0.0288F,-0.2172F));
		var3.addOrReplaceChild("rightHair5",CubeListBuilder.create().texOffs(0,4).mirror().addBox(-0.65F,-3.9F,-0.5F,2F,4F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0.5F,-1.5F,1.25F,-0.289F,0.0337F,-0.0081F));
		var3.addOrReplaceChild("rightHair6",CubeListBuilder.create().texOffs(0,4).mirror().addBox(-0.65F,-3.9F,-0.5F,2F,4F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0.5F,-1.5F,0.25F,0.1135F,0.0827F,0.1597F));
		var3.addOrReplaceChild("rightHair7",CubeListBuilder.create().texOffs(0,4).mirror().addBox(-0.65F,-3.9F,-0.5F,2F,4F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0.25F,-1.5F,0.75F,-0.0577F,0.1198F,-0.2333F));
		var3.addOrReplaceChild("rightHair8",CubeListBuilder.create().texOffs(0,4).mirror().addBox(-0.65F,-3.9F,-0.5F,2F,4F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-0.5F,-1.5F,0.25F,0.0512F,0.1307F,-0.4089F));
		var3.addOrReplaceChild("rightHair9",CubeListBuilder.create().texOffs(0,4).mirror().addBox(-0.65F,-3.9F,-0.5F,2F,4F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-0.5F,-1.5F,-0.25F,0.4659F,0.1198F,-0.2333F));
		return LayerDefinition.create(var0,32,32);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		super.setupAnim(var1,var2,var3,var4,var5,var6);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.head.render(var1,var2,var3,var4,var5);
	}
}