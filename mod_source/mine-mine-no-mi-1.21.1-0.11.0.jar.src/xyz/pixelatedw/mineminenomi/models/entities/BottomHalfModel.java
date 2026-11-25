package xyz.pixelatedw.mineminenomi.models.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class BottomHalfModel<T extends LivingEntity> extends PlayerModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","bottom_half"),"main");

	public BottomHalfModel(ModelPart var1) {
		super(var1,false);
	}
	public static LayerDefinition createBodyLayer() {
		return LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE,false),64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		super.setupAnim(var1,var2,var3,var4,var5,var6);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.leftLeg.render(var1,var2,var3,var4,var5);
		this.leftPants.render(var1,var2,var3,var4,var5);
		this.rightLeg.render(var1,var2,var3,var4,var5);
		this.rightPants.render(var1,var2,var3,var4,var5);
	}
}