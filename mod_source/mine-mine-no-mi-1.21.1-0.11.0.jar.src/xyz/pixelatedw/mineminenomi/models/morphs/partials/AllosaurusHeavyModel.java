package xyz.pixelatedw.mineminenomi.models.morphs.partials;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.HumanoidMorphModel;

public class AllosaurusHeavyModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","allosaurus_heavy"),"main");
	private final ModelPart lowerleftleg1;
	private final ModelPart lowerleftleg2;
	private final ModelPart lefttalon;
	private final ModelPart leftToe1;
	private final ModelPart leftSubToe;
	private final ModelPart leftToe2;
	private final ModelPart leftSubToe2;
	private final ModelPart leftToe3;
	private final ModelPart leftSubToe3;
	private final ModelPart lowerrightleg1;
	private final ModelPart lowerrightleg;
	private final ModelPart rightToe1;
	private final ModelPart rightSubToe;
	private final ModelPart rightToe2;
	private final ModelPart rightSubToe2;
	private final ModelPart rightToe3;
	private final ModelPart rightSubToe3;
	private final ModelPart righttalon;
	private final ModelPart tail;
	private final ModelPart tail2;
	private final ModelPart tail3;
	private final ModelPart tail4;
	private final ModelPart tail5;

	public AllosaurusHeavyModel(ModelPart var1) {
		super(var1);
		this.lowerleftleg1 = this.leftLeg.getChild("lowerleftleg1");
		this.lowerleftleg2 = this.lowerleftleg1.getChild("lowerleftleg2");
		this.lefttalon = this.lowerleftleg2.getChild("lefttalon");
		this.leftToe1 = this.lowerleftleg2.getChild("leftToe1");
		this.leftSubToe = this.leftToe1.getChild("leftSubToe");
		this.leftToe2 = this.lowerleftleg2.getChild("leftToe2");
		this.leftSubToe2 = this.leftToe2.getChild("leftSubToe2");
		this.leftToe3 = this.lowerleftleg2.getChild("leftToe3");
		this.leftSubToe3 = this.leftToe3.getChild("leftSubToe3");
		this.lowerrightleg1 = this.rightLeg.getChild("lowerrightleg1");
		this.lowerrightleg = this.lowerrightleg1.getChild("lowerrightleg");
		this.rightToe1 = this.lowerrightleg.getChild("rightToe1");
		this.rightSubToe = this.rightToe1.getChild("rightSubToe");
		this.rightToe2 = this.lowerrightleg.getChild("rightToe2");
		this.rightSubToe2 = this.rightToe2.getChild("rightSubToe2");
		this.rightToe3 = this.lowerrightleg.getChild("rightToe3");
		this.rightSubToe3 = this.rightToe3.getChild("rightSubToe3");
		this.righttalon = this.lowerrightleg.getChild("righttalon");
		this.tail = this.body.getChild("tail");
		this.tail2 = this.tail.getChild("tail2");
		this.tail3 = this.tail2.getChild("tail3");
		this.tail4 = this.tail3.getChild("tail4");
		this.tail5 = this.tail4.getChild("tail5");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(0,32).addBox(-1.9F,-1.8F,-1.8F,3.8F,6.6F,3.6F,new CubeDeformation(0.2F)),PartPose.offsetAndRotation(2.3F,11.697F,-0.3532F,-0.1047F,0F,0F)).addOrReplaceChild("lowerleftleg1",CubeListBuilder.create().texOffs(0,43).addBox(-1.4958F,-0.5156F,-1.6517F,3F,3F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.0083F,3.7578F,-0.2068F,0.7679F,0F,0F)).addOrReplaceChild("lowerleftleg2",CubeListBuilder.create().texOffs(0,50).addBox(-1.4958F,-0.9112F,-0.4336F,3F,9F,3F,new CubeDeformation(0.1F)),PartPose.offsetAndRotation(0F,0.4599F,0.318F,-0.7941F,0F,0F));
		var2.addOrReplaceChild("lefttalon",CubeListBuilder.create().texOffs(10,60).addBox(-0.6F,-0.6039F,-0.5317F,1.2F,1.2F,2.8F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.0083F,6.5021F,2.1765F,-0.6109F,0F,0F));
		var2.addOrReplaceChild("leftToe1",CubeListBuilder.create().texOffs(13,56).addBox(-0.1632F,-0.957F,-1.7595F,1F,1F,2F,new CubeDeformation(-0.1F)),PartPose.offsetAndRotation(0.6789F,7.8758F,-0.2283F,0.262F,-0.043F,-0.0076F)).addOrReplaceChild("leftSubToe",CubeListBuilder.create().texOffs(13,56).addBox(-0.1632F,-0.957F,-1.7595F,1F,1F,2F,new CubeDeformation(-0.2F)),PartPose.offsetAndRotation(0.0112F,-0.139F,-1.2439F,0.3927F,0F,0F));
		var2.addOrReplaceChild("leftToe2",CubeListBuilder.create().texOffs(13,56).addBox(-0.1632F,-0.957F,-1.7595F,1F,1F,2F,new CubeDeformation(-0.1F)),PartPose.offsetAndRotation(-0.3211F,7.8758F,-0.2283F,0.2618F,0F,0F)).addOrReplaceChild("leftSubToe2",CubeListBuilder.create().texOffs(13,56).addBox(-0.1632F,-0.957F,-1.7595F,1F,1F,2F,new CubeDeformation(-0.2F)),PartPose.offsetAndRotation(0.0112F,-0.139F,-1.2439F,0.3927F,0F,0F));
		var2.addOrReplaceChild("leftToe3",CubeListBuilder.create().texOffs(13,56).addBox(-0.1632F,-0.957F,-1.7595F,1F,1F,2F,new CubeDeformation(-0.1F)),PartPose.offsetAndRotation(-1.3211F,7.8758F,-0.2283F,0.262F,0.043F,0.0076F)).addOrReplaceChild("leftSubToe3",CubeListBuilder.create().texOffs(13,56).addBox(-0.1632F,-0.957F,-1.7595F,1F,1F,2F,new CubeDeformation(-0.2F)),PartPose.offsetAndRotation(0.0112F,-0.139F,-1.2439F,0.3927F,0F,0F));
		PartDefinition var3 = var1.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(0,32).mirror().addBox(-1.9F,-1.8F,-1.8F,3.8F,6.6F,3.6F,new CubeDeformation(0.2F)).mirror(false),PartPose.offsetAndRotation(-2.3F,11.697F,-0.3532F,-0.1047F,0F,0F)).addOrReplaceChild("lowerrightleg1",CubeListBuilder.create().texOffs(0,43).mirror().addBox(-1.5042F,-0.5156F,-1.6517F,3F,3F,3F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-0.0083F,3.7578F,-0.2068F,0.7679F,0F,0F)).addOrReplaceChild("lowerrightleg",CubeListBuilder.create().texOffs(0,50).mirror().addBox(-1.5042F,-0.9112F,-0.4336F,3F,9F,3F,new CubeDeformation(0.1F)).mirror(false),PartPose.offsetAndRotation(0F,0.4599F,0.318F,-0.7941F,0F,0F));
		var3.addOrReplaceChild("rightToe1",CubeListBuilder.create().texOffs(13,56).mirror().addBox(-0.8368F,-0.957F,-1.7595F,1F,1F,2F,new CubeDeformation(-0.1F)).mirror(false),PartPose.offsetAndRotation(-0.6789F,7.8758F,-0.2283F,0.262F,0.043F,0.0076F)).addOrReplaceChild("rightSubToe",CubeListBuilder.create().texOffs(13,56).mirror().addBox(-0.8368F,-0.957F,-1.7595F,1F,1F,2F,new CubeDeformation(-0.2F)).mirror(false),PartPose.offsetAndRotation(-0.0112F,-0.139F,-1.2439F,0.3927F,0F,0F));
		var3.addOrReplaceChild("rightToe2",CubeListBuilder.create().texOffs(13,56).mirror().addBox(-0.8368F,-0.957F,-1.7595F,1F,1F,2F,new CubeDeformation(-0.1F)).mirror(false),PartPose.offsetAndRotation(0.3211F,7.8758F,-0.2283F,0.2618F,0F,0F)).addOrReplaceChild("rightSubToe2",CubeListBuilder.create().texOffs(13,56).mirror().addBox(-0.8368F,-0.957F,-1.7595F,1F,1F,2F,new CubeDeformation(-0.2F)).mirror(false),PartPose.offsetAndRotation(-0.0112F,-0.139F,-1.2439F,0.3927F,0F,0F));
		var3.addOrReplaceChild("rightToe3",CubeListBuilder.create().texOffs(13,56).mirror().addBox(-0.8368F,-0.957F,-1.7595F,1F,1F,2F,new CubeDeformation(-0.1F)).mirror(false),PartPose.offsetAndRotation(1.3211F,7.8758F,-0.2283F,0.262F,-0.043F,-0.0076F)).addOrReplaceChild("rightSubToe3",CubeListBuilder.create().texOffs(13,56).mirror().addBox(-0.8368F,-0.957F,-1.7595F,1F,1F,2F,new CubeDeformation(-0.2F)).mirror(false),PartPose.offsetAndRotation(-0.0112F,-0.139F,-1.2439F,0.3927F,0F,0F));
		var3.addOrReplaceChild("righttalon",CubeListBuilder.create().texOffs(10,60).mirror().addBox(-0.6F,-0.6039F,-0.5317F,1.2F,1.2F,2.8F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0.0083F,6.5021F,2.1765F,-0.6109F,0F,0F));
		var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(19,32).addBox(-4F,0F,-2F,8F,12F,4F,new CubeDeformation(0.1F)),PartPose.offset(0F,0F,0F)).addOrReplaceChild("tail",CubeListBuilder.create().texOffs(0,0).addBox(-2.75F,-3F,-1.25F,5.5F,6F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.0625F,10.125F,1.625F,-0.4363F,0F,0F)).addOrReplaceChild("tail2",CubeListBuilder.create().texOffs(10,3).addBox(-2.5F,-2.5F,-0.8125F,5F,5F,6F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0.5625F)).addOrReplaceChild("tail3",CubeListBuilder.create().texOffs(0,15).addBox(-2.25F,-2.25F,0.1875F,4.5F,4.5F,5.5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,3.75F,0.0873F,0F,0F)).addOrReplaceChild("tail4",CubeListBuilder.create().texOffs(13,18).addBox(-2F,-2F,0.4375F,4F,4F,8F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,4.25F,0.1745F,0F,0F)).addOrReplaceChild("tail5",CubeListBuilder.create().texOffs(39,18).addBox(-1.75F,-1.75F,0.25F,3.5F,3.5F,7.5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,6.9375F,0.1309F,0F,0F));
		var1.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(48,48).mirror().addBox(-1F,-2F,-2F,4F,12F,4F,new CubeDeformation(0.1F)).mirror(false),PartPose.offset(5F,2F,0F));
		var1.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(48,32).addBox(-3F,-2F,-2F,4F,12F,4F,new CubeDeformation(0.1F)),PartPose.offset(-5F,2F,0F));
		var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(32,0).addBox(-4F,-8F,-4F,8F,8F,8F,new CubeDeformation(0.1F)),PartPose.offset(0F,0F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		super.setupAnim(var1,var2,var3,var4,var5,var6);
		if (var1.isSprinting()){

		}
		ModelPart var8 = this.rightLeg;
		var8.xRot = (var8.xRot) - (0.1F);
		ModelPart var9 = this.leftLeg;
		var9.xRot = (var9.xRot) - (0.1F);
		this.tail.yRot = ((float)(Math.sin(((double)(var4)) * (0.01D)))) / (20F);
		this.tail.xRot = ((float)(Math.sin(((double)(var4)) * (0.005D)))) / (10F);
		this.tail2.yRot = ((float)(Math.sin(((double)(var4)) * (0.09D)))) / (20F);
		this.tail2.xRot = ((float)(Math.sin(((double)(var4)) * (0.01D)))) / (10F);
		this.tail3.yRot = ((float)(Math.sin(((double)(var4)) * (0.1D)))) / (20F);
		this.tail3.xRot = ((float)(Math.sin(((double)(var4)) * (0.05D)))) / (10F);
		this.setupAttackAnimation(var1,var4);
		if (var1.isCrouching()){
			this.rightLeg.z = 2F;
			this.rightLeg.y = 11F;
			this.leftLeg.z = 2F;
			this.leftLeg.y = 11F;
			this.tail.z = 2F;
			this.tail.y = 10F;
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		super.renderToBuffer(var1,var2,var3,var4,var5);
	}
	public boolean renderFirstPersonArm(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5, HumanoidArm var6, boolean var7) {
		float var8 = ((var6) == (HumanoidArm.RIGHT)) ? (1F) : (-1F);
		var1.translate((-0.2D) * ((double)(var8)),-0.4D,-0.4D);
		var1.mulPose(Axis.XP.rotationDegrees(20F));
		if (var7){
			var1.translate((0.2D) * ((double)(var8)),0.3D,0.2D);
		}
		super.renderFirstPersonArm(var1,var2,var3,var4,var5,var6,var7);
		return false;
	}
}