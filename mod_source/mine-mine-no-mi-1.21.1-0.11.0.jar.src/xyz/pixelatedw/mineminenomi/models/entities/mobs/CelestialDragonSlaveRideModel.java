package xyz.pixelatedw.mineminenomi.models.entities.mobs;

import com.google.common.collect.ImmutableList;
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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;

public class CelestialDragonSlaveRideModel<T extends LivingEntity> extends HumanoidModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","celestial_dragon_slave_ride"),"main");
	private final ModelPart nose;
	private final ModelPart headwear;
	private final ModelPart headwear2;
	private final ModelPart bodywear;
	private final ModelPart arms;
	private final ModelPart mirrored;

	public CelestialDragonSlaveRideModel(ModelPart var1) {
		super(var1);
		this.nose = var1.getChild("nose");
		this.headwear = var1.getChild("headwear");
		this.headwear2 = var1.getChild("headwear2");
		this.bodywear = var1.getChild("bodywear");
		this.arms = var1.getChild("arms");
		this.mirrored = this.arms.getChild("mirrored");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		PartDefinition var1 = var0.getRoot();
		var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-10F,-4F,8F,10F,8F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,17F,-9F,0.2618F,0F,0F));
		var1.addOrReplaceChild("nose",CubeListBuilder.create().texOffs(24,0).addBox(-1F,-1F,-6F,2F,4F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,15F,-9.5F,0.2618F,0F,0F));
		var1.addOrReplaceChild("headwear",CubeListBuilder.create().texOffs(32,0).addBox(-4F,-10F,-4F,8F,10F,8F,new CubeDeformation(0.51F)),PartPose.offsetAndRotation(0F,17F,-9F,0.2618F,0F,0F));
		var1.addOrReplaceChild("headwear2",CubeListBuilder.create().texOffs(30,47).addBox(-8F,-8F,-6F,16F,16F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,17F,-9F,-1.309F,0F,0F));
		var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(16,20).addBox(-4F,0F,-3F,8F,12F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,17F,-7F,1.5708F,0F,0F));
		var1.addOrReplaceChild("bodywear",CubeListBuilder.create().texOffs(0,38).addBox(-4F,0F,-3F,8F,20F,6F,new CubeDeformation(0.5F)),PartPose.offsetAndRotation(0F,17F,-7F,1.5708F,0F,0F));
		var1.addOrReplaceChild("arms",CubeListBuilder.create().texOffs(40,38).addBox(-4F,2F,-2F,8F,4F,4F,new CubeDeformation(0F)).texOffs(44,22).addBox(-8F,-2F,-2F,4F,8F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,18.95F,-7.05F,-0.7505F,0F,0F)).addOrReplaceChild("mirrored",CubeListBuilder.create().texOffs(44,22).mirror().addBox(4F,-23.05F,-3.05F,4F,8F,4F,new CubeDeformation(0F)).mirror(false),PartPose.offset(0F,21.05F,1.05F));
		var1.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(0,22).addBox(-2F,0F,-2F,4F,12F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-3F,22F,1F,1.5708F,0F,0F));
		var1.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(0,22).addBox(-2F,0F,-2F,4F,12F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(3F,22F,1F,1.5708F,0F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(LivingEntity var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		var2 = (var2) * (2F);
		var3 = (var3) * (2F);
		this.arms.z = (-7F) + (((Mth.cos(((var2) * (0.5F)) + (3.1415927F))) * (1.6F)) * (var3));
		this.rightLeg.z = ((Mth.cos(((var2) * (0.96F)) + (3.1415927F))) * (2.6F)) * (var3);
		this.leftLeg.z = ((Mth.cos((var2) * (0.96F))) * (2.6F)) * (var3);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.head.render(var1,var2,var3,var4,var5);
		this.nose.render(var1,var2,var3,var4,var5);
		this.headwear.render(var1,var2,var3,var4,var5);
		this.headwear2.render(var1,var2,var3,var4,var5);
		this.body.render(var1,var2,var3,var4,var5);
		this.bodywear.render(var1,var2,var3,var4,var5);
		this.arms.render(var1,var2,var3,var4,var5);
		this.rightLeg.render(var1,var2,var3,var4,var5);
		this.leftLeg.render(var1,var2,var3,var4,var5);
	}
	public Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.head,this.nose,this.headwear,this.headwear2);
	}
	public Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body,this.bodywear,this.arms,this.rightLeg,this.leftLeg);
	}
}