package xyz.pixelatedw.mineminenomi.models.entities.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
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

public class PheasantModel<T extends Entity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","pheasant"),"main");
	private final ModelPart body1;
	private final ModelPart body2;
	private final ModelPart body3;
	private final ModelPart body4;
	private final ModelPart body5;
	private final ModelPart head;
	private final ModelPart tuft1;
	private final ModelPart tuft2;
	private final ModelPart tuft3;
	private final ModelPart beak1;
	private final ModelPart beak2;
	private final ModelPart beak3;
	private final ModelPart beak4;
	private final ModelPart tail1;
	private final ModelPart tail2;
	private final ModelPart tail3;
	private final ModelPart rightleg1;
	private final ModelPart rightleg2;
	private final ModelPart rightfoot1;
	private final ModelPart leftleg1;
	private final ModelPart leftleg2;
	private final ModelPart leftfoot1;
	private final ModelPart rightWing1;
	private final ModelPart rightWing3;
	private final ModelPart rightWing5;
	private final ModelPart rightWing2;
	private final ModelPart rightWing4;
	private final ModelPart leftWing1;
	private final ModelPart leftWing3;
	private final ModelPart leftWing5;
	private final ModelPart leftWing2;
	private final ModelPart leftWing4;
	private static final float[] WING_ANIMATION_FRAMES;

	public PheasantModel(ModelPart var1) {
		this.body1 = var1.getChild("body1");
		this.body2 = this.body1.getChild("body2");
		this.body3 = this.body1.getChild("body3");
		this.body4 = this.body1.getChild("body4");
		this.body5 = this.body1.getChild("body5");
		this.head = var1.getChild("head");
		this.tuft1 = var1.getChild("tuft1");
		this.tuft2 = var1.getChild("tuft2");
		this.tuft3 = var1.getChild("tuft3");
		this.beak1 = var1.getChild("beak1");
		this.beak2 = var1.getChild("beak2");
		this.beak3 = var1.getChild("beak3");
		this.beak4 = var1.getChild("beak4");
		this.tail1 = var1.getChild("tail1");
		this.tail2 = var1.getChild("tail2");
		this.tail3 = var1.getChild("tail3");
		this.rightleg1 = var1.getChild("rightleg1");
		this.rightleg2 = var1.getChild("rightleg2");
		this.rightfoot1 = var1.getChild("rightfoot1");
		this.leftleg1 = var1.getChild("leftleg1");
		this.leftleg2 = var1.getChild("leftleg2");
		this.leftfoot1 = var1.getChild("leftfoot1");
		this.rightWing1 = var1.getChild("rightWing1");
		this.rightWing3 = this.rightWing1.getChild("rightWing3");
		this.rightWing5 = this.rightWing3.getChild("rightWing5");
		this.rightWing2 = this.rightWing1.getChild("rightWing2");
		this.rightWing4 = this.rightWing2.getChild("rightWing4");
		this.leftWing1 = var1.getChild("leftWing1");
		this.leftWing3 = this.leftWing1.getChild("leftWing3");
		this.leftWing5 = this.leftWing3.getChild("leftWing5");
		this.leftWing2 = this.leftWing1.getChild("leftWing2");
		this.leftWing4 = this.leftWing2.getChild("leftWing4");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("body1",CubeListBuilder.create().texOffs(0,0).addBox(-3.5F,-6.5F,-5F,7F,7F,10F,new CubeDeformation(0F)),PartPose.offset(0F,-1F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("body2",CubeListBuilder.create().texOffs(0,18).addBox(-3F,-3F,5F,6F,6F,2F,new CubeDeformation(0F)),PartPose.offset(0F,-3F,-0.5F));
		PartDefinition var4 = var2.addOrReplaceChild("body3",CubeListBuilder.create().texOffs(0,27).addBox(-3F,-3F,-1F,6F,6F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-3.5F,-5.7F,-0.0524F,0F,0F));
		PartDefinition var5 = var2.addOrReplaceChild("body4",CubeListBuilder.create().texOffs(0,36).addBox(-2.5F,-2.5F,-1F,5F,5F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-4F,-7.5F,-0.0524F,0F,0F));
		PartDefinition var6 = var2.addOrReplaceChild("body5",CubeListBuilder.create().texOffs(0,44).addBox(-2F,-2.5F,-1.5F,4F,4F,2F,new CubeDeformation(0F)),PartPose.offset(0F,-3.5F,-8.7F));
		PartDefinition var7 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,51).addBox(-1.5F,-6.4874F,-12.6075F,3F,3F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1F,0F,0.0873F,0F,0F));
		PartDefinition var8 = var1.addOrReplaceChild("tuft1",CubeListBuilder.create().texOffs(13,51).addBox(-0.5F,-12.3649F,-6.362F,1F,1F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1F,0F,0.6981F,0F,0F));
		PartDefinition var9 = var1.addOrReplaceChild("tuft2",CubeListBuilder.create().texOffs(22,51).addBox(0.7103F,-12.1338F,-6.5559F,1F,1F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1F,0F,0.6981F,0F,-0.3491F));
		PartDefinition var10 = var1.addOrReplaceChild("tuft3",CubeListBuilder.create().texOffs(29,51).addBox(-1.7103F,-12.1338F,-6.5559F,1F,1F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1F,0F,0.6981F,0F,0.3491F));
		PartDefinition var11 = var1.addOrReplaceChild("beak1",CubeListBuilder.create().texOffs(0,58).addBox(-4.2311F,-7.411F,-12.648F,1F,1F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1F,0F,0.2665F,-0.2618F,-0.0181F));
		PartDefinition var12 = var1.addOrReplaceChild("beak2",CubeListBuilder.create().texOffs(0,61).addBox(3.2311F,-7.411F,-12.648F,1F,1F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1F,0F,0.2665F,0.2618F,0.0181F));
		PartDefinition var13 = var1.addOrReplaceChild("beak3",CubeListBuilder.create().texOffs(7,58).addBox(-0.5F,-7.568F,-13.2824F,1F,1F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1F,0F,0.2618F,0F,0F));
		PartDefinition var14 = var1.addOrReplaceChild("beak4",CubeListBuilder.create().texOffs(7,61).addBox(-0.5F,-1.2612F,-14.5378F,1F,1F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1F,0F,-0.1745F,0F,0F));
		PartDefinition var15 = var1.addOrReplaceChild("tail1",CubeListBuilder.create().texOffs(17,18).addBox(-1F,-2.8108F,6.8308F,2F,1F,8F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1F,0F,0.2618F,0F,0F));
		PartDefinition var16 = var1.addOrReplaceChild("tail2",CubeListBuilder.create().texOffs(29,12).addBox(-1.6471F,-2.8637F,6.6334F,3F,1F,7F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1F,0F,0.2618F,-0.2618F,0F));
		PartDefinition var17 = var1.addOrReplaceChild("tail3",CubeListBuilder.create().texOffs(29,12).mirror().addBox(-1.3529F,-2.8637F,6.6334F,3F,1F,7F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0F,-1F,0F,0.2618F,0.2618F,0F));
		PartDefinition var18 = var1.addOrReplaceChild("rightleg1",CubeListBuilder.create().texOffs(25,0).addBox(-2.6224F,0.5776F,1.1989F,3F,4F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1F,0F,0.7854F,-0.1745F,0F));
		PartDefinition var19 = var1.addOrReplaceChild("rightleg2",CubeListBuilder.create().texOffs(38,0).addBox(-2.1224F,4.5776F,1.6989F,2F,2F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1F,0F,0.7854F,-0.1745F,0F));
		PartDefinition var20 = var1.addOrReplaceChild("rightfoot1",CubeListBuilder.create().texOffs(38,5).addBox(-2.0956F,5.7364F,-2.1297F,2F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1F,0F,1.309F,-0.1745F,0F));
		PartDefinition var21 = var1.addOrReplaceChild("leftleg1",CubeListBuilder.create().texOffs(25,0).addBox(-0.3776F,0.5776F,1.1989F,3F,4F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1F,0F,0.7854F,0.1745F,0F));
		PartDefinition var22 = var1.addOrReplaceChild("leftleg2",CubeListBuilder.create().texOffs(38,0).addBox(0.1224F,4.5776F,1.6989F,2F,2F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1F,0F,0.7854F,0.1745F,0F));
		PartDefinition var23 = var1.addOrReplaceChild("leftfoot1",CubeListBuilder.create().texOffs(38,5).addBox(0.0956F,5.7364F,-2.1297F,2F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1F,0F,1.309F,0.1745F,0F));
		PartDefinition var24 = var1.addOrReplaceChild("rightWing1",CubeListBuilder.create().texOffs(47,0).addBox(-8.6204F,-5.8085F,-3.6132F,4F,2F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1F,0F,-0.0373F,-0.2592F,0.1445F));
		PartDefinition var25 = var24.addOrReplaceChild("rightWing3",CubeListBuilder.create().texOffs(17,33).addBox(-6.5F,0F,0F,6F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-16.6203F,-6.0085F,-4.1132F,0F,0.2443F,0F)).addOrReplaceChild("rightWing5",CubeListBuilder.create().texOffs(10,44).addBox(-6.2F,0.5F,1F,6F,0F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,-0.0175F,0F,0F));
		PartDefinition var26 = var24.addOrReplaceChild("rightWing2",CubeListBuilder.create().texOffs(17,29).addBox(-9.5F,0F,0.5F,9F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-7.7203F,-5.4085F,-3.6132F,-0.0358F,-0.1004F,0.0607F)).addOrReplaceChild("rightWing4",CubeListBuilder.create().texOffs(10,37).addBox(-8.8F,0.5F,1F,12F,0F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0.0175F,0.0456F,0F));
		PartDefinition var27 = var1.addOrReplaceChild("leftWing1",CubeListBuilder.create().texOffs(47,0).addBox(4.6204F,-5.8082F,-3.6137F,4F,2F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1F,0F,-0.0374F,0.2592F,-0.1445F));
		PartDefinition var28 = var27.addOrReplaceChild("leftWing3",CubeListBuilder.create().texOffs(17,33).addBox(0.5F,0F,0F,6F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(16.6203F,-5.9082F,-4.1137F,0F,-0.2443F,0F)).addOrReplaceChild("leftWing5",CubeListBuilder.create().texOffs(10,44).addBox(0.5F,0.5F,1F,6F,0F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.15F,-0.1F,0F,-0.0175F,0F,0F));
		PartDefinition var29 = var27.addOrReplaceChild("leftWing2",CubeListBuilder.create().texOffs(17,29).addBox(0.5F,0F,0.5F,9F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(7.6204F,-5.4082F,-3.6137F,-0.0358F,0.1004F,-0.0607F)).addOrReplaceChild("leftWing4",CubeListBuilder.create().texOffs(10,37).addBox(-2.8F,0.5F,1F,12F,0F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.1F,-0.1F,0F,0.0175F,-0.0456F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(Entity var1, float var2, float var3, float var4, float var5, float var6) {
		int var7 = (int)((((double)(var1.tickCount)) * (1.85D)) % ((double)(WING_ANIMATION_FRAMES.length)));
		if (!(Minecraft.getInstance().isPaused())){
			this.rightWing1.zRot = (float)(Math.toRadians((double)(WING_ANIMATION_FRAMES[var7])));
			this.leftWing1.zRot = (float)((Math.toRadians((double)(WING_ANIMATION_FRAMES[var7]))) * (-1D));
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.body1.render(var1,var2,var3,var4,var5);
		this.head.render(var1,var2,var3,var4,var5);
		this.tuft1.render(var1,var2,var3,var4,var5);
		this.tuft2.render(var1,var2,var3,var4,var5);
		this.tuft3.render(var1,var2,var3,var4,var5);
		this.beak1.render(var1,var2,var3,var4,var5);
		this.beak2.render(var1,var2,var3,var4,var5);
		this.beak3.render(var1,var2,var3,var4,var5);
		this.beak4.render(var1,var2,var3,var4,var5);
		this.tail1.render(var1,var2,var3,var4,var5);
		this.tail2.render(var1,var2,var3,var4,var5);
		this.tail3.render(var1,var2,var3,var4,var5);
		this.rightleg1.render(var1,var2,var3,var4,var5);
		this.rightleg2.render(var1,var2,var3,var4,var5);
		this.rightfoot1.render(var1,var2,var3,var4,var5);
		this.leftleg1.render(var1,var2,var3,var4,var5);
		this.leftleg2.render(var1,var2,var3,var4,var5);
		this.leftfoot1.render(var1,var2,var3,var4,var5);
		this.rightWing1.render(var1,var2,var3,var4,var5);
		this.leftWing1.render(var1,var2,var3,var4,var5);
	}
	static  {
		float[] var0 = new float[]{5F,10F,15F,20F,25F,30F,35F,30F,25F,20F,15F,10F,5F,0F,-5F,-10F,-15F,-10F,-5F,0F,5F};
		WING_ANIMATION_FRAMES = var0;
	}
}