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
import net.minecraft.world.item.ArmorItem;

public class PearlArmorModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation HAT_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","pearl_hat_gear"),"main");
	public static final ModelLayerLocation ARMOR_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","pearl_armor_gear"),"main");
	public static final ModelLayerLocation LEGS_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","pearl_legs_gear"),"main");
	private ModelPart leftArmGear;
	private ModelPart rightArmGear;
	private ModelPart leftLegGear;
	private ModelPart rightLegGear;
	private ModelPart bodyFace;
	private ModelPart bodyBack;
	private ModelPart headPearl;

	public PearlArmorModel(ModelPart var1) {
		super(var1);
		if (var1.hasChild("leftArmGear")){
			this.leftArmGear = var1.getChild("leftArmGear");
		}
		if (var1.hasChild("rightArmGear")){
			this.rightArmGear = var1.getChild("rightArmGear");
		}
		if (var1.hasChild("leftLegGear")){
			this.leftLegGear = var1.getChild("leftLegGear");
		}
		if (var1.hasChild("rightLegGear")){
			this.rightLegGear = var1.getChild("rightLegGear");
		}
		if (var1.hasChild("bodyFace")){
			this.bodyFace = var1.getChild("bodyFace");
		}
		if (var1.hasChild("bodyBack")){
			this.bodyBack = var1.getChild("bodyBack");
		}
		if (var1.hasChild("headPearl")){
			this.headPearl = var1.getChild("headPearl");
		}
	}
	public static LayerDefinition createBodyLayer(ArmorItem.Type var0) {
		MeshDefinition var1 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var2 = var1.getRoot();
		if ((var0) == (ArmorItem.Type.HELMET)){
			PartDefinition var3 = var2.addOrReplaceChild("headPearl",CubeListBuilder.create().texOffs(0,37).addBox(-4.5F,-7F,-4.5F,9F,1F,9F,new CubeDeformation(0.1F)).texOffs(0,48).addBox(-4.5F,-13F,-4.5F,9F,6F,9F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		}else{
			if ((var0) == (ArmorItem.Type.CHESTPLATE)){
				PartDefinition var3_1 = var2.addOrReplaceChild("bodyFace",CubeListBuilder.create().texOffs(22,1).addBox(-7F,-0.5F,-3.75F,14F,15F,2F,new CubeDeformation(0F)).texOffs(19,19).addBox(-1.5F,5F,-5.25F,3F,3F,2F,new CubeDeformation(0F)).texOffs(11,1).addBox(5F,-0.5F,-2F,1F,1F,4F,new CubeDeformation(-0.01F)).texOffs(11,1).addBox(-6F,-0.5F,-2F,1F,1F,4F,new CubeDeformation(-0.01F)),PartPose.offset(0F,0F,0F));
				PartDefinition var4 = var2.addOrReplaceChild("bodyBack",CubeListBuilder.create().texOffs(22,1).addBox(-7F,-0.5F,2.25F,14F,15F,2F,new CubeDeformation(0F)).texOffs(19,19).addBox(-1.5F,5F,3.75F,3F,3F,2F,new CubeDeformation(0F)),PartPose.offset(0F,0F,-0.5F));
				PartDefinition var5 = var2.addOrReplaceChild("leftArmGear",CubeListBuilder.create(),PartPose.offset(5F,2F,0F));
				PartDefinition var6 = var5.addOrReplaceChild("leftElbowGear",CubeListBuilder.create().texOffs(0,7).addBox(-1F,-3.5F,-3.5F,2F,7F,7F,new CubeDeformation(0F)).texOffs(0,0).addBox(1F,-1.5F,-1.5F,1F,3F,3F,new CubeDeformation(0F)).texOffs(0,22).addBox(-5.1F,-0.75F,-2F,4F,1F,4F,new CubeDeformation(0.1F)),PartPose.offset(4F,3.25F,0F));
				PartDefinition var7 = var5.addOrReplaceChild("leftPunchGear",CubeListBuilder.create().texOffs(0,7).addBox(-1F,-3.5F,-3.5F,2F,7F,7F,new CubeDeformation(0F)).texOffs(0,0).addBox(1F,-1.5F,-1.5F,1F,3F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(1F,11F,0F,0F,0F,1.5708F));
				PartDefinition var8 = var2.addOrReplaceChild("rightArmGear",CubeListBuilder.create(),PartPose.offset(-5F,2F,0F));
				PartDefinition var9 = var8.addOrReplaceChild("rightElbowGear",CubeListBuilder.create().texOffs(0,7).addBox(-1F,-3.5F,-3.5F,2F,7F,7F,new CubeDeformation(0F)).texOffs(0,0).addBox(-2F,-1.5F,-1.5F,1F,3F,3F,new CubeDeformation(0F)).texOffs(0,22).addBox(1.1F,-0.75F,-2F,4F,1F,4F,new CubeDeformation(0.1F)),PartPose.offset(-4F,3.25F,0F));
				PartDefinition var10 = var8.addOrReplaceChild("rightPunchGear",CubeListBuilder.create().texOffs(0,7).addBox(-1F,-3.5F,-3.5F,2F,7F,7F,new CubeDeformation(0F)).texOffs(0,0).addBox(1F,-1.5F,-1.5F,1F,3F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-1F,11F,0F,0F,0F,1.5708F));
			}else{
				if ((var0) == (ArmorItem.Type.LEGGINGS)){
					PartDefinition var3_2 = var2.addOrReplaceChild("leftLegGear",CubeListBuilder.create(),PartPose.offset(1.9F,12F,0F));
					PartDefinition var4_1 = var3_2.addOrReplaceChild("leftKneeGear",CubeListBuilder.create().texOffs(0,22).addBox(-1.6F,-0.75F,0F,4F,1F,4F,new CubeDeformation(0.1F)),PartPose.offset(-0.4F,6.25F,-2F));
					PartDefinition var5_1 = var4_1.addOrReplaceChild("leftKneeGearBase",CubeListBuilder.create().texOffs(0,28).addBox(-1.5F,-3.5F,-1.25F,6F,6F,2F,new CubeDeformation(0.01F)).texOffs(20,25).addBox(0F,-2F,-2F,3F,3F,1F,new CubeDeformation(0F)),PartPose.offset(-1F,0.75F,-0.25F));
					PartDefinition var6_1 = var2.addOrReplaceChild("rightLegGear",CubeListBuilder.create(),PartPose.offset(-5.65F,12F,0F));
					PartDefinition var7_1 = var6_1.addOrReplaceChild("rightKneeGear",CubeListBuilder.create().texOffs(0,22).addBox(-1.6F,-0.75F,0F,4F,1F,4F,new CubeDeformation(0.1F)),PartPose.offset(3.4F,6.25F,-2F));
					PartDefinition var8_1 = var7_1.addOrReplaceChild("rightKneeGearBase",CubeListBuilder.create().texOffs(0,28).addBox(-1.5F,-3.5F,-1.25F,6F,6F,2F,new CubeDeformation(0.01F)).texOffs(20,25).addBox(0F,-2F,-2F,3F,3F,1F,new CubeDeformation(0F)),PartPose.offset(-1F,0.75F,-0.25F));
				}
			}
		}
		return LayerDefinition.create(var1,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		if ((this.leftArmGear) != (null)){
			this.leftArmGear.copyFrom(this.leftArm);
			this.leftArmGear.render(var1,var2,var3,var4,var5);
		}
		if ((this.rightArmGear) != (null)){
			this.rightArmGear.copyFrom(this.rightArm);
			this.rightArmGear.render(var1,var2,var3,var4,var5);
		}
		if ((this.leftLegGear) != (null)){
			this.leftLegGear.copyFrom(this.rightLeg);
			this.leftLegGear.render(var1,var2,var3,var4,var5);
		}
		if ((this.rightLegGear) != (null)){
			this.rightLegGear.copyFrom(this.leftLeg);
			ModelPart var6 = this.rightLegGear;
			var6.x = (var6.x) - (3.5F);
			this.rightLegGear.render(var1,var2,var3,var4,var5);
		}
		if ((this.bodyFace) != (null)){
			this.bodyFace.copyFrom(this.body);
			this.bodyFace.render(var1,var2,var3,var4,var5);
		}
		if ((this.bodyBack) != (null)){
			this.bodyBack.copyFrom(this.body);
			this.bodyBack.render(var1,var2,var3,var4,var5);
		}
		if ((this.headPearl) != (null)){
			this.headPearl.copyFrom(this.head);
			this.headPearl.render(var1,var2,var3,var4,var5);
		}
	}
}