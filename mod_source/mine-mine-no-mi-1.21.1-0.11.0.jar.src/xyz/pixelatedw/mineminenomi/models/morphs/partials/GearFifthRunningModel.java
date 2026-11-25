package xyz.pixelatedw.mineminenomi.models.morphs.partials;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;

public class GearFifthRunningModel<T extends LivingEntity> extends HumanoidModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","gear_fifth_running"),"main");
	private final ModelPart leftLegs;
	private final ModelPart leftLeg;
	private final ModelPart leftLeg2;
	private final ModelPart leftLeg3;
	private final ModelPart leftLeg4;
	private final ModelPart leftLeg5;
	private final ModelPart leftLeg6;
	private final ModelPart rightLegs;
	private final ModelPart rightLeg;
	private final ModelPart rightLeg2;
	private final ModelPart rightLeg3;
	private final ModelPart rightLeg4;
	private final ModelPart rightLeg5;
	private final ModelPart rightLeg6;
	private final ModelPart leftOverlays;
	private final ModelPart leftPants;
	private final ModelPart leftPants2;
	private final ModelPart leftPants3;
	private final ModelPart leftPants4;
	private final ModelPart leftPants5;
	private final ModelPart leftPants6;
	private final ModelPart rightOverlays;
	private final ModelPart rightPants;
	private final ModelPart rightPants2;
	private final ModelPart rightPants3;
	private final ModelPart rightPants4;
	private final ModelPart rightPants5;
	private final ModelPart rightPants6;

	public GearFifthRunningModel(ModelPart var1) {
		super(var1);
		this.leftLegs = var1.getChild("left_leg");
		this.leftLeg = this.leftLegs.getChild("left_leg1");
		this.leftLeg2 = this.leftLegs.getChild("left_leg2");
		this.leftLeg3 = this.leftLegs.getChild("left_leg3");
		this.leftLeg4 = this.leftLegs.getChild("left_leg4");
		this.leftLeg5 = this.leftLegs.getChild("left_leg5");
		this.leftLeg6 = this.leftLegs.getChild("left_leg6");
		this.rightLegs = var1.getChild("right_leg");
		this.rightLeg = this.rightLegs.getChild("right_leg1");
		this.rightLeg2 = this.rightLegs.getChild("right_leg2");
		this.rightLeg3 = this.rightLegs.getChild("right_leg3");
		this.rightLeg4 = this.rightLegs.getChild("right_leg4");
		this.rightLeg5 = this.rightLegs.getChild("right_leg5");
		this.rightLeg6 = this.rightLegs.getChild("right_leg6");
		this.leftOverlays = var1.getChild("left_pants");
		this.leftPants = this.leftOverlays.getChild("left_pants1");
		this.leftPants2 = this.leftOverlays.getChild("left_pants2");
		this.leftPants3 = this.leftOverlays.getChild("left_pants3");
		this.leftPants4 = this.leftOverlays.getChild("left_pants4");
		this.leftPants5 = this.leftOverlays.getChild("left_pants5");
		this.leftPants6 = this.leftOverlays.getChild("left_pants6");
		this.rightOverlays = var1.getChild("right_pants");
		this.rightPants = this.rightOverlays.getChild("right_pants1");
		this.rightPants2 = this.rightOverlays.getChild("right_pants2");
		this.rightPants3 = this.rightOverlays.getChild("right_pants3");
		this.rightPants4 = this.rightOverlays.getChild("right_pants4");
		this.rightPants5 = this.rightOverlays.getChild("right_pants5");
		this.rightPants6 = this.rightOverlays.getChild("right_pants6");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("left_leg",CubeListBuilder.create(),PartPose.offset(2F,17F,0F));
		var2.addOrReplaceChild("left_leg1",CubeListBuilder.create().texOffs(16,48).addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,-0.9599F,0F,0F));
		var2.addOrReplaceChild("left_leg2",CubeListBuilder.create().texOffs(16,48).addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0.01F)),PartPose.offset(0F,0F,0F));
		var2.addOrReplaceChild("left_leg3",CubeListBuilder.create().texOffs(16,48).addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0.9599F,0F,0F));
		var2.addOrReplaceChild("left_leg4",CubeListBuilder.create().texOffs(16,48).addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,0F,0F,1.9199F,0F,0F));
		var2.addOrReplaceChild("left_leg5",CubeListBuilder.create().texOffs(16,48).addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,2.8798F,0F,0F));
		var2.addOrReplaceChild("left_leg6",CubeListBuilder.create().texOffs(16,48).addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,0F,0F,-2.4435F,0F,0F));
		PartDefinition var3 = var1.addOrReplaceChild("right_leg",CubeListBuilder.create(),PartPose.offset(-2F,17F,0F));
		var3.addOrReplaceChild("right_leg1",CubeListBuilder.create().texOffs(16,48).mirror().addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0F,0F,0F,-0.9599F,0F,0F));
		var3.addOrReplaceChild("right_leg2",CubeListBuilder.create().texOffs(16,48).mirror().addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0.01F)).mirror(false),PartPose.offset(0F,0F,0F));
		var3.addOrReplaceChild("right_leg3",CubeListBuilder.create().texOffs(16,48).mirror().addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0F,0F,0F,0.9599F,0F,0F));
		var3.addOrReplaceChild("right_leg4",CubeListBuilder.create().texOffs(16,48).mirror().addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0.01F)).mirror(false),PartPose.offsetAndRotation(0F,0F,0F,1.9199F,0F,0F));
		var3.addOrReplaceChild("right_leg5",CubeListBuilder.create().texOffs(16,48).mirror().addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0F,0F,0F,2.8798F,0F,0F));
		var3.addOrReplaceChild("right_leg6",CubeListBuilder.create().texOffs(16,48).mirror().addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0.01F)).mirror(false),PartPose.offsetAndRotation(0F,0F,0F,-2.4435F,0F,0F));
		PartDefinition var4 = var1.addOrReplaceChild("left_pants",CubeListBuilder.create(),PartPose.offset(2F,17F,0F));
		var4.addOrReplaceChild("left_pants1",CubeListBuilder.create().texOffs(0,48).addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(0F,0F,0F,-0.9599F,0F,0F));
		var4.addOrReplaceChild("left_pants2",CubeListBuilder.create().texOffs(0,48).addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0.25F)),PartPose.offset(0F,0F,0F));
		var4.addOrReplaceChild("left_pants3",CubeListBuilder.create().texOffs(0,48).addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(0F,0F,0F,0.9599F,0F,0F));
		var4.addOrReplaceChild("left_pants4",CubeListBuilder.create().texOffs(0,48).addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(0F,0F,0F,1.9199F,0F,0F));
		var4.addOrReplaceChild("left_pants5",CubeListBuilder.create().texOffs(0,48).addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(0F,0F,0F,2.8798F,0F,0F));
		var4.addOrReplaceChild("left_pants6",CubeListBuilder.create().texOffs(0,48).addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(0F,0F,0F,-2.4435F,0F,0F));
		PartDefinition var5 = var1.addOrReplaceChild("right_pants",CubeListBuilder.create(),PartPose.offset(-2F,17F,0F));
		var5.addOrReplaceChild("right_pants1",CubeListBuilder.create().texOffs(0,48).mirror().addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0.25F)).mirror(false),PartPose.offsetAndRotation(0F,0F,0F,-0.9599F,0F,0F));
		var5.addOrReplaceChild("right_pants2",CubeListBuilder.create().texOffs(0,48).mirror().addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0.25F)).mirror(false),PartPose.offset(0F,0F,0F));
		var5.addOrReplaceChild("right_pants3",CubeListBuilder.create().texOffs(0,48).mirror().addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0.25F)).mirror(false),PartPose.offsetAndRotation(0F,0F,0F,0.9599F,0F,0F));
		var5.addOrReplaceChild("right_pants4",CubeListBuilder.create().texOffs(0,48).mirror().addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0.25F)).mirror(false),PartPose.offsetAndRotation(0F,0F,0F,1.9199F,0F,0F));
		var5.addOrReplaceChild("right_pants5",CubeListBuilder.create().texOffs(0,48).mirror().addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0.25F)).mirror(false),PartPose.offsetAndRotation(0F,0F,0F,2.8798F,0F,0F));
		var5.addOrReplaceChild("right_pants6",CubeListBuilder.create().texOffs(0,48).mirror().addBox(-2F,-3F,-5.9326F,4F,12F,4F,new CubeDeformation(0.25F)).mirror(false),PartPose.offsetAndRotation(0F,0F,0F,-2.4435F,0F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		this.leftLegs.xRot = (float)((((double)(var4)) * (3.141592653589793D)) * (0.15000000596046448D));
		this.rightLegs.xRot = (float)((((double)(var4)) * (3.141592653589793D)) * (0.15000000596046448D));
		this.leftOverlays.xRot = (float)((((double)(var4)) * (3.141592653589793D)) * (0.15000000596046448D));
		this.rightOverlays.xRot = (float)((((double)(var4)) * (3.141592653589793D)) * (0.15000000596046448D));
		if ((((var1.isSprinting()) && ((var1.getDeltaMovement().length()) > (0.05000000074505806D))) ? (1) : (0)) != (0)){
			this.leftLegs.visible = true;
			this.rightLegs.visible = true;
			this.leftOverlays.visible = true;
			this.rightOverlays.visible = true;
		}else{
			this.leftLegs.visible = false;
			this.rightLegs.visible = false;
			this.leftOverlays.visible = false;
			this.rightOverlays.visible = false;
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.leftLegs.render(var1,var2,var3,var4,var5);
		this.rightLegs.render(var1,var2,var3,var4,var5);
		this.leftOverlays.render(var1,var2,var3,var4,var5);
		this.rightOverlays.render(var1,var2,var3,var4,var5);
	}
}