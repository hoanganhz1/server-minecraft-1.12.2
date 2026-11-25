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

public class KameWalkModel<T extends LivingEntity> extends HumanoidModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","kame_walk"),"main");
	private final ModelPart shell;

	public KameWalkModel(ModelPart var1) {
		super(var1);
		this.shell = var1.getChild("shell");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		var0.getRoot().addOrReplaceChild("shell",CubeListBuilder.create().texOffs(0,18).addBox(-5.5F,-1F,1F,11F,3F,2F,new CubeDeformation(0F)).texOffs(0,13).addBox(-5.5F,10F,1F,11F,3F,2F,new CubeDeformation(0F)).texOffs(10,23).addBox(-5.5F,2F,1F,3F,8F,2F,new CubeDeformation(0F)).texOffs(0,23).addBox(2.5F,2F,1F,3F,8F,2F,new CubeDeformation(0F)).texOffs(0,0).addBox(-4.5F,0F,3F,9F,12F,1F,new CubeDeformation(0F)).texOffs(20,0).addBox(-3.5F,1F,4F,7F,10F,1F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		if (this.crouching){
			this.shell.xRot = 0.5F;
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.shell.copyFrom(this.body);
		this.shell.render(var1,var2,var3,var4,var5);
	}
}