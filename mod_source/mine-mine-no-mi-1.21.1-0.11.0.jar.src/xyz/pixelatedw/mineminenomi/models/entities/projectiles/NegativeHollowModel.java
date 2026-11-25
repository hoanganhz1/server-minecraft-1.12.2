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

public class NegativeHollowModel<T extends Entity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","negative_hollow"),"main");
	private final ModelPart hollow;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart rightArm;
	private final ModelPart rightArm2;
	private final ModelPart leftArm;
	private final ModelPart leftArm2;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;

	public NegativeHollowModel(ModelPart var1) {
		this.hollow = var1.getChild("hollow");
		this.head = this.hollow.getChild("head");
		this.body = this.hollow.getChild("body");
		this.rightArm = this.hollow.getChild("rightArm");
		this.rightArm2 = this.rightArm.getChild("rightArm2");
		this.leftArm = this.hollow.getChild("leftArm");
		this.leftArm2 = this.leftArm.getChild("leftArm2");
		this.rightLeg = this.hollow.getChild("rightLeg");
		this.leftLeg = this.hollow.getChild("leftLeg");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("hollow",CubeListBuilder.create(),PartPose.offsetAndRotation(0F,1F,0F,1.5708F,0F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).mirror().addBox(-1F,-4F,-2F,4F,4F,4F,new CubeDeformation(0F)).mirror(false),PartPose.offset(-1F,-2F,-0.5F));
		PartDefinition var3 = var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(17,0).addBox(1F,0F,0F,3F,8F,2F,new CubeDeformation(0.01F)),PartPose.offset(-2.5F,-2F,-1F));
		PartDefinition var4 = var1.addOrReplaceChild("rightArm",CubeListBuilder.create().texOffs(13,10).addBox(0F,-1F,-1F,1F,4F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2.5F,-0.5F,0F,3.1416F,0F,0F)).addOrReplaceChild("rightArm2",CubeListBuilder.create().texOffs(0,10).addBox(0F,0F,-1.5F,2F,6F,3F,new CubeDeformation(0F)),PartPose.offset(-0.5F,3F,0F));
		PartDefinition var5 = var1.addOrReplaceChild("leftArm",CubeListBuilder.create().texOffs(13,10).mirror().addBox(1F,-1F,-1F,1F,4F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0.5F,-0.5F,0F,3.1416F,0F,0F)).addOrReplaceChild("leftArm2",CubeListBuilder.create().texOffs(0,10).mirror().addBox(0F,0F,-1.5F,2F,6F,3F,new CubeDeformation(0F)).mirror(false),PartPose.offset(0.5F,3F,0F));
		PartDefinition var6 = var1.addOrReplaceChild("rightLeg",CubeListBuilder.create().texOffs(8,9).addBox(0.5F,0F,-0.5F,1F,3F,1F,new CubeDeformation(0F)),PartPose.offset(-1.75F,6F,0F));
		PartDefinition var7 = var1.addOrReplaceChild("leftLeg",CubeListBuilder.create().texOffs(8,9).addBox(0.5F,0F,-0.5F,1F,3F,1F,new CubeDeformation(0F)),PartPose.offset(-0.25F,6F,0F));
		return LayerDefinition.create(var0,32,32);
	}
	public void setupAnim(Entity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.hollow.render(var1,var2,var3,var4,var5);
	}
}