package xyz.pixelatedw.mineminenomi.models.armors;

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

public class WizardHatModel<T extends LivingEntity> extends HumanoidModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","wizard_hat"),"main");
	private final ModelPart hat1;
	private final ModelPart hat2;
	private final ModelPart hat3;
	private final ModelPart hat4;
	private final ModelPart hat5;

	public WizardHatModel(ModelPart var1) {
		super(var1);
		this.hat1 = this.head.getChild("hat1");
		this.hat2 = this.hat1.getChild("hat2");
		this.hat3 = this.hat2.getChild("hat3");
		this.hat4 = this.hat3.getChild("hat4");
		this.hat5 = this.hat4.getChild("hat5");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		var0.getRoot().addOrReplaceChild("head",CubeListBuilder.create(),PartPose.offset(0F,0F,0F)).addOrReplaceChild("hat1",CubeListBuilder.create().texOffs(0,0).addBox(-4.5F,0F,-4.5F,9F,2F,9F,new CubeDeformation(0F)),PartPose.offset(0F,-8.5F,0F)).addOrReplaceChild("hat2",CubeListBuilder.create().texOffs(0,11).addBox(-4F,-2F,-4F,8F,2F,8F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F)).addOrReplaceChild("hat3",CubeListBuilder.create().texOffs(0,21).addBox(-3.5F,-5F,-3.5F,7F,3F,7F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F)).addOrReplaceChild("hat4",CubeListBuilder.create().texOffs(0,31).addBox(-3F,-7F,-3F,6F,2F,6F,new CubeDeformation(0F)),PartPose.offset(0F,0.5F,0F)).addOrReplaceChild("hat5",CubeListBuilder.create().texOffs(0,39).addBox(-2.5F,-9F,-2.5F,5F,2F,5F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		super.setupAnim(var1,var2,var3,var4,var5,var6);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.head.render(var1,var2,var3,var4,var5);
	}
}