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
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.FightingFishEntity;

public class FightingFishModel<T extends FightingFishEntity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","fighting_fish"),"main");
	private final ModelPart body;
	private final ModelPart topFins;
	private final ModelPart tail;
	private final ModelPart flippersLeft;
	private final ModelPart flippersRight;
	private final ModelPart head;
	private final ModelPart rightHorn;
	private final ModelPart leftHorn;

	public FightingFishModel(ModelPart var1) {
		this.body = var1.getChild("body");
		this.topFins = this.body.getChild("topFins");
		this.tail = this.body.getChild("tail");
		this.flippersLeft = this.body.getChild("flippersLeft");
		this.flippersRight = this.body.getChild("flippersRight");
		this.head = this.body.getChild("head");
		this.rightHorn = this.head.getChild("rightHorn");
		this.leftHorn = this.head.getChild("leftHorn");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("body",CubeListBuilder.create().texOffs(83,83).addBox(-18F,-56F,-7F,36F,50F,26F,new CubeDeformation(0F)).texOffs(0,105).addBox(-17F,-55F,19F,34F,48F,7F,new CubeDeformation(0F)).texOffs(180,185).addBox(-16F,-54F,26F,32F,46F,6F,new CubeDeformation(0F)).texOffs(0,161).addBox(-13F,-51F,32F,26F,40F,9F,new CubeDeformation(0F)).texOffs(201,1).addBox(-7F,-46F,41F,14F,30F,9F,new CubeDeformation(0F)),PartPose.offset(0F,16F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("topFins",CubeListBuilder.create(),PartPose.offsetAndRotation(0F,8F,0F,0.0873F,0F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("Fin2_r1_r1",CubeListBuilder.create().texOffs(42,186).addBox(-1F,-57.0162F,9.3144F,0F,14F,29F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-35.9056F,53.2801F,1.2217F,0F,0F));
		PartDefinition var4 = var2.addOrReplaceChild("Fin3_r1_r1",CubeListBuilder.create().texOffs(190,32).addBox(-1F,-48.6147F,10.8288F,0F,42F,29F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-31.4289F,51.3315F,1.1345F,0F,0F));
		PartDefinition var5 = var1.addOrReplaceChild("tail",CubeListBuilder.create().texOffs(156,160).addBox(-1F,-18F,-4F,2F,18F,8F,new CubeDeformation(0F)).texOffs(0,0).addBox(0F,-28F,4F,0F,50F,54F,new CubeDeformation(0F)),PartPose.offset(0F,-22F,54F));
		PartDefinition var6 = var1.addOrReplaceChild("flippersLeft",CubeListBuilder.create().texOffs(109,59).mirror().addBox(-1.1585F,-7.7967F,0F,38F,16F,0F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(16F,-10F,-13F,0F,0F,0.3927F));
		PartDefinition var7 = var1.addOrReplaceChild("flippersRight",CubeListBuilder.create().texOffs(109,59).addBox(-35.8839F,-7.9665F,0F,38F,16F,0F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-17F,-10F,-13F,0F,0F,-0.3927F));
		PartDefinition var8 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(109,0).addBox(-17F,-63F,-17F,34F,48F,10F,new CubeDeformation(0F)).texOffs(75,160).addBox(-16F,-62F,-25F,32F,46F,8F,new CubeDeformation(0F)).texOffs(0,0).addBox(-15F,-58F,-33F,30F,38F,8F,new CubeDeformation(0F)).texOffs(167,240).addBox(-13.5F,-36F,-38F,27F,11F,5F,new CubeDeformation(0F)),PartPose.offset(0F,8F,0F));
		PartDefinition var9 = var8.addOrReplaceChild("eyebrowRight_r1",CubeListBuilder.create().texOffs(117,215).addBox(-13F,-58F,-35F,13F,8F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-14.5302F,8.9549F,-18.4865F,-0.2967F,0F,0.2182F));
		PartDefinition var10 = var8.addOrReplaceChild("eyebrowLeft_r1",CubeListBuilder.create().texOffs(117,215).addBox(0F,-58F,-35F,13F,8F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(14.5302F,8.9549F,-18.4865F,-0.2967F,0F,-0.2182F));
		PartDefinition var11 = var8.addOrReplaceChild("nose_r1",CubeListBuilder.create().texOffs(110,229).addBox(-3F,-46F,-33F,6F,10F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,10.1535F,-24.5092F,-0.48F,0F,0F));
		PartDefinition var12 = var8.addOrReplaceChild("rightHorn",CubeListBuilder.create(),PartPose.offsetAndRotation(-6F,-2F,-1F,0.3927F,0.3054F,0F));
		PartDefinition var13 = var12.addOrReplaceChild("Horn1_r1_r1",CubeListBuilder.create().texOffs(123,232).mirror().addBox(-13.3176F,-75.2156F,-16.4664F,8F,11F,13F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(6F,-0.5533F,-15.4788F,-0.3491F,0F,0F));
		PartDefinition var14 = var12.addOrReplaceChild("Horn2_r1_r1",CubeListBuilder.create().texOffs(0,227).mirror().addBox(-14.3176F,-74.1982F,-5.345F,10F,8F,12F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(6F,-9.2795F,26.1106F,0.5236F,0F,0F));
		PartDefinition var15 = var12.addOrReplaceChild("Horn3_r1_r1",CubeListBuilder.create().texOffs(33,236).mirror().addBox(-14.5912F,-75.5682F,-21.7721F,8F,8F,12F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(8.2298F,3.39F,-10.4674F,-0.0873F,0.2182F,0F));
		PartDefinition var16 = var12.addOrReplaceChild("Horn4_r1_r1",CubeListBuilder.create().texOffs(0,211).mirror().addBox(-11.5873F,-68.896F,-13.4859F,6F,9F,6F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(8.1316F,-33.0373F,27.2738F,0.7418F,0F,-0.1309F));
		PartDefinition var17 = var12.addOrReplaceChild("Horn5_r1_r1",CubeListBuilder.create().texOffs(75,232).mirror().addBox(-14.3176F,-68.3589F,-18.2183F,10F,11F,13F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(6F,-18.2846F,-38.5549F,-0.9599F,0F,0F));
		PartDefinition var18 = var8.addOrReplaceChild("leftHorn",CubeListBuilder.create(),PartPose.offsetAndRotation(-28F,-2F,-1F,0.3927F,-0.3054F,0F));
		PartDefinition var19 = var18.addOrReplaceChild("Horn1_r2_r1",CubeListBuilder.create().texOffs(123,232).addBox(3.7443F,-75.6612F,-26.6796F,8F,11F,13F,new CubeDeformation(0F)),PartPose.offsetAndRotation(28F,-0.5533F,-15.4788F,-0.3491F,0F,0F));
		PartDefinition var20 = var18.addOrReplaceChild("Horn2_r2_r1",CubeListBuilder.create().texOffs(0,227).addBox(2.7443F,-82.3086F,-11.5683F,10F,8F,12F,new CubeDeformation(0F)),PartPose.offsetAndRotation(28F,-9.2795F,26.1106F,0.5236F,0F,0F));
		PartDefinition var21 = var18.addOrReplaceChild("Horn3_r2_r1",CubeListBuilder.create().texOffs(33,236).addBox(2.2045F,-78.0495F,-38.2916F,8F,8F,12F,new CubeDeformation(0F)),PartPose.offsetAndRotation(24.964F,3.39F,-3.1074F,-0.0873F,-0.2182F,0F));
		PartDefinition var22 = var18.addOrReplaceChild("Horn4_r2_r1",CubeListBuilder.create().texOffs(0,211).addBox(3.226F,-81.2571F,-14.969F,6F,9F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(25.5775F,-28.5994F,27.2738F,0.7418F,0F,0.1309F));
		PartDefinition var23 = var18.addOrReplaceChild("Horn5_r2_r1",CubeListBuilder.create().texOffs(75,232).addBox(2.7443F,-62.8664F,-26.8405F,10F,11F,13F,new CubeDeformation(0F)),PartPose.offsetAndRotation(28F,-18.2846F,-38.5549F,-0.9599F,0F,0F));
		return LayerDefinition.create(var0,256,256);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		this.body.xRot = (var6) * (0.017453292F);
		this.body.yRot = (var5) * (0.017453292F);
		this.tail.yRot = (0.1F) * ((-0.2F) + (Mth.cos((var4) * (0.15F))));
		this.flippersRight.yRot = (0.1F) * ((-1.5F) + (Mth.cos((var4) * (0.35F))));
		this.flippersLeft.yRot = (0.1F) * ((-1.5F) + (Mth.cos((var4) * (0.35F))));
		this.topFins.xRot = (0.01F) * ((-0.2F) + (Mth.cos((var4) * (0.15F))));
		this.topFins.yRot = (0.02F) * ((-0.2F) + (Mth.cos((var4) * (0.15F))));
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.body.render(var1,var2,var3,var4,var5);
	}
}