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

public class FishmanRayModel<T extends LivingEntity> extends HumanoidModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","fishman_ray"),"main");
	private final ModelPart rightGills;
	private final ModelPart leftGills;
	private final ModelPart leftBlade;
	private final ModelPart rightBlade;

	public FishmanRayModel(ModelPart var1) {
		super(var1);
		this.rightGills = this.head.getChild("rightGills");
		this.leftGills = this.head.getChild("leftGills");
		this.leftBlade = this.leftArm.getChild("leftBlade");
		this.rightBlade = this.rightArm.getChild("rightBlade");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("head",CubeListBuilder.create(),PartPose.offset(0F,0F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("rightGills",CubeListBuilder.create().texOffs(0,-2).addBox(-0.001F,-1.5F,-2F,0F,3F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-4F,-2.2F,-1F,0.1745F,0F,0F));
		PartDefinition var4 = var2.addOrReplaceChild("leftGills",CubeListBuilder.create().texOffs(0,-2).mirror().addBox(0.001F,-1.5F,-2F,0F,3F,4F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(4F,-2.2F,-1F,0.1745F,0F,0F));
		var1.addOrReplaceChild("left_arm",CubeListBuilder.create(),PartPose.offset(5F,2F,0F)).addOrReplaceChild("leftBlade",CubeListBuilder.create().texOffs(2,2).mirror().addBox(-0.5F,-2.5F,-4F,1F,6F,6F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(1F,4.15F,3.05F,-0.7854F,0F,0F));
		var1.addOrReplaceChild("right_arm",CubeListBuilder.create(),PartPose.offset(-5F,2F,0F)).addOrReplaceChild("rightBlade",CubeListBuilder.create().texOffs(2,2).addBox(-0.5F,-2.5F,-4F,1F,6F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-1F,4.15F,3.05F,-0.7854F,0F,0F));
		return LayerDefinition.create(var0,16,16);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.head.render(var1,var2,var3,var4,var5);
		this.leftArm.render(var1,var2,var3,var4,var5);
		this.rightArm.render(var1,var2,var3,var4,var5);
	}
}