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

public class ChopperHatModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","chopper_hat"),"main");
	private final ModelPart base;
	private final ModelPart top;

	public ChopperHatModel(ModelPart var1) {
		super(var1);
		this.base = var1.getChild("base");
		this.top = this.base.getChild("top");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("base",CubeListBuilder.create().texOffs(0,18).addBox(-5.5F,-8F,-5.5F,11F,1F,11F,new CubeDeformation(0.5F)),PartPose.offset(0F,-1F,0F)).addOrReplaceChild("top",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-8F,-4F,8F,8F,8F,new CubeDeformation(0.5F)),PartPose.offset(0F,-8F,0F));
		return LayerDefinition.create(var0,64,32);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.base.copyFrom(this.head);
		this.base.render(var1,var2,var3,var4,var5);
	}
}