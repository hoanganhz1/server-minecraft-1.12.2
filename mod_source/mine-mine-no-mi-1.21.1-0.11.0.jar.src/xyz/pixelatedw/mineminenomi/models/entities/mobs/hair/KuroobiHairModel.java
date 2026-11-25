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

public class KuroobiHairModel<T extends LivingEntity> extends HumanoidModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","kuroobi_hair"),"main");
	private final ModelPart ponytail;
	private final ModelPart rightKnot;
	private final ModelPart leftKnot;

	public KuroobiHairModel(ModelPart var1) {
		super(var1);
		this.ponytail = this.head.getChild("ponytail");
		this.rightKnot = this.head.getChild("rightKnot");
		this.leftKnot = this.head.getChild("leftKnot");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("head",CubeListBuilder.create(),PartPose.offset(0F,0F,0F));
		var1.addOrReplaceChild("ponytail",CubeListBuilder.create().texOffs(0,0).addBox(-0.5F,0.5F,-0.5F,1F,12F,1F,new CubeDeformation(0F)),PartPose.offset(0F,-0.5F,3.5F));
		var1.addOrReplaceChild("rightKnot",CubeListBuilder.create().texOffs(4,0).addBox(-0.5F,-1.5F,-0.5F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2.5F,-8.25F,0.5F,0F,0F,-0.2618F));
		var1.addOrReplaceChild("leftKnot",CubeListBuilder.create().texOffs(4,0).mirror().addBox(-0.5F,-1.5F,-0.5F,1F,2F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(2.5F,-8.25F,0.5F,0F,0F,0.2618F));
		return LayerDefinition.create(var0,8,16);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		super.setupAnim(var1,var2,var3,var4,var5,var6);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.head.render(var1,var2,var3,var4,var5);
	}
}