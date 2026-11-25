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

public class WhosWhoMaskModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","whos_who_mask"),"main");
	private final ModelPart mask;
	private final ModelPart leftHorn;
	private final ModelPart lefthornsection2;
	private final ModelPart lefthornsection3;
	private final ModelPart lefthornsection4;
	private final ModelPart lefthornsection5;
	private final ModelPart lefthornsection6;
	private final ModelPart lefthornsection7;
	private final ModelPart lefthornsection8;
	private final ModelPart lefthornsection9;
	private final ModelPart lefthornsection10;
	private final ModelPart rightHorn;
	private final ModelPart righthornsection2;
	private final ModelPart righthornsection3;
	private final ModelPart righthornsection4;
	private final ModelPart righthornsection5;
	private final ModelPart righthornsection6;
	private final ModelPart righthornsection7;
	private final ModelPart righthornsection8;
	private final ModelPart righthornsection9;
	private final ModelPart righthornsection10;

	public WhosWhoMaskModel(ModelPart var1) {
		super(var1);
		this.mask = var1.getChild("mask");
		this.leftHorn = this.mask.getChild("leftHorn");
		this.lefthornsection2 = this.leftHorn.getChild("lefthornsection2");
		this.lefthornsection3 = this.lefthornsection2.getChild("lefthornsection3");
		this.lefthornsection4 = this.lefthornsection3.getChild("lefthornsection4");
		this.lefthornsection5 = this.lefthornsection4.getChild("lefthornsection5");
		this.lefthornsection6 = this.lefthornsection5.getChild("lefthornsection6");
		this.lefthornsection7 = this.lefthornsection6.getChild("lefthornsection7");
		this.lefthornsection8 = this.lefthornsection7.getChild("lefthornsection8");
		this.lefthornsection9 = this.lefthornsection8.getChild("lefthornsection9");
		this.lefthornsection10 = this.lefthornsection9.getChild("lefthornsection10");
		this.rightHorn = this.mask.getChild("rightHorn");
		this.righthornsection2 = this.rightHorn.getChild("righthornsection2");
		this.righthornsection3 = this.righthornsection2.getChild("righthornsection3");
		this.righthornsection4 = this.righthornsection3.getChild("righthornsection4");
		this.righthornsection5 = this.righthornsection4.getChild("righthornsection5");
		this.righthornsection6 = this.righthornsection5.getChild("righthornsection6");
		this.righthornsection7 = this.righthornsection6.getChild("righthornsection7");
		this.righthornsection8 = this.righthornsection7.getChild("righthornsection8");
		this.righthornsection9 = this.righthornsection8.getChild("righthornsection9");
		this.righthornsection10 = this.righthornsection9.getChild("righthornsection10");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("mask",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-8F,-4F,8F,8F,8F,new CubeDeformation(0.55F)),PartPose.offset(0F,0F,0F));
		var1.addOrReplaceChild("leftHorn",CubeListBuilder.create().texOffs(0,17).mirror().addBox(1.5F,-1F,-1F,0F,2F,2F,new CubeDeformation(0.3F)).mirror(false),PartPose.offset(3.3125F,-5.75F,0F)).addOrReplaceChild("lefthornsection2",CubeListBuilder.create().texOffs(9,17).mirror().addBox(-0.5F,-1F,-1F,3F,2F,2F,new CubeDeformation(0.2F)).mirror(false),PartPose.offsetAndRotation(1.8125F,0F,0F,0F,0F,0.0873F)).addOrReplaceChild("lefthornsection3",CubeListBuilder.create().texOffs(20,17).mirror().addBox(-0.0625F,-1F,-1F,2F,2F,2F,new CubeDeformation(0.1F)).mirror(false),PartPose.offsetAndRotation(2.6213F,0F,-0.0238F,0F,0F,-0.0436F)).addOrReplaceChild("lefthornsection4",CubeListBuilder.create().texOffs(20,17).mirror().addBox(-0.5F,-1F,-1F,2F,2F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(2.0625F,-0.025F,0F,0F,0F,-0.3491F)).addOrReplaceChild("lefthornsection5",CubeListBuilder.create().texOffs(1,22).mirror().addBox(0.0553F,-0.5325F,-0.5F,1F,1F,1F,new CubeDeformation(0.4F)).mirror(false),PartPose.offsetAndRotation(1.2375F,0.0875F,0F,0F,0F,-0.6458F)).addOrReplaceChild("lefthornsection6",CubeListBuilder.create().texOffs(1,22).mirror().addBox(0.0604F,-0.4674F,-0.5F,1F,1F,1F,new CubeDeformation(0.3F)).mirror(false),PartPose.offsetAndRotation(1.1178F,0.0594F,0F,0F,0F,-0.6589F)).addOrReplaceChild("lefthornsection7",CubeListBuilder.create().texOffs(1,22).mirror().addBox(0.0412F,-0.4525F,-0.5F,1F,1F,1F,new CubeDeformation(0.2F)).mirror(false),PartPose.offsetAndRotation(1.1875F,0.0625F,0F,0F,0F,-0.3796F)).addOrReplaceChild("lefthornsection8",CubeListBuilder.create().texOffs(0,26).mirror().addBox(-0.3146F,-0.4674F,-0.5F,2F,1F,1F,new CubeDeformation(0.1F)).mirror(false),PartPose.offsetAndRotation(1.25F,0F,0F,0F,0F,-0.0436F)).addOrReplaceChild("lefthornsection9",CubeListBuilder.create().texOffs(1,22).mirror().addBox(-0.1271F,-0.4674F,-0.5F,1F,1F,1F,new CubeDeformation(0.05F)).mirror(false),PartPose.offsetAndRotation(1.8125F,0F,0F,0F,0F,0.2225F)).addOrReplaceChild("lefthornsection10",CubeListBuilder.create().texOffs(0,26).mirror().addBox(-0.1271F,-0.4594F,-0.5F,2F,1F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0.875F,-0.025F,0F,0F,0F,0.2225F));
		var1.addOrReplaceChild("rightHorn",CubeListBuilder.create().texOffs(0,17).mirror().addBox(-1.5F,-1F,-1F,0F,2F,2F,new CubeDeformation(0.3F)).mirror(false),PartPose.offset(-3.3125F,-5.75F,0F)).addOrReplaceChild("righthornsection2",CubeListBuilder.create().texOffs(9,17).mirror().addBox(-2.5F,-1F,-1F,3F,2F,2F,new CubeDeformation(0.2F)).mirror(false),PartPose.offsetAndRotation(-1.8125F,0F,0F,0F,0F,-0.0873F)).addOrReplaceChild("righthornsection3",CubeListBuilder.create().texOffs(20,17).mirror().addBox(-1.9375F,-1F,-1F,2F,2F,2F,new CubeDeformation(0.1F)).mirror(false),PartPose.offsetAndRotation(-2.6213F,0F,-0.0238F,0F,0F,0.0436F)).addOrReplaceChild("righthornsection4",CubeListBuilder.create().texOffs(20,17).mirror().addBox(-1.5F,-1F,-1F,2F,2F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-2.0625F,-0.025F,0F,0F,0F,0.3491F)).addOrReplaceChild("righthornsection5",CubeListBuilder.create().texOffs(1,22).mirror().addBox(-1.0553F,-0.5325F,-0.5F,1F,1F,1F,new CubeDeformation(0.4F)).mirror(false),PartPose.offsetAndRotation(-1.2375F,0.0875F,0F,0F,0F,0.6458F)).addOrReplaceChild("righthornsection6",CubeListBuilder.create().texOffs(1,22).mirror().addBox(-1.0604F,-0.4674F,-0.5F,1F,1F,1F,new CubeDeformation(0.3F)).mirror(false),PartPose.offsetAndRotation(-1.1178F,0.0594F,0F,0F,0F,0.6589F)).addOrReplaceChild("righthornsection7",CubeListBuilder.create().texOffs(1,22).mirror().addBox(-1.0412F,-0.4525F,-0.5F,1F,1F,1F,new CubeDeformation(0.2F)).mirror(false),PartPose.offsetAndRotation(-1.1875F,0.0625F,0F,0F,0F,0.3796F)).addOrReplaceChild("righthornsection8",CubeListBuilder.create().texOffs(0,26).mirror().addBox(-1.6854F,-0.4674F,-0.5F,2F,1F,1F,new CubeDeformation(0.1F)).mirror(false),PartPose.offsetAndRotation(-1.25F,0F,0F,0F,0F,0.0436F)).addOrReplaceChild("righthornsection9",CubeListBuilder.create().texOffs(1,22).mirror().addBox(-0.8729F,-0.4674F,-0.5F,1F,1F,1F,new CubeDeformation(0.05F)).mirror(false),PartPose.offsetAndRotation(-1.8125F,0F,0F,0F,0F,-0.2225F)).addOrReplaceChild("righthornsection10",CubeListBuilder.create().texOffs(0,26).mirror().addBox(-1.8729F,-0.4594F,-0.5F,2F,1F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-0.875F,-0.025F,0F,0F,0F,-0.2225F));
		return LayerDefinition.create(var0,32,32);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.mask.copyFrom(this.head);
		this.mask.render(var1,var2,var3,var4,var5);
	}
}