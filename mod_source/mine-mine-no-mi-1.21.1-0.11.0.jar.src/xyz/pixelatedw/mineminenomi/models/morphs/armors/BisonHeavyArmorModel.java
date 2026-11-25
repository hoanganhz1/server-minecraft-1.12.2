package xyz.pixelatedw.mineminenomi.models.morphs.armors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

public class BisonHeavyArmorModel<T extends LivingEntity> extends MorphArmorModel<T> {
	// Fields
	public static final ModelLayerLocation INNER_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","bison_heavy_inner_armor"),"main");
	public static final ModelLayerLocation OUTER_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","bison_heavy_outer_armor"),"main");

	public BisonHeavyArmorModel(ModelPart var1) {
		super(var1);
	}
	public static LayerDefinition createLayer(CubeDeformation var0) {
		MeshDefinition var1 = MorphArmorModel.createBodyLayer(var0);
		PartDefinition var2 = var1.getRoot();
		var2.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(4,16).mirror().addBox(-2F,-1.0808F,-2.2595F,0F,6F,4F,new CubeDeformation(0.01F)).mirror(false).texOffs(0,16).mirror().addBox(2F,-1.0808F,-2.2595F,0F,6F,4F,new CubeDeformation(0.01F)).mirror(false).texOffs(4,20).addBox(-2F,-1.0808F,-2.2595F,4F,6F,0F,new CubeDeformation(0.01F)).texOffs(8,20).addBox(-2F,-1.0808F,1.7405F,4F,6F,0F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(-3F,12F,2F,-0.3491F,0F,0F));
		var2.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(4,16).addBox(2F,-1.0808F,-2.2595F,0F,6F,4F,new CubeDeformation(0.01F)).texOffs(0,16).addBox(-2F,-1.0808F,-2.2595F,0F,6F,4F,new CubeDeformation(0.01F)).texOffs(4,20).mirror().addBox(-2F,-1.0808F,-2.2595F,4F,6F,0F,new CubeDeformation(0.01F)).mirror(false).texOffs(8,20).mirror().addBox(-2F,-1.0808F,1.7405F,4F,6F,0F,new CubeDeformation(0.01F)).mirror(false),PartPose.offsetAndRotation(3F,12F,2F,-0.3491F,0F,0F));
		var2.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(40,16).mirror().addBox(0.25F,-0.5F,-2.5F,4F,8F,4F,var0.extend(0.6F)).mirror(false),PartPose.offsetAndRotation(4.75F,1F,1F,0.0873F,0F,-0.1309F));
		var2.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(40,16).addBox(-4.25F,-0.5F,-2.5F,4F,8F,4F,var0.extend(0.6F)),PartPose.offsetAndRotation(-4.75F,1F,1F,0.0873F,0F,0.1309F));
		var2.addOrReplaceChild("body",CubeListBuilder.create().texOffs(16,16).addBox(-4F,0.4457F,-2.5997F,8F,12F,4F,var0.extend(1.6F)),PartPose.offsetAndRotation(0F,10F,2F,0.0873F,0F,0F));
		var2.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-5.3304F,-6.2969F,8F,8F,8F,new CubeDeformation(-1F)),PartPose.offsetAndRotation(0F,0F,0F,0.1745F,0F,0F));
		return LayerDefinition.create(var1,64,32);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		super.renderToBuffer(var1,var2,var3,var4,var5);
	}
}