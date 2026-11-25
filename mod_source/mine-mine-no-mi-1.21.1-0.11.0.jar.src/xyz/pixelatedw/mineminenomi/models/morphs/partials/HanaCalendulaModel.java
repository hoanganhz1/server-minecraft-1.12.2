package xyz.pixelatedw.mineminenomi.models.morphs.partials;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
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
import org.joml.Quaternionf;

public class HanaCalendulaModel<T extends LivingEntity> extends HumanoidModel<T> {
	// Fields
	public static final ModelLayerLocation WIDE_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","hana_wide_calendula"),"main");
	public static final ModelLayerLocation SLIM_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","hana_slim_calendula"),"main");
	private final ModelPart calendula;
	private final ModelPart arms;
	private final ModelPart calendula1;
	private final ModelPart calendula2;
	private final ModelPart calendula3;
	private final ModelPart calendula4;
	private final ModelPart calendula5;
	private final ModelPart calendula6;
	private final ModelPart calendula7;
	private final ModelPart calendula8;
	private final ModelPart overlay;
	private final ModelPart calendula9;
	private final ModelPart calendula10;
	private final ModelPart calendula11;
	private final ModelPart calendula12;
	private final ModelPart calendula13;
	private final ModelPart calendula14;
	private final ModelPart calendula15;
	private final ModelPart calendula16;

	public HanaCalendulaModel(ModelPart var1) {
		super(var1);
		this.calendula = var1.getChild("calendula");
		this.arms = this.calendula.getChild("arms");
		this.calendula1 = this.arms.getChild("calendula1");
		this.calendula2 = this.arms.getChild("calendula2");
		this.calendula3 = this.arms.getChild("calendula3");
		this.calendula4 = this.arms.getChild("calendula4");
		this.calendula5 = this.arms.getChild("calendula5");
		this.calendula6 = this.arms.getChild("calendula6");
		this.calendula7 = this.arms.getChild("calendula7");
		this.calendula8 = this.arms.getChild("calendula8");
		this.overlay = this.calendula.getChild("overlay");
		this.calendula9 = this.overlay.getChild("calendula9");
		this.calendula10 = this.overlay.getChild("calendula10");
		this.calendula11 = this.overlay.getChild("calendula11");
		this.calendula12 = this.overlay.getChild("calendula12");
		this.calendula13 = this.overlay.getChild("calendula13");
		this.calendula14 = this.overlay.getChild("calendula14");
		this.calendula15 = this.overlay.getChild("calendula15");
		this.calendula16 = this.overlay.getChild("calendula16");
	}
	public static LayerDefinition createSlimBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("calendula",CubeListBuilder.create(),PartPose.offset(-9F,7F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("arms",CubeListBuilder.create(),PartPose.offset(0F,0F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("calendula1",CubeListBuilder.create().texOffs(40,16).addBox(-2F,-2F,-2F,3F,12F,4F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		PartDefinition var4 = var2.addOrReplaceChild("calendula2",CubeListBuilder.create().texOffs(40,16).addBox(-2F,-2F,-2F,3F,12F,4F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,0F,0F,-0.7854F,0F,0F));
		PartDefinition var5 = var2.addOrReplaceChild("calendula3",CubeListBuilder.create().texOffs(40,16).addBox(-2F,-2F,-2F,3F,12F,4F,new CubeDeformation(0.011F)),PartPose.offsetAndRotation(0F,0F,0F,-1.5708F,0F,0F));
		PartDefinition var6 = var2.addOrReplaceChild("calendula4",CubeListBuilder.create().texOffs(40,16).addBox(-2F,-2F,-2F,3F,12F,4F,new CubeDeformation(0.012F)),PartPose.offsetAndRotation(0F,0F,0F,-2.3562F,0F,0F));
		PartDefinition var7 = var2.addOrReplaceChild("calendula5",CubeListBuilder.create().texOffs(40,16).addBox(-2F,-2F,-2F,3F,12F,4F,new CubeDeformation(0.013F)),PartPose.offsetAndRotation(0F,0F,0F,3.1416F,0F,0F));
		PartDefinition var8 = var2.addOrReplaceChild("calendula6",CubeListBuilder.create().texOffs(40,16).addBox(-2F,-2F,-2F,3F,12F,4F,new CubeDeformation(0.014F)),PartPose.offsetAndRotation(0F,0F,0F,2.3562F,0F,0F));
		PartDefinition var9 = var2.addOrReplaceChild("calendula7",CubeListBuilder.create().texOffs(40,16).addBox(-2F,-2F,-2F,3F,12F,4F,new CubeDeformation(0.015F)),PartPose.offsetAndRotation(0F,0F,0F,1.5708F,0F,0F));
		PartDefinition var10 = var2.addOrReplaceChild("calendula8",CubeListBuilder.create().texOffs(40,16).addBox(-2F,-2F,-2F,3F,12F,4F,new CubeDeformation(0.016F)),PartPose.offsetAndRotation(0F,0F,0F,0.7854F,0F,0F));
		PartDefinition var11 = var1.addOrReplaceChild("overlay",CubeListBuilder.create(),PartPose.offset(0F,0F,0F));
		PartDefinition var12 = var11.addOrReplaceChild("calendula9",CubeListBuilder.create().texOffs(40,32).addBox(-2F,-2F,-2F,3F,12F,4F,new CubeDeformation(0.25F)),PartPose.offset(0F,0F,0F));
		PartDefinition var13 = var11.addOrReplaceChild("calendula10",CubeListBuilder.create().texOffs(40,32).addBox(-2F,-2F,-2F,3F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(0F,0F,0F,-0.7854F,0F,0F));
		PartDefinition var14 = var11.addOrReplaceChild("calendula11",CubeListBuilder.create().texOffs(40,32).addBox(-2F,-2F,-2F,3F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(0F,0F,0F,-1.5708F,0F,0F));
		PartDefinition var15 = var11.addOrReplaceChild("calendula12",CubeListBuilder.create().texOffs(40,32).addBox(-2F,-2F,-2F,3F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(0F,0F,0F,-2.3562F,0F,0F));
		PartDefinition var16 = var11.addOrReplaceChild("calendula13",CubeListBuilder.create().texOffs(40,32).addBox(-2F,-2F,-2F,3F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(0F,0F,0F,3.1416F,0F,0F));
		PartDefinition var17 = var11.addOrReplaceChild("calendula14",CubeListBuilder.create().texOffs(40,32).addBox(-2F,-2F,-2F,3F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(0F,0F,0F,2.3562F,0F,0F));
		PartDefinition var18 = var11.addOrReplaceChild("calendula15",CubeListBuilder.create().texOffs(40,32).addBox(-2F,-2F,-2F,3F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(0F,0F,0F,1.5708F,0F,0F));
		PartDefinition var19 = var11.addOrReplaceChild("calendula16",CubeListBuilder.create().texOffs(40,32).addBox(-2F,-2F,-2F,3F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(0F,0F,0F,0.7854F,0F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public static LayerDefinition createWideBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("calendula",CubeListBuilder.create(),PartPose.offset(-9F,7F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("arms",CubeListBuilder.create(),PartPose.offset(0F,0F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("calendula1",CubeListBuilder.create().texOffs(40,16).addBox(-3F,-2F,-2F,4F,12F,4F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		PartDefinition var4 = var2.addOrReplaceChild("calendula2",CubeListBuilder.create().texOffs(40,16).addBox(-3F,-2F,-2F,4F,12F,4F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,0F,0F,-0.7854F,0F,0F));
		PartDefinition var5 = var2.addOrReplaceChild("calendula3",CubeListBuilder.create().texOffs(40,16).addBox(-3F,-2F,-2F,4F,12F,4F,new CubeDeformation(0.011F)),PartPose.offsetAndRotation(0F,0F,0F,-1.5708F,0F,0F));
		PartDefinition var6 = var2.addOrReplaceChild("calendula4",CubeListBuilder.create().texOffs(40,16).addBox(-3F,-2F,-2F,4F,12F,4F,new CubeDeformation(0.012F)),PartPose.offsetAndRotation(0F,0F,0F,-2.3562F,0F,0F));
		PartDefinition var7 = var2.addOrReplaceChild("calendula5",CubeListBuilder.create().texOffs(40,16).addBox(-3F,-2F,-2F,4F,12F,4F,new CubeDeformation(0.013F)),PartPose.offsetAndRotation(0F,0F,0F,3.1416F,0F,0F));
		PartDefinition var8 = var2.addOrReplaceChild("calendula6",CubeListBuilder.create().texOffs(40,16).addBox(-3F,-2F,-2F,4F,12F,4F,new CubeDeformation(0.014F)),PartPose.offsetAndRotation(0F,0F,0F,2.3562F,0F,0F));
		PartDefinition var9 = var2.addOrReplaceChild("calendula7",CubeListBuilder.create().texOffs(40,16).addBox(-3F,-2F,-2F,4F,12F,4F,new CubeDeformation(0.015F)),PartPose.offsetAndRotation(0F,0F,0F,1.5708F,0F,0F));
		PartDefinition var10 = var2.addOrReplaceChild("calendula8",CubeListBuilder.create().texOffs(40,16).addBox(-3F,-2F,-2F,4F,12F,4F,new CubeDeformation(0.016F)),PartPose.offsetAndRotation(0F,0F,0F,0.7854F,0F,0F));
		PartDefinition var11 = var1.addOrReplaceChild("overlay",CubeListBuilder.create(),PartPose.offset(0F,0F,0F));
		PartDefinition var12 = var11.addOrReplaceChild("calendula9",CubeListBuilder.create().texOffs(40,32).addBox(-3F,-2F,-2F,4F,12F,4F,new CubeDeformation(0.25F)),PartPose.offset(0F,0F,0F));
		PartDefinition var13 = var11.addOrReplaceChild("calendula10",CubeListBuilder.create().texOffs(40,32).addBox(-3F,-2F,-2F,4F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(0F,0F,0F,-0.7854F,0F,0F));
		PartDefinition var14 = var11.addOrReplaceChild("calendula11",CubeListBuilder.create().texOffs(40,32).addBox(-3F,-2F,-2F,4F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(0F,0F,0F,-1.5708F,0F,0F));
		PartDefinition var15 = var11.addOrReplaceChild("calendula12",CubeListBuilder.create().texOffs(40,32).addBox(-3F,-2F,-2F,4F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(0F,0F,0F,-2.3562F,0F,0F));
		PartDefinition var16 = var11.addOrReplaceChild("calendula13",CubeListBuilder.create().texOffs(40,32).addBox(-3F,-2F,-2F,4F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(0F,0F,0F,3.1416F,0F,0F));
		PartDefinition var17 = var11.addOrReplaceChild("calendula14",CubeListBuilder.create().texOffs(40,32).addBox(-3F,-2F,-2F,4F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(0F,0F,0F,2.3562F,0F,0F));
		PartDefinition var18 = var11.addOrReplaceChild("calendula15",CubeListBuilder.create().texOffs(40,32).addBox(-3F,-2F,-2F,4F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(0F,0F,0F,1.5708F,0F,0F));
		PartDefinition var19 = var11.addOrReplaceChild("calendula16",CubeListBuilder.create().texOffs(40,32).addBox(-3F,-2F,-2F,4F,12F,4F,new CubeDeformation(0.25F)),PartPose.offsetAndRotation(0F,0F,0F,0.7854F,0F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		this.calendula.xRot = (-(var4)) * (0.15F);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		var1.pushPose();
		var1.translate(0D,-0.4D,0.2D);
		var1.mulPose(Axis.YP.rotationDegrees(-90F));
		this.calendula.render(var1,var2,var3,var4,var5);
		var1.popPose();
	}
}