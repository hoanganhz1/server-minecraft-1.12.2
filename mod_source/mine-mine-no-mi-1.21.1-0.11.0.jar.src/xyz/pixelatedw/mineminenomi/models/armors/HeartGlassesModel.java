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

public class HeartGlassesModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","heart_glasses"),"main");
	private final ModelPart glasses;
	private final ModelPart leftLens;
	private final ModelPart rightLens;

	public HeartGlassesModel(ModelPart var1) {
		super(var1);
		this.glasses = var1.getChild("glasses");
		this.leftLens = this.glasses.getChild("leftLens");
		this.rightLens = this.glasses.getChild("rightLens");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("glasses",CubeListBuilder.create().texOffs(0,-4).addBox(-4.25F,-5F,-4.5F,0F,1F,6F,new CubeDeformation(0F)).texOffs(0,-4).mirror().addBox(4.25F,-5F,-4.5F,0F,1F,6F,new CubeDeformation(0F)).mirror(false).texOffs(0,0).addBox(-5F,-5F,-4.5F,10F,1F,0F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("leftLens",CubeListBuilder.create().texOffs(0,4).addBox(-1.341F,0F,0F,2F,1F,1F,new CubeDeformation(0.1F)).texOffs(6,4).addBox(-0.341F,-1F,0F,1F,1F,1F,new CubeDeformation(0.1F)),PartPose.offsetAndRotation(-1.659F,-4.7374F,-5F,0F,0F,0.7854F));
		PartDefinition var3 = var1.addOrReplaceChild("rightLens",CubeListBuilder.create().texOffs(0,4).addBox(-1.341F,0F,0F,2F,1F,1F,new CubeDeformation(0.1F)).texOffs(6,4).addBox(-0.341F,-1F,0F,1F,1F,1F,new CubeDeformation(0.1F)),PartPose.offsetAndRotation(2.091F,-4.7374F,-5F,0F,0F,0.7854F));
		return LayerDefinition.create(var0,32,32);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.glasses.copyFrom(this.head);
		this.glasses.render(var1,var2,var3,var4,var5);
	}
}