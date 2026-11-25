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
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BananawaniEntity;

public class BananawaniModel<T extends BananawaniEntity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","bananawani"),"main");
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart tail2;
	private final ModelPart tail3;
	private final ModelPart tail4;
	private final ModelPart tail5;
	private final ModelPart tail6;
	private final ModelPart tail7;
	private final ModelPart tailOrnament;
	private final ModelPart tailOrnament2;
	private final ModelPart tailOrnament3;
	private final ModelPart tailOrnament4;
	private final ModelPart tailOrnament5;
	private final ModelPart head;
	private final ModelPart lowerMouth;
	private final ModelPart upperMouth;
	private final ModelPart ornament;
	private final ModelPart ornament2;
	private final ModelPart ornament3;
	private final ModelPart ornament4;
	private final ModelPart ornament5;
	private final ModelPart leftBackLeg;
	private final ModelPart leftBackLeg2;
	private final ModelPart leftBackPaw;
	private final ModelPart leftBackPaw2;
	private final ModelPart rightBackLeg;
	private final ModelPart rightBackLeg2;
	private final ModelPart rightBackPaw;
	private final ModelPart rightBackPaw2;
	private final ModelPart leftFrontLeg;
	private final ModelPart leftFrontLeg2;
	private final ModelPart leftFrontPaw;
	private final ModelPart leftFrontPaw2;
	private final ModelPart rightFrontLeg;
	private final ModelPart rightFrontLeg2;
	private final ModelPart rightFrontPaw;
	private final ModelPart rightFrontPaw2;
	protected float biteAnimationProgress;

	public BananawaniModel(ModelPart var1) {
		this.body = var1.getChild("body");
		this.tail = var1.getChild("tail");
		this.tail2 = this.tail.getChild("tail2");
		this.tail3 = this.tail2.getChild("tail3");
		this.tail4 = this.tail3.getChild("tail4");
		this.tail5 = this.tail4.getChild("tail5");
		this.tail6 = this.tail5.getChild("tail6");
		this.tail7 = this.tail6.getChild("tail7");
		this.tailOrnament = this.tail7.getChild("tailOrnament");
		this.tailOrnament2 = this.tailOrnament.getChild("tailOrnament2");
		this.tailOrnament3 = this.tailOrnament2.getChild("tailOrnament3");
		this.tailOrnament4 = this.tailOrnament3.getChild("tailOrnament4");
		this.tailOrnament5 = this.tailOrnament.getChild("tailOrnament5");
		this.head = var1.getChild("head");
		this.lowerMouth = this.head.getChild("lowerMouth");
		this.upperMouth = this.head.getChild("upperMouth");
		this.ornament = this.upperMouth.getChild("ornament");
		this.ornament2 = this.ornament.getChild("ornament2");
		this.ornament3 = this.ornament2.getChild("ornament3");
		this.ornament4 = this.ornament3.getChild("ornament4");
		this.ornament5 = this.ornament.getChild("ornament5");
		this.leftBackLeg = var1.getChild("leftBackLeg");
		this.leftBackLeg2 = this.leftBackLeg.getChild("leftBackLeg2");
		this.leftBackPaw = this.leftBackLeg2.getChild("leftBackPaw");
		this.leftBackPaw2 = this.leftBackPaw.getChild("leftBackPaw2");
		this.rightBackLeg = var1.getChild("rightBackLeg");
		this.rightBackLeg2 = this.rightBackLeg.getChild("rightBackLeg2");
		this.rightBackPaw = this.rightBackLeg2.getChild("rightBackPaw");
		this.rightBackPaw2 = this.rightBackPaw.getChild("rightBackPaw2");
		this.leftFrontLeg = var1.getChild("leftFrontLeg");
		this.leftFrontLeg2 = this.leftFrontLeg.getChild("leftFrontLeg2");
		this.leftFrontPaw = this.leftFrontLeg2.getChild("leftFrontPaw");
		this.leftFrontPaw2 = this.leftFrontPaw.getChild("leftFrontPaw2");
		this.rightFrontLeg = var1.getChild("rightFrontLeg");
		this.rightFrontLeg2 = this.rightFrontLeg.getChild("rightFrontLeg2");
		this.rightFrontPaw = this.rightFrontLeg2.getChild("rightFrontPaw");
		this.rightFrontPaw2 = this.rightFrontPaw.getChild("rightFrontPaw2");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,1).addBox(-8F,-7F,-12F,16F,9F,24F,new CubeDeformation(0F)).texOffs(0,35).addBox(-7.5F,2F,-12F,15F,1F,24F,new CubeDeformation(0F)).texOffs(52,63).addBox(-7F,-9F,-12F,14F,2F,24F,new CubeDeformation(0F)).texOffs(0,62).addBox(-6F,-10F,-11F,12F,1F,22F,new CubeDeformation(0F)).texOffs(51,91).addBox(-7F,-7F,-16F,14F,9F,4F,new CubeDeformation(0F)).texOffs(59,18).addBox(-6F,-8.25F,-15.5F,12F,2F,4F,new CubeDeformation(0F)),PartPose.offset(0F,18F,3F));
		PartDefinition var3 = var1.addOrReplaceChild("tail",CubeListBuilder.create().texOffs(60,37).addBox(-7F,-4.5F,0F,14F,9F,8F,new CubeDeformation(0F)).texOffs(60,0).addBox(-6F,-5.5F,0.5F,12F,1F,5F,new CubeDeformation(0F)),PartPose.offset(0F,15.5F,14.5F)).addOrReplaceChild("tail2",CubeListBuilder.create().texOffs(92,0).addBox(-6F,-5F,0.5F,12F,8F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,1.25F,6.75F,0.2182F,0F,0F)).addOrReplaceChild("tail3",CubeListBuilder.create().texOffs(100,15).addBox(-5F,-3.25F,0.5F,10F,7F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1F,5.5F,0.0873F,0F,0F)).addOrReplaceChild("tail4",CubeListBuilder.create().texOffs(106,27).addBox(-4F,-2.5F,0F,8F,6F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,3.75F,0.1745F,0F,0F)).addOrReplaceChild("tail5",CubeListBuilder.create().texOffs(110,37).addBox(-3F,-2.5F,0F,6F,5F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0.75F,2.75F,0.0873F,0F,0F)).addOrReplaceChild("tail6",CubeListBuilder.create().texOffs(116,46).addBox(-2F,-2F,0F,4F,4F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0.25F,2.75F,0.1745F,0F,0F)).addOrReplaceChild("tail7",CubeListBuilder.create().texOffs(120,53).addBox(-1F,-1.5F,0F,2F,3F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0.25F,1.5F,0.2182F,0F,0F)).addOrReplaceChild("tailOrnament",CubeListBuilder.create(),PartPose.offsetAndRotation(-0.4837F,-0.7728F,1F,-1.0036F,0F,0F));
		PartDefinition var4 = var3.addOrReplaceChild("tailOrnament_r1",CubeListBuilder.create().texOffs(106,58).addBox(-3.0477F,-2.3929F,-0.5F,4F,3F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.0814F,-0.0843F,0F,0F,0F,0.3491F));
		PartDefinition var5 = var3.addOrReplaceChild("tailOrnament2",CubeListBuilder.create().texOffs(106,58).addBox(-3F,-2.5F,-0.5F,4F,3F,3F,new CubeDeformation(-0.001F)),PartPose.offsetAndRotation(3.4837F,-0.7772F,0F,0F,0F,-0.3491F)).addOrReplaceChild("tailOrnament3",CubeListBuilder.create().texOffs(106,58).addBox(-2.5F,-2.5F,-0.5F,3F,3F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.95F,-0.35F,0F,0F,0F,0.7854F)).addOrReplaceChild("tailOrnament4",CubeListBuilder.create().texOffs(106,54).addBox(-2.5F,-2.5F,-1.5F,3F,3F,7F,new CubeDeformation(0F)),PartPose.offset(-0.05F,-3.05F,-1F));
		PartDefinition var6 = var3.addOrReplaceChild("tailOrnament5",CubeListBuilder.create().texOffs(106,58).addBox(-2.5F,-2.5F,-0.5F,3F,3F,3F,new CubeDeformation(-0.001F)),PartPose.offsetAndRotation(-1.2163F,-2.3772F,0F,0F,0F,-0.5672F));
		PartDefinition var7 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(59,7).addBox(-5F,-3.9667F,-7.75F,10F,3F,7F,new CubeDeformation(0F)).texOffs(49,106).addBox(-6F,-1.7667F,-8.75F,12F,6F,8F,new CubeDeformation(0F)),PartPose.offset(0F,15.2667F,-12.25F));
		PartDefinition var8 = var7.addOrReplaceChild("lowerMouth",CubeListBuilder.create().texOffs(0,103).addBox(-5.5F,-0.25F,-12.25F,11F,3F,12F,new CubeDeformation(0F)),PartPose.offset(0F,0.9833F,-8.5F));
		PartDefinition var9 = var7.addOrReplaceChild("upperMouth",CubeListBuilder.create().texOffs(0,87).addBox(-5.5F,-1.7667F,-12.25F,11F,2F,12F,new CubeDeformation(0F)),PartPose.offset(0F,0.5F,-8.5F)).addOrReplaceChild("ornament",CubeListBuilder.create(),PartPose.offset(-0.4837F,-2.2894F,-10F));
		PartDefinition var10 = var9.addOrReplaceChild("ornament_r1",CubeListBuilder.create().texOffs(1,93).addBox(-1.0477F,-0.3929F,-0.5F,2F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.0814F,-0.0843F,0F,0F,0F,0.3491F));
		PartDefinition var11 = var9.addOrReplaceChild("ornament2",CubeListBuilder.create().texOffs(1,93).addBox(-1F,-0.5F,-0.5F,2F,1F,1F,new CubeDeformation(-0.001F)),PartPose.offsetAndRotation(1.4837F,-0.0272F,0F,0F,0F,-0.3491F)).addOrReplaceChild("ornament3",CubeListBuilder.create().texOffs(1,93).addBox(-0.5F,-0.5F,-0.5F,1F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.95F,-0.35F,0F,0F,0F,0.7854F)).addOrReplaceChild("ornament4",CubeListBuilder.create().texOffs(1,91).addBox(-0.5F,-0.5F,-1.5F,1F,1F,3F,new CubeDeformation(0F)),PartPose.offset(-0.05F,-0.8F,0F));
		PartDefinition var12 = var9.addOrReplaceChild("ornament5",CubeListBuilder.create().texOffs(1,93).addBox(-0.5F,-0.5F,-0.5F,1F,1F,1F,new CubeDeformation(-0.001F)),PartPose.offsetAndRotation(-0.9663F,-0.6272F,0F,0F,0F,-0.5672F));
		PartDefinition var13 = var1.addOrReplaceChild("leftBackLeg",CubeListBuilder.create(),PartPose.offsetAndRotation(7.25F,18F,9.75F,0F,-0.7418F,0F));
		PartDefinition var14 = var13.addOrReplaceChild("leftBackLeg_r1",CubeListBuilder.create().texOffs(105,95).addBox(-4.723F,-2F,-0.5F,7F,4F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(1.223F,1.361F,-2.2513F,0.1309F,0.6981F,0.48F));
		PartDefinition var15 = var13.addOrReplaceChild("leftBackLeg2",CubeListBuilder.create().texOffs(105,105).addBox(-1.9113F,-2.5F,-1.6398F,4F,5F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(3.3395F,2.9488F,-2.9942F,-0.0886F,0.1739F,-0.0154F)).addOrReplaceChild("leftBackPaw",CubeListBuilder.create().texOffs(105,116).addBox(-1.3182F,-1F,-1.8327F,4F,2F,4F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(-0.5931F,3.2271F,0.2929F,0.0873F,0F,0F)).addOrReplaceChild("leftBackPaw2",CubeListBuilder.create().texOffs(108,125).addBox(-1.3182F,0F,-2.6827F,4F,1F,1F,new CubeDeformation(0.02F)),PartPose.offset(0F,0F,0F));
		PartDefinition var16 = var1.addOrReplaceChild("rightBackLeg",CubeListBuilder.create(),PartPose.offsetAndRotation(-7.25F,18F,9.75F,0F,0.7418F,0F));
		PartDefinition var17 = var16.addOrReplaceChild("rightBackLeg_r1",CubeListBuilder.create().texOffs(105,95).mirror().addBox(-2.277F,-2F,-0.5F,7F,4F,4F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-1.223F,1.361F,-2.2513F,0.1309F,-0.6981F,-0.48F));
		PartDefinition var18 = var16.addOrReplaceChild("rightBackLeg2",CubeListBuilder.create().texOffs(105,105).mirror().addBox(-2.0887F,-2.5F,-1.6398F,4F,5F,4F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-3.3395F,2.9488F,-2.9942F,-0.0886F,-0.1739F,0.0154F)).addOrReplaceChild("rightBackPaw",CubeListBuilder.create().texOffs(105,116).mirror().addBox(-2.6818F,-1F,-1.8327F,4F,2F,4F,new CubeDeformation(0.01F)).mirror(false),PartPose.offsetAndRotation(0.5931F,3.2271F,0.2929F,0.0873F,0F,0F)).addOrReplaceChild("rightBackPaw2",CubeListBuilder.create().texOffs(108,125).mirror().addBox(-2.6818F,0F,-2.6827F,4F,1F,1F,new CubeDeformation(0.02F)).mirror(false),PartPose.offset(0F,0F,0F));
		PartDefinition var19 = var1.addOrReplaceChild("leftFrontLeg",CubeListBuilder.create(),PartPose.offsetAndRotation(7.25F,18F,-4.25F,0F,-0.5236F,0F));
		PartDefinition var20 = var19.addOrReplaceChild("leftFrontLeg_r1",CubeListBuilder.create().texOffs(105,95).addBox(-4.723F,-2F,-0.5F,7F,4F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(1.223F,1.361F,-2.2513F,0.1309F,0.6981F,0.48F));
		PartDefinition var21 = var19.addOrReplaceChild("leftFrontLeg2",CubeListBuilder.create().texOffs(105,105).addBox(-1.9113F,-2.5F,-1.6398F,4F,5F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(3.3395F,2.9488F,-2.9942F,-0.1007F,0.5214F,-0.0503F)).addOrReplaceChild("leftFrontPaw",CubeListBuilder.create().texOffs(105,116).addBox(-1.3182F,-1F,-1.8327F,4F,2F,4F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(-0.5931F,3.2271F,0.2929F,0.0873F,0F,0F)).addOrReplaceChild("leftFrontPaw2",CubeListBuilder.create().texOffs(108,125).addBox(-1.3182F,0F,-2.6827F,4F,1F,1F,new CubeDeformation(0.02F)),PartPose.offset(0F,0F,0F));
		PartDefinition var22 = var1.addOrReplaceChild("rightFrontLeg",CubeListBuilder.create(),PartPose.offsetAndRotation(-7.25F,18F,-4.25F,0F,0.5236F,0F));
		PartDefinition var23 = var22.addOrReplaceChild("rightFrontLeg_r1",CubeListBuilder.create().texOffs(105,95).mirror().addBox(-2.277F,-2F,-0.5F,7F,4F,4F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-1.223F,1.361F,-2.2513F,0.1309F,-0.6981F,-0.48F));
		PartDefinition var24 = var22.addOrReplaceChild("rightFrontLeg2",CubeListBuilder.create().texOffs(105,105).mirror().addBox(-2.0887F,-2.5F,-1.6398F,4F,5F,4F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-3.3395F,2.9488F,-2.9942F,-0.1007F,-0.5214F,0.0503F)).addOrReplaceChild("rightFrontPaw",CubeListBuilder.create().texOffs(105,116).mirror().addBox(-2.6818F,-1F,-1.8327F,4F,2F,4F,new CubeDeformation(0.01F)).mirror(false),PartPose.offsetAndRotation(0.5931F,3.2271F,0.2929F,0.0873F,0F,0F)).addOrReplaceChild("rightFrontPaw2",CubeListBuilder.create().texOffs(108,125).mirror().addBox(-2.6818F,0F,-2.6827F,4F,1F,1F,new CubeDeformation(0.02F)).mirror(false),PartPose.offset(0F,0F,0F));
		return LayerDefinition.create(var0,128,128);
	}
	public void prepareMobModel(T var1, float var2, float var3, float var4) {
		if (var1 instanceof BananawaniEntity){
			this.biteAnimationProgress = var1.getBiteAnimationProgress(var4);
		}
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		float var7 = 1F;
		float var8 = 0.25F;
		float var9 = 0.4F;
		if (!(var1.isControlledByLocalInstance())){
			var8 = 0.8F;
		}
		this.rightFrontLeg.xRot = (((Mth.cos((var2) * (var8))) * (var9)) * (var3)) / (var7);
		this.leftFrontLeg.xRot = (((Mth.cos(((var2) * (var8)) + (3.1415927F))) * (var9)) * (var3)) / (var7);
		this.rightBackLeg.xRot = (((Mth.sin((var2) * (var8))) * (var9)) * (var3)) / (var7);
		this.leftBackLeg.xRot = (((Mth.sin(((var2) * (var8)) + (3.1415927F))) * (var9)) * (var3)) / (var7);
		if ((this.biteAnimationProgress) < (1F)){
			float var10 = (Mth.sin(((this.biteAnimationProgress) * (0.5F)) * (3.1415927F))) * (0.7F);
			this.upperMouth.xRot = ((float)(Math.toRadians(-40D))) + (var10);
			this.lowerMouth.xRot = ((float)(Math.toRadians(40D))) - (var10);
		}else{
			if ((this.biteAnimationProgress) >= (1F)){
				var1.swinging = false;
				this.upperMouth.xRot = (float)(Math.toRadians(0D));
				this.lowerMouth.xRot = (float)(Math.toRadians(0D));
			}
		}
		this.tail.yRot = (Mth.sin((var4) * (0.05F))) * (0.1F);
		this.tail3.yRot = (Mth.sin((var4) * (0.05F))) * (0.1F);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.body.render(var1,var2,var3,var4,var5);
		this.tail.render(var1,var2,var3,var4,var5);
		this.head.render(var1,var2,var3,var4,var5);
		this.leftBackLeg.render(var1,var2,var3,var4,var5);
		this.rightBackLeg.render(var1,var2,var3,var4,var5);
		this.leftFrontLeg.render(var1,var2,var3,var4,var5);
		this.rightFrontLeg.render(var1,var2,var3,var4,var5);
	}
}