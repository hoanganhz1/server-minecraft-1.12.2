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

public class MihawkHatModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","mihawk_hat"),"main");
	private final ModelPart mihawkHat;
	private final ModelPart top;
	private final ModelPart top2s;
	private final ModelPart top3s;
	private final ModelPart feather;
	private final ModelPart feather2;
	private final ModelPart feather3;
	private final ModelPart feather4;
	private final ModelPart feather5;
	private final ModelPart feather6;
	private final ModelPart feather7;
	private final ModelPart rightSide;
	private final ModelPart leftSide;

	public MihawkHatModel(ModelPart var1) {
		super(var1);
		this.mihawkHat = var1.getChild("mihawkHat");
		this.top = this.mihawkHat.getChild("top");
		this.top2s = this.top.getChild("top2s");
		this.top3s = this.top.getChild("top3s");
		this.feather = this.mihawkHat.getChild("feather");
		this.feather2 = this.feather.getChild("feather2");
		this.feather3 = this.feather.getChild("feather3");
		this.feather4 = this.feather.getChild("feather4");
		this.feather5 = this.feather.getChild("feather5");
		this.feather6 = this.feather.getChild("feather6");
		this.feather7 = this.feather.getChild("feather7");
		this.rightSide = this.mihawkHat.getChild("rightSide");
		this.leftSide = this.mihawkHat.getChild("leftSide");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("mihawkHat",CubeListBuilder.create().texOffs(0,0).addBox(-6F,-6.5F,-7F,12F,1F,14F,new CubeDeformation(0F)).texOffs(0,0).addBox(-6F,-7F,-7F,12F,1F,14F,new CubeDeformation(-0.001F)),PartPose.offset(0F,0F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("top",CubeListBuilder.create().texOffs(0,25).addBox(-4F,-4F,-4F,8F,4F,8F,new CubeDeformation(0.52F)).texOffs(15,15).addBox(-4F,-4.75F,-4F,2F,1F,8F,new CubeDeformation(0.501F)).texOffs(15,15).addBox(2F,-4.75F,-4F,2F,1F,8F,new CubeDeformation(0.501F)),PartPose.offset(0F,-7F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("top2s",CubeListBuilder.create().texOffs(37,17).addBox(-0.75F,0F,-4F,1F,1F,8F,new CubeDeformation(0.5F)),PartPose.offsetAndRotation(-1F,-4F,0F,0F,0F,0.7854F));
		PartDefinition var4 = var2.addOrReplaceChild("top3s",CubeListBuilder.create().texOffs(37,17).addBox(0F,-0.75F,-4F,1F,1F,8F,new CubeDeformation(0.5F)),PartPose.offsetAndRotation(1F,-4F,0F,0F,0F,0.7854F));
		PartDefinition var5 = var1.addOrReplaceChild("feather",CubeListBuilder.create().texOffs(0,54).addBox(-2F,-4F,0F,4F,4F,5F,new CubeDeformation(0F)),PartPose.offsetAndRotation(4.25F,-6.25F,0.75F,0.1745F,0F,0F));
		PartDefinition var6 = var5.addOrReplaceChild("feather2",CubeListBuilder.create().texOffs(0,54).addBox(-2F,-4F,-1F,4F,4F,5F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0F,0F,0F,0.2182F,0F,0F));
		PartDefinition var7 = var5.addOrReplaceChild("feather3",CubeListBuilder.create().texOffs(0,62).addBox(-2F,-4.04F,-1.75F,4F,3F,2F,new CubeDeformation(-0.02F)),PartPose.offsetAndRotation(0F,-0.25F,0.25F,0.6109F,0F,0F));
		PartDefinition var8 = var5.addOrReplaceChild("feather4",CubeListBuilder.create().texOffs(0,55).addBox(-2F,-4.2F,-2.25F,4F,2F,2F,new CubeDeformation(-0.03F)),PartPose.offsetAndRotation(0F,0F,0F,0.7854F,0F,0F));
		PartDefinition var9 = var5.addOrReplaceChild("feather5",CubeListBuilder.create().texOffs(0,54).addBox(-2F,-4F,-1F,4F,4F,5F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0F,0.25F,3.75F,-0.2182F,0F,0F));
		PartDefinition var10 = var5.addOrReplaceChild("feather6",CubeListBuilder.create().texOffs(0,55).addBox(-2F,-2.25F,-2F,4F,4F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0.8602F,8.2891F,-0.7418F,0F,0F));
		PartDefinition var11 = var5.addOrReplaceChild("feather7",CubeListBuilder.create().texOffs(0,55).addBox(-2F,-2F,-2.75F,4F,4F,4F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0F,2.6102F,8.5391F,-1.5708F,0F,0F));
		PartDefinition var12 = var1.addOrReplaceChild("rightSide",CubeListBuilder.create().texOffs(20,28).addBox(-1F,-1.15F,-7F,3F,1F,14F,new CubeDeformation(-0.01F)).texOffs(20,28).addBox(-1F,-1.55F,-7F,3F,1F,14F,new CubeDeformation(-0.02F)),PartPose.offsetAndRotation(7F,-5.75F,0F,0F,0F,-0.3491F));
		PartDefinition var13 = var1.addOrReplaceChild("leftSide",CubeListBuilder.create().texOffs(20,28).addBox(-2F,-1.15F,-7F,3F,1F,14F,new CubeDeformation(-0.01F)).texOffs(20,28).addBox(-2F,-1.55F,-7F,3F,1F,14F,new CubeDeformation(-0.02F)),PartPose.offsetAndRotation(-7F,-5.75F,0F,0F,0F,0.3491F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.mihawkHat.copyFrom(this.head);
		this.mihawkHat.render(var1,var2,var3,var4,var5);
	}
}