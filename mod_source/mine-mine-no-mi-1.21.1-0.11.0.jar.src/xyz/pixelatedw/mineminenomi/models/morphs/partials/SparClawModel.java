package xyz.pixelatedw.mineminenomi.models.morphs.partials;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Optional;
import java.util.function.Function;
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
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;

public class SparClawModel<T extends LivingEntity> extends HumanoidModel<T> {
	// Fields
	public static final ModelLayerLocation WIDE_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","wide_spar_claw"),"main");
	public static final ModelLayerLocation SLIM_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","slim_spar_claw"),"main");
	private final ModelPart rightArmBlade;
	private final ModelPart leftArmBlade;
	private final ModelPart leftLegBlade;
	private final ModelPart rightLegBlade;

	public SparClawModel(ModelPart var1) {
		super(var1);
		this.rightArmBlade = this.rightArm.getChild("rightArmBlade");
		this.leftArmBlade = this.leftArm.getChild("leftArmBlade");
		this.leftLegBlade = this.leftLeg.getChild("leftLegBlade");
		this.rightLegBlade = this.rightLeg.getChild("rightLegBlade");
	}
	public static LayerDefinition createWideBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot();
		var1.addOrReplaceChild("right_arm",CubeListBuilder.create(),PartPose.offset(-5F,2F,0F)).addOrReplaceChild("rightArmBlade",CubeListBuilder.create().texOffs(0,0).mirror().addBox(-1.5F,-6F,-1.5F,3F,12F,3F,new CubeDeformation(-0.08F)).mirror(false),PartPose.offsetAndRotation(-2.8789F,4F,-0.0075F,0F,0.7854F,0F));
		var1.addOrReplaceChild("left_arm",CubeListBuilder.create(),PartPose.offset(5F,2F,0F)).addOrReplaceChild("leftArmBlade",CubeListBuilder.create().texOffs(0,0).addBox(-1.5F,-6F,-1.5F,3F,12F,3F,new CubeDeformation(-0.08F)),PartPose.offsetAndRotation(2.9698F,4F,0.0228F,0F,-0.7854F,0F));
		addLegLayers(var1);
		return LayerDefinition.create(var0,64,64);
	}
	public static LayerDefinition createSlimBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot();
		var1.addOrReplaceChild("right_arm",CubeListBuilder.create(),PartPose.offset(-5F,2F,0F)).addOrReplaceChild("rightArmBlade",CubeListBuilder.create().texOffs(0,0).mirror().addBox(-1.5F,-6F,-1.5F,3F,12F,3F,new CubeDeformation(-0.08F)).mirror(false),PartPose.offsetAndRotation(-1.8789F,4F,-0.0075F,0F,0.7854F,0F));
		var1.addOrReplaceChild("left_arm",CubeListBuilder.create(),PartPose.offset(5F,2F,0F)).addOrReplaceChild("leftArmBlade",CubeListBuilder.create().texOffs(0,0).addBox(-1.5F,-6F,-1.5F,3F,12F,3F,new CubeDeformation(-0.08F)),PartPose.offsetAndRotation(1.9698F,4F,0.0228F,0F,-0.7854F,0F));
		addLegLayers(var1);
		return LayerDefinition.create(var0,64,64);
	}
	private static void addLegLayers(PartDefinition var0) {
		var0.addOrReplaceChild("left_leg",CubeListBuilder.create(),PartPose.offset(1.9F,12F,0F)).addOrReplaceChild("leftLegBlade",CubeListBuilder.create().texOffs(0,0).addBox(-0.9929F,-6F,0.1858F,3F,12F,3F,new CubeDeformation(-0.08F)),PartPose.offsetAndRotation(0.9929F,6F,-3.6858F,0F,-0.7854F,0F));
		var0.addOrReplaceChild("right_leg",CubeListBuilder.create(),PartPose.offset(-1.9F,12F,0F)).addOrReplaceChild("rightLegBlade",CubeListBuilder.create().texOffs(0,0).addBox(-0.9929F,-6F,0.1858F,3F,12F,3F,new CubeDeformation(-0.08F)),PartPose.offsetAndRotation(0.7429F,6F,-3.6858F,0F,-0.7854F,0F));
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		super.setupAnim(var1,var2,var3,var4,var5,var6);
		Function<EntityStatsData, Boolean> var7 = (l0) -> {
			return Boolean.valueOf(l0.isBlackLeg());
		};
		if (((Boolean)(((java.util.Optional)(EntityStatsData.get(var1))).map((Function)(var7)).orElse(Boolean.valueOf(false)))).booleanValue()){
			this.leftLeg.visible = true;
			this.rightLeg.visible = true;
			this.leftArm.visible = false;
			this.rightArm.visible = false;
		}else{
			this.leftArm.visible = true;
			this.rightArm.visible = true;
			this.leftLeg.visible = false;
			this.rightLeg.visible = false;
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.leftArm.render(var1,var2,var3,var4,var5);
		this.rightArm.render(var1,var2,var3,var4,var5);
		this.leftLeg.render(var1,var2,var3,var4,var5);
		this.rightLeg.render(var1,var2,var3,var4,var5);
	}
}