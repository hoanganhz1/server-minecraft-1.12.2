package xyz.pixelatedw.mineminenomi.models.entities.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.PacifistaEntity;

public class PacifistaModel<T extends PacifistaEntity> extends HumanoidModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","pacifista"),"main");

	public PacifistaModel(ModelPart var1) {
		super(var1);
	}
	public static LayerDefinition createBodyLayer() {
		return LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0F),0F),64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		super.setupAnim(var1,var2,var3,var4,var5,var6);
		this.body.xScale = 1.3F;
		this.body.yScale = 1.2F;
		this.body.zScale = 1.6F;
		this.rightArm.yScale = 1.2F;
		this.leftArm.yScale = 1.2F;
		this.rightLeg.yScale = 0.9F;
		this.leftLeg.yScale = 0.9F;
		this.body.y = -0.4F;
		this.rightArm.x = -6F;
		this.rightArm.y = 2F;
		this.leftArm.x = 6F;
		this.leftArm.y = 2F;
		this.rightLeg.y = 14F;
		this.leftLeg.y = 14F;
		this.head.y = -0F;
		this.hat.y = -0F;
	}
	public void prepareMobModel(T var1, float var2, float var3, float var4) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		super.renderToBuffer(var1,var2,var3,var4,var5);
	}
}