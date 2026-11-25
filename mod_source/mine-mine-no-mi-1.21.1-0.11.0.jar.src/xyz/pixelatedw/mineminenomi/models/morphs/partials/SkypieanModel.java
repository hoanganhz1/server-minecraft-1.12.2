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

public class SkypieanModel<T extends LivingEntity> extends HumanoidModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","skypiean_partial"),"main");
	private final ModelPart antennas;
	private final ModelPart leftAntenna;
	private final ModelPart leftAntennaTip;
	private final ModelPart rightAntenna;
	private final ModelPart rightAntennaTip;
	private final ModelPart wings;
	private final ModelPart leftWing;
	private final ModelPart rightWing;

	public SkypieanModel(ModelPart var1) {
		super(var1);
		this.antennas = var1.getChild("antennas");
		this.leftAntenna = this.antennas.getChild("leftAntenna");
		this.leftAntennaTip = this.leftAntenna.getChild("leftAntennaTip");
		this.rightAntenna = this.antennas.getChild("rightAntenna");
		this.rightAntennaTip = this.rightAntenna.getChild("rightAntennaTip");
		this.wings = var1.getChild("wings");
		this.leftWing = this.wings.getChild("leftWing");
		this.rightWing = this.wings.getChild("rightWing");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("antennas",CubeListBuilder.create(),PartPose.offset(0F,0F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("leftAntenna",CubeListBuilder.create().texOffs(0,1).addBox(-1F,-4F,0F,1F,4F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2F,-7.25F,0F,0F,0F,-0.5236F)).addOrReplaceChild("leftAntennaTip",CubeListBuilder.create().texOffs(6,2).addBox(-1F,-1F,-1F,2F,2F,2F,new CubeDeformation(0F)),PartPose.offset(-0.5F,-5F,0.5F));
		PartDefinition var4 = var2.addOrReplaceChild("rightAntenna",CubeListBuilder.create().texOffs(0,1).mirror().addBox(0F,-4F,0F,1F,4F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(2F,-7.25F,0F,0F,0F,0.5236F)).addOrReplaceChild("rightAntennaTip",CubeListBuilder.create().texOffs(6,2).mirror().addBox(-1F,-1F,-1F,2F,2F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offset(0.5F,-5F,0.5F));
		PartDefinition var5 = var1.addOrReplaceChild("wings",CubeListBuilder.create(),PartPose.offset(0F,0F,0.25F));
		PartDefinition var6 = var5.addOrReplaceChild("leftWing",CubeListBuilder.create().texOffs(16,0).addBox(-10.9727F,-2.9604F,-0.005F,11F,6F,0F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2.5F,2.25F,2F,0F,0.2618F,0.1309F));
		PartDefinition var7 = var5.addOrReplaceChild("rightWing",CubeListBuilder.create().texOffs(16,0).mirror().addBox(-0.0273F,-2.9604F,-0.005F,11F,6F,0F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(2.5F,2.25F,2F,0F,-0.2618F,-0.1309F));
		return LayerDefinition.create(var0,64,32);
	}
	public void setupAnim(LivingEntity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.antennas.copyFrom(this.head);
		this.wings.copyFrom(this.body);
		this.antennas.render(var1,var2,var3,var4,var5);
		this.wings.render(var1,var2,var3,var4,var5);
	}
}