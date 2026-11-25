package xyz.pixelatedw.mineminenomi.models.entities.mobs.animals;

import com.google.common.collect.ImmutableSet;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.function.Consumer;
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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.extensions.IAgeableListModelExtension;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractGorillaEntity;

public class GorillaModel<T extends AbstractGorillaEntity> extends HumanoidModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","gorilla"),"main");
	private final ModelPart rightArm2;
	private final ModelPart rightPalm;
	private final ModelPart leftArm2;
	private final ModelPart leftPalm;
	private final ModelPart rightFeet1;
	private final ModelPart rightFeet2;
	private final ModelPart rightFeet3;
	private final ModelPart leftFeet1;
	private final ModelPart leftFeet2;
	private final ModelPart leftFeet3;

	public GorillaModel(ModelPart var1) {
		super(var1);
		this.rightArm2 = this.rightArm.getChild("rightArm2");
		this.rightPalm = this.rightArm2.getChild("rightPalm");
		this.leftArm2 = this.leftArm.getChild("leftArm2");
		this.leftPalm = this.leftArm2.getChild("leftPalm");
		this.rightFeet1 = this.rightLeg.getChild("rightFeet1");
		this.rightFeet2 = this.rightLeg.getChild("rightFeet2");
		this.rightFeet3 = this.rightLeg.getChild("rightFeet3");
		this.leftFeet1 = this.leftLeg.getChild("leftFeet1");
		this.leftFeet2 = this.leftLeg.getChild("leftFeet2");
		this.leftFeet3 = this.leftLeg.getChild("leftFeet3");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(0,0).addBox(-2F,0.5F,-2F,4F,7F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2F,7F,2F,0F,0F,0.8727F)).addOrReplaceChild("rightArm2",CubeListBuilder.create().texOffs(18,0).addBox(-2F,0.25F,-2F,4F,7F,4F,new CubeDeformation(-0.01F)).texOffs(36,0).addBox(-2.5F,5.25F,-2.5F,5F,2F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,6.75F,0F,0F,0F,-0.1745F)).addOrReplaceChild("rightPalm",CubeListBuilder.create().texOffs(35,9).addBox(-1.25F,-1F,-2F,3F,2F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.1771F,7.7431F,0F,0F,0F,-0.2182F));
		PartDefinition var3 = var1.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(0,0).addBox(-2F,0.5F,-2F,4F,7F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(2F,7F,2F,0F,0F,-0.8727F)).addOrReplaceChild("leftArm2",CubeListBuilder.create().texOffs(18,0).addBox(-2F,0.25F,-2F,4F,7F,4F,new CubeDeformation(-0.01F)).texOffs(36,0).addBox(-2.5F,5.25F,-2.5F,5F,2F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,6.75F,0F,0F,0F,0.1745F)).addOrReplaceChild("leftPalm",CubeListBuilder.create().texOffs(35,9).addBox(-1F,-1F,-2F,3F,2F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.8229F,7.7431F,0F,0F,0F,0.2182F));
		PartDefinition var4 = var1.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(39,19).addBox(-0.2929F,0.1667F,-0.25F,2F,5F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-3.5F,18.8333F,1.5F,0F,0.7854F,0F));
		PartDefinition var5 = var4.addOrReplaceChild("rightFeet1",CubeListBuilder.create().texOffs(38,28).addBox(-0.5489F,4.1667F,-2.5077F,1F,1F,4F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0F,0F,0F,0F,0.1309F,0F));
		PartDefinition var6 = var4.addOrReplaceChild("rightFeet2",CubeListBuilder.create().texOffs(38,28).addBox(0.4571F,4.1667F,-2.5F,1F,1F,4F,new CubeDeformation(-0.01F)),PartPose.offset(0F,0F,0F));
		PartDefinition var7 = var4.addOrReplaceChild("rightFeet3",CubeListBuilder.create().texOffs(49,29).addBox(1.1744F,4.1667F,-1.7126F,1F,1F,3F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0F,0F,-0.25F,0F,-0.3054F,0F));
		PartDefinition var8 = var1.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(39,19).addBox(-1F,0.1667F,-0.9571F,2F,5F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(2.5F,18.8333F,1.5F,0F,-0.7854F,0F));
		PartDefinition var9 = var8.addOrReplaceChild("leftFeet1",CubeListBuilder.create().texOffs(38,28).addBox(0.1577F,4.1667F,-3.1511F,1F,1F,4F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0F,0F,0F,0F,-0.1309F,0F));
		PartDefinition var10 = var8.addOrReplaceChild("leftFeet2",CubeListBuilder.create().texOffs(38,28).addBox(-0.75F,4.1667F,-3.1571F,1F,1F,4F,new CubeDeformation(-0.01F)),PartPose.offset(0F,0F,0F));
		PartDefinition var11 = var8.addOrReplaceChild("leftFeet3",CubeListBuilder.create().texOffs(49,29).addBox(-1.1874F,4.1667F,-2.6744F,1F,1F,3F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0F,0F,0F,0F,0.3054F,0F));
		PartDefinition var12 = var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,32).addBox(-5.5F,-3.25F,-5.5F,11F,3F,10F,new CubeDeformation(0F)).texOffs(0,46).addBox(-6F,-0.25F,-6F,12F,7F,11F,new CubeDeformation(0F)),PartPose.offset(0F,12.25F,1.5F)).addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,18).addBox(-4.5F,-2.5F,-4.25F,9F,5F,8F,new CubeDeformation(0F)),PartPose.offset(0F,-5.25F,-0.25F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		((IAgeableListModelExtension)(this)).getParts().forEach((Consumer)(((Consumer<ModelPart>)(ModelPart::resetPose))));
		if (((((var1.getDeltaMovement().x) != (0D)) || ((var1.getDeltaMovement().z) != (0D))) ? (1) : (0)) != (0)){
			this.rightLeg.yRot = 0.3F;
			this.leftLeg.yRot = -0.3F;
		}
		float var7 = 1F;
		this.rightArm.xRot = ((((Mth.cos(((var2) * (0.6F)) + (3.1415927F))) * (2F)) * (var3)) * (0.5F)) / (var7);
		this.leftArm.xRot = ((((Mth.cos((var2) * (0.6F))) * (2F)) * (var3)) * (0.5F)) / (var7);
		this.rightLeg.xRot = (((Mth.cos((var2) * (0.8F))) * (1.4F)) * (var3)) / (var7);
		this.leftLeg.xRot = (((Mth.cos(((var2) * (0.8F)) + (3.1415927F))) * (1.4F)) * (var3)) / (var7);
		this.setupAttackAnimation(var1,var4);
		Animation.animationAngles(var1,this,var2,var3,var4,var5,var6);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.rightArm.render(var1,var2,var3,var4,var5);
		this.leftArm.render(var1,var2,var3,var4,var5);
		this.rightLeg.render(var1,var2,var3,var4,var5);
		this.leftLeg.render(var1,var2,var3,var4,var5);
		this.body.render(var1,var2,var3,var4,var5);
	}
	public ModelPart getHead() {
		return this.head;
	}
	protected ModelPart getArm(HumanoidArm var1) {
		return ((var1) == (HumanoidArm.LEFT)) ? (this.leftArm) : (this.rightArm);
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		this.getArm(var1).translateAndRotate(var2);
		var2.mulPose(Axis.ZP.rotationDegrees(((var1) == (HumanoidArm.RIGHT)) ? (-20F) : (20F)));
		var2.translate(((var1) == (HumanoidArm.RIGHT)) ? (-0.12D) : (0.12D),0.32D,0.15D);
	}
	protected Iterable<ModelPart> headParts() {
		return ImmutableSet.of(this.head);
	}
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableSet.of(this.body,this.leftArm,this.rightArm,this.leftLeg,this.rightLeg);
	}
}