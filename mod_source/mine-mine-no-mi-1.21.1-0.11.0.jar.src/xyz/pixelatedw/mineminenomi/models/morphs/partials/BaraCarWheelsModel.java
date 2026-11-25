package xyz.pixelatedw.mineminenomi.models.morphs.partials;

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
import net.minecraft.world.entity.LivingEntity;

public class BaraCarWheelsModel<T extends LivingEntity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","bara_car_wheels"),"main");
	private final ModelPart frontWheels;
	private final ModelPart frontRightWheel;
	private final ModelPart frontLeftWheel;
	private final ModelPart backWheels;
	private final ModelPart backLeftWheel;
	private final ModelPart backRightWheel;

	public BaraCarWheelsModel(ModelPart var1) {
		this.frontWheels = var1.getChild("frontWheels");
		this.frontRightWheel = this.frontWheels.getChild("frontRightWheel");
		this.frontLeftWheel = this.frontWheels.getChild("frontLeftWheel");
		this.backWheels = var1.getChild("backWheels");
		this.backLeftWheel = this.backWheels.getChild("backLeftWheel");
		this.backRightWheel = this.backWheels.getChild("backRightWheel");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("frontWheels",CubeListBuilder.create().texOffs(1,11).addBox(-13F,-1F,-1F,26F,2F,2F,new CubeDeformation(0F)).texOffs(0,10).addBox(-13F,-0.5F,-1.5F,26F,1F,3F,new CubeDeformation(0F)).texOffs(2,11).addBox(-13F,-1.5F,-0.5F,26F,3F,1F,new CubeDeformation(0F)),PartPose.offset(0F,23F,-16F));
		var2.addOrReplaceChild("frontRightWheel",CubeListBuilder.create().texOffs(1,47).addBox(-3F,-4F,-4F,3F,8F,8F,new CubeDeformation(0F)).texOffs(2,48).addBox(-3F,-4.5F,-3.5F,3F,9F,7F,new CubeDeformation(-0.1F)).texOffs(0,46).addBox(-3F,-3.5F,-4.5F,3F,7F,9F,new CubeDeformation(-0.1F)),PartPose.offset(-13F,0F,0F));
		var2.addOrReplaceChild("frontLeftWheel",CubeListBuilder.create().texOffs(1,47).addBox(0F,-4F,-4F,3F,8F,8F,new CubeDeformation(0F)).texOffs(2,48).addBox(0F,-4.5F,-3.5F,3F,9F,7F,new CubeDeformation(-0.1F)).texOffs(0,46).addBox(0F,-3.5F,-4.5F,3F,7F,9F,new CubeDeformation(-0.1F)),PartPose.offset(13F,0F,0F));
		PartDefinition var3 = var1.addOrReplaceChild("backWheels",CubeListBuilder.create().texOffs(2,1).addBox(-21F,-2.25F,-2.25F,26F,5F,5F,new CubeDeformation(0F)).texOffs(0,0).addBox(-21F,-1.75F,-2.75F,26F,4F,6F,new CubeDeformation(0F)).texOffs(4,2).addBox(-21F,-2.75F,-1.75F,26F,6F,4F,new CubeDeformation(0F)),PartPose.offset(8F,20.75F,14.25F));
		var3.addOrReplaceChild("backLeftWheel",CubeListBuilder.create().texOffs(0,18).addBox(0F,-7.75F,-7.25F,6F,15F,15F,new CubeDeformation(0F)).texOffs(1,19).addBox(0F,-8.75F,-6.75F,6F,17F,14F,new CubeDeformation(-0.1F)).texOffs(0,18).addBox(0F,-7.25F,-8.25F,6F,14F,17F,new CubeDeformation(-0.1F)),PartPose.offset(5F,0F,0F));
		var3.addOrReplaceChild("backRightWheel",CubeListBuilder.create().texOffs(0,18).addBox(-6F,-7.75F,-7.25F,6F,15F,15F,new CubeDeformation(0F)).texOffs(1,19).addBox(-6F,-8.75F,-6.75F,6F,17F,14F,new CubeDeformation(-0.1F)).texOffs(0,17).addBox(-6F,-7.25F,-8.25F,6F,14F,17F,new CubeDeformation(-0.1F)),PartPose.offset(-21F,0F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		this.frontRightWheel.xRot = (var2) * (0.8F);
		this.frontLeftWheel.xRot = (var2) * (0.8F);
		this.backRightWheel.xRot = (var2) * (0.8F);
		this.backLeftWheel.xRot = (var2) * (0.8F);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.frontWheels.render(var1,var2,var3,var4,var5);
		this.backWheels.render(var1,var2,var3,var4,var5);
	}
}