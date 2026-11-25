package xyz.pixelatedw.mineminenomi.models.entities.projectiles;

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
import net.minecraft.world.entity.Entity;

public class ArrowModel<T extends Entity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","arrow"),"main");
	private final ModelPart body;
	private final ModelPart tail1;
	private final ModelPart tail2;
	private final ModelPart head1;
	private final ModelPart head2;

	public ArrowModel(ModelPart var1) {
		this.body = var1.getChild("body");
		this.tail1 = this.body.getChild("tail1");
		this.tail2 = this.body.getChild("tail2");
		this.head1 = this.body.getChild("head1");
		this.head2 = this.head1.getChild("head2");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,0).addBox(-0.5F,-0.5F,-5F,1F,1F,10F,new CubeDeformation(0F)),PartPose.offset(0F,0.5F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("tail1",CubeListBuilder.create().texOffs(15,0).addBox(-5F,-1.7F,0.4F,4F,4F,0F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-0.5F,0F,0F,1.5708F,0.7854F));
		PartDefinition var3 = var1.addOrReplaceChild("tail2",CubeListBuilder.create().texOffs(15,5).addBox(-5F,-1.8F,-0.3F,4F,4F,0F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-0.5F,0F,0F,1.5708F,-0.7854F));
		PartDefinition var4 = var1.addOrReplaceChild("head1",CubeListBuilder.create().texOffs(0,0).addBox(-1F,-0.5F,-6F,2F,2F,1F,new CubeDeformation(0F)),PartPose.offset(0F,-0.5F,0F)).addOrReplaceChild("head2",CubeListBuilder.create().texOffs(0,4).addBox(-0.5F,0F,-7F,1F,1F,1F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		return LayerDefinition.create(var0,32,16);
	}
	public void setupAnim(Entity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.body.render(var1,var2,var3,var4,var5);
	}
}