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

public class SaboHatModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","sabo_hat"),"main");
	private final ModelPart saboHat;
	private final ModelPart band;
	private final ModelPart goggles;
	private final ModelPart leftGoggle;
	private final ModelPart leftGoggle1;
	private final ModelPart leftGoggle2;
	private final ModelPart leftGoggle3;
	private final ModelPart leftGoggle4;
	private final ModelPart leftGoggleRim1;
	private final ModelPart leftGoggleRim3;
	private final ModelPart leftGoggleRim2;
	private final ModelPart leftGoggleRim5;
	private final ModelPart leftGoggleRim6;
	private final ModelPart leftGoggleRim4;
	private final ModelPart rightGoggle;
	private final ModelPart rightGoggle1;
	private final ModelPart rightGoggle2;
	private final ModelPart rightGoggle3;
	private final ModelPart rightGoggle4;
	private final ModelPart rightGoggleRim1;
	private final ModelPart rightGoggleRim3;
	private final ModelPart rightGoggleRim2;
	private final ModelPart rightGoggleRim5;
	private final ModelPart rightGoggleRim6;
	private final ModelPart rightGoggleRim4;

	public SaboHatModel(ModelPart var1) {
		super(var1);
		this.saboHat = var1.getChild("saboHat");
		this.band = this.saboHat.getChild("band");
		this.goggles = this.saboHat.getChild("goggles");
		this.leftGoggle = this.goggles.getChild("leftGoggle");
		this.leftGoggle1 = this.leftGoggle.getChild("leftGoggle1");
		this.leftGoggle2 = this.leftGoggle.getChild("leftGoggle2");
		this.leftGoggle3 = this.leftGoggle.getChild("leftGoggle3");
		this.leftGoggle4 = this.leftGoggle.getChild("leftGoggle4");
		this.leftGoggleRim1 = this.leftGoggle.getChild("leftGoggleRim1");
		this.leftGoggleRim3 = this.leftGoggle.getChild("leftGoggleRim3");
		this.leftGoggleRim2 = this.leftGoggle.getChild("leftGoggleRim2");
		this.leftGoggleRim5 = this.leftGoggle.getChild("leftGoggleRim5");
		this.leftGoggleRim6 = this.leftGoggle.getChild("leftGoggleRim6");
		this.leftGoggleRim4 = this.leftGoggle.getChild("leftGoggleRim4");
		this.rightGoggle = this.goggles.getChild("rightGoggle");
		this.rightGoggle1 = this.rightGoggle.getChild("rightGoggle1");
		this.rightGoggle2 = this.rightGoggle.getChild("rightGoggle2");
		this.rightGoggle3 = this.rightGoggle.getChild("rightGoggle3");
		this.rightGoggle4 = this.rightGoggle.getChild("rightGoggle4");
		this.rightGoggleRim1 = this.rightGoggle.getChild("rightGoggleRim1");
		this.rightGoggleRim3 = this.rightGoggle.getChild("rightGoggleRim3");
		this.rightGoggleRim2 = this.rightGoggle.getChild("rightGoggleRim2");
		this.rightGoggleRim5 = this.rightGoggle.getChild("rightGoggleRim5");
		this.rightGoggleRim6 = this.rightGoggle.getChild("rightGoggleRim6");
		this.rightGoggleRim4 = this.rightGoggle.getChild("rightGoggleRim4");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("saboHat",CubeListBuilder.create().texOffs(0,0).addBox(-7F,-6F,-7F,14F,1F,14F,new CubeDeformation(0F)).texOffs(0,16).addBox(-4F,-12F,-4F,8F,6F,8F,new CubeDeformation(0.6F)).texOffs(0,16).addBox(-4F,-17.55F,-4F,8F,5F,8F,new CubeDeformation(0.8F)),PartPose.offset(0F,0F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("band",CubeListBuilder.create().texOffs(0,44).addBox(-5F,-0.5F,-0.925F,11F,2F,10F,new CubeDeformation(-0.3F)),PartPose.offset(-0.5F,-9.25F,-3.875F));
		PartDefinition var3 = var1.addOrReplaceChild("goggles",CubeListBuilder.create(),PartPose.offset(0F,0.5F,0F));
		PartDefinition var4 = var3.addOrReplaceChild("leftGoggle",CubeListBuilder.create().texOffs(0,33).addBox(-1.34F,-0.9F,-0.5F,1F,2F,1F,new CubeDeformation(-0.19F)).texOffs(0,33).addBox(2.74F,-0.9F,-0.5F,1F,2F,1F,new CubeDeformation(-0.19F)).texOffs(0,6).addBox(-1.26F,-1.4F,0.1F,5F,3F,0F,new CubeDeformation(0F)),PartPose.offset(-3.5F,-9.35F,-4.75F));
		PartDefinition var5 = var4.addOrReplaceChild("leftGoggle1",CubeListBuilder.create().texOffs(-1,33).addBox(-0.5005F,-1.4324F,-0.5F,1F,3F,1F,new CubeDeformation(-0.2F)),PartPose.offsetAndRotation(0.2305F,-1.0176F,0F,0F,0F,1.1345F));
		PartDefinition var6 = var4.addOrReplaceChild("leftGoggle2",CubeListBuilder.create().texOffs(-1,33).mirror().addBox(-0.4995F,-1.4324F,-0.5F,1F,3F,1F,new CubeDeformation(-0.2F)).mirror(false),PartPose.offsetAndRotation(2.1695F,-1.0176F,0F,0F,0F,-1.1345F));
		PartDefinition var7 = var4.addOrReplaceChild("leftGoggle3",CubeListBuilder.create().texOffs(0,33).addBox(-0.5F,-1.5F,-0.5F,1F,3F,1F,new CubeDeformation(-0.201F)),PartPose.offsetAndRotation(0.1578F,1.2026F,0F,0F,0F,-1.1345F));
		PartDefinition var8 = var4.addOrReplaceChild("leftGoggle4",CubeListBuilder.create().texOffs(0,33).mirror().addBox(-0.5F,-1.5F,-0.5F,1F,3F,1F,new CubeDeformation(-0.201F)).mirror(false),PartPose.offsetAndRotation(2.2422F,1.2026F,0F,0F,0F,1.1345F));
		PartDefinition var9 = var4.addOrReplaceChild("leftGoggleRim1",CubeListBuilder.create().texOffs(2,2).addBox(-1.4794F,-0.9959F,0F,3F,2F,0F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.2494F,-0.5191F,0.1F,0F,0F,-0.4363F));
		PartDefinition var10 = var4.addOrReplaceChild("leftGoggleRim3",CubeListBuilder.create().texOffs(2,2).addBox(-1.4994F,-0.9909F,0F,3F,2F,0F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.2494F,0.7309F,0.1F,0F,0F,-2.7053F));
		PartDefinition var11 = var4.addOrReplaceChild("leftGoggleRim2",CubeListBuilder.create().texOffs(2,2).addBox(-1.5444F,-0.9709F,0F,3F,2F,0F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(2.1994F,-0.5191F,0.1F,0F,0F,0.4363F));
		PartDefinition var12 = var4.addOrReplaceChild("leftGoggleRim5",CubeListBuilder.create().texOffs(2,4).addBox(-0.7103F,-0.9966F,0F,2F,1F,0F,new CubeDeformation(0F)),PartPose.offsetAndRotation(2.8703F,0.0866F,0.1F,0F,0F,1.5708F));
		PartDefinition var13 = var4.addOrReplaceChild("leftGoggleRim6",CubeListBuilder.create().texOffs(2,4).mirror().addBox(-0.9732F,-0.4863F,0F,2F,1F,0F,new CubeDeformation(0.01F)).mirror(false),PartPose.offsetAndRotation(-0.9668F,0.3763F,0.1F,0F,0F,-1.5708F));
		PartDefinition var14 = var4.addOrReplaceChild("leftGoggleRim4",CubeListBuilder.create().texOffs(2,2).addBox(-1.5444F,-1.0009F,0F,3F,2F,0F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(2.0994F,0.7309F,0.1F,0F,0F,2.7053F));
		PartDefinition var15 = var3.addOrReplaceChild("rightGoggle",CubeListBuilder.create().texOffs(0,33).mirror().addBox(0.34F,-0.9F,-0.5F,1F,2F,1F,new CubeDeformation(-0.19F)).mirror(false).texOffs(0,33).mirror().addBox(-3.74F,-0.9F,-0.5F,1F,2F,1F,new CubeDeformation(-0.19F)).mirror(false).texOffs(0,6).addBox(-3.74F,-1.4F,0.1F,5F,3F,0F,new CubeDeformation(0F)),PartPose.offset(3.5F,-9.35F,-4.75F));
		PartDefinition var16 = var15.addOrReplaceChild("rightGoggle1",CubeListBuilder.create().texOffs(-1,33).mirror().addBox(-0.4995F,-1.4324F,-0.5F,1F,3F,1F,new CubeDeformation(-0.2F)).mirror(false),PartPose.offsetAndRotation(-0.2305F,-1.0176F,0F,0F,0F,-1.1345F));
		PartDefinition var17 = var15.addOrReplaceChild("rightGoggle2",CubeListBuilder.create().texOffs(-1,33).addBox(-0.5005F,-1.4324F,-0.5F,1F,3F,1F,new CubeDeformation(-0.2F)),PartPose.offsetAndRotation(-2.1695F,-1.0176F,0F,0F,0F,1.1345F));
		PartDefinition var18 = var15.addOrReplaceChild("rightGoggle3",CubeListBuilder.create().texOffs(0,33).mirror().addBox(-0.5F,-1.5F,-0.5F,1F,3F,1F,new CubeDeformation(-0.201F)).mirror(false),PartPose.offsetAndRotation(-0.1578F,1.2026F,0F,0F,0F,1.1345F));
		PartDefinition var19 = var15.addOrReplaceChild("rightGoggle4",CubeListBuilder.create().texOffs(0,33).addBox(-0.5F,-1.5F,-0.5F,1F,3F,1F,new CubeDeformation(-0.201F)),PartPose.offsetAndRotation(-2.2422F,1.2026F,0F,0F,0F,-1.1345F));
		PartDefinition var20 = var15.addOrReplaceChild("rightGoggleRim1",CubeListBuilder.create().texOffs(2,2).mirror().addBox(-1.5206F,-0.9959F,0F,3F,2F,0F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-0.2494F,-0.5191F,0.1F,0F,0F,0.4363F));
		PartDefinition var21 = var15.addOrReplaceChild("rightGoggleRim3",CubeListBuilder.create().texOffs(2,2).mirror().addBox(-1.5006F,-0.9909F,0F,3F,2F,0F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-0.2494F,0.7309F,0.1F,0F,0F,2.7053F));
		PartDefinition var22 = var15.addOrReplaceChild("rightGoggleRim2",CubeListBuilder.create().texOffs(2,2).mirror().addBox(-1.4556F,-0.9709F,0F,3F,2F,0F,new CubeDeformation(0.01F)).mirror(false),PartPose.offsetAndRotation(-2.1994F,-0.5191F,0.1F,0F,0F,-0.4363F));
		PartDefinition var23 = var15.addOrReplaceChild("rightGoggleRim5",CubeListBuilder.create().texOffs(2,4).mirror().addBox(-1.2897F,-0.9966F,0F,2F,1F,0F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-2.8703F,0.0866F,0.1F,0F,0F,-1.5708F));
		PartDefinition var24 = var15.addOrReplaceChild("rightGoggleRim6",CubeListBuilder.create().texOffs(2,4).addBox(-1.0268F,-0.4863F,0F,2F,1F,0F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0.9668F,0.3763F,0.1F,0F,0F,1.5708F));
		PartDefinition var25 = var15.addOrReplaceChild("rightGoggleRim4",CubeListBuilder.create().texOffs(2,2).mirror().addBox(-1.4556F,-1.0009F,0F,3F,2F,0F,new CubeDeformation(0.01F)).mirror(false),PartPose.offsetAndRotation(-2.0994F,0.7309F,0.1F,0F,0F,-2.7053F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.saboHat.copyFrom(this.head);
		this.saboHat.render(var1,var2,var3,var4,var5);
	}
}