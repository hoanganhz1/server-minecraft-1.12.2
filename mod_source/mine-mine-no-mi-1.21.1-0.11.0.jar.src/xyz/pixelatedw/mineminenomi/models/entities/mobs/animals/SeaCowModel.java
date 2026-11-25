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
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.SeaCowEntity;

public class SeaCowModel<T extends SeaCowEntity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","sea_cow"),"main");
	public final ModelPart head;
	private final ModelPart mouth;
	private final ModelPart noseRing;
	private final ModelPart leftHorn;
	private final ModelPart leftHorn2;
	private final ModelPart leftHorn3;
	private final ModelPart rightHorn;
	private final ModelPart rightHorn2;
	private final ModelPart rightHorn3;
	private final ModelPart leftEar;
	private final ModelPart rightEar;
	public final ModelPart rightFin;
	public final ModelPart rightFin2;
	public final ModelPart leftFin;
	public final ModelPart leftFin2;
	private final ModelPart body;
	private final ModelPart body2;
	private final ModelPart body3;
	private final ModelPart body4;
	private final ModelPart body5;
	private final ModelPart body6;
	private final ModelPart tail;

	public SeaCowModel(ModelPart var1) {
		this.head = var1.getChild("head");
		this.mouth = this.head.getChild("mouth");
		this.noseRing = this.mouth.getChild("noseRing");
		this.leftHorn = this.head.getChild("leftHorn");
		this.leftHorn2 = this.leftHorn.getChild("leftHorn2");
		this.leftHorn3 = this.leftHorn2.getChild("leftHorn3");
		this.rightHorn = this.head.getChild("rightHorn");
		this.rightHorn2 = this.rightHorn.getChild("rightHorn2");
		this.rightHorn3 = this.rightHorn2.getChild("rightHorn3");
		this.leftEar = this.head.getChild("leftEar");
		this.rightEar = this.head.getChild("rightEar");
		this.rightFin = var1.getChild("rightFin");
		this.rightFin2 = this.rightFin.getChild("rightFin2");
		this.leftFin = var1.getChild("leftFin");
		this.leftFin2 = this.leftFin.getChild("leftFin2");
		this.body = var1.getChild("body");
		this.body2 = this.body.getChild("body2");
		this.body3 = this.body2.getChild("body3");
		this.body4 = this.body3.getChild("body4");
		this.body5 = this.body4.getChild("body5");
		this.body6 = this.body5.getChild("body6");
		this.tail = this.body6.getChild("tail");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(76,82).addBox(-6.5F,-7.75F,-12F,13F,13F,13F,new CubeDeformation(0F)),PartPose.offset(0F,7.75F,-18F));
		PartDefinition var3 = var2.addOrReplaceChild("mouth",CubeListBuilder.create().texOffs(98,110).addBox(-3.5F,-2.1781F,-4.5795F,7F,6F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0.25F,-11.5F,0.0873F,0F,0F)).addOrReplaceChild("noseRing",CubeListBuilder.create().texOffs(99,123).addBox(-2.5F,-0.8423F,-0.4163F,5F,5F,0F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-0.1663F,-4.299F,-0.1222F,0F,0F));
		PartDefinition var4 = var2.addOrReplaceChild("leftHorn",CubeListBuilder.create().texOffs(81,118).mirror().addBox(1.1337F,1.3166F,-12.316F,3F,5F,5F,new CubeDeformation(0.4F)).mirror(false),PartPose.offsetAndRotation(6.1004F,-10.7588F,7.2694F,-0.1745F,0F,0.6981F)).addOrReplaceChild("leftHorn2",CubeListBuilder.create().texOffs(63,119).mirror().addBox(-1.6504F,3.1528F,-11.816F,3F,4F,5F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-0.3542F,-4.5847F,-0.4754F,0F,0F,-0.5236F)).addOrReplaceChild("leftHorn3",CubeListBuilder.create().texOffs(51,121).mirror().addBox(-2.9892F,3.5255F,-10.816F,2F,4F,3F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-0.4638F,-4.1606F,0.0033F,0F,0F,-0.3491F));
		PartDefinition var5 = var2.addOrReplaceChild("rightHorn",CubeListBuilder.create().texOffs(81,118).addBox(-4.1337F,1.3166F,-12.316F,3F,5F,5F,new CubeDeformation(0.4F)),PartPose.offsetAndRotation(-6.1004F,-10.7588F,7.2694F,-0.1745F,0F,-0.6981F)).addOrReplaceChild("rightHorn2",CubeListBuilder.create().texOffs(63,119).addBox(-1.3496F,3.1528F,-11.816F,3F,4F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.3542F,-4.5847F,-0.4754F,0F,0F,0.5236F)).addOrReplaceChild("rightHorn3",CubeListBuilder.create().texOffs(51,121).addBox(0.9892F,3.5255F,-10.816F,2F,4F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.4638F,-4.1606F,0.0033F,0F,0F,0.3491F));
		PartDefinition var6 = var2.addOrReplaceChild("leftEar",CubeListBuilder.create().texOffs(110,122).mirror().addBox(0F,-1F,-2F,5F,2F,4F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(6.5F,-6.125F,-8.3125F,-0.1129F,0.1334F,1.1618F));
		PartDefinition var7 = var2.addOrReplaceChild("rightEar",CubeListBuilder.create().texOffs(110,122).addBox(-5F,-1F,-2F,5F,2F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-6.5F,-6.125F,-8.3125F,-0.1129F,-0.1334F,-1.1618F));
		PartDefinition var8 = var1.addOrReplaceChild("rightFin",CubeListBuilder.create(),PartPose.offsetAndRotation(-7F,8.5F,-4F,0F,0F,0.1745F));
		PartDefinition var9 = var8.addOrReplaceChild("rightFin_r1",CubeListBuilder.create().texOffs(0,83).addBox(-2F,-8F,-5F,4F,16F,10F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.9285F,7.2187F,-0.9823F,0F,0F,0F));
		PartDefinition var10 = var8.addOrReplaceChild("rightFin2",CubeListBuilder.create(),PartPose.offsetAndRotation(-0.4321F,13.9255F,-0.9823F,0F,0F,1.2217F)).addOrReplaceChild("rightFin2_r1",CubeListBuilder.create().texOffs(0,110).addBox(-1.5F,-4F,-4.5F,3F,8F,9F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.1636F,3.1611F,0F,0F,0F,0F));
		PartDefinition var11 = var1.addOrReplaceChild("leftFin",CubeListBuilder.create(),PartPose.offsetAndRotation(7F,8.25F,-4F,0F,0F,-0.1745F));
		PartDefinition var12 = var11.addOrReplaceChild("leftFin_r1",CubeListBuilder.create().texOffs(0,83).mirror().addBox(-2F,-8F,-5F,4F,16F,10F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(1.0411F,7.566F,-0.9823F,0F,0F,0F));
		PartDefinition var13 = var11.addOrReplaceChild("leftFin2",CubeListBuilder.create(),PartPose.offsetAndRotation(0.5375F,14.0228F,-0.9823F,0F,0F,-1.1781F)).addOrReplaceChild("leftFin2_r1",CubeListBuilder.create().texOffs(0,110).mirror().addBox(-1.5F,-4F,-4.5F,3F,8F,9F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-0.3364F,3.1611F,0F,0F,0F,0F));
		PartDefinition var14 = var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,0).addBox(-5.5F,-4.268F,-7.9762F,11F,13F,9F,new CubeDeformation(0F)).texOffs(110,-9).addBox(0F,-8.268F,-7.9762F,0F,5F,9F,new CubeDeformation(0F)),PartPose.offset(0F,8.3125F,-10.625F)).addOrReplaceChild("body2",CubeListBuilder.create().texOffs(0,24).addBox(-6.5F,-3.4375F,-2.3125F,13F,15F,19F,new CubeDeformation(0F)).texOffs(93,-10).addBox(0F,-7.4375F,0.6875F,0F,5F,16F,new CubeDeformation(0F)),PartPose.offset(0F,1.3961F,-0.5129F)).addOrReplaceChild("body3",CubeListBuilder.create().texOffs(20,60).addBox(-7.5F,-2.3125F,-0.125F,15F,14F,13F,new CubeDeformation(0F)).texOffs(107,4).addBox(0F,-7.3125F,4.875F,0F,5F,8F,new CubeDeformation(0F)),PartPose.offset(0F,1.8493F,11.443F)).addOrReplaceChild("body4",CubeListBuilder.create().texOffs(42,0).addBox(-7F,-3.3125F,-0.4375F,14F,12F,8F,new CubeDeformation(0F)).texOffs(113,11).addBox(0F,-9.0625F,0.5625F,0F,7F,7F,new CubeDeformation(0F)),PartPose.offset(0F,3.4819F,11.5169F)).addOrReplaceChild("body5",CubeListBuilder.create().texOffs(46,22).addBox(-6F,-4.0625F,-1.1875F,12F,9F,11F,new CubeDeformation(0F)).texOffs(108,17).addBox(0F,-11.0625F,0.8125F,0F,7F,9F,new CubeDeformation(0F)),PartPose.offset(0F,3.5852F,6.553F)).addOrReplaceChild("body6",CubeListBuilder.create().texOffs(65,44).addBox(-5F,-3.1993F,-1.0463F,10F,7F,12F,new CubeDeformation(0F)).texOffs(106,25).addBox(0F,-9.1993F,1.9537F,0F,7F,9F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0.875F,8.8125F,0.3054F,0F,0F)).addOrReplaceChild("tail",CubeListBuilder.create().texOffs(19,100).addBox(-9.5F,-2.308F,-0.5636F,19F,4F,11F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0.0426F,9.893F,0.6109F,0F,0F));
		return LayerDefinition.create(var0,128,128);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		this.head.xRot = 0F;
		if (var1.isInWater()){
			this.leftFin2.zRot = 0F;
			this.leftFin.xRot = (float)(Math.toRadians(45D));
			this.rightFin2.zRot = 0F;
			this.rightFin.xRot = (float)(Math.toRadians(45D));
			this.body6.xRot = 0F;
			this.tail.xRot = (float)(Math.toRadians(5D));
			this.body5.xRot = ((((Mth.cos(((var2) * (0.4F)) + (3.1415927F))) * (0.4F)) * (var3)) * (0.7F)) / (1F);
			this.body6.xRot = ((((Mth.cos(((var2) * (0.4F)) + (3.1415927F))) * (0.4F)) * (var3)) * (0.7F)) / (1F);
			this.tail.xRot = ((((Mth.cos(((var2) * (0.4F)) + (3.1415927F))) * (0.4F)) * (var3)) * (0.7F)) / (1F);
			this.rightFin.zRot = (float)((Math.toRadians(20D)) + ((double)(((((Mth.cos(((var2) * (0.4F)) + (3.1415927F))) * (0.9F)) * (var3)) * (0.7F)) / (1F))));
			this.rightFin.yRot = (float)((Math.toRadians(-20D)) - ((double)(((((Mth.cos(((var2) * (0.4F)) + (3.1415927F))) * (0.2F)) * (var3)) * (0.7F)) / (1F))));
			this.leftFin.zRot = (float)((Math.toRadians(-20D)) - ((double)(((((Mth.cos(((var2) * (0.4F)) + (3.1415927F))) * (0.9F)) * (var3)) * (0.7F)) / (1F))));
			this.leftFin.yRot = (float)((Math.toRadians(20D)) + ((double)(((((Mth.cos(((var2) * (0.4F)) + (3.1415927F))) * (0.2F)) * (var3)) * (0.7F)) / (1F))));
		}else{
			this.leftFin2.zRot = (float)(Math.toRadians(-67.5D));
			this.leftFin.xRot = (float)(Math.toRadians(0D));
			this.rightFin2.zRot = (float)(Math.toRadians(67.5D));
			this.rightFin.xRot = (float)(Math.toRadians(0D));
			this.body5.xRot = 0F;
			this.body6.xRot = (float)(Math.toRadians(17.5D));
			this.tail.xRot = (float)(Math.toRadians(35D));
			this.rightFin.xRot = ((((Mth.cos(((var2) * (0.4F)) + (3.1415927F))) * (0.9F)) * (var3)) * (0.7F)) / (1F);
			this.rightFin.yRot = 0F;
			this.rightFin.zRot = (float)(Math.toRadians(10D));
			this.leftFin.xRot = ((((Mth.cos(((var2) * (0.4F)) + (3.1415927F))) * (0.9F)) * (var3)) * (0.7F)) / (1F);
			this.leftFin.yRot = 0F;
			this.leftFin.zRot = (float)(Math.toRadians(-10D));
		}
		Animation.animationAngles(var1,this,var2,var3,var4,var5,var6);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.head.render(var1,var2,var3,var4,var5);
		this.rightFin.render(var1,var2,var3,var4,var5);
		this.leftFin.render(var1,var2,var3,var4,var5);
		this.body.render(var1,var2,var3,var4,var5);
	}
}