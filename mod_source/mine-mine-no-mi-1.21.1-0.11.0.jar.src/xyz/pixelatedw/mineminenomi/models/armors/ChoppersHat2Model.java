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

public class ChoppersHat2Model<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","choppers_hat_2"),"main");
	private final ModelPart chopperHat;
	private final ModelPart leftCap;
	private final ModelPart rightCap;
	private final ModelPart mainCap;
	private final ModelPart leftStrap;
	private final ModelPart rightStrap;

	public ChoppersHat2Model(ModelPart var1) {
		super(var1);
		this.chopperHat = var1.getChild("chopperHat");
		this.leftCap = this.chopperHat.getChild("leftCap");
		this.rightCap = this.chopperHat.getChild("rightCap");
		this.mainCap = this.chopperHat.getChild("mainCap");
		this.leftStrap = this.chopperHat.getChild("leftStrap");
		this.rightStrap = this.chopperHat.getChild("rightStrap");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("chopperHat",CubeListBuilder.create().texOffs(0,17).addBox(-4.5F,-7.5F,-4.5F,9F,3F,9F,new CubeDeformation(0.1F)).texOffs(0,17).addBox(-4.5F,-13.5F,-4.5F,9F,3F,9F,new CubeDeformation(0.1F)).texOffs(0,0).addBox(-5F,-12.5F,-5F,10F,7F,10F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("leftCap",CubeListBuilder.create().texOffs(5,33).addBox(0.8609F,-1.122F,-1.5F,3F,2F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-6F,-5F,0F,0F,0.1309F));
		PartDefinition var3 = var1.addOrReplaceChild("rightCap",CubeListBuilder.create().texOffs(5,33).mirror().addBox(-1.5F,-1F,-1F,3F,2F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-2.3566F,-5.8128F,-5.5F,0F,0F,-0.1309F));
		PartDefinition var4 = var1.addOrReplaceChild("mainCap",CubeListBuilder.create().texOffs(6,29).mirror().addBox(-1F,-1F,-1F,2F,2F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offset(0F,-6F,-5.5F));
		PartDefinition var5 = var1.addOrReplaceChild("leftStrap",CubeListBuilder.create().texOffs(0,34).addBox(0.4F,0F,-1F,0F,5F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(4.1F,-4.4F,-0.5F,0F,0F,-0.0873F));
		PartDefinition var6 = var1.addOrReplaceChild("rightStrap",CubeListBuilder.create().texOffs(1,28).mirror().addBox(-0.4F,0F,0F,0F,5F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-4.1F,-4.4F,-0.5F,0F,0F,0.0873F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.chopperHat.copyFrom(this.head);
		this.chopperHat.render(var1,var2,var3,var4,var5);
	}
}