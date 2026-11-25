package xyz.pixelatedw.mineminenomi.models.morphs;

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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.HumanoidMorphModel;

public class ShinokuniModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","shinokuni"),"main");
	private final ModelPart neck;

	public ShinokuniModel(ModelPart var1) {
		super(var1);
		this.neck = this.body.getChild("neck");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot();
		var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(46,0).addBox(-7F,-6F,-1F,11F,3F,6F,new CubeDeformation(0F)).texOffs(42,27).addBox(-8F,-11F,-2F,13F,5F,8F,new CubeDeformation(0F)).texOffs(0,16).addBox(-9F,-19F,-3F,15F,9F,10F,new CubeDeformation(0F)).texOffs(40,16).addBox(-8F,-22F,-0.5F,13F,3F,7F,new CubeDeformation(0F)).texOffs(52,51).addBox(-7.5F,-21F,-2.5F,12F,2F,2F,new CubeDeformation(0F)).texOffs(42,40).addBox(-8F,-3F,-2F,13F,3F,8F,new CubeDeformation(0F)).texOffs(0,35).addBox(-9F,0F,-3F,15F,3F,10F,new CubeDeformation(0F)).texOffs(0,0).addBox(-10F,3F,-4F,17F,4F,12F,new CubeDeformation(0F)),PartPose.offset(1.5F,17F,-2F)).addOrReplaceChild("neck",CubeListBuilder.create().texOffs(36,74).addBox(-2F,-2.5F,-2F,4F,5F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-1.5F,-19.6013F,-1.2589F,0.48F,0F,0F));
		var1.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(0,48).mirror().addBox(-0.4739F,-4.8191F,-5F,7F,7F,8F,new CubeDeformation(0F)).mirror(false).texOffs(0,90).mirror().addBox(12.5261F,-2.8191F,-2.5F,7F,3F,3F,new CubeDeformation(0F)).mirror(false).texOffs(0,76).mirror().addBox(6.5261F,-3.8191F,-1F,11F,1F,2F,new CubeDeformation(0F)).mirror(false).texOffs(0,63).mirror().addBox(6.5261F,-3.8191F,-3F,10F,1F,2F,new CubeDeformation(0F)).mirror(false).texOffs(0,66).mirror().addBox(6.5261F,0.1809F,-1F,10F,1F,2F,new CubeDeformation(0F)).mirror(false).texOffs(0,86).mirror().addBox(6.5261F,0.1809F,-3F,9F,1F,2F,new CubeDeformation(0F)).mirror(false).texOffs(0,80).mirror().addBox(6.5261F,-3.8191F,1F,11F,4F,1F,new CubeDeformation(0F)).mirror(false).texOffs(0,69).mirror().addBox(6.5261F,-3.8191F,-4F,9F,5F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(7F,-1F,1F,0F,0F,1.2217F));
		var1.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(0,48).addBox(-6.5261F,-4.8191F,-5F,7F,7F,8F,new CubeDeformation(0F)).texOffs(0,90).addBox(-19.5261F,-2.8191F,-2.5F,7F,3F,3F,new CubeDeformation(0F)).texOffs(0,76).addBox(-17.5261F,-3.8191F,-1F,11F,1F,2F,new CubeDeformation(0F)).texOffs(0,63).addBox(-16.5261F,-3.8191F,-3F,10F,1F,2F,new CubeDeformation(0F)).texOffs(0,66).addBox(-16.5261F,0.1809F,-1F,10F,1F,2F,new CubeDeformation(0F)).texOffs(0,86).addBox(-15.5261F,0.1809F,-3F,9F,1F,2F,new CubeDeformation(0F)).texOffs(0,80).addBox(-17.5261F,-3.8191F,1F,11F,4F,1F,new CubeDeformation(0F)).texOffs(0,69).addBox(-15.5261F,-3.8191F,-4F,9F,5F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-7F,-1F,1F,0F,0F,-1.2217F));
		return LayerDefinition.create(var0,128,128);
	}
	public void setupAnim(LivingEntity var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		float var7 = 1F;
		this.rightArm.yRot = ((((Mth.cos(((var2) * (0.6F)) + (3.1415927F))) * (0.5F)) * (var3)) * (0.5F)) / (var7);
		this.leftArm.yRot = ((((-(Mth.cos((var2) * (0.6F)))) * (0.5F)) * (var3)) * (0.5F)) / (var7);
		this.attackTime = var1.attackAnim;
		if ((this.attackTime) > (0F)){
			this.body.yRot = (Mth.sin((Mth.sqrt(this.attackTime)) * (6.2831855F))) * (0.2F);
			this.rightArm.z = (Mth.sin(this.body.yRot)) * (5F);
			this.rightArm.x = (-(Mth.cos(this.body.yRot))) * (8F);
			this.leftArm.z = (-(Mth.sin(this.body.yRot))) * (5F);
			this.leftArm.x = (Mth.cos(this.body.yRot)) * (5F);
			ModelPart var8 = this.rightArm;
			var8.yRot = (var8.yRot) + (this.body.yRot);
			ModelPart var9 = this.leftArm;
			var9.yRot = (var9.yRot) + (this.body.yRot);
			ModelPart var10 = this.leftArm;
			var10.xRot = (var10.xRot) + (this.body.yRot);
			float var11 = (1F) - (this.attackTime);
			var11 = (var11) * (var11);
			var11 = (var11) * (var11);
			var11 = (1F) - (var11);
			float var12 = Mth.sin((var11) * (3.1415927F));
			float var13 = (Mth.sin((this.attackTime) * (3.1415927F))) * (0.75F);
			this.rightArm.xRot = (float)(((double)(this.rightArm.xRot)) - ((((double)(var12)) * (1.5D)) + ((double)(var13))));
			ModelPart var14 = this.rightArm;
			var14.yRot = (var14.yRot) + ((this.body.yRot) * (5F));
			ModelPart var15 = this.rightArm;
			var15.zRot = (var15.zRot) + ((Mth.sin((this.attackTime) * (3.1415927F))) * (-0.9F));
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.body.render(var1,var2,var3,var4,var5);
		this.leftArm.render(var1,var2,var3,var4,var5);
		this.rightArm.render(var1,var2,var3,var4,var5);
	}
	public boolean renderFirstPersonArm(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5, HumanoidArm var6, boolean var7) {
		float var8 = ((var6) == (HumanoidArm.RIGHT)) ? (1F) : (-1F);
		var1.translate(0D,-0.2D,0.4D);
		var1.mulPose(Axis.ZP.rotationDegrees((-90F) * (var8)));
		return super.renderFirstPersonArm(var1,var2,var3,var4,var5,var6,var7);
	}
}