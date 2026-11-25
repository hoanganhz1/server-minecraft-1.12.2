package xyz.pixelatedw.mineminenomi.models.entities.mobs.hair;

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

public class Mr3HairModel<T extends LivingEntity> extends HumanoidModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","mr3_hair"),"main");
	private final ModelPart hair;

	public Mr3HairModel(ModelPart var1) {
		super(var1);
		this.hair = var1.getChild("hair");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		var0.getRoot().addOrReplaceChild("hair",CubeListBuilder.create().texOffs(0,2).addBox(-1F,-9F,0F,3F,1F,1F,new CubeDeformation(0F)).texOffs(0,0).addBox(-2F,-17F,0F,4F,1F,1F,new CubeDeformation(0F)).texOffs(7,3).addBox(0F,-13F,0F,2F,1F,1F,new CubeDeformation(0F)).texOffs(9,1).addBox(-1F,-13F,0F,1F,1F,1F,new CubeDeformation(0.2F)).texOffs(4,4).addBox(2F,-12.25F,0F,1F,4F,1F,new CubeDeformation(0F)).texOffs(0,4).addBox(2F,-16.75F,0F,1F,4F,1F,new CubeDeformation(0F)).texOffs(8,5).addBox(-3F,-9.25F,0F,2F,1F,1F,new CubeDeformation(0F)).texOffs(0,9).addBox(-4F,-9.5F,0F,1F,1F,1F,new CubeDeformation(0F)).texOffs(7,8).addBox(-3F,-16.5F,0F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		return LayerDefinition.create(var0,16,16);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		super.setupAnim(var1,var2,var3,var4,var5,var6);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.hair.copyFrom(this.head);
		this.hair.render(var1,var2,var3,var4,var5);
	}
}