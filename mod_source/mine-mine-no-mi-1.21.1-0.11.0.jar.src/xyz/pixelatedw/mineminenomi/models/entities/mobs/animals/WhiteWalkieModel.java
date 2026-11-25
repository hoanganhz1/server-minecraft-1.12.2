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
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WhiteWalkieEntity;

public class WhiteWalkieModel<T extends WhiteWalkieEntity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","white_walkie"),"main");
	private final ModelPart body;
	private final ModelPart ass;
	private final ModelPart tail;
	private final ModelPart frontLeftLeg;
	private final ModelPart frontLeftFoot;
	private final ModelPart frontRightLeg;
	private final ModelPart frontRightFoot;
	private final ModelPart backRightLeg;
	private final ModelPart backRightFoot;
	private final ModelPart backLeftLeg;
	private final ModelPart backLeftFoot;
	private final ModelPart bodyFur;
	private final ModelPart head;
	private final ModelPart upperMouth;
	private final ModelPart lowerMouth;
	private final ModelPart chinFur;
	private final ModelPart leftEar;
	private final ModelPart rightEar;
	protected float entityTick;
	protected float biteAnimationProgress;
	private boolean isSleeping;

	public WhiteWalkieModel(ModelPart var1) {
		this.body = var1.getChild("body");
		this.ass = this.body.getChild("ass");
		this.tail = this.body.getChild("tail");
		this.frontLeftLeg = this.body.getChild("frontLeftLeg");
		this.frontLeftFoot = this.frontLeftLeg.getChild("frontLeftFoot");
		this.frontRightLeg = this.body.getChild("frontRightLeg");
		this.frontRightFoot = this.frontRightLeg.getChild("frontRightFoot");
		this.backRightLeg = this.body.getChild("backRightLeg");
		this.backRightFoot = this.backRightLeg.getChild("backRightFoot");
		this.backLeftLeg = this.body.getChild("backLeftLeg");
		this.backLeftFoot = this.backLeftLeg.getChild("backLeftFoot");
		this.bodyFur = this.body.getChild("bodyFur");
		this.head = var1.getChild("head");
		this.upperMouth = this.head.getChild("upperMouth");
		this.lowerMouth = this.head.getChild("lowerMouth");
		this.chinFur = this.lowerMouth.getChild("chinFur");
		this.leftEar = this.head.getChild("leftEar");
		this.rightEar = this.head.getChild("rightEar");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(6,109).addBox(-11F,-8.3771F,-27.5417F,22F,16F,5F,new CubeDeformation(0F)).texOffs(0,0).addBox(-13F,-9.0385F,-24.5417F,26F,20F,34F,new CubeDeformation(0F)).texOffs(0,54).addBox(-11F,-11.0385F,-22.5417F,22F,20F,30F,new CubeDeformation(0F)),PartPose.offset(0F,3.4271F,9.751F));
		PartDefinition var3 = var2.addOrReplaceChild("ass",CubeListBuilder.create().texOffs(89,0).addBox(-15F,-10.1202F,-18.2696F,24F,18F,8F,new CubeDeformation(0F)),PartPose.offsetAndRotation(3F,2.5729F,23.874F,-0.0436F,0F,0F));
		PartDefinition var4 = var2.addOrReplaceChild("tail",CubeListBuilder.create().texOffs(126,29).addBox(-4F,-0.2505F,1.0033F,8F,10F,0F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-5.4285F,12.8316F,0.1309F,0F,0F));
		PartDefinition var5 = var2.addOrReplaceChild("frontLeftLeg",CubeListBuilder.create().texOffs(113,61).mirror().addBox(-3F,-1.5847F,-3.176F,6F,10F,6F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(7F,10.5729F,-19.751F,-0.1309F,0F,0F)).addOrReplaceChild("frontLeftFoot",CubeListBuilder.create().texOffs(110,78).mirror().addBox(-3F,-2F,-4.25F,6F,4F,8F,new CubeDeformation(-0.01F)).mirror(false),PartPose.offsetAndRotation(0F,8.3032F,-1.0862F,0.1309F,0F,0F));
		PartDefinition var6 = var2.addOrReplaceChild("frontRightLeg",CubeListBuilder.create().texOffs(113,61).addBox(-3F,-1.4799F,-1.7758F,6F,10F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-7F,10.5729F,-21.001F,-0.1309F,0F,0F)).addOrReplaceChild("frontRightFoot",CubeListBuilder.create().texOffs(110,78).addBox(-3F,-2F,-3.25F,6F,4F,8F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0F,8.408F,-0.6861F,0.1309F,0F,0F));
		PartDefinition var7 = var2.addOrReplaceChild("backRightLeg",CubeListBuilder.create().texOffs(139,63).addBox(-3F,-0.058F,-2.7389F,6F,8F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-8F,10.5729F,4.3735F,-0.1309F,0F,0F)).addOrReplaceChild("backRightFoot",CubeListBuilder.create().texOffs(139,78).addBox(-3F,-2F,-4F,6F,4F,8F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0F,8.0212F,-0.7307F,0.1309F,0F,0F));
		PartDefinition var8 = var2.addOrReplaceChild("backLeftLeg",CubeListBuilder.create().texOffs(139,63).mirror().addBox(-3F,-0.0792F,-3F,6F,8F,6F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(8F,10.5729F,4.6658F,-0.1309F,0F,0F)).addOrReplaceChild("backLeftFoot",CubeListBuilder.create().texOffs(139,78).mirror().addBox(-3F,-2F,-4F,6F,4F,8F,new CubeDeformation(-0.01F)).mirror(false),PartPose.offsetAndRotation(0F,8F,-0.9917F,0.1309F,0F,0F));
		PartDefinition var9 = var2.addOrReplaceChild("bodyFur",CubeListBuilder.create().texOffs(64,120).addBox(-11F,-2.25F,-28F,22F,7F,0F,new CubeDeformation(0F)).texOffs(64,77).addBox(12F,-2.25F,-27F,0F,7F,32F,new CubeDeformation(0F)).texOffs(64,77).addBox(-12F,-2.25F,-27F,0F,7F,32F,new CubeDeformation(0F)).texOffs(64,119).addBox(-11F,-3.25F,6F,22F,8F,0F,new CubeDeformation(0F)),PartPose.offset(0F,11.8229F,3.874F));
		PartDefinition var10 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(4,133).addBox(-10F,-9.879F,-20.199F,20F,18F,10F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,4.5813F,-4.4598F,0.0436F,0F,0F));
		PartDefinition var11 = var10.addOrReplaceChild("upperMouth",CubeListBuilder.create().texOffs(6,162).addBox(-9F,-11.6868F,-16.3353F,18F,10F,16F,new CubeDeformation(0F)).texOffs(4,170).addBox(3F,-2.3763F,-15.8167F,4F,2F,1F,new CubeDeformation(0F)).texOffs(4,170).addBox(-7F,-2.3763F,-15.8167F,4F,2F,1F,new CubeDeformation(0F)),PartPose.offset(0F,4.7688F,-14.9009F));
		PartDefinition var12 = var10.addOrReplaceChild("lowerMouth",CubeListBuilder.create().texOffs(3,190).addBox(-8F,0.2077F,-11.7395F,16F,4F,12F,new CubeDeformation(0F)),PartPose.offset(0F,2.912F,-18.2061F)).addOrReplaceChild("chinFur",CubeListBuilder.create().texOffs(3,203).addBox(7F,-2.4798F,-14.4479F,0F,4F,10.375F,new CubeDeformation(0F)).texOffs(3,203).addBox(-7F,-2.4798F,-14.4479F,0F,4F,10.375F,new CubeDeformation(0F)).texOffs(3,208).addBox(-7F,-2.4798F,-14.4479F,14F,4F,0F,new CubeDeformation(0F)),PartPose.offset(0F,4.5067F,3.7492F));
		PartDefinition var13 = var10.addOrReplaceChild("leftEar",CubeListBuilder.create().texOffs(1,164).mirror().addBox(-0.7642F,-0.5365F,-1.5F,6F,1F,3F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(9.3123F,-8.7507F,-15.4047F,0F,0F,0.48F));
		PartDefinition var14 = var10.addOrReplaceChild("rightEar",CubeListBuilder.create().texOffs(1,164).addBox(-5.2358F,-0.5365F,-1.5F,6F,1F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-9.4837F,-8.6947F,-15.4047F,0F,0F,-0.48F));
		return LayerDefinition.create(var0,256,256);
	}
	public void prepareMobModel(T var1, float var2, float var3, float var4) {
		if (var1 instanceof WhiteWalkieEntity){
			this.biteAnimationProgress = var1.getBiteAnimationProgress(var4);
		}
		this.entityTick = (float)(var1.tickCount);
		this.isSleeping = var1.isSleeping();
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		this.head.zRot = 0F;
		this.leftEar.zRot = (float)(Math.toRadians(60D));
		this.rightEar.zRot = (float)(Math.toRadians(-60D));
		float var7 = 0.95F;
		float var8 = 0.6F;
		if (var1.isSprinting()){
			var7 = 0.125F;
		}
		this.frontRightLeg.xRot = (float)((Math.toRadians(-8D)) + ((double)(((Mth.cos((var2) * (var7))) * (var8)) * (var3))));
		this.frontLeftLeg.xRot = (float)((Math.toRadians(-8D)) + ((double)(((Mth.cos(((var2) * (var7)) + (3.1415927F))) * (var8)) * (var3))));
		this.backRightLeg.xRot = (float)((Math.toRadians(-8D)) + ((double)(((Mth.cos(((var2) * (var7)) + (3.1415927F))) * (var8)) * (var3))));
		this.backLeftLeg.xRot = (float)((Math.toRadians(-8D)) + ((double)(((Mth.cos((var2) * (var7))) * (var8)) * (var3))));
		if ((var1 instanceof WhiteWalkieEntity) && ((var1.getShakeAnimationTime()) > (0F))){
			this.head.zRot = (float)((Math.cos(((double)((var4) * (0.4F))) + (3.141592653589793D))) * (0.10000000149011612D));
			ModelPart var9 = this.leftEar;
			var9.zRot = (float)(((double)(var9.zRot)) + ((Math.cos(((double)((var4) * (0.8F))) + (3.141592653589793D))) * (0.20000000298023224D)));
			ModelPart var10 = this.rightEar;
			var10.zRot = (float)(((double)(var10.zRot)) + ((Math.cos(((double)((var4) * (0.8F))) + (3.141592653589793D))) * (0.20000000298023224D)));
		}
		if ((this.biteAnimationProgress) < (1F)){
			float var9_1 = (Mth.sin(((this.biteAnimationProgress) * (0.5F)) * (3.1415927F))) * (0.7F);
			this.upperMouth.xRot = ((float)(Math.toRadians(-40D))) + (var9_1);
			this.lowerMouth.xRot = ((float)(Math.toRadians(40D))) - (var9_1);
		}else{
			if ((this.biteAnimationProgress) >= (1F)){
				var1.swinging = false;
				this.upperMouth.xRot = (float)(Math.toRadians(0D));
				this.lowerMouth.xRot = (float)(Math.toRadians(0D));
			}else{
				return;
			}
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.body.render(var1,var2,var3,var4,var5);
		this.head.render(var1,var2,var3,var4,var5);
	}
	public void setSleepPosition(boolean var1) {
		this.showLegs(!(var1));
		this.head.y = (var1) ? (7F) : (4.5813F);
	}
	public void showLegs(boolean var1) {
		if (var1){
			this.frontRightLeg.visible = true;
			this.frontLeftLeg.visible = true;
			this.backRightLeg.visible = true;
			this.backLeftLeg.visible = true;
		}else{
			this.frontRightLeg.visible = false;
			this.frontLeftLeg.visible = false;
			this.backRightLeg.visible = false;
			this.backLeftLeg.visible = false;
		}
	}
}