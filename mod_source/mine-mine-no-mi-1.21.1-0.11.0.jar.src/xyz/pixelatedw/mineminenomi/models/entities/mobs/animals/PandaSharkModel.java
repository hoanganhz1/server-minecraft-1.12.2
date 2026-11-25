package xyz.pixelatedw.mineminenomi.models.entities.mobs.animals;

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
import net.minecraft.util.Mth;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.PandaSharkEntity;

public class PandaSharkModel<T extends PandaSharkEntity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","panda_shark"),"main");
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart mouth;
	private final ModelPart body2;
	private final ModelPart backFin;
	private final ModelPart leftFin;
	private final ModelPart rightFin;
	private final ModelPart body3;
	private final ModelPart body4;
	private final ModelPart body5;
	private final ModelPart tail;

	public PandaSharkModel(ModelPart var1) {
		this.body = var1.getChild("body");
		this.head = this.body.getChild("head");
		this.mouth = this.head.getChild("mouth");
		this.body2 = this.body.getChild("body2");
		this.backFin = this.body2.getChild("backFin");
		this.leftFin = this.body2.getChild("leftFin");
		this.rightFin = this.body2.getChild("rightFin");
		this.body3 = this.body2.getChild("body3");
		this.body4 = this.body3.getChild("body4");
		this.body5 = this.body4.getChild("body5");
		this.tail = this.body5.getChild("tail");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,26).addBox(-4F,-5F,-9.75F,8F,10F,3F,new CubeDeformation(0.2F)),PartPose.offset(0F,16F,-3.25F));
		PartDefinition var2 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,40).addBox(-4F,-5F,-14.75F,8F,10F,6F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F)).addOrReplaceChild("mouth",CubeListBuilder.create().texOffs(0,57).addBox(-3.5F,-3.5F,-12F,7F,7F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,1.0086F,-6.7946F,0.0873F,0F,0F));
		PartDefinition var3 = var1.addOrReplaceChild("body2",CubeListBuilder.create().texOffs(0,0).addBox(-4.5F,-5.4386F,-9.7491F,9F,11F,14F,new CubeDeformation(0F)),PartPose.offset(0F,-0.0614F,1.9991F));
		PartDefinition var4 = var3.addOrReplaceChild("backFin",CubeListBuilder.create().texOffs(50,37).addBox(0F,-11.6077F,-5.1699F,0F,12F,8F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-3.1886F,-1.7491F,-0.48F,0F,0F));
		PartDefinition var5 = var3.addOrReplaceChild("leftFin",CubeListBuilder.create().texOffs(-10,73).mirror().addBox(-1F,0F,-5F,14F,0F,10F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(4.5F,3.5614F,-3.7491F,0F,0F,0.4363F));
		PartDefinition var6 = var3.addOrReplaceChild("rightFin",CubeListBuilder.create().texOffs(-10,73).addBox(-13F,0F,-5F,14F,0F,10F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-4.5F,3.5614F,-3.7491F,0F,0F,-0.4363F));
		PartDefinition var7 = var3.addOrReplaceChild("body3",CubeListBuilder.create().texOffs(24,26).addBox(-3.5F,-5F,-1.75F,7F,10F,7F,new CubeDeformation(0F)),PartPose.offset(0F,0.0614F,4.0009F)).addOrReplaceChild("body4",CubeListBuilder.create().texOffs(29,44).addBox(-2.5F,-4.5F,-1F,5F,9F,5F,new CubeDeformation(0F)),PartPose.offset(0F,0F,5.25F)).addOrReplaceChild("body5",CubeListBuilder.create().texOffs(27,59).addBox(-2F,-4F,-0.75F,4F,8F,4F,new CubeDeformation(0F)),PartPose.offset(0F,0F,3.75F)).addOrReplaceChild("tail",CubeListBuilder.create().texOffs(30,63).addBox(0F,-11F,0F,0F,22F,9F,new CubeDeformation(0F)),PartPose.offset(0F,0F,3.25F));
		return LayerDefinition.create(var0,128,128);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		this.body.xRot = (var6) * (0.017453292F);
		this.body.yRot = (var5) * (0.017453292F);
		this.tail.yRot = (0.1F) * (Mth.cos((var4) * (0.15F)));
		this.rightFin.yRot = (0.1F) * (Mth.cos((var4) * (0.35F)));
		this.rightFin.zRot = (0.1F) * (Mth.cos((var4) * (0.35F)));
		this.leftFin.yRot = (0.1F) * (-(Mth.cos((var4) * (0.35F))));
		this.leftFin.zRot = (0.1F) * (-(Mth.cos((var4) * (0.35F))));
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.body.render(var1,var2,var3,var4,var5);
	}
}