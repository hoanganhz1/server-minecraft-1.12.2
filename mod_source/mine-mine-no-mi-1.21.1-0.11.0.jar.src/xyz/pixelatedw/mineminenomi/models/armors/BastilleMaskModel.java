package xyz.pixelatedw.mineminenomi.models.armors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidArmorModel;
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

public class BastilleMaskModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","bastille_mask"),"main");
	private final ModelPart leftHorn;
	private final ModelPart lefthornsection3;
	private final ModelPart lefthornsection4;
	private final ModelPart rightHorn;
	private final ModelPart righthornsection3;
	private final ModelPart righthornsection4;

	public BastilleMaskModel(ModelPart var1) {
		super(var1);
		this.leftHorn = this.head.getChild("leftHorn");
		this.lefthornsection3 = this.leftHorn.getChild("lefthornsection3");
		this.lefthornsection4 = this.lefthornsection3.getChild("lefthornsection4");
		this.rightHorn = this.head.getChild("rightHorn");
		this.righthornsection3 = this.rightHorn.getChild("righthornsection3");
		this.righthornsection4 = this.righthornsection3.getChild("righthornsection4");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-8F,-4F,8F,8F,8F,new CubeDeformation(0.55F)),PartPose.offset(0F,0F,0F));
		var1.addOrReplaceChild("leftHorn",CubeListBuilder.create().texOffs(0,17).addBox(-0.25F,-1F,-1F,3F,2F,2F,new CubeDeformation(0.3F)),PartPose.offsetAndRotation(4.5F,-6.75F,0F,0F,0F,-0.0436F)).addOrReplaceChild("lefthornsection3",CubeListBuilder.create().texOffs(0,21).addBox(-0.447F,-0.8844F,-1F,4F,2F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(2.624F,0.0761F,0F,0F,0F,-0.7854F)).addOrReplaceChild("lefthornsection4",CubeListBuilder.create().texOffs(0,25).addBox(-2F,-0.5F,-0.5F,3F,1F,1F,new CubeDeformation(0.3F)),PartPose.offsetAndRotation(4.7265F,-0.9957F,0F,0F,0F,-0.6981F));
		var1.addOrReplaceChild("rightHorn",CubeListBuilder.create().texOffs(0,17).mirror().addBox(-2.75F,-1F,-1F,3F,2F,2F,new CubeDeformation(0.3F)).mirror(false),PartPose.offsetAndRotation(-4.5F,-6.75F,0F,0F,0F,0.0436F)).addOrReplaceChild("righthornsection3",CubeListBuilder.create().texOffs(0,21).mirror().addBox(-3.553F,-0.8844F,-1F,4F,2F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-2.624F,0.0761F,0F,0F,0F,0.7854F)).addOrReplaceChild("righthornsection4",CubeListBuilder.create().texOffs(0,25).mirror().addBox(-1F,-0.5F,-0.5F,3F,1F,1F,new CubeDeformation(0.3F)).mirror(false),PartPose.offsetAndRotation(-4.7265F,-0.9957F,0F,0F,0F,0.6981F));
		return LayerDefinition.create(var0,32,32);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.head.render(var1,var2,var3,var4,var5);
	}
}