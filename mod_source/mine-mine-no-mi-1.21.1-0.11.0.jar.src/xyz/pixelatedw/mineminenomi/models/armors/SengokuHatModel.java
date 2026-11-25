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

public class SengokuHatModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","sengoku_hat"),"main");
	private final ModelPart sengokuHat;
	private final ModelPart base;
	private final ModelPart tip;
	private final ModelPart bird;
	private final ModelPart birbTail;
	private final ModelPart birbTail1;
	private final ModelPart birbTail5;
	private final ModelPart birbTail2;
	private final ModelPart birbTail4;
	private final ModelPart birbTail3;
	private final ModelPart birbRightWing;
	private final ModelPart birbRightWing1;
	private final ModelPart birbRightWing2;
	private final ModelPart birbLeftWing;
	private final ModelPart birbLeftWing1;
	private final ModelPart birbLeftWing2;
	private final ModelPart birbHead;

	public SengokuHatModel(ModelPart var1) {
		super(var1);
		this.sengokuHat = var1.getChild("sengokuHat");
		this.base = this.sengokuHat.getChild("base");
		this.tip = this.base.getChild("tip");
		this.bird = this.sengokuHat.getChild("bird");
		this.birbTail = this.bird.getChild("birbTail");
		this.birbTail1 = this.birbTail.getChild("birbTail1");
		this.birbTail5 = this.birbTail.getChild("birbTail5");
		this.birbTail2 = this.birbTail.getChild("birbTail2");
		this.birbTail4 = this.birbTail.getChild("birbTail4");
		this.birbTail3 = this.birbTail.getChild("birbTail3");
		this.birbRightWing = this.bird.getChild("birbRightWing");
		this.birbRightWing1 = this.birbRightWing.getChild("birbRightWing1");
		this.birbRightWing2 = this.birbRightWing.getChild("birbRightWing2");
		this.birbLeftWing = this.bird.getChild("birbLeftWing");
		this.birbLeftWing1 = this.birbLeftWing.getChild("birbLeftWing1");
		this.birbLeftWing2 = this.birbLeftWing.getChild("birbLeftWing2");
		this.birbHead = this.bird.getChild("birbHead");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("sengokuHat",CubeListBuilder.create(),PartPose.offset(0F,0F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("base",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-4F,-4F,8F,4F,8F,new CubeDeformation(0.52F)),PartPose.offset(0F,-6F,0F)).addOrReplaceChild("tip",CubeListBuilder.create().texOffs(0,12).addBox(-4F,-1F,-1F,8F,1F,2F,new CubeDeformation(0.501F)),PartPose.offset(0F,-0.6F,-5F));
		PartDefinition var3 = var1.addOrReplaceChild("bird",CubeListBuilder.create().texOffs(0,24).addBox(-0.5F,-1F,-0.5F,1F,1F,2F,new CubeDeformation(0F)).texOffs(0,27).addBox(-0.5F,-2F,0.5F,1F,1F,1F,new CubeDeformation(0F)).texOffs(0,24).addBox(1.5F,-1F,-0.5F,1F,1F,2F,new CubeDeformation(0F)).texOffs(0,27).addBox(1.5F,-2F,0.5F,1F,1F,1F,new CubeDeformation(0F)).texOffs(0,15).addBox(-0.5F,-4F,-1.5F,3F,2F,4F,new CubeDeformation(0F)),PartPose.offset(-1F,-10.5F,-0.75F));
		PartDefinition var4 = var3.addOrReplaceChild("birbTail",CubeListBuilder.create(),PartPose.offsetAndRotation(0F,-2F,3F,-0.1745F,0F,0F));
		PartDefinition var5 = var4.addOrReplaceChild("birbTail1",CubeListBuilder.create().texOffs(4,26).addBox(-0.25F,-0.5F,-0.5F,1F,1F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.25F,-0.75F,-0.75F,0F,-0.7418F,0F));
		PartDefinition var6 = var4.addOrReplaceChild("birbTail5",CubeListBuilder.create().texOffs(4,26).addBox(-0.25F,-0.5F,-0.5F,1F,1F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(1.25F,-0.75F,-0.5F,0F,0.7418F,0F));
		PartDefinition var7 = var4.addOrReplaceChild("birbTail2",CubeListBuilder.create().texOffs(4,26).addBox(-0.5F,-0.5F,-0.5F,1F,1F,3F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0.75F,-0.75F,-0.5F,0F,-0.3927F,0F));
		PartDefinition var8 = var4.addOrReplaceChild("birbTail4",CubeListBuilder.create().texOffs(4,26).addBox(-0.5F,-0.5F,-0.5F,1F,1F,3F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(1.25F,-0.75F,-0.5F,0F,0.3927F,0F));
		PartDefinition var9 = var4.addOrReplaceChild("birbTail3",CubeListBuilder.create().texOffs(4,26).addBox(-0.75F,-0.5F,-0.5F,1F,1F,3F,new CubeDeformation(0.02F)),PartPose.offset(1.25F,-0.75F,-0.5F));
		PartDefinition var10 = var3.addOrReplaceChild("birbRightWing",CubeListBuilder.create(),PartPose.offset(-0.5F,-2.75F,-0.25F));
		PartDefinition var11 = var10.addOrReplaceChild("birbRightWing1",CubeListBuilder.create().texOffs(10,15).addBox(-3F,-0.75F,-0.5F,4F,1F,2F,new CubeDeformation(0.05F)),PartPose.offsetAndRotation(0F,-0.25F,0F,0F,0F,0.4363F));
		PartDefinition var12 = var10.addOrReplaceChild("birbRightWing2",CubeListBuilder.create().texOffs(0,21).addBox(-2.9F,-1F,-0.5F,3F,1F,2F,new CubeDeformation(0.04F)),PartPose.offsetAndRotation(-2F,-1.25F,0F,0F,0F,-0.48F));
		PartDefinition var13 = var3.addOrReplaceChild("birbLeftWing",CubeListBuilder.create(),PartPose.offset(2.5F,-2.75F,-0.25F));
		PartDefinition var14 = var13.addOrReplaceChild("birbLeftWing1",CubeListBuilder.create().texOffs(10,15).mirror().addBox(-1F,-0.75F,-0.5F,4F,1F,2F,new CubeDeformation(0.05F)).mirror(false),PartPose.offsetAndRotation(0F,-0.25F,0F,0F,0F,-0.4363F));
		PartDefinition var15 = var13.addOrReplaceChild("birbLeftWing2",CubeListBuilder.create().texOffs(0,21).mirror().addBox(0.15F,-1.15F,-0.5F,3F,1F,2F,new CubeDeformation(0.04F)).mirror(false),PartPose.offsetAndRotation(1.75F,-1.25F,0F,0F,0F,0.48F));
		PartDefinition var16 = var3.addOrReplaceChild("birbHead",CubeListBuilder.create().texOffs(0,0).addBox(-2F,-2F,-1F,2F,2F,2F,new CubeDeformation(0F)).texOffs(12,21).addBox(-1.5F,-1.25F,-2.75F,1F,1F,2F,new CubeDeformation(0F)),PartPose.offset(2F,-3F,-2F));
		return LayerDefinition.create(var0,32,32);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.sengokuHat.copyFrom(this.head);
		this.sengokuHat.render(var1,var2,var3,var4,var5);
	}
}