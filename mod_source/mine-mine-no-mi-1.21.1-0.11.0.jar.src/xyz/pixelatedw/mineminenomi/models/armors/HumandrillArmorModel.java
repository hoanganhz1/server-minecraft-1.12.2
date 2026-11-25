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
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.HumandrillEntity;

public class HumandrillArmorModel<T extends HumandrillEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","humandrill_armor"),"main");

	public HumandrillArmorModel(ModelPart var1) {
		super(var1);
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot();
		var1.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(0,16).mirror().addBox(-2.25F,6.0692F,-2.1596F,4F,12F,4F,new CubeDeformation(1.8F)).mirror(false),PartPose.offset(4F,4F,1.25F));
		var1.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(0,16).addBox(-1.75F,6.0692F,-2.1596F,4F,12F,4F,new CubeDeformation(1.8F)),PartPose.offset(-4F,4F,1.25F));
		var1.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(40,16).mirror().addBox(0F,-2F,-1.95F,4F,12F,4F,new CubeDeformation(2.4F)).mirror(false),PartPose.offset(9F,-6F,2.25F));
		var1.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(40,16).addBox(-4F,-2F,-1.95F,4F,12F,4F,new CubeDeformation(2.4F)),PartPose.offset(-9F,-6F,2.25F));
		var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(16,16).addBox(-4.5F,-4.7662F,-0.4782F,8F,12F,4F,new CubeDeformation(4.55F)),PartPose.offsetAndRotation(0.5F,-2F,1F,0.0873F,0F,0F));
		var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-9.5233F,-7.7379F,8F,8F,8F,new CubeDeformation(-0.55F)),PartPose.offset(0F,-9F,0F));
		return LayerDefinition.create(var0,64,32);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.leftLeg.render(var1,var2,var3,var4,var5);
		this.rightLeg.render(var1,var2,var3,var4,var5);
		this.leftArm.render(var1,var2,var3,var4,var5);
		this.rightArm.render(var1,var2,var3,var4,var5);
		this.body.render(var1,var2,var3,var4,var5);
		this.head.render(var1,var2,var3,var4,var5);
	}
}