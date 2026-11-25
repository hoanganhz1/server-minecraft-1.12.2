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

public class CelestialDragonBubbleModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","celestial_dragon_bubble"),"main");
	private final ModelPart bubble;

	public CelestialDragonBubbleModel(ModelPart var1) {
		super(var1);
		this.bubble = var1.getChild("bubble");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		var0.getRoot().addOrReplaceChild("bubble",CubeListBuilder.create().texOffs(0,0).addBox(-6F,-10F,-6F,12F,12F,12F,new CubeDeformation(0F)).texOffs(0,26).addBox(-2F,-9.5F,1.25F,4F,1F,2F,new CubeDeformation(0F)).texOffs(1,29).addBox(-1F,-8.5F,2F,2F,2F,1F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.bubble.copyFrom(this.head);
		this.bubble.render(var1,var2,var3,var4,var5);
	}
}