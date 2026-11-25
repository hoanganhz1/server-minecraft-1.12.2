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
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;

public class MinkDogModel<T extends LivingEntity> extends HumanoidModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","mink_dog_partial"),"main");
	private final ModelPart ears;
	private final ModelPart rightEar1;
	private final ModelPart rightEar2;
	private final ModelPart leftEar1;
	private final ModelPart leftEar2;
	private final ModelPart tailBase;
	private final ModelPart tail;
	private final ModelPart tail2;
	private final ModelPart mouth;

	public MinkDogModel(ModelPart var1) {
		super(var1);
		this.ears = var1.getChild("ears");
		this.rightEar1 = this.ears.getChild("rightEar1");
		this.rightEar2 = this.rightEar1.getChild("rightEar2");
		this.leftEar1 = this.ears.getChild("leftEar1");
		this.leftEar2 = this.leftEar1.getChild("leftEar2");
		this.tailBase = var1.getChild("tailBase");
		this.tail = this.tailBase.getChild("tail");
		this.tail2 = this.tail.getChild("tail2");
		this.mouth = var1.getChild("mouth");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("ears",CubeListBuilder.create(),PartPose.offset(0F,8F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("rightEar1",CubeListBuilder.create().texOffs(0,25).addBox(-1.1471F,-2.5F,-0.1504F,3F,3F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2.5F,-7.5F,0F,0F,-0.1745F,-0.2618F)).addOrReplaceChild("rightEar2",CubeListBuilder.create().texOffs(0,16).addBox(-1.1814F,-1.189F,-0.1179F,2F,2F,1F,new CubeDeformation(-0.001F)),PartPose.offsetAndRotation(0.3214F,-2.2239F,-0.0325F,0F,0F,0.7854F));
		PartDefinition var4 = var2.addOrReplaceChild("leftEar1",CubeListBuilder.create().texOffs(0,25).mirror().addBox(-1.8529F,-2.5F,-0.1504F,3F,3F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(2.5F,-7.5F,0F,0F,0.1745F,0.2618F)).addOrReplaceChild("leftEar2",CubeListBuilder.create().texOffs(0,16).mirror().addBox(-0.8186F,-1.189F,-0.1179F,2F,2F,1F,new CubeDeformation(-0.001F)).mirror(false),PartPose.offsetAndRotation(-0.3214F,-2.2239F,-0.0325F,0F,0F,-0.7854F));
		PartDefinition var5 = var1.addOrReplaceChild("tailBase",CubeListBuilder.create(),PartPose.offsetAndRotation(0F,15F,3F,0.3054F,0F,0F)).addOrReplaceChild("tail",CubeListBuilder.create().texOffs(0,0).addBox(-1F,-1F,-1.5F,2F,2F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-0.8066F,1.57F,0.3054F,0F,0F)).addOrReplaceChild("tail2",CubeListBuilder.create().texOffs(0,6).addBox(-1F,-1F,-2F,2F,2F,4F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0F,0.532F,3.1644F,-0.3054F,0F,0F));
		PartDefinition var6 = var1.addOrReplaceChild("mouth",CubeListBuilder.create().texOffs(16,13).addBox(-1.5F,-3F,-8F,3F,3F,5F,new CubeDeformation(0F)),PartPose.offset(0F,6F,1F));
		return LayerDefinition.create(var0,32,32);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		this.tail.yRot = (float)((Math.sin(((double)(var4)) * (0.06D))) / (6D));
		this.tail.xRot = (-(this.body.xRot)) + ((float)((Math.sin(((double)(var4)) * (0.02D))) / (10D)));
		if (var1.isCrouching()){
			this.tail.xRot = -0.2F;
			this.tail.y = 9.5F;
		}
		if ((var1 instanceof Player) && (((Player)(var1)).getAbilities().flying)){
			double var7 = Math.abs((var1.xo) - (var1.getX()));
			double var8 = Math.abs((var1.zo) - (var1.getZ()));
			if ((!((var7) >= (0.2D))) && (!((var8) >= (0.2D)))){
				return;
			}else{
				this.tail.xRot = -0.2F;
			}
		}else{
			this.tail.y = 10F;
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.ears.copyFrom(this.head);
		this.mouth.copyFrom(this.head);
		this.tailBase.copyFrom(this.body);
		this.ears.render(var1,var2,var3,var4,var5);
		this.tailBase.render(var1,var2,var3,var4,var5);
		this.mouth.render(var1,var2,var3,var4,var5);
	}
}