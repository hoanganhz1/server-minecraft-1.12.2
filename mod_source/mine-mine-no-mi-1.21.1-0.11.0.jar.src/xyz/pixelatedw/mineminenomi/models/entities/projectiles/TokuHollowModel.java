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

public class TokuHollowModel<T extends Entity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","toku_hollow"),"main");
	private final ModelPart head;
	private final ModelPart head2;
	private final ModelPart head3;
	private final ModelPart head4;
	private final ModelPart body;
	private final ModelPart rightarm;
	private final ModelPart leftarm;
	private final ModelPart rightleg;
	private final ModelPart leftleg;

	public TokuHollowModel(ModelPart var1) {
		this.head = var1.getChild("head");
		this.head2 = var1.getChild("head2");
		this.head3 = var1.getChild("head3");
		this.head4 = var1.getChild("head4");
		this.body = var1.getChild("body");
		this.rightarm = var1.getChild("rightarm");
		this.leftarm = var1.getChild("leftarm");
		this.rightleg = var1.getChild("rightleg");
		this.leftleg = var1.getChild("leftleg");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).addBox(0F,0F,0F,24F,24F,24F,new CubeDeformation(0F)),PartPose.offset(-12F,-14F,-5F));
		PartDefinition var3 = var1.addOrReplaceChild("head2",CubeListBuilder.create().texOffs(0,50).addBox(0F,0F,0F,22F,22F,25F,new CubeDeformation(0F)),PartPose.offset(-11F,-13F,-5.5F));
		PartDefinition var4 = var1.addOrReplaceChild("head3",CubeListBuilder.create().texOffs(98,50).addBox(0F,0F,0F,25F,22F,22F,new CubeDeformation(0F)),PartPose.offset(-12.5F,-13F,-4F));
		PartDefinition var5 = var1.addOrReplaceChild("head4",CubeListBuilder.create().texOffs(98,0).addBox(0F,0F,0F,22F,25F,22F,new CubeDeformation(0F)),PartPose.offset(-11F,-14.5F,-4F));
		PartDefinition var6 = var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(188,0).addBox(0F,0F,0F,8F,4F,9F,new CubeDeformation(0F)),PartPose.offset(-4F,-2F,19.5F));
		PartDefinition var7 = var1.addOrReplaceChild("rightarm",CubeListBuilder.create().texOffs(188,22).addBox(0F,0F,0F,3F,3F,12F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-17F,2F,-9F,0.1396F,0.4189F,0F));
		PartDefinition var8 = var1.addOrReplaceChild("leftarm",CubeListBuilder.create().texOffs(188,22).addBox(0F,0F,0F,3F,3F,12F,new CubeDeformation(0F)),PartPose.offsetAndRotation(14.2F,2F,-10F,0.1396F,-0.4189F,0F));
		PartDefinition var9 = var1.addOrReplaceChild("rightleg",CubeListBuilder.create().texOffs(188,14).addBox(0F,0F,0F,2F,2F,4F,new CubeDeformation(0F)),PartPose.offset(-3.25F,-1F,28.5F));
		PartDefinition var10 = var1.addOrReplaceChild("leftleg",CubeListBuilder.create().texOffs(188,14).addBox(0F,0F,0F,2F,2F,4F,new CubeDeformation(0F)),PartPose.offset(1.25F,-1F,28.5F));
		return LayerDefinition.create(var0,256,128);
	}
	public void setupAnim(Entity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.head.render(var1,var2,var3,var4,var5);
		this.head2.render(var1,var2,var3,var4,var5);
		this.head3.render(var1,var2,var3,var4,var5);
		this.head4.render(var1,var2,var3,var4,var5);
		this.body.render(var1,var2,var3,var4,var5);
		this.rightarm.render(var1,var2,var3,var4,var5);
		this.leftarm.render(var1,var2,var3,var4,var5);
		this.rightleg.render(var1,var2,var3,var4,var5);
		this.leftleg.render(var1,var2,var3,var4,var5);
	}
}