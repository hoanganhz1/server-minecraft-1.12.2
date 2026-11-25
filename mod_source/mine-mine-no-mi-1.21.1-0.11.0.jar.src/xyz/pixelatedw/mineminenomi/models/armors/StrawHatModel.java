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

public class StrawHatModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","straw_hat"),"main");
	private final ModelPart strawhat;

	public StrawHatModel(ModelPart var1) {
		super(var1);
		this.strawhat = var1.getChild("strawhat");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		var0.getRoot().addOrReplaceChild("strawhat",CubeListBuilder.create().texOffs(-17,47).addBox(-8.5F,-6F,-8.5F,17F,0F,17F,new CubeDeformation(0F)),PartPose.offset(0F,-0.275F,0F)).addOrReplaceChild("top",CubeListBuilder.create().texOffs(0,22).addBox(-5F,-34.155F,-4F,9F,3F,9F,new CubeDeformation(0.15F)),PartPose.offset(0.5F,25F,-0.5F)).addOrReplaceChild("band",CubeListBuilder.create().texOffs(0,36).addBox(-5F,-32.255F,-4F,9F,1F,9F,new CubeDeformation(0.25F)),PartPose.offset(0F,0F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.strawhat.copyFrom(this.head);
		this.strawhat.render(var1,var2,var3,var4,var5);
	}
}