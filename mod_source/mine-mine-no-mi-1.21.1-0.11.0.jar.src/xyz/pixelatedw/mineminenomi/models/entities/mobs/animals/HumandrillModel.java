package xyz.pixelatedw.mineminenomi.models.entities.mobs.animals;

import com.google.common.collect.ImmutableSet;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.Consumer;
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
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.extensions.IAgeableListModelExtension;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.HumandrillEntity;

public class HumandrillModel<T extends HumandrillEntity> extends HumanoidModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","humandrill"),"main");
	private final ModelPart snout;
	private final ModelPart forehead;
	private final ModelPart leftArmArmor;
	private final ModelPart leftArmArmorPlate;
	private final ModelPart lowerLeftArm;
	private final ModelPart rightArmArmor;
	private final ModelPart rightArmArmorPlate;
	private final ModelPart lowerRightArm;
	private final ModelPart lowerLeftLeg;
	private final ModelPart leftLegArmor;
	private final ModelPart leftLegArmorPlate;
	private final ModelPart leftFoot;
	private final ModelPart lowerRightLeg;
	private final ModelPart rightLegArmor;
	private final ModelPart rightLegArmorPlate;
	private final ModelPart rightFoot;

	public HumandrillModel(ModelPart var1) {
		super(var1);
		this.snout = this.head.getChild("snout");
		this.forehead = this.head.getChild("forehead");
		this.leftArmArmor = this.leftArm.getChild("leftArmArmor");
		this.leftArmArmorPlate = this.leftArmArmor.getChild("leftArmArmorPlate");
		this.lowerLeftArm = this.leftArm.getChild("lowerLeftArm");
		this.rightArmArmor = this.rightArm.getChild("rightArmArmor");
		this.rightArmArmorPlate = this.rightArmArmor.getChild("rightArmArmorPlate");
		this.lowerRightArm = this.rightArm.getChild("lowerRightArm");
		this.lowerLeftLeg = this.leftLeg.getChild("lowerLeftLeg");
		this.leftLegArmor = this.lowerLeftLeg.getChild("leftLegArmor");
		this.leftLegArmorPlate = this.leftLegArmor.getChild("leftLegArmorPlate");
		this.leftFoot = this.lowerLeftLeg.getChild("leftFoot");
		this.lowerRightLeg = this.rightLeg.getChild("lowerRightLeg");
		this.rightLegArmor = this.lowerRightLeg.getChild("rightLegArmor");
		this.rightLegArmorPlate = this.rightLegArmor.getChild("rightLegArmorPlate");
		this.rightFoot = this.lowerRightLeg.getChild("rightFoot");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(16,0).addBox(-3.0625F,-4.125F,-6.4375F,6F,3F,6F,new CubeDeformation(0F)),PartPose.offset(0F,-9F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("snout",CubeListBuilder.create().texOffs(0,16).addBox(-2.5F,-2F,-3F,5F,4F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1.8376F,-3.9299F,0.1309F,0F,0F));
		PartDefinition var4 = var2.addOrReplaceChild("forehead",CubeListBuilder.create().texOffs(0,0).addBox(-1.5F,-5.25F,-2.0313F,3F,2F,4F,new CubeDeformation(0F)).texOffs(0,7).addBox(-2.5F,-3.25F,-2.4688F,5F,3F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-3.4375F,-3.5938F,-0.1309F,0F,0F));
		PartDefinition var5 = var1.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(96,4).mirror().addBox(-0.7187F,-4.208F,-3.9572F,6F,14F,8F,new CubeDeformation(0F)).mirror(false),PartPose.offset(9F,-6F,2.25F));
		PartDefinition var6 = var5.addOrReplaceChild("leftArmArmor",CubeListBuilder.create().texOffs(75,20).mirror().addBox(-3F,-1.5F,-4F,6F,3F,8F,new CubeDeformation(0.3F)).mirror(false),PartPose.offset(2.2813F,4.3159F,0.0567F)).addOrReplaceChild("leftArmArmorPlate",CubeListBuilder.create().texOffs(85,35).mirror().addBox(-0.5F,-6F,-5F,1F,12F,10F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(3.75F,-0.4659F,-0.1552F,0.3054F,0F,0F));
		PartDefinition var7 = var5.addOrReplaceChild("lowerLeftArm",CubeListBuilder.create().texOffs(99,27).mirror().addBox(-3F,-1.2157F,-2.8679F,5F,10F,6F,new CubeDeformation(0F)).mirror(false),PartPose.offset(2.7188F,9.1563F,-0.25F));
		PartDefinition var8 = var1.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(96,4).addBox(-5.2813F,-4.208F,-3.9572F,6F,14F,8F,new CubeDeformation(0F)),PartPose.offset(-9F,-6F,2.25F));
		PartDefinition var9 = var8.addOrReplaceChild("rightArmArmor",CubeListBuilder.create().texOffs(75,20).addBox(-3F,-1.5F,-4F,6F,3F,8F,new CubeDeformation(0.3F)),PartPose.offset(-2.2813F,4.3159F,0.0567F)).addOrReplaceChild("rightArmArmorPlate",CubeListBuilder.create().texOffs(85,35).addBox(-0.5F,-6F,-5F,1F,12F,10F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-3.75F,-0.4659F,-0.1552F,0.3054F,0F,0F));
		PartDefinition var10 = var8.addOrReplaceChild("lowerRightArm",CubeListBuilder.create().texOffs(99,27).addBox(-2F,-1.2157F,-2.8679F,5F,10F,6F,new CubeDeformation(0F)),PartPose.offset(-2.7188F,9.1563F,-0.25F));
		PartDefinition var11 = var1.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(0,33).mirror().addBox(-3.125F,0.3775F,-2.8619F,6F,8F,6F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(4F,4F,1.5F,-0.0436F,0F,0F)).addOrReplaceChild("lowerLeftLeg",CubeListBuilder.create().texOffs(0,47).mirror().addBox(-2F,0.0153F,-2.0871F,4F,12F,4F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-0.125F,7.0907F,-0.2903F,0.0873F,0F,0F));
		PartDefinition var12 = var11.addOrReplaceChild("leftLegArmor",CubeListBuilder.create().texOffs(12,27).mirror().addBox(-2F,-1F,-2F,4F,2F,4F,new CubeDeformation(0.3F)).mirror(false),PartPose.offset(0F,4.3278F,-0.0871F)).addOrReplaceChild("leftLegArmorPlate",CubeListBuilder.create().texOffs(1,28).mirror().addBox(-2F,-2F,-0.5F,4F,4F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-0.0006F,-0.0054F,-2.8125F,0F,0F,-0.0873F));
		PartDefinition var13 = var11.addOrReplaceChild("leftFoot",CubeListBuilder.create().texOffs(17,44).mirror().addBox(-2.5F,-0.9618F,-4.3742F,5F,2F,7F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-0.0625F,11.7406F,0.0398F,-0.0436F,0F,0F));
		PartDefinition var14 = var1.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(0,33).addBox(-2.875F,0.3775F,-2.8619F,6F,8F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-4F,4F,1.5F,-0.0436F,0F,0F)).addOrReplaceChild("lowerRightLeg",CubeListBuilder.create().texOffs(0,47).addBox(-2F,0.0153F,-2.0871F,4F,12F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.125F,7.0907F,-0.2903F,0.0873F,0F,0F));
		PartDefinition var15 = var14.addOrReplaceChild("rightLegArmor",CubeListBuilder.create().texOffs(12,27).addBox(-2F,-1F,-2F,4F,2F,4F,new CubeDeformation(0.3F)),PartPose.offset(0F,4.3278F,-0.0871F)).addOrReplaceChild("rightLegArmorPlate",CubeListBuilder.create().texOffs(1,28).addBox(-2F,-2F,-0.5F,4F,4F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.0006F,-0.0054F,-2.8125F,0F,0F,0.0873F));
		PartDefinition var16 = var14.addOrReplaceChild("rightFoot",CubeListBuilder.create().texOffs(17,44).addBox(-2.5F,-0.9618F,-4.3742F,5F,2F,7F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.0625F,11.7406F,0.0398F,-0.0436F,0F,0F));
		PartDefinition var17 = var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(30,26).addBox(-9F,-9.125F,-2.9375F,17F,9F,8F,new CubeDeformation(0F)).texOffs(28,0).addBox(-8F,-7.6875F,-5F,15F,13F,12F,new CubeDeformation(0F)).texOffs(31,44).addBox(-7F,4.3125F,-4F,13F,3F,10F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.5F,-2F,1F,0.0873F,0F,0F));
		return LayerDefinition.create(var0,128,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		((IAgeableListModelExtension)(this)).getParts().forEach((Consumer)(((Consumer<ModelPart>)(ModelPart::resetPose))));
		this.rightArm.xRot = ((((Mth.cos(((var2) * (0.6662F)) + (3.1415927F))) * (0.8F)) * (var3)) * (0.5F)) / (1F);
		this.leftArm.xRot = ((((Mth.cos((var2) * (0.6662F))) * (0.8F)) * (var3)) * (0.5F)) / (1F);
		this.rightLeg.xRot = (((Mth.cos((var2) * (0.6662F))) * (0.7F)) * (var3)) / (1F);
		this.leftLeg.xRot = (((Mth.cos(((var2) * (0.6662F)) + (3.1415927F))) * (0.7F)) * (var3)) / (1F);
		if (!(var1.getMainHandItem().isEmpty())){
			ModelPart var7 = this.rightArm;
			var7.yRot = (var7.yRot) - (-0.15F);
		}
		this.setupAttackAnimation(var1,var4);
		Animation.animationAngles(var1,this,var2,var3,var4,var5,var6);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.head.render(var1,var2,var3,var4,var5);
		this.leftArm.render(var1,var2,var3,var4,var5);
		this.rightArm.render(var1,var2,var3,var4,var5);
		this.leftLeg.render(var1,var2,var3,var4,var5);
		this.rightLeg.render(var1,var2,var3,var4,var5);
		this.body.render(var1,var2,var3,var4,var5);
		this.leftArmArmor.visible = false;
		this.rightArmArmor.visible = false;
		this.leftLegArmor.visible = false;
		this.rightLegArmor.visible = false;
	}
	public ModelPart getHead() {
		return this.head;
	}
	protected ModelPart getArm(HumanoidArm var1) {
		return ((var1) == (HumanoidArm.LEFT)) ? (this.leftArm) : (this.rightArm);
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		this.getArm(var1).translateAndRotate(var2);
		var2.translate(((var1) == (HumanoidArm.RIGHT)) ? (-0.1D) : (0.2D),0.4D,-0.1D);
	}
	protected Iterable<ModelPart> headParts() {
		return ImmutableSet.of(this.head,this.forehead);
	}
	protected Iterable<ModelPart> bodyParts() {
		ModelPart[] var1 = new ModelPart[]{this.lowerLeftLeg,this.rightLeg,this.lowerRightLeg};
		return ImmutableSet.of(this.body,this.leftArm,this.lowerLeftArm,this.rightArm,this.lowerRightArm,this.leftLeg,var1);
	}
}