package xyz.pixelatedw.mineminenomi.models.morphs.partials;

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
import net.minecraft.world.phys.Vec3;

public class SpringLegsModel<T extends LivingEntity> extends HumanoidModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","spring_legs"),"main");

	public SpringLegsModel(ModelPart var1) {
		super(var1);
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot();
		var1.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(0,0).addBox(-1F,-0.25F,-2F,2F,1F,1F,new CubeDeformation(-0.01F)).texOffs(0,0).addBox(1F,0F,-2F,1F,1F,3F,new CubeDeformation(0F)).texOffs(0,0).addBox(-2F,0F,1F,4F,1F,1F,new CubeDeformation(0F)).texOffs(0,0).addBox(-2F,1F,-2F,1F,1F,4F,new CubeDeformation(0F)).texOffs(0,0).addBox(-2F,1.5F,-2F,4F,1F,1F,new CubeDeformation(0.01F)).texOffs(0,0).addBox(1F,2F,-2F,1F,1F,4F,new CubeDeformation(0F)).texOffs(0,0).addBox(-2F,2.5F,1F,4F,1F,1F,new CubeDeformation(0.01F)).texOffs(0,0).addBox(-2F,3F,-2F,1F,1F,4F,new CubeDeformation(0F)).texOffs(0,0).addBox(-2F,3.5F,-2F,4F,1F,1F,new CubeDeformation(0.01F)).texOffs(0,0).addBox(1F,4F,-2F,1F,1F,4F,new CubeDeformation(0F)).texOffs(0,0).addBox(-2F,4.5F,1F,4F,1F,1F,new CubeDeformation(0.01F)).texOffs(0,0).addBox(-2F,5F,-2F,1F,1F,4F,new CubeDeformation(0F)).texOffs(0,0).addBox(-2F,5.5F,-2F,4F,1F,1F,new CubeDeformation(0.01F)).texOffs(0,0).addBox(1F,6F,-2F,1F,1F,4F,new CubeDeformation(0F)).texOffs(0,0).addBox(-2F,6.5F,1F,4F,1F,1F,new CubeDeformation(0.01F)).texOffs(0,0).addBox(-2F,7F,-2F,1F,1F,4F,new CubeDeformation(0F)).texOffs(0,0).addBox(-2F,7.5F,-2F,4F,1F,1F,new CubeDeformation(0.01F)).texOffs(0,0).addBox(1F,8F,-2F,1F,1F,4F,new CubeDeformation(0F)).texOffs(0,0).addBox(-2F,8.5F,1F,4F,1F,1F,new CubeDeformation(0.01F)).texOffs(0,0).addBox(-2F,9F,-2F,1F,1F,4F,new CubeDeformation(0F)).texOffs(0,0).addBox(-2F,9.5F,-2F,4F,1F,1F,new CubeDeformation(0.01F)).texOffs(0,0).addBox(1F,10F,-2F,1F,1F,4F,new CubeDeformation(0F)).texOffs(0,0).addBox(-2F,10.5F,1F,4F,1F,1F,new CubeDeformation(0F)).texOffs(0,0).addBox(-2F,11F,-2F,1F,1F,4F,new CubeDeformation(0F)).texOffs(0,0).addBox(-1F,11F,-2F,2F,1F,1F,new CubeDeformation(0F)),PartPose.offset(-2F,12F,0F));
		var1.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(0,0).mirror().addBox(-1F,-0.25F,-2F,2F,1F,1F,new CubeDeformation(-0.01F)).mirror(false).texOffs(0,0).mirror().addBox(-2F,0F,-2F,1F,1F,3F,new CubeDeformation(0F)).mirror(false).texOffs(0,0).mirror().addBox(-2F,0F,1F,4F,1F,1F,new CubeDeformation(0F)).mirror(false).texOffs(0,0).mirror().addBox(1F,1F,-2F,1F,1F,4F,new CubeDeformation(0F)).mirror(false).texOffs(0,0).mirror().addBox(-2F,1.5F,-2F,4F,1F,1F,new CubeDeformation(0.01F)).mirror(false).texOffs(0,0).mirror().addBox(-2F,2F,-2F,1F,1F,4F,new CubeDeformation(0F)).mirror(false).texOffs(0,0).mirror().addBox(-2F,2.5F,1F,4F,1F,1F,new CubeDeformation(0.01F)).mirror(false).texOffs(0,0).mirror().addBox(1F,3F,-2F,1F,1F,4F,new CubeDeformation(0F)).mirror(false).texOffs(0,0).mirror().addBox(-2F,3.5F,-2F,4F,1F,1F,new CubeDeformation(0.01F)).mirror(false).texOffs(0,0).mirror().addBox(-2F,4F,-2F,1F,1F,4F,new CubeDeformation(0F)).mirror(false).texOffs(0,0).mirror().addBox(-2F,4.5F,1F,4F,1F,1F,new CubeDeformation(0.01F)).mirror(false).texOffs(0,0).mirror().addBox(1F,5F,-2F,1F,1F,4F,new CubeDeformation(0F)).mirror(false).texOffs(0,0).mirror().addBox(-2F,5.5F,-2F,4F,1F,1F,new CubeDeformation(0.01F)).mirror(false).texOffs(0,0).mirror().addBox(-2F,6F,-2F,1F,1F,4F,new CubeDeformation(0F)).mirror(false).texOffs(0,0).mirror().addBox(-2F,6.5F,1F,4F,1F,1F,new CubeDeformation(0.01F)).mirror(false).texOffs(0,0).mirror().addBox(1F,7F,-2F,1F,1F,4F,new CubeDeformation(0F)).mirror(false).texOffs(0,0).mirror().addBox(-2F,7.5F,-2F,4F,1F,1F,new CubeDeformation(0.01F)).mirror(false).texOffs(0,0).mirror().addBox(-2F,8F,-2F,1F,1F,4F,new CubeDeformation(0F)).mirror(false).texOffs(0,0).mirror().addBox(-2F,8.5F,1F,4F,1F,1F,new CubeDeformation(0.01F)).mirror(false).texOffs(0,0).mirror().addBox(1F,9F,-2F,1F,1F,4F,new CubeDeformation(0F)).mirror(false).texOffs(0,0).mirror().addBox(-2F,9.5F,-2F,4F,1F,1F,new CubeDeformation(0.01F)).mirror(false).texOffs(0,0).mirror().addBox(-2F,10F,-2F,1F,1F,4F,new CubeDeformation(0F)).mirror(false).texOffs(0,0).mirror().addBox(-2F,10.5F,1F,4F,1F,1F,new CubeDeformation(0F)).mirror(false).texOffs(0,0).mirror().addBox(1F,11F,-2F,1F,1F,4F,new CubeDeformation(0F)).mirror(false).texOffs(0,0).mirror().addBox(-1F,11F,-2F,2F,1F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offset(2F,12F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		if (!(var1.onGround())){
			var2 = (var2) / (4F);
			var3 = (var3) / (4F);
			if ((var1.getDeltaMovement().y) > (0.1D)){
				this.rightLeg.yScale = 1.5F;
				this.leftLeg.yScale = 1.5F;
			}
		}
		super.setupAnim(var1,var2,var3,var4,var5,var6);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.rightLeg.render(var1,var2,var3,var4,var5);
		this.leftLeg.render(var1,var2,var3,var4,var5);
	}
}