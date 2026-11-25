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
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.morph.IFirstPersonHandReplacer;

public class DamnedPunkModel<T extends LivingEntity> extends HumanoidModel<T> implements IFirstPersonHandReplacer {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","damned_punk"),"main");
	private final ModelPart cube1;
	private final ModelPart cube2;
	private final ModelPart bigOctagon;
	private final ModelPart wire1;
	private final ModelPart wire2;
	private final ModelPart wire3;
	private final ModelPart wire4;
	private final ModelPart wire5;
	private final ModelPart wire6;
	private final ModelPart wire7;
	private final ModelPart wire8;
	private final ModelPart wire9;
	private final ModelPart detail7;
	private final ModelPart detail6;
	private final ModelPart detail5;
	private final ModelPart detail4;
	private final ModelPart detail3;
	private final ModelPart detail2;
	private final ModelPart detail1;
	private final ModelPart armSupport;
	private final ModelPart innerGun;
	private final ModelPart armSupport2;
	private final ModelPart octagon;
	private final ModelPart octagon_r1;
	private final ModelPart octagon3;
	private final ModelPart octagon_r2;
	private final ModelPart octagon2;
	private final ModelPart octagon_r3;

	public DamnedPunkModel(ModelPart var1) {
		super(var1);
		this.cube1 = this.rightArm.getChild("cube1");
		this.cube2 = this.rightArm.getChild("cube2");
		this.bigOctagon = this.rightArm.getChild("bigOctagon");
		this.wire1 = this.rightArm.getChild("wire1");
		this.wire2 = this.rightArm.getChild("wire2");
		this.wire3 = this.rightArm.getChild("wire3");
		this.wire4 = this.rightArm.getChild("wire4");
		this.wire5 = this.rightArm.getChild("wire5");
		this.wire6 = this.rightArm.getChild("wire6");
		this.wire7 = this.rightArm.getChild("wire7");
		this.wire8 = this.rightArm.getChild("wire8");
		this.wire9 = this.rightArm.getChild("wire9");
		this.detail7 = this.rightArm.getChild("detail7");
		this.detail6 = this.rightArm.getChild("detail6");
		this.detail5 = this.rightArm.getChild("detail5");
		this.detail4 = this.rightArm.getChild("detail4");
		this.detail3 = this.rightArm.getChild("detail3");
		this.detail2 = this.rightArm.getChild("detail2");
		this.detail1 = this.rightArm.getChild("detail1");
		this.armSupport = this.rightArm.getChild("armSupport");
		this.innerGun = this.rightArm.getChild("innerGun");
		this.armSupport2 = this.rightArm.getChild("armSupport2");
		this.octagon = this.rightArm.getChild("octagon");
		this.octagon_r1 = this.octagon.getChild("octagon_r1");
		this.octagon3 = this.rightArm.getChild("octagon3");
		this.octagon_r2 = this.octagon3.getChild("octagon_r2");
		this.octagon2 = this.rightArm.getChild("octagon2");
		this.octagon_r3 = this.octagon2.getChild("octagon_r3");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(0,16).addBox(-9F,-4F,-4F,5F,5F,8F,new CubeDeformation(0.002F)).texOffs(24,47).addBox(-9F,1F,-4F,3F,1F,8F,new CubeDeformation(0F)).texOffs(14,73).addBox(-9F,2F,-3F,3F,1F,6F,new CubeDeformation(0F)).texOffs(84,49).addBox(-10F,-3F,-3F,1F,2F,6F,new CubeDeformation(0F)).texOffs(75,14).addBox(-10F,-1F,-4F,1F,2F,1F,new CubeDeformation(0F)).texOffs(56,51).addBox(-10F,3F,0.25F,1F,1F,1F,new CubeDeformation(0F)).texOffs(56,51).addBox(-10F,3F,-1.25F,1F,1F,1F,new CubeDeformation(0F)).texOffs(56,51).addBox(-10F,3F,-2.75F,1F,1F,1F,new CubeDeformation(0F)).texOffs(56,51).addBox(-10F,3F,1.75F,1F,1F,1F,new CubeDeformation(0F)).texOffs(75,14).addBox(-10F,-1F,-0.5F,1F,2F,1F,new CubeDeformation(0F)).texOffs(75,14).addBox(-10F,-1F,3F,1F,2F,1F,new CubeDeformation(0F)).texOffs(84,41).addBox(-10F,1F,-3F,1F,2F,6F,new CubeDeformation(0F)).texOffs(57,0).addBox(-11F,-5F,-5F,8F,1F,10F,new CubeDeformation(0F)).texOffs(85,11).addBox(-9F,3F,-1F,2F,4F,2F,new CubeDeformation(0F)).texOffs(0,0).addBox(-13F,-8F,-39F,14F,18F,29F,new CubeDeformation(0F)).texOffs(87,23).addBox(-4F,-5F,-46F,2F,2F,2F,new CubeDeformation(-0.001F)).texOffs(87,23).addBox(-4F,1F,-46F,2F,2F,2F,new CubeDeformation(-0.001F)).texOffs(87,23).addBox(-10F,1F,-46F,2F,2F,2F,new CubeDeformation(-0.001F)).texOffs(87,23).addBox(-10F,-5F,-46F,2F,2F,2F,new CubeDeformation(-0.001F)).texOffs(86,29).addBox(-5F,-13F,-34F,3F,6F,3F,new CubeDeformation(0F)).texOffs(30,67).addBox(-10F,-9F,-24F,8F,1F,8F,new CubeDeformation(0F)).texOffs(0,47).addBox(-16F,-3F,-34F,1F,6F,6F,new CubeDeformation(-0.001F)).texOffs(64,47).addBox(-8F,-10F,-22F,4F,1F,4F,new CubeDeformation(0F)).texOffs(0,47).addBox(2.5F,-4F,-20F,1F,2F,2F,new CubeDeformation(0F)).texOffs(0,47).addBox(-16.5F,-1F,-32F,1F,2F,2F,new CubeDeformation(0F)).texOffs(0,47).addBox(2.5F,4F,-30F,1F,2F,2F,new CubeDeformation(0F)),PartPose.offset(-5F,2F,0F));
		var1.addOrReplaceChild("cube1",CubeListBuilder.create().texOffs(83,0).addBox(-2F,-2F,-2F,4F,4F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-7F,-5F,0F,-0.7854F,0F,0F));
		var1.addOrReplaceChild("cube2",CubeListBuilder.create().texOffs(83,0).addBox(-2F,-2F,-2F,4F,4F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-7F,-5F,0F,0.1968F,0.3527F,0.5763F));
		var1.addOrReplaceChild("bigOctagon",CubeListBuilder.create().texOffs(0,47).addBox(-0.5F,-3F,-3F,1F,6F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-15.5F,0F,-31F,0.7854F,0F,0F));
		var1.addOrReplaceChild("wire1",CubeListBuilder.create().texOffs(57,0).addBox(-1F,-1F,-1F,2F,7F,2F,new CubeDeformation(0.001F)),PartPose.offsetAndRotation(-8F,7F,-1F,-0.829F,0F,0F));
		var1.addOrReplaceChild("wire2",CubeListBuilder.create().texOffs(57,2).addBox(-1F,0.7386F,-0.3149F,2F,5F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-8F,9.7425F,-4.2669F,-1.1781F,0F,0F));
		var1.addOrReplaceChild("wire3",CubeListBuilder.create().texOffs(57,2).addBox(-1F,-1.6462F,-1.2493F,2F,5F,2F,new CubeDeformation(0.11F)),PartPose.offsetAndRotation(-8F,11.7425F,-9.2669F,-2.3998F,0F,0F));
		var1.addOrReplaceChild("wire4",CubeListBuilder.create().texOffs(25,56).addBox(-2.5F,-1F,0F,5F,2F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-10.9153F,3F,-6.8922F,0F,-2.7053F,0F));
		var1.addOrReplaceChild("wire5",CubeListBuilder.create().texOffs(27,56).addBox(-1.5F,-1F,-0.7255F,3F,2F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-13.9323F,3F,-6.7628F,0F,3.0543F,0F));
		var1.addOrReplaceChild("wire6",CubeListBuilder.create().texOffs(24,56).addBox(-5.9F,-1.5F,-2F,7F,2F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-16.9153F,6F,-11.7922F,2.5986F,0.8367F,2.459F));
		var1.addOrReplaceChild("wire7",CubeListBuilder.create().texOffs(24,56).addBox(-8.4F,-1F,-2F,7F,2F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-16.9153F,6F,-19.7922F,0F,1.5708F,0F));
		var1.addOrReplaceChild("wire8",CubeListBuilder.create().texOffs(25,56).addBox(-3.9043F,-1F,-0.6308F,4F,2F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-16.3634F,6F,-21.6518F,0F,1.0908F,0F));
		var1.addOrReplaceChild("wire9",CubeListBuilder.create().texOffs(25,56).addBox(-3F,-1F,-1F,5F,2F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-14F,6F,-23F,0F,0.6545F,0F));
		var1.addOrReplaceChild("detail7",CubeListBuilder.create().texOffs(25,15).addBox(-0.5F,-1F,-0.5F,1F,2F,1F,new CubeDeformation(0F)).texOffs(25,15).addBox(-0.5F,-1F,-0.5F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-8.5F,-7F,-2F,0.7854F,0F,-0.6981F));
		var1.addOrReplaceChild("detail6",CubeListBuilder.create().texOffs(25,15).addBox(-0.5F,-1F,-0.5F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-7.8572F,-6.234F,2F,-0.6545F,0.3054F,-0.6981F));
		var1.addOrReplaceChild("detail5",CubeListBuilder.create().texOffs(25,16).addBox(-0.5F,-1F,-0.5F,1F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-9.3893F,-4.9484F,0F,-0.1745F,0F,-0.6981F));
		var1.addOrReplaceChild("detail4",CubeListBuilder.create().texOffs(25,15).addBox(-0.5F,-1F,-0.5F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-5.6823F,-6.7535F,2F,-0.8727F,0F,0.6109F));
		var1.addOrReplaceChild("detail3",CubeListBuilder.create().texOffs(25,15).addBox(-0.5F,-2F,-0.5F,1F,2F,1F,new CubeDeformation(0F)).texOffs(25,15).addBox(-0.5F,-2F,-0.5F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-5.6823F,-6.7535F,0F,0F,0F,0.6109F));
		var1.addOrReplaceChild("detail2",CubeListBuilder.create().texOffs(25,15).addBox(-0.5F,-1F,-0.5F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-8.5F,-7F,0F,0F,0F,-0.6981F));
		var1.addOrReplaceChild("detail1",CubeListBuilder.create().texOffs(25,15).addBox(-0.5F,-1F,-0.5F,1F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-6.0747F,-6.6512F,-1.5F,0.4363F,0F,0.48F));
		var1.addOrReplaceChild("armSupport",CubeListBuilder.create().texOffs(0,0).addBox(-3F,-4.5F,-4.5F,5F,8F,8F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-1F,1F,0F,-0.7854F,0F,0F));
		var1.addOrReplaceChild("innerGun",CubeListBuilder.create().texOffs(36,78).addBox(-36.5F,4F,-4F,2F,2F,6F,new CubeDeformation(0F)).texOffs(82,67).addBox(-36.5F,-4F,-4F,2F,2F,6F,new CubeDeformation(0F)).texOffs(24,47).addBox(-36.5F,-2F,2F,2F,6F,2F,new CubeDeformation(0F)).texOffs(57,11).addBox(-36.5F,-2F,-6F,2F,6F,2F,new CubeDeformation(0F)).texOffs(0,47).addBox(-34.5F,-5F,-7F,5F,12F,14F,new CubeDeformation(0F)).texOffs(0,73).addBox(-0.5F,-3F,-5F,3F,8F,8F,new CubeDeformation(0F)).texOffs(38,47).addBox(2.5F,-1F,-3F,3F,4F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-7F,0F,-9.5263F,0F,-1.5708F,-1.5708F));
		var1.addOrReplaceChild("armSupport2",CubeListBuilder.create().texOffs(0,0).addBox(-3F,-4F,-5F,5F,8F,8F,new CubeDeformation(-0.001F)),PartPose.offsetAndRotation(-1F,1F,0F,-1.5708F,0F,0F));
		var1.addOrReplaceChild("octagon",CubeListBuilder.create().texOffs(74,55).addBox(-4F,-9.2426F,5F,2F,2F,6F,new CubeDeformation(-0.003F)).texOffs(0,0).addBox(-4F,-11F,6.7574F,2F,6F,2F,new CubeDeformation(0.001F)),PartPose.offset(5F,5F,-27F)).addOrReplaceChild("octagon_r1",CubeListBuilder.create().texOffs(0,0).addBox(4F,-3F,-1.2426F,2F,6F,2F,new CubeDeformation(0F)).texOffs(74,55).addBox(4F,-1.2426F,-3F,2F,2F,6F,new CubeDeformation(-0.002F)),PartPose.offsetAndRotation(-8F,-8F,8F,-0.7854F,0F,0F));
		var1.addOrReplaceChild("octagon3",CubeListBuilder.create().texOffs(38,47).addBox(-16F,-11F,-12.2426F,2F,6F,14F,new CubeDeformation(0.002F)).texOffs(56,73).addBox(-16F,-15.2426F,-8F,2F,14F,6F,new CubeDeformation(-0.001F)),PartPose.offset(1F,8F,-26F)).addOrReplaceChild("octagon_r2",CubeListBuilder.create().texOffs(56,73).addBox(-8F,-7.2426F,-3F,2F,14F,6F,new CubeDeformation(-0.002F)).texOffs(38,47).addBox(-8F,-3F,-7.2426F,2F,6F,14F,new CubeDeformation(-0.004F)),PartPose.offsetAndRotation(-8F,-8F,-5F,-0.7854F,0F,0F));
		var1.addOrReplaceChild("octagon2",CubeListBuilder.create().texOffs(74,55).addBox(-4F,-9.2426F,5F,2F,2F,6F,new CubeDeformation(0.002F)).texOffs(0,0).addBox(-4F,-11F,6.7574F,2F,6F,2F,new CubeDeformation(-0.001F)),PartPose.offset(5F,13F,-37F)).addOrReplaceChild("octagon_r3",CubeListBuilder.create().texOffs(0,0).addBox(4F,-3F,-1.2426F,2F,6F,2F,new CubeDeformation(-0.004F)).texOffs(74,55).addBox(4F,-1.2426F,-3F,2F,2F,6F,new CubeDeformation(0F)).texOffs(74,55).addBox(4F,-1.2426F,-3F,2F,2F,6F,new CubeDeformation(-0.002F)),PartPose.offsetAndRotation(-8F,-8F,8F,-0.7854F,0F,0F));
		return LayerDefinition.create(var0,128,128);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		super.setupAnim(var1,var2,var3,var4,var5,var6);
		this.rightArm.xRot = 0F;
		this.rightArm.yRot = 0F;
		this.rightArm.zRot = 0F;
		this.rightArm.x = -3F;
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.rightArm.render(var1,var2,var3,var4,var5);
	}
	public boolean renderFirstPersonArm(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5, HumanoidArm var6, boolean var7) {
		var1.translate(-0.2D,1D,0.5D);
		var1.mulPose(Axis.YP.rotationDegrees(60F));
		var1.mulPose(Axis.ZP.rotationDegrees(-10F));
		var1.mulPose(Axis.XP.rotationDegrees(115F));
		this.renderToBuffer(var1,var2,var3,var4,var5);
		return true;
	}
}