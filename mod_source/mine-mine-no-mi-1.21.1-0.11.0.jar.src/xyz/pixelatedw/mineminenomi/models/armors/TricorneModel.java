package xyz.pixelatedw.mineminenomi.models.armors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidArmorModel;
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
import net.minecraft.world.entity.LivingEntity;

public class TricorneModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","tricorne"),"main");
	private final ModelPart tricorne;
	private final ModelPart side1;
	private final ModelPart side12;
	private final ModelPart side2;
	private final ModelPart side22;
	private final ModelPart side3;
	private final ModelPart side32;
	private final ModelPart mid;
	private final ModelPart bone;
	private final ModelPart filling1;
	private final ModelPart filling2;

	public TricorneModel(ModelPart var1) {
		super(var1);
		this.tricorne = var1.getChild("tricorne");
		this.side1 = this.tricorne.getChild("side1");
		this.side12 = this.side1.getChild("side12");
		this.side2 = this.tricorne.getChild("side2");
		this.side22 = this.side2.getChild("side22");
		this.side3 = this.tricorne.getChild("side3");
		this.side32 = this.side3.getChild("side32");
		this.mid = this.tricorne.getChild("mid");
		this.bone = this.mid.getChild("bone");
		this.filling1 = this.tricorne.getChild("filling1");
		this.filling2 = this.tricorne.getChild("filling2");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("tricorne",CubeListBuilder.create(),PartPose.offset(0F,0F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("side1",CubeListBuilder.create().texOffs(21,0).addBox(-0.2997F,-0.5F,-10.6117F,1F,1F,20F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-5.8303F,-5.5F,-3.1883F,0F,-0.6109F,0F)).addOrReplaceChild("side12",CubeListBuilder.create().texOffs(0,35).addBox(0F,-2.4F,-10.4F,0F,4F,20F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.0497F,-2F,-0.2117F,0F,0F,0.096F));
		PartDefinition var3 = var1.addOrReplaceChild("side2",CubeListBuilder.create().texOffs(21,0).addBox(-0.5F,-0.5F,-10.25F,1F,1F,20F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(5.8961F,-5.5F,-3.1225F,0F,0.6109F,0F)).addOrReplaceChild("side22",CubeListBuilder.create().texOffs(0,35).addBox(-0.5F,-3.4F,-10.5F,0F,4F,20F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.75F,-1F,0F,0F,0F,-0.1309F));
		PartDefinition var4 = var1.addOrReplaceChild("side3",CubeListBuilder.create().texOffs(0,22).addBox(-0.5F,-0.5F,-11F,1F,1F,22F,new CubeDeformation(-0.001F)),PartPose.offsetAndRotation(0.1F,-5.5F,4.75F,0F,-1.5708F,0F)).addOrReplaceChild("side32",CubeListBuilder.create().texOffs(0,26).addBox(0.5F,-3.4F,-11F,0F,4F,22F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.25F,-1F,0F,0F,0F,-0.1309F));
		PartDefinition var5 = var1.addOrReplaceChild("mid",CubeListBuilder.create().texOffs(44,0).addBox(-7.5F,-4.5F,-10.25F,10F,5F,10F,new CubeDeformation(-0.48F)),PartPose.offset(2.5F,-5.5F,5.15F)).addOrReplaceChild("bone",CubeListBuilder.create(),PartPose.offset(0F,0F,0F));
		PartDefinition var6 = var1.addOrReplaceChild("filling1",CubeListBuilder.create().texOffs(0,0).addBox(-3.75F,-0.5F,-4.25F,8F,1F,8F,new CubeDeformation(-0.05F)),PartPose.offsetAndRotation(-0.1889F,-5.5F,-5.0154F,0F,-0.7854F,0F));
		PartDefinition var7 = var1.addOrReplaceChild("filling2",CubeListBuilder.create().texOffs(26,23).addBox(-16F,-0.5F,-6.5F,21F,1F,9F,new CubeDeformation(-0.04F)),PartPose.offset(5.5611F,-5.5F,2.2346F));
		return LayerDefinition.create(var0,128,128);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.tricorne.copyFrom(this.head);
		this.tricorne.render(var1,var2,var3,var4,var5);
	}
}