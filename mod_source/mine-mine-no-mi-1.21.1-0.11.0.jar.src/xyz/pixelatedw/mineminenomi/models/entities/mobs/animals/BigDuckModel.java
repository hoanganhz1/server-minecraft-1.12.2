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
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BigDuckEntity;

public class BigDuckModel<T extends BigDuckEntity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","big_duck"),"main");
	private final ModelPart body;
	private final ModelPart leftWing;
	private final ModelPart rightWing;
	private final ModelPart tail;
	private final ModelPart leftLeg;
	private final ModelPart lowerLeftLeg;
	private final ModelPart leftFoot;
	private final ModelPart rightLeg;
	private final ModelPart lowerRightLeg;
	private final ModelPart rightFoot;
	private final ModelPart head;
	private final ModelPart neck;
	private final ModelPart mainHead;
	private final ModelPart upperBeak;
	private final ModelPart lowerBeak;
	protected float quackAnimationProgress;

	public BigDuckModel(ModelPart var1) {
		this.body = var1.getChild("body");
		this.leftWing = this.body.getChild("leftWing");
		this.rightWing = this.body.getChild("rightWing");
		this.tail = this.body.getChild("tail");
		this.leftLeg = var1.getChild("leftLeg");
		this.lowerLeftLeg = this.leftLeg.getChild("lowerLeftLeg");
		this.leftFoot = this.lowerLeftLeg.getChild("leftFoot");
		this.rightLeg = var1.getChild("rightLeg");
		this.lowerRightLeg = this.rightLeg.getChild("lowerRightLeg");
		this.rightFoot = this.lowerRightLeg.getChild("rightFoot");
		this.head = var1.getChild("head");
		this.neck = this.head.getChild("neck");
		this.mainHead = this.head.getChild("mainHead");
		this.upperBeak = this.mainHead.getChild("upperBeak");
		this.lowerBeak = this.mainHead.getChild("lowerBeak");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(64,30).addBox(-7F,-5.4042F,-14.8958F,14F,11F,18F,new CubeDeformation(0F)).texOffs(60,61).addBox(-6.5F,-3.6542F,-12.3958F,13F,10F,15F,new CubeDeformation(0F)).texOffs(72,0).addBox(-5.5F,-5.9542F,-15.6458F,11F,11F,17F,new CubeDeformation(0F)).texOffs(61,89).addBox(-6F,-4.7042F,2.6042F,12F,10F,3F,new CubeDeformation(0F)).texOffs(61,104).addBox(-5F,-4.4042F,5.6042F,10F,8F,2F,new CubeDeformation(0F)),PartPose.offset(0F,6.4042F,7.8958F));
		PartDefinition var3 = var2.addOrReplaceChild("leftWing",CubeListBuilder.create().texOffs(8,72).mirror().addBox(0.5F,-2.4526F,-2.2454F,1F,9F,13F,new CubeDeformation(0F)).mirror(false).texOffs(0,84).mirror().addBox(0.5F,-2.4526F,10.7546F,1F,8F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(6.5F,-2.9042F,-11.8958F,-0.2182F,0F,0F));
		PartDefinition var4 = var2.addOrReplaceChild("rightWing",CubeListBuilder.create().texOffs(8,72).addBox(-1.5F,-2.5F,-1.8125F,1F,9F,13F,new CubeDeformation(0F)).texOffs(0,84).addBox(-1.5F,-2.5F,11.1875F,1F,8F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-6.5F,-2.9042F,-11.8958F,-0.2182F,0F,0F));
		PartDefinition var5 = var2.addOrReplaceChild("tail",CubeListBuilder.create().texOffs(53,116).addBox(-5F,0.1937F,0.0119F,10F,0F,10F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-4.3853F,7.3423F,-0.1309F,0F,0F));
		PartDefinition var6 = var1.addOrReplaceChild("leftLeg",CubeListBuilder.create().texOffs(16,97).mirror().addBox(-1F,0F,-1F,2F,6F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offset(2.5F,11.3125F,3F)).addOrReplaceChild("lowerLeftLeg",CubeListBuilder.create().texOffs(16,107).mirror().addBox(-1F,0F,-1F,2F,6F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offset(0F,6F,0F)).addOrReplaceChild("leftFoot",CubeListBuilder.create().texOffs(-7,114).mirror().addBox(-2.5F,1F,-7F,5F,0F,8F,new CubeDeformation(0F)).mirror(false).texOffs(13,117).mirror().addBox(-1.5F,0F,-3F,3F,1F,4F,new CubeDeformation(0F)).mirror(false),PartPose.offset(0F,5.6875F,0F));
		PartDefinition var7 = var1.addOrReplaceChild("rightLeg",CubeListBuilder.create().texOffs(16,97).addBox(-1F,0F,-1F,2F,6F,2F,new CubeDeformation(0F)),PartPose.offset(-2.5F,11.3125F,3F)).addOrReplaceChild("lowerRightLeg",CubeListBuilder.create().texOffs(16,107).addBox(-1F,0F,-1F,2F,6F,2F,new CubeDeformation(0F)),PartPose.offset(0F,6F,0F)).addOrReplaceChild("rightFoot",CubeListBuilder.create().texOffs(13,117).addBox(-1.5F,-0.25F,-3.0625F,3F,1F,4F,new CubeDeformation(0F)).texOffs(-7,114).addBox(-2.5F,0.75F,-7.0625F,5F,0F,8F,new CubeDeformation(0F)),PartPose.offset(0F,5.9375F,0.0625F));
		PartDefinition var8 = var1.addOrReplaceChild("head",CubeListBuilder.create(),PartPose.offset(-0.0789F,0.1837F,-4F));
		PartDefinition var9 = var8.addOrReplaceChild("neck",CubeListBuilder.create().texOffs(38,25).addBox(-6.9211F,-4.61F,-4.4195F,8F,11F,7F,new CubeDeformation(0F)),PartPose.offsetAndRotation(3F,0F,1F,0.4363F,0F,0F));
		PartDefinition var10 = var8.addOrReplaceChild("mainHead",CubeListBuilder.create().texOffs(0,0).addBox(-5.5F,-8.5F,-9F,11F,11F,12F,new CubeDeformation(0F)),PartPose.offset(0.0789F,-2.9837F,-0.5F));
		PartDefinition var11 = var10.addOrReplaceChild("upperBeak",CubeListBuilder.create().texOffs(0,24).addBox(-5F,-1.5F,-5F,10F,3F,6F,new CubeDeformation(0F)).texOffs(0,35).addBox(-5.5F,-1.5F,-11F,11F,3F,6F,new CubeDeformation(0F)),PartPose.offset(0F,-2F,-9F));
		PartDefinition var12 = var10.addOrReplaceChild("lowerBeak",CubeListBuilder.create().texOffs(0,46).addBox(-4.5F,-1F,-11.5F,9F,2F,11F,new CubeDeformation(0F)),PartPose.offset(0F,0.5F,-8F));
		return LayerDefinition.create(var0,128,128);
	}
	public void prepareMobModel(T var1, float var2, float var3, float var4) {
		this.quackAnimationProgress = var1.getQuackAnimationProgress(var4);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		this.head.yRot = (var5) * (0.017453292F);
		this.head.xRot = (var6) * (0.017453292F);
		if ((this.quackAnimationProgress) < (1F)){
			this.upperBeak.xRot = (-(Mth.sin(((this.quackAnimationProgress) * (2F)) * (3.1415927F)))) * (0.5F);
			this.upperBeak.xRot = Math.min(this.upperBeak.xRot,0F);
			this.lowerBeak.xRot = (Mth.sin(((this.quackAnimationProgress) * (2F)) * (3.1415927F))) * (0.5F);
			this.lowerBeak.xRot = Math.max(this.lowerBeak.xRot,0F);
		}else{
			this.upperBeak.xRot = 0F;
			this.lowerBeak.xRot = 0F;
		}
		Vec3 var7 = var1.getDeltaMovement();
		if ((!(var1.onGround())) && ((var7.y) < (0D))){
			this.leftWing.xRot = (float)(Math.toRadians(75D));
			this.leftWing.zRot = ((float)(Math.toRadians(75D))) + ((float)((Math.sin((double)((var4) * (0.75F)))) * (0.8999999761581421D)));
			this.rightWing.xRot = (float)(Math.toRadians(75D));
			this.rightWing.zRot = (-((float)(Math.toRadians(75D)))) - ((float)((Math.sin((double)((var4) * (0.75F)))) * (0.8999999761581421D)));
			this.tail.xRot = (float)((Math.sin((double)((var4) * (1.2F)))) * (0.4000000059604645D));
		}else{
			float var8 = 0F;
			this.leftWing.xRot = var8;
			this.leftWing.yRot = var8;
			this.leftWing.zRot = var8;
			float var9 = 0F;
			this.rightWing.xRot = var9;
			this.rightWing.yRot = var9;
			this.rightWing.zRot = var9;
			float var10 = 1F;
			this.rightLeg.xRot = (((Mth.cos((var2) * (0.6F))) * (1.5F)) * (var3)) / (var10);
			this.leftLeg.xRot = (((Mth.cos(((var2) * (0.6F)) + (3.1415927F))) * (1.5F)) * (var3)) / (var10);
			float var11 = 0.2F;
			if (((var1.getControllingPassenger()) != (null)) && (var1.getControllingPassenger().isSprinting())){
				var11 = 0.6F;
				this.leftWing.zRot = (float)(Math.toRadians(55D));
				this.leftWing.xRot = (float)((Math.sin((double)((var4) * (0.15F)))) * (0.05000000074505806D));
				this.leftWing.yRot = (float)(Math.toRadians(25D));
				this.rightWing.zRot = (float)(Math.toRadians(-55D));
				this.rightWing.xRot = (float)((Math.sin((double)((var4) * (0.15F)))) * (0.05000000074505806D));
				this.rightWing.yRot = (float)(Math.toRadians(-25D));
			}
			this.tail.xRot = (float)((Math.sin((double)((var4) * (var11)))) * (0.10000000149011612D));
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		if (this.young){
			var1.scale(0.5F,0.5F,0.5F);
			var1.translate(0D,1.5D,0D);
		}
		this.body.render(var1,var2,var3,var4,var5);
		this.leftLeg.render(var1,var2,var3,var4,var5);
		this.rightLeg.render(var1,var2,var3,var4,var5);
		this.head.render(var1,var2,var3,var4,var5);
	}
}