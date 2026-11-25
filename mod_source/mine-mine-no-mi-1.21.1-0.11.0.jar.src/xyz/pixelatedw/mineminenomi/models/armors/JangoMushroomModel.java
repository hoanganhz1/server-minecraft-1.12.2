package xyz.pixelatedw.mineminenomi.models.armors;

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

public class JangoMushroomModel<T extends LivingEntity> extends HumanoidModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","jango_mushroom"),"main");
	private final ModelPart hair;
	private final ModelPart hair2;

	public JangoMushroomModel(ModelPart var1) {
		super(var1);
		this.hair = var1.getChild("hair");
		this.hair2 = this.hair.getChild("hair2");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		var0.getRoot().addOrReplaceChild("hair",CubeListBuilder.create(),PartPose.offset(0F,0F,0F)).addOrReplaceChild("hair2",CubeListBuilder.create().texOffs(0,0).addBox(-1F,-1F,-1F,2F,4F,2F,new CubeDeformation(-0.2F)).texOffs(0,0).addBox(-1F,2.5F,-1F,2F,1F,2F,new CubeDeformation(-0.3F)),PartPose.offsetAndRotation(0F,0F,-3.5F,-0.4363F,0F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.hair.copyFrom(this.head);
		this.hair.render(var1,var2,var3,var4,var5);
	}
}