package xyz.pixelatedw.mineminenomi.models.morphs.partials;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

public class FishmanGarfishModel<T extends LivingEntity> extends HumanoidModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","fishman_garfish"),"main");
	private final ModelPart lips;

	public FishmanGarfishModel(ModelPart var1) {
		super(var1);
		this.lips = this.head.getChild("lips");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		var0.getRoot().addOrReplaceChild("head",CubeListBuilder.create(),PartPose.offset(0F,0F,0F)).addOrReplaceChild("lips",CubeListBuilder.create().texOffs(0,0).addBox(-0.5F,-0.75F,-2F,1F,1F,2F,new CubeDeformation(0F)).texOffs(0,4).addBox(-1.5F,-2.05F,-2.001F,3F,3F,0F,new CubeDeformation(0F)),PartPose.offset(0F,-1.5F,-4F));
		return LayerDefinition.create(var0,8,8);
	}
	public void setupAnim(LivingEntity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.head.render(var1,var2,var3,var4,var5);
	}
}