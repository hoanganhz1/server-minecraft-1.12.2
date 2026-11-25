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

public class AceHatModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","ace_hat"),"main");
	private final ModelPart aceHat;
	private final ModelPart base;
	private final ModelPart base2;
	private final ModelPart top;
	private final ModelPart symbols;
	private final ModelPart beads;
	private final ModelPart beads1;
	private final ModelPart beads2;
	private final ModelPart beads3;
	private final ModelPart beads4;
	private final ModelPart rightString;
	private final ModelPart rightString1;
	private final ModelPart rightString2;
	private final ModelPart rightString3;
	private final ModelPart leftString;
	private final ModelPart leftString1;
	private final ModelPart leftString2;
	private final ModelPart leftString3;
	private final ModelPart ornament;

	public AceHatModel(ModelPart var1) {
		super(var1);
		this.aceHat = var1.getChild("aceHat");
		this.base = this.aceHat.getChild("base");
		this.base2 = this.base.getChild("base2");
		this.top = this.base.getChild("top");
		this.symbols = this.top.getChild("symbols");
		this.beads = this.base.getChild("beads");
		this.beads1 = this.beads.getChild("beads1");
		this.beads2 = this.beads.getChild("beads2");
		this.beads3 = this.beads.getChild("beads3");
		this.beads4 = this.beads.getChild("beads4");
		this.rightString = this.aceHat.getChild("rightString");
		this.rightString1 = this.rightString.getChild("rightString1");
		this.rightString2 = this.rightString1.getChild("rightString2");
		this.rightString3 = this.rightString2.getChild("rightString3");
		this.leftString = this.aceHat.getChild("leftString");
		this.leftString1 = this.leftString.getChild("leftString1");
		this.leftString2 = this.leftString1.getChild("leftString2");
		this.leftString3 = this.leftString2.getChild("leftString3");
		this.ornament = this.aceHat.getChild("ornament");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("aceHat",CubeListBuilder.create(),PartPose.offset(0F,0F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("base",CubeListBuilder.create().texOffs(0,0).addBox(-6F,-1.01F,-7F,12F,1F,14F,new CubeDeformation(0.02F)).texOffs(2,2).addBox(-7F,-1F,-6F,14F,1F,12F,new CubeDeformation(0F)),PartPose.offset(0F,-6F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("base2",CubeListBuilder.create().texOffs(0,16).addBox(-6F,-1F,-5F,10F,1F,12F,new CubeDeformation(0.02F)).texOffs(2,18).addBox(-7F,-1F,-4F,12F,1F,10F,new CubeDeformation(0F)),PartPose.offset(1F,-1F,-1F));
		PartDefinition var4 = var2.addOrReplaceChild("top",CubeListBuilder.create().texOffs(0,30).addBox(-4.5F,-3F,-4.5F,9F,1F,9F,new CubeDeformation(0.02F)).texOffs(28,30).addBox(-3F,-5F,-3F,6F,2F,6F,new CubeDeformation(0F)).texOffs(37,22).addBox(-2F,-6F,-2F,4F,1F,4F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F)).addOrReplaceChild("symbols",CubeListBuilder.create().texOffs(4,0).addBox(-1F,-2F,0F,2F,2F,1F,new CubeDeformation(0F)).texOffs(4,3).addBox(-4F,-2F,0F,2F,2F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(1.5F,-3F,-4.5F,-0.3927F,0F,0F));
		PartDefinition var5 = var2.addOrReplaceChild("beads",CubeListBuilder.create(),PartPose.offset(0F,0F,0F));
		PartDefinition var6 = var5.addOrReplaceChild("beads1",CubeListBuilder.create().texOffs(10,5).addBox(2F,-3F,-5.25F,1F,1F,1F,new CubeDeformation(0F)).texOffs(10,5).addBox(0.5F,-3F,-5.25F,1F,1F,1F,new CubeDeformation(0F)).texOffs(10,5).addBox(-1F,-3F,-5.25F,1F,1F,1F,new CubeDeformation(0F)).texOffs(10,5).addBox(-2.5F,-3F,-5.25F,1F,1F,1F,new CubeDeformation(0F)).texOffs(10,5).addBox(-4F,-3F,-5.25F,1F,1F,1F,new CubeDeformation(0F)),PartPose.offset(0.5F,0F,-0.5F));
		PartDefinition var7 = var5.addOrReplaceChild("beads2",CubeListBuilder.create().texOffs(10,5).addBox(2.5F,-0.5F,-0.5F,1F,1F,1F,new CubeDeformation(0F)).texOffs(10,5).addBox(1F,-0.5F,-0.5F,1F,1F,1F,new CubeDeformation(0F)).texOffs(10,5).addBox(-0.5F,-0.5F,-0.5F,1F,1F,1F,new CubeDeformation(0F)).texOffs(10,5).addBox(-2F,-0.5F,-0.5F,1F,1F,1F,new CubeDeformation(0F)).texOffs(10,5).addBox(-3.5F,-0.5F,-0.5F,1F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-2.5F,5.25F,0F,3.1416F,0F));
		PartDefinition var8 = var5.addOrReplaceChild("beads3",CubeListBuilder.create().texOffs(10,5).addBox(2.5F,-0.5F,-0.5F,1F,1F,1F,new CubeDeformation(0F)).texOffs(10,5).addBox(1F,-0.5F,-0.5F,1F,1F,1F,new CubeDeformation(0F)).texOffs(10,5).addBox(-0.5F,-0.5F,-0.5F,1F,1F,1F,new CubeDeformation(0F)).texOffs(10,5).addBox(-2F,-0.5F,-0.5F,1F,1F,1F,new CubeDeformation(0F)).texOffs(10,5).addBox(-3.5F,-0.5F,-0.5F,1F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-5.25F,-2.5F,0F,0F,1.5708F,0F));
		PartDefinition var9 = var5.addOrReplaceChild("beads4",CubeListBuilder.create().texOffs(10,5).addBox(2F,-3F,-0.25F,1F,1F,1F,new CubeDeformation(0F)).texOffs(10,5).addBox(0.5F,-3F,-0.25F,1F,1F,1F,new CubeDeformation(0F)).texOffs(10,5).addBox(-1F,-3F,-0.25F,1F,1F,1F,new CubeDeformation(0F)).texOffs(10,5).addBox(-2.5F,-3F,-0.25F,1F,1F,1F,new CubeDeformation(0F)).texOffs(10,5).addBox(-4F,-3F,-0.25F,1F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(5.5F,0F,0.5F,0F,-1.5708F,0F));
		PartDefinition var10 = var1.addOrReplaceChild("rightString",CubeListBuilder.create(),PartPose.offset(-5F,-6F,-3F)).addOrReplaceChild("rightString1",CubeListBuilder.create().texOffs(0,4).addBox(-0.5048F,-0.2181F,-0.5F,1F,4F,1F,new CubeDeformation(-0.2F)),PartPose.offsetAndRotation(-0.5F,0F,1.5F,-0.3927F,0F,-0.0873F)).addOrReplaceChild("rightString2",CubeListBuilder.create().texOffs(0,4).addBox(-0.5F,-0.25F,-0.5F,1F,4F,1F,new CubeDeformation(-0.2F)),PartPose.offsetAndRotation(-0.0048F,3.5319F,0F,-0.1309F,0F,-0.1309F)).addOrReplaceChild("rightString3",CubeListBuilder.create().texOffs(0,0).addBox(-0.5F,-0.25F,-0.5F,1F,8F,1F,new CubeDeformation(-0.2F)),PartPose.offsetAndRotation(0F,3.5F,0F,0.2182F,0F,-0.3927F));
		PartDefinition var11 = var1.addOrReplaceChild("leftString",CubeListBuilder.create(),PartPose.offset(5F,-6F,-3F)).addOrReplaceChild("leftString1",CubeListBuilder.create().texOffs(0,4).addBox(-0.5048F,-0.2181F,-0.5F,1F,4F,1F,new CubeDeformation(-0.2F)),PartPose.offsetAndRotation(0.5F,0F,1.5F,-0.3927F,0F,0.0873F)).addOrReplaceChild("leftString2",CubeListBuilder.create().texOffs(0,4).addBox(-0.5F,-0.25F,-0.5F,1F,4F,1F,new CubeDeformation(-0.2F)),PartPose.offsetAndRotation(-0.0048F,3.5319F,0F,-0.1309F,0F,0.1309F)).addOrReplaceChild("leftString3",CubeListBuilder.create().texOffs(0,0).addBox(-0.5F,-0.25F,-0.5F,1F,8F,1F,new CubeDeformation(-0.2F)),PartPose.offsetAndRotation(0F,3.5F,0F,0.2182F,0F,0.3927F));
		PartDefinition var12 = var1.addOrReplaceChild("ornament",CubeListBuilder.create().texOffs(4,6).addBox(-1F,-1F,-1F,2F,2F,1F,new CubeDeformation(0F)).texOffs(9,8).addBox(-0.5F,-1F,-0.75F,1F,5F,1F,new CubeDeformation(-0.1F)).texOffs(0,10).addBox(-1F,1.25F,-1.25F,2F,1F,2F,new CubeDeformation(-0.3F)).texOffs(0,10).addBox(-1F,2.5F,-1.25F,2F,1F,2F,new CubeDeformation(-0.3F)),PartPose.offset(0F,6F,-6F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.aceHat.copyFrom(this.head);
		this.aceHat.render(var1,var2,var3,var4,var5);
	}
}