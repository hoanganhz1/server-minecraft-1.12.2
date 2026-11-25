package xyz.pixelatedw.mineminenomi.models.morphs.partials;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.HumanoidMorphModel;

public class PteranodonAssaultModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","pteranodon_assault"),"main");
	private final ModelPart leftWingTip;
	private final ModelPart leftWing2;
	private final ModelPart rightWingTip;
	private final ModelPart rightWing2;
	private final ModelPart horn;

	public PteranodonAssaultModel(ModelPart var1) {
		super(var1);
		this.leftWingTip = this.leftArm.getChild("leftWingTip");
		this.leftWing2 = this.leftArm.getChild("leftWing2");
		this.rightWingTip = this.rightArm.getChild("rightWingTip");
		this.rightWing2 = this.rightArm.getChild("rightWing2");
		this.horn = this.head.getChild("horn");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(24,0).mirror().addBox(-0.0557F,-1.5F,-5F,1F,18F,10F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(3.8557F,1.6409F,-1F,-0.2967F,-0.0262F,-0.0873F));
		var2.addOrReplaceChild("leftWingTip",CubeListBuilder.create().texOffs(4,7).mirror().addBox(-0.8057F,-0.192F,-0.3995F,1F,2F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(1F,15.75F,-4.5F,-1.2217F,0F,0F));
		var2.addOrReplaceChild("leftWing2",CubeListBuilder.create().texOffs(0,0).mirror().addBox(-0.8057F,-20.6626F,-0.2402F,1F,21F,10F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(1.25F,16.25F,-4.5F,-0.6977F,0.028F,0.0334F));
		PartDefinition var3 = var1.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(24,0).addBox(-0.9557F,-1.5F,-5F,1F,18F,10F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-3.8943F,1.6409F,-1F,-0.2967F,-0.0262F,0.0873F));
		var3.addOrReplaceChild("rightWingTip",CubeListBuilder.create().texOffs(0,7).addBox(-0.2057F,-0.192F,-0.3995F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-1F,15.75F,-4.5F,-1.2217F,0F,0F));
		var3.addOrReplaceChild("rightWing2",CubeListBuilder.create().texOffs(0,0).addBox(0.2943F,-20.6626F,-0.2402F,1F,21F,10F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-1.75F,16.25F,-4.5F,-0.6977F,-0.028F,-0.0334F));
		var1.addOrReplaceChild("head",CubeListBuilder.create(),PartPose.offset(0F,0F,0F)).addOrReplaceChild("horn",CubeListBuilder.create().texOffs(0,0).addBox(-1F,-9.2193F,-7.8714F,2F,5F,2F,new CubeDeformation(0F)).texOffs(12,0).addBox(-1F,-12.2193F,-7.8714F,2F,3F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0.75F,0F,-0.8727F,0F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		RendererHelper.resetModelToDefaultPivots(this);
		super.setupAnim(var1,var2,var3,var4,var5,var6);
		if (!(var1.onGround())){
			this.rightWing2.xRot = (float)(Math.toRadians(190D));
			this.rightWing2.z = 4.5F;
			this.rightWing2.y = 15F;
			this.rightWing2.x = -1.5F;
			this.leftWing2.xRot = (float)(Math.toRadians(190D));
			this.leftWing2.z = 4.5F;
			this.leftWing2.y = 15F;
			this.leftWing2.x = 0.8F;
			this.rightArm.z = 2F;
			this.leftArm.z = 2F;
			this.rightArm.xRot = 0F;
			ModelPart var7 = this.rightArm;
			var7.yRot = (var7.yRot) + ((0.9F) + (Mth.cos((float)((((double)(var1.tickCount)) * (0.3D)) + (3.141592653589793D)))));
			ModelPart var8 = this.rightArm;
			var8.zRot = (var8.zRot) + ((1.3F) + (Mth.cos((float)((((double)(var1.tickCount)) * (0.3D)) + (3.141592653589793D)))));
			ModelPart var9 = this.rightWing2;
			var9.zRot = (var9.zRot) + ((Mth.cos((float)((((double)(var1.tickCount)) * (0.3D)) + (3.141592653589793D)))) / (3F));
			this.leftArm.xRot = 0F;
			ModelPart var10 = this.leftArm;
			var10.yRot = (var10.yRot) - ((0.9F) + (Mth.cos((float)((((double)(var1.tickCount)) * (0.3D)) + (3.141592653589793D)))));
			ModelPart var11 = this.leftArm;
			var11.zRot = (var11.zRot) - ((1.3F) + (Mth.cos((float)((((double)(var1.tickCount)) * (0.3D)) + (3.141592653589793D)))));
			ModelPart var12 = this.leftWing2;
			var12.zRot = (var12.zRot) - ((Mth.cos((float)((((double)(var1.tickCount)) * (0.3D)) + (3.141592653589793D)))) / (3F));
		}
		super.setupAttackAnimation(var1,var4);
		if (var1.isCrouching()){
			this.head.y = 4.2F;
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.leftArm.render(var1,var2,var3,var4,var5);
		this.rightArm.render(var1,var2,var3,var4,var5);
		this.head.render(var1,var2,var3,var4,var5);
	}
}