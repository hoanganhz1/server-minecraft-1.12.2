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

public class MH5GasMaskModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","mh5_gas_mask"),"main");
	private final ModelPart mask;
	private final ModelPart leftEar;
	private final ModelPart leftEarConnector;
	private final ModelPart eyes;
	private final ModelPart mouthFilter;
	private final ModelPart rightEar;
	private final ModelPart rightEarConnector;

	public MH5GasMaskModel(ModelPart var1) {
		super(var1);
		this.mask = var1.getChild("mask");
		this.leftEar = this.mask.getChild("leftEar");
		this.leftEarConnector = this.leftEar.getChild("leftEarConnector");
		this.eyes = this.mask.getChild("eyes");
		this.mouthFilter = this.mask.getChild("mouthFilter");
		this.rightEar = this.mask.getChild("rightEar");
		this.rightEarConnector = this.rightEar.getChild("rightEarConnector");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("mask",CubeListBuilder.create().texOffs(0,12).addBox(-1.5F,-3F,-4.5F,3F,3F,1F,new CubeDeformation(0.5F)),PartPose.offset(0F,0F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("leftEar",CubeListBuilder.create().texOffs(17,10).addBox(2.8F,-5.6F,-1.8F,2F,2F,4F,new CubeDeformation(0.5F)),PartPose.offset(0F,0F,0F)).addOrReplaceChild("leftEarConnector",CubeListBuilder.create().texOffs(8,10).addBox(3.5F,-5.4F,-4.4F,1F,1F,3F,new CubeDeformation(0.5F)),PartPose.offset(0F,0F,0F));
		PartDefinition var3 = var1.addOrReplaceChild("eyes",CubeListBuilder.create().texOffs(0,0).addBox(-3F,-5.9F,-5F,6F,3F,2F,new CubeDeformation(0.5F)),PartPose.offset(0F,0F,0F));
		PartDefinition var4 = var1.addOrReplaceChild("mouthFilter",CubeListBuilder.create().texOffs(0,8).addBox(-1F,-2.5F,-5.4F,2F,2F,2F,new CubeDeformation(0.5F)),PartPose.offset(0F,0F,0F));
		PartDefinition var5 = var1.addOrReplaceChild("rightEar",CubeListBuilder.create().texOffs(17,10).addBox(-5.2F,-5.6F,-1.8F,2F,2F,4F,new CubeDeformation(0.5F)),PartPose.offset(0F,0F,0F)).addOrReplaceChild("rightEarConnector",CubeListBuilder.create().texOffs(8,10).addBox(-4.5F,-5.4F,-4.4F,1F,1F,3F,new CubeDeformation(0.5F)),PartPose.offset(0F,0F,0F));
		return LayerDefinition.create(var0,32,16);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.mask.copyFrom(this.head);
		this.mask.render(var1,var2,var3,var4,var5);
	}
}