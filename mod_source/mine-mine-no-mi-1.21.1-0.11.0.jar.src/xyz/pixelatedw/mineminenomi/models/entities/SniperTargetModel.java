package xyz.pixelatedw.mineminenomi.models.entities;

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
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class SniperTargetModel<T extends Entity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","sniper_target"),"main");
	private final ModelPart target;
	private final ModelPart parachute;
	private final ModelPart parachute_cloth8;
	private final ModelPart parachute_cloth2;
	private final ModelPart parachute_cloth7;
	private final ModelPart parachute_cord1;
	private final ModelPart parachute_cloth3;
	private final ModelPart parachute_cloth6;
	private final ModelPart parachute_cord2;
	private final ModelPart parachute_cord3;
	private final ModelPart parachute_cloth1;
	private final ModelPart parachute_cloth4;
	private final ModelPart parachute_cord4;
	private final ModelPart parachute_cloth5;

	public SniperTargetModel(EntityRendererProvider.Context var1) {
		ModelPart var2 = var1.bakeLayer(LAYER_LOCATION);
		this.target = var2.getChild("target");
		this.parachute = var2.getChild("parachute");
		this.parachute_cloth8 = this.parachute.getChild("parachute_cloth8");
		this.parachute_cloth2 = this.parachute.getChild("parachute_cloth2");
		this.parachute_cloth7 = this.parachute.getChild("parachute_cloth7");
		this.parachute_cord1 = this.parachute.getChild("parachute_cord1");
		this.parachute_cloth3 = this.parachute.getChild("parachute_cloth3");
		this.parachute_cloth6 = this.parachute.getChild("parachute_cloth6");
		this.parachute_cord2 = this.parachute.getChild("parachute_cord2");
		this.parachute_cord3 = this.parachute.getChild("parachute_cord3");
		this.parachute_cloth1 = this.parachute.getChild("parachute_cloth1");
		this.parachute_cloth4 = this.parachute.getChild("parachute_cloth4");
		this.parachute_cord4 = this.parachute.getChild("parachute_cord4");
		this.parachute_cloth5 = this.parachute.getChild("parachute_cloth5");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("target",CubeListBuilder.create().texOffs(0,0).addBox(-4F,0F,-0.5F,8F,8F,1F,new CubeDeformation(0F)),PartPose.offset(0F,20F,0F));
		PartDefinition var3 = var1.addOrReplaceChild("parachute",CubeListBuilder.create(),PartPose.offset(0F,19F,0F));
		PartDefinition var4 = var3.addOrReplaceChild("parachute_cloth8",CubeListBuilder.create().texOffs(4,30).addBox(-5F,-11.5F,-8.75F,10F,1F,10F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0.2618F,5.4978F,0F));
		PartDefinition var5 = var3.addOrReplaceChild("parachute_cloth2",CubeListBuilder.create().texOffs(4,30).addBox(-5F,-11.5F,-8.75F,10F,1F,10F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0.2618F,0.7854F,0F));
		PartDefinition var6 = var3.addOrReplaceChild("parachute_cloth7",CubeListBuilder.create().texOffs(4,29).addBox(-4F,-11.5F,-9F,8F,1F,12F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0.2618F,4.7124F,0F));
		PartDefinition var7 = var3.addOrReplaceChild("parachute_cord1",CubeListBuilder.create().texOffs(0,30).addBox(0F,-11F,-1F,1F,12F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0.4363F,0F,0F));
		PartDefinition var8 = var3.addOrReplaceChild("parachute_cloth3",CubeListBuilder.create().texOffs(4,29).addBox(-4F,-19.5F,-5.5F,8F,1F,12F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-1.3102F,8.6333F,0F,0.2618F,1.5708F,0F));
		PartDefinition var9 = var3.addOrReplaceChild("parachute_cloth6",CubeListBuilder.create().texOffs(4,30).addBox(-5F,-11.5F,-8.5F,10F,1F,10F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0.2618F,3.927F,0F));
		PartDefinition var10 = var3.addOrReplaceChild("parachute_cord2",CubeListBuilder.create().texOffs(0,30).addBox(0F,-11F,-1F,1F,12F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0.4363F,3.1416F,0F));
		PartDefinition var11 = var3.addOrReplaceChild("parachute_cord3",CubeListBuilder.create().texOffs(0,30).addBox(0F,-11F,-1F,1F,12F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0.4363F,1.5708F,0F));
		PartDefinition var12 = var3.addOrReplaceChild("parachute_cloth1",CubeListBuilder.create().texOffs(4,29).addBox(-4F,-11.5F,-9F,8F,1F,12F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0.2618F,0F,0F));
		PartDefinition var13 = var3.addOrReplaceChild("parachute_cloth4",CubeListBuilder.create().texOffs(4,30).addBox(-5F,-11.5F,-8.5F,10F,1F,10F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0.2618F,2.3562F,0F));
		PartDefinition var14 = var3.addOrReplaceChild("parachute_cord4",CubeListBuilder.create().texOffs(0,30).addBox(0F,-11F,-1F,1F,12F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0.4363F,-1.5708F,0F));
		PartDefinition var15 = var3.addOrReplaceChild("parachute_cloth5",CubeListBuilder.create().texOffs(4,29).addBox(-4F,-11.5F,-9F,8F,1F,12F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,0.2618F,3.1416F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(Entity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.target.render(var1,var2,var3,var4,var5);
		this.parachute.render(var1,var2,var3,var4,var5);
	}
}