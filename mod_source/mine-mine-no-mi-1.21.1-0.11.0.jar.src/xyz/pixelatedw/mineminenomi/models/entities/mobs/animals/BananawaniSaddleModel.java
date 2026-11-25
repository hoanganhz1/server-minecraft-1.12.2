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
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BananawaniEntity;

public class BananawaniSaddleModel<T extends BananawaniEntity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","bananawani_saddle"),"main");
	private final ModelPart saddle;
	private final ModelPart saddle_seat;
	private final ModelPart saddle_seat2;
	private final ModelPart saddle_seat_border_left;
	private final ModelPart saddle_seat_border_left2;
	private final ModelPart saddle_seat_border_right;
	private final ModelPart saddle_seat_border_right2;
	private final ModelPart saddle_seat_pillow;
	private final ModelPart saddle_roof;
	private final ModelPart saddle_roof_spikes1;
	private final ModelPart saddle_roof_spikes2;
	private final ModelPart saddle_roof_spikes3;
	private final ModelPart saddle_roof_spikes4;
	private final ModelPart saddle_roof_spikes5;
	private final ModelPart saddle_roof_spikes6;
	private final ModelPart saddle_support_back_left;
	private final ModelPart saddle_support_back_left2;
	private final ModelPart saddle_support_front_left;
	private final ModelPart saddle_support_front_left2;
	private final ModelPart saddle_support_front_left3;
	private final ModelPart saddle_support_front_right;
	private final ModelPart saddle_support_front_right2;
	private final ModelPart saddle_support_front_right3;
	private final ModelPart saddle_support_back_right;
	private final ModelPart saddle_support_back_right2;

	public BananawaniSaddleModel(ModelPart var1) {
		this.saddle = var1.getChild("saddle");
		this.saddle_seat = this.saddle.getChild("saddle_seat");
		this.saddle_seat2 = this.saddle_seat.getChild("saddle_seat2");
		this.saddle_seat_border_left = this.saddle_seat.getChild("saddle_seat_border_left");
		this.saddle_seat_border_left2 = this.saddle_seat_border_left.getChild("saddle_seat_border_left2");
		this.saddle_seat_border_right = this.saddle_seat.getChild("saddle_seat_border_right");
		this.saddle_seat_border_right2 = this.saddle_seat_border_right.getChild("saddle_seat_border_right2");
		this.saddle_seat_pillow = this.saddle_seat.getChild("saddle_seat_pillow");
		this.saddle_roof = this.saddle.getChild("saddle_roof");
		this.saddle_roof_spikes1 = this.saddle_roof.getChild("saddle_roof_spikes1");
		this.saddle_roof_spikes2 = this.saddle_roof.getChild("saddle_roof_spikes2");
		this.saddle_roof_spikes3 = this.saddle_roof.getChild("saddle_roof_spikes3");
		this.saddle_roof_spikes4 = this.saddle_roof.getChild("saddle_roof_spikes4");
		this.saddle_roof_spikes5 = this.saddle_roof.getChild("saddle_roof_spikes5");
		this.saddle_roof_spikes6 = this.saddle_roof.getChild("saddle_roof_spikes6");
		this.saddle_support_back_left = this.saddle.getChild("saddle_support_back_left");
		this.saddle_support_back_left2 = this.saddle_support_back_left.getChild("saddle_support_back_left2");
		this.saddle_support_front_left = this.saddle.getChild("saddle_support_front_left");
		this.saddle_support_front_left2 = this.saddle_support_front_left.getChild("saddle_support_front_left2");
		this.saddle_support_front_left3 = this.saddle_support_front_left2.getChild("saddle_support_front_left3");
		this.saddle_support_front_right = this.saddle.getChild("saddle_support_front_right");
		this.saddle_support_front_right2 = this.saddle_support_front_right.getChild("saddle_support_front_right2");
		this.saddle_support_front_right3 = this.saddle_support_front_right2.getChild("saddle_support_front_right3");
		this.saddle_support_back_right = this.saddle.getChild("saddle_support_back_right");
		this.saddle_support_back_right2 = this.saddle_support_back_right.getChild("saddle_support_back_right2");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("saddle",CubeListBuilder.create().texOffs(0,33).addBox(-5F,-1F,-11F,10F,1F,21F,new CubeDeformation(0F)),PartPose.offset(0F,8F,3F));
		PartDefinition var2 = var1.addOrReplaceChild("saddle_seat",CubeListBuilder.create().texOffs(0,0).addBox(-5F,-7F,-0.25F,10F,7F,1F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,0.25F,4.75F,-0.5236F,0F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("saddle_seat2",CubeListBuilder.create().texOffs(0,8).addBox(-5F,-6F,-0.25F,10F,6F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-6.25F,0F,0.1745F,0F,0F));
		PartDefinition var4 = var2.addOrReplaceChild("saddle_seat_border_left",CubeListBuilder.create().texOffs(4,19).addBox(-1F,-6F,-0.25F,1F,8F,1F,new CubeDeformation(0F)),PartPose.offset(5.5F,-1F,-0.75F)).addOrReplaceChild("saddle_seat_border_left2",CubeListBuilder.create().texOffs(8,19).addBox(-1F,-4F,-0.25F,1F,6F,1F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,-7.75F,-0.25F,0.1745F,0F,0F));
		PartDefinition var5 = var2.addOrReplaceChild("saddle_seat_border_right",CubeListBuilder.create().texOffs(4,19).addBox(-1F,-6F,-0.25F,1F,8F,1F,new CubeDeformation(0F)),PartPose.offset(-4.5F,-1F,-0.75F)).addOrReplaceChild("saddle_seat_border_right2",CubeListBuilder.create().texOffs(8,19).addBox(-1F,-4F,-0.25F,1F,6F,1F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,-7.75F,-0.25F,0.1745F,0F,0F));
		PartDefinition var6 = var2.addOrReplaceChild("saddle_seat_pillow",CubeListBuilder.create().texOffs(0,15).addBox(-5F,-3F,0F,10F,3F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-10.75F,-1.75F,0.3491F,0F,0F));
		PartDefinition var7 = var1.addOrReplaceChild("saddle_roof",CubeListBuilder.create().texOffs(0,0).addBox(-5F,-2F,-16.75F,10F,2F,31F,new CubeDeformation(0.02F)),PartPose.offset(0F,-14.5F,4.5F));
		PartDefinition var8 = var7.addOrReplaceChild("saddle_roof_spikes1",CubeListBuilder.create().texOffs(0,55).addBox(-5F,-1F,-3.5F,10F,2F,7F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-2F,-13F,0.2618F,0F,0F));
		PartDefinition var9 = var7.addOrReplaceChild("saddle_roof_spikes2",CubeListBuilder.create().texOffs(0,55).addBox(-5F,-1F,-3F,10F,2F,7F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,-2F,-9F,0.2618F,0F,0F));
		PartDefinition var10 = var7.addOrReplaceChild("saddle_roof_spikes3",CubeListBuilder.create().texOffs(0,55).addBox(-5F,-1F,-3.5F,10F,2F,7F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-2F,-4F,0.2618F,0F,0F));
		PartDefinition var11 = var7.addOrReplaceChild("saddle_roof_spikes4",CubeListBuilder.create().texOffs(0,55).addBox(-5F,-1F,-3.5F,10F,2F,7F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-2F,1F,0.2618F,0F,0F));
		PartDefinition var12 = var7.addOrReplaceChild("saddle_roof_spikes5",CubeListBuilder.create().texOffs(0,55).addBox(-5F,-1F,-3.5F,10F,2F,7F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-2F,6F,0.2618F,0F,0F));
		PartDefinition var13 = var7.addOrReplaceChild("saddle_roof_spikes6",CubeListBuilder.create().texOffs(0,55).addBox(-5F,-1F,-3.5F,10F,2F,7F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-2F,11F,0.2618F,0F,0F));
		PartDefinition var14 = var1.addOrReplaceChild("saddle_support_back_left",CubeListBuilder.create().texOffs(0,19).addBox(-1F,-9F,0F,1F,9F,1F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(5F,0F,9F,-0.3491F,0F,0F)).addOrReplaceChild("saddle_support_back_left2",CubeListBuilder.create().texOffs(0,19).addBox(-1F,-9F,0F,1F,9F,1F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,-9F,0F,-0.3491F,0F,0F));
		PartDefinition var15 = var1.addOrReplaceChild("saddle_support_front_left",CubeListBuilder.create().texOffs(8,19).addBox(-1F,-6F,0F,1F,6F,1F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(4.65F,0F,-10.75F,0F,0F,0.3491F)).addOrReplaceChild("saddle_support_front_left2",CubeListBuilder.create().texOffs(4,33).addBox(-1F,-5F,0F,1F,5F,1F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,-5.75F,0F,-0.0436F,0F,-0.2618F)).addOrReplaceChild("saddle_support_front_left3",CubeListBuilder.create().texOffs(8,19).addBox(-1F,-6F,0F,1F,6F,1F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,-5F,0F,0F,0F,-0.4363F));
		PartDefinition var16 = var1.addOrReplaceChild("saddle_support_front_right",CubeListBuilder.create().texOffs(8,19).mirror().addBox(0F,-6F,0F,1F,6F,1F,new CubeDeformation(0.01F)).mirror(false),PartPose.offsetAndRotation(-4.65F,0F,-10.75F,0F,0F,-0.3491F)).addOrReplaceChild("saddle_support_front_right2",CubeListBuilder.create().texOffs(4,33).mirror().addBox(0F,-5F,0F,1F,5F,1F,new CubeDeformation(0.01F)).mirror(false),PartPose.offsetAndRotation(0F,-5.75F,0F,-0.0436F,0F,0.2618F)).addOrReplaceChild("saddle_support_front_right3",CubeListBuilder.create().texOffs(8,19).mirror().addBox(0F,-6F,0F,1F,6F,1F,new CubeDeformation(0.01F)).mirror(false),PartPose.offsetAndRotation(0F,-5F,0F,0F,0F,0.4363F));
		PartDefinition var17 = var1.addOrReplaceChild("saddle_support_back_right",CubeListBuilder.create().texOffs(0,19).addBox(-1F,-9F,0F,1F,9F,1F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(-4F,0F,9F,-0.3491F,0F,0F)).addOrReplaceChild("saddle_support_back_right2",CubeListBuilder.create().texOffs(0,19).addBox(-1F,-9F,0F,1F,9F,1F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,-9F,0F,-0.3491F,0F,0F));
		return LayerDefinition.create(var0,128,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.saddle.render(var1,var2,var3,var4,var5);
	}
}