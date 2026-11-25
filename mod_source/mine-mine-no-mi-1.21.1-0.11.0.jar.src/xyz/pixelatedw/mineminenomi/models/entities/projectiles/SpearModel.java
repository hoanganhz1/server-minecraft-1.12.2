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

public class SpearModel<T extends Entity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","spear"),"main");
	private final ModelPart harpooncable;
	private final ModelPart harpoon2;
	private final ModelPart harpoon4;
	private final ModelPart harpoon3;
	private final ModelPart harpoon5;
	private final ModelPart harpoon7;
	private final ModelPart harpoon1;

	public SpearModel(ModelPart var1) {
		this.harpooncable = var1.getChild("harpooncable");
		this.harpoon2 = this.harpooncable.getChild("harpoon2");
		this.harpoon4 = this.harpoon2.getChild("harpoon4");
		this.harpoon3 = this.harpoon2.getChild("harpoon3");
		this.harpoon5 = this.harpoon2.getChild("harpoon5");
		this.harpoon7 = this.harpoon2.getChild("harpoon7");
		this.harpoon1 = this.harpooncable.getChild("harpoon1");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("harpooncable",CubeListBuilder.create().texOffs(0,0).addBox(-0.5F,-0.5F,-15F,1F,1F,30F,new CubeDeformation(0F)),PartPose.offset(0.5F,0.5F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("harpoon2",CubeListBuilder.create(),PartPose.offset(0F,-0.5F,-15F));
		PartDefinition var3 = var2.addOrReplaceChild("harpoon4",CubeListBuilder.create().texOffs(-1,12).addBox(-1.5F,0F,-3F,3F,1F,1F,new CubeDeformation(0.02F)),PartPose.offset(0F,0F,5F));
		PartDefinition var4 = var2.addOrReplaceChild("harpoon3",CubeListBuilder.create().texOffs(0,8).addBox(-1.5F,0F,-2F,3F,1F,2F,new CubeDeformation(0.03F)),PartPose.offset(0F,0F,5F));
		PartDefinition var5 = var2.addOrReplaceChild("harpoon5",CubeListBuilder.create().texOffs(0,15).addBox(-0.6F,0F,-2.75F,5F,1F,1F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,0F,5F,0F,1.1345F,0F));
		PartDefinition var6 = var2.addOrReplaceChild("harpoon7",CubeListBuilder.create().texOffs(0,15).mirror().addBox(-4.4F,0F,-2.75F,5F,1F,1F,new CubeDeformation(0.01F)).mirror(false),PartPose.offsetAndRotation(0F,0F,5F,0F,-1.1345F,0F));
		PartDefinition var7 = var1.addOrReplaceChild("harpoon1",CubeListBuilder.create().texOffs(0,0).addBox(-0.5F,-0.5F,0F,2F,2F,2F,new CubeDeformation(0F)),PartPose.offset(-0.5F,-0.5F,-10F));
		return LayerDefinition.create(var0,128,32);
	}
	public void setupAnim(Entity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.harpooncable.render(var1,var2,var3,var4,var5);
	}
}