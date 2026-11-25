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

public class MinkLionModel<T extends LivingEntity> extends HumanoidModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","mink_lion_partial"),"main");
	private final ModelPart mane;
	private final ModelPart mane2;
	private final ModelPart tailBase;
	private final ModelPart tail;
	private final ModelPart tail2;
	private final ModelPart tailTip;

	public MinkLionModel(ModelPart var1) {
		super(var1);
		this.mane = var1.getChild("mane");
		this.mane2 = this.mane.getChild("mane2");
		this.tailBase = var1.getChild("tailBase");
		this.tail = this.tailBase.getChild("tail");
		this.tail2 = this.tail.getChild("tail2");
		this.tailTip = this.tail2.getChild("tailTip");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("mane",CubeListBuilder.create().texOffs(0,15).addBox(-6.5F,-10.5F,-2F,13F,13F,4F,new CubeDeformation(0F)),PartPose.offset(0F,6.5F,1F)).addOrReplaceChild("mane2",CubeListBuilder.create().texOffs(0,15).addBox(-10F,-9F,-2F,11F,11F,6F,new CubeDeformation(0F)),PartPose.offset(4.5F,-0.5F,-1F));
		PartDefinition var3 = var1.addOrReplaceChild("tailBase",CubeListBuilder.create(),PartPose.offsetAndRotation(0F,11F,1F,0.3054F,0F,0F)).addOrReplaceChild("tail",CubeListBuilder.create().texOffs(0,0).addBox(-0.5F,-0.5F,-1.5F,1F,1F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,10.5953F,3.1299F,0.3054F,0F,0F)).addOrReplaceChild("tail2",CubeListBuilder.create().texOffs(0,6).addBox(-0.5F,-0.5F,-2F,1F,1F,4F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0F,1.2826F,2.6897F,-0.7417F,0F,0F)).addOrReplaceChild("tailTip",CubeListBuilder.create().texOffs(11,0).addBox(-1F,-1F,-1.5F,2F,2F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0.2532F,3.4293F,-0.1309F,0F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		this.tail.yRot = (float)((Math.sin(((double)(var4)) * (0.06D))) / (10D));
		this.tail.xRot = (float)(((double)(-(this.body.xRot))) + ((Math.sin(((double)(var4)) * (0.02D))) / (5D)));
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
		this.mane.copyFrom(this.head);
		this.tailBase.copyFrom(this.body);
		this.mane.render(var1,var2,var3,var4,var5);
		this.tailBase.render(var1,var2,var3,var4,var5);
	}
}