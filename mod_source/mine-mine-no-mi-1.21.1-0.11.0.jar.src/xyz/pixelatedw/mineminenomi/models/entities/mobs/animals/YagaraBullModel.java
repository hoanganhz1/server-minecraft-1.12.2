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
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.YagaraBullEntity;

public class YagaraBullModel<T extends YagaraBullEntity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","yagara_bull"),"main");
	private final ModelPart body;
	private final ModelPart frontLeftFin;
	private final ModelPart frontRightFin;
	private final ModelPart back;
	private final ModelPart backLeftFin;
	private final ModelPart backRightFin;
	private final ModelPart tail;
	private final ModelPart tail2;
	private final ModelPart tail3;
	private final ModelPart tail4;
	private final ModelPart tail5;
	private final ModelPart tail6;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart saddle;
	private final ModelPart saddleRight1;
	private final ModelPart saddleLeft1;
	private final ModelPart saddleRight2;
	private final ModelPart saddleLeft2;
	private final ModelPart saddleFront;
	private final ModelPart saddleBack;
	private final ModelPart belt1;
	private final ModelPart belt2;

	public YagaraBullModel(ModelPart var1) {
		this.body = var1.getChild("body");
		this.frontLeftFin = this.body.getChild("frontLeftFin");
		this.frontRightFin = this.body.getChild("frontRightFin");
		this.back = this.body.getChild("back");
		this.backLeftFin = this.back.getChild("backLeftFin");
		this.backRightFin = this.back.getChild("backRightFin");
		this.tail = var1.getChild("tail");
		this.tail2 = this.tail.getChild("tail2");
		this.tail3 = this.tail.getChild("tail3");
		this.tail4 = this.tail.getChild("tail4");
		this.tail5 = this.tail.getChild("tail5");
		this.tail6 = this.tail5.getChild("tail6");
		this.neck = var1.getChild("neck");
		this.head = this.neck.getChild("head");
		this.saddle = var1.getChild("saddle");
		this.saddleRight1 = this.saddle.getChild("saddleRight1");
		this.saddleLeft1 = this.saddle.getChild("saddleLeft1");
		this.saddleRight2 = this.saddle.getChild("saddleRight2");
		this.saddleLeft2 = this.saddle.getChild("saddleLeft2");
		this.saddleFront = this.saddle.getChild("saddleFront");
		this.saddleBack = this.saddle.getChild("saddleBack");
		this.belt1 = this.saddle.getChild("belt1");
		this.belt2 = this.saddle.getChild("belt2");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,0).addBox(-6F,-5.5F,-5.5F,12F,11F,11F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,18.3F,-4F,-0.0698F,0F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("frontLeftFin",CubeListBuilder.create().texOffs(99,41).addBox(0F,-0.5F,-6F,8F,1F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(3.9F,2F,-2F,-0.1922F,-0.6485F,0.4436F));
		PartDefinition var4 = var2.addOrReplaceChild("frontRightFin",CubeListBuilder.create().texOffs(99,41).addBox(-8F,-0.5F,-6F,8F,1F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-3.9F,2F,-2F,-0.1922F,0.6485F,-0.4436F));
		PartDefinition var5 = var2.addOrReplaceChild("back",CubeListBuilder.create().texOffs(0,23).addBox(-6F,-5F,-4.5F,12F,10F,9F,new CubeDeformation(-0.001F)),PartPose.offsetAndRotation(0F,-0.65F,9.3F,0.1222F,0F,0F));
		PartDefinition var6 = var5.addOrReplaceChild("backLeftFin",CubeListBuilder.create().texOffs(99,49).addBox(0F,-0.5F,-5F,6F,1F,5F,new CubeDeformation(-0.001F)),PartPose.offsetAndRotation(4.4F,2.9F,2.7F,-0.2423F,-0.7268F,0.4754F));
		PartDefinition var7 = var5.addOrReplaceChild("backRightFin",CubeListBuilder.create().texOffs(99,49).addBox(-6F,-0.5F,-5F,6F,1F,5F,new CubeDeformation(-0.001F)),PartPose.offsetAndRotation(-4.4F,2.9F,2.7F,-0.2423F,0.7268F,-0.4754F));
		PartDefinition var8 = var1.addOrReplaceChild("tail",CubeListBuilder.create().texOffs(47,0).addBox(-5F,-4.5F,0F,10F,9F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,18F,9.5F,0.0873F,0F,0F));
		PartDefinition var9 = var8.addOrReplaceChild("tail2",CubeListBuilder.create().texOffs(47,15).addBox(-4F,-3.5349F,-1.5006F,8F,7F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0.0873F,5.4985F,-0.0349F,0F,0F));
		PartDefinition var10 = var8.addOrReplaceChild("tail3",CubeListBuilder.create().texOffs(47,28).addBox(-2.5F,-2.6045F,-0.5055F,5F,5F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0.3138F,8.989F,-0.1047F,0F,0F));
		PartDefinition var11 = var8.addOrReplaceChild("tail4",CubeListBuilder.create().texOffs(47,37).addBox(-1.5F,-1.6392F,-0.5097F,3F,3F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0.6271F,11.469F,-0.1396F,0F,0F));
		PartDefinition var12 = var8.addOrReplaceChild("tail5",CubeListBuilder.create().texOffs(47,44).addBox(-0.5F,-0.7249F,-0.5256F,1F,1F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,1.1733F,14.4159F,-0.2269F,0F,0F)).addOrReplaceChild("tail6",CubeListBuilder.create().texOffs(47,42).addBox(0F,-1.247F,-1.281F,0F,7F,7F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-0.4505F,-0.1678F,0.4712F,0F,0F));
		PartDefinition var13 = var1.addOrReplaceChild("neck",CubeListBuilder.create().texOffs(78,14).addBox(-5.5F,-4.5F,0F,11F,9F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,16F,-13.8F,-0.3491F,0F,0F));
		PartDefinition var14 = var13.addOrReplaceChild("neck2_r1",CubeListBuilder.create().texOffs(78,0).addBox(-4F,-14F,-17.3F,8F,6F,7F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,16.5467F,2.6819F,-0.6981F,0F,0F));
		PartDefinition var15 = var13.addOrReplaceChild("neck3_r1",CubeListBuilder.create().texOffs(78,30).addBox(-3.5F,-31.1F,-6.5F,7F,5F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,10.2663F,-28.8447F,-1.1868F,0F,0F));
		PartDefinition var16 = var13.addOrReplaceChild("head",CubeListBuilder.create(),PartPose.offset(0F,0F,0F));
		PartDefinition var17 = var16.addOrReplaceChild("lefteye_r1",CubeListBuilder.create().texOffs(40,57).addBox(1.5F,-30F,-3.5F,2F,2F,2F,new CubeDeformation(0F)).texOffs(40,57).addBox(-3.5F,-30F,-3.5F,2F,2F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,15.0144F,3.3925F,0.3491F,0F,0F));
		PartDefinition var18 = var16.addOrReplaceChild("mane_r1",CubeListBuilder.create().texOffs(67,29).addBox(0F,-34.8F,-3F,0F,10F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,17.027F,4.2006F,0.2269F,0F,0F));
		PartDefinition var19 = var16.addOrReplaceChild("rightgill_r1",CubeListBuilder.create().texOffs(0,61).addBox(-5.2F,-25F,-2.5F,3F,0F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(18.1894F,5.9943F,-3.424F,0.1417F,0.64F,-0.7848F));
		PartDefinition var20 = var16.addOrReplaceChild("leftgill_r1",CubeListBuilder.create().texOffs(0,61).addBox(2.2F,-25.5F,-2F,3F,0F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-18.4098F,5.9091F,-3.5225F,0.1417F,-0.64F,0.7848F));
		PartDefinition var21 = var16.addOrReplaceChild("head3_r1",CubeListBuilder.create().texOffs(19,43).addBox(-2.5F,-29F,-6.5F,5F,4F,4F,new CubeDeformation(0F)).texOffs(19,52).addBox(-3F,-29F,-10.5F,6F,5F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,11.7941F,8.8052F,0.576F,0F,0F));
		PartDefinition var22 = var16.addOrReplaceChild("head1_r1",CubeListBuilder.create().texOffs(0,43).addBox(-3F,-30.3F,-0.5F,6F,5F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,14.2732F,4.8985F,0.4014F,0F,0F));
		PartDefinition var23 = var16.addOrReplaceChild("head2_r1",CubeListBuilder.create().texOffs(0,52).addBox(-3F,-29.5F,-3F,6F,5F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,13.9253F,5.6797F,0.4363F,0F,0F));
		PartDefinition var24 = var16.addOrReplaceChild("neck4_r1",CubeListBuilder.create().texOffs(78,41).addBox(-3F,-31.9F,-3.5F,6F,5F,4F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0F,-1.1258F,-33.5366F,-1.3963F,0F,0F));
		PartDefinition var25 = var1.addOrReplaceChild("saddle",CubeListBuilder.create().texOffs(56,64).addBox(-5.5F,-4.0615F,-10.0695F,11F,1F,21F,new CubeDeformation(0F)),PartPose.offset(0F,16.2939F,1.0429F));
		PartDefinition var26 = var25.addOrReplaceChild("saddleRight1",CubeListBuilder.create().texOffs(100,78).addBox(-5.5F,-5F,-0.5F,11F,5F,1F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(-5.4446F,-3.8143F,5.389F,-0.0873F,-1.4835F,0F));
		PartDefinition var27 = var25.addOrReplaceChild("saddleLeft1",CubeListBuilder.create().texOffs(100,78).addBox(-5.5F,-5F,-0.5F,11F,5F,1F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(5.4446F,-3.8143F,5.389F,-0.0873F,1.4835F,0F));
		PartDefinition var28 = var25.addOrReplaceChild("saddleRight2",CubeListBuilder.create().texOffs(100,78).addBox(-5.5F,-5F,-0.4F,11F,5F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-5.5446F,-3.8143F,-5.3248F,0.0873F,1.4835F,0F));
		PartDefinition var29 = var25.addOrReplaceChild("saddleLeft2",CubeListBuilder.create().texOffs(100,78).addBox(-5.5F,-5F,-0.4F,11F,5F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(5.5446F,-3.8143F,-5.3248F,0.0873F,-1.4835F,0F));
		PartDefinition var30 = var25.addOrReplaceChild("saddleFront",CubeListBuilder.create().texOffs(100,78).addBox(-5.5F,-5F,-1F,11F,5F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.0554F,-3.8143F,-9.6248F,0.0873F,0F,0F));
		PartDefinition var31 = var25.addOrReplaceChild("saddleBack",CubeListBuilder.create().texOffs(100,78).addBox(-5.5F,-5F,-0.5F,11F,5F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.0554F,-3.8143F,10.389F,-0.0873F,0F,0F));
		PartDefinition var32 = var25.addOrReplaceChild("belt1",CubeListBuilder.create().texOffs(100,64).addBox(-6.5F,-3.4535F,5.3522F,13F,11F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0.0436F,0F,0F));
		PartDefinition var33 = var25.addOrReplaceChild("belt2",CubeListBuilder.create().texOffs(100,64).addBox(-6.5F,-6F,-0.5F,13F,12F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,2.0056F,-6.1828F,-0.0873F,0F,0F));
		return LayerDefinition.create(var0,128,128);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		this.tail.yRot = (0.1F) * ((-0.2F) + (Mth.cos((var4) * (0.15F))));
		this.tail2.yRot = (0.2F) * ((-0.2F) + (Mth.cos((var4) * (0.15F))));
		this.tail3.yRot = (0.1F) * ((-0.2F) + (Mth.cos((var4) * (0.15F))));
		this.tail4.yRot = (0.2F) * ((-0.2F) + (Mth.cos((var4) * (0.15F))));
		this.tail5.yRot = (0.1F) * ((-0.2F) + (Mth.cos((var4) * (0.15F))));
		this.frontLeftFin.yRot = (0.2F) * (-(Mth.cos((var4) * (0.15F))));
		this.frontLeftFin.xRot = (0.2F) * (-(Mth.cos((var4) * (0.15F))));
		this.backLeftFin.yRot = (0.2F) * (Mth.cos((var4) * (0.15F)));
		this.backLeftFin.xRot = (0.2F) * (Mth.cos((var4) * (0.15F)));
		this.frontRightFin.yRot = (0.2F) * (-(Mth.cos((var4) * (0.15F))));
		this.frontRightFin.xRot = (0.2F) * (-(Mth.cos((var4) * (0.15F))));
		this.backRightFin.yRot = (0.2F) * (Mth.cos((var4) * (0.15F)));
		this.backRightFin.xRot = (0.2F) * (Mth.cos((var4) * (0.15F)));
		if ((((var4) % (300F)) > (0F)) && (((var4) % (300F)) < (50F))){
			this.head.yRot = (0.4F) * (Mth.cos((var4) * (0.25F)));
		}else{
			this.head.yRot = (float)(Math.toDegrees(0D));
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.body.render(var1,var2,var3,var4,var5);
		this.tail.render(var1,var2,var3,var4,var5);
		this.neck.render(var1,var2,var3,var4,var5);
		this.saddle.render(var1,var2,var3,var4,var5);
		this.saddle.visible = false;
	}
	public void renderSaddle() {
		this.saddle.visible = true;
	}
}