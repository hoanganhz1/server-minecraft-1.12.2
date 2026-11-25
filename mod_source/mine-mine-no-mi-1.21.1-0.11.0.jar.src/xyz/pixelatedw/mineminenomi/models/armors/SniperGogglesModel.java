package xyz.pixelatedw.mineminenomi.models.armors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Optional;
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
import xyz.pixelatedw.mineminenomi.abilities.ZoomAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;

public class SniperGogglesModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","sniper_goggles"),"main");
	private final ModelPart base;
	private final ModelPart rightEye;
	private final ModelPart rightEye2;
	private final ModelPart rightEyeDetail;
	private final ModelPart rightEyeDetail2;
	private final ModelPart leftEye;
	private final ModelPart leftEyeDetail;
	private final ModelPart leftEye2;
	private final ModelPart leftEyeDetail2;

	public SniperGogglesModel(ModelPart var1) {
		super(var1);
		this.base = var1.getChild("base");
		this.rightEye = this.base.getChild("rightEye");
		this.rightEye2 = this.rightEye.getChild("rightEye2");
		this.rightEyeDetail = this.rightEye.getChild("rightEyeDetail");
		this.rightEyeDetail2 = this.rightEye.getChild("rightEyeDetail2");
		this.leftEye = this.base.getChild("leftEye");
		this.leftEyeDetail = this.leftEye.getChild("leftEyeDetail");
		this.leftEye2 = this.leftEye.getChild("leftEye2");
		this.leftEyeDetail2 = this.leftEye.getChild("leftEyeDetail2");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("base",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-8F,-4F,8F,8F,8F,new CubeDeformation(0.25F)).texOffs(24,0).addBox(-4.85F,-5F,-0.8F,1F,2F,2F,new CubeDeformation(0F)).texOffs(24,0).mirror().addBox(3.85F,-5F,-0.8F,1F,2F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offset(0F,0F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("rightEye",CubeListBuilder.create().texOffs(0,0).addBox(-0.95F,-1F,-0.5F,2F,2F,1F,new CubeDeformation(0.01F)),PartPose.offset(-1.9F,-4F,-4.25F));
		PartDefinition var3 = var2.addOrReplaceChild("rightEye2",CubeListBuilder.create().texOffs(0,5).addBox(-0.45F,-0.5F,-0.5F,1F,1F,1F,new CubeDeformation(0F)),PartPose.offset(0F,0F,-0.3F));
		PartDefinition var4 = var2.addOrReplaceChild("rightEyeDetail",CubeListBuilder.create().texOffs(0,0).addBox(-1.45F,-0.5F,-0.5F,3F,1F,1F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		PartDefinition var5 = var2.addOrReplaceChild("rightEyeDetail2",CubeListBuilder.create().texOffs(0,0).addBox(-0.45F,-1.5F,-0.5F,1F,3F,1F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		PartDefinition var6 = var1.addOrReplaceChild("leftEye",CubeListBuilder.create().texOffs(0,0).addBox(-1.05F,-1F,-0.5F,2F,2F,1F,new CubeDeformation(0.01F)),PartPose.offset(1.9F,-4F,-4.25F));
		PartDefinition var7 = var6.addOrReplaceChild("leftEyeDetail",CubeListBuilder.create().texOffs(0,0).addBox(-1.55F,-0.5F,-0.5F,3F,1F,1F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		PartDefinition var8 = var6.addOrReplaceChild("leftEye2",CubeListBuilder.create().texOffs(0,5).addBox(-0.55F,-0.5F,-0.5F,1F,1F,1F,new CubeDeformation(0F)),PartPose.offset(0F,0F,-0.3F));
		PartDefinition var9 = var6.addOrReplaceChild("leftEyeDetail2",CubeListBuilder.create().texOffs(0,0).addBox(-0.55F,-1.5F,-0.5F,1F,3F,1F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		return LayerDefinition.create(var0,32,16);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.base.copyFrom(this.head);
		this.base.render(var1,var2,var3,var4,var5);
	}
	public void updateState(LivingEntity var1) {
		AbilityData var2 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
		if ((var2) == (null)){
			return;
		}else{
			this.leftEye.y = -5.5F;
			this.rightEye.y = -5.5F;
			ZoomAbility var3 = ((ZoomAbility)(var2.getEquippedAbility(((AbilityCore)(ZoomAbility.INSTANCE.get())))));
			if (((var3) != (null)) && (var3.isContinuous())){
				this.leftEye.y = -3.5F;
				this.rightEye.y = -3.5F;
			}
			return;
		}
	}
}