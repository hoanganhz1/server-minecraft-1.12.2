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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class CaptainCapeModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","captain_cape"),"main");
	private final ModelPart cape;
	private final ModelPart back;
	private final ModelPart leftCoat;
	private final ModelPart capefrontleft;
	private final ModelPart rightShoulderPad;
	private final ModelPart caperightarm;
	private final ModelPart caperightsholderpad1;
	private final ModelPart leftShoulderPad;
	private final ModelPart capeleftarm;
	private final ModelPart capeleftsholderpad1;
	private final ModelPart rightCoat;
	private final ModelPart capefrontright;
	private final ModelPart rightSholder;
	private final ModelPart leftSholder;
	private final ModelPart rightCollar1;
	private final ModelPart leftCollar1;
	private final ModelPart rightCollar2;
	private final ModelPart leftCollar2;

	public CaptainCapeModel(ModelPart var1) {
		super(var1);
		this.cape = var1.getChild("cape");
		this.back = this.cape.getChild("back");
		this.leftCoat = this.back.getChild("leftCoat");
		this.capefrontleft = this.leftCoat.getChild("capefrontleft");
		this.rightShoulderPad = this.back.getChild("rightShoulderPad");
		this.caperightarm = this.rightShoulderPad.getChild("caperightarm");
		this.caperightsholderpad1 = this.rightShoulderPad.getChild("caperightsholderpad1");
		this.leftShoulderPad = this.back.getChild("leftShoulderPad");
		this.capeleftarm = this.leftShoulderPad.getChild("capeleftarm");
		this.capeleftsholderpad1 = this.leftShoulderPad.getChild("capeleftsholderpad1");
		this.rightCoat = this.back.getChild("rightCoat");
		this.capefrontright = this.rightCoat.getChild("capefrontright");
		this.rightSholder = this.cape.getChild("rightSholder");
		this.leftSholder = this.cape.getChild("leftSholder");
		this.rightCollar1 = this.cape.getChild("rightCollar1");
		this.leftCollar1 = this.cape.getChild("leftCollar1");
		this.rightCollar2 = this.cape.getChild("rightCollar2");
		this.leftCollar2 = this.cape.getChild("leftCollar2");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("cape",CubeListBuilder.create(),PartPose.offset(0F,0.5F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("back",CubeListBuilder.create().texOffs(5,75).addBox(-8F,0F,0F,17F,22F,0F,new CubeDeformation(0F)),PartPose.offset(-0.5F,-0.5F,2.5F));
		PartDefinition var3 = var2.addOrReplaceChild("leftCoat",CubeListBuilder.create().texOffs(40,70).addBox(0F,0F,-5F,0F,22F,5F,new CubeDeformation(0F)),PartPose.offset(9F,0F,0F)).addOrReplaceChild("capefrontleft",CubeListBuilder.create().texOffs(28,98).addBox(-3F,0F,0F,3F,22F,0F,new CubeDeformation(0F)),PartPose.offset(0F,0F,-5F));
		PartDefinition var4 = var2.addOrReplaceChild("rightShoulderPad",CubeListBuilder.create().texOffs(5,106).addBox(-4F,0F,-5.5F,5F,3F,6F,new CubeDeformation(0F)),PartPose.offset(-6.4F,0F,0F));
		PartDefinition var5 = var4.addOrReplaceChild("caperightarm",CubeListBuilder.create().texOffs(35,98).addBox(-4.1F,1F,-2F,2F,12F,4F,new CubeDeformation(0F)),PartPose.offset(0.5F,0F,-2.5F));
		PartDefinition var6 = var4.addOrReplaceChild("caperightsholderpad1",CubeListBuilder.create().texOffs(5,98).addBox(-4.5F,-0.8F,-3F,5F,1F,6F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0.5F,0F,-2.5F,0F,0F,-0.1745F));
		PartDefinition var7 = var2.addOrReplaceChild("leftShoulderPad",CubeListBuilder.create().texOffs(5,106).mirror().addBox(-1F,0F,-5.5F,5F,3F,6F,new CubeDeformation(0F)).mirror(false),PartPose.offset(7.4F,0F,0F));
		PartDefinition var8 = var7.addOrReplaceChild("capeleftarm",CubeListBuilder.create().texOffs(35,98).mirror().addBox(2.1F,1F,-2F,2F,12F,4F,new CubeDeformation(0F)).mirror(false),PartPose.offset(-0.5F,0F,-2.5F));
		PartDefinition var9 = var7.addOrReplaceChild("capeleftsholderpad1",CubeListBuilder.create().texOffs(5,98).mirror().addBox(-0.5F,-0.8F,-3F,5F,1F,6F,new CubeDeformation(-0.01F)).mirror(false),PartPose.offsetAndRotation(-0.5F,0F,-2.5F,0F,0F,0.1745F));
		PartDefinition var10 = var2.addOrReplaceChild("rightCoat",CubeListBuilder.create().texOffs(40,70).addBox(0F,0F,-5F,0F,22F,5F,new CubeDeformation(0F)),PartPose.offset(-8F,0F,0F)).addOrReplaceChild("capefrontright",CubeListBuilder.create().texOffs(28,98).addBox(0F,0F,0F,3F,22F,0F,new CubeDeformation(0F)),PartPose.offset(0F,0F,-5F));
		PartDefinition var11 = var1.addOrReplaceChild("rightSholder",CubeListBuilder.create().texOffs(51,75).addBox(-6F,0F,0F,6F,0F,5F,new CubeDeformation(0F)),PartPose.offset(-3.5F,-0.51F,-2.5F));
		PartDefinition var12 = var1.addOrReplaceChild("leftSholder",CubeListBuilder.create().texOffs(51,75).addBox(0F,0F,0F,6F,0F,5F,new CubeDeformation(0F)),PartPose.offset(3.5F,-0.51F,-2.5F));
		PartDefinition var13 = var1.addOrReplaceChild("rightCollar1",CubeListBuilder.create().texOffs(51,81).addBox(0F,0F,0F,0F,3F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-5.2F,-3.5F,-2.3F,-0.0169F,0.1913F,-0.0888F));
		PartDefinition var14 = var1.addOrReplaceChild("leftCollar1",CubeListBuilder.create().texOffs(51,81).mirror().addBox(0F,0F,0F,0F,3F,5F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(5.2F,-3.5F,-2.3F,-0.0169F,-0.1913F,0.0888F));
		PartDefinition var15 = var1.addOrReplaceChild("rightCollar2",CubeListBuilder.create().texOffs(51,90).addBox(0F,0F,0F,0F,2F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-5.2F,-3.5F,-2.3F,0.0202F,0.1909F,0.1066F));
		PartDefinition var16 = var1.addOrReplaceChild("leftCollar2",CubeListBuilder.create().texOffs(51,90).mirror().addBox(0F,0F,0F,0F,2F,5F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(5.2F,-3.5F,-2.3F,0.0202F,-0.1909F,-0.1066F));
		return LayerDefinition.create(var0,128,128);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		double var7 = var1.distanceToSqr(var1.xo,var1.yo,var1.zo);
		if (((var7) > (0D)) && ((var7) <= (0.02D))){
			var7 = (var7) + (0.02D);
		}
		double var8 = Mth.clamp(((var7) * (1000D)) - (1D),0D,70D);
		int var9 = ((var7) > (0.02D)) ? (1) : (0);
		if ((var9) != (0)){
			var8 = (var8) + ((double)((Mth.sin((float)(Mth.lerp(var8,(double)(var1.walkDistO),(double)(var1.walkDist))))) * (6F)));
		}
		this.back.xRot = (float)(Math.toRadians(var8));
		this.rightShoulderPad.xRot = (float)(Math.toRadians(-(var8)));
		this.leftShoulderPad.xRot = (float)(Math.toRadians(-(var8)));
		this.caperightarm.xRot = (float)(Math.toRadians((var8) - ((double)(((var9) == (0)) ? (0) : (20)))));
		this.capeleftarm.xRot = (float)(Math.toRadians((var8) - ((double)(((var9) == (0)) ? (0) : (20)))));
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.cape.copyFrom(this.body);
		this.cape.render(var1,var2,var3,var4,var5);
	}
}