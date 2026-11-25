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

public class AxolotlWalkModel<T extends LivingEntity> extends QuadrupedMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","axolotl_walk"),"main");
	private final ModelPart top_gills;
	private final ModelPart left_gills;
	private final ModelPart right_gills;
	private final ModelPart tail;

	public AxolotlWalkModel(ModelPart var1) {
		super(var1);
		this.top_gills = this.head.getChild("top_gills");
		this.left_gills = this.head.getChild("left_gills");
		this.right_gills = this.head.getChild("right_gills");
		this.tail = var1.getChild("tail");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = QuadrupedModel.createBodyMesh(0,CubeDeformation.NONE);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,1).addBox(-4F,-3F,-5F,8F,5F,5F,new CubeDeformation(0F)),PartPose.offset(0F,18F,-5F));
		var2.addOrReplaceChild("top_gills",CubeListBuilder.create().texOffs(3,37).addBox(-4F,-3F,0F,8F,3F,0F,new CubeDeformation(0F)),PartPose.offset(0F,-3F,-1F));
		var2.addOrReplaceChild("left_gills",CubeListBuilder.create().texOffs(0,40).addBox(-11F,-5F,0F,3F,7F,0F,new CubeDeformation(0F)),PartPose.offset(4F,0F,-1F));
		var2.addOrReplaceChild("right_gills",CubeListBuilder.create().texOffs(11,40).addBox(8F,-5F,0F,3F,7F,0F,new CubeDeformation(0F)),PartPose.offset(-4F,0F,-1F));
		var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,11).addBox(-4F,-2F,-9F,8F,4F,10F,new CubeDeformation(0F)).texOffs(2,17).addBox(0F,-3F,-8F,0F,5F,9F,new CubeDeformation(0F)),PartPose.offset(0F,18F,4F));
		var1.addOrReplaceChild("left_front_leg",CubeListBuilder.create().texOffs(2,13).addBox(6F,0F,0F,3F,5F,0F,new CubeDeformation(0F)),PartPose.offset(-3.5F,19F,-4F));
		var1.addOrReplaceChild("left_hind_leg",CubeListBuilder.create().texOffs(2,13).addBox(6F,0F,0F,3F,5F,0F,new CubeDeformation(0F)),PartPose.offset(-3.5F,19F,3F));
		var1.addOrReplaceChild("right_front_leg",CubeListBuilder.create().texOffs(2,13).addBox(-9F,0F,0F,3F,5F,0F,new CubeDeformation(0F)),PartPose.offset(3.5F,19F,-4F));
		var1.addOrReplaceChild("right_hind_leg",CubeListBuilder.create().texOffs(2,13).addBox(-9F,0F,0F,3F,5F,0F,new CubeDeformation(0F)),PartPose.offset(3.5F,19F,3F));
		var1.addOrReplaceChild("tail",CubeListBuilder.create().texOffs(2,19).addBox(0F,-3F,0F,0F,5F,12F,new CubeDeformation(0F)),PartPose.offset(0F,18F,5F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(LivingEntity var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		int var7 = ((var1.getFallFlyingTicks()) > (4)) ? (1) : (0);
		boolean var8 = var1.isVisuallySwimming();
		this.head.yRot = (var5) * (0.017453292F);
		if ((var7) != (0)){
			this.head.xRot = -0.7853982F;
		}else{
			if ((this.swimAmount) > (0F)){
				if (var8){
					this.head.xRot = this.rotlerpRad(this.head.xRot,-0.7853982F,this.swimAmount);
				}else{
					this.head.xRot = this.rotlerpRad(this.head.xRot,(var6) * (0.017453292F),this.swimAmount);
				}
			}else{
				this.head.xRot = (var6) * (0.017453292F);
				if (((double)(this.head.xRot)) > (0.4D)){
					this.head.xRot = 0.4F;
				}else{
					if (((double)(this.head.xRot)) < (-0.4D)){
						this.head.xRot = -0.4F;
					}
				}
			}
		}
		float var9 = 1F;
		this.rightFrontLeg.xRot = ((((Mth.cos(((var2) * (0.6662F)) + (3.1415927F))) * (0.8F)) * (var3)) * (0.5F)) / (var9);
		this.rightHindLeg.xRot = ((((Mth.cos(((var2) * (0.6662F)) + (3.1415927F))) * (0.8F)) * (var3)) * (0.5F)) / (var9);
		this.leftFrontLeg.xRot = (((Mth.cos((var2) * (0.6662F))) * (0.7F)) * (var3)) / (var9);
		this.leftHindLeg.xRot = (((Mth.cos((var2) * (0.6662F))) * (0.7F)) * (var3)) / (var9);
		this.tail.yRot = ((float)(Math.sin(((double)(var4)) * (0.06D)))) / (10F);
		this.tail.xRot = ((float)(Math.sin(((double)(var4)) * (0.02D)))) / (10F);
		this.attackTime = var1.attackAnim;
		if ((this.attackTime) > (0F)){
			ModelPart var10 = this.head;
			var10.yRot = (var10.yRot) + (this.body.yRot);
			float var11 = (1F) - (this.attackTime);
			var11 = (var11) * (var11);
			var11 = (var11) * (var11);
			var11 = (1F) - (var11);
			float var12 = Mth.sin((var11) * (3.1415927F));
			float var13 = ((Mth.sin((this.attackTime) * (3.1415927F))) * (-((this.head.xRot) - (0.1F)))) * (0.15F);
			this.head.xRot = (float)(((double)(this.head.xRot)) - ((((double)(var12)) * (1.5D)) + ((double)(var13))));
			this.head.zRot = (Mth.sin((this.attackTime) * (3.1415927F))) * (-0.4F);
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		super.renderToBuffer(var1,var2,var3,var4,var5);
		this.tail.render(var1,var2,var3,var4,var5);
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		this.head.translateAndRotate(var2);
		var2.mulPose(Axis.ZP.rotationDegrees(90F));
		var2.mulPose(Axis.XP.rotationDegrees(260F));
		var2.translate(0.1D,-0.2D,-0.03D);
	}
	public ModelPart getHead() {
		return this.head;
	}
}