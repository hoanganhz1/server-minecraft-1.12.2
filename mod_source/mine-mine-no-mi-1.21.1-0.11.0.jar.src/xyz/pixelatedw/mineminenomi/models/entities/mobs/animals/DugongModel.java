package xyz.pixelatedw.mineminenomi.models.entities.mobs.animals;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
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
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractDugongEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractDugongEntity$TrainingMethod;

public class DugongModel<T extends AbstractDugongEntity> extends AgeableListModel<T> implements ArmedModel, HeadedModel {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","dugong"),"main");
	private final ModelPart head;
	private final ModelPart snout;
	private final ModelPart headShell;
	private final ModelPart body;
	private final ModelPart shell;
	private final ModelPart lowerBody;
	private final ModelPart lowerBody2;
	public final ModelPart tailBase;
	private final ModelPart tail2;
	private final ModelPart tail3;
	private final ModelPart tail4;
	private final ModelPart tailTip1;
	private final ModelPart tailTip2;
	public final ModelPart rightArm;
	public final ModelPart leftArm;

	public DugongModel(ModelPart var1) {
		this.head = var1.getChild("head");
		this.snout = this.head.getChild("snout");
		this.headShell = this.head.getChild("headShell");
		this.body = var1.getChild("body");
		this.shell = this.body.getChild("shell");
		this.lowerBody = var1.getChild("lowerBody");
		this.lowerBody2 = this.lowerBody.getChild("lowerBody2");
		this.tailBase = var1.getChild("tailBase");
		this.tail2 = this.tailBase.getChild("tail2");
		this.tail3 = this.tail2.getChild("tail3");
		this.tail4 = this.tail3.getChild("tail4");
		this.tailTip1 = this.tail4.getChild("tailTip1");
		this.tailTip2 = this.tail4.getChild("tailTip2");
		this.rightArm = var1.getChild("rightArm");
		this.leftArm = var1.getChild("leftArm");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,9).addBox(-2.5F,-4.75F,-2.8F,5F,5F,5F,new CubeDeformation(0F)),PartPose.offset(0F,10.75F,-2.2F));
		PartDefinition var3 = var2.addOrReplaceChild("snout",CubeListBuilder.create().texOffs(0,19).addBox(-1.5F,-1F,-3F,3F,2F,1F,new CubeDeformation(0F)),PartPose.offset(0F,-1.25F,-0.7F));
		PartDefinition var4 = var2.addOrReplaceChild("headShell",CubeListBuilder.create().texOffs(0,0).addBox(-3F,-2.5F,-2F,6F,5F,3F,new CubeDeformation(0F)),PartPose.offset(0F,-2.55F,1.7F));
		PartDefinition var5 = var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(20,13).addBox(-4F,-4F,-2.5F,8F,8F,5F,new CubeDeformation(0F)),PartPose.offset(0F,15F,-2F)).addOrReplaceChild("shell",CubeListBuilder.create().texOffs(19,0).addBox(-4.5F,-0.5F,-3.5F,9F,9F,3F,new CubeDeformation(0F)),PartPose.offset(0F,-4F,4F));
		PartDefinition var6 = var1.addOrReplaceChild("lowerBody",CubeListBuilder.create().texOffs(43,23).addBox(-3F,-3F,-2F,6F,5F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,20.7189F,-0.9321F,0.4363F,0F,0F)).addOrReplaceChild("lowerBody2",CubeListBuilder.create().texOffs(11,27).addBox(-3F,-0.75F,-2.0079F,6F,2F,3F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0F,1.25F,0F,0.6981F,0F,0F));
		PartDefinition var7 = var1.addOrReplaceChild("tailBase",CubeListBuilder.create().texOffs(44,0).addBox(-3F,-2F,-2F,6F,4F,4F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,22.1689F,1.8179F,1.5708F,0F,0F)).addOrReplaceChild("tail2",CubeListBuilder.create().texOffs(48,9).addBox(-2.5F,-0.0912F,-1.3767F,5F,3F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,1.1491F,-0.3358F,0.6981F,0F,0F)).addOrReplaceChild("tail3",CubeListBuilder.create().texOffs(51,17).addBox(-2F,0F,-1F,4F,2F,2F,new CubeDeformation(0F)),PartPose.offset(0F,2.7088F,0.1233F)).addOrReplaceChild("tail4",CubeListBuilder.create().texOffs(43,10).addBox(-0.5F,0.2856F,-2.0321F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,4.2169F,-1.2119F,-2.9671F,0F,0F));
		PartDefinition var8 = var7.addOrReplaceChild("tailTip1",CubeListBuilder.create().texOffs(42,15).addBox(-1.4162F,0.208F,-2.0917F,3F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-1.5314F,-0.4396F,-0.3374F,0F,-0.2269F,0.3491F));
		PartDefinition var9 = var7.addOrReplaceChild("tailTip2",CubeListBuilder.create().texOffs(42,15).addBox(-1.5838F,0.208F,-2.0917F,3F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(1.5371F,-0.478F,-0.3502F,0F,0.2269F,-0.3491F));
		PartDefinition var10 = var1.addOrReplaceChild("rightArm",CubeListBuilder.create().texOffs(0,22).addBox(-1.5F,-1F,-1.5F,2F,7F,3F,new CubeDeformation(0F)),PartPose.offset(-4.5F,12.3F,-2F));
		PartDefinition var11 = var1.addOrReplaceChild("leftArm",CubeListBuilder.create().texOffs(0,22).mirror().addBox(-0.5F,-1F,-1.5F,2F,7F,3F,new CubeDeformation(0F)).mirror(false),PartPose.offset(4.5F,12.3F,-2F));
		return LayerDefinition.create(var0,64,32);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		this.head.z = -2.2F;
		this.head.y = 10.75F;
		this.head.xRot = 0F;
		this.rightArm.x = -4.5F;
		this.rightArm.y = 12.3F;
		this.rightArm.z = -1.5F;
		this.leftArm.x = 4.5F;
		this.leftArm.y = 12.3F;
		this.leftArm.z = -1.5F;
		float var7 = 0F;
		this.rightArm.zRot = var7;
		this.rightArm.yRot = var7;
		float var8 = 0F;
		this.leftArm.zRot = var8;
		this.leftArm.yRot = var8;
		this.body.z = -2F;
		this.lowerBody.z = -0.9321F;
		this.lowerBody.xRot = 0.4363F;
		this.tailBase.z = 1.8179F;
		this.tailBase.y = 22.1689F;
		this.tailBase.xRot = 1.5708F;
		this.tail2.xRot = 0.69F;
		this.rightArm.xRot = (((Mth.cos(((var2) * (0.6662F)) + (3.1415927F))) * (2F)) * (var3)) * (0.5F);
		this.leftArm.xRot = (((Mth.cos((var2) * (0.6662F))) * (2F)) * (var3)) * (0.5F);
		this.head.xRot = (var6) * (0.017453292F);
		this.head.yRot = (var5) * (0.017453292F);
		if (var1.isCheering()){
			this.rightArm.xRot = ((float)(Math.toRadians(180D))) + ((Mth.sin((float)((((double)(var4)) * (3.141592653589793D)) * (0.30000001192092896D)))) * (0.2F));
			this.leftArm.xRot = ((float)(Math.toRadians(180D))) + ((Mth.sin((float)((((double)(var4)) * (3.141592653589793D)) * (0.30000001192092896D)))) * (0.2F));
		}
		if (var1.isResting()){
			this.head.xRot = (float)(Math.toRadians(-90D));
			this.head.z = -4F;
			this.head.y = 9F;
			this.rightArm.yRot = (float)(Math.toRadians(90D));
			this.rightArm.xRot = (float)(Math.toRadians(-45D));
			this.rightArm.z = -3.5F;
			this.leftArm.yRot = (float)(Math.toRadians(-90D));
			this.leftArm.xRot = (float)(Math.toRadians(-45D));
			this.leftArm.z = -3.5F;
			this.lowerBody.xRot = 0F;
			this.lowerBody.z = -2F;
			this.tailBase.xRot = 0F;
			this.tailBase.y = 23F;
			this.tailBase.z = -1.95F;
			this.tail2.xRot = 0F;
		}
		if (var1.isTraining()){
			switch (DugongModel$1.$SwitchMap$xyz$pixelatedw$mineminenomi$entities$mobs$animals$AbstractDugongEntity$TrainingMethod[var1.getTrainingMode().ordinal()]){
			case 1:
				this.rightArm.xRot = (float)(Math.toRadians(-90D));
				this.leftArm.xRot = (float)(Math.toRadians(-90D));
				this.tailBase.xRot = (float)(Math.toRadians(30D));
				this.lowerBody.xRot = (float)(Math.toRadians(0D));
				float var9 = -(Mth.cos((var4) * (0.5F)));
				this.lowerBody.z = -2F;
				this.tailBase.z = -1F;
				this.tailBase.y = 24F;
				float var10 = (-0.7F) * ((3.5F) - (var9));
				this.head.z = var10;
				this.body.z = var10;
				break;
			case 2:
				float var11 = -(Mth.cos((var4) * (1.2F)));
				this.rightArm.z = (-2.5F) * ((0.5F) - (var11));
				this.rightArm.xRot = (float)(Math.toRadians(-90D));
				this.rightArm.yRot = 0F;
				float var12 = Mth.cos((var4) * (1.2F));
				this.leftArm.z = (-2.5F) * ((0.5F) - (var12));
				this.leftArm.xRot = (float)(Math.toRadians(-90D));
				this.leftArm.yRot = 0F;
				break;
			}
		}
		if (var1.isHappy()){
			this.tail2.xRot = (0.4F) * ((0.7F) + (Mth.cos((var4) * (0.4F))));
			float var9_1 = 0F;
			this.tail2.yRot = var9_1;
			this.tail2.zRot = var9_1;
			this.tail3.xRot = (0.6F) * (this.tail2.xRot);
			float var10_1 = 0F;
			this.tail3.yRot = var10_1;
			this.tail3.zRot = var10_1;
		}else{
			this.tail2.xRot = 0.69F;
			this.tail3.xRot = (float)(Math.toRadians(0D));
			this.tail2.zRot = (((Mth.cos(((var2) * (0.4F)) + (3.1415927F))) * (2F)) * (var3)) * (0.3F);
			this.tail3.zRot = (((Mth.cos(((var2) * (0.4F)) + (3.1415927F))) * (2F)) * (var3)) * (0.3F);
		}
		this.setupAttackAnimation(var1,var4);
		Animation.animationAngles(var1,this,var2,var3,var4,var5,var6);
	}
	protected void setupAttackAnimation(T var1, float var2) {
		if ((this.attackTime) > (0F)){
			float var3 = this.attackTime;
			this.body.yRot = (Mth.sin((Mth.sqrt(var3)) * (6.2831855F))) * (0.2F);
			ModelPart var4 = this.rightArm;
			var4.yRot = (var4.yRot) + (this.body.yRot);
			ModelPart var5 = this.leftArm;
			var5.yRot = (var5.yRot) + (this.body.yRot);
			ModelPart var6 = this.leftArm;
			var6.xRot = (var6.xRot) + (this.body.yRot);
			var3 = (1F) - (this.attackTime);
			var3 = (var3) * (var3);
			var3 = (var3) * (var3);
			var3 = (1F) - (var3);
			float var7 = Mth.sin((var3) * (3.1415927F));
			float var8 = ((Mth.sin((this.attackTime) * (3.1415927F))) * (-((this.head.xRot) - (0.7F)))) * (0.75F);
			this.rightArm.xRot = (float)(((double)(this.rightArm.xRot)) - ((((double)(var7)) * (1.2D)) + ((double)(var8))));
			ModelPart var9 = this.rightArm;
			var9.yRot = (var9.yRot) + ((this.body.yRot) * (2F));
			ModelPart var10 = this.rightArm;
			var10.zRot = (var10.zRot) + ((Mth.sin((this.attackTime) * (3.1415927F))) * (-0.4F));
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		if (this.young){
			var1.scale(0.5F,0.5F,0.5F);
			var1.translate(0D,1.5D,0D);
		}
		this.head.render(var1,var2,var3,var4,var5);
		this.body.render(var1,var2,var3,var4,var5);
		this.lowerBody.render(var1,var2,var3,var4,var5);
		this.tailBase.render(var1,var2,var3,var4,var5);
		this.rightArm.render(var1,var2,var3,var4,var5);
		this.leftArm.render(var1,var2,var3,var4,var5);
	}
	public ModelPart getHead() {
		return this.head;
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		this.getArm(var1).translateAndRotate(var2);
		var2.translate(((var1) == (HumanoidArm.RIGHT)) ? (0.03D) : (-0.03D),-0.2D,0D);
	}
	protected ModelPart getArm(HumanoidArm var1) {
		return ((var1) == (HumanoidArm.LEFT)) ? (this.leftArm) : (this.rightArm);
	}
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.head);
	}
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body,this.lowerBody,this.rightArm,this.leftArm,this.tailBase);
	}
}