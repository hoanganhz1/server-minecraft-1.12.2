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

public class SogekingMaskModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","sogeking_mask"),"main");
	private final ModelPart mask;
	private final ModelPart leftPanel;
	private final ModelPart rightPanel;
	private final ModelPart topPanel;

	public SogekingMaskModel(ModelPart var1) {
		super(var1);
		this.mask = var1.getChild("mask");
		this.leftPanel = this.mask.getChild("leftPanel");
		this.rightPanel = this.mask.getChild("rightPanel");
		this.topPanel = this.mask.getChild("topPanel");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("mask",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-8F,-4F,8F,8F,8F,new CubeDeformation(0.55F)),PartPose.offset(0F,0F,0F));
		var1.addOrReplaceChild("leftPanel",CubeListBuilder.create().texOffs(0,17).addBox(-0.5F,-4F,0F,9F,8F,0F,new CubeDeformation(0F)),PartPose.offsetAndRotation(4.5F,-4F,-4.25F,0F,-0.1745F,0F));
		var1.addOrReplaceChild("rightPanel",CubeListBuilder.create().texOffs(0,17).mirror().addBox(-8.5F,-4F,0F,9F,8F,0F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-4.5F,-4F,-4.25F,0F,0.1745F,0F));
		var1.addOrReplaceChild("topPanel",CubeListBuilder.create().texOffs(19,17).mirror().addBox(-4F,-8.5F,0F,8F,9F,0F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0F,-8.5F,-4.25F,-0.1745F,0F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.mask.copyFrom(this.head);
		this.mask.render(var1,var2,var3,var4,var5);
	}
}