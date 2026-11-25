package xyz.pixelatedw.mineminenomi.models.entities;

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

public class CandleLockModel<T extends LivingEntity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","candle_lock"),"main");
	private final ModelPart candleLock1;
	private final ModelPart candleLock3;
	private final ModelPart candleLock2;

	public CandleLockModel(ModelPart var1) {
		this.candleLock1 = var1.getChild("candleLock1");
		this.candleLock3 = this.candleLock1.getChild("candleLock3");
		this.candleLock2 = this.candleLock1.getChild("candleLock2");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("candleLock1",CubeListBuilder.create().texOffs(0,0).addBox(-7.5F,-3.5F,-3.5F,15F,7F,7F,new CubeDeformation(0F)),PartPose.offset(0F,18.9F,0F));
		var1.addOrReplaceChild("candleLock3",CubeListBuilder.create().texOffs(0,17).addBox(7.5F,-4F,-4F,4F,8F,8F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		var1.addOrReplaceChild("candleLock2",CubeListBuilder.create().texOffs(0,17).addBox(-11.5F,-4F,-4F,4F,8F,8F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(LivingEntity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.candleLock1.render(var1,var2,var3,var4,var5);
	}
}