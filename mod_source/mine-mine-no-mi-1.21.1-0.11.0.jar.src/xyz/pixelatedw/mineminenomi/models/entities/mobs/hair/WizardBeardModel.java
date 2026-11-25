package xyz.pixelatedw.mineminenomi.models.entities.mobs.hair;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

public class WizardBeardModel<T extends LivingEntity> extends HumanoidModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","wizard_beard"),"main");
	private final ModelPart beard1;
	private final ModelPart beard2;
	private final ModelPart beardExtra;
	private final ModelPart leftFancyBeard1;
	private final ModelPart rightFancyBeard1;

	public WizardBeardModel(ModelPart var1) {
		super(var1);
		this.beard1 = this.head.getChild("beard1");
		this.beard2 = this.beard1.getChild("beard2");
		this.beardExtra = this.beard1.getChild("beardExtra");
		this.leftFancyBeard1 = this.beardExtra.getChild("leftFancyBeard1");
		this.rightFancyBeard1 = this.beardExtra.getChild("rightFancyBeard1");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("head",CubeListBuilder.create(),PartPose.offset(0F,0F,0F)).addOrReplaceChild("beard1",CubeListBuilder.create().texOffs(0,0).addBox(-2.5F,0F,0F,5F,7F,1F,new CubeDeformation(0F)),PartPose.offset(0F,-2F,-5F));
		var1.addOrReplaceChild("beard2",CubeListBuilder.create().texOffs(1,9).addBox(-1.5F,-1.5F,-0.5F,3F,3F,1F,new CubeDeformation(-0.001F)),PartPose.offsetAndRotation(0F,7F,0.5F,0F,0F,0.7854F));
		PartDefinition var2 = var1.addOrReplaceChild("beardExtra",CubeListBuilder.create(),PartPose.offset(0F,0F,0F));
		var2.addOrReplaceChild("leftFancyBeard1",CubeListBuilder.create().texOffs(1,9).addBox(0F,-2F,0F,4F,3F,1F,new CubeDeformation(-0.001F)),PartPose.offsetAndRotation(0.8F,1F,0F,0F,0F,1.0472F));
		var2.addOrReplaceChild("rightFancyBeard1",CubeListBuilder.create().texOffs(1,9).addBox(-4F,-2F,0F,4F,3F,1F,new CubeDeformation(-0.001F)),PartPose.offsetAndRotation(-0.8F,1F,0F,0F,0F,-1.0472F));
		return LayerDefinition.create(var0,16,16);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		super.setupAnim(var1,var2,var3,var4,var5,var6);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.head.render(var1,var2,var3,var4,var5);
	}
}