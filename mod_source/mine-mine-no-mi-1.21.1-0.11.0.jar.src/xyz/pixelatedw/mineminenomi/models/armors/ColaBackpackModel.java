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

public class ColaBackpackModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","cola_backpack"),"main");
	private final ModelPart base;

	public ColaBackpackModel(ModelPart var1) {
		super(var1);
		this.base = var1.getChild("base");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("base",CubeListBuilder.create().texOffs(5,12).addBox(-0.5F,3F,2F,1F,2F,2F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("rightCola",CubeListBuilder.create().texOffs(0,0).addBox(-3.5F,1.5F,2F,3F,7F,3F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F)).addOrReplaceChild("rightColaCap",CubeListBuilder.create().texOffs(0,14).addBox(-2.5F,1F,3F,1F,1F,1F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		PartDefinition var3 = var1.addOrReplaceChild("leftCola",CubeListBuilder.create().texOffs(0,0).addBox(0.5F,1.5F,2F,3F,7F,3F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F)).addOrReplaceChild("leftColaCap",CubeListBuilder.create().texOffs(0,14).addBox(1.5F,1F,3F,1F,1F,1F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		return LayerDefinition.create(var0,16,16);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.base.copyFrom(this.body);
		this.base.render(var1,var2,var3,var4,var5);
	}
}