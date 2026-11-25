package xyz.pixelatedw.mineminenomi.models.morphs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.QuadrupedModel;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.QuadrupedMorphModel;

public class AllosaurusWalkModel<T extends LivingEntity> extends QuadrupedMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","allosaurus_walk"),"main");
	private final ModelPart leftLeg2;
	private final ModelPart leftFeet;
	private final ModelPart leftFeet2;
	private final ModelPart leftToe1;
	private final ModelPart leftToe2;
	private final ModelPart leftToe3;
	private final ModelPart leftToe4;
	private final ModelPart rightLeg2;
	private final ModelPart rightFeet;
	private final ModelPart rightFeet2;
	private final ModelPart rightToe1;
	private final ModelPart rightToe2;
	private final ModelPart rightToe3;
	private final ModelPart rightToe4;
	private final ModelPart rightArm2;
	private final ModelPart rightHand;
	private final ModelPart rightFinger1;
	private final ModelPart rightFinger2;
	private final ModelPart rightFinger3;
	private final ModelPart leftArm2;
	private final ModelPart leftHand;
	private final ModelPart leftFinger1;
	private final ModelPart leftFinger2;
	private final ModelPart leftFinger3;
	private final ModelPart neck3;
	private final ModelPart neck2;
	private final ModelPart neck1;
	private final ModelPart upperHead;
	private final ModelPart upperTeeth;
	private final ModelPart lowerHead;
	private final ModelPart lowerTeeth;
	private final ModelPart tail;
	private final ModelPart tail2;
	private final ModelPart tail3;
	private final ModelPart tail4;
	private final ModelPart tail5;
	private final ModelPart tail6;
	private final ModelPart tail7;
	private final ModelPart tail8;

	public AllosaurusWalkModel(ModelPart var1) {
		super(var1);
		this.leftLeg2 = this.leftHindLeg.getChild("leftLeg2");
		this.leftFeet = this.leftLeg2.getChild("leftFeet");
		this.leftFeet2 = this.leftFeet.getChild("leftFeet2");
		this.leftToe1 = this.leftFeet2.getChild("leftToe1");
		this.leftToe2 = this.leftFeet2.getChild("leftToe2");
		this.leftToe3 = this.leftFeet2.getChild("leftToe3");
		this.leftToe4 = this.leftFeet2.getChild("leftToe4");
		this.rightLeg2 = this.rightHindLeg.getChild("rightLeg2");
		this.rightFeet = this.rightLeg2.getChild("rightFeet");
		this.rightFeet2 = this.rightFeet.getChild("rightFeet2");
		this.rightToe1 = this.rightFeet2.getChild("rightToe1");
		this.rightToe2 = this.rightFeet2.getChild("rightToe2");
		this.rightToe3 = this.rightFeet2.getChild("rightToe3");
		this.rightToe4 = this.rightFeet2.getChild("rightToe4");
		this.rightArm2 = this.rightFrontLeg.getChild("rightArm2");
		this.rightHand = this.rightArm2.getChild("rightHand");
		this.rightFinger1 = this.rightHand.getChild("rightFinger1");
		this.rightFinger2 = this.rightHand.getChild("rightFinger2");
		this.rightFinger3 = this.rightHand.getChild("rightFinger3");
		this.leftArm2 = this.leftFrontLeg.getChild("leftArm2");
		this.leftHand = this.leftArm2.getChild("leftHand");
		this.leftFinger1 = this.leftHand.getChild("leftFinger1");
		this.leftFinger2 = this.leftHand.getChild("leftFinger2");
		this.leftFinger3 = this.leftHand.getChild("leftFinger3");
		this.neck3 = this.head.getChild("neck3");
		this.neck2 = this.head.getChild("neck2");
		this.neck1 = this.head.getChild("neck1");
		this.upperHead = this.head.getChild("upperHead");
		this.upperTeeth = this.upperHead.getChild("upperTeeth");
		this.lowerHead = this.upperHead.getChild("lowerHead");
		this.lowerTeeth = this.lowerHead.getChild("lowerTeeth");
		this.tail = var1.getChild("tail");
		this.tail2 = this.tail.getChild("tail2");
		this.tail3 = this.tail2.getChild("tail3");
		this.tail4 = this.tail3.getChild("tail4");
		this.tail5 = this.tail4.getChild("tail5");
		this.tail6 = this.tail5.getChild("tail6");
		this.tail7 = this.tail6.getChild("tail7");
		this.tail8 = this.tail7.getChild("tail8");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = QuadrupedModel.createBodyMesh(0,CubeDeformation.NONE);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("left_hind_leg",CubeListBuilder.create().texOffs(52,80).addBox(0F,-12F,-7F,7F,24F,14F,new CubeDeformation(0F)),PartPose.offsetAndRotation(6.5F,-10.564F,16.0191F,0.2182F,0F,0F)).addOrReplaceChild("leftLeg2",CubeListBuilder.create().texOffs(46,120).addBox(0.5F,-4.6299F,0.6175F,6F,27F,8F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,9.4613F,-5.3088F,-0.6981F,0F,0F)).addOrReplaceChild("leftFeet",CubeListBuilder.create().texOffs(95,92).addBox(-1F,-2.5F,-5F,9F,5F,10F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,21.6027F,5.2897F,0.48F,0F,0F)).addOrReplaceChild("leftFeet2",CubeListBuilder.create().texOffs(94,109).addBox(-1.5F,-2F,-5F,10F,4F,10F,new CubeDeformation(0F)),PartPose.offset(0F,0.5F,-3F));
		var2.addOrReplaceChild("leftToe1",CubeListBuilder.create().texOffs(104,125).addBox(1.8812F,-1.6739F,-3.3871F,3F,3F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(3.8689F,1.01F,-6.0793F,0.2075F,-0.2612F,-0.0036F));
		var2.addOrReplaceChild("leftToe2",CubeListBuilder.create().texOffs(104,125).addBox(2F,-1.5F,-2.5F,3F,3F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0.9569F,-6.7225F,0.1745F,0F,0F));
		var2.addOrReplaceChild("leftToe3",CubeListBuilder.create().texOffs(104,125).addBox(1.9468F,-1.3945F,-2.4015F,3F,3F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-3.7618F,0.7739F,-5.8025F,0.2188F,0.1744F,0.0077F));
		var2.addOrReplaceChild("leftToe4",CubeListBuilder.create().texOffs(104,125).addBox(2F,-1.5F,-1.5F,3F,3F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-2.4597F,7.6541F,-0.5672F,0F,0F));
		PartDefinition var3 = var1.addOrReplaceChild("right_hind_leg",CubeListBuilder.create().texOffs(52,80).addBox(0F,-12F,-7F,7F,24F,14F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-13.5F,-10.564F,16.0191F,0.2182F,0F,0F)).addOrReplaceChild("rightLeg2",CubeListBuilder.create().texOffs(46,120).addBox(0.5F,-4.6299F,0.6175F,6F,27F,8F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,9.4613F,-5.3088F,-0.6981F,0F,0F)).addOrReplaceChild("rightFeet",CubeListBuilder.create().texOffs(95,92).addBox(-1F,-2.5F,-5F,9F,5F,10F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,21.6027F,5.2897F,0.48F,0F,0F)).addOrReplaceChild("rightFeet2",CubeListBuilder.create().texOffs(94,109).addBox(-1.5F,-2F,-5F,10F,4F,10F,new CubeDeformation(0F)),PartPose.offset(0F,0.5F,-3F));
		var3.addOrReplaceChild("rightToe1",CubeListBuilder.create().texOffs(104,125).addBox(1.8812F,-1.6739F,-3.3871F,3F,3F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(3.8689F,1.01F,-6.0793F,0.2075F,-0.2612F,-0.0036F));
		var3.addOrReplaceChild("rightToe2",CubeListBuilder.create().texOffs(104,125).addBox(2F,-1.5F,-2.5F,3F,3F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0.9569F,-6.7225F,0.1745F,0F,0F));
		var3.addOrReplaceChild("rightToe3",CubeListBuilder.create().texOffs(104,125).addBox(1.9468F,-1.3945F,-2.4015F,3F,3F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-3.7618F,0.7739F,-5.8025F,0.2188F,0.1744F,0.0077F));
		var3.addOrReplaceChild("rightToe4",CubeListBuilder.create().texOffs(104,125).addBox(2F,-1.5F,-1.5F,3F,3F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-2.4597F,7.6541F,-0.5672F,0F,0F));
		PartDefinition var4 = var1.addOrReplaceChild("right_front_leg",CubeListBuilder.create().texOffs(68,169).addBox(0.5F,-2.5559F,-3.1355F,6F,11F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-13F,-7F,-16F,0.3054F,0F,0F)).addOrReplaceChild("rightArm2",CubeListBuilder.create().texOffs(69,187).addBox(1F,-2.6022F,-3.2765F,5F,11F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,7.4026F,-1.1698F,-1.309F,0F,0F)).addOrReplaceChild("rightHand",CubeListBuilder.create().texOffs(69,205).addBox(0F,-2.5F,-1.5F,7F,5F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,9.3131F,-0.4006F,0.6545F,0F,0F));
		var4.addOrReplaceChild("rightFinger1",CubeListBuilder.create().texOffs(70,215).addBox(2.5F,-1F,-2.5F,2F,2F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(2.25F,4.4447F,0.6321F,-1.2217F,0F,0F));
		var4.addOrReplaceChild("rightFinger2",CubeListBuilder.create().texOffs(70,215).addBox(2.5F,-1F,-2.5F,2F,2F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,4.5397F,0.4151F,-1.2217F,0F,0F));
		var4.addOrReplaceChild("rightFinger3",CubeListBuilder.create().texOffs(70,215).addBox(2.5F,-1F,-2.5F,2F,2F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2.25F,4.3628F,0.7644F,-1.2217F,0F,0F));
		PartDefinition var5 = var1.addOrReplaceChild("left_front_leg",CubeListBuilder.create().texOffs(68,169).addBox(0.5F,-2.5559F,-3.1355F,6F,11F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(6F,-7F,-16F,0.3054F,0F,0F)).addOrReplaceChild("leftArm2",CubeListBuilder.create().texOffs(69,187).addBox(1F,-2.6022F,-3.2765F,5F,11F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,7.4026F,-1.1698F,-1.309F,0F,0F)).addOrReplaceChild("leftHand",CubeListBuilder.create().texOffs(69,205).addBox(0F,-2.5F,-1.5F,7F,5F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,9.3131F,-0.4006F,0.6545F,0F,0F));
		var5.addOrReplaceChild("leftFinger1",CubeListBuilder.create().texOffs(70,215).addBox(2.5F,-1F,-2.5F,2F,2F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(2.25F,4.4447F,0.6321F,-1.2217F,0F,0F));
		var5.addOrReplaceChild("leftFinger2",CubeListBuilder.create().texOffs(70,215).addBox(2.5F,-1F,-2.5F,2F,2F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,4.5397F,0.4151F,-1.2217F,0F,0F));
		var5.addOrReplaceChild("leftFinger3",CubeListBuilder.create().texOffs(70,215).addBox(2.5F,-1F,-2.5F,2F,2F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2.25F,4.3628F,0.7644F,-1.2217F,0F,0F));
		var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,0).addBox(-7F,-13.0714F,-10.7857F,21F,27F,16F,new CubeDeformation(0F)).texOffs(1,156).addBox(-6F,-12.0714F,-16.7857F,19F,24F,6F,new CubeDeformation(0F)).texOffs(3,188).addBox(-5F,-11.0714F,-21.7857F,17F,21F,5F,new CubeDeformation(0F)).texOffs(8,217).addBox(-3.5F,-10.0714F,-23.7857F,14F,18F,2F,new CubeDeformation(0F)).texOffs(0,43).addBox(-6F,-12.0714F,5.2143F,19F,25F,12F,new CubeDeformation(0F)).texOffs(0,81).addBox(-5.5F,-11.5714F,17.2143F,18F,24F,7F,new CubeDeformation(0F)).texOffs(0,115).addBox(-4.5F,-10.5714F,24.2143F,16F,22F,5F,new CubeDeformation(0F)),PartPose.offset(-3.5F,-14.9286F,-4.2143F));
		PartDefinition var6 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(75,146).addBox(-3.5F,-14.1985F,-23.0413F,14F,15F,5F,new CubeDeformation(0.03F)),PartPose.offset(-3.5F,-19.3015F,-23.9587F));
		var6.addOrReplaceChild("neck3",CubeListBuilder.create().texOffs(106,52).addBox(-6.5F,-3.5F,-7F,13F,7F,14F,new CubeDeformation(0.02F)),PartPose.offsetAndRotation(3.5F,-6.3693F,-16.9473F,1.3963F,0F,0F));
		var6.addOrReplaceChild("neck2",CubeListBuilder.create().texOffs(74,0).addBox(-6.5F,-4.5F,-7F,13F,9F,14F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(3.5F,-4.5383F,-10.4505F,1.2217F,0F,0F));
		var6.addOrReplaceChild("neck1",CubeListBuilder.create().texOffs(62,29).addBox(-6.5F,-9.5F,-7F,13F,19F,14F,new CubeDeformation(0F)),PartPose.offsetAndRotation(3.5F,1.606F,-0.0608F,0.9599F,0F,0F));
		PartDefinition var7 = var6.addOrReplaceChild("upperHead",CubeListBuilder.create().texOffs(117,149).addBox(-3.5F,-5.1947F,-6.7478F,14F,12F,7F,new CubeDeformation(0F)).texOffs(116,173).addBox(-2.5F,-4.1947F,-12.7478F,12F,11F,6F,new CubeDeformation(0F)).texOffs(117,194).addBox(-1.5F,-3.1947F,-20.7478F,10F,10F,8F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-9.0255F,-22.121F,0.1309F,0F,0F));
		var7.addOrReplaceChild("upperTeeth",CubeListBuilder.create().texOffs(102,20).addBox(7.5F,-1.5F,-8.7143F,0F,3F,20F,new CubeDeformation(0F)).texOffs(102,17).addBox(-0.5F,-1.5F,-8.7143F,0F,3F,20F,new CubeDeformation(0F)).texOffs(58,10).addBox(0F,-1.5F,-8.7143F,7F,3F,0F,new CubeDeformation(0F)),PartPose.offset(0F,7.8053F,-11.0335F));
		var7.addOrReplaceChild("lowerHead",CubeListBuilder.create().texOffs(92,241).addBox(-0.5F,-0.7355F,-21.9123F,8F,3F,9F,new CubeDeformation(0F)).texOffs(94,230).addBox(-1.5F,-0.7355F,-12.9123F,10F,3F,6F,new CubeDeformation(0F)).texOffs(93,219).addBox(-2.5F,-0.7355F,-6.9123F,12F,3F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,7.0898F,2.1358F,0.2182F,0F,0F)).addOrReplaceChild("lowerTeeth",CubeListBuilder.create().texOffs(102,14).addBox(7.25F,-1.5F,-8.7143F,0F,3F,20F,new CubeDeformation(0F)).texOffs(102,11).addBox(-0.25F,-1.5F,-8.7143F,0F,3F,20F,new CubeDeformation(0F)).texOffs(58,13).addBox(0F,-1.5F,-8.7143F,7F,3F,0F,new CubeDeformation(0F)),PartPose.offset(0F,-1.7355F,-12.198F));
		var1.addOrReplaceChild("tail",CubeListBuilder.create().texOffs(166,4).addBox(-3.5F,-7.5F,-37.625F,14F,15F,5F,new CubeDeformation(0F)),PartPose.offset(-3.5F,-15.5F,62.625F)).addOrReplaceChild("tail2",CubeListBuilder.create().texOffs(167,26).addBox(-2F,-6F,0.375F,11F,12F,6F,new CubeDeformation(0F)),PartPose.offset(0F,0F,-33F)).addOrReplaceChild("tail3",CubeListBuilder.create().texOffs(165,47).addBox(-1.5F,-5F,-0.625F,10F,10F,10F,new CubeDeformation(0F)),PartPose.offset(0F,0F,7F)).addOrReplaceChild("tail4",CubeListBuilder.create().texOffs(165,69).addBox(-1F,-4.5F,-0.125F,9F,9F,11F,new CubeDeformation(0F)),PartPose.offset(0F,0F,9F)).addOrReplaceChild("tail5",CubeListBuilder.create().texOffs(160,93).addBox(-0.5F,-4F,-0.125F,8F,8F,16F,new CubeDeformation(0F)),PartPose.offset(0F,0F,11F)).addOrReplaceChild("tail6",CubeListBuilder.create().texOffs(161,119).addBox(0F,-3.5F,-0.625F,7F,7F,15F,new CubeDeformation(0F)),PartPose.offset(0F,0F,16F)).addOrReplaceChild("tail7",CubeListBuilder.create().texOffs(163,144).addBox(0.5F,-3F,0.375F,6F,6F,15F,new CubeDeformation(0F)),PartPose.offset(0F,0F,14F)).addOrReplaceChild("tail8",CubeListBuilder.create().texOffs(161,169).addBox(1F,-2.5F,0.375F,5F,5F,16F,new CubeDeformation(0F)),PartPose.offset(0F,0F,15F));
		return LayerDefinition.create(var0,256,256);
	}
	public void setupAnim(LivingEntity var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		float var7 = 0.3F;
		if (var1.isSprinting()){
			var7 = 0.5F;
		}
		this.rightHindLeg.xRot = (0.2F) + (((Mth.cos((var2) * (var7))) * (0.5F)) * (var3));
		this.rightLeg2.xRot = (-0.7F) - (((Mth.sin((var2) * (var7))) * (0.5F)) * (var3));
		this.leftHindLeg.xRot = (0.2F) + (((Mth.cos(((var2) * (var7)) + (3.1415927F))) * (0.5F)) * (var3));
		this.leftLeg2.xRot = (-0.7F) - (((Mth.sin(((var2) * (var7)) + (3.1415927F))) * (0.5F)) * (var3));
		if (var1.isSprinting()){
			this.tail3.xRot = ((Mth.cos((var2) * (0.6F))) * (0.1F)) * (var3);
		}else{
			this.tail2.yRot = ((float)(Math.sin(((double)(var4)) * (0.01D)))) / (20F);
			this.tail2.xRot = ((float)(Math.sin(((double)(var4)) * (0.005D)))) / (10F);
			this.tail3.yRot = ((float)(Math.sin(((double)(var4)) * (0.01D)))) / (20F);
			this.tail3.xRot = ((float)(Math.sin(((double)(var4)) * (0.005D)))) / (10F);
			this.tail4.yRot = ((float)(Math.sin(((double)(var4)) * (0.01D)))) / (20F);
			this.tail4.xRot = ((float)(Math.sin(((double)(var4)) * (0.005D)))) / (10F);
		}
		this.tail5.yRot = ((float)(Math.sin(((double)(var4)) * (0.06D)))) / (20F);
		this.tail5.xRot = ((float)(Math.sin(((double)(var4)) * (0.02D)))) / (10F);
		this.tail6.yRot = ((float)(Math.sin(((double)(var4)) * (0.06D)))) / (20F);
		this.tail6.xRot = ((float)(Math.sin(((double)(var4)) * (0.02D)))) / (10F);
		this.tail7.yRot = ((float)(Math.sin(((double)(var4)) * (0.06D)))) / (20F);
		this.tail7.xRot = ((float)(Math.sin(((double)(var4)) * (0.02D)))) / (10F);
		this.tail8.yRot = ((float)(Math.sin(((double)(var4)) * (0.06D)))) / (20F);
		this.tail8.xRot = ((float)(Math.sin(((double)(var4)) * (0.02D)))) / (10F);
		this.attackTime = var1.attackAnim;
		if ((this.attackTime) > (0F)){
			ModelPart var8 = this.head;
			var8.yRot = (var8.yRot) + (this.body.yRot);
			float var9 = (1F) - (this.attackTime);
			var9 = (var9) * (var9);
			var9 = (var9) * (var9);
			var9 = (1F) - (var9);
			float var10 = Mth.sin((var9) * (3.1415927F));
			float var11 = ((Mth.sin((this.attackTime) * (3.1415927F))) * (-((this.head.xRot) - (0.1F)))) * (0.15F);
			this.head.xRot = (float)(((double)(this.head.xRot)) + ((((double)(var10)) * (0.8D)) + ((double)(var11))));
			ModelPart var12 = this.head;
			var12.zRot = (var12.zRot) - ((Mth.sin((this.attackTime) * (3.1415927F))) * (-0.8F));
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		super.renderToBuffer(var1,var2,var3,var4,var5);
		this.tail.render(var1,var2,var3,var4,var5);
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		this.head.translateAndRotate(var2);
		var2.scale(2F,2F,2F);
		var2.mulPose(Axis.ZP.rotationDegrees(90F));
		var2.mulPose(Axis.XP.rotationDegrees(260F));
		var2.translate(0.05D,0.5D,0.1D);
	}
	public ModelPart getHead() {
		return this.head;
	}
}