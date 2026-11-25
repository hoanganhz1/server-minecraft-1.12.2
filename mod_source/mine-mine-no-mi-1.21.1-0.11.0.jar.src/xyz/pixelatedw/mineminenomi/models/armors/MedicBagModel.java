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

public class MedicBagModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","medic_bag"),"main");
	private final ModelPart backpack;
	private final ModelPart backpack_2;
	private final ModelPart backpack_strings;

	public MedicBagModel(ModelPart var1) {
		super(var1);
		this.backpack = var1.getChild("backpack");
		this.backpack_2 = this.backpack.getChild("backpack_2");
		this.backpack_strings = this.backpack.getChild("backpack_strings");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("backpack",CubeListBuilder.create().texOffs(0,0).addBox(-2.5F,1.5F,2F,5F,5F,3F,new CubeDeformation(0F)),PartPose.offset(0F,3.7F,2F));
		PartDefinition var2 = var1.addOrReplaceChild("backpack_2",CubeListBuilder.create().texOffs(0,9).addBox(-3.5F,0.5F,0F,7F,4F,4F,new CubeDeformation(0F)),PartPose.offset(0F,3.7F,2F));
		PartDefinition var3 = var1.addOrReplaceChild("backpack_strings",CubeListBuilder.create().texOffs(20,0).addBox(-5F,-4F,0.1F,10F,9F,0F,new CubeDeformation(0F)),PartPose.offset(0F,3.7F,2F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.backpack.copyFrom(this.body);
		this.backpack.render(var1,var2,var3,var4,var5);
	}
}