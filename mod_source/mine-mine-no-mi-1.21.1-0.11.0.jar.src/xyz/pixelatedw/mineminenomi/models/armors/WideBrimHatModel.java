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

public class WideBrimHatModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","wide_brim_hat"),"main");
	private final ModelPart wideBrimHat;
	private final ModelPart top;
	private final ModelPart leftSide;
	private final ModelPart rightSide;

	public WideBrimHatModel(ModelPart var1) {
		super(var1);
		this.wideBrimHat = var1.getChild("wideBrimHat");
		this.top = this.wideBrimHat.getChild("top");
		this.leftSide = this.wideBrimHat.getChild("leftSide");
		this.rightSide = this.wideBrimHat.getChild("rightSide");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("wideBrimHat",CubeListBuilder.create().texOffs(0,4).addBox(-8F,-6.5F,-8F,16F,1F,16F,new CubeDeformation(0F)).texOffs(0,4).addBox(-8F,-7.25F,-8F,16F,1F,16F,new CubeDeformation(-0.2F)),PartPose.offset(0F,0F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("top",CubeListBuilder.create().texOffs(0,35).addBox(-5F,-3F,-5F,10F,3F,10F,new CubeDeformation(0F)).texOffs(29,23).addBox(-5F,-0.5F,-5.5F,10F,1F,1F,new CubeDeformation(0F)).texOffs(29,23).addBox(-5F,-0.5F,4.5F,10F,1F,1F,new CubeDeformation(0F)).texOffs(32,27).addBox(4.5F,-0.5F,-5.5F,1F,1F,11F,new CubeDeformation(0.01F)).texOffs(32,27).addBox(-5.5F,-0.5F,-5.5F,1F,1F,11F,new CubeDeformation(0.01F)).texOffs(0,23).addBox(-4.5F,-3.5F,-4.5F,9F,1F,9F,new CubeDeformation(0F)),PartPose.offset(0F,-7F,0F));
		PartDefinition var3 = var1.addOrReplaceChild("leftSide",CubeListBuilder.create(),PartPose.offset(-5F,-5.75F,-1F));
		PartDefinition var4 = var1.addOrReplaceChild("rightSide",CubeListBuilder.create(),PartPose.offset(5F,-5.75F,-1F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(LivingEntity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.wideBrimHat.copyFrom(this.head);
		this.wideBrimHat.render(var1,var2,var3,var4,var5);
	}
}