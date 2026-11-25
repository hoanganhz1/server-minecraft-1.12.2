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

public class MiniHollowModel<T extends Entity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","mini_hollow"),"main");
	private final ModelPart mini;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart rightarm;
	private final ModelPart leftarm;
	private final ModelPart rightleg;
	private final ModelPart leftleg;

	public MiniHollowModel(ModelPart var1) {
		this.mini = var1.getChild("mini");
		this.head = this.mini.getChild("head");
		this.body = this.mini.getChild("body");
		this.rightarm = this.mini.getChild("rightarm");
		this.leftarm = this.mini.getChild("leftarm");
		this.rightleg = this.mini.getChild("rightleg");
		this.leftleg = this.mini.getChild("leftleg");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("mini",CubeListBuilder.create(),PartPose.offsetAndRotation(0F,2F,0F,1.5708F,0F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).addBox(-1F,-1F,-1F,2F,2F,2F,new CubeDeformation(0F)),PartPose.offset(0F,-2F,0F));
		PartDefinition var3 = var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(9,0).addBox(-0.5F,-1F,-0.5F,1F,2F,1F,new CubeDeformation(0.01F)),PartPose.offset(0F,0F,0F));
		PartDefinition var4 = var1.addOrReplaceChild("rightarm",CubeListBuilder.create().texOffs(0,5).addBox(-0.5F,-1F,-0.5F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-1.0151F,-0.3077F,0F,0F,0F,0.9425F));
		PartDefinition var5 = var1.addOrReplaceChild("leftarm",CubeListBuilder.create().texOffs(0,5).addBox(-0.5F,-1F,-0.5F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(1.1029F,-0.3167F,0F,0F,0F,-0.9425F));
		PartDefinition var6 = var1.addOrReplaceChild("rightleg",CubeListBuilder.create().texOffs(6,5).addBox(-0.5F,-1F,-0.5F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.564F,1.6059F,0F,0F,0F,0.6109F));
		PartDefinition var7 = var1.addOrReplaceChild("leftleg",CubeListBuilder.create().texOffs(6,5).addBox(-0.5F,-1F,-0.5F,1F,2F,1F,new CubeDeformation(0.001F)),PartPose.offsetAndRotation(0.5831F,1.6324F,0F,0F,0F,-0.6109F));
		return LayerDefinition.create(var0,16,16);
	}
	public void setupAnim(Entity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.mini.render(var1,var2,var3,var4,var5);
	}
}